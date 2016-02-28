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
    <title>书籍样式列表-书籍交换与分享平台</title>

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
	
	<!-- 内容开始 -->
	
	<section class="content-wrap">
		<div class="container-fluid">
			<div class="row">
				<jsp:include page="/WEB-INF/views/common/asideNav.jsp"></jsp:include>
				<div class="col-md-10 div-content">
					<div class="btn_group">
						<a class="btn btn-default" href="<%=bookAdminPath %>/style?bookId=${param.bookId}" role="button">添加</a>
						<button type="button" class="btn btn-success" id="batchDelBtn">删除</button>
						<button type="button" class="btn btn-primary" id="batchAddBtn">恢复</button>
						<button type="button" class="btn btn-info" id="choiceBtn">选中当前</button>
						<button type="button" class="btn btn-warning">操作五</button>
						<button type="button" class="btn btn-danger">操作六</button>
					</div>
					<hr>
					<form action="<%=bookAdminPath %>/styles" method="POST" id="_form">
						<input type="hidden" name="_method" id="_method"/>
						<input type="hidden" name="pageNo" value="${(page.number+1) }">
					</form>
					
					<table class="table table-bordered table-hover" style="text-align:center;">
						<tr class="active">
							<td><input type="checkbox" name="all"  <c:if test="${page.numberOfElements == 0}">disabled="disabled"</c:if>/>全选</td>
							<td>#</td>
							<td>修改</td>
							<td>图片名称</td>
							<td>是否可见</td>
							<td>是否选中</td>
							<td>发布时间</td>
							<td>所属书籍</td>
							<td>图片</td>
							<td>删除</td>
						</tr>
						<c:choose>
							<c:when test="${page != null && page.numberOfElements > 0 }">
								
								<c:forEach items="${page.content }" var="style">
									<tr>
										<td><input type="checkbox" name="styleids" value="${style.id }"/></td>
										<td>${style.id }</td>
										<td>
											<a href="<%=bookAdminPath %>/style/${style.id}"><i class="fa fa-edit" ></i></a>
										</td>
										<td>${style.imageName }</td>
										<td><c:if test="${style.visible eq false }">否</c:if><c:if test="${style.visible ne false }">是</c:if></td>
										<td><c:if test="${style.choice eq false }">否</c:if><c:if test="${style.choice ne false }">是</c:if></td>
										<td><fmt:formatDate value="${style.createTime }" type="both"/></td>
										<td><a href="<%=bookAdminPath%>/styles?bookId=${style.book.id}">${style.book.name}</a></td>
										<td><img alt="" src="<%=beaspPath %>${style.imageFullPath }" width="50"></td>
										<td>
											<a href="<%=bookAdminPath %>/style/${style.id}?bookId=${param.bookId}" class="delete"><i class="fa fa-remove" ></i></a>
										</td>
									</tr>
								</c:forEach>
								
								
							</c:when>
							<c:otherwise>
								<tr class="danger">
									<td colspan="9">暂时没有任何样式!</td>
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
			$(":checkbox").checkAll({single:"styleids"});
			
			//批量删除
			$("#batchDelBtn").batchDelete({
				list:"styleids",
				alertMsg:"请选择你要删除的样式!",
	    		confirmMsg:"确定要删除这些样式吗?",
				url:"<%=bookAdminPath%>/style/ids"
			});
			
			//批量恢复
			$("#batchAddBtn").batchDelete({
				list:"styleids",
				alertMsg:"请选择你要还原的样式!",
	    		confirmMsg:"确定要还原这些样式吗?",
				url:"<%=bookAdminPath%>/style/ids",
				isAdd:true
			});
			
			//选中当前
			$("#choiceBtn").choiceCurrent({
				list:"styleids",
				url:"<%=bookAdminPath%>/style/choice"
			});
		});
	</script>
  </body>
</html>