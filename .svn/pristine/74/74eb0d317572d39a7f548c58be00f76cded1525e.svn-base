package com.fixit.domain.vo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@NamedQueries({
	@NamedQuery(name="findAllCountry", query="select myCountry from Country myCountry"),
})
@Table(catalog = "fixit", name = "country")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "Country")
public class Country implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * primary key user id
	 */
	@Column(name = "country_id", length = 11, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer countryId;

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Column(name = "country_name", length=50 , nullable = false  )
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String countryName;

		public String toString() {

		StringBuilder buffer = new StringBuilder();
        buffer.append("countryId=[").append(countryId).append("] ");
		buffer.append("countryName=[").append(countryName).append("] ");
        return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((countryId == null) ? 0 : countryId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Country))
			return false;
		Country equalCheck = (Country) obj;
		if ((countryId == null && equalCheck.countryId != null)
				|| (countryId != null && equalCheck.countryId == null))
			return false;
		if (countryId != null && !countryId.equals(equalCheck.countryId))
			return false;
		return true;
	}

}
