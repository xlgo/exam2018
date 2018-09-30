package cn.hnzxl.exam.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.log4j.Logger;

/**
 * get post 工具类
 * @author ZXL
 *
 */
public class HttpClientUtil {

	public static Logger log = Logger.getLogger(HttpClientUtil.class);
	public static String charset = "utf-8";
	
	private static HttpClient hc = HttpClientBuilder.create().build();
	public static String get(String url) {
		return get(url,null);
	}
	public static String get(String url,Map<String,Object> map) {
		try {
			if(map!=null && map.size()>0){
				List<NameValuePair> pair  = new ArrayList<NameValuePair>();
				for (String key : map.keySet()) {
					pair.add(new BasicNameValuePair(key, map.get(key).toString()));
				}
				
				String params = EntityUtils.toString(new UrlEncodedFormEntity(pair,charset));
				url+="?"+params;
			}
			log.info("get url:"+url);
			HttpGet get = new HttpGet(url);
			HttpResponse response = hc.execute(get);
			String result = EntityUtils.toString(response.getEntity());
			log.info("get response body:"+result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String post(String url ,Map<String,Object> map){
		log.info("post url:"+url);
		HttpPost post = new HttpPost(url);
		List<NameValuePair> pair  = new ArrayList<NameValuePair>();
		
		for (String key : map.keySet()) {
			pair.add(new BasicNameValuePair(key, map.get(key).toString()));
		}
		
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pair,charset);
			log.info("post body:"+EntityUtils.toString(entity,charset));
			post.setEntity(entity);
			HttpResponse response = hc.execute(post);
			String result = EntityUtils.toString(response.getEntity());
			log.info("post response body:"+result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String post(String url ,String body){
		log.info("post url:"+url);
		HttpPost post = new HttpPost(url);
		
		try {
			StringEntity entity = new StringEntity(body,charset);
			log.info("post body:"+EntityUtils.toString(entity,charset));
			post.setEntity(entity);
			HttpResponse response = hc.execute(post);
			String result = EntityUtils.toString(response.getEntity());
			log.info("post response body:"+result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		//map.put("user", "zxl");
		//map.put("tt", 222);
		System.out.println(HttpClientUtil.get("http://exam.hnzxl.cn/index2",map));
	}
	
}
