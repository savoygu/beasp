<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 只显示九个页码 -->
<%--
	方式三：
	我们同样需要计算页码列表的开始和结束位置,
	1. 总页数不足 9 页, begin = 1, end = 最大值,
	2. 通过公式设置 begin 和 end, begin = 当前页 - 4, end = 当前页 + 4,
	3. 如果 begin < 1, 那么让 begin = 1, end = 9,
	4. 如果 end > 总页数, 那么让 begin = 总页数 - 8, end = 总页数
	<c:set var="begin" value=""></c:set>
	<c:set var="end" value=""></c:set>
--%>
<%-- 上一页 --%>
<c:choose>
	<c:when test="${(page.number+1) eq 1 }">
		上一页
	</c:when>
	<c:otherwise>
		<a href="?pageNo=${(page.number+1)-1 }<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">上一页</a>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${page.totalPages <= 9 }">
		<c:set var="begin" value="1"></c:set>
		<c:set var="end" value="${page.totalPages }"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="begin" value="${(page.number+1) - 4 }"></c:set>
		<c:set var="end" value="${(page.number+1) + 4 }"></c:set>
		<c:if test="${begin < 1 }">
			<c:set var="begin" value="1"></c:set>
			<c:set var="end" value="9"></c:set>
		</c:if>
		<c:if test="${end > page.totalPages }">
			<c:set var="begin" value="${page.totalPages - 8 }"></c:set>
			<c:set var="end" value="${page.totalPages }"></c:set>
		</c:if>
	</c:otherwise>
</c:choose>

<c:forEach begin="${begin }" end="${end }" var="wp">
	<c:choose>
		<c:when test="${wp eq (page.number+1) }">
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