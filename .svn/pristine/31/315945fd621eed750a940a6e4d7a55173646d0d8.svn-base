
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<script type="text/javascript" src="../js/commonMethods.js"></script>
</head>

   <body>
     <script>
 
</script>
      <a name="home" id="home"></a>
      <!-- price -->
      <section class="member">
         <div class="container">
         
         
         <div class="row">
				<div class="col-md-9 col-sm-9"></div>
				<div class="col-md-3 col-sm-3">
						<p style="font-size:25px; color:#f00; font-weight:bold;">Tools:</p>
						<a href="https://www.teamviewer.com/en/download" target="blank" class="pull-right"><img src="../images/tvIcon.png" width="150"></a>
						<a href="https://join.me/" target="blank"  class="pull-right"><img src="../images/jmLogo.png" width="70">
									 </a>
							
				</div>
			</div>
            <div class="row">
				<div class="col-md-1 col-sm-1"></div>
               <div class="col-md-10">
                  <h1>Query Review</h1>
				  <h2 class="QueryTitle fontsize35">1.Query Title: ${query.queryTitle}</h2>
				  <div class="notimessagetxt"><span class="more">${queryContent}</span></div>
				  <!-- <div class="step">STEP 1: Create your Login &amp; Enter Your Details</div> -->
               </div>
			   <div class="col-md-1 col-sm-1"></div>
            </div>
            <br>
            <div class="row">
               <div class="col-md-12 col-sm-12">
               </div>
            </div>
            <div class="row">
               <div class="col-md-1 col-sm-1"></div>
               <div class="col-md-10 col-sm-10 col-sm-12">
                  <div class="notimessagetxt">
                     <div class="graybg1">
                        <div class="row">
                           <div class="col-md-6 col-sm-6">
                              <h2><span>1.</span> Reason of Member</h2>
                             
                              <div class="form-group">
                                 <textarea class="form-control solutionsbox" placeholder="Type here.." style="max-height:135px;" readonly>${memberReason}</textarea>
                              </div>
                              <h2><span>2.</span> Reason of Fixer</h2>
                               <textarea class="form-control solutionsbox" placeholder="Type here.." style="max-height:135px;" readonly>${fixerReason}</textarea>
                           </div>
                           <div class="col-md-6 col-sm-6">
                              <div class="chatdiv">
                                 <h2 class="text-center">Chat Discussion</h2>
                                 <div class="chatbox height450">
                                    <div class="chat">
                                    
                                       <c:forEach var="message" items="${messagesSet}">	
											<c:if test="${message.msgFrom == 'F'}">
											<div class="crow">
														<img src='${fixerImgIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
													         ${fixerName}  ${message.auditTime} <br>
													         <p>
															<c:if test="${message.status=='W'}">
																${message.message}
														<!-- <script>document.write(replaceURLWithHTMLLinks('${message.message}'))</script> -->
														</c:if>
														<c:if test="${message.status=='IR'}">
														
														${message.message}
														</c:if>
														<c:if test="${message.status=='WD'}">
														Document Uploaded <br>
														<a href="${message.message}"><i class="fa fa-paperclip"></i> ${message.documentFilename}</a>
														</c:if>
														<c:if test="${message.status=='WL'}">
														Link Added <br>
														<a href="${message.message}" target="_blank">${message.message}</a>
														</c:if></p>

													</div>
					                         </c:if>
					                         
					                         <c:if test="${message.msgFrom == 'C'}">
						                   
													<div class="crow">
														<img src='${myUser.profileIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
													 ${memberName}	${message.auditTime} <br>
													 <p>
														<c:if test="${message.status=='W'}">
															${message.message}
														<!-- <script>document.write(replaceURLWithHTMLLinks('${message.message}'))</script> -->
														</c:if>
														<c:if test="${message.status=='IR'}">
														
														${message.message}
														</c:if>
														<c:if test="${message.status=='WD'}">
														Document Uploaded <br>
														<a href="${message.message}"><i class="fa fa-paperclip"></i> ${message.documentFilename}</a>
														</c:if>
														<c:if test="${message.status=='WL'}">
														Link Added <br>
														<a href="${message.message}" target="_blank">${message.message}</a>
														</c:if>
                                                       </p>
													</div>
					                         </c:if>
					                         <c:if test="${message.msgFrom == 'A'}">
						                   
													<div class="crow">
														<img src='' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
													Admin: ${admin.userName}	${message.auditTime} <br>
													 <p>
														<c:if test="${message.status=='IR'}">
														
														${message.message}
														</c:if>
														<%-- <c:if test="${message.status=='WD'}">
														Document Uploaded <br>
														<a href="${message.message}"><i class="fa fa-paperclip"></i> ${message.documentFilename}</a>
														</c:if>
														<c:if test="${message.status=='WL'}">
														Link Added <br>
														<a href="${message.message}" target="_blank">${message.message}</a>
														</c:if> --%>
                                                       </p>
													</div>
					                         </c:if>
											</c:forEach>
                                    </div>
                                    
                                    <div class="typechat">
                                    	<textarea id="myTextarea" rows="" cols="" placeholder="Type Here.."></textarea>
                                    	<button type="submit" class="btnsend" onclick="sendButtonClicked()"><i class="fa fa-paper-plane"></i></button>
                                    </div>
                                 </div>
                              </div>
                           </div>
                        </div>
                        <div class="clearfix"></div>
                     </div>
                     
                      <%-- <h2 style="text-align:left; margin-left:0px;" class="fontsize35">2.Please choose from following button:</h2>
                     <div class="row">
                        <div class="col-md-6 col-sm-6">
                        <a href="${pageContext.request.contextPath}/admin/favorInFixer?queryId=${query.hashcode}" style="margin-top:15px" class="btn btn-info btn-lg  btn-block">Resolve in favour of fixer</a>
                           <!-- <button type="button" style="margin-top:15px" class="btn btn-info btn-lg  btn-block">Resolve in favour of fixer</button> -->
                        </div>
                        
                        <div class="col-md-6 col-sm-6">
                        <a href="${pageContext.request.contextPath}/admin/favorInMember?queryId=${query.hashcode}" style="margin-top:15px" class="btn btn-warning  btn-block">Resolve in favour of User</a>
                          <!--  <button type="button" style="margin-top:15px" class="btn btn-warning  btn-block">Resolve in favour of User</button> -->
                        </div>
                        <!-- Button trigger modal -->
                        
                     </div> --%>
                  </div>
               </div>
               <div class="col-md-1 col-sm-1"></div>
            </div>
         </div>
         	<input id="customerId" type="hidden" value=${query.user.userId}>
											<input id="fixerId" type="hidden" value=${query.fixerId}>
											<input id="queryId" type="hidden" value=${query.queryId} >
      </section>
      
      <script>
      	function sendButtonClicked(){
		var msg=document.getElementById( 'myTextarea' ).value;
		if(msg.length<=0){
			return;
		}
		$.ajax({
			method : "POST",
		    url : "${pageContext.request.contextPath}/member/sendMessageReview",
		    data : {
		    	customerId : document.getElementById( 'customerId' ).value,
		    	fixerId : document.getElementById( 'fixerId' ).value,
		    	queryId : document.getElementById( 'queryId' ).value,
		    	msgFrom : 'C',
		    	message : msg
		    }
		   }).done(function(response) {
			   window.location.reload();
			   
				
		   }); 
		   console.log("parshant");
		
	}
        </script>
   </body>
