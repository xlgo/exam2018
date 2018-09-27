<%@page import="com.github.pagehelper.Page"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%@ include file="/WEB-INF/view/common/pageForm.jsp"%>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<c:url value="/project/examination/list"/>" method="post">
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
					<%-- 
					<li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li>
					--%>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a title="试卷-添加" class="add" href="<c:url value="/project/examination/add?navTabId=${searchParam.navTabId}"/>" target="navTab"><span>添加</span></a></li>
			<li><a warn="请至少选择一行进行删除！" title="确实要删除吗？" target="selectedTodo" rel="id" href="<c:url value="/project/examination/delJSON"/>" class="delete"><span>批量删除</span></a></li>
			<li><a title="试卷-修改" class="edit" href="<c:url value="/project/examination/edit?navTabId=${searchParam.navTabId}&id="/>{examinationId}" rel="examination_edit" target="navTab" fresh="false" warn="请选要编辑的行！" ><span>修改</span></a></li>
			<%-- 
			<li class="line">line</li>
			<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			--%>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="id" class="checkboxCtrl"></th>
				<th orderField="examination_subject" class="${searchParam.sortField=='examination_subject'?searchParam.sortOrder:""}">试卷标题</th>
				<th orderField="examination_time_length" class="${searchParam.sortField=='examination_time_length'?searchParam.sortOrder:""}">答题时长</th>
				<th orderField="examination_start_time" class="${searchParam.sortField=='examination_start_time'?searchParam.sortOrder:""}">开卷时间</th>
				<th orderField="examination_end_time" class="${searchParam.sortField=='examination_end_time'?searchParam.sortOrder:""}">结束时间</th>
				<th orderField="examination_status" class="${searchParam.sortField=='examination_status'?searchParam.sortOrder:""}">状态</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.list}" var="model" varStatus="eachStatus">
				<tr target="examinationId" rel="${model.id}" >
					<td><input name="id" value="${model.id}" type="checkbox"></td>
					<td>${model.examinationSubject}</td>
					<td>${model.examinationTimeLength}</td>
					<td><fmt:formatDate value="${model.examinationStartTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td><fmt:formatDate value="${model.examinationEndTime}" pattern="yyyy-MM-dd HH:mm" /></td>
					<td>${model.examinationStatus==1?"可以答题":model.examinationStatus==2?"答题结束":"未开始"}</td>
					<td>
						<a title="确实要删除吗？" target="ajaxTodo" href="<c:url value="/project/examination/delJSON?id=${model.id}"/>" class="btnDel">删除</a>
						<a title="试卷-修改" target="navTab" rel="examination_edit" href="<c:url value="/project/examination/edit?navTabId=${searchParam.navTabId}&id=${model.id}"/>" fresh="false" class="btnEdit">编辑</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@ include file="/WEB-INF/view/common/pageBar.jsp"%>
</div>
