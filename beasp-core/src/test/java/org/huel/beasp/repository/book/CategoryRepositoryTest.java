package org.huel.beasp.repository.book;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.huel.beasp.entity.book.Category;
import org.huel.beasp.service.book.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CategoryRepositoryTest {
    @Autowired	private CategoryRepository categoryRepository;
    @Autowired private CategoryService categoryService;
    @Autowired private DataSource dataSource;
    
    //获取相应子类别 ids
    @Test
    public void testGetSubCategoryIds() {
    	System.out.println(categoryRepository.getSubCategoryIds(Arrays.asList(new Integer[]{1})));
    }
    
    @Test
    public void testGetPartByParent_Id() {
    	List<Category> categories = categoryService.getPartByParentId(1);
    	print(categories);
    }
    
    @Test
    public void testGetByParentId() {
    	List<Category> categories = categoryService.getByParentId(1);
    	print(categories);
    }
    
    @Test
    public void testGetPageByParentId() {
    	Page<Category> page = categoryService.getPageByParentId(1, 5, null);
    	print(page.getContent());
    	System.out.println(page.getNumber());
    	System.out.println(page.getNumberOfElements());
    	System.out.println(page.getSize());
    	System.out.println(page.getTotalPages());
    	System.out.println(page.getTotalElements());
    }
    
    @Test
    public void testGetByParent_IdIsNull() {
    	List<Category> categories = categoryService.getByParentIsNull();
    	print(categories);
    }
    
    private void print(List<Category> categories) {
    	for(Category category : categories) {
    		System.out.println(category.getId());
    		System.out.println(category.getName());
    		System.out.println(category.getDescribe());
    	}
    }
    
	@Test
	public void testDelete() {
		categoryService.delete(1);
	}
	
	@Test
	public void testUpdate() {
		Category category = new Category();
		category.setId(1);
		category.setName("励志");
		category.setDescribe("励志类书籍");
		categoryService.save(category);
	}
	
	@Test
	public void testSave() {
		for(int i = 0; i < 100; i++) {
			Category category = new Category();
			category.setName("教育"+i);
			category.setDescribe("教育类书籍"+i);
			categoryService.save(category);
		}
	}
	
	@Test
	public void testTransactional() {
		List<Category> categories = categoryService.getAll();
		System.out.println(categories);
	}
	
	@Test
	public void testSecondLevelCache() {
		List<Category> categories = categoryRepository.getAll();
		System.out.println(categories);
		categories = categoryRepository.getAll();
		System.out.println(categories);
	}
	
	@Test
	public void testDataSource() throws SQLException {
		System.out.println(dataSource.getConnection());
	}

}
