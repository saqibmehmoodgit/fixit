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
@NamedQueries({  @NamedQuery(name = "getQueryFixersBasedOnQueryId", query = "select myQueryFixers from QueryFixers myQueryFixers where myQueryFixers.query.queryId = ?1"),
	 			@NamedQuery(name = "deleteQueryFixersBasedOnQueryId", query = "delete from  QueryFixers myQueryFixers where myQueryFixers.query.queryId=?1 "),
	 			@NamedQuery(name = "findQueryFixersBasedOnQueryIdAndFixerId", query = "select myQueryFixers from QueryFixers myQueryFixers where myQueryFixers.query.queryId = ?1 and myQueryFixers.user.userId=?2"),
})

@Table(catalog = "fixit", name = "query_fixers")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "QueryFixers")
public class QueryFixers implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "query_fixers_id", length = 11, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer queryFixersId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "query_fixers_userid", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("QueryFixers-User")
	User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "query_fixers_queryid", referencedColumnName = "query_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("QueryAudit-Query")
	Query query;

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

	public Integer getQueryFixersId() {
		return queryFixersId;
	}

	public void setQueryFixersId(Integer queryFixersId) {
		this.queryFixersId = queryFixersId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
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
	
	public void copy(QueryFixers that) {
		setQueryFixersId(that.getQueryFixersId());
		setUser(that.getUser());
		setQuery(that.getQuery());

	}

	public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("queryFixersId=[").append(queryFixersId).append("] ");
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((queryFixersId == null) ? 0 : queryFixersId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof QueryFixers))
			return false;
		QueryFixers equalCheck = (QueryFixers) obj;
		if ((queryFixersId== null && equalCheck.queryFixersId != null)
				|| (queryFixersId != null && equalCheck.queryFixersId == null))
			return false;
		if (queryFixersId != null && !queryFixersId.equals(equalCheck.queryFixersId))
			return false;
		return true;
	}

	
	
	
	@PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }
}
