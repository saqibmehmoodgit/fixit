<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>ERPfixers | New Request</title>
<link rel="stylesheet" type="text/css"
	href="../css/jquery.datetimepicker.css"/ >
<script src="../js/jquery.datetimepicker.full.min.js"></script>
<script type="text/javascript" src="../js/pages/category.js"></script>
<script type="text/javascript" src="../js/pages/script.js"></script>
<script>
var catJson = ${allCategoryJson};
var allCategory = ${allCategoryJson};
var parentCatJson = ${parentCategoryJson};
var id=${myUser.userId};
var userId = id;
var myUserJson=${myUserJson};
var credits = ${points};
var userSource = ${myUser.source};
var trackCredit = '${myUser.trackCredit}';
var modeType = ${modeType};
 </script>
<script type="text/javascript" src="../js/pages/request.js"></script>

<script>
var deadlineDate ;
var deadlineTime ;


var catIdsArr =  [];

$(document).ready(function(){
	
if (userSource == 1 && trackCredit == 'N'){
		
		$('#asugCredits').css('display', 'block');
	}
	else{
		$('#asugCredits').css('display', 'none');
	}
	
	 $('#reassignedForm').find('input').keypress(function(e){
			  
		    if ( e.which == 13 ) // Enter key = keycode 13
		    {
		        $(this).next().focus();  //Use whatever selector necessary to focus the 'next' input
		        return false;
		    }
		}); 
	
	
	$('a').removeClass('active');
	$('#reAssinged').addClass('active').css('display', 'block');

	$('.catIds').each(function () {
		
		catIdsArr.push(this.id);
	});
	var len =  catIdsArr.length;
	var id = '';
	for(var i = 0; i< len ; i++){
		id  = catIdsArr[i].split('catNameId')[1];
	    $('#catId' + id).attr('checked', true);	
	}
	
	
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
		  minTime:0
		});
		
	
	$('#datetimepicker1').bind('change',function(event){
		event.stopPropagation();
		console.log('hello')
		changeTimeProperty();
		});
	
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
		$('.catIds').each(function () {
			
			catIdsArr.push(this.id);
		});
		var len =  catIdsArr.length;
		var id = '';
		for(var i = 0; i< len ; i++){
			id  = catIdsArr[i].split('catNameId')[1];
		    $('#catId' + id).attr('checked', true);	
		}
		
		setCurrentDate('currentDate');
	
});
function setCurrentDate(date){
    var addHrs = 0;
	var currentDate = new Date();
	
	if(date == 'currentDate'){
    
		var credits = ($('#hours').val()) * 12;
	 	addHrs = currentDate.getHours() + credits;
		currentDate.setHours(addHrs);

	}
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
	//var setTime = hour + ':' + min
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
	if(date == 'currentDate'){
	 $('#datetimepicker1').val(setDate);
     $('#datetimepicker2').val(setTime);
	}
     return setDate;
}

function addhoursDateTime(value){
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
	var setTime = hour + ':' + min;
	
	var currentDate = setCurrentDate('');
	/* var splitWithSlash = currentDate.split('/'); */
	 var bool = validateDate(currentDate, setDate);
	
	 if(bool){
	 
		 $('#datetimepicker1').val(setDate);
	     $('#datetimepicker2').val(setTime);
	     $('#queryDeadlineDate').val(dateTimeString);
	} 
	 else{
	     $('#queryDeadlineDate').val(currentDate + ' ' +time+ ':'+ '00');
 
		 
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


function addhoursOnchange(value){
	var add12Hrs;
	
	
	var datetime = new Date();
	
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
	var dateTimeString = day+'/'+month+'/'+year+' '+hour+':'+min+':'+sec;
	var setDate = day+'/'+month+'/'+year;
	var setTime = hour + ':' + min;
	
	var currentDate = setCurrentDate('');
	var splitWithSlash = currentDate.split('/');
	 var bool = validateDate(currentDate, setDate);
	 if(bool){
	 
		 $('#datetimepicker1').val(setDate);
	     $('#datetimepicker2').val(setTime);
	     $('#queryDeadlineDate').val(dateTimeString);
	} 
	 else{
	     $('#queryDeadlineDate').val(currentDate + ' ' +time+ ':'+ '00');
 
		 
	 }
	 
	
	
}



function cancelBtnClick(){
	
	window.location.replace("${pageContext.request.contextPath}/member/request?msgKey=R&status=InProgress");

	
}

function validateRequest(points){
	
	var error = false;
	if( $("#querytitle").val()=='')
		{
		 $("#querytitleAlert").css({"display":"block"});
		 error = true;
		
		}
	 if($("#queryDesc").val()=='')
		{
		 $("#queryDescAlert").css({"display":"block"});
		 error = true;
		}
	  if(getCheckedBoxes('categories')==null){
		  $("#categoriesAlert").css({"display":"block"});
		  error = true;
	  } 
	  if($("#hours").val()<1)
		  {
		  $("#hoursAlert").css({"display":"block"});
		  $("#hoursAlert").text('Estimated hours must be greater than 0');
		  error = true;
		  }
	  if($("#hours").val()>points)
		  {
		  $("#hoursAlert").css({"display":"block"});
		  $("#hoursAlert").text('Estimated hours must be less then avaialble points');
		  error = true;
		  }
	  
	  if(!$('#leaverequest').is(':checked') && (selectedFixers ==null || selectedFixers.length == 0) )
		  {
		  $("#leaverequestAlert").css({"display":"block"});
		  error = true;
		  }
	  if(error == false)
	  {
		  addhoursDateTime(0);
	  $("#reassignedForm").submit();
	  }
  else
	  window.scrollTo(0, 0);
	
	
}


</script>

</head>
<body>
	<div id="pagewrap">
		<!-- Work Here start-->
		<section class="wrapper">
			<div class="container-fluid">
				<form:form role="form" class="reassignedForm" id="reassignedForm"
					method="post"
					action="${pageContext.request.contextPath}/member/reassignedAskQuestion"
					modelAttribute="query" commandName="query"
					enctype="multipart/form-data">
					<div class="requestsBox">
						<div class="row">
							<div class="col-lg-8 col-md-8 col-sm-8 col-xs-12">
								<div id="titleDescDiv" class="newRequest">
									<div class="row">
										<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
											<div class="newRequestWhite">
												<h1 class="h1-underline">1. Title & Description</h1>

												<div class="descForm">
													<label>Request Title</label> <span class="alertF"
														id="querytitleAlert" style="display: none">Please
														enter title of query.</span>
													<form:textarea class="formControl" type="text"
														id="querytitle" path="queryTitle"
														value="${query.queryTitle}"></form:textarea>
												</div>

												<div class="descForm">
													<label>Description</label> <span class="alertF"
														id="queryDescAlert" style="display: none">Please
														enter description of query.</span>
													<form:textarea type="text" path="queryContent"
														value="${query.queryContent}" id="queryDesc"
														class="formControl"></form:textarea>
												</div>
											</div>
										</div>
										<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
											<div class="newRequestRight">
												<p>
													<strong>Its best to put something specific and
														descriptive so the best Fixers find your Request.</strong>
												</p>
											</div>
										</div>
									</div>
								</div>


								<div id="categDiv" class="newRequest">
									<div class="row">
										<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">

											<div class="newRequestWhite">

												<h1 class="h1-underline">2. Choose Categories</h1>

												<div class="descForm choosC">
													<span class="alertF" id="categoriesAlert"
														style="display: none">Please select atleast one
														category.</span> <label>Request Categories: <span>Select
															one or more.</span></label>
													<div id="the-basics">
														<input class="typeahead formControl" type="text"
															placeholder="Search" id="smart_search"> <input
															type="hidden" value="${rejectedfixerId}"
															name="rejectedfixerId" id="rejectedFixerId"> <input
															type="hidden" value="${queryHashCode}"
															name="queryHascode" id="queryHashCode">
													</div>
													<input type="hidden" name="queryDeadlineDate"
														id="queryDeadlineDate">
													<!-- <input type="text" value="" placeholder="Search" class="formControl"> -->
														<span class="selectCate" data-toggle="modal" data-target="#myModal"
														data-backdrop="static" data-keyboard="false">Categories</span>

												</div>


												<div class="categoriesDiv">
													<div id="categoryNameId">

														<c:forEach var="categories" items="${queryCategories}">

															<span class="catIds" id="catNameId${categories.catId}">${categories.catgName}<i
																class="icon" data-icon="y"
																onclick="onDelete(${categories.catId})"></i></span>


														</c:forEach>

													</div>
												</div>

												<div class="clearfix"></div>
											</div>
										</div>
										<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
											<div class="newRequestRight">
												<p>
													<strong>Search or select the most precise category
														related to your request, so that it is atched with the
														best fixer.</strong>
												</p>
											</div>
										</div>
									</div>
								</div>
								<div id="attacDiv" class="newRequest">
									<div class="row">
										<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
											<div class="newRequestWhite">
												<h1 class="h1-underline">3. Attachments</h1>
												<div id="attach">

													<c:forEach var="documents" items="${documents}">

														<div class="download" id="${documents.fileUniqueCode}">
															<span>Download:</span> <input type="text"
																name="documents" value="${documents.fileName}">
															<i data-icon="y" class="icon"  onclick = "deleteFileReassign('${documents.fileName}','${documents.fileUniqueCode}','${queryId}')"> </i>
														</div>
													</c:forEach>

												</div>
												<div class="browsfile">
													<input id="documentUpload" placeholder="" type="file"
														onchange="uploadDocument()"> Upload File (5MB limit)<span
														class="uploadLoader" style="display: none"
														id="uploadLoader"><img
														src="${pageContext.request.contextPath}/images/loader.gif"
														alt=""></span>
												</div>
												<small>File upload allow only ".jpg .png and .pdf".</small>
											</div>
										</div>
										<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
											<div class="newRequestRight">
												<p>
													<strong>Upload a file to share with the Fixer.</strong>
												</p>
											</div>
										</div>
									</div>
								</div>

								<div id="proposedDiv" class="newRequest">
									<div class="row">
										<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
											<div class="newRequestWhite">
												<h1 class="h1-underline">4. Proposed Hours</h1>
												<div class="estimateH" style="min-height: 78px;">
													<div class="addH" style="margin: 0px;">
														<span class="alertF" id="hoursAlert" style="display: none">Please
															enter title of query.</span> <img
															src="${pageContext.request.contextPath}/images/moreA.png"
															alt="" class="leftimg" id="leftimg"
															onclick="leftCredirBtnPressed(${points})"> <input
															value="${query.queryCredits}" type="number"
															onchange="addhoursOnchange(this.value)"
															name="queryCredits" id="hours"> <img
															src="${pageContext.request.contextPath}/images/moreA.png"
															alt="" id="rightimg"
															onclick="rightCredirBtnPressed(${points},1)">
															<input
															type="hidden" name="oldCredits"
															value="${query.queryCredits}"
															id="oldCredits">
													</div>
													<div class="remainging">
														<strong>${points} Credit Remaining</strong> <a
															id="add_more" href="javascript:void(0)">+ Add More
															Credits</a>
													</div>
												</div>
												<br> <strong>Due Date</strong>
												<div class="descForm">
													<label>Desired due date and time:</label> <input
														type="text" id="datetimepicker1" class="formControl"
														style="width: 60%;"><input type="text"
														id="datetimepicker2" class="formControl"
														style="width: 40%; border-left: 0;"> <span
														class="alertF" id="dateTimeAlert" style="display: none">Please
														Select date</span>
												</div>
											</div>
										</div>
										<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
											<div class="newRequestRight">
												<p>
													<strong>Is this a quick task? or will it take
														multiple hours? Give us your best guess here and then you
														and your Fixer can set a final number later.</strong>
												</p>
											</div>
										</div>
									</div>
								</div>
								<div id="findFixerDiv" class="newRequest">
									<div class="row">
										<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
											<div class="newRequestWhite">
												<h1 class="h1-underline">5. Find a Fixer</h1>
												<div class="estimateH">

													<span class="alertF" id="leaverequestAlert"
														style="display: none">Please Select atleast one
														fixer for the request.</span>
													<div class="radio" id="LeaveRequestFixer"
														onclick="openToAllFixerClicked()">
														<input id="leaverequest" name="fixer" type="radio" checked>
														<label id="openRequest" for="leaverequest">Send to all Fixers within selected Module(s)</label>
													</div>
													<div class="radio" id="SendSpecificFixer"
														onchange="sendSpecificFixerClickReassignRequest('',${rejectedfixerId})">
														<input id="SendSpecific" name="fixer" type="radio">
														<label for="SendSpecific">Send to specific Fixer(s)</label>
													</div>

												</div>

												<input placeholder="" name="fixerId" value="${query.fixerId}"
													id="fixerId" type="hidden"> <input placeholder=""
													name="fixersIds" value="0" id="fixersIds" type="hidden">
												<input type="hidden" name="queryMode" value="CREATE"
													id="queryMode">
												<div class="filterDiv" id="filterDivsearch">
													<div class="filterA">
														<div class="folterD" id="filter" onclick="onFilterClick()">
															Filter <i data-icon="j" class="icon"></i>
														</div>
														<div class="folterD" id="ClearFilter"
															onclick="onClearFilterClick()">
															Clear Filter <i data-icon="j" class="icon"></i>
														</div>
														<div class="descForm" id="ParentCategory">
															<label>Parent Category</label> <select
																id="parentCategory"
																onchange="findCatByparentCatToShowOnList()">

																<option value="-1">Filter by Parent Category</option>

																<c:forEach var="parentCategory"
																	items="${parentCategory}" varStatus="theparentCategory">
																	<option value="${parentCategory.catId}">${parentCategory.catgName}</option>
																</c:forEach>

															</select>
														</div>

														<div class="descForm descFormChild"
															id="childCatDropDownList"></div>

														<div class="descForm" id="SearchIndustry">
															<label>Industry</label> <select>
																<option>Any Industry</option>
																<option>option</option>
																<option>option</option>
															</select>
														</div>

														<div class="descForm" id="SearchRating">
															<label>Rating</label> <select id="selectedRating"
																onchange="ratingChangeListener()">
																<option value="">Any Rating</option>
																<option value="1">1+</option>
																<option value="2">2+</option>
																<option value="3">3+</option>
																<option value="4">4+</option>
																<option value="5">5+</option>
															</select>
														</div>

														<div class="descForm" id="SearchCuntry">
															<label>Country</label> <select id="selectedCountry"
																onchange="ratingChangeListener()">
																<option value="">Any Country</option>
																<c:forEach var="countryList" items="${countryList}"
																	varStatus="thecountryList">
																	<option value="${countryList.countryName}">${countryList.countryName}</option>
																</c:forEach>
															</select>
														</div>


														<div class="descForm">
															<label>Search Name</label> <input type="search"
																id="searchId" value="" placeholder="Search"
																class="formControl"
																onkeyup="searchNameChangedReassignRequest(this.value,${rejectedfixerId})">
														</div>
													</div>
													<!-- // 1 // -->
													<div id="interestedFixers_searched"></div>
													<div class="requestloadmore" onclick="loadMore()">Load
														More</div>
												</div>
												<div class="findFixBtn">
													<div class="row">
														<div
															class="col-lg-6 col-md-6 col-sm-6 col-xs-12 nopadding">
															<button type="button" class="clear"
																onclick="cancelBtnClick()">Cancel</button>
														</div>
														<div
															class="col-lg-6 col-md-6 col-sm-6 col-xs-12 nopadding">
															<button type="button" id="submitBtnImpl"
																onclick="validateRequest(${points})" class="SRequest">Submit
																Request</button>
														</div>
													</div>
												</div>

											</div>
										</div>
										<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
											<div class="newRequestRight">
												<p>
													<strong>Leave your Request open to our pool of
														expert Fixers. Or, send your Request straight to the Fixer
														of your choice.</strong>
												</p>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- poppup -->
							<div class="modal fade" id="myModal" role="dialog">
								<div class="modal-dialog modal-lg">
									<div class="modal-content">
										<div class="modal-body">
											<h1 class="h1-underline">Choose Categories</h1>
											<ul class="ChooseCategories">

												<c:forEach var="parentCat" items="${parentCategory}">
													<li><div class="categoriesT"
															id="categoriesT${parentCat.catId}">${parentCat.catgName}</div>
														<div class="categoriesList" style="display:none"
															id="categoriesL${parentCat.catId}">
															<ul class="MyIndus">
																<c:forEach var="childCat" items="${allCategory}">
																	<c:if test="${childCat.parentId==parentCat.catId}">
																		<li><input name="categories"
																			id="catId${childCat.catId}" value="${childCat.catId}"
																			onclick="onChildCatClick(${childCat.catId})"
																			type="checkbox"> <label for="categories">${childCat.catgName}</label>
																		</li>
																	</c:if>
																</c:forEach>

															</ul>
														</div></li>
												</c:forEach>
											</ul>
										</div>
										<div class="modal-footer">

											<button class="saveChange width100B" onclick="onSave()"
												data-dismiss="modal">Save </button>
											<button onclick="onCancel()" class="cancel width100B"
												data-dismiss="modal">Cancel</button>
										</div>
									</div>
								</div>
							</div>
							<!-- poppup -->
						</div>
					</div>
				</form:form>
			</div>
		</section>
	</div>
	<!--   New  -->

	<div class="custm_popup_resolve">
		<div class="resolvedRequests wow fadeIn" data-wow-delay="0.5s">
			<span class="cclose"><i data-icon="y" class="icon"></i></span>
			<div class="HourPacks" style="display: block;">
				<div class="hourClose"><span id="icon"><i data-icon="y" class="icon"></i></span></div>
				<h1>Hour Packs</h1>
				<p>Purchase larger bundles and save.</p>
				<p id="asugCredits" style="display:none">You get 1 free credit as an ASUG member or affiliate</p>
				
				<div class="hourpackDiv">
					<div id="purple" class="hp-cell">
						<div class="purple">
							<span class="grayN">5</span> <span class="price"> <a
								id="money_575" href="javascript:void(0)"> $575 </a>
							</span>
						</div>
					</div>
					<div  id="purpleDark" class="hp-cell">
						<div class="purpleDark">
							<span class="whiteN">10</span> <span class="price"> <a
								id="money_1125" href="javascript:void(0)">$1,125</a>
							</span>
						</div>
					</div>
					<div  id="green" class="hp-cell">
						<div class="green">
							<span class="grayN">20</span> <span class="price"> <a
								id="money_2200" href="javascript:void(0)">$2,200</a>
							</span>
						</div>
					</div>
					<div  id="blue" class="hp-cell">
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
						Purchase <i data-icon="K" class="icon"></i><strong>$<span id="money">0</span></strong>
					</button>
				</div>
			</div>

		</div>
	</div>
</body>
</html>