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
<title>书籍交换与分享平台</title>

<!-- Bootstrap -->
<link href="<%=cssPath%>/bootstrap.min.css" rel="stylesheet">
<link href="<%=cssPath%>/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/index.css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/frontNav.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/common/frontBanner.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/common/frontMenu.jsp"></jsp:include>
	<div class="container-fluid m" id="zt">
		<div class="container m0 bod">

			<div id="recommendName" class="hot-book w-1000 mar-t10">
				<ul class="cf">
					<li class="on" data-positionId="1">热门书籍</li>
					<li class="" data-positionId="2">最新书籍</li>
					<li class="" data-positionId="3">分享书籍</li>
					<li class="" data-positionId="3">交换书籍</li>
				</ul>
			</div>
			<div id="hot-bookbox" class="mar-t20 w-1000">
				<div class="book-list" style="display: block;">
					<ul class="cf">
						<c:forEach items="${hotBooks }" var="book">
							<li>
								<div class="bookimg-box">
									<a href="#"> <c:forEach items="${book.styles }" var="style">
											<c:if test="${style.choice eq true }">
												<img class="bookimg" alt=""
													src="<%=beaspPath%>${style.imageFullPath}">
											</c:if>
										</c:forEach>
									</a>
								</div>
								<div class="book-info" style="height: 52px;">
									<h2 class="book-info-h2">${book.name }</h2>
								</div>
								<div class="book-cover-c">
									<i class="fa fa-list-ul"></i> ${book.category.name }
								</div>
								<div class="book-cover-stat">
									<i class="fa fa-eye"></i><span class="f10">
										&nbsp;${book.browse }</span> <i class="fa fa-heart"></i><span
										class="f10"> &nbsp;${book.collection }</span>
									<div class="cover-yh">
										<a title="" data-original-title=""
											href="${book.user.userName }" data-container="body"
											data-toggle="popover" data-placement="top"
											data-content="56789a1987 "> <i class="fa fa-user-secret"></i>
										</a>
									</div>
								</div>
								<div class="book-cover-btn">
									<a href="<%=beaspPath%>/book/view/${book.id}">我要${book.state.name }</a>
								</div>
								<div class="zzc"></div>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="book-list" style="display: none;">
					<ul class="cf">
						<c:forEach items="${newBooks }" var="book">
							<li>
								<div class="bookimg-box">
									<a href="#"> <c:forEach items="${book.styles }" var="style">
											<c:if test="${style.choice eq true }">
												<img class="bookimg" alt=""
													src="<%=beaspPath%>${style.imageFullPath}">
											</c:if>
										</c:forEach>
									</a>
								</div>
								<div class="book-info" style="height: 52px;">
									<h2 class="book-info-h2">${book.name }</h2>
								</div>
								<div class="book-cover-c">
									<i class="fa fa-list-ul"></i> ${book.category.name }
								</div>
								<div class="book-cover-stat">
									<i class="fa fa-eye"></i><span class="f10">
										&nbsp;${book.browse }</span> <i class="fa fa-heart"></i><span
										class="f10"> &nbsp;${book.collection }</span>
									<div class="cover-yh">
										<a title="" data-original-title=""
											href="${book.user.userName }" data-container="body"
											data-toggle="popover" data-placement="top"
											data-content="56789a1987 "> <i class="fa fa-user-secret"></i>
										</a>
									</div>
								</div>
								<div class="book-cover-btn">
									<a href="<%=beaspPath%>/book/view/${book.id}">我要${book.state.name }</a>
								</div>
								<div class="zzc"></div>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="book-list" style="display: none;">
					<ul class="cf">
						<c:forEach items="${shareBooks }" var="book">
							<li>
								<div class="bookimg-box">
									<a href="#"> <c:forEach items="${book.styles }" var="style">
											<c:if test="${style.choice eq true }">
												<img class="bookimg" alt=""
													src="<%=beaspPath%>${style.imageFullPath}">
											</c:if>
										</c:forEach>
									</a>
								</div>
								<div class="book-info" style="height: 52px;">
									<h2 class="book-info-h2">${book.name }</h2>
								</div>
								<div class="book-cover-c">
									<i class="fa fa-list-ul"></i> ${book.category.name }
								</div>
								<div class="book-cover-stat">
									<i class="fa fa-eye"></i><span class="f10">
										&nbsp;${book.browse }</span> <i class="fa fa-heart"></i><span
										class="f10"> &nbsp;${book.collection }</span>
									<div class="cover-yh">
										<a title="" data-original-title=""
											href="${book.user.userName }" data-container="body"
											data-toggle="popover" data-placement="top"
											data-content="56789a1987 "> <i class="fa fa-user-secret"></i>
										</a>
									</div>
								</div>
								<div class="book-cover-btn">
									<a href="<%=beaspPath%>/book/view/${book.id}">我要${book.state.name }</a>
								</div>
								<div class="zzc"></div>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="book-list" style="display: none;">
					<ul class="cf">
						<c:forEach items="${exBooks }" var="book">
							<li>
								<div class="bookimg-box">
									<a href="#"> <c:forEach items="${book.styles }" var="style">
											<c:if test="${style.choice eq true }">
												<img class="bookimg" alt=""
													src="<%=beaspPath%>${style.imageFullPath}">
											</c:if>
										</c:forEach>
									</a>
								</div>
								<div class="book-info" style="height: 52px;">
									<h2 class="book-info-h2">${book.name }</h2>
								</div>
								<div class="book-cover-c">
									<i class="fa fa-list-ul"></i> ${book.category.name }
								</div>
								<div class="book-cover-stat">
									<i class="fa fa-eye"></i><span class="f10">
										&nbsp;${book.browse }</span> <i class="fa fa-heart"></i><span
										class="f10"> &nbsp;${book.collection }</span>
									<div class="cover-yh">
										<a title="" data-original-title=""
											href="${book.user.userName }" data-container="body"
											data-toggle="popover" data-placement="top"
											data-content="56789a1987 "> <i class="fa fa-user-secret"></i>
										</a>
									</div>
								</div>
								<div class="book-cover-btn">
									<a href="<%=beaspPath%>/book/view/${book.id}">我要${book.state.name }</a>
								</div>
								<div class="zzc"></div>
							</li>
						</c:forEach>
					</ul>
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
	<script src="<%=jsPath%>/ban.js"></script>
	<script type="text/javascript">var n=0;</script>
	<script type="text/javascript" src="<%=jsPath%>/m.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/common.js"></script>
	
</body>
</html>