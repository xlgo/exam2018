<%@page import="org.apache.ibatis.scripting.xmltags.ForEachSqlNode"%>
<%@page import="cn.hnzxl.exam.project.model.Question"%>
<%@page import="com.github.pagehelper.Page"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%@ include file="/WEB-INF/view/common/pageForm.jsp"%>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<c:url value="/project/headline/list"/>" method="post">
		<input type="hidden" name="navTabId" value="${searchParam.navTabId}" />
		<input name="examination.id" value="${searchParam['examination.id']}" type="hidden">
		<div class="searchBar">
			<ul class="searchContent">
				<li style="width: 246px;"><label  style="width: 60px;">大题名称：</label> <input type="text" name="S_headlineHeadlineSubject" value="${searchParam.S_headlineHeadlineSubject}" /></li>
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
			<li><a title="大题-添加" class="add" href="<c:url value="/project/headline/add?navTabId=${searchParam.navTabId}"/>" target="navTab"><span>添加</span></a></li>
			<li><a warn="请至少选择一行进行删除！" title="确实要删除吗？" target="selectedTodo" rel="id" href="<c:url value="/project/headline/delJSON"/>" class="delete"><span>批量删除</span></a></li>
			<li><a title="大题-修改" class="edit" href="<c:url value="/project/headline/edit?navTabId=${searchParam.navTabId}&id="/>{headlineId}" rel="headline_edit" target="navTab" fresh="false" warn="请选要编辑的行！" ><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="id" class="checkboxCtrl"></th>
				<th orderField="headline_examination_id" class="${searchParam.sortField=='headline_examination_id'?searchParam.sortOrder:""}">试卷名称</th>
				<th orderField="headline_headline_subject" class="${searchParam.sortField=='headline_headline_subject'?searchParam.sortOrder:""}">大题名称</th>
				<th orderField="headline_question_count" class="${searchParam.sortField=='headline_question_count'?searchParam.sortOrder:""}">题目数量</th>
				<th orderField="headline_score" class="${searchParam.sortField=='headline_score'?searchParam.sortOrder:""}">每题分数</th>
				<th orderField="headline_pattern" class="${searchParam.sortField=='headline_pattern'?searchParam.sortOrder:""}">试卷模式</th>
				<th orderField="headline_answer_pattern" class="${searchParam.sortField=='headline_answer_pattern'?searchParam.sortOrder:""}">答案模式</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.list}" var="model" varStatus="eachStatus">
				<tr target="headlineId" rel="${model.id}"  >
					<td><input name="id" value="${model.id}" type="checkbox"></td>
					<td>${model.headlineExaminationName}</td>
					<td>${model.headlineHeadlineSubject}</td>
					<td>${model.headlineQuestionCount}</td>
					<td>${model.headlineScore}</td>
					<td>${model.headlinePattern==0?"选题":model.headlinePattern==1?"试卷随机":"用户随机"}</td>
					<td>${model.headlineAnswerPattern==1?"固定模式":"随机模式"}</td>
					<td>
						<a title="确实要删除吗？" target="ajaxTodo" href="<c:url value="/project/headline/delJSON?id=${model.id}"/>" class="btnDel">删除</a>
						<a title="大题-修改" target="navTab" rel="headline_edit" href="<c:url value="/project/headline/edit?navTabId=${searchParam.navTabId}&id=${model.id}"/>" fresh="false" class="btnEdit">编辑</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@ include file="/WEB-INF/view/common/pageBar.jsp"%>
</div>
