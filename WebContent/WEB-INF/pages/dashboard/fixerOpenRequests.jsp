
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ERPfixers | Requests</title>
<script>


//<![CDATA[
$(window).load(function(){
// auto adjust the height of
$('.textauto').on( 'keydown', 'textarea', function (e){
    $(this).css('height', 'auto' );
    $(this).height( this.scrollHeight );
});
$('.textauto').find( 'textarea' ).keydown();
});//]]> 
</script>
<script>
	  var client = new XMLHttpRequest();
      var req;
	  var qId='';
	  var fixerName = '';
	  var fixerId = ${myUser.userId};
	  var virtualFileName='';
	  var queryHashCodeId = '';
      var customerId = ''; 
	  
      var chatHMax = 0;
      var chatH = 0;
      var chatHArr = [];
      var senchatScroll = false;
      chatHArr.push(0);
	  
	  $(document).ready(function(){
		  
		  if($('.fixerProfileImage').attr('src')==''){

				$('.fixerProfileImage').attr('src','../images/profile-pic.jpg');
				  
				}
		  if($('.CuntryFlagMember').attr('src')==''){

				$('.CuntryFlagMember').attr('src','../images/profile-pic.jpg');
				  
				}
		  
			$('.message').bind('click',function(){
				
				$('#chatUserName').text('');
				var id = this.id;
				var name  = $('#'+ id).text().split('Message')[1].trim();
				name =  name.substr(0,1).toUpperCase() + name.substr(1)
				$('#chatUserName').text('Message to ' + name); 
				
				
				
				$(".messageChat").fadeIn("slow");
				 var objDiv = document.getElementById("chatbgBody");
				    objDiv.scrollTop = objDiv.scrollHeight;	
				
			
			});
			$(".closeMessage").click(function(){
				$(".messageChat").fadeOut("slow");
				senchatScroll = true;
			});
			
			$('a').removeClass('active');

			$("#newRequest").addClass('active');
			
			setInterval(checkVisibility, 10000);
			
			$('#chatbgBody').on('scroll',function(){
		    	 var h =  $('#chatbgBody').scrollTop();
		    	 chatH = h;
		    	 console.log(chatH)
		    	 chatHArr.push(h);
		    	  
		      });
		});
	  
	  function checkVisibility(){
			
			var msg = $("#chatTextArea").val();
			if($('.messageChat').is(':visible')){
				if(msg == null || msg.trim()==''){
					loadGroupMessage();
				}
			}
		}
	  function loadGroupMessage() {
			var msg = $("#chatTextArea").val();
			
			if(msg == null || msg.trim()==''){
			req = $.ajax({
				method : "POST",
				url : "../fixer/memberLoadGroupMessage",
				data : {
					
					memberId: customerId,
			    	 queryId: queryHashCodeId
				}
			}).done(function(response) {
				var successResponse = response.status;
				if (successResponse == 'success') {
					var messageSet = response.result.messagesSet;
					setchatMessages(messageSet);
				}
			});
			}else{
				req.abort();
			}
			$('#chatTextArea').on('keydown',function(){
				req.abort();
			});
		}
	  function uploadBtnClick(){
			$('#chatload').css('display','block');
		    $('.closeMessage').css("pointer-events", "none");

		  uploadDocument(queryHashCodeId);
	  }
	  
		function sendBtnClick(){
	    	
			var chatTextArea = $('#chatTextArea').val();
			
		
			
			if(chatTextArea != null && chatTextArea.trim() !=''){
		    	sendChatmessage(queryHashCodeId,customerId, fixerId, chatTextArea);
			}
			
			$('#chatTextArea').val('');
			
			chatTextArea = '';
	
}
	  function messageBtnClick(name, index , queryId)
		{
			$('#chatUserName').text('');
			$('#chatUserName').text(name);
			$(".messageChat").fadeIn("slow");
			
			  $.ajax({
					method : "POST",
				    url : "${pageContext.request.contextPath}/fixer/updateMessageCounts",
				    data : {
				    fixerId: 0,
				    queryId: queryId
				     }
				   }).done(function(response) {
					   var data = response.status;
					   if(data=='success'){
						  
						   $("#chatMsg"+index).text("0");
						   $("#chatMsg"+index).hide();
					  
					   }
				   });  
		}
	  
		 function uploadDocument(queryHashCode)
	  {
			//$("#uploadIcon").addClass('fa fa-spinner fa-spin');
		    var file = document.getElementById("documentUpload");
		  document.getElementById("chatTextArea").disabled = true;
		    //document.getElementById("sendMessageButton").disabled = true;
		    		    $('#documentUpload').attr('disabled', 'true');

		    / Create a FormData instance /
		    var formData = new FormData();  
		    formData.append("file", file.files[0]);
		  //  var id=$('#queryId').val();
		    formData.append("queryId",queryHashCode);
		    client.open("post", "../fixer/uploadDocDetailpage", true);
		    client.send(formData);
			}
		client.onreadystatechange=function()
		{
			
		if (client.readyState==4 && client.status==200)
		  {
			
		var json=	client.responseText;
	    var str = "DOCTYPE html";
		if(json.indexOf(str) != -1){
			var file = document.getElementById("documentUpload");
			
			virtualFileName = file.files[0].name;
			
		}else{
		
		virtualFileName=json;
		}
	    if(virtualFileName != null && virtualFileName !=''){
	    	
	    	saveDoc();
	    	
	    }
	   
		  }
		}
		
		function saveDoc(){
		    $('.closeMessage').css("pointer-events", "none");

			$('#chatload').css('display','block');
			var clientChat = new XMLHttpRequest();
			var file = document.getElementById("documentUpload");
			var fileName = '';
			 var formData = new FormData();  
			    formData.append("file", file.files[0]);
			   // var id=$('#queryId').val();
			    formData.append("queryId",queryHashCodeId);
			    formData.append("fixerId",fixerId);

			    fileName = virtualFileName;
			    formData.append("virtualFileName",virtualFileName);

				
			    virtualFileName = '';
			    clientChat.open("post", "../fixer/uploadChatDocDetailpage", true);
			    clientChat.send(formData);
			    clientChat.onreadystatechange=function()
				{
			    	
				if (clientChat.readyState==4 && clientChat.status==200)
				  {
					var json=	clientChat.responseText;

				    var str = "<!DOCTYPE html PUBLIC";
					 var fileUrl = '#';
						if(json.indexOf(str) != -1){
							fileUrl = json.split('<!-- Header end-->')[1].split('</body>')[0];
							
						}else{
							fileUrl = json ; 
						
						}
					
				   var htmlString =  '<span><a href="'+fileUrl+'" class="Rfileattachment"> '+fileName+'</a><span>';
					$('.chatbgBody').append(htmlString);
					 
					   var objDiv = document.getElementById("chatbgBody");
					    objDiv.scrollTop = objDiv.scrollHeight;	
					    $('#chatload').css('display','none');
					    $('.closeMessage').css("pointer-events", "auto");

				    fileName = '';		
		
				}
				$('#documentUpload').val("");
  
				$('#chatload').css('display','none');
				  document.getElementById("chatTextArea").disabled = false;
				  $('#documentUpload').removeAttr('disabled');

				    $('.closeMessage').css("pointer-events", "auto");

				}
			    
			  
		}
		function sendChatmessage(queryHashCode,customerId, fixerId, chatFromTextArea){
			$('#chatload').css('display','block');
			  $('.closeMessage').css("pointer-events", "none");
			  senchatScroll = true;
		
		//	document.getElementById("sendMessageButton").disabled = true;
		//	document.getElementById("documentUpload").disabled = true;
			
					
					$.ajax({
						method : "POST",
					    url : "../fixer/sendMessage",
					    data : {
					    	customerId : customerId,					    
					    	fixerId : fixerId,
					    	queryId : queryHashCode,
					    	msgFrom : 'F',
					    	message : chatFromTextArea
					    }
					   }).done(function(response) {
						   
						   
						   var successResponse = response.status;
						   
						   if(successResponse == 'success'){
							   
							 var messageSet = response.result.messagesSet;
						   setchatMessages(messageSet);
						   }
							$('#chatload').css('display','none');
							  $('.closeMessage').css("pointer-events", "auto");

					   }); 

				  }
	  
	  
		/* show less  */
		function moreBtnclick(id,queryId,totalcount,memberId,hashcode){
			qId = hashcode;
			senchatScroll = true;
			  queryHashCodeId = hashcode;
		     customerId = memberId; 
		     $('.requestsbg').css('opacity','0.5');
	            $('#requestsbg'+id).css('opacity','1');
				$('.chatbgBody').empty();   
				$('.CloseDetail').css('bottom','55px');
				$('#titleContent'+id).attr("class", "QOverviewOpen");

			
			for(var i=0;i<totalcount;i++)
				{
				 $("#Expand-Collaps"+ i).css({"height":"133px","overflow":"hidden"});
				    $("#CloseDetail"+ i).hide();
				    $("#MoreDetail"+ i).show();
					$(".clientConver").hide();
					  $("#working_member"+i).hide();
					  $("#closeDate"+i).hide();
					  $("#account"+i).hide();
					$(".duration").css({"bottom":"20px"});
				
				}
			  $("#Expand-Collaps"+ id).css({"height":"auto","overflow":"visible"});
			    $("#CloseDetail"+ id).show();
			  $("#working_member"+id).show();
			    $("#MoreDetail"+ id).hide();
			    $("#clientConver").show();
			    $("#account"+id).show();
			    $("#closeDate"+id).show();
			    $(".duration").css({"bottom":"55px"});
			 /* 
			    $("#chatsend").bind('click', function(){
			    	
					var chatTextArea = $('#chatTextArea').val();
					if(chatTextArea != null && chatTextArea.trim() !=''){
				    	sendChatmessage(hashcode,customerId, fixerId, chatTextArea);
					}
					$('#chatTextArea').val('');
					chatTextArea = '';
				}); */
			    
			   /*  $("#chatTextArea").keyup(function(event){
			    	var chatFromTextArea = $("#chatTextArea").val();
			    	
			    	
				    if(event.keyCode == 13){
				    	
				    	sendChatmessage(hashcode,customerId, fixerId, chatFromTextArea);
				    	
				    }
				});	 */	    
				/* $('#documentUpload').bind('change', function(){
			
					uploadDocument(hashcode);
			
					});	  */
				
			   
			   $.ajax({
					method : "POST",
				    url : "${pageContext.request.contextPath}/fixer/findMemberDetails",
				    data : {
				    	memberId: memberId,
				    	 queryId: hashcode

				     }
				   }).done(function(response) {
					   var data = response.result.status;
					   if(data=='success'){
						   var messageSet =  response.result.messagesSet;
						   if(messageSet != undefined && messageSet != '' ){
							$('.chatbgBody').empty();  
							
							   setchatMessages(messageSet);
							   
						   }   
						  
					  
					   }
				   }); 
			  
			   
			   var objDiv = document.getElementById("chatbgBody");
			    objDiv.scrollTop = objDiv.scrollHeight;	
		}
		
		function setchatMessages(messageSet){
		$('.chatbgBody').empty();
		var message = messageSet;
		var len = message.length;
		var finalHtml = '';
		var htmlchatTxtR = '<div class="chatTxtR">'
		var span = '<span>'
		var divClearFix = '</span></div><div class="clearfix"></div>';
        var htmlChatTxt =  '<div class="chatTxt">';
      
      for(var i=0; i < len ; i++){
    	  if (message[i].msgFrom == 'F'){
    		  
    		  if(message[i].status=='W'){
    		  finalHtml += htmlchatTxtR + message[i].message + span + message[i].auditTime + divClearFix;
    		  }
    		  if(message[i].status=='WD'){
    		  
    		  finalHtml += span + '<a href="'+message[i].message+'" class="Rfileattachment"> '+message[i].documentFilename+'</a>' + span;
    		  }
    		  if(message[i].status=='WL'){
    		finalHtml += span+   '<a href="'+message[i].message+'" target="_blank"> '+message[i].message+'</a>' + span;
    			  
    		  }
    		  }else{
    			  if(message[i].status=='W'){
    		  finalHtml+= htmlChatTxt + message[i].message + span + message[i].auditTime + divClearFix;
    			  }
    		  if(message[i].status=='WD'){
        		  
        		  finalHtml += span + '<a class="fileattachment" href="'+message[i].message+'">'+message[i].documentFilename+'</a>' + span;
        		  }
        		  if(message[i].status=='WL'){
        			  finalHtml += span+   '<a href="'+message[i].message+'" target="_blank"> '+message[i].message+'</a>' + span;
        			  
        		  }
    	  }
    	  $('.chatbgBody').append(finalHtml);
    	  finalHtml = '';
    	  $('#chatTextArea').val('');
    	  
         	}
      console.log('chatHArr::'+ chatHArr);
      chatHMax =   Math.max.apply(Math, chatHArr);
      console.log('chatHMax::'+ chatHMax);
    	if((chatH == chatHMax) || senchatScroll){
	    var objDiv = document.getElementById("chatbgBody");
	    objDiv.scrollTop = objDiv.scrollHeight; 
	    senchatScroll = false;
 }	
	}	
			
			function closeBtnclick(id){
				
				$('#titleContent'+id).attr("class", "QOverviewClose");
				  $('.requestsbg').css('opacity','1');
			    $("#Expand-Collaps"+ id).css({"height":"133px","overflow":"hidden"});
			    $("#CloseDetail"+ id).hide();
			    $("#MoreDetail"+ id).show();
				$(".clientConver").hide();
				  $("#closeDate"+id).hide();
				$(".duration").css({"bottom":"20px"});
				$("#account"+id).hide();
			
				  $("#working_member"+id).css('display','none');
				  
			}
			
			function denyRequest(queryId)
			{
				$("[onclick]").removeAttr("onclick"); 

				window.location.replace("${pageContext.request.contextPath}/fixer/decline?queryId="+queryId);
				
			}
			
			function applyRequest(queryId)
			{
				
				//get query current status
					$.ajax({
							method : "POST",
						    url : "../fixer/getReqStatus",
						    data : {
						    	queryId:queryId
						    	}
						   }).done(function(response) {
							   
							   if(response.status=='success')
								   {
									   if(response.result.queryData.currentStatus=='O')
										{
											$("[onclick]").removeAttr("onclick"); 
	
											window.location.replace("${pageContext.request.contextPath}/fixer/apply?queryId="+queryId);
										}
									else
										{
										//alert("Request is on Hold");
										jQuery('#reqStatusMsg').text("This Request is on hold, as the Member is reviewing solutions.");
						 				jQuery('#popup').fadeIn(1000);
						 				jQuery('body').addClass('overlay');
						 				var popuph = document.getElementById('popup').offsetHeight;	
						 				var inpopuph = document.getElementById('inpopup').offsetHeight;
						 				if(popuph>500){					
						 					jQuery('#inpopup').css('margin',+(popuph-inpopuph)/2+'px auto');
						 				}
						 				jQuery('.closepopup').click(function(){
						 					jQuery('#popup').fadeOut(1000);
						 					jQuery('body').removeClass('overlay');
						 				});
										}
								   }
							  
						});
    
				 
				
			}
			
			function detailFixer(){
		        $("#clientB").slideDown();
		        $("#detailFixer").hide();
		        $("#closeFixer").show();
		    }
			function closeFixer(){
		        $("#clientB").slideUp();
		        $("#detailFixer").show();
		        $("#closeFixer").hide();
		    }
			
			
			
			
			
			
		
	  </script>



<script type="text/javascript">//<![CDATA[
		function preloader(){document.getElementById("loader").style.display="none";document.getElementById("pagewrap").style.display="block";}
		window.onload=preloader;
		//]]>
		</script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script type="text/javascript" src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<div id="pagewrap">
		<!-- Work Here start-->
		<section class="wrapper">
			<div class="container-fluid">
				<div class="requestsBox">
					<c:if test="${queryCounts == 0}">
						<div class="nodatamsg">There are currently no Open requests.
						</div>
					</c:if>
					<div class="row">
						<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
							<c:if test="${msgType == 'success'}">
								<div style="margin-top: 18px; margin-right: 15px;"
									class="alert alert-success fade in">
									<a title="close" aria-label="close" data-dismiss="alert"
										class="close" href="#">�</a> ${message}
								</div>
							</c:if>
							<div class="">
								<c:forEach var="queryList" items="${queryList}"
									varStatus="thequeryList">
									<div   id="requestsbg${thequeryList.index}" class="requestsbg">
									
										<c:if test="${queryList.unreadMessageCount != 0}">
											<span class="chatmsgnumber" id="chatMsg${thequeryList.index}">${queryList.unreadMessageCount}</span>
										</c:if>
										<div class="Expand-Collaps"
											id="Expand-Collaps${thequeryList.index}">
											<div class="time">
												<h1>${queryList.queryCredits}</h1>
												<span>
												<c:choose>
												<c:when test="${queryList.queryCredits eq 1}">
												HOUR
												</c:when>
												<c:otherwise>
												HOURS
												</c:otherwise>
												</c:choose>
												
												
												</span>
											</div>
											<div class="more-less">
												<div class="more-block">
													<div id="titleHead${thequeryList.index}" class="titleHeaddiv">${queryList.queryTitle}<div class="half"></div></div>


													<div class="textauto" id="textauto${thequeryList.index}">
														<p name="queryContent"
															id="titleContent${thequeryList.index}"
															class="QOverviewClose" >${queryList.queryContent}</p>
													</div>
													<%-- <textarea name = "queryContent" rows="" id="titleContent${thequeryList.index}"
													 class="titleContent" readonly>${queryList.queryContent}</textarea> --%>

													<input type="hidden" name="queryId"
														value="${queryList.queryId}"
														id="queryId${thequeryList.index}">
													<h2>Categories</h2>
													<div class="categoriesBox">

														<c:forEach var="catList" items="${queryList.subCategory}"
															varStatus="thecatList">
															<div class="categoriesBox">
																<span class="categoriesBtn"><input
																	type="checkbox" checked="checked" value="35" id="35"
																	name="categories">${catList} <i
																	id="catListCross${thequeryList.index}_${thecatList.index}"
																	data-icon="y" class="icon"
																	onClick="crossCategories(${thequeryList.index}_${thecatList.index})"
																	style="display: none"></i></span>
															</div>
														</c:forEach>
													</div>
													<div class="clearfix"></div>
													<h2>Attachment(s)</h2>
													
													<div id="attach${queryList.queryId}">
														<c:forEach var="attachList"
															items="${queryList.attachedDocuments}"
															varStatus="theattachList">
														
															<div class="download" id="${attachList.fileUniqueCode}">
																<a href="${attachList.fileUrl}"> <span>Download:</span>
																	<input type="text" name="documents" style="cursor:pointer"
																	value="${attachList.fileName}(315KB)" readonly>

																</a>

															</div>
														</c:forEach>
													</div>

													<div class="reqProfileBox"
														id="working_member${thequeryList.index}">
														
														
														<c:if test="${not empty queryList.queryDeadlineDate}">
						
							
							
										<div style="margin: -30px 0px 50px; display: block;" id="closeDate${thequeryList.index}" class="grayInfo">
										<div>
										<span class="dueTime accept100">
										<span class="value"><strong>${queryList.queryValue} </strong>VALUE </span> 
										<span class="due"><strong>DUE </strong>${queryList.queryDeadlineDate} Hours</span></span>
										</div>
										</div>
												
							
														</c:if>
														
														
														
													<div class="profileBox">
															<div class="leftImg">
																<img id="fixerProfileImage${thequeryList.index}" class="fixerProfileImage"
																	src="${queryList.member.profilePhoto}"
																	onerror="this.src ='${pageContext.request.contextPath}/images/profile.png'">
																<img id="fixerFlagImage${thequeryList.index}"
																	class="CuntryFlagMember" src="${pageContext.request.contextPath}/flags/${queryList.member.country}.png"
																	onerror="this.src ='../images/profile.png'">
															</div>
															<div class="rightBox">
																<h1>${queryList.member.firstName}
																	${queryList.member.lastName} 
																	<i data-icon="q" class="icon checkY"></i>
																	<span>${queryList.member.companyName}</span>
																	
																</h1>
																<p>
																	Location: <strong>${queryList.member.city}, 
																		${queryList.member.country}</strong>
																</p>
																<p>
																	Member Since: <strong>${queryList.member.fixersSince}</strong>
																</p>
																<p>
																	Time Zone: <strong>${queryList.member.timeZone}</strong>
																</p>
																
															</div>
															
														</div>
														<div style="clear:both;"></div>
															<p style="word-wrap:break-word;">${queryList.member.overview}</p>
													

														<h2>Industries</h2>
														<c:forEach var="categoryList"
															items="${queryList.member.categoryList}"
															varStatus="thecategoryList">
															<span class="categoriesBtn">${categoryList}</span>
														</c:forEach>
													</div>

												</div>

												<div class="account" id="account${thequeryList.index}"
													style="width: 100%; border: 0px;">

													<div class="proBtn">
														<span class="request request50"
															id="denyRequest${thequeryList.index}"
															onclick="denyRequest(${queryList.queryId})">Not Interested</span> 
														<%-- 	<span class="message"
															id="message${thequeryList.index}"
															onclick="messageBtnClick('${queryList.member.firstName}',${thequeryList.index} , ${queryList.queryId} )">Message
															${queryList.member.firstName}</span> --%>
															
															 <span class="accept request50"
															id="accept${thequeryList.index}"
															onclick="applyRequest(${queryList.queryId})">Apply for Request</span>
													</div>
												</div>

												<div class="clearfix"></div>
											</div>
										</div>


										<span class="duration green">${queryList.timeDiff}
											ago... </span> <span class="MoreDetail"
											id="MoreDetail${thequeryList.index}"
											onclick="moreBtnclick(${thequeryList.index},${queryList.queryId},${queryCounts},${queryList.memberId},'${queryList.hashcode}' )">More</span>
										<span class="CloseDetail"
											id="CloseDetail${thequeryList.index}"
											onclick="closeBtnclick(${thequeryList.index})">Close</span>


									</div>
								</c:forEach>

							</div>


						</div>

						<div class="messageChat">
							<div class="messageWarpC">
								<!-- // chat // -->
								<div class="popupChat">
									<div class="popupChatDiv">
										<div class="popupcahtH">
											<h1 id="chatUserName"></h1>
											<div class="closeMessage">
												<i data-icon="y" class="icon"></i>
											</div>
										</div>
										<div class="">
											<div style="visibility: visible;" class="cahatingBox">
												<div id="chatbgBody" class="chatbgBody"></div>
												<div id="chatload" class="chatLoad">Please wait while loading..</div>
												<div class="chatbgFooter">
													<textarea  placeholder="Type here..." id="chatTextArea"></textarea>
													<span> <input value="" id="documentUpload"
														onchange="uploadBtnClick()" type="file"> <img
														src="${pageContext.request.contextPath}/images/attachment.png"
														alt="">
													</span>
													<div id="chatsend" onclick="sendBtnClick()"
														class="chatsend">Send</div>

												</div>
											</div>
										</div>
									</div>
								</div>
								<!--// chat //  -->
							</div>
						</div>

					</div>



				</div>
			</div>
	</div>
	</section>

	</div>
	<!-- <script type="text/javascript">

$(function(){

// The height of the content block when it's not expanded
var adjustheight = 130;
// The "more" link text
var moreText = "More ";
// The "less" link text
var lessText = "Close";

// Sets the .more-block div to the specified height and hides any content that overflows
$(".more-less .more-block").css('height', adjustheight).css('overflow', 'hidden');

// The section added to the bottom of the "more-less" div
$(".more-less").append('<p class="continued"></p><a href="#" class="adjust"></a>');

$("a.adjust").text(moreText);

$(".adjust").toggle(function() {
		$(this).parents("div:first").find(".more-block").css('height', 'auto').css('overflow', 'visible');
		// Hide the [...] when expanded
		$(this).parents("div:first").find("p.continued").css('display', 'none');
		$(this).text(lessText);
	}, function() {
		$(this).parents("div:first").find(".more-block").css('height', adjustheight).css('overflow', 'hidden');
		$(this).parents("div:first").find("p.continued").css('display', 'block');
		$(this).text(moreText);
});
});

</script> -->
<div id="popup" style=" display: none;">    
    	<div id="inpopup">
    	
        	<span class="closepopup"><i data-icon="y" class="icon"></i></span>
            
            <div id="popupcahtH">
           <h1 id="reqStatusMsg"> </h1>
</div>
        </div>
    </div>
<style>
	
	#popup {
	width: 100%;
    background: rgba(0,0,0,0.85);
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 99999;
	overflow-y: auto;
}

#popupcahtH {
    min-height: 45px;
    position: relative;
}

#inpopup {
	width: 500px;
    background: #fff;
	padding: 20px;
	margin: 40px auto 20px;
	position: relative;
}

#inpopup .closepopup {
	cursor: pointer;
    overflow: hidden;
    display: block;
    position: absolute;
    right: 0;
    top: 0;
}
	
	
	

</style>
</body>
</html>