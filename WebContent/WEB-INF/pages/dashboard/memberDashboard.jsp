<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>

<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
response.setContentLength(2000000);
%>


<title>ERPfixers | Member Dashboard</title>

<%-- <script src="${pageContext.request.contextPath}/js/gantt/amcharts.js"></script>
<script src="${pageContext.request.contextPath}/js/gantt/serial.js"></script>
<script src="${pageContext.request.contextPath}/js/gantt/light.js"></script>
<script src="${pageContext.request.contextPath}/js/gantt/gantt.js"></script> --%>
<script src="${pageContext.request.contextPath}/js/pie/amcharts.js"></script>
<script src="${pageContext.request.contextPath}/js/pie/pie.js"></script>
<script src="${pageContext.request.contextPath}/js/pie/light.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/pages/script.js"></script>
 <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>

 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jquery-ui.min.css">
<!-- <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
 -->
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/cityrelatedinfo.js"></script>
	
<script type="text/javascript">
var groupId = ${groupId};
//var countryListJson = ${countryList};
var userIndustry = ${industJSONByUser};
var  countMsg = ${countMsg};
var industryJson = ${industryJSON};
var userEmail = '${myUser.email}';
var userName = '${myUser.userName}';
var timeZone = '${userTimeZone}';
var array =  ${pieChartData};
/* var startDate = frequencyDuration.startDate;
var array = frequencyDuration.frequencyDurations; */
var userId = ${myUser.userId};
var country =  "${myUser.country}";
var userSource = ${myUser.source};
var trackCredit = '${myUser.trackCredit}';
</script>
<script
	src="${pageContext.request.contextPath}/js/pages/memberDashboard.js"></script>

</head>
<body>

	<div id="pagewrap">
		<!-- Work Here start-->
		<section class="wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
						<div class="profileSetting wow fadeIn"
							style="background: #d1d3d4;">
							<div class="settingDiv" style="display: none">
								<h1>Settings</h1>
								<div class ="halfBorder mtb"></div>
								<div class="row">
									<div class="col-lg-7 col-md-7 col-sm-6 col-xs-12">
										<form:form id="actionSetting" method="POST"
											action="updateMemberSettings" class="form-horizontal"
											modelAttribute="memberSetting" commandName="memberSetting">
								<form:input id="imageUrlSetting" path="imageUrl" type="hidden" />
											
											<div class="form-group">
												<label class="control-label col-sm-4">Email:</label>
												<div class="col-sm-8">
													<span class="alertF" id="spanUserEmail">Please enter your email address.</span>

													<form:input path="email"  type="email" class="form-control"
														id="userEmail" />
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4">Username:</label>
												<div class="col-sm-8">
													<span class="alertF" id="spanUserName">Please enter a username.</span>

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
											<div class="form-group" style="padding:2px 0px">
												<label class="control-label col-sm-4"  style="margin-top:5px">Send Alerts:</label>
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
											<div id="staticPass"class="form-group">
												<label class="control-label col-sm-4">Password:</label>
												<div class="col-sm-8">
													
 										<span class="alertF" id="spanPassword">old password does not match.</span>
													<input  type="password" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
														class="form-control" id="password"  />
														
														<a onclick="changePassDiv()" href="javaScript:void(0)" class="changeLink">Change</a>
												</div>
												
											</div>
																						<div id="passDiv" style="display:none">
											
											<div class="form-group">
												<label class="control-label col-sm-4"><span>Re-Enter Current&nbsp;Password:</span></label>
												<div class="col-sm-8">
													<span class="alertF" id="spanOldPassword">old password does not match.</span>

													<form:input path="oldPasword" type="password"
														class="form-control" id="oldPassword" />
												</div>
											</div>
											
											<div class="form-group">
												<label class="control-label col-sm-4">New Password:</label>
												<div class="col-sm-8 relative">
													<span class="alertF" id="spanNewPassword">Please enter a password.</span>

													<form:input path="newPasword" type="password"
														class="form-control" id="newPassword" />
																	<a class="tooltipTrigger">?</a>
											<div class="tooltipTarget"">
											<ul>
												<li  id = "newChar8Len"><i id = "newChar8LenI" class="fa fa-check-circle"></i> 8 or more characters</li>
												<li id = "newUpperlower"><i id = "newUpperlowerI" class="fa fa-check-circle"></i> Upper & lowercase letters</li>
												<li id = "newOneNumb"><i  id = "newOneNumbI" class="fa fa-check-circle"></i> At least one number</li>
											</ul>
											
											
											
											</div>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4"><span>Re-Type
													Password:</span></label>
												<div class="col-sm-8">
													<span class="alertF" id="spanReTypePassword">Please retype your password.</span>

													<form:input path="confirmPassword" type="password"
														class="form-control" id="reTypePassword" />
															<a class="tooltipTrigger">?</a>
											<div class="tooltipTarget" >
											<ul>
	<li id="reCheckPass" ><i id="reCheckPassI" class="fa fa-check-circle"></i> Re-type your password</li>
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
									<c:forEach var="industryItem" items="${allIndustry}">
										<li><input name="indestries"
											id="industryItem${industryItem.indstId}"
											value="${industryItem.indstId}" type="checkbox"> <label
											for="industryItem${industryItem.indstId}">${industryItem.indstName}</label>
										</li>
									</c:forEach>


								</ul>
								<div class="MyIndusSC">
									<button class="saveChange" onclick="saveIndustry()" id="savechange">Save</button>
									<button class="cancel" id="MIClose" onclick="cancelIndustry()" >Cancel</button>
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
																onchange="uploadProfileImage()"> <span id="uploadtxt"
																class="uploadtxt">Upload Photo</span>
														</div>

														<div class="upLoader">
															<img
																src="${pageContext.request.contextPath}/images/loader.gif">
														</div>
													<img src="../flags/${myUser.country}.png" class="userflag" >
														
													</div>
													
													<ul class="settingnav">
														<li class="edit"><a href="javascript:void(0)"><i
																data-icon="c" class="icon"></i> Edit Profile</a>
															<div id="editProfile" class="settingsbtn"
																style="display: none;">
																<a href="" class="cancel">Cancel</a> <a
																	href="javascript:void(0)" onclick="editProfile();"
																	class="saveC">Save</a>
															</div></li>
														<li class="settings"><a href="javascript:void(0)"><i
																data-icon="h" class="icon"></i> Settings</a>
															<div id="setting" class="settingsbtn"
																style="display: none;">
																<a href="" class="cancel">Cancel</a> <a
																	href="javascript:void(0)" id="saveSetting"
																	class="saveC">Save</a>
															</div></li>
														<li class="saptips"><a target ="_blank" href="http://www.erpfixers.com/resources/"><i
																data-icon="f" class="icon"></i> SAP Tips</a></li>
													</ul>
												</div>
											</div>
											<div class="col-lg-8 col-md-8 col-sm-8 col-xs-7">
											
											<c:if test="${msgType == 'success'}">
											<div style="margin-top:18px; margin-right: 15px;" class="alert alert-success fade in">
    												<a title="close" aria-label="close" data-dismiss="alert" class="close" href="#">×</a> ${message}
    										    </div>
											</c:if>
										<c:if test="${msgType == 'Error'}">
											
											<div style="margin-top:18px; margin-right: 15px;" class="alert alert-danger fade in">
    												<a title="close" aria-label="close" data-dismiss="alert" class="close" href="#">×</a> ${message}
    										    </div>
											</c:if>
												
												<div id="profileEditRight" class="prifileRight">
													<!-- <div class="editprofileDiv"> -->
													<form:form id="editUser" method="POST" action="editProfile"
														class="form-horizontal" modelAttribute="userBo"
														commandName="userBo" enctype="multipart/form-data">

														<form:input id="imageUrl" path="imageUrl" type="hidden" />
														<div class="headTitle">
															<div class="pRelative">
																<span class="alertF" id="spanName">Please enter your name.</span>

																<form:input id="name" type="hidden" path="name"
																placeholder="name"
																	value="${myUser.firstName} ${myUser.lastName}"
																	class="titleName" />
																<h1 id="headingName">${myUser.firstName} ${myUser.lastName}</h1>
															</div>
															<div class="pRelative">
																<span class="alertF" id="spancompanyName">Please
																	enter your company.</span>

																<form:input id="companyName" type="hidden" placeholder = "Company"
																	path="companyName" value="${myUser.companyName}"
																	class="Designation"/>
															<span id="headingCompanyName">${myUser.companyName}</span>
															
															</div>
															
															
														</div><div id="halfFullBorder" class="halfBorder"></div>
														<div class="location">
															<%-- <span>Location: <form:input id="location"
																	type="text" path="location"
																	value="${myUser.city}, ${myUser.country}"
																	class="Location" readonly="true" />
															</span> --%>
															<span class="location100">
															<span class="locations">Location:</span>
															<div class="show_not"><strong>${myUser.city}, ${myUser.country}</strong></div>
															<div class="showornot">
															<span
																class="alertF" id="spanCountry">Please enter your country.</span> 
																<select name="country"   style="font-weight: bold;"  id="country" class="cuntry">
														<c:forEach var="country" items="${countryList}">
															<option value="${country.countryName}">
																<c:out value="${country.countryName}" />
															</option>
														</c:forEach>
													</select>
																
																<%-- <form:input path="country"
																	class="cuntry typeahead" type="text"
																	value="${myUser.country}" id="country"
																	readOnly="true" /> --%> <span class="alertF" id="spanCity">Please enter your city.</span> <form:input   style="font-weight: bold;"  path="city" id="f_elem_city"
																	type="text" value="${myUser.city}" class="city" placeholder = "City"
																	readonly="true" /> 
</div>
															</span>
															<%--  <span class="linkdin100"><img
																src="${pageContext.request.contextPath}/images/in.png"
																alt="">: <span class="alertF"
																id="spanlinkedinProfile">Please enter your LinkedIn url.</span> <form:input
																	id="linkedinProfile" type="text" path="linkedinProfile"
																	value="${myUser.linkedinProfile}" class="linkdin"
																	readonly="true" /></span>  --%>
																	<span class="fixsin">Member Since: <strong>${userSince}</strong>
															</span> 
															<span class="fixsin">Time Zone: <strong>${myUser.timeZone}</strong>
															</span> 
														</div>
														<div class="clearfix"></div>
														<div class="pRelative">
															<span class="alertF" style="top: -7px;"
																id="spanOverviewText">Please enter your bio.</span>

															<textarea id="overviewText" class="detail" placeholder = "Bio"
																name="overview" readonly="true">${myUser.overview}</textarea>
															</textarea>
														</div>
													</form:form>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
									<div class="my-industries1">
										<div class="headC">
											<h1>Industries</h1>
										</div>

										<div class="categoriesDiv">
											<div id="industrySpanDiv">
												<c:forEach var="industryByUserItem"
													items="${industryByUser}">
													<span id="industrySpan${industryByUserItem.indstId}">${industryByUserItem.indstName}<i
														data-icon="y" class="icon"
														onclick="removeSelectedIndustry(${industryByUserItem.indstId})"></i></span>
												</c:forEach>
											</div>								
											<div class="clearfix"></div>
										</div>
										<span class="addnew" id="AddNew">Add New <i
												data-icon="p" class="icon"></i></span>
									</div>



								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
						<div class="resolvedRequests wow fadeIn" data-wow-delay="0.5s">
							<h1>Credit Available</h1>
							<h2>${remaininghours}</h2>

							<span class="gotoreq"><i data-icon="p" class="icon"></i>
								Add Hours</span>

							<div class="HourPacks">
								<h1>Hour Packs</h1>
								<p>Purchase larger bundles and save.</p>
								<p id="asugCredits" style="display:none">You get 1 free credit as an ASUG member or affiliate</p>
								<div class="hourpackDiv">
									<div  id="purple" class="hp-cell">
										<div class="purple">
											<span class="grayN">5</span> <span class="price"> <a
											id="money_575"	href="javascript:void(0)">
													$575 </a>
											</span>
										</div>
									</div>
									<div id="purpleDark"  class="hp-cell">
										<div id="purpleDark" class="purpleDark">
											<span class="whiteN">10</span> <span class="price"> <a
											id="money_1125"	href="javascript:void(0)">$1,125</a>
											</span>
										</div>
									</div>
									<div  id="green" class="hp-cell">
										<div  class="green">
											<span class="grayN">20</span> <span class="price"> <a
											id="money_2200"	href="javascript:void(0)">$2,200</a>
											</span>
										</div>
									</div>
									<div id="blue" class="hp-cell">
										<div  class="blue">
											<span class="whiteN">40</span> <span class="price"> <a
											id="money_4300"	href="javascript:void(0)">$4,300</a>
											</span>
										</div>
									</div>
									<div class="clearfix"></div>
								</div>
								<div class="clearfix"></div>
								<div class="btnc">
									<button class="cancel">
										<i data-icon="y" class="icon"></i> Cancel
									</button>
									<button id="purchaseCredits" class="hpbutton" style="padding:10px 0px !important; font-size:10px; white-space:nowarp;">
										Purchase <i data-icon="K" class="icon"></i><!-- img src="${pageContext.request.contextPath}/images/next.png" style="width:15px;" --> <strong>$<span  id="money">0</span></strong>
									</button>
								</div>
							</div>

						</div>
						<div id="messageTab"  class="newmessage " data-wow-delay="0.5s">
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
											<div id="chatload" class="chatLoad">Please wait while loading...</div>
												
												<div class="chatbgFooter">
													<textarea  placeholder = "Type here..."  id="chatTextArea"></textarea>
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
						<div class="openRequests wow fadeIn" data-wow-delay="1s">
							<h2>Open Requests</h2>
							<h1>${openRequestCount}</h1>
							<div class="completedUsed">
								<div class="completed">
									<h3>${fixedIssuesCount}</h3>
									<span>Completed</span>
								</div>
								<div class="hoursUsed">
									<h3>${sumOfCredits}</h3>
									<span>Credits Used</span>
								</div>

							</div>
							<div class="clearfix"></div>
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
						<div class="reviews wow fadeIn" data-wow-delay="1.5s">
							<h2>Request Areas</h2>
							<div id="graph_container" class="average-review-graph"></div>
						</div>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
						<div class="frequency wow fadeIn" data-wow-delay="2s">
							<h2>Requests Posted per Module</h2>
							<!-- <h1></b><span id="chartForMonth"></span></h1> -->
						      <div id="chartdiv"></div>
						    <!--  <div  id="leftGraphBtn" class="leftGraphBtn" onclick="showPreviousGiattChart();"></div>
  							 <div id="rightGraphBtn" class="rightGraphBtn" onclick="showNextGiattChart();"></div> -->
						      
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
</script>
 -->
	</div>
	<style>
		.form-control{width:95%;}
		.col-sm-8 .tooltipTrigger{ margin-right:30px; margin-top: -24px;}
	</style>
</body>
</html>