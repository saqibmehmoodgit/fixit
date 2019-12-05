<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
	<section class="member">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="animated fadeInDown">${blogById.blogTitle}</h1>
					
				</div>
			</div>
			<br>
			
			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
				<div class="blogmain">
					
				   
					
					
					
					<div class="blog bordernone">
						<span class="blogpost">by <span>${blogById.user.firstName}  ${blogById.user.lastName},</span>${blogById.getCreatedAt().getTime()} </span>
							 
							<p>
							${blogById.blogDescription}
							</p>
							
						<c:if test="${blogById.blogStatus == 'Declined'}">
									  <span class="text-danger dec">Reason:- ${blogById.reason}</span>
						</c:if> 		
							
							<div class="btnBlog1">
											<c:forEach var="catName"  items="${blogCategories}" >
   								<span class="btn btn-primary" style="cursor:default;"><i class="fa fa-tag"></i> ${catName.categoryType.catgName}</span>
							</c:forEach>
										</div>
						
					</div>
					
					
					
					
				
				
				<div>
				</div>
				<div class="col-md-1 col-sm-1"></div>
			</div>
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		</div>
	</section>
	<!-- price close -->
	
	
    <script>
 
</script>
  </body>
</html>