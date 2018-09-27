package cn.hnzxl.exam.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.project.dao.HeadlineMapper;
import cn.hnzxl.exam.project.model.Headline;

@Service
public class HeadlineService extends BaseService<Headline, String> {
	@Autowired
	private HeadlineMapper headlineMapper = null;

	@Override
	public BaseMapper<Headline, String> getBaseMapper() {
		return headlineMapper;
	}
}