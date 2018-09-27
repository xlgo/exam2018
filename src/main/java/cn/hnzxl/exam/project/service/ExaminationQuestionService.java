package cn.hnzxl.exam.project.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.project.dao.ExaminationQuestionMapper;
import cn.hnzxl.exam.project.model.ExaminationQuestion;

@Service
public class ExaminationQuestionService extends BaseService<ExaminationQuestion, String> {
	@Resource
	private ExaminationQuestionMapper examinationQuestionMapper = null;

	@Override
	public BaseMapper<ExaminationQuestion, String> getBaseMapper() {
		return examinationQuestionMapper;
	}
}