package com.fixit.domain.bo;

import java.math.BigInteger;

public class FixerAccountingGroup {

	private Integer fixer_id;
	
	private BigInteger count;
	
	private Double amount_paid;

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



	
}
