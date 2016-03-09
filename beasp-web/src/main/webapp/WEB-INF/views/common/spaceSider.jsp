<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<div class="sider">
	<div class="user-info">
		<span class="user-pic">
			<c:if test="${empty user.photoName }">
				<img src="<%=imgPath %>/user/default/prototype/5458505c00018e9202200220-220-220.jpg" alt="${user.nickName }" />
			</c:if>
			<c:if test="${!empty user.photoName }">
				<img src="<%=beaspPath %>${user.imageFullPath}" alt="${user.nickName }" />
			</c:if>
		</span>
		<ul class="user-lay">
			<li class="mynick-name"><span class="user-name">${user.nickName }</span></li>
			<li><span class="user-school">${user.school }</span></li>
			<li><a class="user-setup" href="<%=beaspPath%>/user/userinfo">设置</a></li>
		</ul>
	</div>
	<div class="user-desc">
		<div class="sign-wrap">
			<p id="signed" class="signed">
				<strong><c:if test="${empty user.summary }">这位童鞋很酷，什么也不想说</c:if>${user.summary }</strong>
				<em id="publishsign" class="publish-sign"></em>
			</p>
			<textarea id="js-sign-editor" class="sign-editor" style="height: 42px;">${user.summary }</textarea>
			<p id="rlf-tip-wrap" class="rlf-tip-wrap"></p>
		</div>
	</div>
	<ul class="mp cf">
		<li class="mp-item">
			<span class="mp-atag">
				<p class="mp-num">${user.phone }</p>
				<p class="mp-title">手机号</p>
			</span>
		</li>
		<li class="mp-item">
			<span class="mp-atag">
				<p class="mp-num">${user.gender.name }</p>
				<p class="mp-title">性别</p>
			</span>
		</li>
	</ul>
	<ul class="nav">
		<li>
			<a class="js-count-book <c:if test='${state ne "browse" and state ne "collection" and state ne "praise"   and state ne "recycle" and state ne "share" and state ne "exchange" }'>active</c:if>" href="<%=beaspPath%>/space/index">
				<i class="fa fa-book"></i>我的书籍<em class="got-num">${count }</em>
			</a>
		</li>
		<li>
			<a class="js-count-share <c:if test='${state eq "share" }'>active</c:if>" href="<%=beaspPath%>/space/share">
				<i class="fa fa-share"></i>我的分享<em class="got-num">${shareCount }</em>
			</a>
		</li>
		<li>
			<a class="js-count-exchange <c:if test='${state eq "exchange" }'>active</c:if>" href="<%=beaspPath%>/space/exchange">
				<i class="fa fa-exchange"></i>我的交换<em class="got-num">${exchangeCount }</em>
			</a>
		</li>
		<li>
			<a class="js-count-browse <c:if test='${state eq "browse" }'>active</c:if>" href="<%=beaspPath%>/space/browse">
				<i class="fa fa-eye"></i>我的浏览<em class="got-num">${browseCount }</em>
			</a>
		</li>
		<li>
			<a class="js-count-collection <c:if test='${state eq "collection" }'>active</c:if>" href="<%=beaspPath%>/space/collection">
				<i class="fa fa-heart"></i>我的收藏<em class="got-num">${collectionCount }</em>
			</a>
		</li>
		<li>
			<a class="js-count-praise <c:if test='${state eq "praise" }'>active</c:if>" href="<%=beaspPath%>/space/praise">
				<i class="fa fa-thumbs-up"></i>我的点赞<em class="got-num">${praiseCount }</em>
			</a>
		</li>
		<li>
			<a class="js-count-recyclebin <c:if test='${state eq "recycle" }'>active</c:if>" href="<%=beaspPath%>/space/recycle">
				<i class="fa fa-recycle"></i>我的回收站<em class="got-num">${recycleCount }</em>
			</a>
		</li>
		<li class="rd-dissu">
			<a class="read-mine" href="<%=beaspPath%>/space/index">查看我的社区</a>
			<p class="read-notice">看看里面有什么好玩的事情吧~~</p>
		</li>
	</ul>
	<div class="recent-visitors">
		<h4>最近访客</h4>
		<div class="visitors-box">
			<c:forEach items="${visitors }" var="v">
				<a class="visitor-pic" target="_blank" href="<%=beaspPath%>/space/u/uid/${v.id}">
					<c:if test="${!empty v.photoName }">
						<img src="<%=beaspPath %>${v.imageFullPath}" alt="${v.nickName}">
					</c:if>
					<c:if test="${empty v.photoName }">
						<img src="<%=imgPath %>/user/default/prototype/5458505c00018e9202200220-220-220.jpg" alt="${v.nickName}" >
					</c:if>
				</a>
			</c:forEach>
		</div>
	</div>
</div>