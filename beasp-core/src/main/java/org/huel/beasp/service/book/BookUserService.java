package org.huel.beasp.service.book;

import java.util.List;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.BookUser;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.repository.book.BookRepository;
import org.huel.beasp.repository.book.BookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 用户书籍业务逻辑层
 * @author 001
 *
 */
@Service("bookUserService")
public class BookUserService {
	@Autowired private BookUserRepository bookUserRepository;
	@Autowired private BookRepository bookRepository;
	
	/**
	 * 获取最近一次浏览的书籍
	 * @param userId
	 * @param state
	 * @return
	 */
	@Transactional(readOnly=true)
	public BookUser getTop1ByUserIdAndStateOrderByCreateTimeDesc(Integer userId, State state) {
		return bookUserRepository.getTop1ByUser_IdAndStateOrderByCreateTimeDesc(userId, state);
	}
	
	/**
	 * 按用户id 和状态 获取所有书籍
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<BookUser> findByUser_IdAndState(Integer userId, State state) {
		return bookUserRepository.findByUser_IdAndState(userId, state);
	}
	
	/**
	 * 按用户id 和状态 获取所有书籍
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Book> findBookIdByUser_IdAndState(Integer userId, String state, int pageNo, int pageSize) {
		List<Integer> ids = bookUserRepository.findBookIdByUser_IdAndState(userId, state, (pageNo-1)*pageSize, pageSize);
		System.out.println(ids);
		Page<Book> books = bookRepository.findByIdIn(ids, new PageRequest(0, pageSize));
		return books;
	}
	
	/**
	 * 获取BookUser
	 * @param userId
	 * @param state
	 * @param pageNo
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<BookUser> findByUser_IdAndState(Integer userId, State state, int pageNo, int pageSize) {
		return bookUserRepository.findByUser_IdAndState(userId, state, new PageRequest(pageNo-1, pageSize, new Sort(Direction.DESC, "createTime", "id")));
	}
	
	/**
	 * 按用户id 和状态 获取数量
	 * @param id
	 * @param state
	 * @return
	 */
	@Transactional(readOnly=true)
	public long getCountByUserIdAndState(Integer id, State state) {
		return bookUserRepository.getCountByUserIdAndState(id, state);
	}
	
	/**
	 * 查找
	 * @param bookId 书籍id
	 * @param userId 用户id
	 * @param state 书籍所属用户状态
	 * @return
	 */
	@Transactional(readOnly=true)
	public BookUser findByBook_IdAndUser_IdAndState(Integer bookId, Integer userId, State state) {
		return bookUserRepository.findByBook_IdAndUser_IdAndState(bookId, userId, state);
	}
	
	/**
	 * 删除
	 */
	@Transactional
	public void delete(BookUser bu) {
		bookUserRepository.delete(bu);
	}
	
	/**
	 * 保存
	 * @param bookUser
	 */
	@Transactional
	public BookUser save(BookUser bookUser) {
		return bookUserRepository.saveAndFlush(bookUser);
	}
}
