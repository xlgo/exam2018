<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>


<div class="pageContent">
	<form method="post" action="<c:url value="/system/user/changepassword"/>" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>原密码：</label>
				<input name="oldPassword" type="password"  class="" />
			</p>
			<p>
				<label>新密码：</label>
				<input id="newPassword1" name="newPassword1" type="password" />
			</p>
			<p>
				<label>确认密码：</label>
				<input name="newPassword" type="password" equalto="#newPassword1" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>