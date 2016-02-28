package org.huel.beasp.repository.book;

import java.util.List;

import org.huel.beasp.entity.book.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * 书籍样式持久层
 * @author 001
 *
 */
public interface StyleRepository extends JpaRepository<Style, Integer>, JpaSpecificationExecutor<Style>{
	
	@Modifying
	@Query("UPDATE Style s SET s.choice=?1 WHERE s.book.id=?2")
	public void updateByBookId(boolean chioce, Integer bookId);
	
	@Modifying
	@Query("UPDATE Style s SET s.choice=?1 WHERE s.id=?2")
	public void updateById(boolean chioce, Integer id);
	
	@Modifying
	@Query("UPDATE Style s SET s.visible=:visible WHERE s.id=:id")
	public void deleteStyle(@Param("visible") boolean visible, @Param("id") Integer id);
	
	@Modifying
	@Query("UPDATE Style s SET s.visible=:visible WHERE s.id in (:ids)")
	public void batchDeleteStyle(@Param("visible") boolean visible, @Param("ids") List<Integer> ids);
	
	/**
	 * 根据书籍 id 获取所有样式
	 * @param booId
	 * @return
	 */
	public List<Style> getByBook_Id(Integer bookId);
}