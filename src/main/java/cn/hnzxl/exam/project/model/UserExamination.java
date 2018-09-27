package cn.hnzxl.exam.project.model;

import java.util.Date;

import cn.hnzxl.exam.base.model.BaseModel;

/**
 * 用试卷表
 * 
 * @author ZXL
 * @date 2014年11月12日 上午10:54:50
 *
 */
public class UserExamination extends BaseModel<String> {

	private String userExaminationId;
	// 用户的id
	private String userExaminationUserid;
	// 试卷的id
	private String userExaminationExaminationId;
	private String userExaminationExaminationName;
	public String getUserExaminationExaminationName() {
		return userExaminationExaminationName;
	}

	public void setUserExaminationExaminationName(String userExaminationExaminationName) {
		this.userExaminationExaminationName = userExaminationExaminationName;
	}

	// 创建ip
	private String userExaminationIp;
	// 提交时间
	private Date userExaminationSubmitTime;
	// 耗时
	private Double userExaminationTimeLength;
	// 分数
	private Integer userExaminationScore;
	// 浏览器信息
	private String userExaminationBorwseinfo;
	// 系统信息
	private String userExaminationSysteminfo;
	// 状态 0有效，1答题完成，2作废
	private String userExaminationStatus;

	public String getUserExaminationId() {
		return userExaminationId;
	}

	public void setUserExaminationId(String userExaminationId) {
		this.userExaminationId = userExaminationId;
	}

	public String getUserExaminationUserid() {
		return userExaminationUserid;
	}

	public void setUserExaminationUserid(String userExaminationUserid) {
		this.userExaminationUserid = userExaminationUserid;
	}

	public String getUserExaminationExaminationId() {
		return userExaminationExaminationId;
	}

	public void setUserExaminationExaminationId(String userExaminationExaminationId) {
		this.userExaminationExaminationId = userExaminationExaminationId;
	}

	public String getUserExaminationIp() {
		return userExaminationIp;
	}

	public void setUserExaminationIp(String userExaminationIp) {
		this.userExaminationIp = userExaminationIp == null ? null : userExaminationIp.trim();
	}

	public Date getUserExaminationSubmitTime() {
		return userExaminationSubmitTime;
	}

	public void setUserExaminationSubmitTime(Date userExaminationSubmitTime) {
		this.userExaminationSubmitTime = userExaminationSubmitTime;
	}

	public Double getUserExaminationTimeLength() {
		return userExaminationTimeLength;
	}

	public void setUserExaminationTimeLength(Double userExaminationTimeLength) {
		this.userExaminationTimeLength = userExaminationTimeLength;
	}

	public Integer getUserExaminationScore() {
		return userExaminationScore;
	}

	public void setUserExaminationScore(Integer userExaminationScore) {
		this.userExaminationScore = userExaminationScore ;
	}

	public String getUserExaminationBorwseinfo() {
		return userExaminationBorwseinfo;
	}

	public void setUserExaminationBorwseinfo(String userExaminationBorwseinfo) {
		this.userExaminationBorwseinfo = userExaminationBorwseinfo == null ? null : userExaminationBorwseinfo.trim();
	}

	public String getUserExaminationSysteminfo() {
		return userExaminationSysteminfo;
	}

	public void setUserExaminationSysteminfo(String userExaminationSysteminfo) {
		this.userExaminationSysteminfo = userExaminationSysteminfo == null ? null : userExaminationSysteminfo.trim();
	}

	public String getUserExaminationStatus() {
		return userExaminationStatus;
	}

	public void setUserExaminationStatus(String userExaminationStatus) {
		this.userExaminationStatus = userExaminationStatus == null ? null : userExaminationStatus.trim();
	}

	@Override
	public String getId() {
		return this.userExaminationId;
	}

	@Override
	public void setId(String modelId) {
		this.userExaminationId = modelId;
	}
}