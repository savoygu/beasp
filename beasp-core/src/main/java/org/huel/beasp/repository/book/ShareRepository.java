package org.huel.beasp.repository.book;

import java.util.Date;
import java.util.List;

import org.huel.beasp.entity.book.Share;
import org.huel.beasp.entity.book.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
/**
 * 分享持久层
 * @author 001
 *
 */
public interface ShareRepository extends JpaRepository<Share, Integer>, 
	JpaSpecificationExecutor<Share>{
	
	public Share getByTarget_Id(Integer bookId);
	
	@Query(value="select s.id from beasp_share s left join beasp_book b on s.target_id=b.id where b.user_id=? and b.category_id=? order by s.apply_time desc limit ?, ?", nativeQuery=true)
	public List<Integer> getByTarget_User_IdAndCategory_Id(Integer userId, Integer cId, int pageNo, int pageSize);
	
	@Query(value="select s.id from beasp_share s left join beasp_book b on s.target_id=b.id where b.user_id=?  order by s.apply_time desc limit ?, ?", nativeQuery=true)
	public List<Integer> getIdByTarget_User_Id(Integer userId, int pageNo, int pageSize);
	
	public Page<Share> findByTarget_Category_IdAndTarget_User_Id(Integer cId, Integer userId, Pageable pageable);
	
	public Page<Share> findByTarget_User_Id(Integer userId, Pageable pageable);
	
	@Query("select count(s) from Share s where s.user.id=?1")
	public long getCountByUser_Id(Integer userId);
	
	@Query("select s.target.id  from Share s where s.target.user.id=?1")
	public List<Integer> getBookIdByTarget_User_Id(Integer userId);
	
	@Query("select s.target.id from Share s where s.user.id=?1")
	public List<Integer> getBookIdByUser_Id(Integer userId);
	
	@Query("SELECT DISTINCT s.applyTime FROM Share s WHERE s.id in(?1) ORDER BY s.applyTime DESC")
	public List<Date> getApplyTimeById(List<Integer> ids);

	@Query(value="select s.id from beasp_share s left join beasp_book b on s.target_id=b.id where s.user_id=? and b.category_id=? order by s.apply_time desc limit ?, ?", nativeQuery=true)
	public List<Integer> getIdByUser_IdAndCategory_Id(Integer userId, Integer cId, int pageNo, int pageSize);
	
	@Query(value="select s.id from beasp_share s where s.user_id=? order by s.apply_time desc limit ?, ?", nativeQuery=true)
	public List<Integer> getIdByUser_Id(Integer userId, int pageNo, int pageSize);
	
	public Page<Share> findByTarget_Category_IdAndUser_IdAndState(Integer cId, Integer userId, State state, Pageable pageable);
	
	public Page<Share> findByTarget_Category_IdAndUser_Id(Integer cId, Integer userId, Pageable pageable);
	
	public Page<Share> findByTarget_IdAndUser_IdAndState(Integer bookId, Integer userId, State state, Pageable pageable);
	
	public Page<Share> findByUser_IdAndState(Integer userId, State state, Pageable pageable);
	
	public Page<Share> findByTarget_IdAndUser_Id(Integer bookId, Integer userId, Pageable pageable);
	
	public Page<Share> findByUser_Id(Integer userId, Pageable pageable);
  	
	@Query("SELECT count(s) FROM Share s WHERE s.target.id=?1 AND s.result in (?2)")
	public long getCountByTarget_IdAndResultIn(Integer bookId, List<Integer> results);
	
	@Query("SELECT count(s) FROM Share s WHERE s.target.id=?1")
	public long getCountByTarget_Id(Integer bookId);
	
	public Share getByUser_IdAndTarget_Id(Integer userId, Integer bookId);
	
	
}
