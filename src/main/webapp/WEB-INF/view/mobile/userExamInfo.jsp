<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>河南省第二届大学生反邪教警示教育知识竞赛</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/gaimi.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/tishi.css"/>">
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.7.2.js" />"></script>
<script type="text/javascript">
	function viewRanking(examId){
		//$.get('<c:url value="/project/userexamination/ranking?id="/>'+examId+"&_r="+Math.random(),function(data){
			$("#"+examId).html("加载中...");
		//});
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
    <ul>
        <li><span>试卷名称：</span><b><lable>${modelList[0].userExaminationExaminationName}</lable></b></li>
        <li><span>开始时间：</span><b><fmt:formatDate value="${modelList[0].createTime}" pattern="yyyy-MM-dd HH:mm"/></b></li>
        <li><span>答卷耗时：</span><b><fmt:formatNumber value="${modelList[0].userExaminationTimeLength}" pattern="0.0"/></b></li>
        <li><span>得分：</span><b>${modelList[0].userExaminationScore!=null?modelList[0].userExaminationScore:"计算中..."}</b></li>
    </ul>
</div>



</body>
</html>
