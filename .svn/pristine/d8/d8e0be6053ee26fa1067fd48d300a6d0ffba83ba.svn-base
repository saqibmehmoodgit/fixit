<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
   <div class="latest-feeds">
   
   					<div class="feedslisting" >
   						
   						<ul>
   							<li class="head">
   								<span class="sno" >S. No.</span>
   								<span class="detail" >Detail</span>
   							</li>
   						
   							
   					     <c:forEach var="latestFeed" items="${latestFeed}" >
   					     	<li class="latestfeedlist">
                  <c:if test="${latestFeed.status=='O'}">
            
                          	   <span class="sno">${latestFeed.feedNo }</span>
   								<span class="detail">${latestFeed.memberName } post a Request ${latestFeed.queryTitle}  ${latestFeed.timeDiff } ago.</span>
   							
           </c:if>
             <c:if test="${latestFeed.status=='W'}">
                <c:if test="${latestFeed.messageFrom=='F'}">
                 <c:choose>
                 <c:when test="${latestFeed.message=='Started Working'}">
                      <span class="sno">${latestFeed.feedNo }</span>
   				     <span class="detail">${latestFeed.fixerName }  started working  for Request ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
                 </c:when>
                 <c:otherwise>
                     <span class="sno">${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.fixerName }  message ${latestFeed.message}  for Request ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
                 </c:otherwise>
                 </c:choose>
                </c:if>
                <c:if test="${latestFeed.messageFrom=='C'}">
                     <span class="sno" >${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.memberName } message ${latestFeed.message} for Request ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
                </c:if>
                <c:if test="${latestFeed.messageFrom=='A'}">
                </c:if>
           </c:if>
             <c:if test="${latestFeed.status=='C'}">
             
                   <span class="sno">${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.memberName } closed Request  ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
           </c:if>
             <c:if test="${latestFeed.status=='FR'}">
             
                  <span class="sno">${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.memberName}  rejected  ${latestFeed.fixerName} for Request  ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
           </c:if>
             <c:if test="${latestFeed.status=='UI'}">
                  <span class="sno">${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.fixerName}  rejected  for Non response for Request  ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
           </c:if  >
             <c:if  test="${latestFeed.status=='UN'}">
             
                  <span class="sno">${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.memberName}  mark  ${latestFeed.queryTitle}  Request not fixed ${latestFeed.timeDiff } ago.</span>
             </c:if>
             <c:if  test="${latestFeed.status=='WD'}">
               <c:if test="${latestFeed.messageFrom=='F'}">
               
                    <span class="sno">${latestFeed.feedNo }</span>
   				 <span class="detail" >${latestFeed.fixerName}  added document for Request  ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
                </c:if>
             <c:if test="${latestFeed.messageFrom=='C'}">
             
                  <span class="sno" >${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.memberName}   added document  for Request  ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
             
                  <p></p>
                </c:if>
             </c:if>
             <c:if  test="${latestFeed.status=='WL'}">
                <c:if test="${latestFeed.messageFrom=='F'}">
                     <span class="sno">${latestFeed.feedNo }</span>
   				    <span class="detail">${latestFeed.fixerName}  added link for Request  ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
                </c:if>
             <c:if test="${latestFeed.messageFrom=='C'}">
             
                  <span class="sno" >${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.memberName}   added link  for Request  ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
                </c:if>
             </c:if>
               <c:if  test="${latestFeed.status=='R'}">
                    <span class="sno" >${latestFeed.feedNo }</span>
   				 <span class="detail">Request  goes for Review ${latestFeed.queryTitle} ${latestFeed.timeDiff } ago.</span>
             </c:if>
              <c:if  test="${latestFeed.status=='IR'}">
                  <c:if test="${latestFeed.messageFrom=='C'}">
                        <span class="sno" >${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.memberName} message ${latestFeed.message} when Request  ${latestFeed.queryTitle}  in Review ${latestFeed.timeDiff } ago.</span>
                  </c:if>
                  
                       <c:if test="${latestFeed.messageFrom=='F'}">
                        <span class="sno" >${latestFeed.feedNo }</span>
   				 <span class="detail">${latestFeed.fixerName} message ${latestFeed.message} when Request  ${latestFeed.queryTitle}  in Review ${latestFeed.timeDiff } ago.</span>
                  </c:if>
                  
                       <c:if test="${latestFeed.messageFrom=='A'}">
                        <span class="sno" >${latestFeed.feedNo }</span>
   				 <span class="detail">Admin message ${latestFeed.message} when Request  ${latestFeed.queryTitle}  in Review ${latestFeed.timeDiff } ago.</span>
                  </c:if>
              
             </c:if>
             
             </li >
          </c:forEach>
   							
   						</ul>
   					
   					</div>
</div>

       <div class="row">
                        <div class="col-md-12 newpagination">
                           <ul class="pagination pull-right" id="pagination_unClaimed">
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
                                          <li><a href="../admin/latestFeeds?pageNo=${currentPageNo}&flag=left" class="nexpre"><i class="fa fa-caret-left"></i></a>
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
                                       <li><a href="../admin/latestFeeds?pageNo=${p.index}&flag=current">${p.index}</a>
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
                                          <li><a href="../admin/latestFeeds?pageNo=${currentPageNo}&flag=right" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                          </li>
                                       </c:otherwise>
                                    </c:choose>
                                 </c:otherwise>
                              </c:choose>
                           </ul>
                        </div>
                     </div>
</body>
