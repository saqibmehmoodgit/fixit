<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>ERPFixers | Admin</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/typeahead.bundle.js"></script>
<script src="${pageContext.request.contextPath}/js/pages/script.js"></script>
<script src="${pageContext.request.contextPath}/editor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/editor/samples/js/sample.js"></script>
<script src="${pageContext.request.contextPath}/editor/plugins/dragdrop_0.1.0_0/dragdrop/plugin.js"></script>

<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet" type="text/css">
<style>
.error {
 color: red;
 font-weight: bold;
}
</style>

<script>
var allCategory = ${allCategory};
$(document).on('click','#the-basics_cat .tt-selectable',function(){
 	   var str = $("#smart_search_cat").val();
 	   var value = findCatValueFromKey(catList,str);
 	   var parentId=findParentIdValueFromcatKey(catParentList,value);
 	   findCatByparentCatNew(parentId); 
 	   $("#categCheckBox"+value).attr('checked', 'checked');
 	   var html = '<span id="categ'+value+'"  class="btn btn-warning" >'+$(this).text()+' <i class="fa fa-times " onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
      $("#addCategory").append(html);
      $("#smart_search_cat").val('');	
   }); 
 
 
$(document).on('click','ul.catindus.catErp li',function(){
	 var checkbox = $(this).find("input[type='checkbox']");
  var value = $(this).children().eq(0).val();
  if(!checkbox.attr('checked')){
     checkbox.attr('checked', true);
     var html = '<span id="categ'+value+'"  class="btn btn-warning" >'+$(this).text()+' <i class="fa fa-times " onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
  $("#addCategory").append(html);
  }else{
     checkbox.attr('checked', false);
     $("#categ"+value).remove();
  }
});

var catTypeSet = ${catTypeSet};
 if(catTypeSet.length>0){
 var parentId = catTypeSet[0].parentId;
 findCatByparentCat(parentId);
 var html ='';
 for(var i =0 ; i<catTypeSet.length ; i++){
	  $("#categCheckBox"+catTypeSet[i].catId).attr('checked', true);
	  html ='';
	  html = '<span id="categ'+catTypeSet[i].catId+'"  class="btn btn-warning" >'+catTypeSet[i].catgName+' <i class="fa fa-times " onclick="return closebtn('+catTypeSet[i].catId+')" id="close'+catTypeSet[i].catId+'"></i></span>';
     $("#addCategory").append(html);	  
 } 
 }
 function findCatByparentCat(parentId){
	  var html = '';

	  for(var i =0; i< allCategory.length;i++){
	 	 
	    if(allCategory[i].parentId == parentId){
	 	html= '<li class="checkbox" ><input type="checkbox" class="myCheckBoxCat" value="'+allCategory[i].catId+'"  id ="categCheckBox'+allCategory[i].catId+'"  name="catId"  >'+allCategory[i].catgName+'</li>'; 
	      
	      if($("#categoryList"+parentId+" #categCheckBox"+allCategory[i].catId).length==0){
	      	$("#categoryList"+parentId).append(html);
	      }
	      }else{
	     	 var istoggled=  $("#categoryList"+allCategory[i].parentId).is(":visible"); 
	     		if(istoggled){
	     			$("#categoryList"+allCategory[i].parentId).slideUp();
	     		}
	    } 
	   }
	 /*  $("#categoryList"+parentId).show(); */
	  $("#categoryList"+parentId).slideToggle();
	 }


function closebtn(value) {
	$("#categ" + value).remove();
	$("#categCheckBox" + value).attr('checked', false);
}




 $(document).ready(function(){
	 $("#loading1").toggle();
	 $("#submit_form").on("submit", function(){
		   // $('#gif').css('visibility', 'visible');
		   $('#buttonSave').css('display', 'none'); 
		   $('#buttonSaveType').css('display', 'block');
		   $('#publish').css('display', 'none'); 
		   $('#publishType').css('display', 'block');
		    return true;
		    
		});
}); 
 </script>
</head>

<body>
	<a name="home" id="home"></a>
	 

<script>
 
</script>



	<!-- price -->

	<section class="create-issue">
		<div class="container">
			<div class="row">
				<div class="col-md-2 col-sm-2"></div>
				<div class="col-md-8 col-sm-8">
					<h1 class="animated fadeInDown">Add your Tip here*</h1>
					<div class="graybg">
						<div id="loading1"></div>
						
						<form:form method="POST" action="createBlog" modelAttribute="blogBo"
							commandName="blogBo" id="submit_form">
							<h2>
								<span>1.</span> Add Tip title.
							</h2>
							 
								
							 

							<div class="row">
								<div class="col-md-7 col-sm-8 col-xs-12">
									<fieldset>
										<legend>Tip Title</legend>
										<div id="the-basics">
											<form:input type="text" path="blogTitle" class="typeahead" />
										</div>
									</fieldset>
							  <form:errors path="blogTitle" cssClass="error" />


								</div>
							</div>
							<br>
							<h2>
								<span>2.</span> Description
							</h2>
							<main>
							<div class="adjoined-bottom">
								<div class="grid-container">
									<div class="grid-width-100">

										<form:textarea id="editor" path="blogDescription"/>
										<div id="preview"></div>
									</div>
								</div>
							</div>
							</main>
					  <form:errors path="blogDescription" cssClass="error" />
							
							<br>
							<!-- <h2><span>3.</span> Upload File.</h2>
                        
                        <div class="row">
                            <div class="col-md-5 col-sm-5">
                                <button class="btn btn-primary upload">Upload</button>
                                <input style="position: absolute;top: 0px;height: 48px;opacity:0;" type="file" name = "blogFile"/>
                            </div>
                            <div class="col-md-5 col-sm-5">
                                <i class="fa fa-spinner fa-spin" style="font-size: 30px;margin: 12px 0 0 0;"></i>
                            </div> 
                        </div> -->
							<script>
								initSample();
							</script>
							<br>

							<div class="row">
								<div class="col-md-7 col-sm-8 col-xs-12">
									<fieldset data-toggle="modal" data-target="#myModal1"
							style="cursor: pointer;">
							<legend>Categories</legend>
							<div style="cursor: pointer;height: 28px;width: 28px;float: right;background: url(../images/donload.png) 97% 0 no-repeat;">
      								 </div>
						</fieldset>


								</div>
								
							</div>
						<div class="categorydis" id = "addCategory" ></div>
							<br>
							  <form:errors path="catId" cssClass="error" />
							<div class="row">
								<div class="col-md-6 col-sm-6">
									<button type="submit" id="buttonSave" 
								
										class="btn btn-warning btn-block" name="button" value="Save">
										<i class="fa fa-pencil-square-o"></i> Save
									</button>
									<button type="button" id="buttonSaveType" 
										class="btn btn-warning btn-block btnNone" name="button" >
										<i class="fa fa-pencil-square-o"></i> Save
									</button>
								</div>
								
								<div class="col-md-6 col-sm-6" style="visiblity:hidden;">
									<button type="submit" class="btn btn-primary btn-block" id="publish"
									
										name="button" value="Publish">
										<i class="fa fa-upload"></i> Publish
									</button>
									<button type="button" class="btn btn-primary btn-block btnNone" id="publishType"
									
										name="button" >
										<i class="fa fa-upload"></i> Publish
									</button>
								</div>
							</div>

							
							<!-- category -->
							<div id="myModal1" class="modal fade" role="dialog">
								<div class="modal-dialog modal-lg">

									<!-- Modal content-->
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h2 class="modal-title">Categories</h2>
										</div>



										<div class="modal-body modalBodyCat" style="overflow: hidden;">

											<div class="categoryMain">
												<c:forEach var="parentCategory" items="${parentCategory}">
													<div class="dropCat">
														<h2 type="button" id="maincate${parentCategory.catId}"
															value="${parentCategory.catId}"
															onclick="findCatByparentCat(${parentCategory.catId})">
															${parentCategory.catgName} <i class="fa fa-angle-down"></i>
														</h2>
														<ul class="catindus catErp"
															id="categoryList${parentCategory.catId}" style="display:none">
														</ul>
													</div>
												</c:forEach>
											</div>


											<c:forEach var="parentCategory" items="${parentCategory}">





											</c:forEach>













											<div class="row">

												<div class="col-md-4 col-sm-4">
													<div class="leftCat">
														<button class="Showoptions">Select Categories</button>
														<select id="cat1"
															onchange="findCatByparentCat(this.value)"
															multiple="multiple" name="foo" onFocus="" onBlur="">
															<c:forEach var="parentCategory" items="${parentCategory}">
																<option value="${parentCategory.catId}">${parentCategory.catgName}</option>
															</c:forEach>
														</select>
													</div>

												</div>

												<c:forEach var="parentCategory" items="${parentCategory}">

													<div class="col-md-8 col-sm-8">
														<ul class="catindus catErp"
															style="list-style: none; margin: 10px 0 0 -31px; overflow-y: auto; overflow-x: hidden; height: 289px;"
															id="categoryList${parentCategory.catId}">
														</ul>
													</div>

												</c:forEach>

											</div>
										</div>


										<div class="modal-footer">
											<button type="button"
												style="border: none; padding: 0px 25px; font-size: 34px !important; border-radius: 5px;"
												class="btn-info" data-dismiss="modal">Save</button>
										</div>
									</div>

								</div>
							</div>




							<!-- categories close  -->
						</form:form>
					</div>
				</div>
				<div class="col-md-2 col-sm-2"></div>
			</div>
			<br>

			<div class="row">
				<div class="col-md-12 col-sm-12"></div>
			</div>









			<!-- Modal -->
	</section>
	<!-- price close -->
	 

	
</body>

</html>