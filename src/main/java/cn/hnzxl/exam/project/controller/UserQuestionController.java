package cn.hnzxl.exam.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.hnzxl.exam.base.controller.BaseController;
import cn.hnzxl.exam.base.util.PageUtil;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.project.model.UserQuestion;
import cn.hnzxl.exam.project.service.ExaminationService;
import cn.hnzxl.exam.project.service.UserQuestionService;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.service.UserService;

/**
 * 用户试卷表的控制器
 * @author ZXL
 * @date 2014年11月11日 下午10:51:50
 *
 */
@Controller
@RequestMapping("/project/userquestion/")
public class UserQuestionController  extends BaseController<UserQuestion,Long>{
	@Autowired
	private UserQuestionService userQuestionService;
	@Autowired
	private ExaminationService examinationService;
	@Autowired
	private UserService userService;
	@Override
	public UserQuestionService getBsetService() {
		return userQuestionService;
	}
	@Override
	public Class<UserQuestion> getModelClass() {
		return UserQuestion.class;
	}
	@RequestMapping("startExam")
	public ModelAndView startExam(HttpServletRequest request, HttpServletResponse response){
		User user =SessionUtil.getCurrentUser();
		user=userService.selectByPrimaryKey(user.getUserid());
		if(StringUtils.isEmpty(user.getIdentity())){
    		return new ModelAndView("redirect:/index");
    	}
		Map<String,Object>  userInfo = new HashMap<String, Object>();
		userInfo.put("ip", request.getHeader("X-Real-IP"));
		userInfo.put("userAgent", request.getHeader("User-Agent"));
		Map<String, Object> examInfo = userQuestionService.getExamInfo(userInfo);
		if(examInfo==null){
			return new ModelAndView("/project/userquestion/failure");
		}
		return new ModelAndView(getRequestPath(request)).addAllObjects(examInfo);
	}
	
	@RequestMapping("startExamJSON")
	@ResponseBody
	public Object startExamJSON(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object>  userInfo = new HashMap<String, Object>();
		return userQuestionService.getExamInfo(userInfo);
	}
	@RequestMapping("save")
	public ModelAndView save(Long examinationId,HttpServletRequest request, HttpServletResponse response) {
		User currentUser =SessionUtil.getCurrentUser();
		Map<String, String[]> userQuestionInfo = new HashMap<>(request.getParameterMap());
		userQuestionInfo.remove("examinationId");
		userQuestionService.saveUserExam(examinationId, userQuestionInfo,"1");
		return new ModelAndView("redirect:/project/userquestion/success");
	}
	
	@RequestMapping("success")
	public ModelAndView success(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/project/userquestion/success");
	}
	
	@RequestMapping("userExamInfo")
	public ModelAndView userExamInfo(HttpServletRequest request, HttpServletResponse response) {
		User currentUser =SessionUtil.getCurrentUser();
		Map<String,Object> userExaminationParam = new HashMap<String, Object>();
		userExaminationParam.put("userExaminationUserid", currentUser.getId());
		userExaminationParam.put("user_examination_status", "1");
		userQuestionService.selectAll(userExaminationParam);
		return new ModelAndView("redirect:/project/userquestion/success");
	}
	
	@RequestMapping("rightSort")
	public ModelAndView rightSort(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> param = new HashMap<String, Object>();
		return new ModelAndView(getRequestPath(request)).addObject("modelList",  getBsetService().selectRightSort(param));
	}
	
	@RequestMapping("errorSort")
	public ModelAndView errorSort(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> param = new HashMap<String, Object>();
		return new ModelAndView(getRequestPath(request)).addObject("modelList",  getBsetService().selectErrorSort(param));
	}
}
