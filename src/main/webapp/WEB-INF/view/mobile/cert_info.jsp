<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, userscalable=no">
	<title>河南省高校国家安全教育互联网知识竞赛</title>
	<link href="<c:url value="/resource/mobile/css2/style.css"/>" type="text/css" rel="stylesheet" />
	<link href="<c:url value="/resource/mobile/css2/reset.css"/>" type="text/css" rel="stylesheet" />
  	<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
	<script type="text/javascript" src="//static.runoob.com/assets/qrcode/qrcode.min.js"></script>
	
</head>
<body>
<div class="wrap">
	<c:if test="${not empty certImage}">
		<div class="tip">
			<img src="<c:url value="${certImage}"/>" width="100%"/>
			<p>您的证书验证码为：</p>
			<p>${certCode}</p>
			<p>二维码直接验证：</p>
			<p><span id="ermSpan"></span></p>
			<a href="<c:url value="/m/index"/>"><div class="btn"  >返回首页</div></a>
		</div>
		<script type="text/javascript">
			$(function(){
				var qrcode = new QRCode(document.getElementById("ermSpan"), {
					width : 100,
					height : 100
				});
				qrcode.makeCode("${hostName}/cert?certCode=${certCode}");
			});
		</script>
	</c:if>
	<c:if test="${empty certImage }">
		<div class="tip">
			<p>${msg}</p>
			<a href="<c:url value="/m/index"/>"><div class="btn"  >返回首页</div></a>
		</div>
	</c:if>
</div>
</body>
</html>

