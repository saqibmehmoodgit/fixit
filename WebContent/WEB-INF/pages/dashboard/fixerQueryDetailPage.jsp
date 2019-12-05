<%@ page contentType="text/html; charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<% pageContext.setAttribute("singleQuoteChar", ""); %>
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
					<%-- <h1 class="animated fadeInDown">${queryTitle}</h1> --%>
					
				</div>
			</div>
			<br>
			
			<div class="row">
				<div class="col-md-9 col-sm-9"></div>
				<div class="col-md-3 col-sm-3">
						<p style="font-size:25px; color:#f00; font-weight:bold;">Screen sharing tools:</p>
						<a href="https://www.teamviewer.com/en/download" target="blank" class="pull-right"><img src="../images/tvIcon.png" width="150"></a>
						<a href="https://join.me/" target="blank"  class="pull-right"><img src="../images/jmLogo.png" width="70">
									 </a>
							
				</div>
			</div>
			
				<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
					<div class="notimessagetxt">
							<h2 class="fontsize35" style="text-align:left;">1. Request Title: ${queryTitle}</h2>
							
							<span class="more">${queryContent}</span>
				             <br>
							<div class="graybg1">
								<div class="row">
									<div class="col-md-6 col-sm-6">
										<h2><span>1.</span> What is your Solution/Message?</h2>
										<p>Write an answer to the member request and how to resolve it.</p>
										<div class="form-group">
											<textarea class="form-control solutionsbox solutionsbox1" placeholder="Type here.." id="myTextarea"></textarea>
											

											<button style="top:60px;"   class="upload uploadFile" id="uploadFileIcon"><i id="uploadIcon" class="fa fa-paperclip"></i></button>
											<input id="documentUpload" style="top:60px;" class="inputFileUp"  onchange="uploadDocument()"  type="file">
											
										</div>
										<div class="row">
											 <div class="col-md-6 alert alert-info text-success2 " id="alertmsgshow" style=" display: none; ">
										 		<p  id="docShow"  ></p>
										 		<i class="fa fa-times" style=" float:right;" type="button" onclick="deleteAttachment()"></i>
											 </div>
										 </div>
										<div class="row">
											<div class="col-md-6">
												<button type="button" class="btn btn-success  btn-block"  style="padding: 5px 40px;font-size: 23px; margin-top:10px;" onclick="sendButtonClicked()" id="sendMessageButton">Send Message</button>
											</div>
										</div>
									
										<!-- <h2><span>2.</span> Have an Attachment?</h2> -->
										<!-- <p>Upload a file to share with the fixer, 5MB limit.</p> -->
										<!-- <button class="btn btn-primary upload" style="padding: 5px 40px;font-size: 23px; margin-top:10px;">Upload</button>
										<input id="documentUpload"  onchange="uploadDocument()" style="position:relative;margin: -46px 0 0 0;height: 48px;opacity:0;" type="file">
										<br>
											<div class="col-md-5 col-sm-5"  id="documentLoader"  >
					            <i  class="fa fa-spinner fa-spin" style="font-size: 30px;margin: 12px 0 0 0;"></i>
					           </div> -->
										
										
										
										
										
									<!-- 	<div class="row">
											<div class="col-md-6">
												<button class="btn btn-primary upload" style="padding: 5px 40px;font-size: 23px; margin-top:10px;">Upload</button>
										<input id="documentUpload"  onchange="uploadDocument()" style="position:relative;margin: -46px 0 0 0;height: 48px;opacity:0;" type="file">
											</div>
											<div class="col-md-6" id="documentLoader">
												<i  class="fa fa-spinner fa-spin" style="font-size: 30px;margin: 12px 0 0 0;"></i>
											</div>
										</div> -->
										
										
										
										
										
										
										
										<!-- <div class="row">
											<div class="col-md-6">
												<button type="button" class="btn btn-danger  btn-block"  style="padding: 5px 40px;font-size: 23px; margin-top:10px;">Quit Ticket</button>
											</div>
											<div class="col-md-12">
												<button type="button" class="btn btn-success  btn-block"  style="padding: 5px 40px;font-size: 23px; margin-top:10px;" onclick="sendButtonClicked()">Send Message</button>
											</div>
										</div> -->
										
										
										
									</div>
									<div class="col-md-6 col-sm-6">
										<div class="chatdiv">
											<h2 class="text-center">Previous Discussion or Chat</h2>
											<div class="chatbox">
											<div class="chat">
											<c:forEach var="message" items="${messagesSet}">
											<input id="customerId" type="hidden" value=${message.customerId}>
											<input id="fixerId" type="hidden" value=${message.fixerId}>
											<input id="queryId" type="hidden" value=${queryId} >
											
											<c:if test="${message.msgFrom == 'F'}">
											
						                   
													<div class="crow">
														<img src='${myUser.profileIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
													         You  ${message.auditTime} <br>
													         <p>
															<c:if test="${message.status=='W'}">
																${message.message}
													<!-- 	<script type="text/javascript">										
														var newLineCharRemoved=${fn:replace(message.message, newLineChar, "")};
														
														var singleQuoteCharRemoved=${fn:replace(newLineCharRemoved, singleQuoteChar, " ")};
														document.write(replaceURLWithHTMLLinks(singleQuoteCharRemoved));</script> -->
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
						                   
													<div class="crow1">
														<img src='${memberImgIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
													 ${memberName}	${message.auditTime} <br>
													 <p>
														<c:if test="${message.status=='W'}">
															${message.message}
													<!-- 	<script type="text/javascript">
														
														document.write(replaceURLWithHTMLLinks( '${fn:replace(message.message, newLineChar, " ")}'));</script> -->
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
										<!-- <div class="texttype">
													<textarea class="messagebox" placeholder="Type here.." id="myTextarea"></textarea>
												</div> -->
										
										</div>
										<!-- <div class="row">
											<div class="col-md-6 col-xs-12">
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
	var virtualFileName='';	
	function sendButtonClicked(){
		var msg=document.getElementById( 'myTextarea' ).value;
		document.getElementById("sendMessageButton").disabled = true;
		document.getElementById("documentUpload").disabled = true;
		var clientChat = new XMLHttpRequest();
		var file = document.getElementById("documentUpload");
		 var formData = new FormData();  
		    formData.append("file", file.files[0]);
		    var id=$('#queryId').val();
		    formData.append("queryId",id);
		    formData.append("virtualFileName",virtualFileName);
		    clientChat.open("post", "../fixer/uploadChatDocDetailpage", true);
		    clientChat.send(formData);
		    clientChat.onreadystatechange=function()
			{
			if (clientChat.readyState==4 && clientChat.status==200)
			  {
		if(msg.length<=0){
			document.getElementById("sendMessageButton").disabled = false;
			document.getElementById("documentUpload").disabled = false;
			   window.location.reload();
			return;
		}
		$.ajax({
			method : "POST",
		    url : "../fixer/sendMessage",
		    data : {
		    	customerId : document.getElementById( 'customerId' ).value,
		    	fixerId : document.getElementById( 'fixerId' ).value,
		    	queryId : document.getElementById( 'queryId' ).value,
		    	msgFrom : 'F',
		    	message : msg
		    }
		   }).done(function(response) {
			   window.location.reload();
			   
				
		   }); 
		   console.log("parshant");
			  }
			}
		
	}
	
	var client = new XMLHttpRequest();
	function uploadDocument()
	   {
		/* if($('#documentLoader').is(':visible')) {
			alert("Already inProgress");
		    return;
		} */
		document.getElementById("documentUpload").disabled = true;
		$("#uploadIcon").addClass('fa-spinner fa-spin');
		/* $("#documentLoader").show(); */
	    var file = document.getElementById("documentUpload");
	    document.getElementById("myTextarea").disabled = true;
	    document.getElementById("sendMessageButton").disabled = true;
	    / Create a FormData instance /
	    var formData = new FormData();  
	    formData.append("file", file.files[0]);
	    var id=$('#queryId').val();
	    formData.append("queryId",id);
	    client.open("post", "../fixer/uploadDocDetailpage", true);
	    client.send(formData);
		}
	client.onreadystatechange=function()
	{
	if (client.readyState==4 && client.status==200)
	  {
		var json=	client.responseText;
		virtualFileName=json;
		//$("#uploadIcon").addClass('fa fa-paperclip');
		$("#uploadIcon").removeClass('fa-spinner fa-spin');
		document.getElementById("alertmsgshow").style.display = "block";
		$('#docShow').text(virtualFileName);
		document.getElementById("documentUpload").disabled = true;
		document.getElementById("myTextarea").disabled = false;
		document.getElementById("sendMessageButton").disabled = false;
	  }
	}

	function replaceURLWithHTMLLinks(text)
    {
      var exp = /(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig;
      return text.replace(exp,"<a  href='$1'  >$1</a>"); 
    }
function deleteAttachment(){
	document.getElementById("documentUpload").disabled = false;
	document.getElementById("alertmsgshow").style.display = "none";
	document.getElementById("documentUpload").value='';
	var queryId=$('#queryId').val();;
	$.ajax({
		method : "POST",
	    url : "../fixer/deleteFileEdit",
	    data : {
	    	fileName : virtualFileName,
	    	queryId : queryId
	    }
	   }).done(function(response) {
		
	   }); 
}
	
	</script>
  </body>
