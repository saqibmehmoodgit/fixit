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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(catalog = "fixit", name = "message_doc")
public class MessageDoc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * primary key message id
	 */
	@Column(name = "message_doc_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer messageDocId;
	
	
	@Column(name = "doc_url")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String docUrl;
	
	
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
    @JoinColumns({ @JoinColumn(name = "message_id", referencedColumnName = "message_id") })
    @XmlTransient
    @JsonIgnore
    @JsonBackReference("MessageDoc-ChatMessage")
    ChatMessage chatMessage;

	public Integer getMessageDocId() {
		return messageDocId;
	}

	public void setMessageDocId(Integer messageDocId) {
		this.messageDocId = messageDocId;
	}


	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
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

	public ChatMessage getChatMessage() {
		return chatMessage;
	}

	public void setChatMessage(ChatMessage chatMessage) {
		this.chatMessage = chatMessage;
	}
	
	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(MessageDoc that) {
		setMessageDocId(that.getMessageDocId());
		setDocUrl(that.getDocUrl());
		setChatMessage(that.getChatMessage());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		
	}
	
	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();
		buffer.append("messageDocId=[").append(messageDocId).append("] ");
		buffer.append("docUrl=[").append(docUrl).append("] ");
		buffer.append("chatMessage=[").append(chatMessage).append("] ");
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
		result = (int) (prime * result + ((messageDocId == null) ? 0 : messageDocId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof MessageDoc))
			return false;
		MessageDoc equalCheck = (MessageDoc) obj;
		if ((messageDocId == null && equalCheck.messageDocId != null)
				|| (messageDocId != null && equalCheck.messageDocId == null))
			return false;
		if (messageDocId != null && !messageDocId.equals(equalCheck.messageDocId))
			return false;
		return true;
	}

}
