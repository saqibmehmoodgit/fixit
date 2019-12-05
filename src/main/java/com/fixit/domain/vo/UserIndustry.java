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

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({
	@NamedQuery(name = "findUserIndustryByUserId", query = "select myUserIndustry from UserIndustry myUserIndustry WHERE myUserIndustry.user.userId = ?1"),
	@NamedQuery(name = "findUserIndustryByUserIdandIndstId", query = "select myUserIndustry from UserIndustry myUserIndustry WHERE myUserIndustry.user.userId = ?1 and myUserIndustry.industryType.indstId =?2 "),
	@NamedQuery(name = "deleteUserIndustry", query = "delete  from UserIndustry myUserIndustry WHERE myUserIndustry.user.userId = ?1 and myUserIndustry.industryType.indstId NOT IN (:indstIds)"),
	@NamedQuery(name = "deleteUserIndustryBasedOnUserId", query = "delete  from UserIndustry myUserIndustry WHERE myUserIndustry.user.userId = ?1 "),
	@NamedQuery(name = "deleteUserIndustryByUserIdandIndstId", query = "delete  from UserIndustry myUserIndustry WHERE myUserIndustry.user.userId = ?1 AND myUserIndustry.industryType.indstId =?2 ")
})

@Table(catalog = "fixit", name = "user_indst")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "UserIndustry")
public class UserIndustry implements Serializable{

	 
	private static final long serialVersionUID = 1L;

	@Column(name = "user_indst_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer userIndstId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "user_id", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("UserIndustry-User")
	User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "indst_id", referencedColumnName = "indst_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("UserIndustry-IndustryType")
	IndustryType industryType;
	
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
	
	
	
	
	
	public Integer getUserIndstId() {
		return userIndstId;
	}

	public void setUserIndstId(Integer userIndstId) {
		this.userIndstId = userIndstId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public IndustryType getIndustryType() {
		return industryType;
	}

	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
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

	public void copy(UserIndustry that) {
		setUserIndstId(that.getUserIndstId());
		setUser(that.getUser());
		setUpdatedAt(that.getUpdatedAt());
		setCreatedAt(that.getCreatedAt());
		setIndustryType(that.getIndustryType());
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("userIndstId=[").append(userIndstId).append("] ");
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((userIndstId == null) ? 0 : userIndstId.hashCode()));
		return result;
	}

	@PrePersist
	private void prePersist() {
		this.createdAt = Calendar.getInstance();
	}

	
	
}
