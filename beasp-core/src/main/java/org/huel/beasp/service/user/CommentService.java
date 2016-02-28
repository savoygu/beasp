package org.huel.beasp.service.user;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.huel.beasp.entity.user.Comment;
import org.huel.beasp.repository.user.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("commentService")
public class CommentService {
	@Autowired private CommentRepository commentRepository;
	
	/**
	 * 按书籍id 获取所有评论（改善方式，自定义）
	 * @param pageNo
	 * @param pageSize
	 * @param bookId
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Comment> findByBook_Id(int pageNo, int pageSize, final Integer bookId) {
		Sort sort = new Sort(Direction.DESC, "commentTime", "id");
		Pageable pageable = new PageRequest(pageNo-1, pageSize, sort);
		Specification<Comment> specification = new Specification<Comment>() {

			@Override
			public Predicate toPredicate(Root<Comment> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("book").get("id"), bookId);
				return predicate;
			}
		};
		return commentRepository.findAll(specification, pageable);
	}
	/**
	 * 删除评论
	 * @param comment
	 */
	@Transactional
	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}
	/**
	 * 保存评论
	 * @param comment
	 */
	@Transactional
	public void save(Comment comment) {
		commentRepository.saveAndFlush(comment);
	}
}
