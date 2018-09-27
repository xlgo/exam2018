package cn.hnzxl.exam.system.dao;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.system.model.SystemConfig;

public interface SystemConfigMapper  extends BaseMapper<SystemConfig, String>{

	void restTime();
}