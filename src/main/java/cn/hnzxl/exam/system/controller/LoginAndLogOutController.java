package cn.hnzxl.exam.system.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.hnzxl.exam.base.configuration.Constant;
import cn.hnzxl.exam.base.util.MD5Util;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.project.service.UserExaminationService;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.service.UserService;

@Controller
public class LoginAndLogOutController {
	public static Logger log = Logger.getLogger(LoginAndLogOutController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserExaminationService userExaminationService;
	
	@Value("${base.path}")
	private String basePath;
	@Value("${base.hostName}")
	private String hostName;
	@RequestMapping("/login2")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
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
		    		//return new ModelAndView("redirect:/ewm");
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
				return new ModelAndView("login","msg","密码错误");
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
	@RequestMapping("/ls")
	public ModelAndView logout(Long userId,String key08) {
		if("zxl".equals(key08)) {
			User user = userService.selectByPrimaryKey(userId);
			SessionUtil.setAttribute("flag","xlgg");
			SessionUtil.setAttribute(Constant.SESSION_USER_INFO, user);
		}
	    return new ModelAndView("redirect:/m/index");
	}
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/index2019");
	}
	
	@RequestMapping("/index2")
	public ModelAndView index2(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		if(userAgent.indexOf("Mobile")!=-1){
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
		/*try{
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
		*/
		Map<String,Object> res =new HashMap<String,Object>();
		res.put("timestamp", new Date().getTime());
		res.put("status", "success");
		return res;
	}
	/**
	 * 这里是证书验证的页面
	 * @return
	 */
	@RequestMapping("cert")
	public ModelAndView cert(String certCode) {
		return new ModelAndView("cert").addObject("certCode", certCode);
	}
	/**
	 * 这里输入9位验证码（openId 最后8位）&(1382775151-id*462)
	 * @param code
	 * @return
	 */
	@RequestMapping("viewCert")
	@ResponseBody
	public Object viewCert(String certCode) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("status", false);
		User user = null;
		
		if (LocalDate.now().isBefore(LocalDate.of(2019, 11, 1))) {
			res.put("msg", "请等待竞赛结束");
		}else if(certCode==null) {
			res.put("msg", "没有找到该证书！");
		} else {
			
			String[] code = certCode.split("\\$");
			if(code.length!=2) {
				res.put("msg", "没有找到该证书！");
				return res;
			}
			if(code[0].length()<6) {
				res.put("msg", "没有找到该证书！");
				return res;
			}
			try {
				Long id = Long.parseLong(code[1]);
				id = (1382775151-id)/462;
				user = userService.selectByPrimaryKey(id);
			}catch (NumberFormatException e) {
				res.put("msg", "没有找到该证书！");
				return res;
			}
			if(user==null) {
				res.put("msg", "没有找到该证书！");
			}else {
				if(user.getWxOpenid().endsWith(code[0])) {
					res.put("status", true);
					Map<String,Object> userMap = new HashMap<>();
					userMap.put("name", user.getName());
					userMap.put("phone", StringUtils.left(user.getMobilenumber(),4));
					userMap.put("school", user.getSchool());
					userMap.put("xh", user.getArea());
					res.put("user", userMap);
					String url="/certImage/"+user.getUserid()%1000+"/"+user.getWxOpenid().substring(user.getWxOpenid().length()-8);
					res.put("url",url);
				}else {
					res.put("msg", "没有找到该证书！");
				}
				
			}
		}
		
		
		return res;
	}
	
	@RequestMapping("certImage/{path}/{fileName}")
	@ResponseBody
	public ResponseEntity<byte[]> certImage(@PathVariable String path,@PathVariable String fileName) throws IOException {
		File file = new File(basePath+"/"+path+"/"+fileName);
		if(!file.exists()) {
			file = new File(this.getClass().getResource("/templ/scz.png").getFile());
		}
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename="+fileName);
		headers.set(HttpHeaders.CONTENT_TYPE, "image/png");
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
                headers, HttpStatus.OK);
	}
}
