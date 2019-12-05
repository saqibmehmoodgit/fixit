package com.fixit.domain.vo;
import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fixit.utility.FixitException;

@Entity
@NamedQueries({ 
	@NamedQuery(name="findVerificationByUserId",query="select  myVerification from Verification myVerification where myVerification.userId=?1"),
	@NamedQuery(name="deleteVerificationByUserId",query="delete from Verification myVerification where myVerification.userId=?1"),
	@NamedQuery(name="findVerificationByHashCode",query="select  myVerification from Verification myVerification where myVerification.hashCode=?1 AND myVerification.createdAt>?2")
	
})
@Table(catalog = "fixit",  name = "verification")

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "Verification")
public class Verification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ver_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer verId;
	
	
	@Column(name = "user_id", length = 11 )
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer userId;
	
	
	
	@Column(name = "hash_code" )
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hashCode;
	
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name = "created_at" ,nullable = false, updatable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at",nullable = false, insertable = false, updatable = false )
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;
	
	
	
	
	
	
	public Integer getVerId() {
		return verId;
	}

	public void setVerId(Integer verId) {
		this.verId = verId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
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

	public void copy(Verification that) {
		setVerId(that.getVerId());
		setUserId(that.getUserId());
		setHashCode(that.getHashCode());
		setUpdatedAt(that.getUpdatedAt());
		setCreatedAt(that.getCreatedAt());
	}
	
	public String toString() {

		StringBuilder buffer = new StringBuilder();
        buffer.append("verId=[").append(verId).append("] ");
		buffer.append("userId=[").append(userId).append("] ");
		buffer.append("hashCode=[").append(hashCode).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
		

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((verId == null) ? 0 : verId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof CategoryType))
			return false;
		CategoryType equalCheck = (CategoryType) obj;
		if ((verId == null && equalCheck.catId != null)
				|| (verId != null && equalCheck.catId == null))
			return false;
		if (verId != null && !verId.equals(equalCheck.catId))
			return false;
		return true;
	}
	 @PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }

}
