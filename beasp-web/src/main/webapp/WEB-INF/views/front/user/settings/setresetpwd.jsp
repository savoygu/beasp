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
<title>${user.nickName }的修改密码-书籍交换与分享平台</title>
<link rel="stylesheet" href="<%=cssPath%>/font-awesome.min.css">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/user/settings/set.css">
<%-- <link rel="stylesheet" href="<%=layerPath%>/skin/layer.css"> --%>
<style type="text/css">
 	body{padding-top: 50px;}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/frontNav-custom.jsp"></jsp:include>
	
	<section id="main">
		<div class="wcontainer set-space-cont cf">
			<div class="setting-left l">
				<jsp:include page="/WEB-INF/views/common/settingsSider.jsp"></jsp:include>
			</div>
			<div class="setting-right">
				<div class="setting-right-wrap wrap-boxes settings">
					<div class="pwd-reset-wrap setting-resetpwd">
						<form action="${pageContext.request.contextPath }/user/setresetpwd" autocomplete="off" method="post" id="resetpwdForm">
							<c:if test="${success eq true }">
								<p class="pwdMsg successMsg">${pwdMsg }</p>
							</c:if>
							<c:if test="${success ne true }">
								<p class="pwdMsg">${pwdMsg }</p>
							</c:if>
							<div class="wlfg-wrap wlfg-wrap-h">
								<label class="label-name" for="oldpwd">当前密码</label>
								<div class="rlf-group">
									<input id="oldpwd" type="password" class="rlf-input rlf-input-pwd inputClass" placeholder="请输入当前密码" autocomplete="off" value="${oldpwdval }" name="oldpwd">
									<p id="oldpwdError" class="rlf-tip-wrap errorClass"></p>
								</div>
							</div>
							<div class="wlfg-wrap wlfg-wrap-h">
								<label class="label-name" for="newpwd">新密码</label>
								<div class="rlf-group">
									<input id="newpwd" type="password" class="rlf-input rlf-input-pwd inputClass" placeholder="请输入新密码" name="newpwd">
									<p id="newpwdError" class="rlf-tip-wrap errorClass"></p>
								</div>
							</div>
							<div class="wlfg-wrap wlfg-wrap-h">
								<label class="label-name" for="repwd">确认密码</label>
								<div class="rlf-group">
									<input id="repwd" type="password" class="rlf-input rlf-input-pwd inputClass" placeholder="请输入确认密码" name="repwd">
									<p id="repwdError" class="rlf-tip-wrap errorClass"></p>
								</div>
							</div>
							<div class="wlfg-wrap">
								<div class="rlf-group">
									<button id="resetpwd-submit" class="rlf-btn-purple btn-block profile-btn" aria-role="button" type="submit">保存</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=layerPath%>/layer.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/user/settings/setpwd.js"></script>
</body>
</html>