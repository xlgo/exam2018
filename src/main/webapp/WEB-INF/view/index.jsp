<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>河南省大学生宗教知识网络竞赛</title>
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/main/login.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/bootstrap/css/bootstrap.min.css"/>" />
	<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resource/js/bootstrap/js/bootstrap.min.js" />"></script>
	<script type="text/javascript">
		$(function(){
			
		});
		function changePassrod(){
			var newPassword1 = $("[name=newPassword1]").val();
			var password = $("[name=password]").val();
			var oldPassword = $("[name=oldPassword]").val();
			if(!newPassword1){
				alert("新密码不能为空！");
				return;
			}else if(newPassword1!=password){
				alert("两次密码不一致！");
				return;
			}
			$.post("<c:url value="/system/user/changepassword1"/>",{oldPassword:oldPassword,newPassword:password},function(data){
				if(data.status=="success"){
					alert(data.msg);
					$('#changePassword').modal('hide')
				}else{
					alert(data.msg)
				}
			});
		}
	</script>
</head>
<body>
<div style="position: absolute;top:10px;right: 20px;font-size: 14px;">
<a href="<c:url value='/logout'/>">注销${currentUser.username }</a>
</div>

	<div class="loginWraper">
		<div class="exam"><img src="<c:url value="/resource/css/main/images/exam.jpg"/>" alt="" usemap="#Map" border="0" hidefocus="true"  />
	      <map name="Map" id="Map">
	        <area shape="circle" coords="849,182,63" target="_blank" href="<c:url value="/project/userquestion/startExam?navTabId=startExam"/>" />
	        <area shape="circle" coords="790,324,51" target="_blank" href="<c:url value="/project/userexamination/userExamInfo?type=1"/>" />
	        <area shape="circle" coords="703,436,48" target="_blank" href="<c:url value="/project/userexamination/userExamInfo?type=2"/>" />
	        <area shape="circle" coords="590,521,41" href="#" data-toggle="modal" data-target="#changePassword" />
	      </map>
		</div>
	    <div class="bottom"><%@ include file="/WEB-INF/view/common/bottom.jsp"%></div>	
	</div>
	
	<div id="changePassword" class="modal fade " >
	  <div class="modal-dialog modal-sm" style="width:320px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title">修改密码</h4>
	      </div>
	      <div class="modal-body">
	        <p><label>原&nbsp;密&nbsp;码&nbsp;：<input type="password" name="oldPassword" /></label></p>
	        <p><label>新&nbsp;密&nbsp;码&nbsp;：<input type="password" name="newPassword1" /></label></p>
	        <p><label>确认密码：<input type="password" name="password" /></label></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="changePassrod()">保存</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</body>
</html>