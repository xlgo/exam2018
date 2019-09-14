package cn.hnzxl.exam.project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.hnzxl.exam.project.model.Question;

/**
 * 问题的工具类，主要用于随机获取题目，存库题库，当缓存用
 * 
 * @author ZXL
 * @date 2014年11月11日 上午1:13:14
 *
 */
@Component
public class QuestionUtil {
	public static final Logger log = Logger.getLogger(QuestionUtil.class);
	@Autowired
	public QuestionService questionService;
	private static Map<Long, Question> questionsById;
	private static Map<String, List<Question>> questionsByType;

	public List<Question> getQuestionsByType(String type) {
		if(questionsByType == null){
			initQuestions();
		}
		return questionsByType.get(type);
	}
	public static void clearQuestions(){
		questionsByType = null;
		questionsById=null;
	}
	
	public Map<Long, Question> getQuestions() {
		if(questionsById == null){
			initQuestions();
		}
		return questionsById;
	}
	private void initQuestions(){
		questionsByType = new HashMap<String, List<Question>>();
		questionsById =new HashMap<Long, Question>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("questionStatus", "0");
		List<Question> questions = questionService.selectAll(params);
		log.debug("从新获取试题:("+questions.size()+")条");
		for (Question question : questions) {
			questionsById.put(question.getId(), question);
			List<Question> typeQuestion = questionsByType.get(question.getQuestionType());
			if(typeQuestion == null){
				typeQuestion = new ArrayList<Question>();
				questionsByType.put(question.getQuestionType(), typeQuestion);
			}
			
			typeQuestion.add(question);
		}
	}
	public List<Question> getQuestionsByIds(List<Long> ids){
		if(questionsById == null){
			initQuestions();
		}
		List<Question> resQuestions  = new ArrayList<Question>();
		for (Long id : ids) {
			resQuestions.add(questionsById.get(id));
		}
		return resQuestions;
	}
	/**
	 * 随机获取某个类型的题目
	 * 
	 * @param type
	 * @param randSize
	 * @return
	 */
	public List<Question> getQuestionsByType(String type, Integer randSize) {
		List<Question> questions  = getQuestionsByType(type);
		int[] indexs = randArray(questions.size(),randSize);
		List<Question> resQuestions  = new ArrayList<Question>();
		for (int i : indexs) {
			resQuestions.add(questions.get(i));
		}
		
		return resQuestions;
	}
	/**
	 * 随机获取index数据，并窃取前n个数值
	 * @param round
	 * @param size
	 * @return
	 * @test 5000 题目 取出30道 ,获取一次的时间大概 2~3毫秒
	 * @test 5000 题目 取出30道 ,获取一千次的时间大概 230~260毫秒
	 * @test 5000 题目 取出30道，获取 一万次时间大概2~3秒
	 */
	public static int[] randArray(int round,int size){
		int[] randInt = new int[round];
		for (int i = 0; i < randInt.length; i++) {
			randInt[i] = i;
		}
		// 打乱顺序
		Random r = new Random();
		for (int count = 0; count < 3; count++) {//打乱50遍
			for (int i = 0; i < randInt.length; i++) {
				int rInt = r.nextInt(randInt.length);
				int t = randInt[i];
				randInt[i] = randInt[rInt];
				randInt[rInt] = t;
			}
		}
		//size 数值较大时， 直接返回 round 个
		if(round<=size){
			return randInt;
		}
		//获取前N条
		int[] resRandInt=new int[size];
		for (int i = 0; i < size; i++) {
			resRandInt[i]=randInt[i];
		}
		return resRandInt;
	}
}
