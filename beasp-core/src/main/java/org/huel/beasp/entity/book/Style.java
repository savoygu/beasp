package org.huel.beasp.entity.book;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

/**
 * 书籍样式
 * 
 * @author 001
 *
 */
@Table(name = "beasp_book_style")
@Entity @Indexed(index="style")
public class Style implements Serializable{
	/**
	 * 
	 */ 
	private static final long serialVersionUID = -4088679399238464135L;
	/** 样式标识 **/
	private Integer id;
	/** 样式名称 **/
	/*private String name;*/
	/** 图片 **/
	private String imageName;
	/** 是否可见 **/
	private boolean visible = true;
	/** 是否选中 **/
	private boolean choice = true;
	/** 创建时间 **/
	private Date createTime = new Date();
	/** 所属书籍 **/
	private Book book;

	public Style() {
		super();
	}

	public Style(String imageName) {
		super();
		this.imageName = imageName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)  @DocumentId(name="styleId")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*@Column(length = 20, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}*/

	@Transient
	public String getImageFullPath() {
		return "/resources/images/book/" + this.getBook().getCategory().getId()
				+ "/" + this.getBook().getId() + "/prototype/" + this.imageName;
	}

	@Transient
	public String getImage140FullPath() {
		return "/resources/images/book/" + this.getBook().getCategory().getId() + "/"
				+ this.getBook().getId() + "/140x/" + this.imageName;
	}

	@Column(length = 40, nullable = false) @Field(index=Index.NO, analyze=Analyze.NO, store=Store.YES)
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Column(nullable = false)
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Column(nullable = false)
	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "BOOK_ID")  @IndexedEmbedded(depth=1)
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
		Style other = (Style) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
