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
<title>ERPfixers | Fixed</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/animate.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/custom.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/pages/script.js"></script>
<script src="${pageContext.request.contextPath}/js/wow.min.js"></script>
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

	  var userId = ${myUser.userId};
	  var virtualFileName='';	
	  var queryHashCodeId = '';
      var fixerIdForChat = '';
	  var qId='';
	  var fixerName = '';
	  var indexToHide = -1;
	  var indexToLoadMsg = -1;

	  
	  var chatHMax = 0;
      var chatH = 0;
      var chatHArr = [];
      var senchatScroll = false;
      chatHArr.push(0);
	  
	  /* show less  */
		
		$(document).ready(function(){
        	   
        	   $('a').removeClass('active');
				$('#closed').addClass('active');
			
				setInterval(checkVisibility, 10000);


				$('.chatbgBody').on('scroll',function(){
			    	 var h =  $('#' + scrollId).scrollTop();
			    	 chatH = h;
			    	 chatHArr.push(h);
			    	  
			      });  	   
        	   
           }) ;
		
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
		    //document.getElementById("sendMessageButton").disabled = true;
		  $('#documentUpload'+index).attr('disabled', 'true');

		    / Create a FormData instance /
		    var formData = new FormData();  
		    formData.append("file", file.files[0]);
		  //  var id=$('#queryId').val();
		    formData.append("queryId",queryHashCode);
		    client.open("post", "../member/uploadDocDetailpage", true);
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
		function saveDoc(index){
				
			var clientChat = new XMLHttpRequest();
			var file = document.getElementById("documentUpload"+index);
			var fileName = '';

			 var formData = new FormData();  
			    formData.append("file", file.files[0]);
			   // var id=$('#queryId').val();
			    formData.append("queryId",queryHashCodeId);
			    formData.append("fixerId",fixerIdForChat);
			    fileName = virtualFileName;

			    formData.append("virtualFileName",virtualFileName);
			    virtualFileName = '';
			    clientChat.open("post", "../member/uploadChatDocDetailpage", true);
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
					    url : "../member/sendMessage",
					    data : {
					    	customerId : customerId,					    
					    	fixerId : fixerId,
					    	queryId : queryHashCode,
					    	msgFrom : 'C',
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

		function sendBtnClick(index){
			
			
			var chatTextArea = $('#chatTextArea'+index).val();
			if(chatTextArea != null && chatTextArea.trim() !=''){
				$('#chatsend'+index).css('pointer-events','none');

		    	sendChatmessage(index,queryHashCodeId,userId, fixerIdForChat, chatTextArea);
			}
			$('#chatTextArea').val('');
			chatTextArea = '';
			
	
	
}
		

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
				url : "../member/fixerLoadGroupMessage",
				data : {
					
					fixerId: fixerIdForChat,
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


		
		function moreBtnclick(id,queryId,totalcount,fixerId,hashcode,starPoints){
			qId = hashcode;
			 chatHMax = 0;
		      chatH = 0;
		      chatHArr = [];
		      chatHArr.push(0);
		      
			senchatScroll = true;
			indexToLoadMsg = id;

			queryHashCodeId =hashcode;
			fixerIdForChat = fixerId;
			$('.CloseDetail').css('bottom','55px');

			$('#working_fixer'+indexToHide).css('display','none');
			$('.requestsbg').css('opacity','0.5');
	        $('#requestsbg'+id).css('opacity','1');
			$(".copyrequest").css('display','none');
			for(var i=0;i<totalcount;i++)
				{
				 $("#Expand-Collaps"+ i).css({"height":"133px","overflow":"hidden"});
				    $("#CloseDetail"+ i).hide();
				    $("#MoreDetail"+ i).show();
					$(".clientConver").hide();
					
				//	 $("#duplicateRequest"+ i).css({"display":"none"});
					$("#closeDate"+i).hide();
					$(".duration").css({"bottom":"20px"});
				//	$(".copyrequest").css({"display":"block"});
					
					
				
				}
			
			  $("#Expand-Collaps"+ id).css({"height":"auto","overflow":"visible"});
			    $("#CloseDetail"+ id).show();
			    $("#CloseDetail"+ id).show();
			    $("#duplicateRequest"+ id).css('display','block');
			    
				$("#closeDate"+id).show();
			    $("#MoreDetail"+ id).hide();
			    $("#clientConver").show();
			    scrollId = 'chatbgBody' + id;
			    $(".duration").css({"bottom":"55px"});
			    $("#queryIdRating"+id).val(hashcode);
			   // $("#rateLatter"+id).attr("href","${pageContext.request.contextPath}/member/rateLater?queryId="+hashcode);
			    
			    
			    
			    /* $("#chatsend").bind('click', function(){
			    	
					var chatTextArea = $('#chatTextArea').val();
					if(chatTextArea != null && chatTextArea.trim() !=''){
				    	sendChatmessage(hashcode,userId, fixerId, chatTextArea);
					}
					$('#chatTextArea').val('');
					chatTextArea = '';
				});
			     */
			    
			  /*   $("#chatTextArea").keyup(function(event){
			    	var chatFromTextArea = $("#chatTextArea").val();
			    	
				    if(event.keyCode == 13){
				    	
				    	sendChatmessage(hashcode,userId, fixerId, chatFromTextArea);
				    }
				});	 */	    
			/* 	$('#documentUpload').bind('change', function(){
			
					uploadDocument(hashcode);
			
					}); */	   
			   
			    $.ajax({
					method : "POST",
				    url : "${pageContext.request.contextPath}/member/findFixerDetails",
				    data : {
				    fixerId: fixerId,
				    queryId: hashcode

				     }
				   }).done(function(response) {
					   
					   var data = response.result.status;
					   if(data=='success'){
						   var catList = '';
							for(var j=0;j<response.result.fixer.categoryList.length;j++)
								{
								catList = catList + response.result.fixer.categoryList[j] + ', ';
								}
							catList = catList.substring(0, catList.length - 2);
						   $("#working_fixer"+id).css('display','block'); 
						  
						   
						   
						   if(response.result.fixer.profilePhoto == ''){
							   $("#fixerProfileImage" +id).attr("src", "../images/profile.png");

						   }else{
							   $("#fixerProfileImage" +id).attr("src", response.result.fixer.profilePhoto);

						   }
						   if(response.result.fixer.country == ''){
							   $("#fixerFlag"+id).attr("src", "../images/profile.png");

						   }else{
							   $("#fixerFlag"+id).attr("src", '../flags/'+response.result.fixer.country+".png");

						   }
						   
						   
						   
						   $("#fixerName"+id).text(response.result.fixer.firstName+" "+ response.result.fixer.lastName);
						  
						   var companyName = '';
							if(response.result.fixer.companyName != null)
								companyName = response.result.fixer.companyName;

						   $("#fixerCompany"+id).text(companyName);
						  
						   if(response.result.fixer.favouriteFixerStatus == 'F'){
							   $('#fav'+id).addClass('green');
			 					$("#favtext"+id).css('display','block');

							   $('#fav'+id).attr("onclick",'makeUserUnFav('+fixerId+','+id+')'); 

						   }
						   else{
			 					$("#favtext"+id).css('display','none');

						   $('#fav'+id).attr("onclick",'makeUserFav('+fixerId+','+id+')'); 
						   }
						   $("#location"+id).text(response.result.fixer.city + ", "+ response.result.fixer.country);
						   if(response.result.fixer.linkedinProfile.indexOf("http")>-1)
							   $("#locationhref"+id).attr("href",response.result.fixer.linkedinProfile);
							   else
								   $("#locationhref"+id).attr("href",'https://'+response.result.fixer.linkedinProfile); 
						   $("#linkedIn"+id).text(response.result.fixer.linkedinProfile);
						   $("#memberSince"+id).text(response.result.fixer.fixersSince);
						   $("#timezone"+id).text(response.result.fixer.timeZone);
						   $("#categories"+id).text(catList);
						   $("#fixedRequests"+id).text(response.result.fixer.fixedCounts);
						   $("#rateAccept"+id).text('Rate ' + response.result.fixer.firstName+" "+ response.result.fixer.lastName);
						   $("#resolvedwithInDeadline"+id).text(response.result.fixer.fixedUnderdeadline +"%");
						   $("#lastActive"+id).text(response.result.fixer.lastLogin);
						   $("#overview"+id).text(response.result.fixer.overview);
						   $("#rateBtn"+id).text('Rate ' + response.result.fixer.firstName+" "+ response.result.fixer.lastName);
						   $('#rateAccept' + id).text('Rate '+ response.result.fixer.firstName+" "+ response.result.fixer.lastName);
						   for(var j=1;j<=response.result.fixer.fixerRating;j++)
							{
							$("#ratingStatic_"+j+"_"+id).attr("data-icon", "m");
							
							}
						   
						   
						    var messageSet =  response.result.messagesSet;
						   if(messageSet != undefined && messageSet != '' ){
							$('#chatbgBody'+id).empty();   
							   setchatMessages(id,messageSet);
							   
						   }  
					  
					   }
				   }); 
			    
			   $.ajax({
					method : "POST",
				    url : "${pageContext.request.contextPath}/member/updateMessageCounts",
				    data : {
				    fixerId: fixerId,
				    queryId: queryId
				     }
				   }).done(function(response) {
					   var data = response.status;
					   if(data=='success'){
						  
						   $("#chatMsg"+id).text("0");
						   $("#chatMsg"+id).hide();
					  
					   }
				   });  
			    
			    if(starPoints >0)
			    	{
			    	 $("#rateBtn").css('display','none'); 
			    	
			    	}
			    else
			    	{
			    	$("#rateBtn").css('display','block'); 
			    	}
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
        	  if (message[i].msgFrom == 'C'){
        		  
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
				senchatScroll = true;

	            $('.requestsbg').css('opacity','1');
			    $("#Expand-Collaps"+ id).css({"height":"133px","overflow":"hidden"});
			    $("#CloseDetail"+ id).hide();
			    $("#MoreDetail"+ id).show();
				$(".clientConver").hide();
				$("#closeDate"+id).hide();
				$(".duration").css({"bottom":"20px"});
				$("#account"+id).hide();
				 $("#duplicateRequest"+ id).hide();

				  $("#working_fixer"+id).css('display','none');
				  $(".copyrequest").css('display','none');
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
			
			function rateClick(rating, index)
			{
				$("#starPoints"+index).val(rating);
				for(var i =1;i<=5;i++)
					{
					$("#rate_"+i+"_"+ index).removeClass().addClass("rateL");
					}
				
				for(var i =1;i<=rating;i++)
				{
				$("#rate_"+i+"_"+ index).removeClass().addClass("rateF");
				}
				
			}
			
			function submitRating(index)
			{
				
				var selectedRating = $("#starPoints"+index).val();
				if(selectedRating==0)
					{
					 jQuery('#ratingMsg').text('Select atleast one star.');
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
				else
					{
					$("[onclick]").removeAttr("onclick"); 

					$("#rateForm"+index).submit();
					}
				
			}
			
			
			function dialogReassign(rating, index)
			{
				rateClick(rating, index);
				 $("#popupRateFixed"+index).css('display','block');
				
			}
			
			
			function reassignCancel(index)
			{
				$("#popupReassign"+index).css('display','none');
			}
			
			function reassignSubmit(index)
			{
				$("#reassign-form"+index).submit();
			}
			

			function makeUserFav(userId,index)
			{
				 $.ajax({
			 			method : "POST",
			 		    url : "../member/fixerFavourite",
			 		    data : {
			 		  fixerId : userId
			 		   
			 		   
			 		    }
			 		   }).done(function(response) {
			 			if(response.result.response == 'success')
			 				{
		 					$("#favtext"+index).css('display','block');

			 					$("#fav"+index).addClass('green');
			 					 document.getElementById('fav'+index).setAttribute('onclick','makeUserUnFav('+userId+','+index+')');
			 				}
			 				
			 		   });
			}
			
			function makeUserUnFav(userId,index)
			{
				 $.ajax({
			 			method : "POST",
			 		    url : "../member/deleteFavouriteFixer",
			 		    data : {
			 		  fixerId : userId
			 		   
			 		   
			 		    }
			 		   }).done(function(response) {
			 			if(response.status == 'success')
			 				{
		 					$("#favtext"+index).css('display','none');

			 				document.getElementById('fav'+index).setAttribute('onclick','makeUserFav('+userId+','+index+')')
			 					$("#fav"+index).removeClass('green');
			 				}
			 				
			 		   });
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
				<c:if test = "${queryCounts == 0}">
					<div class="nodatamsg">
						There are currently no Fixed requests.
					</div>
					</c:if>
				<div class="row">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<c:if test = "${msgType == 'success'}" >
						<div style="margin-top:18px; margin-right: 15px;" class="alert alert-success fade in col-lg-6 col-md-6 col-sm-12 col-xs-12">
    												<a title="close" aria-label="close" data-dismiss="alert" class="close" href="#">×</a> ${message}
    										    </div>
    										    <div style="clear:both;"></div>
    										    </c:if>
    										    
						<div class="">
							<c:forEach var="queryList" items="${queryList}"
									varStatus="thequeryList">
								<div style="overflow:visible; clear:both;/*  margin-bottom: 10px;padding-top: 10px; */">
						<div id="requestsbg${thequeryList.index}" class="col-lg-6 col-md-6 col-sm-12 col-xs-12 requestsbg">
						<input type="hidden" id="${queryList.queryId}">
						  <c:if test = "${queryList.unreadMessageCount != 0}"> 
						<span class="chatmsgnumber"
						   data-toggle="tooltip" title="You have ${queryList.unreadMessageCount} new message" id="chatMsg${thequeryList.index}"  onclick ="moreBtnclick(${thequeryList.index},${queryList.queryId},${queryCounts},${queryList.fixerId},'${queryList.hashcode}',${queryList.starRating} )">${queryList.unreadMessageCount}</span> </c:if> 
									<div class="Expand-Collaps" id="Expand-Collaps${thequeryList.index}">
										<div class="time">
											<h1>${queryList.queryCredits}</h1>
											<span><c:choose>
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
										<%-- <div class="textauto" id="title${thequeryList.index}">		
													<textarea style=" margin-top:0px; width:74%;" name="queryTitle" type="text" id="titleHead${thequeryList.index}"
													placeholder="${queryList.queryTitle}" class="titleHead" readonly></textarea>
											</div>	 --%>	
											<span class="ratingC" id= "queryRating${thequeryList.index}"> 
											<i id="rate1_${thequeryList.index}" data-icon="l" class="icon"></i>
														<i id="rate2_${thequeryList.index}" data-icon="l" class="icon"></i>
														<i id="rate3_${thequeryList.index}" data-icon="l" class="icon"></i>
														<i id="rate4_${thequeryList.index}" data-icon="l" class="icon"></i> 
														<i id="rate5_${thequeryList.index}" data-icon="l" class="icon"></i>
														<script>
														var rating = ${queryList.queryRating};
														var index = ${thequeryList.index};
														for(var i = 1 ; i<=rating;i++)
															{
															$("#rate"+i+"_"+index).attr("data-icon", "m");
															}
			
														</script>
														</span>		
													
													<div class = "textauto" id="textauto${thequeryList.index}">
										           <textarea name = "queryContent" rows="" id="titleContent${thequeryList.index}"
													 class="titleContent" readonly>${queryList.queryContent}</textarea>
										           </div>
												<%-- <textarea name = "queryContent" rows="" id="titleContent${thequeryList.index}"
													 class="titleContent" readonly>${queryList.queryContent}</textarea> --%>
													
												<input type="hidden" name="queryId" value="${queryList.queryId}"
													id="queryId${thequeryList.index}"> 
									<h2>Categories</h2>
									<div class="categoriesBox">
										
									<c:forEach var="catList" items="${queryList.subCategory}"
													varStatus="thecatList">
													<div class="categoriesBox">
														<span class="categoriesBtn"><input type="checkbox"  checked="checked"  value="35"  id ="35"  name="categories">${catList} <i id="catListCross${thequeryList.index}_${thecatList.index}" data-icon="y" class="icon" onClick="crossCategories(${thequeryList.index}_${thecatList.index})" style="display: none"></i></span>
													</div>
												</c:forEach> 
												</div>
									<div class="clearfix"></div>
								
									<div id = "attach${queryList.queryId}">
													<c:forEach var="attachList"
														items="${queryList.attachedDocuments}"
														varStatus="theattachList">
														<c:if test="${theattachList.index==0}">
															<h2>Attachment(s)</h2>
															</c:if>
														<div class="download" id="${attachList.fileUniqueCode}">
															 <span>Download:</span>  <a href="${attachList.fileUrl}" >
																<input type="text" name = "documents" value = "${attachList.fileName}(315KB)" style = "cursor:pointer" readonly >
																
															</a>
															
														</div>
													</c:forEach>
												</div>
												
											<div class="clearfix"></div>	
												<div class="durrations" id="">
													<span class="durtop">Duration:</span>
													<span class="durbottom">${queryList.queryStartDate}- ${queryList.queryEndDate}, ${queryList.dateTimeDiffernce}</span>
												</div>
									
									
									
									
									
								<div class="clearfix"></div>	
								</div>
								</div>
								
							
							
							
							
							
							
							
							
							
							</div>
							<c:choose>
										
										<c:when test="${thequeryList.index eq 0}">
									<span class="duration green">Posted: ${queryList.timeDiff}
											ago... </span>
										
										</c:when>
										<c:otherwise>
										
										<span class="duration green">${queryList.timeDiff}
											ago... </span>
										</c:otherwise>
										</c:choose>
							
							
										<span class="MoreDetail" id="MoreDetail${thequeryList.index}" onclick ="moreBtnclick(${thequeryList.index},${queryList.queryId},${queryCounts},${queryList.fixerId},'${queryList.hashcode}',${queryList.starRating} )">More</span>
										<span class="CloseDetail" id="CloseDetail${thequeryList.index}" onclick = "closeBtnclick(${thequeryList.index})">Close</span>
							
							              
							
								
							
							
												<span class="copyrequest" id="duplicateRequest${thequeryList.index}" style = "display:none; position: absolute; left: 0;"><a href="../member/copyQuestion?queryId=${queryList.hashcode}">Duplicate Request</a></span>
						</div>


					<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12" id="working_fixer${thequeryList.index}" style = "display : none">
					<div id="clientConver">
						<div class="clientBox">
							<div class="clientbg">
								<h1>Assigned Fixer</h1>
								<div class="clientDetail">
									<div class="clientDetail">
										<div class="clientHead">
												<img id="fixerProfileImage${thequeryList.index}"  onerror= "this.src ='${pageContext.request.contextPath}/images/profile.png'"
														onclick="detailFixer(${thequeryList.index})"	src="${pageContext.request.contextPath}/images/profile.png"
														>
														<img id="fixerFlag${thequeryList.index}" src="" class="CuntryFlag"  onerror= "this.src ='${pageContext.request.contextPath}/flags/United States of America(USA).png'">
														
													<h1>
														<span class="name"  	onclick="detailFixer(${thequeryList.index})" id="fixerName${thequeryList.index}"></span> 
															<span id = "fav${thequeryList.index}" onclick = "" class="fixertag "> <i data-icon="k" class="icon">
 			</i> <span id="favtext${thequeryList.index}">Favorite</span></span>
															<span 	onclick="detailFixer(${thequeryList.index})" class="designation" id="fixerCompany${thequeryList.index}"></span>
													</h1>
											<span class="ratingC"> <i id = "ratingStatic_1_${thequeryList.index}" data-icon="l" class="icon"></i>
														<i id = "ratingStatic_2_${thequeryList.index}" data-icon="l" class="icon"></i> 
														<i id = "ratingStatic_3_${thequeryList.index}" data-icon="l" class="icon"></i>
														<i id = "ratingStatic_4_${thequeryList.index}" data-icon="l" class="icon"></i> 
														<i id= "ratingStatic_5_${thequeryList.index}" data-icon="l" class="icon"></i></span>
											<span class="details" id="detailFixer${thequeryList.index}" onclick = "detailFixer(${thequeryList.index})">Details</span>
											<span class="closeC" id="closeFixer${thequeryList.index}" onclick = "closeFixer(${thequeryList.index})">Close</span>
										</div>	
										<div class="clientB" id="clientB${thequeryList.index}">
											<div class="clientD">
												<span class="location">Location: <strong id="location${thequeryList.index}"></strong></span>
												
												<span class="linkdin" ><img src="${pageContext.request.contextPath}/images/in.png" alt=""> <a id="locationhref${thequeryList.index}" href="" target = "_blank"><span id="linkedIn${thequeryList.index}"></span></a></span>
												<div class="clearfix"></div>
												<span class="time">Fixer Since: <strong id="memberSince${thequeryList.index}"></strong></span>
												<span class="time">Time Zone: <strong
															id="timezone${thequeryList.index}"></strong></span>
												<p id="overview${thequeryList.index}"></p>
												<span class="categorie">Categories: <strong id="categories${thequeryList.index}"></strong></span>
												<%-- <span class="time">Fixed Requests: <strong id="fixedRequests${thequeryList.index}"></strong></span>
												<span class="time">Resolved within Deadline: <strong id="resolvedwithInDeadline${thequeryList.index}"></strong></span>
												<span class="time">Last Active: <strong id="lastActive${thequeryList.index}"></strong></span> --%>
											</div>
											
											<div class="ReassignRequest" id="rateBtn${thequeryList.index}" onclick="dialogReassign(${queryList.queryRating},${thequeryList.index})"></div>
										</div>
									</div>
									
									<!-- // popup //  -->
									<!-- // rating popup // -->
							<div class="popupRfixed" id="popupRateFixed${thequeryList.index}">
							<form  role="form" class="contact-form" id="rateForm${thequeryList.index}"  
           																				 method="post"
           																					 action="../member/rate"  >
                                 <input  value="rateFixerFixed" name="rateFixerFixed" type="hidden">
           																					 
           								<input id="starPoints${thequeryList.index}" value="1" name="starPoints" type="hidden">
           								<input id="queryIdRating${thequeryList.index}" value="0" name="queryId" type="hidden">													 
								<div class="chatDiv">
									<h1>Rate Your Fixer</h1>
									<div class="chatbody">
										<div class="rateIcon">
											<div class="ratingDiv">
									           <span class="rateF" id="rate_1_${thequeryList.index}" onclick="rateClick(1,${thequeryList.index})"></span>
									           <span class="rateL" id="rate_2_${thequeryList.index}" onclick = "rateClick(2,${thequeryList.index})"></span>
									           <span class="rateL" id="rate_3_${thequeryList.index}" onclick = "rateClick(3,${thequeryList.index})"></span>
									           <span class="rateL" id="rate_4_${thequeryList.index}" onclick = "rateClick(4,${thequeryList.index})"></span>
									           <span class="rateL" id = "rate_5_${thequeryList.index}" onclick = "rateClick(5,${thequeryList.index})"></span>
									          </div>
										</div>
										<div class="rateTextarea">
											<label>Write a Review</label>
											<textarea rows="" name="review" cols=""></textarea>
										</div>
									</div>
									<div class="proBtn">
										<a href="" id="rateLatter${thequeryList.index}" class="request">Rate Later</a>
										<span class="accept boldText" id="rateAccept${thequeryList.index}" onclick = "submitRating(${thequeryList.index})">Rate Kim C.</span>
									</div>
								</div>
								</form>
							</div>
							<!--// rating popup //  -->
									<!-- // popup //  -->
								</div>
							</div>
						</div>
					
						 <div class="cahatingBox">
							<div id="chatbgBody${thequeryList.index}" class="chatbgBody">
								

							</div>
					<div id="chatload${thequeryList.index}" class="chatLoad">Please wait while loading...</div>
							
							<div class="chatbgFooter">
								<textarea placeholder="Type here..." id="chatTextArea${thequeryList.index}"></textarea>
								<span>
									<input type="file" onchange="uploadBtnClick(${thequeryList.index})"  id="documentUpload${thequeryList.index}" value="">
									<img src="${pageContext.request.contextPath}/images/attachment.png" alt="">
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

<div id="popup" style=" display: none;">    
    	<div id="inpopup">
    	
        	<span class="closepopup"><i data-icon="y" class="icon"></i></span>
            
            <div id="popupcahtH">
           <h1 id="ratingMsg"> </h1>
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
