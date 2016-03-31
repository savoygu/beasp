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
    <title>求书籍列表-书籍交换与分享平台</title>

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
					<div class="btn_group">
						<button type="button" class="btn btn-default" id="batchPassBtn">审核通过</button>
						<button type="button" class="btn btn-primary" id="batchFailBtn">审核失败</button>					
						<button type="button" class="btn btn-success">操作三</button>
						<button type="button" class="btn btn-info">操作四</button>
						<button type="button" class="btn btn-warning">操作五</button>
						<button type="button" class="btn btn-danger">操作六</button>
					</div>
					<hr>
					<form action="<%=bookAdminPath %>/books/share" method="POST" id="_form">
						<input type="hidden" name="_method" id="_method"/>
						<input type="hidden" name="pageNo" value="${(page.number+1) }">
					</form>
					
					<table class="table table-bordered table-hover" style="text-align:center;">
						<tr class="active">
							<td><input type="checkbox" name="all"  <c:if test="${page.numberOfElements == 0}">disabled="disabled"</c:if>/>全选</td>
							<td>#</td>
							<td>书籍作者</td>
							<td>书籍名称</td>
							<td>书籍版本</td>
							<td>性质</td>
							<td>状态</td>
							<td>提供人</td>
							<td>提供的书籍</td>
							<td>申请时间</td>
							<td>申请人</td>
						</tr>
						<c:choose>
							<c:when test="${page != null && page.numberOfElements > 0 }">
								
								<c:forEach items="${page.content }" var="applyBook">
									<tr>
										<td><input type="checkbox" name="applyBookids" value="${applyBook.id }"/></td>
										<td>${applyBook.id }</td>
										<td>${applyBook.bookAuthor }</td>
										<td>${applyBook.bookName }</td>
										<td>${applyBook.bookVersion }</td>
										<td><c:if test="${applyBook.shareExchange eq 1 }">分享</c:if><c:if test="${applyBook.shareExchange eq 2 }">交换</c:if></td>
										<td><c:if test="${applyBook.state eq 'VERIFYPASS' }">审核通过</c:if><c:if test="${applyBook.state eq 'WAITVERIFY' }">等待审核</c:if><c:if test="${applyBook.state eq 'WAITSURE' }">等待确认</c:if><c:if test="${applyBook.state eq 'UPLOADED' }">已经上传</c:if><c:if test="${applyBook.state eq 'WAITUPLOAD' }">等待上传</c:if></td>
										<td><a href="<%=userAdminPath %>/userinfo?userId=${applyBook.applyer.id}">${applyBook.applyer.userName }</a></td>
										<td><a href="<%=bookAdminPath %>/bookinfo?bookId=${applyBook.book.id}">${applyBook.book.name}</a></td>
										<td>${applyBook.createTime }</td>
										<td><a href="<%=userAdminPath %>/userinfo?userId=${applyBook.requirer.id}">${applyBook.requirer.userName }</a></td>
									</tr>
								</c:forEach>
								
								
							</c:when>
							<c:otherwise>
								<tr class="danger">
									<td colspan="8">暂时没有任何书籍!</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<jsp:include page="/WEB-INF/views/common/adminPaging.jsp"></jsp:include>
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
			
			//全选与否
			$(":checkbox").checkAll({single:"applyBookids"});
			
			//批量确认通过
			$("#batchPassBtn").batchDelete({
				list:"applyBookids",
				alertMsg:"请选择你要审核的书籍!",
	    		confirmMsg:"确定要审核通过?",
				url:"<%=bookAdminPath%>/book/pass/ids",
				isPass:true
			});
			
			//批量确认失败
			$("#batchFailBtn").batchDelete({
				list:"applyBookids",
				alertMsg:"请选择你要审核的书籍!",
	    		confirmMsg:"确定要审核失败?",
				url:"<%=bookAdminPath%>/book/fail/ids",
				isFail:true
			});
		});
	</script>
  </body>
</html>