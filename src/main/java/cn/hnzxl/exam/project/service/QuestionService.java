package cn.hnzxl.exam.project.service;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hnzxl.exam.base.dao.BaseMapper;
import cn.hnzxl.exam.base.service.BaseService;
import cn.hnzxl.exam.base.util.GUIDUtil;
import cn.hnzxl.exam.project.dao.QuestionMapper;
import cn.hnzxl.exam.project.model.Question;

@Service
public class QuestionService extends BaseService<Question, String> {
	@Autowired
	private QuestionMapper questionMapper = null;

	@Override
	public QuestionMapper getBaseMapper() {
		return questionMapper;
	}
	/**
	 * 数据导入
	 * @param is  excel文件输入流
	 * @return 成功数量
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public int insertByExcelStream(InputStream is) throws Exception{
		Workbook book = Workbook.getWorkbook(is);
		Sheet sheet  =  book.getSheet( 0 );
		int columns = sheet.getColumns();
		int rows = sheet.getRows();
		int recordStatc = 0;
		for (int i = 1; i < rows; i++) {
			recordStatc=0;
			String questionType = sheet.getCell(0,i).getContents();
			if(StringUtils.isEmpty(questionType)){
				continue;
			}
			String questionSubject = sheet.getCell(1,i).getContents();
			String questionAnswer = sheet.getCell(2,i).getContents();
			if(!"00".equals(questionType) && (StringUtils.isBlank(questionAnswer)||StringUtils.isBlank(questionSubject))){
				recordStatc=1;
			}
			String questionRightAnswer = sheet.getCell(3,i).getContents();
			if(StringUtils.isEmpty(questionRightAnswer)){
				recordStatc=1;
			}
			String questionScore = sheet.getCell(4,i).getContents();
			String questionAnalysis = sheet.getCell(5,i).getContents();
			String isTest = sheet.getCell(5,i).getContents();
			Question question = new Question();
			question.setQuestionId(GUIDUtil.getUUID());
			question.setQuestionType(questionType);
			question.setQuestionSubject(questionSubject);
			question.setQuestionAnswer(questionAnswer);
			question.setQuestionRightAnswer(questionRightAnswer);
			question.setQuestionScore(NumberUtils.toInt(questionScore, 1));
			question.setQuestionAnalysis(questionAnalysis);
			question.setQuestionStatus(recordStatc);
			//if(!"1".equals(isTest)){
			//}
			getBaseMapper().insert(question);
		}
		return rows-1;
	}
}