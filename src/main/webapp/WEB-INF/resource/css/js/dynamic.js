$(function(){
   $('#timer').timer({format: "今日是yy年mm月dd日 W HH:MM:ss"});
});

$(function(){
	 var $div_li =$("div.friendshipTitle ul li"); 
	    $div_li.mouseover(function(){
			$(this).addClass("current")           
				   .siblings().removeClass("current");
		var index = $div_li.index(this);
		$("div.linkzone > ul")   	//选取子节点。不选取子节点的话，会引起错误。如果里面还有div 
					.eq(index).show()   //显示 <li>元素对应的<div>元素
					.siblings().hide();	
		})
})

$(function(){
	$("ul.sxNewsList li:odd").css({background:"#f6f6f6"})		   
})


var start=0
var num=0
var timer
var speed=1
$(function() { 
//Default Action 
$(".fourWrap").hide(); //Hide all content 
$("ul.cylUl li:first").addClass("current").show(); //Activate first tab 
$(".fourWrap:first").show(); //Show first tab content 
//On Click Event 
$("ul.cylUl li a").mouseover(function() { 
$("ul.cylUl li").removeClass("current"); //Remove any "active" class 
$(this).parent("li").addClass("current"); //Add "active" class to selected tab 
$(".fourWrap").hide(); //Hide all tab content 
var activeTab = $(this).attr("tag"); //Find the rel attribute value to identify the active tab + content 
$(activeTab).fadeIn(100); //Fade in the active content 
return false; 
}); 
num=$(".listTr td").length

timer=setInterval("motion()",30)
$(".listTr").append($(".listTr").html())
$(".listTr td").mouseover(function(){
	clearInterval(timer)
	})
	$(".listTr td").mouseout(function(){
	timer=setInterval("motion()",30)
	})


$(".llxqLeft").mouseover(function(){
	clearInterval(timer)
	speed=5
	timer=setInterval("motion()",30)
	})
	$(".llxqLeft").mouseout(function(){

	clearInterval(timer)
	speed=1
	timer=setInterval("motion()",30)
	})
	
$(".llxqRight").mouseover(function(){
	clearInterval(timer)
	speed=5
	timer=setInterval("motion1()",30)
	})
	$(".llxqRight").mouseout(function(){

	clearInterval(timer)
	speed=1
	timer=setInterval("motion()",30)
	})	
});
function motion(){

	
	$(".picList").css("left",start-=speed);
	 if(start<= 746-$(".picList").width()){
		start=746-$(".picList").width()*0.5
	 }
	 
	//$("#llPic").css("left",$("#llPic").css("left")-1)
}

function motion1(){

	
	$(".picList").css("left",start+=speed);
	 if(start>=0){
		start=0-$(".picList").width()*0.5
	 }
	 
	//$("#llPic").css("left",$("#llPic").css("left")-1)
}

$(function(){
	$("ul.gnUl li a").hover(function(){
	$(this).addClass("current").parent().siblings().children().removeClass();
	})	   
})


$(function(){
	$("ul.sxUl li a").hover(function(){
		$(this).attr("class",$(this).attr("class").split("_")[0]+"_on").parent().siblings().children().each(function(){
			$(this).attr("class",$(this).attr("class").split("_")[0]);
		});
	})	   
})

$(function(){
	$(".mainNav ul li").hover(function(){
	$(this).addClass("current").siblings().removeClass();
	})	   
})


$(function() { 
//Default Action 
$(".fourWrap1").hide(); //Hide all content 
$("ul.text1 li:first").addClass("current").show(); //Activate first tab 
$(".fourWrap1:first").show(); //Show first tab content 
//On Click Event 
$("ul.text1 li a").mouseover(function() { 
$("ul.text1 li").removeClass("current"); //Remove any "active" class 
$(this).parent("li").addClass("current"); //Add "active" class to selected tab 
$(".fourWrap1").hide(); //Hide all tab content 
var activeTab = $(this).attr("tag"); //Find the rel attribute value to identify the active tab + content 
$(activeTab).fadeIn(100); //Fade in the active content 
return false; 
}); 
});

$(function() { 
//Default Action 
$(".fourWrap2").hide(); //Hide all content 
$("ul.text2 li:first").addClass("current").show(); //Activate first tab 
$(".fourWrap2:first").show(); //Show first tab content 
//On Click Event 
$("ul.text2 li a").mouseover(function() { 
$("ul.text2 li").removeClass("current"); //Remove any "active" class 
$(this).parent("li").addClass("current"); //Add "active" class to selected tab 
$(".fourWrap2").hide(); //Hide all tab content 
var activeTab = $(this).attr("tag"); //Find the rel attribute value to identify the active tab + content 
$(activeTab).fadeIn(100); //Fade in the active content 
return false; 
}); 
});

$(function() { 
//Default Action 
$(".fourWrap3").hide(); //Hide all content 
$("ul.text3 li:first").addClass("current").show(); //Activate first tab 
$(".fourWrap3:first").show(); //Show first tab content 
//On Click Event 
$("ul.text3 li a").mouseover(function() { 
$("ul.text3 li").removeClass("current"); //Remove any "active" class 
$(this).parent("li").addClass("current"); //Add "active" class to selected tab 
$(".fourWrap3").hide(); //Hide all tab content 
var activeTab = $(this).attr("tag"); //Find the rel attribute value to identify the active tab + content 
$(activeTab).fadeIn(100); //Fade in the active content 
return false; 
}); 
});

$(function() { 
//Default Action 
$(".fourWrap4").hide(); //Hide all content 
$("ul.text4 li:first").addClass("current").show(); //Activate first tab 
$(".fourWrap4:first").show(); //Show first tab content 
//On Click Event 
$("ul.text4 li a").mouseover(function() { 
$("ul.text4 li").removeClass("current"); //Remove any "active" class 
$(this).parent("li").addClass("current"); //Add "active" class to selected tab 
$(".fourWrap4").hide(); //Hide all tab content 
var activeTab = $(this).attr("tag"); //Find the rel attribute value to identify the active tab + content 
$(activeTab).fadeIn(100); //Fade in the active content 
return false; 
}); 
});

$(function(){
		$(".xxcjTable tr:even").css("background","#f6f6f6"); /* 基数行添加样式*/
	})

$(function(){
				
				var twidth=$("#cdiv .title").width();
				var cwidth=$("#cdiv .div").width();
				var countWidth=$("#cdiv").width();
				// 修正一些宽度
			
				$("#cdiv .div").width(countWidth-($("#cdiv .title").size()-1)*twidth);
				//$("#cdiv .contextul").width($("#cdiv .div").width());
				twidth=$("#cdiv .title").width();
				cwidth=$("#cdiv .div").width();
				
				
				//$("#cdiv .contextul")
				
				$("#cdiv .div").each(function(){
					var index=$("#cdiv .div").index($(this));
					$(this).css({"z-index":index,"left":twidth*(index)});
				});
				
				$("#cdiv .title").click(function(){
					$(this).parent().prevAll().add($(this).parent()).each(function(){
						var index=$("#cdiv .div").index($(this));
						$(this).animate({left: index*twidth+"px"}, 500);
					});
					$(this).parent().nextAll().each(function(){
						var index=$("#cdiv .div").index($(this));
						$(this).animate({left: cwidth+((index-1)*twidth)-0+"px"}, 500);
					});
				});
			});
