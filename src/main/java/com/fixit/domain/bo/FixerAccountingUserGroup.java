package com.fixit.domain.bo;

import java.math.BigInteger;

public class FixerAccountingUserGroup {
	private Integer fixer_id;

	private BigInteger count;

	private Double amount_paid;

	private String first_name;

	private String last_name;

	private String email;

	public Integer getFixer_id() {
		return fixer_id;
	}

	public void setFixer_id(Integer fixer_id) {
		this.fixer_id = fixer_id;
	}

	public BigInteger getCount() {
		return count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}

	public Double getAmount_paid() {
		return amount_paid;
	}

	public void setAmount_paid(Double amount_paid) {
		this.amount_paid = amount_paid;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
