package org.huel.beasp.entity.book;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.huel.beasp.entity.user.User;
/**
 * 书籍用户状态表
 * @author 001
 *
 */
@Table(name="beasp_book_user")
@Entity
public class BookUser {
	/** 标识 **/
	private Integer id;
	/**书籍**/
	private Book book;
	/**用户**/
	private User user;
	/**状态(收藏COLLECTION，浏览BROWSE，点赞PRAISE，我自己SELF)**/
	private State state;
	/**创建时间**/
	private Date createTime =  new Date();//浏览
	
	public BookUser() {
		super();
	}
	public BookUser(Book book, User user) {
		super();
		this.book = book;
		this.user = user;
	}
	
	public BookUser(Book book, User user, State state) {
		super();
		this.book = book;
		this.user = user;
		this.state = state;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="BOOK_ID")
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Enumerated(EnumType.STRING) @Column(length=20,nullable=false)
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookUser other = (BookUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
