package cn.hnzxl.exam.base.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
public class MPMessage {
	//开发者微信号
	private String toUserName;
	
	//发送方帐号（一个OpenID）
	private String fromUserName;
	
	//消息创建时间 （整型）
	private String createTime;
	
	//消息类型(event,text,image,voice,video,music,news)
	private String msgType; //这里有个空格的
	
	
	private String content;
	
	private String msgId;
	//subscribe(订阅)、unsubscribe(取消订阅),CLICK(菜单单击)
	
	private String event;
	//事件KEY值，qrscene_为前缀，后面为二维码的参数值
	//事件KEY值，与自定义菜单接口中KEY值对应
	
	private String eventKey;
	//二维码的ticket，可用来换取二维码图片
	//事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	private String ticket;
	
	private String mediaId;
	
	@XmlElement(name="ToUserName")
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	@XmlElement(name="FromUserName")
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	@XmlElement(name="CreateTime")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@XmlElement(name="MsgType")
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@XmlElement(name="Content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@XmlElement(name="MsgId")
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	@XmlElement(name="Event")
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	@XmlElement(name="EventKey")
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	@XmlElement(name="Ticket")
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	@XmlElement(name="MediaId")
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
