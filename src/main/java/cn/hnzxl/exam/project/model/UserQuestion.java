package cn.hnzxl.exam.project.model;

import cn.hnzxl.exam.base.model.BaseModel;

public class UserQuestion extends BaseModel<Long> {
	private Long userQuestionId;

	private Long userQuestionExaminationId;

	private Long userQuestionHeadlineId;

	private Long userQuestionUserid;

	private Long userQuestionQuestionId;

	private Integer userQuestionScore;

	private String userQuestionRightAnswer;

	private String userQuestionUserAnswer;
	
	private Integer userQuestionSort;
	public Long getUserQuestionId() {
		return userQuestionId;
	}

	public void setUserQuestionId(Long userQuestionId) {
		this.userQuestionId = userQuestionId;
	}

	public Long getUserQuestionExaminationId() {
		return userQuestionExaminationId;
	}

	public void setUserQuestionExaminationId(Long userQuestionExaminationId) {
		this.userQuestionExaminationId = userQuestionExaminationId;
	}

	public Long getUserQuestionHeadlineId() {
		return userQuestionHeadlineId;
	}

	public void setUserQuestionHeadlineId(Long userQuestionHeadlineId) {
		this.userQuestionHeadlineId = userQuestionHeadlineId;
	}

	public Long getUserQuestionUserid() {
		return userQuestionUserid;
	}

	public void setUserQuestionUserid(Long userQuestionUserid) {
		this.userQuestionUserid = userQuestionUserid;
	}

	public Long getUserQuestionQuestionId() {
		return userQuestionQuestionId;
	}

	public void setUserQuestionQuestionId(Long userQuestionQuestionId) {
		this.userQuestionQuestionId = userQuestionQuestionId;
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
	public Long getId() {
		return this.userQuestionId;
	}

	@Override
	public void setId(Long modelId) {
		this.userQuestionId  = modelId;
	}

	public Integer getUserQuestionSort() {
		return userQuestionSort;
	}

	public void setUserQuestionSort(Integer userQuestionSort) {
		this.userQuestionSort = userQuestionSort;
	}
}