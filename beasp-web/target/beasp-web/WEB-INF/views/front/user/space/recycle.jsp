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
<title>${user.nickName }的回收站-个人空间-书籍交换与分享平台</title>
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
				<div class="family">
					<h1 class="family-hd">我的回收站</h1>
				</div>
				<div class="book-tool-bar cf">
					<div class="tool-left l">
						<a class="sort-item active" href="<%=beaspPath %>/space/index">回收站</a>
					</div>
					<div class="tool-right r">
						<span class="tool-item total-num">
							共<b>${recycleCount }</b>个书籍
						</span>
						<c:if test="${recycleCount gt 0 }">
							<span class="tool-item tool-pager">
								<span class="pager-num">
									<b class="pager-cur">1</b>/<em class="pager-total">1</em>
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
						
							<span class="tool-item tool-remove js-program-remove-edit" data-edit="0">
								<i class="icon icon-del"></i>
								<b>删除</b>
							</span>
							<span class="tool-item tool-backup js-program-backup-edit" data-edit="0">
								<i class="fa fa-share"></i>
								<b>还原</b>
							</span>							
						</c:if>
					</div>
				</div>
				<c:if test="${empty page or page.numberOfElements eq 0}">
					<ul class="follow-list">
			          <div class="unbook">
				        <p>暂无任何回收站书籍</p>
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
																		<i class="btn-add-collection <c:if test='${fn:contains(collectionstrs, book.id) }'>btn-remove-collection </c:if>"></i>
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
															<span class="backup"></span>
														</li>
													</c:if>		              
												</c:forEach>
											</ul>
										</div>
									</div>
								</c:forEach>
							</div>
							<jsp:include page="/WEB-INF/views/common/frontSpacePaging-imooc.jsp"></jsp:include>
						</div>
					</div>
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
			$(".js-book-list .del").backUpOrRemove({isRecycle:true});
		})
	</script>
</body>
</html>