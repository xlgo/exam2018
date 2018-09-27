package cn.hnzxl.exam.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.system.dao.UserPrizeMapper;
import cn.hnzxl.exam.system.model.UserPrize;
/**
 * 系统用户的操作类
 * @author ZXL
 * @date 2016年11月4日00:14:44
 *
 */
@Service
public class UserPrizeService extends BaseService<UserPrize, String> {
	@Autowired
	private UserPrizeMapper userPrizeMapper = null;

	@Override
	public BaseMapper<UserPrize, String> getBaseMapper() {
		return userPrizeMapper;
	}
	
}
