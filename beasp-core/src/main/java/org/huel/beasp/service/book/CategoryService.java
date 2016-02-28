package org.huel.beasp.service.book;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.huel.beasp.entity.book.Category;
import org.huel.beasp.exception.BeaspException;
import org.huel.beasp.repository.book.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 书籍类别业务逻辑层
 * 
 * @author 001
 *
 */
@Service("categoryService")
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * 获取相应子类别
	 * @return
	 */
	public List<Integer> getSubCategoryIds(Integer[] ids) {
		return categoryRepository.getSubCategoryIds(Arrays.asList(ids));
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional
	public void batchDelete(List<Integer> ids){
		List<Category> categories = categoryRepository.findAll(ids);
		if(categories == null) {
			throw new BeaspException();
		}
		categoryRepository.deleteInBatch(categories);
	}
	
	/**
	 * 按类别名称模糊查询
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @param name 类别名称（模糊的）
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Category> getPageByName(int pageNo, int pageSize, final String name) {
		Pageable pageable = new PageRequest(pageNo - 1, pageSize);
		Specification<Category> specification = new Specification<Category>() {

			@Override
			public Predicate toPredicate(Root<Category> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate predicate = cb.like(root.get("name").as(String.class), "%"+name+"%");
				return predicate;
			}
		};
		return categoryRepository.findAll(specification, pageable);
	}
	
	/**
	 * 根据"父类别标识"获取所有子类别
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @param parentId 父类别标识
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Category> getPageByParentId(int pageNo, int pageSize, final Integer parentId) {
		Pageable pageable = new PageRequest(pageNo - 1, pageSize);
		Specification<Category> specification = new Specification<Category>() {

			@Override
			public Predicate toPredicate(Root<Category> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("parent"), parentId);
				//顶级类别
				if(parentId == null || parentId < 1) {
					predicate = cb.isNull(root.get("parent"));
				}
				return predicate;
			}
		};
		return categoryRepository.findAll(specification, pageable);
	}
	
	/**
	 * 获取所有类别并分页
	 * @param pageNo 当前页
	 * @param pageSize 每页大小
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Category> getPage(int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo - 1, pageSize);
		Specification<Category> specification = new Specification<Category>() {

			@Override
			public Predicate toPredicate(Root<Category> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {
				//顶级类别
				Predicate predicate = cb.isNull(root.get("parent"));
				return predicate;
			}
		};
		return categoryRepository.findAll(specification, pageable);
	}
	
	/**
	 * 获取所有父类别为空的类别
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Category> getByParentIsNull() {
		return categoryRepository.getByParentIsNull();
	}
	
	/**
	 * 获取所有父类别不为空的类别
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Category> getByParentIsNotNull() {
		return categoryRepository.getByParentIsNotNull();
	}
	
	/**
	 * 按父类 Id 获取所有子类别
	 * @param parentId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Category> getPartByParentId(Integer parentId) {
		return categoryRepository.getPartByParent_Id(parentId);
	}
	
	/**
	 * 按父类 Id 获取所有子类别
	 * @param parentId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Category> getByParentId(Integer parentId) {
		return categoryRepository.getByParent_Id(parentId);
	}
	
	/**
	 * 获取所有类别(二级缓存)
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Category> getAll() {
		return categoryRepository.getAll();
	}

	/**
	 * 删除类别
	 * @param id 类别标识
	 */
	@Transactional
	public void delete(Integer id) {
		if(categoryRepository.findOne(id) == null) {
			throw new BeaspException();
		}
		categoryRepository.delete(id);
	}

	/**
	 * 获取类别
	 * @param id 类别标识
	 * @return
	 */
	public Category getById(Integer id) {
		return categoryRepository.findOne(id);
	}

	/**
	 * 保存类别
	 * @param category 类别
	 */
	@Transactional
	public void save(Category category) {
		categoryRepository.saveAndFlush(category);
	}

	/**
	 * 获取类别
	 * @param name 类别名称
	 * @return
	 */
	public Category getByName(String name) {
		return categoryRepository.getByName(name);
	}
}
