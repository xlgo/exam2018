<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		<h1>
			<button	id="restTime"  title="如果宕机的时候，时间较长， 可以把考试时间延后！">重置考试时间</button>
		</h1>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#restTime").click(function(){
				$.post("system/config/restTime",function(data){
					alert("重置考试时间成功");
				})
			});
		})
	</script>
</div>