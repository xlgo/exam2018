package cn.hnzxl.exam.base.model;

/**
 * DWZ 的 AJAX 操作后返回的数据
 * 
 * @author ZXL
 * @date 2014年11月3日 下午11:35:49
 *
 */
public class DWZResult {
	// 成功
	private int statusCode = 200;
	// 消息
	private String message = "操作成功";
	//要刷新的navTabId
	private String navTabId;

	private String rel;
	// callbackType如果是closeCurrent就会关闭当前tab
	//只有callbackType="forward"时需要forwardUrl值
	private String callbackType;
	public final static String CALLBACKTYPE_CLOSE="closeCurrent";
	public final static String CALLBACKTYPE_FORWARD="forward";
	//转向的URL
	private String forwardUrl;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
}
