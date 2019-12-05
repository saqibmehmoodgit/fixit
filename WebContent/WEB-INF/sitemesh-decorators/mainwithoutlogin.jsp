<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator" %>

<!DOCTYPE html>
<html lang="en  ">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title><decorator:title default="ERPfixers"></decorator:title></title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/favicon.ico"
	type="image/x-icon">
<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico"
	type="image/x-icon">
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

<script
	src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-icon.css">
	<link href="css/font-awesome.css" rel="stylesheet" type="text/css">
	
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/custom.css">
<script type="text/javascript" src="js/typeahead.bundle.js"></script>
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
	  ga('create', 'UA-69774430-1', 'auto');
	  ga('send', 'pageview');

</script>



<decorator:head></decorator:head>
</head>

<body class="bodywhote">
	<jsp:directive.include
		file="/WEB-INF/sitemesh-common/header_withoutlogin.jsp" />
	<decorator:body />
	<jsp:directive.include file="/WEB-INF/sitemesh-common/footer.jsp" />
</body>
</html>