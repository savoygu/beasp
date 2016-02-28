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
    <title>书籍类别查询-书籍交换与分享平台</title>

    <!-- Bootstrap -->
    <link href="<%=cssPath %>/bootstrap.min.css" rel="stylesheet">
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
	
	<!-- 导航菜单 -->
	<c:set var="menuout" value=""/>
	<c:forEach items="${menucates }" var="cate">
		<c:if test='${param.parentId!=cate.id }'>
			<c:set var="menuout" value="&nbsp;&nbsp;/&nbsp;&nbsp;<a href='selectType?parentId=${cate.id }'>${cate.name }</a>${menuout }"/>
		</c:if>
		<c:if test='${param.parentId==cate.id }'>
			<c:set var="menuout" value="&nbsp;&nbsp;/&nbsp;&nbsp;${cate.name }${menuout }"/>
		</c:if>
	</c:forEach>
	
	<div class="container">
		<div class="row">
			书籍类别列表,请选择分类:<br>
			导航：<a href="<%=bookAdminPath%>/selectType">顶级目录</a> <c:out value="${menuout }" escapeXml="false"></c:out>
			<form:form action="" method="post">
				<table class="table">
					<tr><td id="typecontent">
						<table class="table">
							<tr>
								<c:forEach items="${types }" var="type" varStatus="loop">
									<td>
										<c:choose>
											<c:when test="${fn:length(type.children) > 0 }">
												<a href="<%=bookAdminPath %>/selectType?parentId=${type.id }">${type.name }</a>
												<font color="red">(${fn:length(type.children) })</font>
											</c:when>
											<c:otherwise>
												<input type="radio" name="type" onclick="getDicName('${type.id}', '${type.name }')"/>${type.name }
											</c:otherwise>
										</c:choose>
									</td>
									<c:if test="${loop.count % 5 == 0}"></tr><tr></c:if>
								</c:forEach>
							</tr>
						</table>
					</td></tr>
					<tr><td colspan="2" align="center">
						<input type="button" class="btn btn-success" value="确 认 " onclick="checkIt()">
						<input type="button" class="btn btn-info" onclick="window.close()" value="取 消 "> 
				    </td></tr>
				</table>
				<input type="hidden" name="dicId">
				<input type="hidden" name="dicName">
			</form:form>
		</div>
	</div>
	
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script type="text/javascript" src="<%=jsPath %>/jquery-2.1.4.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script type="text/javascript" src="<%=jsPath %>/bootstrap.min.js"></script>
    <script type="text/javascript">
		function checkIt(){
			var objForm = document.forms[0];
			var form = window.opener.document.forms[0];
			if (form){
				window.opener.document.getElementById("categoryId").value = objForm.dicId.value;
				window.opener.document.getElementById("categoryName").value = objForm.dicName.value;
			}
			window.close();
		}
		function getDicName(dicId,strDicName){
			var objForm = document.forms[0];
			objForm.dicId.value = dicId;
			objForm.dicName.value = strDicName;
		}
	</script>
</body>
</html>