package com.fixit.domain.vo;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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


@Entity
@NamedQueries({
	@NamedQuery(name = "findDeclineQuery", query = "select myUserDecline from UserDecline myUserDecline WHERE myUserDecline.userId = ?1 and myUserDecline.queryId =?2 "),
	@NamedQuery(name = "findDeclineQueryByUserId", query = "select myUserDecline from UserDecline myUserDecline WHERE myUserDecline.userId = ?1 "),
	@NamedQuery(name = "deleteDeclineQueryBasedOnUserID", query = "delete from UserDecline myUserDecline WHERE myUserDecline.userId = ?1 ")
})

@Table(catalog = "fixit", name = "user_decline")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "UserDecline")
public class UserDecline {

	private static final long serialVersionUID = 1L;
	@Column(name = "decline_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer declineId;
	
	@Column(name = "user_id", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer userId;
	
	@Column(name = "query_id", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer queryId;
	
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
	
	
	
	
	
	public Integer getDeclineId() {
		return declineId;
	}

	public void setDeclineId(Integer declineId) {
		this.declineId = declineId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getQueryId() {
		return queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
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

	public void copy(UserDecline that) {
		setDeclineId(that.getDeclineId());
		setUserId(that.getUserId());
		setQueryId(that.getQueryId());
		setUpdatedAt(that.getUpdatedAt());
		setCreatedAt(that.getCreatedAt());
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("declineId=[").append(declineId).append("] ");
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((declineId == null) ? 0 : declineId.hashCode()));
		return result;
	}

	@PrePersist
	private void prePersist() {
		this.createdAt = Calendar.getInstance();
	}


}
