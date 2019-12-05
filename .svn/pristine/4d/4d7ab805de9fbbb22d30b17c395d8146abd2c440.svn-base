<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <title>ERPfixers | Change Password</title>
  <body>
 <style>
 .error {
	color: red;
	font-weight: bold;
	position: relative;
}
</style> 
	<section class="member membersignup">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="animated fadeInDown">Change your password?</h1>
					
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-sm-3"></div>
			<form:form method="POST" action="${pageContext.request.contextPath}/resetpassword" 
			modelAttribute="validatePassword" commandName="validatePassword" enctype="multipart/form-data"
			>
				<div class="col-md-4 col-sm-6">
					<c:choose>
					<c:when test="${msgType=='success'}">
					  <c:if test="${not empty msg}">
						<div class="alert alert-success">
							    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							    <strong>Success!</strong> ${msg}
							  </div>
					</c:if>
					</c:when>
					<c:otherwise>
					<c:if test="${not empty msg}">
				     <div class="alert alert-danger">
							    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							    <strong>Failed!</strong> ${msg}
							  </div>
							  </c:if>	
					</c:otherwise>
					</c:choose>
					<input type="hidden" name="userId" value="${userId}">
					  <div class="relative col-md-12"  style="margin-bottom: 20px;">
						<label>Reset Password<span>*</span></label>
						<form:password path="newPassword" id="newPassword" class = "form-control"/>
						<a class="tooltipTrigger" style="line-height:20px; right:13px;">?</a>
						<div class="tooltipTarget" style="right:-300px; top:38px;">
											<i id = "newChar8Len" class="fa fa-check-circle"> 8 or more characters</i>
											<i id = "newUpperlower" class="fa fa-check-circle"> Upper & lowercase letters</i>
											<i id = "newOneNumb" class="fa fa-check-circle"> At least one number</i>
											</div>
					</div>
					 <form:errors path="newPassword" class="error" />
					
					  <form:errors path="newPassword" class="error" />
					    <div class="col-md-12"  style="margin-bottom: 20px;">
						<label>Confirm Password<span>*</span></label>
						<form:password path="confirmPassword" id="reTypePassword" class = "form-control"/>
						<a class="tooltipTrigger" style="line-height:20px; right:13px;">?</a>
						<div class="tooltipTarget" style="right:-300px; top:38px;">
						<i id = "reCheckPass" class="fa fa-check-circle"> Re-type your password</i>
											
											</div>
					</div>
					 <form:errors path="confirmPassword" class="error" />
					<form:errors path="mismatchPassword" class="error" />
					<br>
					<div class="col-md-12">
					<div class="btn_div">
						<button type="submit" class="btn btn-success btn-block" >Send Email</button>
					</div>
					</div>
				</div>
				</form:form>
				<div class="col-md-4 col-sm-3"></div>
			</div>
		</div>
	</section>
	<script>
	
	$(document).ready(function() {
		

		$('a').removeClass('active');
		$("#dashboard").addClass('active');
		$('#newPassword').on('keyup', function() {
			$('#newOneNumb').css('color', '#999');
			$('#newChar8Len').css('color', '#999');
			$('#newUpperlower').css('color', '#999');
			var password = $(this).val();
			var numbBool = hasNumbers(password);
			var passLen = password.length;
			var upperCaseBool = hasUpperCaseChar(password);
			var upperLowerBool = hasLowerCaseChar(password);

			if (numbBool) {
				$('#newOneNumb').css('color', '#7DD320');
			}
			if (passLen > 7) {
				$('#newChar8Len').css('color', '#7DD320');

			}
			if (upperCaseBool && upperLowerBool) {

				$('#newUpperlower').css('color', '#7DD320');

			}

		});

		$('#reTypePassword').on('keyup', function() {
			$('#reCheckPass').css('color', '#999');
	        var password = $('#newPassword').val();
			var ReTypepassword = $(this).val();
			if(password != null && password.trim() != ''){
				
			if(ReTypepassword == password){
				$('#reCheckPass').text(" Passsword  match");
				$('#reCheckPass').css('color', '#7DD320');

			}else{
				
				$('#reCheckPass').text(" Passsword not match");
				$('#reCheckPass').css('color', '#999');

			}
			
			}
			

		});
	});

	function hasUpperCaseChar(t) {
		var regex = /[A-Z]/;
		return regex.test(t);
	}

	function hasLowerCaseChar(t) {
		var regex = /[a-z]/;
		return regex.test(t);
	}

	function hasNumbers(t) {
		var regex = /\d/g;
		return regex.test(t);
	}
	</script>
  </body>
</html>