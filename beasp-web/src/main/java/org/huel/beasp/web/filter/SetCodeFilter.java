package org.huel.beasp.web.filter;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.Velocity;
/**
 * 编码设置UTF-8
 * @author 001
 *
 */
public class SetCodeFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		/**
		 * 初始化模版文件
		 */
		Properties prop = new Properties();
		prop.put("runtime.log", filterConfig.getServletContext().getRealPath("/WEB-INF/log/velocity.log"));
		prop.put("file.resource.loader.path", filterConfig.getServletContext().getRealPath("/WEB-INF/vm"));
		prop.put("input.encoding", "UTF-8");
		prop.put("output.encoding", "UTF-8");
		Velocity.init(prop);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
