<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% 
	String beaspPath = request.getContextPath();
	String bookAdminPath = beaspPath + "/admin/book";
	String userAdminPath = beaspPath + "/admin/user";
	
	String jsPath = beaspPath + "/resources/js";
	String layerPath = beaspPath + "/resources/layer";
	String cssPath = beaspPath + "/resources/css";
	String imgPath = beaspPath + "/resources/images";
%>
