package org.huel.beasp.entity.book;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 想要交换的书籍
 * 
 * @author 001
 *
 */
@Table(name="beasp_want")
@Entity
public class Want {

	/** 交换标识 **/
	private Integer id;
	/** 书籍名称 **/
	private String name;
	/** 书籍作者 **/
	private String author;
	/** 书籍版本 **/
	private String version;
	/** 所属书籍 **/
	private Book book;
	
	public Want() {
		super();
	}

	public Want(String name, String author, String version) {
		super();
		this.name = name;
		this.author = author;
		this.version = version;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length=40, nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length=40, nullable=false)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(length=20, nullable=false)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="BOOK_ID")
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? super.hashCode() : id.hashCode());
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
		Want other = (Want) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
