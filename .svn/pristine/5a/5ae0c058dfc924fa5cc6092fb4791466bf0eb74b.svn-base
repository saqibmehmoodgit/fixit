<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<title>ERPfixers | Become a Fixer</title>
<head>
<script src="js/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/flick/jquery-ui.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="js/cityrelatedinfo.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jstz-1.0.4.min.js"></script>
<style type="text/css">
.error {
	color: red;
	font-weight: bold;
	position: relative;
}
</style>
<script>
 	$(document).ready(function() {
		var url = ${url};
		if(window.parent != null)
			{
			window.parent.location.href = url;
			}
		else{
		window.location=url;
		}
	}); 

</script>
<style>
#loading {
	display: block;
	position: fixed;
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	z-index: 100000;
	width: 100vw;
	height: 100vh;
	background-color: rgba(255, 255, 255, 0.92);
	background-image: url("images/pageLoader.gif");
	background-repeat: no-repeat;
	background-position: center;
}
</style>
</head>
<body>
	<div id="loading"></div>
</body>
</html>