<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 基于慕课网风格的分页 -搜索结果-->
<div class="page">
	<%-- 上一页 --%>
	<c:choose>
		<c:when test="${pageView.currentPage eq 1 }">
			<span class="disable_page">首页</span>
			<span class="disable_page">上一页</span>
		</c:when>
		<c:otherwise>
			<a href="?pageNo=1<c:if test='${!empty words  }'>&words=${words }</c:if>">首页</a>
			<a href="?pageNo=${pageView.currentPage-1 }<c:if test='${!empty words  }'>&words=${words }</c:if>">上一页</a>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${pageView.totalPage > 9 }"><!-- 总页数大于 9 -->
			<c:if test="${pageView.currentPage != 1}">
				<a href="?pageNo=1<c:if test='${!empty words  }'>&words=${words }</c:if>">1</a>
				<c:if test="${pageView.currentPage-4 > 1}"><!-- 当前页大于等于6 -->
					<span>...</span>
					<c:choose>
						<c:when test="${pageView.currentPage > (pageView.totalPage - 4 )}">
							<c:forEach begin="${pageView.totalPage - 7}" end="${pageView.currentPage - 1}" var="wp">
								<a href="?pageNo=${wp}<c:if test='${!empty words  }'>&words=${words }</c:if>">${wp}</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
								<a href="?pageNo=${pageView.currentPage-3}<c:if test='${!empty words  }'>&words=${words }</c:if>">${pageView.currentPage-3}</a>
								<a href="?pageNo=${pageView.currentPage-2}<c:if test='${!empty words  }'>&words=${words }</c:if>">${pageView.currentPage-2}</a>
								<a href="?pageNo=${pageView.currentPage-1}<c:if test='${!empty words  }'>&words=${words }</c:if>">${pageView.currentPage-1}</a>
						</c:otherwise>
					</c:choose>
				</c:if>
				
				<!-- 当前页 - 4 小于 或等于 1 -->
				<c:if test="${pageView.currentPage-4 <= 1}">
					<c:if test="${(pageView.currentPage - 1) >= 2 }">
						<c:forEach begin="2" end="${pageView.currentPage - 1 }" var="wp">
								<a href="?pageNo=${wp}<c:if test='${!empty words  }'>&words=${words }</c:if>">${wp}</a>
						</c:forEach>
					</c:if>
				</c:if>
				
			</c:if>
			<a href="javascript:;" class="active">${pageView.currentPage}</a>
			<c:if test="${pageView.currentPage != pageView.totalPage}">
				
				<!-- 当前页 + 4 大于 或等于 最大页 -->
				<c:if test="${(pageView.currentPage + 4) >= pageView.totalPage }">
					<c:if test="${((page.number + 1 ) + 1) <= (pageView.totalPage - 1)}">
						<c:forEach begin="${(page.number + 1 ) + 1}" end="${pageView.totalPage - 1 }" var="wp">
							<a href="?pageNo=${wp}<c:if test='${!empty words  }'>&words=${words }</c:if>">${wp}</a>
						</c:forEach>
					</c:if>
				</c:if>
				
				<c:if test="${pageView.currentPage + 4 < pageView.totalPage}">
					<c:choose>
						<c:when test="${pageView.currentPage < 5}">
							<c:forEach begin="${pageView.currentPage + 1}" end="8" var="wp">
									<a href="?pageNo=${wp}<c:if test='${!empty words  }'>&words=${words }</c:if>">${wp}</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<a href="?pageNo=${pageView.currentPage+1}<c:if test='${!empty words  }'>&words=${words }</c:if>">${pageView.currentPage+1}</a>
							<a href="?pageNo=${pageView.currentPage+2}<c:if test='${!empty words  }'>&words=${words }</c:if>">${pageView.currentPage+2}</a>
							<a href="?pageNo=${pageView.currentPage+3}<c:if test='${!empty words  }'>&words=${words }</c:if>">${pageView.currentPage+3}</a>
						</c:otherwise>
					</c:choose>								
						<span>...</span>
				</c:if>
				
				<a href="?pageNo=${pageView.totalPage}<c:if test='${!empty words  }'>&words=${words }</c:if>">${pageView.totalPage}</a>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:forEach begin="1" end="${pageView.totalPage }" var="wp">
					<c:if test="${wp eq pageView.currentPage }">
						<a href="javascript:;" class="active">${pageView.currentPage}</a>
					</c:if>
					<c:if test="${wp ne pageView.currentPage }">
						<a href="?pageNo=${wp}<c:if test='${!empty words  }'>&words=${words }</c:if>">${wp}</a>
					</c:if>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<%-- 下一页 --%>
	<c:choose>
		<c:when test="${pageView.currentPage eq pageView.totalPage }">
			<span class="disable_page">下一页</span>
			<span class="disable_page">尾页</span>
		</c:when>
		<c:otherwise>
			<a href="?pageNo=${pageView.currentPage+1}<c:if test='${!empty words  }'>&words=${words }</c:if>">下一页</a>
			<a href="?pageNo=${pageView.totalPage}<c:if test='${!empty words  }'>&words=${words }</c:if>">尾页</a>
		</c:otherwise>
	</c:choose>
</div>