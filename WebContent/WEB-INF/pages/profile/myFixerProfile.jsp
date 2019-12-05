<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<title>ERPfixers |  My Profile</title>
	<head>
  <script type="text/javascript" src="../js/jstz-1.0.4.min.js"></script>
</head>
  
  <body>
<script>
 
</script>
<script type="text/javascript">




$(document).ready(function(){ 
	showStars();
	
	 });
 

	 $(function() {    		
	$('input[type=submit]').click(function() {
		$('p').html('<span class="stars">'+parseFloat($('input[name=amount]').val())+'</span>');
		
	});    		
	$('input[type=submit]').click();
});
 
$.fn.stars = function() {
	return $(this).each(function() {
		console.log($(this).html());
		
		$(this).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($(this).html())))) * 16));
	});
}

function showStars(){
	/* $('p').html('<span class="stars">'+parseFloat($({"fixerRating"}+'</span>'); */
	$('span.stars').stars();
}

</script>
	
	<section class="member">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					
					<c:if test = "${User_Type == 'admin'}">
								<h1 class="animated fadeInDown">Fixer  Profile</h1>
								</c:if>
								<c:if test = "${User_Type == 'fixer'}">
								<h1 class="animated fadeInDown">My  Profile</h1>
								</c:if>
				</div>
			</div>
			<br><br>
			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
				<div class="profilOuter">
					<div class="createmyprofile">
						<div class="row">
							<div class="col-md-5 col-sm-5">
								<div class="profilepic">
									<div class="btn-file">
									<img src='${fixerImagePath}' onerror="this.src ='../images/profile-pic.jpg'">
										<!-- <img src="../images/default_profile.jpg"> -->
									<!-- 	<input type="file"> -->
									</div>
									<div class="profilOuter1">
									<div class="row">
									<div class="col-md-8">
									<c:choose >
									<c:when test = "${User_Type == 'admin'}" >
									<p>Profile Created:: ${lastLogin} ago.</p>
									</c:when>
									<c:otherwise>
									<p>Last login: ${lastLogin} ago.</p>
									</c:otherwise>
									</c:choose>
									
										<c:if  test="${user.timeZone!=null }">
										<p id="timeZone" >TimeZone :${user.timeZone}</p>
										</c:if>
										
										<p>Location: ${user.city} , ${user.country}</p>
										<p><span class="stars">${fixerRating}</span></p>
									</div>
									<div class="col-md-4">
										<img src="../flags/${user.country}.png" class="pull-right1">
										<div class="clearfix"></div>
										<p class="text-success pull-right1">Verified   <i class="fa fa-check-circle"></i></p>
									</div>
								</div>
								</div>
								</div>	
							</div>
							<div class="col-md-7 col-sm-7">
								<div class="profildiv">
								<c:if test = "${User_Type == 'admin'}">
								<h2> ${user.userName}</h2>
								</c:if>
								<c:if test = "${User_Type == 'fixer'}">
								<h2> ${user.userName}<a href="${pageContext.request.contextPath}/fixer/editProfile" class="btn btn-info pull-right"><i class="fa fa-pencil-square"></i> Edit Profile</a></h2>
								</c:if>
								
										   <c:if test="${not empty user.linkedinProfile}">
										   
										     <c:choose>
 													<c:when test="${fn:contains(user.linkedinProfile, 'http')}">
                                                         		<p class="linkedinProfile"><i class="fa fa-linkedin"></i> : <a href="${user.linkedinProfile}" target="#blank">${user.linkedinProfile}</a></p>

  													</c:when>
  
  													<c:otherwise>
												<p class="linkedinProfile"><i class="fa fa-linkedin"></i> : <a href="https://${user.linkedinProfile}" target="#blank">${user.linkedinProfile}</a></p>
 														  </div>
 													</c:otherwise>
											 </c:choose>
                                                       
										   
					                       </c:if>
								
										   <c:if test="${not empty user.paypalId}">
										   <p class="linkedinProfile" style="margin: -24px 0 0 0;"><img src="../images/paypal.png" alt="paypal" style="width:70px;">: <a href="https://www.paypal.com/" target="#blank">${user.paypalId}</a></p>	
					                       </c:if>
								
									<p>Overview<br>${user.overview} </p>
								
								<%-- <h2>My Industry(s)</h2>
								<div class="cat">
								 <c:forEach var="industry" items="${industry}">
									<span class="btn btn-warning">${industry.indstName}</span>
                                 </c:forEach> --%>
                                 <h2>Main Module(s)</h2>
									<div class="cat">
								 <c:forEach var="parentCat" items="${parentCat}">
									<span class="btn btn-warning" style="cursor:default;">${parentCat}</span>
                                 </c:forEach>
										
	                                 
									</div>
									
									<h2>My Category(s)</h2>
									
								<div class="cat">
								 <c:forEach var="category" items="${category}">
									<span class="btn btn-warning" style="cursor:default;">${category.catgName}</span>
                                 </c:forEach>
								</div>
								
								
								</div>
							
							</div>
						</div>
					</div>
					
					
					
					<div class="clearfix"></div>
					<c:if test = "${User_Type == 'admin'}">
								<div class="memberstatus">
						<h2>Fixer Status</h2>
						<div class="row">
							<div class="col-md-4 col-sm-4">
								Resolved Queries = ${resolvedPercentage}
							</div>
							<div class="col-md-4 col-sm-4">
								Average Fixer Review =${fixerRating}
							</div>
							<div class="col-md-4 col-sm-4">
								Average Response Time = ${responseTime} hours
							</div>
						</div>
					</div>
								</c:if>
								<c:if test = "${User_Type == 'fixer'}">
								<div class="memberstatus">
						<h2>Fixer Status</h2>
						<div class="row">
							<div class="col-md-4 col-sm-4">
								Resolved Queries = ${resolvedPercentage}
							</div>
							<div class="col-md-4 col-sm-4">
								Average Fixer Review = ${fixerRating}
							</div>
							<div class="col-md-4 col-sm-4">
								Average Response Time = ${responseTime} hours
							</div>
						</div>
					</div>
								</c:if>
					
					
					
					
					
					
					
					
					
					
					
					
					
				</div>	
				</div>
				<div class="col-md-1 col-sm-1"></div>
			</div>
			
			
			
			
			
			
			
			
			
			
			
		</div>
	</section>
	
	
	

    
  </body>