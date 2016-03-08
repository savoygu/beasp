package org.huel.beasp.web.interceptor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.huel.beasp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
/**
 * Url过滤拦截器
 * @author 001
 *
 */
public class UrlFilterInterceptor implements HandlerInterceptor {

	@Autowired private RequestMappingHandlerMapping handlerMapping;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(request.getSession().getAttribute("allUrls") != null) {//用allUrls匹配当前请求url
			Set<String> allUrls = (Set<String>) request.getSession().getAttribute("allUrls");
			if(!allUrls.contains(WebUtils.getRequestURIWithParam(request))) {
				response.sendRedirect("/error/noexists");
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {//获取所有urls
		Set<String> allUrls = new HashSet<String>();
		Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
		Iterator<Entry<RequestMappingInfo, HandlerMethod>>  iterator = map.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<RequestMappingInfo, HandlerMethod> entry = (Entry<RequestMappingInfo, HandlerMethod>) iterator.next();
			System.out.println(entry.getKey()+"--------------------"+entry.getValue());
			RequestMappingInfo info = entry.getKey();
			PatternsRequestCondition patterns = info.getPatternsCondition();
			Set<String> urls = patterns.getPatterns();
			allUrls.addAll(urls);
			/*System.out.println("info.getName():"+info.getName());
			System.out.println("info.getConsumesCondition():"+info.getConsumesCondition());
			System.out.println("info.getCustomCondition():"+info.getCustomCondition());
			System.out.println("info.getHeadersCondition():"+info.getHeadersCondition());
			System.out.println("info.getMatchingCondition(request):"+info.getMatchingCondition(request));*/
			//请求方式get/post/put/delete
			//System.out.println("info.getMethodsCondition():"+info.getMethodsCondition());
			//请求参数
			//System.out.println("info.getParamsCondition():"+info.getParamsCondition());
			//请求url地址
			//System.out.println("info.getPatternsCondition():"+info.getPatternsCondition());
			//System.out.println("info.getProducesCondition():"+info.getProducesCondition());
		}
		request.getSession().setAttribute("allUrls", allUrls);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
