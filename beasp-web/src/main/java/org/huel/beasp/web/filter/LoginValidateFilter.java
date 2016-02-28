package org.huel.beasp.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.utils.WebUtils;

/**
 * 未登录请求拦截(拦截请求为/user开头)
 * @author 001
 *
 */
public class LoginValidateFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = WebUtils.getUser(req);
		if(user == null) {
			//1. 得到当前请求路径
			String url = WebUtils.getRequestURIWithParam(req);
			//2. 进行编码
			String fromurl = new String(Base64.encodeBase64(url.getBytes()));
			//3. 把编码的路径作为请求参数附带到登录路径上
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect("/account/signin/fromurl/"+fromurl);
		} else 
			chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
