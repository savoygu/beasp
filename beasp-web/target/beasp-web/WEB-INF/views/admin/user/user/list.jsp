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
					<div class="btn_group">
						<a class="btn btn-default" href="<%=userAdminPath %>/user" role="button">添加</a>
						<a class="btn btn-primary" href="<%=userAdminPath %>/user/find" role="button">查询</a>					
						<button type="button" class="btn btn-success" id="batchDelBtn">删除</button>
						<button type="button" class="btn btn-info">操作四</button>
						<button type="button" class="btn btn-warning">操作五</button>
						<button type="button" class="btn btn-danger">操作六</button>
					</div>
					<hr>
					<form action="<%=userAdminPath %>/users" method="POST" id="_form">
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
							<td>用户名</td>
							<td>昵称</td>
							<td>性别</td>
							<td>Tel</td>
							<td>Email</td>
							<td>School</td>
							<td>省</td>
							<td>市</td>
							<!-- <td>区</td> -->
							<td>注册时间</td>
							<td>状态</td>
							<td>头像</td>
							<td>删除</td>
						</tr>
						<c:choose>
							<c:when test="${page != null && page.numberOfElements > 0 }">
								
								<c:forEach items="${page.content }" var="user">
									<tr>
										<td><input type="checkbox" name="userids" value="${user.id }"/></td>
										<td>${user.id }</td>
										<td>
											<a href="<%=userAdminPath %>/user/${user.id}"><i class="fa fa-edit" ></i></a>
										</td>
										<td>${user.userName }</td>
										<td>${user.nickName }</td>
										<td>${user.gender.name }</td>
										<td>${user.phone}</td>
										<td>${user.email }</td>
										<td>${user.school }</td>
										<td>${user.province }</td>
										<td>${user.city }</td>
										<%-- <td>${user.area }</td> --%>
										<td><fmt:formatDate value="${user.createTime }" type="both"/></td>
										<td><c:if test="${user.status eq 0 }">可用</c:if><c:if test="${user.status eq 1 }">锁定</c:if><c:if test="${user.status eq 1 }">删除</c:if></td>
										<td>
											<c:if test="${empty user.photoName }">
												<img alt="${user.userName }" src="<%=imgPath %>/user/default/40x/5458505c00018e9202200220-40-40.jpg" height="40" width="40">
											</c:if>
											<c:if test="${!empty user.photoName }">
												<img alt="${user.userName }" src="<%=beaspPath %>${user.image40FullPath}" height="40" width="40">
											</c:if>
										</td>
										<td>
											<a href="<%=userAdminPath %>/user/${user.id}" class="delete"><i class="fa fa-remove" ></i></a>
											<input type="hidden" value="${user.userName }"/>
										</td>
									</tr>
								</c:forEach>
								
								
							</c:when>
							<c:otherwise>
								<tr class="danger">
									<td colspan="16">暂时没有任何用户!</td>
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
	    		alertMsg:"请选择你要删除的用户!",
	    		confirmMsg:"确定要删除这些用户吗?",
	    		url:"<%=userAdminPath%>/user/ids"
			});
			
			$("#batchDelBtnRecyclebin").batchDelete({
				list:"bookids",
	    		alertMsg:"请选择你要完全删除的用户!",
	    		confirmMsg:"确定要完全删除这些用户吗?",
	    		url:"<%=userAdminPath%>/user/ids",
	    		isRecyclebin:true
			});
		});
		
	</script>
  </body>
</html>