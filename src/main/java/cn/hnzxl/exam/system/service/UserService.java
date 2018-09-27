package cn.hnzxl.exam.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.base.util.MD5Util;
import cn.hnzxl.exam.system.dao.UserMapper;
import cn.hnzxl.exam.system.model.User;
/**
 * 系统用户的操作类
 * @author ZXL
 * @date 2014年11月2日 上午12:16:11
 *
 */
@Service
public class UserService extends BaseService<User, String> {
	@Autowired
	private UserMapper userMapper = null;

	@Override
	public BaseMapper<User, String> getBaseMapper() {
		return userMapper;
	}
	public boolean hasUserByUsername(String username){
		return selectByUserName(username)!=null;
	}
	
	public User selectByUserName(String username){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("username", username);
		List<User>  users  =  userMapper.selectAll(params);
		if(users == null || users.size() ==0){
			return null;
		}
		return users.get(0);
	}
	
	/**
	 * 插入的时候对用户密码进行加密
	 */
	@Override
	public int insert(User record) {
		record.setPassword(MD5Util.MD5(record.getUsername()+record.getPassword()));
		return super.insert(record);
	}
	
	/**
	 * 用户保存的时候验证密码是否修改了， 如果修改了密码 那么就对密码进行加密， 没有修改密码不对密码进行加密
	 */
	@Override
	public int updateByPrimaryKeySelective(User record) {
		//User user= this.selectByUserName(record.getUsername());
		if( StringUtils.isNotBlank(record.getPassword()) && record.getPassword().length()!=32){
			record.setPassword(MD5Util.MD5(record.getUsername()+record.getPassword()));
		}
		return super.updateByPrimaryKeySelective(record);
	}
}
