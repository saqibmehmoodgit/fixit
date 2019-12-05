package com.fixit.domain.vo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@NamedQueries({ @NamedQuery(name = "findUserCreditPointsByUserId", query = "select myUserCredits.points from UserCredit myUserCredits where myUserCredits.user.userId = ?1"),
	@NamedQuery(name = "updateUserCreditPointsByUserId", query = "update UserCredit myUserCredits set myUserCredits.points=?2 where myUserCredits.user.userId=?1 "),
	@NamedQuery(name = "getCreditCountsByUserId", query = "select count(*) from UserCredit myUserCredits where myUserCredits.user.userId = ?1"),
	@NamedQuery(name = "deleteUserCreditPoints", query = "delete from UserCredit myUserCredits where myUserCredits.user.userId=?1 "),


})

@Table(catalog = "fixit", name = "user_credit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "UserCredit")
public class UserCredit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "user_credit_id", length = 11, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer userCreditId;
	

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "user_id", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("UserCredit-User")
	User user;
	
	@Column(name = "points")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Long points;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at" , nullable = false, updatable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at" , nullable = false, insertable = false, updatable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;

	public Integer getUserCreditId() {
		return userCreditId;
	}

	public void setUserCreditId(Integer userCreditId) {
		this.userCreditId = userCreditId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public void copy(UserCredit that) {
		setUserCreditId(that.getUserCreditId());
		setUser(that.getUser());
		setPoints(that.getPoints());
	}

	public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("userCreditId=[").append(userCreditId).append("] ");
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((userCreditId == null) ? 0 : userCreditId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof UserCredit))
			return false;
		UserCredit equalCheck = (UserCredit) obj;
		if ((userCreditId== null && equalCheck.userCreditId != null)
				|| (userCreditId != null && equalCheck.userCreditId == null))
			return false;
		if (userCreditId != null && !userCreditId.equals(equalCheck.userCreditId))
			return false;
		return true;
	}

	
	
	
	@PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }
}
