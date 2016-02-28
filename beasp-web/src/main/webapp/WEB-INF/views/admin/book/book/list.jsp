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
    <title>书籍列表-书籍交换与分享平台</title>

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
						<a class="btn btn-default" href="<%=bookAdminPath %>/book" role="button">添加</a>
						<a class="btn btn-primary" href="<%=bookAdminPath %>/book/find" role="button">查询</a>					
						<button type="button" class="btn btn-success" id="batchDelBtn">删除</button>
						<button type="button" class="btn btn-info" id="batchDelBtnRecyclebin">回收站</button>
						<button type="button" class="btn btn-warning" id="batchConfirmBook">批量审核通过</button>
						<button type="button" class="btn btn-danger">操作六</button>
					</div>
					<hr>
					<form action="<%=bookAdminPath %>/books" method="POST" id="_form">
						<input type="hidden" name="_method" id="_method"/>
						<input type="hidden" name="pageNo" value="${(page.number+1) }">
						<input type="hidden" name="state" value="${param.state }">
						<input type="hidden" name="query" value="${query }">
						<input type="hidden" name="param" value="${params }">
					</form>
					
					<table class="table table-bordered table-hover" style="text-align:center;">
						<tr class="active">
							<td><input type="checkbox" name="all"  <c:if test="${page.numberOfElements == 0}">disabled="disabled"</c:if>/>全选</td>
							<td>#</td>
							<td>修改</td>
							<td>书籍名称</td>
							<td>作者</td>
							<!-- <td>语言</td>
							<td>版本</td>
							<td>出版社</td>
							<td>出版时间</td> -->
							<td>交换</td>
							<td>分享</td>
							<td>状态</td>
							<td>发布时间</td>
							<td>所属用户</td>
							<td>所属类别</td>
							<td>删除</td>
							<td>产品样式</td>
							<td>载入</td>
						</tr>
						<c:choose>
							<c:when test="${page != null && page.numberOfElements > 0 }">
								
								<c:forEach items="${page.content }" var="book">
									<tr>
										<td><input type="checkbox" name="bookids" value="${book.id }"/></td>
										<td>${book.id }</td>
										<td>
											<%-- <a href="<%=bookAdminPath %>/book/${book.id}"><i class="fa fa-edit" ></i></a> --%>
											<a href="<%=bookAdminPath %>/book/${book.id}?pageNo=${page.number+1}"><i class="fa fa-edit" ></i></a>
										</td>
										<td>${book.name }</td>
										<td>${book.author }</td>
										<%-- <td>${book.language.name }</td>
										<td>${book.version}</td>
										<td>${book.press }</td>
										<td>${book.issueTime }</td> --%>
										<td><c:if test="${book.exchange eq false }">否</c:if><c:if test="${book.exchange ne false }">是</c:if></td>
										<td><c:if test="${book.share eq false }">否</c:if><c:if test="${book.share ne false }">是</c:if></td>
										<td>${book.state.name }</td>
										<td><fmt:formatDate value="${book.createTime }" type="both"/></td>
										<td>${book.user.userName}</td>
										<td>${book.category.name}</td>
										<td>
											<a href="<%=bookAdminPath %>/book/${book.id}" class="delete"><i class="fa fa-remove" ></i></a>
											<input type="hidden" value="${book.name }"/>
										</td>
										<td><a href="<%=bookAdminPath %>/styles?bookId=${book.id}">样式管理<c:if test="${fn:length(book.styles) > 0 }"><font color="red">(${fn:length(book.styles) })</font></c:if></a></td>
										<td><a href="<%=bookAdminPath%>/bookinfo?bookId=${book.id}">载入</a></td>
									</tr>
								</c:forEach>
								
								
							</c:when>
							<c:otherwise>
								<tr class="danger">
									<td colspan="14">暂时没有任何书籍!</td>
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
			//单个删除
			$(".delete").simpleDelete();
			
			//全选与否
			$(":checkbox").checkAll({single:"bookids"});
			
			//批量删除书籍
			$("#batchDelBtn").batchDelete({
				list:"bookids",
	    		alertMsg:"请选择你要删除的书籍!",
	    		confirmMsg:"确定要删除这些书籍吗?",
	    		url:"<%=bookAdminPath%>/book/ids"
			});
			
			$("#batchDelBtnRecyclebin").batchDelete({
				list:"bookids",
	    		alertMsg:"请选择你要完全删除的书籍!",
	    		confirmMsg:"确定要完全删除这些书籍吗?",
	    		url:"<%=bookAdminPath%>/book/ids",
	    		isRecyclebin:true
			});
			
			$("#batchConfirmBook").batchDelete({
				list:"bookids",
				alertMsg:"请选择你要审核的书籍!",
	    		confirmMsg:"确定要审核这些书籍吗?",
	    		url:"<%=bookAdminPath%>/batchConfirmBook",
			});
		});
		
	</script>
  </body>
</html>