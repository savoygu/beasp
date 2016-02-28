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
    <title>书籍类别添加与修改界面-书籍交换与分享平台</title>

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
					<c:set value="${pageContext.request.contextPath }/admin/book/category" var="url"></c:set>
					<c:if test="${!empty category.id }">
						<c:set value="${pageContext.request.contextPath }/admin/book/category/${category.id }" var="url"></c:set>
					</c:if>
					
					<h3 class="page-header">书籍类别<c:if test="${!empty category.id }">修改</c:if><c:if test="${empty category.id }">添加</c:if></h3>
					<div class="jumbotron">
						<form:form action="${url }" method="POST" modelAttribute="category" class="form-horizontal" id="form">
							
							<c:if test="${!empty category.id }">
								<input type="hidden" id="_oldName" value="${category.name }"/>
								<form:hidden path="id"/>
								<input type="hidden" name="_method" value="PUT"/>
							</c:if>
							
							<c:if test="${!empty category.parent.id }">
								<form:hidden path="parent.id"/>
							</c:if>
							
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">类别名称</label>
								<div class="col-sm-5">
									<form:input path="name" class="form-control" id="name" placeholder="类别名称" required="true"/>
								</div>
								<label id="nameMsg"></label>
							</div>
							<div class="form-group">
								<label for="describe" class="col-sm-2 control-label">类别描述</label>
								<div class="col-sm-5">
									<form:input path="describe" class="form-control" id="describe" placeholder="类别描述" required="true"/>
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
	<script type="text/javascript">
		$(document).ready(function(){
			$("#name").change(function() {
				$(this).validateName({
					url:"<%=bookAdminPath%>/ajaxValidateName"
				});
			});
			$("#form").submit(function() {
				var bool = true;
				if(!$("#name").validateName({url:"<%=bookAdminPath%>/ajaxValidateName" })){
					bool = false;
				}
				return bool;
			});
		})
	</script>
</body>
</html>