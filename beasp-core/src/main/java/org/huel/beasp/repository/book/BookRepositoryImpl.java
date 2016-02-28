package org.huel.beasp.repository.book;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.common.QueryResult;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

/**
 * 自定义全文检索实现
 * @author 001
 *
 */
public class BookRepositoryImpl implements BookDao {
	@PersistenceContext private EntityManager em;
	
	/**
	 * 搜索书籍
	 * @param pageNo 当前页码
	 * @param pageSize 每页大小
	 * @param words 搜索关键字
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public QueryResult<Book> search(int pageNo, int pageSize, String words) {
		QueryResult<Book> qr = new QueryResult<Book>();
		FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
		QueryBuilder qb = ftem.getSearchFactory().buildQueryBuilder().forEntity(Book.class).get();
		Query q = qb.keyword().onFields("bookName", "author", "summary").matching(words).createQuery();
		
		FullTextQuery ftq = ftem.createFullTextQuery(q, Book.class);
		qr.setTotalRecord(ftq.getResultSize());
		
		List<Book> books = ftq.setFirstResult(pageNo).setMaxResults(pageSize).getResultList();
		
		books = highLight(q, books, Book.class, null, "name", "author", "summary");
		
		qr.setResultList(books);
		return qr;
	}
	
	/**
	 * 高亮搜索结果
	 * @param org.apache.lucene.search.Query luceneQuery
	 * @param searchResults 搜索结果集
	 * @param searchResultClass 搜索结果类型
	 * @param excludeFields 要排除高亮的字段
	 * @param fieldNames 需要高亮的字段
	 * @return 高亮后的searchResults
	 */
	private <E> List<E> highLight(Query luceneQuery, List<E> searchResults, Class<E> searchResultClass,
			List<String> excludeFields, String ... fieldNames) {
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class=\"highlight\">", "</span>");//高亮修饰
		QueryScorer queryScorer = new QueryScorer(luceneQuery);
		Highlighter highlighter = new Highlighter(formatter, queryScorer);
		Analyzer analyzer = new SmartChineseAnalyzer();
		
		for(E e : searchResults) {
			for(String fieldName : fieldNames) {
				if(null != excludeFields && excludeFields.contains(fieldName)) {//去除非高亮
					continue;
				}
				//获取属性对应的属性值
				Object fieldValue = ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(searchResultClass, fieldName).getReadMethod(), e);
				
				String highLightFieldValue = null;
				
				if(fieldValue instanceof String) {
					try {
						//高亮属性值
						highLightFieldValue = highlighter.getBestFragment(analyzer, fieldName, String.valueOf(fieldValue));
					} catch (Exception e1) {e1.printStackTrace();}
					if(highLightFieldValue != null) {//高亮值非空重新赋值
						//重新写入属性值
						ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(searchResultClass, fieldName).getWriteMethod(), e, highLightFieldValue);
					}
				}
			}
		}
		return searchResults;
	}
}
