<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<title>ERPfixers | Become a Member</title>
<head>
<script type="text/javascript" src="http://platform.linkedin.com/in.js">
  api_key: 75mti0kdh1z8qp
  credentials_cookie: false
  credentials_cookie_crc: false
  authorize: false
  scope: r_emailaddress,r_basicprofile
</script>
<style type="text/css">
.error {
	color: red;
	font-weight: bold;
	position: relative;
}
</style>

<script>
	$(document).ready(function(){
		
		var errorFields=<c:out default="[]" escapeXml="false" value="${not empty errorFields?errorFields:'[]'}" />;
		//alert(errorFields);
	for(var i = 0; i<errorFields.length;i++)
		{
			var field = "#"+errorFields[i];
			
			if(field == '#city')
				field = '#f_elem_city';
			if(field == '#country')
				field = '#smart_search';
			field_label = field + "_lable";
			$(field).addClass('error');
			$(field_label).addClass('error');
			
			console.log(field);
		
		}
		$("#signUpNow").html("BACK");
		$("#signUpNow").click(function(){
			window.history.back();
				});
		var sendEmailNotif =  '${member.sendEmail}';
		if(sendEmailNotif == 'Y'){
			$('#sendEmail').attr('checked', true);
			
		}else{
			$('#sendEmail').attr('checked', false);
			
		}
		var load = ${load};
 		if(load == true){
			$('#firstName').val("");
			$('#email').val("");
			$('#userName').val("");
			$('#country').val("");
			$('#f_elem_city').val("");
			$('#iAgree').attr('checked', false);
 		}
 		$('#password').on('keyup',function(){
 			$('#oneNumb').css('color','#999');
 			$('#char8Len').css('color','#999');
 			$('#upperlower').css('color','#999');
 			$('#oneNumbI').css('color','#999');
 			$('#char8LenI').css('color','#999');
 			$('#upperlowerI').css('color','#999');
 			
 			var password = $(this).val();
 			var numbBool  = hasNumbers(password);
 			var passLen = password.length;
 		    var upperCaseBool = hasUpperCaseChar(password);
 		    var upperLowerBool = hasLowerCaseChar(password);

 		    if(numbBool){
 		    	$('#oneNumb').css('color','#1e9873');
 		    	$('#oneNumbI').css('color','#1e9873');

 			}
 			if(passLen>7){
 		    	$('#char8Len').css('color','#1e9873');
 		    	$('#char8LenI').css('color','#1e9873');


 			}
 			if(upperCaseBool && upperLowerBool){
 				
 		    	$('#upperlower').css('color','#1e9873');
 		    	$('#upperlowerI').css('color','#1e9873');

 			}
 			
 		});
	$('#userName').on('keyup',function(){
		
 			$('#letterNumb').css('color', '#999');
 			$('#char6_12').css('color', '#999');
 			$('#letterNumbI').css('color', '#999');
 			$('#char6_12I').css('color', '#999');
 			
 			var userName = $(this).val();
 			
 			var userNameLen = userName.length;
 		   

 		    if(userName.match(/^[a-z0-9]+$/i)){
 		    	$('#letterNumb').css('color','#1e9873');
 		    	$('#letterNumbI').css('color','#1e9873');
 			}
 			if(userNameLen>6 && userNameLen<13){
 		    	$('#char6_12').css('color','#1e9873');
 		    	$('#char6_12I').css('color','#1e9873');


 			}
 		});

	
	});
	
	
	
	function getTimezoneName() {
		timezone = jstz.determine();
		return timezone.name();
	}
	
	function hasUpperCaseChar(t)
	{
	var regex = /[A-Z]/;
	return regex.test(t);
	}
	
	function hasLowerCaseChar(t)
	{
	var regex = /[a-z]/;
	return regex.test(t);
	}
	
	function hasNumbers(t)
	{
	var regex = /\d/g;
	return regex.test(t);
	}
	
	$(document).ready(function() {
		$("#time_Zone").val((getTimezoneName()));
	});
</script>
</head>
<body style="background: #fff;">
	<!-- window onload text  start here -->
	<section id="popup_alert">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<div id="mainform">

						<!-- Right all code inside here  -->


					</div>


				</div>
			</div>
		</div>
	</section>

	<!-- window onload text end here  -->
	<!-- price -->
	<section class="member membersignup">
		<div class="container">
			<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<h1>Create a Free Member Account</h1>
					<p>
						Are you an SAP expert? <a
							href="${pageContext.request.contextPath}/fixerSignUp">Sign up
							as a Fixer</a>
					</p>
					<hr>
				</div>

				<div class="col-md-2"></div>
			</div>


			<div class="row">
				<div class="col-md-2 col-sm-3"></div>
				<div class="col-md-8 col-sm-12">

					<div class="row">
					<div class="stylebox">


						<div class="row">



							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<div class="signin_text">
									<div class="col-lg-3 col-md-4 col-sm-5">
										<h3>Step 1:</h3>
										<h4>Create your account</h4>
										<h4>or:</h4>
										<div class="loginBtn">
											<button class="lnBtn" onclick="linkedInLoginTabbed()">
												<!-- <i data-icon="Q" class="icon"></i>
												<p>
													<b>Sign up with Linkedin</b>
												</p> -->
												<img src="${pageContext.request.contextPath}/images/linkedin.png">
											</button>

											<button id="google_auth" class="googleBtn"
												onclick="handleAuthClick()">
												<!-- <i data-icon="P" class="icon"></i>
												<p>
													<b>Sign up with Google</b>
												</p> -->
												<img src="${pageContext.request.contextPath}/images/googlep.png">
											</button>
										</div>


									</div>
								</div>
								<div class="col-md-6 col-sm-6">
									<form:form method="POST"
										action="${pageContext.request.contextPath}/memberSignUp"
										modelAttribute="member" commandName="member"
										onsubmit="return customValidation()">
										<%-- 	<form:input path="imageUrl" type="hidden"
										value="${member.imageUrl}" />  --%>

										<form:input path="overview" type="hidden"
											value="${member.overview}" />
										<form:input path="socialLogin" type="hidden"
											value="${member.socialLogin}" />

										<div class="row">

											<div class="col-md-6 col-sm-6">
												<div class="radio">
													<c:choose>
														<c:when test="${flag=='true'}">
															<input type="radio" id="Bmember" checked="checked"
																name="optradio" onchange="memberPage();">
															<label for="Bmember" class="red-tooltip"
																data-toggle="tooltip" data-placement="bottom"
																title="A Member is an SAP customer that has requests and issues to resolve.">Become
																a Member</label>

														</c:when>
														<c:otherwise>
															<input type="radio" id="Bmember" name="optradio"
																onchange="memberPage();">
															<label for="Bmember" class="red-tooltip"
																data-toggle="tooltip" data-placement="bottom"
																title="A Member is an SAP customer that has requests and issues to resolve.">Become
																a Member</label>

														</c:otherwise>
													</c:choose>
												</div>

											</div>

											<div class="col-md-6 col-sm-6">
												<div class="radio">
													<input type="radio" id="Bfixer" name="optradio"
														onchange="fixerPage();"> <label for="Bfixer"
														class="red-tooltip" data-toggle="tooltip"
														data-placement="bottom"
														title="A Fixer is an SAP expert that can resolve issues and deal with requests.">
														Become a Fixer</label>
												</div>
											</div>
										</div>




										<c:if test="${not empty error}">
											<div class="error">${error}</div>
										</c:if>
										<c:if test="${not empty msg}">
											<div class="msg">${msg}</div>
										</c:if>
										<c:if test="${not empty newMember}">
											<div class="msg">${newMember}</div>
										</c:if>
										<c:if test="${not empty newFixer}">
											<div class="msg">${newFixer}</div>
										</c:if>


										<div
											style="color: #f00; text-align: center; margin: -20px 0 30px 0; font-weight: bold;"
											id="spanExist">
											<form:errors path="exist" cssClass="error" />
										</div>
										<div class="row">
											<label id="firstName_lable"
												class="col-md-12 col-sm-12 col-xs-12"> <spring:message
													code="welcome.name" /> <span>*</span>
											</label>
											<div class="col-md-6 col-sm-6 col-xs-12">
												<!-- <input type="text" name="username"> -->
												<form:input type="text" path="firstName"
													class="form-control" id="firstName" />
												<span class="text12">First Name</span>

											</div>

											<div class="col-md-6 col-sm-6 col-xs-12">
												<form:input type="text" path="lastName" class="form-control"
													id="lastName" />
												<span class="text12">Last Name</span>
											</div>
											<form:errors path="firstName" cssClass="col-md-12  error" />
										</div>
										<p id="phoneerror"
											style="text-align: left; color: #ff0000; display: none;">Phone
											No. should be in these formats.[+xx-xxxx-xxxx, +xx.xxxx.xxxx,
											+xx xxxx xxxx,+xx xxxx xxxxxx]</p>


										<div>
											<label id="email_lable"> <spring:message
													code="welcome.email" /> <span>*</span>
											</label>
											<!-- <input type="email" name="username"> -->
											<form:input type="text" path="email" class="form-control"
												id="email" onblur="userNameFromEmail();" />
											<form:errors path="email" cssClass="error" />
											

										</div>

										<div class="relative">
											<label id="userName_lable"> <spring:message
													code="welcome.username" />
											</label>
											<!-- <input type="email" name="username"> -->
											<form:input type="text" path="userName" class="form-control"
												id="userName" />
											<form:errors path="userName" cssClass="error" />
											<a class="tooltipTrigger">?</a>
											<div class="tooltipTarget">
											<ul>
												<li id="char6_12"><i id="char6_12I" class="fa fa-check-circle"></i> between 6-12 characters</li>
												<li id="letterNumb"><i  id="letterNumbI" class="fa fa-check-circle"></i> letters numbers</li>
											</ul>
											 </div>
										</div>
										<c:if test="${member.socialLogin == false}">

											<div class="relative">
												<label id="password_lable"> <spring:message
														code="welcome.password" /> <span>*</span>
												</label>
												<!-- <input type="password" name="username"> -->
												<form:input type="password" path="password"
													class="form-control" id="password" />
												<a class="tooltipTrigger">?</a>
											<div class="tooltipTarget">
											<ul>
												
												<li id = "char8Len"><i id = "char8LenI" class="fa fa-check-circle"></i> 8 or more characters</li>
												<li  id = "upperlower" ><i id = "upperlowerI" class="fa fa-check-circle"></i> Upper & lowercase letters</li>
												<li id = "oneNumb"><i  id = "oneNumbI" class="fa fa-check-circle"></i> At least one number</li>
											</ul>
											</div>
									<form:errors path="password" cssClass="error" />
											
											</div>
										</c:if>


										<div class="">
											<label id="smart_search_lable"> <spring:message
													code="welcome.country" /> <span>*</span>
											</label>
											<div id="the-basics">
												<select name="country" id="country"
													class="form-control width100">
													<c:forEach var="country" items="${countryList}">
														<option value="${country.countryName}">
															<c:out value="${country.countryName}" />
														</option>
													</c:forEach>
												</select>


												<%-- 	<form:input type="text" path="country"
													class="form-control width100" id="smart_search" /> --%>
											</div>

											<form:hidden path="timeZone" id="time_Zone" />

											<form:errors path="country" cssClass="error" />
										</div>


										<div id="f_elem_city_div">
											<label id="f_elem_city_lable"> <spring:message
													code="welcome.city" /> <span>*</span>
											</label>
											<form:input type="text" path="city" class="form-control"
												id="f_elem_city" />
											<form:errors path="city" cssClass="error" />
										</div>

										<div>
											<label> How did you hear about us? <span></span></label>

											<c:choose>
												<c:when test="${ref=='asug'}">
													<select id="source" name="source" disabled="disabled"
														class="form-control">
														<option>Select</option>
														<option selected>ASUG or an ASUG affiliate</option>
														<option>Search Engine</option>
														<option>Social Network</option>
														<option>Advertisement</option>
														<option>Friend</option>
													</select>
												</c:when>
												<c:otherwise>

													<select id="source" name="source" class="form-control">
														<option value="0">Select</option>
														<option value="1">ASUG or an ASUG affiliate</option>
														<option value="2">Search Engine</option>
														<option value="3">Social Network</option>
														<option value="4">Advertisement</option>
														<option value="5">Friend</option>
													</select>
												</c:otherwise>
											</c:choose>

										</div>


										<div class="dcheck">
											<form:checkbox id="sendEmail" path="sendEmail" value=""
												label="Send me genuinely useful emails every now and then to help me get the most out of ERPfixers." />
											<!-- <label >Send me genuinely useful email every now and then to help me get the most out of ERPfixers.</label> -->
										</div>

										<div class="dcheck">
											<c:choose>
												<c:when test="${errors == true }">
													<input id="iAgree" type="checkbox" name="checkbox"
														value="2" checked="checked">
												</c:when>
												<c:otherwise>
													<input type="checkbox" id="iAgree" value="">
												</c:otherwise>
											</c:choose>
											<label for="iAgree" id="iAgree">Yes, I understand and
												agree to the ERPfixers <br> <a target="_blank"
												href="${pageContext.request.contextPath}/userAgreement">User
													Agreement.</a>
											</label>
										</div>

										<div class="btn_div">
											<button type="submit" class="btn_custm" style="width: 40%;">Sign
												up</button>
										</div>
									</form:form>
								</div>
							</div>




							<div class="col-md-2 col-sm-3"></div>
							<!-- </form> -->

						</div>

					</div>
					</div>
				</div>

				<div class="col-md-3 col-sm-3"></div>

			</div>

		</div>
	</section>



	<script type="text/javascript">

var clientId = '255895471038-evd54lonh3p570eoo50hplvl6gq3nr8r.apps.googleusercontent.com';
// Enter the API key from the Google Developer Console - to handle any unauthenticated
// requests in the code.
// The provided key works for this sample only when run from
// https://google-api-javascript-client.googlecode.com/hg/samples/authSample.html
// To use in your own application, replace this API key with your own.
var apiKey = 'Vam82Zn8NWeI1-AJ08OY4MJP';
// To enter one or more authentication scopes, refer to the documentation for the API.
var scopes = 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email';
function handleClientLoad() {
	
	alert('handle client load is called');
	
	
  gapi.client.setApiKey(apiKey);
  window.setTimeout(checkAuth,1);
}
function checkAuth() {
  gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: true}, handleAuthResult);
}
function handleAuthResult(authResult) {
  var authorizeButton = document.getElementById('google_auth');
  if (authResult && !authResult.error) {
    //authorizeButton.style.visibility = 'hidden';
    makeApiCall();
  } else {
    //authorizeButton.style.visibility = '';
    authorizeButton.onclick = handleAuthClick;
  }
}
function handleAuthClick(event) {
  gapi.auth.authorize({client_id: clientId, scope: scopes, immediate: false}, handleAuthResult);
  return false;
}
// Load the API and make an API call.  Display the results on the screen.
function makeApiCall() {
	
	 gapi.client.load('oauth2', 'v2', function() {
		  gapi.client.oauth2.userinfo.get().execute(function(resp) {
		    // Shows user email
		    console.log(resp.email);
		  })
		});
	 
  gapi.client.load('plus', 'v1', function() {
    var request = gapi.client.plus.people.get({
      'userId': 'me'
    });
    request.execute(function(resp) {
  	  console.log(resp);
  	
  	  var givenName = resp.name.givenName;
  	  var familyName = resp.name.familyName;
  	  var email = resp.emails[0].value;
  	  var imageUrl = resp.image.url;
  	  
  	  var method;
	        method = method || "post";
	        var form = document.createElement("form");
	        form.setAttribute("method", method);
	        form.setAttribute("action", "${pageContext.request.contextPath}/googleSignUp");

	        var hiddenField = document.createElement("input");
	          hiddenField.setAttribute("type", "hidden");
	          hiddenField.setAttribute("name", "given_name");
	          hiddenField.setAttribute("value", givenName);
			  form.appendChild(hiddenField);
			  
			  var hiddenField1 = document.createElement("input");
	          hiddenField1.setAttribute("type", "hidden");
			  hiddenField1.setAttribute("name", "family_name");
	          hiddenField1.setAttribute("value", familyName);
			  form.appendChild(hiddenField1);
			  
			  var hiddenField2 = document.createElement("input");
	          hiddenField2.setAttribute("type", "hidden");
			  hiddenField2.setAttribute("name", "image_url");
	          hiddenField2.setAttribute("value", imageUrl);
			  form.appendChild(hiddenField2);
			  
			  var hiddenField3 = document.createElement("input");
	          hiddenField3.setAttribute("type", "hidden");
			  hiddenField3.setAttribute("name", "email");
	          hiddenField3.setAttribute("value", email);
			  form.appendChild(hiddenField3);
			  
			  var hiddenField4 = document.createElement("input");
	          hiddenField4.setAttribute("type", "hidden");
			  hiddenField4.setAttribute("name", "error");
	          hiddenField4.setAttribute("value","");
			  form.appendChild(hiddenField4);
			  
			  var hiddenField5 = document.createElement("input");
	          hiddenField5.setAttribute("type", "hidden");
			  hiddenField5.setAttribute("name", "isUserType");
	          hiddenField5.setAttribute("value","MEMBER");
	          form.appendChild(hiddenField5);
			  
	             
	        
	       
	        document.body.appendChild(form);
	        form.submit();
	      
    });
  });
}


    $("#signUpNow").html("BACK");
    function customValidation(){
    	
				
		if($('#iAgree').is(':checked') == true){
			if ($('input[name=optradio]:checked').val() ) {  
				return true;
			}
			else{
				alert("Please select one of the options \"Become a Member\" or \"Become a Fixer\" in order to sign up.");
				return false;
			}
		}else{
			alert("Please agree the terms and conditions");
			return false;
		}
		
		
		
	}
    function memberPage(){
		console.log("memberpage");
	}
	function fixerPage(){
		console.log("fixerpage");
		
			var url = "${pageContext.request.contextPath}/fixerSignUp";
		window.location.replace(url);
		
	}
    function userNameFromEmail(){
    	   var email = $("#email").val();
    	   var strArray = email.split('@');
    	   var userName = strArray[0];
    	   $("#userName").val(userName);
    	  }
    
/* 
	var substringMatcher = function(strs) {
	      return function findMatches(q, cb) {
	    	var matches, substringRegex;
	    
	    	// an array that will be populated with substring matches
	    	matches = [];
	    
	    	// regex used to determine if a string contains the substring `q`
	    	substrRegex = new RegExp(q, 'i');
	    
	    	// iterate through the pool of strings and for any string that
	    	// contains the substring `q`, add it to the `matches` array
	    	$.each(strs, function(i, str) {
	    	  if (substrRegex.test(str)) {
	    		matches.push(str);
	    	  }
	    	});
	    
	    	cb(matches);
	      };
	    };
      var countryListJson =${countryList};
      var countryList = new Array();
      for(var i=0; i<countryListJson.length;i++){
    	  countryList.push(countryListJson[i].countryName);
      }
      $('#the-basics .typeahead').typeahead({
    	    hint: true,
    	    highlight: true,
    	    minLength: 1
    	  },
    	  {
    	    name: 'states',
    	    source: substringMatcher(countryList)
    	  });

    	$(document).on('click','.tt-selectable',function(){
    		   var str = $("#smart_search").val();
    		   
    	}); */
    	
</script>
	<script>

function linkedInLoginTabbed() {
	IN.User.authorize(function(){
		OnLinkedInAuth();
	   });

}

function googleLoginTabbed() {
	var redirectUri = "${pageContext.request.contextPath}/login-via-google";

	window.location.replace(redirectUri);

}

function OnLinkedInAuth() {
   IN.API.Profile("me").fields("first-name", "last-name", "email-address","picture-url","headline","location").result(ShowProfileData);
}

function ShowProfileData(profiles) 
{
   var member = profiles.values[0];
   console.log(member);
   var id=member.id;
   var firstName=member.firstName; 
   var lastName=member.lastName; 
   var summary = member.summary;
   var email = member.emailAddress;
   var photo=member.pictureUrl; 
   var headline=member.headline; 
   var location = member.location;
   var code = location.country.code;
   var city = location.name;
   
   var method;
   method = method || "post";
   

    var form = document.createElement("form");
   form.setAttribute("method", method);
   form.setAttribute("action", "${pageContext.request.contextPath}/linkedInSignup");

   var hiddenField = document.createElement("input");
     hiddenField.setAttribute("type", "hidden");
     hiddenField.setAttribute("name", "given_name");
     hiddenField.setAttribute("value", firstName);
	  form.appendChild(hiddenField);
	  
	  var hiddenField1 = document.createElement("input");
     hiddenField1.setAttribute("type", "hidden");
	  hiddenField1.setAttribute("name", "family_name");
     hiddenField1.setAttribute("value", lastName);
	  form.appendChild(hiddenField1);
	  
	  var hiddenField2 = document.createElement("input");
     hiddenField2.setAttribute("type", "hidden");
	  hiddenField2.setAttribute("name", "image_url");
     hiddenField2.setAttribute("value", photo);
	  form.appendChild(hiddenField2);
	  
	  var hiddenField3 = document.createElement("input");
     hiddenField3.setAttribute("type", "hidden");
	  hiddenField3.setAttribute("name", "email");
     hiddenField3.setAttribute("value", email);
	  form.appendChild(hiddenField3);
	  
	  var hiddenField4 = document.createElement("input");
     hiddenField4.setAttribute("type", "hidden");
	  hiddenField4.setAttribute("name", "error");
     hiddenField4.setAttribute("value","");
	  form.appendChild(hiddenField4);
	  
	  var hiddenField6 = document.createElement("input");
     hiddenField6.setAttribute("type", "hidden");
	  hiddenField6.setAttribute("name", "summary");
     hiddenField6.setAttribute("value",headline);
     form.appendChild(hiddenField6);
	  
	  var hiddenField5 = document.createElement("input");
     hiddenField5.setAttribute("type", "hidden");
	  hiddenField5.setAttribute("name", "isUserType");
     hiddenField5.setAttribute("value","MEMBER");
     form.appendChild(hiddenField5);
	  
     var hiddenField7 = document.createElement("input");
     hiddenField7.setAttribute("type", "hidden");
	  hiddenField7.setAttribute("name", "code");
     hiddenField7.setAttribute("value",code);
     form.appendChild(hiddenField7);
   
     var hiddenField8 = document.createElement("input");
     hiddenField8.setAttribute("type", "hidden");
	  hiddenField8.setAttribute("name", "city");
     hiddenField8.setAttribute("value",city);
     form.appendChild(hiddenField8);
   document.body.appendChild(form);
   form.submit();


 
}

$(document).ready(function() {
	 $( "#firstName" ).val('${member.firstName}');
	 $( "#lastName" ).val('${member.lastName}');
	 $( "#email" ).val('${member.email}');
	 var linkedinCity = '${member.city}';
	 
	 var errors = '${not empty errorFields}';
	 if(errors == 'false'){
		 var userNameLinkedIn = '${member.email}'.split('@')[0];
		 $( "#userName" ).val(userNameLinkedIn);
	 } else{
		 $( "#userName" ).val('${member.userName}');
	 }
	 
	 
	 if(linkedinCity.trim() != '')
		 linkedinCity = linkedinCity.split(',')[0].trim();
	 $( "#f_elem_city" ).val(linkedinCity);
	

	 var country = '${member.country}';
	 $("#country option[value='" + country + "']").prop(
				'selected', true);

$("#custm_pop #cancel").click(function() {
$(this).parent().parent().hide();
});
$("#onclick").click(function() {
$("#contactdiv").css("display", "block");
});
$("#contact #cancel").click(function() {
$(this).parent().parent().hide();
});
});


</script>
</body>

