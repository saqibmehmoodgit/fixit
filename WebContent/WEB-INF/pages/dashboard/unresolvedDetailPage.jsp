<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<script type="text/javascript" src="../js/commonMethods.js"></script>
</head>

   <body>
   <script>
 
</script>
         <a name="home" id="home"></a>
     
      <!--header close-->
      <!-- price -->
      <section class="member">
         <div class="container">
            <div class="row">
               <div class="col-md-12">
                  <h1 class="animated fadeInDown">Not Fixed/Inactive</h1>
                  
               </div>
               
            </div>
            <br>
             <div class="row">
				<div class="col-md-9 col-sm-9"></div>
				<div class="col-md-3 col-sm-3">
						<p style="font-size:25px; color:#f00; font-weight:bold;">Screen sharing tools:</p>
						<a href="https://www.teamviewer.com/en/download" target="blank" class="pull-right"><img src="../images/tvIcon.png" width="150"></a>
						<a href="https://join.me/" target="blank"  class="pull-right"><img src="../images/jmLogo.png" width="70">
									 </a>
							
				</div>
			</div>
            <div class="row">
            	<div class="col-md-1 col-sm-1"></div>
               <div class="col-md-10 col-sm-10">
               <h2 class="QueryTitle fontsize35">1.Query Title: ${query.queryTitle}</h2>
                 <div class="notimessagetxt"> <span class="more">${queryContent}</span></div>
				<br>
               </div>
            </div>
            <div class="row">
               <div class="col-md-1 col-sm-1"></div>
               <div class="col-md-10 col-sm-10 col-sm-12">
                  <div class="notimessagetxt">
                     <div class="graybg1">
                        <div class="row">
                           <div class="col-md-6 col-sm-6">
                              <h2><span>1.</span> Reason given by Member</h2>
                              
                              <div class="form-group">
                                 <textarea class="form-control solutionsbox" readonly placeholder="${memberReason}" style="max-height:135px;"></textarea>
                              </div>
                              
                              
                           </div>
                           <div class="col-md-6 col-sm-6">
                              <div class="chatdiv">
                                 <h2 class="text-center">Previous Discussion or Chat</h2>
                                 <div class="chatbox">
                                    <div class="chat">
                                       <c:forEach var="message" items="${messagesSet}">
											<input id="customerId" type="hidden" value=${message.customerId}>
											<input id="fixerId" type="hidden" value=${message.fixerId}>
											<input id="queryId" type="hidden" value=${queryId} >
											
											<c:if test="${message.msgFrom == 'F'}">
											
						                   
													<div class="crow">
														<img src='${myUser.profileIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
												   You  ${message.auditTime} <br>
															<c:if test="${message.status=='W'}">
														<p>	${message.message}
														<!-- <script>document.write(replaceURLWithHTMLLinks('${message.message}'))</script> --></p>
														</c:if>
														<c:if test="${message.status=='WD'}">
														Document Uploaded <br>
														<a href="${message.message}"><i class="fa fa-paperclip"></i> ${message.documentFilename}</a>
														</c:if>
														<c:if test="${message.status=='WL'}">
														Link Added <br>
														<a href="${message.message}" target="_blank">${message.message}</a>
														</c:if>
													</div>
					                         </c:if>
					                         
					                         <c:if test="${message.msgFrom == 'C'}">
						                   
													<div class="crow1">
														<img src='${memberImgIcon}' onerror="if (this.src != '../images/default_profile.jpg') this.src ='../images/default_profile.jpg';">
													 ${memberName}	${message.auditTime} <br>
														<c:if test="${message.status=='W'}">
														
														<p>	${message.message}
														<!-- <script>document.write(replaceURLWithHTMLLinks('${message.message}'))</script> --></p>
														</c:if>
														<c:if test="${message.status=='WD'}">
														Document Uploaded <br>
														<a href="${message.message}"><i class="fa fa-paperclip"></i> ${message.documentFilename}</a>
														</c:if>
														<c:if test="${message.status=='WL'}">
														Link Added <br>
														<a href="${message.message}" target="_blank">${message.message}</a>
														</c:if>

													</div>
					                         </c:if>
					                        
											</c:forEach>
                                    </div>
                                 </div>
                              </div>
                           </div>
                        </div>
                        <div class="clearfix"></div>
                     </div>
                     <h2  class="fontsize35" style="text-align:left; margin-left:0px;">2.Please choose the following button:</h2>
                     <div class="row">
                        <div class="col-md-9 col-sm-9">
                           <button type="button"style="margin-top:15px;width:230px; margin-right:20px;" data-toggle="modal" data-target="#myModal7" class="btn btn-info">Raised a Conflict</button>
                           <a href="${pageContext.request.contextPath}/fixer/declineSelf?queryId=${queryId}" style="margin-top:15px;width:230px;" class="btn btn-warning">Accept the Declination</a>
                        </div>
                        <!-- Modal -->
                        <div class="modal fade" id="myModal7" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                           <div class="vertical-alignment-helper">
                              <div class="modal-dialog vertical-align-center">
                                 <div class="modal-content">
                                    <div class="modal-header">
                                       <button type="button" class="close" style="font-size:40px;" data-dismiss="modal"><span aria-hidden="true">&times;</span>
                                       </button>
                                       <h2 class="modal-title" id="myModalLabel">Not Fixed</h2>
                                    </div>
                                    <div class="modal-body">
                                     <form  role="form" class="contact-form" id="contact-form" method="post"  action="${pageContext.request.contextPath}/fixer/fixerRaiseConflict"  >
                                       <h2 class="text-danger" style="font-size:35px !important;margin:0 0 10px 0;">Give us reason why this query is fixed? </h2>
                                       <textarea class="nofixedtext" placeholder="type here..." id="nofixedtextArea" name="message" onkeyup="textEnteredInArea()"></textarea>
                                        <input  type="hidden"  value="${queryId}" name="queryId" >
                                       <div class="clearfix"></div>
                                       <p class="text-left">Note :Admin will resolve this Conflict.</p>
                                       <button type="submit" class="btn btn-success" id="nofixedtextAreaButton" style="padding: 5px 40px;font-size: 23px; margin-top:10px;">Send</button>
                                  </form>
                                    </div>
                                 </div>
                              </div>
                           </div>
                        </div>
                        <div class="col-md-6 col-sm-6">
                        
                           <!-- <button type="button" style="margin-top:15px" class="btn btn-warning  btn-block">Accept the Declination</button> -->
                        </div>
                        <!-- Button trigger modal -->
                        
                     </div>
                  </div>
               </div>
               <div class="col-md-1 col-sm-1"></div>
            </div>
         </div>
      </section>
     
     <script>
     jQuery(document).ready(function () {
     	$("#nofixedtextAreaButton").prop("disabled",true);
     });
     
     function textEnteredInArea(){
     	var ele = document.getElementById('nofixedtextArea').value;
     	if(ele.length>0){
     	$("#nofixedtextAreaButton").prop("disabled",false);
     	}else{
     		$("#nofixedtextAreaButton").prop("disabled",true);
     	}
     }
     </script>
   </body>
