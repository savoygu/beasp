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
 * 交换
 * 
 * @author 001
 *
 */
@Table(name = "beasp_exchange")
@Entity
public class Exchange {

	/** 标识id **/
	private Integer id;
	/** 替换 **/
	private Book replace;
	/** 目标(被替换的) **/
	private Book target;
	/** 交换者 **/
	private User exchanger;
	/** 被交换者(被替换的) **/
	private User user;
	/** 分享与交换申请时间 **/
	private Date applyTime = new Date();
	/*** 分享与交换审核时间 **/
	private Date confirmTime = new Date();
	/** 分享结果：0代表待分享(未审核)，1代表分享成功，2代表分享失败 **/
	private int result = 0;
	/** 分享状态 **/
	private State state = State.EXCHANGEING;

	public Exchange() {
	}

	public Exchange(Book replace, Book target) {
		super();
		this.replace = replace;
		this.target = target;
	}
	
	public Exchange(Book replace, Book target, User exchanger, User user) {
		super();
		this.replace = replace;
		this.target = target;
		this.exchanger = exchanger;
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
	@JoinColumn(name="REPLACE_ID")
	public Book getReplace() {
		return replace;
	}

	public void setReplace(Book replace) {
		this.replace = replace;
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
	@JoinColumn(name="EXCHANGER_ID")
	public User getExchanger() {
		return exchanger;
	}

	public void setExchanger(User exchanger) {
		this.exchanger = exchanger;
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
	
	//交换中，已交换，交换失败	
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
		Exchange other = (Exchange) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
