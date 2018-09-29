<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>第五届河南省大学生宗教知识网络竞赛</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style.css"/>">
</head>

<body class="index-bei">


<div class="index-head">
    <p class="index-one">第五届河南省</p>
    <p class="index-two">大学生宗教知识网络竞赛</p>
    <img src="<c:url value="/resource/mobile/image/toubei.png"/>" alt="" />
 </div>
<div class="index-nei">
    <a href="<c:url value="/m/startExam"/>"><div class="index-botton " >开始答题</div></a>
    <a href="<c:url value="/m/userExamInfo"/>"><div class="index-botton ">成绩查询</div></a>
    <a href="<c:url value="/m/resetPassword"/>"><div class="index-botton ">修改密码</div></a>
    <a href="<c:url value="/m/logout"/>"><div class="index-botton ">退出系统</div></a>
</div>










</body>
</html>
