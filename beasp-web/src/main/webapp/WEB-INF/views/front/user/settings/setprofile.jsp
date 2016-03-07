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
<title>${user.nickName }的个人设置-书籍交换与分享平台</title>
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
					<div id="setting-profile" class="setting-wrap setting-profile">
						<form action="${pageContext.request.contextPath }/user/setprofile" id="profile" method="post" id="profileForm">
							<input type="hidden" id="_oldPhone" value="${user.phone }">
							<div class="wlfg-wrap wlfg-wrap-h">
								<label class="label-name" for="nickName">昵称</label>
								<div class="rlf-group">
									<input id="nickName" class="rlf-input rlf-input-nickName inputClass" placeholder="请输入昵称" value="${user.nickName }" data-validate="nickName" autocomplete="off" name="nickName">
									<p id="nickNameError" class="rlf-tip-wrap errorClass"></p>
								</div>
							</div>
							<div class="wlfg-wrap wlfg-wrap-h">
								<label class="label-name" for="nickName">学校</label>
								<div class="rlf-group profile-address">
									<select id="province" name="province" class="selectClass">
										<option value="">选择省份</option>
										<c:forEach items="${provinces }" var="p">
											<c:if test="${p.name eq user.province }">
												<option value="${p.id }" selected="selected">${p.name }</option>
											</c:if>
											<c:if test="${p.name ne user.province }">
												<option value="${p.id }">${p.name }</option>
											</c:if>
										</c:forEach>
									</select>
									<select id="city" name="city" class="selectClass">
										<option value="">选择城市</option>
										<c:forEach items="${cities }" var="c">
											<c:if test="${c.name eq user.city }">
												<option value="${c.id }" selected="selected">${c.name }</option>
											</c:if>
											<c:if test="${c.name ne user.city }">
												<option value="${c.id }">${c.name }</option>
											</c:if>
										</c:forEach>
									</select>
									<select id="school" name="school" class="selectClass">
										<option value="">选择学校</option>
										<c:forEach items="${schools }" var="s">
											<c:if test="${s.name eq user.school }">
												<option value="${s.id }" selected="selected">${s.name }</option>
											</c:if>
											<c:if test="${s.name ne user.school }">
												<option value="${s.id }">${s.name }</option>
											</c:if>
										</c:forEach>
									</select>
									<p id="provinceError" class="rlf-tip-wrap errorClass"></p>
									<p id="cityError" class="rlf-tip-wrap errorClass"></p>
									<p id="schoolError" class="rlf-tip-wrap errorClass"></p>
								</div>
							</div>
							<div class="wlfg-wrap wlfg-wrap-h">
								<label class="label-name" for="nickName">性别</label>
								<div class="rlf-group rlf-radio-group">
									<label><input type="radio" name="gender" value="SECRET" <c:if test='${user.gender eq "SECRET" }'>checked="checked"</c:if>>保密</label>
									<label><input type="radio" name="gender" value="MAN" <c:if test='${user.gender eq "MAN" }'>checked="checked"</c:if>>男</label>
									<label><input type="radio" name="gender" value="WOMEN" <c:if test='${user.gender eq "WOMEN" }'>checked="checked"</c:if>>女</label>
									<p id="genderError" class="rlf-tip-wrap errorClass">${errors.gender }</p>
								</div>
							</div>
							<div class="wlfg-wrap wlfg-wrap-h">
								<label class="label-name" for="phone">手机号</label>
								<div class="rlf-group">
									<input id="phone" class="rlf-input inputClass" placeholder="请输入手机号" value="${user.phone }" data-validate="phone" autocomplete="off" name="phone">
									<p id="phoneError" class="rlf-tip-wrap errorClass">${errors.phone }</p>
								</div>
							</div>
							<div class="wlfg-wrap wlfg-wrap-h175">
								<label class="label-name" for="summary">个性签名</label>
								<div class="rlf-group">
									<textarea id="summary" class="textarea inputClass" rows="5" cols="30" name="summary">${user.summary }</textarea>
									<p id="summaryError" class="rlf-tip-wrap errorClass">${errors.summary }</p>
								</div>
							</div>
							<div class="wlfg-wrap">
								<div class="rlf-group">
									<button id="profile-submit" class="rlf-btn-purple btn-block profile-btn" aria-role="button" type="submit">保存</button>
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
	<script src="<%=jsPath%>/core/jquery.beasp.user.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/front/user/settings/setprofile.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".inputClass").mouseEvent({
				isFocusBlur:true
			});
			//获取市
			//$("#province").val("");//清空默认省
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