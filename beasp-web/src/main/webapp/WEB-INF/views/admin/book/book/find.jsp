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
    <title>书籍查询-书籍交换与分享平台</title>

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
				<div class="col-md-10 div-content">
					
					<h3 class="page-header">书籍查询</h3>
					<div class="jumbotron">
						<form action="<%=bookAdminPath %>/books" method="POST" class="form-horizontal" id="_form">
							<input type="hidden" name="query" value="true">
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">书籍名称</label>
								<div class="col-sm-5">
									<input type="text" name="name" class="form-control" id="name" placeholder="书籍名称"/>
								</div>
								<label id="nameMsg"></label>
							</div>
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">书籍作者</label>
								<div class="col-sm-5">
									<input type="text" name="name" class="form-control" id="author" placeholder="书籍作者"/>
								</div>
								<label id="nameMsg"></label>
							</div>
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">书籍出版社</label>
								<div class="col-sm-5">
									<input type="text" name="name" class="form-control" id="version" placeholder="书籍出版社"/>
								</div>
								<label id="nameMsg"></label>
							</div>
							<div class="form-group">
	    						<div class="col-sm-offset-2 col-sm-10">
									<input type="submit" class="btn btn-default" value="查询"/>
								</div>
							</div>
						</form>
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
    <script type="text/javascript" src="<%=jsPath %>/admin/common.js"></script>
    <script type="text/javascript">
    	$(document).ready(function() {
    		$("input:submit").click(function() {
    			var param = "";
        		$("input:text").each(function() {
    				if($(this).val()) {
    					param += $(this).val() + "-";  
    				} else {
    					param += "$-";
    				}
        		})
        		if(param=="$-$-$-") {
        			alert("请至少选择一种方式查询!");
        			return false;
        		} else {
        			param = param.substring(0, param.length-1);
        			$("#_form").prepend("<input type='hidden' name='param' value='"+param+"'>");
        		}
    		})
    	})
    </script>
</body>
</html>