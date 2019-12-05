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
@NamedQueries({ @NamedQuery(name = "deleteUserCategory", query = "delete  from UserCategory myUserCategory WHERE myUserCategory.user.userId = ?1 and myUserCategory.categoryType.catId NOT IN (:catIds)"),
	@NamedQuery(name = "findUserCategoryByUserIdandcatId", query = "select myUserCategory from UserCategory myUserCategory WHERE myUserCategory.user.userId = ?1 and myUserCategory.categoryType.catId =?2 "),
	@NamedQuery(name = "findUserCategoryByUserId", query = "select myUserCategory from UserCategory myUserCategory WHERE myUserCategory.user.userId = ?1"),
	@NamedQuery(name = "deleteUserCategoryBasedOnUserId", query = "delete  from UserCategory myUserCategory WHERE myUserCategory.user.userId = ?1 "),
	@NamedQuery(name = "deleteUserCategoryBasedOnUserIdandCatId", query = "delete  from UserCategory myUserCategory WHERE myUserCategory.user.userId = ?1 AND myUserCategory.categoryType.catId =?2 ")

	
})
@Table(catalog = "fixit", name = "user_cat")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "UserCategory")
public class UserCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(name = "user_cat_id", length = 11, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer userCatId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "user_id", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("FixerCategory-User")
	User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "cat_id", referencedColumnName = "cat_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("FixerCat-CategoryType")
	CategoryType categoryType;
	

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
	public Integer getUserCatId() {
		return userCatId;
	}

	public void setUserCatId(Integer fixerCatId) {
		this.userCatId = fixerCatId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
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
	public void copy(UserCategory that) {
		setUserCatId(that.getUserCatId());
		setUser(that.getUser());
		setCategoryType(that.getCategoryType());
		
	}

	public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("userCatId=[").append(userCatId).append("] ");
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((userCatId == null) ? 0 : userCatId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof UserCategory))
			return false;
		UserCategory equalCheck = (UserCategory) obj;
		if ((userCatId== null && equalCheck.userCatId != null)
				|| (userCatId != null && equalCheck.userCatId == null))
			return false;
		if (userCatId != null && !userCatId.equals(equalCheck.userCatId))
			return false;
		return true;
	}

	 @PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }
}
