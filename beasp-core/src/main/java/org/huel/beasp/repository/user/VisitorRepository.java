package org.huel.beasp.repository.user;

import java.util.List;

import org.huel.beasp.entity.user.User;
import org.huel.beasp.entity.user.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisitorRepository extends JpaRepository<Visitor, Integer>{
	
	/**
	 * 按被访问者id 获取所有访客
	 * @param id
	 * @return
	 */
	@Query("SELECT v FROM Visitor v JOIN FETCH v.visitor JOIN FETCH v.user WHERE v.user.id=?1 ORDER BY v.visitTime DESC")//解决1+n问题
	public List<Visitor> findByUser_Id(Integer id);
	
	/**
	 * 获取所有访问者
	 * @param id
	 * @return
	 */
	@Query("SELECT v.visitor FROM Visitor v WHERE v.user.id=?1 ORDER BY v.visitTime DESC")
	public List<User> findVisitorByUser_Id(Integer id);
	
	/**
	 * 按被访问者和访客的id 获取记录数
	 * @param userId
	 * @param visitorId
	 * @return
	 */
	@Query("SELECT count(v) FROM Visitor v WHERE v.user.id=?1 and v.visitor.id=?2")
	public long getCountByUser_IdAndVisitor_Id(Integer userId, Integer visitorId);
	
	/**
	 * 按被访问者和访客的id 获取访问者和访客信息//
	 * @param userId
	 * @param visitorId
	 * @return
	 */
	@Query("SELECT v FROM Visitor v JOIN FETCH v.visitor JOIN FETCH v.user WHERE v.user.id=?1 and v.visitor.id=?2")//解决1+n问题
	public Visitor findVisitorByUser_IdAndVisitor_Id(Integer userId, Integer visitorId);
}
