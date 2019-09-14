package cn.hnzxl.exam.project.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import cn.hnzxl.exam.base.model.BaseModel;
/**
 * 试卷表
 * @author ZXL
 * @date 2014年11月2日 下午3:51:22
 *
 */
public class Examination extends BaseModel<Long> {
	//试卷id
	private Long examinationId;
	//类别id
	private String examinationCategoryId;
	//试卷标题
	private String examinationSubject;
	//试卷概要说明
	private String examinationContent;
	//开始考试时间
	private Date examinationStartTime;
	//结束考试时间
	private Date examinationEndTime;
	//考试时长
	private Integer examinationTimeLength;
	//试卷总分数
	private Integer examinationScore;
	//试卷状态  状态	0出题中，1可以答题，2结束
	private Integer examinationStatus;
	
	
	public Long getExaminationId() {
		return examinationId;
	}

	public void setExaminationId(Long examinationId) {
		this.examinationId = examinationId;
	}

	public String getExaminationCategoryId() {
		return examinationCategoryId;
	}

	public void setExaminationCategoryId(String examinationCategoryId) {
		this.examinationCategoryId = examinationCategoryId == null ? null : examinationCategoryId.trim();
	}

	public String getExaminationSubject() {
		return examinationSubject;
	}

	public void setExaminationSubject(String examinationSubject) {
		this.examinationSubject = examinationSubject == null ? null : examinationSubject.trim();
	}

	public String getExaminationContent() {
		return examinationContent;
	}

	public void setExaminationContent(String examinationContent) {
		this.examinationContent = examinationContent == null ? null : examinationContent.trim();
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	public Date getExaminationStartTime() {
		return examinationStartTime;
	}

	public void setExaminationStartTime(Date examinationStartTime) {
		this.examinationStartTime = examinationStartTime;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	public Date getExaminationEndTime() {
		return examinationEndTime;
	}

	public void setExaminationEndTime(Date examinationEndTime) {
		this.examinationEndTime = examinationEndTime;
	}

	public Integer getExaminationTimeLength() {
		return examinationTimeLength;
	}

	public void setExaminationTimeLength(Integer examinationTimeLength) {
		this.examinationTimeLength = examinationTimeLength;
	}

	public Integer getExaminationScore() {
		return examinationScore;
	}

	public void setExaminationScore(Integer examinationScore) {
		this.examinationScore = examinationScore;
	}

	public Integer getExaminationStatus() {
		return examinationStatus;
	}

	public void setExaminationStatus(Integer examinationStatus) {
		this.examinationStatus = examinationStatus;
	}
	
	@Override
	public Long getId() {
		return this.examinationId;
	}

	@Override
	public void setId(Long modelId) {
		this.examinationId  = modelId;
	}
}