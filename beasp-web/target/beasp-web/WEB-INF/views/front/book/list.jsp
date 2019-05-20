<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>书籍交换与分享平台-列表页</title>

<!-- Bootstrap -->
<%-- <link href="<%=cssPath%>/bootstrap.min.css" rel="stylesheet"> --%>
<link href="<%=cssPath%>/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/book/list.css">
<link rel="stylesheet" href="<%=cssPath%>/front/login-regist.css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/frontNav-custom.jsp"></jsp:include>

	<div id="menu_wrap"
		class="yn jz container-fluid nav-bgn m0 visible-lg visible-md visible-sm">
		<div class="container m0" style="position: relative;">
			<a href="<%=beaspPath%>/book/list/category/1" id="z1" class="nzz">
				<span class="sort"><i class="fa fa-paint-brush"></i> &nbsp;文艺
					<i class="fa fa-angle-down"></i></span>
			</a> | <a class="nzz" href="<%=beaspPath%>/book/list/category/6" id="z2">
				<span class="sort"><i class="fa fa-keyboard-o"></i> &nbsp;青春
					<i class="fa fa-angle-down"></i></span>
			</a> | <a class="nzz" href="<%=beaspPath%>/book/list/category/8" id="z3">
				<span class="sort"><i class="fa fa-film"></i> &nbsp;教育 <i
					class="fa fa-angle-down"></i></span>
			</a> | <a class="nzz" href="<%=beaspPath%>/book/list/category/11" id="z4">
				<span class="sort "><i class="fa fa-paper-plane-o"></i>
					&nbsp;生活 <i class="fa fa-angle-down"></i></span>
			</a> | <a class="nzz" href="<%=beaspPath%>/book/list/category/15" id="z5">
				<span class="sort"><i class="fa fa-dropbox"></i> &nbsp;人文社科 <i
					class="fa fa-angle-down"></i></span>
			</a> | <a class="nzz" href="<%=beaspPath%>/book/list/category/22" id="z6">
				<span class="sort"><i class="fa fa-code"></i> &nbsp;经管 <i
					class="fa fa-angle-down"></i></span>
			</a> | <a class="nzz" href="<%=beaspPath%>/book/list/category/26" id="z7">
				<span class="sort"><i class="fa fa-code"></i> &nbsp;科技 <i
					class="fa fa-angle-down"></i></span>
			</a>| <a class="nzz" href="<%=beaspPath%>/book/list/category/33" id="z8">
				<span class="sort"><i class="fa fa-code"></i> &nbsp;励志<i
					class="fa fa-angle-down"></i></span>
			</a>
		</div>

		<div class="container-fluid">
			<div id="n1" class="nav-zi ty" style="display: none; height: 244px;">
				<ul id="nz1" class="nn list-inline container m0 nadc"
					style="display: block;">
					<li><a href="<%=beaspPath%>/book/list/category/2"><i
							class="fa fa-delicious ls"></i> 文学</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/3"><i
							class="fa fa-bell-o ls"></i> 传记</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/4"><i
							class="fa fa-sort-numeric-desc ls"></i> 艺术</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/5"><i
							class="fa fa-comments-o ls"></i> 摄影</a></li>
				</ul>
				<ul id="nz2" class="nn list-inline container m0 nadc"
					style="display: none;">
					<li><a href="<%=beaspPath%>/book/list/category/7"><i
							class="fa fa-eyedropper ls"></i> 青春文学</a></li>
				</ul>
				<ul id="nz3" class="nn list-inline container m0 nadc"
					style="display: none;">
					<li><a href="<%=beaspPath%>/book/list/category/9"><i
							class="fa fa-play-circle-o ls"></i> 教材</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/10"><i
							class="fa fa-exchange ls"></i> 考试</a></li>
				</ul>
				<ul id="nz4" class="nn list-inline container m0 nadc"
					style="display: none;">
					<li><a href="<%=beaspPath%>/book/list/category/12"><i
							class="fa fa-long-arrow-right ls"></i> 休闲/爱好</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/13"><i
							class="fa fa-long-arrow-down ls"></i> 旅游/地图</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/14"><i
							class="fa fa-sitemap ls"></i> 体育/运动</a></li>
				</ul>
				<ul id="nz5" class="nn list-inline container m0 nadc"
					style="display: none;">
					<li><a href="<%=beaspPath%>/book/list/category/16"><i
							class="fa fa-rocket ls"></i> 历史</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/17"><i
							class="fa fa-expand ls"></i> 文化</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/18"><i
							class="fa fa-arrows-v ls"></i> 古籍</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/19"><i
							class="fa fa-cogs ls"></i> 心理学</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/20"><i
							class="fa fa-gift ls"></i> 社会学科</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/21"><i
							class="fa fa-gamepad ls"></i> 法律</a></li>
				</ul>
				<ul id="nz6" class="nn list-inline container m0 nadc"
					style="display: none;">
					<li><a href="<%=beaspPath%>/book/list/category/23"><i
							class="fa fa-rocket ls"></i> 管理</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/24"><i
							class="fa fa-expand ls"></i> 投资理财</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/25"><i
							class="fa fa-arrows-v ls"></i> 经济</a></li>
				</ul>
				<ul id="nz7" class="nn list-inline container m0 nadc"
					style="display: none;">
					<li><a href="<%=beaspPath%>/book/list/category/27"><i
							class="fa fa-rocket ls"></i> 科普读物</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/28"><i
							class="fa fa-expand ls"></i> 建筑</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/29"><i
							class="fa fa-arrows-v ls"></i> 医学</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/30"><i
							class="fa fa-cogs ls"></i> 计算机/网络</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/31"><i
							class="fa fa-gift ls"></i> 农业/林业</a></li>
					<li><a href="<%=beaspPath%>/book/list/category/32"><i
							class="fa fa-gamepad ls"></i> 自然科学</a></li>
				</ul>
				<ul id="nz8" class="nn list-inline container m0 nadc"
					style="display: none;">
					<li><a href="<%=beaspPath%>/book/list/category/34"><i
							class="fa fa-rocket ls"></i> 成功/励志</a></li>
				</ul>
			</div>

		</div>
	</div>

	<div class="container m0 bod mar-t70 w-1200 mar-b50" id="zt">
		<!-- 导航菜单 -->
		<c:set var="menuout" value="" />
		<c:forEach items="${menucates }" var="cate" varStatus="status">
			<c:set var="menuout" value="<i class='path-split'>&#92;</i><a href='/book/list/category/${cate.id }'>${cate.name }</a>${menuout }" />
				<%-- <c:choose>
					<c:when test="${empty menuout }">
						<c:set var="menuout" value="<a href='/book/list/category/${cate.id }'>${cate.name }</a>${menuout }" />
					</c:when>					
					<c:otherwise>
						<c:set var="menuout" value="<a href='/book/list/category/${cate.id }'>${cate.name }</a><i class='path-split'>&#92;</i>${menuout }" />
					</c:otherwise>
				</c:choose> --%>
		</c:forEach>
		<%-- <c:forEach items="${menucates }" var="cate">
			<c:if test='${cateid!=cate.id }'>
				<c:if test="${fn:length(menucates) <= 3}">
					<c:set var="menuout"
						value="<a href='${cate.id }'>${cate.name }</a>${menuout }" />
				</c:if>
				<c:if test="${fn:length(menucates) > 3}">
					<c:set var="menuout"
						value="<a href='${cate.id }'>${cate.name }</a>${menuout }" />
				</c:if>
			</c:if>
			<c:if test='${cateid==cate.id }'>
				<c:if test="${fn:length(menucates) > 1}">
					<c:set var="menuout"
						value="</li><li><font color='#FF318C'>${cate.name }${menuout }</font>" />
				</c:if>
				<c:if test="${fn:length(menucates) <= 1}">
					<c:set var="menuout"
						value="<font color='#FF318C'>${cate.name }${menuout }</font>" />
				</c:if>
			</c:if>
		</c:forEach> --%>


		<div class="container m0 bod w-1200">
			<div class="w-1200">
				<div class="book-nav-box">
					<div class="book-nav-hd book-infos">
						<%-- <ol class="breadcrumb jjk20">
							<li><a href="<%=beaspPath%>/">首页</a></li>
							<c:if test="${booklist eq true }">
								<li><font color='#FF318C'>交换与分享</font></li>
							</c:if>
							<c:if test="${booklist ne true }">
								<li><a href="<%=beaspPath%>/book/list/category/0">交换与分享</a></li>
								<li><c:out value="${menuout }" escapeXml="false"></c:out></li>
							</c:if>
						</ol> --%>
						<div class="path">
							<a href="<%=beaspPath%>/">首页</a> <i class='path-split'>&#92;</i> <a
								href="<%=beaspPath%>/book/list/category/0">交换与分享</a> <!-- <i class='path-split'>&#92;</i> -->
							<c:out value="${menuout }" escapeXml="false"></c:out>
						</div>
					</div>
					<div class="book-nav-row cf">
						<span class="hd l">方向：</span>
						<div class="bd">
							<ul>
								<li class="book-nav-item <c:if test="${empty cateid}">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/0">全部</a></li>
								<li
									class="book-nav-item <c:if test="${cateParentid eq 1 }">on</c:if> <c:if test="${cateid eq 1 }">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/1">文艺</a></li>
								<li
									class="book-nav-item <c:if test="${cateParentid eq 6 }">on</c:if> <c:if test="${cateid eq 6 }">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/6">青春</a></li>
								<li
									class="book-nav-item <c:if test="${cateParentid eq 8 }">on</c:if> <c:if test="${cateid eq 8 }">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/8">教育</a></li>
								<li
									class="book-nav-item <c:if test="${cateParentid eq 11 }">on</c:if> <c:if test="${cateid eq 11 }">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/11">生活</a></li>
								<li
									class="book-nav-item <c:if test="${cateParentid eq 15 }">on</c:if> <c:if test="${cateid eq 15 }">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/15">人文社科</a></li>
								<li
									class="book-nav-item <c:if test="${cateParentid eq 22 }">on</c:if> <c:if test="${cateid eq 22 }">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/22">经管</a></li>
								<li
									class="book-nav-item <c:if test="${cateParentid eq 26 }">on</c:if> <c:if test="${cateid eq 26 }">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/26">科技</a></li>
								<li
									class="book-nav-item <c:if test="${cateParentid eq 33 }">on</c:if> <c:if test="${cateid eq 33 }">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/33">励志</a></li>
							</ul>
						</div>
					</div>
					<div class="book-nav-row cf">
						<span class="hd l">分类：</span>
						<div class="bd">
							<ul>
								<li
									class="book-nav-item <c:if test="${empty cateParentid}">on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/<c:if test='${empty cateParentid }'>${cateid}</c:if><c:if test='${!empty cateParentid }'>${cateParentid}</c:if>">全部</a></li>
								<c:forEach items="${categories }" var="c">
									<li
										class="book-nav-item <c:if test="${c.id==cateid }">on</c:if>"><a
										href="<%=beaspPath%>/book/list/category/${c.id}<c:if test='${!empty param.is_exchange }'>?is_exchange=${param.is_exchange }</c:if><c:if test='${!empty param.is_exchange}'><c:if test='${!empty param.sort }'>&</c:if></c:if><c:if test='${empty param.is_exchange}'><c:if test='${!empty param.sort }'>?</c:if></c:if><c:if test='${!empty param.sort}'>sort=${param.sort }</c:if>">${c.name }</a></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="book-nav-row cf">
						<span class="hd l">性质：</span>
						<div class="bd">
							<ul>
								<li class="book-nav-item <c:if test='${param.is_exchange eq 0 || empty param.is_exchange}'>on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/<c:if test='${booklist eq true}'>0</c:if><c:if test='${!empty cateid}'>${cateid }</c:if>?is_exchange=0<c:if test='${!empty param.sort}'>&sort=${param.sort }</c:if>">全部</a></li>
								<li class="book-nav-item <c:if test='${param.is_exchange eq 1 }'>on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/<c:if test='${booklist eq true}'>0</c:if><c:if test='${!empty cateid}'>${cateid }</c:if>?is_exchange=1<c:if test='${!empty param.sort}'>&sort=${param.sort }</c:if>">交换</a></li>
								<li class="book-nav-item <c:if test='${param.is_exchange eq 2 }'>on</c:if>"><a
									href="<%=beaspPath%>/book/list/category/<c:if test='${booklist eq true}'>0</c:if><c:if test='${!empty cateid}'>${cateid }</c:if>?is_exchange=2<c:if test='${!empty param.sort}'>&sort=${param.sort }</c:if>">分享</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="book-tool-bar cf">
					<div class="tool-left l">
						<a class="sort-item <c:if test="${param.sort ne 'pop' }">active</c:if>" 
						href="<%=beaspPath%>/book/list/category/<c:if test='${booklist eq true}'>0</c:if><c:if test='${!empty cateid}'>${cateid }</c:if>?sort=last<c:if test='${!empty param.is_exchange }'>&is_exchange=${param.is_exchange }</c:if>">最新</a> 
						<a class="sort-item <c:if test="${param.sort eq 'pop' }">active</c:if>" 
						href='<%=beaspPath%>/book/list/category/<c:if test='${booklist eq true}'>0</c:if><c:if test='${!empty cateid}'>${cateid }</c:if>?sort=pop<c:if test='${!empty param.is_exchange }'>&is_exchange=${param.is_exchange }</c:if>'>最热</a>
					</div>
					<div class="tool-right r">
						<span class="tool-item tool-page"> <span class="page-num">
								<b class="page-cur">${page.number+1 }</b>/<em class="page-total">${page.totalPages }</em>
						</span> <c:if test="${page.number+1 eq 1 }">
								<a class="page-action pager-prev hide-text disable"
									href="javascript:void(0)">上一页<i class="fa fa-angle-left"></i></a>
							</c:if> <c:if test="${page.number+1 gt 1 }">
								<a class="page-action pager-prev hide-text"
									href="?pageNo=${page.number }<c:if test='${!empty param.is_exchange }'>&is_exchange=${param.is_exchange }</c:if><c:if test='${!empty param.sort}'>&sort=${param.sort }</c:if>">上一页<i
									class="fa fa-angle-left"></i></a>
							</c:if> <c:if test="${page.number+1 < page.totalPages }">
								<a class="page-action pager-next hide-text"
									href="?pageNo=${page.number+2 }<c:if test='${!empty param.is_exchange }'>&is_exchange=${param.is_exchange }</c:if><c:if test='${!empty param.sort}'>&sort=${param.sort }</c:if>">下一页<i
									class="fa fa-angle-right"></i></a>
							</c:if> <c:if test="${page.number+1 eq page.totalPages }">
								<a class="page-action pager-next hide-text disable"
									href="javascript:void(0)">下一页<i class="fa fa-angle-right"></i></a>
							</c:if>
						</span>
					</div>
				</div>

				<div class="book-list">
					<div class="js-book-lists">
						<ul class="cf">
							<c:forEach items="${page.content }" var="book">
								<li class="book-one">
									<a target="_blank" href="<%=beaspPath%>/book/view/${book.id}">
										<div class="book-list-img">
											<c:forEach items="${book.styles }" var="style">
												<c:if test="${style.choice eq true }">
													<img alt="${book.name }"
														src="<%=beaspPath %>${style.imageFullPath}" width="240"
														height="135">
												</c:if>
											</c:forEach>
										</div>
										<h5>
											<span>${book.name }</span>
										</h5>
										<div class="tips">
											<p class="text-ellipsis">${book.summary }</p>
											<span class="r mar-l20 book-state"><i class="fa fa-list"></i>&nbsp;${book.state.name }</span>
											<span class="r mar-l20 collection"><i class="fa fa-heart"></i>&nbsp;${book.collection }</span> 
											<span class="r update-lastest"><i class="fa fa-eye"></i>&nbsp;${book.browse }</span>
										</div> 
										<span class="time-label">${book.author }</span>
									</a> 
									<span class="user-label" style="display: none;"><a target="_blank" href="<%=beaspPath%>/user/userinfo">${book.user.userName }A-Lin</a></span>
								</li>
							</c:forEach>
						</ul>
					</div>

					<jsp:include page="/WEB-INF/views/common/frontPaging-imooc.jsp"></jsp:include>

				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>

	<a href="javascript:;" id="back-to-top" title="回到顶部"></a>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="<%=jsPath%>/bootstrap.min.js"></script>
	<script type="text/javascript">
		var n = 1;
	</script>
	<script type="text/javascript" src="<%=jsPath%>/m.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/common.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/user/login-regist.js"></script>
</body>
</html>