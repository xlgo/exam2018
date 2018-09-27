package cn.hnzxl.exam.project.model;

import cn.hnzxl.exam.base.model.BaseModel;

public class ExaminationQuestion extends BaseModel<String> {
	private String examinationQuestionId;

	private String examinationQuestionExaminationId;

	private String examinationQuestionHeadlineId;

	private String examinationQuestionQuestionId;

	public String getExaminationQuestionId() {
		return examinationQuestionId;
	}

	public void setExaminationQuestionId(String examinationQuestionId) {
		this.examinationQuestionId = examinationQuestionId == null ? null : examinationQuestionId.trim();
	}

	public String getExaminationQuestionExaminationId() {
		return examinationQuestionExaminationId;
	}

	public void setExaminationQuestionExaminationId(String examinationQuestionExaminationId) {
		this.examinationQuestionExaminationId = examinationQuestionExaminationId == null ? null
				: examinationQuestionExaminationId.trim();
	}

	public String getExaminationQuestionHeadlineId() {
		return examinationQuestionHeadlineId;
	}

	public void setExaminationQuestionHeadlineId(String examinationQuestionHeadlineId) {
		this.examinationQuestionHeadlineId = examinationQuestionHeadlineId == null ? null
				: examinationQuestionHeadlineId.trim();
	}

	public String getExaminationQuestionQuestionId() {
		return examinationQuestionQuestionId;
	}

	public void setExaminationQuestionQuestionId(String examinationQuestionQuestionId) {
		this.examinationQuestionQuestionId = examinationQuestionQuestionId == null ? null
				: examinationQuestionQuestionId.trim();
	}
	
	@Override
	public String getId() {
		return this.examinationQuestionId;
	}

	@Override
	public void setId(String modelId) {
		this.examinationQuestionId  = modelId;
	}
}