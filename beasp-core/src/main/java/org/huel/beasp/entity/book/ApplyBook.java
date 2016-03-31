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
 * 求书籍实体
 * 
 * @author 001
 *
 */
@Table(name = "beasp_apply_book")
@Entity
public class ApplyBook {

	/** 标识 **/
	private Integer id;
	/** 所求书籍名称 **/
	private String bookName;
	/** 所求书籍版本 **/
	private String bookVersion;
	/** 所求书籍作者 **/
	private String bookAuthor;
	/** 所求书籍描述 **/
	private String description;
	/** 分享还是交换: 0 代表分享, 1代表交换 **/
	private int shareExchange;
	/** 状态（等待上传，已经上传，等待确认，等待审核，审核通过）：默认等待上传 **/
	private State state = State.WAITUPLOAD;
	/** 需求者 **/
	private User requirer;
	/** 提供者 **/
	private User applyer;
	/** 提供的书籍 **/
	private Book book;
	/**创建时间**/
	private Date createTime = new Date();

	public ApplyBook() {
		super();
	}

	public ApplyBook(String bookName, String bookVersion, String bookAuthor,
			String description) {
		super();
		this.bookName = bookName;
		this.bookVersion = bookVersion;
		this.bookAuthor = bookAuthor;
		this.description = description;
	}

	public ApplyBook(String bookName, String bookVersion, String bookAuthor,
			String description, int shareExchange) {
		super();
		this.bookName = bookName;
		this.bookVersion = bookVersion;
		this.bookAuthor = bookAuthor;
		this.description = description;
		this.shareExchange = shareExchange;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length = 20, nullable = false)
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Column(length = 20, nullable = false)
	public String getBookVersion() {
		return bookVersion;
	}

	public void setBookVersion(String bookVersion) {
		this.bookVersion = bookVersion;
	}

	@Column(length = 20, nullable = false)
	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	
	@Column(length = 500, nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getShareExchange() {
		return shareExchange;
	}

	public void setShareExchange(int shareExchange) {
		this.shareExchange = shareExchange;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@ManyToOne
	@JoinColumn(name = "requirer_id")
	public User getRequirer() {
		return requirer;
	}

	public void setRequirer(User requirer) {
		this.requirer = requirer;
	}

	@ManyToOne
	@JoinColumn(name = "applyer_id")
	public User getApplyer() {
		return applyer;
	}

	public void setApplyer(User applyer) {
		this.applyer = applyer;
	}

	@ManyToOne
	@JoinColumn(name = "book_id")
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
		ApplyBook other = (ApplyBook) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
