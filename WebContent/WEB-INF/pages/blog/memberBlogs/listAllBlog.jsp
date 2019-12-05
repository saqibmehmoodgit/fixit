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
		$(".dec").each(function(i) {
			len = $(this).text().length;
			if (len > 80) {
				$(this).text($(this).text().substr(0, 40) + '...');
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
	$(document).ready(function() {

		var count = ${blogListSize};
		for ( var i = 0; i < count; i++) {
			var id = 'blogDesc' + i;
			var blogDesc = $("#" + id).find('img').remove();
			console.log("blogDesc" + blogDesc);
		}
	});
</script>

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
					<div class="blogmain">


						<c:forEach var="blogList" items="${blogList}"
							varStatus="commentLoop">
							<div class="blog">
								<div class="unclatbn text-right">
								
									<c:if test="${blogList.blogStatus == 'draft' }">
										<a href="publishBlog?blogId=${blogList.blogId }"
											class="btn btn-warning"><i class="fa fa-pencil"></i>
											Publish</a>
									</c:if>
									
									<c:if test="${blogList.blogStatus == 'pending' || blogList.blogStatus == 'published' || blogList.blogStatus == 'declined' || blogList.blogStatus == 'approved'}">
										<a href="publishBlog?blogId=${blogList.blogId }"
											class="btn btn-warning"><i class="fa fa-pencil"></i> Edit</a>
									</c:if>
									<a href="deleteBlog?blogId=${blogList.blogId}"
										class="btn btn-danger"><i class="fa fa-times"></i> Delete</a>
									<a href="showBlog?blogId=${blogList.blogId}"
										class="btn btn-info"><i class="fa fa-arrow-circle-o-right"></i>
										Read More.</a>
								</div>
								<div class="blogBox">

									<h2 class="blogh2">
										<a href="showBlog?blogId=${blogList.blogId}">${blogList.blogTitle}</a>
										<c:if test="${blogList.blogStatus != 'Declined'}">

										</c:if>
										<span class="DPbtn text-warning">${blogList.blogStatus}</span>

									</h2>
									<span class="blogpost">by <span>${myUser.firstName} ${myUser.lastName},</span>${blogList.getCreatedAt().getTime()}
									</span>
									<div class="heightset" id="blogDesc${commentLoop.index}">${blogList.blogDescription}</div>
									<c:forEach var="blogCategories" items="${blogCategories}"
										varStatus="i">

										<c:if test="${blogList.blogId == blogCategories.key}">
										
										<div class="btnBlog1">
											<c:forEach var="catName" items="${blogCategories.value}"
												varStatus="l">
												
												<a href="blogsByCategories?catId=${catName.categoryType.catId}">
												<span class="btn btn-primary" style="cursor: default;"><i
													class="fa fa-tag"></i> ${catName.categoryType.catgName}</span>
												</a>

											</c:forEach>
										</div>
										</c:if>

									</c:forEach>
									<c:if test="${blogList.blogStatus == 'declined'}">
										<span class="text-danger dec" style="float: left">Reason:-
											${blogList.reason}</span>
									</c:if>
									<div class="btnBlog">
										<%-- <c:if test="${blogList.blogStatus == 'Draft' }">
									 <a href="" class="btn btn-warning"><i class="fa fa-pencil-square-o"></i> Draft</a>
								</c:if> --%>

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
														href="../member/blogList?pageNo=${currentPageNo}&flag=left"
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
													href="../member/blogList?pageNo=${p.index}&flag=current">${p.index}</a></li>
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
														href="../member/blogList?pageNo=${currentPageNo}&flag=right"
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
</body>
