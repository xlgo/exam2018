package cn.hnzxl.exam.base.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.base.model.BaseModel;
import cn.hnzxl.exam.base.util.PageUtil;
import cn.hnzxl.exam.base.util.QueryUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 基本服务类， 需要提供BaseMapper的实现， 来操作实体
 * 
 * @author ZXL
 * @date 2014年10月25日 上午12:16:10
 *
 * @param <MODEL>
 * @param <PK>
 */
public abstract class BaseService<MODEL extends BaseModel, PK extends Serializable> {
	public static Logger log = Logger.getLogger(BaseModel.class.getName()+"Service");
	public abstract BaseMapper<MODEL, PK> getBaseMapper();

	/**
	 * 根据主键删除实体
	 * 
	 * @param id
	 *            实体类
	 * @return 成功数
	 */
	public int deleteByPrimaryKey(PK id) {
		return getBaseMapper().deleteByPrimaryKey(id);
	}

	/**
	 * 插入MODEL，同时设置创建时间字段
	 * 
	 * @param record
	 *            实体类
	 * @return 成功数
	 */
	public int insert(MODEL record) {
		record.setCreateTime(new Date());
		return getBaseMapper().insert(record);
	}
	public int insert(List<MODEL> record) {
		for (MODEL model : record) {
			this.insert(model);
		}
		return record.size();
	}
	/**
	 * 插入实体类，对非空字段不进行操作
	 * 
	 * @param record
	 *            体类实
	 * @return 成功数
	 */
	public int insertSelective(MODEL record) {
		return getBaseMapper().insertSelective(record);
	}

	/**
	 * 根据ID查找实体类
	 * 
	 * @param id
	 *            实体类主键
	 * @return 实体类
	 */
	public MODEL selectByPrimaryKey(PK id) {
		return getBaseMapper().selectByPrimaryKey(id);
	}

	/**
	 * 根据主键更新实体类，实体类为空的字段不进行更新
	 * 
	 * @param record
	 *            实体类
	 * @return 成功数
	 */
	public int updateByPrimaryKeySelective(MODEL record) {
		record.setUpdateTime(new Date());
		return getBaseMapper().updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据主键更新实体类，为空字段设置为空
	 * 
	 * @param record
	 *            实体类
	 * @return 成功数
	 */
	public int updateByPrimaryKey(MODEL record) {
		record.setUpdateTime(new Date());
		return getBaseMapper().updateByPrimaryKey(record);
	}

	/**
	 * 根据参数进行查询，根据
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	public List<MODEL> selectAll(Map<String, Object> params) {
		return getBaseMapper().selectAll(params);
	}
	
	/**
	 * 根据参数进行查询，根据
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	public PageUtil<MODEL> selectAll(QueryUtil util) {
		return this.selectAllByPage(util.getFilter());
	}
	
	public PageUtil<MODEL> selectAllByPage(Map<String, Object> params){
		PageHelper.startPage( Integer.valueOf(params.get("pageNum").toString()), Integer.valueOf(params.get("pageSize").toString()));
		Page<MODEL> list = (Page<MODEL>) this.selectAll(params);
		PageUtil<MODEL> pu = new PageUtil<MODEL>();
		pu.setList(list);
		pu.setPageNum(list.getPageNum());
		pu.setTotal(list.getTotal());
		pu.setPages(list.getPages());
		pu.setPageSize(list.getPageSize());
		return pu;
	}
	/**
	 * 根据主键批量删除对象
	 * 
	 * @param ids 主键数组
	 * @return 删除功能数
	 */
	public int deleteByPrimaryKeyBatch(PK[] ids) {
		for (PK id : ids) {
			this.deleteByPrimaryKey(id);
		}
		return ids.length;
	}
	
	/**
	 * 判断一个对象的列是否存在一个值
	 * @param column	查询列
	 * @param value     查询值
	 * @return
	 */
	public boolean isExist(String column , String value) {
		if(value.matches(".*[\\*\\%\\_\\[\\^].*")){
			throw new RuntimeException("非法的字符串*%_[");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(column, value);
		List<MODEL> list = this.selectAll(params);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
}
