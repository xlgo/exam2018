<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第五届河南省大学生宗教知识网络竞赛</title>
<!-- head中需要引入的部分 begin -->
<link href="<c:url value="/resource/css/reset.css "/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resource/css/style.css"/>" rel="stylesheet" type="text/css">
<script type="text/javascript">
	var basePath = "<c:url value="/" />";
</script>
<script type="text/javascript" src="<c:url value="/resource/js/jquery/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resource/js/jquery-migrate-1.2.1.min.js" />"></script>
<script src="<c:url value="/resource/js/transit.js" />"></script>
<!--<script src="js/touchSwipe.js"></script>-->
<script src="<c:url value="/resource/js/simpleSlider.js" />"></script>
<script src="<c:url value="/resource/js/backstretch.js" />"></script>
<script src="<c:url value="/resource/js/custom.js" />"></script>
<!-- head中需要引入的部分end -->
</head>
<body>
<!-- 代码 begin -->
<div class='pagewrap'>
  <div class='pageblock pageblock2' id='fullscreen'>
    <div class='slider'>
      <!--<div class='slide' id="first">
        <div class='slidecontent'> <span class="headersur"></span>
          <h1><img src="images/fxj.png" class="fxjImg" /></h1>
          
          <div class="button" onClick="">我要参赛</div>
          <p class="duben"><a href="">反邪教学习读本</a></p>
          <div class="danwei">
          	<p>主办单位：水电费就打发打发斯蒂芬是开始对减肥</p>
          	<p>承办单位：水电费就开始对减肥</p>
          </div>
        </div>
      </div>-->
      <div class='slide'>
        <div class='slidecontent'> <span class="headersur"></span>
          <h1><img src="<c:url value="/resource/images/zongjiao.png" />" class="fxjImg" /></h1>
          <div class="text">
            <p></p>
            <div class="button button_01"  onClick="javascript:location.href='<c:url value="/system/user/registerUser" />'">我要参赛</div>
            
          </div>
        </div>
      </div>
      <!--<div class='slide' id="thirth">
        <div class='slidecontent'> <span class="headersur"></span>
          <h1></h1>
          <div class="text">
            <p>Now it's time to bind the simpleslider on your HTML element. Check the  Github README for an example HTML.</p>
            <xmp>$(document).ready(function(){
              $(".slider").simpleSlider();
              });</xmp>
            <div class="button" onClick="mainslider.nextSlide();">Done? Let's move on again!</div>
          </div>
        </div>
      </div>-->
      <!--<div class='slide' id="fourth">
        <div class='slidecontent'> <span class="headersur">And you're done! Grab a beer and</span>
          <h1>WATCH</h1>
          <div class="text">
            <p>SimpleSlider will now do the rest. Are you looking for more options? Check the Github readme!</p>
            <p>SimpleSlider standard comes with a few methods, triggers and a tracker. These are all easy to configure. More information about them can be found on the Github page<br/>
              Btw, this site has been made using simpleslider! </p>
          </div>
        </div>
      </div>-->
    </div>
  </div>
</div>
<div objid="6034"  class="infor">
    	<div objid="6035"  class="inforContent">
          <div class="foot-left">
            <ul objid="6039" >
                <li>主办单位：中共河南省委高校工委，中共河南省委统战部，河南省教育厅，河南省人民政府宗教事务局，河南省人民政府防范和处理邪教问题办公室</li>
                 <li>承办单位：河南中医药大学</li>
                <li>地址：郑州市郑东新区正光路11号</li>
                 <li>邮政编码：450000</li>

                <li>Copyright 2016 http://kdqc.herycn.com All Rights Reserved </li>
            </ul>
          </div>
          <div class="erweima">
            <img src="<c:url value="/resource/images/erweima.png" />" alt="" />
            <p>关注微信公众号  手机答题更便捷</p>
          </div>
            
        </div>
    </div>
</div>

<!-- 代码 end -->
</body>
</html>