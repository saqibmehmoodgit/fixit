<%@ page contentType="text/html; charset=UTF-8" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
    <title>ERPfixers | Forgot Password</title>
  <body>
	
	<section class="member membersignup">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="animated fadeInDown">Forgot your password <span style="font-family:arial;">?</span></h1>
					<p class="animated fadeIn">Enter the email address associated with your account. An email will be sent to you with instructions on how to reset your password.</p>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-md-4 col-sm-3"></div>
				<form:form method="POST" action="${pageContext.request.contextPath}/resetpasswordmail">
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
					
				
				<div class="col-md-12"  style="margin-bottom: 20px;">
						<label>E-mail<span>*</span></label>
						<input class="form-control" type="email" id="" name="email">
					</div>
									
					<br>
					<div class="col-md-12">
					<div class="btn_div">
						<button type="submit" class="btn btn-success btn-block">Send Email</button>
					</div>
					</div>
				</div>
				</form:form>
				<div class="col-md-4 col-sm-3"></div>
			</div>
			
		</div>
	</section>
	
    
  </body>
