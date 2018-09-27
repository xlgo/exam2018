<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<shiro:lacksPermission name="sys">
	<jsp:forward page="/index.jsp"></jsp:forward>
</shiro:lacksPermission>
<!DOCTYPE HTML >
<html>
<head>
<meta charset="utf-8" />
<title>管理员首页</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/dwz/themes/silver/style.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/dwz/themes/css/core.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/uploadify/css/uploadify.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/base.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/main/admin.css"/>" />
<!--[if IE]>
<link rel="stylesheet" type="text/css" href="<c:url value="/resource/js/dwz/themes/css/ieHack.css"/>" />
<![endif]-->
<!--[if lte IE 9]>
<script type="text/javascript" src="<c:url value="/resource/js/dwz/js/speedup.js" />"></script>
<![endif]-->
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.7.2.js" />"></script>
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery.cookie.js" />"></script>
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery.validate.js" />"></script>
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery.bgiframe.js" />"></script>

<script type="text/javascript" src="<c:url value="/resource/js/xheditor/xheditor-1.2.1.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resource/js/xheditor/xheditor_lang/zh-cn.js" />"></script>

<script type="text/javascript" src="<c:url value="/resource/js/uploadify/scripts/jquery.uploadify.min.js" />"></script>

<script type="text/javascript" src="<c:url value="/resource/js/dwz/js/dwz.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resource/js/dwz/js/dwz.regional.zh.js" />"></script>

<script type="text/javascript">
	$(function() {
		DWZ.init("<c:url value='/resource/js/dwz/dwz.frag.xml'/>", {
			loginUrl : "<c:url value='/login' />", // 跳到登录页面
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "pageSize",
				orderField : "sortField",
				orderDirection : "sortOrder"
			},
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
			}
		});
	});
</script>
</head>

<body>
	<div id="layout">
		<div id="header">
			<div class="logo1">
				<span><a target="dialog" width="510" height="200"  mask=true href="<c:url value='/system/user/changepasswordDialog'/>">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="<c:url value='/logout'/>">注销</a></span>
			</div>
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>竞赛系统</h2>
					<div>收缩</div>
				</div>

				<div class="accordion" fillSpace="sidebar">
				<%--
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>考试信息
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="<c:url value="/project/userquestion/startExam?navTabId=startExam"/>" target="navTab" rel="startExam" external="true">开始考试</a></li>
							<li><a href="<c:url value="/"/>" target="navTab" rel="resultInfo">错题答案对比</a></li>
							<li><a href="<c:url value="/"/>" target="navTab" rel="ranking">查看排名</a></li>
							<li><a href="<c:url value="/"/>" target="navTab" rel="ranking">个人信息</a></li>
						</ul>
					</div>
				 --%>
					
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>系统管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="<c:url value="/system/user/list?navTabId=userManage"/>" target="navTab" rel="userManage">用户管理</a></li>
							<li><a href="<c:url value="/system/config/edit?id=1&navTabId=systemConfig"/>" target="navTab" rel="systemConfig">错题对比设置</a></li>
							<li><a href="<c:url value="/system/config/choujiang?navTabId=choujiang"/>" target="navTab" rel="choujiang">抽奖设置</a></li>
							<li>
								<a>试卷管理</a>
								<ul>
									<li><a href="<c:url value="/project/examination/list?navTabId=examinationManage"/>" target="navTab" rel="examinationManage">试卷维护</a></li>
									<li><a href="<c:url value="/project/headline/list?navTabId=headlineManage"/>" target="navTab" rel="headlineManage">试卷大题维护</a></li>
								</ul>
							</li>
							<shiro:hasPermission name="admin">
								<li><a href="<c:url value="/project/question/list?navTabId=questionManage"/>" target="navTab" rel="questionManage">题库管理</a></li>
							</shiro:hasPermission>
							<li><a href="<c:url value="/project/userexamination/award?navTabId=acquireAward"/>" target="navTab" rel="acquireAward">奖品领取</a></li>
							<li><a href="<c:url value="/system/config/tools?id=1&navTabId=tools"/>" target="navTab" rel="tools">系统工具</a></li>
						</ul>
					</div>
					
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>系统分析
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="<c:url value="/project/userquestion/errorSort"/>" target="navTab" rel="errorSort">前十答错排名</a></li>
							<li><a href="<c:url value="/project/userquestion/rightSort"/>" target="navTab" rel="rightSort">前十答对排名</a></li>
							<li><a href="<c:url value="/project/userexamination/enterCount"/>" target="navTab" rel="ranking">参赛总数统计</a></li>
							<li><a href="<c:url value="/project/userexamination/schoolCount"/>" target="navTab" rel="schoolCount">学校统计</a></li>
							<li><a href="<c:url value="/project/userexamination/scoreCount"/>" target="navTab" rel="scoreCount">分数排行榜</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<p><h1 style="line-height:70px;font-size:44px;">欢迎使用大学生宗教知识网络竞赛系统</h1><p>
					<p><ol >
						<li style="line-height: 30px;font-size:16px;">1.首选创建一套试卷</li>
						<li style="line-height: 30px;font-size:16px;">2.然后对大题进行维护</li>
						<li style="line-height: 30px;font-size:16px;">3.等待竞赛结束后就可以查看排名了</li>
					</ol><p>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div id="footer"><%@ include file="/WEB-INF/view/common/bottom.jsp"%></div>
</body>
</html>
