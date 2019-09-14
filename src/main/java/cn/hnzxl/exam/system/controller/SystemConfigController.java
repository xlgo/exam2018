package cn.hnzxl.exam.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.hnzxl.exam.base.controller.BaseController;
import cn.hnzxl.exam.base.model.BaseModel;
import cn.hnzxl.exam.base.model.DWZResult;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.base.util.GUIDUtil;
import cn.hnzxl.exam.base.util.MD5Util;
import cn.hnzxl.exam.base.util.QueryUtil;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.system.model.SystemConfig;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.service.SystemConfigService;
import cn.hnzxl.exam.system.service.UserService;

@Controller
@RequestMapping("/system/config/")
public class SystemConfigController extends BaseController<SystemConfig, Long> {

	@Autowired
	private SystemConfigService systemConfigService;

	@Override
	public BaseService<SystemConfig, Long> getBsetService() {
		return systemConfigService;
	}

	@Override
	public Class<SystemConfig> getModelClass() {
		return SystemConfig.class;
	}
	
	
	 @RequestMapping("tools")
	 public ModelAndView tools(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 return new ModelAndView(getRequestPath(request));
	 }
	 
	 @RequestMapping("restTime")
	 @ResponseBody
	 public String restTime(){
		 this.systemConfigService.restTime();
		 return "重置成功！";
	 }
	 
	 @Override
	public ModelAndView edit(Long id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		 SystemConfig model = getBsetService().selectByPrimaryKey(id);
		 if(model == null){
			model=new SystemConfig();
			model.setId(1L);
			getBsetService().insert(model);
		 }
		return new ModelAndView(getRequestPath(request)).addObject("model", model);
	}
	 @RequestMapping("choujiang")
	 public ModelAndView choujiang(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 SystemConfig model = getBsetService().selectByPrimaryKey(2L);
		 if(model == null){
			 model=new SystemConfig();
			model.setId(2L);
			model.setType("抽奖");
			model.setValue("10,10:10,10:10");
			getBsetService().insert(model);
		 }
		 
		 model.setMETHOD(BaseModel.METHOD_EDIT);
		 return new ModelAndView(getRequestPath(request)).addObject("model", model).addObject("pm", model.getValue().split(","));
	 }
	 
	 @RequestMapping("choujiangSaveJSON")
	 @ResponseBody
	 public DWZResult choujiangSaveJSON(SystemConfig model,String sc,String ss,String se, HttpServletRequest request, HttpServletResponse response){
		 DWZResult dwzResult = new DWZResult();
		 model.setValue(sc+","+ss+","+se);
		 getBsetService().updateByPrimaryKeySelective(model);
		 dwzResult.setMessage("保存成功！");
			 
		 dwzResult.setCallbackType(DWZResult.CALLBACKTYPE_CLOSE);
		 dwzResult.setNavTabId(request.getParameter("navTabId"));
		 return dwzResult;
	 }
}
