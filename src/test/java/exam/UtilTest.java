package exam;

import java.text.ParseException;
import java.util.Map;

import org.junit.Test;

import cn.hnzxl.exam.base.util.QueryUtil;
import base.BaseTestMybatis;

public class UtilTest extends BaseTestMybatis{
	
	@Test
	public void queryUtil01() throws ParseException{
		QueryUtil qu = QueryUtil.getInstance();
		qu.addParam("string0","无");
		qu.addParam("S_string1","无");
		qu.addParam("S_string2_L","左");
		qu.addParam("S_string3_R","右");
		qu.addParam("S_string4_LR","左右");
		qu.addParam("date1","2014-10-23 23:46:12");
		qu.addParam("D_date","2014-10-24 23:46:12");
		qu.addParam("D_date_D","2014-10-25 23:46:12");
		qu.addParam("D_date_DT","2014-10-26 23:46:12");
		qu.addParam("N_number","12312");
		qu.addParam("N_number1_D","4.1415");
		qu.addParam("N_number1_I","-44");
		qu.addParam("N_number1_F","6.1415");
		Map<String, Object> filter = qu.getFilter();
		for (String key : filter.keySet()) {
			System.out.println(key+"\t:"+filter.get(key)+"("+filter.get(key).getClass().getName()+")");
		}
	}
}	
