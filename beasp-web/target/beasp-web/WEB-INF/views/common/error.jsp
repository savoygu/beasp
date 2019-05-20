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
<title>书籍交换与分享平台</title>
<link rel="stylesheet" href="<%=cssPath%>/font-awesome.min.css">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<%-- <link rel="stylesheet" href="<%=layerPath%>/skin/layer.css"> --%>
<style type="text/css">
 	body{padding-top: 50px; background: #edeff0;}
 	#main{ min-height: 610px;height: 610px; padding: 20px 0px;}
 	.wrong-warp {width: 630px; height: 450px; margin: 150px auto 0px;}
 	.wrong-warp img, .wrong-warp p{float: let; height: 216px; line-height: 216px;}
 	.msg-wrap{margin: 90px 0 0 41px;}
 	.errorInfo{color: #656e73; font-size: 18px; font-weight: blod; margin: 5px 50px 0 0;}
 	.beback:link, .beback:visited{font-family: '微软雅黑'; display: inline-block; padding: 0 31px; height: 40px; line-height: 40px; border-radius: 25px; border: 1px solid #39b94e; text-align: center; font-size: 14px; background: #dbe9df; color: #39b94e; cursor: pointer;}
 	.beback:hover{ background:#39b94e;color:#fff;}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/frontNav-custom.jsp"></jsp:include>
	
	<section id="main">
		<div class="wrong-warp">
			<div class="broken-imgs l">
				<img src="<%=beaspPath %>/resources/images/besorry.png" alt="抱歉">
			</div>
			<div class="msg-wrap l">
				<em class="errorInfo l">此页面无法访问......</em>
				<a href="<%=beaspPath %>/" class="beback">返回首页</a>
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
</body>
</html>