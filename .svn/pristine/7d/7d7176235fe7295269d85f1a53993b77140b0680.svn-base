<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>ERPfixers | Create My Profile</title>
<head>
   
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
      
      
      $(document).ready(function(){ 
       $('ul#industry_list li').click(function(e) {
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
     
     
      });
     $(document).on('click','.tt-selectable',function(){
     	   var str = $("#smart_search").val();
     	   var value = findValueFromKey(indstList,str);
     	   $("#categCheckBox"+value).attr('checked', true);
     	   var html = '<span id="categ'+value+'"  class="btn btn-warning" >'+$(this).text()+' <i class="fa fa-times " onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
          $("#addCategory").append(html);
         $("#smart_search").val('');
       });
        
    
      
     
      var client = new XMLHttpRequest();
      function uploadProfileImage(){
    	  
    		var filePath =document.getElementById("multipartFile").value;
    		var fileName = filePath.split('\\').pop();
    		var fileExtension = fileName.split('.').pop().toLowerCase();
    		if(fileExtension=="jpg" ||fileExtension=="jpeg" || fileExtension=="png"){
    			$("#imageLoader").show();
        	    var multipartFile = document.getElementById("multipartFile");
        	  / Create a FormData instance /
        	    var formData = new FormData();  
        	    formData.append("multipartFile", multipartFile.files[0]);
        	    var userName='${user.userName}';
        	    console.log(userName);
        	    formData.append("userName",userName);
        	  /*   var id=${user.userId};
        	    console.log(id);
        	    formData.append("userId",id); */
        	    //console.log(formData);
        	    client.open("post", "../memberProfileImage", true);
        	    $('#saveButton').attr('disabled', 'disabled');
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
    	  	 $('#myProfileImage').attr("src",data.result.originalFullFileName);
    	  	 document.getElementById("myImageUrl").value=data.result.originalFileName; 
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
            action="${pageContext.request.contextPath}/member/editProfile"  modelAttribute="profile" commandName="profile" enctype="multipart/form-data"  >
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
                              <img src='${memberImagePath}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';" id="myProfileImage">
                                 <!-- <img src="../images/default_profile.jpg"> -->
                                 <form:input type="file" path="multipartFile" onchange="uploadProfileImage()"/>
                                 <img src="../images/loading_spinner.gif" style="position: absolute;
   									 top: 0; display:none;" id="imageLoader">
                              </div>
                              <div class="profilOuter1">
                              <div class="row">
                                 <div class="col-md-8">
                                    <p>Last login: ${lastLogin} ago.</p>
                                    <c:if  test="${profile.timeZone!=null }">
                                      <p  id="timeZone" >TimeZone :${profile.timeZone }</p>
                                    </c:if>
                                  
                                    <p >Location: ${user.city} , ${user.country}</p>
                                 </div>
                                 <div class="col-md-4">
                                    <img src="../flags/${user.country}.png" class="pull-right1"> 
                                    <p class="text-success pull-right1">Verified  <i class="fa fa-check-circle"></i></p>
                                 </div>
                              </div>
                           </div>
                           </div>
                        </div>
                        <div class="col-md-7 col-sm-7">
                           <form:input path ="userId"  type="hidden" value="${user.userId}"/>
                           <div class="form-group">
                              <div class="title">${user.userName}  
                                 <span>Write an overview about your company and your position.</span>
                              </div>
                              <fieldset class="height">
                                 <legend>Type here</legend>
                                   <form:textarea  path ="overview" type="text"></form:textarea>  
                                  <%--  <form:input type="text"  rows=""  path="overview"  id="overview" value="${user.overview}" /> --%>  
                      </fieldset>        
                      <fieldset>  
                     <legend><spring:message code="welcome.companyName" /><span>*</span></legend>
					<form:input type="text" path="companyName" class="form-control noborder" value="${user.companyName}"/>
					</fieldset>
					<form:errors path="companyName" cssClass="error" />
                              
                           </div>
                           <div class="categorydis" id = "addCategory" ></div>
                           <div class="form-group">
                              <div class="title">My Industry(s)
                                 <span>Search for your industry or select from the dropdown.</span>
                              </div>
                              <fieldset>
                                 <legend>Smart Search</legend>
                                 <div id="the-basics">
                                    <input class="typeahead" type="text" placeholder="" id ="smart_search" >
                                 </div>
                              </fieldset>
                              <fieldset data-toggle="modal" data-target="#myModal1" style="cursor:pointer;">
                                 <legend>Industries</legend>
                                 <select style="cursor:pointer;">									
                                 </select>
                              </fieldset>
                              <form:errors path="categories" cssClass="error" />
                              <button type="submit" class="btn btn-warning btn-block" id="saveButton">Save Profile</button>
                           </div>
                           <!-- Modal -->
                           <!-- Modal -->
                           <div id="myModal1" class="modal fade" role="dialog">
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
                                             <li>
                                                <form:checkbox id ="categCheckBox${industry.indstId}"  value="${industry.indstId}" path = "categories" />
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
   
   <script type="text/javascript">
      var industJSONByUser = ${industJSONByUser};
      for(var i =0; i<industJSONByUser.length; i++){
      	     var value = industJSONByUser[i].indstId;
      	     $("#categCheckBox"+value).attr('checked', true);
      		 var html = '<span id="categ'+value+'"  class="btn btn-warning" >'+industJSONByUser[i].indstName+' <i class="fa fa-times " onclick="return closebtn('+value+')" id="close'+value+'"></i></span>';
      	     $("#addCategory").append(html);
      }
    </script>
</body>