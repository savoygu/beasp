package org.huel.beasp.entity.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.huel.beasp.entity.book.Book;

/*@SecondaryTables({
	@SecondaryTable(name="beasp_address", pkJoinColumns={
			@PrimaryKeyJoinColumn(name="USER_ID", referencedColumnName="id")
	})
})*/
@Table(name="beasp_user")
@Entity
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3988156642318324046L;
	/**用户标识**/
	private Integer id;
	/**用户名**/
	private String userName;
	/**密码**/
	private String password;//MD5加密
	/**昵称**/
	private String nickName;
	/**性别**/
	private Gender gender = Gender.SECRET;
	/**电话号码**/
	private String phone;
	/**电子邮件**/
	private String email;
	/**所在学校**/
	private String school;
	
	/**所在省**/
	private String province;
	/**所在市**/
	private String city;
	/**所在区**/
//	private String area;
	
	/**注册时间**/
	private Date createTime = new Date();
	
	/**用户状态,0,1,2分别代表，可用，锁定，删除**/
	private int status = 0;
	
	/**个人简介**/
	private String summary;
	
	/**所属书籍**/
	private Set<Book> books = new HashSet<Book>();
	
	/**我的头像**/
	private String photoName;
	
	/**邮箱验证,0,1分别代表，为验证，已验证**/
	private int verifyEmail = 0;
	
	public int getVerifyEmail() {
		return verifyEmail;
	}

	public void setVerifyEmail(int verifyEmail) {
		this.verifyEmail = verifyEmail;
	}

	@Transient
	public String getImageFullPath() {
		return "/resources/images/user/" + this.id + "/prototype/" + this.photoName;
	}

	@Transient
	public String getImage40FullPath() {
		return "/resources/images/user/" + this.id + "/40x/" + this.photoName;
	}
	
	@Transient
	public String getDefaultImage40Path() {
		return "/resources/images/user/default/40x/5458505c00018e9202200220-40-40.jpg";
	}
	
	@Transient
	public String getDefaultImageFullPath() {
		return "/resources/images/user/default/prototype/5458505c00018e9202200220-200-200.jpg";
	}

	@Column(length = 40)
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
	@OneToMany(mappedBy="user")
	public Set<Book> getBooks() {
		return books;
	}
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(length=20, nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(length=32, nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(length=20, nullable=false)
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Enumerated(EnumType.STRING) @Column(length=20, nullable=false)
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@Column(length=11, nullable=false)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(length=40, nullable=false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length=20, nullable=false)
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	
	
	
	/*@Column(length=10, nullable=false, table="beasp_address")
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(length=10, nullable=false, table="beasp_address")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}*/
	
	@Column(length=20, nullable=false)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(length=20, nullable=false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(length=100)
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password="
				+ password + ", nickName=" + nickName + ", gender=" + gender
				+ ", phone=" + phone + ", email=" + email + ", school="
				+ school + ", province=" + province + ", city=" + city
				+ ", createTime=" + createTime + ", status=" + status
				+ ", summary=" + summary + ", books=" + books + ", photoName="
				+ photoName + "]";
	}
	
}
