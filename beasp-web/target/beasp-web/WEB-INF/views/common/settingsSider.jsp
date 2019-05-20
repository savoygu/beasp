<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<ul class="wrap-boxes">
	<li <c:if test='${status eq 1 }'>class="nav-active"</c:if>>
		<a class="onactive" href="<%=beaspPath%>/user/setprofile">个人资料</a>
	</li>
	<li <c:if test='${status eq 2 }'>class="nav-active"</c:if>>
		<a class="onactive" href="<%=beaspPath%>/user/setavator">头像设置</a>
	</li>
	<li <c:if test='${status eq 3 }'>class="nav-active"</c:if>>
		<a class="onactive" href="<%=beaspPath%>/user/setverifyemail">邮箱验证</a>
	</li>
	<li <c:if test='${status eq 4 }'>class="nav-active"</c:if>>
		<a class="onactive" href="<%=beaspPath%>/user/setresetpwd">修改密码</a>
	</li>
	<%-- <li>
		<a class="onactive" href="<%=beaspPath%>/user/setbindsns">绑定账号</a>
	</li> --%>
</ul>