package cn.hnzxl.exam.project.model;

import cn.hnzxl.exam.base.model.BaseModel;

/**
 * 题库实体类
 * 
 * @author ZXL
 * @date 2014年11月8日 下午6:47:44
 *
 */
public class Question extends BaseModel<Long> {
	//判断
	public final static String TYPE_JUDGE="00";
	//单选
	public final static String TYPE_SINGLE="01";
	//多选
	public final static String TYPE_MULTIPLE="02";
	
	private Long questionId;
	// 类别id
	private String questionCategoryId;
	// 题目类型 00:判断，01:单选，02:多选
	private String questionType;
	// 题目
	private String questionSubject;
	// 问题备选答案
	private String questionAnswer;
	// 问题正确答案
	private String questionRightAnswer;
	// 默认分数
	private Integer questionScore;
	// 题目解析
	private String questionAnalysis;
	// 状态 0:正常,1:不可用
	private Integer questionStatus;

	private String questionRemark;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionCategoryId() {
		return questionCategoryId;
	}

	public void setQuestionCategoryId(String questionCategoryId) {
		this.questionCategoryId = questionCategoryId == null ? null : questionCategoryId.trim();
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType == null ? null : questionType.trim();
	}

	public String getQuestionSubject() {
		return questionSubject;
	}

	public void setQuestionSubject(String questionSubject) {
		this.questionSubject = questionSubject == null ? null : questionSubject.trim();
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer == null ? null : questionAnswer.trim();
	}

	public String getQuestionRightAnswer() {
		return questionRightAnswer;
	}

	public void setQuestionRightAnswer(String questionRightAnswer) {
		this.questionRightAnswer = questionRightAnswer == null ? null : questionRightAnswer.trim();
	}

	public Integer getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(Integer questionScore) {
		this.questionScore = questionScore;
	}

	public String getQuestionAnalysis() {
		return questionAnalysis;
	}

	public void setQuestionAnalysis(String questionAnalysis) {
		this.questionAnalysis = questionAnalysis == null ? null : questionAnalysis.trim();
	}

	public Integer getQuestionStatus() {
		return questionStatus;
	}

	public void setQuestionStatus(Integer questionStatus) {
		this.questionStatus = questionStatus;
	}

	public String getQuestionRemark() {
		return questionRemark;
	}

	public void setQuestionRemark(String questionRemark) {
		this.questionRemark = questionRemark == null ? null : questionRemark.trim();
	}

	@Override
	public Long getId() {
		return this.questionId;
	}

	@Override
	public void setId(Long modelId) {
		this.questionId = modelId;
	}
}