package cn.hnzxl.exam.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.project.dao.UserExaminationMapper;
import cn.hnzxl.exam.project.model.Examination;
import cn.hnzxl.exam.project.model.Headline;
import cn.hnzxl.exam.project.model.Question;
import cn.hnzxl.exam.project.model.UserExamination;
import cn.hnzxl.exam.project.model.UserQuestion;
import cn.hnzxl.exam.system.model.User;

@Service
public class UserExaminationService extends BaseService<UserExamination, String> {
	@Autowired
	private UserExaminationMapper userExaminationMapper = null;
	@Autowired
	private ExaminationService examinationService;
	@Autowired
	private HeadlineService headlineService;
	@Autowired
	private QuestionUtil questionUtil;
	@Autowired
	private UserQuestionService userQuestionService;
	@Override
	public BaseMapper<UserExamination, String> getBaseMapper() {
		return userExaminationMapper;
	}
	/**
	 * 获取用户排名
	 * @param record
	 * @return
	 */
	public Integer selectRanking(UserExamination record){
		return userExaminationMapper.selectRanking(record);
	}
	
	/**
	 * 获取用户答题的信息
	 */
	public Map<String,Object> contrast(String examinationId) {
		Map<String, Object> res = new HashMap<String, Object>();
		
		User currentUser =SessionUtil.getCurrentUser();
		//获取能够参加的考试
		Examination examination = examinationService.selectByPrimaryKey(examinationId);
		res.put("examination", examination);
		
		Map<String,Object> userExaminationParam = new HashMap<String, Object>();
		userExaminationParam.put("userExaminationUserid", currentUser.getUserid());
		userExaminationParam.put("userExaminationExaminationId", examination.getExaminationId());
		List<UserExamination>  userExaminations = this.selectAll(userExaminationParam);
		res.put("userExamination", userExaminations.get(0));
		//获取大题信息
		Map<String,Object> headlineParam = new HashMap<String, Object>();
		headlineParam.put("headlineExaminationId", examinationId);
		List<Headline> headlines = headlineService.selectAll(headlineParam);
		res.put("headline", headlines);
		for (Headline headline : headlines) {
			//从用户试题库拿题
			Map<String,Object> userQuestionParam = new HashMap<String, Object>();
			userQuestionParam.put("userQuestionUserid", currentUser.getUserid());
			userQuestionParam.put("userQuestionHeadlineId", headline.getHeadlineId());
			List<UserQuestion> userQuestions = userQuestionService.selectAll(userQuestionParam);
			res.put("userQuestion_"+headline.getHeadlineId(), userQuestions);
		}
		res.put("questions", questionUtil.getQuestions());
		return res;
	}
	
	public List<Map<String,Object>> selectEnterCount(Map<String,Object> param){
		return userExaminationMapper.selectEnterCount(param);
	}
	
	public List<Map<String,Object>> selectAward(Map<String,Object> param){
		return userExaminationMapper.selectAward(param);
	}
	
	public List<Map<String,Object>> selectSchoolCount(Map<String,Object> param){
		return userExaminationMapper.selectSchoolCount(param);
	}
	public List<Map<String,Object>> selectScoreCount(Map<String,Object> param){
		return userExaminationMapper.selectScoreCount(param);
	}
}
