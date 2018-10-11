<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	boolean mm = request.getHeader("user-agent").indexOf("Mobile")!=-1;
	pageContext.setAttribute("mm", mm);
%>
<c:if test="${!mm}">
<c:redirect url="/index2"/>
</c:if>
<c:if test="${mm}">
	<c:redirect url="/index2"/>
		<div style="text-align: center;">
			<br/>
		<h1 style="font-size: 24px;">关注口袋青春微信公众号，回复“参赛”进入竞赛</h1></div>
		<div style="text-align: center;"><img alt="微信二维码" width="80%" src="<c:url value="/resource/img/erweima.png"/>"></h1>
</c:if>