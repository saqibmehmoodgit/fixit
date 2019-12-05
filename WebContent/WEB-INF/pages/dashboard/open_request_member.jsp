<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css"
	href="../css/jquery.datetimepicker.css"/ >
<script src="../js/jquery.datetimepicker.full.min.js"></script>
<title>ERPfixers | Open Requests</title>
<script>
var req;
var chatHMax = 0;
var chatH = 0;
var chatHArr = [];
var senchatScroll = false;
var purchaseCreditViaApprove = false;
var purchaseCreditViaEdit = false;
var appliedFixerId = 0;
chatHArr.push(0);
	 var catJson = ${allCategoryJson};
	 var allCategory = ${allCategoryJson};
	 var parentCatJson = ${parentCategoryJson};
	 var myUserJson = ${myUserJson};
	var queryContent = '';
	var userSource = ${myUser.source};
	var trackCredit = '${myUser.trackCredit}';
	var reqyestAjax = null;
    var indexToHide = -1;
    var points = ${points};
    indexTohideSearchFixer = -1;
$(window).load(function(){
$('.textauto').on( 'keydown', 'textarea', function (e){
    $(this).css('height', 'auto' );
    $(this).height( this.scrollHeight );
});
$('.textauto').find( 'textarea' ).keydown();
});
</script>



<script>
var isMobile = false;
$(document).ready(function(){

	// device detection
	if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent) 
	    || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0,4))) isMobile = true;
	
if (userSource == 1 && trackCredit == 'N'){
		
		$('#asugCredits').css('display', 'block');
	}
	else{
		$('#asugCredits').css('display', 'none');
	}
	 $(document).find('input').keypress(function(e){
		
		    if ( e.which == 13 ) // Enter key = keycode 13
		    {
		        $(this).next().focus();  //Use whatever selector necessary to focus the 'next' input
		        return false;
		    }
		}); 
	 
		setInterval(checkVisibility, 10000);

		 $('#chatbgBody').on('scroll',function(){
	    	 var h =  $('#chatbgBody').scrollTop();
	    	 chatH = h;
	    	 chatHArr.push(h);
	    	  
	      });
		 
		 
		 
});


var client = new XMLHttpRequest();
var clientChatDoc = new XMLHttpRequest();

var userId = ${myUser.userId};
var fixerId = 0;

var virtualFileName = '';
var queryHashCodeId = '';
var fixerIdForChat = '';
var currentPage = 1; 
var searchedText = '';



	
	
	
function setCurrentDate(){
	var currentDate = new Date();
	var month = currentDate.getMonth()+1;
	var day = currentDate.getDate();
	var year = currentDate.getFullYear();
	var hour = currentDate.getHours();
    if (hour < 10)
	    hour = "0"+hour;
    

	var min = currentDate.getMinutes();
	if (min < 10)
	    min = "0"+min;
	var setDate = month+'/'+day+'/'+year;
	//var setTime = hour + ':' + min;
	var setTime ;
	if(hour>=12)
		{
			if(hour>12)
				{
					setTime = (hour-12)+ ':' + min+' pm';
				}
			else
				{
					setTime = hour+ ':' + min+' pm';
				}
		}
	else
		{
			setTime = hour+ ':' + min+' am';
		}
     return setDate;
}



function addhoursDateTime(value,index){
	
	var add12Hrs;
	var date = $('#datetimepicker1').val();
	var time = $('#datetimepicker2').val();
	
	 /* var splitArr = date.split('/');
	 date = splitArr[1]+'/' + splitArr[0] +'/' + splitArr[2] ; */
	 
	var datetime = new Date(date+' '+time);
	
	 add12Hrs = datetime.getHours() + value;

	datetime.setHours(add12Hrs);

	// format the output
	var month = datetime.getMonth()+1;
	var day = datetime.getDate();
	var year = datetime.getFullYear();

	var hour = datetime.getHours();
    if (hour < 10)
	    hour = "0"+hour;
    

	var min = datetime.getMinutes();
	if (min < 10)
	    min = "0"+min;
	

	var sec = datetime.getSeconds();
      if (sec < 10)
	    sec = "0"+sec;

	// put it all togeter
	var dateTimeString = month+'/'+day+'/'+year+' '+hour+':'+min+':'+sec;
	var setDate = month+'/'+day+'/'+year;
	//var setTime = hour + ':' + min;
	var setTime ;
	if(hour>=12)
		{
			if(hour>12)
				{
					setTime = (hour-12)+ ':' + min+' pm';
				}
			else
				{
					setTime = hour+ ':' + min+' pm';
				}
		}
	else
		{
			setTime = hour+ ':' + min+' am';
		}
	 
    
   
     
     
     var currentDate = setCurrentDate();
 	// var splitWithSlash = currentDate.split('/');
 	 var bool = validateDate(currentDate, setDate);
 	console.log(index);
 	 
 	 if(bool){
 	 
 		 $('#datetimepicker1').val(setDate);
 	     $('#datetimepicker2').val(setTime);
 	    if(index != -1){
 	     $('#queryDeadlineDate' + index).val(dateTimeString);
 	    }
 	} 
 	 else{
 		 
	     $('#queryDeadlineDate' + index).val(currentDate + ' ' +time+ ':'+ '00');
  
 		 
 	 }
 	  
}
function validateDate(current , queryDate ){
	
	if( Date.parse(current) <= Date.parse(queryDate)){
	return true;
	}
	else {
		return false;
	}
	
}


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
	var doc = $("#documentUploadChat").val();
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
	  uploadChatDocument(queryHashCodeId);
	  $('.closeMessage').css("pointer-events", "none");

}

function uploadChatDocument(queryHashCode)
{
	
	//$("#uploadIcon").addClass('fa fa-spinner fa-spin');
 var file = document.getElementById("documentUploadChat");
 document.getElementById("chatTextArea").disabled = true;
 //document.getElementById("sendMessageButton").disabled = true;
 $('#documentUploadChat').attr('disabled', 'true');

 / Create a FormData instance /
 var formData = new FormData();  
 formData.append("file", file.files[0]);
 var id=$('#queryId').val();
 formData.append("queryId",queryHashCode);
 clientChatDoc.open("post", "../member/uploadDocDetailpage", true);
 clientChatDoc.send(formData);
	}
	
clientChatDoc.onreadystatechange=function()
{
	
if (clientChatDoc.readyState==4 && clientChatDoc.status==200)
{
	var json=	clientChatDoc.responseText;
    var str = "DOCTYPE html";
	if(json.indexOf(str) != -1){
		var file = document.getElementById("documentUploadChat");
		
		virtualFileName = file.files[0].name;
		
	}else{
	
	virtualFileName=json;
	}

if(virtualFileName != null && virtualFileName !=''){
	
	saveDoc();
	
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



function saveDoc(){
		
	var clientChat = new XMLHttpRequest();
	var file = document.getElementById("documentUploadChat");
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
			
			var json = clientChat.responseText;
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
		$('#documentUploadChat').val("");
		  document.getElementById("chatTextArea").disabled = false;
		  $('#documentUploadChat').removeAttr('disabled');

		  $('.closeMessage').css("pointer-events", "auto");
		}
}


function sendChatmessage(queryHashCode,customerId, fixerId, chatFromTextArea){
	senchatScroll = true;
	$('#chatload').css('display','block');
	$('.closeMessage').css("pointer-events", "none");
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
				   var successResponse = response.result.result;
				   
				   if(successResponse == 'success'){
					   
					   var messageSet = response.result.messagesSet;
				   setchatMessages(messageSet);
				   }
					$('#chatload').css('display','none');
					  $('.closeMessage').css("pointer-events", "auto");

			   }); 
		
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

function sendBtnClick(){
	
	
	var chatTextArea = $('#chatTextArea').val();
	if(chatTextArea != null && chatTextArea.trim() !=''){
    	sendChatmessage(queryHashCodeId,userId, fixerIdForChat, chatTextArea);
	}
	$('#chatTextArea').val('');
	chatTextArea = '';
	


}
        var currentOpenIndex = -1;
		function moreBtnclick(id,queryId,totalcount, queryHashCode,queryCredits){
			 currentPage = 1;
			if(currentOpenIndex!=id)
				{
					currentOpenIndex=id;
					senchatScroll = true;
					originalCategory = new Array();
					
					selectedFixers = new Array();
					selectedFixers = [];
					currentIndex = id;
					
					queryHashCodeId =queryHashCode;
				    $('.popupRfixed').css('display', 'none')
		            $('.requestsbg').css('opacity','0.5');
		            $('#requestsbg'+id).css('opacity','1');
					$('.CloseDetail').css('bottom','55px');
					
					for(var i=0;i<totalcount;i++)
						{
						 $("#Expand-Collaps"+ i).css({"height":"133px","overflow":"hidden"});
						 
						 
						 $('#titleHead'+ i).removeClass(
					      'titleHeadedit').addClass(
					      'titleHead');
						  $('#titleContent'+ i).removeClass(
					      'titleContentEdit').addClass(
					      'titleContent'); 
						   $('#titleHead' + i).attr('readonly',
						           true);
						  $('#titleContent' + i).attr('readonly',
						           true); 
						    $("#CloseDetail"+ i).hide();
						    $("#MoreDetail"+ i).show();
							$(".clientConver").hide();
							$(".account"+i).hide();
							$(".duration").css({"bottom":"20px"});
							$("#account"+i).hide();
							  $("#moreBlock"+i).empty();
							  $("#account"+i).empty();
							onClearFilterClick(i);
						}
					
					var listQueries=<c:out default="[]" escapeXml="false" value="${jsonObject}" />;
				    
				    $("#Expand-Collaps"+ id).css({"height":"auto","overflow":"visible"});
				    $("#CloseDetail"+ id).show();
				    
				    $("#interested_fixers" + id).css('display','block');
				    
				    $("#MoreDetail"+ id).hide();
				    $("#clientConver").show();
				    $(".account"+id).show();
				    $(".duration").css({"bottom":"55px"});
				    $("#account"+id).show();
				  
				    setInterestedFixers(queryId, queryHashCode,id,queryCredits);
				}
		    
		}
		
		
		function onFilterClick(id){
	        $("#ParentCategory"+id).slideDown();
	        $("#SearchIndustry"+id).slideDown();
	        $("#SearchRating"+id).slideDown();
	        $("#SearchCuntry"+id).slideDown();
	        $("#childCatDropDownList"+id).slideDown();
	        $("#filter"+id).css({"display":"none"});
	        $("#ClearFilter"+id).css({"display":"block"});
	    }
		function onClearFilterClick(id){
	        $("#ParentCategory"+id).slideUp();
	        $("#childCatDropDownList"+id).slideUp();
	        $("#SearchCuntry"+id).slideUp();
	        $("#SearchIndustry"+id).slideUp();
	        $("#SearchRating"+id).slideUp();
	        $("#SearchCuntry"+id).slideUp();
	        $("#filter"+id).css({"display":"block"});
	        $("#ClearFilter"+id).css({"display":"none"});
	        
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
		 					
			 					$("#fav"+index).addClass('fixertag  green');
			 					document.getElementById('fav'+index).setAttribute('onclick','makeUserUnFav('+userId+',\''+index+'\')');
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

			 				document.getElementById('fav'+index).setAttribute('onclick','makeUserFav('+userId+',\''+index+'\')')
			 					$("#fav"+index).removeClass('fixertag  green');
			 					$("#fav"+index).addClass('fixertag');
			 				}
			 				
			 		   });
			} 
		 
		function setInterestedFixers(queryId,queryHashCode,index,queryCredits)
		{
			
			  var queryid = queryId;
			  var userId = ${myUser.userId};
			  $.ajax({
					method : "POST",
				    url : "../member/interestedFixers",
				    data : {
				    	
				    	userId :userId,
				    	queryId:queryid
				    }
				   }).done(function(response) {
						
					   
					setResponseInDiv(response,queryHashCode,index,queryCredits);
					createCategoryPopUp(index);
					generateHtml(response.result.queryData,index,response.result.countryList,response.result.myCountry,queryId);
					$(".catIcon").css("display","none");
					addEditButton(response.result.queryData,index);
					  
					})
			  .fail(function( jqXHR, textStatus, errorThrown) {  console.log(jqXHR);
		        console.log(textStatus);
		        console.log(errorThrown); });

		}

		function setResponseInDiv(response,queryHashCode, index,queryCredits)
		{
			
			$('#interested_fixers'+ indexToHide).css('display','none');
			$('#interested_fixers'+ index).css('display','block');

			$("#interested_fixers1_" + index).empty();	
				
			
			 if(response.result.fixersList.length==0){
				 var noFixerSpan = '<div class="nodatamsg_intrested_fixer"> No Fixers have applied for this request. </div>';
				
				 $("#interested_fixers1_" + index).append(noFixerSpan);	
				
			 }
			
			
			var position = -1;
			 var appliedFixerId  = getParameterByName('appliedFixer');
			 var payStatus = getParameterByName('payStatus');
			 if(senderId == null){
				 senderId = appliedFixerId;
			 }
			 for(var i=0;i<response.result.fixersList.length;i++){
				 
				 if(triggerAutomatic || appliedFixerId!=null)
					 {
					 
						 if(response.result.fixersList[i].userFixer.userId==senderId)
							 {
							 	position = i;
							 }
					 }
				 
			var catList = '';
			for(var j=0;j<response.result.fixersList[i].userFixer.categoryList.length;j++)
				{
				catList = catList + response.result.fixersList[i].userFixer.categoryList[j] + ', ';
				}
			catList = catList.substring(0, catList.length - 2);
			var companyName = '';
			
			if(response.result.fixersList[i].userFixer.companyName != null)
				companyName = response.result.fixersList[i].userFixer.companyName;
			
			 var html ='<div class= "clientDetail" id="clientDetail'+i+'"><input type="hidden" id="interestedFixerId'+response.result.fixersList[i].userFixer.userId+'" value="'+response.result.fixersList[i].userFixer.userId+'"/>';
			 
			 if(response.result.fixersList[i].unreadCounts >0){
				 if(response.result.fixersList[i].unreadCounts == 1)
				  html+= '<span  onclick="showChatMesg(\''+queryHashCode+'\','+response.result.fixersList[i].userFixer.userId+', \''+response.result.fixersList[i].userFixer.firstName+'\','+i+','+response.result.queryId+')" style="cursor:pointer;" class="chatmsgnumber" data-toggle="tooltip" title="You have '+response.result.fixersList[i].unreadCounts +' new message" id="chatIntersFixers'+i+'">'+response.result.fixersList[i].unreadCounts +'</span>';
				 if(response.result.fixersList[i].unreadCounts > 1)
					  html+= '<span  onclick="showChatMesg(\''+queryHashCode+'\','+response.result.fixersList[i].userFixer.userId+', \''+response.result.fixersList[i].userFixer.firstName+'\','+i+','+response.result.queryId+')" style="cursor:pointer;" class="chatmsgnumber" data-toggle="tooltip" title="You have '+response.result.fixersList[i].unreadCounts +' new messages" id="chatIntersFixers'+i+'">'+response.result.fixersList[i].unreadCounts +'</span>';
				
			 }
			 
			  html += '<div class="clientHead">';
			  if(response.result.fixersList[i].userFixer.profilePhoto == ''){
			  html +=   '<img onclick="detailFixerClick('+index+','+i+', '+response.result.fixersList[i].userFixer.userId+')" src="../images/profile.png" onerror= "this.src =\'../images/profile.png\'">';
			
			  }else{
				  html +=   '<img onclick="detailFixerClick('+index+','+i+', '+response.result.fixersList[i].userFixer.userId+')" src="'+response.result.fixersList[i].userFixer.profilePhoto+'" onerror= "this.src =\'../images/profile.png\'">';

				  
			  }
			  if(response.result.fixersList[i].userFixer.country == ''){
				  
				  html +=  '<img src="../images/profile.png" >';

				  
			  }else{
				  html +=  '<img src="../flags/'+response.result.fixersList[i].userFixer.country+'.png" class="CuntryFlag"  onerror= "this.src =\'../flags/United States of America(USA).png\'">';

				  
			  }
			  html +=	'<h1><span  onclick="detailFixerClick('+index+','+i+', '+response.result.fixersList[i].userFixer.userId+')" class="name">'+response.result.fixersList[i].userFixer.firstName+'&nbsp;'+response.result.fixersList[i].userFixer.lastName+'</span>';
					
			  if(response.result.fixersList[i].userFixer.favouriteFixerStatus == 'F'){
					
					/* html +=	'<span class="fixertag  green" > <i data-icon="k" class="icon"></i><span>Favorite</span>';  */
					html +=	'<span id="fav'+index+''+i+'" class="fixertag  green" onclick="makeUserUnFav('+response.result.fixersList[i].userFixer.userId+',\''+index+''+i+'\')"> <i data-icon="k" class="icon"></i><span id="favtext'+index+''+i+'">Favorite</span></span>'; 
				}else{
					 html +=	'<span id="fav'+index+''+i+'" class="fixertag"  onclick="makeUserFav('+response.result.fixersList[i].userFixer.userId+',\''+index+''+i+'\')"> <i data-icon="k" class="icon"></i> <span id="favtext'+index+''+i+'" style="display:none;">Favorite</span></span>'; 
					/* html +=	'<span class="fixertag"> <i data-icon="k" class="icon"></i> <span style="display:none;">Favorite</span>'; */
				}
					html +=		'</span> <span onclick="detailFixerClick('+index+','+i+', '+response.result.fixersList[i].userFixer.userId+')" >'+companyName+'</span>'+
					'</h1>'+
					'<span class="ratingC"> <i id = "rating1_'+index+'_'+i+'" data-icon="l" class="icon"></i>'+
					'	<i id = "rating2_'+index+'_'+i+'" data-icon="l" class="icon"></i> <i id = "rating3_'+index+'_'+i+'" data-icon="l" class="icon"></i>'+
					'	<i id = "rating4_'+index+'_'+i+'" data-icon="l" class="icon"></i> '+
					'	<i id= "rating5_'+index+'_'+i+'" data-icon="l" class="icon"></i>'+
					'</span> <span style="display: block;" class = "details" id="details_'+index+'_'+i+'" onclick="detailFixerClick('+index+','+i+', '+response.result.fixersList[i].userFixer.userId+')">Details</span>'+
					'<span style="display: none;" class="closeC" id="closeC_'+index+'_'+i+'" onclick="closeFixerClick('+index+','+i+')">Close</span>'+
				'</div>'+
				'<div style="display: none;" class="clientB" id="clientB_'+index+'_'+i+'">'+
					'<div class="clientD">'+
					'	<span class="location">Location: <strong>'+response.result.fixersList[i].userFixer.city+', '+response.result.fixersList[i].userFixer.country+'</strong></span>'+
						'<span class="linkdin">';
						if((response.result.fixersList[i].userFixer.linkedinProfile).indexOf("http")>-1){
			html += '	<img src="${pageContext.request.contextPath}/images/in.png" alt=""> <a  target = "_blank" href = "'+response.result.fixersList[i].userFixer.linkedinProfile+'">'+response.result.fixersList[i].userFixer.linkedinProfile+'</a></span>';
						}else{
							html += '	<img src="${pageContext.request.contextPath}/images/in.png" alt=""> <a  target = "_blank" href = "https://'+response.result.fixersList[i].userFixer.linkedinProfile+'">'+response.result.fixersList[i].userFixer.linkedinProfile+'</a></span>';

						}
			html +=		'<div class="clearfix"></div>'+
						'<span class="time">Fixer Since: <strong>'+response.result.fixersList[i].userFixer.fixersSince+'</strong></span> '+
						'<span class="time">Time Zone: <strong>'+response.result.fixersList[i].userFixer.timeZone+'</strong></span> '+
						'<p class="overview">'+response.result.fixersList[i].userFixer.overview+'</p>'+
						'<span class="categorie">Categories: <strong>'+catList+'</strong></span><br>'+
						/* '<span class="time">Fixed Requests: <strong>'+response.result.fixersList[i].userFixer.fixedCounts+'</strong></span>'+
						'<span class="time">Resolved within Deadline: <strong>'+response.result.fixersList[i].userFixer.fixedUnderdeadline+'%</strong></span>'+
						'<span class="time">Last Active: <strong>'+response.result.fixersList[i].userFixer.lastLogin+'</strong></span>'+ */
				'	</div>'+
					
					'<div class="proBtn"><a href="javascript:void(0)" class="message" onclick="showChatMesg(\''+queryHashCode+'\','+response.result.fixersList[i].userFixer.userId+', \''+response.result.fixersList[i].userFixer.firstName+'\','+i+','+response.result.queryId+')" id="messageInterestedFixer'+i+'">Message</a>'+
					'<a href="javascript:void(0)" onclick="approveFixerClick('+response.result.fixersList[i].userFixer.userId+','+response.result.queryId+','+i+','+queryCredits+')" class="request" id="approveRequest'+i+'" >Approve Fixer</a>'+
					'<a href="javascript:void(0)" id="denyAppliedFixer_'+i+'" onclick = "denyAppliedFixer('+response.result.queryId+','+response.result.fixersList[i].userFixer.userId+', \'clientDetail'+i+'\', '+response.result.fixersList.length+', '+i+')" class="request accept">Remove</a></div>'+

							'</div></div>' ;
				
				$("#interested_fixers1_"+ index).append(html);
				
				for(var j=1;j<=response.result.fixersList[i].userFixer.fixerRating;j++)
					{
					$("#rating"+j+"_"+index+"_"+i).attr("data-icon", "m");
					}
				
			 	
				
			} 
			 //alert("automatic"+triggerAutomatic+"position"+position);
			 if(triggerAutomatic && position!=-1)
				 {
				 	triggerAutomatic = false;
				 	detailFixerClick(index,position, response.result.fixersList[position].userFixer.userId);

				 	if(appliedFixerId == null){
				 	showChatMesg(queryHashCode, response.result.fixersList[position].userFixer.userId, response.result.fixersList[position].userFixer.firstName,position,response.result.queryId);

				 	}
				 	else
				 	{
				 		if(payStatus!=null)
				 		{
				 			if(payStatus=='true')
				 				{
				 				    jQuery('#payStatusMsg').text('Success! You now have ' +points + ' credits available to use for your request.');
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
				 				//alert('Success! You now have ' +points + ' credits available to use for your request.');
				 				}
				 		}
				 	}/* else{
				 		//var interestedFixerId = $('#interestedFixerId' + appliedFixerId);
				 		var parentDiv = $('#interestedFixerId' + appliedFixerId).parent().attr('id'); 
						
						console.log(parentDiv);
					
						var onclickFuntion = $('#'+parentDiv +' span[id^="details_"]').attr('onclick');
						debugger;
						console.log(onclickFuntion);
						var func = new Function(onclickFuntion);
						func();
				 	} */
				 }
			 
			 indexToHide = index;
				

		}
	
		function showChatMesg(queryHashCode, fixerId, name,index,queryId){
			fixerIdForChat = fixerId;
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
						  
						   $('#chatIntersFixers'+index).hide();
					  
					   }
				   });  
			
			name =  name.substr(0,1).toUpperCase() + name.substr(1)
			$('#chatUserName').text('Message to ' + name); 
			 

		
			queryHashCodeId  = queryHashCode; 
			 $.ajax({
				method : "POST",
			    url : "../member/showChatMessages",
			    data : {
			    	queryHasCode : queryHashCode,
			    	fixerId : fixerId
			    }
			   }).done(function(response) {
				   
				   var successResponse = response.result.status;
				   
				   if(successResponse == 'success'){
					   
					   var messageSet = response.result.messagesSet;
				   setchatMessages(messageSet);
				   }
					
			   }); 
			$('.messageChat').css('display', 'block');
		
      /*  $("#chatsend").bind('click', function(){
		    	
				var chatTextArea = $('#chatTextArea').val();
				if(chatTextArea != null && chatTextArea.trim() !=''){
			    	sendChatmessage(queryHashCodeId,userId, fixerId, chatTextArea);
				}
				 $('#chatTextArea').val('');
				chatTextArea = '';
			}); */
			
			/* $("#chatTextArea").keyup(function(event){

				var chatFromTextArea = $("#chatTextArea").val();
				    if(event.keyCode == 13){
				    	
				    	
				    	if(fixerId !=0 && queryHashCodeId !=''){
				    	sendChatmessage(queryHashCodeId,userId, fixerId, chatFromTextArea);
				    	}
				    }
				});	 */
			/* $('#documentUploadChat').bind('change', function(){
				
				uploadChatDocument(queryHashCodeId);
				
				
			});	 */
			
		}

		function closeBtnclick(id){
			currentOpenIndex = -1;
            $('.requestsbg').css('opacity','1');
           
		    $("#Expand-Collaps"+ id).css({"height":"133px","overflow":"hidden"});
		    $('#titleHead'+ id).removeClass(
		      'titleHeadedit').addClass(
		      'titleHead');
			  $('#titleContent'+ id).removeClass(
		      'titleContentEdit').addClass(
		      'titleContent'); 
			   $('#titleHead' + id).attr('readonly',
			           true);
			  $('#titleContent' + id).attr('readonly',
			           true); 
			  
			  
		    $("#CloseDetail"+ id).hide();
		    $("#MoreDetail"+ id).show();
			$(".clientConver").hide();
			$(".account"+id).hide();
			$(".duration").css({"bottom":"20px"});
			$("#account"+id).hide();
			   $("#interested_fixers"+id).css('display','none');
			   $("#interested_fixers"+indexToHide).css('display','none');
			   $("#moreBlock"+id).empty();
		}
		
		
		function approveRequest(userId,queryId)
		{
			$("[onclick]").removeAttr("onclick"); 
			window.location.replace("../member/approveFixer?userId="+userId+"&queryId="+queryId);
		}
		
		function approveFixerClick(userId,queryId,index,queryCredits){
		
			purchaseMoneyClickEvent();
			
			 if(points > 0 && points >= queryCredits){
				 $("[onclick]").removeAttr("onclick"); 
					window.location.replace("../member/approveFixer?fixerId="+userId+"&queryId="+queryId+"&queryPoints="+queryCredits);
				
			} else{
				appliedFixerId = userId;
				purchaseCreditViaApprove = true;
				$('.custm_popup_resolve').css('display', 'block');
				$('body').css('overflow', 'hidden');	
				return false;
			}
			
		
		}
		
		function denyAppliedFixer(queryId,fixerId, divId, appliedFixerLength, index){
			$('#denyAppliedFixer_'+index).css("pointer-events", "none");
			$.ajax({
				
				method : "POST",
				url : "../member/denyAppliedFixer",
				data :{
				queryId:queryId,
				fixerId:fixerId
				
				}
			
			
			   
			}).done(function(response){
			
				var resp = response.status;
				if(resp == 'success'){
					$('#'+ divId).remove();
					var interestedFixersCount = parseInt($('#interestedFixersCount'+ index ).text());

					if(appliedFixerLength == 1){
						
						 var noFixerSpan = '<div class="nodatamsg_intrested_fixer"> No Fixers have applied for this request. </div>';
							
						 $('#interested_fixers1_' + index).append(noFixerSpan);	
						 $('#interestedFixersCount'+ index ).remove();
						
					}
					else{
						$('#interestedFixersCount'+ index ).text(appliedFixerLength - 1);
					}
				}
				
			})
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
						<div class="nodatamsg">There are currently no Open requests.</div>
					</c:if>
					<div class="row">

						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

							<c:if test="${msgType == 'success'}">
								<div style="margin-top:0px; margin-right: 15px;"
									class="alert alert-success fade in col-lg-6 col-md-6 col-sm-12 col-xs-12">
									<a title="close" aria-label="close" data-dismiss="alert"
										class="close" href="#">×</a> ${message}
								</div>
							</c:if>
							<div class="">

								<c:forEach var="queryList" items="${queryList}"
									varStatus="thequeryList">
									<form:form role="form" style="display: block; clear:both;"
										id="contact-form${thequeryList.index}" method="post"
										action="askQuestion" modelAttribute="query"
										commandName="query" enctype="multipart/form-data">

										<div id="requestsbg${thequeryList.index}"
											class="col-lg-6 col-md-6 col-sm-12 col-xs-12 requestsbg">
<input type="hidden" id="${queryList.queryId}">
											<c:if test="${queryList.interestedFixersCount ne 0}">
												<c:choose>
											
														<c:when test="${queryList.interestedFixersCount eq 1}">
														     <span data-toggle="tooltip" title="You have ${queryList.interestedFixersCount} interested Fixer." class="chatmsgnumber" id="interestedFixersCount${thequeryList.index}" onclick="moreBtnclick(${thequeryList.index},${queryList.queryId},${queryCounts},'${queryList.hashcode}', '${queryList.queryCredits}')" >${queryList.interestedFixersCount}</span>
											
														</c:when>
											
														<c:otherwise>
											
																<span data-toggle="tooltip" title="You have ${queryList.interestedFixersCount} interested Fixers." class="chatmsgnumber" id="interestedFixersCount${thequeryList.index}"  onclick="moreBtnclick(${thequeryList.index},${queryList.queryId},${queryCounts},'${queryList.hashcode}', '${queryList.queryCredits}')">${queryList.interestedFixersCount}</span>
											
														</c:otherwise>
														
											</c:choose>
											</c:if>

											<div class="Expand-Collaps"
												id="Expand-Collaps${thequeryList.index}">
												<div class="time">
													<h1>${queryList.queryCredits}</h1>
													<span> <c:choose>
															<c:when test="${queryList.queryCredits eq 1}">
												Credit
												</c:when>
															<c:otherwise>
												Credits
												</c:otherwise>
														</c:choose>
													</span>
												</div>
												<div class="more-less">
													<div class="more-block" style="position: relative">
														<span class="alertF"
															id="querytitleAlert${thequeryList.index}"
															style="display: none">Please enter the title of
															the request.</span> <input name="queryTitle" type="hidden"
															id="titleHead${thequeryList.index}"
															value="${queryList.queryTitle}" class="titleHead">
														<H1 id="headingQueryTitle${thequeryList.index}">${queryList.queryTitle}
															<div class="half"></div>
														</H1>

														<div class="textauto" id="textAuto${thequeryList.index}"
															style="position: relative">
															<span class="alertF"
																id="queryDescAlert${thequeryList.index}"
																style="display: none"> Please enter the
																description of the request.</span>
															<textarea name="queryContent" rows=""
																id="titleContent${thequeryList.index}"
																class="titleContent" readonly>${queryList.queryContent}</textarea>
														</div>

														<input type="hidden" name="queryId"
															value="${queryList.queryId}"
															id="queryId${thequeryList.index}"> <input
															type="hidden" name="oldCredits"
															value="${queryList.queryCredits}"
															id="oldCredits${thequeryList.index}"> <input
															placeholder="" name="fixerId" value="0"
															id="fixerId${thequeryList.index}" type="hidden">


														<input placeholder="" name="fixersIds"
															value="${queryList.fixersIds}"
															id="fixersIds${thequeryList.index}" type="hidden">

														<input type="hidden" name="queryDeadlineDate"
															id="queryDeadlineDate${thequeryList.index}"> <input
															type="hidden" name="userId" value="${myUser.userId}"
															id="userId${thequeryList.index}">

														<div id="moreBlock${thequeryList.index}"></div>
														<%-- <input type="hidden" name="queryMode" value="EDIT"
															id="queryMode${thequeryList.index}"> <input
															placeholder="" name="fixersNames"
															value="${query.fixersNames}"
															id="fixersNames${thequeryList.index}" type="hidden"> --%>



													</div>
												</div>



											</div>
											<span class="duration green">${queryList.timeDiff}
												ago... </span> <span class="MoreDetail"
												id="MoreDetail${thequeryList.index}"
												onclick="moreBtnclick(${thequeryList.index},${queryList.queryId},${queryCounts},'${queryList.hashcode}', '${queryList.queryCredits}')">More</span>
											<span class="CloseDetail"
												id="CloseDetail${thequeryList.index}"
												onclick="closeBtnclick(${thequeryList.index})">Close</span>
											<div class="account" id="account${thequeryList.index}"
												style="border: 0px;"></div>


											<div class="popupRfixed"
												id="popupRequestFixed${thequeryList.index}"
												style="display: none;">
												<div class="chatDiv">
													<h1>Are you sure?</h1>
													<div class="chatbody">
														<p>
															Are you sure you want to delete this <strong>Request?</strong>
														</p>
													</div>
													<div class="proBtn">
														<span class="request width100per"
															onclick="cancelDeleteBtnclick(${thequeryList.index})">Cancel</span>
														<span class="accept width100per"
															id="delete${thequeryList.index}">Yes, delete this
															Request</span>
													</div>
												</div>
											</div>



										</div>

										<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"
											id="interested_fixers${thequeryList.index}"
											style="display: none">
											<div class="clientBox">
												<div class="clientbg">
													<h1>Interested Fixers</h1>
													<div id="interested_fixers1_${thequeryList.index}"></div>
												</div>
											</div>
										</div>

									</form:form>


									<!-- 	<script>
								
								var index = ${thequeryList.index};
								var selectedId = ${selectedQueryId};
								var queryId = ${queryList.queryId};
								var queryId = ${queryList.queryId};
								var size = ${queryCounts};
								
								if(queryId == selectedId){
									var queryHashCode = '${queryList.hashcode}';
								  moreBtnclick(index,queryId,size,queryHashCode); 
								}
								</script> -->




								</c:forEach>




								<!-- // chat // -->
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

														<div id="chatload" class="chatLoad">Please wait
															while loading...</div>

														<div class="chatbgFooter">
															<textarea  placeholder = "Type here..."  id="chatTextArea"></textarea>
															<span> <input value="" onchange="uploadBtnClick()"
																id="documentUploadChat" type="file"> <img
																src="../images/attachment.png" alt="">
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
								<!--// chat //  -->

							</div>
						</div>


						<!-- intereted fixer div -->

						<!-- div class="col-lg-5 col-md-5 col-sm-12 col-xs-12"
							id="interested_fixers" style="display: none">
							<div class="clientBox">
								<div class="clientbg">
									<h1>Interested Fixers</h1>
									<div id="interested_fixers1"></div>
								</div>
							</div>
						</div -->


					</div>
				</div>
			</div>
		</section>

	</div>

	<div class="custm_popup_resolve">
		<div class="resolvedRequests wow fadeIn" data-wow-delay="0.5s">
			<span class="cclose"><i data-icon="y" class="icon"></i></span>
			<div class="HourPacks" style="display: block;">
				<div class="hourClose">
					<span id="icon"><i data-icon="y" class="icon"></i></span>
				</div>
				<h1>Hour Packs</h1>
				<p>Purchase larger bundles and save.</p>
				<p id="asugCredits" style="display: none">You get 1 free credit
					as an ASUG member or affiliate</p>
				<div class="hourpackDiv">
					<div id="purple" class="hp-cell">
						<div class="purple">
							<span class="grayN">5</span> <span class="price"> <a
								id="money_575" href="javascript:void(0)"> $575 </a>
							</span>
						</div>
					</div>
					<div id="purpleDark" class="hp-cell">
						<div class="purpleDark">
							<span class="whiteN">10</span> <span class="price"> <a
								id="money_1125" href="javascript:void(0)">$1,125</a>
							</span>
						</div>
					</div>
					<div id="green" class="hp-cell">
						<div class="green">
							<span class="grayN">20</span> <span class="price"> <a
								id="money_2200" href="javascript:void(0)">$2,200</a>
							</span>
						</div>
					</div>
					<div id="blue" class="hp-cell">
						<div class="blue">
							<span class="whiteN">40</span> <span class="price"> <a
								id="money_4300" href="javascript:void(0)">$4,300</a>
							</span>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
				<div class="btnc">
					<button class="cancel">
						<i data-icon="y" class="icon"></i> Cancel
					</button>
					<button id="purchaseCredits" class="hpbutton">
						Purchase <i data-icon="K" class="icon"></i><strong>$<span
							id="money">0</span></strong>
					</button>
				</div>
			</div>

		</div>
	</div>


	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/pages/openRequestCategory.js"></script>
	<script type="text/javascript">

  

 function detailFixerClick(index, id, fixerId)
 {
	 
		fixerIdForChat = fixerId;

	 $('#clientB_'+index+'_'+id).slideDown();
     $('#details_'+index+'_'+id).hide();
     $('#closeC_'+index+'_'+id).show(); 
 }
 
 function closeFixerClick(index,id)
 {
	 $('#clientB_'+index+'_'+id).slideUp();
     $('#details_'+index+'_'+id).show();
     $('#closeC_'+index+'_'+id).hide(); 
 }
 
 
 
 function detailFixer(id)
 {
	 closeFixer(indexTohideSearchFixer);
	  $('.clientHead').css('opacity','0.5');
	  $('#clientHead' +id).css('opacity','1');
	 $('#clientB'+id).slideDown();
     $('#details'+id).hide();
     $('#closeC'+id).show(); 
     indexTohideSearchFixer = id;

 }
 
 function closeFixer(id)
 {
	  $('.clientHead').css('opacity','1');

	 $('#clientB'+id).slideUp();
     $('#details'+id).show();
     $('#closeC'+id).hide(); 
 }
 function openToAllFixerClicked(id)
 {
	 $("#filterDivsearch").slideUp();
	 $("#fixersIds"+id).val('');
	 $("#updateBtn").text('Update Request');
 }
 
 
 function submitRequestToFixer(id , fixerId){
	 selectedFixers.push(fixerId);
	 $("#fixersIds"+id).val('');
	 var fixerIdsArray = document.getElementById("fixersIds"+id);
	 fixerIdsArray.value=selectedFixers;
 }
 
 function sendSpecificFixerClick(id,searchFieldText,categoryId,selectedRate,countryName,queryId)
 {
	 
	 
	 $("#filterDivsearch").slideDown();
	  $.ajax({
			method : "POST",
		    url : "${pageContext.request.contextPath}/member/fixerList",
		    data : {
		    searchFieldText: searchFieldText ,
		    catId: categoryId ,
		    selectedRate : selectedRate,
		    country : countryName,
		    index : id,
		    editQueryId : queryId
		   
		    }
		   }).done(function(response) {
			 
				
		   });
	
 }
 
 function loadMore(id,queryId){
	 	sendSpecificFixerClick(searchedText,id,queryId);
	   
}
 
 function searchNameChanged(searchFieldText,id,queryId)
 {
	  searchedText = searchFieldText;
	  currentPage = 1;
	  $("#interestedFixers_searched" + id ).empty();
	  sendSpecificFixerClick(searchFieldText,id,queryId);
	  
 }
 function sendSpecificFixerClick(searchFieldText,id,queryId)
 {
	
	 if(reqyestAjax != null)
 		 reqyestAjax.abort();
	 
	 $("#filterDivsearch").slideDown();
	  var  categoryIds = getCheckedBoxes('categories');
	  var countryName = myUserJson.country;
	  
	  $("#fixersIds"+id).val(selectedFixers);
	  $("#updateBtn").text('Update Request ('+selectedFixers.length+')');
	  
	  reqyestAjax =  $.ajax({
			method : "POST",
		    url : "../member/fixerList",
		    data : {
		    searchFieldText: searchFieldText ,
		    categoryIds: categoryIds ,
		    country : countryName,
		    pageNo : currentPage,
		    editQueryId: queryId
		   
		    }
		   }).done(function(response) {
			   
			 currentPage = response.result.currentPage;
			
			 var len = response.result.fixers.length;
 			 if(len > 0){
 				
 				$('.requestloadmore').css('display', 'block');
 				setSearchedFixers1(response,id)
 			 }else{
 				 
 				$('.requestloadmore').css('display', 'none');
 			 }
			 
			 
				
		   });
	
 }
 


 
	
function btnEditRequestTabbed(id,catCount)
{
	lodeMoreId = 0
	$('#headingQueryTitle' + id).css("display","none")
	$(".catIcon").css("display","block");
	$('#add_more').click(function(){
		purchaseCreditViaEdit = true;
		$('.custm_popup_resolve').css('display', 'block');
		$('body').css('overflow', 'hidden');	
	});
	
	purchaseMoneyClickEvent();
	
	
	

	 
	  $('#categoriesEdit').css('display', 'block');
	  $('#editableSections').css('display', 'block');
	  $('#beforeEdit').css('display', 'none');
	  $('#afterEdit').css('display', 'block');
	  
	  
	   for(var i=0;i<catCount;i++)
		  {
		  $('#catListCross' + "_" + i).css('display', 'block');
		  } 
	  
	  
	  
	  $('#titleHead'+ id).removeClass(
      'titleHead').addClass(
      'titleHeadedit');
	  $('#titleContent'+ id).removeClass(
      'titleContent').addClass(
      'titleContentEdit'); 
	   $('#titleHead' + id).attr('readonly',
	           false);
	  $('#titleContent' + id).attr('readonly',
	           false); 
	  $('#titleHead' + id).attr('type',
	           'text');
	  
	  
}
 function purchaseMoneyClickEvent(){
	 
	 $('.cclose').click(function(){
			$('.hp-cell').css('opacity','1');

			$('.custm_popup_resolve').css('display', 'none');
			$('body').css('overflow', 'auto');	
		});
		
		$('.cancel').click(function(){
			purchaseCreditViaApprove = false;
	   	 purchaseCreditViaEdit = false;
			$('.hp-cell').css('opacity','1');

			$('.custm_popup_resolve').css('display', 'none');
			$('body').css('overflow', 'auto');	
		}); 
		$('#icon').click(function() {
			purchaseCreditViaApprove = false;
		   	 purchaseCreditViaEdit = false;
			$('.hp-cell').css('opacity', '1');
			$('.custm_popup_resolve').css('display', 'none');
			$('body').css('overflow', 'auto');
		});
		
		
	 $('#money_575').bind('click', function() {
			$('#money').text('575');
			$('.hp-cell').css('opacity','0.5');
			$('#purple').css('opacity','1');
			$('#purchaseCredits').css({
				"background" : "#b13794",
				"color" : "#fff"
			});

		});

		$('#money_1125').bind('click', function() {
			$('#money').text('1125');
			$('.hp-cell').css('opacity','0.5');
			$('#purpleDark').css('opacity','1');
			$('#purchaseCredits').css({
				"background" : "#63429a",
				"color" : "#fff"
			});

		});

		$('#money_2200').bind('click', function() {
			$('#money').text('2200');
			$('.hp-cell').css('opacity','0.5');
			$('#green').css('opacity','1');
			$('#purchaseCredits').css({
				"background" : "#28b78a",
				"color" : "#fff"
			});

		});

		$('#money_4300').bind('click', function() {
			$('#money').text('4300');
			$('.hp-cell').css('opacity','0.5');
			$('#blue').css('opacity','1');
			$('#purchaseCredits').css({
				"background" : "#1c75bc",
				"color" : "#fff"
			});

		});


		$('#purchaseCredits').bind('click', function(){
			var money = $('#money').text();
			money = money.replace(',','');
			money = parseInt(money);
			var userurl = window.location.href;
			if(userurl.indexOf('appliedFixer')>-1){
				userurl = userurl.split('&')[0];
				userurl = userurl.split('/member/')[1];
			}else{
			userurl = userurl.split('/member/')[1];
			}
			
			if (money != 0) {
				var queryId = $('#queryIdAddmore').val();
				userurl= userurl + ',queryId='+ queryId;
				 if(purchaseCreditViaApprove){
						var appliedFixerIdVal = $('#interestedFixerId'+appliedFixerId).val();
						userurl = userurl +  ',appliedFixer=' + appliedFixerIdVal;
					}
				userurl = userurl.replace('?',',');
				//userurl = userurl.replace('&',',');
				userurl = '\"'+userurl+'\"';
				var url = '';
				 
			
				url = '../member/paypal?amount=' + money +'&userUrl='+userurl;
				window.location.href= url;

				/* var win = window.open(url, '_blank');
				win.focus(); */
			}
		});				
		
	 
	 
 }

function crossCategories(id)
{
	 $('#catListCross' + id).css('display', 'block');
}


function leftCredirBtnPressed1(id, remainingCredits)
{
	 var hoursShown = $('#hours').val();
	 
	 
	 if(remainingCredits == 0)
		{
		$("#hoursAlert").css({
			"display" : "block"
		});
		$("#hoursAlert").text('You have 0 credit remaining');
		return false;
		}else{
			$("#hoursAlert").css({
				"display" : "none"
			});
		}
	
	 
	 
	 
	 if(hoursShown-1 > 0)
		 $('#hours').val(hoursShown-1);
	 addhoursDateTime(-12,-1);

}

function rightCredirBtnPressed1(id,remainingCredits,initalCredits)
{

	 var hoursShown = $('#hours').val();
	 
	 
	 if(remainingCredits == 0)
		{
		$("#hoursAlert").css({
			"display" : "block"
		});
		$("#hoursAlert").text('You have 0 credit remaining');
		return false;
		}else{
			$("#hoursAlert").css({
				"display" : "none"
			});
		}
	
	 
	 var totalCredits = parseInt(initalCredits)+parseInt(remainingCredits);
	 if((parseInt(hoursShown)+1) <= totalCredits)
		 {
		 $('#hours').val(parseInt(hoursShown)+1);
	 addhoursDateTime(12,-1);
		 }
}


function updateRequest(id)
{
	var form_id = 'contact-form' + id ;
	 $("#" + form_id).submit();
}


var client = new XMLHttpRequest();
function uploadDocument(queryId)
{
 var file = document.getElementById("documentUpload");
 / Create a FormData instance /
 var formData = new FormData();  
 formData.append("file", file.files[0]);
 var id=${myUser.userId};
 
 formData.append("userId",id);
 formData.append("queryId",queryId);
 $("#uploadLoader").css('display', 'inline-block');
 document.getElementById("documentUpload").disabled = true;

 //console.log(formData);
 client.open("post", "../member/createDocFolder", true);
 client.send(formData);
	}
	
	
client.onreadystatechange=function()
{
if (client.readyState==4 && client.status==200)
{
	var data=jQuery.parseJSON(client.response);
	var fileName = data.result.originalFileName;
	var fileUniqueCode = data.result.fileUniqueCode;
	var queryId = data.result.queryId;
	var fileUrl = data.result.fileUrl;
 	var awsFileUrl =  data.result.awsFileUrl

	document.getElementById("documentUpload").disabled = false;
	$("#uploadLoader").css('display', 'none');
	$("#documentUpload").val("");
	
	var html = '<div class = "download" id="'+data.result.fileUniqueCode+'"><span>Download:</span><a href="'+awsFileUrl+'"><input type="text" style="cursor:pointer" name = "documents" value ="'+data.result.originalFileName+'" readonly></a><i data-icon="y" class="icon" onclick = "deleteFile(\''+data.result.originalFileName+'\',\''+data.result.fileUniqueCode+'\','+data.result.queryId+')"></i></div>';	
	$("#attach").append(html);
   }
	
}


 function findCatByparentCatToShowOnList(id,queryId){
	var parentId = $('#parentCategory'+id).val();
	if(parentId=='-1'){
		$("#childCatDropDownList"+id).empty();
	}else{
	 $("#childCatDropDownList"+id).empty();
    var html = '';
      html+='<label>Child Category</label><select class="select" id="catSelection'+id+'" style="cursor:pointer" onchange="ratingChangeListener('+id+','+queryId+')"><option value = "-1">Select Child Category</option>';
      for(var i =0; i< allCategory.length;i++){
        if(allCategory[i].parentId == parentId){
	      html+='<option value="'+allCategory[i].catId+'">'+allCategory[i].catgName+'</option>';
        } 
      }
     html+='</select><i data-icon="y" class="icon" onclick="childcatIconClick('+id+','+queryId+')"></i>';
     $("#childCatDropDownList"+id).append(html); 
     ratingChangeListener(id,queryId);
	} 
 }
 
 
 
 function childcatIconClick(id,queryId)
 {
	 $("#childCatDropDownList"+id).empty(); 
	 ratingChangeListener(id,queryId);
 }
 function validationCheck( points,index,initialPoints)
	{
	 
		var date = $('#datetimepicker1').val();
		var time = $('#datetimepicker2').val();
		var querytitle = $('#titleHead'+index).val();

	
		var error = false;
		if( $("#titleHead"+index).val()=='')
			{
			$("#querytitleAlert" +index).text("Please enter the title of the request.");

			 $("#querytitleAlert"+index).css({"display":"block"});
			 error = true;
			
			}
		 if($("#titleContent"+index).val()=='')
			{
			 $("#queryDescAlert"+index).css({"display":"block"});
			 error = true;
			}
		  if(getCheckedBoxes('categories')===null){
			  $("#categoriesAlert").css({"display":"block"});
			  error = true;
		  }  
		 
		  if($("#hours").val()<1)
			  {
			  $("#hoursAlert").css({"display":"block"});
			  $("#hoursAlert").text('Proposed credits must be greater than 0');
			  error = true;
			  }
		  if($("#hours").val()>points+initialPoints)
			  {
			  $("#hoursAlert").css({"display":"block"});
			  $("#hoursAlert").text('Proposed credits must be less or equal to your available credits.');
			  error = true;
			  }
		
		  
		  if(!$('#leaverequest').is(':checked') && (selectedFixers ==null || selectedFixers.length == 0) )
			  {
			  $("#leaverequestAlert").css({"display":"block"});
			  error = true;
			  }
		 
 		  if((date == null || date =='') || (time == null || time =='')){
			  $("#dateTimeAlert").text(" Please Select date")
				  $("#dateTimeAlert").css({"display":"block"});
				  error = true;
		  }
 		  if(date != null && date.trim() !=''){
				  var bool = validateDate(setCurrentDate('') , date )
				  if(!bool){
				  $("#dateTimeAlert").css({"display":"block"});
				  $("#dateTimeAlert").text("You can not select previous date");
				  error = true;
	  }
	  
 }
 		  
 		 if(querytitle.length >100){
 			$("#querytitleAlert"+index).css({
 				"display" : "block"
 			});
 			$("#querytitleAlert"+index).text("Title length must less than 100");
 			error = true;
 		}
		  if(error == false)
			  {
				$("[onclick]").removeAttr("onclick"); 

			  addhoursDateTime(0,index);
				  if($('#leaverequest').is(':checked'))//leave request to all is clicked
				  {
					  $("#fixersIds"+index).val('');
				  }
				  else
					{
					  $("#fixersIds"+index).val(selectedFixers);
					}
			  $("#contact-form"+index).submit();
			  }
		 
		  
		
	}
 
 var selectedFixers = new Array();
	
	function submitRequestToFixer(id,firstName,lastName,index){
		 selectedFixers.push(id);
		 $("#submitBtnImpl").text('Submit Requests ('+selectedFixers.length+')');
		 $("#submitbtn"+index).text('DeSelect '+firstName+' '+lastName);
		 $("#fixersIds"+index).val(selectedFixers);
		 document.getElementById('submitbtn'+index).setAttribute('onclick','removeRequestToFixer('+id+',\''+firstName+'\',\''+lastName+'\','+index+')')
		// document.getElementById("submitbtn"+index).onclick = removeRequestToFixer(id,firstName,lastName,index);
	}
	
	function removeRequestToFixer(id,firstName,lastName,index){
		 var j = selectedFixers.indexOf(id);
		 if(j != -1) {
			 selectedFixers.splice(j, 1);
		 }
		 $("#submitBtnImpl").text('Submit Requests('+selectedFixers.length+')');
		 $("#submitbtn"+index).text('Submit request to '+firstName+' '+lastName);
		 $("#fixersIds"+index).val(selectedFixers);
		 document.getElementById('submitbtn'+index).setAttribute('onclick','submitRequestToFixer('+id+',\''+firstName+'\',\''+lastName+'\','+index+')');
	}
 
function ratingChangeListener(id,queryId)
{
	var id = id;
	var categoryId= '-1';
	var searchFieldText = $('#searchId'+id).val();
	var selectedRate = $('#selectedRating'+id).val();

	var countryName = $('#selectedCountry'+id).val();
	var parentCat = $('#parentCategory'+id).val();
	if(parentCat=='-1'){
	 categoryId = '-1';
	}
	else
		{
		categoryId = parentCat;
		var chilCat = $('#catSelection'+id).val();
		if(typeof chilCat != 'undefined' && chilCat != '-1')
			categoryId = chilCat;
		}
	sendSpecificFixerClick(id,searchFieldText,categoryId,selectedRate,countryName,queryId)
}

function deleteFile( fileName , fileUniqueCode , queryId ){
	  var userId = ${myUser.userId}
	 
	  $('#uploadLoader').show();
		  $.ajax({
				method : "POST",
			    url : "../member/deleteFileEdit",
			    data : {
			    	fileName : fileName,
			    	userId :userId,
			    	queryId:queryId,
			    	fileUniqueCode:fileUniqueCode

			    }
			   }).done(function(response) {
				   
				 
				   $("#"+ fileUniqueCode).remove();
					  $('#uploadLoader').hide();

				  
});
	 
	 
	    
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
	
	function setSelectedFIxerDivColor(index, selectDeselect){
		var sel = selectDeselect;
		if(sel == 'selected'){
		$('#clientHead' + index).css('background','#478fc9');
		$('#searchedFixerNAme'+index).css('color','#fff');
		$('#favtext'+index).css('color','#fff');
		$('#closeC'+index).css('color','#fff');
		$('#details'+index).css('color','#fff');
		$('#closeC'+index).css('background','url(../images/dropLeftWhite.png) no-repeat 100% -38px');
		$('#details'+index).css('background','url(../images/dropLeftWhite.png) no-repeat 100% 7px');
         $('#ratingC'+index+' i').css('color','#fff');
        $('#companyName'+ index).css('color','#fff');
		}else{
			$('#clientHead' + index).css('background','#f1f2f2');
			$('#searchedFixerNAme'+index).css('color','#000000');
			$('#favtext'+index).css('color','');
			$('#closeC'+index).css('color','');
			$('#details'+index).css('color','');
			$('#closeC'+index).css('background','url(../images/dropLeft.png) no-repeat 100% 7px');
			$('#details'+index).css('background','url(../images/dropLeft	.png) no-repeat 100% 7px');
			 $('#ratingC'+index+' i').css('color','#63429a');
			 $('#companyName'+ index).css('color','');
		}
		
	}
function holdRequest(queryId, refId)
{
	
   	
   
   	
   	$.ajax({
		method : "POST",
	    url : "../member/changeReqStatus",
	    data : {
	    	queryId:queryId,
	    	currentStatus:'H'
	    	}
	   }).done(function(response) {
		   
		   if(response.result.status=='OK')
			   {
		   	$('#'+refId).text('Release Request');
		   
		   		$('#'+refId).attr("data-toggle",'tooltip');
		   	$('#'+refId).attr("title",'Click here to resume further applications for this Request.');
		   	$('#'+refId).attr("onclick",'releaseRequest(\''+queryId+'\',\''+refId+'\')');
			   }
		  
	});
    
}

function releaseRequest(queryId, refId)
{
	
	
	
	$.ajax({
		method : "POST",
	    url : "../member/changeReqStatus",
	    data : {
	    	queryId:queryId,
	    	currentStatus:'O'
	    	}
	   }).done(function(response) {
		   
		   if(response.result.status=='OK')
		   {
		   $('#'+refId).text('Hold Request');
			$('#'+refId).attr("data-toggle",'tooltip');
		   	$('#'+refId).attr("title",'Click here to stop any further applications for this Request.');
			$('#'+refId).attr('onclick','holdRequest(\''+queryId+'\',\''+refId+'\')');
		   }
		  
	});
}
	
function addEditButton(queryData,index)
{
	
	
	
	
	queryContent = queryData.queryContent;
	
	
	var myvar = '<div class="proBtn clickEdit" id = "beforeEdit">'+
	'													<a href="javascript:void(0)"'+
	'														onclick="btnEditRequestTabbed('+index+','+queryData.subCategory.length+')"'+
	'														class="request request50">Edit Request</a> ';
	if(queryData.currentStatus=='O')
	{
		
	 myvar = myvar+'														<a data-toogle="tooltip" title="Click here to stop any further applications for this Request." id="changeReq'+queryData.queryId+'" href="javascript:void(0)" class="update-request request50"'+
		'														onclick="holdRequest('+queryData.queryId+',\'changeReq'+queryData.queryId+'\')">Hold Request'+
		'														</a>';
	}
	else
	{
	myvar = myvar+'														<a data-toogle="tooltip" title="Click here to resume further applications for this Request."id="changeReq'+queryData.queryId+'" href="javascript:void(0)" class="update-request request50"'+
	'														onclick="releaseRequest('+queryData.queryId+',\'changeReq'+queryData.queryId+'\')">Release Request'+
	'														</a>';
	}
	myvar=myvar+
	'												</div>'+
	'												<div class="proBtn CDelete" id="afterEdit">'+
	'													<a href="javascript:void(0)" class="request" onclick = "cancelButtonClick1('+queryData.queryId+',\''+queryData.hashcode+'\','+index+',\''+queryData.queryTitle+'\', '+queryData.queryCredits+')">Cancel</a> <a'+
	'														href="javascript:void(0)" class="delete" onclick = "deletePopUp(\''+queryData.hashcode+'\', '+index+')">Delete</a> <a'+
	'														href="javascript:void(0)" id="updateBtn" class="update-request"'+
	'														onclick="validationCheck('+${points}+','+index+','+queryData.queryCredits+')">Update'+
	'														Request</a>'+
	'												</div>';
	$("#account"+index).append(myvar);
		

	}
	
function contains(a, obj) {
    var i = a.length;
    while (i--) {
       if (a[i] == obj) {
           return true;
       }
    }
    return false;
}

function createCategoryPopUp(index) {
 var popUp=	
	 '		<div class="modal fade" id="myModal" role="dialog">' +
	'			<div class="modal-dialog modal-lg">' +
	'				<div class="modal-content">' +
	'					<div class="modal-body">' +
	'						<h1 class="h1-underline">Choose Categories</h1>'  +
	'						<ul class="ChooseCategories">';
			for(var i= 0; i<parentCatJson.length;i++) {	
				 popUp+=	'												<li><div class="categoriesT" onclick="selectCategoryTest(this)"' +
'														id="categoriesT'+parentCatJson[i].catId+'">'+parentCatJson[i].catgName+'</div> ' +
'														<div class="categoriesList" style="display:none" '+
'														id="categoriesL'+parentCatJson[i].catId+'"> '+
'															<ul class="MyIndus"> ';
for(var j =0 ; j<allCategory.length;j++) {
						if(parentCatJson[i].catId==allCategory[j].parentId) {
							 popUp+=	'																	<li><input name="categories" '+
'																		id="catId'+allCategory[j].catId+'" value="'+allCategory[j].catId+'" '+
'																		onclick="onChildCatClick('+allCategory[j].catId+')" '+
'																		type="checkbox"> <label for="categories">'+allCategory[j].catgName+'</label> '+
'																		</li> ';
												} 
											} 
popUp+=	'															</ul> '+
'														</div></li> ';
} 
			 popUp+=	'											</ul> '+
'										</div> '+
'										<div class="modal-footer"> '+
'											<button class="saveChange width100B" onclick="onSave()" '+
'											data-dismiss="modal">Save</button> '+
'											<button onclick="onCancel()" class="cancel width100B" '+
'											data-dismiss="modal">Cancel</button> '+
'										</div> '+
'									</div> '+
'								</div> '+
'							</div> ';	
$("#moreBlock"+index).append(popUp);
}


/* function findChildCatIdByParent(id){
	 childIdByParentId = [];
	 for(var i=0; i<catJson.length;i++){
		 if(catJson[i].parentId==id){
			 childIdByParentId.push(catJson[i].catId);
		 }
	 }
}  


function checkChildIdChecked(){
	var j;
	for(var i=0;i<childIdByParentId.length;i++){
		  j = checkedItem.indexOf(childIdByParentId[i]);
		if(j!=-1){
			return true;
		}
	}
	return false;
}  */


function selectCategoryTest(val){
	
	if($("#"+val.id).next().is(":visible")){
		$("#"+val.id).next().delay(500);
		$(".categoriesList" ).css( "display", "none" );
		
	}else{
		$(".categoriesList" ).css( "display", "none" );
		$("#"+val.id).next().css( "display", "block" ); 
	}
	highlightParentCatBorder();
}

$('.modal-footer button').click(function(e){
	$(".categoriesList").css({"display":"none"});
});


function addhoursOnchange(value,index){
	
	var add12Hrs;
	var date = $('#datetimepicker1').val();
	var time = $('#datetimepicker2').val();
	/*  var splitArr = date.split('/');
	 date = splitArr[1]+'/' + splitArr[0] +'/' + splitArr[2] ; */
	var datetime = new Date(date+' '+time);
	
	 add12Hrs = datetime.getHours() + 12 * value;

	datetime.setHours(add12Hrs);

	// format the output
	var month = datetime.getMonth()+1;
	var day = datetime.getDate();
	var year = datetime.getFullYear();

	var hour = datetime.getHours();
    if (hour < 10)
	    hour = "0"+hour;
    

	var min = datetime.getMinutes();
	if (min < 10)
	    min = "0"+min;
	

	var sec = datetime.getSeconds();
      if (sec < 10)
	    sec = "0"+sec;

	// put it all togeter
	var dateTimeString = month+'/'+day+'/'+year+' '+hour+':'+min+':'+sec;
	var setDate = month+'/'+day+'/'+year;
	//var setTime = hour + ':' + min;
	var setTime ;
	if(hour>=12)
		{
			if(hour>12)
				{
					setTime = (hour-12)+ ':' + min+' pm';
				}
			else
				{
					setTime = hour+ ':' + min+' pm';
				}
		}
	else
		{
			setTime = hour+ ':' + min+' am';
		}
	
	var currentDate = setCurrentDate('');
	//var splitWithSlash = currentDate.split('/');
	 var bool = validateDate(currentDate, setDate);
	 if(bool){
	 
		 $('#datetimepicker1').val(setDate);
	     $('#datetimepicker2').val(setTime);
	     $('#queryDeadlineDate' + index).val(dateTimeString);
	} 
	 else{
	     $('#queryDeadlineDate' + index).val(currentDate + ' ' +time+ ':'+ '00');
 
		 
	 }
	 
	
	
}




function generateHtml(queryData,index,countryData,country, queryid)
{
	
var deadlineDate ;
var deadlineTime ;
var responseDate  = queryData.queryDeadlineDate;

	if(responseDate != null){
	/* 	var splitArr = responseDate.split('/');
		responseDate = splitArr[1]+'/' + splitArr[0] +'/' + splitArr[2] ; */

		var dateFromResponse = new Date(responseDate);
		
		var month = dateFromResponse.getMonth()+1;
		var day = dateFromResponse.getDate();
		var year = dateFromResponse.getFullYear();
		var hour = dateFromResponse.getHours();
	    if (hour < 10)
		    hour = "0"+hour;
	    

		var min = dateFromResponse.getMinutes();
		if (min < 10)
		    min = "0"+min;
		 deadlineDate = month+'/'+day+'/'+year;
		 //deadlineTime = hour + ':' + min;
		 var deadlineTime ;
			if(hour>=12)
				{
					if(hour>12)
						{
						deadlineTime = (hour-12)+ ':' + min+' pm';
						}
					else
						{
						deadlineTime = hour+ ':' + min+' pm';
						}
				}
			else
				{
				deadlineTime = hour+ ':' + min+' am';
				}
	

	}	

		var myvar = '<div>	'+
		'														<h2>Categories</h2>'+
		'														<div class="descForm choosC"'+
		'															id="categoriesEdit"'+
		'															style="display: none">'+
		'															<span class="alertF" id="categoriesAlert"'+
		'														style="display: none">Please select at least one category.'+
		'														</span>'+
		'															<label class="top33">Request Categories: <span>Select'+
		'																	one or more.</span></label>'+
		'															<div id="the-basics"  class="width50per">'+
		'																<input class="typeahead formControl" type="text"'+
		'																	placeholder="Search"'+
		'																	id="smart_search">'+
		'															</div>'+
		'														<span class="selectCate width50per" data-toggle="modal" data-target="#myModal"  data-backdrop="static" data-keyboard="false">Categories</span> ' +
		'														</div>'+
		'														<div class="categoriesDiv">'+
		'															<div id="categoryNameId">';
		for(var i=0;i<queryData.subCategory.length;i++){
																	
			myvar +='																	<div class="categoriesBox">'+
		'																		<div id="valueHolderId" style="display: none;">'+queryData.subCategory[i]+'</div>'+
		'																		<div id="valueHolderIdIndex" style="display: none;"></div>'+
		'																		<script type="text/javascript">'+
		'													showSelectedCategory();'+
		'                                                    </scr'+'ipt>'+
		'																	</div>';
		}
		myvar +='															</div>'+
		'														</div>'+
		'														<div class="clearfix"></div>'+
		'														<h2 id ="attachText">Attachment(s)</h2>'+
		'														<div id="attach">';
		for(var i=0;i<queryData.attachedDocuments.length;i++){
			myvar +='																<div class="download" id="'+queryData.attachedDocuments[i].fileUniqueCode+'">'+
		'																	 <span>Download:</span>'+
		'																		<a href="'+queryData.attachedDocuments[i].fileUrl+'"> <input type="text" name="documents"'+
		'																		value="'+queryData.attachedDocuments[i].fileName+'" style="cursor:pointer" readonly>'+
		'																	</a> <i data-icon="y" class="icon catIcon"'+
		'																		onclick="deleteFile(\''+queryData.attachedDocuments[i].fileName+'\',\''+queryData.attachedDocuments[i].fileUniqueCode+'\','+queryData.queryId+')"></i>'+
		'																</div>';
		}
		myvar +='														</div>'+
		'														<br>'+
		'														<div id="editableSections"'+
		'															style="display: none">'+
		'															<div class="browsfile">'+
		'																<input id="documentUpload" placeholder="" type="file"'+
		'																	onchange="uploadDocument('+queryData.queryId+')">'+
		'																Upload File (5MB limit)'+
		'																<!-- <span>dfg.xls <i data-icon="y" class="icon"></i></span> -->'+
		'																<span class="uploadLoader" style="display: none"'+
		'																	id="uploadLoader"><img'+
		'																	src="${pageContext.request.contextPath}/images/loader.gif"'+
		'																	alt=""></span>'+
		'															</div>'+
	
		'															<h2>Hours</h2>'+
		'															<div class="estimateH" style="min-height: 78px;">'+
		'																<div class="addH" style="margin: 0px;">'+
		'																<span class="alertF" id="hoursAlert" style="display: none">Please'+
		'															enter title of query.</span>'+
		'																	<img'+
		'																		src="${pageContext.request.contextPath}/images/moreA.png"'+
		'																		alt="" class="leftimg"'+
		'																		id="leftimg"'+
		'																		onclick="leftCredirBtnPressed1('+index+','+${points}+')">'+
		'																	<input value="'+queryData.queryCredits+'" onchange="addhoursOnchange(this.value, '+index+')" type="number"'+
		'																		name="queryCredits" id="hours">'+
		'																	<img'+
		'																		src="${pageContext.request.contextPath}/images/moreA.png"'+
		'																		alt="" class = "rightimg" id="rightimg"'+
		'																		onclick="rightCredirBtnPressed1('+ index+','+${points}+','+queryData.queryCredits+')">'+
		'																</div>'+
		'																<div class="remainging">'+
		'																	<strong>'+${points}+' Credits Remaining</strong> <a id="add_more" href="javascript:void(0)">+'+
		'																		Add More Credits</a>'+
		'																<input type="hidden" id="queryIdAddmore" value= "'+queryData.queryId+'">'+
		'																</div>'+
		'															</div>';
		
		if(responseDate != null){
		myvar +='		                                          <br> <strong>Due Date</strong>'+
		'												<div class="descForm">'+
		'													<label class="width100w">Desired due date and time:</label> <input value= "'+deadlineDate+'"'+
		'														type="text" id="datetimepicker1" class="formControl"'+
		'														style="width: 60%;"><input type="text"  value= "'+deadlineTime+'"'+
		'														id="datetimepicker2" class="formControl"'+
		'														style="width: 40%; border-left: 0;">'+
		'														<span class="alertF" id="dateTimeAlert" style="display: none">Please Select date</span>'+
		'												</div>';
		}

		myvar +='																<h2>Find a Fixer</h2>'+
		'															<div class="estimateH">'+
		'															<span class="alertF"'+
		'														id="leaverequestAlert" style="display: none"> Please select at least one Fixer for request.</span>'+
		'																<div class="radio"'+
		'																	id="LeaveRequestFixer"'+
		'																	onclick="openToAllFixerClicked('+index+')" >'+
		'																	<input id="leaverequest"'+
		'																		name="fixer" type="radio" checked> <label'+
		'																		id="openRequest"'+
		'																		for="leaverequest">Leave'+
		'																		Request Open to All Fixers</label>'+
		'																</div>'+
		'																<div class="radio"'+
		'																	id="SendSpecificFixer"'+
		' onchange="sendSpecificFixerClick(\'\','+index+','+queryid+')"'+
		'																	>'+
		'																	<input id="SendSpecific"'+
		'																		name="fixer" type="radio"> <label'+
		'																		for="SendSpecific">Send'+
		'																		to Specific Fixer(s)</label>'+
		'																</div>'+
		'															</div>'+
		'															<div class="filterDiv"'+
		'																id="filterDivsearch">'+
		'																<div class="filterA">'+
		'																	<div class="folterD"'+
		'																		id="ClearFilter"'+
		'																		onclick="onClearFilterClick('+index+')">'+
		'																		Clear Filter <i data-icon="j" class="icon"></i>'+
		'																	</div>'+
		'																	<div class="descForm"'+
		'																		id="ParentCategory">'+
		'																		<label>Parent Category</label> <select'+
		'																			id="parentCategory"'+
		'																			onchange="findCatByparentCatToShowOnList('+index+','+queryid+')">'+
		'																			<option value="-1">Filter by Parent Category</option>'+
		'																		</select>'+
		'																	</div>'+
		'																	<div class="descForm descFormChild"'+
		'																		id="childCatDropDownList">'+
		'																	</div>'+
		'																	<div class="descForm"'+
		'																		id="SearchIndustry">'+
		'																		<label>Industry</label> <select>'+
		'																			<option>Any Industry</option>'+
		'																			<option>option</option>'+
		'																			<option>option</option>'+
		'																		</select>'+
		'																	</div>'+
		'																	<div class="descForm"'+
		'																		id="SearchRating">'+
		'																		<label>Rating</label> <select'+
		'																			id="selectedRating"'+
		'																			onchange="ratingChangeListener('+index+','+queryid+')">'+
		'																			<option value="">Any Rating</option>'+
		'																			<option value="1">1+</option>'+
		'																			<option value="2">2+</option>'+
		'																			<option value="3">3+</option>'+
		'																			<option value="4">4+</option>'+
		'																			<option value="5">5+</option>'+
		'																		</select>'+
		'																	</div>'+
		'																	<div class="descForm"'+
		'																		id="SearchCuntry">'+
		'																		<label>Country</label> <select'+
		'																			id="selectedCountry"'+
		'																			onchange="ratingChangeListener('+index+','+queryid+')">'+
		'																			<option value="">Any Country</option>';
		for(var i=0;i<countryData.length;i++){
			myvar +='																				<option value="'+countryData[i].countryName+'">'+countryData[i].countryName+'</option>'
}																		
myvar +='																			</select>'+
		'																	</div>'+
		'																	<div class="descForm">'+
		'																		<label>Search Name</label> <input type="search"'+
		'																			id="searchId" value=""'+
		'																			placeholder="Search" class="formControl"'+
		'																			onkeyup="searchNameChanged(this.value,'+index+','+queryid+' )"'+
		'																			>'+
		'																	</div>'+
		'																</div>'+
		'																<!-- // 1 // -->'+
		'																<div id="interestedFixers_searched'+index+'">'+
		'																</div> <div class="requestloadmore" onclick="loadMore('+index+','+queryid+')" >Load More</div>'+
		'															</div>'+
		'														</div>'+
		'														<div class="clearfix"></div>'+
		'														</div>  <input type="hidden" name="queryMode" value="EDIT" id="queryMode">' ;
									
		
		
			$("#moreBlock"+index).append(myvar);
			 $('#the-basics .typeahead').typeahead({
			       hint: true,
			       highlight: true,
			       minLength: 1
			     },
			     {
			       name: 'states',
			       source: substringMatcher(catArr)
			     });
			 

			 

				jQuery.datetimepicker.setLocale('en');

			jQuery('#datetimepicker1').datetimepicker({
			 i18n:{
			  en:{
			   months:[
			    'January','February','March','April',
			    'May','June','July','August',
			    'September','October','November','December',
			   ],
			   dayOfWeek:[
			    "Su", "Mo", "Tu", "We", 
			    "Th", "Fr", "Sa"
			   ]
			  }
			 },
			 timepicker:false,
			 format:'m/d/Y',
			 minDate: 0,
			 scrollMonth : false,
			 scrollInput : false
			});

			jQuery('#datetimepicker2').datetimepicker({
				  datepicker:false,
				  format:'h:i a',
				  minTime: 0
				});
			$('#datetimepicker1').bind('change',function(event){
				event.stopPropagation();
				console.log('hello')
				changeTimeProperty();
				});
			highlightParentCatBorder(); 
			 
		}
		


function changeTimeProperty()
{
	jQuery('#datetimepicker2').datetimepicker({
		  datepicker:false,
		  format:'h:i a',
		  minTime: selectDateTime()
		  
		});
}
function selectDateTime(){
	var currentDate = setCurrentDate('');
	var selectedDate = $('#datetimepicker1').val();

	 if( Date.parse(currentDate) != Date.parse(selectedDate)){
			return 1;
			}
			else {
				return 0;
			}
}

		function cancelButtonClick1(queryId,queryHashCode,id,queryTitle,queryCredits)
		{
			$(".catIcon").css("display","none");
			$(".alertF").css("display","none");
			$("#titleHead"+id).val(queryTitle);
			$("#titleContent"+id).val(queryContent);
			
			
			 $('#titleHead'+ id).removeClass(
		      'titleHeadedit').addClass(
		      'titleHead');
			  $('#titleContent'+ id).removeClass(
		      'titleContentEdit').addClass(
		      'titleContent'); 
			   $('#titleHead' + id).attr('readonly',
			           true);
			  $('#titleContent' + id).attr('readonly',
			           true); 
			 
			   $("#moreBlock"+id).empty();
			   $("#account"+id).empty();
			   setInterestedFixers(queryId, queryHashCode,id,queryCredits);
		}
		
		function deletePopUp(queryHashCode, index)
		{
			$('#popupRequestFixed'+index).css('display', 'block')
			$('#delete'+index).attr('onclick','deleteRequestClick(\''+queryHashCode+'\','+ index+')');
		}
		function deleteRequestClick(queryHashCode, index)
		{
			$("[onclick]").removeAttr("onclick"); 
//			$("#delete" + index).css("pointer-events", "none");

		 window.location.replace("../member/delete?queryId="+queryHashCode);
		}
		function cancelDeleteBtnclick(index){
			
			$('#popupRequestFixed'+index).css('display', 'none');

		}
	
		 
</script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/pages/script.js"></script>
	<script type="text/javascript">
	
	var triggerAutomatic = false;
	var senderId = null;
	
$(document).ready(function(){
	 $(".closeMessage").click(function(){
		 $('.chatbgBody').empty();
		 senchatScroll = true;
		 
		});
	 });
$(document).ready(function(){
	
	
	 var divQueryId = getParameterByName('queryId');
	 var aplyQueryCode = getParameterByName('applyQueryCode');
		
		
		 
		if(divQueryId!=null){
			
		senderId = getParameterByName('senderId');
	
		
		triggerAutomatic = true;
		
		
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
	else if(aplyQueryCode!=null)
	{
		var parentDiv = $('#'+aplyQueryCode).parent().attr('id'); 
		
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

function getParameterByName(name, url) {
    if (!url) {
      url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
 
</script>

<div id="popup" style=" display: none;">    
    	<div id="inpopup">
    	
        	<span class="closepopup"><i data-icon="y" class="icon"></i></span>
            
            <div id="popupcahtH">
           <h1 id="payStatusMsg"> </h1>
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