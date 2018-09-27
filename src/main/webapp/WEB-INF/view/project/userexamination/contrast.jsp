<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>错题对比</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/bootstrap/css/bootstrap.min.css"/>" />
	<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.11.1.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resource/js/bootstrap/js/bootstrap.min.js" />"></script>
	<script type="text/javascript">
		$(function(){
			$(":input").prop("disabled",true);
			$(".userAnswer").each(function(){
				$(this).html($(this).html().replace(/1/,"A").replace(/2/,"B").replace(/3/,"C").replace(/4/,"D").replace(/5/,"F").replace(/6/,"G").replace(/7/,"H").replace(/8/,"I").replace(/9/,"J").replace(/\|/g,"、"))
			});
			$("[isRight]").each(function(){
				var $input = $("[name="+$(this).attr("id")+"]");
				$.each($(this).attr("userAnswer").split("|"),function(i,o){
					if(o)
					$input.filter("[value="+o+"]").prop("checked",true);
				});
				var $navItem = $(".nav_"+$(this).attr("id"));
				if($(this).attr("isRight")=="true"){
					$navItem.find("a").addClass("sucessItem");
				}else{
					$navItem.find("a").addClass("failItem");
				}
			});
		});
		
	</script>
	<style type="text/css">
		label{
			margin-bottom: 0px;
			margin-top: 5px;
		}
		.pagination .sucessItem{
			color: #fff;
			background-color: #5cb85c;
		}
		.pagination .failItem{
			color: #fff;
			background-color: #d9534f;
		}
		
		.bg-danger {
			background-color: #f2dede;
		}
		.bg-success{
			background-color: #dff0d8;
		}
	</style>
</head>
<body>
<div style="position: fixed;bottom:10px;right: 10px;width:320px;" class="alert alert-warning">
<c:forEach var="headlineItem" items="${headline}">
  <div>${headlineItem.headlineHeadlineSubject}</div>
  <div>
	  <ul class="pagination pagination-sm" style="margin:0px;">
	  	<li style="display: none;"><a href="#">&nbsp;</a></li>
	    <c:set var="userQuestionItems" value="userQuestion_${headlineItem.headlineId}"></c:set>
		<c:forEach var="userQuestionItem"  varStatus="userQuestionStatus" items="${requestScope[userQuestionItems]}">
			<li class="nav_${userQuestionItem.userQuestionQuestionId}"><a href="#${userQuestionItem.userQuestionQuestionId}">${userQuestionStatus.count}</a></li>
		</c:forEach>
		<li style="display: none;"><a href="#">&nbsp;</a></li>
	  </ul>
  </div>
</c:forEach>
</div>

<form method="post" action="<c:url value="/project/userquestion/save"/>" onsubmit="return checkForm();">
	<input type="hidden" name="examinationId" value="${examination.examinationId}"/>
	<c:if test="${empty error }">
		<div class="jumbotron">
			<div class="container">
			  <h1>错题对比</h1>
			  <p>可以通过右边导航快速定位到错误的位置进行查看，其中<span class="label label-success">题号</span>表示答题正确，<span class="label label-danger">题号</span>表示答题错误</p>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty error }">
		<div class="jumbotron">
			<div class="container">
			  <h1>${errorTitle }</h1>
			  <p>${error }</p>
			</div>
		</div>
	</c:if>
	<c:forEach var="headlineItem" items="${headline}">
		<div>
			<div>
				<br/>
				<h2>${headlineItem.headlineHeadlineSubject}</h2>
				<h4>${headlineItem.headlineExaminationContent}</h4>
			</div>
			<div>
				<c:set var="userQuestionItems" value="userQuestion_${headlineItem.headlineId}"></c:set>
				<c:forEach var="userQuestionItem"  varStatus="userQuestionStatus" items="${requestScope[userQuestionItems]}">
					<c:set var="ma" value="${questions[userQuestionItem.userQuestionQuestionId].questionAnswer}"></c:set>
					<c:set var="mqa" value="${questionItem.questionRightAnswer}" ></c:set>
					<c:if test="${headlineItem.headlineQuestionType =='00'}">
					<!-- 判断题 -->
						<div  class="well well-sm ${userQuestionItem.userQuestionRightAnswer!=userQuestionItem.userQuestionUserAnswer?'bg-danger':'bg-success'}">
							<div userAnswer="${userQuestionItem.userQuestionUserAnswer}" isRight="${userQuestionItem.userQuestionRightAnswer==userQuestionItem.userQuestionUserAnswer}" id="${userQuestionItem.userQuestionQuestionId}" >
								${userQuestionStatus.count}.&nbsp;&nbsp;${questions[userQuestionItem.userQuestionQuestionId].questionSubject}
								<label> <input name="${userQuestionItem.userQuestionQuestionId}" type="radio" value="1" />错误</label>
								<label> <input name="${userQuestionItem.userQuestionQuestionId}" type="radio" value="0" />正确</label>
							</div>
							
						</div>
					</c:if>
					<c:if test="${headlineItem.headlineQuestionType =='01'}">
					<!-- 单选题 -->
						<div class="well ${userQuestionItem.userQuestionRightAnswer!=userQuestionItem.userQuestionUserAnswer?'bg-danger':'bg-success'}">
							<div  userAnswer="${userQuestionItem.userQuestionUserAnswer}" isRight="${userQuestionItem.userQuestionRightAnswer==userQuestionItem.userQuestionUserAnswer}"  id="${userQuestionItem.userQuestionQuestionId}"><h5>${userQuestionStatus.count}. ${questions[userQuestionItem.userQuestionQuestionId].questionSubject}</h5></div>
							<div>
								<ol  type="A">
									<%
										String[] answers = ((String)pageContext.getAttribute("ma")).split("\\||\r");
										int i=0;
										for(String answer : answers){
											pageContext.setAttribute("answer", answer);
											pageContext.setAttribute("answerIndex", ++i);
											%><li><label><input name="${userQuestionItem.userQuestionQuestionId}" value="${answerIndex}" type="radio"/>${answer}</label></li><%
										}
									%>
								</ol>
							</div>
							<c:if test="${userQuestionItem.userQuestionRightAnswer!=userQuestionItem.userQuestionUserAnswer}">
								<a class="btn btn-danger">正确答案:<span class="userAnswer">${userQuestionItem.userQuestionRightAnswer}</span></a>
							</c:if>
							<!-- 
							<a class="btn btn-success">正确答案:${userQuestionItem.userQuestionRightAnswer}</a>
							<a class="btn btn btn-danger">用户答案:${userQuestionItem.userQuestionUserAnswer}</a>
							 -->
						</div>
					</c:if>
					<c:if test="${headlineItem.headlineQuestionType =='02'}">
					<!-- 多选题 -->
						<div class="well ${userQuestionItem.userQuestionRightAnswer!=userQuestionItem.userQuestionUserAnswer?'bg-danger':'bg-success'}">
							<div userAnswer="${userQuestionItem.userQuestionUserAnswer}" isRight="${userQuestionItem.userQuestionRightAnswer==userQuestionItem.userQuestionUserAnswer}" id="${userQuestionItem.userQuestionQuestionId}"><h5>${userQuestionStatus.count}. ${questions[userQuestionItem.userQuestionQuestionId].questionSubject}</h5></div>
							<div>
								<ol  type="A">
									<%
										String[] answers = ((String)pageContext.getAttribute("ma")).split("\\||\r");
										int i=0;
										for(String answer : answers){
											pageContext.setAttribute("answer", answer);
											pageContext.setAttribute("answerIndex", ++i);
											%><li><label><input name="${userQuestionItem.userQuestionQuestionId}" value="${answerIndex}" type="checkbox"/>${answer}</label></li><%
										}
									%>
								</ol>
							</div>
							<c:if test="${userQuestionItem.userQuestionRightAnswer!=userQuestionItem.userQuestionUserAnswer}">
								<a class="btn btn-danger">正确答案:<span class="userAnswer">${userQuestionItem.userQuestionRightAnswer}</span></a>
							</c:if>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
</form>
</body>
</html>