package org.huel.beasp.repository.book;

import java.util.List;

import org.huel.beasp.entity.book.BookUser;
import org.huel.beasp.entity.book.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookUserRepository extends JpaRepository<BookUser, Integer>, JpaSpecificationExecutor<BookUser>{
	
//	@Query("select bu from BookUser bu left join fetch bu.user u left join fetch bu.book b where u.id=?1 and bu.state=?2 order by bu.createTime desc")
//	public BookUser getLastestBrowseByUser_Id(Integer userId, State state);
	
	public BookUser getTop1ByUser_IdAndStateOrderByCreateTimeDesc(Integer userId, State state);
	
	/**
	 * 按用户 id 和书籍状态查询书籍数量
	 * @param id
	 * @param state
	 * @return
	 */
	@Query("SELECT count(b) FROM BookUser b WHERE  b.user.id=?1 and b.state=?2")
	public long getCountByUserIdAndState(Integer id, State state);
	
	/**
	 * 按书籍id和用户id和书籍状态获取BookUser实例
	 * @param bookId
	 * @param userId
	 * @return
	 */
	public BookUser findByBook_IdAndUser_IdAndState(Integer bookId, Integer userId, State state);
	
	/**
	 * 获取BookUser实例列表
	 * @return
	 */
	@Query("SELECT b FROM BookUser b JOIN FETCH b.book JOIN FETCH b.user WHERE b.user.id=:id and b.state=:state")//解决（1级）1+n问题
	public List<BookUser> findByUser_IdAndState(@Param("id") Integer userId, @Param("state") State state);
	
	/**
	 * 方案一：1+n问题较多, 替代方案可以使用JpaSpecificationExecutor解决
	 */
	public Page<BookUser> findByUser_IdAndState(Integer userId, State state, Pageable pageable);
	
	/**
	 * 方案二：(存在bug，无法探知book的浏览顺序)
	 */
	/**
	 * 获取书籍ids
	 * @param userId
	 * @param state
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Query(value="SELECT b.book_id FROM beasp_book_user b WHERE b.user_id=? and b.state=? ORDER BY b.create_time DESC, b.id DESC limit ?, ?", nativeQuery=true)
	public List<Integer> findBookIdByUser_IdAndState(Integer userId, String state, int pageNo, int pageSize);
}
