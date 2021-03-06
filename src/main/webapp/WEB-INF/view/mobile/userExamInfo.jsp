<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>河南省高校国家安全教育互联网知识竞赛</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/gaimi.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/tishi.css"/>">
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.7.2.js" />"></script>
<script type="text/javascript">
	function viewCert(){
		$.get('<c:url value="/m/viewCert"/>',function(data){
			if(data.status){
				location.href=data.url;
			}else{
				alert(data.msg);
			}
		},"json");
	}
</script>
</head>

<body >

<div class="gaimi-header">
    <div class="header-back">
        <a href="<c:url value="/m/index"/>">
               <img src="<c:url value="/resource/mobile/image/arrow.png"/>">
           </a>
    </div>
    <div class="gaimi-title">成绩查询</div> 
</div>
<div class="gaimi-index">
<c:forEach items="${modelList}" var="item">
    <ul>
        <li><span>试卷名称：</span><b><lable>${item.userExaminationExaminationName}</lable></b></li>
        <li><span>开始时间：</span><b><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm"/></b></li>
        <li><span>答卷耗时：</span><b><fmt:formatNumber value="${item.userExaminationTimeLength}" pattern="0.0"/></b></li>
        <li><span>得分：</span><b>${item.userExaminationScore!=null?item.userExaminationScore:"计算中..."}</b></li>
    </ul>
</c:forEach>
    
</div>
<div class="login-tijiao gaitop" onclick="viewCert();">查看证书</div>



</body>
</html>
