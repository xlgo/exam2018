package cn.hnzxl.exam.mobile.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hnzxl.exam.base.model.BaseModel;
import cn.hnzxl.exam.base.model.MPMessage;
import cn.hnzxl.exam.base.model.MsgType;
import cn.hnzxl.exam.base.util.GUIDUtil;
import cn.hnzxl.exam.base.util.MD5Util;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.project.model.UserExamination;
import cn.hnzxl.exam.project.service.UserExaminationService;
import cn.hnzxl.exam.project.service.UserQuestionService;
import cn.hnzxl.exam.system.model.SystemConfig;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.model.UserPrize;
import cn.hnzxl.exam.system.service.SystemConfigService;
import cn.hnzxl.exam.system.service.UserPrizeService;
import cn.hnzxl.exam.system.service.UserService;
import demo.demo1.GeetestConfig;
import sdk.GeetestLib;

/**
 *  手机的相关功能
 * @author ZXL
 *
 */
@Controller
@RequestMapping("/m/")
public class MobileExamController {
	public static Logger log = Logger.getLogger(MobileExamController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserQuestionService userQuestionService;
	@Autowired
	private SystemConfigService systemconfigService;
	
	@Autowired
	private UserExaminationService userExaminationService;
	//用户的奖品
	@Autowired
	private UserPrizeService userPrizeService;
	private static List<String> school = new ArrayList<String>();
	
	@RequestMapping(value="token",method=RequestMethod.GET)
	 @ResponseBody
	 public String token(HttpServletRequest request) throws IOException{
		return request.getParameter("echostr");
	}
	
	@RequestMapping(value="token",method=RequestMethod.POST)
	 @ResponseBody
	 public MPMessage tokenPost(HttpServletRequest request,@RequestBody MPMessage message) throws IOException{
		MPMessage retMessage = new MPMessage();
		retMessage.setToUserName(message.getFromUserName());
		retMessage.setFromUserName(message.getToUserName());
		retMessage.setCreateTime(new Date().getTime()+"");
		retMessage.setMsgType(MsgType.text.name());
		if("命令".equals(message.getContent())){
			retMessage.setContent("请输入你的账号！");
		}else{
			retMessage.setContent("请输入您的密码！");
		}
		retMessage.setContent("<a href='http://exam.hnzxl.cn/index2'>点击进入</a>");
		return retMessage;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		if(userAgent.indexOf("MicroMessenger")==-1){
			return new ModelAndView("redirect:/index2");
		}
		if(!"xlgg".equals(SessionUtil.getAttribute("ms_flag"))){
			if(!"xlgg".equals(request.getParameter("ms_flag"))){
				try {
					response.sendRedirect("/");
				} catch (IOException e) {
				}
				return null;
			}else{
				Subject subject = SecurityUtils.getSubject();
				subject.logout();
				SessionUtil.setAttribute("ms_flag", "xlgg");
				try {
					response.sendRedirect("/m/login");
				} catch (IOException e) {
				}
				return null;
			}
		}
		
		if("xlgg".equals(request.getParameter("ms_flag"))){
			try {
				response.sendRedirect("/m/login");
			} catch (IOException e) {
			}
			return null;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(StringUtils.isBlank(username)){
			return new ModelAndView("/mobile/login");
		}
		/*try{
			GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), 
					GeetestConfig.isnewfailback());
				
			String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
			String validate = request.getParameter(GeetestLib.fn_geetest_validate);
			String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
			
			//从session中获取gt-server状态
			int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
			//从session中获取userid
			String userid = (String)request.getSession().getAttribute("userid");
			
			int gtResult = 0;

			if (gt_server_status_code == 1) {
				//gt-server正常，向gt-server进行二次验证
				gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
			} else {
				// gt-server非正常情况下，进行failback模式验证
				gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
			}
			if (gtResult == 1) {
			}else{
				throw new Exception("验证码验证失败！");
			}
		}catch (Exception e) {
			return new ModelAndView("/mobile/login","msg",e.getMessage());
		}*/
		log.info(username+"进行登录:info001");
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){
			String md5Password = MD5Util.MD5(username+password);
		    UsernamePasswordToken token = new UsernamePasswordToken(username, md5Password);
		    try {
		    	subject.login(token);
		    	User user = (User)subject.getSession().getAttribute("currentUser");
		    	String identity = user.getIdentity();
		    	if("1".equals(identity)){
		    	}
		    	
			} catch (AccountException e) {
				return new ModelAndView("/mobile/login","msg",e.getMessage());
			} catch (Exception e) {
				return new ModelAndView("/mobile/login","msg","用户名或者密码错误");
			}
		}
	    return new ModelAndView("redirect:/m/index");
	}
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		if(userAgent.indexOf("MicroMessenger")==-1){
			return new ModelAndView("redirect:/index");
		}
		return new ModelAndView("/mobile/index");
	}
	
	@RequestMapping("/valid")
	public ModelAndView valid(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		if(userAgent.indexOf("MicroMessenger")!=-1){
			return new ModelAndView("/mobile/valid");
		}
		return new ModelAndView("redirect:/index");
	}
	
	@RequestMapping("/doValid")
	public ModelAndView doValid(String username,String valid) {
		if(StringUtils.isBlank(username)){
			return new ModelAndView("/mobile/valid").addObject("error", "请输入账号");
		}
		if(StringUtils.isBlank(valid)){
			return new ModelAndView("/mobile/valid").addObject("error", "请输入验证码");
		}
		User user = userService.selectByUserName(username);
		if(user==null){
			return new ModelAndView("/mobile/valid").addObject("error", "验证失败，用户不存在！");
		}
		if(valid.equals(user.getArea())){
			User updUser =new User();
			updUser.setUserid(user.getUserid());
			updUser.setIdentity(valid);
			userService.updateByPrimaryKeySelective(updUser);
			return new ModelAndView("redirect:/m/index");
		}
		return new ModelAndView("/mobile/valid").addObject("error", "验证失败");
	}
	
	@RequestMapping("resetPassword")
	public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/mobile/gaimi");
	}
	
	@RequestMapping("changepassword1")
	 @ResponseBody
	 public Object changepassword1(@RequestParam(defaultValue="") String oldPassword , String newPassword, HttpServletRequest request, HttpServletResponse response){
		 Map<String,String> res= new HashMap<String,String>();
		 //缓存中的用户信息
		 User user = SessionUtil.getCurrentUser();
		 //获取最新的用户信息
		 user = userService.selectByPrimaryKey(user.getUserid());
		 if(MD5Util.MD5(user.getUsername()+oldPassword).equals(user.getPassword())){
			 user.setPassword(MD5Util.MD5(user.getUsername()+newPassword));
			 userService.updateByPrimaryKey(user);
		 }else{
			 res.put("status", "failures");
			 res.put("msg","原始密码不正确！");
			 return res;
		 }
		 res.put("status", "success");
		 res.put("msg","修改密码成功！");
		 return res;
	 }
	
	@RequestMapping("startExam")
	public ModelAndView startExam(HttpServletRequest request, HttpServletResponse response){
		String userAgent = request.getHeader("user-agent");
		if(userAgent.indexOf("MicroMessenger")==-1){
			return new ModelAndView("redirect:/index");
		}
		Map<String,Object>  userInfo = new HashMap<String, Object>();
		userInfo.put("ip", SessionUtil.getIpAddr(request));
		userInfo.put("userAgent", request.getHeader("User-Agent"));
		Map<String, Object> examInfo = userQuestionService.getExamInfo(userInfo);
		if(examInfo==null){
			return new ModelAndView("/mobile/failure");
		}
		log.info(SessionUtil.getIpAddr(request)+",用户IP:info008:info003");
		log.info(SessionUtil.getCurrentUser().getUsername()+"开始答题:info003");
		return new ModelAndView("/mobile/dati").addAllObjects(examInfo);
	}
	
	@RequestMapping("save")
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
		log.info(SessionUtil.getIpAddr(request)+",用户IP:info008:info004");
		log.info(SessionUtil.getCurrentUser().getUsername()+"提交试卷:info004");
		Map<String, String[]> userQuestionInfo = request.getParameterMap();
		String examinationId = request.getParameter("examinationId");
		userQuestionService.saveUserExam(examinationId, userQuestionInfo,"2");
		SystemConfig sc = systemconfigService.selectByPrimaryKey("choujiang");
		if(sc!=null && sc.getValue()!=null){
			
		}
		return new ModelAndView("redirect:/m/success");
	}
	
	@RequestMapping("success")
	public ModelAndView success(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("/mobile/success");
		SystemConfig sc = systemconfigService.selectByPrimaryKey("choujiang");
		User currentUser =SessionUtil.getCurrentUser();
		
		/*User nuser = userService.selectByPrimaryKey(currentUser.getId());
		if(nuser.getVerifyquestion()==null || nuser.getVerifyquestion().length()<20){
			
			User upUser = new User();
			upUser.setId(currentUser.getId());
			if(nuser.getVerifyquestion()==null){
				upUser.setVerifyquestion("0");
			}else{
				upUser.setVerifyquestion(nuser.getVerifyquestion()+"0");
			}
			userService.updateByPrimaryKeySelective(upUser);
			if(upUser.getVerifyquestion().length()>5){
				mav.addObject("msg","");
				return mav;
			}
		}else{
			mav.addObject("msg","刷爽了?");
			return mav;
		}*/
		if(sc!=null && StringUtils.isNotBlank(sc.getValue()) && sc.getValue().split(",").length==3){
			String[] value = sc.getValue().split(",");
			String curr_hhmm = DateFormatUtils.format(new Date(), "HH:mm");
			
			if(curr_hhmm.compareTo(value[1])>=0 && curr_hhmm.compareTo(value[2])<=0){
				String currDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("createTimeString", currDate);
				List<UserPrize> all = userPrizeService.selectAll(param);
				if(Integer.valueOf(value[0])>all.size()){
					UserPrize record = new UserPrize();
					//record.setId(GUIDUtil.getUUID());
					record.setUserId(currentUser.getUserid());
					record.setUserName(currentUser.getName());
					record.setValidCode(RandomStringUtils.randomNumeric(6));
					try{
						if(((RandomStringUtils.random(1, "123")+RandomStringUtils.randomNumeric(2)).equals("397"))||true ){
							userPrizeService.insert(record);
							//mav.addObject("msg",currentUser.getSchool()+"学校"+currentUser.getName()+"同学，恭喜你本次答题被系统抽中参与大奖（请耐心等待组委会工作人员联系您），快快分享给你的同学吧，也把好运带给他们！这个是你的奖品代码："+record.getValidCode());
							mav.addObject("msg",currentUser.getSchool()+"学校"+currentUser.getName()+"同学，恭喜你本次答题获得了系统随机送出的礼品（请耐心等待组委会工作人员联系您），快快分享给你的同学吧，也把好运带给他们！");
						}else{
							mav.addObject("msg","很遗憾，本次答题未能获得系统随机送出的礼物，没关系，下次还有机会哟！");
						}
					}catch (Exception e) {
						UserPrize up = new UserPrize();
						up.setFlag("???");
						up.setUserId(currentUser.getUserid());
						userPrizeService.updateByPrimaryKeySelective(up);
						mav.addObject("msg","小伙子，你已经中过奖了，你是怎么进来了，老实交代，不然你的奖品就作废了！");
					}
				}
			}
		}
		return mav;
	}
	
	
	@RequestMapping("contrast")
	public ModelAndView contrast(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String examinationId = request.getParameter("id");
		SystemConfig config = systemconfigService.selectByPrimaryKey("1");
		if(config!=null ){
			try{
				Long startDate = DateUtils.parseDate(config.getKey(), "yyyy-MM-dd HH:mm").getTime();
				Long endDate = DateUtils.parseDate(config.getValue(), "yyyy-MM-dd HH:mm").getTime();
				Long currDate = new Date().getTime();
				if(!(startDate<=currDate  && endDate >=currDate)){
					return new ModelAndView("/mobile/failure_def").addObject("errorTitle","暂未开放！").addObject("error","本期错题对比将在"+config.getKey()+" ~  "+config.getValue()+"时间段开放，到时候再来吧！");
				}
			}catch (Exception e) {
				return new ModelAndView("/mobile/failure_def").addObject("errorTitle","错误！").addObject("error","错题对比设置错误，请联系管理员！");
			}
		}else{
			return new ModelAndView("/mobile/failure_def").addObject("errorTitle","错误！").addObject("error","系统未设置开放时间，请联系管理员！");
		}
		return new ModelAndView("/mobile/contrast").addAllObjects(userExaminationService.contrast(examinationId));
	}
	/**
	 * 成绩查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("userExamInfo")
	public ModelAndView userExamInfo(HttpServletRequest request, HttpServletResponse response) {
		User currentUser =SessionUtil.getCurrentUser();
		Map<String,Object> userExaminationParam = new HashMap<String, Object>();
		userExaminationParam.put("userExaminationUserid", currentUser.getId());
		userExaminationParam.put("userExaminationStatus", "1");
		List<UserExamination> res =userExaminationService.selectAll(userExaminationParam);
		if(res.size()==0){
			return new ModelAndView("/mobile/failure_def").addObject("error","对不起，您没有参加过考试！");
		}
		return new ModelAndView("/mobile/userExamInfo").addObject("modelList", res);
	}
	
	@RequestMapping("ranking")
	@ResponseBody
	public Object ranking(HttpServletRequest request, HttpServletResponse response) {
		User currentUser =SessionUtil.getCurrentUser();
		UserExamination ue = new UserExamination();
		ue.setUserExaminationExaminationId(request.getParameter("id"));
		ue.setUserExaminationUserid(currentUser.getUserid());
		return userExaminationService.selectRanking(ue);
	}
	
	@RequestMapping("registerUser")
	public ModelAndView registerUser(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/mobile/register");
	}
	@RequestMapping("saveRegister")
	public ModelAndView saveRegister(User user, HttpServletRequest request, HttpServletResponse response,RedirectAttributes ra) {
		user.setUserid(GUIDUtil.getUUID());
		try{
			if(StringUtils.isEmpty(user.getUsername())){
				throw new Exception("用户名不能为空！");
			}
			if(StringUtils.isEmpty(user.getPassword())){
				throw new Exception("密码不能为空！");
			}
			if(StringUtils.isEmpty(user.getSchool())){
				throw new Exception("学校名称不能为空！");
			}
			user.setSchool(user.getSchool().trim());
			
			if(StringUtils.isEmpty(user.getMobilenumber())){
				throw new Exception("手机号不能为空！");
			}
			if(user.getMobilenumber().trim().length()!=11){
				throw new Exception("请输入正确的手机号！");
			}
			if(userService.isExist("username", user.getUsername())){
				throw new Exception("用户名已经存在！");
			}
			
			if(userService.isExist("mobilenumber", user.getMobilenumber())){
				throw new Exception("手机号已经存在！");
			}
			if(school.size()==0){
				String schoolPath = request.getSession().getServletContext().getRealPath("/WEB-INF/resource/data/school.json");
				Scanner s = new Scanner(new InputStreamReader(new FileInputStream(schoolPath),"utf-8"));
				StringBuilder sb = new StringBuilder("");
				while(s.hasNextLine()){
					sb.append(s.nextLine());
				}
				JSONObject json = JSON.parseObject(sb.toString());
				StringBuilder schoolStr = new StringBuilder("");
				for (String key : json.keySet()) {
					schoolStr.append(json.getString(key)).append(",");
				}
				school = Arrays.asList(schoolStr.toString().split(",")) ;
			}
			if(school.indexOf(user.getSchool())==-1){
				throw new Exception("学校不存在，请从列表中选择学校名称！");
			}
			log.info(user.getUsername()+"注册提交:info002");
			userService.insert(user);
		}catch(Exception e){
			ra.addFlashAttribute("errorMsg", e.getMessage());
			ra.addFlashAttribute("user", user);
			return new ModelAndView("redirect:/m/registerUser");
		}
		
		return new ModelAndView("redirect:/m/registerSuccess");
	}
	
	@RequestMapping("registerSuccess")
	public ModelAndView registerSuccess(User model, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/mobile/successRegister");
	}
}
