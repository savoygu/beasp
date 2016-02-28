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
<title>书籍交换与分享平台-单品页</title>

<!-- Bootstrap -->
<link href="<%=cssPath%>/bootstrap.min.css" rel="stylesheet">
<link href="<%=cssPath%>/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/book/view.css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/frontNav.jsp"></jsp:include>
	
	<!-- 导航菜单 -->
	<c:set var="menuout" value=""/>
	<c:forEach items="${menucates }" var="cate" varStatus="status">
			<c:set var="menuout" value="<a href='/book/list/category/${cate.id }'>${cate.name }</a><i class='path-split'>&#92;</i>${menuout }" />
	</c:forEach>
	
	<div id="main">
		<div class="book-infos">
			<div class="w-1200 pr">
				<div class="path">
					<a href="<%=beaspPath%>/">首页</a> <i class='path-split'>&#92;</i> <a
						href="<%=beaspPath%>/book/list/category/0">交换与分享</a> <i
						class='path-split'>&#92;</i>
					<c:out value="${menuout }" escapeXml="false"></c:out>
					${book.name }
				</div>
				<div class="book-name cf">
					<h2 class="l">${book.name }</h2>
				</div>
				<div class="book-details">
					<div class="book-details-item">
						<span class="meta-value"><strong>${book.language.name }</strong></span>
						<span class="meta">语言</span> <em></em>
					</div>
					<div class="book-details-item">
						<span class="meta-value"><strong><fmt:formatDate
									value="${book.issueTime }" type="date" dateStyle="long" /> </strong></span> <span
							class="meta">发布时间</span> <em></em>
					</div>
					<div class="book-details-item">
						<span class="meta-value"><strong><fmt:formatDate
									value="${book.createTime }" type="both" dateStyle="long" /> </strong></span> <span
							class="meta">上传时间</span> <em></em>
					</div>
				</div>
				<div class="extra">
					<div class="share-action r">
						分享 <a class="share wx js-share" data-cmd="weixin"
							href="javascript:;"></a> <a class="share qq js-share"
							data-cmd="qzone" href="javascript:;"></a> <a
							class="share sina js-share" data-cmd="tsina" href="javascript:;"></a>
					</div>
					<i class="split-line r"></i> 
					<c:if test="${collection eq false }">
						<a class="follow-action r js-follow-action" data-cid="${book.id }" data-cmd="follow" href="javascript:;">收藏</a>
					</c:if>
					<c:if test="${collection eq true }">
						<a class="follow-action r js-follow-action followed" data-cid="${book.id }" data-cmd="cancel" href="javascript:;">已收藏</a>
					</c:if>
					<i class="split-line r"></i>
					<c:if test="${praise eq false }">
						<i class="fa fa-thumbs-o-up"></i><a class="praise-action r js-praise-action" data-cid="${book.id }" data-cmd="praise" href="javascript:;">点赞</a>
					</c:if>
					<c:if test="${praise eq true }">
						<i class="fa fa-thumbs-up"></i><a class="praise-action r js-praise-action" data-cid="${book.id }" data-cmd="cancel" href="javascript:;">已点赞</a>
					</c:if>
				</div>
			</div>
			<div class="cover-mask"></div>
			<c:forEach items="${book.styles }" var="style">
				<c:if test="${style.choice eq true }">
					<img alt="${book.name }"
						src="<%=beaspPath %>${style.imageFullPath}">
				</c:if>
			</c:forEach>
		</div>
		<div class="book-infos-main w-1200">
				<!-- <div class="info-bar cf">
					<div class="info-bar-box">
						<a class="btn-purple start-study-btn r" href="">我要交换</a>
					</div>
				</div> -->
				<div class="content-wrap">
					<div class="content">
						<div class="book-brief">
							<h3 class="ctit">书籍介绍</h3>
							<p class="auto-wrap">书籍简介：${book.summary }</p>
						</div>
						<div class="book-evaluation">
							<h3>书籍评价</h3>
							<div class="evaluation-info cf">
								<p class="satisfaction">
									书籍：<em>${book.name }</em>
								</p>
								<!-- 评星 -->
								<!-- <p class="satisfaction">
									满意度评分：<em>10.0</em>
								</p>
								<div class="star-box">
									<i class="fa fa-star active"></i>
									<i class="fa fa-star active"></i>
									<i class="fa fa-star active"></i>
									<i class="fa fa-star active"></i>
									<i class="fa fa-star active"></i>
								</div> -->
								<p class="person-num"><em>${page.totalElements }</em>位同学参与评价</p>
							</div>
							<!-- 评价输入框-->
							<div id="disArea" class="lists-container list-wrap">
								<div id="pl-content" class="list-tab-con" style="padding-top: 30px;">
									<div id="discus-publish" class="publish cf">
										<div class="publish-wrap publish-wrap-pl">
											<div class="pl-input-wrap">
												<div id="js-pl-input-fake" class="pl-input-inner pr">
													<textarea id="js-pl-textarea" class="js-placeholder" placeholder="扯淡、吐槽、表扬、鼓励......想说啥就说啥！"></textarea>
													<span class="num-limit">
														<span id="js-pl-limit">0</span>/300
													</span>
												</div>
												<div class="pl-input-btm input-btm cf">
													<input id="js-pl-submit" class="r book-btn pub-editor-wrap" data-id="${book.id }" type="button" value="发表评论">
													<span style="display: none;" class="errortip r">内容不能少于5个字符！</span>
												</div>
											</div>
										</div>
									</div>
									<div class="plLoadListData">
										<div class="pl-container">
											<c:if test="${empty page or page.numberOfElements eq 0}">
												<ul class="follow-list">
										          <div class="unbook">
											        <p>暂无任何评论</p>
											      </div>
										        </ul>
											</c:if>
											<c:if test="${page != null && page.numberOfElements > 0  }">
												<ul>
													<c:forEach items="${page.content }" var="comment">
														<li class="pl-list cf" >
															<div class="pl-list-avator">
																<a target="_blank" href="/space/u/uid/${comment.user.id }">
																	<c:if test="${empty comment.user.photoName }">
																		<img height="40" width="40" title="${comment.user.userName }" src="<%=imgPath %>/user/default/40x/5458505c00018e9202200220-40-40.jpg" >
																	</c:if>
																	<c:if test="${!empty comment.user.photoName }">
																		<img height="40" width="40" title="${comment.user.userName }" src="<%=beaspPath %>${comment.user.image40FullPath}" >
																	</c:if>
																</a>
															</div>
															<div class="pl-list-main">
																<div class="pl-list-nick">
																	<c:if test="${user.id eq comment.user.id }">
																		<a target="_blank" href="/space/index">${comment.user.userName }</a>
																	</c:if>
																	<c:if test="${user.id ne comment.user.id }">
																		<a target="_blank" href="/space/u/uid/${comment.user.id }">${comment.user.userName }</a>
																	</c:if>
																</div>
																<div class="pl-list-content"><c:out value="${comment.content }" escapeXml="true"></c:out></div>
																<div class="pl-list-btm cf">
																	<span class="pl-list-time l">时间：${comment_time[comment.id] }</span>
																</div>
															</div>
														</li>
													</c:forEach>														
												</ul>
											</c:if>
										</div>
										<div class="page pl-list-page">
											
										</div>
									</div>
								</div>
								
								<!-- 评价列表 -->
							</div>
							
						</div>
					</div>
					<div class="aside r">
						<div class="info-bar-box cf">
							<c:if test="${book.share eq true }">
								<a class="r" href="<%=beaspPath %>/user/share/${book.id}">我要分享</a>
							</c:if>
							<c:if test="${book.exchange eq true }">
								<a class="r" href="<%=beaspPath %>/user/exchange/${book.id}">我要交换</a>
							</c:if>
						</div>
						<div class="bd">
							<div class="box mar-b40">
								<h4>用户详情</h4>
								<div class="user-info">
									<a target="_blank" href="<%=beaspPath %>/space/u/uid/${book.user.id}">
										<c:if test="${empty book.user.photoName }">
											<img width="80" height="80" title="${book.user.userName }" src="<%=imgPath %>/user/default/prototype/5458505c00018e9202200220-220-220.jpg" >
											<%-- <img width="80" height="80" title="${book.user.userName }" src="<%=beaspPath %>${book.user.defaultImage40Path}" > --%>
										</c:if>
										<c:if test="${!empty book.user.photoName }">
											<img width="80" height="80" title="${book.user.userName }" src="<%=beaspPath %>${book.user.imageFullPath}" >
										</c:if>
									</a>
									<span class="tit">
										<c:if test="${user.id eq book.user.id }">
											<a target="_blank" href="<%=beaspPath %>/space/index">${book.user.nickName }</a>
										</c:if>
										<c:if test="${user.id ne book.user.id }">
											<a target="_blank" href="<%=beaspPath %>/space/u/uid/${book.user.id}">${book.user.nickName }</a>
										</c:if>
									</span>
									<span class="job">${book.user.school }</span>
								</div>
								<div class="book-info-tip">
									<dl class="first">
										<dt>书籍须知</dt>
										<dd class="autowrap">1、书籍作者：${book.author }
										2、书籍出版社：${book.press }
										3、书籍版本：第<strong style="font-style: italic; margin: 0px 2px;">${book.version }</strong>版</dd>
									</dl>
									<c:if test="${book.exchange eq true }">
										<br>
										<dl class="first">
											<dt>交换须知：提供以下书籍之一</dt>
											<c:forEach items="${book.wants }" var="want" varStatus="statu">
												<dd>(第${statu.count }本)</dd>
												<dd class="autowrap">a、书籍名称：《${want.name }》
												b、书籍作者：${want.author }
												c、书籍版本：第<strong style="font-style: italic; margin: 0px 2px;">${want.version }</strong>版</dd>
											</c:forEach>
										</dl>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
		
	<a href="javascript:;" id="back-to-top" title="回到顶部"></a>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=layerPath%>/layer.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<%-- <script src="<%=jsPath%>/bootstrap.min.js"></script> --%>
	<script type="text/javascript" src="<%=jsPath%>/front/common.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/book/view.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		})	
	</script>
</body>
</html>