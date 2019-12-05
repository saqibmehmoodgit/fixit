package com.fixit.domain.vo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@NamedQueries({@NamedQuery(name = "findQueryExpireByFixerIdAndhashCode", query = "select myQueryExpire from QueryExpire myQueryExpire where myQueryExpire.fixerId = ?1 and myQueryExpire.hashcode=?2"),
               @NamedQuery(name = "deleteQueryExpireById", query = "delete from QueryExpire myQueryExpire where myQueryExpire.id = ?1")
	          

})
@Table(catalog = "fixit", name = "query_expire")
@SqlResultSetMappings({
	@SqlResultSetMapping(name = "expireQueryMapping", entities = { @EntityResult(entityClass = QueryExpire.class, fields = {
}) }),
})


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "QueryExpire")
public class QueryExpire implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "query_expire_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer queryExpireId;

	@Column(name = "query_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer queryId;

	@Column(name = "member_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer memberId;

	@Column(name = "fixer_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer fixerId;

	@Column(name = "hashcode")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hashcode;

	@Column(name = "internal_url")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String internalUrl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expires_on")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar expiresOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", updatable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;

	public Integer getQueryExpireId() {
		return queryExpireId;
	}

	public void setQueryExpireId(Integer queryExpireId) {
		this.queryExpireId = queryExpireId;
	}

	public Integer getQueryId() {
		return queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public String getInternalUrl() {
		return internalUrl;
	}

	public void setInternalUrl(String internalUrl) {
		this.internalUrl = internalUrl;
	}

	public Calendar getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Calendar expiresOn) {
		this.expiresOn = expiresOn;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
	

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(QueryExpire that) {
		setQueryExpireId(that.getQueryExpireId());
		setQueryId(that.getQueryId());
		setMemberId(that.getMemberId());
		setFixerId(that.getFixerId());
		setHashcode(that.getHashcode());
		setExpiresOn(that.getExpiresOn());
		setInternalUrl(that.getInternalUrl());
		setCreatedAt(that.getCreatedAt());
		
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();
		
		buffer.append("queryExpireId=[").append(queryExpireId).append("] ");
		buffer.append("queryId=[").append(queryId).append("] ");
		buffer.append("memberId=[").append(memberId).append("] ");
		buffer.append("fixerId=[").append(fixerId).append("] ");
		buffer.append("hashcode=[").append(hashcode).append("] ");
		buffer.append("expiresOn=[").append(expiresOn).append("] ");
		buffer.append("internalUrl=[").append(internalUrl).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		
		return buffer.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((queryExpireId == null) ? 0 : queryExpireId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof QueryExpire))
			return false;
		QueryExpire equalCheck = (QueryExpire) obj;
		if ((queryExpireId == null && equalCheck.queryExpireId != null)
				|| (queryExpireId != null && equalCheck.queryExpireId == null))
			return false;
		if (queryExpireId != null && !queryExpireId.equals(equalCheck.queryExpireId))
			return false;
		return true;
	}
	 @PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }
}
