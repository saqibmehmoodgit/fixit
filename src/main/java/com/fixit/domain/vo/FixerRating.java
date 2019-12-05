package com.fixit.domain.vo;

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
import javax.persistence.OneToOne;
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
@NamedQueries({ @NamedQuery(name = "findRatingBasedOnIds", query = "select myFixerRating from FixerRating myFixerRating where myFixerRating.query.queryId = ?1 and myFixerRating.user.userId=?2 and myFixerRating.memberId=?3"),
	@NamedQuery(name = "updateFixerRatingBasedOnIds", query = "update FixerRating myFixerRating set myFixerRating.starRating=?4 , myFixerRating.review =?5 where myFixerRating.query.queryId = ?1 and myFixerRating.user.userId=?2 and myFixerRating.memberId=?3"),
	@NamedQuery(name = "getAggregateRatingsOfFixer", query = "select sum(myFixerRating.starRating)/count(myFixerRating.user.userId) from FixerRating myFixerRating where myFixerRating.user.userId=?1 "),
	@NamedQuery(name = "getCountsRatingsOfFixer", query = "select count(myFixerRating.user.userId) from FixerRating myFixerRating where myFixerRating.user.userId=?1 "),
	
	@NamedQuery(name = "deleteRatingBasedOnQueryId", query = "delete from FixerRating myFixerRating where myFixerRating.query.queryId = ?1 "),
	@NamedQuery(name = "findRatingByFixerIdAndRating", query = "select count(fixerRating.fixerRatingId) , fixerRating.starRating  from FixerRating fixerRating where fixerRating.user.userId = ?1 group by fixerRating.starRating order by fixerRating.starRating  desc")

})

@Table(catalog = "fixit", name = "fixer_rating")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "FixerRating")
public class FixerRating {

	@Column(name = "fixer_rating_id")
	@Basic(fetch = FetchType.LAZY)
	@Id
	@XmlElement
	@GeneratedValue
	Integer fixerRatingId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "query_id", referencedColumnName = "query_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("FixerRating-Query")
	Query query;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "fixer_id", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("FixerRating-User")
	User user;
	
	

	@Column(name = "member_id")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Integer memberId;
	
	
	
	@Column(name = "star_rating", length = 255)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Double starRating;
	
	@Column(name = "review", length = 255)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String review;
	
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Calendar createdAt;
	
	



	public Integer getFixerRatingId() {
		return fixerRatingId;
	}

	public void setFixerRatingId(Integer fixerRatingId) {
		this.fixerRatingId = fixerRatingId;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	

	public Double getStarRating() {
		return starRating;
	}

	public void setStarRating(Double starRating) {
		this.starRating = starRating;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public void copy(FixerRating that) {
		
		setFixerRatingId(that.getFixerRatingId());
		setCreatedAt(that.getCreatedAt());
		setMemberId(that.getMemberId());
		setStarRating(that.getStarRating());
		
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("fixerRatingId=[").append(fixerRatingId).append("] ");
		buffer.append("memberId=[").append(memberId).append("] ");
		buffer.append("starRating=[").append(starRating).append("] ");
		buffer.append("review=[").append(review).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		
		
	/*	buffer.append("fixerAccounting=[").append(fixerAccounting).append("] ");
		buffer.append("query=[").append(query).append("] ");
		buffer.append("testimonial=[").append(testimonial).append("] ");*/
		
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((fixerRatingId == null) ? 0 : fixerRatingId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof FixerRating))
			return false;
		FixerRating equalCheck = (FixerRating) obj;
		if ((fixerRatingId == null && equalCheck.fixerRatingId != null)
				|| (fixerRatingId != null && equalCheck.fixerRatingId == null))
			return false;
		if (fixerRatingId != null && !fixerRatingId.equals(equalCheck.fixerRatingId))
			return false;
		return true;
	}
	
	 @PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }
	
}
