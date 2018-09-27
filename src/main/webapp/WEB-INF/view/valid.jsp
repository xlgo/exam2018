<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>请验证</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/bootstrap/css/bootstrap.min.css"/>" />
	<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resource/js/bootstrap/js/bootstrap.min.js" />"></script>
	<script type="text/javascript">
		function closeWindow(){
			window.open('<c:url value="/"/>','_parent',''); 
			window.close();
		}
	</script>
</head>
<body>
<div class="jumbotron">
	<div class="container">
	  <h2>说明：为了验证您账号的唯一性，请先关注微信公众号，然后在下方菜单中点击“账号验证”，输入此页面提供给您的验证码验证后开始答题！</h2>
	  <h2>您的验证码为：${validCode }</h2>
	  <p><img alt="微信二维码" src="<c:url value="/resource/img/erweima.png"/>"></p>
	  <p>备注：输入此页面提供给您的验证码验证身份后，刷新此页面即可开始答题！</p>
	  <p><a class="btn btn-danger btn-lg"  href="<c:url value="/logout"/>" role="button">关闭</a></p>
	</div>
</div>
</body>
</html>