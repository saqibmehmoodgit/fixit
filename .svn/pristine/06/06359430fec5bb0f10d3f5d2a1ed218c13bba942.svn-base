<%@ page contentType="text/html; charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<script type="text/javascript" src="../js/commonMethods.js"></script>
</head>

  <body>
  <script>
  $(document).ready(function(){
	    $('[data-toggle="tooltip"]').tooltip();   
	});
  

</script>
    
<!-- price -->
	
	<section class="member">
		<div class="container">
			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
				<div class="notimessagetxt ">
					<%-- <h1 class="animated fadeInDown"><span style="font-size:30px;    font-weight: 500;position:absolute;left:0; top:10px;">1. Issue Title:</span> ${queryTitle}</h1> --%>
					
				</div>
				</div>
				<div class="col-md-1 col-sm-1"></div>
			</div>
			<br>
			
			<div class="row">
				<div class="col-md-12 col-sm-12">
					
				</div>
			</div>
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
					<h2  class="fontsize35" style="text-align:left;">1. Chat : ${groupName}</h2>
			<%-- 	<h2  class="fontsize35" style="text-align:left;">1. Request Title: ${queryTitle}</h2>
				<span class="more">${queryContent}</span> --%>
				
				<br>
				
							
							<div class="graybg1">
								<div class="row">
									<div class="col-md-6 col-sm-6">
										<h2><span>1.</span>Please type your Message</h2>
										
										<div class="form-group">
											<textarea class="form-control solutionsbox solutionsbox1" placeholder="Type here.." id="myTextarea"></textarea>
											
<!-- 
											<button   class="upload uploadFile"><i id="uploadIcon" class="fa fa-paperclip"></i></button>
											<input id="documentUpload" class="inputFileUp"  onchange="uploadDocument()"  type="file"> -->
										<!-- 	<button style="top:42px;"   class="upload uploadFile" id="uploadFileIcon"><i id="uploadIcon" class="fa fa-paperclip"></i></button> -->
											<input id="documentUpload" style="top:42px;" class="inputFileUp"  onchange="uploadDocument()"  type="file">
											
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
									
					           
										
					           
										
										
									
									
									

									
									
									
									
									
									
									
									
									
									
									
										
										
									</div>
									<div class="col-md-6 col-sm-6">
										<div class="chatdiv">
											<h2 class="text-center">Previous Discussion or Chat</h2>
											<div class="chatbox">
											<div class="chat">
											<c:forEach var="message" items="${messagesSet}">
										
																		<c:choose>
                  											<c:when test="${message.user.userId==myUserId}">
                 											<div class="crow">
														<%-- <img src='' ${myImgIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';"> --%>
														<img src='${myImgIcon}' onerror="this.src ='../images/profile-pic.jpg'">
														 You  ${message.createdAt} <br>
														 	<p>
													${message.chatMessage}
												</div>
                 											
                 											 </c:when>
                 														 <c:otherwise>
                     <div class="crow1">
														<img src='${message.user.profileIcon}' onerror="this.src ='../images/profile-pic.jpg'">
													${message.user.userName}	${message.createdAt} <br>
														<p>
															${message.chatMessage}
															</p>
													</div>
                 														 </c:otherwise>
            															   </c:choose>
										
										
											
											<%-- <c:if test="${message.chatFrom==myUserId}">
												<div class="crow">
														<img src='${myUser.profileIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
														 You  ${message.auditTime} <br>
														 	<p>
													${message.message}
												</div>
					                         </c:if> --%>
					                         
					                      <%--    <c:if test="${message.chatFrom == 'F'}">
						                   
													<div class="crow1">
														<img src='${fixerImgIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
													${fixerName}	${message.auditTime} <br>
														<p>
															${message.message}
															</p>
													</div>
					                         </c:if> --%>
					                        
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
						<br>
						<!-- <div class="row">
							<div class="col-md-12 ">
								<h2 class="fontsize35">2. Do you Think Request is fixed?</h2>
							</div>
						</div> -->
						
						
						<div class="row">
   <!--       <div class="col-md-6 col-sm-6">
         <button type="button" style="margin-top:15px;width:180px; margin-right:20px;" data-toggle="modal" data-target="#myModal7" class="btn btn-info red-tooltip" data-toggle="tooltip"  data-placement="bottom" title="Click on this button if your request has been resolved."><i class="fa fa-check-circle-o"></i> Fixed</button>
         <button type="button" style="margin-top:15px;width:180px;" data-toggle="modal" data-target="#myModal99" class="btn btn-warning"><i class="fa fa-times-circle-o"></i> Not Fixed</button>
         </div> -->
         <!-- fixed message-->
         <!-- ratingpopup -->
                  						<!-- ratingpopup close-->
       
         <div class="col-md-6 col-sm-6">
          
         </div>
         <!-- Button trigger modal -->

         <!-- Modal -->
         
        </div>
							<br>
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
		var groupId= ${groupId};
		if(msg.length<=0){
			document.getElementById("sendMessageButton").disabled = false;
			document.getElementById("documentUpload").disabled = false;
			window.location.reload();
			return;
		}
		$.ajax({
			method : "POST",
		    url : "../admin/sendGroupMessage",
		    data : {
		    	message : document.getElementById( 'myTextarea' ).value,
		    	groupId : groupId
		    }
		   }).done(function(response) {
			   window.location.reload();
			   
				
		   }); 
		   console.log("parshant");
		/* document.getElementById("sendMessageButton").disabled = true;
		document.getElementById("documentUpload").disabled = true;
		var clientChat = new XMLHttpRequest();
		var file = document.getElementById("documentUpload");
		
		 var formData = new FormData();  
		    formData.append("file", file.files[0]);
		    var id=$('#queryId').val();
		    formData.append("queryId",id);
		    formData.append("virtualFileName",virtualFileName);
		    clientChat.open("post", "../member/uploadChatDocDetailpage", true);
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
			}
		     */

		
		
	}
	
	
	
	
	
	var client = new XMLHttpRequest();
	function uploadDocument()
	   {
		$("#uploadIcon").addClass('fa fa-spinner fa-spin');
	    var file = document.getElementById("documentUpload");
	    document.getElementById("myTextarea").disabled = true;
	    document.getElementById("sendMessageButton").disabled = true;
	    
	    / Create a FormData instance /
	    var formData = new FormData();  
	    formData.append("file", file.files[0]);
	    var id=$('#queryId').val();
	    formData.append("queryId",id);
	    client.open("post", "../member/uploadDocDetailpage", true);
	    client.send(formData);
		}
	client.onreadystatechange=function()
	{
	if (client.readyState==4 && client.status==200)
	  {
		
	var json=	client.responseText;
virtualFileName=json;
var json=	client.responseText;
virtualFileName=json;
//$("#uploadIcon").addClass('fa fa-paperclip');
$("#uploadIcon").removeClass('fa-spinner fa-spin');
document.getElementById("alertmsgshow").style.display = "block";
$('#docShow').text(virtualFileName);
document.getElementById("documentUpload").disabled = true;
document.getElementById("myTextarea").disabled = false;
document.getElementById("sendMessageButton").disabled = false;
		/* window.location.replace(url);
			window.location.reload(); */

	  }
	}


	
	
	
    jQuery(document).ready(function () {
    	    });

  
    
    function textEnteredInArea(){
    	var ele = document.getElementById('nofixedtextArea').value;
    	if(ele.length>0){
    	$("#nofixedtextAreaButton").prop("disabled",false);
    	}else{
    		$("#nofixedtextAreaButton").prop("disabled",true);
    	}
    }
    
    function deleteAttachment(){
    	document.getElementById("documentUpload").disabled = false;
    	document.getElementById("alertmsgshow").style.display = "none";
    	document.getElementById("documentUpload").value='';
    	var queryId=$('#queryId').val();;
    	$.ajax({
    		method : "POST",
    	    url : "../member/deleteFileChatEdit",
    	    data : {
    	    	fileName : virtualFileName,
    	    	queryId : queryId
    	    }
    	   }).done(function(response) {
    		
    	   }); 
    }
	</script>
	
  </body>
