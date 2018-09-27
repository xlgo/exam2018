<%@page import="org.apache.ibatis.scripting.xmltags.ForEachSqlNode"%>
<%@page import="cn.hnzxl.exam.project.model.Question"%>
<%@page import="com.github.pagehelper.Page"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<%@ include file="/WEB-INF/view/common/pageForm.jsp"%>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<c:url value="/project/question/list"/>" method="post">
		<input type="hidden" name="navTabId" value="${searchParam.navTabId}" />
		<div class="searchBar">
			<ul class="searchContent">
				<li style="width: 246px;"><label  style="width: 60px;">题目名称：</label> <input type="text" name="S_questionSubject" value="${searchParam.S_questionSubject}" /></li>
				<li style="width: 148px;">
					<label style="width: 60px;">题目类型：</label>
					<select class="combox" name="questionType">
						<option value="" >所有</option>
						<option value="00" ${searchParam.questionType=="00"?"selected=\"selected\"":""}>判断</option>
						<option value="01" ${searchParam.questionType=="01"?"selected=\"selected\"":""}>单选</option>
						<option value="02" ${searchParam.questionType=="02"?"selected=\"selected\"":""}>多选</option>
					</select>
				</li>
				<li><label style="width: 38px;">状态：</label> <select class="combox" name="questionStatus">
						<option value="" >所有</option>
						<option value="0" ${searchParam.questionStatus==0?"selected=\"selected\"":""}>正常</option>
						<option value="1" ${searchParam.questionStatus==1?"selected=\"selected\"":""}>不可用</option>
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
			<li><a title="题库-添加" class="add" href="<c:url value="/project/question/add?navTabId=${searchParam.navTabId}"/>" target="navTab"><span>添加</span></a></li>
			<li><a warn="请至少选择一行进行删除！" title="确实要删除吗？" target="selectedTodo" rel="id" href="<c:url value="/project/question/delJSON"/>" class="delete"><span>批量删除</span></a></li>
			<li><a title="题库-修改" class="edit" href="<c:url value="/project/question/edit?navTabId=${searchParam.navTabId}&id="/>{questionId}" rel="question_edit" target="navTab" fresh="false" warn="请选要编辑的行！" ><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="<c:url value="/resource/excel/temple.xls"/>" ><span>导出模板</span></a></li>
			<li><a class="icon" href="<c:url value="/project/question/importDialog?navTabId=${searchParam.navTabId}"/>" height="210" width="410" rel="importDialog" mask="true" target="dialog" title="导入考题"><span>导入数据</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="id" class="checkboxCtrl"></th>
				<th width="40" orderField="question_type" class="${searchParam.sortField=='question_type'?searchParam.sortOrder:""}">类型</th>
				<th orderField="question_subject" class="${searchParam.sortField=='question_subject'?searchParam.sortOrder:""}">题目</th>
				<th orderField="question_right_answer" class="${searchParam.sortField=='question_right_answer'?searchParam.sortOrder:""}">正确答案</th>
				<th width="40" orderField="question_status" class="${searchParam.sortField=='question_status'?searchParam.sortOrder:""}">状态</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList.list}" var="model" varStatus="eachStatus">
				<tr class="${model.questionStatus==1?'danger':''}" target="questionId" rel="${model.id}"  >
					<td><input name="id" value="${model.id}" type="checkbox"></td>
					<td>${model.questionType=="00"?"判断":model.questionType=="01"?"单选":model.questionType=="02"?"多选":""}</td>
					<td title="${model.questionSubject}">${model.questionSubject}</td>
					<td title="<%
						Question question = (Question)pageContext.getAttribute("model");
						if("00".equals(question.getQuestionType())){
							//out.print("0".equals(question.getQuestionRightAnswer())?"×":"√");
						}else{
							try{
								String[] answers = question.getQuestionAnswer().split("\\||\r");
								String[] rightAnswersIndexs = question.getQuestionRightAnswer().split("\\||[ ;,]");
								for(String rightAnswersIndex : rightAnswersIndexs){
									int rightIndex = Integer.valueOf(rightAnswersIndex);
									if(rightIndex>0 && rightIndex <= answers.length){
										out.println(answers[rightIndex-1]);
									}
								}
							}catch(Exception e){
							}
						}
					%>">
						<%
							if("00".equals(question.getQuestionType())){
								out.print("0".equals(question.getQuestionRightAnswer())?"×":"√");
							}else{
								try{
									String[] answers = question.getQuestionAnswer().split("\\||\r");
									String[] rightAnswersIndexs = question.getQuestionRightAnswer().split("\\||[ ;,]");
									for(String rightAnswersIndex : rightAnswersIndexs){
										int rightIndex = Integer.valueOf(rightAnswersIndex);
										if(rightIndex>0 && rightIndex <= answers.length){
											out.print(answers[rightIndex-1]+";");
										}
									}
								}catch(Exception e){
								}
							}
						%>
					</td>
					<td>${model.questionStatus==1?"不可用":"正常"}</td>
					<td>
						<a title="确实要删除吗？" target="ajaxTodo" href="<c:url value="/project/question/delJSON?id=${model.id}"/>" class="btnDel">删除</a>
						<a title="题库-修改" target="navTab" rel="question_edit" href="<c:url value="/project/question/edit?navTabId=${searchParam.navTabId}&id=${model.id}"/>" fresh="false" class="btnEdit">编辑</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@ include file="/WEB-INF/view/common/pageBar.jsp"%>
</div>
