package cn.hnzxl.exam.project.model;

import cn.hnzxl.exam.base.model.BaseModel;

public class UserQuestion extends BaseModel<String> {
	private String userQuestionId;

	private String userQuestionExaminationId;

	private String userQuestionHeadlineId;

	private String userQuestionUserid;

	private String userQuestionQuestionId;

	private Integer userQuestionScore;

	private String userQuestionRightAnswer;

	private String userQuestionUserAnswer;
	
	private Integer userQuestionSort;
	public String getUserQuestionId() {
		return userQuestionId;
	}

	public void setUserQuestionId(String userQuestionId) {
		this.userQuestionId = userQuestionId == null ? null : userQuestionId.trim();
	}

	public String getUserQuestionExaminationId() {
		return userQuestionExaminationId;
	}

	public void setUserQuestionExaminationId(String userQuestionExaminationId) {
		this.userQuestionExaminationId = userQuestionExaminationId == null ? null : userQuestionExaminationId.trim();
	}

	public String getUserQuestionHeadlineId() {
		return userQuestionHeadlineId;
	}

	public void setUserQuestionHeadlineId(String userQuestionHeadlineId) {
		this.userQuestionHeadlineId = userQuestionHeadlineId == null ? null : userQuestionHeadlineId.trim();
	}

	public String getUserQuestionUserid() {
		return userQuestionUserid;
	}

	public void setUserQuestionUserid(String userQuestionUserid) {
		this.userQuestionUserid = userQuestionUserid == null ? null : userQuestionUserid.trim();
	}

	public String getUserQuestionQuestionId() {
		return userQuestionQuestionId;
	}

	public void setUserQuestionQuestionId(String userQuestionQuestionId) {
		this.userQuestionQuestionId = userQuestionQuestionId == null ? null : userQuestionQuestionId.trim();
	}

	public Integer getUserQuestionScore() {
		return userQuestionScore;
	}

	public void setUserQuestionScore(Integer userQuestionScore) {
		this.userQuestionScore = userQuestionScore;
	}

	public String getUserQuestionRightAnswer() {
		return userQuestionRightAnswer;
	}

	public void setUserQuestionRightAnswer(String userQuestionRightAnswer) {
		this.userQuestionRightAnswer = userQuestionRightAnswer == null ? null : userQuestionRightAnswer.trim();
	}

	public String getUserQuestionUserAnswer() {
		return userQuestionUserAnswer;
	}

	public void setUserQuestionUserAnswer(String userQuestionUserAnswer) {
		this.userQuestionUserAnswer = userQuestionUserAnswer == null ? null : userQuestionUserAnswer.trim();
	}

	@Override
	public String getId() {
		return this.userQuestionId;
	}

	@Override
	public void setId(String modelId) {
		this.userQuestionId  = modelId;
	}

	public Integer getUserQuestionSort() {
		return userQuestionSort;
	}

	public void setUserQuestionSort(Integer userQuestionSort) {
		this.userQuestionSort = userQuestionSort;
	}
}