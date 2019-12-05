<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator" %>
	<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>ERPfixers | Admin</title>
 <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"> 
    

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/typeahead.bundle.js"></script>
<!-- <script src="../js/jquery.quick.pagination.js"></script> -->
<script src="../js/adminscript.js"></script>
<link href="../adminCss/font-awesome.css" rel="stylesheet" type="text/css">
<link href="../adminCss/bootstrap.css" rel="stylesheet" type="text/css">
<link href="../adminCss/style.css" rel="stylesheet" type="text/css">
<link href="../adminCss/animate.css" rel="stylesheet" type="text/css">
<link href="../adminCss/responsive.css" rel="stylesheet" type="text/css">
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
<body>
	<jsp:directive.include file="/WEB-INF/sitemesh-common/header_admin.jsp" />
	<decorator:body />
	<jsp:directive.include file="/WEB-INF/sitemesh-common/footer.jsp" />
</body>
</html>