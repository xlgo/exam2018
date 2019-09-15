<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<title>河南省第二届大学生反邪教警示教育知识竞赛</title>
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.7.2.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/dati.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/gaimi.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/pre-sale.css"/>">
<!--必要样式-->
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style8.css"/>" />

<script type="text/javascript" src="<c:url value="/resource/mobile/js/modernizr.custom.js"/>"></script>
<script type="text/javascript">
		var startTime="${userExamination.createTime.time}"- - 0;
		var examinationTimeLength = ${examination.examinationTimeLength };
		var current = new Date().getTime();
		var autoSubmit =false;
		var info=[{t:new Date().getTime(),c:0}];
		var info2=[{t:new Date().getTime(),c:0}];
		$(function(){
			$("#submitBtn").click(function(){
				$("form").submit();
			});
			heartbeat2();
			daojishi();
			//秒表
			secondClock();
			//cattsel 选中
			$(":input").change(function(){
				var inputName=$(this).attr("name");
				$nav = $("#nav_"+inputName);
				if($("[name="+inputName+"]:checked").size()){
					$nav.addClass("cattsel");
				}else{
					$nav.removeClass("cattsel");
				}
			});
			
		});
		
		function secondClock(){
			current+=1000;
			setTimeout(secondClock, 1000);
		}
		//倒计时
		function daojishi(){
			//debugger;
			//剩余时间；
			var syTime= examinationTimeLength- parseInt((current-startTime)/60000)-1;
			var sySS = 59-parseInt(((current-startTime)%60000)/1000);
			//alert(current)
			
			//alert(startTime)
			if(syTime<2){
				//$("#timeLength").parent().removeClass().addClass("btn btn-danger btn-xs");
			}else if(syTime<10){
				//$("#timeLength").parent().removeClass().addClass("btn btn-warning btn-xs");
			}else if(syTime<30){
				
			}
			if(syTime <0){
				if(!$("#submitBtn").prop("disabled")){
					autoSubmit=true;
					$("#submitBtn").click();
				}
				return;
			}
			
			$("#timeLength").html(syTime);
			$("#timeLengthSS").html(sySS);
			if(info[info.length-1].c!=$(":input:checked").size()){
				if($(":input:checked").size()-info[info.length-1].c>=2){
					info2.push({t:new Date().getTime(),c:$(":input:checked").size()});
				}
				info.push({t:new Date().getTime(),c:$(":input:checked").size()});
			}
			setTimeout(daojishi, 200);
			
		}
		//心跳包
		function heartbeat(){
			$.post("<c:url value="/m/heartbeat"/>",{info:JSON.stringify(info),info2:JSON.stringify(info2)},function(data){
				//修正时间
				current=data.timestamp;
				info=[{t:new Date().getTime(),c:$(":input:checked").size()}];
				info2=[{t:new Date().getTime(),c:$(":input:checked").size()}];
			},"json");
		}
		function heartbeat2(){
			$.post("<c:url value="/m/heartbeat"/>",{info:JSON.stringify(info),info2:JSON.stringify(info2)},function(data){
				//修正时间
				info=[{t:new Date().getTime(),c:$(":input:checked").size()}];
				info2=[{t:new Date().getTime(),c:$(":input:checked").size()}];
				current=data.timestamp;
				setTimeout(heartbeat2, 60000);
			},"json");
		}
		//答题完毕 提交
		function checkForm(){
			$("#submitBtn").prop("disabled",true);
			if(autoSubmit){
				heartbeat();
				return true;
			}
			var $navItems =$(".nav_item");
			if($navItems.size() == $navItems.filter(".cattsel").size()){
				heartbeat();
				return true;
			}else{
				if(confirm("你还有"+($navItems.size() - $navItems.filter(".cattsel").size())+"题未完成，确认交卷吗？")){
					heartbeat();
					return true;
				}else{					
					$navItems.filter(":not(.cattsel)").get(0).click();
					$("#submitBtn").prop("disabled",false);
					return false;
				}
			}
		};
</script>
</head>

<body >

<div class="dati-header">
   <span class="dati-time">剩余时间：<span id="timeLength">0</span>分钟<span id="timeLengthSS">0</span>秒</span>
   <input id="submitBtn" type="button" value="提交试卷" />
</div>
<div class="common-wrapper">
	<form method="post" action="<c:url value="/m/save"/>" onsubmit="return checkForm();">
		<input type="hidden" name="examinationId" value="${examination.examinationId}"/>
		<div>
			<c:forEach var="headlineItem" items="${headline}">
				<p class="panduan">${headlineItem.headlineHeadlineSubject}</p>
				<ul class="panduan-list">
					<c:set var="questionItems" value="question_${headlineItem.headlineId}"></c:set>
					<c:forEach var="questionItem"  varStatus="questionStatus" items="${requestScope[questionItems]}">
						<c:set var="ma" value="${questionItem.questionAnswer}"></c:set>
						<c:set var="mqa" value="${questionItem.questionRightAnswer}" ></c:set>
					<li id="${questionItem.questionId}">
						<c:if test="${headlineItem.headlineQuestionType =='00'}">
							<p>${questionStatus.count}.&nbsp;&nbsp;${questionItem.questionSubject}</p>
							<div class="panduan-xuan">
								<label>
			                    <input type="radio" name="${questionItem.questionId}" value="0" />
			                    错误
			                    </label>
			                </div>
			                <div class="panduan-xuan">
			                    <label>
			                    	<input type="radio" name="${questionItem.questionId}"  value="1" />
			                    	正确
			                    </label>
			                </div>
						</c:if>
						<c:if test="${headlineItem.headlineQuestionType =='01'}">
							<p>${questionStatus.count}.&nbsp;&nbsp;${questionItem.questionSubject}</p>
							<%
								String[] answers = ((String)pageContext.getAttribute("ma")).split("\\||\r");
								int i=0;
								for(String answer : answers){
									pageContext.setAttribute("answer", answer);
									pageContext.setAttribute("answerIndex", ++i);
									%>
									<div class="panduan-xuan">
										<label>
					                    <input type="radio" name="${questionItem.questionId}" value="${answerIndex}" />
					                    ${answer}
					                    </label>
					                </div>
									<%
								}
							%>
						</c:if>
						<c:if test="${headlineItem.headlineQuestionType =='02'}">
							<p>${questionStatus.count}.&nbsp;&nbsp;${questionItem.questionSubject}</p>
							<%
								String[] answers = ((String)pageContext.getAttribute("ma")).split("\\||\r");
								int i=0;
								for(String answer : answers){
									pageContext.setAttribute("answer", answer);
									pageContext.setAttribute("answerIndex", ++i);
									%>
									<div class="panduan-xuan">
										<label>
					                    <input type="checkbox" name="${questionItem.questionId}" value="${answerIndex}" />
					                    ${answer}
					                    </label>
					                </div>
									<%
								}
							%>
						</c:if>
					</li>
					</c:forEach>
				</ul>
			</c:forEach>
	    </div>
    </form>
	<div class="pre-footer">
		<c:forEach var="headlineItem" items="${headline}" >
		<div class="pre-jiaru container">
			<button id="nva_${headlineItem.headlineId }" type="button" class="pre-button-1">${headlineItem.headlineHeadlineSubject }</button>
		</div>  
		</c:forEach>
	</div>

<script type="text/javascript">
$(document).ready(function() {
	$(".overlay-close").click(function(){
		$(".overlay-contentscale").removeClass("open");
	});
	$(".pre-button-1").click(function(){
		$("#item_"+$(this).attr("id")).addClass("open");
	});
});
</script>



<!-- open/close -->
<c:forEach var="headlineItem" items="${headline}" >
<div id="item_nva_${headlineItem.headlineId }" class="overlay overlay-contentscale">
	<div class="pre-close">
        <button type="button" class="overlay-close">Close</button>
    </div>
	<div class="pre-gou-1">
        <div class="pre-gou-2">
        	<span>${headlineItem.headlineHeadlineSubject }</span><i>（蓝色为已答，灰色为未答）</i>
        </div>
    </div>
	<div class="pre-gou-3">
		<ul><li>
		<div class="pre-guige">
		   <div class="catt" >
		   		<c:set var="questionItems" value="question_${headlineItem.headlineId}"></c:set>
				<c:forEach var="questionItem"  varStatus="questionStatus" items="${requestScope[questionItems]}">
					<a id="nav_${questionItem.questionId}"  class="nav_item" href="#${questionItem.questionId}">${questionStatus.count}</a>
				</c:forEach>
		    </div>
		</div>
		</li></ul>
    </div>
</div>
</c:forEach>

<script type="text/javascript" src="<c:url value="/resource/mobile/js/classie.js"/>"></script>
</div>
</body>
</html>
