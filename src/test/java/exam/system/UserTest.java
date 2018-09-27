package exam.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTestMybatis;
import cn.hnzxl.exam.base.util.GUIDUtil;
import cn.hnzxl.exam.base.util.PageUtil;
import cn.hnzxl.exam.base.util.QueryUtil;
import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.service.UserService;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class UserTest extends BaseTestMybatis{
	
	@Autowired
	private UserService userService;
	
	@Test
	public void insertUser(){
		User user = new User();
		user.setUserid(GUIDUtil.getUUID());
		int i = userService.insert(user);
		Assert.assertEquals(i, 1);
	}
	@Test
	public void selectUser(){
		String id ="ab03270ba2a4403c8d547503981d0f25";
		User user = userService.selectByPrimaryKey(id);
		Assert.assertNotNull(user);
	}
	@Test
	public void selectAllUser(){
		Map<String , Object> params = new HashMap<String , Object>();
		int pageStartRow = 5;
		int pageSize = 3;
		params.put("pageStartRow", pageStartRow);
		params.put("pageSize", pageSize);
		List<User> user = userService.selectAll(params);
		Assert.assertEquals(pageSize,user.size());
	}
	@Test
	public void pageList(){
		Map<String , Object> params = new HashMap<String , Object>();
		PageHelper.startPage(6, 4);
		List<User> user  = userService.selectAll(params);
		PageInfo pageInfo = new PageInfo(user);
		log.debug("pageNum"+pageInfo.getPageNum());
		log.debug("pageSize"+pageInfo.getPageSize());
		log.debug("startRow"+pageInfo.getStartRow());
		log.debug("endRow"+pageInfo.getEndRow());
		log.debug("total"+pageInfo.getTotal());
		log.debug("pages"+pageInfo.getPages());
		log.debug(user.size());
		List<User> user2  = userService.selectAll(params);
		log.debug(user2.size());
	}
	
	@Test
	public void pageList2(){
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("pageNum", 3);
		params.put("pageSize", 10);
		PageUtil<User> user  = userService.selectAllByPage(params);
		log.debug(user.toString());
	}
	
	
	@Test
	public void queryForPage() throws ParseException{
		Map<String , Object> params = new HashMap<String , Object>();
		
		params.put("name", "他");
		List<User> user  = userService.selectAll(params);
		System.out.println(user.size());
	}
	
}	
