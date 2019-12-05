<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<section class="member">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="animated fadeInDown">All Users List</h1>
					  <c:choose>
                  <c:when test="${message==''}">
                  </c:when>
                  <c:otherwise>
                     <div class="row">
                        <div class="col-md-1 col-sm-1"></div>
                        <div class="col-md-10 col-sm-10">
                           <c:choose>
                              <c:when test="${msgType=='success'}">
                                 <div class="alert alert-success">
                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                    <strong>Success!</strong> ${message}
                                 </div>
                              </c:when>
                              <c:otherwise>
                                 <div class="alert alert-danger">
                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                    <strong>Error!</strong> ${message}
                                 </div>
                              </c:otherwise>
                           </c:choose>
                        </div>
                        <div class="col-md-1 col-sm-1"></div>
                     </div>
                  </c:otherwise>
               </c:choose>
				</div>
			</div>
			<br>
			
			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
				
				<ul class="nav nav-tabs width50">
				
													<c:choose>
                                                        <c:when test="${UserType=='C'}">
                                                      <li class="active"><a href="${pageContext.request.contextPath}/admin/users?status=Members">Members</a></li>
	                <li><a href="${pageContext.request.contextPath}/admin/users?status=Fixers">Fixers</a></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                     <li ><a href="${pageContext.request.contextPath}/admin/users?status=Members">Members</a></li>
	                <li class="active"><a href="${pageContext.request.contextPath}/admin/users?status=Fixers">Fixers</a></li>
                                                        </c:otherwise>
                                                    </c:choose>
				
				
					
                </ul>
				<div class="adminFixersList">
						<div id="FixerApproval" class="tab-pane fade in active">
						<div class="row">
						  <c:if test="${totalUsersCount>=1}">
						<div class="col-md-12 col-sm-12 col-xs-12">
							 <div class="searchPay">
								<input type="search" id="searchTextField">	
								<button type="submit" id="searchButton" class="btn btn-danger" onclick="searchButtonTapped()">Search</button>
							</div> 
						</div>
						</c:if>
					</div>
				<div class="overflowX">
				  
				  	  <c:choose>
				  <c:when test="${totalUsersCount>=1}">
				  
				  <div class="FixerApproval"  id="searchFixerList" >
						
                                                    <div class="dtablrow">
                                                       
                                                      
                                                          <c:choose>
                                                        <c:when test="${UserType=='C'}">
                                                        <div class="dtablecel thead">S. No.</div> 
                                                        <div class="dtablecel thead">UserName</div>
                                                        <div class="dtablecel thead">Email</div>
                                                        <div class="dtablecel thead">Credits</div> 
                                                        <div class="dtablecel thead">Action</div> 
                                                        </c:when>
                                                        <c:otherwise>
                                                        <div class="dtablecel thead">S. No.</div> 
                                                        <div class="dtablecel thead">UserName</div>
                                                        <div class="dtablecel thead">Email</div>
                                                        <div class="dtablecel thead">Linkedin Profile</div> 
                                                        <div class="dtablecel thead">Rate fixer</div> 
                                                         <div class="dtablecel thead">Delete</div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                        
                                                        
                                                        <!-- <div class="dtablecel thead">Linkedin Profile</div>  -->
                                                        
                                                    </div>
                                                   
                                                    <c:forEach var="item" items="${allUsers}" varStatus="commentLoop">


                                                         <div class="dtablrow" >
                                                            <div class="dtablecel">${commentLoop.index+1}</div>
                                                            <%-- <button class="astext" onclick="fixerProfile(this.id);" id=${item.userId}>${item.userName}</button> --%>
                                                                <%-- "${pageContext.request.contextPath}/admin/fixerProfile" --%>
                                                                  <c:choose>
                                                        <c:when test="${item.userType=='C'}">
                                                           <div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userMemberProfile?userId=${item.userId}">${item.userName}</a>
                                                                    </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userFixerProfile?fixerId=${item.userId}">${item.userName}</a>
                                                                    </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                                
                                                                
                                                                   
                                                                    <div class="dtablecel">${item.email}</div>
                                                                    <%-- <div class="dtablecel"><a href="http://www.linkedin.com">${item.linkedinProfile}</a>
                                                        </div> --%>
                                                         <%-- <div class="dtablecel"><a href="${item.linkedinProfile}" target="_blank" class="btn btn-info creditPoint" data-toggle="modal" data-target="#UserCredits">${item.userCredits.points}</a> --%>
                                                         <c:choose>
                                                        <c:when test="${item.userType=='C'}">
                                                            <div class="dtablecel"><a  type="button" onclick="creditsChangeButtonCalled(${item.userId})" class="btn btn-info creditPoint">${item.userCredits.points}</a></div>
                                                             
                                                        </c:when>
                                                        <c:otherwise>
                                                       				<c:choose>
 																	 <c:when test="${fn:contains(item.linkedinProfile, 'http')}">
                                              			                   <div class="dtablecel"><a href="${item.linkedinProfile}" target="_blank" >${item.linkedinProfile}</a></div>

  																	 </c:when>
  		
  																	 <c:otherwise>
                                                        <div class="dtablecel"><a href="https://${item.linkedinProfile}" target="_blank" >${item.linkedinProfile}</a></div>

 															 		</c:otherwise>
															</c:choose>
                                                        </c:otherwise>
                                                    </c:choose>
                                                        <c:if test="${item.userType=='F'}">
 	        <div class="dtablecel" style="text-align:center;"><a onclick="updateFixerRating(5 ,${item.userId})" href="javascript:void(0)" ><span style="width:100%; display:block;">5 Rating</span>
 	     
 	    
 	       <c:choose>
 	       
 	       
 	       <c:when test="${item.rating eq 5}">
 	       <i id="${item.userId}" class="fa fa-star"></i>
 	       </c:when>
 	       <c:otherwise>
 	       <i id="${item.userId}" class="fa fa-star-o"></i>
 	       </c:otherwise>
 	       </c:choose>
 	        
 	        </a></div>
                                                        
                                                        </c:if>
                                                         <c:choose>
                                                        <c:when test="${item.userType=='C'}">
                                                           <div class="dtablecel"><a  href="${pageContext.request.contextPath}/admin/deleteMember?userId=${item.userId}" >Delete</a>
                                                        </div>
                                                             
                                                        </c:when>
                                                        <c:otherwise>
                                                        <div class="dtablecel"><a  href="${pageContext.request.contextPath}/admin/deleteFixer?userId=${item.userId}" >Delete</a>
                                                        </div>
                                                        </c:otherwise>
                                                    </c:choose>
 
                                                        
                                                        
                                                    <%-- <c:choose>
                                                        <c:when test="${item.userType=='C'}">
                                                            <div class="dtablecel">Member</div>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <div class="dtablecel">Fixer</div> 
                                                        </c:otherwise>
                                                    </c:choose> --%>
                                                        
                                        </div>
                                        </c:forEach>
                                        
                                        </div>
                                        
                                     <div class="row">
							<div class="col-md-12">
								<ul class="pagination pull-right"  id = "fixerListPagination" >
								     <c:choose>
                                                            <c:when test="${totalPage==0}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${currentPageNo=='1'}">
                                                                        <li><a class="disable"><i class="fa fa-caret-left"></i></a>
                                                                        </li>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    
													<c:choose>
                                                        <c:when test="${UserType=='C'}">
                                                      <li><a href="../admin/users?pageNo=${currentPageNo}&flag=left" class="nexpre"><i class="fa fa-caret-left"></i></a>
                                                                        </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                     <li><a href="../admin/users?pageNo=${currentPageNo}&flag=left&status=Fixers" class="nexpre"><i class="fa fa-caret-left"></i></a>
                                                                        </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                                       
                                                                    </c:otherwise>

                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>




                                                        <c:forEach begin="${startPage}" end="${endPage}" varStatus="p">
                                                            <c:choose>
                                                                <c:when test="${p.index==currentPageNo}">
                                                                    <li><a class="active">${p.index}</a>
                                                                    </li>
                                                                </c:when>
                                                                <c:otherwise>
                                                                
													<c:choose>
                                                        <c:when test="${UserType=='C'}">
                                                     <li><a href="../admin/users?pageNo=${p.index}&flag=current">${p.index}</a>
                                                                    </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                      <li><a href="../admin/users?pageNo=${p.index}&flag=current&status=Fixers">${p.index}</a>
                                                                    </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                                
                                                                    
                                                                </c:otherwise>
                                                            </c:choose>


                                                        </c:forEach>
                                                        <c:choose>
                                                            <c:when test="${totalPage==0}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <li>...</li>
                                                                <li><a>${totalPage}</a>
                                                                </li>
                                                            </c:otherwise>
                                                        </c:choose>



                                                        <c:choose>
                                                            <c:when test="${totalPage==0}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${currentPageNo==totalPage}">
                                                                        <li><a class="disable"><i class="fa fa-caret-right"></i></a>
                                                                        </li>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    
                                                                    
													<c:choose>
                                                        <c:when test="${UserType=='C'}">
                                                      <li><a href="../admin/users?pageNo=${currentPageNo}&flag=right" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                                                        </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                     <li><a href="../admin/users?pageNo=${currentPageNo}&flag=right&status=Fixers" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                                                        </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                                    
                                                                       
                                                                    </c:otherwise>

                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>
								
								</ul>
							</div>
						</div>

                                        
					
                                        </c:when>
                                        <c:otherwise>
                                            <div class="animated fadeInDown nodata">No Data Found.</div>
                                        </c:otherwise>
                                        </c:choose></div>	
                                        
						
					
					
					
					
					
				  </div>
				</div>
					</div>
				</div>
				
			
		
		 <!-- Modal -->
  <div class="modal fade" id="UserCredits" role="dialog">
    <div class="vertical-alignment-helper">
      <div class="modal-dialog  vertical-align-center">


          <div class="modal-content">
  <form  role="form" class="contact-form" id="contact-form"  
            method="post"
            action="${pageContext.request.contextPath}/admin/changeCredits"  >
                      <div class="attachmentlink">
                      <h2 class="text-center" style="text-align:center;">Do you want to change the credits?</h2>
                      <div class="row">
                      	<div class="col-md-3"></div>
                      	<div class="col-md-6">
                      		<fieldset>
						   <legend>Credits</legend>
						  <input id="points" name="points" value="" class="form-control" type="text"> 
						   <input id="fixerId" type="hidden"  value="0" name="fixerId" >
			          	<!-- <input id="fixerId" name="fixerId" value="" class="form-control" type="text">  -->
						</fieldset>
                      	</div>
                      	<div class="col-md-3"></div>
                      	</div>
                      	<br>
                      	<div class="row">
                      	<div class="col-md-3"></div>
                      	<div class="col-md-6">
                      		<button  class="btn btn-info " style="width:48%;" onclick="saveCreditsButtonClicked()">Save</button>
                      		<button class="btn btn-danger "style="width:48%;" data-dismiss="modal">Cancel</button>
                      	</div>
                      	<div class="col-md-3"></div>
                      	</div>
                      	<div class="col-md-4"></div>
                      
                         
						
                      </div>
                  
                  
              </form>
          </div>
       
      </div>
  </div>
  </div>
  
<!--   model end -->
		
		
	</section>
	
	<script>
	 function searchButtonTapped(){
		 
		 var userType='${UserType}';
		 if(userType == 'F'){
			 searchFixerButtonTapped();
			 return;
		 }
		
	    	var name = document.getElementById("searchTextField");
			var fixerName=name.value;
			$.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/admin/searchUserMember",
			    data : {
			    searchText: fixerName
			     }
			   }).done(function(response) {
				   
				   var data = response.result.fixers;
				   console.log(data);
				   var totalPage=response.result.totalPage;
				   var startPage=response.result.startPage;
				   var endPage=response.result.endPage;
				   var currentPageNo=response.result.currentPageNo;
				   var pagHtml='';
				    $("#searchFixerList").empty();
				     $("#fixerListPagination").empty();
	                    if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo=='1'){
				    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
				    	 }else{
				    		 pagHtml='<li onclick="pagMemberClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
				    	 }
				     }
				     
				     for(var k=startPage;k<=endPage;k++){
				    	if(k==currentPageNo){
				    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
				    	}else{
				    	pagHtml +='<li  onclick="pagMemberClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
				    	}
				    	
				    }
				     if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo==totalPage){
				    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
				    	 }else{
				    		 pagHtml +='<li onclick="pagMemberClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
				    	 }
				     }
				     
				    
				    $("#fixerListPagination").append(pagHtml); 
				    var html ='';
					 if(data.length>0){
						html+='<div class="dtablrow"><div class="dtablecel thead">S. No.</div><div class="dtablecel thead">UserName</div><div class="dtablecel thead">Email</div><div class="dtablecel thead">Credits</div><div class="dtablecel thead">Action</div></div>'; 
					 } 
				    for(var i =0 ;i<data.length;i++){
				    	html+='<div class="dtablrow">';
				    	var j=i+1;
				    	html+=' <div class="dtablecel">'+j+'</div>';
				    	
				    	if(data[i].userType == 'C'){
				    		html+='<div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userMemberProfile?userId='+data[i].userId+'">'+data[i].userName+'</a></div>';
				    	}else{
				    		html+='<div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userFixerProfile?fixerId='+data[i].userId+'">'+data[i].userName+'</a></div>';
				    	}
				    	
				    	html += '<div class="dtablecel">'+data[i].email+'</div><div class="dtablecel"><a  type="button" onclick="creditsChangeButtonCalled('+data[i].userId+')" class="btn btn-info creditPoint">'+data[i].points+'</a></div>';
				    	html +=' <div class="dtablecel"><a  href="${pageContext.request.contextPath}/admin/deleteMember?userId='+data[i].userId+'" >Delete</a></div></div>';
				    	
				    }
				    console.log(html);
				    	$("#searchFixerList").append(html);
				    	
				    
				    	
				    
			   });
	    }
	
	 function pagMemberClick(pageNo,flag){
	    	var name = document.getElementById("searchTextField");
			var fixerName=name.value;
			$.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/admin/searchUserMember",
			    data : {
			    searchText: fixerName,
			    pageNo: pageNo,
			    flag:flag
			     }
			   }).done(function(response) {
				   var data = response.result.fixers;
				   console.log(data);
				   var totalPage=response.result.totalPage;
				   var startPage=response.result.startPage;
				   var endPage=response.result.endPage;
				   var currentPageNo=response.result.currentPageNo;
				   var pagHtml='';
				    $("#searchFixerList").empty();
				     $("#fixerListPagination").empty();
	                    if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo=='1'){
				    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
				    	 }else{
				    		 pagHtml='<li onclick="pagMemberClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
				    	 }
				     }
				     
				     for(var k=startPage;k<=endPage;k++){
				    	if(k==currentPageNo){
				    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
				    	}else{
				    	pagHtml +='<li  onclick="pagMemberClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
				    	}
				    	
				    }
				     if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo==totalPage){
				    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
				    	 }else{
				    		 pagHtml +='<li onclick="pagMemberClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
				    	 }
				     }
				     
				    
				    $("#fixerListPagination").append(pagHtml); 
				    var html ='';
					 if(data.length>0){
						html+='<div class="dtablrow"><div class="dtablecel thead">S. No.</div><div class="dtablecel thead">UserName</div><div class="dtablecel thead">Email</div><div class="dtablecel thead">Credits</div><div class="dtablecel thead">Action</div></div>'; 
					 } 
				    for(var i =0 ;i<data.length;i++){
				    	html+='<div class="dtablrow">';
				    	var j=i+1;
				    	html+=' <div class="dtablecel">'+j+'</div>';
				    	if(data[i].userType == 'C'){
				    		html+='<div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userMemberProfile?userId='+data[i].userId+'">'+data[i].userName+'</a></div>';
				    	}else{
				    		html+='<div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userFixerProfile?fixerId='+data[i].userId+'">'+data[i].userName+'</a></div>';
				    	}
				    	
				    	html += '<div class="dtablecel">'+data[i].email+'</div><div class="dtablecel"><a  type="button" onclick="creditsChangeButtonCalled('+data[i].userId+')" class="btn btn-info creditPoint">'+data[i].points+'</a></div>';
				    	html+=' <div class="dtablecel"><a  href="${pageContext.request.contextPath}/admin/deleteMember?userId=${item.userId}" >Delete</a></div></div>'
				    	
				    }
				    console.log(html);
				    	$("#searchFixerList").append(html);
				    	
				    
				    	
				    
			   });
	    }
	 function searchFixerButtonTapped(){
	    	var name = document.getElementById("searchTextField");
			var fixerName=name.value;
			$.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/admin/searchUserFixer",
			    data : {
			    searchText: fixerName
			     }
			   }).done(function(response) {
				   var data = response.result.fixers;
				   console.log(data);
				   var totalPage=response.result.totalPage;
				   var startPage=response.result.startPage;
				   var endPage=response.result.endPage;
				   var currentPageNo=response.result.currentPageNo;
				   var pagHtml='';
				    $("#searchFixerList").empty();
				    $("#fixerListPagination").empty();
				     $("#fixerListPagination").empty();
	                    if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo=='1'){
				    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
				    	 }else{
				    		 pagHtml='<li onclick="pagFixerClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
				    	 }
				     }
				     
				     for(var k=startPage;k<=endPage;k++){
				    	if(k==currentPageNo){
				    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
				    	}else{
				    	pagHtml +='<li  onclick="pagFixerClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
				    	}
				    	
				    }
				     if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo==totalPage){
				    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
				    	 }else{
				    		 pagHtml +='<li onclick="pagFixerClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
				    	 }
				     }
				     
				    
				    $("#fixerListPagination").append(pagHtml); 
				    var html ='';
					 if(data.length>0){
						html+=' <div class="dtablrow"><div class="dtablecel thead">S. No.</div><div class="dtablecel thead">UserName</div><div class="dtablecel thead">Email</div><div class="dtablecel thead">Linkedin Profile</div><div class="dtablecel thead">Rate Fixer</div><div class="dtablecel thead">Delete</div></div>'; 
					 } 
				     
				    for(var i =0 ;i<data.length;i++){
				    	html+='<div class="dtablrow">';
				    	var j=i+1;
				    	html+=' <div class="dtablecel">'+j+'</div>';
				    	if(data[i].userType == 'C'){
				    		html+='<div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userMemberProfile?userId='+data[i].userId+'">'+data[i].userName+'</a></div>';
				    	}else{
				    		html+='<div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userFixerProfile?fixerId='+data[i].userId+'">'+data[i].userName+'</a></div>';
				    	}
				    	
				    	html += '<div class="dtablecel">'+data[i].email+'</div><div class="dtablecel"><a href='+data[i].linkedinProfile+' target="_blank">'+data[i].linkedinProfile+'</a></div>';
				   if(data[i].userType == 'F')
				    	html += '<div class="dtablecel" style="text-align:center;"><a onclick="updateFixerRating(5 ,'+data[i].userId+')" href="javascript:void(0)" ><span style="width:100%; display:block;">5 Rating</span>';
				   if(data[i].rating == 5){
					 html +=  '<i id="'+data[i].userId+'" class="fa fa-star"></i>';
				   }else{
						 html +=  '<i id="'+data[i].userId+'" class="fa fa-star-o"></i>';

				   }
				   html +='</a></div>';
				    html +=' <div class="dtablecel"><a  href="${pageContext.request.contextPath}/admin/deleteFixer?userId='+data[i].userId+'" >Delete</a></div></div>';
				    	
				    }
				    console.log(html);
				    	$("#searchFixerList").append(html);
				    	
				    
				    	
				    
			   });
	    }
	 function pagFixerClick(pageNo,flag){
	    	var name = document.getElementById("searchTextField");
			var fixerName=name.value;
			$.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/admin/searchUserFixer",
			    data : {
			    searchText: fixerName,
			    pageNo: pageNo,
			    flag:flag
			     }
			   }).done(function(response) {
				   var data = response.result.fixers;
				   console.log(data);
				   var totalPage=response.result.totalPage;
				   var startPage=response.result.startPage;
				   var endPage=response.result.endPage;
				   var currentPageNo=response.result.currentPageNo;
				   var pagHtml='';
				    $("#searchFixerList").empty();
				     $("#fixerListPagination").empty();
	                    if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo=='1'){
				    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
				    	 }else{
				    		 pagHtml='<li onclick="pagFixerClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
				    	 }
				     }
				     
				     for(var k=startPage;k<=endPage;k++){
				    	if(k==currentPageNo){
				    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
				    	}else{
				    	pagHtml +='<li  onclick="pagFixerClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
				    	}
				    	
				    }
				     if(totalPage==0){
				    	 
				     }else{
				    	 if(currentPageNo==totalPage){
				    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
				    	 }else{
				    		 pagHtml +='<li onclick="pagFixerClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
				    	 }
				     }
				     
				    
				    $("#fixerListPagination").append(pagHtml); 
				    var html ='';
					 if(data.length>0){
						html+=' <div class="dtablrow"><div class="dtablecel thead">S. No.</div><div class="dtablecel thead">UserName</div><div class="dtablecel thead">Email</div><div class="dtablecel thead">Linkedin Profile</div><div class="dtablecel thead">Action</div></div>'; 
					 } 
				     
				    for(var i =0 ;i<data.length;i++){
				    	html+='<div class="dtablrow">';
				    	var j=i+1;
				    	html+=' <div class="dtablecel">'+j+'</div>';
				    	if(data[i].userType == 'C'){
				    		html+='<div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userMemberProfile?userId='+data[i].userId+'">'+data[i].userName+'</a></div>';
				    	}else{
				    		html+='<div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/userFixerProfile?fixerId='+data[i].userId+'">'+data[i].userName+'</a></div>';
				    	}
				    	
				    	html += '<div class="dtablecel">'+data[i].email+'</div><div class="dtablecel"><a href='+data[i].linkedinProfile+' target="_blank">'+data[i].linkedinProfile+'</a></div>';
				    	html +=' <div class="dtablecel"><a  href="${pageContext.request.contextPath}/admin/deleteFixer?userId=${item.userId}" >Delete</a></div></div>';
				    	
				    }
				    console.log(html);
				    	$("#searchFixerList").append(html);
				    	
				    	
				    
				    	
				    
			   });
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 function creditsChangeButtonCalled(ele){
		 
		 $('#UserCredits').modal({
     		show: 'true'
		 });
		 $('#fixerId').val(ele);
		 document.getElementById("fixerId").value=ele;
		
	 }
	 
	 function saveCreditsButtonClicked(){
		 $('#UserCredits').submit();
	 }
	 
	 
	 function updateFixerRating(rating ,userId){
		 
		 $.ajax({
			 
			 method: "POST",
			 url: "../admin/updateFixerRatingByAdmin",
			 data: {
				 rating : rating,
				 userId : userId
			 }
		 }).done( function(response){
			 
			 var resp = response.status;
			 if(resp == "success"){
				$('#'+userId).removeClass('fa fa-star-o').addClass('fa fa-star');
			 }
			 
		 })
		 
		 
	 }
	</script>
	
</body>
</html>