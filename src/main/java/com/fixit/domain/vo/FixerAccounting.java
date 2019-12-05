package com.fixit.domain.vo;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
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
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fixit.domain.bo.FixerAccountingGroup;

@Entity
@NamedQueries({
	@NamedQuery(name="findFixerUnpaidQueryCount" , query="select count(myFixerAccounting.fixerAccId) from FixerAccounting myFixerAccounting where myFixerAccounting.user.userId =?1 AND myFixerAccounting.status='0' "),
	@NamedQuery(name="findFixerUnpaidQuery" , query="select myFixerAccounting from FixerAccounting myFixerAccounting where myFixerAccounting.user.userId =?1 AND myFixerAccounting.status='0' "),
	@NamedQuery(name="updateFixerPaidStatus" , query="update FixerAccounting myFixerAccounting set myFixerAccounting.status=?2 where myFixerAccounting.user.userId=?1"),
	@NamedQuery(name="deleteDataBasedOnQueryId" , query="delete from FixerAccounting myFixerAccounting  where myFixerAccounting.query.queryId=?1")
})
@Table(catalog = "fixit", name = "fixer_accounting")

@SqlResultSetMappings({
	@SqlResultSetMapping(
	        name = "fixerAccountingMapping",classes=@ConstructorResult(targetClass=FixerAccountingGroup.class,columns={@ColumnResult(name="fixer_id" , type=Integer.class),@ColumnResult(name="count"),@ColumnResult(name="amount_paid",type=Integer.class)})),
	        @SqlResultSetMapping(
	    	        name = "fixerAccountingUserGroup",classes=@ConstructorResult(targetClass=FixerAccountingGroup.class,columns={@ColumnResult(name="fixer_id" , type=Integer.class),@ColumnResult(name="count"),@ColumnResult(name="amount_paid",type=Integer.class),@ColumnResult(name="first_name",type=String.class),@ColumnResult(name="last_name",type=String.class),@ColumnResult(name="email",type=String.class)}))
})




@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "FixerAccounting")
public class FixerAccounting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * primary key user id
	 */
	@Column(name = "fixer_acc_id", length = 11, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	@GeneratedValue
	Integer fixerAccId;

	@Column(name = "amount_paid" )
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Double amountPaid;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "fixer_id", referencedColumnName = "user_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("FixerAccounting-User")
	User user;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "query_id", referencedColumnName = "query_id") })
	@XmlTransient
	@JsonIgnore
	@JsonBackReference("FixerAccounting-Query")
	Query query;
	
	
	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;

	public Integer getFixerAccId() {
		return fixerAccId;
	}

	public void setFixerAccId(Integer fixerAccId) {
		this.fixerAccId = fixerAccId;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void copy(FixerAccounting that) {
		setFixerAccId(that.getFixerAccId());
		setAmountPaid(that.getAmountPaid());
		setQuery(that.getQuery());
		setUser(that.getUser());
	}

	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("fixerAccId=[").append(fixerAccId).append("] ");
		/*buffer.append("user=[").append(user).append("] ");
		buffer.append("query=[").append(query).append("] ");*/
		buffer.append("amountPaid=[").append(amountPaid).append("] ");

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((fixerAccId == null) ? 0 : fixerAccId
				.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof FixerAccounting))
			return false;
		FixerAccounting equalCheck = (FixerAccounting) obj;
		if ((fixerAccId == null && equalCheck.fixerAccId != null)
				|| (fixerAccId != null && equalCheck.fixerAccId == null))
			return false;
		if (fixerAccId != null && !fixerAccId.equals(equalCheck.fixerAccId))
			return false;
		return true;
	}

}
