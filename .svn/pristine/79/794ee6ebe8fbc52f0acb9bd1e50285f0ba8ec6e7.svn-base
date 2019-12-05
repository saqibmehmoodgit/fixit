<!DOCTYPE html>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>

    <!-- price -->
<script>
 
</script>
    <section class="member">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="animated fadeInDown">Unclaimed</h1>

                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-1 col-sm-1"></div>
                <div class="col-md-10 col-sm-10">
                    <div class="fixerdetail">





                        <div id="UnClaimed" class="tab-pane fade in active">


                            <div class="unclaimedR">
                                <div class="uclaimedcell">
                                    <div class="unclatbn text-right">
                                        <i class="fa fa-paperclip" title="Attachment"></i> :
                                        <a href="" class="btn btn-info" data-toggle="modal" data-target="#myModel1"><i class="fa fa-eye"></i>
										View</a>

                                        <div class="modal fade bs-example-modal-lg" id="myModel1" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
                                            <div class="vertical-alignment-helper">
                                                <div class="modal-dialog modal-lg vertical-align-center">





                                                    <div class="modal-content">

                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" style="font-size:40px;">&times;</button>
                                                            <h2 class="modal-title" style="display:block; margin-left:-18px;">Attachments</h2>
                                                        </div>



                                                        <c:choose>
                                                            <c:when test="${queryDetail.attachedDocumentsCount==0}">
                                                                <div class="attachmentlink">
                                                                    No Attachment Found.
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="attachmentlink">
                                                                    <c:forEach var="attachedDocument" items="${queryDetail.attachedDocuments}" varStatus="attDocUnclaimed">
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
                                            <c:when test="${queryDetail.attachedDocumentsCount==0}">
                                                <a data-toggle="modal" data-target="#myModeldownload1" class="btn btn-warning"><i
										class="fa fa-arrow-circle-o-down"></i> Download</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="../member/download?queryId=${queryDetail.hashcode}" class="btn btn-warning"><i
										class="fa fa-arrow-circle-o-down"></i> Download</a>
                                            </c:otherwise>
                                        </c:choose>
                                        
                                         <a href="../member/editQuestion?queryId=${queryDetail.hashcode}" class="btn btn-info"><i class="fa fa-suitcase"></i> Edit</a>
                                   <a class="btn btn-warning" data-toggle="modal" data-target="#myModal6"><i class="fa fa-trash-o"></i> Delete</a>


                                    </div>
                                    <h2>${queryDetail.queryTitle}
								<span class="postAgo">Posted: ${queryDetail.timeDiff} ago. </span>
							</h2>
                                    <p>${queryDetail.queryContent}</p>
                                    <div class="unclatbn1">
                                            <c:forEach var="subCategory" items="${queryDetail.subCategory}">
                                             <a href="#" class="btn btn-primary"><i class="fa fa-tag"></i> ${subCategory}</a>
                                             </c:forEach>

                                   </div>
                                </div>
                                <div class="uclaimedcell">
                                   <%--  <a href="../member/editQuestion?queryId=${queryDetail.hashcode}" class="uclaibtn"><i class="fa fa-suitcase"></i> Edit</a>
                                   <a class="uclaibtn1" data-toggle="modal" data-target="#myModal6"><i class="fa fa-trash-o"></i> Delete</a> --%>
                                                                        
                                                                           
                                                                        
                                                                    

                                </div>
                            </div>
                         <div class="modal fade" id="myModeldownload1" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
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





                                                            <!-- Modal -->
                                                            <div class="modal fade" id="myModal6" role="dialog">
                                                                <div class="modal-dialog">

                                                                    <!-- Modal content-->
                                                                    <div class="modal-content" style="margin-top:38%;">
                                                                        <!-- <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Are you sure to delete?</h4>
        </div> -->
                                                                        <div class="modal-body" style="text-align:center; min-height:160px;">
                                                                            <h2 class="text-center">Are you sure want to delete?</h2>
                                                                            <a href="../member/delete?queryId=${queryDetail.hashcode}" class="btn btn-info width100">OK</a>
                                                                            <a class="btn btn-warning width100" data-dismiss="modal">Cancel</a>

                                                                        </div>

                                                                    </div>

                                                                </div>
                                                            </div>


                        </div>
                        <!--// 1 //-->

                    </div>
                </div>
                <div class="col-md-1 col-sm-1"></div>
            </div>
        </div>
    </section>
    <!-- price close -->


</body>