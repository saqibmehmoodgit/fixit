<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>ERPfixers |  My Profile</title>
	<head>
  <script type="text/javascript" src="../js/jstz-1.0.4.min.js"></script>
  <script type="text/javascript" src="../js/jQuery.circleProgressBar.js"></script>
  <script type="text/javascript" src="../js/raphael-min.js"></script>
  <script type="text/javascript">
    $(function () {
		$('.percent').percentageLoader({
			valElement: 'p',
			strokeWidth:17,
			bgColor: '#F5F5F5',
			ringColor: '#34B7EA',
			textColor: '#34B7EA',
			fontSize: '35px',
			fontWeight: 'bold'
		});
		
		$('.percent1').percentageLoader({
			valElement: 'p',
			strokeWidth:17,
			bgColor: '#F5F5F5',
			ringColor: '#FFD966',
			textColor: '#FFD966',
			fontSize: '35px',
			fontWeight: 'bold'
		});
		$('.percent2').percentageLoader({
			valElement: 'p',
			strokeWidth:17,
			bgColor: '#F5F5F5',
			ringColor: '#449D44',
			textColor: '#449D44',
			fontSize: '35px',
			fontWeight: 'bold'
		});

	});
    </script>
</head>
  
  <body>
<script>
 
</script>
<script type="text/javascript">


</script>
	
	<section class="member">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					
					<c:if test = "${User_Type == 'admin'}">
								<h1 class="animated fadeInDown">Member  Profile</h1>
								</c:if>
								<c:if test = "${User_Type == 'user'}">
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
									
									<img src='${memberImagePath}' onerror="this.src ='../images/profile-pic.jpg'">
										<!-- <img src="../images/default_profile.jpg"> -->
										<!-- <input type="file"> -->
									</div>
									<div class="profilOuter1">
									<div class="row">
									<div class="col-md-8">
										<p>Last login: ${lastLogin} ago.</p>
									     	<c:if  test="${user.timeZone!=null }">
									     		<p id="timeZone" >TimeZone :${user.timeZone}</p>
									     	 </c:if>
										
										<p>Location: ${user.city} , ${user.country}</p>
									</div>
									<div class="col-md-4">
										<img src="../flags/${user.country}.png" class="pull-right1">
										<div class="clearfix"></div>
										<p class="text-success pull-right1">Verified  <i class="fa fa-check-circle"></i></p>
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
								<c:if test = "${User_Type == 'user'}">
								<h2> ${user.userName}<a href="${pageContext.request.contextPath}/member/editProfile" class="btn btn-info pull-right"><i class="fa fa-pencil-square"></i> Edit Profile</a></h2>
								</c:if>
									
									
									<p>Overview<br>${user.overview} </p>
										<p>Company Name<br>${user.companyName} </p>
								
								<h2>My Industry(s)</h2>
								<div class="cat">
								 <c:forEach var="industry" items="${industry}">
									<span class="btn btn-warning">${industry.indstName}</span>
                                 </c:forEach>
									
								</div>
								
								
								</div>
							
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="memberstatus">
						<h2>Member Status</h2>
						<div class="row">
							<div class="col-md-4 col-sm-4">
								<div class="percent" style="width:170px;height:170px;">
									<p style="display:none;">${fixedPercentage}%</p>
									<div class="text-center">Fixed</div>
								</div>
							</div>
						<%-- 	<div class="col-md-4 col-sm-4">
									<div class="percent1" style="width:170px;height:170px; margin:auto;">
									<p style="display:none;">${notFixedPercentage}%</p>
									<div class="text-center">Not Fixed</div>
								</div>
							</div> --%>
							<div class="col-md-4 col-sm-4"><div class="remainingCredit">
									<p style="display:none;"></p>
									<div class="text-center">Remaining Credits: ${remainingPoints}</div>
								</div></div>
						</div>
					</div>
					
					
					
					
					
					
					
					
					
					
					
					
				</div>	
				</div>
				<div class="col-md-1 col-sm-1"></div>
			</div>
			
			
			
			
			
			
			
			
			
			
			
		</div>
	</section>
	
	
	

    
  </body>