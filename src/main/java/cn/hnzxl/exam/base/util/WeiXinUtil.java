package cn.hnzxl.exam.base.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class WeiXinUtil {
	private final static String BASE_URL = "https://api.weixin.qq.com/cgi-bin/";

	public static String accessToken = null;
	public static String appId;
	private static String appSecret;
	public static String token;
	public static String hostName;
	public static String profile;
	
	private static String menu;
	
	@Value("${weixin.appID}")
	public void setAppID(String appID) {
		this.appId = appID;
	}

	@Value("${weixin.appSecret}")
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	@Value("${weixin.token}")
	public void setToken(String token) {
		this.token = token;
	}
	@Value("${base.hostName}")
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	@Value("${base.profile}")
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	@PostConstruct
	public void initMenu(){
		try {
			File menuFile = ResourceUtils.getFile(String.format("classpath:menu-%s.json", profile));
			menu = FileUtils.readFileToString(menuFile,"UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedDelay = 3600*1000)
	public void refash(){
		if(accessToken==null){
			accessToken = genToken();
			menuDelete();
			/*JSONObject menu = new JSONObject();
			JSONArray menuItem = new JSONArray();
			
			JSONObject menuItem1 = new JSONObject();
			menuItem1.put("type", "click");
			menuItem1.put("name", "开始考试");
			menuItem1.put("key", "start_exam");
			
			menuItem.add(menuItem1);
			
			JSONObject menuItem2 = new JSONObject();
			menuItem2.put("type", "click");
			menuItem2.put("name", "点赞");
			menuItem2.put("key", "good");
			
			menuItem.add(menuItem2);
			
			
			menu.put("button", menuItem);*/
			
			menuCreate(menu);
		}else{
			accessToken = genToken();
		}
	}
	/**
	 * 获取票据号
	 * @param appID
	 * @param appSecret
	 * @return
	 */
	public static String genToken(){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("grant_type", "client_credential");
		map.put("appId", appId);
		map.put("secret", appSecret);
		
		String string = HttpClientUtil.get(BASE_URL+"token",map);
		JSONObject json = JSON.parseObject(string);
		return json.getString("access_token");
	}
	
	/**
	 * 初始化菜单
	 * @param accessToken
	 * @param menu
	 */
	public static void menuCreate(String menu){
		HttpClientUtil.post(BASE_URL+"menu/create?access_token="+accessToken,menu);
	}
	/**
	 * 删除菜单
	 */
	public static void menuDelete(){
		HttpClientUtil.get(BASE_URL+"menu/delete?access_token="+accessToken);
	}
	
	public static String userInfo(String openid){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("access_token", accessToken);
		map.put("openid", openid);
		
		return HttpClientUtil.get(BASE_URL+"user/info",map);
	}
	/**
	 * 发送文本消息
	 * @param openid
	 * @param content
	 * @return
	 */
	public static String messageCustomSendText(String openid,String content){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", openid);
		map.put("msgtype", "text");
		
		Map<String, Object> inn = new LinkedHashMap<String, Object>();
		inn.put("content", content);
		
		map.put("text", inn);
		
		return HttpClientUtil.post(BASE_URL+"message/custom/send?access_token="+accessToken,JSON.toJSONString(map));
	}
	
	/**
	 * 发送图片消息
	 * @param openid
	 * @param mediaId
	 * @return
	 */
	public static String messageCustomSendImage(String openid,String mediaId){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", openid);
		map.put("msgtype", "image");
		
		Map<String, Object> inn = new LinkedHashMap<String, Object>();
		inn.put("media_id", mediaId);
		
		map.put("image", inn);
		
		return HttpClientUtil.post(BASE_URL+"message/custom/send?access_token="+accessToken,JSON.toJSONString(map));
	}
	
	/**
	 * 发送语音消息
	 * @param openid
	 * @param mediaId
	 * @return
	 */
	public static String messageCustomSendVoice(String openid,String mediaId){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", openid);
		map.put("msgtype", "voice");
		
		Map<String, Object> inn = new LinkedHashMap<String, Object>();
		inn.put("media_id", mediaId);
		
		map.put("voice", inn);
		
		return HttpClientUtil.post(BASE_URL+"message/custom/send?access_token="+accessToken,JSON.toJSONString(map));
	}
	
	/**
	 * 发送视频消息
	 * @param openid
	 * @param mediaId
	 * @param thumbMediaId
	 * @param title
	 * @param description
	 * @return
	 */
	public static String messageCustomSendVideo(String openid,String mediaId,String thumbMediaId,String title,String description){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", openid);
		map.put("msgtype", "video");
		
		Map<String, Object> inn = new LinkedHashMap<String, Object>();
		inn.put("media_id", mediaId);
		inn.put("thumb_media_id", thumbMediaId);
		inn.put("title", title);
		inn.put("description", description);
		
		map.put("video", inn);
		
		return HttpClientUtil.post(BASE_URL+"message/custom/send?access_token="+accessToken,JSON.toJSONString(map));
	}
	
	/**
	 * 发送音乐
	 * @param openid
	 * @param thumbMediaId
	 * @param title
	 * @param description
	 * @param musicurl
	 * @param hqmusicurl
	 * @return
	 */
	public static String messageCustomSendMusic(String openid,String thumbMediaId,String title,String description,String musicurl,String hqmusicurl){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", openid);
		map.put("msgtype", "music");
		
		Map<String, Object> inn = new LinkedHashMap<String, Object>();
		inn.put("title", title);
		inn.put("description", description);
		inn.put("musicurl", musicurl);
		inn.put("hqmusicurl", hqmusicurl);
		inn.put("thumb_media_id", thumbMediaId);
		
		map.put("music", inn);
		
		return HttpClientUtil.post(BASE_URL+"message/custom/send?access_token="+accessToken,JSON.toJSONString(map));
	}
	/**
	 * 验证签名是否正确
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] arr = new String[]{token,timestamp,nonce};
		Arrays.sort(arr);
		String sha1Hex = DigestUtils.sha1Hex(StringUtils.join(arr));
		return sha1Hex.equals(signature);
	}
}
