<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<div class="pageContent">
	<form method="post" action="<c:url value="/system/config/choujiangSaveJSON"/>" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${model.id}"/>
		<input type="hidden" name="navTabId" value="${param.navTabId}"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>开始时间：</label> <input type="text" name="ss" class="date" datefmt="HH:mm" size="15"  value="${pm[1]}" /><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p>
				<label>结束时间：</label> <input type="text" name="se" class="date" datefmt="HH:mm" size="15" value="${pm[2]}" /><a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p>
				<label>奖品数量：</label> <input type="text" name="sc" class="number"  size="3" value="${pm[0]}" />
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