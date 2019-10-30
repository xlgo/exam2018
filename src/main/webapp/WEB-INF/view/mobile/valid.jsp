<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>河南省高校国家安全教育互联网知识竞赛</title>
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.7.2.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/gaimi.css"/>">

<script type="text/javascript">
	if("${error}"){
		alert("${error}")
	}
	
	$(function(){
		$(".gaitop").click(function(){
			$("form").submit();
		});
	})
</script>
</head>

<body >
<%-- 
<div class="tishi-header">
    <div class="tishi-head">
        <div class="header-close">
            <a href="<c:url value="/"/>">
                <img src="<c:url value="/resource/mobile/image/cross.png"/>">
            </a>
        </div>
        <div class="tishi-title">请输入验证信息</div> 
        <form action="<c:url value="/m/doValid"/>" method="post">
	        <label>账　号：<input name="username"  /></label>
	        <br/>
	        <label>验证码：<input name="valid" /></label>
	        <br/>
	        <input type="submit" />
        </form>
    </div>
</div> --%>
<div class="gaimi-header">
    <div class="header-back">
        <a href="javascript:history.back()">
            <img src="<c:url value="/resource/mobile/image/arrow.png"/>">
        </a>
    </div>
    <div class="gaimi-title">用户验证</div> 
</div>
<form action="<c:url value="/m/doValid"/>" method="post">
<div class="gaimi-index">
    <p class="register-tishi">请输入您的账号及电脑网页提供给您的验证码进行身份唯一性校验！</p>
    <ul>
        <li><span>账　号：</span><input type="text"  id="username" name="username"  placeholder="请输入用户名"/></li>
        <li><span>验证码：</span><input type="text" id="valid" name="valid"  placeholder="请输入验证码"/></li>
     </ul>
</div>
<div class="login-tijiao gaitop" >提交</div>
</form>
</body>
</html>
