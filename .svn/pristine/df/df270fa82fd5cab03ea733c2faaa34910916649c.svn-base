package com.fixit.domain.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(catalog = "fixit", name = "blog_files")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "BlogFiles")
public class BlogFiles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "blog_file_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer blogFileId;

	@Column(name = "file_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String fileName;

	@Column(name = "blog_file_url")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String blogFileUrl;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "blog_id", referencedColumnName = "blog_id") })
    @XmlTransient
    @JsonIgnore
    @JsonBackReference("BlogFiles-Blog")
    Blog blog;

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

	public Integer getBlogFileId() {
		return blogFileId;
	}

	public void setBlogFileId(Integer blogFileId) {
		this.blogFileId = blogFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBlogFileUrl() {
		return blogFileUrl;
	}

	public void setBlogFileUrl(String blogFileUrl) {
		this.blogFileUrl = blogFileUrl;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((blogFileId == null) ? 0 : blogFileId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof BlogFiles))
			return false;
		BlogFiles equalCheck = (BlogFiles) obj;
		if ((blogFileId == null && equalCheck.blogFileId != null)
				|| (blogFileId != null && equalCheck.blogFileId == null))
			return false;
		if (blogFileId != null && !blogFileId.equals(equalCheck.blogFileId))
			return false;
		return true;
	}
	
}
