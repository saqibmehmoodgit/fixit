<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<title>ERPfixers | Fixer Dashboard</title>
<%-- <script src="${pageContext.request.contextPath}/js/gantt/amcharts.js"></script>
<script src="${pageContext.request.contextPath}/js/gantt/serial.js"></script>
<script src="${pageContext.request.contextPath}/js/gantt/light.js"></script>
<script src="${pageContext.request.contextPath}/js/gantt/gantt.js"></script> --%>
<script src="${pageContext.request.contextPath}/js/pie/amcharts.js"></script>
<script src="${pageContext.request.contextPath}/js/pie/pie.js"></script>
<script src="${pageContext.request.contextPath}/js/pie/light.js"></script>
<script>
//var countryListJson = ${countryList};
var userCategory =  ${categoryJSONByUser};
var userIndustry =  ${industJSONByUser};
var allCategoryJson = ${allCategoryJson};
var parentCatJson = ${parentCategoryJson};
var groupId = ${groupId};
var userId = ${myUser.userId};
var averageFixerRating = ${averageFixerRating};
var  countMsg = ${countMsg};
var userEmail = '${myUser.email}';
var userName = '${myUser.userName}';
var paypalId = '${myUser.paypalId}';
var timeZone = '${userTimeZone}';
var country =  "${myUser.country}";
var array =  ${pieChartData};
var linkedInUrl = '${myUser.linkedinProfile}';
/* var startDate = frequencyDuration.startDate;
var array = frequencyDuration.frequencyDurations; */
</script>
<!-- <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
 -->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jquery-ui.min.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/cityrelatedinfo.js"></script>
<script
	src="${pageContext.request.contextPath}/js/pages/fixerDashboard.js"></script>

</head>
<body>

	<div id="pagewrap">
		<!-- Work Here start-->
		<section class="wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
						<div class="profileSetting wow fadeIn">
							<div class="settingDiv" style="display: none;">
								<h1>Settings</h1>
								<div class="halfBorder mtb"></div>
								<div class="row">
									<div class="col-lg-7 col-md-7 col-sm-12 col-xs-12">
										<form:form id="actionSetting" method="POST"
											action="fixerSetting" class="form-horizontal"
											modelAttribute="fixerSetting" commandName="fixerSetting">
											<form:input id="imageUrlSetting" path="imageUrl"
												type="hidden" />

											<div class="form-group">
												<label class="control-label col-sm-4">Email:</label>
												<div class="col-sm-8">
													<span class="alertF" id="spanUserEmail">PPlease
														enter your email address.</span>

													<form:input path="email" type="email" class="form-control"
														id="userEmail" />
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4">Username:</label>
												<div class="col-sm-8">
													<span class="alertF" id="spanUserName">Please enter
														a username.</span>

													<form:input path="userName" type="text"
														class="form-control" id="userName" readonly="true" />
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4">Time Zone:</label>
												<div class="col-sm-8">
													<select name="timeZone" id="timeZone" class="form-control">
														<c:forEach var="current" items="${timeZoneList}">
															<option value="${current}">
																<c:out value="${current}" />
															</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4">PayPal ID:</label>
												<div class="col-sm-8">
													<span class="alertF" id="spanPaypalId"></span>

													<form:input path="paypalId" type="text"
														placeholder="PayPal Id" class="form-control" id="paypalId" />
												</div>
											</div>
											<div class="form-group" style="padding: 2px 0px">
												<label class="control-label col-sm-4" style="margin-top:5px">Send Alerts:</label>
												<div class="col-sm-8">
													<c:choose>
														<c:when test="${myUser.emailAlert=='Y'}">
															<input name="emailAlert" id="emailAlert"
																onclick="onemailAlertCheckBox()" type="checkbox" checked />
															<label for="Send Alerts">&nbsp;</label>
														</c:when>
														<c:otherwise>
															<input name="emailAlert" id="emailAlert"
																onclick="onemailAlertCheckBox()" type="checkbox" />
															<label for="Send Alerts">&nbsp;</label>
														</c:otherwise>
													</c:choose>
												</div>
											</div>

											<div id="staticPass" class="form-group">
												<label class="control-label col-sm-4">Password:</label>
												<div class="col-sm-8">

													<span class="alertF" id="spanPassword">old password
														does not match.</span> <input type="password"
														placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
														class="form-control" id="password" /> <a
														onclick="changePassDiv()" href="javaScript:void(0)"
														class="changeLink">Change</a>
												</div>

											</div>

											<div id="passDiv" style="display: none">
												<div class="form-group">
													<label class="control-label col-sm-4"><span>Re-Enter
															Current&nbsp;Password:</span></label>
													<div class="col-sm-8">
														<span class="alertF" id="spanOldPassword">old
															password does not match.</span>

														<form:input path="oldPasword" type="password"
															class="form-control" id="oldPassword" />
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-4">New Password:</label>
													<div class="col-sm-8 relative">
														<span class="alertF" id="spanNewPassword">Please
															enter a password.</span>
														<form:input path="newPasword" type="password"
															class="form-control" id="newPassword" />
														<a class="tooltipTrigger">?</a>
														<div class="tooltipTarget">
															<ul>
																<li id="newChar8Len"><i id="newChar8LenI"
																	class="fa fa-check-circle"></i> 8 or more characters</li>
																<li id="newUpperlower"><i id="newUpperlowerI"
																	class="fa fa-check-circle"></i> Upper & lowercase
																	letters</li>
																<li id="newOneNumb"><i id="newOneNumbI"
																	class="fa fa-check-circle"></i> At least one number</li>
															</ul>
														</div>
													</div>

												</div>
												<div class="form-group">
													<label class="control-label col-sm-4"><span>Re-Type
															Password:</span></label>
													<div class="col-sm-8">
														<span class="alertF" id="spanReTypePassword">Please
															retype your password.</span>

														<form:input path="confirmPassword" type="password"
															class="form-control" id="reTypePassword" />
														<a class="tooltipTrigger">?</a>
														<div class="tooltipTarget">
															<ul>
																<li id="reCheckPass"><i id="reCheckPassI"
																	class="fa fa-check-circle"></i> Re-type your password</li>
															</ul>
														</div>
													</div>

												</div>
											</div>


										</form:form>
									</div>
								</div>

							</div>

							<div class="MyIndustries" id="MyIndustries">
								<h1 class="h1-underline">My Industries</h1>

								<ul class="MyIndus">
									<c:forEach var="industryItem" items="${industry}">
										<li><input name="indestries"
											id="industryItem${industryItem.indstId}"
											value="${industryItem.indstId}" type="checkbox"> <label
											for="industryItem${industryItem.indstId}">${industryItem.indstName}</label>
										</li>
									</c:forEach>
								</ul>
								<div class="MyIndusSC">
									<button class="saveChange" onclick="saveIndustry()"
										id="savechange">Save</button>
									<button class="cancel" id="MIClose">Cancel</button>
								</div>

							</div>


							<div class="row">
								<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
									<div class="profileBg">
										<div class="row">
											<div class="col-lg-4 col-md-4 col-sm-4 col-xs-5">
												<div class="prifileLeft">
													<div class="profilePic">
														<c:choose>
													    <c:when test="${myUser.profilePhoto == ''}">
													       <img src="../images/profile-pic.jpg" id="myProfileImage"
															onerror="this.src ='../images/profile-pic.jpg'">
													    </c:when>
													   
													    <c:otherwise>
													       <img src="${myUser.profilePhoto}" id="myProfileImage"
															onerror="this.src ='../images/profile-pic.jpg'">
													    </c:otherwise>
													</c:choose>
													
														<%-- <img src="${myUser.profilePhoto}" id="myProfileImage"
															onerror="this.src ='../images/profile-pic.jpg'"> --%>
														<div class="uploadimg">
															<input id="multipartFile" value="" type="file"
																onchange="uploadProfileImage()"> <span
																id="uploadtxt" class="uploadtxt">Upload Photo</span>
														</div>
														<div class="upLoader">
															<img
																src="${pageContext.request.contextPath}/images/loader.gif">
														</div>
														<img src="../flags/${myUser.country}.png" class="userflag">
													</div>
													<ul class="settingnav">
														<li class="edit"><a href="javascript:void(0)"><i
																data-icon="c" class="icon"></i> Edit Profile</a>
															<div id="editProfile" class="settingsbtn"
																style="display: none;">
																<a href="" id="editCancel" class="cancel">Cancel</a> <a
																	href="javascript:void(0)" class="saveC"
																	onclick="editProfile();">Save</a>
															</div></li>
														<li class="settings"><a href="javascript:void(0)"><i
																data-icon="h" class="icon"></i> Settings</a>
															<div id="setting" class="settingsbtn"
																style="display: none;">
																<a href="" id="settingCancel" class="cancel">Cancel</a>
																<a href="javascript:void(0)" id="saveSetting"
																	class="saveC">Save</a>
															</div></li>
														<li class="saptips"><a target="_blank"
															href="http://www.erpfixers.com/resources/"><i
																data-icon="f" class="icon"></i> SAP Tips</a></li>
													</ul>
												</div>
											</div>
											<div class="col-lg-8 col-md-8 col-sm-8 col-xs-7">
												<c:if test="${msgType == 'success'}">
													<div style="margin-top: 18px; margin-right: 15px;"
														class="alert alert-success fade in">
														<a title="close" aria-label="close" data-dismiss="alert"
															class="close" href="#">×</a> ${message}
													</div>
												</c:if>
												<c:if test="${msgType == 'Error'}">

													<div style="margin-top: 18px; margin-right: 15px;"
														class="alert alert-danger fade in">
														<a title="close" aria-label="close" data-dismiss="alert"
															class="close" href="#">×</a> ${message}
													</div>
												</c:if>
												<div id="profileEditRight" class="prifileRight">
													<!-- <div id="profile" class="editprofileDiv">  -->
													<form:form id="editUser" method="POST" action="editProfile"
														class="form-horizontal" modelAttribute="userBo"
														commandName="userBo" enctype="multipart/form-data">
														<form:input id="imageUrl" path="imageUrl" type="hidden" />

														<div class="headTitle">
															<div class="pRelative">
																<span class="alertF" id="spanName">Please enter
																	your name.</span>
																<form:input id="name" placeholder="name" type="hidden"
																	path="name"
																	value="${myUser.firstName} ${myUser.lastName}"
																	class="titleName" />
																<h1 id="headingName">${myUser.firstName}
																	${myUser.lastName}</h1>
															</div>
															<div class="pRelative">
																<!-- <span class="alertF" id="spancompanyName">Please
																	enter a company name.</span> -->
																<form:input id="companyName" type="hidden"
																	path="companyName" value="${myUser.companyName}"
																	class="Designation" placeholder="Company" />
																<span id="headingCompanyName">${myUser.companyName}</span>
															</div>



														</div>
														<div id="halfFullBorder" class="halfBorder"></div>
														<div class="location">
															<span class="location100"> <span class="locations">Location:</span>
																<div class="show_not">
																	<strong>${myUser.city}, ${myUser.country}</strong>
																</div>
																<div class="showornot">
																	<span class="alertF" id="spanCountry">Please
																		enter your country.</span>
																	<%-- 	<form:input path="country" class="cuntry typeahead"
																		type="text" value="${myUser.country}"
																		id="smart_search" readOnly="true" /> --%>
																	<select name="country" style="font-weight: bold;"
																		id="country" class="cuntry">
																		<c:forEach var="country" items="${countryList}">
																			<option value="${country.countryName}">
																				<c:out value="${country.countryName}" />
																			</option>
																		</c:forEach>
																	</select> <span class="alertF" id="spanCity">Please enter
																		your city.</span>
																	<form:input path="city" style="font-weight: bold;"
																		id="f_elem_city" type="text" value="${myUser.city}"
																		class="city" readonly="true" placeholder="City" />
																</div>
															</span> <span class="linkdin100"><img
																src="${pageContext.request.contextPath}/images/in.png"
																alt="">: <span class="alertF"
																id="spanlinkedinProfile">Please enter your
																	LinkedIn url.</span> <strong id="anchorLinkedIn"><a
																	id="linkedInUrl" target="_blank"
																	href="${myUser.linkedinProfile}">
																		${myUser.linkedinProfile} </a></strong> <form:input
																	id="linkedinProfile" type="hidden"
																	path="linkedinProfile"
																	value="${myUser.linkedinProfile}" class="linkdin"
																	placeholder="Https//:" /></span> <span class="fixsin">Fixer
																Since: <strong>${userSince}</strong>
															</span> <span class="fixsin">Time Zone: <strong>${myUser.timeZone}</strong>
															</span>
														</div>
														<div class="clearfix"></div>
														<div class="pRelative">
															<span class="alertF" style="top: 7px;"
																id="spanOverviewText">Please enter your bio.</span>

															<div id="textauto">
																<textarea id="overviewText" name="overview"
																	placeholder="Bio" readonly="true">${myUser.overview}</textarea>
															</div>
														</div>
													</form:form>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
									<div class="my-categories">
										<div class="headC">
											<h1>My Categories</h1>

										</div>

										<div class="categoriesDiv">

											<div id="categorySpanDiv">
												<c:forEach var="categoryByUserItem"
													items="${categoryByUser}">
													<span id="categorySpan${categoryByUserItem.catId}">${categoryByUserItem.catgName}<i
														data-icon="y" class="icon"
														onclick="removeSelectedCategory(${categoryByUserItem.catId})"></i></span>
												</c:forEach>
											</div>
											<div class="clearfix"></div>
										</div>
										<span class="addnew margin-left20" data-toggle="modal"
											data-target="#myModal" data-backdrop="static"
											data-keyboard="false">Add New <i data-icon="p"
											class="icon"></i></span>
										<!-- poppup -->
										<div class="modal fade" id="myModal" role="dialog">
											<div class="modal-dialog modal-lg">
												<div class="modal-content">
													<div class="modal-body">
														<h1 class="h1-underline">Choose Categories</h1>
														<ul class="ChooseCategories">

															<c:forEach var="parentCat" items="${parentCategory}">
																<li><div class="categoriesT"
																		id="categoriesT${parentCat.catId}">${parentCat.catgName}</div>
																	<div class="categoriesList" style="display: none"
																		id="categoriesL${parentCat.catId}">
																		<ul class="MyIndus">
																			<c:forEach var="childCat" items="${allCategory}">
																				<c:if test="${childCat.parentId==parentCat.catId}">
																					<li><input name="categories"
																						id="catId${childCat.catId}"
																						value="${childCat.catId}" type="checkbox">
																						<label for="categories">${childCat.catgName}</label>
																					</li>
																				</c:if>
																			</c:forEach>
																		</ul>
																	</div></li>
															</c:forEach>
														</ul>



													</div>
													<div class="modal-footer">

														<button class="saveChange width100B"
															onclick="saveCategory()" data-dismiss="modal">Save</button>
														<button class="cancel width100B" data-dismiss="modal"
															onclick="cancelCategory()">Cancel</button>
													</div>
												</div>
											</div>
										</div>
										<!-- poppup -->




									</div>
									<!-- div class="my-industries">
        <div class="headC">
         <h1>My Industries</h1>
         <i data-icon="I" class="icon"></i>
         
        </div>
        
        <div class="categoriesDiv">
        <div id="industrySpanDiv">
         <c:forEach  var="industryByUserItem" items="${industryByUser}">
             <span id="industrySpan${industryByUserItem.indstId}">${industryByUserItem.indstName}<i data-icon="y" class="icon" onclick="removeSelectedIndustry(${industryByUserItem.indstId})"></i></span>
         </c:forEach>
        </div>
       
         <span class="addnew"  id="AddNew">Add New <i data-icon="p" class="icon"></i></span>
         <div class="clearfix"></div>
        </div>
        
       </div-->
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
						<div class="resolvedRequests wow fadeIn" data-wow-delay="0.5s">
							<h1 class="small-txt">Resolved Requests</h1>
							<c:choose>
								<c:when test="${empty resolvedQueries}">
									<h2>0</h2>
								</c:when>
								<c:otherwise>
									<h2>${resolvedQueries}</h2>
								</c:otherwise>
							</c:choose>
							<a
								href="${pageContext.request.contextPath}/fixer/request?msgKey=R&amp;status=Closed"
								class="gotoreq">Go to Requests</a>
						</div>
						<div id="messageTab" class="newmessage wow fadeIn"
							data-wow-delay="0.5s">
							<div id="message_count_id" class="no">${countMsg}</div>
							Notifications
						</div>
						<div class="messageChat">
							<div class="messageWarpC">
								<!-- // chat // -->
								<div class="popupChat">
									<div class="popupChatDiv">
										<div class="popupcahtH">
											<h1>Message to Admin</h1>
											<div class="closeMessage">
												<i data-icon="y" class="icon"></i>
											</div>
										</div>
										<div class="">
											<div style="visibility: visible;" class="cahatingBox">
												<div id="chatbgBody" class="chatbgBody">

													<c:forEach var="message" items="${messagesSet}">
														<c:choose>

															<c:when test="${message.user.userId == myUser.userId}">

																<div class="chatTxtR">${message.chatMessage}
																	<span>${message.createdAt}</span>

																</div>
																<div class="clearfix"></div>
															</c:when>

															<c:otherwise>
																<div class="chatTxt">${message.chatMessage}
																	<span>${message.createdAt}</span>
																</div>
																<div class="clearfix"></div>

															</c:otherwise>
														</c:choose>
													</c:forEach>

												</div>
												<div id="chatload" class="chatLoad">Please wait while
													loading...</div>
												<div class="chatbgFooter">
													<textarea placeholder="Type here..." id="chatTextArea"></textarea>
													<%-- <span> <input value="" id="documentUpload"
														onchange="uploadChatDocument()" type="file"> <img
														src="${pageContext.request.contextPath}/images/attachment.png"
														alt="">
													</span> --%>

													<div id="chatsend" class="chatsend">Send</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!--// chat //  -->
							</div>
						</div>
					</div>
				</div>
				<!-- // 1 row // -->

				<div class="row">
					<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
						<div class="averageResponse wow fadeIn" data-wow-delay="1s">
							<h2>Resolved within deadline</h2>
							<h1>
							
							
								<c:if test="${responseTime ne null && responseTime ne 0}">
									${responseTime}%
								</c:if>
								<c:if test="${responseTime eq 0 or empty responseTime}">
														0%
							
							
								</c:if>
							</h1>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
						<div class="reviews wow fadeIn" data-wow-delay="1.5s">
							<h2>
								Reviews
								<div id="averageFixerRating"></div>
								<span>AVERAGE REVIEW</span>
							</h2>
							<div id="graph_container" class="average-review-graph"
								style="height: 437px;"></div>


						</div>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
						<div class="frequency wow fadeIn" data-wow-delay="2s">
							<h2>Requests Resolved per Module</h2>
							<!-- <h1></b><span id="chartForMonth"></span></h1> -->
							<div id="chartdiv"></div>
							<!-- 	 <div  id="leftGraphBtn" class="leftGraphBtn" onclick="showPreviousGiattChartForFixer();"></div>
  							 <div id="rightGraphBtn" class="rightGraphBtn" onclick="showNextGiattChartForFixer();"></div> -->
						</div>
					</div>
				</div>

			</div>
		</section>
		<!--  <script type="text/javascript">
   var d = stringToDate(startDate,'dd/MM/yyyy','/');
   var locale = "en-us";
    document.getElementById("chartForMonth").innerHTML = d.toLocaleString(locale, { month: "long" })+" " + d.getFullYear();  
    d.setMonth(d.getMonth()-1);    
    document.getElementById("leftGraphBtn").innerHTML = d.toLocaleString(locale, { month: "short" })+" " + d.getFullYear(); 
    d = stringToDate(startDate,'dd/MM/yyyy','/');
    d.setMonth(d.getMonth()+1);
    document.getElementById("rightGraphBtn").innerHTML = d.toLocaleString(locale, { month: "short" })+" " + d.getFullYear(); 
</script> -->
	</div>
	

	<div id="popup" style=" display: none;">    
    	<div id="inpopup">
    	
        	<span class="closepopup"><i data-icon="y" class="icon"></i></span>
            
            <div id="popupcahtH">
           <h1> Please upload your profile picture</h1>
</div>
        </div>
    </div>
    
    <!-- <script type="text/javascript">
    jQuery(window).load(function(e) { 
    	jQuery('#popup').fadeIn(1000);
	});
    
    
    jQuery(document).ready(function($){
    	
    	
    	
	    jQuery('.quickconnect').click(function(){
			jQuery('#popup').fadeIn(1000);
			jQuery('body').addClass('overlay');
			var popuph = document.getElementById('popup').offsetHeight;	
			var inpopuph = document.getElementById('inpopup').offsetHeight;
			if(popuph>500){					
				jQuery('#inpopup').css('margin',+(popuph-inpopuph)/2+'px auto');
			}
		});
		
		jQuery('.closepopup').click(function(){
			jQuery('#popup').fadeOut(1000);
			jQuery('body').removeClass('overlay');
		});
    });
    </script> -->
	
	
	<style>
	
	#popup {
	width: 100%;
    background: rgba(0,0,0,0.85);
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 99999;
	overflow-y: auto;
}

#popupcahtH {
    min-height: 45px;
    position: relative;
}

#inpopup {
	width: 500px;
    background: #fff;
	padding: 20px;
	margin: 40px auto 20px;
	position: relative;
}

#inpopup .closepopup {
	cursor: pointer;
    overflow: hidden;
    display: block;
    position: absolute;
    right: 0;
    top: 0;
}
	
	
	
.profileSetting {
	background: #d1d3d4 !important;
}

.form-control {
	width: 95%;
}

.col-sm-8 .tooltipTrigger {
	margin-right: 30px;
}

.col-sm-8 .tooltipTrigger {
	margin-right: 30px;
	margin-top: -24px;
}

#actionSetting .form-control {
	height: 27px;
}
</style>
</body>
</html>