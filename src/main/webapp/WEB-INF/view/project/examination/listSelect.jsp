<%@page import="com.github.pagehelper.Page"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%@ include file="/WEB-INF/view/common/pageForm.jsp"%>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="<c:url value="/project/examination/listSelect"/>" method="post">
		<input type="hidden" name="navTabId" value="${searchParam.navTabId}" />
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>试卷名称：</label> <input type="text" name="S_examinationSubject" value="${searchParam.S_examinationSubject}" /></li>
				<li><label>试卷状态：</label> <select class="combox" name="examinationStatus">
						<option value="" >所有</option>
						<option value="0" ${searchParam.examinationStatus==0?"selected=\"selected\"":""}>未开始</option>
						<option value="1" ${searchParam.examinationStatus==1?"selected=\"selected\"":""}>可以答题</option>
						<option value="2" ${searchParam.examinationStatus==2?"selected=\"selected\"":""}>答题结束</option>
				</select></li>
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
	<table class="table" width="100%" layoutH="138" targetType="dialog">
		<thead>
			<tr>
				<th orderField="examination_subject" class="${searchParam.sortField=='examination_subject'?searchParam.sortOrder:""}">试卷标题</th>
				<th orderField="examination_time_length" class="${searchParam.sortField=='examination_time_length'?searchParam.sortOrder:""}">答题时长</th>
				<th orderField="examination_start_time" class="${searchParam.sortField=='examination_start_time'?searchParam.sortOrder:""}">开卷时间</th>
				<th orderField="examination_end_time" class="${searchParam.sortField=='examination_end_time'?searchParam.sortOrder:""}">结束时间</th>
				<th orderField="examination_status" class="${searchParam.sortField=='examination_status'?searchParam.sortOrder:""}">状态</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.list}" var="model" varStatus="eachStatus">
				<tr target="examinationId" rel="${model.id}" >
					<td>${model.examinationSubject}</td>
					<td>${model.examinationTimeLength}</td>
					<td><fmt:formatDate value="${model.examinationStartTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td><fmt:formatDate value="${model.examinationEndTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td>${model.examinationStatus==1?"可以答题":model.examinationStatus==2?"答题结束":"未开始"}</td>
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({id:'${model.id}', name:'${model.examinationSubject}'})" title="查找带回">选择</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@ include file="/WEB-INF/view/common/pageBarDialog.jsp"%>
</div>
