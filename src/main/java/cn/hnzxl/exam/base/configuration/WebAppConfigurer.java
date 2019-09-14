package cn.hnzxl.exam.base.configuration;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.hnzxl.exam.base.util.HttpClientUtil;
import cn.hnzxl.exam.base.util.SessionUtil;
import cn.hnzxl.exam.base.util.WeiXinUtil;
import cn.hnzxl.exam.system.model.User;

@Configuration
public class WebAppConfigurer extends HandlerInterceptorAdapter {
	//可以直接访问的页面
	private List<String> anon;
	//需要授权之后可以访问的页面
	private List<String> auth;
	AntPathMatcher pathMatcher = new AntPathMatcher("/");
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		User user = SessionUtil.getCurrentUser();
		if (user == null) {
			String queryString = request.getQueryString();
			String formUrl = request.getContextPath() + request.getRequestURI()
					+ (StringUtils.isNotEmpty(queryString) ? "?" + queryString : "");
			formUrl = URLEncoder.encode(formUrl, HttpClientUtil.CHARSET);
			String redirectUri = "/oauth2?formUrl=" + formUrl;
			response.sendRedirect(WeiXinUtil.oauth2AuthorizeUrlByUserinfo(redirectUri));
			return false;
		}else if(user.getStatus()!=1){
			for (String path : auth) {
				if(pathMatcher.matchStart(path, uri)){//如果不需要登陆，就直接展示页面
					return true;
				}
			}
			response.sendRedirect("/m/reginfo");
			return false;
		}
		return true;
	}
	public List<String> getAnon() {
		return anon;
	}
	public void setAnon(List<String> anon) {
		this.anon = anon;
	}
	public List<String> getAuth() {
		return auth;
	}
	public void setAuth(List<String> auth) {
		this.auth = auth;
	}
	
}
