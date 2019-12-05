package com.fixit.domain.vo;

import java.io.Serializable;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@NamedQueries({})
@Table(catalog = "fixit", name = "testimonial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "Testimonial")
public class Testimonial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "t_id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	String testimonialId;

	@Column(name = "content")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String content;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "user_id", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("Testimonial-User")
	User user;

	public String getTestimonialId() {
		return testimonialId;
	}

	public void setTestimonialId(String testimonialId) {
		this.testimonialId = testimonialId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(Testimonial that) {
		setTestimonialId(that.getTestimonialId());
		setContent(that.getContent());
        setUser(that.getUser());
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("testimonialId=[").append(testimonialId).append("] ");
		buffer.append("content=[").append(content).append("] ");

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((testimonialId == null) ? 0
				: testimonialId.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Testimonial))
			return false;
		Testimonial equalCheck = (Testimonial) obj;
		if ((testimonialId == null && equalCheck.testimonialId != null)
				|| (testimonialId != null && equalCheck.testimonialId == null))
			return false;
		if (testimonialId != null
				&& !testimonialId.equals(equalCheck.testimonialId))
			return false;
		return true;
	}

}
