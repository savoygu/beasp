package org.huel.beasp.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.user.User;

public class WebUtils {
	
	/**
	 * 获取登录用户
	 * @param session
	 * @return
	 */
	public static User getUser(HttpSession session) {
		return (User) session.getAttribute("user");
	}
	
	/**
	 * 获取登录用户
	 * @param request
	 * @return
	 */
	public static User getUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
	
	 /***
     * 获取URI的路径,如路径为http://www.babasport.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request){     
        return request.getRequestURI();
    }
    /**
     * 获取完整请求路径(含内容路径及请求参数)
     * @param request
     * @return
     */
    public static String getRequestURIWithParam(HttpServletRequest request){     
        return getRequestURI(request) + (request.getQueryString() == null ? "" : "?"+ request.getQueryString());
    }
	/**
	 * 获取当前页
	 * @param pageNoStr
	 * @return
	 */
	public static int getCurrentPage(String pageNoStr) {
		int pageNo = 1;
		if(pageNoStr != null && !"".equals(pageNoStr)) {
			try {
				//对 pageNo 的校验
				pageNo = Integer.parseInt(pageNoStr);
				if(pageNo < 1) {
					pageNo = 1;
				}
			} catch (Exception e) {}
		}
		return pageNo;
	}
	
	/**
	 * 获取书籍图片所在真实路径
	 * @param book
	 * @param request
	 * @return
	 */
	public static String getRealPathByBook(Book book, HttpServletRequest  request) {
		String pathDir = "/resources/images/book/" + book.getCategory().getId() + "/" + book.getId();//构建文件保存的目录
		//得到图片保存的目录的真实路径
		return request.getSession().getServletContext().getRealPath(pathDir);
	}
	
	/**
     * 添加cookie
     * @param response
     * @param name cookie的名称
     * @param value cookie的值
     * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {        
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge>0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    
    /**
     * 获取cookie的值
     * @param request
     * @param name cookie的名称
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
    	Map<String, Cookie> cookieMap = WebUtils.readCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie.getValue();
        }else{
            return null;
        }
    }
    
    
    protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }
}
