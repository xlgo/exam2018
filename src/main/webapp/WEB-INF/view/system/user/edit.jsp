<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<div class="pageContent">
	<form method="post" action="<c:url value="/system/user/saveJSON"/>" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${model.id}"/>
		<input type="hidden" name="navTabId" value="${param.navTabId}"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>用户名：</label>
				<c:if test="${model.METHOD=='add'}">
					<input remote="<c:url value="/system/user/hasUse" />" name="username" class="required"  maxlength="20" type="text" value="${model.username}" />
				</c:if>
				<c:if test="${model.METHOD=='edit'}">
					<input name="username" type="hidden" value="${model.username}" />
					<input disabled="disabled" type="text" value="${model.username}" />
				</c:if>
			</p>
			<p>
				<label>密码：</label> <input name="password" class="required"  type="password" value="${model.password}" />
			</p>
			<p>
				<label>姓名：</label> <input name="name" class="required"  maxlength="10" type="text" value="${model.name}" />
			</p>
			<p style="height: auto;">
				<label>性别：</label>
				<label style="width: 40px;"><input type="radio" name="gender" value="0" checked="checked" />男 </label>
				<label style="width: 40px;"><input type="radio" name="gender" value="1" ${model.gender==1?"checked=\"checked\"":""}/>女</label> 
			</p>
			<p>
				<label>年龄：</label> <input type="text" name="age" class="digits" size="3" min="10" max="70"  value="${model.age}" />
			</p>
			<p>
				<label>身份证号：</label> <input name="idnumber" class="required digits" type="text" value="${model.idnumber}" />
			</p>
			<p>
				<label>手机号：</label> <input name="mobilenumber" class="required phone" type="text" value="${model.mobilenumber}" />
			</p>
			<p>
				<label>地区：</label> <input name="area" class="" style="width: 240px;" type="text" value="${model.area}" />
			</p>
			<p>
				<label>所属学校：</label> <input name="school" class="" style="width: 240px;" type="text" value="${model.school}" />
			</p>
			<p>
				<label>所在班级：</label> <input name="classname" class="" style="width: 240px;" type="text" value="${model.classname}" />
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