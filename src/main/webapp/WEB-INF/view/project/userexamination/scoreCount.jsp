<%@page import="com.github.pagehelper.Page"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%@ include file="/WEB-INF/view/common/pageForm.jsp"%>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<c:url value="/project/userexamination/scoreCount"/>" method="post">
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
					<label style="width: 60px;">学校：</label>
					<input  style="float: left" name="S_school_R" type="text" value="${searchParam['S_school_R']}"/>
				</li>
				<li>
					<label style="width: 60px;">数量：</label>
					<input  style="float: left" name="limit" type="text" value="${searchParam['limit']}"/>
				</li>
				<li>
					<label style="width: 60px;">答题方式：</label>
					<select name="systeminfo">
						<option value="">所有</option>
						<option ${searchParam['systeminfo']==1?"selected":""} value="1">PC</option>
						<option ${searchParam['systeminfo']==2?"selected":""} value="2">手机</option>
					</select>
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
				<th width="25">&nbsp;</th>
				<th>所在学校</th>
				<th>姓名</th>
				<th>手机号</th>
				<th>专业</th>
				<th>班级</th>
				<th>分数</th>
				<th>答题用时</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${modelList}" var="model" varStatus="eachStatus">
				<tr target="userId" rel="${model.id}" >
					<td>${eachStatus.count }</td>
					<td>${model.school}</td>
					<td>${model.name }</td>
					<td>${model.mobilenumber }</td>
					<td>${model.major}</td>
					<td>${model.classname}</td>
					<td>${model.user_examination_score}</td>
					<td><fmt:formatNumber value="${model.user_examination_time_length}" pattern="0.0"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
