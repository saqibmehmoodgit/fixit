
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ERPfixers | Fixed</title>
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

	  var fixerId = ${myUser.userId};
	  var virtualFileName='';	
      var queryHashCodeId = '';
      var customerId = '';
	  var qId='';
	  var fixerName = '';
	  var indexToHide = -1;
	  var indexToLoadMsg = -1;

	  
	  var scrollId = '';
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
			
			  $('a').removeClass('active');
			
			  $("#closedFixed").addClass('active');
			
			  setInterval(checkVisibility, 10000);
				
				$('.chatbgBody').on('scroll',function(){
			    	 var h =  $('#' + scrollId).scrollTop();
			    	 chatH = h;
			    	 chatHArr.push(h);
			    	  
			      });
		
				
		});
		
		 
		  function checkVisibility(){
				
				var msg = $("#chatTextArea").val();
				if($('#working_fixer'+indexToLoadMsg).is(':visible')){
					if(msg == null || msg.trim()==''){
						loadGroupMessage();
					}
				}
			}
			function loadGroupMessage() {
			  
				var msg = $("#chatTextArea" + indexToLoadMsg).val();
				var doc = $("#documentUpload" + indexToLoadMsg).val();
				if((msg == null || msg.trim()=='') && (doc == null || doc.trim()=='')){
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
						setchatMessages(indexToLoadMsg,messageSet);
					}
				});
				}else{
					req.abort();
				}
				$('#chatTextArea').on('keydown',function(){
					req.abort();
				});
			}


		
		  function uploadBtnClick(index){
				$('#chatload'+index).css('display','block');
				  $('.MoreDetail').css("pointer-events", "none");
				    $('.CloseDetail').css("pointer-events", "none");

			  uploadDocument(index,queryHashCodeId);
		  }
		  
		 function uploadDocument(index,queryHashCode)
		   {
			  var client = new XMLHttpRequest();

			 //$("#uploadIcon").addClass('fa fa-spinner fa-spin');
		    var file = document.getElementById("documentUpload"+index);
			  document.getElementById("chatTextArea"+index).disabled = true;
			    $('#documentUpload'+index).attr('disabled', 'true');

		    //document.getElementById("sendMessageButton").disabled = true;
		    
		    / Create a FormData instance /
		    var formData = new FormData();  
		    formData.append("file", file.files[0]);
		  //  var id=$('#queryId').val();
		    formData.append("queryId",queryHashCode);
		    client.open("post", "../fixer/uploadDocDetailpage", true);
		    client.send(formData);
			
		client.onreadystatechange=function()
		{
			
		if (client.readyState==4 && client.status==200)
		  {
			
			var json=	client.responseText;
		    var str = "DOCTYPE html";
			if(json.indexOf(str) != -1){
				var file = document.getElementById("documentUpload"+index);
			
				virtualFileName = file.files[0].name;
				
			}else{
			
			virtualFileName=json;
			}
		
		if(virtualFileName != null && virtualFileName !=''){
	    	
	    	saveDoc(index);
	    	
	    }
		
		/* //$("#uploadIcon").addClass('fa fa-paperclip');
		//$("#uploadIcon").removeClass('fa-spinner fa-spin');
		//document.getElementById("alertmsgshow").style.display = "block";
		//$('#docShow').text(virtualFileName);
		document.getElementById("documentUpload").disabled = true;
		document.getElementById("myTextarea").disabled = false;
		document.getElementById("sendMessageButton").disabled = false; */
			/* window.location.replace(url);
				window.location.reload(); */

		  }
		}
		   }
		function sendBtnClick(index){
			    	
					var chatTextArea = $('#chatTextArea'+index).val();
					
				
					
					if(chatTextArea != null && chatTextArea.trim() !=''){
						$('#chatsend'+index).css('pointer-events','none');

				    	sendChatmessage(index,queryHashCodeId,customerId, fixerId, chatTextArea);
					}
					
					$('#chatTextArea'+index).val('');
					
					chatTextArea = '';
					
					
			
			
		}
		function saveDoc(index){
				
			var clientChat = new XMLHttpRequest();
			var file = document.getElementById("documentUpload"+index);
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
					$('#chatbgBody'+index).append(htmlString);
					 
					   var objDiv = document.getElementById("chatbgBody"+index);
					    objDiv.scrollTop = objDiv.scrollHeight;	
					    $('#chatload'+index).css('display','none');
					    $('.MoreDetail').css("pointer-events", "auto");
					    $('.CloseDetail').css("pointer-events", "auto");


				    fileName = '';		
		
				}
				$('#documentUpload'+index).val("");
				  document.getElementById("chatTextArea"+index).disabled = false;
				  $('#documentUpload'+index).removeAttr('disabled');

				}
		}
		
		function sendChatmessage(index,queryHashCode,customerId, fixerId, chatFromTextArea){
			senchatScroll = true;

			var msg=chatFromTextArea;
		//	document.getElementById("sendMessageButton").disabled = true;
		//	document.getElementById("documentUpload").disabled = true;
								$('#chatload'+index).css('display','block');
								  $('.MoreDetail').css("pointer-events", "none");
								    $('.CloseDetail').css("pointer-events", "none");

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
						   setchatMessages(index,messageSet);
						   }
							$('#chatload'+index).css('display','none');
							  $('.MoreDetail').css("pointer-events", "auto");
							    $('.CloseDetail').css("pointer-events", "auto");
								$('#chatsend'+index).css('pointer-events','auto');


					   }); 

				  }

	 
		/* show less  */
		function moreBtnclick(id,queryId,totalcount,memberId,hashcode){
			  queryHashCodeId = hashcode;
		      customerId = memberId;
			
		      indexToLoadMsg = id;
			  chatHMax = 0;
			  chatH = 0;
			  chatHArr = [];
			  chatHArr.push(0);
			  senchatScroll = true;
			    scrollId = 'chatbgBody' + id;

			qId = hashcode;
			$('#working_fixer'+indexToHide).css('display','none');
			$('.requestsbg').css('opacity','0.5');
	        $('#requestsbg'+id).css('opacity','1');
	        
	    	$('#titleContent'+id).attr("class", "QOverviewOpen");

				/* $('#documentUpload').bind('change', function(){
				
				uploadDocument(hashcode);
				
			}); */
			
			for(var i=0;i<totalcount;i++)
				{
				 $("#Expand-Collaps"+ i).css({"height":"133px","overflow":"hidden"});
				    $("#CloseDetail"+ i).hide();
				    $("#MoreDetail"+ i).show();
					$(".clientConver").hide();
				
					$(".duration").css({"bottom":"20px"});
				
				}
			  $("#Expand-Collaps"+ id).css({"height":"auto","overflow":"visible"});
			    $("#CloseDetail"+ id).show();
			  
			    $("#MoreDetail"+ id).hide();
			    $("#clientConver").show();
			 
			    $(".duration").css({"bottom":"55px"});
			 
			    
			  
			    
			   /*  $("#chatTextArea").keyup(function(event){
			    	var chatFromTextArea = $("#chatTextArea").val();
			    	
			    	
				    if(event.keyCode == 13){
				    	
				    	sendChatmessage(hashcode,customerId, fixerId, chatFromTextArea);
				    	
				    }
				});		 */    
			 
			   
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
						   var catList = '';
							for(var j=0;j<response.result.fixer.categoryList.length;j++)
								{
								
								catList = catList + (response.result.fixer.categoryList[j]).trim() + ', ';
								}
							catList = catList.substring(0, catList.length - 2);
						   $("#working_fixer"+id).css('display','block'); 
						 
						   $("#fixerName"+id).text(response.result.fixer.firstName+" "+ response.result.fixer.lastName);
						   $("#fixerCompany"+id).text(response.result.fixer.companyName);
						   $("#location"+id).text(response.result.fixer.city + ", "+ response.result.fixer.country);
						   if(response.result.fixer.profilePhoto == ''){
							   $("#fixerProfileImage"+id).attr("src",  '../images/profile-pic.jpg'); 
						   }else{
						   $("#fixerProfileImage"+id).attr("src", response.result.fixer.profilePhoto);
						   }
						   if(response.result.fixer.country ==''){
							   $("#fixerFlag"+id).attr("src", "../images/profile-pic.jpg");
						   }else{
							   $("#fixerFlag"+id).attr("src", '../flags/'+response.result.fixer.country+".png");
						   }
						   
						   $("#memberSince"+id).text(response.result.fixer.fixersSince);
						   $("#timezone"+id).text(response.result.fixer.timeZone);
						   $("#categories"+id).text(catList);
						   $("#overview"+id).text(response.result.fixer.overview);
						   var messageSet =  response.result.messagesSet;
						   if(messageSet != undefined && messageSet != '' ){
							$('#chatbgBody'+id).empty();   
							   setchatMessages(id,messageSet);
							   
						   }  
						   
					  
					   }
				   }); 
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
						  
						   $("#chatMsg"+id).text("0");
						   $("#chatMsg"+id).hide();
					  
					   }
				   });   
			    indexToHide = id;			   
			    	
		}
		function setchatMessages(index,messageSet){
			$('#chatbgBody'+index).empty();
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
        	  $('#chatbgBody'+index).append(finalHtml);
        	  finalHtml = '';
        	  $('#chatTextArea'+index).val('');
        	  
        	  
        	 
          }	
          console.log('chatHArr::'+ chatHArr);
          chatHMax =   Math.max.apply(Math, chatHArr);
          console.log('chatHMax::'+ chatHMax);
          
          if((chatH == chatHMax) || senchatScroll){
			    var objDiv = document.getElementById("chatbgBody"+index);
			    objDiv.scrollTop = objDiv.scrollHeight;
	    		  senchatScroll = false;

        	}
		}	
			function closeBtnclick(id){
	            $('.requestsbg').css('opacity','1');
	            $('#titleContent'+id).attr("class", "QOverviewClose");
			    $("#Expand-Collaps"+ id).css({"height":"133px","overflow":"hidden"});
			    $("#CloseDetail"+ id).hide();
			    $("#MoreDetail"+ id).show();
				$(".clientConver").hide();
			
				$(".duration").css({"bottom":"20px"});
				$("#account"+id).hide();
			
				  $("#working_fixer"+id).css('display','none');
				  indexToLoadMsg = -1;

			}
			
			function detailFixer(index){
		        $("#clientB"+index).slideDown();
		        $("#detailFixer"+index).hide();
		        $("#closeFixer"+index).show();
		    }
			function closeFixer(index){
		        $("#clientB"+index).slideUp();
		        $("#detailFixer"+index).show();
		        $("#closeFixer"+index).hide();
		    }
			
			
			
			
			
			
		
	  </script>



<script type="text/javascript">//<![CDATA[
		function preloader(){document.getElementById("loader").style.display="none";document.getElementById("pagewrap").style.display="block";}
		window.onload=preloader;
		//]]>
		</script>

</head>
<body>

	<div id="pagewrap">

		<!-- Work Here start-->
		<section class="wrapper">
			<div class="container-fluid">
				<div class="requestsBox">
					<c:if test="${queryCounts == 0}">
						<div class="nodatamsg">There are currently no Fixed
							requests.</div>
					</c:if>
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="">
								<c:forEach var="queryList" items="${queryList}"
									varStatus="thequeryList">
									<div
										style="overflow:visible; clear:both;/*  margin-bottom: 10px; padding-top: 10px; */">
										<div id="requestsbg${thequeryList.index}"
											class="col-lg-6 col-md-6 col-sm-12 col-xs-12 requestsbg">
											<input type="hidden" id="${queryList.queryId}">
											 	<c:if test = "${queryList.unreadMessageCount != 0}">
										<span class="chatmsgnumber" data-toggle="tooltip" title="You have ${queryList.unreadMessageCount} new message"
										id="chatMsg${thequeryList.index}" onclick="moreBtnclick(${thequeryList.index},${queryList.queryId},${queryCounts},${queryList.memberId},'${queryList.hashcode}' )">${queryList.unreadMessageCount}</span>		</c:if>	 
											<div class="Expand-Collaps"
												id="Expand-Collaps${thequeryList.index}">
												<div class="time">
													<h1>${queryList.queryCredits}</h1>
													<span> <c:choose>
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
														<div id="titleHead${thequeryList.index}"
															class="titleHeaddiv">${queryList.queryTitle}<div
																class="half"></div>
														</div>

														<span class="ratingC"
															id="queryRating${thequeryList.index}"> <i
															id="rate1_${thequeryList.index}" data-icon="l"
															class="icon"></i> <i id="rate2_${thequeryList.index}"
															data-icon="l" class="icon"></i> <i
															id="rate3_${thequeryList.index}" data-icon="l"
															class="icon"></i> <i id="rate4_${thequeryList.index}"
															data-icon="l" class="icon"></i> <i
															id="rate5_${thequeryList.index}" data-icon="l"
															class="icon"></i> <script>
														var rating = ${queryList.queryRating};
														var index = ${thequeryList.index};
														for(var i = 1 ; i<=rating;i++)
															{
															$("#rate"+i+"_"+index).attr("data-icon", "m");
															}
			
														</script>
														</span>
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
																		style="display:none"></i></span>
																</div>
															</c:forEach>
														</div>
														<div class="clearfix"></div>

														<div id="attach${queryList.queryId}">
															<c:forEach var="attachList"
																items="${queryList.attachedDocuments}"
																varStatus="theattachList">
																<c:if test="${theattachList.index==0}">
																	<h2>Attachment(s)</h2>
																</c:if>
																<div class="download" id="${attachList.fileUniqueCode}">
																	<span>Download:</span><a href="${attachList.fileUrl}">
																		<input type="text" name="documents"
																		style="cursor: pointer" readonly
																		value="${attachList.fileName}(315KB)">

																	</a>

																</div>
															</c:forEach>
														</div>





														<div class="clearfix"></div>
													</div>
												</div>










											</div>
											
											<c:choose>
										
										<c:when test="${thequeryList.index eq 0}">
									<span class="duration green">Posted:
												${queryList.timeDiff} ago... </span> 
										</c:when>
										<c:otherwise>
										
										<span class="duration green">${queryList.timeDiff} ago... </span> 
										</c:otherwise>
										</c:choose>
							
											
												
												<span class="MoreDetail"
												id="MoreDetail${thequeryList.index}"
												onclick="moreBtnclick(${thequeryList.index},${queryList.queryId},${queryCounts},${queryList.memberId},'${queryList.hashcode}' )">More</span>
											<span class="CloseDetail"
												id="CloseDetail${thequeryList.index}"
												onclick="closeBtnclick(${thequeryList.index})">Close</span>

										</div>
										<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"
											id="working_fixer${thequeryList.index}" style="display: none">
											<div id="clientConver">
												<div class="clientBox">
													<div class="clientbg">
														<h1>Member</h1>
														<div class="clientDetail">
															<div class="clientDetail">
																<div class="clientHead">
																	<img class="fixerProfileImage"
																		id="fixerProfileImage${thequeryList.index}"
																		onclick="detailFixer(${thequeryList.index})"
																		src="${pageContext.request.contextPath}/images/profile.png"
																		onerror="this.src ='${pageContext.request.contextPath}/images/profile.png'">
																	<img id="fixerFlag${thequeryList.index}"
																		class="CuntryFlag"
																		src="${pageContext.request.contextPath}/images/profile.png"
																		onerror="this.src ='${pageContext.request.contextPath}/images/profile.png'">
																	<h1>
																		<span class="Titlename"
																			onclick="detailFixer(${thequeryList.index})"
																			id="fixerName${thequeryList.index}">Castel
																			Chemistry</span> <span
																			onclick="detailFixer(${thequeryList.index})"
																			class="designation"
																			id="fixerCompany${thequeryList.index}">Castel
																			Chemistry</span>
																	</h1>
																	<span class="details"
																		id="detailFixer${thequeryList.index}"
																		onclick="detailFixer(${thequeryList.index})">Details</span>
																	<span class="closeC"
																		id="closeFixer${thequeryList.index}"
																		onclick="closeFixer(${thequeryList.index})">Close</span>
																</div>
																<div class="clientB" id="clientB${thequeryList.index}">
																	<div class="clientD">
																		<span class="location">Location: <strong
																			id="location${thequeryList.index}"></strong></span>
																		<div class="clearfix"></div>
																		<span class="time">Member Since: <strong
																			id="memberSince${thequeryList.index}"></strong></span> <span
																			class="time">Time Zone: <strong
																			id="timezone${thequeryList.index}"></strong></span> <span
																			class="categorie">Categories: <strong
																			id="categories${thequeryList.index}"></strong></span>
																	</div>
																	<p id="overview${thequeryList.index}"></p>

																</div>
															</div>

															<!-- // popup //  -->
															<!-- // rating popup // -->

															<!--// rating popup //  -->
															<!-- // popup //  -->
														</div>
													</div>
												</div>

												<div class="cahatingBox">
									<div id="chatbgBody${thequeryList.index}" class="chatbgBody"></div>
									<div id="chatload${thequeryList.index}" class="chatLoad">Please wait while loading...</div>
									<div class="chatbgFooter">
										<textarea placeholder="Type here..." id="chatTextArea${thequeryList.index}"></textarea>
										<span> <input type="file" 	onchange="uploadBtnClick(${thequeryList.index})"  id="documentUpload${thequeryList.index}" value="">
											<img
											src="${pageContext.request.contextPath}/images/attachment.png"
											alt="">
										</span>
									<div id="chatsend${thequeryList.index}" onclick="sendBtnClick(${thequeryList.index})" class="chatsend">Send</div>
										
									</div>
								</div>

													<div class="screenSharing">
									<h2>Screen Sharing Tools</h2>
									<div class="sharingTool">
										<a href="https://www.teamviewer.com/en/download" target="_blank"><img
											src="${pageContext.request.contextPath}/images/team_viewe.png"
											alt="Team Viewer"></a> <a href="https://join.me/"  target="_blank"><img
											src="${pageContext.request.contextPath}/images/join_me.png"
											alt="Join Me"></a>
									</div>
									<div class="clearfix"></div>
								</div> 
											</div>
										</div>
									</div>
								</c:forEach>
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
<script type="text/javascript">
$(document).ready(function(){
	 $(".closeMessage").click(function(){
		 $('.chatbgBody').empty();
		 senchatScroll = true;
		 
		});
	 });
$(document).ready(function(){
	
	var hrefParam  = window.location.search;
	if(hrefParam.indexOf('queryId=')>=0){
	var splitArr =  hrefParam.split('&');
	 var divQueryId = getdivQueryId(splitArr);
	var parentDiv = $('#'+divQueryId).parent().attr('id'); 
	
	console.log(parentDiv);

	var onclickFuntion = $('#'+parentDiv +' span[id^="MoreDetail"]').attr('onclick');
	
	console.log(onclickFuntion);
	var func = new Function(onclickFuntion);
	func();
	$('html, body').animate({
        scrollTop: $("#" + parentDiv).offset().top
    }, 2000);
	}
	
	
});
function getdivQueryId(splitArr){
	var divQueryId = '';
	var arrLen = splitArr.length;
	for(var i = 0; i< arrLen;i++){
		var paramString = splitArr[i];
		if(paramString.indexOf('queryId=')>=0){
			var queryId = paramString.split('queryId=')[1];
			divQueryId = queryId;
			return divQueryId;
		}
		
	}
	
}

</script>
</body>
</html>