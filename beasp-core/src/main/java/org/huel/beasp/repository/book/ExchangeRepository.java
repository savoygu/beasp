package org.huel.beasp.repository.book;

import java.util.Date;
import java.util.List;

import org.huel.beasp.entity.book.Exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 交换持久层
 * @author 001
 *
 */
public interface ExchangeRepository extends JpaRepository<Exchange, Integer>,
	JpaSpecificationExecutor<Exchange>{
	
	@Query("SELECT DISTINCT e.applyTime FROM Exchange e WHERE e.id in(?1) ORDER BY e.applyTime DESC")
	public List<Date> getApplyTimeById(List<Integer> ids);
	
	@Query(value="select e.id from beasp_exchange e where e.user_id=? order by e.apply_time desc limit ?, ?", nativeQuery=true)
	public List<Integer> getIdByUser_Id(Integer userId, int pageNo, int pageSize);
	
	@Query(value="select e.id from beasp_exchange e where e.exchanger_id=? order by e.apply_time desc limit ?, ?", nativeQuery=true)
	public List<Integer> getIdByExchanger_Id(Integer exchangerId, int pageNo,
			int pageSize);
	
	@Query(value="select e.id from beasp_exchange e left join beasp_book b on e.target_id=b.id where e.user_id=? and b.category_id=? order by e.apply_time desc limit ?, ?", nativeQuery=true)
	public List<Integer> getIdByTarget_Category_IdAndUser_Id(Integer userId,
			Integer cId, int pageNo, int pageSize);
	
	@Query(value="select e.id from beasp_exchange e left join beasp_book b on e.target_id=b.id where e.exchanger_id=? and b.category_id=? order by e.apply_time desc limit ?, ?", nativeQuery=true)
	public List<Integer> getIdByTarget_Category_IdAndExchanger_Id(
			Integer userId, Integer cId, int pageNo, int pageSize);
	
	public Page<Exchange> findByExchanger_Id(Integer exchangeId, Pageable pageable);
	
	public Page<Exchange> findByUser_Id(Integer userId, Pageable pageable);
	
	@Query("select count(e) from Exchange e where e.target.id=?1 and e.result in(?2)")
	public long getCountByTarget_IdAndResultIn(Integer targetId, List<Integer> results);
	
	@Query("select count(e) from Exchange e where e.target.id=?1")
	public long getCountByTarget_Id(Integer targetId);

	@Query("select e.target.id from Exchange e where e.user.id=?1")
	public List<Integer> getBookIdByUser_Id(Integer userId);
	
	@Query("select e.target.id from Exchange e where e.exchanger.id=?1")
	public List<Integer> getBookIdByExchanger_Id(Integer exchanger);

	@Query("select count(e) from Exchange e where e.exchanger.id=?1")
	public long getCountByExchange_Id(Integer exchangeId);

	public Page<Exchange> findByTarget_Category_IdAndUser_Id(Integer cId,
			Integer userId, Pageable pageable);
	
	public Page<Exchange> findByTarget_Category_IdAndExchanger_Id(Integer cId,
			Integer exchangeId, Pageable pageable);

	@Query("select count(e) from Exchange e where e.user.id=?1")
	public long getCountByUser_Id(Integer userId);
}
