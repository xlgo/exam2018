package cn.hnzxl.exam.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.hnzxl.exam.base.controller.BaseController;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.base.util.PageUtil;
import cn.hnzxl.exam.base.util.QueryUtil;
import cn.hnzxl.exam.project.model.Examination;
import cn.hnzxl.exam.project.service.ExaminationService;

@Controller
@RequestMapping("/project/examination/")
public class ExaminationController extends BaseController<Examination, String> {
	@Autowired
	private ExaminationService examinationService;

	@Override
	public Class<Examination> getModelClass() {
		return Examination.class;
	}

	@Override
	public BaseService<Examination, String> getBsetService() {
		return examinationService;
	}
	
	@RequestMapping("listSelect")
	 public ModelAndView listSelect(HttpServletRequest request, HttpServletResponse response){
		 QueryUtil queryUtil = new QueryUtil(request);
		 PageUtil<Examination> list = getBsetService().selectAll(queryUtil);
		 return new ModelAndView(getRequestPath(request)).addObject("pageList", list).addObject("searchParam", queryUtil.getParams());
	 }
}
