<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<title>ERPfixers | Create My Profile</title>
<head>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/favicon.ico"
	type="image/x-icon">
<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico"
	type="image/x-icon">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/custom.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/typeahead.bundle.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/pages/script.js"></script>
	
<script type="text/javascript" src="../js/jstz-1.0.4.min.js"></script>
<style type="text/css">
.error {
	color: red;
	font-weight: bold;
	position:absolute;
	bottom: 94px;
    right: 13px;
}
</style>
<link href="../css/font-awesome.css" rel="stylesheet" type="text/css">

</head>
<body>
	<script>
var catJson=${allCategoryJSON};
var parentCatJson = ${parentCategoryJson};

function getCheckedBoxes(chkboxName) {
	  var checkboxes = document.getElementsByName(chkboxName);
	  var checkboxesChecked = [];
	  for (var i=0; i<checkboxes.length; i++) {
	     if (checkboxes[i].checked) {
	        checkboxesChecked.push(checkboxes[i].value);
	     }
	  }
	  return checkboxesChecked.length > 0 ? checkboxesChecked : null;
	}

</script>


	<script type="text/javascript">
	
	
	
   
	$(document).on('submit','#contact-form',function(){
		 
		  
		    var imgVal = $('#multipartFile').val(); 
		    var imgUrlVal = $('#myImageUrl').val();
		    if(imgVal=='' && imgUrlVal=='') 
		    { 
		         
		        //$("#profilePicErr").show();
		        $("#profilePicErr").css("visibility", "visible");
		        return false;

		    } 
		});
	
    	
      var client = new XMLHttpRequest();
      function uploadProfileImage(){
    	  
    	  var filePath =document.getElementById("multipartFile").value;
  		  var fileName = filePath.split('\\').pop();
  		  var fileExtension = fileName.split('.').pop().toLowerCase();
  		  if(fileExtension=="jpg" ||fileExtension=="jpeg" || fileExtension=="png"){
  			document.getElementById("myImageUrl").value="../images/default_profile.jpg";
  			$("#uploadtxt").css('display', 'none');
    		$("#imageLoader").show();
    	    var multipartFile = document.getElementById("multipartFile");
    	  / Create a FormData instance /
    	    var formData = new FormData();  
    	    formData.append("multipartFile", multipartFile.files[0]);
    	   /*  var id=$('#userId').val();
    	    console.log(id); */
    	    formData.append("userName",document.getElementById("userName").value);
    	    //console.log(formData);
    	    $('#saveButton').attr('disabled', 'disabled');
    	    client.open("post", "../fixerProfileImage", true);
    	    client.send(formData);
  		  }else{
  			  alert("Invalid File Format . Only JPG,JPEG,PNG File Format Supported");
  			  return ;
  		  }
    	 	
      }
      client.onreadystatechange=function()
      {
    	  if (client.readyState==4 && client.status==200)
    	    {
    	  	 var data=jQuery.parseJSON(client.response);
    	  	/*$("#documentLoader").hide();
    	  	var html='<p class="text-success alert"  onclick="deleteFile(\''+data.result.originalFileName+'\')"  ><input type="text" name ="documents" value="'+data.result.originalFileName+'"><i class="fa fa-times"  data-dismiss="alert" aria-label="close"  ></i></p>';
    	  	$("#documentNames").append(html); */
    	  
    	 
    	  	document.getElementById("myImageUrl").value=data.result.originalFileName;
    	  	$('#myProfileImage').attr("src",data.result.originalFullFileName);
      	  $("#profilePicErr").css("visibility", "hidden");
      	  $("#socialImageUrl").attr("value",'');

    		$("#imageLoader").hide();
    		$("#uploadtxt").css('display', 'block');
    		$('#saveButton').removeAttr('disabled');
    	  	 console.log(data); 
    	    }
    	  }
      
       
   </script>
	<section class="member membersignup">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="animated fadeInDown">${CreateOrEdit}</h1>
				</div>
			</div>
			
			<form:form role="form" class="contact-form" id="contact-form"
				method="post"
				action="${pageContext.request.contextPath}/fixerSignUp/step2"
				modelAttribute="profile" commandName="profile"
				enctype="multipart/form-data">
				<div class="row">
					<form:input id="myImageUrl" path="imageUrl" type="hidden"
						value="${profile.imageUrl}" />
					<form:input id="socialImageUrl" path="socialImageUrl" type="hidden"
						value="${profile.imageUrl}" />
				 <form:input id="socialLogin" path="socialLogin" type="hidden"
						value="${profile.socialLogin}" />	 
					<div class="col-md-1 col-sm-1"></div>
					<div class="col-md-10 col-sm-10">
						<div class="profilOuter">
							<div class="step">${Step}</div>
							<div class="createmyprofile">
								<div class="row">
									<div class="col-md-5 col-sm-5">
										<div class="profilepic">
											<div class="btn-file">
												<img src='${profile.imageUrl}'
													onerror="this.src ='${pageContext.request.contextPath}/images/profile.png'"
													id="myProfileImage" accept=".png, .jpg, .jpeg">
												<form:input type="file" path="multipartFile"
													id="multipartFile" onchange="uploadProfileImage()" />
										<%-- <form:errors path="multipartFile" cssClass="error" /> --%>
<span id="profilePicErr" style="visibility: hidden;" class="error-img">
			Please upload profile
			
			</span>
												<img
													src="${pageContext.request.contextPath}/images/loader.gif"
													style="position: absolute; top: 38%; left: 38%; display: none; width: 45px; height: 45px; z-index: 9999;"
													id="imageLoader"> <span id="uploadtxt"
													class="uploadtxt">Upload Photo</span>
											</div>
											<div class="profilOuter1">
												<div class="row">
													<div class="col-md-8">
														<!--  <p>Last login: 2 hours ago.</p> -->
														<!--  <p  id="timeZone" ></p> -->
														<c:if test="${profile.timeZone!=null }">
															<p>TimeZone : ${profile.timeZone}</p>
														</c:if>

														<p>Location: ${profile.city} , ${profile.country}</p>
													</div>
													<div class="col-md-4">
														<img src="../flags/${profile.country}.png"
															class="pull-right1">
														<p class="text-success pull-right1">
															Verified <i class="fa fa-check-circle"></i>
														</p>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-7 col-sm-7">
										<form:input path="userId" type="hidden"
											value="${profile.userId}" />
										<form:input path="userId" type="hidden"
											value="${profile.userId}" />
										<form:input path="email" type="hidden"
											value="${profile.email}" />
										<form:input path="firstName" type="hidden"
											value="${profile.firstName}" />
										<form:input path="lastName" type="hidden"
											value="${profile.lastName}" />
										<form:input path="city" type="hidden" value="${profile.city}" />
										<form:input path="country" type="hidden"
											value="${profile.country}" />
										<form:input path="password" type="hidden"
											value="${profile.password}" />
										<form:input path="userName" type="hidden"
											value="${profile.userName}" id="userName" />
										<form:input path="mobileNumber" type="hidden"
											value="${profile.mobileNumber}" />
										<form:input path="linkedinProfile" type="hidden"
											value="${profile.linkedinProfile}" />
										<form:input path="userType" type="hidden"
											value="${profile.userType}" />
										<form:input path="fixerStatus" type="hidden"
											value="${profile.fixerStatus}" />
										<form:input path="paypalId" type="hidden"
											value="${profile.paypalId}" />
										<form:input path="timeZone" type="hidden"
											value="${profile.timeZone}" />
										<form:input path="sendEmail" type="hidden"
											value="${profile.sendEmail}" />
										<div class="form-group">
											<div class="title">${profile.userName}
												<span>Background and Experience.</span>
											</div>
											<div class="height">
												<label>Type here</label>
												<form:textarea placeholder="" path="overview" id="overview"
													style="position: relative;
    margin: 0px;width: 100%;border: 1px solid #ccc;padding: 2px 10px; color: #999;"
													class="form-control"></form:textarea>
												<%--  <form:input type="text" path="overview"  id="overview" value="${profile.overview}" /> --%>
											</div>
										</div>

										<div class="categoriesDiv">
											<div id="categoryNameId"></div>
										</div>
										<div class="title">
											My Category(s) <span>Search for your category or
												select from the dropdown.</span>
										</div>

										<div>
											<label>Smart Search</label>
											<div id="the-basics">
												<input class="typeahead form-control" type="text"
													placeholder="" id="smart_search">
											</div>
										</div>
										<!--  <div class="categorydis" id = "addCategory" ></div> -->

										<div data-toggle="modal" data-target="#myModal"
											style="cursor: pointer;">
											<label class="categories_lable">Categories</label> 
											
											<div id="categories_input"
												style="width: 100%; border: 1px solid #ccc; border-radius: 5px; height: 34px; cursor: pointer; margin-bottom: 15px;"><i
												class="fa fa-caret-down"
												style="float: right; margin: 6px 10px 0 0; font-size: 22px;"></i>
										</div>
											
										</div>
										<form:errors path="categories" cssClass="error" />
										<button type="submit" class="saveChange"
											style="float: left; margintop: 15px;" id="saveButton">Save
											Profile</button>
									</div>
									<!-- Modal -->
									<!-- category -->
									<!-- category -->
									<div id="myModal" class="modal fade" role="dialog">
										<div class="modal-dialog modal-lg">

											<!-- Modal content-->
											<div class="modal-content">
												<!-- <div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							
						</div> -->
												<div class="modal-body">
													<h2 class="h1-underline">Categories</h2>
													<ul class="ChooseCategories">
														<c:forEach var="parentCat" items="${parentCategory}">
															<li><div class="categoriesT"
																	id="categoriesT${parentCat.catId}">${parentCat.catgName}</div>
																<div class="categoriesList" style="display:none"
																	id="categoriesL${parentCat.catId}">
																	<ul class="MyIndus">
																		<c:forEach var="childCat" items="${allCategory}">
																			<c:if test="${childCat.parentId==parentCat.catId}">
																				<li><input name="categories"
																					id="catId${childCat.catId}"
																					value="${childCat.catId}"
																					onclick="onChildCatClick(${childCat.catId})"
																					type="checkbox"> <label for="categories">${childCat.catgName}</label>
																				</li>
																			</c:if>
																		</c:forEach>

																	</ul>
																</div></li>
														</c:forEach>
													</ul>
												</div>
												<div class="modal-footer">
													<button type="button" class="saveChange width100B"
														data-dismiss="modal" onclick="onSave()">Save</button>
													<button type="button" class="cancel width100B"
														data-dismiss="modal" onclick="onCancel()">Cancel</button>
												</div>
											</div>

										</div>
									</div>




									<!-- categories close  -->
									<!-- Modal -->
									<div id="myModal" class="modal fade" role="dialog">
										<div class="modal-dialog modal-lg">
											<!-- Modal content-->
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">&times;</button>
													<h2 class="modal-title">Industries</h2>
												</div>
												<div class="modal-body">
													<ul class="catindus" id="industry_list">
														<c:forEach var="industry" items="${industry}">
															<li id="industryLi${industry.indstId}"><form:checkbox
																	id="categCheckBoxIndust${industry.indstId}"
																	value="${industry.indstId}" path="industries" />
																${industry.indstName}</li>
														</c:forEach>
													</ul>
												</div>
												<div class="modal-footer">
													<button
														style="border: none; padding: 0px 25px; font-size: 34px !important; border-radius: 5px;"
														class="btn-info" data-dismiss="modal">save</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-1 col-sm-1"></div>
			</form:form>
		</div>



	</section>

	<script type="text/javascript" src="../js/pages/category.js"></script>
</body>