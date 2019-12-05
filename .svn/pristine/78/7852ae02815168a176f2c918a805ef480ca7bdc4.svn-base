package com.fixit.domain.bo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ValidatePassword {

	
	@NotEmpty
	@Size(min = 7,message="newpassword size must be greater than 6")
	private String newPassword;
	
	@NotEmpty
	@Size(min = 7,message="confirmpassword size must be greater than 6")
	private String confirmPassword;

	
    private String mismatchPassword;
    
    
	
	
	public String getMismatchPassword() {
		return mismatchPassword;
	}

	public void setMismatchPassword(String mismatchPassword) {
		this.mismatchPassword = mismatchPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	
	
}
