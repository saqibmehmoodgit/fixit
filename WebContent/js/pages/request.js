var currentPage = 1;

var searchedText = '';
var reqyestAjax = null;
var lodeMoreId = 0;
var indexTohide = -1;

$(document).ready(function() {
	/*
	 * $("input").keydown(function (e) {
	 * 
	 * if (e.which == 9) var id = $(this).closest("div").attr("class");
	 * alert(id); });
	 */
	$('textarea').focus(function() {
		$('.newRequest').css('opacity', '0.5');
		$("#titleDescDiv").css('opacity', '1')
		
	});
	$('input').focus(function() {
          
		var ids = $(this).parents('div[class="newRequest"]').attr('id');
		switch (ids) {
		case "titleDescDiv":
			$('.newRequest').css('opacity', '0.5');
			$("#" + ids).css('opacity', '1');
			break;

		case "categDiv":
			$('.newRequest').css('opacity', '0.5');
			$("#" + ids).css('opacity', '1');
			break;

		case "attacDiv":
			$('.newRequest').css('opacity', '0.5');
			$("#" + ids).css('opacity', '1');
			break;

		case "proposedDiv":
			$('.newRequest').css('opacity', '0.5');
			$("#" + ids).css('opacity', '1');
			break;

		case "findFixerDiv":
			$('.newRequest').css('opacity', '0.5');
			$("#" + ids).css('opacity', '1');
			break;

		default:
			break;
		}
	});

	$('#titleDescDiv').on('click', function() {

		$('.newRequest').css('opacity', '0.5');
		$(this).css('opacity', '1')
	});

	$('#categDiv').on('click', function() {

		$('.newRequest').css('opacity', '0.5');
		$(this).css('opacity', '1')
	});

	$('#attacDiv').on('click', function() {

		$('.newRequest').css('opacity', '0.5');
		$(this).css('opacity', '1')
	});

	$('#proposedDiv').on('click', function() {

		$('.newRequest').css('opacity', '0.5');
		$(this).css('opacity', '1')
	});

	$('#findFixerDiv').on('click', function() {

		$('.newRequest').css('opacity', '0.5');
		$(this).css('opacity', '1')
	});

	$('#reAssinged').css('display', 'none');

	$('a').removeClass('active');
	$('#newRequest').addClass('active');

	$('#add_more').click(function() {
		$('.custm_popup_resolve').css('display', 'block');
		$('body').css('overflow', 'hidden');
	});
	$('.cclose').click(function() {
		$('.hp-cell').css('opacity', '1');

		$('.custm_popup_resolve').css('display', 'none');
		$('body').css('overflow', 'auto');
	});

	$('.cancel').click(function() {
		$('.hp-cell').css('opacity', '1');
		$('.custm_popup_resolve').css('display', 'none');
		$('body').css('overflow', 'auto');
	});
	$('#icon').click(function() {
		$('.hp-cell').css('opacity', '1');
		$('.custm_popup_resolve').css('display', 'none');
		$('body').css('overflow', 'auto');
	});
	$("#chatsend").bind('click', function() {
		var chatTextArea = $('#chatTextArea').val();
		if (chatTextArea != null && chatTextArea.trim() != '') {
			userChat();
		}
	});
	$('#money_575').bind('click', function() {
		$('#money').text('575');
		$('.hp-cell').css('opacity', '0.5');
		$('#purple').css('opacity', '1');
		$('#purchaseCredits').css({
			"background" : "#b13794",
			"color" : "#fff"
		});

	});

	$('#money_1125').bind('click', function() {
		$('#money').text('1,125');
		$('.hp-cell').css('opacity', '0.5');
		$('#purpleDark').css('opacity', '1');
		$('#purchaseCredits').css({
			"background" : "#63429a",
			"color" : "#fff"
		});

	});

	$('#money_2200').bind('click', function() {
		$('#money').text('2,200');
		$('.hp-cell').css('opacity', '0.5');
		$('#green').css('opacity', '1');
		$('#purchaseCredits').css({
			"background" : "#28b78a",
			"color" : "#fff"
		});

	});

	$('#money_4300').bind('click', function() {
		$('#money').text('4,300');
		$('.hp-cell').css('opacity', '0.5');
		$('#blue').css('opacity', '1');
		$('#purchaseCredits').css({
			"background" : "#1c75bc",
			"color" : "#fff"
		});

	});

	$('#purchaseCredits').bind('click', function() {
		var money = $('#money').text();
		money = money.replace(',','');
		money = parseInt(money);
		var userurl = window.location.href;
		userurl = userurl.split('/member/')[1];
		
		if (money != 0) {
			
			if (userurl.indexOf('reassignRequest') >= 0)
			{
				var postData = $('#reassignedForm').serialize();	
				userurl +=  postData;
				userurl = userurl.replace('?',',');
				userurl = userurl.replace('&',',');
				userurl = '\"'+userurl+'\"'
			}
			var url = '../member/paypal?amount=' + money +'&userUrl='+userurl;
			
			window.location.href= url;

			/*var win = window.open(url, '_blank');
			win.focus();*/
		}
	});
/*
	if (credits < 1) {

		$('.custm_popup_resolve').css('display', 'block');
		$('body').css('overflow', 'hidden');
	}*/

});

var client = new XMLHttpRequest();
function uploadDocument() {
	var file = document.getElementById("documentUpload");
	/ Create a FormData instance /
	var formData = new FormData();
	formData.append("file", file.files[0]);

	formData.append("userId", id);
	formData.append("queryId", 0);
	$("#uploadLoader").css('display', 'inline-block');
	document.getElementById("documentUpload").disabled = true;

	// console.log(formData);
	client.open("post", "../member/createDocFolder", true);
	client.send(formData);
}

client.onreadystatechange = function() {
	if (client.readyState == 4 && client.status == 200) {

		var data = jQuery.parseJSON(client.response);
		var fileName = data.result.originalFileName;
		var fileUniqueCode = data.result.fileUniqueCode;
		var queryId = data.result.queryId;
		var fileUrl = data.result.fileUrl;
		var awsFileUrl = data.result.awsFileUrl
		document.getElementById("documentUpload").disabled = false;
		$("#uploadLoader").css('display', 'none');
		// var fileFirstName = fileName.split(".");
		// var firstName = fileFirstName[0];
		/* $("#documentLoader").hide(); */
		$("#documentUpload").val("");
		console.log("file_url::::" + fileUrl);

		var html = '<div class = "download" id="'
				+ data.result.fileUniqueCode
				+ '"><span>Download:</span><a href="'
				+ awsFileUrl
				+ '"><input type="text" name = "documents" value ="'
				+ data.result.originalFileName
				+ '" readonly></a><i data-icon="y" class="icon" onclick = "deleteFile(\''
				+ fileUrl + '\',\'' + fileUniqueCode + '\',\'' + queryId
				+ '\')"></i></div>';
		// var html='<div class="alert alert-info text-success2"
		// id="'+data.result.fileUniqueCode+'"
		// onclick="deleteFile(\''+data.result.originalFileName+'\',\''+data.result.fileUniqueCode+'\')"
		// ><input type="text" name ="documents"
		// value="'+data.result.originalFileName+'"><i class="fa
		// fa-times"></i></p>';
		$("#attach").append(html);
		/*
		 * if(modeType=="CREATE"){ $('#createIssue').removeAttr('disabled');
		 * }else{ $('#editIssue').removeAttr('disabled'); }
		 */
	}

}

function openToAllFixerClicked() {
	
	$('#interestedFixers_searched').empty();
	currentPage = 1;
	$("#filterDivsearch").slideUp();
	$("#fixersIds").val('0');
	$("#submitBtnImpl").text('Submit Request');
}

function detailFixerClick(id) {
	closeFixerClick(indexTohide);
	$('.clientHead').css('opacity', '0.5');
	$('#clientHead' + id).css('opacity', '1');
	$('#clientB' + id).slideDown();
	$('#details' + id).hide();
	$('#closeC' + id).show();
	indexTohide = id;
}

function closeFixerClick(id) {
	$('.clientHead').css('opacity', '1');
	$('#clientB' + id).slideUp();
	$('#details' + id).show();
	$('#closeC' + id).hide();
}

function sendSpecificFixerClick(searchFieldText) {
	
	if (reqyestAjax != null)
		reqyestAjax.abort();
	$("#filterDivsearch").slideDown();
	var categoryIds = getCheckedBoxes('categories');
	if (categoryIds == null) {
		categoryIds = [ -1 ];
	}
	
	$("#fixersIds").val(selectedFixers);
	$("#submitBtnImpl").text('Submit Requests (' + selectedFixers.length + ')');

	var countryName = myUserJson.country;
	reqyestAjax = $.ajax({
		method : "POST",
		url : "../member/fixerList",
		data : {
			searchFieldText : searchFieldText,
			categoryIds : categoryIds,
			country : countryName,
			pageNo : currentPage

		}
	}).done(function(response) {

		currentPage = response.result.currentPage;
		var len = response.result.fixers.length;
		if (len > 0) {
			$('.requestloadmore').css('display', 'block');
			setSearchedFixers(response);
		} else {

			$('.requestloadmore').css('display', 'none');
		}

	});

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

function loadMore() {
	sendSpecificFixerClick(searchedText);

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

function setSearchedFixers(response) {

	// array of selected index
	var selectedIndexs = [];
	var length = 0;
	if (response.result.fixers == null)
		length = response.result.fixers.length;
	var html;

	for (var i = 0; i < response.result.fixers.length; i++) {
		var id = i + lodeMoreId;
		var catList = '';
		for (var j = 0; j < response.result.fixers[i].categoryList.length; j++) {
			catList = catList + response.result.fixers[i].categoryList[j]
					+ ', ';
		}
		catList = catList.substring(0, catList.length - 2);
		var companyName = '';

		if (response.result.fixers[i].companyName != null)
			companyName = response.result.fixers[i].companyName;
		html = '';
		html = '<div class= "clientDetail" id="clientDetail' + id + '">'
				+ '<div id="clientHead' + id + '" class="clientHead">';
		if (response.result.fixers[i].profilePhoto == '') {
			html += '<img src="../images/profile.png" onerror= "this.src =\'../images/profile.png\'">';
		} else {
			html += '<img src="'
					+ response.result.fixers[i].profilePhoto
					+ '" onerror= "this.src =\'../images/profile.png\'"><img src="../flags/'
					+ response.result.fixers[i].country
					+ '.png" class="CuntryFlag"  onerror= "this.src =\'../flags/United States of America(USA).png\'">';
		}
		if (response.result.fixers[i].country == '') {
			html += '<img src="../images/profile.png" class="CuntryFlag"  onerror= "this.src =\'../flags/United States of America(USA).png\'">';

		} else {
			html += '<img src="../flags/'
					+ response.result.fixers[i].country
					+ '.png" class="CuntryFlag"  onerror= "this.src =\'../flags/United States of America(USA).png\'">';

		}
		html += '<h1><span id="searchedFixerNAme' + id
				+ '" onclick="detailFixerClick(' + id + ')" class="name">'
				+ response.result.fixers[i].firstName + '&nbsp;'
				+ response.result.fixers[i].lastName + '</span>';
		if (response.result.fixers[i].favUnFavstatus == 'U')
			html += ' <span id = "fav'
					+ id
					+ '" onclick = "makeUserFav('
					+ response.result.fixers[i].userId
					+ ','
					+ id
					+ ')" class="fixertag"> <i data-icon="k" class="icon"></i> <span id = "favtext'
					+ id + '" style="display:none">Favorite</span>';
		else
			html += ' <span id = "fav'
					+ id
					+ '" onclick = "makeUserUnFav('
					+ response.result.fixers[i].userId
					+ ','
					+ id
					+ ')" class="fixertag green"> <i data-icon="k" class="icon"></i> <span id = "favtext'
					+ id + '">Favorite</span>';

		html += '</span> <span id="companyName'
				+ id
				+ '" >'
				+ companyName
				+ '</span>'
				+ '</h1>'
				+ '<span id = "ratingC'
				+ id
				+ '" class="ratingC"> <i id = "rating1_'
				+ id
				+ '" data-icon="l" class="icon"></i>'
				+ '	<i id = "rating2_'
				+ id
				+ '" data-icon="l" class="icon"></i> <i id = "rating3_'
				+ id
				+ '" data-icon="l" class="icon"></i>'
				+ '	<i id = "rating4_'
				+ id
				+ '" data-icon="l" class="icon"></i> '
				+ '	<i id= "rating5_'
				+ id
				+ '" data-icon="l" class="icon"></i>'
				+ '</span> <span style="display: block;" class = "details" id="details'
				+ id + '" onclick="detailFixerClick(' + id
				+ ')">Details</span>'
				+ '<span style="display: none;" class="closeC" id="closeC' + id
				+ '" onclick="closeFixerClick(' + id + ')">Close</span>'
				+ '</div>'
				+ '<div style="display: none;" class="clientB" id="clientB'
				+ id + '">' + '<div class="clientD">'
				+ '	<span class="location">Location: <strong>'
				+ response.result.fixers[i].city + ', '
				+ response.result.fixers[i].country + '</strong></span>';
		if (response.result.fixers[i].linkedinProfile.indexOf("http") < 0)
			html += '<a href = "http://'
					+ response.result.fixers[i].linkedinProfile
					+ '" target="_blank"><span class="linkdin">';
		else
			html += '<a href = "' + response.result.fixers[i].linkedinProfile
					+ '" target="_blank"><span class="linkdin">';

		html += '	<img src="../images/in.png" alt=""> '
				+ response.result.fixers[i].linkedinProfile + '</span></a>'
				+ '<div class="clearfix"></div>'
				+ '<span class="time">Time Zone: <strong>'
				+ response.result.fixers[i].timeZone + '</strong></span> '
				+ '<span class="time">Fixer Since: <strong>'
				+ response.result.fixers[i].fixersSince + '</strong></span> '
				/*+ '<span class="time">Fixed Requests: <strong>'
				+ response.result.fixers[i].fixedCounts + '</strong></span>'
				+ '<span class="time">Resolved within Deadline: <strong>'
				+ response.result.fixers[i].fixedUnderdeadline
				+ '</strong></span>'
				+ '<span class="time">Last Active: <strong>'
				+ response.result.fixers[i].lastLogin + '</strong></span>'*/
				+ '<p>'
				+ response.result.fixers[i].overview + '</p>'
				+ '<span class="categorie">Categories: <strong>' + catList
				+ ' </strong></span>' + '	</div>' ;

		if (contains(selectedFixers, [ response.result.fixers[i].userId ]) == true) {
			selectedIndexs.push(id.toString());
			html += '<div id="submitbtn' + id
					+ '" class="rateFixer" onClick="removeRequestToFixer('
					+ response.result.fixers[i].userId + ',\''
					+ response.result.fixers[i].firstName + '\',\''
					+ response.result.fixers[i].lastName + '\',' + id
					+ ')"  > Deselect ' + response.result.fixers[i].firstName
					+ '&nbsp;' + response.result.fixers[i].lastName + '</div>';
		} else {
			html += '<div id="submitbtn' + id
					+ '" class="rateFixer" onClick="submitRequestToFixer('
					+ response.result.fixers[i].userId + ',\''
					+ response.result.fixers[i].firstName + '\',\''
					+ response.result.fixers[i].lastName + '\',' + id
					+ ')"  > Submit Request to '
					+ response.result.fixers[i].firstName + '&nbsp;'
					+ response.result.fixers[i].lastName + '</div>';
		}
		html += '</div></div>';
		$("#interestedFixers_searched").append(html);
		
		for (var j = 1; j <= response.result.fixers[i].fixerRating; j++) {
			
			$("#rating" + j + "_" + id).attr("data-icon", "m");
		}
	}
	for(var k=0;k<selectedIndexs.length;k++)
		{
		setSelectedFIxerDivColor(selectedIndexs[k], 'selected');
		}
	
	lodeMoreId += response.result.fixers.length;

}

function setSearchedFixersReassignRequest(response, fixerId) {

	var selectedIndexs = [];
	var length = 0;
	if (response.result.fixers == null)
		length = response.result.fixers.length;
	var html;

	for (var i = 0; i < response.result.fixers.length; i++) {
		var id = i + lodeMoreId;
		var catList = '';
		for (var j = 0; j < response.result.fixers[i].categoryList.length; j++) {
			catList = catList + response.result.fixers[i].categoryList[j]
					+ ', ';
		}
		catList = catList.substring(0, catList.length - 2);
		var companyName = '';

		if (response.result.fixers[i].companyName != null)
			companyName = response.result.fixers[i].companyName;
		if(fixerId != response.result.fixers[i].userId)
		{	
			
		html = '';
		html = '<div class= "clientDetail" id="clientDetail' + id + '">'
				+ '<div id="clientHead' + id + '" class="clientHead">';
		if (response.result.fixers[i].profilePhoto == '') {
			html += '<img src="../images/profile.png" onerror= "this.src =\'../images/profile.png\'">';
		} else {
			html += '<img src="'
					+ response.result.fixers[i].profilePhoto
					+ '" onerror= "this.src =\'../images/profile.png\'"><img src="../flags/'
					+ response.result.fixers[i].country
					+ '.png" class="CuntryFlag"  onerror= "this.src =\'../flags/United States of America(USA).png\'">';
		}
		if (response.result.fixers[i].country == '') {
			html += '<img src="../images/profile.png" class="CuntryFlag"  onerror= "this.src =\'../flags/United States of America(USA).png\'">';

		} else {
			html += '<img src="../flags/'
					+ response.result.fixers[i].country
					+ '.png" class="CuntryFlag"  onerror= "this.src =\'../flags/United States of America(USA).png\'">';

		}
		html += '<h1><span id="searchedFixerNAme' + id
				+ '" onclick="detailFixerClick(' + id + ')" class="name">'
				+ response.result.fixers[i].firstName + '&nbsp;'
				+ response.result.fixers[i].lastName + '</span>';
		if (response.result.fixers[i].favUnFavstatus == 'U')
			html += ' <span id = "fav'
					+ id
					+ '" onclick = "makeUserFav('
					+ response.result.fixers[i].userId
					+ ','
					+ id
					+ ')" class="fixertag"> <i data-icon="k" class="icon"></i> <span id = "favtext'
					+ id + '" style="display:none">Favorite</span>';
		else
			html += ' <span id = "fav'
					+ id
					+ '" onclick = "makeUserUnFav('
					+ response.result.fixers[i].userId
					+ ','
					+ id
					+ ')" class="fixertag green"> <i data-icon="k" class="icon"></i> <span id = "favtext'
					+ id + '">Favorite</span>';

		html += '</span> <span id="companyName'
				+ id
				+ '" >'
				+ companyName
				+ '</span>'
				+ '</h1>'
				+ '<span id = "ratingC'
				+ id
				+ '" class="ratingC"> <i id = "rating1_'
				+ id
				+ '" data-icon="l" class="icon"></i>'
				+ '	<i id = "rating2_'
				+ id
				+ '" data-icon="l" class="icon"></i> <i id = "rating3_'
				+ id
				+ '" data-icon="l" class="icon"></i>'
				+ '	<i id = "rating4_'
				+ id
				+ '" data-icon="l" class="icon"></i> '
				+ '	<i id= "rating5_'
				+ id
				+ '" data-icon="l" class="icon"></i>'
				+ '</span> <span style="display: block;" class = "details" id="details'
				+ id + '" onclick="detailFixerClick(' + id
				+ ')">Details</span>'
				+ '<span style="display: none;" class="closeC" id="closeC' + id
				+ '" onclick="closeFixerClick(' + id + ')">Close</span>'
				+ '</div>'
				+ '<div style="display: none;" class="clientB" id="clientB'
				+ id + '">' + '<div class="clientD">'
				+ '	<span class="location">Location: <strong>'
				+ response.result.fixers[i].city + ', '
				+ response.result.fixers[i].country + '</strong></span>';
		if (response.result.fixers[i].linkedinProfile.indexOf("http") < 0)
			html += '<a href = "http://'
					+ response.result.fixers[i].linkedinProfile
					+ '" target="_blank"><span class="linkdin">';
		else
			html += '<a href = "' + response.result.fixers[i].linkedinProfile
					+ '" target="_blank"><span class="linkdin">';

		html += '	<img src="../images/in.png" alt=""> '
				+ response.result.fixers[i].linkedinProfile + '</span></a>'
				+ '<div class="clearfix"></div>'
				+ '<span class="time">Time Zone: <strong>'
				+ response.result.fixers[i].timeZone + '</strong></span> '
				+ '<span class="time">Fixer Since: <strong>'
				+ response.result.fixers[i].fixersSince + '</strong></span> '
				/*+ '<span class="time">Fixed Requests: <strong>'
				+ response.result.fixers[i].fixedCounts + '</strong></span>'
				+ '<span class="time">Resolved within Deadline: <strong>'
				+ response.result.fixers[i].fixedUnderdeadline
				+ '</strong></span>'
				+ '<span class="time">Last Active: <strong>'
				+ response.result.fixers[i].lastLogin + '</strong></span>'*/
				+ '<p class="overview">'
				+ response.result.fixers[i].overview + '</p>'
				+ '<span class="categorie">Categories: <strong>' + catList
				+ ' </strong></span>' + '	</div>' ;

		if (contains(selectedFixers, [ response.result.fixers[i].userId ]) == true) {
			selectedIndexs.push(id.toString());
			html += '<div id="submitbtn' + id
					+ '" class="rateFixer" onClick="removeRequestToFixer('
					+ response.result.fixers[i].userId + ',\''
					+ response.result.fixers[i].firstName + '\',\''
					+ response.result.fixers[i].lastName + '\',' + id
					+ ')"  > Deselect ' + response.result.fixers[i].firstName
					+ '&nbsp;' + response.result.fixers[i].lastName + '</div>';
		} else {
			html += '<div id="submitbtn' + id
					+ '" class="rateFixer" onClick="submitRequestToFixer('
					+ response.result.fixers[i].userId + ',\''
					+ response.result.fixers[i].firstName + '\',\''
					+ response.result.fixers[i].lastName + '\',' + id
					+ ')"  > Submit Request to '
					+ response.result.fixers[i].firstName + '&nbsp;'
					+ response.result.fixers[i].lastName + '</div>';
		}
		html += '</div></div>';
		$("#interestedFixers_searched").append(html);
		
		for (var j = 1; j <= response.result.fixers[i].fixerRating; j++) {
			
			$("#rating" + j + "_" + id).attr("data-icon", "m");
		}
	}
	}
	for(var k=0;k<selectedIndexs.length;k++)
	{
		setSelectedFIxerDivColor(selectedIndexs[k], 'selected');
	}
	lodeMoreId += response.result.fixers.length;

}

function findCatByparentCatToShowOnList() {
	var parentId = $('#parentCategory').val();
	if (parentId == '-1') {
		$("#childCatDropDownList").empty();
	} else {
		$("#childCatDropDownList").empty();
		var html = '';
		html += '<label>Child Category</label><select class="select" id="catSelection" style="cursor:pointer" onchange="ratingChangeListener()"><option value = "-1">Select Child Category</option>';
		for (var i = 0; i < allCategory.length; i++) {
			if (allCategory[i].parentId == parentId) {
				html += '<option value="' + allCategory[i].catId + '">'
						+ allCategory[i].catgName + '</option>';
			}
		}
		html += '</select><i data-icon="y" class="icon" onclick="childcatIconClick()"></i>';
		$("#childCatDropDownList").append(html);
		ratingChangeListener();
	}
}

function childcatIconClick() {
	$("#childCatDropDownList").empty();
	ratingChangeListener();
}

function searchNameChanged(searchFieldText) {
	searchedText = searchFieldText;
	currentPage = 1;
	$("#interestedFixers_searched").empty();
	sendSpecificFixerClick(searchFieldText);
}

function searchNameChangedReassignRequest(searchFieldText, fixerId) {
	searchedText = searchFieldText;
	currentPage = 1;
	$("#interestedFixers_searched").empty();
	sendSpecificFixerClickReassignRequest(searchFieldText, fixerId);
}

function sendSpecificFixerClickReassignRequest(searchFieldText, fixerId) {
	
	if (reqyestAjax != null)
		reqyestAjax.abort();
	$("#filterDivsearch").slideDown();
	var categoryIds = getCheckedBoxes('categories');
	if (categoryIds == null) {
		categoryIds = [ -1 ];
	}
	$("#fixersIds").val(selectedFixers);
	$("#submitBtnImpl").text('Submit Requests (' + selectedFixers.length + ')');

	var countryName = myUserJson.country;
	reqyestAjax = $.ajax({
		method : "POST",
		url : "../member/fixerList",
		data : {
			searchFieldText : searchFieldText,
			categoryIds : categoryIds,
			country : countryName,
			pageNo : currentPage

		}
	}).done(function(response) {

		currentPage = response.result.currentPage;
		var len = response.result.fixers.length;
		if (len > 0) {
			$('.requestloadmore').css('display', 'block');
			setSearchedFixersReassignRequest(response, fixerId);
		} else {

			$('.requestloadmore').css('display', 'none');
		}

	});

}


function ratingChangeListener() {

	var categoryId = '-1';
	var searchFieldText = $('#searchId').val();
	var selectedRate = $('#selectedRating').val();

	var countryName = $('#selectedCountry').val();
	var parentCat = $('#parentCategory').val();
	if (parentCat == '-1') {
		categoryId = '-1';
	} else {
		categoryId = parentCat;
		var chilCat = $('#catSelection').val();
		if (typeof chilCat != 'undefined' && chilCat != '-1')
			categoryId = chilCat;
	}
	sendSpecificFixerClick(searchFieldText, categoryId, selectedRate,
			countryName)
}

function onFilterClick() {
	$("#ParentCategory").slideDown();
	$("#SearchIndustry").slideDown();
	$("#SearchRating").slideDown();
	$("#SearchCuntry").slideDown();
	$("#childCatDropDownList").slideDown();
	$("#filter").css({
		"display" : "none"
	});
	$("#ClearFilter").css({
		"display" : "block"
	});
}
function onClearFilterClick() {
	$("#ParentCategory").slideUp();
	$("#childCatDropDownList").slideUp();
	$("#SearchCuntry").slideUp();
	$("#SearchIndustry").slideUp();
	$("#SearchRating").slideUp();
	$("#SearchCuntry").slideUp();
	$("#filter").css({
		"display" : "block"
	});
	$("#ClearFilter").css({
		"display" : "none"
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

function validationCheck(points) {
	$('.alertF').css('display', 'none');

	var date = $('#datetimepicker1').val();
	var time = $('#datetimepicker2').val();
	var querytitle = $('#querytitle').val();

	var error = false;
	if ($("#querytitle").val() == '') {
		$("#querytitleAlert").css({
			"display" : "block"
		});
		$("#querytitleAlert").text("Please enter the title of the request.");

		error = true;

	}
	if ($("#queryDesc").val() == '') {
		$("#queryDescAlert").css({
			"display" : "block"
		});
		error = true;
	}
	if (getCheckedBoxes('categories') == null) {
		$("#categoriesAlert").css({
			"display" : "block"
		});
		error = true;
	}
	if ($("#hours").val() < 1) {
		$("#hoursAlert").css({
			"display" : "block"
		});
		$("#hoursAlert").text('Estimated hours must be greater than 0');
		error = true;
	}
	/*if ($("#hours").val() > points) {
		$("#hoursAlert").css({
			"display" : "block"
		});
		$("#hoursAlert").text(
				'Estimated hours must be less then avaialble points');
		error = true;
	}*/

	if (!$('#leaverequest').is(':checked')
			&& (selectedFixers == null || selectedFixers.length == 0)) {
		$("#leaverequestAlert").css({
			"display" : "block"
		});
		error = true;
	}

	if ((date == null || date == '') || (time == null || time == '')) {

		$("#dateTimeAlert").text(" Please Select date")

		$("#dateTimeAlert").css({
			"display" : "block"
		});
		error = true;
	}

	if (date != null && date.trim() != '') {
		var bool = validateDate(setCurrentDate(''), date)
		if (!bool) {
			$("#dateTimeAlert").css({
				"display" : "block"
			});
			$("#dateTimeAlert").text("You can not select previous date");
			error = true;
		}

	}
	if(querytitle.length >100){
		
		
		$("#querytitleAlert").css({
			"display" : "block"
		});
		$("#querytitleAlert").text("Title length must less than 100");
		error = true;
	}
	
	if (error == false) {
		$('#submitBtnImpl').attr('disabled',true);
		$('#clearButton').attr('disabled', true);
		addhoursDateTime(0);
		$("#contact-form").submit();
	} else{
		$('#submitBtnImpl').attr('disabled',false);
		$('#clearButton').attr('disabled', false);

		window.scrollTo(0, 0);
	}

}

function clearBtnClick() {
	window.location.replace("../member/newRequest");

}

var selectedFixers = new Array();

function submitRequestToFixer(id, firstName, lastName, index) {
	selectedFixers.push(id);
	$("#submitBtnImpl").text('Submit Requests (' + selectedFixers.length + ')');
	$("#submitbtn" + index).text('Deselect ' + firstName + ' ' + lastName);
	$("#fixersIds").val(selectedFixers);
	document.getElementById('submitbtn' + index).setAttribute(
			'onclick',
			'removeRequestToFixer(' + id + ',\'' + firstName + '\',\''
					+ lastName + '\',' + index + ')')
	// document.getElementById("submitbtn"+index).onclick =
	// removeRequestToFixer(id,firstName,lastName,index);
	setSelectedFIxerDivColor(index, 'selected');
}

function removeRequestToFixer(id, firstName, lastName, index) {
	var j = selectedFixers.indexOf(id);
	if (j != -1) {
		selectedFixers.splice(j, 1);
	}
	$("#submitBtnImpl").text('Submit Requests (' + selectedFixers.length + ')');
	$("#submitbtn" + index).text(
			'Submit Request to ' + firstName + ' ' + lastName);
	$("#fixersIds").val(selectedFixers);
	document.getElementById('submitbtn' + index).setAttribute(
			'onclick',
			'submitRequestToFixer(' + id + ',\'' + firstName + '\',\''
					+ lastName + '\',' + index + ')');
	setSelectedFIxerDivColor(index, 'deselected');
}

function makeUserFav(userId, index) {
	$.ajax({
		method : "POST",
		url : "../member/fixerFavourite",
		data : {
			fixerId : userId

		}
	}).done(
			function(response) {
				if (response.result.response == 'success') {
					$("#fav" + index).addClass('green');
					$("#favtext" + index).css('display', 'block');
					document.getElementById('fav' + index).setAttribute(
							'onclick',
							'makeUserUnFav(' + userId + ',' + index + ')');
				}

			});
}

function makeUserUnFav(userId, index) {
	$.ajax({
		method : "POST",
		url : "../member/deleteFavouriteFixer",
		data : {
			fixerId : userId

		}
	}).done(
			function(response) {
				if (response.status == 'success') {
					document.getElementById('fav' + index).setAttribute(
							'onclick',
							'makeUserFav(' + userId + ',' + index + ')')
					$("#fav" + index).removeClass('green');
					$("#favtext" + index).css('display', 'none');

				}

			});
}

function disableEnterKey(e) {

	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox

	return (key != 13);
}

function deleteFile(filePath, fileUniqueCode, queryid) {

	if ($('#uploadLoader').is(':visible')) {
		alert("Already inProgress");
		return;
	}

	$("#uploadLoader").show();
	if (modeType == "CREATE") {

		$.ajax({
			method : "POST",
			url : "../member/deleteFileCreate",
			data : {
				fileName : filePath,
				userId : userId
			}
		}).done(function(response) {
			$("#uploadLoader").hide();

			$("#" + fileUniqueCode).remove();

		});
	}

	if (modeType == "EDIT") {

		$.ajax({
			method : "POST",
			url : "../member/deleteFileEdit",
			data : {
				fileName : filePath,
				userId : userId,
				queryId : queryid,
				fileUniqueCode : fileUniqueCode

			}
		}).done(function(response) {
			$("#uploadLoader").hide();
			$("#" + fileUniqueCode).remove();

		});
	}

}

function deleteFileReassign(filePath, fileUniqeCode, queryid) {

	modeType = "EDIT";
	deleteFile(filePath, fileUniqeCode, queryid);
}

function deleteFileCopyRequest(fileUniqeCode) {

	$("#" + fileUniqeCode).remove();

}

function setSelectedFIxerDivColor(index, selectDeselect) {
	var sel = selectDeselect;
	if (sel == 'selected') {
		$('#clientHead' + index).css('background', '#478fc9');
		$('#searchedFixerNAme' + index).css('color', '#fff');
		$('#favtext' + index).css('color', '#fff');
		$('#closeC' + index).css('color', '#fff');
		$('#details' + index).css('color', '#fff');
		$('#closeC' + index).css('background',
				'url(../images/dropLeftWhite.png) no-repeat 100% -38px');
		$('#details' + index).css('background',
				'url(../images/dropLeftWhite.png) no-repeat 100% 7px');
		$('#ratingC' + index + ' i').css('color', '#fff');
		$('#companyName' + index).css('color', '#fff');
	} else {
		$('#clientHead' + index).css('background', '#f1f2f2');
		$('#searchedFixerNAme' + index).css('color', '#000000');
		$('#favtext' + index).css('color', '');
		$('#closeC' + index).css('color', '');
		$('#details' + index).css('color', '');
		$('#closeC' + index).css('background',
				'url(../images/dropLeft.png) no-repeat 100% 7px');
		$('#details' + index).css('background',
				'url(../images/dropLeft	.png) no-repeat 100% 7px');
		$('#ratingC' + index + ' i').css('color', '#63429a');
		$('#companyName' + index).css('color', '');
	}

}
