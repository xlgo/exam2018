<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!doctype html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resource/mobile/css/index.css"/>" />
    <title>河南省高校国家安全教育互联网知识竞赛</title>
  </head>
  <body>
  	<!--背景-->
    <div class="bg"></div>
	<div class="message">
	   		<img class="portraitbg" src="../image/portraitbg.png"/>
	   		<div class="portraitwrap">
    			<img src="../image/portrait.png" class="portrait">
    		</div>
	   		<div class="integral">
	   			欢迎张晓明参与竞赛
	   		</div>
	   	</div>
    <div class="content">
    	
    	<div class="options">
    		<div class="littleoptions">
    			<img src="../image/optionbg.png" class="optionbg"/>
    			<div class="optioncontent">
    				<img src="../image/competition.png" class="optionicon" />
    				<div class="optioncontenttext">
    					<div class="texts">
    						答题竞赛
    					</div>
    					<div>
    						<button type="button" class="btn btn-default btn-xs">开始答题</button>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="littleoptions">
    			<img src="../image/optionbg.png" class="optionbg"/>
    			<div class="optioncontent">
    				<img src="../image/rule.png" class="optionicon" />
    				<div class="optioncontenttext">
    					<div class="texts">
    						竞赛规则
    					</div>
    					<div>
    						<button type="button" class="btn btn-default btn-xs">点击查看</button>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="littleoptions" style="display: none;">
    			<img src="../image/optionbg.png" class="optionbg"/>
    			<div class="optioncontent">
    				<img src="../image/statistics.png" class="optionicon" />
    				<div class="optioncontenttext">
    					<div class="texts">
    						竞赛统计
    					</div>
    					<div>
    						<button type="button" class="btn btn-default btn-xs">点击查看</button>
    					</div>
    				</div>
    			</div>
    		</div>
    		<div class="littleoptions">
    			<img src="../image/optionbg.png" class="optionbg"/>
    			<div class="optioncontent">
    				<img src="../image/warehouse.png" class="optionicon" />
    				<div class="optioncontenttext">
    					<div class="texts">
    						竞赛成绩查询
    					</div>
    					<div>
    						<button type="button" class="btn btn-default btn-xs">立即查看</button>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
    </div>
  </body>
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</html>