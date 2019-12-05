package com.fixit.domain.bo;

import com.fixit.domain.vo.User;

public class UsersAccountingBo {

	private User user;
    private String type;
    private double amount;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
