<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 基于网易云音乐风格的分页 -->
<div class="pageContainer">
	<%-- 上一页 --%>
	<c:choose>
		<c:when test="${(page.number+1) eq 1 }">
			上一页
		</c:when>
		<c:otherwise>
			<a href="?pageNo=${(page.number+1)-1 }<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">上一页</a>
		</c:otherwise>
	</c:choose>
	<c:if test="${(page.number+1) != 1}">
		<a href="?pageNo=1<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">1</a>
		
		<c:if test="${(page.number+1)-4 > 1}">
			<span>...</span>
			<c:choose>
				<c:when test="${(page.number+1) > (page.totalPages - 4 )}">
					<c:forEach begin="${page.totalPages - 7}" end="${(page.number + 1) - 1}" var="wp">
						<a href="?pageNo=${wp}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${wp}</a>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<a href="?pageNo=${(page.number+1)-3}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${(page.number+1)-3}</a>
					<a href="?pageNo=${(page.number+1)-2}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${(page.number+1)-2}</a>
					<a href="?pageNo=${(page.number+1)-1}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${(page.number+1)-1}</a>
				</c:otherwise>
			</c:choose>
		</c:if>
		
		<!-- 当前页 - 4 小于 或等于 1 -->
		<c:if test="${(page.number+1)-4 <= 1}">
			<c:if test="${((page.number + 1) - 1) >= 2 }">
				<c:forEach begin="2" end="${(page.number + 1) - 1 }" var="wp">
					<a href="?pageNo=${wp}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${wp}</a>
				</c:forEach>
			</c:if>
		</c:if>
		
	</c:if>
	<a href="javascript:;" style="color:#6CF;">${(page.number + 1)}</a>
	<c:if test="${(page.number+1) != page.totalPages}">
		
		<!-- 当前页 + 4 大于 或等于 最大页 -->
		<c:if test="${((page.number + 1) + 4) >= page.totalPages }">
			<c:if test="${((page.number + 1 ) + 1) <= (page.totalPages - 1)}">
				<c:forEach begin="${(page.number + 1 ) + 1}" end="${page.totalPages - 1 }" var="wp">
					<a href="?pageNo=${wp}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${wp}</a>
				</c:forEach>
			</c:if>
		</c:if>
		
		<c:if test="${(page.number + 1) + 4 < page.totalPages}">
			<c:choose>
				<c:when test="${(page.number+1) < 5}">
					<c:forEach begin="${(page.number + 1) + 1}" end="8" var="wp">
						<a href="?pageNo=${wp}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${wp}</a>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<a href="?pageNo=${(page.number+1)+1}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${(page.number+1)+1}</a>
					<a href="?pageNo=${(page.number+1)+2}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${(page.number+1)+2}</a>
					<a href="?pageNo=${(page.number+1)+3}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${(page.number+1)+3}</a>
				</c:otherwise>
			</c:choose>								
			<span>...</span>
		</c:if>
		
		<a href="?pageNo=${page.totalPages}<c:if test='${! empty param.parentId}'>&parentId=${param.parentId}</c:if>">${page.totalPages}</a>
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
</div>