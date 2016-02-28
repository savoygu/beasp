package org.huel.beasp.entity.book;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.huel.beasp.entity.user.User;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name="beasp_book")
@Entity @Indexed(index="book")//标记该表可索引,参数index指定存放索引信息的文件名,路径在主配置文件中指定
@Analyzer(impl=SmartChineseAnalyzer.class)//分词器
public class Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4383943154018973902L;
	/**书籍标识**/
	private Integer id;
	/**书籍名称**/
	private String name;
	/**书籍作者**/
	private String author;
	/**书籍语言**/
	private Language language;
	/**书籍所属类别**/
	private Category category;
	/**书籍版本**/
	private String version;
	/**书籍出版社**/
	private String press;
	/**书籍出版时间**/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date issueTime;
	/**书籍详情**/
	private String summary;
	/**书籍是否交换**/
	private boolean exchange = false;
	/**书籍是否分享**/
	private boolean share = false;
	/**书籍状态**/
	private State state = State.WAITCONFIRM;//默认待审核
	/**书籍起始状态**/
	private int originState;//1.代表发布，2代表交换，3代表分享，4代表待审核:作用回收站记录原来的状态
	/**书籍发布时间**/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime = new Date();
	/** 书籍收藏数量 **/
	private int collection = 0;
	/** 书籍浏览数量 **/
	private int browse = 0; 
	/** 书籍称赞数量 **/
	private int praise = 0;
	/**希望交换的书籍**/
	private Set<Want> wants = new HashSet<Want>();
	
	/**
	 * 添加想要的
	 * @param want
	 */
	public void addWant(Want want) {
		this.wants.add(want);
		want.setBook(this);//由want维护关联关系
	}
	
	@OneToMany(mappedBy="book", cascade={CascadeType.ALL})
	public Set<Want> getWants() {
		return wants;
	}

	public void setWants(Set<Want> wants) {
		this.wants = wants;
	}

	public int getOriginState() {
		return originState;
	}

	public void setOriginState(int originState) {
		this.originState = originState;
	}

	public int getBrowse() {
		return browse;
	}

	public void setBrowse(int browse) {
		this.browse = browse;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}

	public int getCollection() {
		return collection;
	}

	public void setCollection(int collection) {
		this.collection = collection;
	}
	
	/**所属用户**/
	private User user;
	
	/**所有样式**/
	private Set<Style> styles = new HashSet<Style>();
	
	/**
	 * 添加样式到样式集合
	 * @param style
	 */
	public void addBookStyle(Style style) {
		if(!this.styles.contains(style)) {
			this.styles.add(style);
			style.setBook(this);
		}
	}
	
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.PERSIST}, mappedBy="book") @ContainedIn//嵌入和关联实体映射 
	public Set<Style> getStyles() {
		return styles;
	}
	public void setStyles(Set<Style> styles) {
		this.styles = styles;
	}
	
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="USER_ID")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) @DocumentId(name="bookId")//以字段id作为文档id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(length=20, nullable=false) @Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES, name="bookName", boost=@Boost(value=2))
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=20, nullable=false) @Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Enumerated(EnumType.STRING) @Column(length=20, nullable=false) @Field(index=Index.NO, analyze=Analyze.NO, store=Store.YES)
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	@JoinColumn(name="CATEGORY_ID") @IndexedEmbedded(depth=1)//嵌入和关联实体映射 
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Column(length=20) @Field(index=Index.NO, analyze=Analyze.NO, store=Store.YES)
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(length=20) @Field(index=Index.NO, analyze=Analyze.NO, store=Store.YES)
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	
	@Temporal(TemporalType.DATE) @Field(index=Index.NO, analyze=Analyze.NO, store=Store.YES)
	public Date getIssueTime() {
		return issueTime;
	}
	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}
	
	@Lob @Basic(fetch=FetchType.LAZY, optional=true) @Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(nullable=false) 
	public boolean getExchange() {
		return exchange;
	}

	public void setExchange(boolean exchange) {
		this.exchange = exchange;
	}

	@Column(nullable=false)
	public boolean getShare() {
		return share;
	}

	public void setShare(boolean share) {
		this.share = share;
	}
	
	@Enumerated(EnumType.STRING) @Column(length=20,nullable=false)
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	@Temporal(TemporalType.TIMESTAMP) @Column(nullable=false)
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
		Book other = (Book) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
