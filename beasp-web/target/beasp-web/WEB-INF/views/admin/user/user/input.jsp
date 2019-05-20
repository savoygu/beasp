<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户添加与修改界面-书籍交换与分享平台</title>

    <!-- Bootstrap -->
    <link href="<%=cssPath %>/bootstrap.min.css" rel="stylesheet">
    <link href="<%=cssPath %>/font-awesome.min.css" rel="stylesheet">
	<link href="<%=cssPath %>/admin/common.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    	.emailist{border:1px solid #bdbdbd; border-radius: 4px; background-color:#fff; color:#666; font-size:14px; list-style-type:0; padding:0; margin:0; overflow:hidden;}
		.emailist li{padding:2px 11px; cursor:pointer;}
		.emailist .on, .emailist li:hover{background-color:#eee;}
		label.error{background-color:#ddd;width: 200px; height: 20px; line-height: 20px;}
    </style>
  </head>
  <body>
</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/adminNav.jsp"></jsp:include>
	
	<!-- 内容开始 -->
	
	<section class="content-wrap">
		<div class="container-fluid">
			<div class="row">
				<jsp:include page="/WEB-INF/views/common/asideNav.jsp"></jsp:include>
				<div class="col-md-10 main-content">
					
					<h3 class="page-header">用户添加</h3>
					<div class="jumbotron">
						<form:form action="${pageContext.request.contextPath }/admin/user/user" method="POST" modelAttribute="user" 
							class="form-horizontal" id="form">	
							
							<div class="form-group">
								<label for="userName" class="col-sm-2 control-label">用户名</label>
								<div class="col-sm-5">
									<form:input path="userName" class="form-control" id="userName" placeholder="用户名"/>
								</div>
								<label id="userNameMsg"><img alt="" id="userNameLoading" src="<%=imgPath%>/loading-4.gif" style="display: none;"></label>
							</div>
							<div class="form-group">
								<label for="password" class="col-sm-2 control-label">密码</label>
								<div class="col-sm-5">
									<form:password path="password" class="form-control" id="password" placeholder="密码"/>
								</div>
							</div>
							<div class="form-group">
								<label for="confirmPassword" class="col-sm-2 control-label">确认密码</label>
								<div class="col-sm-5">
									<input type="password" name="confirmPassword" class="form-control" id="confirmPassword" placeholder="确认密码"/>
								</div>
							</div>
							<div class="form-group">
								<label for="gender" class="col-sm-2 control-label">性别</label>
								<div class="col-sm-5">
									<form:radiobutton path="gender" value="MAN" />男
									<form:radiobutton path="gender" value="WOMEN" />女
									<form:radiobutton path="gender" id="gender" value="SECRET" checked="checked"/>保密
								</div>
							</div>
							<div class="form-group">
								<label for="nickName" class="col-sm-2 control-label">昵称</label>
								<div class="col-sm-5">
									<form:input path="nickName" class="form-control" id="nickName" placeholder="昵称"/>
								</div>
							</div>
							<div class="form-group">
								<label for="phone" class="col-sm-2 control-label">手机号码</label>
								<div class="col-sm-5">
									<form:input path="phone" class="form-control" id="phone" placeholder="电话号码"/>
								</div>
								<label id="phoneMsg"><img alt="" id="phoneLoading" src="<%=imgPath%>/loading-4.gif" style="display: none;"></label>
							</div>
							<div class="form-group">
								<label for="email" class="col-sm-2 control-label">电子邮件</label>
								<div class="col-sm-5">
									<form:input path="email" class="form-control" id="email" placeholder="电子邮件"/>
								</div>
								<label id="emailMsg"><img alt="" id="emailLoading" src="<%=imgPath%>/loading-4.gif" style="display: none;"></label>
							</div>
							<div class="form-group">
								<label for="exchange" class="col-sm-2 control-label">所在地</label>
								<div class="col-sm-6">
									<form:select path="province">
										<form:option value="">请选择省...</form:option>
										<form:options items="${provinces }" itemLabel="name" itemValue="id"/>
									</form:select>
									<form:select path="city">
										<form:option value="">请选择市...</form:option>
									</form:select>
									<form:select path="school">
										<form:option value="">请选择学校...</form:option>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label for="email" class="col-sm-2 control-label">所在学校</label>
								<div class="col-sm-5">
									<form:input path="school" class="form-control" id="school" placeholder="所在学校"/>
								</div>
							</div>
							<div class="form-group">
								<label for="summary" class="col-sm-2 control-label">个人简介</label>
								<div class="col-sm-5">
									<form:textarea path="summary" class="form-control" id="summary" placeholder="个人简介" rows="5"/>
								</div>
							</div>
							<div class="form-group">
	    						<div class="col-sm-offset-2 col-sm-10">
									<input type="submit" class="btn btn-default" value="添加"/>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<!-- 内容结束 -->
	
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script type="text/javascript" src="<%=jsPath %>/jquery-2.1.4.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script type="text/javascript" src="<%=jsPath %>/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=jsPath%>/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/jquery.blockUI.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/jquery.mailAutoComplete-4.0.js"></script>
	<script type="text/javascript" src="<%=jsPath %>/core/jquery.beasp.core.js"></script>
	<script type="text/javascript" src="<%=jsPath %>/admin/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//blockUI 
			/* $(document).ajaxStart(function() {
				$.blockUI({ 
		            message: $('#loading'), 
		            css: { 
		                top:  ($(window).height() - 400) /2 + 'px', 
		                left: ($(window).width() - 400) /2 + 'px', 
		                width: '400px',
		                border: 'none'
		            },
		            overlayCSS: { backgroundColor: '#fff' }
		        });
			}).ajaxStop($.unblockUI); */
			
			//文本框邮箱地址自动提示
			$("#email").mailAutoComplete();
			
			//注册校验
			
			$("#form").validate({
				debug:true,
				rules:{
					userName:{
						required:true,
						rangelength:[3,20],
						remote:{
							url:"<%=userAdminPath%>/ajaxValidateUserName",
							type:"POST",
							data:{
								time:function() {
									return +new Date;
								}
							}
						}
					},
					password:{
						required:true,
						rangelength:[6,20]
					},
					confirmPassword:{
						required:true,
						rangelength:[6,20],
						equalTo:"#password"
					},
					nickName:{
						required:true,
						rangelength:[3,20]
					},
					phone:{
						required:true,
						digits:true,
						phone_number:true,//自定义的规则
						remote:{
							url:"<%=userAdminPath%>/ajaxValidatePhone",
							type:"POST",
							data:{
								time:function() {
									return +new Date;
								}
							}
						}
					},
					email:{
						required:true,
						email:true,
						remote:{
							url:"<%=userAdminPath%>/ajaxValidateEmail",
							type:"POST",
							data:{
								time:function() {
									return +new Date;
								}
							}
						}
					},
					area:{
						required:true,	
					},
					school:{
						required:true,
					},
					summary:{
						rangelength:[0,100]
					}
					
				},
				messages:{
					userName:{
						required:"用户名不能为空!",
						rangelength:"用户名应该在3-20位!",
						remote:"用户名已被使用!"
					},
					password:{
						required:"密码不能为空!",
						rangelength:"密码应该在6-20位!"
					},
					confirmPassword:{
						required:"确认密码也不能为空!",
						rangelength:"确认密码应该在6-20位!",
						equalTo:"密码与确认密码不一致!"
					},
					nickName:{
						required:"昵称不能为空!",
						rangelength:"昵称应该在3-20位!"
					},
					phone:{
						required:"手机号码不能为空!",
						digits:"手机号码应该是数字",
						remote:"手机号码已被使用!"
					},
					email:{
						required:"Email不能为空!",
						email:"Email格式不正确!",
						remote:"Email已被使用!"
						
					},
					area:{
						required:"请选择所在省市!",
					},
					school:{
						required:"学校不能为空!",
					},
					summary:{
						rangelength:"个人简介不能超过100字!"
					}
				},
				submitHandler:function(form) {
					form.submit();
				}
			});
			//添加自定义验证规则
			jQuery.validator.addMethod("phone_number", function(value, element) { 
				var length = value.length; 
				var phone_number = /^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\d{8}$/;
				return this.optional(element) || (length == 11 && phone_number.test(value)); 
			}, "手机号码格式不正确");
			
			//获取市
			$("#province").on("change", function() {
				$("#city option:not(:first)").remove();
				$("#area option:not(:first)").remove();
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
			//获取市/县
			$("#city").on("change", function() {
				$("#area option:not(:first)").remove();
				var city = $(this).val();
				if(city != "") {
					var url="<%=userAdminPath%>/ajaxGetSchools";
					var args={"cityId":city, "time":new Date()};
					$.getJSON(url, args, function(data) {
						if(data.length == 0) {
							alert("当前市没有区或县!");
						} else {
							for(var i=0; i<data.length; i++) {
								$("#school").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");						
							}
						}
					})
				}
			});
			
			//校验用户名
			<%-- $(":input[name='userName']").on("change", function() {
				var userName = $(this).val();
				if($.trim(userName)) {
					var url = "<%=userAdminPath%>/ajaxValidateUserName";
					var args = {"userName":userName, "time":new Date()};
					$("#userNameLoading").css("display","block");
					$.post(url, args, function(data) {
						$("#userNameMsg").html(data);
					});
				}
			}); --%>
			//校验 Email
			<%-- $(":input[name='email']").on("change", function() {
				var email = $(this).val();
				if($.trim(email)) {
					var url = "<%=userAdminPath%>/ajaxValidateEmail";
					var args = {"email":email, "time":new Date()};
					$("#emailLoading").css("display","block");
					$.post(url, args, function(data) {
						$("#emailMsg").html(data);
					});
				}
			}); --%>
			//校验 Phone
			<%-- $(":input[name='phone']").on("change", function() {
				var phone = $(this).val();
				if($.trim(phone)) {
					var url = "<%=userAdminPath%>/ajaxValidatePhone";
					var args = {"phone":phone, "time":new Date()};
					$("#phoneLoading").css("display","block");
					$.post(url, args, function(data) {
						$("#phoneMsg").html(data);
					});
				}
			}); --%>
		})
	</script>
</body>
</html>