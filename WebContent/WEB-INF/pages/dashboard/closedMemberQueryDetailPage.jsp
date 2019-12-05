<%@ page contentType="text/html; charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<script type="text/javascript" src="../js/commonMethods.js"></script>
</head>

  <body>
   <script>
 
</script>
<!-- price -->
	
	<section class="member">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<c:if test="${Conflict=='Yes'}">
						<h1 class="animated fadeInDown">This issue is currently in review</h1>
			    </c:if>
					<c:if test="${Conflict=='No'}">
						<h1 class="animated fadeInDown">Closed Request</h1>
			    </c:if>
						<c:if test="${Conflict=='NotFixed'}">
						<h1 class="animated fadeInDown">Not Fixed</h1>
			    </c:if>
				</div>
			</div>
			<br>
			
			<div class="row">
			<div class="col-md-3 col-sm-3"></div>
				<div class="col-md-6 col-sm-6">
				<div class="notimessagetxt">
					<h2  class="fontsize35">1.Query Title: ${query.queryTitle}</h2>
					<span class="more">${queryContent}</span>
				   <br>
				</div>
				</div>
				<div class="col-md-3 col-sm-3"></div>
			</div>
			
				<div class="row">
				<div class="col-md-3 col-sm-3"></div>
				<div class="col-md-6 col-sm-6">
					<div class="notimessagetxt">
							
							<div class="graybg1" style=" padding: 25px 25px 25px 7px;">
								<div class="row">
									
									<div class="col-md-12 col-sm-12">
										<div class="chatdiv">
											
											<div class="chatbox">
											<div class="chat">
											<c:forEach var="message" items="${messagesSet}">
											<input id="customerId" type="hidden" value=${message.customerId}>
											<input id="fixerId" type="hidden" value=${message.fixerId}>
											<input id="queryId" type="hidden" value=${queryId} >
											
											<c:if test="${message.msgFrom == 'C'}">
											
						                   
													<div class="crow">
														<img src='${myUser.profileIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
														 You  ${message.auditTime} <br>
														 <p>
														<c:if test="${message.status=='W'}">
															${message.message}
														<!-- <script>document.write(replaceURLWithHTMLLinks('${message.message}'))</script> -->
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
					                         
					                         <c:if test="${message.msgFrom == 'F'}">
						                   
													<div class="crow1">
														<img src='${fixerImgIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
													${fixerName}	${message.auditTime} <br>
														<p>
														<c:if test="${message.status=='W'}">
															${message.message}
														<!-- <script>document.write(replaceURLWithHTMLLinks('${message.message}'))</script> -->
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
					                        
											</c:forEach>
											 </div>
											<!-- <div class="chatbox">
												<div class="chat">
													<div class="crow">
														<img src="../images/member03.jpg">
														20/08/2015, 1PM
														<p>"So when did a first appear?"
														<span><i class="fa fa-paperclip"></i> arrow.png</span>
														</p>
													</div>
													<div class="crow1">
														<img src="../images/member01.jpg">
														20/08/2015, 3PM
														<p>"Get amazing results working with the best programmers, designers, writers and other top online pros. Hire freelancers with confidence, "
														<span><i class="fa fa-paperclip"></i> arrow.png</span>
														</p>
													</div>
													<div class="crow">
														<img src="../images/member03.jpg">
														20/08/2015, 1PM
														<p>"So when did a first appear?"</p>
													</div>
													<div class="crow1">
														<img src="../images/member01.jpg">
														20/08/2015, 3PM
														<p>"I think it is once I login."</p>
													</div>
													<div class="crow">
														<img src="../images/member03.jpg">
														20/08/2015, 1PM
														<p>"So when did a first appear?"</p>
													</div>
													<div class="crow1">
														<img src="../images/member01.jpg">
														20/08/2015, 3PM
														<p>"I think it is once I login."
															<span><i class="fa fa-paperclip"></i> arrow.png</span>
														</p>
													</div>
												</div>
												<div class="texttype">
													<textarea class="messagebox" placeholder="Type here.."></textarea>
												</div>
											</div> -->
									<!-- 	<div class="texttype">
													<textarea class="messagebox" placeholder="Type here.." id="myTextarea"></textarea>
												</div> -->
										
										</div>
										<div class="row">
											<!-- <div class="col-md-6 col-xs-12">
												<a href="#" style="margin-top: 9px !important;display: block;">View Member Profile</a>
											</div> -->
											<!-- <div class="col-md-2 col-xs-5">
												<div class="upload" style="padding:2px 11px;font-size: 23px; margin-top:0px;"><i class="fa fa-paperclip"></i></div>
												<input style="position:relative;margin: -46px 0 0 0;height: 48px;opacity:0;" type="file">
											</div>	 -->
											<!-- <div class="col-md-4 col-xs-7">	
												<button type="button" style="padding:4px;font-size:15px; margin-top:5px;" class="btn btn-primary btn-sm btn-block" onclick="sendButtonClicked()">Send</button>
											</div> -->
										</div>
									</div>
								</div>
								
							</div>
							
						</div>
					</div>
				<div class="col-md-1 col-sm-1"></div>
			</div>
			</div>								
	</section>
	<!-- price close -->
	<script>
	  $(document).ready(function(){ 
		  <c:if test="${not empty RateIt}">
		  $('#ratingpopup').modal({
      		show: 'true'
 		 }); 
		</c:if>
		  
			 });
	
	function sendButtonClicked(){
		/* var messages= <c:out default="[]" escapeXml="false" value="${not empty messagesSet?messagesSet:'[]'}"/>
		if(messages.length >0){
		}
		*/
		$.ajax({
			method : "POST",
		    url : "../member/sendMessage",
		    data : {
		    	customerId : document.getElementById( 'customerId' ).value,
		    	fixerId : document.getElementById( 'fixerId' ).value,
		    	queryId : document.getElementById( 'queryId' ).value,
		    	msgFrom : 'C',
		    	message : document.getElementById( 'myTextarea' ).value
		    }
		   }).done(function(response) {
			   window.location.reload();
			   
				
		   }); 
		   console.log("parshant");
		
	}
	
	 function ratePopUpButtonCLicked(){
	    	/* document.Rate-form.submit(); */
	    	 $( "#rateForm" ).submit();
	    }
	</script>
	 <!-- ratingpopup -->
          
						<div class="modal fade" id="ratingpopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                           <div class="vertical-alignment-helper">
                              <div class="modal-dialog vertical-align-center">
                                 <div class="modal-content">
                                    <div class="modal-header">
                                       <button type="button" class="close" data-dismiss="modal" style="font-size:40px;">&times;</button>
                                       <h2 class="modal-title">Rating</h2>
                                    </div>
                                    <div class="modal-body">
									<form  role="form" class="contact-form" id="rateForm"  
           																				 method="post"
           																					 action="../member/rateClosed"  >
           																					 
           								<input  type="hidden"  value="${queryId}" name="queryId" >
                                       <h2 class="text-center  text-success" style="font-size:35px !important; margin-bottom:25px;">
											<input id="input-21d" value="0" type="number" class="rating" min=0 max=5  data-size="sm" name="starPoints">
									   </h2>
                                       <div class="row">
                                          <div class="col-md-3"></div>
                                          <div class="col-md-6">
                                             <button type="button" onclick="ratePopUpButtonCLicked()" data-toggle="modal" data-target="#ratingpopup"class="btn btn-success btn-block btnmd">Rate</button>
                                          </div>
                                         <%--  <div class="col-md-3">
                                             <a type="button" href="${pageContext.request.contextPath}/member/rateLater?queryId=${queryId}" data-dismiss="modal" class="btn  btn-danger btn-block btnmd">Rate Later</a>
                                          </div> --%>
                                          <div class="col-md-3"></div>
                                       </div>
                                       <br>
									 </form>
                                    </div>
                                 </div>
                              </div>
                           </div>
                        </div>
                        
                        						<!-- ratingpopup close-->
         
	
  </body>
