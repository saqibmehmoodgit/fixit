package com.fixit.domain.vo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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
@NamedQueries({
	 @NamedQuery(name = "findAllIndustryType", query = "select myIndustryType from IndustryType myIndustryType order by myIndustryType.indstName "),
	 @NamedQuery(name = "findIndustryTypeByIndstId", query = "select myIndustryType from IndustryType myIndustryType where myIndustryType.indstId = ?1"),
})
@Table(catalog = "fixit", name = "industry_type")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "IndustryType")
public class IndustryType implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "indst_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer indstId;

	@Column(name = "indst_name", length = 150)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String indstName;

	@OneToMany(mappedBy = "industryType", fetch = FetchType.EAGER)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("UserIndustry-IndustryType")
	java.util.Set<com.fixit.domain.vo.UserIndustry> userIndustry;

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

	public java.util.Set<com.fixit.domain.vo.UserIndustry> getUserIndustry() {
		return userIndustry;
	}

	public void setUserIndustry(
			java.util.Set<com.fixit.domain.vo.UserIndustry> userIndustry) {
		this.userIndustry = userIndustry;
	}

	public Integer getIndstId() {
		return indstId;
	}

	public void setIndstId(Integer indstId) {
		this.indstId = indstId;
	}

	public String getIndstName() {
		return indstName;
	}

	public void setIndstName(String indstName) {
		this.indstName = indstName;
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

	public void copy(IndustryType that) {
		setIndstId(that.getIndstId());
		setIndstName(that.getIndstName());
		setUpdatedAt(that.getUpdatedAt());
		setCreatedAt(that.getCreatedAt());
		setUserIndustry(that.getUserIndustry());
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("indstId=[").append(indstId).append("] ");
		buffer.append("indstName=[").append(indstName).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
		buffer.append("userIndustry=[").append(userIndustry).append("] ");
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((indstId == null) ? 0 : indstId
				.hashCode()));
		return result;
	}

	@PrePersist
	private void prePersist() {
		this.createdAt = Calendar.getInstance();
	}

}
