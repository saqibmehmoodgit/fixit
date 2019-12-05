package com.fixit.domain.bo;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.User;

public class AppliedFixersListBo {


	private Integer queryAppliedFixersId;


	private String status;




	private UserBo userFixer;


	private UserBo userMember;
	
	private QueryBo query;


	private Calendar createdAt;


	private Calendar updatedAt;
	
	private long unreadCounts;


	public Integer getQueryAppliedFixersId() {
		return queryAppliedFixersId;
	}


	public void setQueryAppliedFixersId(Integer queryAppliedFixersId) {
		this.queryAppliedFixersId = queryAppliedFixersId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	


	public UserBo getUserFixer() {
		return userFixer;
	}


	public void setUserFixer(UserBo userFixer) {
		this.userFixer = userFixer;
	}


	public UserBo getUserMember() {
		return userMember;
	}


	public void setUserMember(UserBo userMember) {
		this.userMember = userMember;
	}


	public QueryBo getQuery() {
		return query;
	}


	public void setQuery(QueryBo query) {
		this.query = query;
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


	public long getUnreadCounts() {
		return unreadCounts;
	}


	public void setUnreadCounts(long unreadCounts) {
		this.unreadCounts = unreadCounts;
	}

}
