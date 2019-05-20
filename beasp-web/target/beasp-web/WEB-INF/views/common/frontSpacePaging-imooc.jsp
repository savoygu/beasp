<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 基于慕课网风格的分页 -个人空间-->
<div class="page">
	<%-- 上一页 --%>
	<c:choose>
		<c:when test="${(page.number+1) eq 1 }">
			<span class="disable_page">首页</span>
			<span class="disable_page">上一页</span>
		</c:when>
		<c:otherwise>
			<a href="?pageNo=1">首页</a>
			<a href="?pageNo=${(page.number+1)-1 }">上一页</a>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${page.totalPages > 9 }"><!-- 总页数大于 9 -->
			<c:if test="${(page.number+1) != 1}">
				<a href="?pageNo=1">1</a>
				<c:if test="${(page.number+1)-4 > 1}"><!-- 当前页大于等于6 -->
					<span>...</span>
					<c:choose>
						<c:when test="${(page.number+1) > (page.totalPages - 4 )}">
							<c:forEach begin="${page.totalPages - 7}" end="${(page.number + 1) - 1}" var="wp">
								<a href="?pageNo=${wp}">${wp}</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
								<a href="?pageNo=${(page.number+1)-3}">${(page.number+1)-3}</a>
								<a href="?pageNo=${(page.number+1)-2}">${(page.number+1)-2}</a>
								<a href="?pageNo=${(page.number+1)-1}">${(page.number+1)-1}</a>
						</c:otherwise>
					</c:choose>
				</c:if>
				
				<!-- 当前页 - 4 小于 或等于 1 -->
				<c:if test="${(page.number+1)-4 <= 1}">
					<c:if test="${((page.number + 1) - 1) >= 2 }">
						<c:forEach begin="2" end="${(page.number + 1) - 1 }" var="wp">
								<a href="?pageNo=${wp}">${wp}</a>
						</c:forEach>
					</c:if>
				</c:if>
				
			</c:if>
			<a href="javascript:;" class="active">${(page.number + 1)}</a>
			<c:if test="${(page.number+1) != page.totalPages}">
				
				<!-- 当前页 + 4 大于 或等于 最大页 -->
				<c:if test="${((page.number + 1) + 4) >= page.totalPages }">
					<c:if test="${((page.number + 1 ) + 1) <= (page.totalPages - 1)}">
						<c:forEach begin="${(page.number + 1 ) + 1}" end="${page.totalPages - 1 }" var="wp">
							<a href="?pageNo=${wp}">${wp}</a>
						</c:forEach>
					</c:if>
				</c:if>
				
				<c:if test="${(page.number + 1) + 4 < page.totalPages}">
					<c:choose>
						<c:when test="${(page.number+1) < 5}">
							<c:forEach begin="${(page.number + 1) + 1}" end="8" var="wp">
									<a href="?pageNo=${wp}">${wp}</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<a href="?pageNo=${(page.number+1)+1}">${(page.number+1)+1}</a>
							<a href="?pageNo=${(page.number+1)+2}">${(page.number+1)+2}</a>
							<a href="?pageNo=${(page.number+1)+3}">${(page.number+1)+3}</a>
						</c:otherwise>
					</c:choose>								
						<span>...</span>
				</c:if>
				
				<a href="?pageNo=${page.totalPages}">${page.totalPages}</a>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:forEach begin="1" end="${page.totalPages }" var="wp">
					<c:if test="${wp eq (page.number+1) }">
						<a href="javascript:;" class="active">${(page.number + 1)}</a>
					</c:if>
					<c:if test="${wp ne (page.number+1) }">
						<a href="?pageNo=${wp}">${wp}</a>
					</c:if>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<%-- 下一页 --%>
	<c:choose>
		<c:when test="${(page.number+1) eq page.totalPages }">
			<span class="disable_page">下一页</span>
			<span class="disable_page">尾页</span>
		</c:when>
		<c:otherwise>
			<a href="?pageNo=${(page.number+1)+1}">下一页</a>
			<a href="?pageNo=${page.totalPages}">尾页</a>
		</c:otherwise>
	</c:choose>
</div>