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
				<th width="40" align="center"> 类型 </th>
				<th >问题名称</th>
				<th >答对人数</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${modelList}" var="model" varStatus="eachStatus">
				<tr >
					<td>${eachStatus.count }</td>
					<td align="center">${model.type=="00"?"判断":model.type=="01"?"单选":model.type=="02"?"多选":""}</td>
					<td>${model.questionSubject}</td>
					<td>${model.count}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
