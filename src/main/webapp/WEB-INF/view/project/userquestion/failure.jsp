<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>获取试卷失败</title>
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
	  <h1>获取试卷失败！</h1>
	  <p>你已经参加过竞赛(每人每期只有一次竞赛机会)，或者竞赛没有开始，如有疑问，请联系管理员！</p>
	  <p><a class="btn btn-danger btn-lg" onclick="closeWindow();" role="button">关闭</a></p>
	</div>
</div>
</body>
</html>