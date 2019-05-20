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
<title>注册界面-书籍交换与分享平台</title>

<!-- Bootstrap -->
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/user/signup.css">
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
		<div id="mainCnt" class="main-inner" style="background-image: url('/beasp/resources/images/user/140919_mailapp_cnt.jpg');">
			<div id="registBlock" class="regist">
				<div class="registFunc">
					<div id="idNormal" class="registFuncNormal">用户注册</div>
				</div>
				<div id="normalRegistTab" class="registForm" style="display: block;">
					<form action="${pageContext.request.contextPath }/account/signup" id="registForm" method="POST" name="registBeasp">
						<div id="idInputLine" class="registFormIpt">
							<label for="userName" class="labelClass">用户名：</label>
							<input type="text" id="userName" class="inputClass" name="userName" placeholder="请输入用户名" value="${userName }">
							<div id="userNameError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.userName }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<div id="idInputLine" class="registFormIpt">
							<label for="password" class="labelClass">密码：</label>
							<input type="password" id="password" class="inputClass" name="password" placeholder="请输入密码" value="${password }">
							<div id="passwordError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.password }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<div id="idInputLine" class="registFormIpt">
							<label for="email" class="labelClass">Email：</label>
							<input type="text" id="email" class="inputClass" name="email" placeholder="请输入Email" value="${email }">
							<div id="emailError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.email }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<div id="idInputLine" class="registFormIpt">
							<label for="nickName" class="labelClass">昵称：</label>
							<input type="text" id="nickName" class="inputClass" name="nickName" placeholder="请输入昵称" value="${nickName }">
							<div id="nickNameError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.nickName }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<div id="idInputLine" class="registFormIpt">
							<label for="gender" class="labelClass">性别：</label>
							<input type="radio"  class="radioClass gender" name="gender" value="MAN"/>男
							<input type="radio"  class="radioClass gender" name="gender" value="WOMEN"/>女
							<input type="radio"  class="radioClass gender" name="gender" value="SECRET" checked="checked"/>保密
							<div id="genderError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.gender }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<div id="idInputLine" class="registFormIpt">
							<label for="phone" class="labelClass">手机号码：</label>
							<input type="text" id="phone" class="inputClass" name="phone" placeholder="请输入手机号码" value="${email }">
							<div id="phoneError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.phone }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<div id="idInputLine" class="registFormIpt">
							<label class="labelClass">所在地：</label>
							<select name="province" id="province" class="selectClass">
								<option value="">请选择省...</option>
								<c:forEach items="${provinces }" var="province">
									<option value="${province.id }">${province.name }</option>
								</c:forEach>
							</select>
							<select name="city" id="city" class="selectClass">
								<option value="">请选择市...</option>
							</select>
							<!-- <select name="area" id="area">
								<option value="">请选则区/县...</option>
							</select> -->
							<div id="provinceError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.province }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
							<div id="cityError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.city }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<div id="idInputLine" class="registFormIpt">
							<label for="school" class="labelClass">所在学校：</label>
							<select name="school" id="school" class="selectClass">
								<option value="">请选择学校...</option>
							</select>
							<div id="schoolError" class="errorClass" style="text-align: left">
								<div class="layer-mid">
									<div class="error-tt">
										<p>${errors.school }</p>
									</div>
								</div>
								<i class="layer-arr"></i>
							</div>
						</div>
						<input type="hidden" name="fromurl" value="${fromurl }">
						<div class="registFormBtn">
							<button id="registBtn" class="btn btn-side  btn-regist" type="submit" tabindex="6">注册</button>
						</div>
					</form>
				</div>
			</div>			
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
		
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script src="<%=jsPath%>/front/user/signup.js"></script>
	<script src="<%=jsPath%>/core/jquery.beasp.user.js"></script>
	<%-- <script type="text/javascript" src="<%=jsPath%>/jquery.mailAutoComplete-4.0.js"></script> --%>
	<%-- <script type="text/javascript" src="<%=jsPath%>/autocomplete.js"></script> --%>
	<script type="text/javascript">
		$(document).ready(function() {
			//文本框邮箱地址自动提示
			//$("#email").mailAutoComplete();
			//$("#email").autocomplete();
			//输入框鼠标事件
			$(".inputClass").mouseEvent({
				isFocusBlur:true
			});
			//注册按钮移入移出
			$("#registBtn").mouseEvent({
				overLeaveClz:"btn-reg-hover", 
				downClz:"btn-reg-active"
			});
			//获取市
			$("#province").val("");//清空默认省
			$("#province").on("change", function() {
				$("#city option:not(:first)").remove();
				$("#school option:not(:first)").remove();
				var province = $(this).val();
				if(province != "") {
					var url="<%=userAdminPath%>/ajaxGetCities";
					var args={"provinceId":province, "time":new Date()};
					$.getJSON(url, args, function(data) {
						if(data.length == 0) {
							alert("当前省没有市!");
						} else {
							for(var i=0; i<data.length; i++) {
								$("#city").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");								
							}
						}
					})
				}
			});
			//获取学校
			 $("#city").on("change", function() {
				$("#school option:not(:first)").remove();
				var city = $(this).val();
				if(city != "") {
					var url="<%=userAdminPath%>/ajaxGetSchools";
					var args={"cityId":city, "time":new Date()};
					$.getJSON(url, args, function(data) {
						if(data.length == 0) {
							alert("当前市没有学校!");
						} else {
							for(var i=0; i<data.length; i++) {
								$("#school").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");						
							}
						}
					})
				}
			});
		})
	</script>
</body>
</html>