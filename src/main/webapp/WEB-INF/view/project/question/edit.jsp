<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<div class="pageContent">
	<form method="post" action="<c:url value="/project/question/saveJSON"/>" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${model.questionId}"/>
		<input type="hidden" name="navTabId" value="${param.navTabId}"/>
		<input type="hidden" name="examinationCategoryId" value="${model.questionCategoryId}"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>题目：</label> <input name="questionSubject" class="required" type="text" style="width: 240px;" size="35"  value="${model.questionSubject}" />
			</p>
			<p>
				<label>类型：</label>
				<label style="width: 50px;"><input type="radio" name="questionType" value="00" checked="checked" />判断 </label>
				<label style="width: 50px;"><input type="radio" name="questionType" value="01" ${model.questionType=="01"?"checked=\"checked\"":""}/>单选</label> 
				<label style="width: 50px;"><input type="radio" name="questionType" value="02" ${model.questionType=="02"?"checked=\"checked\"":""}/>复选</label> 
			</p>
			<p style="height: auto;">
				<label>备选答案：</label> <textarea name="questionAnswer" class="" style="width: 240px;" rows="4" >${model.questionAnswer}</textarea>
			</p>
			<p>
				<label>正确答案：</label> <input name="questionRightAnswer" class="required" type="text" style="width: 240px;" size="35"  value="${model.questionRightAnswer}" />
			</p>
			<p>
				<label>默认分数：</label> <input name="questionScore" class="digits" type="text" size="5"  value="${model.questionScore}" />
			</p>
			<p style="height: auto;">
				<label>题目解析：</label> <textarea name="questionAnalysis" class="" style="width: 240px;" rows="3" >${model.questionAnalysis}</textarea>
			</p>
			<p>
				<label>试卷状态：</label>
				<select name="questionStatus" size="10" class="required combox">
					<option value="0" ${model.questionStatus==0 || empty model.questionStatus?"selected=\"selected\"":""}>正常</option>
					<option value="1" ${model.questionStatus==1?"selected=\"selected\"":""}>不可用</option>
				</select>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>