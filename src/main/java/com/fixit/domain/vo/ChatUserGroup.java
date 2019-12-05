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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({ 
    @NamedQuery(name = "findChatUserGroupBasedOnIds", query = "select myChatUserGroup from ChatUserGroup myChatUserGroup WHERE myChatUserGroup.userId IN (?1) group by myChatUserGroup.chatGroups.chatGroupId having count(myChatUserGroup.chatGroups.chatGroupId)=?2 "),

  
})
@Table(catalog = "fixit", name = "chatuser_group")
public class ChatUserGroup implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * primary key user id
	 */
	@Column(name = "chatuser_group_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer chatUserGroupId;
	
	/*@Column(name = "chat_group_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String chatGroupId;*/
	
	@Column(name = "user_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer userId;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "chat_group_id", referencedColumnName = "chat_group_id") })
    @XmlTransient
    @JsonBackReference("ChatUserGroup-ChatGroups")
    ChatGroups chatGroups;
	

	public ChatGroups getChatGroups() {
		return chatGroups;
	}

	public void setChatGroups(ChatGroups chatGroups) {
		this.chatGroups = chatGroups;
	}

	public Integer getChatUserGroupId() {
		return chatUserGroupId;
	}

	public void setChatUserGroupId(Integer chatUserGroupId) {
		this.chatUserGroupId = chatUserGroupId;
	}

/*	public String getChatGroupId() {
		return chatGroupId;
	}

	public void setChatGroupId(String chatGroupId) {
		this.chatGroupId = chatGroupId;
	}*/

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	@PrePersist
	 private void prePersist() {
	  this.createdAt = Calendar.getInstance();
	 }
	
	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(ChatUserGroup that) {
		setChatGroups(that.getChatGroups());
/*		setChatGroupId(that.getChatGroupId());*/
		setChatUserGroupId(that.getChatUserGroupId());
		setUserId(that.getUserId());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("chatUserGroupId=[").append(chatUserGroupId).append("] ");
/*		buffer.append("chatGroupId=[").append(chatGroupId).append("] ");*/
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
	
		
	/*	buffer.append("fixerAccounting=[").append(fixerAccounting).append("] ");
		buffer.append("query=[").append(query).append("] ");
		buffer.append("testimonial=[").append(testimonial).append("] ");*/
		
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((chatUserGroupId == null) ? 0 : chatUserGroupId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ChatUserGroup))
			return false;
		ChatUserGroup equalCheck = (ChatUserGroup) obj;
		if ((chatUserGroupId == null && equalCheck.chatUserGroupId != null)
				|| (chatUserGroupId != null && equalCheck.chatUserGroupId == null))
			return false;
		if (chatUserGroupId != null && !chatUserGroupId.equals(equalCheck.chatUserGroupId))
			return false;
		return true;
	}
	
}

