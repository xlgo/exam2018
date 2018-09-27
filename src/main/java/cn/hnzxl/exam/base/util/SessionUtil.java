package cn.hnzxl.exam.base.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import cn.hnzxl.exam.system.model.User;

/**
 * 获取session 中的一些信息
 * @author ZXL
 * @date 2014年11月10日 上午12:08:29
 *
 */
public class SessionUtil {
	/**
	 * 获取属性值
	 * @param attr
	 * @return
	 */
	public static Object getAttribute(String attr){
		return getSession().getAttribute(attr);
	}
	public static void setAttribute(String attr,String value){
		getSession().setAttribute(attr,value);
	}
	public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getRemoteAddr();  
                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                    //根据网卡取本机配置的IP  
                    InetAddress inet=null;  
                    try {  
                        inet = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {
                    }  
                    ipAddress= inet.getHostAddress();  
                }  
            }  
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
                if(ipAddress.indexOf(",")>0){  
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
                }  
            }  
            return ipAddress;   
    }
	/**
	 * 获取当前登陆用户的信息
	 * @return
	 */
	public static User getCurrentUser(){
		Object user = getAttribute("currentUser");
		if(user==null){
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
		}
		return (User)user;
	}
	private static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	private static Session getSession(){
		return getSubject().getSession();
	}
	 /**
     * 方法级频率控制
     * @param rate 毫秒
     */
	public static void rateLimit(Long rate) {
		StackTraceElement traceElement = Thread.currentThread().getStackTrace()[2];
		rateLimit(traceElement.getClassName()+"."+traceElement.getMethodName(),rate);
	}
	public static void rateLimit(String rateKey,Long rate) {
		Long lastTimeMillis = (Long)SessionUtil.getSession().getAttribute(rateKey);
		long currentTimeMillis = System.currentTimeMillis();
		SessionUtil.getSession().setAttribute(rateKey,currentTimeMillis);
		if(lastTimeMillis==null){
			return;
		}
		if(currentTimeMillis-lastTimeMillis<rate){
			throw new RuntimeException("发送频率过快");
		}
	}
}
