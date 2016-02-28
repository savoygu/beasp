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
<title>${user.nickName }的交换-书籍交换与交换平台</title>
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
					<h1 class="l family-hd">我的交换</h1>
				</div>
				<!-- 工具条开始 -->
				<div class="book-tool-bar cf">
					<div class="tool-left l">
						<a class="sort-item <c:if test='${empty way or way < 1 or way > 1 }'>active</c:if>" href="<%=beaspPath %>/space/exchange">TA人交换</a>
						<a class="sort-item <c:if test='${way eq 1 }'>active</c:if>" href="<%=beaspPath %>/space/exchange/w/1">交换TA人</a>
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
								<c:if test='${empty way or way < 1 or way > 1 }'>
									<li><a href="<%=beaspPath%>/space/exchange/cid/0" data-id="0">全部</a></li>
									<c:forEach items="${categories }" var="c">
										<li><a href="<%=beaspPath%>/space/exchange/cid/${c.id}" data-id="${c.id }" >${c.name }</a></li>
									</c:forEach>
								</c:if>
								<c:if test="${!empty way and way eq 1 }">
									<li><a href="<%=beaspPath%>/space/exchange/w/${way}/cid/0" data-id="0">全部</a></li>
									<c:forEach items="${categories }" var="c">
										<li><a href="<%=beaspPath%>/space/exchange/w/${way}/cid/${c.id}" data-id="${c.id }">${c.name }</a></li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
						<span class="tool-item total-num">
							共<b>${count }</b>个书籍
						</span>
						<c:if test="${page.totalPages gt 0 }">
							<c:if test="${count gt 0 }">
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
								<!-- <span class="tool-item tool-remove js-program-edit" data-edit="0">
									<i class="icon icon-del"></i>
									<b>删除</b>
								</span> -->
							</c:if>
						</c:if>
					</div>
				</div>
				<!-- 工具条结束-->
				<!-- 个人书籍展示开始 -->
				<c:if test="${empty page or page.numberOfElements eq 0}">
					<ul class="follow-list">
			          <div class="unbook">
				        <p>暂无任何<c:if test="${way lt 1 or way gt 1 or empty way }">TA人交换</c:if><c:if test="${way eq 1 }">交换TA人</c:if>书籍</p>
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
												<c:forEach items="${page.content }" var="exchange">
													<c:set var="createTime">
														<fmt:formatDate value="${exchange.applyTime }" pattern="yyyy-MM-dd"/>
													</c:set>
													<c:if test="${createTime eq curTime}">
														<li class="book-one" data-id="${exchange.target.id }">
															
															<c:if test="${empty way }"><c:set var="way" value="0"></c:set></c:if>
															
															<a href="<%=beaspPath%>/space/exchange/w/${way}/view/${exchange.id}">
																<div class="book-state">
																	<h2>${exchange.state.name }</h2>
																</div>
																<div class="book-list-img">
																	<c:forEach items="${exchange.target.styles }" var="style">
																		<c:if test="${style.choice eq true }">
																			<img alt="${exchange.target.name }" src="<%=beaspPath %>${style.imageFullPath}" height="135" width="240">
																		</c:if>
																	</c:forEach>
																	<div class="media-progress">
																		<div class="mask"></div>
																	</div>
																	<c:if test="${fn:length(collections) != 0 }">
																		<i class="btn-add-collection <c:if test='${!empty collection_book[exchange.target.id]}'>btn-remove-collection </c:if>"></i>
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
																<h5><span>${exchange.target.name }</span></h5>
																<div class="tips">
																	<p class="text-ellipsis">${exchange.target.summary }</p>
																	<span class="l"><i class="fa fa-list-ul"></i> ${exchange.target.category.name }</span>
																	<span class="l mar-l20"><i class="fa fa-eye"></i> ${exchange.target.browse }</span>
																</div>
																<span class="time-label">${exchange.target.author }</span>
															</a>
															
															<%-- <c:if test="${way lt 1 or way gt 1 or empty way }">
																<c:if test="${exchange.state eq 'EXCHANGEING' }">
																	<div class="book-oper" style="display: none;">
																		<a href="<%=beaspPath%>/user/exchange/${exchange.target.id}/aggre" >同意交换</a>
																		<a href="<%=beaspPath%>/user/exchange/${exchange.target.id}/disaggre">不同意</a>
																		<a href="javascript:void(0)" data-bookId="${exchange.target.id }" id="js-aggre-exchange">同意交换</a>
																		<a href="javascript:void(0)" data-bookId="${exchange.target.id }" id="js-disaggre-exchange">不同意</a>
																	</div>
																</c:if>
															</c:if> --%>
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
			//同意交换or不同意显示
			$(".book-one").on({
				mouseenter:function() {
					$(this).find(".book-oper").css("display","block");
				},
				mouseleave:function() {
					$(this).find(".book-oper").css("display","none");
				}
			});
			//同意交换与不同意操作
			$("#js-aggre-exchange").on("click", function() {
				var $this = $(this),
					id = $this.data("bookid");
				$.ajax({
					type:"post",
					url:"/space/ajaxagreeexchange",
					dataType:"json",
					data:{book:id},
					success:function(data) {
						if(data.status==0) {
							layer.msg('同意交换成功', {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
							$this.parents(".book-one").find(".book-state h2").text("已交换");
							$this.parents(".book-oper").remove();
						}else{
		                    layer.msg('同意交换失败，请稍后再试', {icon:2, time:2000, shade: [0.8, '#393D49'], shift:6});
		                }
					},
					error:function() {
						layer.msg('网络错误，请稍后再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
					}
				})
			});
			$("#js-disaggre-exchange").on("click", function() {
				var $this = $(this),
					id = $this.data("bookid");
				$.ajax({
					type:"post",
					url:"/space/ajaxdisagreeexchange",
					dataType:"json",
					data:{book:id},
					success:function(data) {
						if(data.status==0) {
							layer.msg('不同意交换成功', {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
							$this.parents(".book-one").find(".book-state h2").text("交换失败");
							$this.parents(".book-oper").remove();
						}else{
		                    layer.msg('不同意交换失败，请稍后再试', {icon:2, time:2000, shade: [0.8, '#393D49'], shift:6});
		                }
					},
					error:function() {
						layer.msg('网络错误，请稍后再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
					}
				})
			})
		})
	</script>
</body>
</html>