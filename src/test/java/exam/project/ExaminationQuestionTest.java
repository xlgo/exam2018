package exam.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.CollectionUtils;

import base.BaseTestMybatis;
import cn.hnzxl.exam.base.util.GUIDUtil;
import cn.hnzxl.exam.base.util.QueryUtil;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.project.model.Examination;
import cn.hnzxl.exam.project.model.UserExamination;
import cn.hnzxl.exam.project.service.ExaminationService;
import cn.hnzxl.exam.project.service.UserExaminationService;
import cn.hnzxl.exam.system.model.User;

public class ExaminationQuestionTest extends BaseTestMybatis {
	
	@Autowired
	private ExaminationService examinationService;
	@Autowired
	private UserExaminationService userExaminationService;
	@Test
	@Rollback(false)
	public void addTest(){
		Examination examination = new Examination();
		examinationService.insert(examination);
	}
	
	@Test
	public void selectByPKTest(){
		Examination examination = examinationService.selectByPrimaryKey(1L);
	}
	@Test
	@Rollback(false)
	public void getExamination() {
		Map<String,Object> examinationParam = new HashMap<String, Object>();
		examinationParam.put("examinationTime", new Date());
		examinationParam.put("examinationStatus", 1);
		List<Examination> examinations = examinationService.selectAll(examinationParam);
		//User currentUser =SessionUtil.getCurrentUser();
		for (Examination examination : examinations) {
			//获取用户试卷
			Map<String,Object> userExaminationParam = new HashMap<String, Object>();
			userExaminationParam.put("userExaminationUserid", "1a5b8e00a2384afba13c1cb10fae24c3");
			userExaminationParam.put("userExaminationExaminationId", examination.getExaminationId());
			List<UserExamination>  userExaminations = userExaminationService.selectAll(userExaminationParam);
			if(CollectionUtils.isEmpty(userExaminations)){
				UserExamination userExamination = new UserExamination();
				userExamination.setUserExaminationUserid(1L);
				userExamination.setUserExaminationExaminationId(examination.getExaminationId());
				userExamination.setUserExaminationIp("123123");
				userExamination.setUserExaminationStatus("0");
				userExaminationService.insert(userExamination);
				System.out.println("======================你可以参加这场考试");
				break;
			}else if("0".equals(userExaminations.get(0).getUserExaminationStatus())){
				UserExamination userExamination = userExaminations.get(0);
				userExamination.setUserExaminationStatus("1");
				userExaminationService.updateByPrimaryKey(userExamination);
				System.out.println("======================你已答过题目，但是没有提交");
				break;
			}else{
				System.out.println("已经考试完毕了");
			}
		}
	}
	
}
