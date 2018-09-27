package cn.hnzxl.exam.base.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 基础类，提供四个常用属性，都需要实现该方法，如果表中不需要刻意不加入该字段即可
 * @author ZXL
 *
 */
public abstract class  BaseModel<PK extends Serializable> {
	public static final String METHOD_ADD = "add";
	public static final String METHOD_EDIT = "edit";
	/**
	 * 常量，用于记录方法状态
	 */
	private  String METHOD =METHOD_ADD;
	private Date updateTime;
	private Date createTime;
	private String createBy;
	private String updateBy;
	//这个id用于处理主键的问题
	private String id;
	public abstract PK getId();
	public abstract void setId(PK modelId);
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getMETHOD() {
		return METHOD;
	}
	public void setMETHOD(String method) {
		METHOD = method;
	}
}
