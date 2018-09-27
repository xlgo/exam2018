package cn.hnzxl.exam.project.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.hnzxl.exam.base.controller.BaseController;
import cn.hnzxl.exam.base.model.DWZResult;
import cn.hnzxl.exam.base.util.QueryUtil;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.project.model.UserExamination;
import cn.hnzxl.exam.project.service.UserExaminationService;
import cn.hnzxl.exam.system.model.SystemConfig;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.service.SystemConfigService;

/**
 * 用户试卷表的控制器
 * @author ZXL
 * @date 2014年11月11日 下午10:51:50
 *
 */
@Controller
@RequestMapping("/project/userexamination/")
public class UserExaminationController  extends BaseController<UserExamination,String>{
	@Autowired
	private UserExaminationService userExaminationService;
	
	@Autowired
	private SystemConfigService systemconfigService;
	@Override
	public UserExaminationService getBsetService() {
		return userExaminationService;
	}
	@Override
	public Class<UserExamination> getModelClass() {
		return UserExamination.class;
	}
	
	
	@RequestMapping("userExamInfo")
	public ModelAndView userExamInfo(HttpServletRequest request, HttpServletResponse response) {
		User currentUser =SessionUtil.getCurrentUser();
		Map<String,Object> userExaminationParam = new HashMap<String, Object>();
		userExaminationParam.put("userExaminationUserid", currentUser.getId());
		userExaminationParam.put("userExaminationStatus", "1");
		List<UserExamination> res =userExaminationService.selectAll(userExaminationParam);
		return new ModelAndView(getRequestPath(request)+"List").addObject("modelList", res);
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
	@RequestMapping("contrast")
	public ModelAndView contrast(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String examinationId = request.getParameter("id");
		SystemConfig config = systemconfigService.selectByPrimaryKey("1");
		if(config!=null){
			try{
				Long startDate = DateUtils.parseDate(config.getKey(), "yyyy-MM-dd HH:mm").getTime();
				Long endDate = DateUtils.parseDate(config.getValue(), "yyyy-MM-dd HH:mm").getTime();
				Long currDate = new Date().getTime();
				if(!(startDate<=currDate  && endDate >=currDate)){
					return new ModelAndView(getRequestPath(request)).addObject("error","本期错题对比将在"+config.getKey()+" ~  "+config.getValue()+"时间段开放，到时候再来吧!");
				}
			}catch (Exception e) {
				return new ModelAndView(getRequestPath(request)).addObject("errorTitle","错误！").addObject("error","错题对比设置错误，请联系管理员！");
			}
		}else{
			return new ModelAndView(getRequestPath(request)).addObject("errorTitle","错误！").addObject("error","系统未设置开放时间，请联系管理员！");
		}
		return new ModelAndView(getRequestPath(request)).addAllObjects(userExaminationService.contrast(examinationId));
	}
	
	@RequestMapping("contrastJSON")
	@ResponseBody
	public Object contrastJSON(HttpServletRequest request, HttpServletResponse response) {
		String examinationId = request.getParameter("id");
		
		return userExaminationService.contrast(examinationId);
	}
	
	@RequestMapping("enterCount")
	public ModelAndView enterCount(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> param = new HashMap<String, Object>();
		return new ModelAndView(getRequestPath(request)).addObject("modelList",  getBsetService().selectEnterCount(param));
	}
	
	@RequestMapping("award")
	public ModelAndView award(HttpServletRequest request, HttpServletResponse response) {
		QueryUtil queryUtil = new QueryUtil(request);
		queryUtil.getFilter(); 
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userExaminationExaminationId", request.getParameter("examination.id"));
		String limit =request.getParameter("limit");
		param.put("limit",StringUtils.isEmpty(limit)?10:limit);
		return new ModelAndView(getRequestPath(request)).addObject("modelList",  getBsetService().selectAward(param)).addObject("searchParam", queryUtil.getParams());
	}
	
	@RequestMapping("lingquJSON")
	 @ResponseBody
	 public DWZResult lingquJSON(HttpServletRequest request, HttpServletResponse response){
		 DWZResult dwzResult = new DWZResult();
		 String userExamId = request.getParameter("id");
		 UserExamination ue = getBsetService().selectByPrimaryKey(userExamId);
		 ue.setUserExaminationSysteminfo("1");
		 getBsetService().updateByPrimaryKey(ue);
		 dwzResult.setMessage("领取成功！");
		 return dwzResult;
	 }
	
	@RequestMapping("schoolCount")
	public ModelAndView schoolCount(HttpServletRequest request, HttpServletResponse response) {
		QueryUtil queryUtil = new QueryUtil(request);
		queryUtil.getFilter(); 
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userExaminationExaminationId", request.getParameter("examination.id"));
		String limit =request.getParameter("limit");
		param.put("limit",StringUtils.isEmpty(limit)?10:limit);
		return new ModelAndView(getRequestPath(request)).addObject("modelList",  getBsetService().selectSchoolCount(param)).addObject("searchParam", queryUtil.getParams());
	}
	
	
	@RequestMapping("scoreCount")
	public ModelAndView scoreCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryUtil queryUtil = new QueryUtil(request);
		
		String limit =request.getParameter("limit");
		queryUtil.addParam("limit",StringUtils.isEmpty(limit)?"10":limit);
		queryUtil.addParam("userExaminationExaminationId", request.getParameter("examination.id"));
		return new ModelAndView(getRequestPath(request)).addObject("modelList",  getBsetService().selectScoreCount(queryUtil.getFilter())).addObject("searchParam", queryUtil.getParams());
	}
}
