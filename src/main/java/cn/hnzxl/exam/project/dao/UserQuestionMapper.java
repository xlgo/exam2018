package cn.hnzxl.exam.project.dao;

import java.util.List;
import java.util.Map;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.project.model.UserQuestion;

public interface UserQuestionMapper extends BaseMapper<UserQuestion, Long>{
	int updateUserRightAnswer(UserQuestion userQuestion);
	Integer selectUserScore(UserQuestion userQuestion);
	
	List<Map<String,Object>> selectRightSort(Map<String,Object> param);
	List<Map<String,Object>> selectErrorSort(Map<String,Object> param);
	
	int insertBatch(List<UserQuestion> uqs);
	
	int updateUserRightAnswer2(List<UserQuestion> userQuestion);
}