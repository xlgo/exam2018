package cn.hnzxl.exam.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.hnzxl.exam.base.util.MD5Util;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.mobile.controller.MobileExamController;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.service.UserService;

@Controller
public class LoginAndLogOutController {
	public static Logger log = Logger.getLogger(LoginAndLogOutController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login2")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		if(userAgent.indexOf("MicroMessenger")!=-1){
			return new ModelAndView("redirect:/m/login");
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(StringUtils.isBlank(username)){
			return new ModelAndView("login");
		}
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){
			String md5Password = MD5Util.MD5(username+password);
		    UsernamePasswordToken token = new UsernamePasswordToken(username, md5Password);
		    try {
		    	subject.login(token);
		    	User user = (User)subject.getSession().getAttribute("currentUser");
		    	String identity = user.getIdentity();
		    	if("1".equals(identity)){
		    		return new ModelAndView("redirect:admin");
		    	}
		    	if(!"1".equals(identity)){
		    		subject.logout();
		    		//throw new RuntimeException("请关注微信后进行登录！");
		    		return new ModelAndView("redirect:/ewm");
		    	}
		    	
		    	if(StringUtils.isEmpty(identity)){
		    		String valid = user.getArea();
		    		if(StringUtils.isEmpty(valid)){
		    			valid = RandomStringUtils.randomNumeric(6);
				    	User updateUser = new User();
				    	updateUser.setArea(valid);
				    	updateUser.setUserid(user.getUserid());
				    	userService.updateByPrimaryKeySelective(updateUser);
		    		}
		    		return new ModelAndView("valid").addObject("validCode", valid);
		    	}
			} catch (AccountException e) {
				return new ModelAndView("login","msg",e.getMessage());
			} catch (Exception e) {
				return new ModelAndView("login","msg",e.getMessage());
			}
		}
	    return new ModelAndView("redirect:login2");
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	    return new ModelAndView("redirect:login2");
	}
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		if(userAgent.indexOf("MicroMessenger")!=-1){
			return new ModelAndView("redirect:/m/index");
		}
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		User user =SessionUtil.getCurrentUser();
		
		user=userService.selectByPrimaryKey(user.getUserid());
		if(StringUtils.isEmpty(user.getIdentity())){
    		String valid = user.getArea();
    		if(StringUtils.isEmpty(valid)){
    			valid = RandomStringUtils.randomNumeric(6);
		    	User updateUser = new User();
		    	updateUser.setArea(valid);
		    	updateUser.setUserid(user.getUserid());
		    	userService.updateByPrimaryKeySelective(updateUser);
    		}
    		return new ModelAndView("valid").addObject("validCode", valid);
    	}
		return new ModelAndView("index");
	}
	
	@RequestMapping("/index2")
	public ModelAndView index2(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		if(userAgent.indexOf("MicroMessenger")!=-1){
			SessionUtil.setAttribute("ms_flag", "xlgg");
			return new ModelAndView("redirect:/m/login");
		}
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		return new ModelAndView("index2");
	}
	@RequestMapping("/admin")
	public ModelAndView admin(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("admin");
	}
	@RequestMapping("/ewm")
	public ModelAndView ewm(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("ewm");
	}
	@RequestMapping("heartbeat")
	@ResponseBody
	public Object heartbeat(String info,String info2,HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		try{
			if(JSON.parseArray(info).size()==0|| JSON.parseArray(info2).size()==0){
				log.info(SessionUtil.getIpAddr(request)+",用户IP:info008:exp002");
				log.error(SessionUtil.getCurrentUser().getUsername()+"，初始化为空:exp002！");
			}
			if(JSON.parseArray(info2).size()>1){
				log.info(SessionUtil.getIpAddr(request)+",用户IP:info008:exp001");
				log.error(SessionUtil.getCurrentUser().getUsername()+"，有异常答题信息:exp001！");
				log.error(SessionUtil.getCurrentUser().getUsername()+info2);
			}
			log.info(SessionUtil.getCurrentUser().getUsername()+info+":info000");
		}catch (Exception e) {
			log.info(SessionUtil.getIpAddr(request)+",用户IP:info008:exp000");
			log.error(SessionUtil.getCurrentUser().getUsername()+"，心跳参数错误:exp000！");
		}
		Map<String,Object> res =new HashMap<String,Object>();
		res.put("timestamp", new Date().getTime());
		res.put("status", "success");
		return res;
	}
}
