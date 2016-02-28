package org.huel.beasp.handler.common;

import java.util.Map;

import org.huel.beasp.entity.book.State;
import org.huel.beasp.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonHandler {
	@Autowired private BookService bookService;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/")
	public String frontIndex(Map<String, Object> map) {
		map.put("hotBooks", bookService.findTop8ByOrderByCollectionDescIdDesc());
		map.put("newBooks", bookService.findTop8ByOrderByCreateTimeDesc());
		map.put("shareBooks", bookService.findTop8ByStateOrderByCreateTimeDesc(State.SHARE));
		map.put("exBooks", bookService.findTop8ByStateOrderByCreateTimeDesc(State.EXCHANGE));
		return "front/index";
	}
	
	/**
	 * 后台首页
	 * @param map
	 * @return
	 */
	@RequestMapping("/admin")
	public String adminIndex(Map<String, Object> map) {
		return "admin/index";
	}
}
