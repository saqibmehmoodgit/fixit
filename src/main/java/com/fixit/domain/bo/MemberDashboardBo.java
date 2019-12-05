package com.fixit.domain.bo;

import java.util.List;
import java.util.Set;

import com.fixit.domain.vo.QueryAppliedFixers;
import com.fixit.domain.vo.QueryFiles;
import com.google.gson.Gson;

public class MemberDashboardBo {
    private int queryId;
    private String queryContent;
    private String timeDiff;
    private String queryTitle;
    private int queryCredits;
    private String fixersIds="[]";
	private String fixersNames="[]";
	private boolean selectedTrue; 
    private Integer fixerId;
    private Integer memberId;
    private UserBo member;
    private String subCatJson;
    private double queryRating;
    private String queryStartDate;
    private String queryEndDate;
    private String queryDeadlineDate;
    private String queryDeadlineTime;
    private long unreadMessageCount;
    private long fixedCounts=0;
    private String queryValue;// value of query in dollar
   
   
	private Set<String> subCategory;
    private String hashcode;
    private String currentStatus;
    private Set<QueryFilesBo> attachedDocuments;
    private Integer attachedDocumentsCount;
    private Double starRating;
    private String dateTimeDiffernce;
    private Set<QueryAppliedFixers> interestedFixers;
    private long interestedFixersCount;
 
    public int getQueryCredits() {
		return queryCredits;
	}
	public void setQueryCredits(int queryCredits) {
		this.queryCredits = queryCredits;
	} 
	public String getDateTimeDiffernce() {
		return dateTimeDiffernce;
	}
	public void setDateTimeDiffernce(String dateTimeDiffernce) {
		this.dateTimeDiffernce = dateTimeDiffernce;
	}
	public Double getStarRating() {
		return starRating;
	}
	public void setStarRating(Double starRating) {
		this.starRating = starRating;
	}
	public Integer getAttachedDocumentsCount() {
		return attachedDocumentsCount;
	}
	public void setAttachedDocumentsCount(Integer attachedDocumentsCount) {
		this.attachedDocumentsCount = attachedDocumentsCount;
	}
	
	
	
	public Set<QueryFilesBo> getAttachedDocuments() {
		return attachedDocuments;
	}
	public void setAttachedDocuments(Set<QueryFilesBo> attachedDocuments) {
		this.attachedDocuments = attachedDocuments;
	}
	public String getHashcode() {
		return hashcode;
	}
	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}
	public int getQueryId() {
		return queryId;
	}
	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}
	public String getQueryContent() {
		return queryContent;
	}
	public void setQueryContent(String queryContent) {
		this.queryContent = queryContent;
	}
	public String getTimeDiff() {
		return timeDiff;
	}
	public void setTimeDiff(String timeDiff) {
		this.timeDiff = timeDiff;
	}
	
	public String getQueryTitle() {
		return queryTitle;
	}
	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public Set<String> getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(Set<String> subCategory) {
		this.subCategory = subCategory;
	}
	public Set<QueryAppliedFixers> getInterestedFixers() {
		return interestedFixers;
	}
	public void setInterestedFixers(Set<QueryAppliedFixers> interestedFixers) {
		this.interestedFixers = interestedFixers;
	}
	public long getInterestedFixersCount() {
		return interestedFixersCount;
	}
	public void setInterestedFixersCount(long interestedFixersCount) {
		this.interestedFixersCount = interestedFixersCount;
	}
	public String getFixersIds() {
		return fixersIds;
	}
	public void setFixersIds(String fixersIds) {
		this.fixersIds = fixersIds;
	}
	public String getFixersNames() {
		return fixersNames;
	}
	public void setFixersNames(String fixersNames) {
		this.fixersNames = fixersNames;
	}
	public boolean isSelectedTrue() {
		return selectedTrue;
	}
	public void setSelectedTrue(boolean selectedTrue) {
		this.selectedTrue = selectedTrue;
	}
	public Integer getFixerId() {
		return fixerId;
	}
	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public UserBo getMember() {
		return member;
	}
	public void setMember(UserBo member) {
		this.member = member;
	}
	public String getSubCatJson() {
		return subCatJson;
	}
	public void setSubCatJson(String subCatJson) {
		this.subCatJson = subCatJson;
	}
	public double getQueryRating() {
		return queryRating;
	}
	public void setQueryRating(double queryRating) {
		this.queryRating = queryRating;
	}
	public String getQueryStartDate() {
		return queryStartDate;
	}
	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}
	public String getQueryEndDate() {
		return queryEndDate;
	}
	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}
	public long getUnreadMessageCount() {
		return unreadMessageCount;
	}
	public void setUnreadMessageCount(long unreadMessageCount) {
		this.unreadMessageCount = unreadMessageCount;
	}
	public String getQueryDeadlineDate() {
		return queryDeadlineDate;
	}
	public void setQueryDeadlineDate(String queryDeadlineDate) {
		this.queryDeadlineDate = queryDeadlineDate;
	}
	public long getFixedCounts() {
		return fixedCounts;
	}
	public void setFixedCounts(long fixedCounts) {
		this.fixedCounts = fixedCounts;
	}
	public String getQueryValue() {
		return queryValue;
	}
	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}
	public String getQueryDeadlineTime() {
		return queryDeadlineTime;
	}
	public void setQueryDeadlineTime(String queryDeadlineTime) {
		this.queryDeadlineTime = queryDeadlineTime;
	}
	

	
	
	
    
}
