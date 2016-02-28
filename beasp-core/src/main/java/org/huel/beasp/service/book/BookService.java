package org.huel.beasp.service.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.Category;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.entity.common.QueryResult;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.exception.BeaspException;
import org.huel.beasp.exception.BeaspFrontException;
import org.huel.beasp.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 书籍业务逻辑层
 * @author 001
 *
 */
@Service("bookService")
public class BookService{
	@Autowired private BookRepository bookRepository;
	
	@Transactional(readOnly=true)
	public List<Book> getByUserIdAndState(Integer userId, State state) {
		return bookRepository.getByUser_IdAndState(userId, state);
	}
	
	@Transactional(readOnly=true)
	public boolean getCountByIdAndUserId(Integer id, Integer userId) {
		return bookRepository.getCountByIdAndUserId(id, userId) > 0 ? true : false;
	}
	
	@Transactional(readOnly=true)
	public Book getByIdAndUserId(Integer id, Integer userId) {
		return bookRepository.getByIdAndUser_Id(id, userId);
	}
	
	/**
	 * 更新书籍浏览、收藏、点赞数量
	 */
	@Transactional
	public void updateBookBrowse(Book book) {
		bookRepository.updateBookBrowse(book.getId());
	}
	@Transactional
	public void updateBookFollowCancel(Book book) {
		bookRepository.updateBookFollowCancel(book.getId());
	}
	@Transactional
	public void updateBookFollow(Book book) {
		bookRepository.updateBookFollow(book.getId());
	}
	@Transactional
	public void updateBookPraiseCancel(Book book) {
		bookRepository.updateBookPraiseCancel(book.getId());
	}
	@Transactional
	public void updateBookPraise(Book book) {
		bookRepository.updateBookPraise(book.getId());
	}
	/**
	 * 全文检索书籍
	 * @param pageNo
	 * @param pageSize
	 * @param words
	 * @return
	 */
	@Transactional(readOnly=true)
	public QueryResult<Book> search(int pageNo,int pageSize, String words) {
		return bookRepository.search(pageNo, pageSize, words);
	}
	
	/**
	 * 按用户id和书籍状态获取所有书籍（可见，非回收站）
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Book> findByUser_IdAndStateNotIn(Integer id, List<State> states, int pageNo, int pageSize) {
		Sort sort = new Sort(Direction.DESC, "createTime", "id");
		Pageable pageable = new PageRequest(pageNo-1, pageSize, sort);
		return bookRepository.findByUser_IdAndStateNotIn(id, states, pageable);
	}
	
	/**
	 * 按类别id 和 用户id 和 状态获取所有书籍
	 * 	获取所有属于特定用户特定状态的某个类别的书籍
	 * @param cId
	 * @param userId
	 * @param state
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Book> findByCategory_IdAndUser_IdAndState(Integer cId, Integer userId, State state, int pageNo, int pageSize){
		Sort sort = new Sort(Direction.DESC, "createTime", "id");
		Pageable pageable = new PageRequest(pageNo-1, pageSize, sort);
		return bookRepository.findByCategory_IdAndUser_IdAndState(cId, userId, state, pageable);
	}
	
	
	/**
	 * 按 用户id 和书籍状态 获取书籍所有不重复类别
	 * @param id
	 * @param state
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Category> getCategoryByUserIdAndState(Integer id, State state) {
		return bookRepository.getCategoryByUserIdAndState(id, state);
	}
	
	/**
	 * 按 用户id 获取书籍所有不重复类别
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Category> getCategoryByUserId(Integer id) {
		return bookRepository.getCategoryByUserId(id);
	}
	 
	@Transactional(readOnly=true)
	public long getCountByUserIdAndStateIn(Integer id, List<State> states) {
		return bookRepository.getCountByUserIdAndStateIn(id, states);
	}
	
	/**
	 * 按用户 id 和 书籍状态 查询书籍数量
	 * @param id 用户 id
	 * @param state 书籍状态
	 * @return
	 */
	@Transactional(readOnly=true)
	public long getCountByUserIdAndState(Integer id, State state) {
		return bookRepository.getCountByUserIdAndState(id, state);
	}
	
	/**
	 * 按用户id 查询书籍数量(不包含回收站的书籍)
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public long getCountByUserId(Integer id, List<State> states) {
		return bookRepository.getCountByUserId(id, states);
	}
	
	/**
	 * 按书籍状态获取 所有书籍(分页)
	 * @param pageNo
	 * @param pageSize
	 * @param states
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Book> findByStateIn(int pageNo, int pageSize, String sort, int isExchange) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize, getPageSort(sort));
		if(isExchange >= 0 && isExchange <= 3) {
			if(isExchange == 2) {
				return bookRepository.findByStateIn(Arrays.asList(State.SHARE), pageable);
			} else  if(isExchange == 1) {
				return bookRepository.findByStateIn(Arrays.asList(State.EXCHANGE), pageable);
			} else if(isExchange == 0) {
				return bookRepository.findByStateIn(Arrays.asList(State.SHARE, State.EXCHANGE), pageable);
			}
		}
		return bookRepository.findByStateIn(Arrays.asList(State.SHARE, State.EXCHANGE), pageable);
	}
	
	/**
	 * 设置排序方式
	 * @return
	 */
	private Sort getPageSort(String sort) {
		Sort pageSort = null;
		if(sort != null && !"".equals(sort)) {
			/*if("last".equals(sort)) {
				pageSort = new Sort(Direction.DESC, "createTime");
			}else */if("pop".equals(sort)) {
				pageSort = new Sort(Direction.DESC, "browse");//按浏览数量倒序
			} else if("last".equals(sort)) {
				pageSort = new Sort(Direction.DESC, "createTime");//按时间倒序
			}
		} else {//默认排序
			pageSort = new Sort(Direction.DESC, "createTime");//按时间倒序
		}
		return pageSort;
	}
	
	/**
	 * 按书籍类别 ids 获取所有书籍(不分页)
	 * @param ids
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Book> findByCategory_IdIn(List<Integer> ids, int pageNo, int pageSize, String sort) {
		Pageable pageable  = new PageRequest(pageNo-1, pageSize, getPageSort(sort));
		return bookRepository.findByCategory_IdIn(ids, Arrays.asList(State.SHARE, State.EXCHANGE), pageable);
	}
	
	/**
	 * 按书籍类别 ids 获取所有书籍(分页)
	 * @param ids
	 * @return
	 */
	@Transactional
	public Page<Book> findByCategory_IdInAndStateIn(List<Integer> ids, int pageNo, int pageSize, String sort, int isExchange) {
		Sort pageSort = null;
		Pageable pageable = null;
		if(sort != null && !"".equals(sort)) {
			/*if("last".equals(sort)) {
				pageSort = new Sort(Direction.DESC, "createTime");
			}else */if("pop".equals(sort)) {
				pageSort = new Sort(Direction.DESC, "browse");//按浏览数量倒序
			} else if("last".equals(sort)) {
				pageSort = new Sort(Direction.DESC, "createTime");//按时间倒序
			}
		} else {//默认排序
			pageSort = new Sort(Direction.DESC, "createTime");//按时间倒序
		}
		pageable = new PageRequest(pageNo-1, pageSize, pageSort);
		if(isExchange >=0 && isExchange <=2) {
			if(isExchange == 2) {
				return bookRepository.findByCategory_IdInAndStateIn(ids, Arrays.asList(State.SHARE), pageable);
			} else if(isExchange == 1) {
				return bookRepository.findByCategory_IdInAndStateIn(ids, Arrays.asList(State.EXCHANGE), pageable);
			} else if(isExchange == 0){
				return bookRepository.findByCategory_IdInAndStateIn(ids, Arrays.asList(State.SHARE, State.EXCHANGE), pageable);
			}
		}
		return bookRepository.findByCategory_IdInAndStateIn(ids, Arrays.asList(State.SHARE, State.EXCHANGE), pageable);
	}
	
	/**
	 * 批量审核失败
	 * @param ids
	 */
	@Transactional
	public void batchNoCofirmBook(List<Integer> ids) {
		for(Integer id : ids) {
			Book book = bookRepository.findOne(id);
			if(book == null) {
				throw new BeaspException("你要审核的书籍不存在!");
			}
			if(State.WAITCONFIRM.equals(book.getState())) {
				confirmBook(id, State.CONFIRMFAIL);
			}
		}
	}
	
	/**
	 * 批量审核通过
	 * @param ids
	 */
	@Transactional
	public void batchCofirmBook(List<Integer> ids) {
		for(Integer id : ids) {
			Book book = bookRepository.findOne(id);
			if(book == null) {
				throw new BeaspException("你要审核的书籍不存在!");
			}
			if(State.WAITCONFIRM.equals(book.getState())) {
				if(book.getShare()) {
					confirmBook(id, State.SHARE);
				} else if(book.getExchange()) {
					confirmBook(id, State.EXCHANGE);
				} else {
					confirmBook(id, State.RELEASE);
				}
			}
		}
	}
	
	/**
	 * 审核通过, 审核失败
	 * @param id
	 * @param state
	 */
	@Transactional
	public void confirmBook(Integer id, State state) {
		Book book = bookRepository.findOne(id);
		if(book == null) {
			throw new BeaspException();
		}
		bookRepository.simplyDelete(id, state);
	}
	
	/**
	 * 按 收藏数量 collection 获取前8条记录
	 * @return
	 */
	@Transactional
	public List<Book> findTop8ByOrderByCollectionDescIdDesc() {
		Pageable pageable = new PageRequest(0, 8);
		return bookRepository.findTop8ByOrderByCollectionDescIdDesc(pageable);
	}
	
	/**
	 * 按 发布时间createTime Desc 获取前8条记录
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Book> findTop8ByStateOrderByCreateTimeDesc(State state) {
		Pageable pageable = new PageRequest(0, 8);
		return bookRepository.findTop8ByStateOrderByCreateTimeDesc(state, pageable);
	}
	
	/**
	 * 按 书籍状态state并且createTime Desc 获取前8条记录
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Book> findTop8ByOrderByCreateTimeDesc(){
		Pageable pageable = new PageRequest(0, 8);
		return bookRepository.findTop8ByOrderByCreateTimeDesc(pageable);
	}
	
	/**
	 * 根据书籍标识 id 获取书籍
	 * @param bookId
	 * @return
	 */
	@Transactional
	public Book getOneById(Integer bookId) {
		if(bookId <= 0 || bookId == null) {
			throw new BeaspFrontException("书籍不存在!");
		}
		return bookRepository.findOne(bookId);
	}
	
	/**
	 * 按参数查询书籍（模糊查询）
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @param param（书籍名称，作者，出版社）
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Book> getPageByParams(int pageNo, 
			int pageSize, final String param) {
		Sort sort = new Sort(Direction.DESC, "createTime", "id");
		Pageable pageable = new PageRequest(pageNo-1, pageSize, sort);
		if(param != null) {
			final String [] params = param.split("-");
			if(params.length == 3) {
				Specification<Book> specification = new Specification<Book>() {

					@Override
					public Predicate toPredicate(Root<Book> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate1 = null,predicate2 = null,predicate3 = null;
						List<Predicate> predicates = new ArrayList<Predicate>();
						if(params[0] != null && !"$".equals(params[0])) {
							predicate1 = cb.like(root.get("name").as(String.class), "%"+params[0]+"%");
							predicates.add(predicate1);
						}
						if(params[1] != null && !"$".equals(params[1])) {
							predicate2 = cb.like(root.get("author").as(String.class), "%"+params[1]+"%");
							predicates.add(predicate2);
						}
						if(params[2] != null && !"$".equals(params[2])) {
							predicate3 = cb.like(root.get("version").as(String.class), "%"+params[2]+"%");
							predicates.add(predicate3);
						}
						return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
					}
				};
				return bookRepository.findAll(specification, pageable);
			}
		}
		return bookRepository.findAll(pageable);
	}
	
	/**
	 * 按 用户id 和 书籍状态 和 书籍类别 获取 书籍id
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @param userId 用户id
	 * @param state 书籍状态
	 * @param cId 类别id
	 * @return
	 */
	public List<Date> findCreateTimeByUser_IdAndStateAndCategory_Id(int pageNo, int pageSize, 
			Integer userId, String state, Integer cId) {
		List<Integer> ids = bookRepository.findIdByUser_IdAndStateAndCategory_Id(userId, state, cId, (pageNo-1)*pageSize, pageSize);//基于原生SQL 分页
		if(ids != null && ids.size() > 0) {
			return bookRepository.findCreateTimeById(ids);
		}
		return null;
	}
	
	/**
	 * 按 用户id 和 书籍状态获取 书籍创建时间
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @param userId 用户 id
	 * @param state 书籍状态
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Date> findCreateTimeByUser_IdAndState(int pageNo, int pageSize,
			Integer userId, String state) {
		List<Integer> ids = bookRepository.findIdByUser_IdAndState(userId, state, (pageNo-1)*pageSize, pageSize);//基于原生SQL分页
		if(ids != null && ids.size() > 0) {
			return bookRepository.findCreateTimeById(ids);
		}
		return null;
	}
	
	/**
	 * 按用户标识获取指定状态所有书籍
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @param state 状态
	 * @param user 用户
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Book> getPageByStateAndUserId(int pageNo, 
			int pageSize, final State state, final User user) {
		Sort sort = new Sort(Direction.DESC, "createTime", "id");
		Pageable pageable = new PageRequest(pageNo-1, pageSize, sort);
		Specification<Book> specification = new Specification<Book>() {

			@Override
			public Predicate toPredicate(Root<Book> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate1 = cb.equal(root.get("state"), state);
				Predicate predicate2 = cb.equal(root.get("user").get("id"), user.getId());
				return cb.and(predicate1,predicate2);
			}
		};
		return bookRepository.findAll(specification, pageable);
	}
	
	/**
	 * 按状态获取所有书籍
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @param state 状态
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Book> getPageByState(int pageNo, int pageSize, final State state) {
		Sort sort = new Sort(Direction.ASC, "createTime", "id");
		Pageable pageable = new PageRequest(pageNo-1, pageSize, sort);
		Specification<Book> specification = new Specification<Book>() {

			@Override
			public Predicate toPredicate(Root<Book> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("state"), state);
				return predicate;
			}
		};
		return bookRepository.findAll(specification, pageable);
	}
	
	/**
	 * 获取所有书籍（发布时间降序）
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Book> getPage(int pageNo, int pageSize) {
		Sort sort = new Sort(Direction.ASC, "createTime", "id");
		Pageable pageable = new PageRequest(pageNo-1, pageSize, sort);
		return bookRepository.findAll(pageable);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional
	public void batchDelete(boolean isRecyclebin, List<Integer> ids) {
		if(ids != null && ids.size() > 0) {
			if(isRecyclebin) {//来自回收站
				bookRepository.batchDelete(State.INVISIBLE, ids);
			} else {
				bookRepository.batchDelete(State.RECYCLEBIN, ids);
			}
		}
	}
	
	/**
	 * 删除书籍：（增加一个回收站，用来存放用户执行删除之后的书籍，
	 * 如果再在回收站执行删除之后，那么会把书籍状态设为不可见）
	 * @param id
	 */
	@Transactional
	public void delete(Integer id) {
		Book book = bookRepository.findOne(id);
		if(book == null) {
			throw new BeaspException();
		}
		if(book.getState() != State.RECYCLEBIN) {
			bookRepository.simplyDelete(id, State.RECYCLEBIN);
		} else {
			bookRepository.simplyDelete(id, State.INVISIBLE);
		}
	}
	
	/**
	 * 删除书籍
	 * @param id
	 */
	@Transactional
	public void deleteInRecycleBin(Integer id) {
		Book book = bookRepository.findOne(id);
		if(book == null) {
			throw new BeaspException();
		}
		if(book.getState() != State.RECYCLEBIN) {
			bookRepository.recycleDelete(id, getBookState(book), State.RECYCLEBIN);
		} else {
			bookRepository.simplyDelete(id, State.INVISIBLE);
		}
	}
	
	/**
	 * 还原书籍
	 * @param id
	 */
	@Transactional
	public void backupInRecycleBin(Integer id) {
		Book book = bookRepository.findOne(id);
		if(book == null) {
			throw new BeaspException();
		}
		if(book.getState() == State.RECYCLEBIN) {
			bookRepository.simplyDelete(id, getBookOriginState(book.getOriginState()));//修改状态为原来的的状态
		}
	}
	
	/**
	 * 获取书籍起始状态
	 * @param originState
	 * @return
	 */
	private State getBookOriginState(int originState) {
		if(originState>0) {
			if(originState ==4) {
				return State.WAITCONFIRM;
			} else if(originState == 3) {
				return State.SHARE;
			} else if(originState == 2) {
				return State.EXCHANGE;
			} else if(originState == 1) {
				return State.RELEASE;
			}
		}
		return State.CONFIRMFAIL;
	}
	
	/**
	 * 获取书籍当前状态
	 * @return
	 */
	private int getBookState(Book book) {
		if(book.getState().equals(State.WAITCONFIRM)) {
			return 4;
		} else if(book.getState().equals(State.SHARE)) {
			return 3;
		} else if(book.getState().equals(State.EXCHANGE)) {
			return 2;
		} else if(book.getState().equals(State.RELEASE)) {
			return 1;
		} else  {
			return 0;
		}
	}
	
	/**
	 * 更新/保存书籍
	 * @param book
	 */
	@Transactional
	public Book save(Book book) {
		return bookRepository.saveAndFlush(book);
	}
}
