package org.huel.beasp.handler.user;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.BookUser;
import org.huel.beasp.entity.book.Category;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.entity.user.Visitor;
import org.huel.beasp.exception.BeaspFrontException;
import org.huel.beasp.handler.dto.AjaxOper;
import org.huel.beasp.service.book.BookService;
import org.huel.beasp.service.book.BookUserService;
import org.huel.beasp.service.user.UserService;
import org.huel.beasp.service.user.VisitorService;
import org.huel.beasp.utils.Constants;
import org.huel.beasp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 指定人空间
 * @author 001
 *
 */
@Controller
public class SpecifyHandler {
	@Autowired private BookService bookService;
	@Autowired private UserService userService;
	@Autowired private VisitorService visitorService;
	@Autowired private BookUserService bookUserService;
	/**
	 * 指定人空间首页
	 * @param id
	 * @return
	 */
	@RequestMapping("/space/u/uid/{id}")
	public String index(@PathVariable("id") Integer id, HttpSession session, 
			Map<String, Object> map, @RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr) {
		if(WebUtils.getUser(session) != null) {
			User vuser = WebUtils.getUser(session);//访问者
			if(vuser.getId() == id) {//如果以这种方式访问的是自己的空间，那就重定向
				return "redirect:/space/index";
			} else {//追加此人为访客
				visitor(vuser, id);
			} 
		}
		Page<Book> books = null;
		List<Category> categories = null;
		User user = null;
		if(id != null && id > 0) {
			user = userService.getById(id);
			if(user == null)  
				throw new BeaspFrontException();
			books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), 
					Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT, State.WAITCONFIRM, user);
			categories = bookService.getCategoryByUserIdAndState(id, State.WAITCONFIRM);
		} else 
			throw new BeaspFrontException();
		getMap(map, user, categories, books, id, WebUtils.getUser(session));
		return "front/user/specify/index";
	}
	
	/**
	 * TA人简介
	 * @param uidStr
	 * @param map
	 * @return
	 */
	@RequestMapping("/space/profile")
	public String profile(@RequestParam("uid") Integer uid, Map<String, Object> map) {
		if(uid  != null && uid > 0) {
			User user = userService.getById(uid);
			if(user == null){
				throw new BeaspFrontException();
			}
			map.put("spuser", user);
			map.put("state", "profile");
		} else 
			throw new BeaspFrontException();
		return "front/user/specify/profile";
	}
	
	/**
	 * TA的浏览
	 * @return
	 */
	@RequestMapping("/space/u/uid/{id}/browse")
	public String hisBrowse(@PathVariable("id") Integer id, HttpSession session, 
			Map<String, Object> map, @RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr) {
		User user = null;
		Page<BookUser> books = null;
		if(id != null && id > 0) {
			user = userService.getById(id);
			if(user == null)
				throw new BeaspFrontException();
			books = bookUserService.findByUser_IdAndState(id, State.BROWSE, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
		}
		getMapWithBookUser(map, user, books, id, WebUtils.getUser(session));
		map.put("state", "browse");
		return "front/user/specify/browse";
	}
	
	/**
	 * TA的点赞
	 * @return
	 */
	@RequestMapping("/space/u/uid/{id}/praise")
	public String hisPraise(@PathVariable("id") Integer id, HttpSession session, 
			Map<String, Object> map, @RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr) {
		User user = null;
		Page<BookUser> books = null;
		if(id != null && id > 0) {
			user = userService.getById(id);
			if(user == null)
				throw new BeaspFrontException();
			books = bookUserService.findByUser_IdAndState(id, State.PRAISE, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
		}
		getMapWithBookUser(map, user, books, id, WebUtils.getUser(session));
		map.put("state", "praise");
		return "front/user/specify/praise";
	}
	
	/**
	 * TA的收藏
	 * @return
	 */
	@RequestMapping("/space/u/uid/{id}/collection")
	public String hisCollection(@PathVariable("id") Integer id, HttpSession session, 
			Map<String, Object> map, @RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr) {
		User user = null;
		Page<BookUser> books = null;
		if(id != null && id > 0) {
			user = userService.getById(id);
			if(user == null)
				throw new BeaspFrontException();
			books = bookUserService.findByUser_IdAndState(id, State.COLLECTION, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
		}
		getMapWithBookUser(map, user, books, id, WebUtils.getUser(session));
		map.put("state", "collection");
		return "front/user/specify/collection";
	}
	
	/**
	 * 取消收藏:思路
	 * 	1.先判断用户是否登录，
	 *  2.在判断该用户是否已经收藏过该书籍
	 *    	如果已经收藏，则取消收藏
	 *  	如果未收藏，则取消失败	
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/space/ajaxfollowcancel", method=RequestMethod.POST)
	public AjaxOper followCancel(@RequestParam("book") Integer id,
			 HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "取消收藏");
		if(WebUtils.getUser(request) != null) {
			User user = WebUtils.getUser(request);
			if(id != null && id > 0) {
				Book book = bookService.getOneById(id);
				if(book != null) {
					BookUser bu = bookUserService.findByBook_IdAndUser_IdAndState(id, user.getId(), State.COLLECTION);
					if(bu != null) {
						bookUserService.delete(bu);//取消收藏
						bookService.updateBookFollowCancel(book);//收藏数量+1
					} else 
						ajaxOper = new AjaxOper(1, "取消失败");
				} else 
					ajaxOper = new AjaxOper(1, "取消失败");
			}else
				ajaxOper = new AjaxOper(1, "取消失败");
		}
		return ajaxOper;
	}
	
	/**
	 * 增加收藏:思路
	 * 	1.先判断用户是否登录，
	 *  2.在判断该用户是否已经收藏过该书籍
	 *  	如果已经收藏，则收藏失败
	 *  	如果未收藏，则增加收藏
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/space/ajaxfollow", method=RequestMethod.POST)
	public AjaxOper follow(@RequestParam("book") Integer id,
			 HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "增加收藏");
		if(WebUtils.getUser(request) != null) {
			User user = WebUtils.getUser(request);
			if(id != null && id > 0) {
				Book book = bookService.getOneById(id);
				if(book != null) {
					BookUser bookUser = bookUserService.findByBook_IdAndUser_IdAndState(id, user.getId(), State.COLLECTION);
					if(bookUser == null) {
						BookUser bu = new BookUser(book, user, State.COLLECTION);
						bookUserService.save(bu);//增加收藏
						bookService.updateBookFollow(book);//收藏数量+1
					} else {
						ajaxOper = new AjaxOper(1, "收藏失败");
					}
				} else
					ajaxOper = new AjaxOper(1, "收藏失败");
			}else
				ajaxOper = new AjaxOper(1, "收藏失败");
		}
		return ajaxOper;
	}
	
	/**
	 * 访客哦
	 * @param vuser
	 * @param id
	 */
	private void visitor(User vuser, Integer id) {
		List<User> users = visitorService.findVisitorByUser_Id(id);//按被访问者id 去查询所有访客
		Visitor visitor = null;
		if(users.contains(vuser)) {//如果所有访客中包含该用户
			visitor = visitorService.findVisitorByUser_IdAndVisitor_Id(id, vuser.getId());//定位到此记录
			visitor.setVisitTime(new Date());//修改为当前时间
		} else {//如果不包含该记录
			visitor = new Visitor(userService.getById(id), vuser);
		}
		visitorService.save(visitor);//保存访客与被访者信息
	}
	
	/**
	 * 指定人空间首页(基于书籍类别id)
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/u/uid/{id}/cid/{cId}")
	public String index(@PathVariable("id") Integer id, @PathVariable("cId") Integer cId, @RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session,Map<String, Object> map) {
		if(WebUtils.getUser(session) != null){
			User vuser = WebUtils.getUser(session);
			if(vuser.getId() == id) {//如果以这种方式访问的是自己的空间，那就重定向
				return "redirect:/space/index/cid/{cId}";
			} else {//追加此人为访客
				visitor(vuser, id);
			}
		}
		Page<Book> books = null;
		List<Category> categories = null;
		User user = null;
		if(id != null && id > 0) {
			user = userService.getById(id);
			if(user == null) 
				throw new BeaspFrontException();
			if(cId != null && cId > 0){
				books = bookService.findByCategory_IdAndUser_IdAndState(cId, id, 
						State.WAITCONFIRM, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
				categories = bookService.getCategoryByUserIdAndState(id, State.WAITCONFIRM);
			} else {
				books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), 
						Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT, State.WAITCONFIRM, user);
				categories = bookService.getCategoryByUserIdAndState(id, State.WAITCONFIRM);
			}
			getMap(map, user, categories, books, cId, WebUtils.getUser(session));
		} else 
			throw new BeaspFrontException();
		return "front/user/specify/index";
	}
	
	/**
	 * 获取特定用户的 (发布书籍、分享书籍、交换书籍、待审核书籍
	 * @return
	 */
	@RequestMapping("/space/u/uid/{id}/book/s/{status}")
	public String hisBook(@PathVariable("id") Integer id, @PathVariable("status") Integer status,
			@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session,Map<String, Object> map) {
		if(WebUtils.getUser(session) != null){
			User vuser = WebUtils.getUser(session);
			if(vuser.getId() == id) {//如果以这种方式访问的是自己的空间，那就重定向
				return "redirect:/space/book/s/{status}";
			} else {//追加此人为访客
				visitor(vuser, id);
			}
		}
		Page<Book> books = null;
		List<Category> categories = null;
		User user = null;
		if(id != null && id > 0) {
			user = userService.getById(id);
			if(user == null) 
				throw  new BeaspFrontException();
			if(status != null && status > 0 && status < 4) {
				if(status == 1) {
					books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.EXCHANGE, user);
					categories = bookService.getCategoryByUserIdAndState(id, State.EXCHANGE);
				} else if(status == 2){
					books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.SHARE, user);
					categories = bookService.getCategoryByUserIdAndState(id, State.SHARE);
				} else if(status == 3){
					books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.RELEASE, user);
					categories = bookService.getCategoryByUserIdAndState(id, State.RELEASE);
				}
			} else {
				books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.WAITCONFIRM, user);
				categories = bookService.getCategoryByUserIdAndState(id, State.WAITCONFIRM);
			}
			
		} else 
			throw new BeaspFrontException();
		getMap(map, user, categories, books, id, WebUtils.getUser(session));
		return "front/user/specify/index";
	}
	
	/**
	 * 获取指定用户的 (发布书籍、分享书籍、交换书籍、待审核书籍),基于书籍类别id
	 * @param status
	 * @param cId
	 * @param pageNoStr
	 * @param session
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/space/u/uid/{id}/book/s/{status}/cid/{cId}")
	public String hisBookByCategoryId(@PathVariable("id") Integer id,@PathVariable("status") Integer status, @PathVariable("cId") Integer cId,
			@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr, HttpSession session,
			Map<String, Object> map) {
		if(WebUtils.getUser(session) != null) {
			User vuser = WebUtils.getUser(session);
			if(vuser.getId() == id) {//如果以这种方式访问的是自己的空间，那就重定向
				if(cId != null && cId > 0) {
					return "redirect:/space/book/s/{status}/cid/{cId}";
				}
			} else {//追加此人为访客
				visitor(vuser, id);
			}
		}
		Page<Book> books = null;
		List<Category> categories = null;
		User user = null;
		if(id != null && id > 0) {
			 user = userService.getById(id);
			if(user == null) 
				throw  new BeaspFrontException();
			if(status != null && status > 0 && status < 4) {
				if(cId != null && cId > 0) {
					if(status == 1) {
						books = bookService.findByCategory_IdAndUser_IdAndState(cId, id, State.EXCHANGE, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
						categories = bookService.getCategoryByUserIdAndState(id, State.EXCHANGE);
					} else if(status == 2) {
						books = bookService.findByCategory_IdAndUser_IdAndState(cId, id, State.SHARE, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
						categories = bookService.getCategoryByUserIdAndState(id, State.SHARE);
					} else if(status == 3) {
						books = bookService.findByCategory_IdAndUser_IdAndState(cId, id, State.RELEASE, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
						categories = bookService.getCategoryByUserIdAndState(id, State.RELEASE);
					}
				} else {
					hisBook(id, status, pageNoStr, session, map);
				}
			} else {
				if(cId != null && cId > 0) {
					books = bookService.findByCategory_IdAndUser_IdAndState(cId, id, State.WAITCONFIRM, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
					categories = bookService.getCategoryByUserIdAndState(id, State.WAITCONFIRM);
				} else {
					hisBook(id, status, pageNoStr, session, map);
				}
					
			}
		} else 
			throw new BeaspFrontException();
		getMap(map, user, categories, books, id, WebUtils.getUser(session));
		return "front/user/specify/index";
	}
	
	/**
	 * map
	 * @param map
	 * @param user
	 * @param categories
	 * @param books
	 * @param id
	 * @param vuser
	 */
	private void getMapWithBookUser(Map<String, Object>  map, User user, 
			Page<BookUser> books, Integer id, User vuser) {
		map.put("spuser", user);//获取被访问的用户
		if(books != null) {
			map.put("page", books);
		}
		map.put("count", bookService.getCountByUserId(id, Arrays.asList(State.RECYCLEBIN, State.INVISIBLE)));//获取访问用户的书籍数量
		map.put("visitors", visitorService.findVisitorByUser_Id(id));
		map.put("browseCount", bookUserService.getCountByUserIdAndState(id, State.BROWSE));
		map.put("collectionCount", bookUserService.getCountByUserIdAndState(id, State.COLLECTION));
		map.put("praiseCount", bookUserService.getCountByUserIdAndState(id, State.PRAISE));
		List<BookUser> bus = bookUserService.findByUser_IdAndState(vuser.getId(), State.COLLECTION);
		map.put("collections", bus);//用户收藏的书籍
		//以字符串形式去连接所有的收藏书籍的id：
		/**
		 * 作用是：<i class="btn-add-collection <c:if test='${fn:contains(collectionstrs, book.id) }'>btn-remove-collection </c:if>"></i>
		 * 显示收藏
		 */
		if(bus != null && bus.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for(BookUser bu : bus) {
				sb.append(String.valueOf(bu.getBook().getId())).append(",");
			}
			map.put("collectionstrs", sb.deleteCharAt(sb.length()-1));
		}
	}
	
	/**
	 * map
	 * @param map
	 * @param user 被访问用户
	 * @param categories 所有书籍类别
	 * @param books 所有书籍
	 * @param id 被访问用户id
	 * @param vuser 登录用户
	 */
	private void getMap(Map<String, Object> map, User user, List<Category> categories,
			Page<Book> books, Integer id, User vuser)  {
		map.put("spuser", user);//获取访问的用户
		if(books != null) {
			map.put("page", books);
		}
		if(categories != null) {
			map.put("categories", categories);
		}
		map.put("count", bookService.getCountByUserId(id, Arrays.asList(State.RECYCLEBIN, State.INVISIBLE)));//获取访问用户的书籍数量
		map.put("visitors", visitorService.findVisitorByUser_Id(id));
		map.put("browseCount", bookUserService.getCountByUserIdAndState(id, State.BROWSE));
		map.put("collectionCount", bookUserService.getCountByUserIdAndState(id, State.COLLECTION));
		map.put("praiseCount", bookUserService.getCountByUserIdAndState(id, State.PRAISE));
		List<BookUser> bus = bookUserService.findByUser_IdAndState(vuser.getId(), State.COLLECTION);
		map.put("collections", bus);//用户收藏的书籍
		//以字符串形式去连接所有的收藏书籍的id：
		/**
		 * 作用是：<i class="btn-add-collection <c:if test='${fn:contains(collectionstrs, book.id) }'>btn-remove-collection </c:if>"></i>
		 * 显示收藏
		 */
		if(bus != null && bus.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for(BookUser bu : bus) {
				sb.append(String.valueOf(bu.getBook().getId())).append(",");
			}
			map.put("collectionstrs", sb.deleteCharAt(sb.length()-1));
		}
	}
}
