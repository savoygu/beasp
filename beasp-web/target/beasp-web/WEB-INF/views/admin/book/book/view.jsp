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
					<div class="btn_group">
						<c:if test="${book.state == 'WAITCONFIRM' }">
							<button type="button" class="btn btn-primary" onclick="ActionEvent('confirmBook', '${book.id}', '${book.state }', '${book.share }', '${book.exchange }')">审核通过</button>
							<button type="button" class="btn btn-primary" onclick="ActionEvent('noConfirmBook', '${book.id}', '${book.state }', '${book.share }', '${book.exchange }')">审核失败</button>
						</c:if>
						
					</div>
					<div class="page-header">
						<h3>书籍编号：${book.id }（<font color="red">${book.state.name }</font>）
							<small class="pull-right">发布时间：${book.createTime }&nbsp;&nbsp;
								<a href="<%=bookAdminPath %>/books" class="btn btn-info">书籍列表</a>
								<a href="<%=bookAdminPath %>/books/exchange" class="btn btn-info">交换列表</a>
								<a href="<%=bookAdminPath %>/books/share" class="btn btn-info">分享列表</a>
							</small>
						</h3>
					</div>
					<form:form action="/control/order/Order-list" method="post">
						<table class="table table-bordered table-hover" style="text-align:center;">
							<tr class="active">
								<td colspan="6" align="left"><h4>上传书籍信息：</h4></td>
							</tr>
							<tr class="success">
								<td colspan="2" align="left">书籍名称:《${book.name }》</td>
								<td colspan="2" align="left">书籍作者:（${book.author }）</td>
								<td colspan="1" align="left">是否分享:（<c:if test="${book.share eq true}">是</c:if><c:if test="${book.share eq false}">否</c:if>）</td>
								<td colspan="1" align="left">是否交换:（<c:if test="${book.exchange eq true}">是</c:if><c:if test="${book.exchange eq false}">否</c:if>）</td>
							</tr>
							<tr class="warning">
								<td colspan="2" align="left">书籍状态:（${book.state.name }）</td>
								<td colspan="2" align="left">书籍发布时间:（${book.createTime }）</td>
								<td colspan="1" align="left">所属类别:（${book.category.name }）</td>
								<td colspan="1" align="left">书籍样式:（<c:forEach items="${book.styles }" var="style"><c:if test="${style.choice eq true }"><img alt="" src="<%=beaspPath %>${style.imageFullPath }"></c:if></c:forEach>）</td>
							</tr>
							<tr class="info">
								<td colspan="6" align="left">书籍简介:（${book.summary }）</td>
							</tr>
							<tr class="active">
								<td colspan="6" align="left"><h4>上传者信息：</h4></td>
							</tr>
							<tr class="info">
								<td>上传者用户名</td>
								<td>${book.user.userName }（${book.user.gender.name }）</td>
								<td>上传者联系方式</td>
								<td>${book.user.phone }</td>
								<td>地址</td>
								<td>${book.user.province },${book.user.city },${book.user.school }</td>
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