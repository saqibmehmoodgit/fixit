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
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@NamedQueries({ 
    @NamedQuery(name = "getAllChatGroups", query = "select myChatGroups from ChatGroups myChatGroups order by createdAt DESC  "),
    @NamedQuery(name = "findChatGroupBasedOnGroupId", query = "select myChatGroups from ChatGroups myChatGroups where  myChatGroups.chatGroupId=?1")
   })
@Table(catalog = "fixit", name = "chat_groups")
public class ChatGroups implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * primary key chat_group_id
	 */
	@Column(name = "chat_group_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer chatGroupId;
	
	@Column(name = "chat_group_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String chatGroupName;
	
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
	
	@OneToMany(mappedBy = "chatGroups", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonManagedReference("ChatUserGroup-ChatGroups")
	java.util.Set<com.fixit.domain.vo.ChatUserGroup> chatUserGroup;
	
	@OneToMany(mappedBy = "chatGroups", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonManagedReference("ChatMessage-ChatGroups")
	java.util.Set<com.fixit.domain.vo.ChatUserGroup> chatMessage;
	
	
	 public java.util.Set<com.fixit.domain.vo.ChatUserGroup> getChatMessage() {
		return chatMessage;
	}

	public void setChatMessage(java.util.Set<com.fixit.domain.vo.ChatUserGroup> chatMessage) {
		this.chatMessage = chatMessage;
	}

	public java.util.Set<com.fixit.domain.vo.ChatUserGroup> getChatUserGroup() {
		return chatUserGroup;
	}

	public void setChatUserGroup(java.util.Set<com.fixit.domain.vo.ChatUserGroup> chatUserGroup) {
		this.chatUserGroup = chatUserGroup;
	}

	public Integer getChatGroupId() {
		return chatGroupId;
	}

	public void setChatGroupId(Integer chatGroupId) {
		this.chatGroupId = chatGroupId;
	}

	public String getChatGroupName() {
		return chatGroupName;
	}

	public void setChatGroupName(String chatGroupName) {
		this.chatGroupName = chatGroupName;
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
	public void copy(ChatGroups that) {
		setChatGroupId(that.getChatGroupId());
		setChatGroupName(that.getChatGroupName());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
	/*	setChatUserGroup(that.getChatUserGroup());*/
		
		/*setChatMessage(that.getChatMessage());*/
		
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("chatGroupId=[").append(chatGroupId).append("] ");
		buffer.append("chatGroupName=[").append(chatGroupName).append("] ");
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
		result = (int) (prime * result + ((chatGroupId == null) ? 0 : chatGroupId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ChatGroups))
			return false;
		ChatGroups equalCheck = (ChatGroups) obj;
		if ((chatGroupId == null && equalCheck.chatGroupId != null)
				|| (chatGroupId != null && equalCheck.chatGroupId == null))
			return false;
		if (chatGroupId != null && !chatGroupId.equals(equalCheck.chatGroupId))
			return false;
		return true;
	}
	
}
