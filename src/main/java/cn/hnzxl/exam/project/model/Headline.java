package cn.hnzxl.exam.project.model;

import cn.hnzxl.exam.base.model.BaseModel;

/**
 * 试卷中的大题进行维护
 * 
 * @author ZXL
 * @date 2014年11月10日 下午9:45:17
 *
 */
public class Headline extends BaseModel<Long> {
	// 大题编号
	private Long headlineId;
	// 试卷id
	private Long headlineExaminationId;
	// 试卷名称
	private String headlineExaminationName;
	// 大题标题
	private String headlineHeadlineSubject;
	// 大题描述
	private String headlineExaminationContent;
	// 问题总数
	private Integer headlineQuestionCount;
	// 每题分数
	private Integer headlineScore;
	// 试卷模式 0.选题，1.试卷随机，2.用户随机
	private Integer headlinePattern;
	// 答案模式 1.固定模式，2随机模式
	private Integer headlineAnswerPattern;
	// 顺序
	private Integer headlineSort;
	// 题目类型 00判断  01 单选  02多选
	private String headlineQuestionType;

	public String getHeadlineQuestionType() {
		return headlineQuestionType;
	}

	public void setHeadlineQuestionType(String headlineQuestionType) {
		this.headlineQuestionType = headlineQuestionType;
	}

	public Long getHeadlineId() {
		return headlineId;
	}

	public void setHeadlineId(Long headlineId) {
		this.headlineId = headlineId;
	}

	public Long getHeadlineExaminationId() {
		return headlineExaminationId;
	}

	public void setHeadlineExaminationId(Long headlineExaminationId) {
		this.headlineExaminationId = headlineExaminationId;
	}

	public String getHeadlineHeadlineSubject() {
		return headlineHeadlineSubject;
	}

	public void setHeadlineHeadlineSubject(String headlineHeadlineSubject) {
		this.headlineHeadlineSubject = headlineHeadlineSubject == null ? null : headlineHeadlineSubject.trim();
	}

	public String getHeadlineExaminationContent() {
		return headlineExaminationContent;
	}

	public void setHeadlineExaminationContent(String headlineExaminationContent) {
		this.headlineExaminationContent = headlineExaminationContent == null ? null : headlineExaminationContent.trim();
	}

	public Integer getHeadlineQuestionCount() {
		return headlineQuestionCount;
	}

	public void setHeadlineQuestionCount(Integer headlineQuestionCount) {
		this.headlineQuestionCount = headlineQuestionCount;
	}

	public Integer getHeadlineScore() {
		return headlineScore;
	}

	public void setHeadlineScore(Integer headlineScore) {
		this.headlineScore = headlineScore;
	}

	public Integer getHeadlinePattern() {
		return headlinePattern;
	}

	public void setHeadlinePattern(Integer headlinePattern) {
		this.headlinePattern = headlinePattern;
	}

	public Integer getHeadlineAnswerPattern() {
		return headlineAnswerPattern;
	}

	public void setHeadlineAnswerPattern(Integer headlineAnswerPattern) {
		this.headlineAnswerPattern = headlineAnswerPattern;
	}

	public Integer getHeadlineSort() {
		return headlineSort;
	}

	public void setHeadlineSort(Integer headlineSort) {
		this.headlineSort = headlineSort;
	}

	@Override
	public Long getId() {
		return this.headlineId;
	}

	@Override
	public void setId(Long modelId) {
		this.headlineId = modelId;
	}

	public String getHeadlineExaminationName() {
		return headlineExaminationName;
	}

	public void setHeadlineExaminationName(String headlineExaminationName) {
		this.headlineExaminationName = headlineExaminationName;
	}

}