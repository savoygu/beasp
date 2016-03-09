<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<nav style="text-align: center;">
	<ul class="pagination pagination-lg">
		<%-- 上一页 --%>
		<c:choose>
			<c:when test="${(page.number+1) eq 1 }">
				<li class="disabled">
					<span><span aria-hidden="true">&laquo;</span></span>
				</li>
			</c:when>
			<c:otherwise>
				<li>
					<a href="javascript:toPage(${(page.number+1)-1 })"><span aria-hidden="true">&laquo;</span></a>
				</li>
			</c:otherwise>
		</c:choose>
		<c:forEach begin="${begin }" end="${end }" var="wp">
			<c:choose>
				<c:when test="${wp eq (page.number+1) }">
					<li class="disabled"><span>${wp }</span></li>
				</c:when>
				<c:otherwise>
					<li>	
						<a href="javascript:toPage(${wp })">${wp }</a>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<%-- 下一页 --%>
		<c:choose>
			<c:when test="${(page.number+1) eq page.totalPages }">
				<li class="disabled"><span><span aria-hidden="true">&raquo;</span></span></li>
			</c:when>
			<c:otherwise>
				<li>
					<a href="javascript:toPage(${(page.number+1)+1 })"><span aria-hidden="true">&raquo;</span></a>
				</li>
			</c:otherwise>
		</c:choose>
	</ul>
</nav>