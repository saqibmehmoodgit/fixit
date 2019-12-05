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
@NamedQueries({
	@NamedQuery(name = "findQueryCategoryById", query = "select myQueryCategory from QueryCategory myQueryCategory where myQueryCategory.queryCategoryId = ?1"),
	@NamedQuery(name = "findQueryCategoryByQueryId", query = "select myQueryCategory from QueryCategory myQueryCategory where myQueryCategory.query.queryId = ?1"),
	@NamedQuery(name = "deleteQueryCategoryByQueryIds", query = "delete  from QueryCategory myQueryCategory WHERE myQueryCategory.query.queryId = ?1 and myQueryCategory.categoryType.catId NOT IN (:categories)"),
	@NamedQuery(name = "findQueryCategoryByQueryIdandCatId", query = "select myQueryCategory from QueryCategory myQueryCategory where myQueryCategory.query.queryId = ?1 and myQueryCategory.categoryType.catId = ?2"),
	@NamedQuery(name = "deleteQueryCategoryByQueryId", query = "delete  from QueryCategory myQueryCategory WHERE myQueryCategory.query.queryId = ?1 "),
	
})
@Table(catalog = "fixit", name = "query_cat")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "QueryCategory")
public class QueryCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "query_cat_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer queryCategoryId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "query_id", referencedColumnName = "query_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("QueryCategory-Query")
	Query query;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "cat_id", referencedColumnName = "cat_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("QueryCat-CategoryType")
	CategoryType categoryType;

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

	
	public Integer getQueryCategoryId() {
		return queryCategoryId;
	}

	public void setQueryCategoryId(Integer queryCategoryId) {
		this.queryCategoryId = queryCategoryId;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
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

	public void copy(QueryCategory that) {

		setQueryCategoryId(that.getQueryCategoryId());
		setQuery(that.getQuery());
		setCategoryType(that.getCategoryType());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());

	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("queryCategoryId=[").append(queryCategoryId).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((queryCategoryId == null) ? 0
				: queryCategoryId.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof QueryCategory))
			return false;
		QueryCategory equalCheck = (QueryCategory) obj;
		if ((queryCategoryId == null && equalCheck.queryCategoryId != null)
				|| (queryCategoryId != null && equalCheck.queryCategoryId == null))
			return false;
		if (queryCategoryId != null
				&& !queryCategoryId.equals(equalCheck.queryCategoryId))
			return false;
		return true;
	}
	
	 @PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }
}
