<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>
<aside class="col-md-2">
	<div class="nav-content">
		<ul id="accordion" class="accordion">
			<li class="">
				<div class="link"><i class="fa fa-paint-brush"></i>类别管理<i class="fa fa-chevron-down"></i></div>
				<ul class="submenu" style="display: none;">
					<li><a href="<%=bookAdminPath%>/categories">&gt;&nbsp;&nbsp;类别列表</a></li>
					<li><a href="<%=bookAdminPath%>/category/find">&gt;&nbsp;&nbsp;类别查询</a></li>
				</ul>
			</li>
			<li class="">
				<div class="link"><i class="fa fa-code"></i>书籍管理<i class="fa fa-chevron-down"></i></div>
				<ul class="submenu" style="display: none;">
					<li><a href="<%=bookAdminPath%>/books">&gt;&nbsp;&nbsp;书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/book/find">&gt;&nbsp;&nbsp;书籍查询</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=WAITCONFIRM">&gt;&nbsp;&nbsp;未审核书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=CONFIRMFAIL">&gt;&nbsp;&nbsp;审核失败书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=RELEASE">&gt;&nbsp;&nbsp;发布书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=EXCHANGE">&gt;&nbsp;&nbsp;交换书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=SHARE">&gt;&nbsp;&nbsp;分享书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=RECYCLEBIN">&gt;&nbsp;&nbsp;回收站书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=INVISIBLE">&gt;&nbsp;&nbsp;不可见书籍列表</a></li>
				</ul>
			</li>
			<li class="">
				<div class="link"><i class="fa fa-mobile"></i>样式管理<i class="fa fa-chevron-down"></i></div>
				<ul class="submenu" style="display: none;">
					<li><a href="<%=bookAdminPath%>/styles">&gt;&nbsp;&nbsp;样式列表</a></li>
				</ul>
			</li>
			<li class=""><div class="link"><i class="fa fa-globe"></i>用户管理<i class="fa fa-chevron-down"></i></div>
				<ul class="submenu" style="display: none;">
					<li><a href="<%=userAdminPath%>/users">&gt;&nbsp;&nbsp;用户列表</a></li>
					<li><a href="<%=userAdminPath%>/users?status=0">&gt;&nbsp;&nbsp;可用用户列表</a></li>
					<li><a href="<%=userAdminPath%>/users?status=1">&gt;&nbsp;&nbsp;锁定用户列表</a></li>
					<li><a href="<%=userAdminPath%>/users?status=2">&gt;&nbsp;&nbsp;删除用户列表</a></li>
				</ul>
			</li>
			<li class=""><div class="link"><i class="fa fa-globe"></i>书籍交换与分享管理<i class="fa fa-chevron-down"></i></div>
				<ul class="submenu" style="display: none;">
					<li><a href="<%=bookAdminPath%>/books/exchange">&gt;&nbsp;&nbsp;书籍交换列表</a></li>
					<li><a href="<%=bookAdminPath%>/books/share">&gt;&nbsp;&nbsp;书籍分享列表</a></li>
				</ul>
			</li>
		</ul>
		<%-- <ul class="nav navContent-menu">
			<li class="active"><a href="#">首页</a></li>
			<li><a role="button" data-toggle="collapse" href="#collapse_one" aria-expanded="true">类别管理<span class="pull-right">&lt;&lt;</span></a>
				<ul class="nav nav-second-level collapse" id="collapse_one">
					<li><a href="<%=bookAdminPath%>/categories">&gt;&nbsp;&nbsp;类别列表</a></li>
					<li><a href="<%=bookAdminPath%>/category/find">&gt;&nbsp;&nbsp;类别查询</a></li>
				</ul> <!-- /.nav-second-level -->
			</li>
			<li><a role="button" data-toggle="collapse" href="#collapse_two" aria-expanded="true">书籍管理<span class="pull-right">&lt;&lt;</span></a>
				<ul class="nav nav-second-level collapse" id="collapse_two">
					<li><a href="<%=bookAdminPath%>/books">&gt;&nbsp;&nbsp;书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/book/find">&gt;&nbsp;&nbsp;书籍查询</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=WAITCONFIRM">&gt;&nbsp;&nbsp;未审核书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=RELEASE">&gt;&nbsp;&nbsp;发布书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=EXCHANGE">&gt;&nbsp;&nbsp;交换书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=SHARE">&gt;&nbsp;&nbsp;分享书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=RECYCLEBIN">&gt;&nbsp;&nbsp;回收站书籍列表</a></li>
					<li><a href="<%=bookAdminPath%>/books?state=INVISIBLE">&gt;&nbsp;&nbsp;不可见书籍列表</a></li>
				</ul> <!-- /.nav-second-level -->
			</li>
			<li><a role="button" data-toggle="collapse" href="#collapse_three" aria-expanded="true">样式管理<span class="pull-right">&lt;&lt;</span></a>
				<ul class="nav nav-second-level collapse" id="collapse_three">
					<li><a href="<%=bookAdminPath%>/styles">&gt;&nbsp;&nbsp;样式列表</a></li>
				</ul> <!-- /.nav-second-level -->
			</li>
			<li><a role="button" data-toggle="collapse" href="#collapse_four" aria-expanded="true">用户管理<span class="pull-right">&lt;&lt;</span></a>
				<ul class="nav nav-second-level collapse" id="collapse_four">
					<li><a href="<%=userAdminPath%>/users">&gt;&nbsp;&nbsp;用户列表</a></li>
					<li><a href="<%=userAdminPath%>/users?status=0">&gt;&nbsp;&nbsp;可用用户列表</a></li>
					<li><a href="<%=userAdminPath%>/users?status=1">&gt;&nbsp;&nbsp;锁定用户列表</a></li>
					<li><a href="<%=userAdminPath%>/users?status=2">&gt;&nbsp;&nbsp;删除用户列表</a></li>
				</ul> <!-- /.nav-second-level -->
			</li>
			<li><a href="#">帮助</a></li>
		</ul> --%>
	</div>
</aside>