package cn.hnzxl.exam.system.controller;

import java.io.FileInputStream;
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

import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.UpdateIndexCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hnzxl.exam.base.controller.BaseController;
import cn.hnzxl.exam.base.model.DWZResult;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.base.util.GUIDUtil;
import cn.hnzxl.exam.base.util.MD5Util;
import cn.hnzxl.exam.base.util.QueryUtil;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.project.model.Examination;
import cn.hnzxl.exam.project.model.UserExamination;
import cn.hnzxl.exam.project.model.UserQuestion;
import cn.hnzxl.exam.project.service.ExaminationService;
import cn.hnzxl.exam.project.service.UserExaminationService;
import cn.hnzxl.exam.project.service.UserQuestionService;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.service.UserService;

@Controller
@RequestMapping("/system/user/")
public class UserController extends BaseController<User, String> {
	private static List<String> school = new ArrayList<String>();
	@Autowired
	private UserService userService;

	@Autowired
	private UserExaminationService userExaminationService;
	
	@Autowired
	private UserQuestionService userQuestionService;
	
	@Autowired
	private ExaminationService examinationService ;
	
	@Override
	public BaseService<User, String> getBsetService() {
		return userService;
	}

	@Override
	public Class<User> getModelClass() {
		return User.class;
	}

	@RequestMapping("hasUse")
	@ResponseBody
	public boolean hasUse(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		if(StringUtils.isBlank(username)){
			return false;
		}
		return !userService.isExist("username", username);
	}

	@RequestMapping("changepasswordDialog")
	public ModelAndView changepasswordDialog(HttpServletRequest request, HttpServletResponse response) {
		QueryUtil queryUtil = new QueryUtil(request);
		return new ModelAndView(getRequestPath(request)).addObject("searchParam", queryUtil.getParams());
	}
	
	@RequestMapping("registerUser")
	public ModelAndView registerUser(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("redirect:login2");
		//return new ModelAndView(getRequestPath(request));
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
			if(userService.isExist("username", user.getUsername())){
				throw new Exception("用户名已经存在！");
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
			
			userService.insert(user);
		}catch(Exception e){
			ra.addFlashAttribute("errorMsg", e.getMessage());
			ra.addFlashAttribute("user",user);
			return new ModelAndView("redirect:/system/user/registerUser");
		}
		return new ModelAndView("redirect:/ewm");
	}
	
	@RequestMapping("registerSuccess")
	public ModelAndView registerSuccess(User model, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(getRequestPath(request));
	}
	
	 @RequestMapping("changepassword")
	 @ResponseBody
	 public DWZResult changepassword(@RequestParam(defaultValue="") String oldPassword , String newPassword, HttpServletRequest request, HttpServletResponse response){
		 DWZResult dwzResult = new DWZResult();
		 //缓存中的用户信息
		 User user = SessionUtil.getCurrentUser();
		 //获取最新的用户信息
		 user = getBsetService().selectByPrimaryKey(user.getUserid());
		 if(MD5Util.MD5(user.getUsername()+oldPassword).equals(user.getPassword())){
			 user.setPassword(MD5Util.MD5(user.getUsername()+newPassword));
			 getBsetService().updateByPrimaryKey(user);
		 }else{
			 dwzResult.setStatusCode(300);
			 dwzResult.setMessage("原始密码不正确！");
			 return dwzResult;
		 }
		 
		 dwzResult.setMessage("修改密码成功！");
		 dwzResult.setCallbackType(DWZResult.CALLBACKTYPE_CLOSE);
		 return dwzResult;
	 }
	 
	 @RequestMapping("changepassword1")
	 @ResponseBody
	 public Object changepassword1(@RequestParam(defaultValue="") String oldPassword , String newPassword, HttpServletRequest request, HttpServletResponse response){
		 Map<String,String> res= new HashMap<String,String>();
		 //缓存中的用户信息
		 User user = SessionUtil.getCurrentUser();
		 //获取最新的用户信息
		 user = getBsetService().selectByPrimaryKey(user.getUserid());
		 if(MD5Util.MD5(user.getUsername()+oldPassword).equals(user.getPassword())){
			 user.setPassword(MD5Util.MD5(user.getUsername()+newPassword));
			 getBsetService().updateByPrimaryKey(user);
		 }else{
			 res.put("status", "failures");
			 res.put("msg","原始密码不正确！");
			 return res;
		 }
		 res.put("status", "success");
		 res.put("msg","修改密码成功！");
		 return res;
	 }
	 /**
	  * 从新答题
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("resetJSON")
	 @ResponseBody
	 public DWZResult resetJSON(HttpServletRequest request, HttpServletResponse response){
		 DWZResult dwzResult = new DWZResult();
		 String[] ids = request.getParameterValues("id");
		 
		 
		//获取能够参加的考试
		Map<String,Object> examinationParam = new HashMap<String, Object>();
		examinationParam.put("examinationTime", new Date());
		examinationParam.put("examinationStatus", 1);
		List<Examination> examinations = examinationService.selectAll(examinationParam);
		 if(examinations.size()!=0){
			 Map<String ,Object> param = new HashMap<String, Object>();
			 param.put("userExaminationUserid", ids[0]);
			 param.put("userExaminationExaminationId", examinations.get(0).getExaminationId());
			 List<UserExamination> ues = userExaminationService.selectAll(param);
			 if(ues.size()==1){
				 if(ues.get(0).getUserExaminationStatus().equals("0")){
					 dwzResult.setStatusCode(300);
					 dwzResult.setMessage("用户正在答题中，不能进行重置！");
				 }else{
					 userExaminationService.deleteByPrimaryKey(ues.get(0).getUserExaminationId());
					 
					 param.clear();
					 param.put("userQuestionExaminationId", examinations.get(0).getExaminationId());
					 param.put("userQuestionUserid", ids[0]);
					 List<UserQuestion> uqs = userQuestionService.selectAll(param);
					 
					String[] uqIds = new String[uqs.size()];
					for (int i = 0; i < uqs.size(); i++) {
						uqIds[i]=uqs.get(i).getUserQuestionId();
					}
					userQuestionService.deleteByPrimaryKeyBatch(uqIds);
					 dwzResult.setMessage("重置成功，用户可以重新开始答题了！");
				 }
			 }else{
				 dwzResult.setStatusCode(300);
				 dwzResult.setMessage("该用户并没有开始答题！");
			 }
		 }else{
			 dwzResult.setStatusCode(300);
			 dwzResult.setMessage("当前没有要参加的考试！");
		 }
		 return dwzResult;
	 }
}
