<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
 <script src="js/jquery.min.js"></script> 


	<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script> -->
	
	<link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/flick/jquery-ui.css" />
	
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
	
	<script src="js/cityrelatedinfo.js"></script>
	<style type="text/css">
.error {
	color: red;
	font-weight: bold;
	position: relative;
}
</style>
</head>
  <body>
  <script>
 
</script>
  <style>
  .error {
	color: red;
	font-weight: bold;
	position: relative;
}
  </style>
  
  <script>
  $(document).ready(function(){
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
	  
	  
	    var countryListJson = <c:out default="[]" escapeXml="false" value="${not empty countryList?countryList:'[]'}" />;
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
	 		   
	 	});

	  });
	  
  
  
  function onemailAlertCheckBox(checkbox){
	   if (checkbox.checked == true) {
		   $("#emailAlert_id").val("Y");
	    }else{
	    	$("#emailAlert_id").val("N");
	    }
	
  }   
  
	  </script>
	  

 
  
  <a name="home" id="home"></a>
   
	<!-- price -->
	
	<section class="member-settins1">
		<div class="container">
			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
				<h1 class="animated fadeInDown">Settings 
					<!-- <button type="button" class="btn btn-warning btn2" style="position:absolute; right:0;"><i class="fa fa-pencil-square"></i> Edit Profile</button> --></h1>
				</div>
				<div class="col-md-1 col-sm-1"></div>
			</div>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-5 col-sm-5">
					<c:if test="${not empty userUpdate}">
						<div class="alert alert-success">
				     <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
         <strong>Success!</strong>   ${userUpdate}
       </div>
					</c:if>
					<div class="graybg">
					<form:form method="POST"
								action="${pageContext.request.contextPath}/fixer/updateFixerSettings"
								modelAttribute="member" commandName="member">
								
						<h2>My Information</h2>
						<%--  <form:errors path="exist" cssClass="error" /> --%>
						<c:if test="${not empty exist}">
						<div class="error">${exist}</div>
					</c:if>
						<fieldset>
						   <legend><spring:message code="welcome.first&lastName" /><span>*</span></legend>
						  <form:input type="text" path="firstName" class="form-control"
												id="firstName" value="${member.firstName}${member.lastName}" /> 
			          
						</fieldset>
						 <form:errors path="firstName" cssClass="error" />
						<fieldset>
						  <legend><spring:message code="welcome.email" /><span>*</span></legend>
							<form:input type="text" path="email" class="form-control" id="email" value="${member.email}" />
						</fieldset>
							<form:errors path="email" cssClass="error" />
						<fieldset>
						  <legend>	<spring:message code="welcome.username" /><span>*</span></legend>
						<form:input type="text" path="userName" class="form-control" id="userName" value="${member.userName}" />
						</fieldset>
						<form:errors path="userName" cssClass="error" />
						<%-- <fieldset>
						  <legend><spring:message code="welcome.password" /><span>*</span></legend>
						<form:input type="password" path="password"
												class="form-control" id="password" value="${member.password}" />
						</fieldset>
						<form:errors path="password" cssClass="error" /> --%>
						<fieldset>
						<legend><spring:message code="welcome.linkedinProfile" /></legend>
						<form:input type="text" path="linkedinProfile"
												class="form-control" id="password" value="${member.linkedinProfile}" />
						</fieldset>
						<form:errors path="linkedinProfile" cssClass="error" />
						<fieldset>
							<legend><spring:message code="welcome.paypalId" /></legend>
						<form:input type="text" path="paypalId"
												class="form-control" id="password" value="${member.paypalId}" />
						</fieldset>
						<form:errors path="paypalId" cssClass="error" />
						<fieldset>
						  <legend><spring:message code="welcome.city" /><span>*</span></legend>
					<form:input class="form-control" type="text" path="city"
												value="${member.city}" id="f_elem_city" />
						</fieldset>
						<form:errors path="city" cssClass="error" />
						<fieldset>
						  <legend><spring:message code="welcome.country" /><span>*</span></legend>
						 <div id="the-basics">
						<form:input type="text" path="country"  class="typeahead"
					      id="smart_search"  value="${member.country}" />
					   </div>	
						</fieldset>
						
						
						<form:errors path="country" cssClass="error" />
						
						<fieldset>
						<legend>TimeZone<span>*</span></legend>
						<form:select path="timeZone">
					  <form:option value="${member.timeZone}" label="" >${member.timeZone}</form:option>
					  <form:options items="${timeZoneList}" />
				       </form:select>
						</fieldset>
						
						<button type="submit" class="btn btn-primary btn-block"><i class="fa fa-check-circle" ></i> Update Fixer Profile</button>
					</form:form>
					</div>
					
					
				</div><!-- // left close // -->
				
				<div class="col-md-5 col-sm-5">
					
					<div class="graybg">
						<form:form method="POST"
								action="${pageContext.request.contextPath}/fixer/updateFixerAlert"
								modelAttribute="fixerAlert" commandName="fixerAlert">
						<h2>My Alerts</h2>
						
						
						  <div class="" style="height:40px; width:100%; clear:both;">
							<h3>Email Alerts 
							<input type="hidden"  name ="emailAlert"  value="${fixerAlert.emailAlert}"  id="emailAlert_id" >
							<c:choose>
							<c:when  test="${fixerAlert.emailAlert=='Y'}">
							<span class="switcher"><input  checked    onclick="onemailAlertCheckBox(this)" type="checkbox"  ></span>
							</c:when>
							<c:otherwise>
							<span class="switcher"><input   onchange="onemailAlertCheckBox(this)" type="checkbox"  ></span>
							</c:otherwise>
							</c:choose>
							</h3>
							
						</div>
						<div class="clearfix"></div>
						<fieldset>
						  <legend>Email</legend>
							<input type="text" placeholder="" value="${member.email}" readonly>
						</fieldset>
						
						<button type="submit" class="btn btn-primary btn-block"><i class="fa fa-check-circle"></i> Update Alert</button>
	                     </form:form>					
						
					</div>
					<div class="graybg">
					<form:form method="POST"
								action="${pageContext.request.contextPath}/fixer/saveNewPassword"
								modelAttribute="newPassword" commandName="newPassword">
		<%-- 			  <form id="" action="${pageContext.request.contextPath}" method="POST"> --%>
						<h2>Change Password</h2>
					<c:if test="${not empty passwordError}">
						<div class="error">${passwordError}</div>
					</c:if>
						<div class="clearfix"></div>
						<%-- <fieldset>
						  <legend>	<spring:message code="welcome.currentPassword" /><span>*</span></legend>
						<form:input type="password" path="currentPassword" class="form-control" id="currentPassword" value="" />
						</fieldset>
						<form:errors path="currentPassword" cssClass="error" /> --%>
						
						<fieldset>
						  <legend>	<spring:message code="welcome.newPassword" /><span>*</span></legend>
						<form:input type="password" path="newPassword" class="form-control" id="newPassword" value="" />
						</fieldset>
						<form:errors path="newPassword" cssClass="error" />
						
						<fieldset>
						  <legend>	<spring:message code="welcome.confirmNewPassword" /><span>*</span></legend>
						<form:input type="password" path="confirmNewPassword" class="form-control" id="confirmNewPassword" value="" />
						</fieldset>
						<form:errors path="confirmNewPassword" cssClass="error" />
						
						
						<!-- <fieldset>
						  <legend>Password</legend>
							<input type="password" placeholder="" value="" >
						</fieldset>
						
						<fieldset>
						  <legend>Confirm Password</legend>
							<input type="password" placeholder="" value="" >
						</fieldset> -->
					
                     	<button type="submit" class="btn btn-primary btn-block"><i class="fa fa-check-circle"></i> Update Password</button>
						<%-- </form> --%>
						</form:form>
						 
					</div>
				</div><!-- // right close // -->
				<div class="col-md-1 col-sm-1"></div>
			</div>
			<br>
			
			
			
			
			<!-- category -->
			<div id="myModal1" class="modal fade" role="dialog">
			  <div class="modal-dialog modal-lg">

				<!-- Modal content-->
				<div class="modal-content">
				  <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h2 class="modal-title">Categories</h2>
				  </div>
				  <div class="modal-body">
					<div class="row">
									<div class="col-md-4">
										<h3 style="margin-bottom:10px;"><strong>Financial Accounting <br>(this top node is not selectable)</strong></h3>
										<ul style="list-style:none; text-align:left !important; margin-left:0px; padding-left:0px;">
											<li><input type="checkbox"> General Ledger</li>
											<li><input type="checkbox"> New General ledger</li>
											<li><input type="checkbox"> Accounts Receivable</li>
											<li><input type="checkbox"> Credit Management</li>
											<li><input type="checkbox"> Accounts Payable</li>
											<li><input type="checkbox"> Treasury</li>
											<li><input type="checkbox"> Electronic Banking</li>
											<li><input type="checkbox"> Value Added Tax (V.A.T)</li>
											<li><input type="checkbox"> Deferred Tax</li>
											<li><input type="checkbox"> Withholding Tax</li>
											<li><input type="checkbox"> US Taxes</li>
											<li><input type="checkbox"> Fixed Assets</li>
											<li><input type="checkbox"> Special Purpose Ledger</li>
											<li><input type="checkbox"> Lease Accounting</li>
											<li><input type="checkbox"> Funds Management</li>
											<li><input type="checkbox"> Financial Supply Chain Management</li>
											<li><input type="checkbox"> Closing Cockpit</li>
											<li><input type="checkbox"> Travel Management</li>
										</ul>	
									</div>
									<div class="col-md-4">
										<h3 style="margin-bottom:10px;"><strong>Controlling <br>(this top node is not selectable)</strong></h3>
										<ul style="list-style:none; text-align:left !important; margin-left:0px; padding-left:0px;">
											<li><input type="checkbox"> Cost Element Accounting</li>
											<li><input type="checkbox"> Cost Center Accounting</li>
											<li><input type="checkbox"> Cost center Planning</li>
											<li><input type="checkbox"> Internal Orders</li>
											<li><input type="checkbox"> Activity Based Costing</li>
											<li><input type="checkbox"> Product Cost Controlling</li>
											<li><input type="checkbox"> Material Ledger</li>
											<li><input type="checkbox"> Sales Order Costing</li>
											<li><input type="checkbox"> Split Valuation</li>
											<li><input type="checkbox"> Profitability Analysis</li>
											<li><input type="checkbox"> Profit Center Accounting</li>
											<li><input type="checkbox"> Profit Center Transfer Pricing</li>
										</ul>
									</div>
									<div class="col-md-4">
										<h3 style="margin-bottom:10px;"><strong>Other</strong><br></h3>
										<ul style="list-style:none; text-align:left !important; margin-left:0px; padding-left:0px;">
											<li><input type="checkbox"> Project Systems</li>
											<li><input type="checkbox"> Real Estate Management</li>
											<li><input type="checkbox"> Enterprise Controlling Consolidation System</li>
											<li><input type="checkbox"> Simple Finance</li>
											<li><input type="checkbox"> Investment Management</li>
											<li><input type="checkbox"> Business Planning and Consolidation</li>
											<li><input type="checkbox"> Governance, Regulation and Compliance (GRC)</li>
											<li><input type="checkbox"> Not Sure</li>
										</ul>
									</div>
									
								</div>
				  </div>
				  <div class="modal-footer">
					<button type="button" style="border:none;padding: 0px 25px;
font-size: 34px !important; border-radius:5px;" class="btn-info" data-dismiss="modal">save</button>
				  </div>
				</div>

			  </div>
			</div>
			
			
			
			<!-- popup message -->
			  <div class="modal fade" id="myModal" role="dialog">
				<div class="modal-dialog ">
				
				  <!-- Modal content-->
				  <div class="modal-content">
					<!-- <div class="modal-header">
					  <button type="button" class="close" data-dismiss="modal">&times;</button>
					  <h4 class="modal-title">Modal with Dark Overlay</h4>
					</div> -->
					<div class="modal-body">
					  <h1 class="text-success text-center">Issue Create successfully!</h1>
					  <h3 class="text-center">We Guarantee that a Fixer will begin within 24 hours.</h3>
					</div>
					<div class="modal-footer" style="text-align:center;">
					  <button type="button" class="btn btn-danger" style="padding: 2px 17px;
font-size: 20px;" data-dismiss="modal">Dismiss</button>
					</div>
				  </div>
				  
				</div>
			  </div>
			<!-- Modal -->
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		</div>
	</section>
	<!-- price close -->
	<br>
	
	
  </body>
