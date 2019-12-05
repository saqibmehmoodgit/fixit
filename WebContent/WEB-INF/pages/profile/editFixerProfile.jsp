<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<title>ERPfixers | Create My Profile</title>
<head>
   <script type="text/javascript" src="../js/jstz-1.0.4.min.js"></script>
   <style  type="text/css" >
      .error {
      color: red;
      font-weight: bold;
      }
   </style>
</head>
<body>
<script>
 
</script>
<script type="text/javascript">
      var catJson=${allCategory};
    var catList=new Array();
     for(var i=0; i<catJson.length;i++){
        catList.push({
      	key:catJson[i].catgName,
      	value:catJson[i].catId,
        });
      } 	  
     var catParentList=new Array();
     for(var i=0; i<catJson.length;i++){
    	 catParentList.push({
      	key:catJson[i].catId,
      	value:catJson[i].parentId,
        });
      } 
     
    var json = ${industryJSON};
    var indstList = new Array();
      for(var i=0; i<json.length;i++){
        indstList.push({
      	key:json[i].indstName,
      	value:json[i].indstId,
        });
      }	 
        
      
      function findParentIdValueFromcatKey(catParentList,key){
    	  for(var i in catList){
    		  if(catParentList[i]['key'] == key){
    			  return  catParentList[i]['value'];
    		}
    	  }
      }
      
      function findCatValueFromKey(catList,key){
    	  for(var i in catList){
    		  if(catList[i]['key'] == key){
    			  return  catList[i]['value'];
    		}
    	  }
      }
      function findCatValueFromKeyString(catList,value){
    	  for(var i in catList){
    		  if(catList[i]['value'] == value){
    			  return  catList[i]['key'];
    		}
    	  }
      }
      function findValueFromKey(indstList,key){
    	  for(var i in indstList){
    		  if(indstList[i]['key'] == key){
    			  return  indstList[i]['value'];
    		}
    	  }
      }
      
      /* function getTimezoneName() {
          timezone = jstz.determine();
          return timezone.name();
      } */
      
      function closeIndustrybtn(value){
          $("#categIndust"+value).remove();
          $("#categCheckBoxIndust"+value).attr('checked', false);
       }
       
      
      
      function closebtn(value){
         $("#categ"+value).remove();
         $("#categCheckBox"+value).attr('checked', false);
      }
      
      
      $(document).ready(function(){ 
    	  
    		var parentCategories=<c:out default="[]" escapeXml="false" value="${not empty parentCategories?parentCategories:'[]'}" />;
    		if(parentCategories.length>0){
    			for(var s=parentCategories.length-1;s>=0;s--){
    				
    			findCatByparentCat(parentCategories[s].catId);
    			}
    		}
    	  
    	  
    	  var industJSONByUser= <c:out default="[]" escapeXml="false" value="${not empty industJSONByUser?industJSONByUser:'[]'}" />
          /*  = ${industJSONByUser}; */
    	  if(industJSONByUser.length>0){
    		  for(var i =0; i<industJSONByUser.length; i++){
           	     var value = industJSONByUser[i].indstId;
           	     $("#categCheckBoxIndust"+value).attr('checked', true);
           		 var html = '<span id="categIndust'+value+'"  class="btn btn-warning" >'+industJSONByUser[i].indstName+' <i class="fa fa-times " onclick="return closeIndustrybtn('+value+')" id="close'+value+'"></i></span>';
           	     $("#addIndustry").append(html);
           }
    		 
          }else{
        	  industJSONByUser= <c:out default="[]" escapeXml="false" value="${not empty profile.industries?profile.industries:'[]'}" />

        	  for(var cnt_i in industJSONByUser)
        		 {
        		  	var id = industJSONByUser[cnt_i];
        		  	
        			var industryText= $('#industryLi'+id).text().trim();
        			 var html = '<span id="categIndust'+id+'"  class="btn btn-warning" >'+industryText+' <i class="fa fa-times " onclick="return closeIndustrybtn('+id+')" id="close'+id+'"></i></span>';
        	           $("#addIndustry").append(html);
        		  	// $("#addIndustry").append(industryText); 
        		 }
          }
         
    	
          var catsJSONByUser= <c:out default="[]" escapeXml="false" value="${not empty catsJSONByUser?catsJSONByUser:'[]'}" />
         
          /*  = ${industJSONByUser}; */
         
          if(catsJSONByUser.length>0){
        	  var parentId=catsJSONByUser[0].parentId;
  		  	   findCatByparentCat(parentId);
  		  	for(var i =0; i<catsJSONByUser.length; i++){
         	     var value = catsJSONByUser[i].catId;
         	   
         	     $("#categCheckBox"+value).attr('checked', true);
         		 var html = '<span id="categ'+value+'"  class="btn btn-warning" >'+catsJSONByUser[i].catgName+' <i class="fa fa-times " onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
         	     $("#addCategory").append(html);
         }
          }else{
        	  catsJSONByUser= <c:out default="[]" escapeXml="false" value="${not empty profile.categories?profile.categories:'[]'}" />
        	  if(catsJSONByUser.length>0){
        	  var parentId=findParentIdValueFromcatKey(catParentList,catsJSONByUser[0]);
   		  	findCatByparentCat(parentId);
        	  }
        	  for(var cnt_i in catsJSONByUser)
     		 {
     		  	var id = catsJSONByUser[cnt_i]; 
     		  	 var catText = findCatValueFromKeyString(catList,id);
     		  	 $("#categCheckBox"+id).attr('checked', true);
     			 var html = '<span id="categ'+id+'"  class="btn btn-warning" >'+catText+' <i class="fa fa-times " onclick="return closebtn('+id+')" id="close'+id+'"></i></span>';
     	           $("#addCategory").append(html); 
     		  	// $("#addIndustry").append(industryText); 
     		 }
          }
        
          
         
    	/*   var indusValue = <c:out default="[]" escapeXml="false" value="${not empty profile.industries?profile.industries:'[]'}" /> ;
    	  
    	  for(var cnt_i in indusValue)
    		 {
    		  	var id = indusValue[cnt_i];
    		  	
    			var industryText= $('#industryLi'+id).text().trim();
    			 var html = '<span id="categ'+id+'"  class="btn btn-warning" >'+industryText+' <i class="fa fa-times " onclick="return closebtn('+id+')" id="close'+id+'"></i></span>';
    	           $("#addIndustry").append(html);
    		  	// $("#addIndustry").append(industryText); 
    		 }
    	 
    	  

    	  var categoriesValue = <c:out default="[]" escapeXml="false" value="${not empty profile.categories?profile.categories:'[]'}" /> ;
    	  
    	  for(var cnt_i in categoriesValue)
    		 {
    		  	var id = categoriesValue[cnt_i]; 
    		  	var parentId=findParentIdValueFromcatKey(catParentList,id);
    		  	findCatByparentCat(parentId);
    		  	 var catText = findCatValueFromKeyString(catList,id);
    			 var html = '<span id="categ'+id+'"  class="btn btn-warning" >'+catText+' <i class="fa fa-times " onclick="return closebtn('+id+')" id="close'+id+'"></i></span>';
    	           $("#addCategory").append(html); 
    		  	// $("#addIndustry").append(industryText); 
    		 }
 */
     
          $('#the-basics_cat .typeahead_cat').typeahead({
              hint: true,
              highlight: true,
              minLength: 1
            },
            {
              name: 'states',
              source: substringMatcher(catArr)
            });
      });
    	  
       $('ul#industry_list li').click(function(e) {
            var checkbox = $(this).find("input[type='checkbox']");
            var value = $(this).children().eq(0).val();
            if(!checkbox.attr('checked')){
               checkbox.attr('checked', true);
               var html = '<span id="categIndust'+value+'"  class="btn btn-warning" >'+$(this).text()+' <i class="fa fa-times " onclick="return closeIndustrybtn('+value+')" id="close'+value+'"></i></span>';
            $("#addIndustry").append(html);
            }else{
               checkbox.attr('checked', false);
               $("#categ"+value).remove();
            }
        });
      /*  $("#timeZone").text("Time zone:  "+getTimezoneName()); */
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
	
      
      
      
      
       var catArr = new Array();
       for(var i=0; i<catJson.length;i++){
      	  catArr.push(catJson[i].catgName);
       }
       
       
       
       var indstArr = new Array();
       for(var i=0; i<json.length;i++){
      	  indstArr.push(json[i].indstName);
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
      
      
     $('#the-basics .typeahead').typeahead({
        hint: true,
        highlight: true,
        minLength: 1
      },
      {
        name: 'states',
        source: substringMatcher(indstArr)
      });
     
    
     
     $(document).on('click','#the-basics .tt-selectable',function(){
     	   var str = $("#smart_search").val();
     	   var value = findValueFromKey(indstList,str);
     	   $("#categCheckBoxIndust"+value).attr('checked', true);
     	   var html = '<span id="categ'+value+'"  class="btn btn-warning" >'+$(this).text()+' <i class="fa fa-times " onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
          $("#addIndustry").append(html);
          $("#smart_search").val('');
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
      
     
      var path = "";
      var allCategory =${allCategory}; 
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
      
      function findCatByparentCatNew(parentId){
   		 /* $("#categoryList").empty(); */
   		 for(var i =0; i< allCategory.length;i++){
   			  if(allCategory[i].parentId == parentId){
   				    var html = '<li class="checkbox" ><input type="checkbox"  value="'+allCategory[i].catId+'"  id ="categCheckBox'+allCategory[i].catId+'"  name="categories"  >'+allCategory[i].catgName+'</li>'; 
   				 $("#categoryList").append(html); 
   				} 
   		  }  
   }
       
      
      var client = new XMLHttpRequest();
      function uploadProfileImage(){
    	  var filePath =document.getElementById("multipartFile").value;
  		  var fileName = filePath.split('\\').pop();
  		  var fileExtension = fileName.split('.').pop().toLowerCase();
  		  if(fileExtension=="jpg" ||fileExtension=="jpeg" || fileExtension=="png"){
  			document.getElementById("myImageUrl").value="../images/default_profile.jpg";
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
    	  	 document.getElementById("myImageUrl").value=data.result.originalFileName;
    	  	 $('#myProfileImage').attr("src",data.result.originalFullFileName);
    		 $('#saveButton').removeAttr('disabled');
    		 $("#imageLoader").hide();
    	  	 console.log(data); 
    	    }
      }
      
       
   </script>
   <section class="member">
      <div class="container">
         <div class="row">
            <div class="col-md-12">
               <h1 class="animated fadeInDown">${CreateOrEdit}</h1>
            </div>
         </div>
         
         <form:form role="form" class="contact-form" id="contact-form"  
            method="post"
            action="${pageContext.request.contextPath}/fixer/profile"  modelAttribute="profile" commandName="profile" enctype="multipart/form-data"  >
            <div class="row">
            <form:input id="myImageUrl" path ="imageUrl"  type="hidden" value="${profile.imageUrl}"/>
               <div class="col-md-1 col-sm-1"></div>
               <div class="col-md-10 col-sm-10">
               <div class="profilOuter">
               <div class="step">${Step}  </div>
                  <div class="createmyprofile">
                     <div class="row">
                        <div class="col-md-5 col-sm-5">
                           <div class="profilepic">
                              <div class="btn-file">
                               <img src='${fixerImagePath}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';" id="myProfileImage">
                                <form:input type="file" path="multipartFile" onchange="uploadProfileImage()"/> 
                                <img src="../images/loading_spinner.gif" style="position: absolute;
  									  top: 0;display:none; " id="imageLoader">
                              </div>
                              <div class="profilOuter1">
                              <div class="row">
                                 <div class="col-md-8">
                                    <p>Last login: 2 hours ago.</p>
                                    	<c:if  test="${profile.timeZone!=null }">
                                    	 <p  id="timeZone" >TimeZone :${profile.timeZone }</p>
                                    	</c:if>
                                   
                                    <p >Location: ${profile.city} , ${profile.country}</p>
                                 </div>
                                 <div class="col-md-4">
                                    <img src="../flags/${profile.country}.png" class="pull-right1"> 
                                    <p class="text-success pull-right1">Verified <i class="fa fa-check-circle"></i></p>
                                 </div>
                              </div>
                              </div>
                           </div>
                        </div>
                        <div class="col-md-7 col-sm-7">
                           <form:input path ="userId"  type="hidden" value="${profile.userId}"/>
                           <form:input path="userId" type="hidden"
										value="${profile.userId}" />
									<form:input path="email" type="hidden"
										value="${profile.email}" />
									<form:input path="firstName" type="hidden"
										value="${profile.firstName}" />
									<form:input path="lastName" type="hidden"
										value="${profile.lastName}" />
									<form:input path="city" type="hidden"
										value="${profile.city}" />
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
                           <div class="form-group">
                              <div class="title">${profile.userName}  
                                 <span>Background and Experience.</span>
                              </div>
                              <fieldset class="height">
                                 <legend>Type here</legend>
                                 <form:textarea  path ="overview" type="text"></form:textarea>
                                 <%--  <form:input type="text" path="overview"  id="overview" value="${profile.overview}" /> --%> 
                              </fieldset>
                           </div>
                          <!--  <div class="categorydis" id = "addIndustry" ></div>
                           <div class="form-group">
                              <div class="title">My Industry(s)
                                 <span>Search for your industry or select from the dropdown.</span>
                              </div>
                              <fieldset>
                                 <legend>Smart Search</legend>
                                 <div id="the-basics">
                                    <input class="typeahead" type="text" placeholder="" id ="smart_search" >
                                 </div>
                              </fieldset> -->
                             
                             <%--  <fieldset data-toggle="modal" data-target="#myModal" style="cursor:pointer;">
                                 <legend>Industries</legend>
                                 <select style="cursor:pointer;">									
                                 </select>
                              </fieldset>
                              <form:errors path="industries" cssClass="error" />  --%>
                               <div class="categorydis" id = "addCategory" ></div>
                               <div class="title">My Category(s)
                                 <span>Search for your category or select from the dropdown.</span>
                              </div>
                              
                              <fieldset>
                                 <legend>Smart Search</legend>
                                 <div id="the-basics_cat">
                                    <input class="typeahead_cat" type="text" placeholder="" id ="smart_search_cat" >
                                 </div>
                              </fieldset>
                             <!--  <div class="categorydis" id = "addCategory" ></div> -->

						<fieldset data-toggle="modal" data-target="#myModal1"
							style="cursor: pointer;">
							<legend>Categories</legend>
						<div style="cursor: pointer;height: 28px;width: 28px;float: right;background: url(../images/donload.png) 97% 0 no-repeat;">
       </div>
						</fieldset>
						<form:errors path="categories" cssClass="error" />
                              <button type="submit" class="btn btn-warning btn-block" id="saveButton">Save Profile</button>
                           </div>
                           <!-- Modal -->
                           <%-- <!-- category -->
			<div id="myModal1" class="modal fade" role="dialog">
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
                                            <select id="cat1" onchange="findCatByparentCat(this.value)" multiple="multiple" name="foo" onFocus="expand(this)" onBlur="unexpand(this)">
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
                      </div>						<div class="modal-footer">
							<button type="button"
								style="border: none; padding: 0px 25px; font-size: 34px !important; border-radius: 5px;"
								class="btn-info" data-dismiss="modal">Save</button>
						</div>
					</div>

				</div>
			</div> --%>
			
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
                                       <ul class="catindus" id ="industry_list" >
                                          <c:forEach var="industry" items="${industry}">
                                             <li id="industryLi${industry.indstId}" >
                                                <form:checkbox id ="categCheckBoxIndust${industry.indstId}"  value="${industry.indstId}" path = "industries" />
                                                ${industry.indstName} 
                                             </li>
                                          </c:forEach>
                                       </ul>
                                    </div>
                                    <div class="modal-footer">
                                       <button style="border:none;padding: 0px 25px;
                                          font-size: 34px !important; border-radius:5px;" class="btn-info" data-dismiss="modal">save</button>
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
            </div>
         </form:form>
      </div>
   </section>
  
</body>