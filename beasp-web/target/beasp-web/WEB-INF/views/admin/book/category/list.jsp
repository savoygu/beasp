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
    <title>书籍类别列表-书籍交换与分享平台</title>

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
						<a class="btn btn-default" href="<%=bookAdminPath %>/category<c:if test='${!empty param.parentId }'>?parentId=${param.parentId}</c:if>" role="button">添加</a>
						<a class="btn btn-primary" href="<%=bookAdminPath %>/category/find" role="button">查询</a>					
						<button type="button" class="btn btn-success" id="batchDelBtn">删除</button>
						<button type="button" class="btn btn-info">操作四</button>
						<button type="button" class="btn btn-warning">操作五</button>
						<button type="button" class="btn btn-danger">操作六</button>
						
						<c:if test="${page.numberOfElements != 0 && !empty param.parentId }">
							<a class="btn btn-info pull-right" href="<%=bookAdminPath %>/categories?parentId=${page.content[0].parent.parent.id}" role="button">返回</a>
						</c:if>
						<c:if test="${page.numberOfElements == 0 && !empty param.parentId }">
							<a class="btn btn-info pull-right" href="<%=bookAdminPath %>/categories">返回Top</a>
						</c:if>
					</div>
					<hr>
					<form action="<%=bookAdminPath %>/categories" method="POST" id="_form">
						<input type="hidden" name="_method" id="_method"/>
						<input type="hidden" name="pageNo" value="${(page.number+1) }">
						<input type="hidden" name="query" value="${query }">
						<input type="hidden" name="name" value="${name }">
						<input type="hidden" name="parentId" value="${param.parentId }">
					</form>
					
					<table class="table table-bordered table-hover" style="text-align:center;">
						<tr class="active">
							<td><input type="checkbox" name="all"  <c:if test="${page.numberOfElements == 0}">disabled="disabled"</c:if>/>全选</td>
							<td>#</td>
							<td>修改</td>
							<td>书籍类别名称</td>
							<td>创建下级类别</td>
							<td>所属父类</td>
							<td>备注</td>
							<td>删除</td>
						</tr>
						<c:choose>
							<c:when test="${page != null && page.numberOfElements > 0 }">
								
								<c:forEach items="${page.content }" var="category">
									<tr>
										<td><input type="checkbox" name="cateids" value="${category.id }"/></td>
										<td>${category.id }</td>
										<td>
											<a href="<%=bookAdminPath %>/category/${category.id}"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>
										</td>
										<td><a href="<%=bookAdminPath %>/categories?parentId=${category.id}">${category.name }</a></td>
										<td><a href="<%=bookAdminPath %>/category?parentId=${category.id}">
											<i class="fa fa-edit" ></i>
											</a></td>
										<td><a href="<%=bookAdminPath %>/categories<c:if test='${! empty category.parent.parent.id}'>?parentId=${category.parent.parent.id}</c:if>">${category.parent.name }</a></td>
										<td>${category.describe }</td>
										<td>
											<a href="<%=bookAdminPath %>/category/${category.id}<c:if test='${!empty param.parentId}'>?parentId=${param.parentId}</c:if>" class="delete"><i class="fa fa-remove"></i></a>
											<input type="hidden" value="${category.name }"/>
										</td>
									</tr>
								</c:forEach>
								
								
							</c:when>
							<c:otherwise>
								<tr class="danger">
									<td colspan="8">暂时没有任何类别!</td>
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
			$(":checkbox").checkAll({single:"cateids"});
			
			//批量删除
			$("#batchDelBtn").batchDelete({
				list:"cateids",
				url:"<%=bookAdminPath%>/category/ids"
			});
		});
	</script>
  </body>
</html>