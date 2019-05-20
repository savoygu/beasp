<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 只显示六个页码，显示点点点 -->
<%-- 方式二：
	我们需要计算页码列表的开始和结束位置，
	即两个变量 begin 和 end计算它们需要通过当前页码！
	1. 总页数不足 6 页 --> begin = 1, end = 最大值
	2. 通过公式设置 begin 和 end, begin = 当前页-1, end = 当前页 + 3
	3. 如果 begin < 1, 那么让 begin = 1, end = 6 
	4. 如果 end > tp, 让 begin = tp-3, end = tp
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
	<c:when test="${page.totalPages le 6 }">
		<c:set var="begin" value="1"></c:set>
		<c:set var="end" value="${page.totalPages }"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="begin" value="${(page.number+1)-2}"></c:set>
		<c:set var="end" value="${(page.number+1)+3 }"></c:set>
		<c:if test="${begin lt 1 }">
			<c:set var="begin" value="1"></c:set>
			<c:set var="end" value="6"></c:set>
		</c:if>
		<c:if test="${end gt page.totalPages }">
			<c:set var="begin" value="${page.totalPages-5 }"></c:set>
			<c:set var="end" value="${page.totalPages }"></c:set>
		</c:if>
	</c:otherwise>
</c:choose>

<!-- 页码 -->
<c:forEach begin="${begin }" end="${end }" var="wp">
	<c:choose>
		<c:when test="${wp eq  (page.number+1)}">
			${wp }
		</c:when>
		<c:otherwise>
			<a href="?pageNo=${wp }<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${wp }</a>
		</c:otherwise>
	</c:choose>
</c:forEach>

<!-- 显示点点点 -->
<c:if test="${end < page.totalPages }">
	...
</c:if>

<%-- 下一页 --%>
<c:choose>
	<c:when test="${(page.number+1) eq page.totalPages }">
		下一页
	</c:when>
	<c:otherwise>
		<a href="?pageNo=${(page.number+1)+1 }<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">下一页</a>
	</c:otherwise>
</c:choose>