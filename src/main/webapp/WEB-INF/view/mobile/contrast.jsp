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
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/tishi.css"/>">
<!--必要样式-->
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/mobile/css/style8.css"/>" />

<script type="text/javascript" src="<c:url value="/resource/mobile/js/modernizr.custom.js"/>"></script>
<script type="text/javascript">
		$(function(){
			$("form :input").prop("disabled",true);
			$(".userAnswer").each(function(){
				$(this).html($(this).html().replace(/\|/g,"、"))
			});
			$("[isRight]").each(function(){
				var $input = $("[name="+$(this).attr("id")+"]");
				$.each($(this).attr("userAnswer").split("|"),function(i,o){
					if(o)
					$input.filter("[value="+o+"]").prop("checked",true);
				});
				var $navItem = $("#nav_"+$(this).attr("id"));
				if($(this).attr("isRight")=="true"){
					$navItem.addClass("sucessItem");
				}else{
					$navItem.addClass("failItem");
				}
			});
		});
</script>
<style type="text/css">
	.tishi-head{
		background: transparent;
	}
	.overlay-contentscale .sucessItem{
		color: #fff;
		background-color: #5cb85c;
	}
	.overlay-contentscale .failItem{
		color: #fff;
		background-color: #d9534f;
	}
	
	.panduan-list .bg-danger {
		background-color: #f2dede;
	}
	.panduan-list .bg-success{
		background-color: #dff0d8;
	}
	.userAnswer{
		display:inline-block;
		color: #fff;
	    background-color: #d9534f;
	    border-color: #d43f3a;
	    padding:3px;
	    margin:5px;
    }
    .dati-header{
    	font-size: 16px;
    	font-weight: bold;
    	text-align: center;
    }
</style>
</head>

<body >

<div class="dati-header">
    <div class="tishi-head">
        <div class="header-close">
            <a href="<c:url value="/"/>">
                <img src="<c:url value="/resource/mobile/image/cross.png"/>">
            </a>
        </div>
        <div class="tishi-title">错题对比</div> 
    </div>
</div>
<div class="common-wrapper">
	<form method="post" action="<c:url value="/m/save"/>" onsubmit="return checkForm();">
		<div>
			<c:forEach var="headlineItem" items="${headline}">
				<p class="panduan">${headlineItem.headlineHeadlineSubject}</p>
				<ul class="panduan-list">
					<c:set var="userQuestionItems" value="userQuestion_${headlineItem.headlineId}"></c:set>
					<c:forEach var="userQuestionItem"  varStatus="userQuestionStatus" items="${requestScope[userQuestionItems]}">
						<c:set var="ma" value="${questions[userQuestionItem.userQuestionQuestionId].questionAnswer}"></c:set>
						<c:set var="mqa" value="${questionItem.questionRightAnswer}" ></c:set>
					<li id="${userQuestionItem.userQuestionQuestionId}"  class="${userQuestionItem.userQuestionRightAnswer!=userQuestionItem.userQuestionUserAnswer?'bg-danger':'bg-success'}" userAnswer="${userQuestionItem.userQuestionUserAnswer}" isRight="${userQuestionItem.userQuestionRightAnswer==userQuestionItem.userQuestionUserAnswer}" >
						<c:if test="${headlineItem.headlineQuestionType =='00'}">
							<p>${userQuestionStatus.count}.&nbsp;&nbsp;${questions[userQuestionItem.userQuestionQuestionId].questionSubject}</p>
							<div class="panduan-xuan">
								<label>
			                    <input type="radio" name="${userQuestionItem.userQuestionQuestionId}" value="0" />
			                    错误
			                    </label>
			                </div>
			                <div class="panduan-xuan">
			                    <label>
			                    	<input type="radio" name="${userQuestionItem.userQuestionQuestionId}"  value="1" />
			                    	正确
			                    </label>
			                </div>
						</c:if>
						<c:if test="${headlineItem.headlineQuestionType =='01'}">
							<p>${userQuestionStatus.count}.&nbsp;&nbsp;${questions[userQuestionItem.userQuestionQuestionId].questionSubject}</p>
							<%
								String[] answers = ((String)pageContext.getAttribute("ma")).split("\\||\r");
								int i=0;
								for(String answer : answers){
									pageContext.setAttribute("answer", answer);
									pageContext.setAttribute("answerIndex", ++i);
									%>
									<div class="panduan-xuan">
										<label>
					                    <input type="radio" name="${userQuestionItem.userQuestionQuestionId}" value="${answerIndex}" />
					                    ${answer}
					                    </label>
					                </div>
									<%
								}
							%>
							<c:if test="${userQuestionItem.userQuestionRightAnswer!=userQuestionItem.userQuestionUserAnswer}">
								<div style="clear: both;" class="btn btn-danger"><span class="userAnswer">正确答案:${userQuestionItem.userQuestionRightAnswer}</span></div>
							</c:if>
						</c:if>
						<c:if test="${headlineItem.headlineQuestionType =='02'}">
							<p>${userQuestionStatus.count}.&nbsp;&nbsp;${questions[userQuestionItem.userQuestionQuestionId].questionSubject}</p>
							<%
								String[] answers = ((String)pageContext.getAttribute("ma")).split("\\||\r");
								int i=0;
								for(String answer : answers){
									pageContext.setAttribute("answer", answer);
									pageContext.setAttribute("answerIndex", ++i);
									%>
									<div class="panduan-xuan">
										<label>
					                    <input type="checkbox" name="${userQuestionItem.userQuestionQuestionId}" value="${answerIndex}"  />
					                    ${answer}
					                    </label>
					                </div>
									<%
								}
							%>
							<c:if test="${userQuestionItem.userQuestionRightAnswer!=userQuestionItem.userQuestionUserAnswer}">
								<div style="clear: both;" class="btn btn-danger"><span class="userAnswer">正确答案:${userQuestionItem.userQuestionRightAnswer}</span></div>
							</c:if>
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
        	<span>${headlineItem.headlineHeadlineSubject }</span><i>（红色为错误，绿色为正确）</i>
        </div>
    </div>
	<div class="pre-gou-3">
		<ul><li>
		<div class="pre-guige">
		   <div class="catt" >
		   		<c:set var="userQuestionItems" value="userQuestion_${headlineItem.headlineId}"></c:set>
				<c:forEach var="userQuestionItem"  varStatus="userQuestionStatus" items="${requestScope[userQuestionItems]}">
					<a id="nav_${userQuestionItem.userQuestionQuestionId}"  class="nav_item" href="#${userQuestionItem.userQuestionQuestionId}">${userQuestionStatus.count}</a>
				</c:forEach>
		    </div>
		</div>
		</li></ul>
    </div>
</div>
</c:forEach>

<script type="text/javascript" src="<c:url value="/resource/mobile/js/classie.js"/>"></script>
</div>
<br/>
<br/>
<br/>
</body>
</html>
