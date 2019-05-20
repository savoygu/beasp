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
<title>忘记密码-链接过期-书籍交换与分享平台</title>
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet"
	href="<%=cssPath%>/front/user/settings/forgot.css">
</head>
<body>
	<div id="head" class="sig-head">
		<a class="sig-img" target="_self" href="<%=beaspPath%>/">书籍交换与分享平台</a>
	</div>
	<div id="main">
		<div class="wcontainer">
			<div class="wwrap wrap-boxes">
				<div class="wheader-wrap">
					<h1>重设密码</h1>
				</div>
				<div class="pwd-reset-result">
					<div class="pwd-rstsuc-inner">
						<p class="pwd-rstsuc-txt">重置密码链接已过期！</p>
					</div>
					<a href="<%=beaspPath %>/" class="rlf-btn-green btn-block-reset">返回首页</a>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script src="<%=jsPath%>/front/user/settings/newforgot.js"></script>

</body>
</html>