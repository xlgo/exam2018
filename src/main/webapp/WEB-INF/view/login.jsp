<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>河南省大学生宗教知识网络竞赛</title>
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/main/login.css"/>" />
	
	<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery.cookie.js" />"></script>
	
	<script type="text/javascript">
		$(function(){
			var msg="${msg}";
			if(msg){
				alert(msg);
			}
			if($.cookie("remember")){
				$("[name=username]").val($.cookie("username"));
				$("[name=password]").val($.cookie("password"));
				$("[name=remember]").prop("checked",$.cookie("remember"));
			}
		});
		function loginCheck(){
			$username = $("[name=username]");
			$password = $("[name=password]");
			$remember = $("[name=remember]");
			if(!$username.val()){
				alert("请填写用户名！");
				$username.focus();
				return false;
			}
			if($remember.is(":checked")){
				$.cookie("username",$username.val(),{expires:7});
				$.cookie("password",$password.val(),{expires:7});
				$.cookie("remember",$remember.is(":checked"),{expires:7});
			}else{
				$.cookie("username",null);
				$.cookie("password",null);
				$.cookie("remember",null);
			}
			return true;
		}
	</script>
</head>
<body>
<form action="<c:url value="/login2"/>" method="post" onsubmit="return loginCheck();">
	<div class="loginWraper">
		<div class="loginTop"></div>
	    <div class="loginMain">
	    	<div class="loginMainLeft"></div>
	        <div class="loginMainCenter">
	        	<ul class="loginForm">
	            	<li><label>用户名：</label><input name="username" type="text" class="txt1" /></li>
	                <li><label>密&nbsp;码：</label><input name="password" type="password" class="txt1" /></li>
	                <li class="saveInfo"><input name="remember" type="checkbox" />记住密码 &nbsp;&nbsp;&nbsp;<a href="<c:url value="/system/user/registerUser" />">我要注册</a></li>
	                <li><input type="submit" class="loginBtn" value="" /></li>
	            </ul>
	        </div>
	        <div class="loginMainRight"></div>
	    </div>
	    <div class="bottom"><%@ include file="/WEB-INF/view/common/bottom.jsp"%></div>	
	</div>
</form>
</body>
</html>