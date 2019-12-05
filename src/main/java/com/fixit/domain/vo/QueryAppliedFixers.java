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
@NamedQueries({  @NamedQuery(name = "getFixerListBasedOnQueryAndMemberId", query = "select myQueryFixers from QueryAppliedFixers myQueryFixers where myQueryFixers.query.queryId = ?1 and myQueryFixers.userMember.userId = ?2 and myQueryFixers.status= 'A'"),
	@NamedQuery(name = "updateAppliedFixerStatus", query = "update QueryAppliedFixers myQueryFixers SET myQueryFixers.status = ?3 where myQueryFixers.query.queryId = ?2 and myQueryFixers.userFixer.userId = ?1"),
	@NamedQuery(name = "getAppliedQueriescount", query = "select count(myQueryFixers.queryAppliedFixersId) from QueryAppliedFixers myQueryFixers where  myQueryFixers.userFixer.userId = ?1 and myQueryFixers.status= 'A' and myQueryFixers.query.currentStatus = 'O'"),
	@NamedQuery(name = "getAppliedQueries", query = "select myQueryFixers.query from QueryAppliedFixers myQueryFixers where  myQueryFixers.userFixer.userId = ?1 and myQueryFixers.status= 'A'  and myQueryFixers.query.currentStatus = 'O' order by myQueryFixers.createdAt desc"),
	@NamedQuery(name = "getFixerListBasedOnFixerId", query = "select myQueryFixers from QueryAppliedFixers myQueryFixers where myQueryFixers.userFixer.userId = ? "),
    @NamedQuery(name = "deleteAppliedFixerByQueryId", query = "delete  from QueryAppliedFixers queryAppliedFixers WHERE queryAppliedFixers.query.queryId = ?1 "),
    @NamedQuery(name = "deleteAppliedFixerByQueryIdAndStatus", query = "delete  from QueryAppliedFixers queryAppliedFixers WHERE queryAppliedFixers.query.queryId = ?1 and queryAppliedFixers.status= ?2"),

    @NamedQuery(name = "countAppliedFixerByQueryIdAndStatus", query = "select count(queryAppliedFixers.userFixer.userId) from QueryAppliedFixers queryAppliedFixers where queryAppliedFixers.query.queryId = ?1 and queryAppliedFixers.status= ?2")

	
})


@Table(catalog = "fixit", name = "query_applied_fixers")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "QueryAppliedFixers")
public class QueryAppliedFixers implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "query_applied_fixers_id", length = 11, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer queryAppliedFixersId;
	
	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String status;
	
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "fixer_id", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("QueryAppliedFixers-User")
	User userFixer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "member_id", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("QueryAppliedFixers-User")
	User userMember;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "query_id", referencedColumnName = "query_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("QueryAppliedFixers-Query")
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

	public Integer getQueryAppliedFixersId() {
		return queryAppliedFixersId;
	}

	public void setQueryAppliedFixersId(Integer queryAppliedFixersId) {
		this.queryAppliedFixersId = queryAppliedFixersId;
	}

	public User getUserFixer() {
		return userFixer;
	}

	public void setUserFixer(User userFixer) {
		this.userFixer = userFixer;
	}

	public User getUserMember() {
		return userMember;
	}

	public void setUserMember(User userMember) {
		this.userMember = userMember;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void copy(QueryAppliedFixers that) {
		setQueryAppliedFixersId(that.getQueryAppliedFixersId());
		setUserFixer(that.getUserFixer());
		setUserMember(that.getUserMember());
		setQuery(that.getQuery());
		setStatus(that.getStatus());

	}

	public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("queryAppliedFixersId=[").append(queryAppliedFixersId).append("] ");
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((queryAppliedFixersId == null) ? 0 : queryAppliedFixersId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof QueryAppliedFixers))
			return false;
		QueryAppliedFixers equalCheck = (QueryAppliedFixers) obj;
		if ((queryAppliedFixersId== null && equalCheck.queryAppliedFixersId != null)
				|| (queryAppliedFixersId != null && equalCheck.queryAppliedFixersId == null))
			return false;
		if (queryAppliedFixersId != null && !queryAppliedFixersId.equals(equalCheck.queryAppliedFixersId))
			return false;
		return true;
	}

	
	
	
	@PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }

}
