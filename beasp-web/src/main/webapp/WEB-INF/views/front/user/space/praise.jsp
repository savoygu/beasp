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
<title>我的点赞-${user.nickName }的个人空间-书籍交换与分享平台</title>
<link rel="stylesheet" href="<%=cssPath%>/font-awesome.min.css">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/user/space/index.css">
<%-- <link rel="stylesheet" href="<%=layerPath%>/skin/layer.css"> --%>
<style type="text/css">
 	body{padding-top: 50px;}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/frontNav-custom.jsp"></jsp:include>
	
	<section id="main">
		<div class="main-body w-1200 container cf">
			<div class="l">
				<jsp:include page="/WEB-INF/views/common/spaceSider.jsp"></jsp:include>
			</div>
			<div class="r space-main">
				<div class="family cf">
					<h1 class="l family-hd">我的点赞</h1>
				</div>
				<!-- 工具条开始 -->
				<div class="book-tool-bar cf">
					<div class="tool-left l">
						<a class="sort-item active" href="<%=beaspPath %>/space/u/uid/${id}">我的点赞</a>
					</div>
					<div class="tool-right r">
						<div class="tool-all">
							<span id="js-columnall" class="tool-item">
								<!-- 切换类别开始 -->
								<c:if test="${empty cId or cId le 0}">
									<strong>全部</strong>
								</c:if>
								<c:if test="${!empty cId && cId > 0}">
									<c:forEach items="${categories }" var="c">
										<c:if test="${cId eq c.id }">
											<strong>${c.name }</strong>
										</c:if>
									</c:forEach>
								</c:if>
								<!-- 切换类别结束 -->
								<i class="tool-item icon icon-down"></i>
							</span>
							<ul id="js-columnbd" class="all-cont" style="margin-left: -59px;">
								<c:if test='${empty status or status < 1 or status > 3 }'>
									<li><a href="<%=beaspPath%>/space/u/uid/${id}/cid/0" data-id="0">全部</a></li>
									<c:forEach items="${categories }" var="c">
										<li><a href="<%=beaspPath%>/space/u/uid/${id}/cid/${c.id}" data-id="${c.id }" >${c.name }</a></li>
									</c:forEach>
								</c:if>
								<c:if test="${!empty status and status ge 1 and status le 3 }">
									<li><a href="<%=beaspPath%>/space/u/uid/${id}/book/s/${status}/cid/0" data-id="0">全部</a></li>
									<c:forEach items="${categories }" var="c">
										<li><a href="<%=beaspPath%>/space/u/uid/${id}/book/s/${status}/cid/${c.id}" data-id="${c.id }">${c.name }</a></li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
						<span class="tool-item total-num">
							共<b>${praiseCount }</b>个书籍
						</span>
						<c:if test="${collectionCount gt 0 }">
							<span class="tool-item tool-pager">
								<span class="pager-num">
									<b class="pager-cur">${page.number+1 }</b>/<em class="pager-total">${page.totalPages }</em>
								</span>
								<c:if test="${(page.number + 1) eq 1}">
									<a class="pager-action pager-prev hide-text disabled" href="javascript:void(0)">上一页</a>
									</c:if>
								<c:if test="${(page.number + 1) ne 1}">
									<a class="pager-action pager-prev hide-text" href="?pageNo=${(pageNumber+1) - 1}">上一页</a>
								</c:if> 
								<c:if test="${(page.number + 1) eq page.totalPages }">
									<a class="pager-action pager-next hide-text disabled" href="javascript:void(0)">下一页</a>
								</c:if>
								<c:if test="${(page.number + 1) ne page.totalPages}">
									<a class="pager-action pager-next hide-text" href="?pageNo=${(pageNumber+1) + 1}">下一页</a>
								</c:if> 
							</span>
						</c:if>
					</div>
				</div>
				<!-- 工具条结束-->
				<!-- 个人书籍展示开始 -->
				<c:if test="${empty page or page.numberOfElements eq 0}">
					<ul class="follow-list">
			          <div class="unbook">
				        <p>暂无任何<c:if test="${status lt 1 or status gt 3 or empty status }">待审核</c:if><c:if test="${status eq 1 }">交换</c:if><c:if test="${status eq 2 }">分享</c:if><c:if test="${status eq 3 }">发布</c:if>书籍</p>
				      </div>
			        </ul>
				</c:if>
				<c:if test="${page != null && page.numberOfElements > 0  }">
					<div id="J-MyClass" class="main-bd cf book-list js-book-list">
						<ul>
							<c:forEach items="${page.content }" var="bu">
								<li class="book-one" data-id="${bu.book.id  }">
									<a target="_blank" href="<%=beaspPath%>/book/view/${bu.book.id}">
										<div class="book-list-img">
											<c:forEach items="${bu.book.styles }" var="style">
												<c:if test="${style.choice eq true }">
													<img alt="${bu.book.name }" src="<%=beaspPath %>${style.imageFullPath}" height="135" width="240">
												</c:if>
											</c:forEach>
											<div class="media-progress">
												<div class="mask"></div>
											</div>
											<c:if test="${fn:length(collections) != 0 }">
												<i class="btn-add-collection <c:if test='${fn:contains(collectionstrs, book.id) }'>btn-remove-collection </c:if>"></i>
											</c:if>
											<c:if test="${fn:length(collections) == 0 }">
												<i class="btn-add-collection"></i>
											</c:if>
										</div>
										<h5><span>${bu.book.name }</span></h5>
										<div class="tips">
											<p class="text-ellipsis">${bu.book.summary }</p>
											<span class="l"><i class="fa fa-list-ul"></i> ${bu.book.category.name }</span>
											<span class="l mar-l20"><i class="fa fa-eye"></i> ${bu.book.browse }</span>
										</div>
										<span class="time-label">${bu.book.author }</span>
									</a>
								</li>
							</c:forEach>
						</ul>
						<!-- 个人书籍展示结束 -->
						<jsp:include page="/WEB-INF/views/common/frontSpacePaging-imooc.jsp"></jsp:include>
						<!-- 分页导航结束 -->
					</div>
					<!-- 分页导航开始 -->
				</c:if>
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=layerPath%>/layer.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/space_common.js"></script>
</body>
</html>