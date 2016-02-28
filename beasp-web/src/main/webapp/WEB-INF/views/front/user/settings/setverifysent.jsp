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
		<div class="wcontainer">
			<div class="wwrap wrap-boxes">
				<div class="wheader-wrap">
					<h1>邮箱验证</h1>
				</div>
				<div class="result-container sent-success">
					<i></i>
					<h1>我们向您 ${user.email } 发送了验证邮件</h1>
					<p>验证邮箱可以更好的保护您的账户安全，请及时验证！
						<span class="mail-label">没收到？</span>
						<span class="sent-again">
							<span class="js-resend" data-t="60" data-form="reg" data-email="${user.email }">再次发送验证邮件</span>
						</span>
					</p>
					<div class="bottom">
						<%-- <a class="sbtn-green rlf-btn-green" target="_blank" href="<%=beaspPath %>/user/openactivemail?t=reg">去邮箱验证</a> --%>
						<a class="link-goback" href="<%=beaspPath%>/book/list/category/0">继续浏览</a>
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
			/**定时器**/
			var initTimer = function() {
				var obj = $(".sent-again span");
				var t = $(".sent-again span").attr("data-t")*1;
				if(!t || t==0) {
					$(".sent-again span").addClass('resend');//追加绿色字体样式
				} else {
					function setTimer() {//定时器
						if(t==0) {
							obj.html("再次发送验证邮件").addClass('resend');
							return;
						}
						obj.html("再次发送验证邮件("+t+")");
						t--;
						setTimeout(setTimer, 1000);
					}
					setTimer();
				}
			}
			/**再次发送邮件**/
			$(".sent-again span").click(function() {
				var $this = $(this);
				if(!$this.hasClass('resend')) return;
				$this.html("正在发送...").removeClass('resend');
				$.ajax({
					url:"/beasp/user/verificationmail",
					dataType:"json",
					data:{email:$this.data("email")},
					success:function(data) {
						if(data.status == 0) {
							layer.msg("验证邮件发送成功", {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
							//window.location.href = "/beasp/user/setverifysent";
						} else {
							layer.msg(data.msg, {icon:2, time:2000, shade: [0.8, '#393D49'], shift:6});
						}
						$this.html("再次发送验证邮件(60)").attr("data-t", "60");
						initTimer();//执行定时器
					},
					error:function() {
						layer.msg(data.msg, {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
						$this.html("再次发送验证邮件").addClass('resend');
					}
				})
			});
			initTimer();//执行定时器
		})
	</script>
</body>
</html>