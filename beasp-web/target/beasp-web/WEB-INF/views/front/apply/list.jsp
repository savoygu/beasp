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
<title>求书籍-书籍交换与分享平台</title>

<!-- Bootstrap -->
<link href="<%=cssPath%>/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/apply/apply.css">
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
		<div class="w-1000">
			<!-- book need -->
			<div class="cls-need cf">
				<a id="subCourseBtn" class="greenbtn" href="#">提交书籍需求</a>
				<div class="desc">
					<p>
						<span>${page.totalElements }</span>
						需求 /
						<span>${requirer_count }</span>
						提交者</p>
				</div>
			</div>
			<!-- end book need -->
			<!-- sort -->
			<div class="sortApply mar-t20">
				<div class="wrap">
					<div class="sortMode">
						<dl class="person">
							<dt>
								<c:if test="${empty my }">所有人</c:if>
								<c:if test="${!empty my }">我提交的</c:if>
								<i class="arrow"></i>
							</dt>
							<dd>
								<p>
									<c:if test="${empty my }"><a class="need_login" href="<%=beaspPath%>/topic/my">我提交的</a></c:if>
									<c:if test="${!empty my }"><a class="need_login" href="<%=beaspPath%>/topic">所有人</a></c:if>
								</p>
							</dd>
						</dl>
						<dl class="status">
							<dt>
								<c:if test="${empty param.status }">需求状态</c:if>
								<c:if test="${param.status eq 1 }">等待上传</c:if>
								<c:if test="${param.status eq 2 }">已经上传</c:if>
								<c:if test="${param.status eq 3 }">等待确认</c:if>
								<c:if test="${param.status eq 4 }">等待审核</c:if>
								<c:if test="${param.status eq 5 }">审核通过</c:if>
								<c:if test="${param.status eq 0 }">全部</c:if>
								<i class="arrow"></i>
							</dt>
							<dd>
								<p>
									<a href='?status=1<c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>'>等待上传</a>
									<a href='?status=2<c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>'>已经上传</a>
									<a href='?status=3<c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>'>等待确认</a>
									<a href='?status=4<c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>'>等待审核</a>
									<a href='?status=5<c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>'>审核通过</a>
									<a href='?status=0<c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>'>全部</a>
								</p>
							</dd>
						</dl>
						<dl class="status">
							<dt>
								<c:if test="${empty param.soe }">性质</c:if>
								<c:if test="${param.soe eq 'share'}">分享</c:if>
								<c:if test="${param.soe eq 'exchange' }">交换</c:if>
								<i class="arrow"></i>
							</dt>
							<dd>
								<p>
									<a href='?soe=share<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>'>分享</a>
									<a href='?soe=exchange<c:if test="${!empty param.status }">&status=${param.status }</c:if><c:if test="${!empty param.order }">&order=${param.order }</c:if>'>交换</a>
								</p>
							</dd>
						</dl>
						<dl>
							<dt>
								<c:if test="${empty param.order }">排序</c:if>
								<c:if test="${param.order eq 'time_desc'}">发布时间倒序</c:if>
								<c:if test="${param.order eq 'time_asc' }">发布时间正序</c:if>
								<i class="arrow"></i>
							</dt>
							<dd>
								<p>
									<a href="?order=time_desc<c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.status }">&status=${param.status }</c:if>">发布时间倒序</a>
									<a href="?order=time_asc<c:if test="${!empty param.soe }">&soe=${param.soe }</c:if><c:if test="${!empty param.status }">&status=${param.status }</c:if>">发布时间正序</a>
								</p>
							</dd>
						</dl>
					</div>
				</div>
			</div>
			<!-- end sort -->
			<c:if test="${empty page or page.numberOfElements le 0 }">
				<div class="cls-box">
					<p style="height: 200px; line-height: 200px; text-align: center; font-size: 18px; color: #333333;">暂时没有任何需求书籍</p>
				</div>
			</c:if>
			<c:if test="${!empty page and page.numberOfElements > 0 }">
				<div class="cls-box">
					<ul class="cf">
						<c:if test="${!empty page && page.numberOfElements > 0 }">
							<c:forEach items="${page.content }" var="applyBook">
								<li>
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
										<c:if test="${applyBook.state eq 'WAITUPLOAD' && user.id ne applyBook.requirer.id }">
											<a class="upload" href="<%=beaspPath%>/space/book/b?applyBookId=${applyBook.id}" target="_blank">我要上传</a>
										</c:if>
									</div>
								</li>
							</c:forEach>
						</c:if>
					</ul>	
				</div>
	
				<jsp:include page="/WEB-INF/views/common/frontApplyPaging-imooc.jsp"></jsp:include>	
			</c:if>		
			
		</div>
	</section>
	
	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>

	<a href="javascript:;" id="back-to-top" title="回到顶部"></a>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<c:if test="${empty user or empty user.userName }">
		<script type="text/javascript">
			var OP_CONFIG = {
				"module" : "index",
				"page" : "index",
				"isLogin" : false,
			};
			var isLogin = 0;
			var ownName = "${user.email}";
		</script>
	</c:if>
	<c:if test="${!empty user and !empty user.userName }">
		<script type="text/javascript">
			var OP_CONFIG = {
				"module" : "book",
				"page" : "view",
				"isLogin" : true,
				"userInfo" : {
					"uid" : "${user.id}",
					"nickName" : "${user.nickName}",
					"photoName" : "http://localhost/"
							+ "${user.photoName}!=null?${user.image40FullPath}:${user.defaultImage40Path}"
				}
			};
			var isLogin = 1;
			var ownName = "${user.email}";
		</script>
	</c:if>
	<script type="text/javascript">
		window.WWW_URL='${WWW_URL}/';
	/* 	window.PASSPORT_URL='http://passport.jikexueyuan.com/'; */
		window.CURRENT_URL = '${CURRENT_URL}/';
		window.REQUEST_URL = '${REQUEST_URL}/';
		window.FROM_URL = '${FROM_URL}';
		window.COMPLETE_URL = '${COMPLETE_URL}';
	</script>
	<script type="text/javascript" src="<%=layerPath%>/layer.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<%-- <script src="<%=jsPath%>/bootstrap.min.js"></script> --%>
	<script type="text/javascript" src="<%=jsPath%>/front/common.js"></script>
	<script type="text/javascript"
		src="<%=jsPath%>/front/user/login-regist.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/frontend.min.1837abe6.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/frontend-topic.9f50b788.js"></script>
	<%-- <script type="text/javascript" src="<%=jsPath%>/frontend-topic.min.9f50b788.js"></script> --%>
	<!-- <div style="width: 650px; height: 380px; z-index: 9999; top: 126.5px; left: 349.5px; position: fixed;" class="dialog" id="dia_topic_subcourse">
		<div class="dialog-box">
			<div class="dialog-tit">
				<span class="poptit">提交课程需求</span>
				<span class="popclose"></span>
			</div>
			<div class="dialog-body">
				<div class="jkxy-form">
					<form>
						<input id="temp" value="" type="hidden">
						<div class="form-c">
							<div class="form-dom">
								<div class="form-tit"><i>*</i>课程标题：</div>
								<div class="form-input">
									<input class="jkxy-input" name="course" placeholder="控制在 24 个文字之内" maxlength="24" id="topic_course" type="text">
									<div class="form-tip">&nbsp;</div>
								</div>
							</div>
							<div class="form-dom">
								<div class="form-tit"><i>*</i>课程描述：</div>
								<div class="form-input">
									<textarea name="content" id="topic_content" class="jkxy-textarea topic-textarea" placeholder="控制在 500 个文字之内" maxlength="500"></textarea>
									<div class="form-tip">&nbsp;</div>
								</div>
							</div>
							<div class="form-dom">
								<div class="form-tit">&nbsp;</div>
								<div class="form-input">
									<a href="javascript:;" class="greenbtn" id="diaSubCourse">提交需求</a>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div style="z-index: 9989; background: rgb(0, 0, 0) none repeat scroll 0% 0%; opacity: 0.8; position: absolute; left: 0px; top: 0px; width: 100%; height: 1908px;" id="blacklayer"></div>
	 -->
	<script type="text/javascript">
		/* function() {
			  var a = $("#topic_course").val(),
			    c = $("#topic_content").val();
			  if (1 == b.attr("stop")) return !1;
			  if (!a) return tips(1, "请填写课程标题"), !1;
			  if (a.length > 24) return tips(1, "课程标题请控制在24个文字之内"), !1;
			  if (!c) return tips(1, "请填写课程描述"), !1;
			  if (c.length > 500) return tips(1, "课程描述请控制在500个文字之内"), !1;
			  if (!a || !c) return tips(1, "请补充完所有信息"), !1;
			  b.attr("stop", 1);
			  var d = {
			    title: a,
			    idea: c
			  };
			  $.ajax({
			    url: window.WWW_URL + "topic/create",
			    data: d,
			    dataType: "json",
			    type: "POST",
			    async: !1,
			    success: function(a) {
			      if (0 === a.error) {
			        $(".popclose").click(), tips(0, a.message);
			        a.data.topic.id;
			        setTimeout(function(a) {
			          window.location.href = window.WWW_URL + "topic"
			        }, 2e3)
			      } else tips(1, a.message)
			    },
			    error: function() {
			      tips(1, "系统错误")
			    }
			  }), b.removeAttr("stop")
			} */
		
	</script>
	
</body>
</html>