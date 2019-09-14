package cn.hnzxl.exam.base.util.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 获取code后，请求以下链接获取access_token
 * 
 * @author ZXL
 * @date 2018年11月3日
 */
@Data
public class Oauth2AccessToken {
	@JSONField(name="access_token")
	private String accessToken;
	@JSONField(name="expires_in")
	private int expiresIn;
	@JSONField(name="refresh_token")
	private String refreshToken;
	private String openid;
	private String scope;
	private String errcode;
	private String errmsg;
	private String unionid;
}