package org.huel.beasp.handler.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.Exchange;
import org.huel.beasp.entity.book.Share;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.entity.book.Want;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.exception.BeaspFrontException;
import org.huel.beasp.handler.dto.AjaxOper;
import org.huel.beasp.service.book.BookService;
import org.huel.beasp.service.book.ExchangeService;
import org.huel.beasp.service.book.ShareService;
import org.huel.beasp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 核心控制器：
 * 	分享书籍、交换书籍
 * @author 001
 *
 */
@Controller
public class CoreHandler {
	@Autowired private BookService bookService;
	@Autowired private ShareService shareService;
	@Autowired private ExchangeService exchangeService;
	
	/**
	 * 交换界面
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/exchange/{id}", method=RequestMethod.GET)
	public String showExchange(@PathVariable("id") Integer id, 
			HttpServletRequest request) {
		Book book = validateShare(id, request);
		//3. 判断是否是本人的书籍
		if(bookService.getCountByIdAndUserId(id, WebUtils.getUser(request).getId())) {
			request.setAttribute("errorMsg", "你不能交换你自己的书籍！");
			return "front/user/exchange/exchangeresult";
		}
		//4. 判断该书籍是否已被交换（非法操作）
		if(exchangeService.getCountByTarget_IdAndResultIn(id)) {
			request.setAttribute("errorMsg", "该书籍已被交换了！");
			return "front/user/exchange/exchangeresult";
		} 
		request.setAttribute("book", book);
		/**当前用户所有待交换书籍**/
		List<Book> books = bookService.getByUserIdAndState(WebUtils.getUser(request).getId(), State.EXCHANGE);
		List<Book> exchangeBooks = new ArrayList<Book>();
		for(Want want : book.getWants()) {
			for(Book book2 : books) {
				if(book2.getName().equals(want.getName()) &&
						book2.getAuthor().equals(want.getAuthor()) && book2.getVersion().equals(want.getVersion())) {
					System.out.println(book2.getName()+"------"+book2.getAuthor()+"------"+book2.getVersion());
					exchangeBooks.add(book2);
					break;
				}
			}
		}
		request.setAttribute("exchangeBooks", exchangeBooks);
		return "front/user/exchange/show";
	}
	
	/**
	 * 确认交换
	 * @return
	 */
	@RequestMapping(value="/user/exchange/{id}", method=RequestMethod.POST)
	public String confirmExchange(@PathVariable("id") Integer id, 
			@RequestParam("choice") Integer choiceId,
			HttpServletRequest request) {
		Book book = validateShare(id, request);
		//3. 判断是否是本人的书籍
		if(bookService.getCountByIdAndUserId(id, WebUtils.getUser(request).getId())) {
			request.setAttribute("errorMsg", "你不能交换你自己的书籍！");
			return "front/user/exchange/exchangeresult";
		}
		//4. 判断该书籍是否已被交换（非法操作）
		if(exchangeService.getCountByTarget_IdAndResultIn(id)) {
			request.setAttribute("errorMsg", "该书籍已被交换了！");
			return "front/user/exchange/exchangeresult";
		} 
		//5. 判断是否选中交换的书籍
		if(choiceId != null && choiceId > 0) {
			if(!bookService.getCountByIdAndUserId(choiceId, WebUtils.getUser(request).getId())) {
				request.setAttribute("errorMsg", "你选中交换的书籍不存在！");
				return "front/user/exchange/exchangeresult";
			}
		}
		
		/**
		 * 1. 修改书籍状态为交换中
		 * 2. 保存到交换表中
		 */
		book.setState(State.EXCHANGEING);
		bookService.save(book);
		Book exchangeBook = bookService.getOneById(choiceId);
		exchangeBook.setState(State.EXCHANGEING);
		bookService.save(exchangeBook);
		
		/**
		 * 
		 */
		Exchange exchange  = new Exchange(exchangeBook, book, WebUtils.getUser(request), book.getUser());
		exchange = exchangeService.save(exchange);
		
		request.setAttribute("exchange", exchange);
		return "front/user/exchange/exchangeresult";
	}
	
	/**
	 * 分享界面
	 * @param id 要分享的书籍id
	 * @return
	 */
	@RequestMapping(value="/user/share/{id}", method=RequestMethod.GET)
	public String showShare(@PathVariable("id") Integer id,
			HttpServletRequest request) {
		Book book = validateShare(id, request);
		
		//3. 判断是否是本人的书籍
		if(bookService.getCountByIdAndUserId(id, WebUtils.getUser(request).getId())) 
			//throw new BeaspFrontException("你不能分享你自己的书籍！");
		{
			request.setAttribute("errorMsg", "你不能分享你自己的书籍！");
			return "front/user/share/shareresult";
		}
		//4. 判断该书籍是否已被分享（非法操作）
		if(shareService.getCountByTarget_IdAndResultIn(id)) 
			//throw new BeaspFrontException("该书籍已被分享了！");
		{
			request.setAttribute("errorMsg", "该书籍已被分享了！");
			return "front/user/share/shareresult";
		} 
		
		request.setAttribute("book", book);
		return "front/user/share/show";
		
	}

	private Book validateShare(Integer id, HttpServletRequest request) {
		Book book = null;
		if(id != null && id > 0) {
			User user = WebUtils.getUser(request);
			//1. 判断是否存在登录用户（再次判断）（非法操作）
			if(user == null) throw new BeaspFrontException("请先登录后再进行分享！");
			
			//2. 判断是否存在该书籍（非法操作）
			book = bookService.getOneById(id);
			if(book == null) throw new BeaspFrontException("不存在你想要分享的书籍！");
			
		} else 
			throw new BeaspFrontException();
		return book;
	}
	
	/**
	 * 确认分享
	 * @return
	 */
	@RequestMapping(value="/user/share/{id}", method=RequestMethod.POST)
	public String confirmShare(@PathVariable("id") Integer id,
			HttpServletRequest request) {
		Book book = validateShare(id, request);
		//3. 判断是否是本人的书籍
		if(bookService.getCountByIdAndUserId(id, WebUtils.getUser(request).getId())) 
			//throw new BeaspFrontException("你不能分享你自己的书籍！");
		{
			request.setAttribute("errorMsg", "你不能分享你自己的书籍！");
			return "front/user/share/shareresult";
		}
		//4. 判断该书籍是否已被分享（非法操作）
		if(shareService.getCountByTarget_IdAndResultIn(id)) 
			//throw new BeaspFrontException("该书籍已被分享了！");
		{
			request.setAttribute("errorMsg", "该书籍已被分享了！");
			return "front/user/share/shareresult";
		} 
		/**
		 * 1. 修改书籍状态为分享中
		 * 2. 保存到分享表中
		 */
		//修改状态
		book.setState(State.SHAREING);
		bookService.save(book);
		
		//保存分享
		Share share = new Share(book, WebUtils.getUser(request));
		share = shareService.save(share);
		
		request.setAttribute("share", share);
		return "front/user/share/shareresult";
	}
	

	@ResponseBody
	@RequestMapping(value="/space/ajaxagreeexchange", method=RequestMethod.POST)
	public AjaxOper ajaxagreeexchange(@RequestParam("exchange") Integer id,
			HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "同意交换成功");
		if(id != null && id > 0) {
			Exchange exchange = exchangeService.getById(id);
			if(exchange != null) {
				/**
				 * 1. 修改书籍状态为已交换（直到归还，才修改为交换）
				 * 2. 修改交换表中状态为已交换，result为1，confirmTime为当前时间
				 */
				Book replaceBook = exchange.getReplace();
				replaceBook.setState(State.EXCHANGED);
				bookService.save(replaceBook);
				
				Book targetBook = exchange.getTarget();
				targetBook.setState(State.EXCHANGED);
				bookService.save(targetBook);
				
				exchange.setState(State.EXCHANGED);
				exchange.setResult(1);
				exchange.setConfirmTime(new Date());
				exchangeService.save(exchange);
			} else 
				ajaxOper = new AjaxOper(1, "同意交换失败");
			
		} else 
			ajaxOper = new AjaxOper(1, "同意交换失败");
	
		return ajaxOper;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/space/ajaxagreeshare", method=RequestMethod.POST)
	public AjaxOper ajaxagreeshare(@RequestParam("book") Integer id,
			HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "同意分享成功");
		Book book = validateShare(id, request);
		
		/**
		 * 1. 修改书籍状态为已分享（直到归还，才修改为分享）
		 * 2. 修改分享表中中状态为已分享，result为1，confirmTime为当前时间
		 */
		//修改状态
		book.setState(State.SHARED);
		bookService.save(book);
		
		//保存分享
		Share share = shareService.getByTargetId(book.getId());
		share.setConfirmTime(new Date());
		share.setState(State.SHARED);
		share.setResult(1);
		share = shareService.save(share);
		
		return ajaxOper;
	}
	
	/**
	 * 同意分享
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/share/{id}/aggre")
	public String aggreShare(@PathVariable("id") Integer id,
		HttpServletRequest request) {
		Book book = validateShare(id, request);
		
		/**
		 * 1. 修改书籍状态为已分享（直到归还，才修改为分享）
		 * 2. 修改分享表中中状态为已分享，result为1，confirmTime为当前时间
		 */
		//修改状态
		book.setState(State.SHARED);
		bookService.save(book);
		
		//保存分享
		Share share = shareService.getByTargetId(book.getId());
		share.setConfirmTime(new Date());
		share.setState(State.SHARED);
		share.setResult(1);
		share = shareService.save(share);
		return "front/user/share/aggreshare";
	}
	
	@ResponseBody
	@RequestMapping(value="/space/ajaxdisagreeexchange", method=RequestMethod.POST)
	public AjaxOper ajaxdisagreeexchange(@RequestParam("exchange") Integer id,
			HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "不同意交换成功");
		if(id != null && id > 0) {
			Exchange exchange = exchangeService.getById(id);
			if(exchange != null) {
				/**
				 * 1. 修改书籍状态为交换
				 * 2. 修改交换表中状态为交换失败，result为2，confirmTime为当前时间
				 */
				Book replaceBook = exchange.getReplace();
				replaceBook.setState(State.EXCHANGE);
				bookService.save(replaceBook);
				
				Book targetBook = exchange.getTarget();
				targetBook.setState(State.EXCHANGE);
				bookService.save(targetBook);
				
				exchange.setState(State.EXCHANGEFAIL);
				exchange.setResult(2);
				exchange.setConfirmTime(new Date());
				exchangeService.save(exchange);
			} else 
				ajaxOper = new AjaxOper(1, "不同意交换失败");
			
		} else 
			ajaxOper = new AjaxOper(1, "不同意交换失败");
		
		return ajaxOper;
	}
	
	@ResponseBody
	@RequestMapping(value="/space/ajaxdisagreeshare", method=RequestMethod.POST)
	public AjaxOper ajaxdisagreeshare(@RequestParam("book") Integer id,
			HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "同意分享成功");
		Book book = validateShare(id, request);
		
		/**
		 * 1. 修改书籍状态为分享（仍然可以被他人分享）
		 * 2. 修改分享表中中状态为分享失败，result为2，confirmTime为当前时间
		 */
		//修改状态
		book.setState(State.SHARE);
		bookService.save(book);
		
		//保存分享
		Share share = shareService.getByTargetId(book.getId());
		share.setConfirmTime(new Date());
		share.setState(State.SHAREFAIL);
		share.setResult(2);
		share = shareService.save(share);
		
		return ajaxOper;
	}
	
	/**
	 * 不同意分享
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/share/{id}/disaggre")
	public String disaggreShare(@PathVariable("id") Integer id,
		HttpServletRequest request) {
		Book book = validateShare(id, request);
		
		/**
		 * 1. 修改书籍状态为分享（仍然可以被他人分享）
		 * 2. 修改分享表中中状态为分享失败，result为2，confirmTime为当前时间
		 */
		//修改状态
		book.setState(State.SHARE);
		bookService.save(book);
		
		//保存分享
		Share share = shareService.getByTargetId(book.getId());
		share.setConfirmTime(new Date());
		share.setState(State.SHAREFAIL);
		share.setResult(2);
		share = shareService.save(share);
		return "front/user/share/aggreshare";
	}
}
