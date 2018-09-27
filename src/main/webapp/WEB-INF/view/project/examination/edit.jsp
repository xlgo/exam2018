<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<div class="pageContent">
	<form method="post" action="<c:url value="/project/examination/saveJSON"/>" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${model.examinationId}"/>
		<input type="hidden" name="navTabId" value="${param.navTabId}"/>
		<input type="hidden" name="examinationCategoryId" value="${model.examinationCategoryId}"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>试卷标题：</label> <input name="examinationSubject" class="required" type="text" style="width: 240px;" size="35"  value="${model.examinationSubject}" />
			</p>
			<p style="height: auto;">
				<label>概要说明：</label> <textarea name="examinationContent" class="required" style="width: 240px;" rows="3" >${model.examinationContent}</textarea>
			</p>
			<p>
				<label>竞赛开始时间：</label> <input type="text" name="examinationStartTime" class="date" datefmt="yyyy-MM-dd HH:mm" size="15"  value="<fmt:formatDate value="${model.examinationStartTime}" pattern="yyyy-MM-dd HH:mm" />" /><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p>
				<label>竞赛结束时间：</label> <input type="text" name="examinationEndTime" class="date" datefmt="yyyy-MM-dd HH:mm" size="15" value="<fmt:formatDate value="${model.examinationEndTime}" pattern="yyyy-MM-dd HH:mm" />" /><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p>
				<label>竞赛时长：</label> <input name="examinationTimeLength" class="required digits" type="text" size="5"  value="${model.examinationTimeLength}" /> <span class="unit">分钟</span>
			</p>
			<p>
				<label>竞赛总分：</label> <input name="examinationScore" class="required digits" type="text" size="5"  value="${model.examinationScore}" />
			</p>	
			<p>
				<label>试卷状态：</label>
				<select name="examinationStatus" size="10" class="required combox">
					<option value="0" ${model.examinationStatus==0 || empty model.examinationStatus?"selected=\"selected\"":""}>未开始</option>
					<option value="1" ${model.examinationStatus==1?"selected=\"selected\"":""}>可以答题</option>
					<option value="2" ${model.examinationStatus==2?"selected=\"selected\"":""}>答题结束</option>
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