package org.huel.beasp.repository.book;

import java.util.Date;
import java.util.List;

import javax.persistence.QueryHint;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.Category;
import org.huel.beasp.entity.book.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
/**
 * 书籍持久层
 * @author 001
 *
 */
public interface BookRepository extends JpaRepository<Book, Integer>, 
	JpaSpecificationExecutor<Book>, BookDao{
	
	/**
	 * 获取用户指定状态的所有书籍
	 * @param userId
	 * @param state
	 * @return
	 */
	public List<Book> getByUser_IdAndState(Integer userId, State state);
	
	@Query("SELECT count(b) FROM Book b WHERE b.id=?1 and b.user.id=?2")
	public long getCountByIdAndUserId(Integer id, Integer userId);
	
	public Book getByIdAndUser_Id(Integer id, Integer userId);
	
	/**
	 * 更新书籍浏览、收藏、点赞数量
	 */
	@Modifying
	@Query("UPDATE Book b SET b.browse=b.browse+1 WHERE b.id=?1")
	public void updateBookBrowse(Integer id);
	
	@Modifying
	@Query("UPDATE Book b SET b.collection=b.collection-1 WHERE b.id=?1")
	public void updateBookFollowCancel(Integer id);
	
	@Modifying
	@Query("UPDATE Book b SET b.collection=b.collection+1 WHERE b.id=?1")
	public void updateBookFollow(Integer id);
	
	@Modifying
	@Query("UPDATE Book b SET b.praise=b.praise-1 WHERE b.id=?1")
	public void updateBookPraiseCancel(Integer id);
	
	@Modifying
	@Query("UPDATE Book b SET b.praise=b.praise+1 WHERE b.id=?1")
	public void updateBookPraise(Integer id);
	
	/**
	 * 按书籍ids 获取所有对应书籍
	 * @param ids
	 * @param pageable
	 * @return
	 */
	public Page<Book> findByIdIn(List<Integer> ids, Pageable pageable);
	 
	/**
	 * 按类别id 和 用户id 和 状态获取所有书籍
	 * 	获取所有属于特定用户特定状态的某个类别的书籍
	 * @param cId
	 * @param userId
	 * @param state
	 * @return
	 */
	public Page<Book> findByCategory_IdAndUser_IdAndState(Integer cId, Integer userId, State state, Pageable pageable);
	
	/**
	 * 按 用户id 和书籍状态 获取书籍所有不重复类别
	 * @param id
	 * @param state
	 * @return
	 */
	@Query("SELECT DISTINCT b.category FROM Book b WHERE b.user.id=?1 and b.state=?2")
	public List<Category> getCategoryByUserIdAndState(Integer id, State state);
	
	@Query("SELECT DISTINCT b.category FROM Book b WHERE b.id in(?1)")
	public List<Category> getCategoryByBookIdIn(List<Integer> ids);
	
	/**
	 * 按 用户id 获取书籍所有不重复类别
	 * @param id
	 * @return
	 */
	@Query("SELECT DISTINCT b.category FROM Book b WHERE b.user.id=?1")
	public List<Category> getCategoryByUserId(Integer id);
	
	/**
	 * 按用户id 查询书籍数量
	 * @param id
	 * @return
	 */
	@QueryHints(value={@QueryHint(name=org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value="true")})
	@Query("SELECT count(b) FROM Book b WHERE b.user.id=?1 and b.state not in(?2)")
	public long getCountByUserId(Integer id,List<State> states);
	
	/**
	 * 按用户 id 和书籍状态查询书籍数量
	 * @param id
	 * @param state
	 * @return
	 */
	@Query("SELECT count(b) FROM Book b WHERE  b.user.id=?1 and b.state=?2")
	public long getCountByUserIdAndState(Integer id, State state);
	
	/**
	 * 按用户 id 和书籍状态查询书籍数量
	 * @param id
	 * @param state
	 * @return
	 */
	@Query("SELECT count(b) FROM Book b WHERE  b.user.id=?1 and b.state in(?2)")
	public long getCountByUserIdAndStateIn(Integer id, List<State> states);
	
	
	/**
	 * 获取创建时间
	 * @param ids
	 * @return
	 */
	//@Query(value="SELECT DISTINCT DATE(create_time) FROM beasp_book b WHERE b.id in(?)", nativeQuery=true)
	//@Query("SELECT DISTINCT DATE_FORMAT(b.createTime, '%Y-%m-%d') FROM Book b WHERE b.id in(?1)")
	@Query("SELECT DISTINCT b.createTime FROM Book b WHERE b.id in(?1) ORDER BY b.createTime DESC")
	public List<Date> findCreateTimeById(List<Integer> ids);
	
	/**
	 * 按 用户id 和 书籍状态 和 书籍类别 获取 书籍id
	 * @param userId
	 * @param state
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Query(value="SELECT b.id FROM beasp_book b WHERE b.user_id=? and b.state=? and b.category_id=? ORDER BY create_time DESC limit ?, ?", nativeQuery=true)
	public List<Integer> findIdByUser_IdAndStateAndCategory_Id(Integer userId, String state, Integer cId, int pageNo, int pageSize);
	
	/**
	 * 按 用户id 和 书籍状态获取 书籍id
	 * @param id
	 * @param state
	 * @return
	 */
//	@Query(value="SELECT DISTINCT DATE(create_time) FROM beasp_book b WHERE b.user_id=? and b.state=? GROUP BY create_time  ORDER BY create_time DESC limit ?, ?", nativeQuery=true)
//	public List<String> findCreateTimeByUser_IdAndState(Integer userId, String state, int pageNo, int pageSize);
	@Query(value="SELECT b.id FROM beasp_book b WHERE b.user_id=? and b.state=? ORDER BY create_time DESC limit ?, ?", nativeQuery=true)
	public List<Integer> findIdByUser_IdAndState(Integer userId, String state, int pageNo, int pageSize);
	
	/**
	 * 按书籍 状态 获取所有书籍
	 * @param states
	 * @param pageable
	 * @return
	 */
	public Page<Book> findByStateIn(List<State> states, Pageable pageable);
	
	/**
	 * 按用户id和书籍状态获取所有书籍（可见，非回收站）
	 * @return
	 */
	public Page<Book> findByUser_IdAndStateNotIn(Integer id, List<State> states, Pageable pageable);
	
	/**
	 * 按类别 获取所有书籍
	 * @param ids
	 * @return
	 */
	@Query("SELECT b FROM Book b JOIN FETCH b.category c WHERE c.id in(:ids) and b.state in(:states)")
	public List<Book> findByCategory_IdIn(@Param("ids") List<Integer> ids,@Param("states") List<State> states, Pageable pageable);
	
	/**
	 * 按书籍类别 ids 和 书籍状态 获取所有书籍
	 * @param ids
	 * @param states
	 * @param pageable
	 * @return
	 */
	//@Query("SELECT b FROM Book b  WHERE b.category.id in(:ids) and b.state in(:states)")
	public Page<Book> findByCategory_IdInAndStateIn(@Param("ids") List<Integer> ids,@Param("states") List<State> states, Pageable pageable);
	
	
	/**
	 * 按 收藏数量 collection 获取前8条记录
	 * @return
	 */
	@Query("SELECT b FROM Book b left join fetch b.category c ORDER BY b.collection DESC, b.id DESC")
	public List<Book> findTop8ByOrderByCollectionDescIdDesc(Pageable pageable);
	
	/**
	 * 按 发布时间createTime Desc 获取前8条记录
	 * @return
	 */
	@Query("SELECT b FROM Book b left join fetch b.category c ORDER BY b.createTime DESC, b.id DESC")
	public List<Book> findTop8ByOrderByCreateTimeDesc(Pageable pageable);
	
	/**
	 * 按 书籍状态state并且createTime Desc 获取前8条记录 
	 * @param state
	 * @return
	 */
	@Query("SELECT b FROM Book b left join fetch b.category c where b.state=:state ORDER BY b.createTime DESC, b.id DESC")
	public List<Book> findTop8ByStateOrderByCreateTimeDesc(@Param("state") State state, Pageable pageable);
	
	/**
	 * 按 id 删除书籍（其实是修改状态为不可见）,审核通过, 审核失败
	 * @param id
	 * @param state
	 */
	@Modifying
	@Query("UPDATE Book b SET b.state=:state WHERE b.id=:id")
	public void simplyDelete(@Param("id") Integer id, @Param("state") State state);
	
	/**
	 * 按id 删除书籍，并保存原来的状态
	 * @param id
	 * @param originState
	 * @param state
	 */
	@Modifying
	@Query("UPDATE Book b SET b.state=:state, b.originState=:originState WHERE b.id=:id")
	public void recycleDelete(@Param("id") Integer id, @Param("originState") Integer originState, @Param("state") State state);
	
	/**
	 * 批量删除书籍
	 * @param state
	 * @param ids
	 */
	@Modifying
	@Query("UPDATE Book b SET b.state=:state WHERE b.id in (:ids)")
	public void batchDelete(@Param("state") State state, @Param("ids") List<Integer> ids);
}
