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
    <script type="text/javascript">
	    function ActionEvent(methodName, bookId, state, share, exchange) {
			window.location.href = "<%=bookAdminPath%>/" + methodName + '?bookId=' + bookId + "&state=" + state + '&share=' + share + "&exchange=" + exchange;
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
				<div class="col-md-10 div-content">
					<div class="page-header">
						<h3>用户编号：${user.id }（<font color="red">${user.userName }</font>）
							<small class="pull-right">发布时间：${book.createTime }&nbsp;&nbsp;
								<a href="<%=bookAdminPath %>/users" class="btn btn-info">用户列表</a>
								<a href="<%=bookAdminPath %>/books/exchange" class="btn btn-info">交换列表</a>
								<a href="<%=bookAdminPath %>/books/share" class="btn btn-info">分享列表</a>
								<a href="<%=bookAdminPath %>/books/apply" class="btn btn-info">求书籍列表</a>
							</small>
						</h3>
					</div>
					<form:form action="/control/order/Order-list" method="post">
						<table class="table table-bordered table-hover" style="text-align:center;">
							<tr class="active">
								<td colspan="6" align="left"><h4>用户信息：</h4></td>
							</tr>
							<tr class="success">
								<td colspan="2" align="left">用户昵称:${user.nickName }</td>
								<td colspan="2" align="left">用户Email:${user.email }（<c:if test="${user.verifyEmail eq 1 }">已校验</c:if><c:if test="${user.verifyEmail eq 0 }">未校验</c:if>）</td>
								<td colspan="1" align="left">用户性别:${user.gender.name }</td>
								<td colspan="1" align="left">用户phone:${user.phone }</td>
							</tr>
							<tr class="warning">
								<td colspan="2" align="left">用户状态:（<c:if test="${user.status eq 0}">可用</c:if><c:if test="${user.status eq 1}">已锁定</c:if><c:if test="${user.status eq 2}">已删除</c:if>）</td>
								<td colspan="2" align="left">用户注册时间:（${user.createTime }）</td>
								<td colspan="1" align="left">用户地址:（${user.province },${user.city },${user.school }）</td>
								<td colspan="1" align="left">用户头像:（
									<c:if test="${empty user.photoName }">
										<img alt="${user.userName }" src="<%=imgPath %>/user/default/40x/5458505c00018e9202200220-40-40.jpg" height="40" width="40">
									</c:if>
									<c:if test="${!empty user.photoName }">
										<img alt="${user.userName }" src="<%=beaspPath %>${user.image40FullPath}" height="40" width="40">
									</c:if>
								）</td>
							</tr>
							<tr class="info">
								<td colspan="6" align="left">用户简介:（<c:if test="${ empty user.summary }">这位童鞋很懒，什么都不想说！</c:if><c:if test="${ !empty user.summary }">${user.summary }</c:if>）</td>
							</tr>
						</table>
					</form:form>
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