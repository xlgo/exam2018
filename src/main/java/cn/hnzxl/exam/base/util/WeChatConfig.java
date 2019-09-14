package cn.hnzxl.exam.base.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.ProfileValueSourceConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.Data;

@Data
@Component
public class WeChatConfig {
	/**微信的 appId*/
	@Value("${weixin.appId}")
	private String appId;
	/**微信的 appSecret*/
	@Value("${weixin.appSecret}")
	private String appSecret;
	/**微信的 token*/
	@Value("${weixin.token}")
	private String token;
	/**微信的 encodingtype*/
	@Value("${weixin.encodingtype}")
	private String encodingtype;
	/**微信的 encodingaeskey*/
	@Value("${weixin.encodingaeskey}")
	private String encodingaeskey;
	/**微信网页授权的url*/
	@Value("${weixin.oauthUrl}")
	private String oauthUrl;
	/**微信网页授权的appId*/
	@Value("${weixin.oauthAppId}")
	private String oauthAppId;
	/**微信网页授权的AppSecret*/
	@Value("${weixin.oauthAppSecret}")
	private String oauthAppSecret;
	/**商户号 */
	private String payMchId;
	/**支付证书路径*/
	private String payCaPath;
	/**支付证书密码*/
	private String payCaPassword;
	/**签名key*/
	private String paySignKey;
	/**进行支付的appId*/
	private String payAppid;
	/**支付秘钥*/
	private String payAppSecret;
}
