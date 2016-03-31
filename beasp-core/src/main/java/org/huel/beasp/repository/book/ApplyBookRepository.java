package org.huel.beasp.repository.book;

import java.util.List;

import org.huel.beasp.entity.book.ApplyBook;
import org.huel.beasp.entity.book.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 求书籍持久层
 * @author 001
 *
 */
public interface ApplyBookRepository extends JpaRepository<ApplyBook, Integer>, JpaSpecificationExecutor<ApplyBook>{
	

	@Modifying
	@Query("update ApplyBook a set a.state=:state where a.id=:id")
	public void verifyPass(@Param("state") State verifypass, @Param("id") Integer id);
	
	@Modifying
	@Query("update ApplyBook a set a.state=?1 where a.id=?2")
	public void verifyFail(State verifypass, Integer id);
	
	@Query("select count(a) from ApplyBook a where a.applyer.id = ?1")
	public long getCountByApplyer_Id(Integer id);
	
	@Query("select count(a) from ApplyBook a where a.requirer.id = ?1")
	public long getCountByRequirer_Id(Integer id);
	
	@Query("select count(distinct a.requirer.id) from ApplyBook a")
	public long getDictinctByRequirer_Id();
	
	public Page<ApplyBook> findByApplyer_IdAndShareExchange(Integer id, int shareExchange, Pageable pageable);
	
	public Page<ApplyBook> findByApplyer_Id(Integer applyerId, Pageable pageable);
	
	public Page<ApplyBook> findByRequirer_IdAndStateInAndShareExchange(Integer id, List<State> states, int shareExchange, Pageable pageable);
	
	public Page<ApplyBook> findByRequirer_IdAndShareExchange(Integer id, int shareExchange, Pageable pageable);
	
	public Page<ApplyBook> findByRequirer_IdAndStateIn(Integer id, List<State> states, Pageable pageable);
	
	public Page<ApplyBook> findByRequirer_Id(Integer requirerId, Pageable pageable);
	
	
	public Page<ApplyBook> findByStateInAndShareExchange(List<State> states, int shareExchange, Pageable pageable);

	public Page<ApplyBook> findByShareExchange(int shareExchange, Pageable pageable);
	
	public Page<ApplyBook> findByStateIn(List<State> states, Pageable pageable);

}
