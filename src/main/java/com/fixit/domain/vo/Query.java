package com.fixit.domain.vo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
    @NamedQueries({ @NamedQuery(name = "findQueryByQueryId", query = "select myQuery from Query myQuery where myQuery.queryId = ?1"),
	@NamedQuery(name = "getQuestionsByFixerId", query = "select myQuery from Query myQuery where myQuery.fixerId = ?1 and myquery.currentStatus IN (?2) order by dateRaised DESC"),
	@NamedQuery(name = "getQuestionsByCustomerId", query = "select myQuery from Query myQuery where myQuery.user.userId = ?1 and myquery.currentStatus IN (?2) order by dateRaised DESC"),
    @NamedQuery(name = "findFixerStats", query = "select count(myquery.currentStatus), myquery.currentStatus from Query  myQuery where myQuery.fixerId = ?1 group by myquery.currentStatus"),
	@NamedQuery(name = "findUserStats", query = "select count(myquery.currentStatus), myquery.currentStatus from Query myQuery where myQuery.user.userId = ?1 group by myquery.currentStatus"),
	@NamedQuery(name = "findUserClosedStasCount", query = "select count(myquery.currentStatus) from Query myQuery where myQuery.user.userId = ?1 and (myQuery.currentStatus='C')"),
	@NamedQuery(name = "findUserPendingResolutionStasCount", query = "select count(myquery.currentStatus) from Query myQuery where myQuery.user.userId = ?1 and  (myQuery.currentStatus='R' or myQuery.currentStatus='UN' or myQuery.currentStatus='UI')"),
	@NamedQuery(name = "findUserStasCount", query = "select count(myquery.currentStatus) from Query myQuery where myQuery.user.userId = ?1 and myQuery.currentStatus=?2"),
	@NamedQuery(name = "findFixerStasCount", query = "select count(myquery.currentStatus) from Query myQuery where myQuery.fixerId = ?1 and myQuery.currentStatus=?2"),
	@NamedQuery(name = "findFixerClosedStasCount", query = "select count(myquery.currentStatus) from Query myQuery where myQuery.fixerId = ?1 and (myQuery.currentStatus='C')"),
	@NamedQuery(name = "findFixerPendingResolutionStasCount", query = "select count(myquery.currentStatus) from Query myQuery where myQuery.fixerId = ?1 and  (myQuery.currentStatus='R'  or myQuery.currentStatus='UN' or myQuery.currentStatus='UI')"),
	@NamedQuery(name = "findQueryNotAcceptedByFixer", query = "select myQuery from Query myQuery where myQuery.fixerId = 0 and myQuery.dateRaised <=?1  "),
	@NamedQuery(name = "findQueryNotAcceptedByIndividualFixer", query = "select myQuery from Query myQuery where  myQuery.fixerId  >= 1 and myQuery.dateAccepted is null  and myQuery.dateRaised <=?1  "),
    @NamedQuery(name = "testTrancate", query = "select myQuery from Query myQuery where myQuery.dateRaised = :dateRaised"),
    @NamedQuery(name = "findNotSureQueries", query = "select myQuery from Query myQuery where myQuery.fixerId =?1 order by dateRaised DESC"),
    @NamedQuery(name = "findReviewQueries", query = "select myQuery from Query myQuery where myQuery.currentStatus =?1 order by dateRaised DESC"),
    @NamedQuery(name = "findReviewQueriesCount", query = "select count(myQuery.currentStatus) from Query myQuery where myQuery.currentStatus =?1 order by dateRaised DESC"),
    //@NamedQuery(name = "findQueryNotAccepted", query = "select myQuery from Query myQuery where myQuery.user.userId =?1 and myQuery.currentStatus='O' order by dateRaised DESC"),
    @NamedQuery(name = "findQueryNotAccepted", query = "select myQuery from Query myQuery where myQuery.user.userId =?1 and (myQuery.currentStatus='O' or myQuery.currentStatus='H') order by dateRaised DESC"),
    @NamedQuery(name = "findQueryWorking", query = "select myQuery from Query myQuery where myQuery.user.userId =?1 and  myQuery.currentStatus='W' order by dateRaised DESC"),
    @NamedQuery(name = "findQueryClosed", query = "select myQuery from Query myQuery where myQuery.user.userId =?1 and (myQuery.currentStatus='C') order by dateRaised DESC"),
    @NamedQuery(name = "findQueryClosedByMonth", query = "select myQuery from Query myQuery where myQuery.user.userId =?1 and (myQuery.currentStatus='C') and myQuery.dateRaised between :startDate and :endDate order by dateRaised ASC"),
    @NamedQuery(name = "findQueryPendingResolution", query = "select myQuery from Query myQuery where myQuery.user.userId =?1 and (myQuery.currentStatus='R' or myQuery.currentStatus='UN' or myQuery.currentStatus='UI') order by dateRaised DESC"),
    @NamedQuery(name = "findQueryNotAcceptedFixer", query = "select myQuery from Query myQuery where myQuery.fixerId =?1 and   myQuery.currentStatus='O' order by dateRaised DESC"),
    @NamedQuery(name = "findQueryWorkingFixer", query = "select myQuery from Query myQuery where myQuery.fixerId =?1 and  myQuery.currentStatus='W' order by dateRaised DESC"),
    @NamedQuery(name = "findQueryClosedFixer", query = "select myQuery from Query myQuery where myQuery.fixerId =?1 and (myQuery.currentStatus='C') order by dateRaised DESC "),
    @NamedQuery(name = "findQueryClosedFixerMonth", query = "select myQuery from Query myQuery where myQuery.fixerId =?1 and (myQuery.currentStatus='C') and myQuery.dateRaised between :startDate and :endDate order by dateRaised DESC "),
    @NamedQuery(name = "findQueryPendingResolutionFixer", query = "select myQuery from Query myQuery where myQuery.fixerId =?1 and  (myQuery.currentStatus='R' or myQuery.currentStatus='UN' or myQuery.currentStatus='UI')order by dateRaised DESC "),
    @NamedQuery(name = "deleteQueryByQueryId", query = "delete  from Query myQuery WHERE myQuery.queryId = ?1 "),
    @NamedQuery(name = "notFixedQuerybyFixedId", query = "select myQuery from Query myQuery where myQuery.fixerId =?1 and myQuery.currentStatus='UN' order by dateRaised DESC "),
    @NamedQuery(name = "getQueryByHashCode", query = "select myQuery from Query myQuery where myQuery.hashcode = ?1"),
    @NamedQuery(name = "findUnresolvedAndInactiveQueries", query = "select myQuery from Query myQuery where myQuery.currentStatus = ?1 or myQuery.currentStatus = ?2 order by dateRaised DESC"),
    @NamedQuery(name = "findQueryNotSureCat", query = "select myQuery from Query myQuery where  myQuery.queryId IN (select myQueryCategory.query.queryId from QueryCategory myQueryCategory where myQueryCategory.categoryType.catId = ?1 ) order by dateRaised DESC"),
    @NamedQuery(name = "findQueryNotSureCatCount", query = "select count(myQuery) from Query myQuery where  myQuery.queryId IN (select myQueryCategory.query.queryId from QueryCategory myQueryCategory where myQueryCategory.categoryType.catId = ?1 ) order by dateRaised DESC"),
    @NamedQuery(name = "findIssueOpenDate", query = "select myQuery.dateAccepted from Query myQuery where  myQuery.queryId = ?1"),
    @NamedQuery(name = "findIssueCloseDate", query = "select myQuery.closureDate from Query myQuery where  myQuery.queryId = ?1"),
    @NamedQuery(name = "findQueryByFixerIdAndStatus", query = "select myQuery from Query myQuery where  myQuery.fixerId = ?1 AND myQuery.currentStatus = ?2"),
    @NamedQuery(name = "findMemberQueryCount", query = "select count(myQuery) from Query myQuery where  myQuery.user.userId = ?1"),
    @NamedQuery(name = "findNotAcceptedQueryByFixerCategory", query = "select count(myQuery) from Query myQuery where  myQuery.currentStatus='O' and myQuery.queryId  IN (select myQueryCategory.query.queryId from QueryCategory myQueryCategory where myQueryCategory.query.queryId IN (:categoryList) )"),
    @NamedQuery(name = "findNotAcceptedQueryByFixerCategoryCount", query = "select myQuery from Query myQuery where  myQuery.currentStatus='O' and myQuery.queryId  IN (select myQueryCategory.query.queryId from QueryCategory myQueryCategory where myQueryCategory.query.queryId IN (:categoryList) )"),
    @NamedQuery(name = "findQueryByCustomerId", query = "select myQuery from Query myQuery where myQuery.user.userId = ?1 "),
    @NamedQuery(name = "fixedIssueListByFixerId", query = "SELECT myQuery FROM Query myQuery where  myQuery.fixerId=?1  and myQuery.fixerAccounting.user.userId=?1 and myQuery.currentStatus='C' and myQuery.fixerAccounting.status='0' "),
    @NamedQuery(name = "findSumofQueryCreditByUserId", query = "select sum(query.queryCredits)  from Query query where query.user.userId = ?1"),
    @NamedQuery(name = "findFixerClosedRequestsCountInDeadline", query = "select count(myquery.currentStatus) from Query myQuery where myQuery.fixerId = ?1 and (myQuery.currentStatus='C') and (myQuery.queryDeadlineDate >= myQuery.closureDate or myQuery.queryDeadlineDate IS NULL )"),
    @NamedQuery(name = "findSumofQueryCreditInProgAndFixedByUserId", query = "select sum(query.queryCredits)  from Query query where query.user.userId = ?1 and (query.currentStatus = 'W' or query.currentStatus = 'C')")
   

   
    })

@Table(catalog = "fixit", name = "query")

@SqlResultSetMappings({
	@SqlResultSetMapping(name = "queryMapping", entities = { @EntityResult(entityClass = Query.class, fields = {
}) })
})


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "Query")
public class Query implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "query_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer queryId;
	
	
	@Column(name = "fixer_id", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer fixerId;
	
	@OneToOne(mappedBy = "query", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("FixerAccounting-Query")
	FixerAccounting fixerAccounting;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "customer_id", referencedColumnName = "user_id") })
    @XmlTransient
    @JsonIgnore
    @JsonBackReference("Query-User")
    User user;
	
	
	
	@OneToMany(mappedBy = "query", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("QueryAudit-Query")
	java.util.Set<com.fixit.domain.vo.QueryAudit> queryAudit;
	
	@OneToMany(mappedBy = "query", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("QueryAudit-Query")
	java.util.Set<com.fixit.domain.vo.QueryFixers> queryFixers;
	
	@OneToMany(mappedBy = "query", fetch = FetchType.EAGER)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("QueryAppliedFixers-Query")
	java.util.Set<com.fixit.domain.vo.QueryAppliedFixers> queryAppliedFixers;
	
	
	
	@OneToOne(mappedBy = "query", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("FixerRating-Query")
	FixerRating fixerRating;
	
	

	public FixerRating getFixerRating() {
		return fixerRating;
	}

	public void setFixerRating(FixerRating fixerRating) {
		this.fixerRating = fixerRating;
	}
	@OneToMany(mappedBy = "query", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("QueryCategory-Query")
	java.util.Set<com.fixit.domain.vo.QueryCategory> queryCategory;
	
	
	@OneToMany(mappedBy = "query", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("QueryFiles-Query")
	java.util.Set<com.fixit.domain.vo.QueryFiles> queryFiles;	
	
	@Column(name = "query_content")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String queryContent;
	
	@Column(name = "query_title")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String queryTitle;
	
	@Column(name = "hashcode")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hashcode;
	
	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public String getQueryTitle() {
		return queryTitle;
	}

	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}
	@Column(name = "current_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String currentStatus;
	
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name = "date_raised")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar dateRaised;
	
	@Temporal(TemporalType.TIMESTAMP )
	@Column(name = "query_deadline_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar queryDeadlineDate;
	
	public Calendar getQueryDeadlineDate() {
		return queryDeadlineDate;
	}

	public void setQueryDeadlineDate(Calendar queryDeadlineDate) {
		this.queryDeadlineDate = queryDeadlineDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_accepted")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar dateAccepted;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_by_user")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar lastUpdateByUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_by_fixer")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar lastUpdateByFixer;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "closure_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar closureDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "old_query_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar oldQueryId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at" , nullable = false, updatable = false )
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false )
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;
	
	@Column(name = "query_credits")
	 @Basic(fetch = FetchType.EAGER)
	 @XmlElement
	 Integer queryCredits;
	

	

	public Integer getQueryCredits() {
		return queryCredits;
	}

	public void setQueryCredits(Integer queryCredits) {
		this.queryCredits = queryCredits;
	}

	public FixerAccounting getFixerAccounting() {
		return fixerAccounting;
	}

	public void setFixerAccounting(FixerAccounting fixerAccounting) {
		this.fixerAccounting = fixerAccounting;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public java.util.Set<com.fixit.domain.vo.QueryAudit> getQueryAudit() {
		return queryAudit;
	}

	public void setQueryAudit(java.util.Set<com.fixit.domain.vo.QueryAudit> queryAudit) {
		this.queryAudit = queryAudit;
	}

	public java.util.Set<com.fixit.domain.vo.QueryCategory> getQueryCategory() {
		return queryCategory;
	}

	public void setQueryCategory(
			java.util.Set<com.fixit.domain.vo.QueryCategory> queryCategory) {
		this.queryCategory = queryCategory;
	}

	public java.util.Set<com.fixit.domain.vo.QueryFiles> getQueryFiles() {
		return queryFiles;
	}

	public void setQueryFiles(java.util.Set<com.fixit.domain.vo.QueryFiles> queryFiles) {
		this.queryFiles = queryFiles;
	}

	public Integer getQueryId() {
		return queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}

	public Integer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
	}

	public String getQueryContent() {
		return queryContent;
	}

	public void setQueryContent(String queryContent) {
		this.queryContent = queryContent;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Calendar getDateRaised() {
		return dateRaised;
	}

	public void setDateRaised(Calendar dateRaised) {
		this.dateRaised = dateRaised;
	}

	public Calendar getDateAccepted() {
		return dateAccepted;
	}

	public void setDateAccepted(Calendar dateAccepted) {
		this.dateAccepted = dateAccepted;
	}

	public Calendar getLastUpdateByUser() {
		return lastUpdateByUser;
	}

	public void setLastUpdateByUser(Calendar lastUpdateByUser) {
		this.lastUpdateByUser = lastUpdateByUser;
	}

	public Calendar getLastUpdateByFixer() {
		return lastUpdateByFixer;
	}

	public void setLastUpdateByFixer(Calendar lastUpdateByFixer) {
		this.lastUpdateByFixer = lastUpdateByFixer;
	}

	public Calendar getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(Calendar closureDate) {
		this.closureDate = closureDate;
	}

	public Calendar getOldQueryId() {
		return oldQueryId;
	}

	public void setOldQueryId(Calendar oldQueryId) {
		this.oldQueryId = oldQueryId;
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
	
	public java.util.Set<com.fixit.domain.vo.QueryFixers> getQueryFixers() {
		return queryFixers;
	}

	public void setQueryFixers(java.util.Set<com.fixit.domain.vo.QueryFixers> queryFixers) {
		this.queryFixers = queryFixers;
	}
	
	public java.util.Set<com.fixit.domain.vo.QueryAppliedFixers> getQueryAppliedFixers() {
		return queryAppliedFixers;
	}

	public void setQueryAppliedFixers(java.util.Set<com.fixit.domain.vo.QueryAppliedFixers> queryAppliedFixers) {
		this.queryAppliedFixers = queryAppliedFixers;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(Query that) {
		setQueryId(that.getQueryId());
		setUser(that.getUser());
		setFixerId(that.getFixerId());
		setQueryContent(that.getQueryContent());
		setCurrentStatus(that.currentStatus);
		setDateRaised(that.getDateRaised());
		setDateAccepted(that.getDateAccepted());
		setLastUpdateByFixer(that.getLastUpdateByFixer());
		setLastUpdateByUser(that.getLastUpdateByUser());
		setClosureDate(that.getClosureDate());
		setOldQueryId(that.getOldQueryId());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
        setFixerRating(that.getFixerRating());
        setQueryAppliedFixers(that.getQueryAppliedFixers());
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("queryId=[").append(queryId).append("] ");
		buffer.append("fixerId=[").append(fixerId).append("] ");
		buffer.append("queryContent=[").append(queryContent).append("] ");
		buffer.append("currentStatus=[").append(currentStatus).append("] ");
		buffer.append("dateRaised=[").append(dateRaised).append("] ");
		buffer.append("lastUpdateByFixer=[").append(lastUpdateByFixer).append("] ");
		buffer.append("lastUpdateByUser=[").append(lastUpdateByUser).append("] ");
		buffer.append("closureDate=[").append(closureDate).append("] ");
		buffer.append("oldQueryId=[").append(oldQueryId).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");

		return buffer.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((queryId == null) ? 0 : queryId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Query))
			return false;
		Query equalCheck = (Query) obj;
		if ((queryId == null && equalCheck.queryId != null)
				|| (queryId != null && equalCheck.queryId == null))
			return false;
		if (queryId != null && !queryId.equals(equalCheck.queryId))
			return false;
		return true;
	}
	 @PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }
	
}
