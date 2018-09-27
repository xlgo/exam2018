<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>


<div class="pageContent">
	<form method="post" action="<c:url value="/project/question/importData"/>" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this,dialogAjaxDone);">
		<input type="hidden" name="navTabId" value="${param.navTabId}"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>文件一：</label>
				<input name="questiondata" type="file" />
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

<%--
<div class="pageContent">
	<form method="POST" class="pageForm required-validate" onsubmit="return false;">
		<input type="hidden" name="navTabId" value="${param.navTabId}"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<input id="fileInput" type="file" name="questiondata" 
					uploaderOption="{
						swf:'<c:url value="/resource/js/uploadify/scripts/uploadify.swf" />',
						uploader:'<c:url value="/project/question/importData"/>',
						formData:{navTabId:'${param.navTabId}',rel:'importDialog'},
						buttonText:'请选择文件',
						fileSizeLimit:'2MB',
						fileTypeExts:'*.xls;*.xlsx',
						fileTypeDesc:'Excel 文档',
						auto:false,
						multi:false,
						uploadLimit:1,
						
					}"
				/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button onclick="$('#fileInput').uploadify('upload', '*');">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
 --%>