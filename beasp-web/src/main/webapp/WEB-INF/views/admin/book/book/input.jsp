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
    <title>书籍添加与修改界面-书籍交换与分享平台</title>

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
</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/adminNav.jsp"></jsp:include>
	
	<!-- 内容开始 -->
	
	<section class="content-wrap">
		<div class="container-fluid">
			<div class="row">
				<jsp:include page="/WEB-INF/views/common/asideNav.jsp"></jsp:include>
				<div class="col-md-10 main-content">
					
					<h3 class="page-header">书籍添加</h3>
					<div class="jumbotron">
						<form:form action="${pageContext.request.contextPath }/admin/book/book" method="POST" modelAttribute="book" 
							class="form-horizontal" id="form" enctype="multipart/form-data">	
							
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">书籍名称</label>
								<div class="col-sm-5">
									<form:input path="name" class="form-control" id="name" placeholder="书籍名称" required="true"/>
								</div>
								<label id="nameMsg"></label>
							</div>
							<div class="form-group">
								<label for="author" class="col-sm-2 control-label">书籍作者</label>
								<div class="col-sm-5">
									<form:input path="author" class="form-control" id="author" placeholder="书籍作者" required="true"/>
								</div>
								<label id="nameMsg"></label>
							</div>
							<div class="form-group">
								<label for="language" class="col-sm-2 control-label">书籍语言</label>
								<div class="col-sm-5">
									<form:radiobutton path="language" id="language" value="CHINESE" />简体中文
									<form:radiobutton path="language" value="ENGLISH" />英文
								</div>
							</div>
							<div class="form-group">
								<label for="version" class="col-sm-2 control-label">书籍版本</label>
								<div class="col-sm-5">
									<form:input path="version" class="form-control" id="version" placeholder="书籍版本" required="true"/>
								</div>
							</div>
							<div class="form-group">
								<label for="version" class="col-sm-2 control-label">书籍出版社</label>
								<div class="col-sm-5">
									<form:input path="press" class="form-control" id="press" placeholder="书籍出版社" required="true"/>
								</div>
							</div>
							<div class="form-group">
								<label for="issueTime" class="col-sm-2 control-label">书籍出版时间</label>
								<div class="col-sm-5">
									<form:input path="issueTime" class="form-control" id="issueTime" placeholder="书籍出版时间" required="true"/>
								</div>
							</div>
							<div class="form-group">
								<label for="exchange" class="col-sm-2 control-label">书籍是否交换</label>
								<div class="col-sm-5">
									<form:radiobutton path="exchange" id="exchange" value="true" />是
									<form:radiobutton path="exchange" value="false" />否
								</div>
							</div>
							<div class="form-group">
								<label for="share" class="col-sm-2 control-label">书籍是否分享</label>
								<div class="col-sm-5">
									<form:radiobutton path="share" id="share" value="true" />是
									<form:radiobutton path="share" value="false" />否
								</div>
							</div>
							
							<div class="form-group">
								<label for="imageName" class="col-sm-2 control-label">书籍样式图片</label>
								<div class="col-sm-5">
									<input type="file" name="image" id="imageName" placeholder="书籍样式图片" required="required"/>
								</div>
								<label>${imageErrorMsg }</label>
							</div>
 							
							<div class="form-group">
								<label for="summary" class="col-sm-2 control-label">书籍描述</label>
								<div class="col-sm-5">
									<form:input path="summary" class="form-control" id="summary" placeholder="书籍描述" required="true" min="5" max="100"/>
								</div>
							</div>
							<div class="form-group">
								<label for="categoryId" class="col-sm-2 control-label">所属类别名称</label>
								<div class="col-sm-5">
									<form:input path="category.name" id="categoryName" class="form-control" placeholder="所属类别名称" required="true" disabled="true"/>
									<button type="button" class="btn btn-primary" name="select" value="选择类别" 
										onclick="winOpen('<%=bookAdminPath%>/selectType','选择类别',600,400)">选择类别</button>
									<form:hidden path="category.id" id="categoryId"/>
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
	<script type="text/javascript" src="<%=jsPath %>/core/jquery.beasp.core.js"></script>
	<script type="text/javascript" src="<%=jsPath %>/admin/common.js"></script>
</body>
</html>