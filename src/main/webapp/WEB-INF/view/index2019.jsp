<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
<title>河南省高校国家安全教育互联网知识竞赛</title>
<link href="<c:url value="/resource/css/reset.css"/>" type="text/css" rel="stylesheet" />
<link href="<c:url value="/resource/css/style.css"/>" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="https://cdn.staticfile.org/jquery/1.3.1/jquery.js"></script>
<script type="text/javascript" src="<c:url value="/resource/css/js/jquery.timer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resource/css/js/dynamic.js"/>"></script>

</head>

<body>
<div id="wrap">
	
	<div class="banner">
    	<p><a href="javascript:void(0)" class="btn banner-btn" index="0">大赛简介</a><a href="javascript:void(0)" class="btn banner-btn" index="1">我要参赛</a><a href="javascript:void(0)" class="btn banner-btn" index="2">学习资料</a></p>
    </div>
    <div class="content">
        <div class="mainBody">
			<div class="tab1 tabContent">
				<h3>大赛简介</h3>
				<div class="schedule">
					<p>当一个表格数据做的太多,需要往下或往右滚动翻看的时候,就无法看到表头所表示的内容,有的人会单独冻结某一行,也有的会冻结某一列,但如何同时冻结一行一列或多行当一个表格数据做的太多,需要往下或往右滚动翻看的时候,就无法看到表头所表示的内容,有的人会单独冻结某一行,也有的会冻结某一列,但如何同时冻结一行一列或多行当一个表格数据做的太多,需要往下或往右滚动翻看的时候,就无法看到表头所表示的内容,有的人会单独冻结某一行,也有的会冻结某一列,但如何同时冻结一行一列或多行当一个表格数据做的太多,需要往下或往右滚动翻看的时候,就无法看到表头所表示的内容,有的人会单独冻结某一行,也有的会冻结某一列,但如何同时冻结一行一列或多行</p>
				</div>
			</div>
			<div class="tab2 competition hide tabContent" >
				<img src="<c:url value="/resource/images/ewm.jpg"/>" />
				<p>请扫描二维码参与竞赛</p>
			</div>
			<div class="tab3 hide tabContent">
				<h3>学习资料</h3>
				<div class="schedule fb">
					<p>当一个表格数据做的太多,需要往下或往右滚动翻看的时候,就无法看到表头所表示的内容,有的人会单独冻结某一行,也有的会冻结某一列,但如何同时冻结一行一列或多行当一个表格数据做的太多,需要往下或往右滚动翻看的时候,就无法看到表头所表示的内容,有的人会单独冻结某一行,也有的会冻结某一列,但如何同时冻结一行一列或多行当一个表格数据做的太多,需要往下或往右滚动翻看的时候,就无法看到表头所表示的内容,有的人会单独冻结某一行,也有的会冻结某一列,但如何同时冻结一行一列或多行当一个表格数据做的太多,需要往下或往右滚动翻看的时候,就无法看到表头所表示的内容,有的人会单独冻结某一行,也有的会冻结某一列,但如何同时冻结一行一列或多行</p>
				</div>
			</div>
        </div>
    </div>
    <div class="footer">版权所有：河南省教育厅 地址：郑州市郑东新区正光路11号 邮政编码：450018 豫ICP备0911211号<br />
Copyright 2014 www.haedu.gov.cn All Rights Reserved</div>
</div>
</body>
<script>
	$(function(){
		$('.banner-btn').click(function(){
			var _index = $(this).attr("index");
			_index = parseInt(_index);
			$(".tabContent").eq(_index).show().siblings().hide();
		});
	
	})
</script
</html>
