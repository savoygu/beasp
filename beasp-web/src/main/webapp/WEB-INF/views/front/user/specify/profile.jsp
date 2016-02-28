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
<title>TA的点赞-${spuser.nickName }的个人空间-书籍交换与分享平台</title>
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
				<jsp:include page="/WEB-INF/views/common/specifySider.jsp"></jsp:include>
			</div>
			<div class="r space-main">
				<div class="profile-hd">
					<h2>详细信息</h2>
				</div>
				<div class="main-bd cf">
					<ul class="profile-detail-info">
						<li class="profile-li"><span>昵称：</span><em>${spuser.nickName }</em></li>
						<li class="profile-li"><span>性别：</span><em>${spuser.gender.name }</em></li>
						<li class="profile-li"><span>Email：</span><em>${spuser.email }</em></li>
						<li class="profile-li"><span>手机号：</span><em>${spuser.phone }</em></li>
						<li class="profile-li"><span>学校：</span><em>${spuser.school }</em></li>
						<li class="profile-li"><span>签名：</span><em>${spuser.summary }</em></li>
						<li class="profile-li city">
							<span>所在城市：</span>
							<em>${spuser.province }</em>
							<em>${spuser.city }</em></li>
					</ul>
				</div>
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=layerPath%>/layer.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/space_common.js"></script>
</body>
</html>