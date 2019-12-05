<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<section class="member">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h1 class="animated fadeInDown">Chat Message</h1>
				<div class="col-md-11"
					style="text-align: right; margin-bottom: 10px;">
					<button type="submit" id="mailAllMembersButton"
						class="btn btn-primary" onclick="mailToMembers('C')">Mail
						to all Members</button>
					<button type="submit" id="mailAllFixersButton"
						class="btn btn-success" onclick="mailToMembers('F')">Mail
						to all Fixers</button>
				</div>
			</div>
			<br>

			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">

					<ul class="nav nav-tabs width50">

						<c:choose>
							<c:when test="${UserType=='users'}">
								<li class="active"><a
									href="${pageContext.request.contextPath}/admin/chat?status=Users">Users</a></li>
								<li><a
									href="${pageContext.request.contextPath}/admin/chat?status=Chat">Chat
										History</a></li>
							</c:when>
							<c:otherwise>
								<li><a
									href="${pageContext.request.contextPath}/admin/chat?status=Users">Users</a></li>
								<li class="active"><a
									href="${pageContext.request.contextPath}/admin/chat?status=Chat">Chat
										History</a></li>
							</c:otherwise>
						</c:choose>



					</ul>
					<div class="adminFixersList">
						<div id="FixerApproval" class="tab-pane fade in active">

							<div class="row">
								<c:if test="${UserType=='users'}">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<div class="searchPay">
											<input type="search" id="searchTextField">
											<button type="submit" id="searchButton"
												class="btn btn-danger" onclick="searchButtonTapped()">Search</button>
										</div>
									</div>
								</c:if>
							</div>

							<%-- 	<div class="row">
						  <c:if test="${totalUsersCount>=1}">
						<div class="col-md-12 col-sm-12 col-xs-12">
							 <div class="searchPay">
								<input type="search" id="searchTextField">	
								<button type="submit" id="searchButton" class="btn btn-danger" onclick="searchButtonTapped()">Search</button>
							</div> 
						</div>
						</c:if>
					</div> --%>
							<div class="overflowX">

								<%-- 	  	  <c:choose>
				  <c:when test="${totalUsersCount>=1}"> --%>

								<div class="FixerApproval" id="searchFixerList">

									<div class="dtablrow">


										<c:choose>
											<c:when test="${UserType=='users'}">
												<div class="dtablecel thead custm_dtablecel">Users</div>
												<!--  <div class="dtablecel thead">UserName</div>
                                                        <div class="dtablecel thead">Email</div>
                                                         <div class="dtablecel thead">Credits</div> 
                                                        <div class="dtablecel thead">Action</div>  -->
											</c:when>
											<c:otherwise>
												<div class="dtablecel thead custm_dtablecel">Recent
													Chat</div>

											</c:otherwise>
										</c:choose>


										<!-- <div class="dtablecel thead">Linkedin Profile</div>  -->

									</div>

									<c:forEach var="item" items="${allUsers}"
										varStatus="commentLoop">


										<div class="dtablrow">

											<c:choose>
												<c:when test="${UserType=='users'}">
													<div class="dtablecel custm_dtablecel">
														<a
															href="${pageContext.request.contextPath}/admin/adminChatDetail?userId=${item.userId}">${item.userName}</a>
													</div>
												</c:when>
												<c:otherwise>
													<div class="dtablecel custm_dtablecel">
														<a
															href="${pageContext.request.contextPath}/admin/adminGroupDetail?groupId=${item.userId}">${item.userName}</a>
													</div>
												</c:otherwise>
											</c:choose>

										</div>
									</c:forEach>

								</div>

								<%-- <div class="row">
							<div class="col-md-12">
								<ul class="pagination pull-right"  id = "fixerListPagination" >
								     <c:choose>
                                                            <c:when test="${totalPage==0}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${currentPageNo=='1'}">
                                                                        <li><a class="disable"><i class="fa fa-caret-left"></i></a>
                                                                        </li>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    
													<c:choose>
                                                        <c:when test="${UserType=='C'}">
                                                      <li><a href="../admin/users?pageNo=${currentPageNo}&flag=left" class="nexpre"><i class="fa fa-caret-left"></i></a>
                                                                        </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                     <li><a href="../admin/users?pageNo=${currentPageNo}&flag=left&status=Fixers" class="nexpre"><i class="fa fa-caret-left"></i></a>
                                                                        </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                                       
                                                                    </c:otherwise>

                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>




                                                        <c:forEach begin="${startPage}" end="${endPage}" varStatus="p">
                                                            <c:choose>
                                                                <c:when test="${p.index==currentPageNo}">
                                                                    <li><a class="active">${p.index}</a>
                                                                    </li>
                                                                </c:when>
                                                                <c:otherwise>
                                                                
													<c:choose>
                                                        <c:when test="${UserType=='C'}">
                                                     <li><a href="../admin/users?pageNo=${p.index}&flag=current">${p.index}</a>
                                                                    </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                      <li><a href="../admin/users?pageNo=${p.index}&flag=current&status=Fixers">${p.index}</a>
                                                                    </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                                
                                                                    
                                                                </c:otherwise>
                                                            </c:choose>


                                                        </c:forEach>
                                                        <c:choose>
                                                            <c:when test="${totalPage==0}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <li>...</li>
                                                                <li><a>${totalPage}</a>
                                                                </li>
                                                            </c:otherwise>
                                                        </c:choose>



                                                        <c:choose>
                                                            <c:when test="${totalPage==0}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${currentPageNo==totalPage}">
                                                                        <li><a class="disable"><i class="fa fa-caret-right"></i></a>
                                                                        </li>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                    
                                                                    
													<c:choose>
                                                        <c:when test="${UserType=='C'}">
                                                      <li><a href="../admin/users?pageNo=${currentPageNo}&flag=right" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                                                        </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                     <li><a href="../admin/users?pageNo=${currentPageNo}&flag=right&status=Fixers" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                                                        </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                                    
                                                                       
                                                                    </c:otherwise>

                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>
								
								</ul>
							</div>
						</div> --%>



								<%--   </c:when>
                                        <c:otherwise>
                                            <div class="animated fadeInDown nodata">No Data Found.</div>
                                        </c:otherwise>
                                        </c:choose></div>	 --%>







							</div>
						</div>
					</div>
				</div>





				<!--   model end -->
	</section>
	<script>
	 function searchButtonTapped(){
		 var userType='${UserType}';
	    var name = document.getElementById("searchTextField");
			var fixerName=name.value;
			$.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/admin/searchChatUsers",
			    data : {
			    searchText: fixerName
			     }
			   }).done(function(response) {
				   var data = response.result.chatUsers;
				   console.log(data.length);
				  
				    $("#searchFixerList").empty();
				   /*  <div class="dtablrow" >
                    
                    <c:choose>
                             <c:when test="${UserType=='users'}">
                           <div class="dtablecel custm_dtablecel" ><a href="${pageContext.request.contextPath}/admin/adminChatDetail?userId=${item.userId}">${item.userName}</a></div>
                             </c:when>
                             <c:otherwise>
                            <div class="dtablecel custm_dtablecel" ><a href="${pageContext.request.contextPath}/admin/adminGroupDetail?groupId=${item.userId}">${item.userName}</a></div>
					    </c:otherwise>
                         </c:choose>
                             
             </div> */
	                  
				    var html ='';
             html+= '<div class="dtablrow">';
             html+='<div class="dtablecel thead custm_dtablecel">Users</div></div>'; 
				    for(var i =0 ;i<data.length;i++){
				    	html+='<div class="dtablrow">';
				    	
				    	html+=' <div class="dtablecel custm_dtablecel" ><a href="${pageContext.request.contextPath}/admin/adminChatDetail?userId='+data[i].userId+'">'+data[i].userName+'</a></div></div>';
				    			    	
				    	
				    }
				    console.log(html);
				    	$("#searchFixerList").append(html);
				    	
				    
			      	
				    
			   });
	    }
	 
	 function mailToMembers(userType){
		
		 	if(userType == 'F')
				document.location.href = "${pageContext.request.contextPath}/admin/adminMailFixers"
			else
				document.location.href = "${pageContext.request.contextPath}/admin/adminMailMembers"	
	    }
	</script>


</body>
</html>