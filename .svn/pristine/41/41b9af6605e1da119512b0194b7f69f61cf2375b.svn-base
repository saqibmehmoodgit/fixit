<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <head>
   <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">// <![CDATA[
	$(function(){
	  $(".your-div").each(function(i){
		len=$(this).text().length;
		if(len>80)
		{
		  $(this).text($(this).text().substr(0,200)+'...');
		}
	  });
	});
	// ]]></script>
	<script src="js/script.js"></script>
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
					<h1 class="animated fadeInDown">Search a Fixer</h1>
					
				</div>
			</div>
			<br>
			
			<div class="row">
				<div class="col-md-1 col-sm-1"></div>
				<div class="col-md-10 col-sm-10">
					
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#searchfixer">Search Fixer</a></li>
					<li><a data-toggle="tab" href="#favouritefixer">Favourite Fixer</a></li>
					
					
				</ul>

			  <div class="tab-content">
			<!--  -->	<div id="searchfixer" class="tab-pane fade in active">
					<div class="row">
						<div class="col-md-4 col-sm-4">
							<div class="favsearch">
								<input type="search" id="searchId" placeholder="Search for fixer" onkeyup="searchEnter(this)">
								<i class="fa fa-search"></i>
								<i class="fa fa-times-circle"></i>
							</div>
						
						</div>
						<div class="col-md-4 col-sm-4">
							<select class="select" id="catSelection" onchange="searchEnter(searchId)">
								<option>Filter By Category</option>
								<c:forEach var="category" items="${allCategory}">
								<option value="${category.catId}">${category.catgName}</option>
								</c:forEach>
							</select>
						</div>
					 <div class="col-md-4 col-sm-4">
							<ul class="pagination pull-right">
							  <li><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>
							  <li><a href="#">2</a></li>
							  <li><a href="#">3</a></li>
							  <li class="disabled"><a href="#">4</a></li>
							  <li><a href="#" class="active"><i class="fa fa-caret-right"></i></a></li>
							</ul>
						</div> 
				   </div>
				   
				   <div id="searchFixerList"></div>
                            </div><!--// 1 //-->
				
				
				
				<div id="favouritefixer" class="tab-pane fade">
					<div class="row">
						
					
						<div class="col-md-12 col-sm-12">
							<ul class="pagination pull-right">
							  <li><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>
							  <li><a href="#">2</a></li>
							  <li><a href="#">3</a></li>
							  <li class="disabled"><a href="#">4</a></li>
							  <li><a href="#" class="active"><i class="fa fa-caret-right"></i></a></li>
							</ul>
						</div>
				   </div>
				
				 <!--  <div class="search-favourite-fixer">
						<div class="uclaimedcell">
							<h2>
								<img src="images/member01.jpg" style="display:inline-block; border-radius:50%;">
								<name>John Doe</name>
							
							<p class="your-div">
							After working for 3 Fortune 500 Companies, I have experienced almost every accounting issue in SAP. I started as financial manager, then financial director and finally VP of finance. Now that I'm retired, I would like to help others with their SAP issues. I am available during normal business hours on Eastern Time. After working for 3 Fortune 500 Companies, I have experienced almost every accounting issue in SAP. I started as financial manager, then financial director and finally VP of finance. Now that I'm retired, I would like to help others with their SAP issues. I am available during normal business hours on Eastern Time. After working for 3 Fortune 500 Companies, I have experienced almost every accounting issue in SAP. I started as financial manager, then financial director and finally VP of finance. Now that I'm retired, I would like to help others with their SAP issues. I am available during normal business hours on Eastern Time.</p>
							
							
							</h2>
							<div class="unclatbn">
								<h2>Categories:</h2>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								
							</div>
						</div>&nbsp;&nbsp;
						
						<a href="#" class="uclaimedcell"><div>UnFavourite</div></a>
				  </div> -->
				  
				  <div class="search-favourite-fixer">
						<div class="uclaimedcell">
							<h2>
								<img src="images/member01.jpg" style="display:inline-block; border-radius:50%;">
								<name>John Doe</name>
							
							<p class="your-div">
							After working for 3 Fortune 500 Companies, I have experienced almost every accounting issue in SAP. I started as financial manager, then financial director and finally VP of finance. Now that I'm retired, I would like to help others with their SAP issues. I am available during normal business hours on Eastern Time. After working for 3 Fortune 500 Companies, I have experienced almost every accounting issue in SAP. I started as financial manager, then financial director and finally VP of finance. Now that I'm retired, I would like to help others with their SAP issues. I am available during normal business hours on Eastern Time. After working for 3 Fortune 500 Companies, I have experienced almost every accounting issue in SAP. I started as financial manager, then financial director and finally VP of finance. Now that I'm retired, I would like to help others with their SAP issues. I am available during normal business hours on Eastern Time.</p>
							
							
							</h2>
							<div class="unclatbn">
								<h2>Categories:</h2>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								<div class="btn btn-warning">Download</div>
								
							</div>
						</div>&nbsp;&nbsp;
						
						<a href="#" class="uclaimedcell"><div>UnFavourite</div></a>
				  </div>
				  <div class="row">
						
					
						<div class="col-md-12 col-sm-12">
							<ul class="pagination pull-right">
							  <li><a href="#" class="active"><i class="fa fa-caret-left"></i></a></li>
							  <li><a href="#">2</a></li>
							  <li><a href="#">3</a></li>
							  <li class="disabled"><a href="#">4</a></li>
							  <li><a href="#" class="active"><i class="fa fa-caret-right"></i></a></li>
							</ul>
						</div>
				   </div>
				  
				</div><!--// 2 //-->
				 </div>
				</div>
				<div class="col-md-1 col-sm-1"></div>
			</div>
				
		</div>
	</section>
	<!-- price close -->
	<script>
	function searchEnter(ele) {
	
	   if(ele.value.length>=3){
			var categoryId= document.getElementById("catSelection").value;
		   $.ajax({
				method : "POST",
			    url : "${pageContext.request.contextPath}/member/fixerList",
			    data : {
			    searchFieldText: ele.value ,
			    catId: categoryId,
			    startIndex : -1,
			    maxResult :-1,
			   
			    }
			   }).done(function(response) {
				    var data = response.result.fixers;
				    console.log(data);
				  var html ='';
				    $("#searchFixerList").empty();
				    for(var i =0 ;data.length;i++){
				    	
				    	html =' <div class="search-favourite-fixer"><div class="uclaimedcell"><h2><img src="'+data[i].profilePhoto+'"  style="display:inline-block; border-radius:50%;"><name>'+data[i].userName+'</name><p class="your-div">'+data[i].overview+'</p></h2><div class="unclatbn"><h2>Categories:</h2><div class="btn btn-warning">Download</div></div></div>&nbsp;&nbsp;<a href="#" class="uclaimedcell"><div>Select for the Issue</div></a>&nbsp;&nbsp;<a href="#" class="uclaimedcell"><div>Fav/UnFavourite based on previous selection</div></a></div>';
				    	$("#searchFixerList").append(html);
				    }
					
			   });
	   } 
	}
	</script>
    
  </body>
