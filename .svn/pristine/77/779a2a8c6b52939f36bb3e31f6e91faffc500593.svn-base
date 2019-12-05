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
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@NamedQueries({ 
    @NamedQuery(name = "getAllNotificatiosBasedOnGroupId", query = "select count(myMessageNotification.notificationId) from MessageNotification myMessageNotification WHERE myMessageNotification.receiverId=?1 and readStatus = false"),
    @NamedQuery(name = "updatedMessageStatuses", query = "update MessageNotification myMessageNotification set myMessageNotification.readStatus = true WHERE myMessageNotification.receiverId=?1"),
    
   })
@Table(catalog = "fixit", name = "msg_notification")
public class MessageNotification implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * primary key message id
	 */
	@Column(name = "notification_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer notificationId;
	
	/*@Column(name = "chat_from")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer chatFrom;*/
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "message_id", referencedColumnName = "message_id") })
    @XmlTransient
    @JsonIgnore
    @JsonBackReference("MessageNotification-ChatMessage")
    ChatMessage message;
	
	@Column(name = "receiver_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    int receiverId;
	
	
	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	@Column(name = "read_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	boolean readStatus;
	
	
	
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
		
	

	

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public ChatMessage getMessage() {
		return message;
	}

	public void setMessage(ChatMessage message) {
		this.message = message;
	}

	public boolean isReadStatus() {
		return readStatus;
	}

	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
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

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(MessageNotification that) {
		setNotificationId(that.notificationId);
		setMessage(that.getMessage());
		/*setChatFrom(that.getChatFrom());*/
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		setReceiverId(that.getReceiverId());
		setReadStatus(that.isReadStatus());
		
		
	}
	
	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("notificationId=[").append(notificationId).append("] ");
/*		buffer.append("chatFrom=[").append(chatFrom).append("] ");*/
		buffer.append("readStatus=[").append(readStatus).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
		buffer.append("receiverId=[").append(receiverId).append("] ");
	
		
	/*	buffer.append("fixerAccounting=[").append(fixerAccounting).append("] ");
		buffer.append("query=[").append(query).append("] ");
		buffer.append("testimonial=[").append(testimonial).append("] ");*/
		
		return buffer.toString();
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((notificationId == null) ? 0 : notificationId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof MessageNotification))
			return false;
		MessageNotification equalCheck = (MessageNotification) obj;
		if ((notificationId == null && equalCheck.notificationId != null)
				|| (notificationId != null && equalCheck.notificationId == null))
			return false;
		if (notificationId != null && !notificationId.equals(equalCheck.notificationId))
			return false;
		return true;
	}
}
