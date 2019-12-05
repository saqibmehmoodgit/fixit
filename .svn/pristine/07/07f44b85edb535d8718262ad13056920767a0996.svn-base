<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
   <script type="text/javascript">
      // <![CDATA[
      $(function() {
          $(".title-div").each(function(i) {
              len = $(this).text().length;
              if (len > 80) {
                  $(this).text($(this).text().substr(0, 40) + '...');
              }
          });
          $(".your-div").each(function(i) {
              len = $(this).text().length;
              if (len > 80) {
                  $(this).text($(this).text().substr(0, 400) + '...');
              }
          });
      });
      // ]]>
   </script>
</head>
<title>ERPfixers | Dashboard</title>
<body>
<script>
 
 
</script>
   <!-- price -->
   <section class="member">
      <div class="container">
         <div class="row">
            <div class="col-md-12">
               <h1 class="animated fadeInDown">Admin's DashBoard</h1>
               
               <%-- <c:choose>
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
               </c:choose> --%>
            </div>
         </div>
         <br>
         <div class="row">
            <div class="col-md-1 col-sm-1"></div>
            <div class="col-md-10 col-sm-10">
             <%--   <div class="notification animated fadeInUp" data-toggle="modal" data-target="#myModal" style="cursor:pointer;">
                  I have <span>${points}</span> Credits left
               </div> --%>
               
               
              
               
               
               
                 <%-- <c:choose>
                 <c:when test="${awaitingConfirmationCount>=1}">
                  <ul class="nav nav-tabs adminListTab1 ">
                       <c:if test="${status eq 'Unclaimed'}">
                     <li class="active"><a href="../member/dashBoard?msgKey=R&status=Unclaimed">Unclaimed</a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=InProgress">In Progress <span class="notification1">${inProgressNotification}</span></a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=Closed">Closed</a>
                     </li>
                        <li><a href="../member/dashBoard?msgKey=R&status=Pending">Pending</a>
                        </li>
                  </c:if>
                  <c:if test="${status eq 'InProgress'}">
                     <li><a href="../member/dashBoard?msgKey=R&status=Unclaimed">Unclaimed</a>
                     </li>
                     <li class="active"><a href="../member/dashBoard?msgKey=R&status=InProgress">In Progress <span class="notification1">${inProgressNotification}</span></a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=Closed">Closed</a>
                     </li>
                        <li><a href="../member/dashBoard?msgKey=R&status=Pending">Pending</a>
                        </li>
                  </c:if>
                  <c:if test="${status eq 'Closed'}">
                     <li><a href="../member/dashBoard?msgKey=R&status=Unclaimed">Unclaimed</a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=InProgress">In Progress <span class="notification1">${inProgressNotification}</span></a>
                     </li>
                     <li class="active"><a href="../member/dashBoard?msgKey=R&status=Closed">Closed</a>
                     </li>
                        <li><a href="../member/dashBoard?msgKey=R&status=Pending"> Pending</a>
                        </li>
                  </c:if>
                  <c:if test="${status eq 'Pending'}">
                     <li> <a href="../member/dashBoard?msgKey=R&status=Unclaimed">Unclaimed</a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=InProgress">In Progress <span class="notification1">${inProgressNotification}</span></a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=Closed">Closed</a>
                     </li>
                        <li class="active"><a href="../member/dashBoard?msgKey=R&status=Pending">Pending</a>
                        </li>
                  </c:if>
                   </ul>
                 </c:when>
                 <c:otherwise>
                  <ul class="nav nav-tabs  ">
                     <c:if test="${status eq 'Unclaimed'}">
                     <li class="active"><a href="../member/dashBoard?msgKey=R&status=Unclaimed">Unclaimed</a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=InProgress">In Progress <span class="notification1">${inProgressNotification}</span></a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=Closed">Closed</a>
                     </li>
                  </c:if>
                  <c:if test="${status eq 'InProgress'}">
                     <li><a href="../member/dashBoard?msgKey=R&status=Unclaimed">Unclaimed</a>
                     </li>
                     <li class="active"><a href="../member/dashBoard?msgKey=R&status=InProgress">In Progress <span class="notification1">${inProgressNotification}</span></a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=Closed">Closed</a>
                     </li>
                  </c:if>
                  <c:if test="${status eq 'Closed'}">
                     <li><a href="../member/dashBoard?msgKey=R&status=Unclaimed">Unclaimed</a>
                     </li>
                     <li><a href="../member/dashBoard?msgKey=R&status=InProgress">In Progress <span class="notification1">${inProgressNotification}</span></a>
                     </li>
                     <li class="active"><a href="../member/dashBoard?msgKey=R&status=Closed">Closed</a>
                     </li>
                  </c:if>
                  </ul>
                 </c:otherwise>
                 </c:choose> --%>
               
               
              
              <ul class="nav nav-tabs adminListTab1 fixreporttab">
                       
                     <li ><a href="">Issues Fixed By ${user.userName}</a>
                     </li>
                  
                  
                  
                  
                   </ul> 
               
              
              
               <div class="tab-content">
                  <div id="UnClaimed" class="tab-pane fade in active">
                     <div id="issue_unClaimed">
                        
                              <c:forEach var="queryList" items="${fixedIssueList}" varStatus="thequeryList">
                                 <div class="unclaimedR">
                                    <div class="uclaimedcell">
                                     
                                          
                                    
                                    <h2 class="title-div" >${queryList.queryTitle}</h2>
                                      
                                          
                                    
                                    
                                          
                                           
                                          
                                       
                                    
                                      <%--  <span class="postAgo">Posted: ${queryList.timeDiff} ago. </span> --%>
                                       <p class="your-div"> ${queryList.queryContent} </p>
                                       <div class="unclatbn1">
                                       <%--    <c:forEach var="subCategory" items="${queryList.subCategory}" >
                                             <a href="#" class="btn btn-primary"><i class="fa fa-tag"></i> ${subCategory}</a>
                                          </c:forEach> --%>
                                       </div>
                                    </div>
                                   
                                 </div>
                               
                                 <!-- Modal -->
                                 
                              </c:forEach>
                         
                     </div>
                     <%-- <div class="row">
                        <div class="col-md-12">
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
                                          <li><a href="../member/dashBoard?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=left" class="nexpre"><i class="fa fa-caret-left"></i></a>
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
                                       <li><a href="../member/dashBoard?msgKey=R&status=${status}&pageNo=${p.index}&flag=current">${p.index}</a>
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
                                          <li><a href="../member/dashBoard?msgKey=R&status=${status}&pageNo=${currentPageNo}&flag=right" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                          </li>
                                       </c:otherwise>
                                    </c:choose>
                                 </c:otherwise>
                              </c:choose>
                           </ul>
                        </div>
                     </div> --%>
                  </div>
               </div>
            </div>
         </div>
         <div class="col-md-1 col-sm-1"></div>
      </div>
   </section>
   <!-- price close -->
   <script type="text/javascript">
      //  pagination for unClaimed Tab
      
     
      
   
   </script>
</body>