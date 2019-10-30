<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>河南省高校国家安全教育互联网知识竞赛</title>
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.7.2.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/gaimi.css"/>">
	<script type="text/javascript">
		$(function(){
			$("#submit").click(function(){
				var confirmPassword = $("[name=confirmPassword]").val();
				var oldPassword =$("[name=oldPassword]").val();
				var newPassword =$("[name=newPassword]").val();
				if(!oldPassword){
					alert("请输入原始密码！");
					$("[name=oldPassword]").focus();
					return ;
				}
				if(!newPassword){
					alert("请输入新密码！");
					$("[name=newPassword]").focus();
					return ;
				}
				if(newPassword!=confirmPassword){
					alert("两次输入的密码不一致！");
					return ;
				}
				
				$.post("changepassword1",{oldPassword:oldPassword,newPassword:newPassword},function(data){
					alert(data.msg);
					if(data.status == "success"){
						location.href="index";
					}
				});
			});
		})
	</script>
	<style type="text/css">
	</style>
</head>

<body >

<div class="gaimi-header">
    <div class="header-back">
        <a href="javascript:history.back()">
            <img src="<c:url value="/resource/mobile/image/arrow.png"/>">
        </a>
    </div>
    <div class="gaimi-title">修改密码</div> 
</div>
<div class="gaimi-index">
    <ul>
        <li><span>原密码：</span><input type="password"  name="oldPassword"/></li>
        <li><span>新密码：</span><input type="password"  name="newPassword" /></li>
        <li><span>确认密码：</span><input type="password"  name="confirmPassword" /></li>
    </ul>
</div>
<div class="login-zhuce gaitop"  id="submit">提交</div>

</body>
</html>
