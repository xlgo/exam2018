<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resource/mobile/css/login.css"/>" />
    <title>河南省高校国家安全教育互联网知识竞赛</title>
  </head>
  <body>
  	<!--背景-->
    <div class="bg"></div>
	<div class="loginTop"><img src="<c:url value="/resource/mobile/image/portraitbg.png"/>" class="topImg" /></div>
	<div class="hint">
		填写信息快速登录
	</div>
    <div class="logincontent">
		<div class="forms">
    		<div class="inputs">
			  <input type="text" name="name" class="form-control name" placeholder="请输入本人真实姓名">
			</div>
			<div class="inputs">
			  <input type="tel" name="mobilenumber" class="form-control phone" placeholder="请输入手机号">
			</div>
			<div class="inputs" style="position: relative;">
				  <input disabled type="text" class="form-control xb" placeholder="选择性别" />
				  <input type="hidden" name="gender" />
				  <img class="down" chioceType='sex' src="<c:url value="/resource/mobile/image/down.png"/>"/>
				  <div class="sliding none sliding_sex" >
				  	
				  	<div class="sliding-down">
				  		<div class="sliding-left">
					  		<ul class="left-list">
					  			<li value="1">男</li>
					  			<li value="2">女</li>
							</ul>
					  	</div>
					</div>
				  </div>
			</div>
			
			<div class="inputs" style="position: relative;">
				  <input disabled type="text" class="form-control leibie" placeholder="院校类别" />
				  <input type="hidden" name="identity" />
				  <img class="down" chioceType='schoolType' src="<c:url value="/resource/mobile/image/down.png"/>"/>
				  <div class="sliding none sliding_schoolType" >
				  	
				  	<div class="sliding-down">
				  		<div class="sliding-left">
					  		<ul class="left-list identity-ul">
					  			<li value="1">专科</li>
					  			<li value="2">本科</li>
							</ul>
					  	</div>
					</div>
				  </div>
			</div>
			<div class="inputs" style="position: relative;">
			  <input type="text" class="form-control school" placeholder="所在学校">
			  <input type="hidden" name="school" />
			  <img class="down" chioceType='schoolName' src="<c:url value="/resource/mobile/image/down.png"/>"/>
			  <div class="sliding none sliding_schoolName" >
			  	
			  	<div class="sliding-down">
			  		<div class="sliding-left">
				  		<ul class="left-list school-ul">
				  			
				  		</ul>
				  	</div>
				</div>
			  </div>
			</div>
			<div class="inputs" style="position: relative;">
			  <input type="text" name="area" class="form-control xiaoqu" placeholder="学号">
			</div>
			<div class="inputs">
			  <input type="text" name="major" class="form-control zhuanye" placeholder="请输入专业">
			</div>
			<div class="inputs" style="position: relative;">
			  <input disabled type="text" class="form-control nianji" placeholder="所在年级">
			  <input type="hidden" name="grade" /> 
			  <img class="down" chioceType='grade' src="<c:url value="/resource/mobile/image/down.png"/>"/>
			  <div class="sliding none sliding_grade" >
			  	
			  	<div class="sliding-down">
			  		<div class="sliding-left">
				  		<ul class="left-list">
				  			<li value="1">大一</li>
				  			<li value="2">大二</li>
				  			<li value="3">大三</li>
				  			<li value="4">大四</li>
				  			<li value="5">研究生</li>
				  		</ul>
				  	</div>
				</div>
			  </div>
			  </div>
			<div class="inputs">
			  <input type="text" name="classname" class="form-control banji" placeholder="请输入班级">
			</div>
			<div class="inputs" style="text-align: right;">
			  <label class="checkbox-inline">
			  </label>
			</div>
    	</div>
    	<div class="login">
    		<button type="button" class="btn btn-info btn-lg loginbtn">立即登录</button>
    	</div>
    </div>
  </body>
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  	<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script>
    	$(function(){
    		var $scc = $(".school-ul");
    		$.get("<c:url value="/resource/data/school.json" />",function(data){
    			$.each(data,function(k,v){
    				$.each(v.split(","),function(ik,iv){
    					$scc.append($("<li>").attr({ptype:k,value:iv}).html(iv));
    				});
    			})
    		})
    		$(".down").click(function(){
				var chioceType = $(this).attr("chioceType");
    			if($(".sliding_" + chioceType).css("display")==="none"){
    				$(".sliding_" + chioceType).css("display","block");
					$(this).prev().addClass("clicks");
    			}else{
    				$(".sliding_" + chioceType).css("display","none");
					$(this).prev().removeClass("clicks");
    			}
    		})
			$("ul").on("click","li",function(){
				var val = $(this).attr("value");
				var txt = $(this).text();
				$(this).parents(".inputs").find("input:eq(0)").val(txt);
				$(this).parents(".inputs").find("input:eq(1)").val(val);
				$(this).parents(".sliding").hide();
				$(this).parents(".sliding").prev().prev().removeClass("clicks")
			});
    		
			$(".identity-ul>li").bind("click",function(){
				$scc.find("li").hide();
				if($(".school").val()){
					$scc.find("[ptype="+$(this).val()+"][value*="+$(".school").val()+"]").show();
				}else{
					$scc.find("[ptype="+$(this).val()+"]").show();
				}
    		})
    		$(".school").bind("change",function(){
    			$scc.find("li").hide();
    			if($("[name=identity]").val()){
					$scc.find("[ptype="+$("[name=identity]").val()+"][value*="+$(".school").val()+"]").show();
    			}else{
    				$scc.find("[value*="+$(".school").val()+"]").show();
    			}
    		})
    		var submiting=false;
    		$(".loginbtn").click(function(){
    			if(!submiting){
	    			submiting=true;
	    			var submitData = {};
		    		var hasSubmit=true;
	    			$.each($(":input").serializeArray(),function(i,m){
	    				if(m.value){
	    					submitData[m.name] = m.value;
	    				}else{
	    					hasSubmit=false;
	    					alert("请将信息填写完整!");
	    					return false;
	    				}
	    			});
	    			if(!hasSubmit){
	    				submiting=false;
	    				return;
	    			}
	    			$.post("reginfoSubmit",submitData,function(data){
		    			submiting=false;
		    			location.reload();
	    			});
    			}
    		});
    	})
    </script>
</html>