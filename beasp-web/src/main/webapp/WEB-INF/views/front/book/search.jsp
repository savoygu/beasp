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
<title>书籍交换与分享平台-搜索页</title>

<!-- Bootstrap -->
<%-- <link href="<%=cssPath%>/bootstrap.min.css" rel="stylesheet"> --%>
<link href="<%=cssPath%>/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/book/search.css">
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
	
	<section id="main">
		<div class="search-container">
			<div class="search-banner">
				<div class="search-box cf" data-search="search-page">
					<div class="suggest-input-box l">
						<input class="suggest-input J-suggest-input" type="text" value="${words }" autocomplete="off" 
							placeholder="请输入想搜索的内容..." data-suggest-trigger="suggest-trigger">
						<s class="btn-text-clear" title="清空" data-clear-btn="clear-btn">清空</s>
					</div>
					<input class="btn-search" type="button" value="搜索" data-search-btn="search-btn">
				</div>
			</div>
			<div class="search-main cf">
				<div class="search-result l">
					<ul class="type-list J-type-list cf">
						<li class="type-item selected" data-type="书籍">书籍</li>
						<li class="type-item" data-type="类别">类别</li>
						<li class="move-light" style="left: 0px; width: 120px;overflow: hidden;"></li>
					</ul>
					<div class="result-list J-result-list">
						<div class="result-header cf">
							为您找到到相关<span>书籍${pageView.totalRecord }</span>个
						</div>
						<ul class="search-book">
							<c:if test="${fn:length(pageView.records) > 0}">
								<c:forEach items="${pageView.records }" var="book">
									<li class="book-item border-btm">
										<div class="book-item-content cf">
											<div class="thumbnail">
												<div class="thumbnail-inner">
													<a target="_blank" href="<%=beaspPath %>/book/view/${book.id}">
														<c:forEach items="${book.styles }" var="style">
															<c:if test="${style.choice eq true }">
																<img src="<%=beaspPath %>${style.imageFullPath}"/>
															</c:if>
														</c:forEach>
													</a>
												</div>
											</div>
											<div class="introduction">
												<h2 class="title autowrap">
													<a href="<%=beaspPath %>/book/view/${book.id}" target="_blank">${book.name }</a>
												</h2>
												<div class="chapter autowrap">书籍作者： ${book.author }</div>
												<div class="description autowrap">书籍简介：${book.summary }</div>
											</div>
										</div>
									</li>
								</c:forEach>
							</c:if>
						</ul>
						<jsp:include page="/WEB-INF/views/common/frontSearchPaging-imooc.jsp"></jsp:include>
					</div>
				</div>
				<div class="hot-search r"></div>
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>

	<a href="javascript:;" id="back-to-top" title="回到顶部"></a>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<%-- <script src="<%=jsPath%>/bootstrap.min.js"></script> --%>
	<script type="text/javascript" src="<%=jsPath%>/front/common.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/book/search.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/user/login-regist.js"></script>
</body>
</html>