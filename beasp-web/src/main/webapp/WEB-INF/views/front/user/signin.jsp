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
<title>登录界面-书籍交换与分享平台</title>

<!-- Bootstrap -->
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/user/signin.css">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    	body{padding-top: 50px;}
    </style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/frontNav-custom.jsp"></jsp:include>
	
	<section id="mainBg" class="main" style="background-color: rgb(47, 48, 52);">
		<div id="mainCnt" class="main-inner" style="background-image: url('/resources/images/user/140919_mailapp_cnt.jpg');">
			
			<div id="loginBlock" class="login tab-2">
				<div class="loginFunc">
					<div id="lbApp" class="loginFuncApp">二维码登录</div>
					<div id="idNormal" class="loginFuncNormal">邮箱账号登录</div>
				</div>
				<!-- 邮箱账号登录 -->
				<div id="normalLoginTab" class="loginForm" style="display: block;">
					<div class="msg" <c:if test='${!empty msg}'>style="background-color: #F2DEDE; color: #B84442;"</c:if> >
						<span style="padding: 10px;">${msg }</span>
					</div>
					<form action="${pageContext.request.contextPath }/account/signin" id="loginForm" method="POST" name="loginBeasp">
						<div id="idInputLine" class="loginFormIpt">
							<b class="ico ico-uid"></b>
							<input type="text" id="userName" class="inputClass" name="userName" placeholder="请输入账号/手机号/邮箱" value="${userName }${beaspUserName}">
							<div id="userNameError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.userName }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<div id="pwdInputLine" class="loginFormIpt">
							<b class="ico ico-pwd"></b>
							<input type="password" id="password" class="inputClass" name="password" placeholder="请输入密码" value="${password }${beaspPassword}">
							<div id="passwordError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.password }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<div id="codeInputLine" class="loginFormIpt">
							<input type="text" id="verifyCode" class="inputClass" name="verifyCode" placeholder="验证码" value="${verifyCode }">
							<div id="verifyCodeError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.verifyCode }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
							<div id="divVerifyCode">
								<img alt="验证码" id="imgVerifyCode" src="<%=beaspPath %>/VerifyCodeServlet.servlet"/>
							</div>
							<a href="javascript:_hyz()">换一张</a>
						</div>
						<div class="loginFormCheck">
							<div id="lfAutoLogin" class="loginFormCheckInner">
								<label id="remAutoLoginTxt" for="remAutoLogin">
									<input id="remAutoLogin" class="loginFormCbx" type="checkbox" title="十天内免登录" tabindex="3"/>十天内免登录
								</label>
							</div>
							<div class="forgetPwdLine">
								<a class="forgetPwd" title="找回密码" target="_blank" href="<%=beaspPath%>/account/newforgot<c:if test='${!empty fromurl }'>/fromurl/${fromurl }</c:if>">忘记密码了?</a>
							</div>
						</div>
						<input type="hidden" name="fromurl" value="${fromurl }">
						<div class="loginFormBtn">
							<button id="loginBtn" class="btn btn-main btn-login" type="submit" tabindex="6">登录</button>
							<a id="lfBtnReg" class="btn btn-side btn-reg" tabindex="7" target="_blank" href="<%=beaspPath%>/account/signup<c:if test='${!empty fromurl }'>/fromurl/${fromurl }</c:if>">注册</a>
						</div>
					</form>
				</div>
			</div>
			
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
		
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script src="<%=jsPath%>/front/user/signin.js"></script>
	<script src="<%=jsPath%>/core/jquery.beasp.user.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".inputClass").mouseEvent({
				isFocusBlur:true
			});
			$("#loginBtn").mouseEvent({
				overLeaveClz:"btn-login-hover", 
				downClz:"btn-login-active"}
			);
			$("#lfBtnReg").mouseEvent({
				overLeaveClz:"btn-reg-hover", 
				downClz:"btn-reg-active"
			});
			/*回显Cookie*/
			function echoCookie() {
				var userName = window.decodeURI("${cookie.beaspUserName.value}");
				var password = window.decodeURI("${cookie.beaspPassword.value}");
				alert(userName+"--"+password);
				if("${username}"&&"${password}"){
					userName = "${userName}";
					password = "${password}";
					alert(userName+"--"+password);
				}
				$("#userName").val(userName);
				$("#password").val(password);
			}
			/* echoCookie(); */ 
		})
	</script>
</body>
</html>