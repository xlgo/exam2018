package cn.hnzxl.exam.base.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hnzxl.exam.base.util.bean.MpUserInfo;
import cn.hnzxl.exam.base.util.bean.Oauth2AccessToken;


@Component
public class WeiXinUtil {
	private final static String BASE_URL = "https://api.weixin.qq.com/cgi-bin";
	private final static String BASE_OAUTH2_OPEN_URL = "https://open.weixin.qq.com/connect";
	private final static String BASE_OAUTH2_URL = "https://api.weixin.qq.com/sns";

	private static WeChatConfig weChatConfig;
	private static BaseConfig baseConfig;
	private static StringRedisTemplate stringRedisTemplate;

	@Autowired
	public void setWeChatConfig(WeChatConfig weChatConfig) {
		WeiXinUtil.weChatConfig = weChatConfig;
	}

	@Autowired
	public void setBaseConfig(BaseConfig baseConfig) {
		WeiXinUtil.baseConfig = baseConfig;
	}

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		WeiXinUtil.stringRedisTemplate = stringRedisTemplate;
	}

	public static String getAccessToken() {
		String accessToken = stringRedisTemplate.opsForValue().get("WeChat:accessToken");
		if (StringUtils.isBlank(accessToken)) {
			synchronized ("0") {
				accessToken = stringRedisTemplate.opsForValue().get("WeChat:accessToken");
				if (StringUtils.isBlank(accessToken)) {
					accessToken = genToken();
					stringRedisTemplate.opsForValue().set("WeChat:accessToken", accessToken, 60, TimeUnit.MINUTES);
				}
			}
		}

		return accessToken;
	}

	/**
	 * 获取票据号
	 * 
	 * @param appID
	 * @param appSecret
	 * @return
	 */
	public static String genToken() {
		System.out.println("获取AccessToken" + System.currentTimeMillis() / 1000);

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("grant_type", "client_credential");
		map.put("appId", weChatConfig.getAppId());
		map.put("secret", weChatConfig.getAppSecret());
		String string = HttpClientUtil.get(BASE_URL + "/token", map);
		JSONObject json = JSON.parseObject(string);
		return json.getString("access_token");
	}

	/**
	 * 初始化菜单
	 * 
	 * @param accessToken
	 * @param menu
	 */
	public static void menuCreate(String menu) {
		HttpClientUtil.post(BASE_URL + "/menu/create?access_token=" + getAccessToken(), menu);
	}

	/**
	 * 删除菜单
	 */
	public static void menuDelete() {
		HttpClientUtil.get(BASE_URL + "/menu/delete?access_token=" + getAccessToken());
	}

	public static MpUserInfo userInfo(String openid) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("access_token", getAccessToken());
		map.put("openid", openid);

		return JSON.parseObject(HttpClientUtil.get(BASE_URL + "/user/info", map), MpUserInfo.class);
	}

	/**
	 * 发送文本消息
	 * 
	 * @param openid
	 * @param content
	 * @return
	 */
	public static String messageCustomSendText(String openid, String content) {
		messageCustomSendText(openid, content, null);
		return null;
	}

	public static String messageCustomSendText(String openid, String content, String kfAccount) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", openid);
		map.put("msgtype", "text");

		Map<String, Object> inn = new LinkedHashMap<String, Object>();
		inn.put("content", content);

		map.put("text", inn);
		if (StringUtils.isNotBlank(kfAccount)) {
			Map<String, Object> customservice = new LinkedHashMap<String, Object>();
			customservice.put("kf_account", kfAccount);
			map.put("customservice", customservice);
		}

		return HttpClientUtil.post(BASE_URL + "/message/custom/send?access_token=" + getAccessToken(),
				JSON.toJSONString(map));
	}

	/**
	 * 发送图片消息
	 * 
	 * @param openid
	 * @param mediaId
	 * @return
	 */
	public static String messageCustomSendImage(String openid, String mediaId) {
		return messageCustomSendImage(openid, mediaId, null);
	}

	public static String messageCustomSendImage(String openid, String mediaId, String kfAccount) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", openid);
		map.put("msgtype", "image");

		Map<String, Object> inn = new LinkedHashMap<String, Object>();
		inn.put("media_id", mediaId);

		map.put("image", inn);

		if (StringUtils.isNotBlank(kfAccount)) {
			Map<String, Object> customservice = new LinkedHashMap<String, Object>();
			customservice.put("kf_account", kfAccount);
			map.put("customservice", customservice);
		}
		return HttpClientUtil.post(BASE_URL + "/message/custom/send?access_token=" + getAccessToken(),
				JSON.toJSONString(map));
	}

	/**
	 * 发送语音消息
	 * 
	 * @param openid
	 * @param mediaId
	 * @return
	 */
	public static String messageCustomSendVoice(String openid, String mediaId) {
		return messageCustomSendVoice(openid, mediaId, null);
	}

	public static String messageCustomSendVoice(String openid, String mediaId, String kfAccount) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", openid);
		map.put("msgtype", "voice");

		Map<String, Object> inn = new LinkedHashMap<String, Object>();
		inn.put("media_id", mediaId);

		map.put("voice", inn);

		if (StringUtils.isNotBlank(kfAccount)) {
			Map<String, Object> customservice = new LinkedHashMap<String, Object>();
			customservice.put("kf_account", kfAccount);
			map.put("customservice", customservice);
		}
		return HttpClientUtil.post(BASE_URL + "/message/custom/send?access_token=" + getAccessToken(),
				JSON.toJSONString(map));
	}

	/**
	 * 发送视频消息
	 * 
	 * @param openid
	 * @param mediaId
	 * @param thumbMediaId
	 * @param title
	 * @param description
	 * @return
	 */
	public static String messageCustomSendVideo(String openid, String mediaId, String thumbMediaId, String title,
			String description) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("touser", openid);
		map.put("msgtype", "video");

		Map<String, Object> inn = new LinkedHashMap<String, Object>();
		inn.put("media_id", mediaId);
		inn.put("thumb_media_id", thumbMediaId);
		inn.put("title", title);
		inn.put("description", description);

		map.put("video", inn);

		return HttpClientUtil.post(BASE_URL + "/message/custom/send?access_token=" + getAccessToken(),
				JSON.toJSONString(map));
	}

	/**
	 * 发送音乐
	 * 
	 * @param openid
	 * @param thumbMediaId
	 * @param title
	 * @param description
	 * @param musicurl
	 * @param hqmusicurl
	 * @return
	 */
	public static String messageCustomSendMusic(String openid, String thumbMediaId, String title, String description,
			String musicurl, String hqmusicurl) {
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

		return HttpClientUtil.post(BASE_URL + "/message/custom/send?access_token=" + getAccessToken(),
				JSON.toJSONString(map));
	}

	/**
	 * 验证签名是否正确
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] arr = new String[] { weChatConfig.getToken(), timestamp, nonce };
		Arrays.sort(arr);
		String sha1Hex = DigestUtils.sha1Hex(StringUtils.join(arr));
		return sha1Hex.equals(signature);
	}
	public static String oauth2AuthorizeUrlByBaseOnPay(String url) {
		String appid = StringUtils.defaultIfBlank(weChatConfig.getPayAppid(), weChatConfig.getOauthAppId());
		return oauth2AuthorizeUrl(appid,url, "state", "snsapi_base");
	}
	
	public static String oauth2AuthorizeUrlByBase(String url) {
		return oauth2AuthorizeUrl(weChatConfig.getOauthAppId(),url, "state", "snsapi_base");
	}

	public static String oauth2AuthorizeUrlByUserinfo(String url) {
		return oauth2AuthorizeUrl(weChatConfig.getOauthAppId(),url, "state", "snsapi_userinfo");
	}
	
	private static String oauth2AuthorizeUrl(String appid,String url, String state, String scope) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("appid", appid);
		map.put("redirect_uri", baseConfig.getHostName() + url);
		map.put("response_type", "code");
		map.put("scope", "snsapi_userinfo".equals(scope) ? "snsapi_userinfo" : "snsapi_base");
		map.put("state", state);

		return HttpClientUtil.genUrl(BASE_OAUTH2_OPEN_URL + "/oauth2/authorize", map, "#wechat_redirect");
	}

	public static Oauth2AccessToken oauth2AccessToken(String code) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("appid", weChatConfig.getOauthAppId());
		map.put("secret", weChatConfig.getOauthAppSecret());
		map.put("code", code);
		map.put("grant_type", "authorization_code");

		return JSON.parseObject(HttpClientUtil.get(BASE_OAUTH2_URL + "/oauth2/access_token", map),
				Oauth2AccessToken.class);
	}
	public static Oauth2AccessToken oauth2AccessTokenOnPay(String code) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("appid", weChatConfig.getPayAppid());
		map.put("secret", weChatConfig.getPayAppSecret());
		map.put("code", code);
		map.put("grant_type", "authorization_code");

		return JSON.parseObject(HttpClientUtil.get(BASE_OAUTH2_URL + "/oauth2/access_token", map),
				Oauth2AccessToken.class);
	}

	public static String oauth2RefreshToken(String refreshToken) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("appid", weChatConfig.getOauthAppId());
		map.put("grant_type", "refresh_token");
		map.put("refresh_token", refreshToken);

		return HttpClientUtil.get(BASE_OAUTH2_URL + "/oauth2/refresh_token", map);

	}

	public static MpUserInfo oauth2UserInfo(String accessToken, String openid) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("access_token", accessToken);
		map.put("openid", openid);
		map.put("lang", "zh_CN");
		String string = HttpClientUtil.get(BASE_OAUTH2_URL + "/userinfo", map);
		return JSON.parseObject(string, MpUserInfo.class);

	}

}
