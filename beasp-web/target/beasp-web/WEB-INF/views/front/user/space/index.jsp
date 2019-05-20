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
<title>${user.nickName }的个人空间-书籍交换与分享平台</title>
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
					<h1 class="l family-hd">我的书籍</h1>
					<a class="r btn btn-upload" href="<%=beaspPath%>/space/book/b">上传书籍</a>
				</div>
				<!-- 工具条开始 -->
				<div class="book-tool-bar cf">
					<div class="tool-left l">
						<a class="sort-item <c:if test='${empty status or status < 1 or status > 4 }'>active</c:if>" href="<%=beaspPath %>/space/index">待审核</a>
						<a class="sort-item <c:if test='${status eq 4 }'>active</c:if>" href="<%=beaspPath %>/space/book/s/4">审核失败</a>
						<a class="sort-item <c:if test='${status eq 3 }'>active</c:if>" href="<%=beaspPath %>/space/book/s/3">发布</a>
						<a class="sort-item <c:if test='${status eq 2 }'>active</c:if>" href="<%=beaspPath %>/space/book/s/2">分享</a>
						<a class="sort-item <c:if test='${status eq 1 }'>active</c:if>" href="<%=beaspPath %>/space/book/s/1">交换</a>
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
								<c:if test='${empty status or status < 1 or status > 4 }'>
									<li><a href="<%=beaspPath%>/space/index/cid/0" data-id="0">全部</a></li>
									<c:forEach items="${categories }" var="c">
										<li><a href="<%=beaspPath%>/space/index/cid/${c.id}" data-id="${c.id }" >${c.name }</a></li>
									</c:forEach>
								</c:if>
								<c:if test="${!empty status and status ge 1 and status le 4 }">
									<li><a href="<%=beaspPath%>/space/book/s/${status}/cid/0" data-id="0">全部</a></li>
									<c:forEach items="${categories }" var="c">
										<li><a href="<%=beaspPath%>/space/book/s/${status}/cid/${c.id}" data-id="${c.id }">${c.name }</a></li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
						<span class="tool-item total-num">
							共<b>${count }</b>个书籍
						</span>
						<c:if test="${count gt 0 && !empty page && page.numberOfElements gt 0}">
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
							<span class="tool-item tool-remove js-program-edit" data-edit="0">
								<i class="icon icon-del"></i>
								<b>删除</b>
							</span>
						</c:if>
					</div>
				</div>
				<!-- 工具条结束-->
				<!-- 个人书籍展示开始 -->
				<c:if test="${empty page or page.numberOfElements eq 0}">
					<ul class="follow-list">
			          <div class="unbook">
				        <p>暂无任何<c:if test="${status lt 1 or status gt 4 or empty status }">待审核</c:if><c:if test="${status eq 1 }">交换</c:if><c:if test="${status eq 2 }">分享</c:if><c:if test="${status eq 3 }">发布</c:if><c:if test="${status eq 4 }">审核失败</c:if>书籍</p>
				      </div>
			        </ul>
				</c:if>
				<c:if test="${page != null && page.numberOfElements > 0  }">
				
					<div class="myspace-booklist myspace-bg r">
						<div id="J-MyClass" class="main-bd" data-type="0">
							<div class="js-book-list my-space-book study-tl">
								<c:forEach items="${times }" var="time" varStatus="statu">	
									<c:set var="curTime">
										<fmt:formatDate value="${time }" pattern="yyyy-MM-dd"/>
									</c:set>						
									<div class="tl-item <c:if test='${statu.count eq 1}'>tl-item-first</c:if> <c:if test='${statu.count eq fn:length(times) and statu.count ne 1}'>tl-item-last</c:if>">
										<span class="time">
											<b><fmt:formatDate value="${time }"  type="date" pattern="yyyy"/></b>
											<em><fmt:formatDate value="${time }" type="date"  pattern="MM月dd日"/></em>
										</span>
										<div class="book-list book-list-m three-column">
											<ul class="cf">
												<c:forEach items="${page.content }" var="book">
													<c:set var="createTime">
														<fmt:formatDate value="${book.createTime }" pattern="yyyy-MM-dd"/>
													</c:set>
													<c:if test="${createTime eq curTime}">
														<li class="book-one" data-id="${book.id }">
															<a href="<%=beaspPath%>/book/view/${book.id}">
																<div class="book-list-img">
																	<c:forEach items="${book.styles }" var="style">
																		<c:if test="${style.choice eq true }">
																			<img alt="${book.name }" src="<%=beaspPath %>${style.imageFullPath}" height="135" width="240">
																		</c:if>
																	</c:forEach>
																	<div class="media-progress">
																		<div class="mask"></div>
																	</div>
																	<c:if test="${fn:length(collections) != 0 }">
																		<i class="btn-add-collection <c:if test='${!empty collection_book[book.id]}'>btn-remove-collection </c:if>"></i>
																		<%-- <c:out value='${fn:split(collectionstrs, ",") }'></c:out> --%>
																		<%-- <c:forEach items='${fn:split(collectionstrs, ",") }' var="entry">
																			<c:out value="${entry }"></c:out>
																			<i class="btn-add-collection <c:if test="${entry eq book.id }">btn-remove-collection</c:if>"></i>
																		</c:forEach> --%>
																	</c:if>
																	<c:if test="${fn:length(collections) == 0 }">
																		<i class="btn-add-collection"></i>
																	</c:if>
																</div>
																<h5><span>${book.name }</span></h5>
																<div class="tips">
																	<p class="text-ellipsis">${book.summary }</p>
																	<span class="l"><i class="fa fa-list-ul"></i> ${book.category.name }</span>
																	<span class="l mar-l20"><i class="fa fa-eye"></i> ${book.browse }</span>
																</div>
																<span class="time-label">${book.author }</span>
															</a>
															<span class="del"></span>
															<a class="edit" href="<%=beaspPath%>/space/book/b/${book.id}"><i class="fa fa-edit"></i>编辑</a>
														</li>
													</c:if>		              
												</c:forEach>
											</ul>
										</div>
									</div>
								</c:forEach>
							</div>
							<!-- 个人书籍展示结束 -->
							<jsp:include page="/WEB-INF/views/common/frontSpacePaging-imooc.jsp"></jsp:include>
							<!-- 分页导航结束 -->
						</div>
					</div>
					<!-- 分页导航开始 -->
				</c:if>
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/core/jquery.beasp.book.js"></script>
	<script type="text/javascript" src="<%=layerPath%>/layer.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/space_common.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/book/book.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//删除书籍
			$(".js-book-list .del").backUpOrRemove();
		})
	</script>
</body>
</html>