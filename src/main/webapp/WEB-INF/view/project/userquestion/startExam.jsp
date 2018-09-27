<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/bootstrap/css/bootstrap.min.css"/>" />
	<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resource/js/bootstrap/js/bootstrap.min.js" />"></script>
	
	<script type="text/javascript">
		var startTime="${userExamination.createTime.time}"- - 0;
		var examinationTimeLength = ${examination.examinationTimeLength };
		var current = new Date().getTime();
		var autoSubmit =false;
		$(function(){
			heartbeat();
			daojishi();
			//打乱答案
			//randAnswer();
			//秒表
			secondClock();
			$(":input").change(function(){
				var inputName=$(this).attr("name");
				$nav = $(".nav_"+inputName);
				if($("[name="+inputName+"]:checked").size()){
					$nav.find("a").addClass("sucessItem");
				}else{
					$nav.find("a").removeClass("sucessItem");
				}
			});
		});
		function randAnswer(){
			$("[headlineAnswerPattern=2] .well").each(function(){
				var leng=$(this).find("li").size();
				for(var i=0;i<5;i++){
					var i1= parseInt(Math.random()*leng);
					var i2= parseInt(Math.random()*leng);
					var temp= $(this).find("li").eq(i1).html();
					 $(this).find("li").eq(i1).html($(this).find("li").eq(i2).html());
					 $(this).find("li").eq(i2).html(temp);
				}
			});
		}
		function secondClock(){
			current+=1000;
			setTimeout(secondClock, 1000);
		}
		//倒计时
		function daojishi(){
			//剩余时间；
			var syTime= examinationTimeLength- parseInt((current-startTime)/60000)-1;
			var sySS = 59-parseInt(((current-startTime)%60000)/1000);
			if(syTime<2){
				$("#timeLength").parent().removeClass().addClass("btn btn-danger btn-xs");
			}else if(syTime<10){
				$("#timeLength").parent().removeClass().addClass("btn btn-warning btn-xs");
			}else if(syTime<30){
				
			}
			if(syTime <0){
				if(!$("#submit").prop("disabled")){
					autoSubmit=true;
					$("#submit").click();
				}
				return;
			}
			
			$("#timeLength").html(syTime);
			$("#timeLengthSS").html(sySS);
			
			setTimeout(daojishi, 100);
		}
		//心跳包
		function heartbeat(){
			$.get("<c:url value="/heartbeat"/>",function(data){
				//修正时间
				current=data.timestamp;
				setTimeout(heartbeat, 600000);
			});
		}
		//答题完毕 提交
		function checkForm(){
			$("#submit").prop("disabled",true);
			if(autoSubmit){
				return true;
			}
			var $navItems =$(".pagination li a:visible");
			if($navItems.size() == $navItems.filter(".sucessItem").size()){
				return true;
			}else{
				if(confirm("你还有"+($navItems.size() - $navItems.filter(".sucessItem").size())+"题未完成，确认交卷吗")){
					return true;
				}else{					
					$navItems.filter(":not(.sucessItem)").get(0).click();
					$("#submit").prop("disabled",false);
					return false;
				}
			}
		};
	</script>
	<style type="text/css">
		label{
			margin-bottom: 0px;
			margin-top: 5px;
		}
		.pagination .sucessItem{
			color: #fff;
			background-color: #5cb85c;
			border-color: #4cae4c;
		}
	</style>
</head>
<body>
<div id="infoBar" style="position: fixed;top:10px;right: 10px;width:320px;" class="alert alert-warning">
	<div><span class="btn btn-success btn-xs">剩余时间：<span id="timeLength" style="font-size: 22px;font-weight: bold;">0</span>&nbsp;分钟&nbsp;<span id="timeLengthSS">0</span>&nbsp;秒</span></div>
<c:forEach var="headlineItem" items="${headline}">
  <div>${headlineItem.headlineHeadlineSubject}</div>
  <div>
	  <ul class="pagination pagination-sm" style="margin:0px;">
	  	<li style="display: none;"><a href="#">&nbsp;</a></li>
	    <c:set var="questionItems" value="question_${headlineItem.headlineId}"></c:set>
		<c:forEach var="questionItem"  varStatus="questionStatus" items="${requestScope[questionItems]}">
			<li class="nav_${questionItem.id}"><a href="#${questionItem.id}">${questionStatus.count}</a></li>
		</c:forEach>
		<li style="display: none;"><a href="#">&nbsp;</a></li>
	  </ul>
  </div>
</c:forEach>
</div>

<form method="post" action="<c:url value="/project/userquestion/save"/>" onsubmit="return checkForm();">
	<nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
	  <div class="container text-right" >
	    <input class="btn btn-success navbar-btn" id="submit" type="submit" value="提交"/>
	    <input class="btn btn-default navbar-btn" onclick="$('#infoBar a').removeClass('sucessItem');" type="reset" value="重置"/>
	  </div>
	</nav>
	<input type="hidden" name="examinationId" value="${examination.examinationId}"/>
	<div  class="page-header">
		<h1 align="center">${examination.examinationSubject}</h1>
		<h3 align="center">${examination.examinationContent}</h3>
	</div>
	<c:forEach var="headlineItem" items="${headline}">
		<div style="margin:0 335px 0px 5px;" headlineAnswerPattern="${headlineItem.headlineAnswerPattern }">
			<div>
				<br/>
				<h2>${headlineItem.headlineHeadlineSubject}</h2>
				<h4>${headlineItem.headlineExaminationContent}</h4>
			</div>
			<div>
				<c:set var="questionItems" value="question_${headlineItem.headlineId}"></c:set>
				<c:forEach var="questionItem"  varStatus="questionStatus" items="${requestScope[questionItems]}">
					<c:set var="ma" value="${questionItem.questionAnswer}"></c:set>
					<c:set var="mqa" value="${questionItem.questionRightAnswer}" ></c:set>
					<c:if test="${headlineItem.headlineQuestionType =='00'}">
					<!-- 判断题 -->
						<div  class="well well-sm">
							<div id="${questionItem.questionId}">
								${questionStatus.count}.&nbsp;&nbsp;${questionItem.questionSubject}
								<label> <input name="${questionItem.questionId}" type="radio" value="0" />错误</label>
								<label> <input name="${questionItem.questionId}" type="radio" value="1" />正确</label>
							</div>
						</div>
					</c:if>
					<c:if test="${headlineItem.headlineQuestionType =='01'}">
					<!-- 单选题 -->
						<div class="well">
							<div  id="${questionItem.questionId}"><h5>${questionStatus.count}. ${questionItem.questionSubject}</h5></div>
							<div>
								<ol  type="A">
									<%
										String[] answers = ((String)pageContext.getAttribute("ma")).split("\\||\r");
										int i=0;
										for(String answer : answers){
											pageContext.setAttribute("answer", answer);
											pageContext.setAttribute("answerIndex", ++i);
											%><li><label><input name="${questionItem.questionId}" value="${answerIndex}" type="radio"/>${answer}</label></li><%
										}
									%>
								</ol>
							</div>
						</div>
					</c:if>
					<c:if test="${headlineItem.headlineQuestionType =='02'}">
					<!-- 多选题 -->
						<div class="well">
							<div  id="${questionItem.questionId}"><h5>${questionStatus.count}. ${questionItem.questionSubject}</h5></div>
							<div>
								<ol  type="A">
									<%
										String[] answers = ((String)pageContext.getAttribute("ma")).split("\\||\r");
										int i=0;
										for(String answer : answers){
											pageContext.setAttribute("answer", answer);
											pageContext.setAttribute("answerIndex", ++i);
											%><li><label><input name="${questionItem.questionId}" value="${answerIndex}" type="checkbox"/>${answer}</label></li><%
										}
									%>
								</ol>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
</form>
<br/>
<br/>
<br/>
</body>
</html>