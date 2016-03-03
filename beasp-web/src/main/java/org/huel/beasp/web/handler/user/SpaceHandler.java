package org.huel.beasp.web.handler.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.BookUser;
import org.huel.beasp.entity.book.Category;
import org.huel.beasp.entity.book.Exchange;
import org.huel.beasp.entity.book.Share;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.entity.book.Style;
import org.huel.beasp.entity.book.Want;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.exception.BeaspException;
import org.huel.beasp.service.book.BookService;
import org.huel.beasp.service.book.BookUserService;
import org.huel.beasp.service.book.ExchangeService;
import org.huel.beasp.service.book.ShareService;
import org.huel.beasp.service.user.UserService;
import org.huel.beasp.service.user.VisitorService;
import org.huel.beasp.utils.Constants;
import org.huel.beasp.utils.FileUtils;
import org.huel.beasp.utils.WebUtils;
import org.huel.beasp.web.handler.dto.AjaxOper;
import org.huel.beasp.web.handler.dto.Wants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
/**
 * 用户空间控制层
 * 个人书籍：我的书籍、我的分享、我的交换、我的发布、我的待审核、我的浏览
 * 书籍样式：我的样式
 * @author 001
 *
 */
@Controller
public class SpaceHandler {
	@Autowired private UserService userService;
	@Autowired private BookService bookService;
	@Autowired private VisitorService visitorService;
	@Autowired private BookUserService bookUserService;
	@Autowired private ShareService shareService;
	@Autowired private ExchangeService exchangeService;
	/**
	 * 空间首页
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/index")
	public String index(@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session,Map<String, Object> map) throws ParseException {
		Page<Book> books  = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.WAITCONFIRM, WebUtils.getUser(session));
		List<Date> times = bookService.findCreateTimeByUser_IdAndState(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, WebUtils.getUser(session).getId(), "WAITCONFIRM");
		List<Category> categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.WAITCONFIRM);
		getMapWithList(map, times, categories);
		getMapWithBook(books, session, map);
		return "front/user/space/index";
	}
	
	/**
	 * 空间首页(基于书籍类别id)
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/index/cid/{cId}")
	public String index(@PathVariable("cId") Integer cId,@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session,Map<String, Object> map) throws ParseException {
		Page<Book> books  = null;
		List<Date> times = null;
		List<Category> categories = null;
		if(cId != null && cId > 0) {
			books = bookService.findByCategory_IdAndUser_IdAndState(cId, WebUtils.getUser(session).getId(), State.WAITCONFIRM, 
					WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
			times = bookService.findCreateTimeByUser_IdAndStateAndCategory_Id(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT,
					WebUtils.getUser(session).getId(), "WAITCONFIRM", cId);
			categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.WAITCONFIRM);
		} else {
			index(pageNoStr, session, map);
		}
		getMapWithList(map, times, categories);
		getMapWithBook(books, session, map);
		return "front/user/space/index";
	}
	
	static String originPath = null;
	static String newPath = null;
	Integer originCateId = null;
	Integer newCateId = null;
	@ModelAttribute
	public void getBook(@RequestParam(value="id", required=false) Integer id,
			Map<String, Object> map, HttpServletRequest request) {
		if(id != null && id>0) {
			Book book = bookService.getOneById(id);
			originPath = WebUtils.getRealPathByBook(book, request);//获取书籍原始路径
			originCateId = book.getCategory().getId();
			System.out.println(originPath+"---"+originCateId);
			book.setCategory(null);//解决修改类别id
			map.put("book", book);
		}
	}
	
	/**
	 * 修改书籍信息
	 * @param book
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/space/book/b/{id}", method=RequestMethod.PUT)
	public String update(Book book, Map<String, Object> map, HttpServletRequest request) {
		newPath = WebUtils.getRealPathByBook(book, request);
		newCateId = book.getCategory().getId();
		System.out.println(newPath+"---"+newCateId);
		if(!originCateId.equals(newCateId)) {
			modifyImagePos();		
		}
		bookService.save(book);
		if(book.getState().equals(State.EXCHANGE)) {//判断所编辑书籍的所在状态，并会到指定的状态界面
			return "redirect:/space/book/s/1";
		} else if(book.getState().equals(State.SHARE)) {
			return "redirect:/space/book/s/2";
		} else if(book.getState().equals(State.RELEASE)) {
			return "redirect:/space/book/s/3";
		}
		return "redirect:/space/index";
	}
	
	/**
	 * 修改图片路径
	 */
	public static void modifyImagePos() {
		FileUtils.copyFolder(originPath, newPath);//复制原来的图片目录到新的位置
		FileUtils.delete(originPath);//删除原来的图片目录
	}
	
	/**
	 * 书籍编辑界面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/space/book/b/{id}", method=RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Map<String, Object> map,
			HttpSession session) {
		initUpload(map);
		map.put("book", bookService.getOneById(id));
		getMapWithBook(null, session, map);
		return "front/user/space/edit";
	}
	
	/**
	 * 上传书籍
	 * @param book
	 * @param image
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/space/book/b", method=RequestMethod.POST)
	public String upload(Book book, @RequestParam("image") MultipartFile image,
			HttpServletRequest request, Map<String, Object> map, Wants wants) {
		if(image != null) {
			if(!FileUtils.validateImageFileType(image, image.getContentType(), image.getOriginalFilename())) {
				System.out.println(image.getOriginalFilename()+"--"+image.getName()+"--"+image.getContentType()+"--"+image.getSize());
				map.put("imageErrorMsg", "文件格式不正确,只允许上传gif/jpg/png/bmp图片");
				return "front/user/space/upload";
			}
			//1.获取category 构建图片路径，2.为图片指定一个书籍
			String ext = FileUtils.getExt(image.getOriginalFilename());//获取扩展名
			String fileName = UUID.randomUUID().toString()+"."+ext;//构建文件名称
			/**
			 * 保存样式（级联）
			 */
			book.addBookStyle(new Style(fileName));
			book.setUser(WebUtils.getUser(request));//指定一个所属用户
			//添加想要的(最多四个)
			addWants(book, wants.getName1(), wants.getAuthor1(), wants.getVersion1());
			addWants(book, wants.getName2(), wants.getAuthor2(), wants.getVersion2());
			addWants(book, wants.getName3(), wants.getAuthor3(), wants.getVersion3());
			addWants(book, wants.getName4(), wants.getAuthor4(), wants.getVersion4());
			
			bookService.save(book);
			
			//保存图片到本地服务器
			try {
				FileUtils.saveImageFile(image, book.getCategory().getId(), book.getId(), fileName, request);
			} catch (Exception e) {}
		}
		return "redirect:/space/index";
	}

	private void addWants(Book book, String name, String author, String version) {
		if(isNotNull(name) && isNotNull(author) && isNotNull(version)) {
			Want want = new Want(name, author, version);
			book.addWant(want);
		}
	}
	
	private boolean isNotNull(String attr) {
		return attr != null && !"".equals(attr);
	}
	
	/**
	 * 书籍上传界面
	 * @return
	 */
	@RequestMapping(value="/space/book/b",method=RequestMethod.GET)
	public String input(Map<String, Object> map, HttpSession session) {
		initUpload(map);
		map.put("book", new Book());
		getMapWithBook(null, session, map);
		return "front/user/space/upload";
	}
	/**
	 * 初始化上传
	 * @param map
	 */
	public void initUpload(Map<String, Object> map) {
		Map<String, String> languages = new HashMap<String, String>();//书籍语言
		languages.put("CHINESE", "简体中文");
		languages.put("ENGLISH", "英文");
		map.put("languages", languages);
		Map<Integer,Integer> versions = new HashMap<Integer, Integer>();//书籍版本
		versions.put(1, 1);versions.put(2, 2);versions.put(3, 4);versions.put(4, 4);
		versions.put(5, 5);versions.put(6, 6);versions.put(7, 7);versions.put(8, 8);
		versions.put(9, 9);versions.put(10, 10);versions.put(11, 11);versions.put(12, 12);
		map.put("versions", versions);
		Map<Boolean, String> isOper = new HashMap<Boolean, String>();//书籍是否交换、是否分享
		isOper.put(true, "是");
		isOper.put(false, "否");
		map.put("exchanges", isOper);
		map.put("shares", isOper);
	}
	
	/**
	 * 我的点赞
	 * @param pageNoStr
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/space/praise")
	public String myPraise(@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) {
		Page<BookUser> books = bookUserService.findByUser_IdAndState(WebUtils.getUser(session).getId(), State.PRAISE, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
		getMapWithBookUser(books, session, map);
		map.put("state", "praise");
		return "front/user/space/praise";
	}
	
	/**
	 * 我的收藏
	 * @param pageNoStr
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/space/collection")
	public String myCollection(@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) {
		Page<BookUser> books = bookUserService.findByUser_IdAndState(WebUtils.getUser(session).getId(), State.COLLECTION, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
		getMapWithBookUser(books, session, map);
		map.put("state", "collection");
		return "front/user/space/collection";
	}
	
	/**
	 * 我的浏览
	 * @return
	 */
	@RequestMapping("/space/browse")
	public String myBrowse(@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) {
		Page<BookUser> books = bookUserService.findByUser_IdAndState(WebUtils.getUser(session).getId(), State.BROWSE, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_FIFTEEN_FRONT);
		getMapWithBookUser(books, session, map);
		map.put("state", "browse");
		return "front/user/space/browse";
	}
	
	/**
	 * 交换信息界面
	 * @param id
	 * @return
	 */
	@RequestMapping("/space/exchange/w/{way}/view/{id}")
	public String viewExchange(@PathVariable("way") Integer way, @PathVariable("id") Integer id, 
			Map<String, Object> map) {
		if(way<0 || way >1) return "redirect:/space/exchange/w/{way}";
		if(id != null && id > 0) {
			Exchange exchange = exchangeService.getById(id);
			map.put("exchange", exchange);
		}
		if(way == 1) {
			return "front/user/space/showway1";
		}
		return "front/user/space/showway0";
	}
	
	/**
	 * 我的交换-TA人交换
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/exchange")
	public String indexExchange(@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) throws ParseException {
		Page<Exchange> exchanges = exchangeService.findByUserId(WebUtils.getUser(session).getId(), WebUtils.getCurrentPage(pageNoStr),
				Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
		List<Date> times = exchangeService.getApplyTimeByUserId(WebUtils.getUser(session).getId(), WebUtils.getCurrentPage(pageNoStr), 
				Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
		List<Category> categories = exchangeService.getCategoryByUserId(WebUtils.getUser(session).getId());
		
		getMapWithList(map, times, categories);
		getMapWithExchange(exchanges, session, map);
		map.put("state", "exchange");
		return "front/user/space/exchange";
	}
	
	/**
	 * 我的分享-TA人分享
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/share")
	public String indexShare(@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) throws ParseException {
		/*Page<Book> books  = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.SHAREING, WebUtils.getUser(session));
		List<Date> times = bookService.findCreateTimeByUser_IdAndState(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, WebUtils.getUser(session).getId(), "SHAREING");
		List<Category> categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.SHAREING);
		if(times != null && times.size() > 0) {
			map.put("times", getSortTime(getDistinctTime(times)));
		}
		if(categories != null && categories.size() > 0){
			map.put("categories", categories);
		}
		getMapWithBook(books, session, map);*/
		Page<Share> shares  = shareService.findByTargetUserId(WebUtils.getUser(session).getId(), WebUtils.getCurrentPage(pageNoStr), 
				Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
		List<Date> times = shareService.getApplyTimeByTargetUserId(WebUtils.getUser(session).getId(), WebUtils.getCurrentPage(pageNoStr), 
				Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
		List<Category> categories = shareService.getCategoryByTargetUserId(WebUtils.getUser(session).getId());
		
		getMapWithList(map, times, categories);
		getMapWithShare(shares, session, map);
		map.put("state", "share");
		return "front/user/space/share";
	}

	private void getMapWithList(Map<String, Object> map, List<Date> times,
			List<Category> categories) throws ParseException {
		if(times != null && times.size() > 0) {
			map.put("times", getSortTime(getDistinctTime(times)));
		}
		if(categories != null && categories.size() > 0){
			map.put("categories", categories);
		}
	}
	
	/**
	 * 我的交换-TA人交换
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/exchange/cid/{cId}")
	public String indexExchangeByCategoryId(@PathVariable("cId") Integer cId, @RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) throws ParseException {
		Page<Exchange> exchanges = null;
		List<Date> times = null;
		List<Category> categories = null;
		
		if(cId != null && cId > 0) {
			exchanges = exchangeService.findByTargetCategoryIdAndUserId(cId, WebUtils.getUser(session).getId(), WebUtils.getCurrentPage(pageNoStr),
					Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
			times = exchangeService.getApplyTimeByTargetCategoryIdAndUserId(WebUtils.getUser(session).getId(), cId, WebUtils.getCurrentPage(pageNoStr), 
					Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
			categories = exchangeService.getCategoryByUserId(WebUtils.getUser(session).getId());
			
		} else {
			indexExchange(pageNoStr, session, map);
		}
		
		getMapWithList(map, times, categories);
		getMapWithExchange(exchanges, session, map);
		map.put("state", "exchange");
		return "front/user/space/exchange";
	}
	
	/**
	 * 我的分享-TA人分享
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/share/cid/{cId}")
	public String indexShareByCategoryId(@PathVariable("cId") Integer cId, @RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) throws ParseException {
		/*Page<Book> books  = null;
		List<Date> times = null;
		List<Category> categories = null;
		if(cId != null && cId > 0) {
			books = bookService.findByCategory_IdAndUser_IdAndState(cId, WebUtils.getUser(session).getId(), State.SHAREING, 
					WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
			times = bookService.findCreateTimeByUser_IdAndStateAndCategory_Id(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT,
					WebUtils.getUser(session).getId(), "SHAREING", cId);
			categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.SHAREING);
		} else {
			indexShare(pageNoStr, session, map);
		}
		if(times != null && times.size() > 0) {
			map.put("times", getSortTime(getDistinctTime(times)));
		}
		if(categories != null && categories.size() > 0){
			map.put("categories", categories);
		}*/
		Page<Share> shares = null;
		List<Date> times = null;
		List<Category> categories = null;
		if(cId != null && cId > 0) {
			shares = shareService.findByTargetCategoryIdAndTargetUserId(cId, WebUtils.getUser(session).getId(), WebUtils.getCurrentPage(pageNoStr), 
					Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
			times = shareService.getApplyTimeByTargetUserIdAndCategoryId(WebUtils.getUser(session).getId(), cId, WebUtils.getCurrentPage(pageNoStr), 
					Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
			categories = shareService.getCategoryByTargetUserId(WebUtils.getUser(session).getId());
		} else {
			indexShare(pageNoStr, session, map);
		}
		getMapWithList(map, times, categories);
		getMapWithShare(shares, session, map);
		map.put("state", "share");
		return "front/user/space/share";
	}
	
	/**
	 * 我的交换-交换TA人
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/exchange/w/{way}")
	public String myExchange(@PathVariable("way") Integer way, 
			@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) throws ParseException {
		Page<Exchange> exchanges = null;
		List<Date> times = null;
		List<Category> categories = null;
		User user = WebUtils.getUser(session);
		if(way != null && way > 0 && way < 2) {
			if(way == 1) {//分享TA人
				exchanges = exchangeService.findByExchangerId(user.getId(), WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
				times = exchangeService.getApplyTimeByExchangerId(user.getId(), WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
				categories = exchangeService.getCategoryByExchangerId(WebUtils.getUser(session).getId());
			}
		} else {
			indexExchange(pageNoStr, session, map);
		}
		getMapWithList(map, times, categories);
		getMapWithExchange(exchanges, session, map);
		map.put("state", "exchange");
		return "front/user/space/exchange";
	}

	/**
	 * 我的分享-分享TA人
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/share/w/{way}")
	public String myShare(@PathVariable("way") Integer way, 
			@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) throws ParseException {
		Page<Share> shares = null;
		List<Date> times = null;
		List<Category> categories = null;
		User user = WebUtils.getUser(session);
		if(way != null && way > 0 && way < 2) {
			if(way == 1) {//分享TA人
				shares = shareService.findByUserId(user.getId(), WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
				times = shareService.getApplyTimeByUserId(user.getId(), WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
				categories = shareService.getCategoryByUserId(WebUtils.getUser(session).getId());
			}
		} else {
			indexShare(pageNoStr, session, map);
		}
		getMapWithList(map, times, categories);
		getMapWithShare(shares, session, map);
		map.put("state", "share");
		return "front/user/space/share";
	}
	
	/**
	 * 我的交换-交换TA人
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/exchange/w/{way}/cid/{cId}")
	public String myExchangeByCategoryId(@PathVariable("way") Integer way,  @PathVariable("cId") Integer cId,
			@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) throws ParseException {
		Page<Exchange> exchanges = null;
		List<Date> times = null;
		List<Category> categories = null;
		User user = WebUtils.getUser(session);
		if(way != null && way > 0 && way < 2) {
			if(cId != null && cId > 0) {
				if(way == 1) {
					exchanges = exchangeService.findByTargetCategoryIdAndExchangerId(cId, user.getId(), WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
					times = exchangeService.getApplyTimeByTargetCategoryIdAndExchangerId(user.getId(), cId, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
					categories = exchangeService.getCategoryByExchangerId(WebUtils.getUser(session).getId());
				}
			} else {
				myExchange(way, pageNoStr, session, map);
			}
		} else {
			myExchange(way, pageNoStr, session, map);
		}
		
		getMapWithList(map, times, categories);
		getMapWithExchange(exchanges, session, map);
		map.put("state", "exchange");
		return "front/user/space/exchange";
	}
	
	/**
	 * 我的分享-分享TA人
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/share/w/{way}/cid/{cId}")
	public String myShareByCategoryId(@PathVariable("way") Integer way, @PathVariable("cId") Integer cId,
			@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session, Map<String, Object> map) throws ParseException {
		Page<Share> shares = null;
		List<Date> times = null;
		List<Category> categories = null;
		User user = WebUtils.getUser(session);
		if(way != null && way > 0 && way < 2) {//&& cId != null && cId > 0
			if(cId != null && cId > 0) {
				if(way == 1) {
					shares = shareService.findByTargetCategoryIdAndUserId(cId, user.getId(), WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
					times = shareService.getApplyTimeByUserIdAndCategoryId(user.getId(), cId, WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
					categories = shareService.getCategoryByUserId(WebUtils.getUser(session).getId());
				}
			} else {
				myShare(way, pageNoStr, session, map);
			}
		} else {
			myShare(way, pageNoStr, session, map);
		}
		
		getMapWithList(map, times, categories);
		getMapWithShare(shares, session, map);
		map.put("state", "share");
		return "front/user/space/share";
	}
	
	/**
	 * 我的回收站
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/recycle")
	public String myRecycle(@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session,Map<String, Object> map) throws ParseException {
		Page<Book> books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.RECYCLEBIN, WebUtils.getUser(session));
		List<Date> times = bookService.findCreateTimeByUser_IdAndState(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, WebUtils.getUser(session).getId(), "RECYCLEBIN");
		if(times != null && times.size() > 0) {
			map.put("times", getSortTime(getDistinctTime(times)));
		}
		getMapWithBook(books, session, map);
		map.put("state", "recycle");
		return "front/user/space/recycle";
	}
	
	/**
	 * 获取当前用户的 (发布书籍、分享书籍、交换书籍、待审核书籍),基于书籍类别id
	 * @param status
	 * @param cId
	 * @param pageNoStr
	 * @param session
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/space/book/s/{status}/cid/{cId}")
	public String myBookByCategoryId(@PathVariable("status") Integer status, @PathVariable("cId") Integer cId,
			@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr, HttpSession session,
			Map<String, Object> map) throws ParseException {
		Page<Book> books = null;
		List<Date> times = null;
		List<Category> categories = null;
		if(status != null && status > 0 && status < 4) {//&& cId != null && cId > 0
			if(cId != null && cId > 0) {
				if(status == 1) {
					books = bookService.findByCategory_IdAndUser_IdAndState(cId, WebUtils.getUser(session).getId(), State.EXCHANGE, 
							WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
					times = bookService.findCreateTimeByUser_IdAndStateAndCategory_Id(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT,
							WebUtils.getUser(session).getId(), "EXCHANGE", cId);
					categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.EXCHANGE);
				} else if(status == 2) {
					books = bookService.findByCategory_IdAndUser_IdAndState(cId, WebUtils.getUser(session).getId(), State.SHARE, 
							WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
					times = bookService.findCreateTimeByUser_IdAndStateAndCategory_Id(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT,
							WebUtils.getUser(session).getId(), "SHARE", cId);
					categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.SHARE);
				} else if(status == 3) {
					books = bookService.findByCategory_IdAndUser_IdAndState(cId, WebUtils.getUser(session).getId(), State.RELEASE, 
							WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
					times = bookService.findCreateTimeByUser_IdAndStateAndCategory_Id(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT,
							WebUtils.getUser(session).getId(), "RELEASE", cId);
					categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.RELEASE);
				} else if(status == 4){
					books = bookService.findByCategory_IdAndUser_IdAndState(cId, WebUtils.getUser(session).getId(), State.CONFIRMFAIL, 
							WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
					times = bookService.findCreateTimeByUser_IdAndStateAndCategory_Id(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT,
							WebUtils.getUser(session).getId(), "CONFIRMFAIL", cId);
					categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.CONFIRMFAIL);
				}
			} else {
				myBook(status, pageNoStr, session, map);
			}
		} else {
			if(cId != null && cId > 0) {
				books = bookService.findByCategory_IdAndUser_IdAndState(cId, WebUtils.getUser(session).getId(), State.WAITCONFIRM, 
						WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT);
				times = bookService.findCreateTimeByUser_IdAndStateAndCategory_Id(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT,
						WebUtils.getUser(session).getId(), "WAITCONFIRM", cId);
				categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.WAITCONFIRM);
			}  else {
				myBook(status, pageNoStr, session, map);
			}
		}
		getMapWithList(map, times, categories);
		getMapWithBook(books, session, map);
		return "front/user/space/index";
	}
	
	
	
	/**
	 * 获取当前用户的 (发布书籍、分享书籍、交换书籍、待审核书籍)
	 * @param status
	 * @param pageNoStr
	 * @param session
	 * @param map
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/space/book/s/{status}")
	public String myBook(@PathVariable("status") Integer status,
			@RequestParam(value="pageNo",required=true, defaultValue="1") String pageNoStr,
			HttpSession session,Map<String, Object> map) throws ParseException{
		Page<Book> books = null;
		List<Date> times = null;
		List<Category> categories = null;
		User user = WebUtils.getUser(session);
		if(status != null && status > 0 && status < 4) {
			if(status == 1) {//交换
				books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.EXCHANGE, WebUtils.getUser(session));
				times = bookService.findCreateTimeByUser_IdAndState(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, user.getId(), "EXCHANGE");
				categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.EXCHANGE);
			} else if(status == 2){//分享
				books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.SHARE, WebUtils.getUser(session));
				times = bookService.findCreateTimeByUser_IdAndState(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, user.getId(), "SHARE");
				categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.SHARE);
			} else if(status == 3){//发布
				books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.RELEASE, WebUtils.getUser(session));
				times = bookService.findCreateTimeByUser_IdAndState(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, user.getId(), "RELEASE");
				categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.RELEASE);
			} else if(status == 4){//审核失败
				books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.CONFIRMFAIL, WebUtils.getUser(session));
				times = bookService.findCreateTimeByUser_IdAndState(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, user.getId(), "CONFIRMFAIL");
				categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.CONFIRMFAIL);
			}
			
		} else {//待审核
			books = bookService.getPageByStateAndUserId(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, State.WAITCONFIRM, WebUtils.getUser(session));
			times = bookService.findCreateTimeByUser_IdAndState(WebUtils.getCurrentPage(pageNoStr), Constants.SPACE_PAGE_SIZE_TWELVE_FRONT, user.getId(), "WAITCONFIRM");
			categories = bookService.getCategoryByUserIdAndState(WebUtils.getUser(session).getId(), State.WAITCONFIRM);
		}
		getMapWithList(map, times, categories);
		getMapWithBook(books, session, map);
		return "front/user/space/index";
	}
	
	/**
	 * 时间排序降序
	 * @param times
	 * @return
	 */
	private List<Date> getSortTime(TreeSet<Date> times) {
		System.out.println("getSortTime: "+times);
		List<Date> sortTimes = new ArrayList<Date>();
		for(Date time : times) {
			sortTimes.add(time);
		}
		return sortTimes;
	}
	
	/**
	 * 按 年月日 去除重复时间
	 * @param times
	 * @return
	 * @throws ParseException
	 */
	private TreeSet<Date> getDistinctTime(List<Date> times) throws ParseException {
		System.out.println("getDistinctTime: "+times);
		TreeSet<Date> formatTimes = new TreeSet<Date>(new Comparator<Date>() {//自定义排序:日期降序
			@Override
			public int compare(Date o1, Date o2) {
				return -o1.compareTo(o2);
			}
		});
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(Date time : times) {		
			formatTimes.add(dateFormat.parse(dateFormat.format(time)));
		}
		System.out.println("formatTimes: "+formatTimes);
		return formatTimes;
	}
	/**
	 * map
	 * @param books
	 * @param session
	 * @param map
	 */
	private void getMapWithBook(Page<Book> books, HttpSession session, Map<String, Object> map) {
		if(books != null) {
			map.put("page", books);
		}
		getMap(session, map);
	}
	
	/**
	 * map
	 * @param books
	 * @param session
	 * @param map
	 */
	private void getMapWithExchange(Page<Exchange> books, HttpSession session, Map<String, Object> map) {
		if(books != null) {
			map.put("page", books);
		}
		getMap(session, map);
	}
	
	/**
	 * map
	 * @param books
	 * @param session
	 * @param map
	 */
	private void getMapWithShare(Page<Share> shares, HttpSession session, Map<String, Object> map) {
		if(shares != null) {
			map.put("page", shares);
		}
		getMap(session, map);
	}

	private void getMap(HttpSession session, Map<String, Object> map) {
		map.put("count", bookService.getCountByUserId(WebUtils.getUser(session).getId(), Arrays.asList(State.RECYCLEBIN, State.INVISIBLE)));//获取用户书籍数量(不包含回收站和不可见)
		map.put("shareCount", bookService.getCountByUserIdAndStateIn(WebUtils.getUser(session).getId(), Arrays.asList(State.SHAREING, State.SHARED)) + 
				shareService.getCountByUserId(WebUtils.getUser(session).getId()));
		/*map.put("exchangeCount", bookService.getCountByUserIdAndStateIn(WebUtils.getUser(session).getId(), Arrays.asList(State.EXCHANGEING, State.EXCHANGED)) + 
				exchangeService.getCountByUserId(WebUtils.getUser(session).getId()));*/
		map.put("exchangeCount", bookService.getCountByUserIdAndStateIn(WebUtils.getUser(session).getId(), Arrays.asList(State.EXCHANGEING, State.EXCHANGED)));
		map.put("browseCount", bookUserService.getCountByUserIdAndState(WebUtils.getUser(session).getId(), State.BROWSE));
		map.put("collectionCount", bookUserService.getCountByUserIdAndState(WebUtils.getUser(session).getId(), State.COLLECTION));
		map.put("praiseCount", bookUserService.getCountByUserIdAndState(WebUtils.getUser(session).getId(), State.PRAISE));
		map.put("recycleCount", bookService.getCountByUserIdAndState(WebUtils.getUser(session).getId(), State.RECYCLEBIN));//回收站书籍数量
		map.put("visitors", visitorService.findVisitorByUser_Id(WebUtils.getUser(session).getId()));//获取所有访客信息
		//当前用户收藏的书籍
		List<BookUser> bus = bookUserService.findByUser_IdAndState(WebUtils.getUser(session).getId(), State.COLLECTION);
		map.put("collections", bus);//用户收藏的书籍
		//以字符串形式去连接所有的收藏书籍的id：
		/**
		 * 作用是：
		 * 显示关注
		 */
		if(bus != null && bus.size() > 0) {
			//存在bug，如：如果收藏了 120,119 ，那么collectionstrs="120,119" 
			//如果该用户有 id=1、2、12、20、9、11、19这些书籍，那么这些书籍也会被点亮
			//<i class="btn-add-collection <c:if test='${fn:contains(collectionstrs, book.id) }'>btn-remove-collection </c:if>"></i>
			/*StringBuffer sb = new StringBuffer();
			for(BookUser bu : bus) {
				sb.append(String.valueOf(bu.getBook().getId())).append(",");
			}
			map.put("collectionstrs", sb.deleteCharAt(sb.length()-1));*/
			
			//新的方案：如果该书籍已被收藏，那么collection_book[book.id]则不为空
			//<i class="btn-add-collection <c:if test='${!empty collection_book[book.id]}'>btn-remove-collection </c:if>"></i>
			Map<Integer, String> collection_book = new HashMap<Integer, String>();
			for(BookUser bu : bus) {
				collection_book.put(bu.getBook().getId(), bu.getBook().getName());
			}
			System.out.println(collection_book);
			map.put("collection_book", collection_book);
		}
	}
	
	/**
	 * map
	 * @param books
	 * @param session
	 * @param map
	 */
	private void getMapWithBookUser(Page<BookUser> books, HttpSession session, Map<String, Object> map) {
		if(books != null) {
			map.put("page", books);
		}
		getMap(session, map);
	}
	
	/**
	 * ajax还原书籍
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/space/ajaxBackUp", method=RequestMethod.POST)	
	public AjaxOper ajaxBackUp(@RequestParam(value="bookId", required=true) Integer bookId, HttpSession session) {
		AjaxOper ajaxOper = new AjaxOper(0);
		if(WebUtils.getUser(session) == null) {
			throw new BeaspException();
		}
		if(bookId != null && bookId > 0) {
			bookService.backupInRecycleBin(bookId);//移出回收站
			System.out.println(bookId);
			ajaxOper.setResult(1);
		}
		return ajaxOper;
	}
	
	/**
	 * ajax删除书籍
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/space/ajaxGiveUp", method=RequestMethod.POST)
	public AjaxOper ajaxGiveUp(@RequestParam(value="bookId", required=true) Integer bookId, HttpSession session) {
		AjaxOper ajaxOper = new AjaxOper(0);
		if(WebUtils.getUser(session) == null) {
			throw new BeaspException();
		}
		if(bookId != null && bookId > 0) {
			bookService.deleteInRecycleBin(bookId);//放入回收站,并保存原来的状态
			System.out.println(bookId);
			ajaxOper.setResult(1);
		}
		return ajaxOper;
	}
	
	/**
	 * ajax修改 签名
	 * @param about
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user/singleset", method=RequestMethod.POST)
	public AjaxOper ajaxModifySigned(@RequestParam(value="about",required=true) String about, HttpSession session) {
		User user =  (User) session.getAttribute("user");
		AjaxOper ajaxOper = new AjaxOper(1, "修改失败了");
		if(user == null) {
			throw new BeaspException();
		}
		if(about != null && !"".equals(about)) {
			userService.updateSigned(about, user.getId());
			user.setSummary(userService.getById(user.getId()).getSummary());
			ajaxOper.setStatus(0);
			ajaxOper.setMsg("修改成功了");
		} 
		return ajaxOper;
	}
}
