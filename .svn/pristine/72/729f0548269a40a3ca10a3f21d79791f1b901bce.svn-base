<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<title>ERPfixers | Create My Profile</title>ok
<head>
   <script type="text/javascript" src="../js/jstz-1.0.4.min.js"></script>
   <style  type="text/css" >
      .error {
      color: red;
      font-weight: bold;
      }
   </style>
   <link href="../css/font-awesome.css" rel="stylesheet" type="text/css">
   
</head>

<body>
<script>
 
</script>
<script type="text/javascript">
    var json = ${industryJSON};
    var indstList = new Array();
      for(var i=0; i<json.length;i++){
        indstList.push({
      	key:json[i].indstName,
      	value:json[i].indstId,
        });
      }	 
        
      function findValueFromKey(indstList,key){
    	  for(var i in indstList){
    		  if(indstList[i]['key'] == key){
    			  return  indstList[i]['value'];
    		}
    	  }
      }
      
     
      
      
      function closebtn(value){
         $("#categ"+value).remove();
         $("#categCheckBox"+value).attr('checked', false);
         
      }
      
      var checkArray = [];
      $(document).ready(function(){ 
    	  
    	  $('aside').hide();
    	  
    	  var errorFields=<c:out default="[]" escapeXml="false" value="${not empty errorFields?errorFields:'[]'}" />;
  			//alert(errorFields);
  		for(var i = 0; i<errorFields.length;i++)
  			{
  				var field = "#"+errorFields[i];

  				field_label = field + "_lable";
  				$(field).addClass('error');
  				$(field_label).addClass('error');
  				
  				console.log(field);
  		
  			}
  		var errorCheck = '${errorFields}';
   if(errorCheck !='' ){
	
	var catArr = ${profileCategories};
	if(catArr != null){
    var len = catArr.length;
    for (var i = 0; i < len; i++) {
		  $('#categCheckBox'+catArr[i]).attr('checked', true);
		  var value = $('#categCheckBox'+catArr[i]).closest('li').children('label').text();
		  var html = '<span id="categ'+catArr[i]+'" >'+value+' <i class="icon" data-icon="y" onclick="return closebtn('+catArr[i]+')" id="close'+catArr[i]+'"></i></span>';
          $("#addCategory").append(html);

    }
	}
}
    	  
    	  $( "#overview" ).val('${profile.overview}');
    	  
    	  $(".close").click(function(){
    		  for(var i=0;i<checkArray.length;i++){
    			  $("#addCategory").find('span#categ'+checkArray[i]).remove();
     			  $('#categCheckBox'+checkArray[i]).attr('checked', false);
    		  }
        	}) ;
    	  
    	  $("#save").click(function(){
    		  checkArray = [];
    	  });
           
       $('ul#industry_list li').click(function(e) {
            var checkbox = $(this).find("input[type='checkbox']");
            var value = $(this).children().eq(0).val();
            if(!checkbox.attr('checked')){
               checkbox.attr('checked', true);
               checkArray.push(value);
                
               var html = '<span id="categ'+value+'" >'+$(this).text()+' <i class="icon" data-icon="y" onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
            $("#addCategory").append(html);
            }else{
               checkbox.attr('checked', false);
               $("#categ"+value).remove();
               checkArray.pop('categ'+value);
            }
        });
   
       
      
      
      
      
       
       
       
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
     $(document).on('click','.tt-selectable',function(){
     	   var str = $("#smart_search").val();
     	   var value = findValueFromKey(indstList,str);
     	   $("#categCheckBox"+value).attr('checked', true);
     	   var html = '<span id="categ'+value+'"  >'+$(this).text()+' <i class="icon" data-icon="y" onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
          $("#addCategory").append(html);
         $("#smart_search").val("");
       });
        
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
    	    var userName=$('#userName').val();
    	    console.log(userName);
    	    formData.append("userName",userName);
    	    //console.log(formData);
    	     //$("#saveButton").attr("disables", true);
    	     //document.getElementById("saveButton").disabled = true;
    	    $('#saveButton').attr('disabled', 'disabled');
    	    client.open("post", "../memberProfileImage", true);
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
    	  	
    	  	$('#myProfileImage').attr("src",data.result.originalFullFileName);
    	  	$("#imageLoader").hide();
  			$("#uploadtxt").css('display', 'block');

    	  	document.getElementById("myImageUrl").value=data.result.originalFileName;
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
            action="${pageContext.request.contextPath}/member/profile"  modelAttribute="memberProfile" commandName="memberProfile" enctype="multipart/form-data"  >
          <%--   <form:input id="myImageUrl" path="imageUrl" type="hidden" value=${user.imageUrl}/> --%>
             <form:input id="myImageUrl" path ="imageUrl"  type="hidden" value="${profile.imageUrl}"/>
            <div class="row">
               <div class="col-md-1 col-sm-1"></div>
               <div class="col-md-10 col-sm-10">
				<div class="profilOuter">
               <div class="step">${Step}</div>
                  <div class="createmyprofile">
                     <div class="row">
                        <div class="col-md-5 col-sm-5">
                           <div class="profilepic">
                              <div class="btn-file">
                               <img src='${profile.imageUrl}'  onerror= "this.src ='${pageContext.request.contextPath}/images/profile.png'"  id="myProfileImage" accept=".png, .jpg, .jpeg">
                                 <!-- <img src="../images/default_profile.jpg" id="myProfileImage"> -->
                                 <form:input type="file" path="multipartFile" id="multipartFile" onchange="uploadProfileImage()"/>
                                 <img src="${pageContext.request.contextPath}/images/loader.gif" style="position: absolute;
  									  top:38%; left:38%;display:none; width:45px; height:45px; z-index:9999;" id="imageLoader">
                               <span id="uploadtxt" class="uploadtxt">Upload Photo</span>
                              </div>
                              
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
										<form:input path="userType" type="hidden"
										value="${profile.userType}" />
										<form:input path="timeZone" type="hidden"
										value="${profile.timeZone}" />
										<form:input path="sendEmail" type="hidden"
										value="${profile.sendEmail}" />
										<form:input path="source" id="sorc" type="hidden"
										value="${profile.source}" />
										
                             <div class="profilOuter1">
                              <div class="row">
                                 <div class="col-md-8 "> 
                                 	<c:if  test="${profile.timeZone!=null }">
                                 	<p  id="timeZone" >TimeZone :${profile.timeZone }</p>
                                 	</c:if>
                                    
                                    <p >Location: ${profile.city} , ${profile.country}</p>
                                 </div>
                                 <div class="col-md-4 ">
                                    <img src="../flags/${profile.country}.png" class="pull-right1"> 
                                    <p class="text-success pull-right1">Verified  <i class="fa fa-check-circle"></i></p>
                                 </div>
                              </div>
                              </div>
                           </div>
                        </div>
                        <div class="col-md-7 col-sm-7">
                           <div class="form-group">
                              <div class="title">${profile.userName}  
                                 <span>Write an overview about your company and your position.</span>
                              </div>
                              <div class="height">
                                 <label>Type here</label>
                                  <form:textarea placeholder="" path ="overview" id = "overview" class="form-control" style="position: relative;
    margin: 0px;width: 100%;border: 1px solid #ccc;padding: 2px 10px;"></form:textarea> 
                                 <%--  <form:input type="text" path="overview"  id="overview" value="${user.overview}" /> --%> 
                              </div>
                              
                              <%-- <fieldset >
                                 <legend>Company Name</legend>
                                <form:input type="text" path="companyName"  id="companyName" value="${profile.companyName}" /> 
                              </fieldset>--%>
                              
                              <div> 
                            	<label id = "companyName_lable"><spring:message code="welcome.companyName" /><span>*</span></label>
								<form:input id="companyName" type="text" path="companyName" class="form-control" value="${profile.companyName}"/>
							</div>
					<form:errors path="companyName" cssClass="error" />
                              
                           </div>
                           <div class="categorydis" id = "addCategory" >
                           
                          
                           </div>
                           <div class="form-group">
                              <div class="title">My Industry(s)
                                 <span>Search for your industry or select from the dropdown.</span>
                              </div>
                              <div>
                                 <label>Smart Search</label>
                                 <div id="the-basics">
                                    <input class="typeahead form-control" type="text" placeholder="" id ="smart_search" >
                                 </div>
                              </div>
                              <div data-toggle="modal" data-target="#myModal" style="cursor:pointer;">
                                 <label id="categories_lable">Industries</label>
                                 <span style="cursor:pointer; position: relative; margin: 0; width: 100%; padding: 2px 10px;
    color: #999; border: 1px solid #ccc !important;"  class="form-control"> <i class="fa fa-caret-down" style="float: right; font-size: 22px;"></i>
    </span>
                                 <!-- <select style="cursor:pointer; position: relative; margin: 0; width: 100%; padding: 2px 10px;
    color: #999; border: 1px solid #ccc !important;" class="form-control">									
                                 </select> -->
                              </div>
                              <form:errors path="categories" cssClass="error" />
                              <button type="submit" class="saveChange" style="float:left; margin-top:15px;" id="saveButton">Save Profile</button>
                           </div>
                           <!-- Modal -->
                           <!-- Modal -->
                           <div id="myModal" class="modal custm_mymodl fade" role="dialog">
                              <div class="modal-dialog modal-lg">
                                 <!-- Modal content-->
                                 <div class="modal-content">
                                    <!-- <div class="modal-header">
                                       <button type="button" class="close" data-dismiss="modal">&times;</button>
                                       <h2 class="modal-title">Industries</h2>
                                    </div>  -->
                                    <div class="modal-body">
                                    <h2 class="h1-underline">Industries</h2>
                                       <ul class="MyIndus1" id ="industry_list" >
                                       <c:forEach var="industryItem" items="${industry}">
										<li><input name="categories"
											id="categCheckBox${industryItem.indstId}"
											value="${industryItem.indstId}" type="checkbox"> <label
											for="${industryItem.indstId}">${industryItem.indstName}</label>
										</li>
									</c:forEach>
                                       </ul>
                                    </div>
                                    <div class="modal-footer">
                                    <button  class="saveChange width100B" data-dismiss="modal" id="save">Save</button>
                                    	<button  class="cancel width100B" data-dismiss="modal">Cancel</button>
                                       
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
   
   <script type="text/javascript">
      var industJSONByUser = ${industJSONByUser};
      
      for(var i =0; i<industJSONByUser.length; i++){
      	     var value = industJSONByUser[i].indstId;
      	     $("#categCheckBox"+value).attr('checked', true);
      		 var html = '<span id="categ'+value+'"  >'+industJSONByUser[i].indstName+' <i class="icon" data-icon="y" onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
      	     $("#addCategory").append(html);
      }
    </script>
</body>