<%@page import="com.github.pagehelper.Page"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%@ include file="/WEB-INF/view/common/pageForm.jsp"%>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<c:url value="/system/user/list"/>" method="post">
		<input type="hidden" name="navTabId" value="${searchParam.navTabId}" />
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>姓名：</label> <input type="text" name="S_name" value="${searchParam.S_name}" /></li>
				<li><label>用户名：</label> <input type="text" name="S_username" value="${searchParam.S_username}" /></li>
				<li><label>性别：</label> <select class="combox" name="gender">
						<option value="" >所有</option>
						<option value="0" ${searchParam.gender==0?"selected=\"selected\"":""}>男</option>
						<option value="1" ${searchParam.gender==1?"selected=\"selected\"":""}>女</option>
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
			<li><a title="用户-添加" class="add" href="<c:url value="/system/user/add?navTabId=${searchParam.navTabId}"/>" target="navTab"><span>添加</span></a></li>
			<li><a warn="请至少选择一行进行删除！" title="确实要删除吗？" target="selectedTodo" rel="id" href="<c:url value="/system/user/delJSON"/>" class="delete"><span>批量删除</span></a></li>
			<li><a title="用户-修改" class="edit" href="<c:url value="/system/user/edit?navTabId=${searchParam.navTabId}&id="/>{userId}" rel="user_edit" target="navTab" fresh="false" warn="请选要编辑的行！" ><span>修改</span></a></li>
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
				<th orderField="name" class="${searchParam.sortField=='name'?searchParam.sortOrder:""}">姓名</th>
				<th orderField="gender" class="${searchParam.sortField=='gender'?searchParam.sortOrder:""}">性别</th>
				<th orderField="username" class="${searchParam.sortField=='username'?searchParam.sortOrder:""}">用户名</th>
				<th orderField="age" class="${searchParam.sortField=='age'?searchParam.sortOrder:""}">年龄</th>
				<th orderField="mobilenumber" class="${searchParam.sortField=='mobilenumber'?searchParam.sortOrder:""}">手机号</th>
				<th orderField="school" class="${searchParam.sortField=='school'?searchParam.sortOrder:""}">所属学校</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.list}" var="model" varStatus="eachStatus">
				<tr target="userId" rel="${model.id}" >
					<td><input name="id" value="${model.id}" type="checkbox"></td>
					<td>${model.name}</td>
					<td>${model.gender==0?"男":"女"}</td>
					<td>${model.username}</td>
					<td>${model.age}</td>
					<td>${model.mobilenumber}</td>
					<td>${model.school}</td>
					<td>
						<a title="确实要删除吗？" target="ajaxTodo" href="<c:url value="/system/user/delJSON?id=${model.id}"/>" class="btnDel">删除</a>
						<a title="用户-修改" target="navTab" rel="user_edit" href="<c:url value="/system/user/edit?navTabId=${searchParam.navTabId}&id=${model.id}"/>" fresh="false" class="btnEdit">编辑</a>
						<a title="设置此用户进行从新答题？" target="ajaxTodo" href="<c:url value="/system/user/resetJSON?id=${model.id}"/>" class="btnSelect">从新竞赛</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@ include file="/WEB-INF/view/common/pageBar.jsp"%>
</div>
