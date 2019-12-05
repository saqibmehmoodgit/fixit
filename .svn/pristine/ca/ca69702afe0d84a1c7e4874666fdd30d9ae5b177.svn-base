package com.fixit.domain.vo;

import java.io.Serializable;

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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({
	@NamedQuery(name = "deleteBlogCategoryByBlogId", query = "delete from BlogCategory blogCategory where blog.blogId=?1"),
	@NamedQuery(name = "categoriesByBlogId", query = "select blogCategory from BlogCategory blogCategory where blog.blogId=?1"),
	@NamedQuery(name = "blogByCategories", query = "select blogCategory from BlogCategory blogCategory where blogCategory.categoryType.catId=?1 and blogCategory.blog.user.userId=?2 order by blogCategory.blog.updatedAt desc"),
	@NamedQuery(name = "adminBlogListByCategories", query = "select blogCategory from BlogCategory blogCategory where blogCategory.categoryType.catId=?1 order by blogCategory.blog.updatedAt desc"),
	@NamedQuery(name = "adminBlogListByCategoriesAndStatus", query = "select blogCategory from BlogCategory blogCategory where blogCategory.categoryType.catId=?1 and blogCategory.blog.blogStatus=?2 and blogCategory.blog.user.userId=?3 order by blogCategory.blog.updatedAt desc")

})	


@Table(catalog = "fixit", name = "blog_category")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "BlogCategory")
public class BlogCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "blog_cat_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer blogCatId;



 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "blog_id", referencedColumnName = "blog_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("BlogCategory-Blog")
	Blog blog;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "cat_id", referencedColumnName = "cat_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("BlogCategory-CategoryType")
	CategoryType categoryType;
	
	/*@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private CategoryType categoryType;*/

	public Integer getBlogCatId() {
		return blogCatId;
	}

	public void setBlogCatId(Integer blogCatId) {
		this.blogCatId = blogCatId;
	}

	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(CategoryType categoryType) {
		this.categoryType = categoryType;
	}
	
	


	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((blogCatId == null) ? 0 : blogCatId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof BlogCategory))
			return false;
		BlogCategory equalCheck = (BlogCategory) obj;
		if ((blogCatId == null && equalCheck.blogCatId != null)
				|| (blogCatId != null && equalCheck.blogCatId == null))
			return false;
		if (blogCatId != null && !blogCatId.equals(equalCheck.blogCatId))
			return false;
		return true;
	}

}
