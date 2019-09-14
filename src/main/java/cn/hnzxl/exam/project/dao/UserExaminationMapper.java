package cn.hnzxl.exam.project.dao;

import java.util.List;
import java.util.Map;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.project.model.UserExamination;

public interface UserExaminationMapper extends BaseMapper<UserExamination, Long> {
	/**
	 * 获取用户的排名
	 * @param record
	 * @return
	 */
	Integer selectRanking(UserExamination record);
	
	List<Map<String,Object>> selectEnterCount(Map<String,Object> param);
	
	/**
	 * 获取奖励情况
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> selectAward(Map<String,Object> param);
	
	List<Map<String,Object>> selectSchoolCount(Map<String,Object> param);

	List<Map<String, Object>> selectScoreCount(Map<String, Object> param);
	
}