package org.huel.beasp.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UrlPathHelper;

public class MyDispatcherServlet extends DispatcherServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	private String fileNotFoundUrl = "/error/404.html";
	
	@Override
	protected void noHandlerFound(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(pageNotFoundLogger.isWarnEnabled())  {
			String requestUri = urlPathHelper.getRequestUri(request);
			 pageNotFoundLogger.warn("No mapping found for HTTP request with URI [" + requestUri +  
	                    "] in DispatcherServlet with name '" + getServletName() + "'"); 
		}
		response.sendRedirect(request.getContextPath()+"/error/noexists");
	}

	public String getFileNotFoundUrl() {
		return fileNotFoundUrl;
	}

	public void setFileNotFoundUrl(String fileNotFoundUrl) {
		this.fileNotFoundUrl = fileNotFoundUrl;
	}

}
