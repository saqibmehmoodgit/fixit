<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jstz-1.0.4.min.js"></script>

<meta charset="utf-8">
<meta http-equiv="" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>ERPfixers | Login</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


<link href="adminCss/font-awesome.css" rel="stylesheet" type="text/css">
<link href="adminCss/bootstrap.css" rel="stylesheet" type="text/css">
<link href="adminCss/style.css" rel="stylesheet" type="text/css">
<link href="adminCss/animate.css" rel="stylesheet" type="text/css">
<link href="adminCss/responsive.css" rel="stylesheet" type="text/css">


</head>

<body>
	<script>
 
</script>
	<a name="home" id="home"></a>
	<header class="header">
		<div class="container-fluid">
			<div class="mainhead">
				<div class="logo">
					<a href="index.html"> <img alt="" src="images/logo.png"
						width="90">
					</a>
				</div>
				<div class="navigation">
					<c:choose>
			    <c:when test="${blog == 'active'}">
					<ul class="mainav">
						<li><a href="index.html">Home</a></li>
						<li><a href="index.html#how-work">How it Works</a></li>
						<li><a href="index.html#testimonials">Testimonials</a></li>
						<li><a href="index.html#about">About</a></li>
						<li><a href="blogList" class="active">SAP Tips</a></li>
						<li><a href="index.html#pricing">Pricing</a></li>
						<li><a href="index.html#contact">Contact Us</a></li>
						<li><a href="login.html">Login</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="mainav">
						<li><a href="index.html">Home</a></li>
						<li><a href="index.html#how-work">How it Works</a></li>
						<li><a href="index.html#testimonials">Testimonials</a></li>
						<li><a href="index.html#about">About</a></li>
						<li><a href="blogList">SAP Tips</a></li>
						<li><a href="index.html#pricing">Pricing</a></li>
						<li><a href="index.html#contact">Contact Us</a></li>
						<li><a href="login.html" class="active">Login</a></li>
					</ul>
				</c:otherwise>	
				</c:choose>
				</div>
				
				
				
			</div>
		</div>
	</header>
	<!--header close-->


		
	<section class="member">
		<div class="container">
			<div class="row">
				<div class="col-md-3 col-sm-3"></div>
					<div class="col-md-6 col-sm-6">
					    
					    <h2 style="font-size:27px; text-align:center;">Your are Already Signed in From Another Browser/Device Please <a href="login" style="text-decoration:underline;">Click Here</a> to 
					   Login Again
					    </h2>
					    
					    
					     <!-- <a href="login"><button type="submit" class="btn btn-success btn-block">Login Again</button></a> -->
					</div>
					<div class="col-md-3 col-sm-3"></div>
			    </div>
			</div>
		</section>	    
 
	
</body>
</html>