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
<title>${user.nickName }的书籍上传-个人空间-书籍交换与分享平台</title>
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
					<h1 class="family-hd">我的书籍上传</h1>
				</div>
				<div class="book-tool-bar cf">
					<div class="tool-left l">
						<a class="sort-item active" href="<%=beaspPath %>/space/index">书籍上传</a>
					</div>
				</div>
				<div class="myspace-upload">
					<div class="main-bd">
						<div class="my-space-upload upload-tl">
							<form:form action="${pageContext.request.contextPath }/space/book/b" method="POST" modelAttribute="book" id="uploadForm" enctype="multipart/form-data">
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
								<div style='border:1px solid red; padding: 5px; margin-bottom: 5px; display: none;' class='exchange-container'>
									<div class='exchange-title'><h2>添加想要交换书籍：</h2></div>
									<div class='upload-group exchange'>
										<div class='cf upload-exchange l'>
											<div class='upload-label-exchange l'>
												<label for='name1' class='lableClass nameLabelClass'>名称1：</label>
											</div>
											<div class='upload-input upload-input-exchange l'>
												<input id="name1" type='text' name='name1' class='inputClass'/>
												<p id='name1Error' class='errorClass'></p>
											</div>
										</div>
										<div class='cf upload-exchange l'>
											<div class='upload-label-exchange l'>
												<label for='author1' class='lableClass'>作者1：</label>
											</div>
											<div class='upload-input upload-input-exchange l'>
												<input id="author1" type='text' name='author1' class='inputClass'/><p id='author1Error' class='errorClass'></p>
											</div>
										</div>
										<div class='cf upload-exchange l'>
											<div class='upload-label-exchange l'>
												<label for='version1' class='lableClass'>版本1：</label>
											</div>
											<div class='upload-input upload-select upload-input-exchange l'>
												<select id="version1" name='version1' class='selectClass'>
													<option value=''>选择版本</option>
													<c:forEach items='${versions}' var='v'><option value='${v.key}'>${v.value}</option></c:forEach>
												</select>
												<p id='version1Error' class='errorClass'></p>
											</div>
										</div>
										<div class='exchange-btn l'><a href='javascript:void(0)' class='btn js-exchange-btn'>追加一行</a></div>
									</div>
								</div>
								<div class="cf upload-group">
									<div class="upload-label l">
										<label for="imageName" class="lableClass">书籍图片：</label>
									</div>
									<div class="upload-input l">
										<input id="image" type="file" name="image" class="inputClass"/>
										<p id="imageError" class="errorClass">${errors.image }${imageErrorMsg }</p>
									</div>
								</div>
								
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
	<script type="text/javascript">var edit = 0;</script>
	<script type="text/javascript" src="<%=jsPath%>/front/user/space/upload.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".inputClass").mouseEvent({isOverLeaveDown:false,isFocusBlur:true});
			/**书籍交换**/
			var $flag=true;
			$("input[name='exchange']:radio").on("click", function() {
				var $this = $(this),
					$val = $this.val();
				if($val=="true" && $flag) {
					$(".exchange-container").show();
				} else {
					$(".exchange-container").hide();
				}
			});
			$(".exchange-container").on("click", ".js-exchange-btn", function(){
				var $this = $(this),
					$exchange = $this.parents(".exchange"),//内部容器
					$container = $this.parents(".exchange-container"),//外围容器
					$text = $exchange.find(".nameLabelClass").text(),//文本
					$num = $text.substring($text.length-2, $text.length-1) * 1;//文本中数字
				if($num >= 1) {
					//添加一行
					appendWant($this, $container, $num);
				}
			});
			
			//移除一行
			$(".exchange-container").on("click", ".js-exchange-remove-btn", function() {
				var $this = $(this),
					$exchange = $this.parents(".exchange"),
					$container = $this.parents(".exchange-container"),//外围容器
					$text = $exchange.find(".nameLabelClass").text(),//文本
					$num = $text.substring($text.length-2, $text.length-1) * 1;//文本中数字
				//添加按钮
				//alert($exchange.prevAll(".exchange").length)
				if($container.find(".exchange").length == 2 && $exchange.nextAll(".exchange").length <= 0) {//前面只有一行，后面没有一行
					$exchange.prev(".exchange").append("<div class='exchange-btn l'><a href='javascript:void(0)' class='btn js-exchange-btn'>追加一行</a></div>");
				}
				if($exchange.prevAll(".exchange").length > 1 && $exchange.nextAll(".exchange").length <= 0) {//前面只有一行，后面没有一行
					$exchange.prev(".exchange").append("<div class='exchange-btn l'><a href='javascript:void(0)' class='btn js-exchange-btn'>追加一行</a></div>");
				}
				//修改数字
				if($exchange.nextAll(".exchange").length > 0) {//如果后面还有行
					$.each($exchange.nextAll(".exchange"), function(i, n) {
						$(this).find(".nameLabelClass").text("名称"+($num+i)+"：");
						$(this).find(".nameLabelClass").attr("for", "name"+($num+i));
						$(this).find(".nameInputClass").attr({"id":"name"+($num+i), "name":"name"+($num+i)});
						/* $(this).find(".nameInputClass").attr("name", "name"+($num+i)); */
						$(this).find(".namePClass").attr("id", "name"+($num+i)+"Error");
						
						$(this).find(".authorLabelClass").text("作者"+($num+i)+"：");
						$(this).find(".authorLabelClass").attr("for", "author"+($num+i));
						$(this).find(".authorInputClass").attr({"id":"author"+($num+i), "name":"author"+($num+i)});
						/* $(this).find(".authorInputClass").attr("name", "author"+($num+i)); */
						$(this).find(".authorPClass").attr("id", "author"+($num+i)+"Error");
						
						$(this).find(".versionLabelClass").text("版本"+($num+i)+"：");
						$(this).find(".versionLabelClass").attr("for", "version"+($num+i));
						$(this).find(".versionSelectClass").attr({"id":"version"+($num+i), "name":"version"+($num+i)});
						/* $(this).find(".versionSelectClass").attr("name", "version"+($num+i)); */
						$(this).find(".versionPClass").attr("id", "version"+($num+i)+"Error");
						
					})
				}
				$exchange.remove();//移除一行
			})
		})
		
		function appendWant($this, $container, $num) {
				//追加一行，移除按钮
				if($container.find(".exchange").length <= 3) {
					$container.append("<div class='upload-group exchange'>"+
							"<div class='cf upload-exchange l'>"+
							"<div class='upload-label-exchange l'>"+
								"<label for='name"+($num+1)+"' class='lableClass nameLabelClass'>名称"+($num+1)+"：</label>"+
							"</div>"+
							"<div class='upload-input upload-input-exchange l'>"+
								"<input id='name"+($num+1)+"' type='text' name='name"+($num+1)+"' class='inputClass nameInputClass'/>"+
								"<p id='name"+($num+1)+"Error' class='errorClass namePClass'></p>"+
							"</div>"+
						"</div>"+
						"<div class='cf upload-exchange l'>"+
							"<div class='upload-label-exchange l'>"+
								"<label for='author"+($num+1)+"' class='lableClass authorLabelClass'>作者"+($num+1)+"：</label>"+
							"</div>"+
							"<div class='upload-input upload-input-exchange l'>"+
								"<input id='author"+($num+1)+"' type='text' name='author"+($num+1)+"' class='inputClass authorInputClass'/><p id='author"+($num+1)+"Error' class='errorClass authorPClass'></p>"+
							"</div>"+
						"</div>"+
						"<div class='cf upload-exchange l'>"+
							"<div class='upload-label-exchange l'>"+
								"<label for='version"+($num+1)+"' class='lableClass versionLabelClass'>版本"+($num+1)+"：</label>"+
							"</div>"+
							"<div class='upload-input upload-select upload-input-exchange l'>"+
								"<select id='version"+($num+1)+"' name='version"+($num+1)+"' class='selectClass versionSelectClass'>"+
									"<option value=''>选择版本</option>"+
									"<c:forEach items='${versions}' var='v'><option value='${v.key}'>${v.value}</option></c:forEach>"+
								"</select>"+
								"<p id='version"+($num+1)+"Error' class='errorClass versionPClass'></p>"+
							"</div>"+
						"</div>"+
						"<div class='exchange-btn l'><a href='javascript:void(0)' class='btn js-exchange-remove-btn'>移除一行</a></div>"+
						"<div class='exchange-btn l'><a href='javascript:void(0)' class='btn js-exchange-btn'>追加一行</a></div>"+
					"</div>");
					$this.parent().remove();
					$(".inputClass").mouseEvent({isOverLeaveDown:false,isFocusBlur:true});
				} else 
					return;
		}
	</script>
</body>
</html>