package org.huel.beasp.repository.book;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.common.QueryResult;


/**
 * 自定义全文检索接口
 * @author 001
 *
 */
public interface BookDao {
	//自定义全文检索方法
	public QueryResult<Book> search(int pageNo, int pageSize, String words);
}
