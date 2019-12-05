package com.fixit.domain.vo;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@Entity

@Table(catalog = "fixit", name = "admin")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "Admin")
public class Admin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "admin_id")
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer adminId;

	@Column(name = "admin_email", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String adminEmail;

	@Column(name = "admin_password", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String adminPassword;

	

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	
	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 */
	public void copy(Admin that) {
		setAdminId(that.getAdminId());
		setAdminEmail(that.getAdminEmail());
		setAdminPassword(that.getAdminPassword());
		
        
        
	}
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("adminId=[").append(adminId).append("] ");
		buffer.append("adminEmail=[").append(adminEmail).append("] ");
		buffer.append("adminPassword=[").append(adminPassword).append("] ");
		

		return buffer.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((adminId == null) ? 0 : adminId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Admin))
			return false;
		Admin equalCheck = (Admin) obj;
		if ((adminId == null && equalCheck.adminId != null)
				|| (adminId != null && equalCheck.adminId == null))
			return false;
		if (adminId != null && !adminId.equals(equalCheck.adminId))
			return false;
		return true;
	}

}
