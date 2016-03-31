package org.huel.beasp.web.handler.book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.huel.beasp.entity.book.ApplyBook;
import org.huel.beasp.service.book.ApplyBookService;
import org.huel.beasp.utils.Constants;
import org.huel.beasp.utils.WebUtils;
import org.huel.beasp.web.handler.dto.AjaxOper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 求书籍控制器
 * @author 001
 *
 */
@Controller
public class ApplyBookHandler {
	
	@Autowired private ApplyBookService applyBookService;
	
	/**
	 * 求书籍列表
	 * @return
	 */
	@RequestMapping("/topic")
	public String list(@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			@RequestParam(value="status", required=false, defaultValue="0") int status, 
			@RequestParam(value="soe", required=false) String soe, 
			@RequestParam(value="order", required=false, defaultValue="time_desc") String order,
			Map<String, Object> map) {
		Page<ApplyBook> page = 
				applyBookService.findByStateInAndShareExchange(order, soe, status, 
						WebUtils.getCurrentPage(pageNoStr), Constants.TOPIC_BOOK_PAGE_SIZE_TWELVE_FRONT);
//				applyBookService.findAll(WebUtils.getCurrentPage(pageNoStr), Constants.TOPIC_BOOK_PAGE_SIZE_TWELVE_FRONT);
		map.put("page", page);
		getMap(page, map);
		return "front/apply/list";
	}
	
	
	@RequestMapping("/topic/my")
	public String listMyself(@RequestParam(value="pageNo", required=true, defaultValue="1") String pageNoStr,
			@RequestParam(value="status", required=false, defaultValue="0") int status, 
			@RequestParam(value="soe", required=false) String soe, 
			@RequestParam(value="order", required=false, defaultValue="time_desc") String order,
			Map<String, Object> map, HttpServletRequest request) {
		Page<ApplyBook> page = 
				applyBookService.findByRequirerIdAndStateInAndShareExchange(WebUtils.getUser(request), status, soe, order, 
						WebUtils.getCurrentPage(pageNoStr), Constants.TOPIC_BOOK_PAGE_SIZE_TWELVE_FRONT);
//				applyBookService.findAll(WebUtils.getCurrentPage(pageNoStr), Constants.TOPIC_BOOK_PAGE_SIZE_TWELVE_FRONT);
		map.put("page", page);
		map.put("my", "my");
		getMap(page, map);
		return "front/apply/list";
	}
	
	public void getMap(Page<ApplyBook> applyBooks,
			Map<String, Object> map) {
		//评论时间个性化
		Map<Integer, String> create_time = new HashMap<Integer, String>();
		for(ApplyBook applyBook : applyBooks.getContent()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date nowdate = new Date();//当前时间
			long time_sub = nowdate.getTime()-applyBook.getCreateTime().getTime();//时间差
			System.out.println(time_sub);
			if(time_sub < 1000*60) {//1分钟之内
				create_time.put(applyBook.getId(), time_sub/1000+"秒前");
			} else if(time_sub < 1000*60*60){//1小时之内
				create_time.put(applyBook.getId(), time_sub/(1000*60)+"分钟前");
			} else if(time_sub < 1000*60*60*24) {//1天之内
				create_time.put(applyBook.getId(), time_sub/(1000*60*60)+"小时前");
			} else if(time_sub < 1000*60*60*24*7) {//7天之内
				create_time.put(applyBook.getId(), time_sub/(1000*60*60*24)+"天前");
			} else {
				create_time.put(applyBook.getId(), sdf.format(applyBook.getCreateTime()));
			}
		}
		map.put("create_time", create_time);
		map.put("requirer_count", applyBookService.getDictinctByRequirerId());
	}
	
	@ResponseBody
	@RequestMapping(value="/topic/create", method=RequestMethod.POST)
	public AjaxOper create(@RequestParam("name") String name, @RequestParam("author") String author,
			@RequestParam("version") String version, @RequestParam("description") String description,
			@RequestParam("shareExchange") String shareExchange, HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "提交书籍信息成功");
		if(isNotNull(name) && isNotNull(author) && isNotNull(version) 
				&& isNotNull(description) && isNotNull(shareExchange)) {
			ApplyBook applyBook = new ApplyBook(name, version, author, description, Integer.parseInt(shareExchange));
			applyBook.setRequirer(WebUtils.getUser(request));
			applyBookService.saveOrUpdate(applyBook);
		} else {
			ajaxOper = new AjaxOper(1, "提交书籍信息失败");
		}
		return ajaxOper;
	}
	
	private boolean isNotNull(String str) {
		return str == null || "".equals(str) ? false : true;
	}
	
}
