<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="panelBar">
<div class="pages">
	<span>显示</span> <select class="combox" name="pageSize" onchange="dwzPageBreak({targetType:'dialog', numPerPage:'10'})">
		<option value="5" ${pageList.pageSize==5?"selected=\"selected\"":""}>5</option>
		<option value="10" ${pageList.pageSize==10?"selected=\"selected\"":""}>10</option>
		<option value="20" ${pageList.pageSize==20?"selected=\"selected\"":""}>20</option>
		<option value="50" ${pageList.pageSize==50?"selected=\"selected\"":""} >50</option>
	</select> <span>条，共${pageList.total}条</span>
</div>

<div class="pagination" targetType="navTab" totalCount="${pageList.total}" numPerPage="${pageList.pageSize}" pageNumShown="5" currentPage="${pageList.pageNum}"></div>

</div>