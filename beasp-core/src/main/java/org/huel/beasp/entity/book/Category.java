package org.huel.beasp.entity.book;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * 书籍类别实体
 * @author 001
 *
 */
@Cacheable
@Table(name="beasp_category")
@Entity @Indexed(index="category")
public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2202255976686122874L;
	/**类别标识**/
	private Integer id;
	/**类别名称**/
	private String name;
	/**类别描述**/
	private String describe;
	
	/**父类别**/
	private Category parent;
	/**子类别**/
	private Set<Category> children;
	/**书籍集**/
	private Set<Book> books = new HashSet<Book>();
	
	public Category() {
		super();
	}

	public Category(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@OneToMany(mappedBy="category")
	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO) @DocumentId(name="categoryId")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length=20, nullable=false) @Field(index=Index.YES, store=Store.YES, name="categoryName")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Lob @Column(name="describe_") @Basic(fetch=FetchType.LAZY, optional=true)
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@ManyToOne(cascade={CascadeType.REFRESH}, optional=true, fetch=FetchType.LAZY) 
	@JoinColumn(name="CATEGORY_ID")
	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy="parent", cascade={CascadeType.REFRESH, CascadeType.REMOVE})
	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
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
		Category other = (Category) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
