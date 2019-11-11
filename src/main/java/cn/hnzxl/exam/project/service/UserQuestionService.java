package cn.hnzxl.exam.project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.project.dao.UserQuestionMapper;
import cn.hnzxl.exam.project.dto.ExamCacheInfo;
import cn.hnzxl.exam.project.model.Examination;
import cn.hnzxl.exam.project.model.Headline;
import cn.hnzxl.exam.project.model.Question;
import cn.hnzxl.exam.project.model.UserExamination;
import cn.hnzxl.exam.project.model.UserQuestion;
import cn.hnzxl.exam.system.model.User;

@Service
public class UserQuestionService extends BaseService<UserQuestion, Long> {
	public static Logger log = Logger.getLogger(UserQuestionService.class);
	@Autowired
	private UserQuestionMapper userQuestionMapper;
	@Autowired
	private ExaminationService examinationService;
	@Autowired
	private HeadlineService headlineService;
	@Autowired
	private UserExaminationService userExaminationService;
	@Autowired
	private QuestionUtil questionUtil;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public UserQuestionMapper getBaseMapper() {
		return userQuestionMapper;
	}

	@PostConstruct
	public void processExamInfo() {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							processExam();
						} catch (Exception e) {
							log.error("处理失败！",e);
						}
					}
				}
			});
		}
	}

	private void processExam() {
		if (redisTemplate.opsForList().size(ExamCacheInfo.KEY_PREFIX) > 0) {
			ExamCacheInfo info = (ExamCacheInfo) redisTemplate.opsForList().rightPop("exam:2019:info");
			if (info == null) {
				return;
			}
			if (info.getType().equals(ExamCacheInfo.Type.EXAM_SAVE)) {
				// 保存提交的答题新以及得出成绩！
				Map<String, Object> chacheMap = (Map<String, Object>) redisTemplate.opsForHash().get(info.getDataKey(),
						info.getUserId());

				List<UserQuestion> userQuestions = (List<UserQuestion>) chacheMap.get("userQuestions");
				redisTemplate.opsForHash().delete(info.getDataKey(), info.getUserId());
				// redisTemplate.opsForValue().remove(EXAMINATION_HEADLINE_INFO_CACHE_KEY);
				redisTemplate.delete(EXAMINATION_HEADLINE_INFO_CACHE_KEY + ":" + info.getUserId());
				Long userExaminationId = (Long) chacheMap.get("userExaminationId");
				Long examinationId = (Long) chacheMap.get("examinationId");

				updateUserRightAnswer(userQuestions);
				// 获取用户分数
				UserQuestion sroreParam = new UserQuestion();
				sroreParam.setUserQuestionExaminationId(examinationId);
				sroreParam.setUserQuestionUserid(info.getUserId());
				Integer userScore = selectUserScore(sroreParam);

				UserExamination userExamination = new UserExamination();
				userExamination.setUserExaminationId(userExaminationId);
				userExamination.setUserExaminationScore(userScore == null ? 0 : userScore);
				userExaminationService.updateByPrimaryKeySelective(userExamination);
				System.out.println("获取到数据，处理提交数据！");
			} else if (info.getType().equals(ExamCacheInfo.Type.EXAM_START)) {
				// 把抽到的题目信息保存到数据库中去
				List<UserQuestion> uqs = (List<UserQuestion>) redisTemplate.opsForHash().get(info.getDataKey(),
						info.getUserId());
				redisTemplate.opsForHash().delete(info.getDataKey(), info.getUserId());
				getBaseMapper().insertBatch(uqs);
			}
			System.out.println("获取到数据，处理抽题数据！");
		} else {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
			}
		}

	}

	/**
	 * 开始考试的试卷信息<br/>
	 * examination，试卷<br/>
	 * headline,大题 list<br/>
	 * question_大题id, 试题<br/>
	 * 
	 * @return
	 */
	private final static String EXAMINATION_INFO_CACHE_KEY = ExamCacheInfo.KEY_PREFIX + ":examination";
	private final static String EXAMINATION_HEADLINE_INFO_CACHE_KEY = ExamCacheInfo.KEY_PREFIX + ":examinationHeadline";
	private final static String EXAMINATION_QUESTION_INFO_CACHE_KEY = ExamCacheInfo.KEY_PREFIX + ":examinationQuestion";

	public Map<String, Object> getExamInfo(Map<String, Object> userInfo) {
		SessionUtil.rateLimit2("examInfo", 2 * 1000l);
		Map<String, Object> res = new HashMap<String, Object>();
		// 获取能够参加的考试
		List<Examination> examinations = (List<Examination>) redisTemplate.opsForValue()
				.get(EXAMINATION_INFO_CACHE_KEY);
		if (examinations == null) {
			Map<String, Object> examinationParam = new HashMap<String, Object>();
			examinationParam.put("examinationTime", new Date());
			examinationParam.put("examinationStatus", 1);
			examinations = examinationService.selectAll(examinationParam);
			redisTemplate.opsForValue().set(EXAMINATION_INFO_CACHE_KEY, examinations, 1, TimeUnit.MINUTES);
		}
		if (examinations.size() == 0) {
			System.out.println("没有可进行的答题！");
			return null;
		}
		// 0 表示新的题目 1表示已经有的题库
		int type = 0;
		User currentUser = SessionUtil.getCurrentUser();
		for (Examination examination : examinations) {
			// 获取用户试卷
			Map<String, Object> userExaminationParam = new HashMap<String, Object>();
			userExaminationParam.put("userExaminationUserid", currentUser.getUserid());
			userExaminationParam.put("userExaminationExaminationId", examination.getExaminationId());
			List<UserExamination> userExaminations = userExaminationService.selectAll(userExaminationParam);
			// List<UserExamination> insertExamination = new
			// ArrayList<UserExamination>();
			if (CollectionUtils.isEmpty(userExaminations)) {
				UserExamination userExamination = new UserExamination();
				// userExamination.setUserExaminationId(GUIDUtil.getUUID());
				userExamination.setUserExaminationUserid(currentUser.getUserid());
				userExamination.setUserExaminationExaminationId(examination.getExaminationId());
				userExamination.setUserExaminationIp("" + userInfo.get("ip"));
				userExamination.setUserExaminationBorwseinfo(userInfo.get("userAgent").toString());
				;
				userExamination.setUserExaminationStatus("0");
				userExaminationService.insert(userExamination);
				res.put("examination", examination);
				res.put("userExamination", userExamination);
				break;
			} else if ("0".equals(userExaminations.get(0).getUserExaminationStatus())) {

				type = 1;
				res.put("examination", examination);
				res.put("userExamination", userExaminations.get(0));
				break;
			} else {
			}
		}

		if (res.get("examination") != null) {
			Long examinationId = ((Examination) res.get("examination")).getExaminationId();

			List<Headline> headlines = (List<Headline>) redisTemplate.opsForValue()
					.get(EXAMINATION_HEADLINE_INFO_CACHE_KEY);
			if (headlines == null) {
				Map<String, Object> headlineParam = new HashMap<String, Object>();
				headlineParam.put("headlineExaminationId", examinationId);
				headlines = headlineService.selectAll(headlineParam);
				redisTemplate.opsForValue().set(EXAMINATION_HEADLINE_INFO_CACHE_KEY, headlines, 1, TimeUnit.MINUTES);
			}
			res.put("headline", headlines);

			if (type == 1) {// TODO 这里先重缓存中拿到信息，如果没有在去查数据库
				long c = System.currentTimeMillis();
				Map<String, Object> questionInfo = (Map<String, Object>) redisTemplate.opsForValue()
						.get(EXAMINATION_HEADLINE_INFO_CACHE_KEY + ":" + currentUser.getUserid());
				if (questionInfo == null) {
					questionInfo = new HashMap<>();
					for (Headline headline : headlines) {
						// 从用户试题库拿题
						Map<String, Object> userQuestionParam = new HashMap<String, Object>();
						userQuestionParam.put("userQuestionUserid", currentUser.getUserid());
						userQuestionParam.put("userQuestionExaminationId", examinationId);
						userQuestionParam.put("userQuestionHeadlineId", headline.getHeadlineId());
						List<UserQuestion> userQuestions = this.selectAll(userQuestionParam);
						List<Long> ids = new ArrayList<Long>();
						for (UserQuestion userQuestion : userQuestions) {
							ids.add(userQuestion.getUserQuestionQuestionId());
						}
						List<Question> questionTemp = questionUtil.getQuestionsByIds(ids);
						questionInfo.put("question_" + headline.getHeadlineId(), questionTemp);
					}
					redisTemplate.opsForValue().set(EXAMINATION_HEADLINE_INFO_CACHE_KEY + ":" + currentUser.getUserid(),
							questionInfo, 10, TimeUnit.MINUTES);
				}
				res.putAll(questionInfo);
				log.info(currentUser.getUsername() + ",取题：" + (System.currentTimeMillis() - c) + ":info010");
			} else if (type == 0) {
				// 随机拿题， 并保存到用户试题库

				List<UserQuestion> uqs = new ArrayList<UserQuestion>();
				Map<String, Object> questionInfo = new HashMap<>();
				for (Headline headline : headlines) {
					// "question_"+headlineId
					List<Question> questionTemp = questionUtil.getQuestionsByType(headline.getHeadlineQuestionType(),
							headline.getHeadlineQuestionCount());
					questionInfo.put("question_" + headline.getHeadlineId(), questionTemp);
					int index = 0;
					for (Question question : questionTemp) {
						UserQuestion uq = new UserQuestion();
						// uq.setUserQuestionId(GUIDUtil.getUUID());
						uq.setUserQuestionExaminationId(examinationId);
						uq.setUserQuestionHeadlineId(headline.getHeadlineId());
						uq.setUserQuestionUserid(currentUser.getUserid());
						uq.setUserQuestionQuestionId(question.getQuestionId());
						uq.setUserQuestionScore(headline.getHeadlineScore());
						uq.setUserQuestionRightAnswer(question.getQuestionRightAnswer());
						uq.setUserQuestionSort(index++);
						if ("00".equals(headline.getHeadlineQuestionType())) {
							// uq.setUserQuestionUserAnswer("0");
						}
						uqs.add(uq);
					}
				}
				// 保存抽题的信息
				redisTemplate.opsForValue().set(EXAMINATION_HEADLINE_INFO_CACHE_KEY + ":" + currentUser.getId(),
						questionInfo, 10, TimeUnit.MINUTES);

				res.putAll(questionInfo);
				long c = System.currentTimeMillis();// TODO ss
				// this.getBaseMapper().insertBatch(uqs);

				ExamCacheInfo eci = ExamCacheInfo.getStart(currentUser.getUserid());
				redisTemplate.opsForList().leftPush(eci.KEY_PREFIX, eci);
				redisTemplate.opsForHash().put(eci.getDataKey(), eci.getUserId(), uqs);

				log.info(currentUser.getUsername() + ",抽题：" + (System.currentTimeMillis() - c) + ":info010");
			}

		} else {
			log.info(currentUser.getUsername() + ",已经参加考试了：:info010");
			return null;
		}
		// 如果没有，获取一套试卷

		// 获取大题信息

		// 获取题目
		return res;
	}

	/**
	 * 更新用户答案
	 * 
	 * @param userQuestion
	 * @return
	 */
	public int updateUserRightAnswer(UserQuestion userQuestion) {
		return userQuestionMapper.updateUserRightAnswer(userQuestion);
	}

	public int updateUserRightAnswer(List<UserQuestion> userQuestions) {
		for (UserQuestion userQuestion : userQuestions) {
			userQuestionMapper.updateUserRightAnswer(userQuestion);
		}
		return userQuestions.size();
	}

	/**
	 * 根据用户的答案，获取用户的分数
	 * 
	 * @param userQuestion
	 *            需要试卷id，需要用户id
	 * @return
	 */
	public Integer selectUserScore(UserQuestion userQuestion) {
		return userQuestionMapper.selectUserScore(userQuestion);
	}

	public void saveUserExam_bak(Long examinationId, Map<String, String[]> userQuestionInfo, String type) {
		User currentUser = SessionUtil.getCurrentUser();

		Map<String, Object> userExaminationParam = new HashMap<String, Object>();
		userExaminationParam.put("userExaminationUserid", currentUser.getUserid());
		userExaminationParam.put("userExaminationExaminationId", examinationId);

		// 更新用户试卷信息
		UserExamination userExamination = userExaminationService.selectAll(userExaminationParam).get(0);
		if (StringUtils.equals(userExamination.getUserExaminationStatus(), "1")) {
			return;
		}

		List<UserQuestion> userQuestions = new ArrayList<UserQuestion>();

		for (String key : userQuestionInfo.keySet()) {
			UserQuestion userQuestion = new UserQuestion();
			userQuestion.setUserQuestionExaminationId(examinationId);
			userQuestion.setUserQuestionQuestionId(Long.valueOf(key));
			userQuestion.setUserQuestionUserid(currentUser.getUserid());
			String[] value = userQuestionInfo.get(key);
			Arrays.sort(value);
			String userAnswer = "";
			for (int i = 0; i < value.length; i++) {
				userAnswer += value[i];
				if (i + 1 != value.length) {
					userAnswer += "|";
				}
			}
			userQuestion.setUserQuestionUserAnswer(userAnswer);
			userQuestions.add(userQuestion);
		}
		long c = System.currentTimeMillis();
		// userQuestionMapper.updateUserRightAnswer2(userQuestions);
		this.updateUserRightAnswer(userQuestions);
		log.info(currentUser.getUsername() + "提交：type=" + type + ",size=" + userQuestions.size() + ",times="
				+ (System.currentTimeMillis() - c));
		// 获取用户分数
		UserQuestion sroreParam = new UserQuestion();
		sroreParam.setUserQuestionExaminationId(examinationId);
		sroreParam.setUserQuestionUserid(currentUser.getUserid());
		Integer userScore = this.selectUserScore(sroreParam);
		userExamination.setUserExaminationScore(userScore == null ? 0 : userScore);

		userExamination.setUserExaminationSubmitTime(new Date());
		Long timeLength = userExamination.getUserExaminationSubmitTime().getTime()
				- userExamination.getCreateTime().getTime();
		userExamination.setUserExaminationTimeLength((double) timeLength / 60000);
		userExamination.setUserExaminationStatus("1");
		userExamination.setUserExaminationSysteminfo(type);
		userExaminationService.updateByPrimaryKey(userExamination);

	}

	public void saveUserExam(Long examinationId, Map<String, String[]> userQuestionInfo, String type) {
		User currentUser = SessionUtil.getCurrentUser();

		Map<String, Object> userExaminationParam = new HashMap<String, Object>();
		userExaminationParam.put("userExaminationUserid", currentUser.getUserid());
		userExaminationParam.put("userExaminationExaminationId", examinationId);

		// 更新用户试卷信息
		UserExamination userExamination = userExaminationService.selectAll(userExaminationParam).get(0);
		if (StringUtils.equals(userExamination.getUserExaminationStatus(), "1")) {
			return;
		}

		List<UserQuestion> userQuestions = new ArrayList<UserQuestion>();

		for (String key : userQuestionInfo.keySet()) {
			UserQuestion userQuestion = new UserQuestion();
			userQuestion.setUserQuestionExaminationId(examinationId);
			userQuestion.setUserQuestionQuestionId(Long.valueOf(key));
			userQuestion.setUserQuestionUserid(currentUser.getUserid());
			String[] value = userQuestionInfo.get(key);
			Arrays.sort(value);
			String userAnswer = "";
			for (int i = 0; i < value.length; i++) {
				userAnswer += value[i];
				if (i + 1 != value.length) {
					userAnswer += "|";
				}
			}
			userQuestion.setUserQuestionUserAnswer(userAnswer);
			userQuestions.add(userQuestion);
		}
		long c = System.currentTimeMillis();


		/*
		 * //userQuestionMapper.updateUserRightAnswer2(userQuestions);
		 * this.updateUserRightAnswer(userQuestions); //获取用户分数 UserQuestion
		 * sroreParam = new UserQuestion();
		 * sroreParam.setUserQuestionExaminationId(examinationId);
		 * sroreParam.setUserQuestionUserid(currentUser.getUserid()); Integer
		 * userScore = this.selectUserScore(sroreParam);
		 * userExamination.setUserExaminationScore(userScore==null?0:userScore);
		 */

		userExamination.setUserExaminationSubmitTime(new Date());
		Long timeLength = userExamination.getUserExaminationSubmitTime().getTime()
				- userExamination.getCreateTime().getTime();
		userExamination.setUserExaminationTimeLength((double) timeLength / 60000);
		userExamination.setUserExaminationStatus("1");
		userExamination.setUserExaminationSysteminfo(type);
		userExaminationService.updateByPrimaryKey(userExamination);
		
		
		ExamCacheInfo eci = ExamCacheInfo.getSave(currentUser.getUserid());
		redisTemplate.opsForList().leftPush(eci.KEY_PREFIX, eci);

		Map<String, Object> chacheMap = new HashMap<>();
		chacheMap.put("userQuestions", userQuestions);
		chacheMap.put("userExaminationId", userExamination.getUserExaminationId());
		chacheMap.put("examinationId", examinationId);
		redisTemplate.opsForHash().put(eci.getDataKey(), eci.getUserId(), chacheMap);
		
		log.info(currentUser.getUsername() + "提交：type=" + type + ",size=" + userQuestions.size() + ",times="
				+ (System.currentTimeMillis() - c));

	}

	public List<Map<String, Object>> selectRightSort(Map<String, Object> param) {
		return userQuestionMapper.selectRightSort(param);
	}

	public List<Map<String, Object>> selectErrorSort(Map<String, Object> param) {
		return userQuestionMapper.selectErrorSort(param);
	}
}