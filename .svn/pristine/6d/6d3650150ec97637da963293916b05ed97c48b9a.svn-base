<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript">// <![CDATA[
	$(function(){
	  $(".blogp").each(function(i){
		len=$(this).text().length;
		if(len>80)
		{
		  $(this).text($(this).text().substr(0,200)+'...');
		}
	  });
	  $(".dec").each(function(i){
			len=$(this).text().length;
			if(len>80)
			{
			  $(this).text($(this).text().substr(0,40)+'...');
			}
		  });
	});
	// ]]></script>
	<script> 
	$(document).ready(function(){
		$(".searchc").click(function(){
			$(".search-form1").slideToggle("slow");
		});
		
	});
	
	$(document).ready(function(){
		
	     var count = ${blogListSize};
		 for(var i=0; i< count ; i++){
	     var id = 'blogDesc'+i;
		 var blogDesc = $("#"+id).find('img').remove();
		 console.log("blogDesc" + blogDesc ); 
		 }
	});	
		
	</script>
	<script src="js/script.js"></script>
	
	<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet" type="text/css">
  </head>
  <body>
  <script>
 
</script>
  <a name="home" id="home"></a>

	<!-- price -->
	
	<section class="member">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="animated fadeInDown"></h1>
				</div>
			</div>
			<br>
			
			
			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
				<div class="adminbloglist">					
				
				
				<ul class="nav nav-tabs adminListTab">
				<c:if test="${status == 'draft'}">
					<li class="active"><a  href="blogList?status=draft">Draft</a></li>
					<li><a  href="blogList?status=pending">Pending</a></li>
					<li><a  href="blogList?status=approved">Approved</a></li>
					<li><a  href="blogList?status=declined">Declined</a></li>
					<li><a  href="blogList?status=published">Published</a></li>
				</c:if>	
				<c:if test="${status == 'pending'}">
					<li><a  href="blogList?status=draft">Draft</a></li>
					<li class="active"><a  href="blogList?status=pending">Pending</a></li>
					<li><a  href="blogList?status=approved">Approved</a></li>
					<li><a  href="blogList?status=declined">Declined</a></li>
					<li><a  href="blogList?status=published">Published</a></li>
				</c:if>	
				<c:if test="${status == 'approved'}">
					<li><a  href="blogList?status=draft">Draft</a></li>
					<li><a  href="blogList?status=pending">Pending</a></li>
					<li class="active"><a  href="blogList?status=approved">Approved</a></li>
					<li><a  href="blogList?status=declined">Declined</a></li>
					<li><a  href="blogList?status=published">Published</a></li>
				</c:if>	
				<c:if test="${status == 'declined'}">
					<li><a  href="blogList?status=draft">Draft</a></li>
					<li><a  href="blogList?status=pending">Pending</a></li>
					<li><a  href="blogList?status=approved">Approved</a></li>
					<li  class="active"><a  href="blogList?status=declined">Declined</a></li>
					<li><a  href="blogList?status=published">Published</a></li>
				</c:if>	
				<c:if test="${status == 'published'}">
					<li><a  href="blogList?status=draft">Draft</a></li>
					<li><a  href="blogList?status=pending">Pending</a></li>
					<li><a  href="blogList?status=approved">Approved</a></li>
					<li><a  href="blogList?status=declined">Declined</a></li>
					<li  class="active"><a  href="blogList?status=published">Published</a></li>
				</c:if>	
				</ul>
				
	      
			  <div class="tab-content">
				<div class="tab-pane fade in active">
				
					 
				<div class="unclaimedT">	
				 <c:forEach var="blogList" items="${blogList}" varStatus="commentLoop">
				  
				  <div class="unclaimedR">
						<div class="uclaimedcell">
						
							<div class="btnBlog">
								<c:if test="${status == 'pending'}">
							<a href="updateStatus?status=approved&id=${blogList.blogId}" class="btn btn-warning"><i class="fa fa-thumbs-o-up"></i> Approved</a>
							<a data-toggle="modal" data-target="#declineBlog" class="btn btn-info declineblogs" onClick="idfun(${blogList.blogId})"><i class="fa fa-level-down"></i> Decline</a>
						</c:if> 
						<c:if test="${status == 'approved'}">
							<a href="updateStatus?status=published&id=${blogList.blogId}" class="btn btn-warning"><i class="fa fa-pencil"></i> Publish</a>
							<a data-toggle="modal" data-target="#declineBlog" class="btn btn-info"><i class="fa fa-level-down"></i> Decline</a>
						</c:if> 
						<c:if test="${status == 'declined'}">
							<a href="updateStatus?status=approved&id=${blogList.blogId}" class="btn btn-warning"><i class="fa fa-thumbs-o-up"></i> Approved</a>
							
						</c:if>
						
						<c:if test="${blogList.blogStatus == 'draft' }">
										       <a href="publishBlog?blogId=${blogList.blogId }" class="btn btn-primary"><i class="fa fa-upload"></i> Publish</a>
										</c:if> 
										<c:if test="${blogList.user.userType == 'A' && blogList.blogStatus != 'draft'}">
										       <a href="publishBlog?blogId=${blogList.blogId }" class="btn btn-warning"><i class="fa fa-pencil"></i> Edit</a>
										</c:if>  
										<a href="deleteBlog?blogId=${blogList.blogId}&status=${blogList.blogStatus}" class="btn btn-danger"><i class="fa fa-times"></i> Delete</a>
								        <a href="showBlog?blogId=${blogList.blogId}" class="btn btn-info"><i class="fa fa-arrow-circle-o-right"></i> Read More.</a>
						
							</div>
							
							<h2 class="blogh2">
									<a href="showBlog?blogId=${blogList.blogId}">${blogList.blogTitle}</a>
									
								</h2>
								<span class="blogpost">by <span>${blogList.user.firstName}  ${blogList.user.lastName}, </span>${blogList.getCreatedAt().getTime()}
								</span>
								<div class="heightset" id="blogDesc${commentLoop.index}">${blogList.blogDescription}</div>
								<c:forEach var="blogCategories" items="${blogCategories}" varStatus="i" >
								
										<c:if test="${blogList.blogId == blogCategories.key}">
											<div class="btnBlog1">
										<c:forEach var="catName"  items="${blogCategories.value}" varStatus="l">
										<a href="blogsByCategories?status=${blogList.blogStatus}&catId=${catName.categoryType.catId}">
   													<span class="btn btn-primary" style="cursor: default;"><i
													class="fa fa-tag"></i> ${catName.categoryType.catgName}</span>
													</a>
												</c:forEach>	
											</div>
										</c:if>         
  										    
										</c:forEach>
								<c:if test="${status == 'declined'}">
									  <span class="text-danger dec" style="float:left">Reason:- ${blogList.reason}</span>
							    </c:if>  
								<div class="btnBlog">  
										
								</div>
								
						</div>
						
						
				  </div>
				  
				
				  
				
				  
				  <!-- decline popup -->
				     <div class="modal fade" id="declineBlog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				        <div class="vertical-alignment-helper">
				        <div class="modal-dialog vertical-align-center">
				        <div class="modal-content">
				        <div class="modal-header">
				           <button type="button" class="close" data-dismiss="modal" style="font-size:40px;">&times;</button>
				           <h2 class="modal-title">Decline</h2>
        				</div>
				        <div class="modal-body">
				         <form action="	updateStatus">
           					<h2 class="text-danger" style="font-size:35px !important;margin:0 0 10px 0;">Give Us Reason why this is Decline </h2>
                                   <textarea class="nofixedtext" placeholder="type here..." name="reason" required = "required"></textarea>
                                   <input type="hidden" name="id" id= "blogId" value="">
                                   <input type="hidden" name="status" value="declined">
                                   <div class="clearfix"></div>
                                   <button type="submit" class="btn btn-success" style="padding: 5px 40px;font-size: 23px; margin-top:10px;" name="button" value="declined">Send</button>
           				 </form> 
                        </div>
				        </div>
				        </div>
				        </div>
     				</div>
      <!-- decline popup -->
      </c:forEach> 
      </div> 
				   <div class="row">
						<div class="col-md-12">
							<ul class="pagination pull-right">

         <c:choose>
          <c:when test="${totalPage==0}">
          </c:when>
          <c:otherwise>
           <c:choose>
            <c:when test="${currentPageNo=='1'}">
             <li><a class="disable"><i class="fa fa-caret-left"></i></a></li>
            </c:when>
            <c:otherwise>
             <li><a
              href="blogList?status=${status}&pageNo=${currentPageNo}&flag=left"
              class="nexpre"><i class="fa fa-caret-left"></i></a></li>
            </c:otherwise>

           </c:choose>
          </c:otherwise>
         </c:choose>




         <c:forEach begin="${startPage}" end="${endPage}" varStatus="p">
          <c:choose>
           <c:when test="${p.index==currentPageNo}">
            <li><a class="active">${p.index}</a></li>
           </c:when>
           <c:otherwise>
            <li><a
             href="blogList?status=${status}&pageNo=${p.index}&flag=current">${p.index}</a></li>
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
              href="blogList?status=${status}&pageNo=${currentPageNo}&flag=right"
              class="nexpre"><i class="fa fa-caret-right"></i></a></li>
            </c:otherwise>

           </c:choose>
          </c:otherwise>
         </c:choose>
        </ul>
						</div>
				   </div>
				  
				</div><!--// 1 //-->
				  
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
	function idfun(id)
	{
		//$('#declineBlog').attr("value",""+ id +"");
		$('#blogId').val(id);
	}
		/* $(document).ready(function(){
			$('.declineblogs').click(function(){
				var tab = 12;
				$('#declineBlog').attr("value",""+ tab +" ");
			})
		}); */
	</script>
	
    
  </body>
</html>