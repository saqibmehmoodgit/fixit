<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
	<script>
		
	</script>
	<a name="home" id="home"></a>

	<!-- price -->

	<section class="member">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="animated fadeInDown">Asug User List</h1>

				</div>
			</div>
			<br>

			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
				<ul class="nav nav-tabs width50">  
                    <li id="showAsugList_id" class="active" onclick="showAsugList()"><a >Asug User List</a></li>
	                <li id="showAsugReport_id"  onclick="showAsugReport()"><a>Asug Report</a></li>
                </ul>
				
					<div class="adminFixersList">
						<div id="FixerApproval" class="tab-pane fade in active">
							<div class="row">

								<div class="col-md-12 col-sm-12 col-xs-12">
									<div class="searchPay">
										<input type="search" id="searchTextField">
										<button type="submit" id="searchButton" class="btn btn-danger"
											onclick="searchButtonTapped()">Search</button>
									</div>
								</div>

							</div>
							<div class="overflowX">
								<c:choose>
									<c:when test="${userBySourceCount>=1}">

										<div class="FixerApproval" id="searchAsugUserList">
											<div class="dtablrow">

												<div class="dtablecel thead">S. No.</div>
												<div class="dtablecel thead">UserName</div>
												<div class="dtablecel thead">Email</div>
												<div class="dtablecel thead">Credits Used</div>


											</div>


											<c:forEach var="userBySource" items="${userBySource}"
												varStatus="commentLoop">


												<div class="dtablrow">
													<div class="dtablecel">${commentLoop.index+1}</div>
													<div class="dtablecel">
														<a
															href="${pageContext.request.contextPath}/admin/userMemberProfile?userId=${userBySource.userId}">${userBySource.userName}</a>
													</div>
													<div class="dtablecel">
														<a
															href="${pageContext.request.contextPath}/admin/userMemberProfile?userId=${userBySource.userId}">${userBySource.email}</a>
													</div>
													<c:choose>
														<c:when test="${userBySource.trackCredit eq 'Y'}">
															<div class="dtablecel">
																<a href="javascript:void(0)">Yes</a>
															</div>
														</c:when>
														<c:otherwise>
															<div class="dtablecel">
																<a href="javascript:void(0)">No</a>
															</div>

														</c:otherwise>
													</c:choose>
												</div>
											</c:forEach>

										</div>

										<div class="row">
											<div class="col-md-12">
												<ul class="pagination pull-right" id="memberListPagination">
													<c:choose>
														<c:when test="${totalPage==0}">
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${currentPageNo=='1'}">
																	<li><a class="disable"><i
																			class="fa fa-caret-left"></i></a></li>
																</c:when>
																<c:otherwise>
																	<li><a
																		href="../admin/asugUser?pageNo=${currentPageNo}&flag=left"
																		class="nexpre"><i class="fa fa-caret-left"></i></a></li>
																</c:otherwise>

															</c:choose>
														</c:otherwise>
													</c:choose>




													<c:forEach begin="${startPage}" end="${endPage}"
														varStatus="p">
														<c:choose>
															<c:when test="${p.index==currentPageNo}">
																<li><a class="active">${p.index}</a></li>
															</c:when>
															<c:otherwise>
																<li><a
																	href="../admin/asugUser?pageNo=${p.index}&flag=current">${p.index}</a>
																</li>
															</c:otherwise>
														</c:choose>


													</c:forEach>
													<c:choose>
														<c:when test="${totalPage==0}">
														</c:when>
														<c:otherwise>
															<li>...</li>
															<li><a>${totalPage}</a></li>
														</c:otherwise>
													</c:choose>



													<c:choose>
														<c:when test="${totalPage==0}">
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${currentPageNo==totalPage}">
																	<li><a class="disable"><i
																			class="fa fa-caret-right"></i></a></li>
																</c:when>
																<c:otherwise>
																	<li><a
																		href="../admin/asugUser?pageNo=${currentPageNo}&flag=right"
																		class="nexpre"><i class="fa fa-caret-right"></i></a></li>
																</c:otherwise>

															</c:choose>
														</c:otherwise>
													</c:choose>

												</ul>
											</div>
										</div>

									</c:when>
									<c:otherwise>
										<div class="animated fadeInDown nodata">No Data Found.</div>
									</c:otherwise>
								</c:choose>

							</div>
						</div>
						
						<div  id="asugReport_id" class="asugReport">
						<div class="Ttable">
							<div class="Ttable-row">
								<div class="Ttable-cell">
									<select id="month" class="selectpicker" data-style="btn-primary">
									  <option value="1">JAN</option>
									  <option value="2">FEB</option>
									  <option value="3">MAR</option>
									  <option value="4">APR</option>
									  <option value="5">MAY</option>
									  <option value="6">JUN</option>
									  <option value="7">JUL</option>
									  <option value="8">AUG</option>
									  <option value="9">SEP</option>
									  <option value="10">OCT</option>
									  <option value="11">NOV</option>
									  <option value="12">DEC</option>
									</select>
								</div>
								<div class="Ttable-cell">
									<select id="year" class="selectpicker">
									  <option>2016</option>
									  <option>2017</option>
									  <option>2018</option>
									</select>
								</div>
								<div class="Ttable-cell">
									<button class="btn btn-primary"><span  aria-hidden="true"></span><a id="exportExcel" href="#" onclick="exportExcel()">Export</a></button>
								</div>
							</div>
						</div>
							
							
						<div id="noData" class="nodata">No data found</div>
							
							
							
						</div>
						
					</div>
				</div>
				<div class="col-md-1 col-sm-1"></div>
			</div>


		</div>
	</section>
	<!-- price close -->


	<script>
		function searchButtonTapped() {
			var searchText = $('#searchTextField').val();

			$
					.ajax(
							{
								method : "POST",
								url : "${pageContext.request.contextPath}/admin/searchAsugUser",
								data : {
									searchText : searchText
								}
							})
					.done(
							function(response) {
								
								var data = response.result.users;
								console.log(data);
								var totalPage = response.result.totalPage;
								var startPage = response.result.startPage;
								var endPage = response.result.endPage;
								var currentPageNo = response.result.currentPageNo;
								var pagHtml = '';
								$("#searchAsugUserList").empty();
								$("#memberListPagination").empty();
								if (totalPage == 0) {

								} else {
									if (currentPageNo == '1') {
										pagHtml += '<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
									} else {
										pagHtml = '<li onclick="pagClick(\''
												+ currentPageNo
												+ '\',\''
												+ 'left'
												+ '\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
									}
								}

								for (var k = startPage; k <= endPage; k++) {
									if (k == currentPageNo) {
										pagHtml += '<li ><a  class="active">'
												+ k + '</a></li>';
									} else {
										pagHtml += '<li  onclick="pagClick(\''
												+ k + '\',\'' + 'current'
												+ '\')" ><a   >' + k
												+ '</a></li>';
									}

								}
								if (totalPage == 0) {

								} else {
									if (currentPageNo == totalPage) {
										pagHtml += '<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
									} else {
										pagHtml += '<li onclick="pagClick(\''
												+ currentPageNo
												+ '\',\''
												+ 'right'
												+ '\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
									}
								}

								$("#memberListPagination").append(pagHtml);

								var html = '';
								if (data.length > 0) {
									html += '<div class="dtablrow"> <div class="dtablecel thead">S. No.</div> <div class="dtablecel thead">UserName</div><div class="dtablecel thead">Email</div><div class="dtablecel thead">Credits Used</div> </div>';
								}
								for (var i = 0; i < data.length; i++) {
									var j = i + 1;
									html += '<div class="dtablrow"><div class="dtablecel">'
											+ j
											+ '</div>'
											+ '<div class="dtablecel"><a href="../admin/userMemberProfile?userId='
											+ data[i].userId
											+ '">'
											+ data[i].userName
											+ '</a></div>'
											+ '<div class="dtablecel"><a href="../admin/userMemberProfile?userId='
											+ data[i].userId
											+ '">'
											+ data[i].email + '</a></div>';
									if (data[i].trackCredit == 'Y') {

										html += '<div class="dtablecel"><a href="javascript:void(0)">Yes</a></div>';
									} else {
										html += '<div class="dtablecel"><a href="javascript:void(0)">No</a></div></div>';
									}
								}

								$("#searchAsugUserList").append(html);

							});
		}

		function pagClick(pageNo, flag) {
			var name = document.getElementById("searchTextField");
			var fixerName = name.value;
			$
					.ajax(
							{
								method : "POST",
								url : "${pageContext.request.contextPath}/admin/searchAsugUser",
								data : {
									searchText : fixerName,
									pageNo : pageNo,
									flag : flag
								}
							})
					.done(
							function(response) {
								var data = response.result.users;
								var totalPage = response.result.totalPage;
								var startPage = response.result.startPage;
								var endPage = response.result.endPage;
								var currentPageNo = response.result.currentPageNo;
								var pagHtml = '';
								$("#searchAsugUserList").empty();
								$("#memberListPagination").empty();
								if (totalPage == 0) {

								} else {
									if (currentPageNo == '1') {
										pagHtml += '<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
									} else {
										pagHtml = '<li onclick="pagClick(\''
												+ currentPageNo
												+ '\',\''
												+ 'left'
												+ '\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
									}
								}

								for (var k = startPage; k <= endPage; k++) {
									if (k == currentPageNo) {
										pagHtml += '<li ><a  class="active">'
												+ k + '</a></li>';
									} else {
										pagHtml += '<li  onclick="pagClick(\''
												+ k + '\',\'' + 'current'
												+ '\')" ><a   >' + k
												+ '</a></li>';
									}

								}
								if (totalPage == 0) {

								} else {
									if (currentPageNo == totalPage) {
										pagHtml += '<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
									} else {
										pagHtml += '<li onclick="pagClick(\''
												+ currentPageNo
												+ '\',\''
												+ 'right'
												+ '\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
									}
								}

								$("#memberListPagination").append(pagHtml);

								var html = '';
								if (data.length > 0) {
									html += '<div class="dtablrow"> <div class="dtablecel thead">S. No.</div> <div class="dtablecel thead">UserName</div><div class="dtablecel thead">Email</div><div class="dtablecel thead">Credits Used</div> </div>';
								}
								for (var i = 0; i < data.length; i++) {
									var j = i + 1;
									html += '<div class="dtablrow"><div class="dtablecel">'
											+ j
											+ '</div>'
											+ '<div class="dtablecel"><a href="../admin/userMemberProfile?userId='
											+ data[i].userId
											+ '">'
											+ data[i].userName
											+ '</a></div>'
											+ '<div class="dtablecel"><a href="../admin/userMemberProfile?userId='
											+ data[i].userId
											+ '">'
											+ data[i].email + '</a></div>';
									if (data[i].trackCredit == 'Y') {

										html += '<div class="dtablecel"><a href="javascript:void(0)">Yes</a></div>';
									} else {
										html += '<div class="dtablecel"><a href="javascript:void(0)">No</a></div></div>';
									}
								}
								console.log(html);
								$("#searchAsugUserList").append(html);

							});
		}
		
		function showAsugList(){
			$("#FixerApproval").show();
			$("#asugReport_id").hide();
			$("#showAsugList_id").addClass('active');
			$("#showAsugReport_id").removeClass('active');
		}
		
		function showAsugReport(){
			$("#FixerApproval").hide();
			$("#asugReport_id").show();
			$("#showAsugReport_id").addClass('active');
			$("#showAsugList_id").removeClass('active');
			
		}
		
		$(document).ready(function() {
			$("#FixerApproval").show();
			$("#asugReport_id").hide();
			var excelDataList = '${excelDataList}'
			if(excelDataList == '0'){
		      $('#noData').css('display','block');	
				showAsugReport();
				
			}else
				{
			      $('#noData').css('display','none');	

				}
		});
		
		
		function exportExcel(){
		      $('#noData').css('display','none');	

			var month = $('#month').val();
			var year =  $('#year').val();
			var url = '../admin/exportExcel?month=' + month + '&year=' + year;
			$('#exportExcel').attr('href',url);
		}
		
	</script>
</body>
