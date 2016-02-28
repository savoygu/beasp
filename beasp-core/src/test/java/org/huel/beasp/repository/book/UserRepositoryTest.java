package org.huel.beasp.repository.book;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.huel.beasp.entity.book.Book;
import org.huel.beasp.entity.user.Comment;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.repository.user.CommentRepository;
import org.huel.beasp.repository.user.UserRepository;
import org.huel.beasp.repository.user.VisitorRepository;
import org.huel.beasp.service.user.CommentService;
import org.huel.beasp.service.user.UserService;
import org.huel.beasp.utils.Constants;
import org.huel.beasp.utils.MD5;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserRepositoryTest {
	@Autowired private UserService userService;
	@Autowired private UserRepository userRepository;
	@Autowired private DataSource dataSource;
	@Autowired private VisitorRepository visitorRepository;
	@Autowired private CommentRepository commentRepository;
	@Autowired private CommentService commentService;
	
	@Test
	public void testCommentTime() {
		/**
		 * 书籍评论
		 */
		Page<Comment> comments = 
				commentService.findByBook_Id(1, Constants.COMMENT_PAGE_SIZE_TWELVE_FRONT, 120);
		
		//评论时间
		Map<Integer, String> comment_time = new HashMap<Integer, String>();
		for(Comment comment : comments.getContent()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date nowdate = new Date();
			long time_sub = nowdate.getTime()-comment.getCommentTime().getTime();
			System.out.println(time_sub);
			if(time_sub < 1000*60) {//1分钟之内
				comment_time.put(comment.getId(), time_sub+"秒前");
			} else if(time_sub < 1000*60*60){
				comment_time.put(comment.getId(), time_sub/(1000*60)+"分钟前");
			} else if(time_sub < 1000*60*60*24) {
				comment_time.put(comment.getId(), time_sub/(1000*60*60)+"小时前");
			} else if(time_sub < 1000*60*60*24*7) {
				comment_time.put(comment.getId(), time_sub/(1000*60*60*24)+"天前");
			} else {
				comment_time.put(comment.getId(), sdf.format(comment.getCommentTime()));
			}
		}
		System.out.println(comment_time);
	}
	
	@Test
	public void testfindByBook_Id() {
		for(Comment comment : commentService.findByBook_Id(1, 5, 120).getContent()) {
			System.out.println(comment.getContent());
			System.out.println(commentService.findByBook_Id(1, 5, 120).getContent());
		}
	}
	
	@Test
	public void testUpdateSigned() {
		userService.updateSigned("Hello 大家好，我是A-Lin!", 2);
		User user = userService.getById(2);
		System.out.println(user.getSummary());
	}
	
	
	@Test
	public void testfindVisitorByUser_IdAndVisitor_Id() {
		System.out.println(visitorRepository.findVisitorByUser_IdAndVisitor_Id(2, 1));
	}
	
	@Test
	public void testfindCountByUser_IdAndVisitor_Id() {
		System.out.println(visitorRepository.getCountByUser_IdAndVisitor_Id(1, 2));
	}
	
	@Test
	public void testfindByUser_Id() {
		System.out.println(visitorRepository.findByUser_Id(2));
	}
	
	@Test
	public void testfindVisitorByUser_Id() {
		for(User user:visitorRepository.findVisitorByUser_Id(2)) {
			System.out.println(user.getId()+"--"+user.getNickName()+"--"+user.getImage40FullPath());
		}
	}
	
	
	@Test
	public void testGetPageByStateAndUserId() {
		
	}
	
	@Test
	public void testGetPage() {
//		System.out.println(userService.getCountByEmail("gusfzk@126.com"));
//		System.out.println(userService.getByEmail("gusfzk@126.com"));
		System.out.println(userService.getByEmailAndPassword("gusfzk@126.com", "123456"));
	}
	
	@SuppressWarnings("unused")
	private void print(List<Book> books) {
    	for(Book book : books) {
    		System.out.println(book.getName());
    	}
    }
	    
	@Test
	public void testUpdate() {
	}
	
	@Test
	public void testDelete(){
	}
	
	@Test
	public void testAdd() {
		for(int i = 1; i < 11; i++) {
			User user =  new User();
			user.setCity("郑州市");
			user.setEmail("gusfzk@126.com");
			user.setNickName("A-Lin");
			user.setPassword("123");
			user.setPhone("18203642007");
			user.setProvince("河南省");
			user.setSchool("河南财经政法大学");
			user.setSummary("我最爱的歌手是???");
			user.setUserName("zhangsan");
			/*userService.save(user);*/
		}
	}
    
	@Test
	public void testMD5() {
		String password = "gusfzk@126.com";
		System.out.println("原始：" + password);  
        System.out.println("MD5后：" + MD5.MD5Encode(password));  
        System.out.println("加密的：" + MD5.convertMD5(password));  
        System.out.println("解密的：" + MD5.convertMD5(MD5.convertMD5(password)));   
	}
	
	/**
	 * Base64 加密
	 */
	@Test
	public void testBase64Encode() {
		String email = "gusfzk@126.com";
		String code = new String(Base64.encodeBase64(email.getBytes()));
		System.out.println(code);
	}
	/**
	 * Base64 解密
	 */
	@Test
	public void testBase64Decode() {
		String email = 	new String(Base64.decodeBase64("Z3VzZnprQDEyNi5jb20=".getBytes()));	
		System.out.println(email);
	}
	
	@Test
	public void testEmailNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowdate = sdf.format(new Date());
		String result = new String(Base64.encodeBase64(("gusfzk@126.com"+nowdate).getBytes()));
		System.out.println(result);
	}
	
	@Test
	public void testDataSource() throws SQLException {
		System.out.println(dataSource.getConnection());
	}

}
