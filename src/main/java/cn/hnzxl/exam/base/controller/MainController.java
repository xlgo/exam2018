package cn.hnzxl.exam.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.hnzxl.exam.base.configuration.Constant;
import cn.hnzxl.exam.base.util.BaseConfig;
import cn.hnzxl.exam.base.util.WeiXinUtil;
import cn.hnzxl.exam.base.util.bean.MpUserInfo;
import cn.hnzxl.exam.base.util.bean.Oauth2AccessToken;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	@Autowired
	private BaseConfig baseConfig;
	@Autowired
	private UserService userService;

	@RequestMapping("oauth2")
	public ModelAndView oauth2(String formUrl, String code, String state, HttpServletRequest request) {
		Oauth2AccessToken accessToken = WeiXinUtil.oauth2AccessToken(code);
		// WeiXinUtil.oauth2RefreshToken(accessToken.getRefreshToken());
		if (StringUtils.isEmpty(accessToken.getOpenid())) {
			ModelAndView modelAndView = new ModelAndView("redirect:/error");
			modelAndView.addObject("msg", "获取授权错误！");
			return modelAndView;
		}
		User sysUser = userService.selectByWxOpenid(accessToken.getOpenid());
		if (sysUser == null) {
			MpUserInfo userInfo = WeiXinUtil.oauth2UserInfo(accessToken.getAccessToken(), accessToken.getOpenid());
			log.info("获取成功：" + userInfo);

			sysUser = new User();
			sysUser.setWxOpenid(userInfo.getOpenid());
			//sysUser.setName(userInfo.getNickname());
			sysUser.setNickname(userInfo.getNickname());
			sysUser.setHeadimgurl(userInfo.getHeadimgurl());
			sysUser.setStatus(0);
			userService.insert(sysUser);
		}
		request.getSession().setAttribute(Constant.SESSION_USER_INFO, sysUser);
		return new ModelAndView("redirect:" + formUrl);
	}

	@GetMapping("admin2")
	public ModelAndView adminMain(HttpServletRequest request) {
		request.setAttribute("user", request.getSession().getAttribute(Constant.SESSION_ADMIN_INFO));
		return new ModelAndView("main");
	}
}
