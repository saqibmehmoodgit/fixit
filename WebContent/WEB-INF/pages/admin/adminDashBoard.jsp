<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

    <!DOCTYPE html>
    <html lang="en">

    <head>
    
    <%
    response.setHeader("Cache-Control","no-cache"); 
    response.setHeader("Pragma","no-cache"); 
    response.setDateHeader ("Expires", -1);
    response.setContentLength(2000000);
    %>
    
        <script type="text/javascript" src="../js/jstz-1.0.4.min.js"></script>
    </head>

    <body>
       
        <section class="member">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="animated fadeInDown">Admin Page</h1>

                        <c:choose>
                            <c:when test="${message==''}">

                            </c:when>
                            <c:otherwise>

                                <div class="row">
                                    <div class="col-md-1 col-sm-1"></div>
                                    <div class="col-md-10 col-sm-10">

                                        <c:choose>
                                            <c:when test="${msgType=='success'}">
                                                <div class="alert alert-success">
                                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                    <strong>Success!</strong> ${message}
                                                </div>

                                            </c:when>
                                            <c:otherwise>
                                                <div class="alert alert-danger">
                                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                    <strong>Error!</strong> ${message}
                                                </div>

                                            </c:otherwise>

                                        </c:choose>

                                    </div>
                                    <div class="col-md-1 col-sm-1"></div>
                                </div>
                            </c:otherwise>
                        </c:choose>


                    </div>
                </div>
                <br>

                <div class="row">
                    <div class="col-md-12 col-sm-12">

                    </div>
                </div>

                <div class="row">
                    <div class="col-md-1 col-sm-1"></div>
                    <div class="col-md-10 col-sm-10">
                        <!-- <div class="notification animated fadeInUp">I have <span>10</span> Requests left</div> -->
                        <ul class="nav nav-tabs width25">

                            <c:if test="${status eq 'fixerApproval'}">
                                <li class="active"><a href="../admin/FixerForReview?msgKey=R&status=fixerApproval">Fixer Approval</a>
                                </li>
                                <li><a href="../admin/FixerForReview?msgKey=R&status=queryReview">Queries Review</a>
                                </li>
                                <li><a href="../admin/FixerForReview?msgKey=R&status=notSure">Not Sure</a>
                                </li>
                                <li><a href="../admin/FixerForReview?msgKey=R&status=unClaimed">1 Day UnClaimed</a>
                                </li>
                            </c:if>

                            <c:if test="${status eq 'queryReview'}">
                                <li><a href="../admin/FixerForReview?msgKey=R&status=fixerApproval">Fixer Approval</a>
                                </li>
                                <li class="active"><a href="../admin/FixerForReview?msgKey=R&status=queryReview">Queries Review</a>
                                </li>
                                <li><a href="../admin/FixerForReview?msgKey=R&status=notSure">Not Sure</a>
                                </li>
                                <li><a href="../admin/FixerForReview?msgKey=R&status=unClaimed">1 Day UnClaimed</a>
                                </li>
                            </c:if>


                            <c:if test="${status eq 'notSure'}">
                                <li><a href="../admin/FixerForReview?msgKey=R&status=fixerApproval">Fixer Approval</a>
                                </li>
                                <li><a href="../admin/FixerForReview?msgKey=R&status=queryReview">Queries Review</a>
                                </li>
                                <li class="active"><a href="../admin/FixerForReview?msgKey=R&status=notSure">Not Sure</a>
                                </li>
                                <li><a href="../admin/FixerForReview?msgKey=R&status=unClaimed">1 Day UnClaimed</a>
                                </li>
                            </c:if>

                            <c:if test="${status eq 'unClaimed'}">
                                <li><a href="../admin/FixerForReview?msgKey=R&status=fixerApproval">Fixer Approval</a>
                                </li>
                                <li><a href="../admin/FixerForReview?msgKey=R&status=queryReview">Queries Review</a>
                                </li>
                                <li><a href="../admin/FixerForReview?msgKey=R&status=notSure">Not Sure</a>
                                </li>
                                <li class="active"><a href="../admin/FixerForReview?msgKey=R&status=unClaimed">1 Day UnClaimed</a>
                                </li>
                            </c:if>

                        </ul>

                        <div class="tab-content">
                            <c:if test="${status eq 'fixerApproval'}">
                                <div id="FixerApproval">
                                    <div class="overflowScroll">


                                        <div class="FixerApproval">

                                            <c:choose>
                                                <c:when test="${fixerApprovalCount>=1}">
                                                    <div class="dtablrow">
                                                        <div class="dtablecel thead">S. No.</div>
                                                        <div class="dtablecel thead">UserName</div>
                                                        <div class="dtablecel thead">Email</div>
                                                        <div class="dtablecel thead">Linkdin Profile</div> 
                                                        <div class="dtablecel thead">Action</div>
                                                    </div>
                                                    <c:forEach var="item" items="${fixerStatus}" varStatus="commentLoop">


                                                        <div class="dtablrow">
                                                            <div class="dtablecel">${commentLoop.index+1}</div>
                                                            <%-- <button class="astext" onclick="fixerProfile(this.id);" id=${item.userId}>${item.userName}</button> --%>
                                                                <%-- "${pageContext.request.contextPath}/admin/fixerProfile" --%>
                                                                    <div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/fixerProfile?fixerId=${item.userId}">${item.userName}</a>
                                                                    </div>
                                                                    <div class="dtablecel">${item.email}</div>
                                                                    <%-- <div class="dtablecel"><a href="http://www.linkedin.com">${item.linkedinProfile}</a>
                                                        </div> --%>
                                                        <c:choose>
 															 <c:when test="${fn:contains(item.linkedinProfile, 'http')}">
                                                          <div class="dtablecel"><a href="${item.linkedinProfile}" target="_blank">${item.linkedinProfile}</a>
														  </div>
  															 </c:when>
  
  															 <c:otherwise>
                                                           <div class="dtablecel"><a href="https://${item.linkedinProfile}" target="_blank">${item.linkedinProfile}</a>
 														  </div>
 															 </c:otherwise>
														</c:choose>
                                                       
                                                        <div class="dtablecel">
                                                            <button type="button" class="btn btn-primary" style="border:0px; margin-bottom:8px; font-size:19px; color:#fff; padding:2px 8px; border-radius:3px;" onclick="approve(this.id);" id=${item.userId}>Approve</button>
                                                            <button type="button" class="btn btn-danger" style="border:0px; margin-bottom:8px; font-size:19px; color:#fff; padding:2px 8px; border-radius:3px;" onclick="reject(this.id);" id=${item.userId}>Reject</button>
                                                            <!-- <a href="" class="btn btn-primary">Approve</a>
								<a href="" class="btn btn-danger">Reject</a> -->
                                                        </div>
                                        </div>


                                        </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="animated fadeInDown nodata">No Data Found.</div>
                                        </c:otherwise>
                                        </c:choose>

                                    </div>

                                    <!-- pagination -->
                                    <div class="row">
                                        <div class="col-md-12">
                                            <ul class="pagination pull-right">
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
                                                                <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=left" class="nexpre"><i class="fa fa-caret-left"></i></a>
                                                                </li>
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
                                                            <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${p.index}&flag=current">${p.index}</a>
                                                            </li>
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
                                                                <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=right" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                                                </li>
                                                            </c:otherwise>

                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>


                                            </ul>
                                        </div>
                                    </div>
                                    <!-- pagination -->


                                </div>
                        </div>
                        </c:if>


                        <c:if test="${status eq 'queryReview'}">
                            <div id="InProgress">
                                <div>

                                    <div class="FixerApproval">

                                        <c:choose>
                                            <c:when test="${reviewCount>=1}">
                                                <div class="dtablrow">
                                                    <div class="dtablecel thead">S. No.</div>
                                                   
                                                    <div class="dtablecel thead">MemberId</div>
                                                    <div class="dtablecel thead">Fixer Id</div>
                                                    <div class="dtablecel thead">QueryTitle</div>
                                                    <div class="dtablecel thead">Detail</div>
                                                </div>
                                                <c:forEach var="item" items="${queriesStatus}" varStatus="commentLoop">


                                                    <div class="dtablrow">
                                                        <div class="dtablecel">${commentLoop.index+1}</div>
                                                        
                                                        <div class="dtablecel">${item.memberName}</div>
                                                        <div class="dtablecel">${item.fixername}</div>
                                                        <div class="dtablecel">${item.queryTitle}</div>
                                                        <div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/queryDetail?queryId=${item.queryHashCode}"  >Click here for details</a>
                                                        </div>


                                                    </div>


                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="animated fadeInDown nodata">No Data Found.</div>
                                            </c:otherwise>
                                        </c:choose>


                                    </div>




                                    <!-- pagination -->
                                    <div class="row">
                                        <div class="col-md-12">
                                            <ul class="pagination pull-right">
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
                                                                <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=left" class="nexpre"><i class="fa fa-caret-left"></i></a>
                                                                </li>
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
                                                            <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${p.index}&flag=current">${p.index}</a>
                                                            </li>
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
                                                                <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=right" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                                                </li>
                                                            </c:otherwise>

                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>


                                            </ul>
                                        </div>
                                    </div>
                                    <!-- pagination -->
                                </div>
                            </div>
                        </c:if>





                        <c:if test="${status eq 'notSure'}">
                            <div id="NotSure">
                                <div>
                                    <div class="FixerApproval">
                                        <c:choose>
                                            <c:when test="${notSureCount>=1}">
                                                <c:forEach var="notSure" items="${notSure}" varStatus="theNotSure">

                                                    <div class="unclaimedR">
                                                        <div class="uclaimedcell">
                                                            <div class="unclatbn text-right">
                                                                <i class="fa fa-paperclip" title="Attachment"></i> :
                                                                <a href="" class="btn btn-info" data-toggle="modal" data-target="#myModel${theNotSure.count}"><i class="fa fa-eye"></i>
								View</a>


                                                                <div class="modal fade bs-example-modal-lg" id="myModel${theNotSure.count}" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
                                                                    <div class="vertical-alignment-helper">
                                                                        <div class="modal-dialog modal-lg vertical-align-center">


                                                                            <div class="modal-content">

                                                                                <div class="modal-header">
                                                                                    <button type="button" class="close" data-dismiss="modal" style="font-size:40px;">&times;</button>
                                                                                    <h2 class="modal-title">Attachments</h2>
                                                                                </div>



                                                                                <c:choose>
                                                                                    <c:when test="${notSure.attachedDocumentsCount==0}">
                                                                                        <div class="attachmentlink">
                                                                                            No Attachment Found.
                                                                                        </div>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <div class="attachmentlink">
                                                                                            <c:forEach var="attachedDocument" items="${notSure.attachedDocuments}" varStatus="attDocUnclaimed">
                                                                                                <a href="${attachedDocument.fileUrl}"> ${attDocUnclaimed.count}.  ${attachedDocument.fileName}  &nbsp&nbsp&nbsp&nbsp&nbsp   Uploaded Date :   ${attachedDocument.docUploadDate}</a>
                                                                                            </c:forEach>
                                                                                        </div>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <c:choose>
                                                                    <c:when test="${notSure.attachedDocumentsCount==0}">
                                                                        <a data-toggle="modal" data-target="#myModeldownload${theNotSure.count}" class="btn btn-warning"><i
								class="fa fa-arrow-circle-o-down"></i> Download</a>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a href="../admin/download?queryId=${notSure.hashcode}" class="btn btn-warning"><i
								class="fa fa-arrow-circle-o-down"></i> Download</a>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                
                                                                <a href="../admin/editQuestion?queryId=${notSure.hashcode}&status=notSure" class="btn btn-warning">
                                                            <div>
                                                                <i class="fa fa-pencil-square"></i> Edit
                                                            </div>
                                                        </a>


                                                            </div>
                                                            <%-- <span class="srno">${notSure.queryId}</span> --%>
                                                                <h2>
									 <a  class="title-div" href="${pageContext.request.contextPath}/admin/questionDetailPage?questionId=${notSure.hashcode}">${notSure.queryTitle}</a><span class="postAgo">Posted: ${notSure.timeDiff} ago.
									</span>
								</h2>

                                                                <p class="your-div"> ${notSure.queryContent} </p>

                                                                <div class="unclatbn">
                                                                    <c:forEach var="subCategory" items="${notSure.subCategory}">
                                                                        <a href="#" class="btn btn-primary"><i class="fa fa-tag"></i> ${subCategory}</a>
                                                                    </c:forEach>
                                                                </div>
                                                        </div>
                                                        

                                                        <!-- 	<a class="uclaimedcell" data-toggle="modal" data-target="#myModal6"><div>
									<i class="fa fa-trash-o"></i> Delete
								</div></a> -->

                                                    </div>




                                                    <div class="modal fade" id="myModeldownload${theNotSure.count}" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
                                                        <div class="vertical-alignment-helper">
                                                            <div class="modal-dialog vertical-align-center">
                                                                <div class="modal-content">
                                                                    <div class="attachmentlink">
                                                                        <h2 class="text-center" style="margin-top:68px;"> No attachment found </h2>
                                                                        <button type="button" class="close" data-dismiss="modal" style="font-size:40px; position:absolute; right:10px;top:0px;">&times;</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </c:forEach>

                                            </c:when>
                                            <c:otherwise>
                                                <div class="animated fadeInDown nodata">No Data Found.</div>
                                            </c:otherwise>
                                        </c:choose>

                                    </div>




                                    <!-- pagination -->
                                    <div class="row">
                                        <div class="col-md-12">
                                            <ul class="pagination pull-right">
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
                                                                <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=left" class="nexpre"><i class="fa fa-caret-left"></i></a>
                                                                </li>
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
                                                            <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${p.index}&flag=current">${p.index}</a>
                                                            </li>
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
                                                                <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=right" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                                                </li>
                                                            </c:otherwise>

                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>


                                            </ul>
                                        </div>
                                    </div>
                                    <!-- pagination -->
                                </div>

                            </div>
                        </c:if>



                        <!-- UnClaimed -->
                        <c:if test="${status eq 'unClaimed'}">
                            <div id="UnClaimed">
                                <div>

                                    <div class="FixerApproval">
                                        <c:choose>
                                            <c:when test="${unClaimedCount>=1}">
                                                <c:forEach var="unClaimed" items="${unClaimed}" varStatus="theUnClaimed">

                                                    <div class="unclaimedR">
                                                        <div class="uclaimedcell">
                                                            <div class="unclatbn text-right">
                                                                <i class="fa fa-paperclip" title="Attachment"></i> :
                                                                <a href="" class="btn btn-info" data-toggle="modal" data-target="#myModel${theUnClaimed.count}"><i class="fa fa-eye"></i>
								View</a>


                                                                <div class="modal fade bs-example-modal-lg" id="myModel${theUnClaimed.count}" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
                                                                    <div class="vertical-alignment-helper">
                                                                        <div class="modal-dialog modal-lg vertical-align-center">


                                                                            <div class="modal-content">

                                                                                <div class="modal-header">
                                                                                    <button type="button" class="close" data-dismiss="modal" style="font-size:40px;">&times;</button>
                                                                                    <h2 class="modal-title">Attachments</h2>
                                                                                </div>



                                                                                <c:choose>
                                                                                    <c:when test="${unClaimed.attachedDocumentsCount==0}">
                                                                                        <div class="attachmentlink">
                                                                                            No Attachment Found.
                                                                                        </div>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <div class="attachmentlink">
                                                                                            <c:forEach var="attachedDocument" items="${unClaimed.attachedDocuments}" varStatus="attDocUnclaimed">
                                                                                                <a href="${attachedDocument.fileUrl}"> ${attDocUnclaimed.count}.  ${attachedDocument.fileName}  &nbsp&nbsp&nbsp&nbsp&nbsp   Uploaded Date :   ${attachedDocument.docUploadDate}</a>
                                                                                            </c:forEach>
                                                                                        </div>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <c:choose>
                                                                    <c:when test="${unClaimed.attachedDocumentsCount==0}">
                                                                        <a data-toggle="modal" data-target="#myModeldownload2${theUnClaimed.count}" class="btn btn-warning"><i
								class="fa fa-arrow-circle-o-down"></i> Download</a>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a href="../admin/download?queryId=${unClaimed.hashcode}" class="btn btn-warning"><i
								class="fa fa-arrow-circle-o-down"></i> Download</a>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                
                                                                <a href="../admin/editQuestion?queryId=${unClaimed.hashcode}&status=unClaimed" class="btn btn-info">
                                                            
                                                                <i class="fa fa-pencil"></i> Edit
                                                            
                                                        </a>


                                                            </div>
                                                            <h2>
									 <a class="title-div" href="${pageContext.request.contextPath}/admin/questionDetailPage?questionId=${unClaimed.hashcode}">${unClaimed.queryTitle}</a><span class="postAgo">Posted: ${unClaimed.timeDiff} ago.
									</span>
								</h2>

                                                            <p class="your-div"> ${unClaimed.queryContent} </p>


                                                            <div class="unclatbn">
                                                                <c:forEach var="subCategory" items="${unClaimed.subCategory}">
                                                                    <a href="#" class="btn btn-primary"><i class="fa fa-tag"></i> ${subCategory}</a>
                                                                </c:forEach>
                                                            </div>





                                                        </div>
                                                       <%--  <a href="../admin/editQuestion?queryId=${unClaimed.hashcode}&status=unClaimed" class="uclaimedcell">
                                                            <div>
                                                                <i class="fa fa-pencil-square"></i> Edit
                                                            </div>
                                                        </a> --%>

                                                        <!--  <a class="uclaimedcell" data-toggle="modal" data-target="#myModal6"><div>
									<i class="fa fa-trash-o"></i> Delete
								</div></a> -->
                                                    </div>




                                                    <div class="modal fade" id="myModeldownload2${theUnClaimed.count}" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
                                                        <div class="vertical-alignment-helper">
                                                            <div class="modal-dialog vertical-align-center">
                                                                <div class="modal-content">
                                                                    <div class="attachmentlink">
                                                                        <h2 class="text-center" style="margin-top:68px;"> No attachment found </h2>
                                                                        <button type="button" class="close" data-dismiss="modal" style="font-size:40px; position:absolute; right:10px;top:0px;">&times;</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </c:forEach>

                                            </c:when>
                                            <c:otherwise>
                                                <div class="animated fadeInDown nodata">No Data Found.</div>
                                            </c:otherwise>
                                        </c:choose>

                                    </div>




                                    <!-- pagination -->
                                    <div class="row">
                                        <div class="col-md-12">
                                            <ul class="pagination pull-right">
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
                                                                <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=left" class="nexpre"><i class="fa fa-caret-left"></i></a>
                                                                </li>
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
                                                            <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${p.index}&flag=current">${p.index}</a>
                                                            </li>
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
                                                                <li><a href="../admin/FixerForReview?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=right" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                                                </li>
                                                            </c:otherwise>

                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>


                                            </ul>
                                        </div>
                                    </div>
                                    <!-- pagination -->
                                </div>

                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="col-md-1 col-sm-1"></div>
            </div>
            </div>
        </section>


        <script>
        /*     $(document).ready(function() {
                var timeZone = getTimezoneName();
                var url = $("#queryDetailLink").attr("href");
                url = url + timeZone;
                document.getElementById("queryDetailLink").setAttribute("href", url);
            });

            function getTimezoneName() {
                timezone = jstz.determine();
                return timezone.name();
            }
 */



            function approve(clicked_id) {
                console.log("approved");

                $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/admin/updateFixer",
                        data: {
                            id: clicked_id,
                            status: "A"

                        }
                    })
                    .done(
                        function(response) {
                            window.location.reload();
                        });

            }


            function reject(clicked_id) {
                console.log("rejected");


                $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/admin/updateFixer",
                        data: {
                            id: clicked_id,
                            status: "D"

                        }
                    })
                    .done(
                        function(response) {
                            window.location.reload();

                        });
            }
        </script>

    </body>

    </html>