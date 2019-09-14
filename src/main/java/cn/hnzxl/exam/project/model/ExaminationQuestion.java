package cn.hnzxl.exam.project.model;

import cn.hnzxl.exam.base.model.BaseModel;

public class ExaminationQuestion extends BaseModel<Long> {
	private Long examinationQuestionId;

	private Long examinationQuestionExaminationId;

	private Long examinationQuestionHeadlineId;

	private Long examinationQuestionQuestionId;

	public Long getExaminationQuestionId() {
		return examinationQuestionId;
	}

	public void setExaminationQuestionId(Long examinationQuestionId) {
		this.examinationQuestionId = examinationQuestionId;
	}

	public Long getExaminationQuestionExaminationId() {
		return examinationQuestionExaminationId;
	}

	public void setExaminationQuestionExaminationId(Long examinationQuestionExaminationId) {
		this.examinationQuestionExaminationId = examinationQuestionExaminationId;
	}

	public Long getExaminationQuestionHeadlineId() {
		return examinationQuestionHeadlineId;
	}

	public void setExaminationQuestionHeadlineId(Long examinationQuestionHeadlineId) {
		this.examinationQuestionHeadlineId = examinationQuestionHeadlineId;
	}

	public Long getExaminationQuestionQuestionId() {
		return examinationQuestionQuestionId;
	}

	public void setExaminationQuestionQuestionId(Long examinationQuestionQuestionId) {
		this.examinationQuestionQuestionId = examinationQuestionQuestionId;
	}
	
	@Override
	public Long getId() {
		return this.examinationQuestionId;
	}

	@Override
	public void setId(Long modelId) {
		this.examinationQuestionId  = modelId;
	}
}