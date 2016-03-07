package org.huel.beasp.repository.book;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.book.BookUser;
import org.huel.beasp.entity.book.Category;
import org.huel.beasp.entity.book.Exchange;
import org.huel.beasp.entity.book.Language;
import org.huel.beasp.entity.book.Share;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.entity.book.Style;
import org.huel.beasp.entity.book.Want;
import org.huel.beasp.entity.common.QueryResult;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.service.book.BookService;
import org.huel.beasp.service.book.BookUserService;
import org.huel.beasp.service.book.ExchangeService;
import org.huel.beasp.service.book.ShareService;
import org.huel.beasp.service.book.StyleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class BookRepositoryTest {
	
	/*private ApplicationContext ctx;
	private BookService bookService;
	private BookRepository bookRepository;
	private DataSource dataSource;
	{
		ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
		bookService = ctx.getBean(BookService.class);
		bookRepository = ctx.getBean(BookRepository.class);
		dataSource = ctx.getBean(DataSource.class);
	}*/
	
	@Autowired private BookService bookService;
	@Autowired private StyleService styleService;
	@Autowired private BookRepository bookRepository;
	@Autowired private DataSource dataSource;
	@Autowired private BookUserRepository bookUserRepository;
	@Autowired private BookUserService bookUserService;
	@Autowired private ShareRepository shareRepository;
	@Autowired private ShareService shareService;
	@Autowired private ExchangeRepository exchangeRepository;
	@Autowired private ExchangeService exchangeService;
	
	@Test
	public void testgetLastestBrowseByUser_Id() {
		System.out.println(bookUserRepository.getTop1ByUser_IdAndStateOrderByCreateTimeDesc(6, State.BROWSE).getBook().getId());
	}
	
	@Test
	public void testgetTop10ByOrderByCollectionDesc() {
		print(bookService.getTop10ByOrderByCollectionDesc());
	}
	
	@Test
	public void testfindByUser_Id() {
		for(Exchange exchange : exchangeService.findByUserId(7, 1, 5).getContent()) {
			System.out.println(exchange.getId());
		}
		
		for(Exchange exchange : exchangeService.findByExchangerId(2, 1, 5).getContent()) {
			System.out.println(exchange.getId());
		}
		
		System.out.println(exchangeRepository.getIdByUser_Id(7, 0, 5));
		System.out.println(exchangeRepository.getApplyTimeById(Arrays.asList(1)));
		System.out.println(exchangeRepository.getBookIdByUser_Id(7));
		System.out.println(exchangeRepository.getCountByExchange_Id(2));
		System.out.println(exchangeRepository.getIdByTarget_Category_IdAndUser_Id(7, 30, 0, 5));
	}
	
	@Test
	public void testExchange() {
		Book book = bookService.getOneById(121);
		List<Book> books = bookService.getByUserIdAndState(2, State.EXCHANGE);
		/*for(Book book2 : books) {
			System.out.println(book2.getName()+"---"+book2.getAuthor()+"---"+book2.getVersion());
		}
		for(Want want : book.getWants()) {
			System.out.println(want.getName()+"---"+want.getAuthor()+"---"+want.getVersion());
		}*/
		for(Want want : book.getWants()) {
			for(Book book2 : books) {
				if(book2.getName().equals(want.getName()) &&
						book2.getAuthor().equals(want.getAuthor()) && book2.getVersion().equals(want.getVersion())) {
					System.out.println(book2.getName());
					break;
				}
			}
		}
	}
	
	@Test
	public void testgetCountByTarget_IdAndResultIn3() {
		System.out.println(exchangeRepository.getCountByTarget_IdAndResultIn(1, Arrays.asList(0,1)));
	}
	
	@Test
	public void testgetIdByTarget_User_Id() {
		System.out.println(shareRepository.getIdByTarget_User_Id(1, 0, 5));
		System.out.println(shareRepository.getBookIdByTarget_User_Id(1));
		System.out.println(shareRepository.getByTarget_User_IdAndCategory_Id(1, 34, 0, 5));
		System.out.println(shareRepository.getByTarget_Id(120).getId());
	}
	
	@Test
	public void testfindByTarget_Category_IdAndUser_IdAndState() {
		for(Share share : shareService.findByTargetCategoryIdAndUserId(7, 2, 1, 5).getContent()) {
			System.out.println(share.getId());
		}
		System.out.println(shareRepository.getCountByUser_Id(2));
	}
	
	@Test
	public void testgetIdByUser_IdAndCategory_Id() {
		System.out.println(shareRepository.getIdByUser_IdAndCategory_Id(2, 7, 0, 5));
	}
	
	@Test
	public void testgetCategoryByUserId() {
		
		for(Category category : shareService.getCategoryByUserId(2)) {
			System.out.println(category.getName());
		}
	}
	
	@Test
	public void testgetCategoryByBookIdIn() {
		for(Category category : bookRepository.getCategoryByBookIdIn(Arrays.asList(29,1,45,78,99))) {
			System.out.println(category.getName());
		}
	}
	
	@Test
	public void testgetBookIdByUser_Id() {
		System.out.println(shareRepository.getBookIdByUser_Id(2));
	}
	
	@Test
	public void testgetApplyTimeById() {
		System.out.println(shareRepository.getApplyTimeById(Arrays.asList(5)));
	}
	
	@Test
	public void testgetIdByUserId() {
		System.out.println(shareRepository.getIdByUser_Id(2, 0, 5));
	}
	
	@Test
	public void testfindByTarget_IdAndUser_IdAndState() {
		for(Share share : shareRepository.findByUser_Id(2, new PageRequest(0, 5)).getContent()) {
			System.out.println(share.getId());
		}
		System.out.println(shareRepository.findByUser_Id(2, new PageRequest(0, 5)));
	}
	
	@Test
	public void testgetByIdAndUserId() {
		System.out.println(bookRepository.getCountByIdAndUserId(1, 2));
	}
	
	@Test
	public void testgetCountByTarget_IdAndResultIn2() {
		System.out.println(shareRepository.getCountByTarget_IdAndResultIn(1, Arrays.asList(0,1)));
	}
	
	@Test
	public void testgetCountByTarget_IdAndResultIn() {
		System.out.println(shareService.getCountByTarget_IdAndResultIn(1));
	}
	
	@Test
	public void testgetCountByTarget_Id() {
		System.out.println(shareRepository.getCountByTarget_Id(1));
	}
	
	@Test
	public void testgetByUser_IdAndTarget_Id() {
		System.out.println(shareRepository.getByUser_IdAndTarget_Id(1, 1));
	}
	
	@Test
	public void testfindByUser_IdAndState3() {
		for(BookUser bu : bookUserService.findByUser_IdAndState(2, State.COLLECTION)) {
			System.out.println(bu.getBook().getId());
		}
	}
	
	@Test
	public void testfindByUser_IdAndState2() {
		for(BookUser bu : bookUserService.findByUser_IdAndState(2, State.BROWSE, 1, 3).getContent()) {
			System.out.println(bu.getBook().getName() + "----" + bu.getBook().getId());
		}
	}
	
	@Test
	public void testfindBookIdByUser_IdAndState() {
		print(bookUserService.findBookIdByUser_IdAndState(2, "BROWSE",1, 4).getContent());
	}
	
	@Test
	public void testfindByUser_IdAndState() {
		System.out.println(bookUserRepository.findBookIdByUser_IdAndState(2, "BROWSE", 0, 2));
	}
	
	@Test
	public void testfindByBook_IdAndUser_IdAndState() {
		System.out.println(bookUserRepository.findByBook_IdAndUser_IdAndState(1, 1, State.BROWSE));
	}
	
	@Test
	public void testBaseDao() {
		QueryResult<Book> qr = bookService.search(0, 12, "美好");
		System.out.println(qr.getTotalRecord());
		for(Book book : qr.getResultList()) {
			System.out.println(book.getId()+"-----"+book.getName()+"-----"+book.getAuthor()+"-----"+book.getSummary());
			
		}
	}
	
	@Test
	public void testfindByUser_IdAndStateNotIn() {
		print(bookRepository.findByUser_IdAndStateNotIn(2, Arrays.asList(State.RECYCLEBIN, State.INVISIBLE), new PageRequest(0, 10)).getContent());
	}
	
	@Test
	public void testfindIdByUser_IdAndStateAndCategory_Id() {
		System.out.println(bookService.findCreateTimeByUser_IdAndStateAndCategory_Id(1, 10, 2, State.SHARE.toString(), 2));
	}
	
	@Test
	public void testfindByCategory_IdAndUser_IdAndState() {
		print(bookRepository.findByCategory_IdAndUser_IdAndState(2, 2, State.EXCHANGE, new PageRequest(0, 10)).getContent());
	}
	
	@Test
	public void testgetCategoryByUserIdAndState() {
		System.out.println(bookRepository.getCategoryByUserIdAndState(2, State.EXCHANGE));
	}
	
	@Test
	public void testgetCategoryIdByUserId() {
		System.out.println(bookRepository.getCategoryByUserId(2));
	}
	
	@Test
	public void testdeleteInRecycleBin() {
		bookService.deleteInRecycleBin(3);
	}
	
	@Test
	public void testbackupInRecycleBin() {
		bookService.backupInRecycleBin(3);
	}
	
	@Test
	public void testGetCountByUserIdAndState() {
		System.out.println(bookRepository.getCountByUserIdAndState(2, State.RECYCLEBIN));
	}
	
	@Test
	public void testGetCountByUserId() {
		System.out.println(bookRepository.getCountByUserId(2, Arrays.asList(State.RECYCLEBIN)));
		System.out.println(bookRepository.getCountByUserId(2, Arrays.asList(State.RECYCLEBIN)));
	}
	
	@Test
	public void testFindCreateTimeById() {
		System.out.println(bookService.findCreateTimeByUser_IdAndState(1, 2 , 2, "EXCHANGE"));
	}
	
	@Test
	public void testfindCreateTimeById() {
		System.out.println(bookRepository.findCreateTimeById(Arrays.asList(119,47)));
	}
	
	@Test
	public void testfindCreateTimeByUser_IdAndState() {
		System.out.println(bookRepository.findIdByUser_IdAndState(2, "EXCHANGE", 0, 2));
	}
	
	@Test
	public void testgetPageByStateAndUserId(){
		User user = new User();
		user.setId(2);
		Page<Book> page = bookService.getPageByStateAndUserId(1, 2, State.EXCHANGE, user);
		print(page.getContent());
	}
	
	@Test
	public void testfindByStateIn()  {
		Pageable pageable = new PageRequest(0, 10);
		print(bookRepository.findByStateIn(Arrays.asList(State.RELEASE, State.EXCHANGE), pageable).getContent());
	}
	
	@Test
	public void testFindByCategory_IdIn() {
		Pageable pageable = new PageRequest(0, 10);
		print(bookRepository.findByCategory_IdInAndStateIn(Arrays.asList(new Integer[]{2,3,4,5}), Arrays.asList(State.EXCHANGE, State.SHARE), pageable).getContent());
	}
	
	@Test
	public void testfindTop8ByOrderByCollectionDescIdDesc() {
		print(bookService.findTop8ByOrderByCollectionDescIdDesc());
	}
	
	@Test
	public void testFindTop8ByOrderByCreateTimeDesc() {
		print(bookService.findTop8ByOrderByCreateTimeDesc());
	}
	
	@Test
	public void testGetPageByParams() {
		Page<Book> page = bookService.getPageByParams(1, 5, "$-Android-Android");
		print(page.getContent());
	}
	
	@Test
	public void testGetPageByStateAndUserId() {
		User user = new User();
		user.setId(1);
		Page<Book> page = bookService.getPageByStateAndUserId(1, 5, State.RELEASE, user);
		print(page.getContent());
	}
	
	@Test
	public void testGetPage() {
		Page<Book> page = bookService.getPage(1, 5);
		print(page.getContent());
	}
	 private void print(List<Book> books) {
    	for(Book book : books) {
    		System.out.println(book.getName() + "--" + book.getId());
    	}
    }
	    
	@Test
	public void testUpdate() {
		Book book = bookRepository.findOne(3);
		book.setState(State.RELEASE);
		bookService.save(book);
	}
	
	@Test
	public void testDelete(){
		bookService.delete(3);
	}
	
	@Test
	public void testAdd() {
			for(int i=0; i < 10; i++) {
				Book book = new Book();
				book.setAuthor("郑娅峰");
				Category category = new Category();
				category.setId(3);
				book.setLanguage(Language.CHINESE);
				book.setCategory(category);
				book.setIssueTime(new Date());
				book.setName("网页设计与开发");
				book.setPress("清华大学出版社");
				book.setSummary("HTML、CSS、JavaScript实例教程");
				book.setVersion("第二版");
				
				Style style = new Style("1234.jpg");
				style.setBook(book);
				book.getStyles().add(style);
				bookService.save(book);
			}
	}
    
	@Test
	public void testDataSource() throws SQLException {
		System.out.println(dataSource.getConnection());
	}

}
