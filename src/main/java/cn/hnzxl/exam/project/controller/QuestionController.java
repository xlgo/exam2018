package cn.hnzxl.exam.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.hnzxl.exam.base.controller.BaseController;
import cn.hnzxl.exam.base.model.DWZResult;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.base.util.QueryUtil;
import cn.hnzxl.exam.project.model.Question;
import cn.hnzxl.exam.project.service.QuestionService;

/**
 * 题库控制器
 * 
 * @author ZXL
 * @date 2014年11月8日 下午6:31:19
 *
 */
@Controller
@RequestMapping("/project/question/")
public class QuestionController extends BaseController<Question, Long> {
	@Autowired
	private QuestionService questionService;

	@Override
	public Class<Question> getModelClass() {
		return Question.class;
	}

	@Override
	public QuestionService getBsetService() {
		return questionService;
	}

	@RequestMapping("importDialog")
	public ModelAndView importDialog(HttpServletRequest request, HttpServletResponse response) {
		QueryUtil queryUtil = new QueryUtil(request);
		return new ModelAndView(getRequestPath(request)).addObject("searchParam", queryUtil.getParams());
	}

	@RequestMapping("importData")
	@ResponseBody
	public DWZResult importData(@RequestParam(value = "questiondata") MultipartFile questiondata,
			HttpServletRequest request, HttpServletResponse response) {
		DWZResult dwzResult = new DWZResult();
		try {
			Integer rows = getBsetService().insertByExcelStream(questiondata.getInputStream());
			dwzResult.setMessage("导入试题成功("+rows+"条)！");
			dwzResult.setCallbackType(DWZResult.CALLBACKTYPE_CLOSE);
			dwzResult.setNavTabId(request.getParameter("navTabId"));
		} catch (Exception e) {
			dwzResult.setStatusCode(500);
			dwzResult.setMessage(e.getMessage());
		}
		return dwzResult;
	}
}
