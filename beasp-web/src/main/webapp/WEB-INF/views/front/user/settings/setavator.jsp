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
<title>${user.nickName }的头像上传-书籍交换与分享平台</title>
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
					<div id="setting-avator" class="setting-wrap setting-avator cf">
						<div class="avator-img l">
							<div>
								<%-- <img alt="${user.nickName }" src="http://img.mukewang.com/user/545864490001b5bd02200220.jpg" width="220" height="220"> --%>
								<img alt="${user.nickName }" src="<%=beaspPath %>${user.imageFullPath}" width="220" height="220">
							</div>
						</div>
						<div class="avator-btn-group">
							<div id="avator-btns" class="avator-btn-inner">
								<div class="avator-btn-wrap">
									<form action="${pageContext.request.contextPath }/user/setavator" method="post" enctype="multipart/form-data">
										<a class="avator-btn-fake" href="javascript:void(0)">上传头像</a>
										<input id="upload" type="file" accept="image/*" name="photoName" title="头像上传">
									</form> 
								</div> 
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#upload").on("change", function(e) {
				if(!this.value) return;
				this.form.submit();
			})
		})
	</script>
</body>
</html>