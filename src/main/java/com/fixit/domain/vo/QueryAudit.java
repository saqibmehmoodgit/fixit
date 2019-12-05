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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({
	@NamedQuery(name = "findUnresolvedIssueByFIxerId", query = "select count(myQueryAudit.status) from QueryAudit myQueryAudit where  myQueryAudit.status='UI' and  myQueryAudit.fixerId = ?1 "),
	@NamedQuery(name = "findAllQueryAudit", query = "select myQueryAudit  from QueryAudit myQueryAudit order by auditDate DESC "),
	@NamedQuery(name = "findAllQueryAuditCount", query = "select  count(myQueryAudit)  from QueryAudit myQueryAudit order by auditDate DESC "),
	@NamedQuery(name = "deleteQueryAuditByQueryId", query = "delete  from QueryAudit myQueryAudit WHERE myQueryAudit.query.queryId = ?1 "),
	@NamedQuery(name = "getMessagesByQueryId", query = "select myQueryAudit from QueryAudit myQueryAudit where myQueryAudit.query.queryId = ?1 and (myQueryAudit.fixerId = ?2 ) order by auditDate asc"),
	@NamedQuery(name = "getQueryAuditBasedOnStatus", query = "select myQueryAudit from QueryAudit myQueryAudit where myQueryAudit.query.queryId = ?1 and myQueryAudit.customerId = ?2 and myQueryAudit.fixerId = ?3 and myQueryAudit.status=?4"),
	@NamedQuery(name = "getQueryAuditBasedOnStatusAndMsgFrom", query = "select myQueryAudit from QueryAudit myQueryAudit where myQueryAudit.query.queryId = ?1 and myQueryAudit.customerId = ?2 and myQueryAudit.fixerId = ?3 and myQueryAudit.status=?4 and myQueryAudit.msgFrom=?5"),
	@NamedQuery(name = "findAllQueryCountByFixerId", query = "select count(myQueryAudit) from QueryAudit myQueryAudit where  myQueryAudit.fixerId = ?1 AND myQueryAudit.status = ?2"),
	@NamedQuery(name = "findAllQueryFixerIdAndSatus", query = "select myQueryAudit from QueryAudit myQueryAudit where  myQueryAudit.fixerId = ?1 AND myQueryAudit.status = ?2"),
	@NamedQuery(name = "findAllQueryUserIdAndSatus", query = "select count(myQueryAudit) from QueryAudit myQueryAudit where  myQueryAudit.customerId = ?1 AND myQueryAudit.status = ?2"),
	@NamedQuery(name = "updateUnreadMessages", query = "update QueryAudit myQueryAudit set myQueryAudit.readStatus = 'R' where myQueryAudit.query.queryId = ?1 and (myQueryAudit.fixerId = ?2 ) and myQueryAudit.msgFrom = ?3"),
	@NamedQuery(name = "getUnreadMessagesCountByQueryId", query = "select count(myQueryAudit) from QueryAudit myQueryAudit where myQueryAudit.query.queryId = ?1 and (myQueryAudit.fixerId = ?2 ) and myQueryAudit.readStatus = 'UR'and myQueryAudit.msgFrom = ?3 order by auditDate asc")
	
	
    
})

@Table(catalog = "fixit", name = "query_audit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "QueryAudit")
public class QueryAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "audit_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue
	@XmlElement
	Integer auditId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "query_id", referencedColumnName = "query_id") })
	@XmlTransient
	@JsonBackReference("QueryAudit-Query")
	Query query;

	@Column(name = "customer_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer customerId;

	@Column(name = "fixer_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer fixerId;

	@Column(name = "message")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String message;

	@Column(name = "msg_from")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String msgFrom;

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String status;
	
	@Column(name = "read_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String readStatus = "UR";

	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "audit_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar auditDate;
	
	
	@Transient
	String auditTime;
	
	
	@Transient
	String documentFilename;
	
	

	public String getDocumentFilename() {
		return documentFilename;
	}

	public void setDocumentFilename(String documentFilename) {
		this.documentFilename = documentFilename;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMsgFrom() {
		return msgFrom;
	}

	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	public Calendar getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Calendar auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(QueryAudit that) {
		setAuditId(that.getAuditId());
		setQuery(that.getQuery());
		setCustomerId(that.getCustomerId());
		setFixerId(that.getFixerId());
		setMessage(that.getMessage());
		setMsgFrom(that.getMsgFrom());
		setStatus(that.getStatus());
		setAuditDate(that.getAuditDate());
		setReadStatus(that.getReadStatus());

	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("auditID=[").append(auditId).append("] ");
		buffer.append("customerId=[").append(customerId).append("] ");
		buffer.append("fixerId=[").append(fixerId).append("] ");
		buffer.append("message=[").append(message).append("] ");
		buffer.append("msgFrom=[").append(msgFrom).append("] ");
		buffer.append("status=[").append(status).append("] ");
		buffer.append("auditDate=[").append(auditDate).append("] ");
		buffer.append("readStatus=[").append(readStatus).append("] ");

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((auditId == null) ? 0 : auditId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof QueryAudit))
			return false;
		QueryAudit equalCheck = (QueryAudit) obj;
		if ((auditId == null && equalCheck.auditId != null)
				|| (auditId != null && equalCheck.auditId == null))
			return false;
		if (auditId != null && !auditId.equals(equalCheck.auditId))
			return false;
		return true;
	}
	
	
	
	
}
