package cn.hnzxl.exam.project.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.project.dao.ExaminationMapper;
import cn.hnzxl.exam.project.model.Examination;

@Service
public class ExaminationService extends BaseService<Examination, String> {
	@Resource
	private ExaminationMapper examinationMapper = null;

	@Override
	public BaseMapper<Examination, String> getBaseMapper() {
		return examinationMapper;
	}
}
