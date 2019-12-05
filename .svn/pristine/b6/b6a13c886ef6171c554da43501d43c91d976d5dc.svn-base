<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
<head>
<script
	src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
<script type="text/javascript" src="http://platform.linkedin.com/in.js">
  api_key: 75mti0kdh1z8qp
  credentials_cookie: false
  credentials_cookie_crc: false
  authorize: false
  scope: r_emailaddress,r_basicprofile
</script>
<title>ERPfixers | Login</title>
</head>

<body>
	<script>
	</script>
	<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}
</style>
	<a name="home" id="home"></a>

	<header class="header_without_login">
		<div class="container-fluid">
			<div class="mainhead">
				<div class="logo">
					<a href="http://www.erpfixers.com/"> <img alt=""
						src="${pageContext.request.contextPath}/images/ERP_Fixers-H-C.png"></a>
				</div>
				<div class="signup">
					<div class="mmneu btn btn-info pull-right">
						<i class="fa fa-bars"></i>
					</div>
				</div>
			</div>
		</div>
	</header>
<div class="loginArea">
	<section class="member_without membersignup">
	<div class="row">
		<div class="container">
			<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<h1 class="animated fadeInDown">Welcome Back</h1>
					
				</div>
				<div class="clear"></div>


				<div class="col-md-2"></div>
			</div>



			<div class="row">

				<!-- <form action="loginSSuser" method="post" modelAttribute="user" commandName="user"> -->

				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="signin_text">
						<div class="col-lg-3 col-md-4 col-sm-5">
							<h4>sign in with your email</h4>
							<h4>or:</h4>
							<div class="loginBtn">
								<button class="lnBtn" onclick="linkedInLoginTabbed()">
									<!--  <i data-icon="Q" class="icon"></i>
									<p class="size12">
										<b>Sign in with Linkedin</b>
									</p>
									-->
									<img src="${pageContext.request.contextPath}/images/linkedin.png">
								</button>
								<!-- <script type="in/Login"></script> -->

								<!-- <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark" data-width="155" data-height="35" data-longtitle="false"></div> -->
								<button id="google_auth" class="googleBtn"
									onclick="handleAuthClick()">
									<!--  <i data-icon="P" class="icon"></i>
									<p class="size12">
										<b>Sign in with Google</b>
									</p>-->
									<img src="${pageContext.request.contextPath}/images/googlep.png">
								</button>
							</div>


						</div>
					</div>
					<div class="col-md-6 col-sm-6">
						<form:form method="POST" action="loginSSuser">
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

							<div class="row">
								<div class="col-md-12">
									<label style="margin-bottom: 0px;">E-mail</label> <input
										class="form-control" type="email" id="" name="email" />
								</div>

								<div class="col-md-12">
									<label style="margin-bottom: 0px; margin-top: 10px;">Password</label>
									<input class="form-control" type="password" id=""
										name="password" />
								</div>

							</div>
							<label style="padding-left: 0px; margin: 10px 0px;"
								class="checkbox-inline"><a href="forgotpassword">Forgot
									password?</a></label>
							<div class="btn_div">
								<button
									style="border-radius: 0px !important; display: inline-block; width: 40%;"
									type="submit" class="btn btn-success">Sign in</button>
							</div>

							<label style="padding-left: 0px; margin: -10px 0px 10px;"
								class="checkbox-inline">Don&#39;t have an account? <a
								href="http://www.erpfixers.com/signup">Sign
									Up</a></label>
						</form:form>
					</div>
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3 col-sm-3"></div>
				<!-- </form> -->

			</div>

		</div>
	</div>
	</section>
	</div>
	<!-- price close -->


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
	// Use a button to handle authentication the first time.
      function handleClientLoad() {
		
		//alert('handle client load is called');
    	
		
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
        	  if(resp.emails != null)
        	  var email = resp.emails[0].value;
        	  else
        		  var email = "";
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
		          hiddenField5.setAttribute("value","Login");
		          form.appendChild(hiddenField5);
				  
		             
		        
		       
		        document.body.appendChild(form);
		        form.submit();
		      
          });
        });
        
        
      }
	
	
		$(document).ready(function() {
			
			var isCookiesEnabled = are_cookies_enabled();
			if (!isCookiesEnabled) {
				alert('please enable cookies');
			}
			var timeZone = jstz.determine().name();
			//handleClientLoad();
			$.ajax({
				url : "${pageContext.request.contextPath}/timeZone",
				type : "GET",
				data : {
					timeZone : timeZone
				},
				dataType : 'text'
			}).done(function(response) {
				console.log(response);
			});
		});
	
		
		function are_cookies_enabled() {
			var cookieEnabled = (navigator.cookieEnabled) ? true : false;

			if (typeof navigator.cookieEnabled == "undefined" && !cookieEnabled) {
				document.cookie = "testcookie";
				cookieEnabled = (document.cookie.indexOf("testcookie") != -1) ? true
						: false;
			}
			return (cookieEnabled);
		}

		function customValidation() {
			console.log('asdfasdf');
			if ($('#agree').is(':checked')) {
				return true;
			} else {
				alert("Please agree to the user agreement");
				return false;
			}
		}

		function linkedInLoginTabbed() {
			 //IN.Event.on(IN, "auth", OnLinkedInAuth);
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
	          hiddenField5.setAttribute("value","Login");
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
	</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jstz-1.0.4.min.js"></script>

</body>
</html>