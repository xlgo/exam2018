package cn.hnzxl.exam.project.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.hnzxl.exam.base.controller.BaseController;
import cn.hnzxl.exam.base.model.DWZResult;
import cn.hnzxl.exam.base.util.PageUtil;
import cn.hnzxl.exam.base.util.QueryUtil;
import cn.hnzxl.exam.project.model.Headline;
import cn.hnzxl.exam.project.service.HeadlineService;

/**
 * 大题的控制器
 * 
 * @author ZXL
 * @date 2014年11月9日 下午5:29:16
 *
 */
@Controller
@RequestMapping("/project/headline/")
public class HeadlineController extends BaseController<Headline, Long> {
	@Autowired
	private HeadlineService headlineService;

	@Override
	public HeadlineService getBsetService() {
		return headlineService;
	}

	@Override
	public Class<Headline> getModelClass() {
		return Headline.class;
	}

	public DWZResult saveJSON(Headline model, HttpServletRequest request, HttpServletResponse response) {
		model.setHeadlineExaminationId(Long.valueOf(request.getParameter("examination.id")));
		return super.saveJSON(model, request, response);
	}
	
	@Override
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		QueryUtil queryUtil = new QueryUtil(request);
		try {
			queryUtil.addParam("headlineExaminationId", request.getParameter("examination.id"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PageUtil<Headline> list = getBsetService().selectAll(queryUtil);
		return new ModelAndView(getRequestPath(request)).addObject("pageList", list).addObject("searchParam",
				queryUtil.getParams());
	}
}
