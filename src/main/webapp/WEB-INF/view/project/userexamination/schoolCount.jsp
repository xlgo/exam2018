<%@page import="com.github.pagehelper.Page"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%@ include file="/WEB-INF/view/common/pageForm.jsp"%>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<c:url value="/project/userexamination/schoolCount"/>" method="post">
		<input type="hidden" name="navTabId" value="${searchParam.navTabId}" />
		<input name="examination.id" value="${searchParam['examination.id']}" type="hidden">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label style="width: 60px;">试卷：</label>
					<input  style="float: left" name="examination.name" type="text" value="${searchParam['examination.name']}"/>
					<a class="btnLook" href="<c:url value="/project/examination/listSelect"/>" lookupGroup="examination">查找带回</a>
				</li>
				<li>
					<label style="width: 60px;">数量：</label>
					<input  style="float: left" name="limit" type="text" value="${searchParam['limit']}"/>
				</li>
			</ul>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">检索</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">&nbsp;</th>
				<th>学校名称</th>
				<th>参考人数</th>
				<th>总分数</th>
				<th>平均分数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${modelList}" var="model" varStatus="eachStatus">
				<tr target="userId" rel="${model.id}" >
					<td>${eachStatus.count }</td>
					<td>${model.school}</td>
					<td>${model.count}</td>
					<td>${model.sumScore}</td>
					<td><fmt:formatNumber value="${model.sumScore/model.count}" pattern="0.0"></fmt:formatNumber> </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
