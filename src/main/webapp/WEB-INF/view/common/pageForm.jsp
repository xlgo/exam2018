<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="pagerForm" method="post" action="#rel#">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="pageSize" value="${pageList.pageSize}" />
	<input type="hidden" name="sortField" value="${searchParam.sortField}" />
	<input type="hidden" name="sortOrder" value="${searchParam.sortOrder}" />
</form>