package com.fixit.domain.vo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.corba.se.impl.io.FVDCodeBaseImpl;


@Entity
@NamedQueries({ @NamedQuery(name = "deleteFavouriteFixerByFixerId", query = "delete  from FavouriteFixer myFavouriteFixer WHERE myFavouriteFixer.user.userId=?1 and fixerId=?2"),
	@NamedQuery(name = "deleteUserByFixerId", query = "delete  from FavouriteFixer myFavouriteFixer WHERE myFavouriteFixer.user.userId=?1"),
@NamedQuery(name = "findFavFixerByIds", query = "select favouriteFixer from FavouriteFixer favouriteFixer where favouriteFixer.user.userId=?1 and favouriteFixer.fixerId = ?2")
	
})

@Table(catalog = "fixit", name = "favourite_fixer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "FavouriteFixer")

public class FavouriteFixer implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer favouriteFixerId;

	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "member_id", referencedColumnName = "user_id") })
    @XmlTransient
    @JsonIgnore
    @JsonBackReference("FavouriteFixer-User")
    User user;
	
	
	
	@Column(name = "fixer_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer fixerId;

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
	
	public Integer getFavouriteFixerId() {
		return favouriteFixerId;
	}

	public void setFavouriteFixerId(Integer favouriteFixerId) {
		this.favouriteFixerId = favouriteFixerId;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getFixerId() {
		return fixerId;
	}

	public void setFixerId(Integer fixerId) {
		this.fixerId = fixerId;
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
	public void copy(FavouriteFixer that) {

		setFavouriteFixerId(that.getFavouriteFixerId());
		setFixerId(that.getFixerId());
		setUser(that.getUser());
        
        
	}
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("favouriteFixerId=[").append(favouriteFixerId).append("] ");
		buffer.append("fixerId=[").append(fixerId).append("] ");
		

		return buffer.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((favouriteFixerId == null) ? 0 : favouriteFixerId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof FavouriteFixer))
			return false;
		FavouriteFixer equalCheck = (FavouriteFixer) obj;
		if ((favouriteFixerId == null && equalCheck.favouriteFixerId != null)
				|| (favouriteFixerId != null && equalCheck.favouriteFixerId == null))
			return false;
		if (favouriteFixerId != null && !favouriteFixerId.equals(equalCheck.favouriteFixerId))
			return false;
		return true;
	}
	 

	
	
}
