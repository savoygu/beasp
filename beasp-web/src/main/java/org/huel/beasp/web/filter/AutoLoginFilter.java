package org.huel.beasp.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.huel.beasp.utils.WebUtils;

/**
 * 自动登录过滤器(并未实现)
 */
public class AutoLoginFilter implements Filter {

    
    public AutoLoginFilter() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getSession().getAttribute("user") == null) {
			String cookieValue = WebUtils.getCookieByName(req, "beaspName");
			if(cookieValue != null) {
				String[] cookieValues = cookieValue.split(":");
				if(cookieValues.length == 2) {
					try {
						String userName = URLDecoder.decode(cookieValues[0], "UTF-8");
						String password = URLDecoder.decode(cookieValues[1], "UTF-8");
						System.out.println("当前登录用户的：用户名"+userName+", 当前登录用户的：密码"+password);
					} catch (UnsupportedEncodingException e) {
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {}

}
