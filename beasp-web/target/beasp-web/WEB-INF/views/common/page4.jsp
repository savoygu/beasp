<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 显示所有页码 -->
<%-- 上一页 --%>
<c:choose>
	<c:when test="${(page.number+1) eq 1 }">
		上一页
	</c:when>
	<c:otherwise>
		<a href="?pageNo=${(page.number+1)-1 }<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">上一页</a>
	</c:otherwise>
</c:choose>

<%-- 方式一：--%>
<c:forEach begin="${pageIndex.startIndex }" end="${pageIndex.endIndex }" var="wp">
	<c:choose>
		<c:when test="${(page.number+1) == wp }">
			${wp }
		</c:when>
		<c:otherwise>
			<a href="?pageNo=${wp }<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${wp }</a>
		</c:otherwise>
	</c:choose>
</c:forEach>


<%-- 下一页 --%>
<c:choose>
	<c:when test="${(page.number+1) eq page.totalPages }">
		下一页
	</c:when>
	<c:otherwise>
		<a href="?pageNo=${(page.number+1)+1 }<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">下一页</a>
	</c:otherwise>
</c:choose>