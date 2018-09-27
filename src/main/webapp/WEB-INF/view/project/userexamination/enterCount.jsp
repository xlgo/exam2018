<%@page import="org.apache.ibatis.scripting.xmltags.ForEachSqlNode"%>
<%@page import="cn.hnzxl.exam.project.model.Question"%>
<%@page import="com.github.pagehelper.Page"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%@ include file="/WEB-INF/view/common/pageForm.jsp"%>

<div class="pageHeader">
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th >&nbsp;</th>
				<th >试卷名称</th>
				<th >开卷时间</th>
				<th >参赛人数</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${modelList}" var="model" varStatus="eachStatus">
				<tr >
					<td>${eachStatus.count }</td>
					<td>${model.examSubject}</td>
					<td><fmt:formatDate value="${model.startTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td>${model.count}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
