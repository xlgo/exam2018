<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户注册</title>
		<link href="<c:url value="/resource/css/reset.css"/>"  rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/jquery-ui/jquery-ui.min.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/bootstrap/css/bootstrap.min.css"/>" />
		<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.11.1.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resource/js/bootstrap/js/bootstrap.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resource/js/jquery-ui/jquery-ui.min.js" />"></script>
		
<script type="text/javascript">
	function closeWindow() {
		window.open('<c:url value="/"/>', '_parent', '');
		window.close();
	}
	
	$(function(){
		if("${errorMsg}"){
			alert("${errorMsg}");
		}
		
		//加载学院信息
		$.get("<c:url value="/resource/data/school.json"/>",function(data){
			schoolInfo= JSON.parse(data);
			$("#school").autocomplete({
			      source: schoolInfo[$("[name=idnumber]:checked").val()].split(",") 
		    });
		},"text");
		$("[name=idnumber]").change(function(){
			$("#school").val("");
			$("#school").autocomplete({
			      source: schoolInfo[$("[name=idnumber]:checked").val()].split(",") 
		    });
		});
	});
	function checkForm(){
		var username = $("[name=username]").val();
		if(!username){
			alert("用户名不能为空！");
			return false;
		}
		var hasUse=0;
		$.ajax({
			url:"<c:url value="/system/user/hasUse"/>",
			type:"post",
			async:false,
			data:{username:username},
			success:function(data){
				if(data==false){
					alert("用户名已经存在！");
					hasUse=1;
				}
			}
		});
		if(hasUse==1){
			return false;
		}
		if(!$("[name=password]").val()){
			alert("密码不能为空！");
			return false;
		}
		if($("[name=password]").val()!=$("[name=password2]").val()){
			alert("两次密码不一致！");
			return false;
		}
		if(!$("[name=name]").val()){
			alert("姓名不能为空！");
			return false;
		}
		
		if(!($("[name=age]").val()>10 && $("[name=age]").val()<100)){
			alert("非法的年龄值！");
			return false;
		}
		
		if(!($("[name=mobilenumber]").val().length==11)){
			alert("请输入正确的手机号");
			return false;
		}
		
		if(!$("#major").val()){
			alert("请输入您的专业！");
			return false;
		}
		if(!$("[name=school]").val()){
			alert("所属学校不能为空！");
			return false;
		}
		
		return true;
	}
</script>
<style type="text/css">
.formItem span{
	line-height: 26px;
	text-align: right;
	width: 140px;
	display: block;
	float: left;
}

.ui-autocomplete {
 max-height: 135px;
 overflow-y: auto;
 overflow-x: hidden;
}
</style>
	</head>
	<body class="bgfafafa">
		<div class="topPic"></div>
		<div class="regContent">
			<div class="welcome">
				<h3>欢迎注册<span><a href="<c:url value="/ewm"/>">已有账号登录</a></span></h3>
				<p>请如实填写下面信息，在每期网络知识竞赛后对于获奖者需要进行个人信息核实。
信息一经提交，不允许修改。</p>
			</div>
			<form id="form" method="post" action="<c:url value="/system/user/saveRegister"/>"  onsubmit="return checkForm();">
			
			<table cellpadding="0" cellspacing="0" border="0" class="regTab">
				<tr>
					<td width="120" class="textRight">用户名</td>
					<td width="370"><input id="username" name="username"  type="text" class="normalInput" placeholder="请输入用户名"  value="${user.username }"/></td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">密码</td>
					<td><input type="password" id="password" name="password" class="normalInput" placeholder="请输入密码" /></td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">确认密码</td>
					<td><input type="password" id="password2" name="password2"  class="normalInput" placeholder="再次输入密码" /></td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">姓名</td>
					<td><input type="text"  id="name" name="name" class="normalInput" placeholder="请输入姓名"  value="${user.name }"/></td>
					<td class="regTip">* 姓名必是正确、真实的，否则会影响学分</td>
				</tr>
				<tr>
					<td class="textRight">性别</td>
					<td>
						<label><input name="gender" value="0" type="radio" class="xb"  checked/>男</label>　
						<label><input name="gender" value="1" type="radio" class="xb"  ${user.gender==1?"checked":""} />女</label>
					</td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">年龄</td>
					<td><input  id="age" name="age"  type="text" class="normalInput" placeholder="请输入年龄"   value="${user.age }"/></td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">手机号</td>
					<td><input id="mobilenumber" name="mobilenumber"  type="text" class="normalInput" placeholder="请输入手机号"  value="${user.mobilenumber }"/></td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">学历</td>
					<td>
						<label>
		                	<input type="radio" name="idnumber"  checked="checked"  class="register-nian"  value="1"/><i class="register-ji">专科</i>
		                </label>
		               <label>
		                	<input type="radio" name="idnumber" class="register-nian"  value="2" ${user.idnumber==2?"checked":""}/><i class="register-ji">本科</i>
		                </label>
					</td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">所属学校</td>
					<td><input type="text" id="school" name="school" class="normalInput" placeholder="请输入所属学校"  value="${user.school }"/></td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">所属校区</td>
					<td>
						<input type="text"  id="area" name="area"  class="normalInput"  value="${user.area }"  placeholder="请输入所属校区"/>
					</td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">专业</td>
					<td><input id="major" name="major" type="text" class="normalInput" placeholder="请输入专业"  value="${user.major }"/></td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">所在年级</td>
					<td>
						<select name="grade">
							<option value="1"  selected>大一</option>
							<option value="2" ${user.grade==2?"selected":"" }>大二</option>
							<option value="3" ${user.grade==3?"selected":"" }>大三</option>
							<option value="4" ${user.grade==4?"selected":"" }>大四</option>
						</select>
					</td>
					<td></td>
				</tr>
				<tr>
					<td class="textRight">所在班级</td>
					<td><input id="classname" name="classname" type="text" class="normalInput" placeholder="请输入所在班级"  value="${user.classname }"/></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td><a href="javascript:$('form').submit();" class="blueBtn wid110">提交</a><a href="javascript:form.reset();" class="garyBtn wid110">清空</a></td>
					<td></td>
				</tr>
			</table>
			</form>
			<div class="clear"></div>
			
		</div>
	</body>
</html>