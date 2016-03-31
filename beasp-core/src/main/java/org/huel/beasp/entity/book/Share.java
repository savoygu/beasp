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
 * 分享
 * 
 * @author 001
 *
 */
@Table(name="beasp_share")
@Entity
public class Share {
	/** 标识id **/
	private Integer id;
	/** 要分享的书籍  **/
	private Book target;
	/** 分享者 **/
	private User user;
	/** 分享申请时间 **/
	private Date applyTime = new Date();
	/** 分享审核时间 **/
	private Date confirmTime = new Date();
	/** 分享结果：0代表分享中(未审核)，1代表分享成功，2代表分享失败 **/
	private int result = 0;
	/** 分享状态 **/
	private State state = State.SHAREING;

	public Share() {
	}
	
	public Share(Book target, User user) {
		super();
		this.target = target;
		this.user = user;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="TARGET_ID")
	public Book getTarget() {
		return target;
	}

	public void setTarget(Book target) {
		this.target = target;
	}
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	//分享中，已分享，分享失败	
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
		Share other = (Share) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
