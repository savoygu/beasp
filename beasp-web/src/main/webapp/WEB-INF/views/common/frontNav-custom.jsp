<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<header class="nav-custom">
	<div class="nav-waper">
		<div class="nav-header">
			<a class="nav-logo" href="<%=beaspPath%>/">
				<!-- logo -->
				<%-- <img height="100%" alt="书籍交换与分享平台" src="<%=imgPath%>/logo.png"> --%>
				<!-- 文字 -->
				书籍交换与分享平台
			</a>
		</div>
		<div class="nav-content">
			<ul class="cf">
				<li><a target="_self" href="<%=beaspPath%>/book/list/category/0"><i class="fa fa-stack-exchange"></i> 分享与交换</a></li>
				<li><a target="_self" href="<%=beaspPath%>/topic"><i class="fa fa-hand-scissors-o"></i>求书籍</a></li>
				<li><a rel="nofollow"
					href="http://weibo.com/3195484694" target="_blank"><i
						class="fa fa-weibo"></i> &nbsp;书籍交换与分享平台微博</a></li>
				<c:if test="${empty sessionScope.user }">
					<%-- <li><a id="js-signin-btn" href="<%=beaspPath%>/account/signin">登录</a></li>
					<li><a id="js-signup-btn" href="<%=beaspPath%>/account/signup">注册</a></li> --%>
					<li><a id="js-signin-btn" href="javascript:void(0)">登录</a></li>
					<li><a id="js-signup-btn" href="javascript:void(0)">注册</a></li>
				</c:if>
				<c:if test="${!empty sessionScope.user }">
					<li class="user-card-box set_btn">
						<a id="header-avator" class="user-card-item" target="_blank" href="<%=beaspPath%>/space/index" action-type="my_menu">
							<c:if test="${empty user.photoName }">
								<img alt="${user.userName }" src="<%=imgPath %>/user/default/40x/5458505c00018e9202200220-40-40.jpg" height="40" width="40">
							</c:if>
							<c:if test="${!empty user.photoName }">
								<img alt="${user.userName }" src="<%=beaspPath %>${user.image40FullPath}" height="40" width="40">
							</c:if>
							<i class="myspace-remind" style="display: none;"></i>
						</a>
						<div class="g-user-card">
							<div class="card-inner">
								<div class="card-top">
									<c:if test="${empty user.photoName }">
										<img class="l" src="<%=imgPath %>/user/default/prototype/5458505c00018e9202200220-220-220.jpg" alt="${user.nickName }" />
									</c:if>
									<c:if test="${!empty user.photoName }">
										<img class="l" src="<%=beaspPath %>${user.imageFullPath}" alt="${user.nickName }" />
									</c:if>
									<span  class="name text-ellipsis">${user.userName}</span>
									<p class="meta">
										<a href="<%=beaspPath%>/space/experience">性别：<b id="js-user-mp">${user.gender.name }</b></a>
										<a href="<%=beaspPath%>/myclub/credit">Phone：<b id="js-user-mp">${user.phone }</b></a>
									</p>
								</div>
								<div class="card-links">
									<a class="my-beasp l" href="<%=beaspPath%>/space/index">我的分享与交换</a>
									<span class="split l"></span>
									<a class="my-sns l" href="<%=beaspPath%>/myclub/myquestion/t/ques">我的社区</a>
								</div>
								<div class="card-history">
									<span class="history-item">
										<c:if test="${empty bu }">
											去看看有什么好的书籍吧，<a class="continue" href="<%=beaspPath%>/book/list/category/0">出发</a>
										</c:if>
										<c:if test="${!empty bu }">
											<span class="tit text-ellipsis">${bu.book.name }</span>
											<span class="media-name text-ellipsis">${bu.book.author }</span>
											<i class="fa fa-clock-o"></i>
											<a class="continue" href="<%=beaspPath%>/book/view/${bu.book.id}">继续</a>
										</c:if>
									</span>
								</div>
								<div class="card-sets cf">
									<a class="l" href="<%=beaspPath%>/user/userinfo">个人设置</a>
									<a class="r" href="<%=beaspPath%>/account/logout">退出</a>
								</div>
							</div>
							<i class="card-arr"></i>
						</div>
						<!-- <div class="myhome">
							<a href="http://www.jq22.com/myhome"><img
								src="http://www.jq22.com/tx/23.png"></a>
							<div class="myhome-z bh2">
								<a class="bh" href="http://www.jq22.com/myhome"><i
									class="fa fa-home"></i> 个人中心</a> <a class="bh" href="#"
									onclick="myout()"><i class="fa fa-sign-out"></i> 退出登录</a>
							</div>
						</div> -->
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</header>