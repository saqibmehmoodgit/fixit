<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<title>ERPfixers | Ask Question</title>

<head>
<script type="text/javascript" src="../js/jstz-1.0.4.min.js"></script>
<style type="text/css">
.error {
	color: red;
	font-weight: bold;
	position: relative;
}
</style>

</head>
<body>
<script>

 
</script>


<script type="text/javascript">
var path = "";
var allCategory =${allCategory}; 
/* function findCatByparentCat(parentId){
	     var html = '';
		 $("#categoryList").empty();
		 for(var i =0; i< allCategory.length;i++){
			  if(allCategory[i].parentId == parentId){
				    html = '<li class="checkbox" ><input type="checkbox"  value="'+allCategory[i].catId+'"  id ="categCheckBox'+allCategory[i].catId+'"  name="categories"  >'+allCategory[i].catgName+'</li>'; 
				 $("#categoryList").append(html); 
				} 
		  }  
} 

 function getTimezoneName() {
    timezone = jstz.determine();
    return timezone.name();
} */

function closebtn(value){
    $("#categ"+value).remove();
    $("#categCheckBox"+value).attr('checked', false);
 }
 
 
// show category in edit Mode 
var modeType = ${modeType};

var catJson = ${allCategory};
var catList=new Array();
var catParentList=new Array();

for(var i=0; i<catJson.length;i++){
    catList.push({
  	key:catJson[i].catgName,
  	value:catJson[i].catId,
    });
  } 	


for(var i=0; i<catJson.length;i++){
	 catParentList.push({
 	key:catJson[i].catId,
 	value:catJson[i].parentId,
   });
 } 


function findCatValueFromKey(catList,key){
	  for(var i in catList){
		  if(catList[i]['key'] == key){
			  return  catList[i]['value'];
		}
	  }
}

 function findParentIdValueFromcatKey(catParentList,key){
	  for(var i in catList){
		  if(catParentList[i]['key'] == key){
			  return  catParentList[i]['value'];
		}
	  }
} 
  
  
 function findCatByparentCatNew(parentId){
	 /* $("#categoryList").empty(); */
	 for(var i =0; i< allCategory.length;i++){
		  if(allCategory[i].parentId == parentId){
			    var html = '<li class="checkbox" ><input type="checkbox"  value="'+allCategory[i].catId+'"  id ="categCheckBox'+allCategory[i].catId+'"  name="categories"  >'+allCategory[i].catgName+'</li>'; 
			 $("#categoryList").append(html); 
			} 
	  }  
}

 
function deleteFile( filePath , fileFirstName ){
	if($('#documentLoader').is(':visible')) {
		alert("Already inProgress");
	    return;
	}
	  var userId = $('#userId').val();
	  $("#documentLoader").show();
	  if(modeType=="CREATE"){
		  
		  $.ajax({
				method : "POST",
			    url : "../member/deleteFileCreate",
			    data : {
			    	fileName : filePath,
			    	userId :userId
			    }
			   }).done(function(response) {
				   $("#documentLoader").hide();
				   $("#documentFile"+fileFirstName).remove();
				   console.log(response);
			   });
	  }
	 
	  if(modeType=="EDIT"){
		  var queryid =$('#queryId').val();
		  $.ajax({
				method : "POST",
			    url : "../member/deleteFileEdit",
			    data : {
			    	fileName : filePath,
			    	userId :userId,
			    	queryId:queryid
			    }
			   }).done(function(response) {
				   $("#documentLoader").hide();
				   $("#documentFile"+fileFirstName).remove();
				   console.log(response);
				  
				});
	  }
	 
	    
}



 

$(document).ready(function(){
	
	
	var parentCategories=<c:out default="[]" escapeXml="false" value="${not empty parentCategories?parentCategories:'[]'}" />;
	if(parentCategories.length>0){
		for(var s=parentCategories.length-1;s>=0;s--){
			
		findCatByparentCat(parentCategories[s].catId);
		}
	}
	
      /* var timeZone = getTimezoneName();
      console.log(timeZone);
	  $("#timeZone_id").val(timeZone); */
	
	
	  var catArr = new Array();
	  for(var i=0; i<catJson.length;i++){
	  	  catArr.push(catJson[i].catgName);
	  }



	  var substringMatcher = function(strs) {
	      return function findMatches(q, cb) {
	    	var matches, substringRegex;
	    
	    	// an array that will be populated with substring matches
	    	matches = [];
	    
	    	// regex used to determine if a string contains the substring `q`
	    	substrRegex = new RegExp(q, 'i');
	    
	    	// iterate through the pool of strings and for any string that
	    	// contains the substring `q`, add it to the `matches` array
	    	$.each(strs, function(i, str) {
	    	  if (substrRegex.test(str)) {
	    		matches.push(str);
	    	  }
	    	});
	    
	    	cb(matches);
	      };
	    };

	  $('#the-basics_cat .typeahead_cat').typeahead({
	       hint: true,
	       highlight: true,
	       minLength: 1
	     },
	     {
	       name: 'states',
	       source: substringMatcher(catArr)
	     });

	  $(document).on('click','#the-basics_cat .tt-selectable',function(){
	  	   var str = $("#smart_search_cat").val();
	  	   var value = findCatValueFromKey(catList,str);
	  	   var parentId=findParentIdValueFromcatKey(catParentList,value);
	  	   findCatByparentCatNew(parentId); 
	  	   if(value=='10000'){
	  		 $('.myCheckBoxCat').prop('checked', false);
    		$("#categCheckBox"+10000).prop('checked', true );
    		$("#addCategory").empty();
    		
	  	   }else{
	  		   
	  		  var check=$("#categCheckBox"+10000).prop('checked');
	  		 
	  		  if(check){
	  			$("#categCheckBox"+10000).prop('checked', false );
	  			$("#addCategory").empty();
	  		  }
	  	   }
	  	   
	  	   $("#categCheckBox"+value).prop('checked', true );
	  	   var html = '<span id="categ'+value+'"  class="btn btn-warning" >'+$(this).text()+' <i class="fa fa-times " onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
	       $("#addCategory").append(html);
	       $("#smart_search_cat").val('');	
	    }); 
	  

	
	  
	  
	  $(document).on('click','ul.catindus.catErp li',function(){
			 var checkbox = $(this).find("input[type='checkbox']");
	         var value = $(this).children().eq(0).val();
	        
	         if(checkbox.is(':checked')){
	        	 if(checkbox[0].id == "categCheckBox10000" )
	        		 {
	        		
	        			$('.myCheckBoxCat').prop('checked', false);
	            		
	            		$("#categCheckBox"+10000).prop('checked', true );
	            		$("#addCategory").empty();
	            		 var html = '<span id="categ'+10000+'"  class="btn btn-warning" >'+'Not Sure'+' <i class="fa fa-times " onclick="return closebtn('+10000+')" id="close'+10000+'"></i></span>';
	                     $("#addCategory").append(html);
	        		 }
	        	 else
	        		 {
	        		 $("#categ"+10000).remove();
	        		 $("#categCheckBox"+10000).attr('checked', false);
	        		 var html = '<span id="categ'+value+'"  class="btn btn-warning" >'+$(this).text()+' <i class="fa fa-times " onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
	                 $("#addCategory").append(html);
	        		 }
	        	 $("#categCheckBox"+value).attr('checked', true);
	            /* checkbox.attr('checked', true); */
	           
	         }else{
	            /* checkbox.attr('checked', false); */
	            $("#categCheckBox"+value).attr('checked', false);
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
	  
	  
	  var documentsJSON = <c:out default="[]" escapeXml="false" value="${not empty documents?documents:'[]'}" />
	  var docHtml ='';
	  if(null==documentsJSON){
	  }else{
		  var fileName ='';
		  var fileFirstName='';
		  var firstName='';
	  	 for(var i =0 ; i<documentsJSON.length ; i++){
	  		 fileName = documentsJSON[i];
	  		 fileFirstName = fileName.split(".");
	  		 firstName = fileFirstName[0];
	  		 docHtml='<p  id="documentFile'+firstName+'" class="alert alert-info text-success2"   ><input type="text" name ="documents" value="'+documentsJSON[i]+'" ><i class="fa fa-times" onclick="deleteFile(\''+documentsJSON[i]+'\',\''+firstName+'\')" ></i></p>';
	  			$("#documentNames").append(docHtml); 
	  	} 
	  }
	  
	  
	  
	 /*  var urlJson =<c:out default="[]" escapeXml="false" value="${not empty urls?urls:'[]'}" />;
	  var urlHtml ='';
	  if(urlJson==null){
		  
	  }else{
		  for(var  i=0;i<urlJson.length;i++){
			  urlHtml ='<p class="alert alert-success text-success1" onclick="removeUrl(this)" ><input type="text" name ="urls" value="'+urlJson[i]+'"><i class="fa fa-times"></i></p>';
			  $("#addUrlId").append(urlHtml);
		  }  
	  }
	   */
	  
	  
	});

function findCatByparentCat(parentId){
	 var html = '';

	 for(var i =0; i< allCategory.length;i++){
		 
	   if(allCategory[i].parentId == parentId){
		html= '<li class="checkbox" ><input type="checkbox" class="myCheckBoxCat" value="'+allCategory[i].catId+'"  id ="categCheckBox'+allCategory[i].catId+'"  name="categories"  >'+allCategory[i].catgName+'</li>'; 
	     
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

 

var client = new XMLHttpRequest();
function uploadDocument()
   {
	if($('#documentLoader').is(':visible')) {
		var html ='<div id="myModal" class="modal fade" role="dialog"><div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <button type="button" class="close" data-dismiss="modal">&times;</button> <h4 class="modal-title">Modal Header</h4> </div><div class="modal-body"> <p>Some text in the modal.</p></div><div class="modal-footer"> <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> </div></div></div></div>';
	    alert("uploading already in progress");
		return;
	}
	$("#documentLoader").show();
    var file = document.getElementById("documentUpload");
    if(modeType=="CREATE"){
    	$('#createIssue').attr('disabled', 'disabled');
    }else{
    	$('#editIssue').attr('disabled', 'disabled');
    }
    
    / Create a FormData instance /
    var formData = new FormData();  
    formData.append("file", file.files[0]);
    var id=$('#userId').val();
    console.log(id);
    formData.append("userId",id);
    //console.log(formData);
    client.open("post", "../member/createFolder", true);
    client.send(formData);
	}
client.onreadystatechange=function()
{
if (client.readyState==4 && client.status==200)
  {
	var data=jQuery.parseJSON(client.response);
	var fileName = data.result.originalFileName;
	var fileFirstName = fileName.split(".");
	var firstName = fileFirstName[0];
	$("#documentLoader").hide();
    var html='<p class="alert alert-info text-success2"   id="documentFile'+firstName+'"  onclick="deleteFile(\''+data.result.originalFileName+'\',\''+firstName+'\')"  ><input type="text" name ="documents"  value="'+data.result.originalFileName+'"><i class="fa fa-times"></i></p>';
	$("#documentNames").append(html);
	if(modeType=="CREATE"){
		$('#createIssue').removeAttr('disabled');
    }else{
    	$('#editIssue').removeAttr('disabled');
    }
	console.log(data);
  }
}



/* function  removeUrl(urlRm){
	urlRm.remove();
} 

function addQueryUrlDone(){
	 var addUrlValue = $("#addUrlTextField").val();
	 var addUrl ='<p class="alert alert-success text-success1" onclick="removeUrl(this)" ><input type="text" name ="urls" value="'+addUrlValue+'"><i class="fa fa-times"></i></p>';
	 $("#addUrlId").append(addUrl);
	 $("#addUrlTextField").val("");
} 

function addQueryUrlClose(){
	   $("#addUrlTextField").val("");
}  */
 


 

</script>

 
 
	<section class="create-issue">
		<div class="container">
		 <form:form role="form" class="contact-form" id="contact-form"  
            method="post"
            action="${pageContext.request.contextPath}/admin/askQuestion" 
             modelAttribute="query" commandName="query" enctype="multipart/form-data"  >
			<div class="row">
				<div class="col-md-3 col-sm-2"></div>
				<div class="col-md-6 col-sm-8">
				 <c:choose>
				  <c:when test="${query.queryMode=='CREATE'}" >
				  <h1 class="animated fadeInDown">Title your issue here*</h1>
                 </c:when>
				  <c:otherwise>
				  <h1 class="animated fadeInDown">Edit your issue here*</h1>
				  </c:otherwise>
				 </c:choose>
					
					<!--  <input type="hidden"  name ="timeZone"  value="hello"  id="timeZone_id" > -->
					 <input type="hidden"  name ="queryId"  value="${query.queryId}"  id="queryId">
					 <input type="hidden"  name ="userId"  value="${userId}"  id="userId">
					 <input type="hidden"  name ="queryMode"  value="${query.queryMode}"  id="queryMode">
					
					<div class="graybg">
					
					  
						<h2>
							<span>1.</span> What's the category?
						</h2>
						<p>Search or select the most precise category related to your
							issue, so that it is matched with the best fixer.</p>
						
						
						 <fieldset>
                                <legend>Smart Search</legend>
                                 <div id="the-basics_cat">
                                    <input class="typeahead_cat" type="text" placeholder="" id ="smart_search_cat" >
                                 </div>
                          </fieldset>

						  <div class="categorydis" id = "addCategory" ></div>
                           <form:errors path="categories" cssClass="error" />						  

						<fieldset data-toggle="modal" data-target="#myModal1"
							style="cursor: pointer;">
							<legend>Categories</legend>
							<div style="cursor: pointer;height: 28px;width: 28px;float: right;background: url(../images/donload.png) 97% 0 no-repeat;">
                                    </div>
						</fieldset>
						<br>
						<h2>
							<span>2.</span> What's the Issue?
						</h2>
						<p>Write a 100 word title.</p>
						<fieldset style="height: 65px;">
						<legend>Title</legend>
						<form:textarea class="title100w" type = "text" path="queryTitle"  id=""></form:textarea>
						</fieldset>
						<form:errors path="queryTitle" cssClass="error" />
						<!-- <p>Write a 500 word overview about your SAP issue.</p> -->
						<fieldset style="height: 105px;">
							<legend>Type here</legend>
						<form:textarea  style="width: 99%;" type = "text" path="queryContent"  id=""></form:textarea>
						</fieldset>
                        <form:errors path="queryContent" cssClass="error" />
						<br>
						<h2><span>3.</span> Have an Attachment?</h2>
						<p>upload a file to share with the fixer, 5MB limit.</p>
						
						<div id="documentNames" >
						</div>
						
						
						<div class="row">
							<div class="col-md-5 col-sm-5">
								<button class="btn btn-primary upload">Upload</button>
								<input  id="documentUpload"  onchange="uploadDocument()" type="file" style="position: absolute; top: 0px; height: 48px; opacity: 0;"  >
							</div>
							<div class="col-md-5 col-sm-5"  id="documentLoader" >
					        <i  class="fa fa-spinner fa-spin" style="font-size: 30px;margin: 12px 0 0 0;"></i>
					       </div>
						</div>
						

						
						<!-- <br>
						<h2><span>4.</span> Have a Video/Audio Url?</h2>
						<div id="addUrlId" >
						</div>
						
						<div class="row">
							
							<div class="col-md-12 col-sm-12">
								<button type="button" class="btn btn-success upload add1" data-toggle="collapse" data-target="#demo">Add url</button>
								<div id="demo" class="collapse">
									<fieldset style="margin-bottom:5px;">
									  <legend>Add Url</legend>
										<input  id="addUrlTextField" type="text" name="username"   >
									</fieldset>
									<button type="button"  onclick="addQueryUrlDone();"  class="btn btn-success" style="padding: 5px 9px 2px 9px;
font-size: 15px;"><i class="fa fa-plus"></i> Done</button>
									<button style="padding: 5px 9px 2px 9px;
font-size: 15px;" type="button"   onclick="addQueryUrlClose();" class="btn btn-danger"><i class="fa fa-times"></i> Close</button>
								</div>
							</div>
						</div> -->
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						


						<br>
						<h2>
							<span>4.</span> Open or Private Issue?
						</h2>
						<p>Fixer in your category will review and select your ticket,
							or you can select a specific fixer.</p>
						<div class="row">
						 <c:choose>
			 <c:when test="${query.fixerId>=1}" >
				      <div class="col-md-6 col-sm-6">
								<div class="radio">
									<label><input type="radio" id="radio1"
										value="" name="radiobtn" onchange="openToAllFixerClicked()"> Open to all fixer</label>
								</div>
								
							</div>
							<div class="col-md-6 col-sm-6">
								<div class="radio">
									<label><input type="radio" name="radiobtn"  data-toggle="modal" data-target="#searchfixerpopup" checked> Send to a Specific fixer</label>
								</div>
								<!-- <div class="radio">
									<p data-toggle="modal" data-target="#searchfixerpopup">Send to a Specific fixer</p>
								</div> -->
							</div>
                 </c:when>
				  <c:otherwise>
				   <div class="col-md-6 col-sm-6">
								<div class="radio">
									<label style="line-height: 1;"><input type="radio" id="radio1"
										value="" name="radiobtn" onchange="openToAllFixerClicked()" checked > Open to all fixer</label>
								</div>
								
							</div>
							<div class="col-md-6 col-sm-6">
								<div class="radio">
									<label style="line-height: 1;"><input type="radio" name="radiobtn"  data-toggle="modal" data-target="#searchfixerpopup"> Send to a Specific fixer</label>
								</div>
								<!-- <div class="radio">
									<p data-toggle="modal" data-target="#searchfixerpopup">Send to a Specific fixer</p>
								</div> -->
							</div>
				  </c:otherwise>
				  
      </c:choose>
							
						</div>
 
                 <input placeholder="" name="fixerId"  value ="${query.fixerId}"  id ="fixerId" type="hidden">
                 <input placeholder="" name="status"  value ="${status}"  id ="status" type="hidden">
                 
						<fieldset>
						 <c:choose>
			 <c:when test="${not empty selectedFixer}" >
				     <input placeholder="" name="selectedFixerName"  value ="${selectedFixer.userName}"  id ="selectedFixerName" type="hidden">
							<legend id="particularFixerId">Selected Fixer</legend>
							<input type="text" name="username" id="particularFixer" value="${selectedFixer.userName}" readonly>
                 </c:when>
				  <c:otherwise>
				  <input placeholder="" name="selectedFixerName"  value =""  id ="selectedFixerName" type="hidden">
				   <legend id="particularFixerId">Search Fixers...</legend>
							<input type="text" name="username" id="particularFixer" value="" readonly>
				  </c:otherwise>
				  
      </c:choose>
						</fieldset>

          

						<div class="row">
							<div class="col-md-6 col-sm-6">
								<a href="../admin/FixerForReview?msgKey=R&status=unClaimed" class="btn btn-danger btn-block">Cancel</a>
							</div>
							<div class="col-md-6 col-sm-6">
							 <c:choose>
				              <c:when test="${query.queryMode=='CREATE'}" >
				              <button type="submit"  id="createIssue"  class="btn btn-success btn-block">Post Request</button>
                              </c:when>
				               <c:otherwise>
				         <button type="submit"  id="editIssue"  class="btn btn-success btn-block">Update Request</button>
				          </c:otherwise>
				     </c:choose>
							
								
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-2"></div>
			</div>
			<br>

			<div class="row">
				<div class="col-md-12 col-sm-12"></div>
			</div>


			<!-- category -->
			<%-- <div id="myModal1" class="modal fade" role="dialog">
				<div class="modal-dialog modal-lg">

					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h2 class="modal-title">Categories</h2>
						</div>
							
						<div class="modal-body modalBodyCat">
							<div class="row">
								
								<div class="col-md-4 col-sm-4">
									<div class="leftCat">
										<button class="Showoptions">Select Categories</button>
                                            <select id="cat1" onchange="findCatByparentCat(this.value)" multiple="multiple" name="foo" onFocus="" onBlur="">
										       <c:forEach var="parentCategory" items="${parentCategory}">
											  <option value="${parentCategory.catId}">${parentCategory.catgName}</option>
											</c:forEach>
										    </select>
									</div>
								
								</div>
								<c:forEach var="parentCategory" items="${parentCategory}">
										
								<div class="col-md-8 col-sm-8">
								<ul class="catindus catErp" style="list-style: none;margin: 10px 0 0 -31px;overflow-y: auto;overflow-x: hidden;
    height: 289px;" id ="categoryList${parentCategory.catId}" >
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

 --%>

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
							      	<h2 type="button"  id="maincate${parentCategory.catId}" value="${parentCategory.catId}" onclick="findCatByparentCat(${parentCategory.catId})">${parentCategory.catgName} <i class="fa fa-angle-down"></i>
							      	</h2>
							      	<ul class="catindus catErp" id ="categoryList${parentCategory.catId}" >
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
                                            <select id="cat1" onchange="findCatByparentCat(this.value)" multiple="multiple" name="foo" onFocus="" onBlur="">
										       <c:forEach var="parentCategory" items="${parentCategory}">
											  <option value="${parentCategory.catId}">${parentCategory.catgName}</option>
											</c:forEach>
										    </select>
									</div>
								
								</div>
								
								 <c:forEach var="parentCategory" items="${parentCategory}">
										
								<div class="col-md-8 col-sm-8">
								<ul class="catindus catErp" style="list-style: none;margin: 10px 0 0 -31px;overflow-y: auto;overflow-x: hidden;
    height: 289px;" id ="categoryList${parentCategory.catId}" >
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



<!-- search fixer-->
  <div class="modal fade" id="searchfixerpopup" role="dialog">
    <div class="modal-dialog modal-lg">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" onclick="closePopUpClicked()"><i class="fa fa-times" style="margin-top: 14px;"></i></button>
          <h2 class="modal-title">Search Fixer</h2>
        </div>
        <div class="modal-body">
          <div class="row">
				
				<div class="col-md-12 col-sm-12">
				<div class="searcfixpopup">	
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#searchfixer">Search Fixer</a></li>
					<li><a  type="button" data-toggle="tab" href="#favouritefixer" onclick="favFixerTapClicked(${myUser.userId})">Favourite Fixer</a></li>
					
					
				</ul>
				</div>
			  <div class="tab-content">
				<!--  -->	<div id="searchfixer" class="tab-pane fade in active">
					<div class="row">
						<div class="col-md-4 col-sm-4">
							<div class="favsearch">
								<input type="search" id="searchId" placeholder="Search for fixer" onkeyup="searchEnter(this.value)">
								<i class="fa fa-search"></i>
								<a type="button" class="fa fa-times-circle" style="color:#676A74;"></a>
							</div>
						
						</div>
						<div class="col-md-4 col-sm-4">
							<select class="select" id="parentCatSelection" onchange="findCatByparentCatToShowOnList(this.value)">
								<option>Filter By Parent Category</option>
								<c:forEach var="parentCategory" items="${parentCategory}">
								<option value="${parentCategory.catId}">${parentCategory.catgName}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-md-4 col-sm-4">
							<div id="childCatDropDownList"></div>
							
						</div>
						
					
				   </div>
				   <div class="row" style="margin-top: 49px;">
				   		<div class="col-md-12 col-sm-12">
								<ul class="pagination pull-right"   id = "searchFixerListPagination" >
								<!-- 	<li><a href="#" class="active"><i
											class="fa fa-caret-left"></i></a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li class="disabled"><a href="#">4</a></li>
									<li><a href="#" class="active"><i
											class="fa fa-caret-right"></i></a></li> -->
								</ul>
							</div>
				   </div>
				   
				   <div style="height:10px; width:100%;"></div>
				   <div class="row">
				   <div id="searchFixerList"></div>
                  </div></div><!--// 1 //-->
				
				
				
				<div id="favouritefixer" class="tab-pane fade" >
					<div class="row">
						
					
					<div class="col-md-12 col-sm-12">
								<ul class="pagination pull-right"  id = "favouriteFixerListPagination" >
								<!-- 	<li><a href="#" class="active"><i
											class="fa fa-caret-left"></i></a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li class="disabled"><a href="#">4</a></li>
									<li><a href="#" class="active"><i
											class="fa fa-caret-right"></i></a></li> -->
								</ul>
							</div>
						
				   </div>
				<div id="favouriteFixerList"></div>
				 
				</div><!--// 2 //-->
				
				
				
				
				
				
				
				
				
				
			  </div>
					
					
					
					
					
					
					
					
					
					
					
					
					
				</div>
				
			</div>
        </div>
        
      </div>
      
    </div>
  </div>

<!-- search fixer close-->




















			<!-- popup message -->
			<div class="modal fade" id="myModal" role="dialog">
				<div class="modal-dialog ">

					<!-- Modal content-->
					<div class="modal-content">
						<!-- <div class="modal-header">
					  <button type="button" class="close" data-dismiss="modal">&times;</button>
					  <h4 class="modal-title">Modal with Dark Overlay</h4>
					</div> -->
						<div class="modal-body">
							<h1 class="text-success text-center">Issue Create
								successfully!</h1>
							<h3 class="text-center">We Guarantee that a Fixer will begin
								within 24 hours.</h3>
						</div>
						<div class="modal-footer" style="text-align: center;">
							<button type="button" class="btn btn-danger"
								style="padding: 2px 17px; font-size: 20px;" data-dismiss="modal">Dismiss</button>
						</div>
					</div>

				</div>
			</div>
			<!-- Modal -->
			</form:form>
		</div>
		
	</section>
	<br>
<script>

		
function searchEnterCallByOtherMethod(ele){
	searchEnter(ele.value);
}

	function searchEnter(ele) {
		if(ele.length>0){
		var categoryId;
	   var catSelPresent=document.getElementById("catSelection");
	   if(catSelPresent){
		   categoryId= document.getElementById("catSelection").value; 
	   }else{
		   categoryId='';
	   }
		   $.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/admin/fixerList",
			    data : {
			    searchFieldText: ele ,
			    catId: categoryId
			   
			    }
			   }).done(function(response) {
				    var data = response.result.fixers;
				    var totalPage=response.result.totalPage;
					   var startPage=response.result.startPage;
					   var endPage=response.result.endPage;
					   var currentPageNo=response.result.currentPageNo;
					   $("#searchFixerListPagination").empty();
				    $("#searchFixerList").empty();
				  var html ='';
				  var pagHtml ='';
				    if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo=='1'){
				    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
				    	 }else{
				    		 pagHtml='<li onclick="pagClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
				    	 }
				     }
				     
				     for(var k=startPage;k<=endPage;k++){
				    	if(k==currentPageNo){
				    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
				    	}else{
				    	pagHtml +='<li  onclick="pagClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
				    	}
				    	
				    }
				     if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo==totalPage){
				    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
				    	 }else{
				    		 pagHtml +='<li onclick="pagClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
				    	 }
				     }
				     $("#searchFixerListPagination").append(pagHtml); 
				    for(var i =0 ;data.length;i++){
				    	var catids=data[i].fixerCategories;
				    	
				    	
				    	html = '<div class="search-favourite-fixer"><div class="uclaimedcell"><h2><img src="'+data[i].profilePhoto+'"  style="display:inline-block; border-radius:50%;"><name>'+data[i].userName+'</name><p class="your-div">'+data[i].overview+'</p><p><span class="stars" id="stars_'+data[i].userId+'" >'+data[i].starPoints+'</span></p></h2><div class="unclatbn"><h2>Categories:</h2>';
				    	
				    	for(var j=0;j<catids.length;j++){
				    	
				    		html += '<div class="btn btn-warning">'+catids[j]+'</div>' ;
				    	}
				    	html += '</div></div><a type="button" onclick="selectFixerButtonClicked(\''+data[i].userId+'\',\''+data[i].userName+'\')" class="uclaimedcell" ><div>Select for the Issue</div></a>';
				    	
				    	if(data[i].favUnFavstatus == "F")
				    	{
				    		html += '<a type="button" onclick="unfavButtonClicked('+data[i].userId+')" class="uclaimedcell"><div>UnFavourite</div></a></div>';
				    		
				    	}
				    	else
				    	{
				    		html += '<a type="button" onclick="favButtonClicked('+data[i].userId+')" class="uclaimedcell"><div>Favourite</div></a></div>';
				    	}
				    	
				    	
				    	$("#searchFixerList").append(html);
				    	showStars(data[i].userId);
				    	
				    
				    	  
				    }
					
			   });
		}else if(ele.length==0){
			 $("#searchFixerListPagination").empty();
			    $("#searchFixerList").empty();
		}else{
			
		}
	    
	}
	
	function pagClick(pageNo,flag){
		var name = document.getElementById("searchId");
		var ele=name.value;
		var categoryId;
		   var catSelPresent=document.getElementById("catSelection");
		   if(catSelPresent){
			   categoryId= document.getElementById("catSelection").value; 
		   }else{
			   categoryId='';
		   }
		$.ajax({
			method : "POST",
		    url : "${pageContext.request.contextPath}/admin/fixerList",
		    data : {
		    searchFieldText: ele ,
		    catId: categoryId,
		    pageNo: pageNo,
		    flag:flag
		   
		    }
		   }).done(function(response) {
			    var data = response.result.fixers;
			    var totalPage=response.result.totalPage;
				   var startPage=response.result.startPage;
				   var endPage=response.result.endPage;
				   var currentPageNo=response.result.currentPageNo;
				   $("#searchFixerListPagination").empty();
			    $("#searchFixerList").empty();
			  var html ='';
			  var pagHtml ='';
			    if(totalPage==0){
			    	 
			     }else{
			    	 if(currentPageNo=='1'){
			    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
			    	 }else{
			    		 pagHtml='<li onclick="pagClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
			    	 }
			     }
			     
			     for(var k=startPage;k<=endPage;k++){
			    	if(k==currentPageNo){
			    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
			    	}else{
			    	pagHtml +='<li  onclick="pagClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
			    	}
			    	
			    }
			     if(totalPage==0){
			    	 
			     }else{
			    	 if(currentPageNo==totalPage){
			    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
			    	 }else{
			    		 pagHtml +='<li onclick="pagClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
			    	 }
			     }
			     $("#searchFixerListPagination").append(pagHtml); 
			    for(var i =0 ;data.length;i++){
			    	var catids=data[i].fixerCategories;
			    	
			    	
			    	html = '<div class="search-favourite-fixer"><div class="uclaimedcell"><h2><img src="'+data[i].profilePhoto+'"  style="display:inline-block; border-radius:50%;"><name>'+data[i].userName+'</name><p class="your-div">'+data[i].overview+'</p><p><span class="stars" id="stars_'+data[i].userId+'" >'+data[i].starPoints+'</span></p></h2><div class="unclatbn"><h2>Categories:</h2>';
			    	
			    	for(var j=0;j<catids.length;j++){
			    	
			    		html += '<div class="btn btn-warning">'+catids[j]+'</div>' ;
			    	}
			    	html += '</div></div><a type="button" onclick="selectFixerButtonClicked(\''+data[i].userId+'\',\''+data[i].userName+'\')" class="uclaimedcell" ><div>Select for the Issue</div></a>';
			    	
			    	if(data[i].favUnFavstatus == "F")
			    	{
			    		html += '<a type="button" onclick="unfavButtonClicked('+data[i].userId+')" class="uclaimedcell"><div>UnFavourite</div></a></div>';
			    		
			    	}
			    	else
			    	{
			    		html += '<a type="button" onclick="favButtonClicked('+data[i].userId+')" class="uclaimedcell"><div>Favourite</div></a></div>';
			    	}
			    	
			    	
			    	$("#searchFixerList").append(html);
			    	showStars(data[i].userId);
			    	
			    
			    	  
			    }
				
		   });
    }
 
	
	
	function selectFixerButtonClicked(ele,name){
		var elem = document.getElementById("fixerId");
		elem.value = ele;
		var fixerName = document.getElementById("selectedFixerName");
		fixerName.value=name;
		document.getElementById("fixerName").innerHTML= name;
		//$("#selectFixerModal").showModal();
		$('#selectFixerModal').modal({
        		show: 'true'
   		 }); 
	}
	
	function favButtonClicked(ele){
		
		  $.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/admin/fixerFavourite",
			    data : {
			    fixerId: ele
			     }
			   }).done(function(response) {
				  
				   searchEnter(document.getElementById("searchId").value);
			   });
	}
	
	function unfavButtonClicked(ele){
		
		  $.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/member/deleteFavouriteFixer",
			    data : {
			    fixerId: ele
			     }
			   }).done(function(response) {
				   favFixerTapClicked(ele);
				   searchEnter(document.getElementById("searchId").value);
			   });
	}
	
	function favFixerTapClicked(ele){
		 $.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/admin/favouriteFixerList",
			    data : {
			    fixerId: ele
			     }
			   }).done(function(response) {
				  
				   var data = response.result.fixers;
				   var totalPage=response.result.totalPage;
				   var startPage=response.result.startPage;
				   var endPage=response.result.endPage;
				   var currentPageNo=response.result.currentPageNo;
				   
				    $("#favouriteFixerList").empty();
				    $("#favouriteFixerListPagination").empty();
				    var html ='';
					  var pagHtml ='';
					    if(totalPage==0){
					    	 
					     }else{
					    	 if(currentPageNo=='1'){
					    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
					    	 }else{
					    		 pagHtml='<li onclick="pagFavClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
					    	 }
					     }
					     
					     for(var k=startPage;k<=endPage;k++){
					    	if(k==currentPageNo){
					    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
					    	}else{
					    	pagHtml +='<li  onclick="pagFavClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
					    	}
					    	
					    }
					     if(totalPage==0){
					    	 
					     }else{
					    	 if(currentPageNo==totalPage){
					    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
					    	 }else{
					    		 pagHtml +='<li onclick="pagFavClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
					    	 }
					     }
					     $("#favouriteFixerListPagination").append(pagHtml);
				   
				    for(var i =0 ;data.length;i++){
				    	var catids=data[i].fixerCategories;
				    	
				    	
				    	html = '<div class="search-favourite-fixer"><div class="uclaimedcell"><h2><img src="'+data[i].profilePhoto+'"  style="display:inline-block; border-radius:50%;"><name>'+data[i].userName+'</name><p class="your-div">'+data[i].overview+'</p><p><span class="stars" id="starsFav_'+data[i].userId+'" >'+data[i].starPoints+'</span></p></h2><div class="unclatbn"><h2>Categories:</h2>';
				    	for(var j=0;j<catids.length;j++){
				    	
				    		html += '<div class="btn btn-warning">'+catids[j]+'</div>' ;
				    	}
				    	html += '</div></div><a type="button" onclick="selectFixerButtonClicked(\''+data[i].userId+'\',\''+data[i].userName+'\')" class="uclaimedcell"><div>Select for the Issue</div></a><a type="button" onclick="unfavButtonClicked('+data[i].userId+')" class="uclaimedcell"><div>UnFavourite</div></a></div>';
				    	
				    	
				    	$("#favouriteFixerList").append(html);
				    	showFavStars(data[i].userId);
				    	
				    }
			   });
	}
	
	function pagFavClick(pageNo,flag){
		var ele=${myUser.userId};
		 $.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/admin/favouriteFixerList",
			    data : {
			    fixerId: ele,
			    pageNo: pageNo,
			    flag:flag
			     }
			   }).done(function(response) {
				  
				   var data = response.result.fixers;
				   var totalPage=response.result.totalPage;
				   var startPage=response.result.startPage;
				   var endPage=response.result.endPage;
				   var currentPageNo=response.result.currentPageNo;
				   
				    $("#favouriteFixerList").empty();
				    $("#favouriteFixerListPagination").empty();
				    var html ='';
					  var pagHtml ='';
					    if(totalPage==0){
					    	 
					     }else{
					    	 if(currentPageNo=='1'){
					    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
					    	 }else{
					    		 pagHtml='<li onclick="pagFavClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
					    	 }
					     }
					     
					     for(var k=startPage;k<=endPage;k++){
					    	if(k==currentPageNo){
					    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
					    	}else{
					    	pagHtml +='<li  onclick="pagFavClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
					    	}
					    	
					    }
					     if(totalPage==0){
					    	 
					     }else{
					    	 if(currentPageNo==totalPage){
					    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
					    	 }else{
					    		 pagHtml +='<li onclick="pagFavClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
					    	 }
					     }
					     $("#favouriteFixerListPagination").append(pagHtml);
				   
				    for(var i =0 ;data.length;i++){
				    	var catids=data[i].fixerCategories;
				    	
				    	
				    	html = '<div class="search-favourite-fixer"><div class="uclaimedcell"><h2><img src="'+data[i].profilePhoto+'"  style="display:inline-block; border-radius:50%;"><name>'+data[i].userName+'</name><p class="your-div">'+data[i].overview+'</p><p><span class="stars" id="starsFav_'+data[i].userId+'" >'+data[i].starPoints+'</span></p></h2><div class="unclatbn"><h2>Categories:</h2>';
				    	for(var j=0;j<catids.length;j++){
				    	
				    		html += '<div class="btn btn-warning">'+catids[j]+'</div>' ;
				    	}
				    	html += '</div></div><a type="button" onclick="selectFixerButtonClicked(\''+data[i].userId+'\',\''+data[i].userName+'\')" class="uclaimedcell"><div>Select for the Issue</div></a><a type="button" onclick="unfavButtonClicked('+data[i].userId+')" class="uclaimedcell"><div>UnFavourite</div></a></div>';
				    	
				    	
				    	$("#favouriteFixerList").append(html);
				    	showFavStars(data[i].userId);
				    	
				    }
			   });
    }
 
	function hidesearchModal(){
		
		$('#searchfixerpopup').modal('hide');
		var name = document.getElementById("selectedFixerName");
		var fixerName=name.value;
		$('#particularFixerId').text('Selected Fixer');
		$('#particularFixer').val(fixerName);
		
	}
	
	function openToAllFixerClicked(){
		$('#particularFixerId').text('Search Fixer...');
		$('#particularFixer').val('');
		$('#fixerId').val('0');
	}
	
	$.fn.stars = function(uid) {
		
		return $('#stars_'+uid).each(function() {
			console.log($('#stars_'+uid).text());
			
			
			$('#stars_'+uid).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($('#stars_'+uid).html())))) * 16));
		});
	}
	function showStars(uid){
		$('#stars_'+uid).stars(uid);
	}

	
	
$.fn.favstars = function(uid) {
		
		return $('#starsFav_'+uid).each(function() {
			
			
			
			$('#starsFav_'+uid).html($('<span />').width(Math.max(0, (Math.min(5, parseFloat($('#starsFav_'+uid).html())))) * 16));
		});
	}
	function showFavStars(uid){
		$('#starsFav_'+uid).favstars(uid);
	}
	
	
	function findCatByparentCatToShowOnList(parentId){
		if(parentId=='Filter By Parent Category'){
			$("#childCatDropDownList").empty();
		}else{
		 $("#childCatDropDownList").empty();
	    var html = '';
	    html+='<select class="select" id="catSelection" onchange="searchEnterCallByOtherMethod(searchId)">';
	 for(var i =0; i< allCategory.length;i++){
	   if(allCategory[i].parentId == parentId){
	   html+='<option value="'+allCategory[i].catId+'">'+allCategory[i].catgName+'</option>';
	  } 
	  }
	 html+='</select>';
	 $("#childCatDropDownList").append(html); 
	}
		
		searchEnter(document.getElementById("searchId").value);
	}
	
	
	function closePopUpClicked(){
		var name = document.getElementById("selectedFixerName");
		var fixerName=name.value;
		
		if(fixerName==''){
			document.getElementById('radio1').checked = true;
			
		}
	}
	</script>
	
	
	<!-- Modal -->
  <div class="modal fade" id="selectFixerModal" role="dialog">
  <div class="vertical-alignment-helper">
           <div class="modal-dialog vertical-align-center modal-sm">
    
      <!-- Modal content-->
      <div class="modal-content">
       
        <div class="modal-body selectpopup">
          <h2>You have selected <span id="fixerName"></span></h2>
    <button class="btn btn-info" data-dismiss="modal" onclick="hidesearchModal()">Ok</button>
        </div>
        
      </div>
      
    </div>
    </div>
    
  </div>
</body>


