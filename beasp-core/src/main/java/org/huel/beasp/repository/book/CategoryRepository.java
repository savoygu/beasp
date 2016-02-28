package org.huel.beasp.repository.book;

import java.util.List;

import javax.persistence.QueryHint;

import org.huel.beasp.entity.book.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
/**
 * 书籍类别持久层
 * @author 001
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
	
	/**
	 * 按类别id 获取相应子类别
	 * @param ids
	 * @return
	 */
	@Query("SELECT c.id FROM Category c WHERE c.parent.id in(:ids)")
	public List<Integer> getSubCategoryIds(@Param("ids") List<Integer> ids);
	
	
	/**
	 * SpringData 整合 JPA 使用二级缓存：
	 * 		除了一些配置和jar包，还有一些配置文件外，还需自定义jpql语句，并追加@QueryHints 注解
	 * @return
	 */
	@QueryHints(value={@QueryHint(name=org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value="true")})
	@Query("SELECT c FROM Category c")
	public List<Category> getAll();
	
	@Modifying
	@Query("DELETE FROM Category c WHERE c.id=:id")
	public void deleteCategory(@Param("id") Integer id);
	
	/**
	 * 按类别名称获取类别
	 * @param name
	 * @return
	 */
	public Category getByName(String name);
	
	/**
	 * 获取所有"没有父类"别的类别
	 * @return
	 */
//	public List<Category> getByParent_IdIsNull();
	public List<Category> getByParentIsNull();
//	@Query("SELECT c FROM Category c WHERE c.parent is null")
//	public List<Category> get();
	
	/**
	 * 获取所有父类别不为空的类别
	 * @return
	 */
	public List<Category> getByParentIsNotNull();
	
	/**
	 * 根据父类 Id 获取所有子类别
	 * @param parentId
	 * @return
	 */
	public List<Category> getByParent_Id(Integer parentId);
	
	@Query("SELECT new Category(id,name) FROM Category c WHERE c.parent.id=:parentId")
	public List<Category> getPartByParent_Id(@Param("parentId") Integer parentId);
}
