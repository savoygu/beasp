package org.huel.beasp.repository.book;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.huel.beasp.service.book.StyleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class StyleRepositoryTest {
	@Autowired private StyleService styleService;
	@Autowired private StyleRepository styleRepository;
	@Autowired private DataSource dataSource;
	
	@Test
	public void testBatchDeleteStyle() {
		List<Integer> ids = Arrays.asList(1,2);
		styleService.batchDelete(false, ids);
	}
	
	@Test
	public void testDataSource() throws SQLException {
		System.out.println(dataSource.getConnection());
	}
}
