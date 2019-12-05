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
					<h1 class="animated fadeInDown">Payees  List</h1>
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
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
					<div class="adminFixersList">
						<div id="FixerApproval" class="tab-pane fade in active">
						<div class="row">
						
						<div class="col-md-12 col-sm-12 col-xs-12">
							<!-- <div class="favsearch">
								<input id="searchId" placeholder="Search for fixer" onkeyup="searchEnter(this.value)" type="search">
								<i class="fa fa-search"></i>
								<i class="fa fa-times-circle"></i>
							</div> -->
							<div class="searchPay">
								<input type="search" id="searchTextField" >	
								<button type="submit" id="searchButton" class="btn btn-danger" onclick="searchButtonTapped()">Search</button>
							</div>
						</div>
						
					</div>
				<div class="overflowX">
				  <c:choose>
				  <c:when test="${fixerPaymentCount>=1}">
				  
				  <div class="FixerApproval" id="searchFixerList">
						<div class="dtablrow">
							<div class="dtablecel thead">S.No.</div>
							<div class="dtablecel thead">Name</div>
							<div class="dtablecel thead">PayPal ID</div>
							<div class="dtablecel thead">Issues Fixed</div>
							<div class="dtablecel thead">Amount</div>
							<div class="dtablecel thead">Pay Option</div>
						</div>
					
							
						<c:forEach var="fixerPayment" items="${fixerPayment}" varStatus="commentLoop">
  									
                          
					<div class="dtablrow" >
							<div class="dtablecel">${commentLoop.index+1}</div>
							<div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/fixerProfile?fixerId=${fixerPayment.userId}">${fixerPayment.name}</a></div>
							<div class="dtablecel">${fixerPayment.paypalId}</div>
							<div class="dtablecel" ><a href="${pageContext.request.contextPath}/admin/fixedIssueList?fixerId=${fixerPayment.userId}">${fixerPayment.fixedCount}</a></div>
							<div class="dtablecel">$ ${fixerPayment.amountPaid}</div>
							<div class="dtablecel">
								<a href="${pageContext.request.contextPath}/admin/makeFixerPayment?amount=${fixerPayment.amountPaid}&fixerId=${fixerPayment.userId}" class="btn btn-primary">Done Payment</a>
							</div>
						</div>
						</c:forEach> 
						
							</div>	
							
						<div class="row">
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
                                                                        <li><a href="../admin/fixersList?pageNo=${currentPageNo}&flag=left" class="nexpre"><i class="fa fa-caret-left"></i></a>
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
                                                                    <li><a href="../admin/fixersList?pageNo=${p.index}&flag=current">${p.index}</a>
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
                                                                        <li><a href="../admin/fixersList?pageNo=${currentPageNo}&flag=right" class="nexpre"><i class="fa fa-caret-right"></i></a>
                                                                        </li>
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
					</div>
				</div>
				<div class="col-md-1 col-sm-1"></div>
			</div>
			
					
		</div>
	</section>
	<!-- price close -->
	
	
    <script>
    
    function searchButtonTapped(){
    	var name = document.getElementById("searchTextField");
		var fixerName=name.value;
		$.ajax({
			method : "POST",
		    url : "${pageContext.request.contextPath}/admin/searchFixer",
		    data : {
		    searchText: fixerName
		     }
		   }).done(function(response) {
			   var data = response.result.fixers;
			   console.log(data);
			   var totalPage=response.result.totalPage;
			   var startPage=response.result.startPage;
			   var endPage=response.result.endPage;
			   var currentPageNo=response.result.currentPageNo;
			   var pagHtml='';
			    $("#searchFixerList").empty();
			     $("#fixerListPagination").empty();
                    if(totalPage==0){
			    	 
			     }else{
			    	 if(currentPageNo=='1'){
			    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
			    	 }else{
			    		 pagHtml='<li onclick="pagClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
			    	 }
			     }
			     
			     for(var k=startPage;k<=endPage;k++){
			    	if(k==currentPageNo){
			    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
			    	}else{
			    	pagHtml +='<li  onclick="pagClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
			    	}
			    	
			    }
			     if(totalPage==0){
			    	 
			     }else{
			    	 if(currentPageNo==totalPage){
			    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
			    	 }else{
			    		 pagHtml +='<li onclick="pagClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
			    	 }
			     }
			     
			    
			    $("#fixerListPagination").append(pagHtml); 
			    
			    var html ='';
			    if(data.length>0){
					   html+='<div class="dtablrow"><div class="dtablecel thead">S.No.</div><div class="dtablecel thead">Name</div><div class="dtablecel thead">PayPal ID</div><div class="dtablecel thead">Issues Fixed</div><div class="dtablecel thead">Amount</div><div class="dtablecel thead">Pay Option</div></div>';
				   }
			    for(var i =0 ;i<data.length;i++){
			    	var j=i+1;
			    	html += '<div class="dtablrow" ><div class="dtablecel" id="buttonNumber"+'+j+'>'+ j +'</div><div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/fixerProfile?fixerId='+data[i].userId+'">'+data[i].name+'</a></div><div class="dtablecel">'+data[i].email+'</div><div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/fixedIssueList?fixerId='+data[i].userId+'">'+data[i].fixedCount+'</a></div><div class="dtablecel">$'+data[i].amountPaid+'</div><div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/makeFixerPayment?amount='+data[i].amountPaid+'&fixerId='+data[i].userId+'" class="btn btn-primary">Done Payment</a></div></div>';
			    	
			    }
			    console.log(html);
			    	$("#searchFixerList").append(html);
			    	
			    
			    	
			    
		   });
    }
    
    function pagClick(pageNo,flag){
    	var name = document.getElementById("searchTextField");
		var fixerName=name.value;
		$.ajax({
			method : "POST",
		    url : "${pageContext.request.contextPath}/admin/searchFixer",
		    data : {
		    searchText:fixerName,
		    pageNo: pageNo,
		    flag:flag
		     }
		   }).done(function(response) {
			   var data = response.result.fixers;
			   var totalPage=response.result.totalPage;
			   var startPage=response.result.startPage;
			   var endPage=response.result.endPage;
			   var currentPageNo=response.result.currentPageNo;
			   var pagHtml='';
			    $("#searchFixerList").empty();
			     $("#fixerListPagination").empty();
                    if(totalPage==0){
			    	 
			     }else{
			    	 if(currentPageNo=='1'){
			    		pagHtml+='<li  ><a class="disable"><i class="fa fa-caret-left"></i></a></li  >'
			    	 }else{
			    		 pagHtml='<li onclick="pagClick(\''+currentPageNo+'\',\''+'left'+'\')" ><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>';
			    	 }
			     }
			     
			     for(var k=startPage;k<=endPage;k++){
			    	if(k==currentPageNo){
			    		pagHtml +='<li ><a  class="active">'+k+'</a></li>' ;
			    	}else{
			    	pagHtml +='<li  onclick="pagClick(\''+k+'\',\''+'current'+'\')" ><a   >'+k+'</a></li>' ;
			    	}
			    	
			    }
			     if(totalPage==0){
			    	 
			     }else{
			    	 if(currentPageNo==totalPage){
			    		 pagHtml+='<li  ><a    class="disable"><i class="fa fa-caret-right"></i></a></li>'
			    	 }else{
			    		 pagHtml +='<li onclick="pagClick(\''+currentPageNo+'\',\''+'right'+'\')" ><a  class="active"><i class="fa fa-caret-right"></i></a></li>';
			    	 }
			     }
			     
			    
			    $("#fixerListPagination").append(pagHtml); 
			    
			    var html ='';
				   if(data.length>0){
					   html+='<div class="dtablrow"><div class="dtablecel thead">S.No.</div><div class="dtablecel thead">Name</div><div class="dtablecel thead">PayPal ID</div><div class="dtablecel thead">Issues Fixed</div><div class="dtablecel thead">Amount</div><div class="dtablecel thead">Pay Option</div></div>';
				   }
			    for(var i =0 ;i<data.length;i++){
			    	var j=i+1;
			    	html += '<div class="dtablrow" ><div class="dtablecel" id="buttonNumber"+'+j+'>'+ j +'</div><div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/fixerProfile?fixerId='+data[i].userId+'">'+data[i].name+'</a></div><div class="dtablecel">'+data[i].email+'</div><div class="dtablecel">$<a href="${pageContext.request.contextPath}/admin/fixedIssueList?fixerId='+data[i].userId+'">'+data[i].fixedCount+'</a></div><div class="dtablecel">$'+data[i].amountPaid+'</div><div class="dtablecel"><a href="${pageContext.request.contextPath}/admin/makeFixerPayment?amount='+data[i].amountPaid+'&fixerId='+data[i].userId+'" class="btn btn-primary">Done Payment</a></div></div>';
			    	
			    }
			        console.log(html);
			    	$("#searchFixerList").append(html);
			    	
		   });
    }
 
    
    </script>
  </body>
