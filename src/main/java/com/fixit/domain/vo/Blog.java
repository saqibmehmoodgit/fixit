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
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@NamedQueries({
		@NamedQuery(name = "findBlogDataByStatus", query = "select blog from Blog blog where blog.blogStatus = ?1 order by blog.updatedAt desc"),
		@NamedQuery(name = "updateStatusByBlogId", query = "update Blog blog SET blog.blogStatus=?1 where blog.blogId = ?2"),
		@NamedQuery(name = "findAllBlog", query = "select blog from Blog blog order by blog.updatedAt desc"),
		@NamedQuery(name = "findAllBlogByBlogId", query = "select blog from Blog blog where blog.blogId = ?1 order by blog.updatedAt desc"),
		@NamedQuery(name = "findAllBlogByUserId", query = "select blog from Blog blog where blog.user.userId = ?1 order by blog.updatedAt desc"),
		@NamedQuery(name = "findBlogByStatusAndUserId", query = "select blog from Blog blog where blog.user.userId = ?1 AND blog.blogStatus = ?2 order by blog.updatedAt desc"),
		@NamedQuery(name = "updateStatusWithReasonByBlogId", query = "update Blog blog SET blog.blogStatus=?1,blog.reason=?2 where blog.blogId = ?3"),
		@NamedQuery(name = "deleteBlogByBlogId", query = "delete from Blog blog where blogId=?1")
		
})
@Table(catalog = "fixit", name = "blog")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "Blog")
public class Blog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "blog_id", nullable = false)
	@Basic(fetch = FetchType.LAZY)
	@Id
	@XmlElement
	@GeneratedValue
	Integer blogId;

	@Column(name = "blog_title")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String blogTitle;

	@Column(name = "blog_description")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String blogDescription;

	@Column(name = "blog_status")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String blogStatus;
	
	@Column(name = "reason",length = 10000)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String reason;
	
	@OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("BlogCategory-Blog")
	java.util.Set<com.fixit.domain.vo.BlogCategory> blogCategory;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Calendar createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Calendar updatedAt;

	/*@Column(name = "user_id")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Integer userId;*/
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "user_id", referencedColumnName = "user_id") })
    @XmlTransient
    @JsonIgnore
    @JsonBackReference("Blog-user")
    User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getBlogId() {
		return blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogDescription() {
		return blogDescription;
	}

	public void setBlogDescription(String blogDescription) {
		this.blogDescription = blogDescription;
	}

	public String getBlogStatus() {
		return blogStatus;
	}

	public void setBlogStatus(String blogStatus) {
		this.blogStatus = blogStatus;
	}

	

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	
	public java.util.Set<com.fixit.domain.vo.BlogCategory> getBlogCategory() {
		return blogCategory;
	}

	public void setBlogCategory(
			java.util.Set<com.fixit.domain.vo.BlogCategory> blogCategory) {
		this.blogCategory = blogCategory;
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
		result = (int) (prime * result + ((blogId == null) ? 0 : blogId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Blog))
			return false;
		Blog equalCheck = (Blog) obj;
		if ((blogId == null && equalCheck.blogId != null)
				|| (blogId != null && equalCheck.blogId == null))
			return false;
		if (blogId != null && !blogId.equals(equalCheck.blogId))
			return false;
		return true;
	}

}
