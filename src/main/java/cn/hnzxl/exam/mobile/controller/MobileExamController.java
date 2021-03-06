package cn.hnzxl.exam.mobile.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hnzxl.exam.base.configuration.Constant;
import cn.hnzxl.exam.base.model.BaseModel;
import cn.hnzxl.exam.base.model.MPMessage;
import cn.hnzxl.exam.base.model.MsgType;
import cn.hnzxl.exam.base.util.BaseConfig;
import cn.hnzxl.exam.base.util.GUIDUtil;
import cn.hnzxl.exam.base.util.MD5Util;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.base.util.WeChatConfig;
import cn.hnzxl.exam.base.util.WeiXinUtil;
import cn.hnzxl.exam.project.dto.ExamCacheInfo;
import cn.hnzxl.exam.project.model.UserExamination;
import cn.hnzxl.exam.project.service.UserExaminationService;
import cn.hnzxl.exam.project.service.UserQuestionService;
import cn.hnzxl.exam.project.util.CertImageUtil;
import cn.hnzxl.exam.system.model.SystemConfig;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.model.UserPrize;
import cn.hnzxl.exam.system.service.SystemConfigService;
import cn.hnzxl.exam.system.service.UserPrizeService;
import cn.hnzxl.exam.system.service.UserService;
import demo.demo1.GeetestConfig;
import lombok.Builder.Default;
import sdk.GeetestLib;

/**
 * 手机的相关功能
 * 
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
	private BaseConfig baseConfig;
	@Autowired
	private WeChatConfig weChatConfig;
	@Autowired
	private UserExaminationService userExaminationService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	// 用户的奖品
	@Autowired
	private UserPrizeService userPrizeService;
	private static List<String> school = new ArrayList<String>();

	@RequestMapping(value = "token", method = RequestMethod.GET)
	@ResponseBody
	public String token(String signature, String echostr, String timestamp, String nonce) throws IOException {
		boolean checked = WeiXinUtil.checkSignature(signature, timestamp, nonce);
		if (!checked) {
			return "signature验证失败！";
		}
		return echostr;
	}

	@RequestMapping(value = "token", method = RequestMethod.POST)
	@ResponseBody
	public Object tokenPost(HttpServletRequest request, @RequestBody MPMessage message) throws IOException {
		String msgType = message.getMsgType();

		MPMessage retMessage = new MPMessage();
		retMessage.setToUserName(message.getFromUserName());
		retMessage.setFromUserName(message.getToUserName());
		retMessage.setCreateTime(new Date().getTime() + "");
		retMessage.setMsgType(MsgType.text.name());
		String content = "未处理的类型！";
		/*
		 * if(message!=null){ retMessage.setContent(JSON.toJSONString(message)); return
		 * retMessage; }
		 */
		if (MsgType.event.name().equals(msgType)) {
			// String userInfo = WeiXinUtil.userInfo(message.getFromUserName());
			switch (message.getEvent()) {
			case "subscribe":// 订阅
				content = "亲爱的，欢迎来到大学生的聚集地，一个年轻人嗨玩的社交平台！";
				break;
			case "unsubscribe":// 取消订阅
				content = "取消订阅";
				break;
			case "CLICK":// 菜单点击
				switch (message.getEventKey()) {
				case "start_exam":
					String url = baseConfig.getHostName() + "/m/login?openId=" + message.getFromUserName();
					url += "&signature=" + DigestUtils.md5Hex(message.getFromUserName() + weChatConfig.getAppId());
					content = "<a href='" + url + "'>您已经通过身份唯一性验证，点击开始答题</a>";
					break;
				case "good":
					content = "谢谢支持，我们会更加努力！";
					break;
				case "lxwm-swhz":
					content = "商务合作请联系QQ:3001496102";
					break;
				default:
					content = "未定义菜单功能！";
					break;
				}
				break;
			default:
				content = "未知的事件类型！";
				break;
			}

		} else if (MsgType.text.name().equals(msgType)) {
			if (StringUtils.contains(message.getContent(), "答题") || StringUtils.contains(message.getContent(), "考试")
					|| StringUtils.contains(message.getContent(), "验证")) {
				// String userInfo = WeiXinUtil.userInfo(message.getFromUserName());
				String url = baseConfig.getHostName() + "/m/login?openId=" + message.getFromUserName();
				url += "&signature=" + DigestUtils.md5Hex(message.getFromUserName() + weChatConfig.getAppId());
				content = "<a href='" + url + "'>您已经通过身份唯一性验证，点击开始答题</a>";
			} else if (StringUtils.containsIgnoreCase(message.getContent(), "success")) {
				return message.getContent();
			} else if (StringUtils.containsIgnoreCase(message.getContent(), "empty")) {
				return "";
			} else if (StringUtils.containsIgnoreCase(message.getContent(), "null")) {
				return null;
			} else if (StringUtils.containsIgnoreCase(message.getContent(), "ok！")) {
				return "ok！";
			} else if (StringUtils.containsIgnoreCase(message.getContent(), "fromUserName")) {
				content = message.getFromUserName();
			} else if (StringUtils.containsIgnoreCase(message.getContent(), "accessToken")) {
				content = WeiXinUtil.getAccessToken();
			} else {
				if (message.getFromUserName().equals("oMwu30Qu71mi6Jz_SuZD0FEirlWk")) {
					WeiXinUtil.messageCustomSendText("oMwu30W4hosuh2qVZopcvevobdVk", message.getContent());
					return "";
				} else if (message.getFromUserName().equals("oMwu30W4hosuh2qVZopcvevobdVk")) {
					WeiXinUtil.messageCustomSendText("oMwu30Qu71mi6Jz_SuZD0FEirlWk", message.getContent());
					return "";
				}
				// content=message.getContent();
				return "";
			}
		} else if (MsgType.image.name().equals(msgType)) {
			if (message.getFromUserName().equals("oMwu30Qu71mi6Jz_SuZD0FEirlWk")) {
				WeiXinUtil.messageCustomSendImage("oMwu30W4hosuh2qVZopcvevobdVk", message.getMediaId());
				return "";
			} else if (message.getFromUserName().equals("oMwu30W4hosuh2qVZopcvevobdVk")) {
				WeiXinUtil.messageCustomSendImage("oMwu30Qu71mi6Jz_SuZD0FEirlWk", message.getMediaId());
				return "";

			}

			content = "收到您的图片了";
		} else if (MsgType.voice.name().equals(msgType)) {
			if (message.getFromUserName().equals("oMwu30Qu71mi6Jz_SuZD0FEirlWk")) {
				WeiXinUtil.messageCustomSendVoice("oMwu30W4hosuh2qVZopcvevobdVk", message.getMediaId());
				return "";
			} else if (message.getFromUserName().equals("oMwu30W4hosuh2qVZopcvevobdVk")) {
				WeiXinUtil.messageCustomSendVoice("oMwu30Qu71mi6Jz_SuZD0FEirlWk", message.getMediaId());
				return "";
			}

			content = "收到您的语音了";
		} else {

			content = JSON.toJSONString(message);
		}
		retMessage.setContent(content);
		// retMessage.setContent("<a
		// href='"+JSON.parseObject(userInfo).getString("headimgurl")+"'>亲爱的"+JSON.parseObject(userInfo).getString("nickname")+"，你好。点击查看你的头像</a>");
		return retMessage;
	}

	@RequestMapping("/noFollow")
	public ModelAndView noFollow(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/mobile/no_follow");
	}

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("redirect:/m/index");
	}

	@RequestMapping("/reginfo")
	public String reginfo(Model model) {
		User currentUser = SessionUtil.getCurrentUser();
		if (currentUser.getStatus() == 1) {
			//return "redirect:/m/index";
		}
		model.addAttribute("user", currentUser);
		return "/mobile/reginfo";
	}

	@RequestMapping("/reginfoSubmit")
	@ResponseBody
	public String reginfoSubmit(User user, Model model) {
		user.setUserid(SessionUtil.getCurrentUser().getUserid());
		user.setStatus(1);
		userService.updateByPrimaryKeySelective(user);
		User u = userService.selectByPrimaryKey(SessionUtil.getCurrentUser().getUserid());
		SessionUtil.setAttribute(Constant.SESSION_USER_INFO, u);
		return "success";
	}

	@RequestMapping("/rule")
	public String rule() {
		return "/mobile/rule";
	}
	
	@RequestMapping("/rule2")
	public String rule2() {
		return "/mobile/rule2";
	}
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

		return new ModelAndView("/mobile/index").addObject("user", SessionUtil.getCurrentUser());
	}

	@RequestMapping("/valid")
	public ModelAndView valid(HttpServletRequest request, HttpServletResponse response) {
		String userAgent = request.getHeader("user-agent");
		if (userAgent.indexOf("Mobile") != -1) {
			return new ModelAndView("/mobile/valid");
		}
		return new ModelAndView("redirect:/index");
	}

	@RequestMapping("/doValid")
	public ModelAndView doValid(String username, String valid) {
		if (StringUtils.isBlank(username)) {
			return new ModelAndView("/mobile/valid").addObject("error", "请输入账号");
		}
		if (StringUtils.isBlank(valid)) {
			return new ModelAndView("/mobile/valid").addObject("error", "请输入验证码");
		}
		User user = userService.selectByUserName(username);
		if (user == null) {
			return new ModelAndView("/mobile/valid").addObject("error", "验证失败，用户不存在！");
		}
		if (valid.equals(user.getArea())) {
			User updUser = new User();
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
	public Object changepassword1(@RequestParam(defaultValue = "") String oldPassword, String newPassword,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> res = new HashMap<String, String>();
		// 缓存中的用户信息
		User user = SessionUtil.getCurrentUser();
		// 获取最新的用户信息
		user = userService.selectByPrimaryKey(user.getUserid());
		if (MD5Util.MD5(user.getUsername() + oldPassword).equals(user.getPassword())) {
			user.setPassword(MD5Util.MD5(user.getUsername() + newPassword));
			userService.updateByPrimaryKey(user);
		} else {
			res.put("status", "failures");
			res.put("msg", "原始密码不正确！");
			return res;
		}
		res.put("status", "success");
		res.put("msg", "修改密码成功！");
		return res;
	}

	@RequestMapping("startExam")
	public ModelAndView startExam(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put("ip", SessionUtil.getIpAddr(request));
		userInfo.put("userAgent", request.getHeader("User-Agent"));
		Map<String, Object> examInfo;
		synchronized (SessionUtil.getCurrentUser().getUserid()) {
			examInfo = userQuestionService.getExamInfo(userInfo);
		}
		if (examInfo == null) {
			return new ModelAndView("/mobile/failure");
		}
		log.info(SessionUtil.getIpAddr(request) + ",用户IP:info008:info003");
		log.info(SessionUtil.getCurrentUser().getUsername() + "开始答题:info003");
		return new ModelAndView("/mobile/dati").addAllObjects(examInfo);
	}

	@RequestMapping("save")
	public ModelAndView save(Long examinationId, HttpServletRequest request, HttpServletResponse response) {
		log.info(SessionUtil.getIpAddr(request) + ",用户IP:info008:info004");
		log.info(SessionUtil.getCurrentUser().getUsername() + "提交试卷:info004");
		Map<String, String[]> userQuestionInfo = new HashMap<>(request.getParameterMap());

		// userQuestionInfo.put("userId", new
		// String[]{SessionUtil.getCurrentUser().getUserid()+""});
		// stringRedisTemplate.opsForList().leftPush("exam:2019:saveExam",
		// JSON.toJSONString(userQuestionInfo));
		userQuestionInfo.remove("examinationId");
		userQuestionInfo.remove("userId");

		userQuestionService.saveUserExam(examinationId, userQuestionInfo, "2");

		return new ModelAndView("redirect:/m/success");
	}

	@RequestMapping("success")
	public ModelAndView success(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("/mobile/success");
		SystemConfig sc = systemconfigService.selectByPrimaryKey(2L);
		User currentUser = SessionUtil.getCurrentUser();

		/*
		 * User nuser = userService.selectByPrimaryKey(currentUser.getId());
		 * if(nuser.getVerifyquestion()==null || nuser.getVerifyquestion().length()<20){
		 * 
		 * User upUser = new User(); upUser.setId(currentUser.getId());
		 * if(nuser.getVerifyquestion()==null){ upUser.setVerifyquestion("0"); }else{
		 * upUser.setVerifyquestion(nuser.getVerifyquestion()+"0"); }
		 * userService.updateByPrimaryKeySelective(upUser);
		 * if(upUser.getVerifyquestion().length()>5){ mav.addObject("msg",""); return
		 * mav; } }else{ mav.addObject("msg","刷爽了?"); return mav; }
		 */
		if (sc != null && StringUtils.isNotBlank(sc.getValue()) && sc.getValue().split(",").length == 3) {
			String[] value = sc.getValue().split(",");
			String curr_hhmm = DateFormatUtils.format(new Date(), "HH:mm");

			if (curr_hhmm.compareTo(value[1]) >= 0 && curr_hhmm.compareTo(value[2]) <= 0) {
				String currDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("createTimeString", currDate);
				List<UserPrize> all = userPrizeService.selectAll(param);
				if (Integer.valueOf(value[0]) > all.size()) {
					UserPrize record = new UserPrize();
					// record.setId(GUIDUtil.getUUID());
					record.setUserId(currentUser.getUserid());
					record.setUserName(currentUser.getName());
					record.setValidCode(RandomStringUtils.randomNumeric(6));
					try {
						if (((RandomStringUtils.random(1, "123") + RandomStringUtils.randomNumeric(2)).equals("397"))
								|| true) {
							userPrizeService.insert(record);
							// mav.addObject("msg",currentUser.getSchool()+"学校"+currentUser.getName()+"同学，恭喜你本次答题被系统抽中参与大奖（请耐心等待组委会工作人员联系您），快快分享给你的同学吧，也把好运带给他们！这个是你的奖品代码："+record.getValidCode());
							mav.addObject("msg", currentUser.getSchool() + "学校" + currentUser.getName()
									+ "同学，恭喜你本次答题获得了系统随机送出的礼品（请耐心等待组委会工作人员联系您），快快分享给你的同学吧，也把好运带给他们！");
						} else {
							mav.addObject("msg", "很遗憾，本次答题未能获得系统随机送出的礼物，没关系，下次还有机会哟！");
						}
					} catch (Exception e) {
						UserPrize up = new UserPrize();
						up.setFlag("???");
						up.setUserId(currentUser.getUserid());
						userPrizeService.updateByPrimaryKeySelective(up);
						mav.addObject("msg", "小伙子，你已经中过奖了，你是怎么进来了，老实交代，不然你的奖品就作废了！");
					}
				}
			}
		}
		return mav;
	}

	@RequestMapping("contrast")
	public ModelAndView contrast(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SystemConfig config = systemconfigService.selectByPrimaryKey(1L);
		if (config != null) {
			try {
				Long startDate = DateUtils.parseDate(config.getKey(), "yyyy-MM-dd HH:mm").getTime();
				Long endDate = DateUtils.parseDate(config.getValue(), "yyyy-MM-dd HH:mm").getTime();
				Long currDate = new Date().getTime();
				if (!(startDate <= currDate && endDate >= currDate)) {
					return new ModelAndView("/mobile/failure_def").addObject("errorTitle", "暂未开放！").addObject("error",
							"本期错题对比将在" + config.getKey() + " ~  " + config.getValue() + "时间段开放，到时候再来吧！");
				}
			} catch (Exception e) {
				return new ModelAndView("/mobile/failure_def").addObject("errorTitle", "错误！").addObject("error",
						"错题对比设置错误，请联系管理员！");
			}
		} else {
			return new ModelAndView("/mobile/failure_def").addObject("errorTitle", "错误！").addObject("error",
					"系统未设置开放时间，请联系管理员！");
		}
		return new ModelAndView("/mobile/contrast").addAllObjects(userExaminationService.contrast(id));
	}

	/**
	 * 成绩查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("userExamInfo")
	public ModelAndView userExamInfo(HttpServletRequest request, HttpServletResponse response) {
		User currentUser = SessionUtil.getCurrentUser();
		Map<String, Object> userExaminationParam = new HashMap<String, Object>();
		userExaminationParam.put("userExaminationUserid", currentUser.getId());
		userExaminationParam.put("userExaminationStatus", "1");
		List<UserExamination> res = userExaminationService.selectAll(userExaminationParam);
		if (res.size() == 0) {
			return new ModelAndView("/mobile/failure_def").addObject("error", "对不起，您没有参加过考试！");
		}
		return new ModelAndView("/mobile/userExamInfo").addObject("modelList", res);
	}

	@RequestMapping("ranking")
	@ResponseBody
	public Object ranking(Long id, HttpServletRequest request, HttpServletResponse response) {
		User currentUser = SessionUtil.getCurrentUser();
		UserExamination ue = new UserExamination();
		ue.setUserExaminationExaminationId(id);
		ue.setUserExaminationUserid(currentUser.getUserid());
		return userExaminationService.selectRanking(ue);
	}

	@RequestMapping("registerUser")
	public ModelAndView registerUser(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/mobile/register");
	}

	@RequestMapping("saveRegister")
	public ModelAndView saveRegister(User user, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) {
		// user.setUserid(GUIDUtil.getUUID());
		try {  
			if (StringUtils.isEmpty(user.getUsername())) {
				throw new Exception("用户名不能为空！");
			}
			if (StringUtils.isEmpty(user.getPassword())) {
				throw new Exception("密码不能为空！");
			}
			if (StringUtils.isEmpty(user.getSchool())) {
				throw new Exception("学校名称不能为空！");
			}
			user.setSchool(user.getSchool().trim());

			if (StringUtils.isEmpty(user.getMobilenumber())) {
				throw new Exception("手机号不能为空！");
			}
			if (user.getMobilenumber().trim().length() != 11) {
				throw new Exception("请输入正确的手机号！");
			}
			if (userService.isExist("username", user.getUsername())) {
				throw new Exception("用户名已经存在！");
			}

			if (userService.isExist("mobilenumber", user.getMobilenumber())) {
				throw new Exception("手机号已经存在！");
			}
			if (school.size() == 0) {
				String schoolPath = request.getSession().getServletContext()
						.getRealPath("/WEB-INF/resource/data/school.json");
				Scanner s = new Scanner(new InputStreamReader(new FileInputStream(schoolPath), "utf-8"));
				StringBuilder sb = new StringBuilder("");
				while (s.hasNextLine()) {
					sb.append(s.nextLine());
				}
				JSONObject json = JSON.parseObject(sb.toString());
				StringBuilder schoolStr = new StringBuilder("");
				for (String key : json.keySet()) {
					schoolStr.append(json.getString(key)).append(",");
				}
				school = Arrays.asList(schoolStr.toString().split(","));
			}
			if (school.indexOf(user.getSchool()) == -1) {
				throw new Exception("学校不存在，请从列表中选择学校名称！");
			}
			log.info(user.getUsername() + "注册提交:info002");
			String openId = (String) SessionUtil.getAttribute("openId");
			if (SessionUtil.getAttribute("openId") != null) {

				User hasExisOpenId = userService.selectByWxOpenid(openId);
				if (hasExisOpenId != null) {
					throw new Exception("该微信账号已经关联系统账号(" + hasExisOpenId.getUsername() + ")，无法注册！");
				}
				user.setWxOpenid(openId);
			}
			userService.insert(user);
		} catch (Exception e) {
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

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		String sessOpenId = (String) SessionUtil.getAttribute("openId");
		Subject subject = SecurityUtils.getSubject();
		subject.logout();

		if (sessOpenId != null) {
			SessionUtil.setAttribute("openId", sessOpenId);
		}
		return new ModelAndView("redirect:/m/login");
	}

	@RequestMapping("heartbeat")
	@ResponseBody
	public Object heartbeat(String info, String info2, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("timestamp", new Date().getTime());
		res.put("status", "success");
		return res;
	}

	@RequestMapping("viewCert")
	@ResponseBody
	public Object viewCert() {
		User currentUser = SessionUtil.getCurrentUser();
		Map<String, Object> res = new HashMap<String, Object>();
		
		res.put("status", false);
		
		if (LocalDate.now().isBefore(LocalDate.of(2019, 12, 1)) && StringUtils.isEmpty(currentUser.getUsername())) {
			res.put("msg", "请等待竞赛结束");
		}else if("over".equals(currentUser.getPassword())) {
			res.put("status", true);
			res.put("url", "/m/certInfo");
			
		} else {
			//"775151-"+currentUser.getId(); MD5 文件名，id%1000 文件路径
			//没有就初始化，初始化比较慢，计入缓存（初始化先看缓存是否有值，有说明在处理中）
			//处理中给提示
			//完毕给图片
			Map<String, Object> userExaminationParam = new HashMap<String, Object>();
			userExaminationParam.put("userExaminationUserid", currentUser.getId());
			userExaminationParam.put("userExaminationStatus", "1");
			List<UserExamination> examList = userExaminationService.selectAll(userExaminationParam);
			int maxScore = 0;
			Optional<UserExamination> max = examList.stream()
					.max((o1, o2) -> o1.getUserExaminationScore().compareTo(o2.getUserExaminationScore()));
			if (max.isPresent()) {
				maxScore = max.get().getUserExaminationScore();
			}
			User updUser = new User();
			updUser.setUserid(currentUser.getUserid());
			updUser.setPassword("over");
			updUser.setAge(maxScore);
			userService.updateByPrimaryKeySelective(updUser);
			
			//更新后修改session中的数据,不用在去查询一次了
			currentUser.setPassword("over");
			currentUser.setAge(maxScore);
			SessionUtil.setAttribute(Constant.SESSION_USER_INFO, currentUser);
			
			//System.out.println(maxScore);
			if(maxScore<70) {//小于70分的内有证书
				res.put("msg", "您没有达到获得证书的要求，继续努力！");
			}else {
				//stringRedisTemplate.opsForList().leftPush(ExamCacheInfo.KEY_PREFIX+":cert", "", value);
				
				ExamCacheInfo eci = ExamCacheInfo.getCert(currentUser.getUserid());

				Map<String, Object> chacheMap = new HashMap<>();
				chacheMap.put("user", currentUser);
				chacheMap.put("score", maxScore);
				
				redisTemplate.opsForHash().put(eci.getDataKey(), eci.getUserId(), chacheMap);
				redisTemplate.opsForList().leftPush(eci.KEY_PREFIX, eci);
				
				res.put("status", true);
				res.put("url", "/m/certInfo");
			}
		}
		return res;
	}
	
	
	@RequestMapping("/certInfo")
	public ModelAndView certInfo() {
		ModelAndView modelAndView = new ModelAndView("/mobile/cert_info");
		User currentUser = SessionUtil.getCurrentUser();
		//这里会分三种情况
		//1.没有生成  2.分数不够70分，3.已经生成
		if("over".equals(currentUser.getPassword())) {
			if(currentUser.getAge()<70) {
				modelAndView.addObject("msg", "您没有达到获得证书的要求，继续努力！");
			}else {
				modelAndView.addObject("certCode", CertImageUtil.encodeCertCode(currentUser.getUserid(), currentUser.getWxOpenid().substring(currentUser.getWxOpenid().length()-8)));
				modelAndView.addObject("hostName", baseConfig.getHostName());
				String url="/certImage/"+currentUser.getUserid()%1000+"/"+currentUser.getWxOpenid().substring(currentUser.getWxOpenid().length()-8);
				modelAndView.addObject("certImage",url);
			}
			
		}else{
			modelAndView.addObject("msg", "请在 成绩查询->查看证书 对证书进行生成！");
		}
		return modelAndView;
	}
}
