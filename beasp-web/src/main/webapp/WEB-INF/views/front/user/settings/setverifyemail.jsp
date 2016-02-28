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
<title>${user.nickName }的邮箱验证-书籍交换与分享平台</title>
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
					<div class="setting-verify">
						<h1>当前邮箱</h1>
						<p>${user.email }</p>
						<c:if test="${user.verifyEmail eq 1 }">
							<div class="verify-status verified-status">
								<i class="verify-on-icon"></i>
								<div>邮箱已验证</div>
							</div>		
						</c:if>
						<c:if test="${user.verifyEmail eq 0 }">
							<div class="verify-status">
								<span class="verify-un">邮箱未验证</span>
							</div>
							<button id="verify-btn-sent" class="rlf-btn-green" data-email="${user.email }">发送验证邮件</button>
						</c:if>
						
						<button class="js-resetemail rlf-btn-blue" data-email="${user.email }">更换邮箱</button>
						<p class="avtivateEmailMsg">${avtivateEmailMsg }</p>
					</div>
				</div>
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=layerPath%>/layer.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			/**发送邮件**/
			$("#verify-btn-sent").click(function() {
				var $this = $(this);
				var email = $(this).data("email");
				if($this.text() == "正在发送...") return;
				$this.text("正在发送...");
				$.ajax({
					url:"/beasp/user/verificationmail",
					data:{email:email},
					dataType:"json",
					success:function(data) {
						if(data.status == 0) {
							layer.msg(data.msg, {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
							window.location.href = "/beasp/user/setverifysent";
						} else {
							layer.msg(data.msg, {icon:2, time:2000, shade: [0.8, '#393D49'], shift:6});
							$this.text("发送验证邮件");
						}
					},
					error:function() {
						layer.msg('网络错误，请稍后再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
						$this.text("发送验证邮件");
					}
				})
			})
			
		})
	</script>
</body>
</html>