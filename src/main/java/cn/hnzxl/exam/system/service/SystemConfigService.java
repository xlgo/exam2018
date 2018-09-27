package cn.hnzxl.exam.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.base.util.MD5Util;
import cn.hnzxl.exam.system.dao.SystemConfigMapper;
import cn.hnzxl.exam.system.dao.UserMapper;
import cn.hnzxl.exam.system.model.SystemConfig;
import cn.hnzxl.exam.system.model.User;
/**
 * 系统用户的操作类
 * @author ZXL
 * @date 2014年11月2日 上午12:16:11
 *
 */
@Service
public class SystemConfigService extends BaseService<SystemConfig, String> {
	@Autowired
	private SystemConfigMapper systemConfigMapper = null;

	@Override
	public BaseMapper<SystemConfig, String> getBaseMapper() {
		return systemConfigMapper;
	}
	
	public void restTime(){
		this.systemConfigMapper.restTime();
	}
}
