package org.huel.beasp.web.interceptor;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.huel.beasp.entity.user.User;
import org.huel.beasp.service.user.UserService;
import org.huel.beasp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
/**
 * 自动登录拦截器
 * @author 001
 *
 */
public class AutoLoginInterceptor implements HandlerInterceptor {

	@Autowired private UserService userService;
	@Autowired private RequestMappingHandlerMapping handlerMapping;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		User user = WebUtils.getUser(request);
		if(user == null) {//未登录
			String cookieValue = WebUtils.getCookieByName(request, "beaspName");//尝试获取cookie中的用户名密码
			if(cookieValue != null && !"".equals(cookieValue)) {//如果存在
				String [] cookieVals = cookieValue.split(":");
				String cookieVal0 = URLDecoder.decode(cookieVals[0], "UTF-8");
				if(cookieVal0.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
					user = userService.getByEmailAndPassword(cookieVal0, URLDecoder.decode(cookieVals[1], "UTF-8"));
				} else if(cookieVal0.matches("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$")){
					user = userService.getByPhoneAndPassword(cookieVal0, URLDecoder.decode(cookieVals[1], "UTF-8"));
				} else {
					user = userService.getByUserNameAndPassword(cookieVal0, URLDecoder.decode(cookieVals[1], "UTF-8"));
				}
				if(user != null) {
					System.out.println("org.huel.beasp.web.interceptor.AutoLoginInterceptor:"+cookieVals[0]+":"+cookieVals[1]);
					session.setAttribute("user", user);
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
