<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>第五届河南省大学生宗教知识网络竞赛</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style.css"/>">
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.7.2.js" />"></script>
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery.cookie.js" />"></script>
<script type="text/javascript">
	$(function(){
		var capObj;
		var msg="${msg}";
		if(msg){
			alert(msg);
		}
		if($.cookie("remember")){
			$("[name=username]").val($.cookie("username"));
			$("[name=password]").val($.cookie("password"));
			$("[name=remember]").prop("checked",$.cookie("remember"));
		}
		
		$(".login-tijiao").click(function(){
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
			$("#login-form").submit();
		});
		
		
	})
</script>
</head>

<body>

<div class="header">
	<img src="<c:url value="/resource/mobile/image/top01.jpg"/>">
</div>
<div class="common-wrapper">
	<div class="login-index">
		<form id="login-form" action="<c:url value="/m/login"/>" method="post" >
    	<div class="login-yong">
        	<div class="login-biao">
            	<img src="<c:url value="/resource/mobile/image/user.jpg"/>" class="login-tu" />
            </div>
        	<input placeholder="请输入用户名"  name="username"  type="text" class="shuru">
        </div>
    	<div class="login-mi">
            <div class="login-biao">
                <img src="<c:url value="/resource/mobile/image/password.jpg"/>" class="login-tu" />
            </div>
            <input placeholder="请输入密码"  name="password"  type="password" class="shuru">
        </div>
        <div class="login-ji">
        	<label><input type="checkbox"  id="remember" name="remember" />记住密码</label>
        </div>      
    	<div class="login-tijiao" >登录</div>
        <a href="<c:url value="/m/registerUser"/>" ><div class="login-zhuce">注册</div></a>
        </form>
	</div>



</div>
</body>
</html>
