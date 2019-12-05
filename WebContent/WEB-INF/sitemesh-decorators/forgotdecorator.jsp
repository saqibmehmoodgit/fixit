<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title></decorator:title></title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/adminscript.js"></script>
	<link href="${pageContext.request.contextPath}/adminCss/font-awesome.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/adminCss/bootstrap.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/adminCss/style.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/adminCss/animate.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/adminCss/responsive.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/adminCss/responsive.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet" type="text/css">
</head>
<body class="bodywhote">
	<jsp:directive.include
		file="/WEB-INF/sitemesh-common/header_forgotpassword.jsp" />
	<decorator:body />
	<jsp:directive.include file="/WEB-INF/sitemesh-common/footer.jsp" />
</body>
</html>