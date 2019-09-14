package cn.hnzxl.exam.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * get post 工具类
 * 
 * @author ZXL
 *
 */
@Slf4j
public class HttpClientUtil {

	public static String CHARSET = "UTF-8";

	private static HttpClient hc = HttpClientBuilder.create().build();

	public static String get(String url) {
		return get(url, null);
	}

	public static String get(String url, Map<String, Object> map) {

		return get(url, map, null);
	}

	/**
	 * 拼接好后的url后面加个后缀
	 * 
	 * @param url
	 * @param map
	 * @param suffix
	 * @return
	 */
	public static String get(String url, Map<String, Object> map, String suffix) {
		try {
			url = genUrl(url, map, suffix);
			
			log.info("get url:" + url);
			HttpGet get = new HttpGet(url);
			HttpResponse response = hc.execute(get);
			String result = EntityUtils.toString(response.getEntity(),CHARSET);
			log.info("get response body:" + result);
			return result;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(),e);
			e.printStackTrace();
		}
		return null;
	}

	public static String genUrl(String url, Map<String, Object> map, String suffix) {
		try {
			if (map != null && map.size() > 0) {
				List<NameValuePair> pair = new ArrayList<NameValuePair>();
				for (String key : map.keySet()) {
					pair.add(new BasicNameValuePair(key, map.get(key).toString()));
				}

				String params = EntityUtils.toString(new UrlEncodedFormEntity(pair, CHARSET));
				url += "?" + params + StringUtils.defaultString(suffix, "");
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(),e);
			e.printStackTrace();
		}
		return url;
	}

	public static String post(String url, Map<String, Object> map) {
		log.info("post url:" + url);
		HttpPost post = new HttpPost(url);
		List<NameValuePair> pair = new ArrayList<NameValuePair>();

		for (String key : map.keySet()) {
			pair.add(new BasicNameValuePair(key, map.get(key).toString()));
		}

		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pair, CHARSET);
			log.info("post body:" + EntityUtils.toString(entity, CHARSET));
			post.setEntity(entity);
			HttpResponse response = hc.execute(post);
			String result = EntityUtils.toString(response.getEntity());
			log.info("post response body:" + result);
			return result;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(),e);
			e.printStackTrace();
		}
		return null;
	}

	public static String post(String url, String body) {
		log.info("post url:" + url);
		HttpPost post = new HttpPost(url);

		try {
			StringEntity entity = new StringEntity(body, CHARSET);
			log.info("post body:" + EntityUtils.toString(entity, CHARSET));
			post.setEntity(entity);
			HttpResponse response = hc.execute(post);
			String result = EntityUtils.toString(response.getEntity());
			log.info("post response body:" + result);
			return result;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(),e);
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
	}

}
