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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@NamedQueries({ 
    @NamedQuery(name = "getAllChatMessagesBasedOnGroupId", query = "select myChatMessage from ChatMessage myChatMessage WHERE myChatMessage.chatGroups.chatGroupId=?1 order by createdAt asc  "),
   })
@Table(catalog = "fixit", name = "chat_message")
public class ChatMessage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * primary key message 
	 */
	@Column(name = "message_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer messageId;
	
	/*@Column(name = "chat_from")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer chatFrom;*/
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "chat_from", referencedColumnName = "user_id") })
    @XmlTransient
    @JsonIgnore
    @JsonBackReference("ChatMessage-User")
    User user;
	
	
	@Column(name = "chat_message")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String chatMessage;
	
	
	
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
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "chat_group_id", referencedColumnName = "chat_group_id") })
    @XmlTransient
    @JsonIgnore
    @JsonBackReference("ChatMessage-ChatGroups")
    ChatGroups chatGroups;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}



	public String getChatMessage() {
		return chatMessage;
	}

	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
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

	public ChatGroups getChatGroups() {
		return chatGroups;
	}

	public void setChatGroups(ChatGroups chatGroups) {
		this.chatGroups = chatGroups;
	}
	
	@OneToMany(mappedBy = "chatMessage", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("MessageDoc-ChatMessage")
	java.util.Set<com.fixit.domain.vo.MessageDoc> messageDoc;

	public java.util.Set<com.fixit.domain.vo.MessageDoc> getMessageDoc() {
		return messageDoc;
	}

	public void setMessageDoc(java.util.Set<com.fixit.domain.vo.MessageDoc> messageDoc) {
		this.messageDoc = messageDoc;
	}
	
	@OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("MessageNotification-ChatMessage")
	java.util.Set<com.fixit.domain.vo.MessageNotification> messageNotification;



	public java.util.Set<com.fixit.domain.vo.MessageNotification> getMessageNotification() {
		return messageNotification;
	}

	public void setMessageNotification(java.util.Set<com.fixit.domain.vo.MessageNotification> messageNotification) {
		this.messageNotification = messageNotification;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(ChatMessage that) {
		setChatGroups(that.getChatGroups());
		setMessageId(that.getMessageId());
		/*setChatFrom(that.getChatFrom());*/
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		setMessageDoc(that.getMessageDoc());
		setMessageNotification(that.getMessageNotification());
		
	}
	
	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("messageId=[").append(messageId).append("] ");
/*		buffer.append("chatFrom=[").append(chatFrom).append("] ");*/
		buffer.append("chatMessage=[").append(chatMessage).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
	
		
	/*	buffer.append("fixerAccounting=[").append(fixerAccounting).append("] ");
		buffer.append("query=[").append(query).append("] ");
		buffer.append("testimonial=[").append(testimonial).append("] ");*/
		
		return buffer.toString();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((messageId == null) ? 0 : messageId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ChatMessage))
			return false;
		ChatMessage equalCheck = (ChatMessage) obj;
		if ((messageId == null && equalCheck.messageId != null)
				|| (messageId != null && equalCheck.messageId == null))
			return false;
		if (messageId != null && !messageId.equals(equalCheck.messageId))
			return false;
		return true;
	}
}
