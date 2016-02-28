<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<div class="sider">
	<div class="user-info">
		<span class="user-pic">
			<c:if test="${empty spuser.photoName }">
				<img src="<%=imgPath %>/user/default/prototype/5458505c00018e9202200220-220-220.jpg" alt="${spuser.nickName }" />
			</c:if>
			<c:if test="${!empty spuser.photoName }">
				<img src="<%=beaspPath %>${spuser.imageFullPath}" alt="${spuser.nickName }" />
			</c:if>
		</span>
		<ul class="user-lay">
			<li class="mynick-name"><span class="user-name">${spuser.nickName }</span></li>
			<li><span class="user-school">${spuser.school }</span></li>
			<li class="actions"><a href="javascript:void(0)" class="btn-add-friend js-add-friend" id="js-add-frd" data-uid="${spuser.id }">加为好友</a></li>
		</ul>
	</div>
	<div class="user-desc">
		<div class="sign-wrap">
			<p id="signed" class="signed">
				<strong>${spuser.summary }</strong>
				<a class="more-info" href="<%=beaspPath%>/space/profile?uid=${spuser.id}">详细信息</a>
			</p>
		</div>
	</div>
	<ul class="mp cf">
		<li class="mp-item">
			<span class="mp-atag">
				<p class="mp-num">${spuser.phone }</p>
				<p class="mp-title">手机号</p>
			</span>
		</li>
		<li class="mp-item">
			<span class="mp-atag">
				<p class="mp-num">${spuser.gender.name }</p>
				<p class="mp-title">性别</p>
			</span>
		</li>
	</ul>
	<ul class="nav">
		<li>
			<a class="js-count-book <c:if test='${state ne "browse" and state ne "collection" and state ne "praise" and state ne "profile"}'>active</c:if>" href="<%=beaspPath%>/space/u/uid/${spuser.id}">
				<i class="fa fa-book"></i>TA的书籍<em class="got-num">${count }</em>
			</a>
		</li>
		<li>
			<a class="js-count-browse <c:if test='${state eq "browse" }'>active</c:if>" href="<%=beaspPath%>/space/u/uid/${spuser.id}/browse">
				<i class="fa fa-eye"></i>TA的浏览<em class="got-num">${browseCount }</em>
			</a>
		</li>
		<li>
			<a class="js-count-collection <c:if test='${state eq "collection" }'>active</c:if>" href="<%=beaspPath%>/space/u/uid/${spuser.id}/collection">
				<i class="fa fa-heart"></i>TA的收藏<em class="got-num">${collectionCount }</em>
			</a>
		</li>
		<li>
			<a class="js-count-praise <c:if test='${state eq "praise" }'>active</c:if>" href="<%=beaspPath%>/space/u/uid/${spuser.id}/praise">
				<i class="fa fa-thumbs-up"></i>TA的点赞<em class="got-num">${praiseCount }</em>
			</a>
		</li>
		<li class="rd-dissu">
			<a class="read-mine" href="<%=beaspPath%>/space/index">TA的社区</a>
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