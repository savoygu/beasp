package org.huel.beasp.service.user;

import java.util.List;

import org.huel.beasp.entity.user.User;
import org.huel.beasp.entity.user.Visitor;
import org.huel.beasp.repository.user.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("visitorService")
public class VisitorService {
	@Autowired private VisitorRepository visitorRepository;
	
	/**
	 * 按被访问者id 获取所有访客
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Visitor> findByUser_Id(Integer id) {
		return visitorRepository.findByUser_Id(id);
	}
	
	/**
	 * 获取所有访问者
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<User> findVisitorByUser_Id(Integer id) {
		return visitorRepository.findVisitorByUser_Id(id);
	}
	
	/**
	 * 保存访客与被访问者信息
	 * @param visitor
	 */
	@Transactional
	public void save(Visitor visitor) {
		visitorRepository.saveAndFlush(visitor);
	}
	
	/**
	 * 按访客与被访客 id 判断访客与被访客信息是否存在，
	 * @param userId
	 * @param visitorId
	 * @return true表示存在，false表示不存在
	 */
	@Transactional(readOnly=true)
	public boolean getCountByUser_IdAndVisitor_Id(Integer userId, Integer visitorId) {
		return visitorRepository.getCountByUser_IdAndVisitor_Id(userId, visitorId) > 0;
	}
	
	/**
	 * 按访客与被访客 id 获取访客与被访客信息
	 * @param userId
	 * @param visitorId
	 * @return
	 */
	@Transactional(readOnly=true)
	public Visitor findVisitorByUser_IdAndVisitor_Id(Integer userId, Integer visitorId) {
		return visitorRepository.findVisitorByUser_IdAndVisitor_Id(userId, visitorId);
	}
	
	/**
	 * 按id 获取访客与被访客信息
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Visitor getById(Integer id) {
		return visitorRepository.getOne(id);
	}
}
