var req;
var intervalTimer;
var chatHMax = 0;
var chatH = 0;
var chatHArr = [];
var senchatScroll = false;
$(document).ready(function() {
	$('.alertF').css('display', 'none');
	$('#halfFullBorder').removeClass('halfBorder').addClass('fullBorder');

	setInterval(checkVisibility, 10000);
	$('a').removeClass('active');
	$("#dashboard").addClass('active');
      $('#chatbgBody').on('scroll',function(){
    	 var h =  $('#chatbgBody').scrollTop();
    	 chatH = h;
    	 chatHArr.push(h);
    	  
      });
      
	$('#newPassword').on('keyup', function() {
		$('#newOneNumb').css('color', '#999');
		$('#newChar8Len').css('color', '#999');
		$('#newUpperlower').css('color', '#999');
		$('#newOneNumbI').css('color', '#999');
		$('#newChar8LenI').css('color', '#999');
		$('#newUpperlowerI').css('color', '#999');
		var password = $(this).val();
		var numbBool = hasNumbers(password);
		var passLen = password.length;
		var upperCaseBool = hasUpperCaseChar(password);
		var upperLowerBool = hasLowerCaseChar(password);

		if (numbBool) {
			$('#newOneNumb').css('color', '#1e9873');
			$('#newOneNumbI').css('color', '#1e9873');

		}
		if (passLen > 7) {
			$('#newChar8Len').css('color', '#1e9873');
			$('#newChar8LenI').css('color', '#1e9873');

		}
		if (upperCaseBool && upperLowerBool) {

			$('#newUpperlower').css('color', '#1e9873');
			$('#newUpperlowerI').css('color', '#1e9873');

		}

	});

	$('#reTypePassword').on('keyup', function() {
		$('#reCheckPass').css('color', '#999');
        var password = $('#newPassword').val();
		var ReTypepassword = $(this).val();
		if(password != null && password.trim() != ''){
			var string = '<i id="reCheckPassI" class="fa fa-check-circle"></i>';
		if(ReTypepassword == password){
			$('#reCheckPass').empty();
			$('#reCheckPass').append(string + " Passsword  match");
			$('#reCheckPass').css('color', '#1e9873');
			$('#reCheckPassI').css('color', '#1e9873');


		}else{
			$('#reCheckPass').empty();
			$('#reCheckPass').append(string + " Passsword not match");
			$('#reCheckPass').css('color', '#999');
			$('#reCheckPassI').css('color', '#999');
		}
		}

	});


});

var countryList = new Array();
// <![CDATA[
$(window).load(function() {
	// auto adjust the height of
	$('#textauto').on('keydown', 'textarea', function(e) {
		$(this).css('height', 'auto');
		$(this).height(this.scrollHeight);
	});
	$('#textauto').find('textarea').keydown();
});
function hasUpperCaseChar(t) {
	var regex = /[A-Z]/;
	return regex.test(t);
}

function hasLowerCaseChar(t) {
	var regex = /[a-z]/;
	return regex.test(t);
}

function hasNumbers(t) {
	var regex = /\d/g;
	return regex.test(t);
}

function loadChatMsg() {

}

function highlightParentCatBorder() {
	for (var i = 0; i < parentCatJson.length; i++) {
		$("#" + "categoriesT" + parentCatJson[i].catId).removeClass('active');
	}
	var catsIds = getCheckedBoxes('categories');
	var parentId;
	for (var j = 0; j < catsIds.length; j++) {
		for (var i = 0; i < allCategoryJson.length; i++) {
			if (allCategoryJson[i].catId == catsIds[j]) {
				parentId = allCategoryJson[i].parentId;
			}
		}
		$("#" + "categoriesT" + parentId).addClass('active');
	}
}

var client = new XMLHttpRequest();
function uploadProfileImage() {

	var filePath = $("#multipartFile").val();
	var fileName = filePath.split('\\').pop();
	var fileExtension = fileName.split('.').pop().toLowerCase();
	if (fileExtension == "jpg" || fileExtension == "jpeg"
			|| fileExtension == "png") {
		$('.upLoader').css('display', 'block');
		$("#uploadtxt").css('display', 'none');

		/*
		 * $("#myImageUrl").value="../images/default_profile.jpg";
		 */
		var multipartFile = document.getElementById("multipartFile");
		/ Create a FormData instance /
		var formData = new FormData();
		formData.append("multipartFile", multipartFile.files[0]);
		/*
		 * var id=$('#userId').val(); console.log(id);
		 */
		formData.append("userName", document.getElementById("userName").value);
		// console.log(formData);
		$('#saveButton').attr('disabled', 'disabled');
		client.open("post", "../fixerProfileImage", true);
		client.send(formData);
	} else {
		alert("Invalid File Format . Only JPG,JPEG,PNG File Format Supported");
		return;
	}

}
client.onreadystatechange = function() {
	if (client.readyState == 4 && client.status == 200) {
		var currentDate =  new Date();
		var data = jQuery.parseJSON(client.response);
		document.getElementById("imageUrl").value = data.result.originalFileName;
		document.getElementById("imageUrlSetting").value = data.result.originalFileName;

		// document.getElementById("imageFile").value=data.result.originalFileName;

		$('#myProfileImage').attr("src", data.result.originalFullFileName+"?" + currentDate);

		$('.upLoader').css('display', 'none');
		$("#uploadtxt").css('display', 'block');
		/*
		 * $('#saveButton').removeAttr('disabled'); $("#imageLoader").hide();
		 */
		console.log(data);
	}
}
function userNameFromEmail() {
	var email = $("#userEmail").val();
	var strArray = email.split('@');
	var userName = strArray[0];
	$("#userName").val(userName);
}

function editProfile() {
	var linkedInRegex = /^(?:(https?):\/\/)?(?:([a-zA-Z]{1,15}\.)?)linkedin.com(\w+:{0,1}\w*@)?(\S+)(:([0-9])+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/i;
	var overviewText = $('#overviewText').val();
	var name = $('#name').val();
	var companyName = $('#companyName').val();
	var companyName = $('#companyName').val();
	var city = $('#f_elem_city').val();
	var country = $("#country").val()
	// var locationAddress = $('#location').val();
	var linkedinProfile = $('#linkedinProfile').val();

	if (name == null || name.trim() == "") {
		$('.alertF').css('display', 'none');

		$('#spanName').css('display', 'block');

		return false;
	}
	/*
	 * if (companyName == null || companyName.trim() == "") {
	 * $('.alertF').css('display', 'none');
	 * 
	 * $('#spancompanyName').css('display', 'block');
	 * 
	 * return false; }
	 */
	if (city == null || city.trim() == "") {
		$('.alertF').css('display', 'none');
		$('#spanCity').css('display', 'block');
		return false;
	}
	if (country == null || country.trim() == "") {
		$('.alertF').css('display', 'none');

		$('#spanCountry').css('display', 'block');

		return false;
	}
	/*
	 * if (locationAddress == null || locationAddress.trim() == "") {
	 * $('.alertF').css('display','none');
	 * 
	 * //alert("is empty");
	 * 
	 * return false; }
	 */
	if (linkedinProfile == null || linkedinProfile.trim() == "") {
		$('.alertF').css('display', 'none');

		$('#spanlinkedinProfile').css('display', 'block');

		return false;
	}
	if (linkedinProfile.trim() != "") {

		if (!linkedInRegex.test(linkedinProfile)) {

			$('#spanlinkedinProfile').text("Please enter a valid LinkedIn url");
			$('#spanlinkedinProfile').css('display', 'block');
			return false;
		} else {
			if (linkedinProfile.substring(0, 5) == "https") {

				if (linkedinProfile.length < 26) {
					$('#spanlinkedinProfile').text(
							"Please enter a valid LinkedIn url");
					$('#spanlinkedinProfile').css('display', 'block');
					return false;

				}
			} else if (linkedinProfile.substring(0, 4) == "http") {

				if (linkedinProfile.length < 25) {
					$('#spanlinkedinProfile').text(
							"Please enter a valid LinkedIn url");
					$('#spanlinkedinProfile').css('display', 'block');
					return false;

				}
			} else {
				if (linkedinProfile.length < 18) {
					$('#spanlinkedinProfile').text(
							"Please enter a valid LinkedIn url");
					$('#spanlinkedinProfile').css('display', 'block');
					return false;

				}

			}
		}
	}

	if (overviewText == null || overviewText.trim() == "") {
		$('.alertF').css('display', 'none');

		$('#spanOverviewText').css('display', 'block');

		return false;
	}
	$('.alertF').css('display', 'none');

	$('#editUser').submit();

}

function close(id) {
	
	$('#categories' + id).remove();
}

function onemailAlertCheckBox() {

	$("#emailAlert").is(":checked")
	if ($("#emailAlert").is(":checked")) {
		$("#emailAlert").val("");
		$("#emailAlert").val("Y");
	} else {
		$("#emailAlert").val("");
		$("#emailAlert").val("N");
	}

}

$(document)
		.ready(
				function() {
					
					 var imgSrc = $('#myProfileImage').attr('src');	
					 
					if(imgSrc == '' || imgSrc == '../images/profile-pic.jpg'){

						$('#myProfileImage').attr('src','../images/profile-pic.jpg');
				    	jQuery('#popup').fadeIn(1000);

						}
					
	                  $('#myProfileImage').attr('src', imgSrc + '?' + new Date());
					$('#password').on('click', function() {

						$('#spanPassword').css('display', 'none');

					});
					
					$('#linkedinProfile').on('click', function() {

						$('#spanlinkedinProfile').css('display', 'none');

					});
					$('#f_elem_city').on('click', function() {

						$('#spanCity').css('display', 'none');

					});
					$('#name').on('click', function() {

						$('#spanName').css('display', 'none');

					});

					$('#userEmail').on('click', function() {

						$('#spanUserEmail').css('display', 'none');

					});
					$('#oldPassword').on('click', function() {

						$('#spanOldPassword').css('display', 'none');

					});
					$('#newPassword').on('click', function() {

						$('#spanNewPassword').css('display', 'none');

					});
					$('#reTypePassword').on('click', function() {

						$('#spanReTypePassword').css('display', 'none');

					});

					/*
					 * $('#smart_search').typeahead({ hint : true, highlight :
					 * true, minLength : 1 }, { name : 'states', source :
					 * substringMatcher(countryList) });
					 */

					$("#chatsend").bind('click', function() {
						var chatTextArea = $('#chatTextArea').val();

						if (chatTextArea != null && chatTextArea.trim() != '') {
							$("#chatsend").css('pointer-events', 'none');

							sendChatMessage();
						}
					});

					setAverageFixerRating();
					getGraphBars();

					$('.alertF').css('display', 'none');

					$('#imageUrl').val("");
					$('#imageUrlSetting').val("");

					$('#userEmail').val(userEmail);
					$('#userName').val(userName);
					$('#paypalId').val(paypalId);
					
					console.log(timeZone);
					$("#timeZone option[value='" + timeZone + "']").prop(
							'selected', true);

					$('#country option[value="' + country + '"]').prop(
							'selected', true);
					$('#messageTab').bind('click', function() {
						if (countMsg != 0) {
							updateMessageNotification();
						}
						var objDiv = document.getElementById("chatbgBody");
						objDiv.scrollTop = objDiv.scrollHeight;

					});

					$('#saveSetting')
							.bind(
									'click',
									function() {
										onemailAlertCheckBox();
										$('.alertF').css('display', 'none');

										var userEmail = $('#userEmail').val();
										var userName = $('#userName').val();
										var timeZone = $('#timeZone').val();
										var newPassword = $('#newPassword')
												.val();
										var reTypePassword = $(
												'#reTypePassword').val();
										var oldPassword = $('#oldPassword')
												.val();
										var pass = $('#password').val();

										if (userEmail == null
												|| userEmail.trim() == "") {
											$('.alertF').css('display', 'none');

											$('#spanUserEmail').css('display',
													'block');

											return false;
										}
										/*
										 * if (userName == null ||
										 * userName.trim() == "") {
										 * 
										 * $('.alertF').css('display', 'none');
										 * 
										 * $('#spanUserName').css('display',
										 * 'block'); return false; } var
										 * userNameLen = userName.length; if
										 * (userNameLen < 6 || userNameLen > 12) {
										 * 
										 * $('.alertF').css('display', 'none');
										 * 
										 * $('#spanUserName').css('display',
										 * 'block'); $('#spanUserName') .text(
										 * "Username must not be greater than 12
										 * characters"); return false; }
										 */

										if (timeZone == null
												|| timeZone.trim() == "") {
											// alert("is empty");

											return false;
										}

										if (oldPassword.trim() !="" || newPassword.trim() != ""
												|| newPassword.length > 1
												|| reTypePassword.trim() != ""
												|| reTypePassword.length > 1) {

											if (newPassword.trim() == "") {
												$('.alertF').css('display',
														'none');
												$('#spanNewPassword').text("Please enter a password.");
												$('#spanNewPassword').css(
														'display', 'block');

												return false;
											}
											if (reTypePassword.trim() == "") {
												$('.alertF').css('display',
														'none');
												$('#spanReTypePassword').text("Please enter a password.");

												$('#spanReTypePassword').css(
														'display', 'block');

												return false;
											}
											if (newPassword.trim().length < 7
													|| reTypePassword.trim().length < 7) {

												$('.alertF').css('display',
														'none');
												$('#spanNewPassword').text(
														'8 or more characters');
												$('#spanNewPassword').css(
														'display', 'block');
												return false;
											}

											var numbBool = hasNumbers(newPassword);
											var upperCaseBool = hasUpperCaseChar(newPassword);
											var upperLowerBool = hasLowerCaseChar(newPassword);

											if (!numbBool) {

												$('#spanNewPassword')
														.text(
																'must contains at least one number')
														.css('display', 'block');
												return false;

											}

											if (!(upperCaseBool && upperLowerBool)) {

												$('#spanNewPassword')
														.text(
																'must contains upper & lowercase letters')
														.css('display', 'block');
												return false;

											}
											if (numbBool && upperCaseBool
													&& upperLowerBool) {
												if (reTypePassword != newPassword) {
													$('.alertF').css('display',
															'none');

													$('#spanReTypePassword')
															.text(
																	'Password must be Equal');
													$('#spanReTypePassword')
															.css('display',
																	'block');

													return false;
												}
											}
											if (reTypePassword == newPassword) {
												if (oldPassword.trim() == "") {
													$('#spanOldPassword')
															.text(
																	'Please enter old password')
															.css('display',
																	'block');
													return false
												}
												if ((oldPassword != null && oldPassword
														.trim() != "")) {
													var bool = comparePassword();
													if (!bool) {

														return false;
													}else{
														if(oldPassword == newPassword){
															$('.alertF').css('display',
															'none');
													$('#spanNewPassword').text("New password must be different from old");
													$('#spanNewPassword').css(
															'display', 'block');

													return false;					
														}
														
													}

												}
											}
										}
										$('.alertF').css('display', 'none');

										$("#actionSetting").submit();

									});

					$('.edit')
							.bind(
									'click',
									function() {
										$('#halfFullBorder').removeClass('fullBorder').addClass('halfBorder');

										$('#headingName')
												.css('display', 'none')

										$('#headingCompanyName').css('display',
												'none')
										$('.uploadimg').css('display', 'block');
										$('#profileEditRight').removeClass(
												'prifileRight').addClass(
												'editprofileDiv');
										$('#editProfile').css('display',
												'block');
										$('.settingDiv').css('display', 'none');
										$('#setting').css('display', 'none');
										$('input[type="text"]').prop(
												'readonly', false);
										$('#userName').prop('readonly', true);

										$('#name').attr('type', 'text');
										$('#linkedinProfile').attr('type',
												'text');

										$('#companyName').attr('type', 'text');
										$('#anchorLinkedIn').css('display',
												'none');
										$('#overviewText').prop('readonly',
												false);
									});

					$('.settings').bind('click', function() {

						$('.uploadimg').css('display', 'block');

						$('#editProfile').css('display', 'none');
						$('.settingDiv').css('display', 'block');
						$('#setting').css('display', 'block');

						$('#overviewText').prop('readonly', true);

					});

					var numOfCat = 0;
					/*
					 * $('.addnew') .bind( 'click', function() { var id =
					 * numOfCat; var html = '<span
					 * id="categories'+id+'">Accounting <i id="cross' + id + '"
					 * data-icon="y" class="icon" onClick="close(' + id + ')" ></i></span>';
					 * $('.categoriesDiv').append(html); numOfCat = id + 1; });
					 */

					if (countMsg == 0) {

						$('#messageTab').removeClass("newmessage wow fadeIn")
								.addClass("nullmessage");
					}

				});

function getGraphBars() {

	var htmlStart = '';
	var htmlStarRating = '<i data-icon="m" class="icon"></i>';
	var htmlBlankStar = '<i data-icon="l" class="icon"></i>';
	var htmlMiddle = '';
	var htmlEnd = '';
	$.ajax({
		type : "GET",
		url : "../fixer/fixerRating",

	}).done(
			function(response) {
				var response = response.rating
				var len = response.length;
				var greaterCount;

				var maximum = Math.max.apply(Math, response.map(function(o) {
					return o.count;
				}))
				var percentWidth = 100 / maximum;
				var dynamicWidth;

				for (var i = 0; i < len; i++) {

					htmlStart = ' <div class="grow">'
							+ ' <span class="ratingIcon">';

					for (var j = 0; j < 5; j++) {
						if (j < (response[i].rating)) {
							htmlMiddle = htmlMiddle + htmlStarRating;

						} else {
							htmlMiddle = htmlMiddle + htmlBlankStar;

						}

					}
					dynamicWidth = percentWidth * (response[i].count);

					htmlEnd = ' </span><div class="graphDiv" style="width:'
							+ dynamicWidth + '%" >'
							+ '  <span class="Gline"></span>'
							+ '  <span class="G-no">' + response[i].count
							+ '</span>' + ' </div>' + '</div>';
					$('#graph_container').append(
							htmlStart + htmlMiddle + htmlEnd);
					htmlMiddle = '';
				}

			});

}

/*
 * for (var i = 0; i < countryListJson.length; i++) {
 * 
 * countryList.push(countryListJson[i].countryName); } console.log(countryList);
 */
/*
 * var substringMatcher = function(strs) { return function findMatches(q, cb) {
 * var matches, substringRegex; // an array that will be populated with
 * substring matches matches = []; // regex used to determine if a string
 * contains the substring `q` substrRegex = new RegExp(q, 'i'); // iterate
 * through the pool of strings and for any string that // contains the substring
 * `q`, add it to the `matches` array $.each(strs, function(i, str) { if
 * (substrRegex.test(str)) { matches.push(str); } });
 * 
 * cb(matches); }; };
 */
/*
 * $('#the-basics .typeahead').typeahead({ hint: true, highlight: true,
 * minLength: 1 }, { name: 'states', source: substringMatcher(countryList) });
 */

/*
 * $(document).on('click', '#the-basics .tt-selectable', function() { var str =
 * $("#smart_search").val();
 * 
 * });
 */

function saveCategory() {
	var catIds = new Array();
	catIds = getCheckedBoxes('categories');
	$
			.ajax({
				method : "POST",
				url : '../fixer/updateCategory',
				data : {
					catIds : catIds
				}
			})
			.done(
					function(response) {
						if (response.status == "success") {
							$("#categorySpanDiv").empty();
							var html;
							var catSet = response.result.catIds;
							userCategory = [];
							userCategory = catSet;
							for (var i = 0; i < catSet.length; i++) {
								html = ' <span id="categorySpan'
										+ catSet[i].catId
										+ '" >'
										+ catSet[i].catgName
										+ '<i data-icon="y" class="icon" onclick="removeSelectedCategory('
										+ catSet[i].catId + ')" ></i></span>';
								$("#categorySpanDiv").append(html);
							}
						} else {
							alert("Something Wrong Happened");
						}
					});

}

function cancelCategory() {

	for (var i = 0; i < allCategoryJson.length; i++) {
		$("#catId" + allCategoryJson[i].catId).attr('checked', false);
	}
	for (var i = 0; i < userCategory.length; i++) {
		$("#catId" + userCategory[i].catId).attr('checked', true);
		$("#catId" + userCategory[i].catId).prop('checked', true);
	}
}

function saveIndustry() {
	var industryIds = new Array();
	industryIds = getCheckedBoxes('indestries');
	$
			.ajax({
				method : "POST",
				url : '../fixer/updateIndustry',
				data : {
					industryIds : industryIds
				}
			})
			.done(
					function(response) {
						if (response.status == "success") {
							$("#industrySpanDiv").empty();
							var html;
							var indstSet = response.result.industryIds;
							for (var i = 0; i < indstSet.length; i++) {
								html = ' <span id="industrySpan'
										+ indstSet[i].indstId
										+ '" >'
										+ indstSet[i].indstName
										+ '<i data-icon="y" class="icon" onclick="removeSelectedIndustry('
										+ indstSet[i].indstId
										+ ')" ></i></span>';
								$("#industrySpanDiv").append(html);
							}
						} else {
							alert("Something Wrong Happened");
						}
					});
}

function getCheckedBoxes(chkboxName) {
	var checkboxes = document.getElementsByName(chkboxName);
	var checkboxesChecked = [];
	for (var i = 0; i < checkboxes.length; i++) {
		if (checkboxes[i].checked) {
			checkboxesChecked.push(checkboxes[i].value);
		}
	}
	return checkboxesChecked.length > 0 ? checkboxesChecked : null;
}

$(document).ready(function() {
	for (var i = 0; i < userCategory.length; i++) {
		$("#catId" + userCategory[i].catId).attr('checked', true);
	}
	highlightParentCatBorder();

	for (var i = 0; i < userIndustry.length; i++) {
		$("#industryItem" + userIndustry[i].indstId).attr('checked', true);
	}

	if (!(linkedInUrl.indexOf("http") > -1)) {

		$('#linkedInUrl').attr('href', 'http://' + linkedInUrl);
	}

});

function removeSelectedCategory(catId) {
	$.ajax({
		method : "POST",
		url : '../fixer/deleteCategory',
		data : {
			catId : catId
		}
	}).done(function(response) {
		if (response.status = "success") {
			$("#categorySpan" + catId).remove();
			$("#catId" + catId).attr('checked', false);
		}
	});

}

function removeSelectedIndustry(indstId) {
	$.ajax({
		method : "POST",
		url : '../fixer/deleteIndustry',
		data : {
			indstId : indstId
		}
	}).done(function(response) {
		if (response.status = "success") {
			$("#industrySpan" + indstId).remove();
			$("#industryItem" + indstId).attr('checked', false);
		}
	});
}

function sendChatMessage() {
	senchatScroll = true;
	var msg = $("#chatTextArea").val();
	$('#chatload').css('display', 'block');
	$.ajax({
		method : "POST",
		url : "../fixer/sendGroupMessage",
		data : {
			message : msg,
			groupId : groupId
		}
	}).done(function(response) {

		var successResponse = response.status;

		if (successResponse == 'success') {

			var messageSet = response.result;
			setchatMessages(messageSet);
			$('#chatTextArea').val('');
		}
		$("#chatsend").css('pointer-events', 'auto');

		$('#chatload').css('display', 'none');

	});
}
function setchatMessages(messageSet) {

	$('.chatbgBody').empty();
	var message = messageSet.chatMessageSet;
	var len = message.length;
	var finalHtml = '';
	var htmlchatTxtR = '<div class="chatTxtR">'
	var span = '<span>'
	var divClearFix = '</span></div><div class="clearfix"></div>';
	var htmlChatTxt = '<div class="chatTxt">';

	for (var i = 0; i < len; i++) {
		if (message[i].user.userId == userId) {
			finalHtml += htmlchatTxtR + message[i].chatMessage + span
					+ message[i].createdAt + divClearFix;
		} else {
			finalHtml += htmlChatTxt + message[i].chatMessage + span
					+ message[i].createdAt + divClearFix;

		}
	}
	$('.chatbgBody').append(finalHtml);
	finalHtml = '';
	
	console.log('chatHArr::'+ chatHArr);
    chatHMax =   Math.max.apply(Math, chatHArr);
    console.log('chatHMax::'+ chatHMax);
    
	if((chatH == chatHMax) || senchatScroll ){

	var objDiv = document.getElementById("chatbgBody");
	objDiv.scrollTop = objDiv.scrollHeight;
	senchatScroll = false;
	}
}

function setAverageFixerRating() {

	var htmlStarRating = '<i data-icon="m" class="icon"></i>';
	var htmlBlankStar = '<i data-icon="l" class="icon"></i>';
	var html = '';

	for (var i = 0; i < 5; i++) {
		if (i < averageFixerRating) {
			html += htmlStarRating;
		} else {
			html += htmlBlankStar;

		}

	}

	$('#averageFixerRating').append(html);
	html = '';
}

/*
 * function showPreviousGiattChartForFixer(){ $.ajax({ method : "POST", url :
 * '../fixer/previousGiattChart', data : { currentStartDate: startDate }
 * }).done(function(response) { if(response.status="success"){ var result =
 * response.result; if(result != null){ startDate = result.chartData.startDate;
 * array = result.chartData.frequencyDurations; chart.clear(); if(array != null &&
 * array.length>0){ drawGanttChart(startDate, array); }else{
 * document.getElementById("chartdiv").innerHTML = "Data not available for
 * graph!"; } } } });
 * 
 * var d = stringToDate(startDate,'dd/MM/yyyy','/'); var locale = "en-us";
 * d.setMonth(d.getMonth()-1);
 * document.getElementById("chartForMonth").innerHTML = d.toLocaleString(locale, {
 * month: "long" })+" " + d.getFullYear(); d =
 * stringToDate(startDate,'dd/MM/yyyy','/'); d.setMonth(d.getMonth()-2);
 * document.getElementById("leftGraphBtn").innerHTML = d.toLocaleString(locale, {
 * month: "short" })+" " + d.getFullYear(); d =
 * stringToDate(startDate,'dd/MM/yyyy','/'); d.setMonth(d.getMonth());
 * document.getElementById("rightGraphBtn").innerHTML = d.toLocaleString(locale, {
 * month: "short" })+" " + d.getFullYear(); }
 * 
 * function showNextGiattChartForFixer(){
 * 
 * $.ajax({ method : "POST", url : '../fixer/nextGanttChart', data : {
 * currentStartDate: startDate } }).done(function(response) {
 * if(response.status="success"){ var result = response.result; if(result !=
 * null){ startDate = result.chartData.startDate; array =
 * result.chartData.frequencyDurations; chart.clear(); if(array != null &&
 * array.length>0){ drawGanttChart(startDate, array); }else{
 * document.getElementById("chartdiv").innerHTML = "Data not available for
 * graph!"; } } } });
 * 
 * var d = stringToDate(startDate,'dd/MM/yyyy','/'); var locale = "en-us";
 * d.setMonth(d.getMonth()+1);
 * document.getElementById("chartForMonth").innerHTML = d.toLocaleString(locale, {
 * month: "long" })+" " + d.getFullYear(); d =
 * stringToDate(startDate,'dd/MM/yyyy','/');
 * document.getElementById("leftGraphBtn").innerHTML = d.toLocaleString(locale, {
 * month: "short" })+" " + d.getFullYear(); d =
 * stringToDate(startDate,'dd/MM/yyyy','/'); d.setMonth(d.getMonth()+2);
 * document.getElementById("rightGraphBtn").innerHTML = d.toLocaleString(locale, {
 * month: "short" })+" " + d.getFullYear(); }
 */

/*
 * function stringToDate(_date,_format,_delimiter) { var
 * formatLowerCase=_format.toLowerCase(); var
 * formatItems=formatLowerCase.split(_delimiter); var
 * dateItems=_date.split(_delimiter); var monthIndex=formatItems.indexOf("mm");
 * var dayIndex=formatItems.indexOf("dd"); var
 * yearIndex=formatItems.indexOf("yyyy"); var
 * month=parseInt(dateItems[monthIndex]); month-=1; var formatedDate = new
 * Date(dateItems[yearIndex],month,dateItems[dayIndex]); return formatedDate; }
 */

/*
 * function drawGanttChart(newStartDate, newArray){
 * 
 * AmCharts.useUTC = true; chart = AmCharts.makeChart( "chartdiv", { "type":
 * "gantt", "theme": "light", "marginRight": 10, "period": "DD",
 * "dataDateFormat":"DD/MM/YYYY", "balloonDateFormat": "JJ:NN", "columnWidth":
 * 0.01, "mouseWheelZoomEnabled":true, "valueAxis": { "type": "date", "minimum":
 * 1, "maximum": 30, "labelsEnabled":false, "axisAlpha":0, "gridAlpha":0 },
 * "categoryAxis": { "gridPosition": "start", "labelsEnabled":false,
 * "axisAlpha":0, "gridAlpha":0 }, "brightnessStep": 0, "graph": { "fillAlphas":
 * 1, "balloonText": "<b>[[task]]</b>: [[open]] [[value]]",
 * "fixedColumnWidth":20 }, "rotate": true, "categoryField": "category",
 * "segmentsField": "segments", "colorField": "color", "startDate":
 * newStartDate, "startField": "start", "endField": "end", "durationField":
 * "duration", "gridAboveGraphs": false, "tapToActivate": true,
 * "zoomOutButtonAlpha":0, "zoomOutButtonImageSize":0,
 * "zoomOutButtonRollOverAlpha":0, "zoomOutText":"", "dataProvider": newArray,
 * "valueScrollbar": { "autoGridCount":false, "srollbarHeight":2,
 * "backgroundAlpha": false, "enabled":false }, "chartCursor": {
 * "cursorColor":"#55bb76", "valueBalloonsEnabled": false, "cursorAlpha": 0,
 * "valueLineAlpha":0.1, "valueLineBalloonEnabled": true, "valueLineEnabled":
 * true, "zoomable":false, "valueZoomable":false }, "export": { "enabled": false } } ); }
 */
// var chatArdius = '';
// var chartPieY = '';
// var chatLegendOrientation = '';
//	
// if (($(window).width()>1024)) {
// $('.amChartsLegend').css({"top": "130px !important;"})
// }
// else if (($(window).width()<=1024)) {
// $('.amChartsLegend').css({"top": "180px !important;"})
//		   
// }
var chart = AmCharts.makeChart("chartdiv", {
	"type" : "pie",
	"startDuration" : 0,
	"theme" : "light",
	"addClassNames" : true,
	"labelsEnabled" : false,
	"pullOutOnlyOne" : true,
	"pullOutRadius" : "1%",
	"radius" : "50%",
	"pieY" : "50%",
	"legend" : {
		"position" : "right",
		"verticalGap" : 0,
		"autoMargins" : false,
		"valueWidth" : 0,
		"switchable" : false,
		"fontSize" : 10,
		"spacing" : 0,
		"markerSize" : 8,
		"labelWidth" : 120,
		"top" : 200

	},
	"innerRadius" : "40%",
	"defs" : {
		"filter" : [ {
			"id" : "shadow",
			"width" : "100%",
			"height" : "100%",
			"feOffset" : {
				"result" : "offOut",
				"in" : "SourceAlpha",
				"dx" : 0,
				"dy" : 0
			},
			"feGaussianBlur" : {
				"result" : "blurOut",
				"in" : "offOut",
				"stdDeviation" : 5
			},
			"feBlend" : {
				"in" : "SourceGraphic",
				"in2" : "blurOut",
				"mode" : "normal"
			}
		} ]
	},
	"dataProvider" : array,
	"valueField" : "percentage",
	"titleField" : "module",
	"export" : {
		"enabled" : true
	}
});

chart.addListener("init", handleInit);

chart.addListener("rollOverSlice", function(e) {
	handleRollOver(e);
});

function handleInit() {
	chart.legend.addListener("rollOverItem", handleRollOver);
}

function handleRollOver(e) {
	var wedge = e.dataItem.wedge.node;
	wedge.parentNode.appendChild(wedge);
}

function comparePassword() {

	var bool;
	var oldPassword = $('#oldPassword').val();
	if (oldPassword.trim() == '' || oldPassword == null) {
		oldPassword = $('#password').val();

	}
	$.ajax({
		method : "POST",
		url : "../fixer/getComparePassword",
		data : {
			password : oldPassword

		},

		async : false
	}).done(
			function(response) {

				var getComparePasswordStatus = response.status;
				if (getComparePasswordStatus == 'success') {

					bool = true;
				} else {
					$('#spanOldPassword').text("old password does not match.")
							.css('display', 'block');
					bool = false;
				}

			});
	return bool;

}

function updateMessageNotification() {
	$.ajax({
		method : "POST",
		url : "../fixer/updateMessageNotification",

	}).done(
			function(response) {

				if (response.status == 'success') {
					$('#messageTab').removeClass("newmessage wow fadeIn")
							.addClass("nullmessage");
					$('.no').text(0);
				}
			});

}
function checkVisibility() {

	var msg = $("#chatTextArea").val();
	if ($('.messageChat').is(':visible')) {
		if (msg == null || msg.trim() == '') {
			loadGroupMessage();
		}
	} else {
		adminMessageNotification();
	}
}


function adminMessageNotification(){
	$.ajax({
		method : "POST",
		url : "../fixer/adminMessageNotification",
		data : {
		}
	}).done(function(response) {
		var successResponse = response.status;
		if (successResponse == 'success') {
			 countMsg = response.result.countMsg;
				if(countMsg!=0){
				loadGroupMessage();
				$('#messageTab').addClass("newmessage wow fadeIn").removeClass("nullmessage");
				$('#message_count_id').text(countMsg);
				}
		}
	});
}

function loadGroupMessage() {
	var msg = $("#chatTextArea").val();
	var visibleFlag = false;
	if($('.messageChat').is(':visible')){
		visibleFlag = true;
	}
	if (msg == null || msg.trim() == '') {
		req = $.ajax({
			method : "POST",
			url : "../fixer/loadGroupMessage",
			data : {
				groupId : groupId,
				visibleFlag : visibleFlag
			}
		}).done(function(response) {

			var successResponse = response.status;
			if (successResponse == 'success') {
				var messageSet = response.result;
				setchatMessages(messageSet);
			}
		});
	} else {
		req.abort();
	}
	$('#chatTextArea').on('keydown', function() {
		req.abort();
	});
}
function changePassDiv() {

	
	var password = $('#password').val();
	if (password.trim() != '' && password != null) {
		var bool = comparePassword();
		
		if (bool) {
			$('#staticPass').css('display', 'none');
           $('.alertF').css('display', 'none')
			$('#passDiv').css('display', 'block')

		} else {
			$('#spanPassword').text("old password does not match.");
			$('#spanPassword').css('display', 'block');
		}
	} else {
		$('#spanPassword').text("please enter old password");
		$('#spanPassword').css('display', 'block');
	}
}

jQuery(document).ready(function($){
	
	
	
    jQuery('.quickconnect').click(function(){
		jQuery('#popup').fadeIn(1000);
		jQuery('body').addClass('overlay');
		var popuph = document.getElementById('popup').offsetHeight;	
		var inpopuph = document.getElementById('inpopup').offsetHeight;
		if(popuph>500){					
			jQuery('#inpopup').css('margin',+(popuph-inpopuph)/2+'px auto');
		}
	});
	
	jQuery('.closepopup').click(function(){
		jQuery('#popup').fadeOut(1000);
		jQuery('body').removeClass('overlay');
	});
});