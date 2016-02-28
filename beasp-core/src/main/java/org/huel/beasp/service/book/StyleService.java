package org.huel.beasp.service.book;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.huel.beasp.entity.book.Style;
import org.huel.beasp.exception.BeaspException;
import org.huel.beasp.repository.book.StyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 书籍样式业务逻辑层
 * @author 001
 *
 */
@Service("styleService")
public class StyleService {
	@Autowired private StyleRepository styleRepository;
	
	/**
	 * 选中当前样式
	 * @param id
	 * @return
	 */
	@Transactional
	public Integer choiceCurrent(Integer id) {
		//更新所有的样式，设置chioce=false
		Style style = styleRepository.findOne(id);
		Integer bookId = null;
		if(style != null) {
			bookId = style.getBook().getId();
			List<Style> styles = styleRepository.getByBook_Id(bookId);
			if(styles != null && styles.size() > 0) {
				styleRepository.updateByBookId(false, style.getBook().getId());
			}
		}
		//更新当前样式设置为true
		styleRepository.updateById(true, id);
		return bookId;
	}
	
	/**
	 * 根据书籍 id 获取对应样式（分页）
	 * @param pageNo
	 * @param pageSize
	 * @param bookId
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Style> getPageByBookId(int pageNo, int pageSize, final Integer bookId) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize);
		Specification<Style> specification = new Specification<Style>() {

			@Override
			public Predicate toPredicate(Root<Style> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("book").get("id"), bookId);
				return predicate;
			}
		};
		return styleRepository.findAll(specification, pageable);
	}
	
	/**
	 * 获取所有样式
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Style> getPage(int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize);
		return styleRepository.findAll(pageable);
	}
	
	/**
	 * 根据书籍 id 获取对应样式列表
	 * @param bookId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Style> getByBook_Id(Integer bookId) {
		return styleRepository.getByBook_Id(bookId);
	}
	
	/**
	 * 根据样式 id 获取 对应样式
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Style getById(Integer id) {
		return styleRepository.findOne(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional
	public void batchDelete(boolean visible,List<Integer> ids) {
		
		//真正意义上的删除
		/*List<Style> styles = styleRepository.findAll(ids);
		if(styles != null) {
			throw new BeaspException();
		}
		styleRepository.deleteInBatch(styles);*/
		
		//非真正意义上的删除
		if(ids != null && ids.size() > 0) {
			styleRepository.batchDeleteStyle(visible, ids);
		}
	}
	
	/**
	 * 删除样式
	 * @param id
	 */
	@Transactional
	public void delete(boolean visible, Integer id) {
		Style style = styleRepository.findOne(id);
		if(style == null) {
			throw new BeaspException();
		}
		styleRepository.deleteStyle(visible, id);
	}
	
	/**
	 * 跟新样式
	 * @param style
	 */
	@Transactional
	public void update(Style style) {
		styleRepository.saveAndFlush(style);
	}
	
	/**
	 * 保存样式
	 * @param style
	 * @return
	 */
	@Transactional
	public void save(Style style) {
		//更新所有的样式，设置chioce=false
		if(style.getId() == null) {
			List<Style> styles = styleRepository.getByBook_Id(style.getBook().getId());
			if(styles != null && styles.size() > 0) {
				styleRepository.updateByBookId(false, style.getBook().getId());
			}
		}
		styleRepository.saveAndFlush(style);
	}
}
