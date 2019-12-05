package com.fixit.domain.vo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.fixit.utility.TimeDiffUtility;


@Entity
@NamedQueries({
	@NamedQuery(name = "findDocumentsByQueryId", query = "select myQueryFiles from QueryFiles myQueryFiles where myQueryFiles.query.queryId =?1 and myQueryFiles.fileType='D' and myQueryFiles.fileUniqueCode IS NOT NULL"),
	@NamedQuery(name = "deleteDocumentsByQueryIds", query = "delete  from QueryFiles myQueryFiles WHERE myQueryFiles.query.queryId = ?1 and myQueryFiles.fileType='D' and myQueryFiles.fileName NOT IN (:fileNames)"),
	@NamedQuery(name = "findDocumentsByQueryIdandName", query = "select myQueryFiles from QueryFiles myQueryFiles where myQueryFiles.query.queryId =?1 and myQueryFiles.fileName=?2 and myQueryFiles.fileType='D' "),
	@NamedQuery(name = "deleteAllDocumentsByQueryId", query = "delete  from QueryFiles myQueryFiles WHERE myQueryFiles.query.queryId = ?1 and myQueryFiles.fileType='D' "),
	@NamedQuery(name = "deleteAllDataByQueryId", query = "delete  from QueryFiles myQueryFiles WHERE myQueryFiles.query.queryId = ?1 "),
	
	@NamedQuery(name = "findurlByQueryId", query = "select myQueryFiles from QueryFiles myQueryFiles where myQueryFiles.query.queryId =?1 and myQueryFiles.fileType='L' "),
	@NamedQuery(name = "deleteurlByQueryIds", query = "delete  from QueryFiles myQueryFiles WHERE myQueryFiles.query.queryId = ?1 and myQueryFiles.fileType='L' and myQueryFiles.fileName NOT IN (:urlNames)"),
	@NamedQuery(name = "findurlByQueryIdandName", query = "select myQueryFiles from QueryFiles myQueryFiles where myQueryFiles.query.queryId =?1 and myQueryFiles.fileName=?2 and myQueryFiles.fileType='L' "),
	@NamedQuery(name = "deleteAllurlByQueryId", query = "delete  from QueryFiles myQueryFiles WHERE myQueryFiles.query.queryId = ?1 and myQueryFiles.fileType='L' "),
	@NamedQuery(name = "deleteQueryFilesByFileUniqueCode", query = "delete  from QueryFiles myQueryFiles WHERE myQueryFiles.query.queryId = ?1 and myQueryFiles.fileUniqueCode = ?2")
	
})
@Table(catalog = "fixit", name = "query_files")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "QueryFiles")

public class QueryFiles implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * primary key user id
	 */
	@Column(name = "query_file_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer queryFileId;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "query_id", referencedColumnName = "query_id") })
    @XmlTransient
    @JsonIgnore
    @JsonBackReference("QueryFiles-Query")
    Query query;
	
	
	@Column(name = "file_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String fileName;
	
	@Column(name = "file_url")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String fileUrl;
	

	@Column(name = "file_type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String fileType;
	
	
	@Column(name = "file_unique_code")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String fileUniqueCode;
	
	
	
	
	@Transient
	String docUploadDate;
	
   	
	
	
	
	public String getFileUniqueCode() {
		return fileUniqueCode;
	}

	public void setFileUniqueCode(String fileUniqueCode) {
		this.fileUniqueCode = fileUniqueCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at" , nullable = false, updatable = false )
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Integer getQueryFileId() {
		return queryFileId;
	}

	public void setQueryFileId(Integer queryFileId) {
		this.queryFileId = queryFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
	
	

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	
	

	public String getDocUploadDate() {
		DateFormat pstFormat = new SimpleDateFormat("h.mm a d MMMM yyyy");
		return pstFormat.format(createdAt.getTime());
	}

	public void setDocUploadDate(String docUploadDate) {
		this.docUploadDate = docUploadDate;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + (( queryFileId== null) ? 0 : queryFileId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof QueryFiles))
			return false;
		QueryFiles equalCheck = (QueryFiles) obj;
		if ((queryFileId == null && equalCheck.queryFileId != null)
				|| (queryFileId != null && equalCheck.queryFileId == null))
			return false;
		if (queryFileId != null && !queryFileId.equals(equalCheck.queryFileId))
			return false;
		return true;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(QueryFiles that) {
		setQueryFileId(that.getQueryFileId());
		setFileName(that.getFileName());
		setFileType(that.getFileType());
		setFileUrl(that.getFileUrl());
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("queryFileId=[").append(queryFileId).append("] ");
		buffer.append("fileName=[").append(fileName).append("] ");
		buffer.append("fileType=[").append(fileType).append("] ");
		buffer.append("fileUrl=[").append(fileUrl).append("] ");
		return buffer.toString();
	}
	
	 @PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }
}
