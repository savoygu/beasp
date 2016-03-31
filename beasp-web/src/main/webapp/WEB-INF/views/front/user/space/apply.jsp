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
<title>${user.nickName }的求书籍-书籍交换与分享平台</title>
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
					<h1 class="l family-hd">我的求书籍</h1>
				</div>
				<!-- 工具条开始 -->
				<div class="book-tool-bar cf">
					<div class="tool-left l">
						<a class="sort-item <c:if test='${state eq "require" }'>active</c:if>" href="<%=beaspPath %>/space/require">我的申请</a>
						<a class="sort-item <c:if test='${state eq "apply" }'>active</c:if>" href="<%=beaspPath %>/space/apply">我的提供</a>
					</div>
					<div class="tool-right r">
						<div class="tool-all">
							<span id="js-columnall" class="tool-item">
								<!-- 切换类别开始 -->
								<c:if test="${empty soe or soe le 0}">
									<strong>全部</strong>
								</c:if>
								<c:if test="${soe eq 1 }">
									<strong>分享</strong>
								</c:if>
								<c:if test="${soe eq 2 }">
									<strong>交换</strong>
								</c:if>
								<!-- 切换类别结束 -->
								<i class="tool-item icon icon-down"></i>
							</span>
							<ul id="js-columnbd" class="all-cont" style="margin-left: -59px;">
								<c:if test='${state eq "require" }'>
									<li><a href="<%=beaspPath%>/space/require" data-id="0">全部</a></li>
									<li><a href="<%=beaspPath%>/space/require/soe/1" data-id="1" >分享</a></li>
									<li><a href="<%=beaspPath%>/space/require/soe/2" data-id="2" >交换</a></li>
								</c:if>
								<c:if test='${state eq "apply" }'>
									<li><a href="<%=beaspPath%>/space/apply" data-id="0">全部</a></li>
									<li><a href="<%=beaspPath%>/space/apply/soe/1" data-id="1" >分享</a></li>
									<li><a href="<%=beaspPath%>/space/apply/soe/2" data-id="2" >交换</a></li>
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
				        <p>暂无任何<c:if test="${way lt 1 or way gt 1 or empty way }">我的申请</c:if><c:if test="${way eq 1 }">我的提供</c:if>书籍</p>
				      </div>
			        </ul>
				</c:if>
				<c:if test="${page != null && page.numberOfElements > 0  }">
					<div id="J-MyClass" class="main-bd cf book-list js-book-list cls-box">
						<ul>
							<c:forEach items="${page.content }" var="applyBook">
								<li class="book-one">
									<div id="topic_${applyBook.id }">
										<c:if test="${applyBook.state eq 'WAITUPLOAD' }">
											<i class="ddsc">等待上传</i>
										</c:if>
										<c:if test="${applyBook.state eq 'UPLOADED' }">
											<i class="yjsc">已经上传</i>
										</c:if>
										<c:if test="${applyBook.state eq 'WAITSURE' }">
											<i class="ddqr">等待确认</i>
										</c:if>
										<c:if test="${applyBook.state eq 'WAITVERIFY' }">
											<i class="ddsh">等待审核</i>
										</c:if>
										<c:if test="${applyBook.state eq 'VERIFYPASS' }">
											<i class="shtg">审核通过</i>
										</c:if>
										<h2 style="text-overflow: ellipsis;">书籍名：${applyBook.bookName }(<c:if test="${applyBook.shareExchange eq 1 }">分享</c:if><c:if test="${applyBook.shareExchange eq 2 }">交换</c:if>)</h2>
										<div class="cf">
											<p class="l" style="width: 70%; text-overflow: ellipsis;">作者：${applyBook.bookAuthor }</p>
											<p class="l">版本：${applyBook.bookVersion }</p>									
										</div>
										<p class="cf">
											<span>
												<i class="cl-icon user"></i>
												<span class="username" title="${applyBook.requirer.userName }">${applyBook.requirer.userName }</span>
											</span>
											<span class="gray">
												<i class="cl-icon time"></i>
												${create_time[applyBook.id] }
											</span>
											<!-- <span class="upvote clk" data-id="7056">
												<i class="cl-icon laud"></i>
												<i class="clk-number">1</i>
											</span> -->
										</p>
										<blockquote>
											<p class="clip no_link">${applyBook.description }</p>
										</blockquote>
										<c:if test="${applyBook.state eq 'WAITSURE' && user.id eq applyBook.requirer.id }">
											<a class="upload" href="<%=beaspPath%>/space/book/sure?applyBookId=${applyBook.id}">确认书籍</a>
										</c:if>
										<c:if test="${applyBook.state ne 'WAITUPLOAD' }">
											<div class="info">
												<h2>书籍名称：${applyBook.book.name }</h2>
												<p>书籍作者：${applyBook.book.author }</p>
												<p>书籍版本：${applyBook.book.version }</p>
												<p>书籍语言：${applyBook.book.language }</p>
												<p>书籍类别：${applyBook.book.category.name }</p>
												<p>出版社：${applyBook.book.press }</p>
												<p>出版时间：${applyBook.book.issueTime }</p>
												<p>分享Or交换：<c:if test="${applyBook.book.share eq true }">分享</c:if><c:if test="${applyBook.book.exchange eq true }">交换</c:if></p>
												<p>书籍图片：
													<c:forEach items="${applyBook.book.styles }" var="style">
														<c:if test="${style.visible eq true }">
															<img alt="${applyBook.book.name }" height="40" width="80" src="${style.imageFullPath }">
														</c:if>
													</c:forEach>
												</p>
												<p>书籍描述：${applyBook.book.summary }</p>
											</div>
										</c:if>
									</div>
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
	<script type="text/javascript" src="<%=jsPath%>/core/jquery.beasp.book.js"></script>
	<script type="text/javascript" src="<%=layerPath%>/layer.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/space_common.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/book/book.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//删除书籍
			$(".js-book-list .del").backUpOrRemove();
			//同意分享or不同意显示
			$(".book-one").on({
				mouseenter:function() {
					$(this).find(".book-oper").css("display","block");
				},
				mouseleave:function() {
					$(this).find(".book-oper").css("display","none");
				}
			});
			//同意分享与不同意操作
			$("#js-aggre-share").on("click", function() {
				var $this = $(this),
					id = $this.data("bookid");
				$.ajax({
					type:"post",
					url:"/space/ajaxagreeshare",
					dataType:"json",
					data:{book:id},
					success:function(data) {
						if(data.status==0) {
							layer.msg('同意分享成功', {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
							$this.parents(".book-one").find(".book-state h2").text("已分享");
							$this.parents(".book-oper").remove();
						}else{
		                    layer.msg('同意分享失败，请稍后再试', {icon:2, time:2000, shade: [0.8, '#393D49'], shift:6});
		                }
					},
					error:function() {
						layer.msg('网络错误，请稍后再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
					}
				})
			});
			$("#js-disaggre-share").on("click", function() {
				var $this = $(this),
					id = $this.data("bookid");
				$.ajax({
					type:"post",
					url:"/space/ajaxdisagreeshare",
					dataType:"json",
					data:{book:id},
					success:function(data) {
						if(data.status==0) {
							layer.msg('不同意分享成功', {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
							$this.parents(".book-one").find(".book-state h2").text("分享失败");
							$this.parents(".book-oper").remove();
						}else{
		                    layer.msg('不同意分享失败，请稍后再试', {icon:2, time:2000, shade: [0.8, '#393D49'], shift:6});
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