package cn.hnzxl.exam.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 查询用的工具类，主要封装对<br/>
 * #{pageNum} #{pageSize} #{sortField} #{sortOrder} 参数<br/>
 * 以及查询参数（查询参数主要封装like查询）<br/>
 * 
 * 规则说明<br/>
 * <b>类型_字段名_模式</b>
 * 
 * 1.类型
 * <ul>
 * <li>S 字符串 默认等于</li>
 * <li>D 日期类型 默认日期格式</li>
 * <li>N 数值型 默认整形</li>
 * </ul>
 * 
 * 2.模式
 * <ul>
 * <li>L *value</li>
 * <li>R value*</li>
 * <li>LR *value*</li>
 * <li>D yyyy-MM-dd 格式的字符串转化成日期</li>
 * <li>DT yyyy-MM-dd HH:mm:ss 格式的字符串转化成日期</li>
 * </ul>
 * 
 * @author ZXL
 * @date 2014年10月25日 上午3:12:58
 *
 */
public class QueryUtil {
	/**
	 * 构建好的参数
	 */
	private Map<String, Object> filter = new HashMap<String, Object>();
	private Map<String, Object> params = new HashMap<String, Object>();
	private static final SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat datetimePattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Integer pageNum = null;
	private Integer pageSize = null;
	private String sortField = null;
	private String sortOrder = null;

	private HttpServletRequest request;
	
	/**
	 * 用户自定义QueryUtil 时候的使用
	 * @return
	 */
	public static QueryUtil getInstance(){
		return new QueryUtil();
	}
	
	private QueryUtil(){}
	public QueryUtil(HttpServletRequest request){
		this.request = request;
		Enumeration enumeration = request.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement().toString();
			String value = request.getParameter(key);
			try {
				this.addParam(key, value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 解析并添加参数
	 * @param key
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public QueryUtil addParam(String key, String value) throws ParseException {
		//用于查询返回
		if(StringUtils.isBlank(value)){
			return this;
		}
		this.params.put(key, value);
		
		String[] pathParam = key.split("_");
		if(ArrayUtils.isEmpty(pathParam)){
			return this;
		}
		if (pathParam.length == 3) {
			if (pathParam[0].equals("S")) {
				if ("L".equals(pathParam[2])) {
					this.addFilter(pathParam[1], "%" + value);
				} else if ("R".equals(pathParam[2])) {
					this.addFilter(pathParam[1], value + "%");
				} else if ("LR".equals(pathParam[2])) {
					this.addFilter(pathParam[1], "%" + value + "%");
				}
			} else if (pathParam[0].equals("D")) {
				if ("D".equals(pathParam[2])) {
					this.addFilter(pathParam[1], datePattern.parse(value));
				} else if ("DT".equals(pathParam[2])) {
					this.addFilter(pathParam[1], datetimePattern.parse(value));
				}
			} else if (pathParam[0].equals("N")) {
				if ("I".equals(pathParam[2])) {
					this.addFilter(pathParam[1], Integer.valueOf(value));
				}else if ("D".equals(pathParam[2])) {
					this.addFilter(pathParam[1], Double.valueOf(value));
				}else if ("F".equals(pathParam[2])) {
					this.addFilter(pathParam[1], Float.valueOf(value));
				}
			}
		} else if (pathParam.length == 2) {
			if ("S".equals(pathParam[0])) {
				this.addFilter(pathParam[1], "%"+value+"%");
			} else if ("D".equals(pathParam[0])) {
				this.addFilter(pathParam[1], datePattern.parse(value));
			} else if ("N".equals(pathParam[0])) {
				this.addFilter(pathParam[1], Integer.valueOf(value));
			}
		} else {// pageNum pageSize sortField sortOrder
			if ("pageNum".equals(pathParam[0])) {
				this.setPageNum(Integer.valueOf(value));
			} else if ("pageSize".equals(pathParam[0])) {
				this.setPageSize(Integer.valueOf(value));
			} else if ("sortField".equals(pathParam[0])) {
				this.setSortField(value);
			} else if ("sortOrder".equals(pathParam[0])) {
				this.setSortOrder(value);
			} else {
				this.addFilter(pathParam[0], value);
			}

		}
		
		return this;
	}

	private void addFilter(String key, Object value) {
		this.filter.put(key, value);
	}

	public Map<String, Object> getFilter() {
		this.filter.put("pageNum",this.getPageNum());
		this.filter.put("pageSize",this.getPageSize());
		this.filter.put("sortField",this.getSortField());
		this.filter.put("sortOrder",this.getSortOrder());
		return filter;
	}

	public void setFilter(Map<String, Object> filter) {
		this.filter = filter;
	}

	public Integer getPageNum() {
		if(pageNum == null){
			this.pageNum = 1;
		}
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		if(pageSize == null){
			this.pageSize = 10;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		if(StringUtils.isNotEmpty(this.getSortField()) && StringUtils.isEmpty(sortOrder)){
			this.sortOrder = "DESC";
		}
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
