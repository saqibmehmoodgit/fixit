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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fixit.utility.DbConstants;
import com.fixit.utility.FileUpload;

/**
 * @author linchpin
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findUserById", query = "select myUser from User myUser where myUser.userId = ?1"),
		@NamedQuery(name = "findAdminByUserType", query = "select myUser from User myUser where myUser.userType = ?1"),
		@NamedQuery(name = "findAllFixer", query = "select myUser from User myUser where myUser.userType ='F' "),
		@NamedQuery(name = "findAllUserBasedOnIdList", query = "select myUser from User myUser WHERE myUser.userId IN (:userIdList)"),
		@NamedQuery(name = "getListOfFixerWithActivtedStatus", query = "select myUser from User myUser where  myUser.fixerStatus=?1 and myUser.userType =?2 "),
		@NamedQuery(name = "findFixerByCategory", query = "select myUser from User myUser WHERE myUser.userId IN ( select myUserCategory.user.userId  from UserCategory  myUserCategory  WHERE myUserCategory.categoryType.catId IN (:categoryList))"),
		@NamedQuery(name = "getListOfFavouriteFixerByFixerId", query = "select myUser from User myUser WHERE myUser.userId IN ( select myFavouriteFixer.fixerId  from FavouriteFixer  myFavouriteFixer  WHERE myFavouriteFixer.user.userId = ?1)"),
		@NamedQuery(name = "findActivatedFixerBySearchText", query = "select myUser from User myUser where myUser.userType='F' and myUser.fixerStatus='A' and( myUser.userName LIKE :name or myUser.email LIKE :name or myUser.firstName LIKE :name or myUser.lastName LIKE :name)"),
		@NamedQuery(name = "findUserByEmailOrUserName", query = "select myUser from User myUser where myUser.email = ?1 or myUser.userName = ?2"),
		@NamedQuery(name = "findUserByEmailForSettingsUpdate", query = "select myUser from User myUser where myUser.email = ?1 and myUser.email not in (?2)"),
		@NamedQuery(name = "findUserByUserNameForSettingsUpdate", query = "select myUser from User myUser where myUser.userName = ?1 and myUser.userName not in(?2)"),
		@NamedQuery(name = "findUserByEmail", query = "select myUser from User myUser where myUser.email = ?1 "),
		@NamedQuery(name = "findFixerByStatus", query = "select myUser from User myUser where myUser.fixerStatus = ?1 order by updatedAt DESC"),
		@NamedQuery(name = "findFixerCountByStatus", query = "select count(myUser.fixerStatus) from User myUser where myUser.fixerStatus = ?1 order by updatedAt DESC"),
		@NamedQuery(name = "getFixersBasedOnSearchAndCategory", query = "select myUser from User myUser where myUser.userId IN ( select myUserCategory.user.userId  from UserCategory  myUserCategory  WHERE myUserCategory.categoryType.catId IN (:categoryList)) and (myUser.userType='F' and myUser.fixerStatus='A') and ( myUser.userName LIKE :name) order by createdAt DESC"),
		@NamedQuery(name = "getFixersBasedOnSearch", query = "select myUser from User myUser where (myUser.userType='F' and myUser.fixerStatus='A') and ( myUser.userName LIKE :name) order by createdAt DESC"),
		@NamedQuery(name = "updateUserStatus", query = "update User myUser set myUser.fixerStatus=?2 where myUser.userId=?1 "),
		@NamedQuery(name = "getAllMembers", query = "select myUser from User myUser where myUser.userType='C'"),
		@NamedQuery(name = "getAllFixers", query = "select myUser from User myUser where myUser.userType='F'"),
		@NamedQuery(name = "searchListOfUserUsingSearchTextCount", query = "select count(myUser) from User myUser where myUser.userType='C'"),
		@NamedQuery(name = "searchListOfChatUserUsingSearchTextCount", query = "select count(myUser) from User myUser where  (myUser.userType='C' or (myUser.userType='F' and myUser.fixerStatus='A'))"),
		@NamedQuery(name = "searchListOfUserFixersUsingSearchTextCount", query = "select count(myUser) from User myUser where myUser.userType='F'"),
		@NamedQuery(name = "searchListOfUserUsingSearchText", query = "select myUser from User myUser where (myUser.userType='C') and( myUser.userName LIKE :name or myUser.email LIKE :name or myUser.firstName LIKE :name or myUser.lastName LIKE :name)"),
		@NamedQuery(name = "searchListOfChatUserUsingSearchText", query = "select myUser from User myUser where  (myUser.userType='C' or (myUser.userType='F' and myUser.fixerStatus='A')) and ( myUser.userName LIKE :name or myUser.email LIKE :name or myUser.firstName LIKE :name or myUser.lastName LIKE :name)"),
		@NamedQuery(name = "searchListOfUserFixersUsingSearchText", query = "select myUser from User myUser where (myUser.userType='F') and( myUser.userName LIKE :name or myUser.email LIKE :name or myUser.firstName LIKE :name or myUser.lastName LIKE :name)"),
		@NamedQuery(name = "deleteUserByUserId", query = "delete from  User myUser where myUser.userId=?1 "),
		@NamedQuery(name = "getAllMemberAndActivatedFixers", query = "select myUser from User myUser where  myUser.userType='C' or (myUser.userType='F' and myUser.fixerStatus='A')"),

		@NamedQuery(name = "getFixersBasedOnSearchCategoryAndCountry", query = "select myUser from User myUser where myUser.userId IN ( select myUserCategory.user.userId  from UserCategory  myUserCategory  WHERE myUserCategory.categoryType.catId IN (:categoryList)) and (myUser.userType='F' and myUser.fixerStatus='A') and (myUser.country LIKE :country)  and ( myUser.userName LIKE :name) order by createdAt DESC"),
		@NamedQuery(name = "getFixersBasedOnSearchCatRatingAndCountry", query = "select myUser from User myUser where (myUser.userType='F' and myUser.fixerStatus='A') and (myUser.country LIKE :country) and ( myUser.userName LIKE :name or myUser.email LIKE :name or myUser.firstName LIKE :name or myUser.lastName LIKE :name) order by createdAt DESC"),
		@NamedQuery(name = "findUserBySource", query = "select user from User user where user.source = ?1"),
		@NamedQuery(name = "findCountUserBySource", query = "select count(user) from User user where user.source = ?1"),

		@NamedQuery(name = "findUserBySourceAndName", query = "select user from User user where user.source = ?1  and (user.userName LIKE ?2 or user.email LIKE ?2 or user.firstName LIKE ?2 or user.lastName LIKE ?2)"),
		@NamedQuery(name = "findCountUserBySourceAndName", query = "select count(user) from User user where user.source = ?1  and (user.userName LIKE ?2 or user.email LIKE ?2 or user.firstName LIKE ?2 or user.lastName LIKE ?2)"),
		@NamedQuery(name = "updateFixerRatingByAdmin", query = "update User user set user.rating = ?1 where user.userId = ?2 and user.userType='F'")

})

@Table(catalog = "fixit", name = "user")
@SqlResultSetMappings({ @SqlResultSetMapping(name = "userMapping", entities = {
		@EntityResult(entityClass = User.class, fields = {}) }), })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "fixit/com/fixit/domain/vo", name = "User")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * primary key user id
	 */
	@Column(name = "user_id", nullable = false)
	@Basic(fetch = FetchType.LAZY)
	@Id
	@XmlElement
	@GeneratedValue
	Integer userId;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("FixerAccounting-User")
	java.util.Set<com.fixit.domain.vo.FixerAccounting> fixerAccounting;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("FixerCategory-User")
	java.util.Set<com.fixit.domain.vo.UserCategory> fixerCategory;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("ChatMessage-User")
	java.util.Set<com.fixit.domain.vo.ChatMessage> chatMessages;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("QueryFixers-User")
	java.util.Set<com.fixit.domain.vo.QueryFixers> queryFixers;

	@OneToMany(mappedBy = "userFixer", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("QueryAppliedFixers-User")
	java.util.Set<com.fixit.domain.vo.QueryAppliedFixers> queryAppliedFixers;

	@OneToMany(mappedBy = "userMember", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("QueryAppliedFixers-User")
	java.util.Set<com.fixit.domain.vo.QueryAppliedFixers> queryAppliedMembers;

	public java.util.Set<com.fixit.domain.vo.QueryFixers> getQueryFixers() {
		return queryFixers;
	}

	public void setQueryFixers(java.util.Set<com.fixit.domain.vo.QueryFixers> queryFixers) {
		this.queryFixers = queryFixers;
	}

	public java.util.Set<com.fixit.domain.vo.ChatMessage> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(java.util.Set<com.fixit.domain.vo.ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public java.util.Set<com.fixit.domain.vo.FavouriteFixer> getFavouriteFixer() {
		return favouriteFixer;
	}

	public void setFavouriteFixer(java.util.Set<com.fixit.domain.vo.FavouriteFixer> favouriteFixer) {
		this.favouriteFixer = favouriteFixer;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("UserIndustry-User")
	java.util.Set<com.fixit.domain.vo.UserIndustry> userIndustry;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("UserCredit-User")
	com.fixit.domain.vo.UserCredit userCredits;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("UserAccounting-User")
	java.util.Set<com.fixit.domain.vo.UsersAccounting> usersAccounting;

	public java.util.Set<UserCategory> getFixerCategory() {
		return fixerCategory;
	}

	public void setFixerCategory(java.util.Set<UserCategory> fixerCategory) {
		this.fixerCategory = fixerCategory;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("Query-User")
	java.util.Set<com.fixit.domain.vo.Query> query;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("FixerRating-User")
	java.util.Set<com.fixit.domain.vo.FixerRating> fixerRating;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("FavouriteFixer-User")
	java.util.Set<com.fixit.domain.vo.FavouriteFixer> favouriteFixer;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@JsonIgnore
	@JsonManagedReference("Testimonial-User")
	java.util.Set<com.fixit.domain.vo.Testimonial> testimonial;

	@Column(name = "email", length = 255)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String email;

	@Column(name = "password", length = 150)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String password;

	@Column(name = "user_type")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String userType;

	@Column(name = "first_name", length = 100)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String firstName;

	@Column(name = "last_name", length = 100)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String lastName;

	@Column(name = "company_name", length = 255)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String companyName;

	@Column(name = "job_title", length = 100)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String jobTitle;

	@Column(name = "address")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String address;

	@Column(name = "paypal_id")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String paypalId;

	public String getPaypalId() {
		return paypalId;
	}

	public void setPaypalId(String paypalId) {
		this.paypalId = paypalId;
	}

	@Column(name = "city", length = 100)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String city;

	@Column(name = "state", length = 100)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String state;

	@Column(name = "zip", length = 100)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String zip;

	@Column(name = "country", length = 100)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String country;

	@Column(name = "phone_number", length = 100)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String phoneNumber;

	@Column(name = "alert_type")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String alertType;

	@Column(name = "email_alert")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String emailAlert;

	@Column(name = "profile_photo")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String profilePhoto;

	@Column(name = "profile_icon")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String profileIcon;

	@Column(name = "fixer_status")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String fixerStatus;

	@Column(name = "user_name")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String userName;

	@Column(name = "time_zone")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String timeZone;

	@Column(name = "source")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String source = "0";

	@Column(name = "track_credit")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String trackCredit = "N";

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Column(name = "overview")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String overview;

	@Column(name = "linkedin_profile")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String linkedinProfile;

	public String getLinkedinProfile() {
		return linkedinProfile;
	}

	public void setLinkedinProfile(String linkedinProfile) {
		this.linkedinProfile = linkedinProfile;
	}

	@Column(name = "subscription_payment")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	String subcPayment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Calendar createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Calendar lastLogin;

	public Calendar getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Calendar lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Column(name = "user_rating")
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Integer rating = 0 ;

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	@Basic(fetch = FetchType.LAZY)
	@XmlElement
	Calendar updatedAt;

	public java.util.Set<com.fixit.domain.vo.FixerRating> getFixerRating() {
		return fixerRating;
	}

	public void setFixerRating(java.util.Set<com.fixit.domain.vo.FixerRating> fixerRating) {
		this.fixerRating = fixerRating;
	}

	public String getEmailAlert() {
		return emailAlert;
	}

	public void setEmailAlert(String emailAlert) {
		this.emailAlert = emailAlert;
	}

	public String getProfileIcon() {
		return profileIcon;
	}

	public void setProfileIcon(String profileIcon) {
		this.profileIcon = profileIcon;
	}

	public java.util.Set<com.fixit.domain.vo.FixerAccounting> getFixerAccounting() {
		return fixerAccounting;
	}

	public void setFixerAccounting(java.util.Set<com.fixit.domain.vo.FixerAccounting> fixerAccounting) {
		this.fixerAccounting = fixerAccounting;
	}

	public java.util.Set<com.fixit.domain.vo.Query> getQuery() {
		return query;
	}

	public void setQuery(java.util.Set<com.fixit.domain.vo.Query> query) {
		this.query = query;
	}

	public java.util.Set<com.fixit.domain.vo.Testimonial> getTestimonial() {
		return testimonial;
	}

	public void setTestimonial(java.util.Set<com.fixit.domain.vo.Testimonial> testimonial) {
		this.testimonial = testimonial;
	}

	public java.util.Set<com.fixit.domain.vo.UserIndustry> getUserIndustry() {
		return userIndustry;
	}

	public void setUserIndustry(java.util.Set<com.fixit.domain.vo.UserIndustry> userIndustry) {
		this.userIndustry = userIndustry;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getProfilePhoto() {
		if (!profilePhoto.contains("https://s3-us-west-2.amazonaws.com")) {
			if (userType.equals(DbConstants.FIXER)) {
				return FileUpload.fixerProfilePath(profilePhoto);
			} else {
				return FileUpload.memberProfilePath(profilePhoto);
			}
		} else {
			return profilePhoto;
		}
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getFixerStatus() {
		return fixerStatus;
	}

	public void setFixerStatus(String fixerStatus) {
		this.fixerStatus = fixerStatus;
	}

	public String getSubcPayment() {
		return subcPayment;
	}

	public void setSubcPayment(String subcPayment) {
		this.subcPayment = subcPayment;
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
	public void copy(User that) {
		setUserId(that.getUserId());
		setEmail(that.getEmail());
		setPassword(that.getPassword());
		setUserType(that.getUserType());
		setFirstName(that.getFirstName());
		setLastName(that.getLastName());
		setCompanyName(that.getCompanyName());
		setJobTitle(that.getJobTitle());
		setAddress(that.getAddress());
		setCity(that.getCity());
		setState(that.getState());
		setZip(that.getZip());
		setCountry(that.getCountry());
		setPhoneNumber(that.getPhoneNumber());
		setAlertType(that.getAlertType());
		setProfilePhoto(that.getProfilePhoto());
		setFixerStatus(that.getFixerStatus());
		setSubcPayment(that.getSubcPayment());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		setFixerAccounting(that.getFixerAccounting());
		setQuery(that.getQuery());
		setTestimonial(that.getTestimonial());
		setOverview(that.getOverview());
		setUserName(that.getUserName());
		setUserIndustry(that.getUserIndustry());
		setLastLogin(that.getLastLogin());
		setFavouriteFixer(that.getFavouriteFixer());
		setEmailAlert(that.getEmailAlert());
		setPaypalId(that.getPaypalId());
		setQueryAppliedFixers(that.getQueryAppliedFixers());
		setQueryAppliedMembers(that.getQueryAppliedMembers());
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("userId=[").append(userId).append("] ");
		buffer.append("email=[").append(email).append("] ");
		buffer.append("password=[").append(password).append("] ");
		buffer.append("userType=[").append(userType).append("] ");
		buffer.append("firstName=[").append(firstName).append("] ");
		buffer.append("lastName=[").append(lastName).append("] ");
		buffer.append("companyName=[").append(companyName).append("] ");
		buffer.append("jobTitle=[").append(jobTitle).append("] ");
		buffer.append("address=[").append(address).append("] ");
		buffer.append("city=[").append(city).append("] ");
		buffer.append("state=[").append(state).append("] ");
		buffer.append("zip=[").append(zip).append("] ");
		buffer.append("country=[").append(country).append("] ");
		buffer.append("phoneNumber=[").append(phoneNumber).append("] ");
		buffer.append("alerType=[").append(alertType).append("] ");
		buffer.append("profilePhoto=[").append(profilePhoto).append("] ");
		buffer.append("subcPayment=[").append(subcPayment).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
		buffer.append("userName=[").append(userName).append("] ");
		buffer.append("overview=[").append(overview).append("] ");
		buffer.append("lastLogin=[").append(lastLogin).append("] ");
		buffer.append("emailAlert=[").append(emailAlert).append("] ");
		buffer.append("paypalId=[").append(paypalId).append("] ");

		/*
		 * buffer.append("fixerAccounting=[").append(fixerAccounting).append(
		 * "] "); buffer.append("query=[").append(query).append("] ");
		 * buffer.append("testimonial=[").append(testimonial).append("] ");
		 */

		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((userId == null) ? 0 : userId.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof User))
			return false;
		User equalCheck = (User) obj;
		if ((userId == null && equalCheck.userId != null) || (userId != null && equalCheck.userId == null))
			return false;
		if (userId != null && !userId.equals(equalCheck.userId))
			return false;
		return true;
	}

	@PrePersist
	private void prePersist() {
		this.createdAt = Calendar.getInstance();
	}

	public com.fixit.domain.vo.UserCredit getUserCredits() {
		return userCredits;
	}

	public void setUserCredits(com.fixit.domain.vo.UserCredit userCredits) {
		this.userCredits = userCredits;
	}

	public java.util.Set<com.fixit.domain.vo.UsersAccounting> getUsersAccounting() {
		return usersAccounting;
	}

	public void setUsersAccounting(java.util.Set<com.fixit.domain.vo.UsersAccounting> usersAccounting) {
		this.usersAccounting = usersAccounting;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public java.util.Set<com.fixit.domain.vo.QueryAppliedFixers> getQueryAppliedFixers() {
		return queryAppliedFixers;
	}

	public void setQueryAppliedFixers(java.util.Set<com.fixit.domain.vo.QueryAppliedFixers> queryAppliedFixers) {
		this.queryAppliedFixers = queryAppliedFixers;
	}

	public java.util.Set<com.fixit.domain.vo.QueryAppliedFixers> getQueryAppliedMembers() {
		return queryAppliedMembers;
	}

	public void setQueryAppliedMembers(java.util.Set<com.fixit.domain.vo.QueryAppliedFixers> queryAppliedMembers) {
		this.queryAppliedMembers = queryAppliedMembers;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTrackCredit() {
		return trackCredit;
	}

	public void setTrackCredit(String trackCredit) {
		this.trackCredit = trackCredit;
	}

}
