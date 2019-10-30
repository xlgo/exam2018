<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>河南省高校国家安全教育互联网知识竞赛</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/gaimi.css"/>">

<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/jquery-ui/jquery-ui.min.css"/>" />

<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.7.2.js" />"></script>
<script type="text/javascript" src="<c:url value="/resource/js/jquery-ui/jquery-ui.min.js" />"></script>
<script type="text/javascript">
	var schoolInfo="";
	$(function(){
		if("${errorMsg}"){
			alert("${errorMsg}");
		}
		
		$(".login-zhuce").click(function(){
			$("form").get(0).reset();
		});
		$(".login-tijiao").click(function(){
			$("form").submit();
		}); 
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
	})
	
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
		
		if(!($("[name=mobilenumber]").val().trim().length==11)){
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
</head>

<body >
<form method="post" action="<c:url value="/m/saveRegister"/>" onsubmit="return checkForm();">
<div class="gaimi-header">
    <div class="header-back">
        <a href="javascript:history.back()">
            <img src="<c:url value="/resource/mobile/image/arrow.png"/>">
        </a>
    </div>
    <div class="gaimi-title">注册</div> 
</div>
<div class="gaimi-index">
    <p class="register-tishi">请如实填写下面的信息，在每期网络知识竞赛后对于获奖者需要进行个人信息核实。信息一经提交，不允许修改！</p>
    <ul>
        <li><span>用户名：</span><input type="text"  id="username" name="username"  value="${user.username }" placeholder="请输入用户名"/></li>
        <li><span>密码：</span><input type="password" id="password" name="password"  value="${user.password }"  placeholder="请输入密码"/></li>
        <li><span>确认密码：</span><input type="password" id="password2" name="password2"  value="${user.password }"  placeholder="再次输入密码"/></li>
        <li><span>姓名：</span><input type="text" id="name" name="name"  value="${user.name }"  placeholder="请输入姓名"/></li>
        <li><span>性别：</span>
        	<label>
            	<input type="radio" name="gender" value="0" checked="checked"  ${empty user.gender or user.gender==0?"checked":""} class="register-xuan" /><i class="register-sex">男</i>
            </label>
            <label>
            	<input type="radio"  name="gender" value="1"  ${user.gender==1?"checked":""} class="register-xuan"/><i class="register-sex">女</i>
            </label>
            <div class="clear"></div>
        </li>
        <li><span>年龄：</span><input type="text"  id="age" name="age"  value="${user.age }"  placeholder="请输入年龄"/></li>
        <li><span>手机号：</span><input type="text"   id="mobilenumber" name="mobilenumber"  value="${user.mobilenumber }"  placeholder="请输入手机号"/></li>
        
        <li><span>学历：</span>
            <div class="register-nian-right">
                <label>
                	<input type="radio" name="idnumber"  checked="checked"  class="register-nian"  value="1"/><i class="register-ji">专科</i>
                </label>
               <label>
                	<input type="radio" name="idnumber" class="register-nian"  value="2" ${user.idnumber==2?"checked":""}/><i class="register-ji">本科</i>
                </label>
            </div>
            <div class="clear"></div>
        </li>
        
        
        <li><span>所属学校：</span><input type="text"  id="school" name="school"  value="${user.school }"  placeholder="请输入所属学校"/></li>
        
        <li><span>所属院校：</span><input type="text"  id="area" name="area"  value="${user.area }"  placeholder="请输入所属院校"/></li>
        <li><span>专业：</span><input type="text" id="major"  name="major"  value="${user.major }"  placeholder="请输入您的专业" /></li>
        <li><span>所在年级：</span>
            <div class="register-nian-right">
                <label>
                	<input type="radio" name="grade"  checked="checked"  class="register-nian"  value="1"/><i class="register-ji">大一</i>
                </label>
               <label>
                	<input type="radio" name="grade" class="register-nian"  value="2" ${user.grade==2?"checked":""}/><i class="register-ji">大二</i>
                </label><br/>
                <label>
                	<input type="radio" name="grade" class="register-nian"  value="3" ${user.grade==3?"checked":""}/><i class="register-ji">大三</i>
                </label>
                <label>
                	<input type="radio" name="grade" class="register-nian"  value="4" ${user.grade==4?"checked":""}/><i class="register-ji">大四</i>
                </label>
                <label>
                	<input type="radio" name="grade" class="register-nian"  value="5" ${user.grade==5?"checked":""}/><i class="register-ji">其它</i>
                </label>
            </div>
            <div class="clear"></div>
        </li>
        <li><span>所在班级：</span><input type="text"  name="classname"  value="${user.classname }"  placeholder="请输入所在班级"/></li>
    </ul>
</div>
<div class="login-tijiao gaitop">提交</div>
<div class="login-zhuce ">清空</div>

</form>
</body>
</html>
