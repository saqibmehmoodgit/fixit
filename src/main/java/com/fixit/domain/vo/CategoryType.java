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
import javax.persistence.OneToMany;
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


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@NamedQueries({ @NamedQuery(name = "findCategoryTypeById", query = "select myCategoryType from CategoryType myCategoryType where myCategoryType.catId = ?1"),
	            @NamedQuery(name = "findAllCategoryType", query = "select myCategoryType from CategoryType myCategoryType where myCategoryType.parentId >=1 order by myCategoryType.catgName "),
	            @NamedQuery(name = "findAllCategoryTypeWithoutNotSure", query = "select myCategoryType from CategoryType myCategoryType where myCategoryType.parentId >=1 and myCategoryType.catId !=10000 order by myCategoryType.catgName "),
                @NamedQuery(name = "findAllParentCategory", query = "select myCategoryType from CategoryType myCategoryType where myCategoryType.parentId ='0' order by myCategoryType.catgName "),
                @NamedQuery(name = "findAllParentCategoryWithoutNotSure", query = "select myCategoryType from CategoryType myCategoryType where myCategoryType.parentId ='0'  and myCategoryType.catId !=9999 order by  myCategoryType.catgName"),
                @NamedQuery(name = "findAllParentCategoryByCatId", query = "select myCategoryType.catgName from CategoryType myCategoryType where myCategoryType.catId IN (?1) order by  myCategoryType.catgName")

})
@Table(catalog = "fixit",  name = "cat_type")

@SqlResultSetMappings({
	@SqlResultSetMapping(name = "categoryTypeMapping", entities = { @EntityResult(entityClass = CategoryType.class, fields = {
}) }),
})

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "CategoryType")
public class CategoryType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "cat_id", nullable = false)
	@Basic(fetch = FetchType.LAZY)
	@Id
	@XmlElement
	@GeneratedValue
	Integer catId;
	
	@Column(name = "cat_name", length = 150 )
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String catgName;
	
	@Column(name = "parent_id", length = 11 )
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String parentId;
	
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name = "created_at" ,nullable = false, updatable = false)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Calendar createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at",nullable = false, insertable = false, updatable = false )
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Calendar updatedAt;
	
	@OneToMany(mappedBy = "categoryType", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("QueryCat-CategoryType")
	java.util.Set<com.fixit.domain.vo.QueryCategory> queryCategory;
	
	@OneToMany(mappedBy = "categoryType", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("FixerCat-CategoryType")
	java.util.Set<com.fixit.domain.vo.UserCategory> fixerCategory;
	
	@OneToMany(mappedBy = "categoryType", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("BlogCategory-CategoryType")
	java.util.Set<com.fixit.domain.vo.BlogCategory> blogCategory;
	
	public java.util.Set<com.fixit.domain.vo.QueryCategory> getQueryCategory() {
		return queryCategory;
	}

	public void setQueryCategory(
			java.util.Set<com.fixit.domain.vo.QueryCategory> queryCategory) {
		this.queryCategory = queryCategory;
	}


	public java.util.Set<com.fixit.domain.vo.BlogCategory> getBlogCategory() {
		return blogCategory;
	}

	public void setBlogCategory(
			java.util.Set<com.fixit.domain.vo.BlogCategory> blogCategory) {
		this.blogCategory = blogCategory;
	}

	public String getCatgName() {
		return catgName;
	}

	public void setCatgName(String catgName) {
		this.catgName = catgName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	
	public void copy(CategoryType that) {
		setCatId(that.getCatId());
		setCatgName(that.getCatgName());
		setParentId(that.getParentId());
		setUpdatedAt(that.getUpdatedAt());
		setCreatedAt(that.getCreatedAt());
		setQueryCategory(that.getQueryCategory());
		
		
	}
	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	

	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("catId=[").append(catId).append("] ");
		buffer.append("catgName=[").append(catgName).append("] ");
		buffer.append("parentId=[").append(parentId).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
		buffer.append("queryCategory=[").append(queryCategory).append("] ");
		

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((catId == null) ? 0 : catId
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
		if ((catId == null && equalCheck.catId != null)
				|| (catId != null && equalCheck.catId == null))
			return false;
		if (catId != null && !catId.equals(equalCheck.catId))
			return false;
		return true;
	}
	 @PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }

}
