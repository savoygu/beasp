<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
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
<title>${user.nickName }的编辑书籍-个人空间-书籍交换与分享平台</title>
<link rel="stylesheet" href="<%=cssPath%>/font-awesome.min.css">
<link rel="stylesheet" href="<%=cssPath%>/front/common.css">
<link rel="stylesheet" href="<%=cssPath%>/front/user/space/index.css">
<%-- <link rel="stylesheet" href="<%=layerPath%>/skin/layer.css"> --%>
<style type="text/css">
 	body{padding-top: 50px;}
</style>
<script type="text/javascript">
function winOpen(strURL,strName,width,height){
	//window.opener 返回的是创建当前窗口的那个窗口的引用
	theWindow = window.open(strURL, strName, 
			"width=" + width + ",height=" + height + ",scrollbars=yes,left=" + (1366-width)/2 + ",top=" + (768-height)/2);
	if(theWindow.opener == null) theWindow.opener = window;
	if(window.focus) theWindow.focus();
}
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/frontNav-custom.jsp"></jsp:include>
	
	<section id="main">
		<div class="main-body w-1200 container cf">
			<div class="l">
				<jsp:include page="/WEB-INF/views/common/spaceSider.jsp"></jsp:include>
			</div>
			<div class="r space-main">
				<div class="family">
					<h1 class="family-hd">我的书籍编辑</h1>
				</div>
				<div class="book-tool-bar cf">
					<div class="tool-left l">
						<a class="sort-item active" href="<%=beaspPath %>/space/index">书籍编辑</a>
					</div>
				</div>
				<div class="myspace-upload">
					<div class="main-bd">
						<div class="my-space-upload upload-tl">
							<form:form action="${pageContext.request.contextPath }/space/book/b/${book.id}" method="POST" modelAttribute="book" id="uploadForm">
								
								<form:hidden path="id"/>
								<input type="hidden" name="_method" value="PUT"/>
							
								<div class="cf upload-group">
									<div class="upload-label l">
										<label for="name" class="lableClass">书籍名称：</label>
									</div>
									<div class="upload-input l">
										<form:input path="name" cssClass="inputClass"/>
										<p id="nameError" class="errorClass">${errors.name }</p>
									</div>
								</div>
								
								<div class="cf upload-group">
									<div class="upload-label l">
										<label for="author" class="lableClass">书籍作者：</label>
									</div>
									<div class="upload-input l">
										<form:input path="author" cssClass="inputClass"/>
										<p id="authorError" class="errorClass">${error.author }</p>
									</div>
								</div>

								<div class="cf upload-group upload-group-radio">
									<div class="upload-label l">
										<label for="language" class="lableClass">书籍语言：</label>
									</div>
									<div class="upload-input upload-radio l">
										<form:radiobutton path="language" value="CHINESE" checked="checked" cssClass="radioClass"/>简体中文
										<form:radiobutton path="language" value="ENGLISH" cssClass="radioClass"/>英文
										<%-- <form:radiobuttons path="language" items="${languages}"/> --%>
									</div>
								</div>
								
								<div class="cf upload-group">
									<div class="upload-label l">
										<label for="version" class="lableClass">书籍版本：</label>
									</div>
									<div class="upload-input upload-select l">
										<form:select path="version" cssClass="selectClass">
											<form:option value="">请选择版本...</form:option>
											<form:options items="${versions }"/>
										</form:select>
										<p id="versionError" class="errorClass">${errors.version }</p>
									</div>
								</div>
								
								<div class="cf upload-group">
									<div class="upload-label l">
										<label for="press" class="lableClass">书籍出版社：</label>
									</div>
									<div class="upload-input l">
										<form:input path="press" cssClass="inputClass"/>
										<p id="pressError" class="errorClass">${errors.press }</p>
									</div>
								</div>
								
								<div class="cf upload-group">
									<div class="upload-label l">
										<label for="issueTime" class="lableClass">出版时间：</label>
									</div>
									<div class="upload-input l">
										<form:input path="issueTime" cssClass="inputClass"/>
										<p id="issueTimeError" class="errorClass">${errors.issueTime }</p>
									</div>
								</div>
								
								<div class="cf upload-group upload-group-radio">
									<div class="upload-label l">
										<label  class="lableClass">是否分享：</label>
									</div>
									<div class="upload-input upload-radio l">
										<%-- <form:radiobuttons path="share" items="${shares }"/> --%>
										<form:radiobutton path="share" value="true" cssClass="radioClass"/>是
										<form:radiobutton path="share" value="false" checked="checked" cssClass="radioClass"/>否
									</div>
								</div>
								<div class="cf upload-group upload-group-radio">
									<div class="upload-label l">
										<label  class="lableClass">是否交换：</label>
									</div>
									<div class="upload-input upload-radio l">
										<%-- <form:radiobuttons path="exchange" items="${exchanges }"/> --%>
										<form:radiobutton path="exchange" value="true" cssClass="radioClass"/>是
										<form:radiobutton path="exchange" value="false" checked="checked" cssClass="radioClass"/>否
									</div>
								</div>
								
								<%-- <div class="cf upload-group">
									<div class="upload-label l">
										<label for="imageName" class="lableClass">书籍图片：</label>
									</div>
									<div class="upload-input l">
										<input id="image" type="file" name="image" class="inputClass"/>
										<p id="imageError" class="errorClass">${errors.image }${imageErrorMsg }</p>
									</div>
								</div> --%>
								
								<div class="cf upload-group upload-group-textarea">
									<div class="upload-label l">
										<label for="summary" class="lableClass">书籍描述：</label>
									</div>
									<div class="upload-input upload-textarea l">
										<form:textarea path="summary" cssClass="inputClass textareaClass"/>
										<p id="summaryError" class="errorClass">${errors.summary }</p>
									</div>
								</div>
								
								<div class="cf upload-group">
									<div class="upload-label l">
										<label for="" class="lableClass">所属类别：</label>
									</div>
									<div class="upload-input l">
										<form:input path="category.name" cssClass="inputClass" id="categoryName" disabled="true"/>
										<button type="button" class="choiceBtn" name="select" value="选择类别" 
											onclick="winOpen('<%=bookAdminPath%>/selectType','选择类别',600,400)">选择类别</button>
										<form:hidden path="category.id" id="categoryId"/>
										<p id="categoryNameError" class="errorClass">${errors.category }</p>
									</div>
								</div>
								
								<div class="upload-group">
									<button id="uploadBtn" class="uploadBtn uploadBtn-pos" type="submit" tabindex="6">上传书籍</button>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/frontFoot.jsp"></jsp:include>
	<script src="<%=jsPath%>/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=jsPath%>/core/jquery.beasp.user.js"></script>
	<script type="text/javascript" src="<%=layerPath%>/layer.js"></script>
	<script type="text/javascript">var edit = 1;</script>
	<script type="text/javascript" src="<%=jsPath%>/front/user/space/upload.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".inputClass").mouseEvent({isOverLeaveDown:false,isFocusBlur:true})
		})
	</script>
</body>
</html>