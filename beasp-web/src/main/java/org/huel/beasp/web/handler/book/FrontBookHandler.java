package org.huel.beasp.web.handler.book;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.BookUser;
import org.huel.beasp.entity.book.Category;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.entity.book.Style;
import org.huel.beasp.entity.common.PageView;
import org.huel.beasp.entity.common.QueryResult;
import org.huel.beasp.entity.user.Comment;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.exception.BeaspFrontException;
import org.huel.beasp.service.book.BookService;
import org.huel.beasp.service.book.BookUserService;
import org.huel.beasp.service.book.CategoryService;
import org.huel.beasp.service.user.CommentService;
import org.huel.beasp.utils.Constants;
import org.huel.beasp.utils.WebUtils;
import org.huel.beasp.web.handler.dto.AjaxOper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台书籍控制层:
 * 书籍列表：书籍单页、书籍列表
 * 个人空间：书籍上传、书籍编辑
 * @author 001
 *
 */
@Controller
public class FrontBookHandler {
	@Autowired private CategoryService categoryService;
	@Autowired private BookService bookService;
	@Autowired private BookUserService bookUserService;
	@Autowired private CommentService commentService;
	
	/**
	 * 书籍搜索
	 * @return
	 */
	@RequestMapping("/book/search")
	public String searchBooks(@RequestParam("words") String words, @RequestParam("pos") String pos,
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			Map<String, Object> map, HttpSession session) {
		PageView<Book> pageView = new PageView<Book>(Constants.SEARCH_PAGE_SIZE_TWELVE_FRONT, WebUtils.getCurrentPage(pageNoStr));
		QueryResult<Book> qr = bookService.search(pageView.getFirstResult(), pageView.getMaxResult(), words);
		if(pos != null && !"".equals(pos)) {
			List<Book> books = qr.getResultList();
			List<Book> removeBooks = new ArrayList<Book>(); 
			for(Book book : books) {//获取当前城市的书籍
				if(!book.getUser().getCity().equals(pos)) {
					removeBooks.add(book);
				}
			}
			books.removeAll(removeBooks);
			qr.setResultList(books);
			qr.setTotalRecord(qr.getTotalRecord()-removeBooks.size());
			session.setAttribute("pos", pos);
		}
		pageView.setQueryResult(qr);
		map.put("pageView", pageView);
		map.put("words", words);
		return "front/book/search";
	}
	
	/**
	 * 取消点赞:思路
	 * 	1.先判断用户是否登录，
	 *  2.在判断该用户是否已经点赞过该书籍
	 *  	如果已经点赞，则删除点赞记录
	 *  	如果未点赞，则取消失败
	 *  3.更新书籍点赞数量-1
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/space/ajaxpraisecancel", method=RequestMethod.POST)
	public AjaxOper praiseCancel(@RequestParam("book") Integer id,
			 HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "取消点赞");
		if(WebUtils.getUser(request) != null) {
			User user = WebUtils.getUser(request);
			if(id != null && id > 0) {
				Book book = bookService.getOneById(id);
				if(book != null) {
					BookUser bu = bookUserService.findByBook_IdAndUser_IdAndState(id, user.getId(), State.PRAISE);
					if(bu != null) {
						bookUserService.delete(bu);//取消关注
						bookService.updateBookPraiseCancel(book);//点赞数量-1
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
	 * 增加点赞:思路
	 * 	1.先判断用户是否登录，
	 *  2.在判断该用户是否已经点赞过该书籍
	 *  	如果已经点赞，则点赞失败
	 *  	如果未点赞，则增加点赞
	 *  3.更新书籍点赞数量+1
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/space/ajaxpraise", method=RequestMethod.POST)
	public AjaxOper praise(@RequestParam("book") Integer id,
			 HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "增加点赞");
		if(WebUtils.getUser(request) != null) {
			User user = WebUtils.getUser(request);
			if(id != null && id > 0) {
				Book book = bookService.getOneById(id);
				if(book != null) {
					BookUser bookUser = bookUserService.findByBook_IdAndUser_IdAndState(id, user.getId(), State.PRAISE);
					if(bookUser == null) {
						BookUser bu = new BookUser(book, user, State.PRAISE);
						bookUserService.save(bu);//增加点赞
						bookService.updateBookPraise(book);//点赞数量+1
					} else {
						ajaxOper = new AjaxOper(1, "点赞失败");
					}
				} else
					ajaxOper = new AjaxOper(1, "点赞失败");
			}else
				ajaxOper = new AjaxOper(1, "点赞失败");
		}
		return ajaxOper;
	}
	
	/**
	 * 评论书籍
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/book/comment", method=RequestMethod.POST)
	public AjaxOper commentBook(@RequestParam("content") String content, 
			@RequestParam("bid") Integer bookId, HttpSession session) {
		AjaxOper ajaxOper = new AjaxOper(0, "评论成功");
		if(content != null && !"".equals(content)) {
			Book book = bookService.getOneById(bookId);
			if(book != null) {
				User user = WebUtils.getUser(session);
				if(user != null) {
					Comment comment = new Comment(content, book, user);
					commentService.save(comment);//保存评论
					
					//追加数据
					Map<String, Object> data = new HashMap<String, Object>();
					if(user.getPhotoName() != null && !"".equals(user.getPhotoName())) {
						data.put("portrait", user.getImage40FullPath());
					} else {
						data.put("portrait", user.getDefaultImage40Path());//默认图片
					}
					data.put("nickname", user.getUserName());
					data.put("create_time", "0秒前");
					ajaxOper.setData(data);
				} else 
					ajaxOper = new AjaxOper(1, "评论失败");
			} else 
				ajaxOper = new AjaxOper(1, "评论失败");
		} else 
			ajaxOper = new AjaxOper(1, "评论失败");
		return ajaxOper;
	}
	
	/**
	 * 书籍单页
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("/book/view/{id}")
	public String viewBook(@PathVariable("id") Integer id, Map<String, Object> map,
			HttpServletRequest request, @RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr) {
		Book book = bookService.getOneById(id);
		//书籍详细信息
		if(book != null) {
			/**
			 * 书籍浏览数量：
			 * 		第一步（必须是用户）：目的是记录用户浏览过的书籍
			 * 			1.判断是否有用户登录 
			 * 			2.判断该用户是否已经浏览过该书籍
			 * 				2.1如果浏览过，则更新浏览时间
			 * 				2.2如果没有浏览过，则创建BookUser bu = new BookUser(); 
			 * 			3.添加关联的书籍与用户
			 * 		第二步（不管是用户还是游客）：
			 * 			4.修改书籍浏览数量
			 */
			if(WebUtils.getUser(request) != null) {//第一步：1.判断是否有用户登录 
				//2.判断该用户是否已经浏览过该书籍
				User user = WebUtils.getUser(request);
				BookUser bu = bookUserService.findByBook_IdAndUser_IdAndState(id, user.getId(), State.BROWSE);
				if(bu == null) {//2.2如果没有浏览过
					bu = new BookUser(book, user, State.BROWSE);
				} else {//2.1 如果浏览过
					bu.setCreateTime(new Date());//设置为当前时间
				}
				bu = bookUserService.save(bu);//3.添加关联
				request.getSession().setAttribute("bu", bu);
			}
			//第二步：4.修改书籍浏览数量
			bookService.updateBookBrowse(book);
			
			Set<Style> styles = new HashSet<Style>();
			for(Style style : book.getStyles()) {//获取选中并且可见的样式
				if(style.isChoice() && style.isVisible()) {
					styles.add(style);
					break;
				}
			}
			book.setStyles(styles);
			map.put("book", book);//当前书籍信息
			map.put("praise", false);//没有被当前用户点赞
			map.put("collection", false);//没有被当前用户收藏
			
			
			/**
			 * 书籍是否被点赞、收藏
			 */
			if(WebUtils.getUser(request) != null) {
				//点赞
				User user = WebUtils.getUser(request);
				BookUser praise = bookUserService.findByBook_IdAndUser_IdAndState(id, user.getId(), State.PRAISE);
				if(praise != null) {//被当前用户点赞
					map.put("praise", true);
				}
				BookUser collection = bookUserService.findByBook_IdAndUser_IdAndState(id, user.getId(), State.COLLECTION);
				if(collection != null) {//被当前用户收藏
					map.put("collection", true);
				}
			}
				
			/**
			 * 书籍评论
			 */
			Page<Comment> comments = 
					commentService.findByBook_Id(WebUtils.getCurrentPage(pageNoStr), Constants.COMMENT_PAGE_SIZE_TWELVE_FRONT, id);
			map.put("page", comments);
			
			//评论时间个性化
			Map<Integer, String> comment_time = new HashMap<Integer, String>();
			for(Comment comment : comments.getContent()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date nowdate = new Date();//当前时间
				long time_sub = nowdate.getTime()-comment.getCommentTime().getTime();//时间差
				System.out.println(time_sub);
				if(time_sub < 1000*60) {//1分钟之内
					comment_time.put(comment.getId(), time_sub/1000+"秒前");
				} else if(time_sub < 1000*60*60){//1小时之内
					comment_time.put(comment.getId(), time_sub/(1000*60)+"分钟前");
				} else if(time_sub < 1000*60*60*24) {//1天之内
					comment_time.put(comment.getId(), time_sub/(1000*60*60)+"小时前");
				} else if(time_sub < 1000*60*60*24*7) {//7天之内
					comment_time.put(comment.getId(), time_sub/(1000*60*60*24)+"天前");
				} else {
					comment_time.put(comment.getId(), sdf.format(comment.getCommentTime()));
				}
			}
			map.put("comment_time", comment_time);
		}
		//书籍导航菜单
		if(book != null) {
			Category category = book.getCategory();
			if(category != null) {
				List<Category> cates = new ArrayList<Category>();
				cates.add(category);
				Category parent = category.getParent();
				while(parent != null) {
					cates.add(parent);
					parent = parent.getParent();
				}
				map.put("menucates", cates);//菜单
			}
		} else {
			throw new BeaspFrontException("书籍不存在");
		}
		return "front/book/view";
	}
	
	/**
	 * 书籍列表
	 * @param map
	 * @param pageNoStr
	 * @param sort
	 * @param id
	 * @param isExchange
	 * @return
	 */
	@RequestMapping(value="/book/list/category/{id}")
	public String searchBookList(Map<String, Object> map, 
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			@RequestParam(value="sort", required=false, defaultValue="last") String sort,
			@PathVariable("id") Integer id,
			@RequestParam(value="is_exchange", required=false, defaultValue="0") int isExchange) {
		Page<Book> books = null;
		List<Integer> ids = null;
		if(id > 0){
			ids = new ArrayList<Integer>();
			ids.add(id);
			this.getSubids(ids, new Integer[]{id});
			books = bookService.findByCategory_IdInAndStateIn(ids, WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_SIXTEEN_FRONT, sort, isExchange);
			//导航菜单
			Category category = categoryService.getById(id);
			if(category != null) {
				List<Category> cates = new ArrayList<Category>();
				cates.add(category);
				Category parent = category.getParent();
				if(parent == null) {//当前选择的类别的父类是否为空, 如果为空说明点击的是分类(即顶级类别), 那就获取当前类别的所欲子类别
					map.put("categories", categoryService.getByParentId(id));
					/*map.put("cateChildid", true);*/
				}else {//如果不为空, 说明点击的是方向(即二级类别), 那就获取当前类别父类的所有子类别, 也就是其兄弟类别
					map.put("categories", categoryService.getByParentId(parent.getId()));
					map.put("cateParentid", parent.getId());
				}
				while(parent != null) {
					cates.add(parent);
					parent = parent.getParent();
				}
				map.put("menucates", cates);//菜单
				map.put("cateid", id);//类别id
			}
		} else {
			books  = bookService.findByStateIn(WebUtils.getCurrentPage(pageNoStr), 
					Constants.PAGE_SIZE_TWELVE_FRONT, sort, isExchange);//获取所有
			map.put("booklist", true);
			map.put("categories", categoryService.getByParentIsNotNull());//获取所有父类别不为空的类别
		}
		map.put("page", books);
		return "front/book/list";
	}
	/**
	 * 获取子类别
	 */
	private void getSubids(List<Integer> outids, Integer[] ids) {
		List<Integer> subids = categoryService.getSubCategoryIds(ids);
		if(subids != null && subids.size() > 0) {
			outids.addAll(subids);
			getSubids(outids, subids.toArray(new Integer[]{subids.size()}));
		}
	}
}
