package org.huel.beasp.entity.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 访客
 * @author 001
 *
 */
@Table(name="beasp_visitor")
@Entity
public class Visitor {
	private Integer id;
	private User user;//被访问者
	private User visitor;//访客
	//private Integer visitorId;//访客标识
	//private String imageName;//访客头像
	//private String nickName;//访客昵称
	private Date visitTime = new Date();//访问时间

	public Visitor() {
		super();
	}

	public Visitor(User user, User visitor) {
		super();
		this.user = user;
		this.visitor = visitor;
	}
	
	@Id @GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name="VISITOR_ID")
	public User getVisitor() {
		return visitor;
	}
	public void setVisitor(User visitor) {
		this.visitor = visitor;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
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
		Visitor other = (Visitor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
