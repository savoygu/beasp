<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 基于慕课网风格的分页 -书籍申请-->
<div id="page-nav">
	<%-- 上一页 --%>
	<c:choose>
		<c:when test="${(page.number+1) eq 1 }">
			<a class="no prev" href="javascript:;">«首页</a>
			<a class="no prev" href="javascript:;">«上一页</a>
		</c:when>
		<c:otherwise>
			<a class="prev" href="?pageNo=1<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">«首页</a>
			<a class="prev" href="?pageNo=${(page.number+1)-1 }<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">«上一页</a>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${page.totalPages > 9 }"><!-- 总页数大于 9 -->
			<c:if test="${(page.number+1) != 1}">
				<a href="?pageNo=1<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">1</a>
				<c:if test="${(page.number+1)-4 > 1}"><!-- 当前页大于等于6 -->
					<a class="no" href="javascript:;">…</a>
					<c:choose>
						<c:when test="${(page.number+1) > (page.totalPages - 4 )}">
							<c:forEach begin="${page.totalPages - 7}" end="${(page.number+1) - 1}" var="wp">
								<a href="?pageNo=${wp}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${wp}</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
								<a href="?pageNo=${(page.number+1)-3}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${(page.number+1)-3}</a>
								<a href="?pageNo=${(page.number+1)-2}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${(page.number+1)-2}</a>
								<a href="?pageNo=${(page.number+1)-1}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${(page.number+1)-1}</a>
						</c:otherwise>
					</c:choose>
				</c:if>
				
				<!-- 当前页 - 4 小于 或等于 1 -->
				<c:if test="${(page.number+1)-4 <= 1}">
					<c:if test="${((page.number+1) - 1) >= 2 }">
						<c:forEach begin="2" end="${(page.number+1) - 1 }" var="wp">
								<a href="?pageNo=${wp}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${wp}</a>
						</c:forEach>
					</c:if>
				</c:if>
				
			</c:if>
			<a  class="on" href="javascript:;">${(page.number+1)}</a>
			<c:if test="${(page.number+1) != page.totalPages}">
				
				<!-- 当前页 + 4 大于 或等于 最大页 -->
				<c:if test="${((page.number+1) + 4) >= page.totalPages }">
					<c:if test="${((page.number + 1 ) + 1) <= (page.totalPages - 1)}">
						<c:forEach begin="${(page.number + 1 ) + 1}" end="${page.totalPages - 1 }" var="wp">
							<a href="?pageNo=${wp}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${wp}</a>
						</c:forEach>
					</c:if>
				</c:if>
				
				<c:if test="${(page.number+1) + 4 < page.totalPages}">
					<c:choose>
						<c:when test="${(page.number+1) < 5}">
							<c:forEach begin="${(page.number+1) + 1}" end="8" var="wp">
									<a href="?pageNo=${wp}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${wp}</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<a href="?pageNo=${(page.number+1)+1}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${(page.number+1)+1}</a>
							<a href="?pageNo=${(page.number+1)+2}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${(page.number+1)+2}</a>
							<a href="?pageNo=${(page.number+1)+3}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${(page.number+1)+3}</a>
						</c:otherwise>
					</c:choose>								
						<a class="no" href="javascript:;">…</a>
				</c:if>
				
				<a href="?pageNo=${page.totalPages}">${page.totalPages}</a>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:forEach begin="1" end="${page.totalPages }" var="wp">
					<c:if test="${wp eq (page.number+1) }">
						<a href="javascript:;" class="on">${(page.number+1)}</a>
					</c:if>
					<c:if test="${wp ne (page.number+1) }">
						<a href="?pageNo=${wp}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">${wp}</a>
					</c:if>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<%-- 下一页 --%>
	<c:choose>
		<c:when test="${(page.number+1) eq page.totalPages or page.totalPages eq 0 }">
			<a class="no next" href="javascript:;">下一页»</a>
			<a class="no next" href="javascript:;">尾页»</a>
		</c:when>
		<c:otherwise>
			<a class="next" href="?pageNo=${(page.number+1)+1}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">下一页</a>
			<a class="next" href="?pageNo=${page.totalPages}<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>">尾页</a>
		</c:otherwise>
	</c:choose>
	<span class="total">共${page.totalPages }页</span>
	<span class="gopage">
		<form method="get" action="">
			去第<input name="pageNo" class="page_text" min="1" max="210" size="5" maxlength="5" value="1" type="number">页
				<c:if test="${!empty param.status }">
					<input type="hidden" name="status" value="${param.status }">
				</c:if>
				<c:if test="${!empty param.soe }">
					<input type="hidden" name="soe"  value="${param.soe }">
				</c:if>
				<c:if test="${!empty param.order }">
					<input type="hidden" name="order"  value="${param.order }">	
				</c:if>
			<button>确定</button>
		</form>
	</span>
</div>