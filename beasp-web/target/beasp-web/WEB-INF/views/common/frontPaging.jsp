<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 基于Bootstrap风格的分页 -->
<nav style="text-align: center;padding: 20px 0px;">
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
				<a href="?pageNo=${(page.number+1)-1 }"><span aria-hidden="true">&laquo;</span></a>
			</li>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${page.totalPages > 9 }"><!-- 总页数大于 9 -->
			<c:if test="${(page.number+1) != 1}">
				<li>
					<a href="?pageNo=1">1</a>
				</li>
				<c:if test="${(page.number+1)-4 > 1}"><!-- 当前页大于等于6 -->
					<li class="disable">
						<span>...</span>
					</li>
					<c:choose>
						<c:when test="${(page.number+1) > (page.totalPages - 4 )}">
							<c:forEach begin="${page.totalPages - 7}" end="${(page.number + 1) - 1}" var="wp">
								<li>
									<a href="?pageNo=${wp}">${wp}</a>
								</li>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<li>
								<a href="?pageNo=${(page.number+1)-3}">${(page.number+1)-3}</a>
							</li>
							<li>
								<a href="?pageNo=${(page.number+1)-2}">${(page.number+1)-2}</a>
							</li>
							<li>
								<a href="?pageNo=${(page.number+1)-1}">${(page.number+1)-1}</a>
							</li>
						</c:otherwise>
					</c:choose>
				</c:if>
				
				<!-- 当前页 - 4 小于 或等于 1 -->
				<c:if test="${(page.number+1)-4 <= 1}">
					<c:if test="${((page.number + 1) - 1) >= 2 }">
						<c:forEach begin="2" end="${(page.number + 1) - 1 }" var="wp">
							<li>
								<a href="?pageNo=${wp}">${wp}</a>
							</li>
						</c:forEach>
					</c:if>
				</c:if>
				
			</c:if>
			<li>
				<a href="javascript:;" style="color:#6CF;">${(page.number + 1)}</a>
			</li>
			<c:if test="${(page.number+1) != page.totalPages}">
				
				<!-- 当前页 + 4 大于 或等于 最大页 -->
				<c:if test="${((page.number + 1) + 4) >= page.totalPages }">
					<c:if test="${((page.number + 1 ) + 1) <= (page.totalPages - 1)}">
						<c:forEach begin="${(page.number + 1 ) + 1}" end="${page.totalPages - 1 }" var="wp">
							<li>
								<a href="?pageNo=${wp}">${wp}</a>
							</li>
						</c:forEach>
					</c:if>
				</c:if>
				
				<c:if test="${(page.number + 1) + 4 < page.totalPages}">
					<c:choose>
						<c:when test="${(page.number+1) < 5}">
							<c:forEach begin="${(page.number + 1) + 1}" end="8" var="wp">
								<li>
									<a href="?pageNo=${wp}">${wp}</a>
								</li>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<li><a href="?pageNo=${(page.number+1)+1}">${(page.number+1)+1}</a></li>
							<li><a href="?pageNo=${(page.number+1)+2}">${(page.number+1)+2}</a></li>
							<li><a href="?pageNo=${(page.number+1)+3}">${(page.number+1)+3}</a></li>
						</c:otherwise>
					</c:choose>								
					<li class="disable">
						<span>...</span>
					</li>
				</c:if>
				
				<li><a href="?pageNo=${page.totalPages}">${page.totalPages}</a></li>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:forEach begin="1" end="${page.totalPages }" var="wp">
				<li>
					<a href="?pageNo=${wp}">${wp}</a>
				</li>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<%-- 下一页 --%>
	<c:choose>
		<c:when test="${(page.number+1) eq page.totalPages }">
			<li class="disabled"><span><span aria-hidden="true">&raquo;</span></span></li>
		</c:when>
		<c:otherwise>
			<li>
				<a href="?pageNo=${(page.number+1)+1 }">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
		</c:otherwise>
	</c:choose>
	</ul>
</nav>