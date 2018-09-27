<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, userscalable=no">
	<title>第五届河南省大学生宗教知识网络竞赛</title>
	<link href="<c:url value="/resource/mobile/css2/style.css"/>" type="text/css" rel="stylesheet" />
	<link href="<c:url value="/resource/mobile/css2/reset.css"/>" type="text/css" rel="stylesheet" />
</head>
<body>
<div class="wrap">
	<div class="tip">
		<img src="<c:url value="/resource/mobile/image/warning.png"/>" width="50" />
		<p class="textCenter">${error }</p>
		<a href="<c:url value="/m/index"/>"><div class="btn"  >返回首页</div></a>
	</div>
</div>
</body>
</html>