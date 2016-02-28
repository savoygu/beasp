package org.huel.beasp.utils;

import javax.servlet.http.HttpServletRequest;
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
}
