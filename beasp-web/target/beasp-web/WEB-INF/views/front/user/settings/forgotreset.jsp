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
<title>忘记密码-重设密码-书籍交换与分享平台</title>
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/user/settings/forgot.css">
</head>
<body>
	<div id="head" class="sig-head">
		<a class="sig-img" target="_self" href="<%=beaspPath%>/">书籍交换与分享平台</a>
	</div>
	<div id="main">
		<div class="wcontainer">
			<div class="wwrap wrap-boxes">
				<div class="pwd-reset-box">
					<div class="wel-hd">
						<h1 class="form-h1">重设密码</h1>
						<h2 class="user-email">帐号：<span>${email }</span></h2>
					</div>
					<form action="${pageContext.request.contextPath }/account/resetpasspage" method="post" id="js-forgot-form">
						<p id="js-g-forgot-error" class="tips tips-error">${resetPwdMsg }</p>
						<div class="form-control">
							<input id="newpwd" class="ipt ipt-email inputClass" type="password" placeholder="请输入新密码" name="newpwd" value="${newpwd }">
							<p id="newpwdError" class="tips errorClass"></p>
						</div>
						<div class="form-control">
							<input id="repwd" class="ipt ipt-email inputClass" type="password" placeholder="请输入确认新密码" name="repwd" value="${repwd }">
							<p id="repwdError" class="tips errorClass"></p>
						</div>
						<input type="hidden" name="email" value="${email }">
						<c:if test="${!empty fromurl && fromurl ne '${fromurl}'}">
							<input type="hidden" name="fromurl" value="${fromurl }">
						</c:if>
						<div>
							<button id="forgot-submit" class="btn btn-red btn-block btn-red-reset link-btn" aria-role="button" type="submit">提交</button>
						</div>
					</form>
				</div>
			</div> 
		</div>
	</div>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script src="<%=jsPath%>/front/user/settings/forgotreset.js"></script>
	
</body>
</html>