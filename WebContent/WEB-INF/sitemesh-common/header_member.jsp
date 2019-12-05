<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 
<script type="text/javascript">
	//<![CDATA[
	function preloader() {
		document.getElementById("loader").style.display = "none";
		document.getElementById("pagewrap").style.display = "block";
	}
	window.onload = preloader;
	//]]>
</script>

<div id="loader">
		<img src="${pageContext.request.contextPath}/images/loader.gif" alt="">
	</div>
	

<div class="overlay-menu"></div>
 <aside>
		<div class="logo">
		   <a href="${pageContext.request.contextPath}/member/dashBoard"><img src="../images/logo.jpg" alt="logo" ></a>
		    ${myUser.userName}
		
		</div>
		<div class="menuCL">
			<i class="icon icon-cross-icon-2-1"></i>
		</div>
		<ul>
		
		    <li><a id="dashboard" href="${pageContext.request.contextPath}/member/dashBoard?msgKey=R" class="active"><i data-icon="b" class="icon"></i> Dashboard</a></li>
			<li><a id="newRequest" href="${pageContext.request.contextPath}/member/newRequest" ><i data-icon="e" class="icon"></i> New Request</a></li>
			<li><a id="openRequest" href="${pageContext.request.contextPath}/member/request?msgKey=R"><i class="icon icon-circle-1"></i> Open Requests (${openRequestCount})</a></li>
			<li><a id="inprogress" href="${pageContext.request.contextPath}/member/request?msgKey=R&status=InProgress"><i data-icon="g" class="icon"></i> In Progress (${inProgressCount})</a></li>
			<li><a  id="closed" href="${pageContext.request.contextPath}/member/request?msgKey=R&status=Closed"><i data-icon="d" class="icon"></i> Fixed (${fixedIssuesCount})</a></li>
		  	
		  	<li><a style="display:none" id="reAssinged" href="javaScript:void(0)"><i data-icon="d" class="icon"></i> Reassign Request</a></li>
		  
		
			
		</ul>
		
		<div class="logout"> <a href="${pageContext.request.contextPath}/logout"><i data-icon="i" class="icon"></i> Logout</a></div>
    </aside>
	<!-- Header start-->
    <header>
		<div class="logo">
			<a href="${pageContext.request.contextPath}/member/dashBoard"><img src="../images/logo.jpg" alt="logo"></a>
		    ${myUser.userName}
			<img src="../images/menuBarH.png" alt="" class="mMneu">
		</div>
	</header>
    <!-- Header end-->

  
  