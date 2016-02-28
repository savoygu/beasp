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
<title>忘记密码-书籍交换与分享平台</title>
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/user/settings/forgot.css">
</head>
<body>
	<div id="head" class="sig-head">
		<a class="sig-img" target="_self" href="<%=beaspPath%>/">书籍交换与分享平台</a>
	</div>
	<div id="main">
		<div class="wcontainer">
			<div class="wwrap">
				<div class="page-forgotpwd">
					<div class="page-forgotpwd-wrap">
						<div class="js-forgotpwd-form-wrap">
							<div class="fgtpaw-wrap">
								<h2>忘记密码</h2>
							</div>
							<div class="link-info">
								<h3>通过注册邮箱链接重设密码</h3>
								<a class="right-back" href="<%=beaspPath%>/account/signin">返回立即登录</a>
							</div>
							<form action="${pageContext.request.contextPath }/account/newforgot" method="post" id="js-forgot-form">
								<p id="js-g-forgot-error" class="tips tips-error"></p>
								<div class="form-control">
									<input id="email" class="ipt ipt-email inputClass" type="text" placeholder="请输入登录邮箱" name="email" value="${email }">
									<p id="emailError" class="tips errorClass">${errors.email }</p>
								</div>
								<div class="form-control form-control-verify">
									<input id="verifyCode" class="ipt inputClass" type="text" placeholder="请输验证码" name="verifyCode">
									<img class="verify-img js-change-verify-code" alt="验证码" id="imgVerifyCode" src="<%=beaspPath %>/VerifyCodeServlet.servlet"/>
									<a href="javascript:_hyz()">换一张</a>
									<p id="verifyCodeError" class="tips errorClass">${errors.verifyCode }</p>
								</div>
								<input type="hidden" name="fromurl" value="${fromurl }">
								<div>
									<button id="forgot-submit" class="btn btn-red btn-block link-btn" aria-role="button" type="submit">提交</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div> 
		</div>
	</div>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script src="<%=jsPath%>/front/user/settings/newforgot.js"></script>
	
</body>
</html>