<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>



<script type="text/javascript">
	// <![CDATA[
	$(function() {
		$(".blogp").each(function(i) {
			len = $(this).text().length;
			if (len > 80) {
				$(this).text($(this).text().substr(0, 200) + '...');
			}
		});
		/*$(".blogh2").each(function(i){
		len=$(this).text().length;
		if(len>80)
		{
		  $(this).text($(this).text().substr(0,50)+'...');
		}
		});*/
	});
	// ]]>
$(document).ready(function(){
	
     var count = ${blogListSize};
	 for(var i=0; i< count ; i++){
     var id = 'blogDesc'+i;
	 var blogDesc = $("#"+id).find('img').remove();
	 console.log("blogDesc" + blogDesc ); 
	 }
});	
	
</script>

</head>
<body>
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
					<div class="blogmain">
						
						<c:forEach var="blogList" items="${blogList}"
							varStatus="commentLoop">

							<div class="blog">
							<div class="unclatbn text-right">
                            	 <a href="showBlog?blogId=${blogList.blogId}" class="btn btn-info  pull-right"><i class="fa fa-arrow-circle-o-right"></i> Read More.</a>
                            </div>
							
								<div class="blogBox1">

									<h2 class="blogh2">
										<a href="showBlog?blogId=${blogList.blogId}">${blogList.blogTitle}</a>
									</h2>
									 <span class="blogpost">by <span>${blogList.user.firstName}  ${blogList.user.lastName},</span>${blogList.getCreatedAt().getTime()}</span> 
									<div class="heightset" id="blogDesc${commentLoop.index}" >${blogList.blogDescription}</div>
										<div class="btnBlog1">
										<c:forEach var="blogCategories" items="${blogCategories}" varStatus="i" >
									
										<c:if test="${blogList.blogId == blogCategories.key}">
											
										<c:forEach var="catName"  items="${blogCategories.value}" varStatus="l">
   											<a href="blogListByCategories?catId=${catName.categoryType.catId}">
   													<span class="btn btn-primary" style="cursor:default;"><i class="fa fa-tag"></i> ${catName.categoryType.catgName}</span>
											</a>
												</c:forEach>	
											
										</c:if>         
  										    
										</c:forEach>
										<%-- <c:if test="${blogList.blogStatus == 'Draft' }">
											 <a href="" class="btn btn-warning"><i class="fa fa-pencil-square-o"></i> Draft</a>
										</c:if> --%>        
										        
										        <div class="clearfix"></div>
										</div>
								   </div>
							</div>
						</c:forEach>



						<div class="row">
							<div class="col-md-12 col-sm-12">
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
              href="blogList?pageNo=${currentPageNo}&flag=left"
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
             href="blogList?pageNo=${p.index}&flag=current">${p.index}</a></li>
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
              href="blogList?pageNo=${currentPageNo}&flag=right"
              class="nexpre"><i class="fa fa-caret-right"></i></a></li>
            </c:otherwise>

           </c:choose>
          </c:otherwise>
         </c:choose>
        </ul>
							</div>

						</div>


						<div></div>
						<div class="col-md-1 col-sm-1"></div>
					</div>
				</div>
				</div>
				</div>
				</section>
<script>
 
</script>

</body>
