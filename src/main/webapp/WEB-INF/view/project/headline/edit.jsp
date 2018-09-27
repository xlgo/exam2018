<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<div class="pageContent">
	<form method="post" action="<c:url value="/project/headline/saveJSON"/>" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${model.id}"/>
		<input type="hidden" name="navTabId" value="${param.navTabId}"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>试卷：</label>
				<input name="examination.id" value="${model.headlineExaminationId}" type="hidden">
				<input name="examination.name" type="text" value="${model.headlineExaminationName}"/>
				<a class="btnLook" href="<c:url value="/project/examination/listSelect"/>" lookupGroup="examination">查找带回</a>
				
				<%-- <input name="headlineExaminationId" class="required" type="text" style="width: 240px;" size="35"  value="${model.headlineExaminationId}" />
				--%>
			</p>
			<p>
				<label>大题名称：</label> <input name="headlineHeadlineSubject" class="required" type="text" style="width: 240px;" size="35"  value="${model.headlineHeadlineSubject}" />
			</p>
			<p style="height: auto;">
				<label>大题说明：</label> <textarea name="headlineExaminationContent" class="" style="width: 240px;" rows="4" >${model.headlineExaminationContent}</textarea>
			</p>
			<p>
				<label>问题类型：</label>
				<label style="width: 50px;"><input type="radio" name="headlineQuestionType" value="00" checked="checked" />判断 </label>
				<label style="width: 50px;"><input type="radio" name="headlineQuestionType" value="01" ${model.headlineQuestionType=="01"?"checked=\"checked\"":""}/>单选</label> 
				<label style="width: 50px;"><input type="radio" name="headlineQuestionType" value="02" ${model.headlineQuestionType=="02"?"checked=\"checked\"":""}/>复选</label> 
			</p>
			<p>
				<label>问题总数：</label> <input name="headlineQuestionCount" class="required digits" type="text" style="width: 25px;" value="${model.headlineQuestionCount}" />
			</p>
			<p>
				<label>每小题分数：</label> <input name="headlineScore" class="required digits" type="text" style="width: 25px;" value="${model.headlineScore}" />
			</p>
			<p>
				<label>试卷模式：</label>
				<label style="width: 50px;"><input type="radio" name="headlinePattern" value="0" checked="checked" />选题 </label>
				<label style="width: 70px;"><input type="radio" name="headlinePattern" value="1" ${model.headlinePattern=="1"?"checked=\"checked\"":""}/>试卷随机</label> 
				<label style="width: 70px;"><input type="radio" name="headlinePattern" value="2" ${model.headlinePattern=="2"?"checked=\"checked\"":""}/>用户随机</label> 
			</p>
			<p>
				<label>答案模式：</label>
				<label style="width: 50px;"><input type="radio" name="headlineAnswerPattern" value="1" checked="checked" />固定 </label>
				<label style="width: 50px;"><input type="radio" name="headlineAnswerPattern" value="2" ${model.headlineAnswerPattern=="2"?"checked=\"checked\"":""}/>随机</label> 
			</p>
			<p>
				<label>序号：</label> <input name="headlineSort" class="required digits" type="text" style="width: 25px;"  value="${model.headlineSort}" />
			</p>
		</div>
		<div class="formBar">
			<ul>
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