package exam.project;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTestMybatis;
import cn.hnzxl.exam.project.model.Question;
import cn.hnzxl.exam.project.service.QuestionUtil;

public class QuertionUtilTest extends BaseTestMybatis{
	@Autowired
	private QuestionUtil questionUtil;
	
	@Test
	public void initTest(){
		List<Question> questions = questionUtil.getQuestionsByType(Question.TYPE_SINGLE, 10);
		for (Question question : questions) {
			System.out.println(question.getQuestionSubject());
		}
	}
}
