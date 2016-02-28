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
<title>书籍分享-书籍交换与分享平台</title>
<link rel="stylesheet" href="<%=cssPath%>/font-awesome.min.css">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/user/share/show.css">
<%-- <link rel="stylesheet" href="<%=layerPath%>/skin/layer.css"> --%>
<style type="text/css">
body {
	padding-top: 50px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/frontNav-custom.jsp"></jsp:include>

	<section id="main">
		<div class="main-body w-1200 container">
			<div class="share-main">
				
				<form:form method="post" action="${pageContext.request.contextPath }/user/share/${book.id }" modelAttribute="book">
					
					<div class="share-book-content">
						<div class="book-item-title">
							<h2>你要分享的书籍详细信息：</h2>
						</div>
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
					</div>
					<div class="share-user-content">
						<div class="user-item-title  pr">
							<h2>你要分享的书籍用户信息：</h2>
						</div>
						<div class="user-item-content cf  pr">
							<div class="thumbnail" >
								<div class="thumbnail-inner">
									<a target="_blank" href="<%=beaspPath %>/space/profile?uid=${book.user.id}">
										<c:if test="${empty book.user.photoName }">
											<img class="l" style="position: absolute; right: 0px;" src="<%=imgPath %>/user/default/prototype/5458505c00018e9202200220-220-220.jpg" alt="${book.user.nickName }" />
										</c:if>
										<c:if test="${!empty book.user.photoName }">
											<img class="l" style="position: absolute; right: 0px;" src="<%=beaspPath %>${book.user.imageFullPath}" alt="${book.user.nickName }" />
										</c:if>
									</a>
									
								</div>
							</div>
							<div class="introduction" >
								<h2 class="title autowrap" style="position: absolute; right: 230px;">
									<a href="<%=beaspPath %>/space/profile?uid=${book.user.id}" target="_blank">${book.user.nickName }</a>
								</h2>
								<div class="chapter autowrap" style="position: absolute; right: 230px; top: 31px;">${book.user.phone }：用户手机</div>
								<div class="description autowrap" style="position: absolute; right: 230px; top: 53px;">${book.user.school }：用户学校</div>
							</div>
						</div>
					</div>
					<form:hidden path="id"/>
					<input type="submit" class="share-btn-submit" value="确认分享"/>
				</form:form>
											
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	
</body>
</html>