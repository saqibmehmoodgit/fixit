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
@NamedQueries({@NamedQuery(name = "deleteUserAccountingDataBasedOnUserId", query = "delete from UsersAccounting myUsersAccounting where myUsersAccounting.user.userId=?1 "),
	@NamedQuery(name = "getDataFromUserAccountingBasedOnUserID", query = "select myUsersAccounting from UsersAccounting myUsersAccounting where myUsersAccounting.user.userId=?1 "),
	@NamedQuery(name = "findUserAccountingByUserIdAndMonthYear", query = "select myUsersAccounting from UsersAccounting myUsersAccounting where myUsersAccounting.user.userId = ?1 and YEAR(myUsersAccounting.updatedAt) = ?2 and MONTH(myUsersAccounting.updatedAt) = ?3 ")


})
@Table(catalog = "fixit", name = "users_accounting")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "UsersAccounting")
public class UsersAccounting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "user_account_id", length = 11, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer userAccountId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "user_id", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("UserAccounting-User")
	User user;

	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String type;

	@Column(name = "amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Double amount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time", nullable = false, updatable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar time;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;

	public Integer getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Integer userAccountId) {
		this.userAccountId = userAccountId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
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

	public void copy(UsersAccounting that) {
		setUserAccountId(that.getUserAccountId());
		setUser(that.getUser());
		setType(that.getType());
		setTime(that.getTime());
		setAmount(that.getAmount());
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("userAccountId=[").append(userAccountId).append("] ");
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((userAccountId == null) ? 0
				: userAccountId.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof UsersAccounting))
			return false;
		UsersAccounting equalCheck = (UsersAccounting) obj;
		if ((userAccountId == null && equalCheck.userAccountId != null)
				|| (userAccountId != null && equalCheck.userAccountId == null))
			return false;
		if (userAccountId != null
				&& !userAccountId.equals(equalCheck.userAccountId))
			return false;
		return true;
	}

	@PrePersist
	private void prePersist() {
		this.createdAt = Calendar.getInstance();
	}
}
