package org.huel.beasp.web.handler.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.huel.beasp.entity.book.ApplyBook;
import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.Exchange;
import org.huel.beasp.entity.book.Share;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.entity.book.Style;
import org.huel.beasp.entity.common.PageIndex;
import org.huel.beasp.service.book.ApplyBookService;
import org.huel.beasp.service.book.BookService;
import org.huel.beasp.service.book.CategoryService;
import org.huel.beasp.service.book.ExchangeService;
import org.huel.beasp.service.book.ShareService;
import org.huel.beasp.service.book.StyleService;
import org.huel.beasp.utils.Constants;
import org.huel.beasp.utils.FileUtils;
import org.huel.beasp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/**
 * 书籍控制层
 * @author 001
 *
 */
@Controller
@RequestMapping("/admin/book")
public class BookHandler {
	@Autowired private BookService bookService;
	@Autowired private CategoryService categoryService;
	@Autowired private StyleService styleService;
	@Autowired private ShareService shareService;
	@Autowired private ExchangeService exchangeService;
	@Autowired private ApplyBookService applyBookService;
	
	/**
	 * 求书籍列表
	 * @param pageNoStr
	 * @param map
	 * @return
	 */
	@RequestMapping("/books/apply")
	public String applyBookList(@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			Map<String, Object> map) {
		Page<ApplyBook> applyBooks =  applyBookService.findAll(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN);
		map.put("page", applyBooks);
		return "admin/book/apply/list";
	}
	
	/**
	 * 书籍分享列表
	 * @return
	 */
	@RequestMapping("/books/share")
	public String shareList(@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			Map<String, Object> map) {
		Page<Share> shares = shareService.findAll(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN);
		map.put("page", shares);
		return "admin/book/share/list";
	}
	
	/**
	 * 书籍交换列表
	 * @return
	 */
	@RequestMapping("/books/exchange")
	public String exchangeList(@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			Map<String, Object> map) {
		Page<Exchange> exchanges = exchangeService.findAll(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN);
		map.put("page", exchanges);
		return "admin/book/exchange/list";
	}
	
	/**
	 * 求书籍审核失败
	 * @return
	 */
	@RequestMapping("/book/fail/ids")
	public String batchFailBook(@RequestParam(value="parameter", required=true) String param,
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			Map<String, Object> map) {
		if(param != null) {
			String [] params = param.split("-");
			List<Integer> ids  = new ArrayList<Integer>();
			for(String str : params) {
				ids.add(Integer.parseInt(str));
			}
			applyBookService.batchFailBook(ids);
		}
		backToBefore(pageNoStr, map);//回到从前页
		return "redirect:/admin/book/books/apply";
	}
	
	/**
	 * 求书籍审核通过
	 * @return
	 */
	@RequestMapping("/book/pass/ids")
	public String batchPassBook(@RequestParam(value="parameter", required=true) String param,
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			Map<String, Object> map) {
		if(param != null) {
			String [] params = param.split("-");
			List<Integer> ids  = new ArrayList<Integer>();
			for(String str : params) {
				ids.add(Integer.parseInt(str));
			}
			applyBookService.batchPassBook(ids);
		}
		backToBefore(pageNoStr, map);//回到从前页
		return "redirect:/admin/book/books/apply";
	}
	
	/**
	 * 批量审核书籍
	 * @param param
	 * @param pageNoStr
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/batchConfirmBook", method=RequestMethod.POST)
	public String batchConfirmBook(@RequestParam(value="parameter", required=true) String param,
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			Map<String, Object> map) {
		if(param != null) {
			String[] params = param.split("-");
			List<Integer> ids = new ArrayList<Integer>();
			for(String str : params) {
				ids.add(Integer.parseInt(str));
			}
			bookService.batchCofirmBook(ids);
		}
		backToBefore(pageNoStr, map);//回到从前页
		return "redirect:/admin/book/books?state="+State.WAITCONFIRM;
	}
	
	/**
	 * 审核失败
	 * @param bookId
	 * @param state
	 * @param share
	 * @param exchagne
	 * @return
	 */
	@RequestMapping(value="/noConfirmBook")
	public String noConfirmBook(@RequestParam("bookId") Integer bookId, @RequestParam("state") State state,
			@RequestParam("share") boolean share, @RequestParam("exchange") boolean exchagne) {
		if(state.equals(State.WAITCONFIRM) && bookId > 0) {
			bookService.confirmBook(bookId, State.CONFIRMFAIL);//修改状态为, 审核失败
		}
		return "redirect:/admin/book/books?state="+state;
	}
	
	/**
	 * 审核通过
	 * @param bookId
	 * @param state
	 * @param share
	 * @param exchagne
	 * @return
	 */
	@RequestMapping(value="/confirmBook")
	public String confirmBook(@RequestParam("bookId") Integer bookId, @RequestParam("state") State state,
			@RequestParam("share") boolean share, @RequestParam("exchange") boolean exchagne) {
		if(state.equals(State.WAITCONFIRM) && bookId > 0) {
			if(share) {
				bookService.confirmBook(bookId, State.SHARE);//是否分享, 如果是修改书籍状态为分享状态
			} else if(exchagne) {
				bookService.confirmBook(bookId, State.EXCHANGE);//是否交换, 如果是修改书籍状态为交换状态
			} else {
				bookService.confirmBook(bookId, State.RELEASE);//否则为发布状态
			}
		}
		return "redirect:/admin/book/books?state="+state;
	}
	
	/**
	 * 按书籍 id 获取书籍详细信息
	 * @param bookId
	 * @return
	 */
	@RequestMapping(value="/bookinfo", method=RequestMethod.GET)
	public String getBookInfo(@RequestParam("bookId") Integer bookId, Map<String, Object> map) {
		if(bookId != null && bookId > 0) {
			map.put("book", bookService.getOneById(bookId));
		}
		return "admin/book/book/view";
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	@RequestMapping(value="/book/ids", method=RequestMethod.POST)
	public String batchDelete(@RequestParam(value="parameter", required=true) String param,
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			@RequestParam(value="isRecyclebin", required=false) boolean isRecyclebin,
			Map<String, Object> map) {
		if(param != null) {
			String[] params = param.split("-");
			List<Integer> ids = new ArrayList<Integer>();
			for(String str : params) {
				ids.add(Integer.parseInt(str));
			}
			if(isRecyclebin) {//如果来自回收站
				bookService.batchDelete(true, ids);
			} else {
				bookService.batchDelete(false, ids);
			}
		}
		backToBefore(pageNoStr, map);
		return "redirect:/admin/book/books";
	}
	
	/**
	 * 回到从前页
	 */
	private void backToBefore(String pageNoStr, Map<String, Object> map) {
		//回到从前页
		int pageNo = WebUtils.getCurrentPage(pageNoStr);
		Page<Book> books = bookService.getPage(pageNo, Constants.PAGE_SIZE_ADMIN);
		//如果当前页没有记录,就需要 页数-1
		if(books == null || books.getNumberOfElements() == 0) {
			pageNo = pageNo -1;
			if(pageNo < 1) {
				pageNo = 1;
			}
		}
		map.put("pageNo", pageNo);
	}
	
	/**
	 * 删除书籍(非真正意义删除)
	 * @param id
	 * @param pageNoStr
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/book/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			Map<String, Object> map) {
		bookService.delete(id);
		backToBefore(pageNoStr, map);
		return "redirect:/admin/book/books";
	}
	
	@ModelAttribute
	public void getBook(@RequestParam(value="id", required=false) Integer id,
			Map<String, Object> map) {
		if(id != null && id > 0) {
			Book book = bookService.getOneById(id);
			book.setCategory(null);//解决修改类别id
			map.put("book", book);
		}
	}
	
	/**
	 * 书籍修改
	 * @param book
	 * @return
	 */
	@RequestMapping(value="/book/{id}", method=RequestMethod.PUT)
	public String update(Book book, @RequestParam("pageNo") String pageNoStr) {
		bookService.save(book);//修改书籍
		return "redirect:/admin/book/books?pageNo="+WebUtils.getCurrentPage(pageNoStr);
	}
	
	/**
	 * 书籍修改界面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/book/{id}", method=RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, @RequestParam("pageNo") String pageNoStr, 
			Map<String, Object> map) {
		map.put("book", bookService.getOneById(id));
		map.put("pageNo", WebUtils.getCurrentPage(pageNoStr));
		return "admin/book/book/edit";
	}
	
	/**
	 * 书籍添加
	 * @param book
	 * @param image
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/book", method=RequestMethod.POST)
	public String save(Book book, @RequestParam("image") MultipartFile image,
			HttpServletRequest request, Map<String, Object> map) {
		if(image != null) {
			if(!FileUtils.validateImageFileType(image, image.getContentType(), image.getOriginalFilename())) {
				System.out.println(image.getOriginalFilename()+"--"+image.getName()+"--"+image.getContentType()+"--"+image.getSize());
				map.put("imageErrorMsg", "文件格式不正确,只允许上传gif/jpg/png/bmp图片");
				return "admin/book/book/input";
			}
			//1.获取category 构建图片路径，2.为图片指定一个书籍
			String ext = FileUtils.getExt(image.getOriginalFilename());
			String fileName = UUID.randomUUID().toString()+"."+ext;//构建文件名称
			
			/**
			 * 保存样式
			 */
			/*Style style = new Style(fileName);
			style.setBook(book);
			bookService.save(book);
			styleService.save(style);*/
			/**
			 * 保存样式（级联）
			 */
			book.addBookStyle(new Style(fileName));
			bookService.save(book);
			
			try {
				FileUtils.saveImageFile(image, book.getCategory().getId(), book.getId(), fileName, request);
			} catch (Exception e) {}
		}
		return "redirect:/admin/book/books?state="+State.WAITCONFIRM;
	}
	
	/**
	 * 书籍添加界面
	 */
	@RequestMapping(value="/book", method=RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("book", new Book());
		return "admin/book/book/input";
	}
	
	/**
	 * 书籍查询界面
	 * @return
	 */
	@RequestMapping(value="/book/find", method=RequestMethod.GET)
	public String find() {
		return "admin/book/book/find";
	}
	
	/**
	 * 书籍列表
	 * @param pageNoStr
	 * @param map
	 * @param state
	 * @return
	 */
	@RequestMapping("/books")
	public String list(@RequestParam(name="pageNo", required=false, defaultValue="1") String pageNoStr,
			Map<String, Object> map,
			@RequestParam(value="state", required=false) State state,
			@RequestParam(value="param", required=false) String param,
			@RequestParam(value="query", required=false) String query) {
		Page<Book> books = null;
		if(query!=null && "true".equals(query)) {
			books = bookService.getPageByParams(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN, param);
			map.put("query", "true");
			map.put("params", param);
		} else {
			if(state != null) {
				books = bookService.getPageByState(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN, state);
			} else {
				books = bookService.getPage(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN);
			}
		}
		map.put("page", books);
		map.put("pageIndex", PageIndex.getPageIndex(Constants.VIEW_PAGE_COUNT, 
				Integer.parseInt(pageNoStr), 
				Long.parseLong(String.valueOf(books.getTotalPages()))));
		return "admin/book/book/list";
	}
}
