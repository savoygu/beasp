package org.huel.beasp.web.handler.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.Style;
import org.huel.beasp.service.book.BookService;
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
 * 书籍样式控制层
 * @author 001
 *
 */
@RequestMapping("/admin/book")
@Controller
public class StyleHandler {
	@Autowired private StyleService styleService;
	@Autowired private BookService bookService;
	
	/**
	 * 选中当前
	 * @param param
	 * @param pageNoStr
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/style/choice", method=RequestMethod.POST)
	public String choiceCurrent(@RequestParam(value="param", required=true) String param,
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			 Map<String, Object> map) {
		Integer bookId = null;
		if(param!=null && !"".equals(param)) {
			bookId = styleService.choiceCurrent(Integer.parseInt(param));
		}
		//回到从前页
		int pageNo = WebUtils.getCurrentPage(pageNoStr);
		Page<Style> styles = null;
		if(bookId != null && bookId > 0) {
			styles = styleService.getPageByBookId(pageNo, Constants.PAGE_SIZE_ADMIN, bookId);
		} else {
			styles = styleService.getPage(pageNo, Constants.PAGE_SIZE_ADMIN);
		}
		//如果当前页没有记录,就需要 页数-1
		if(styles == null || styles.getNumberOfElements() == 0) {
			pageNo = pageNo -1;
			if(pageNo < 1) {
				pageNo = 1;
			}
		}
		map.put("pageNo", pageNo);
		if(bookId != null && bookId > 0) {
			return "redirect:/admin/book/styles?bookId="+bookId;
		}
		return "redirect:/admin/book/styles";
	}
	
	/**
	 * 批量删除
	 * @param param
	 * @param pageNoStr
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/style/ids", method=RequestMethod.POST)
	public String batchDelete(@RequestParam(value="parameter", required=true) String param,
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			@RequestParam(value="isAdd", required=false) boolean isAdd, Map<String, Object> map) {
		if(param!=null && !"".equals(param)) {
			String[] params = param.split("-");
			List<Integer> ids = new ArrayList<Integer>();
			for(String str : params) {
				ids.add(Integer.parseInt(str));
			}
			if(isAdd) {
				styleService.batchDelete(true, ids);
			} else {
				styleService.batchDelete(false, ids);
			}
		}
		//回到从前页
		int pageNo = WebUtils.getCurrentPage(pageNoStr);
		Page<Style> styles = styleService.getPage(pageNo, Constants.PAGE_SIZE_ADMIN);
		//如果当前页没有记录,就需要 页数-1
		if(styles == null || styles.getNumberOfElements() == 0) {
			pageNo = pageNo - 1;
			if(pageNo < 1) {
				pageNo = 1;
			}
		}
		map.put("pageNo", pageNo);
		return "redirect:/admin/book/styles";
	}
	
	/**
	 * 样式删除
	 * @param id
	 * @param pageNoStr
	 * @param bookId
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/style/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id, 
			@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			@RequestParam(value="bookId", required=false) Integer bookId,
			Map<String, Object> map) {
		styleService.delete(false, id);
		//回到从前页
		int pageNo = WebUtils.getCurrentPage(pageNoStr);
		Page<Style> styles = null;
		if(bookId != null && bookId > 0) {
			styles = styleService.getPageByBookId(pageNo, Constants.PAGE_SIZE_ADMIN, bookId);
		} else {
			styles = styleService.getPage(pageNo, Constants.PAGE_SIZE_ADMIN);
		}
		//如果当前页没有记录,就需要 页数-1
		if(styles == null || styles.getNumberOfElements() == 0) {
			pageNo = pageNo -1;
			if(pageNo < 1) {
				pageNo = 1;
			}
		}
		map.put("pageNo", pageNo);
		if(bookId != null && bookId > 0) {
			return "redirect:/admin/book/styles?bookId="+bookId;
		}
		return "redirect:/admin/book/styles";
	}
	
	/**
	 * 在执行目标方法之前执行，作用：避免在做修改时，修改未被提交的属性
	 * @param id
	 * @param map
	 */
	@ModelAttribute
	public void getStyle(@RequestParam(value="id", required=false) Integer id,
			Map<String, Object> map) {
		if(id != null) {
			Style style = styleService.getById(id);
			map.put("style", style);
		}
	}
	
	/**
	 * 样式修改
	 * @param style
	 * @param image
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/style/{id}", method=RequestMethod.POST)
	public String update(Style style, @RequestParam("bookId") Integer bookId,
			MultipartFile image, Map<String, Object> map, HttpServletRequest request) {
		if(image != null) {
			if(!FileUtils.validateImageFileType(image, image.getContentType(), image.getOriginalFilename())) {
				map.put("imageErrorMsg", "文件格式不正确,只允许上传gif/jpg/png/bmp图片");
				return "admin/book/book/input";
			}
			//1.获取category 构建图片路径，2.为图片指定一个书籍
			String ext = FileUtils.getExt(image.getOriginalFilename());
			String fileName = UUID.randomUUID().toString()+"."+ext;//构建文件名称
			
			/**
			 * 保存样式
			 */
			Book book = bookService.getOneById(bookId);
			style.setImageName(fileName);
			styleService.update(style);
			
			try {
				FileUtils.saveImageFile(image, book.getCategory().getId(), book.getId(), fileName, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/admin/book/styles?bookId="+bookId;
	}
	
	/**
	 * 样式修改界面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/style/{id}", method=RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		Style style = styleService.getById(id);
		map.put("style", style);
		map.put("bookId", style.getBook().getId());
		return "admin/book/style/input";
	}
	
	/**
	 * 保存样式
	 * @param style
	 * @param bookId
	 * @param image
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/style", method=RequestMethod.POST)
	public String save(Style style, @RequestParam("bookId") Integer bookId, MultipartFile image,
			Map<String, Object> map, HttpServletRequest request) {
		if(image != null) {
			if(!FileUtils.validateImageFileType(image, image.getContentType(), image.getOriginalFilename())) {
				map.put("imageErrorMsg", "文件格式不正确,只允许上传gif/jpg/png/bmp图片");
				return "admin/book/book/input";
			}
			//1.获取category 构建图片路径，2.为图片指定一个书籍
			String ext = FileUtils.getExt(image.getOriginalFilename());
			String fileName = UUID.randomUUID().toString()+"."+ext;//构建文件名称
			
			/**
			 * 保存样式
			 */
			Book book = bookService.getOneById(bookId);
			style.setImageName(fileName);
			style.setBook(book);
			styleService.save(style);
			
			try {
				FileUtils.saveImageFile(image, book.getCategory().getId(), book.getId(), fileName, request);
			} catch (Exception e) {}
		}
		return "redirect:/admin/book/styles?bookId="+bookId;
	}
	
	/**
	 * 书籍样式添加界面
	 * @param map
	 * @param bookId
	 * @return
	 */
	@RequestMapping(value="/style", method=RequestMethod.GET)
	public String input(Map<String, Object> map, @RequestParam("bookId") Integer bookId) {
		map.put("style", new Style());
		if(bookId != null && bookId > 0) {
			map.put("bookId", bookId);
		}
		return "admin/book/style/input";
	}
	
	/**
	 * 书籍样式列表
	 * @param bookId
	 * @param pageNoStr
	 * @param map
	 * @return
	 */
	@RequestMapping("styles")
	public String list(@RequestParam(value="bookId", required=false) Integer bookId,
			@RequestParam(value="pageNo", required=false, defaultValue="1") String pageNoStr,
			Map<String, Object> map) {
		Page<Style> styles = null;
		if(bookId != null && bookId > 0) {
			styles = styleService.getPageByBookId(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN, bookId);
		} else {
			styles = styleService.getPage(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN);
		}
		map.put("page", styles);
		return "admin/book/style/list";
	}
}
