package com.fixit.domain.bo;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class BlogBo {

	@NotEmpty(message = "Please enter Title")
	private String blogTitle;
	@NotEmpty(message = "Please enter Blog Description")
	private String blogDescription;
	private List<MultipartFile> blogFile;
	private String button;
	private String reason;
	@NotEmpty(message = "Please select Categories")
	private List<Integer> catId;

	public List<MultipartFile> getBlogFile() {
		return blogFile;
	}

	public void setBlogFile(List<MultipartFile> blogFile) {
		this.blogFile = blogFile;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogDescription() {
		return blogDescription;
	}

	public void setBlogDescription(String blogDescription) {
		this.blogDescription = blogDescription;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<Integer> getCatId() {
		return catId;
	}

	public void setCatId(List<Integer> catId) {
		this.catId = catId;
	}

}
