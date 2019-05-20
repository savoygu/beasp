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
<title>书籍分享成功-书籍交换与分享平台</title>
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
		<div class="wcontainer">
			<div class="wwrap wrap-boxes">
				<div class="pwd-reset-result">
					<c:if test="${!empty errorMsg }">
						<i class="pwd-rsterror-icon"></i>
						<div class="pwd-rstsuc-inner">
							<p class="pwd-rstsuc-txt">${errorMsg }</p>
							<p class="pwd-rst-msg">你的分享无法完成，由于以上原因。</p>
							<span class="timer-back pwd-rstsuctm-wrap">5</span><span class="timer-back-txt">秒后自动返回书籍与分享平台首页</span>
						</div>
					</c:if>
					<c:if test="${empty errorMsg }">
						<i class="pwd-rstsuc-icon"></i>
						<div class="pwd-rstsuc-inner">
							<p class="pwd-rst-msg">您的分享正在进行中，等待TA的确认。</p>
							<span class="timer-back pwd-rstsuctm-wrap">5</span><span class="timer-back-txt">秒后自动返回书籍与分享平台首页</span>
						</div>
					</c:if>
					<a href="<%=beaspPath%>/" class="rlf-btn-green btn-block-reset">返回首页</a>
				</div>
			</div> 
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			/**定时器**/
			var initTimer = function() {
				var obj = $(".timer-back");
				var t = $(".timer-back").text()*1;
				if(!t || t==0) {
					window.location.href = "/"
				} else {
					function setTimer() {//定时器
						if(t==0) {
							window.location.href = "/"
						}
						obj.html(t);
						t--;
						setTimeout(setTimer, 1000);
					}
					setTimer();
				}
			}
			initTimer();//执行定时器
		})
	</script>
</body>
</html>