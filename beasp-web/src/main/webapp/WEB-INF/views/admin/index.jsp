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
    <title>用户列表-书籍交换与分享平台</title>

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
    
   <jsp:include page="/WEB-INF/views/common/adminNav.jsp"></jsp:include>
	
	<!-- 内容开始 -->
	
	<section class="content-wrap">
		<div class="container-fluid">
			<div class="row">
				<jsp:include page="/WEB-INF/views/common/asideNav.jsp"></jsp:include>
				<div class="col-md-10 div-content">
					<h3 class="page-header">欢迎来到后台管理系统</h3>
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
		$(document).ready(function() {
			//单个删除
			$(".delete").simpleDelete();
			
			//全选与否
			$(":checkbox").checkAll({single:"bookids"});
			
			//批量删除书籍
			$("#batchDelBtn").batchDelete({
				list:"bookids",
	    		alertMsg:"请选择你要删除的书籍!",
	    		confirmMsg:"确定要删除这些书籍吗?",
	    		url:"<%=userAdminPath%>/user/ids"
			});
			
			$("#batchDelBtnRecyclebin").batchDelete({
				list:"bookids",
	    		alertMsg:"请选择你要完全删除的书籍!",
	    		confirmMsg:"确定要完全删除这些书籍吗?",
	    		url:"<%=userAdminPath%>/user/ids",
	    		isRecyclebin:true
			});
		});
		
	</script>
  </body>
</html>