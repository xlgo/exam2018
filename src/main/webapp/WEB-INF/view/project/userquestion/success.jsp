<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>交卷成功</title>
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
	  <h1>交卷成功！</h1>
	  <p>稍后你可以查看排名或通过错题对比查看失误的地方！</p>
	  <p><a class="btn btn-primary btn-lg" onclick="closeWindow();" role="button">关闭</a></p>
	</div>
</div>
</body>
</html>