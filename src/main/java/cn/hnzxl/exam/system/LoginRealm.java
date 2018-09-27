package cn.hnzxl.exam.system;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hnzxl.exam.system.model.User;
import cn.hnzxl.exam.system.service.UserService;

public class LoginRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String currentUsername = (String) super.getAvailablePrincipal(principalCollection);
		if(currentUsername==null){
			currentUsername ="abcdefg";
		}
		User user = userService.selectByUserName(currentUsername);
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		if (user != null) {
			if (StringUtils.isNoneBlank(user.getPermission())) {
				simpleAuthorInfo.addStringPermissions(Arrays.asList(user.getPermission().split("\\|")));
			}
			return simpleAuthorInfo;
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		if (StringUtils.isEmpty(token.getUsername())) {
			throw new AccountException("");
		}
		// 此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息
		// 说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码(可以是从数据库中取到的,本例中为了演示就硬编码了)
		// 这样一来,在随后的登录页面上就只有这里指定的用户和密码才能通过验证
		User user = userService.selectByUserName(token.getUsername());
		if (user == null) {
			throw new AccountException("用户名不存在");
		}
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
				this.getName());
		this.setSession("currentUser", user);
		return authcInfo;
	}

	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

}
