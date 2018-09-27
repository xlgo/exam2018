<%@page import="com.github.pagehelper.Page"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%@ include file="/WEB-INF/view/common/pageForm.jsp"%>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<c:url value="/project/userexamination/award"/>" method="post">
		<input type="hidden" name="navTabId" value="${searchParam.navTabId}" />
		<input name="examination.id" value="${searchParam['examination.id']}" type="hidden">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label style="width: 60px;">试卷：</label>
					<input  style="float: left" name="examination.name" type="text" value="${searchParam['examination.name']}"/>
					<a class="btnLook" href="<c:url value="/project/examination/listSelect"/>" lookupGroup="examination">查找带回</a>
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
				<th width="40">名次</th>
				<th>姓名</th>
				<th>竞赛用时</th>
				<th>竞赛得分</th>
				<th>领奖状态</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${modelList}" var="model" varStatus="eachStatus">
				<tr target="userId" rel="${model.id}" >
					<td>${eachStatus.count }</td>
					<td>${model.name}</td>
					<td><fmt:formatNumber pattern="0.0" value="${model.userExaminationTimeLength}"></fmt:formatNumber></td>
					<td>${model.userExaminationScore}</td>
					<td>${model.awardStatus==1?"已领取":"未领取"}</td>
					<td>
						<c:if test="${model.awardStatus!=1}">
							<a title="请确认领取信息<br/>手机号：${model.mobilenumber}<br/>身份证：${model.idnumber}" target="ajaxTodo" href="<c:url value="/project/userexamination/lingquJSON?id=${model.userExaminationId}"/>" style="background: url(<c:url value="/resource/img/lingqu.png"/>) 0 0;" class="btnDel">删除</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
