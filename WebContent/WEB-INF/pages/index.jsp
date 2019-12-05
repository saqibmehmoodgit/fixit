<%@ page contentType="text/html; charset=UTF-8" %>
<title>ERPfixers | Home</title>

<body>
	<script>
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});

		$(document).ready(function() {
			$('iframe').each(function() {
				var url = $(this).attr("src");
				$(this).attr("src", url + "?wmode=transparent");
			});
		});
	</script>


	<a name="banner" id="banner"></a>
	<div class="banner banner_without_login">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="bannertxt">
						<h1 class="animated fadeInDown">
							You Ask. <span>We Fix.</span>
						</h1>

						<h2 class="animated fadeInUp">Get quality SAP Expertise on
							Demand</h2>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6 col-sm-6 col-xs-6">
					<a href="${pageContext.request.contextPath}/memberSignUp?flag=true"
						class="yellowbtn pull-right red-tooltip" data-toggle="tooltip"
						data-placement="bottom"
						title="A Member is an SAP customer that has requests and issues to resolve.">Become
						a Member</a>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6">
					<a href="${pageContext.request.contextPath}/fixerSignUp"
						class="bluebtn red-tooltip" data-toggle="tooltip"
						data-placement="bottom"
						title="A Fixer is an SAP expert that can resolve issues and deal with requests.">Become
						a Fixer</a>
				</div>
			</div>
		</div>
	</div>


	<!-- how it work -->

	<a name="how-work" id="how-work"></a>
	<section class="how-work">
		<div class="container">
			<div class="row">
				<div class="new_section">
					<h1 class="animated wow fadeInDown" data-wow-delay="0.3s">You Ask. We Fix.</h1>
					<p>ERPfixers brings you SAP micro-consulting in one intuitive platform. Post a request, match with a talented system expert and outsource your work with no commitment. Here it's easy to find the SAP help you're looking for.</p>
				</div>
			</div><hr>
			
			<div class="row">
				<div class="new_section1">
					<div class="col-md-5">
						<img src="images/fixit_images/download.png"  class="img-responsive"/>
					</div>
					<div class="col-md-7">
						<h3>WE COMBINE THE BEST OF WHAT YOU NEED. </h3>
						<h4>RIGHT NOW</h4>
						<p>We recognize that you, the SAP customer, needs help in a system that
						 often does not work for you. SAP forums don’t guarantee answers, 
						 and consulting firms can be too much of a commitment of both your time and money.
						  We believe that ERPfixers offers the best of both worlds - guaranteed rapid responses from
						   System Experts tailored to your specific needs and no contract commitment!</p>
						   
						   <div class=btn_panel>
						   		<div class="find_fixer">find a fixer</div>
						   		<div class="clear"></div>
						   		<p>Got skills? <a href="#">Become a Fixer.</a></p>
						   </div>
						   
						   
					</div>
				</div>
			</div>
			
			<%-- <div class="row">
				<div class="col-md-12 wow fadeInUp" data-wow-delay="0.6s">
					<ul class="nav nav-tabs animated fadeIn">
						<li class="active"><a data-toggle="tab" href="#Members">Members</a></li>
						<li><a data-toggle="tab" href="#Fixers">Fixers</a></li>
					</ul>
					<div class="tab-content">
						<div id="Members" class="tab-pane fade in active">
							<div class="row">
								<div class="col-md-3 col-sm-3"></div>
								<div class="col-md-6 col-sm-6">
									<video width="100%" controls>
										<source src="${memberVideoUrl}" type="video/mp4">
										<source src="${memberVideoOggUrl}" type="video/ogg">
									</video>
									<!-- 	<iframe width="100%" height="300"
											src="https://www.youtube.com/embed/USYRQul0G9s"
											frameborder="0" allowfullscreen></iframe> -->
								</div>
								<div class="col-md-3 col-sm-3"></div>
							</div>
							<br>

							<div class="row">
								<div class="col-md-4">
									<div class="tabcont animated fadeIn">
										<span class="spanicon"><img src="images/submit.png"
											alt=""></span>
										<h3>1. Submit a request</h3>
										<p>Virtual freelancers, aka "fixers," provide
											micro-consulting services for your SAP software, without the
											need for lengthy, high-cost consulting contracts and their
											often protracted solutions. Tasks are assigned to an expert
											consultant who will guide you through to solution for your
											issue.</p>
									</div>
								</div>
								<div class="col-md-4 ">
									<div class="tabcont animated fadeIn">
										<span class="spanicon"><img src="images/relex.png"
											alt=""></span>
										<h3>2. Relax while your fixer does the heavy lifting</h3>
										<p>Increase your work productivity and company's value.
											Save the time that you would possibly use investigating
											solutions to SAP issues. An ERPfixers consultant will be
											assigned to your issue within 24 hours.</p>
									</div>

								</div>
								<div class="col-md-4">
									<div class="tabcont animated fadeIn">
										<span class="spanicon"><img src="images/review.png"
											alt=""></span>
										<h3>3. Issue Fixed. Time and money saved</h3>
										<p>Our system is accessible on any device that you use for
											work. We aim to get the fix right the first time. In the
											unlikely scenario where you aren't completely satisfied with
											the solution, choose to open it up immediately to an
											additional fixer, at no further cost to you.</p>
									</div>
								</div>
							</div>

						</div>

						<div id="Fixers" class="tab-pane fade">
							<div class="row">
								<div class="col-md-3 col-sm-3"></div>
								<div class="col-md-6 col-sm-6">
									<!-- 	<iframe width="100%" height="300"
											src="https://www.youtube.com/embed/m9DkIdaNVJs"
											frameborder="0" allowfullscreen></iframe> -->
									<video width="100%" controls>
										<source src="${fixerVideoUrl}" type="video/mp4">
										<source src="${fixerVideoOggUrl}" type="video/ogg">
									</video>
								</div>
								<div class="col-md-3 col-sm-3"></div>
							</div>
							<br>



							<div class="row">
								<div class="col-md-4">
									<div class="tabcont animated fadeIn">
										<span class="spanicon"><img src="images/sign.png"
											alt=""></span>
										<h3>1.Sign up as a Fixer in your expert categories</h3>
										<p>Select categories that you are confident in providing
											support, training and optimization services</p>
										<p>Showcase your skills accurately, and make yourself more
											selectable to the members</p>
									</div>
								</div>
								<div class="col-md-4 ">
									<div class="tabcont animated fadeIn">
										<span class="spanicon"><img src="images/submit.png"
											alt=""></span>
										<h3>2.Claim a request that relates to your expertise ASAP</h3>
										<p>A fixer needs to be assigned to a request within 24
											hours.</p>
										<p>Set up your account to receive email and/or text alerts
											to get immediate notification when an issue in your expertise
											area is reported.</p>
									</div>
								</div>
								<div class="col-md-4">
									<div class="tabcont animated fadeIn">
										<span class="spanicon"><img src="images/solve.png"
											alt=""></span>
										<h3>3.Solve issues and get paid for each one</h3>
										<p>Use your expert skills to solve the issue in an
											accurate and expedient manner</p>
										<p>Get paid for every issue that a member marks as fixed</p>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div> --%>
		</div>
	</section>
	
	<div class="div_panel">
		<div class="new_image_div">				
		</div>
		<div class="container">
			<h1>"Finally, a service that provides definitive answers to our SAP pain-points. 
			ERPfixers definitely gets a thumbs up from me."</h1>		
			<h3>TIM S., UK - MEMBER</h3>
			
		</div>
		
	</div>
	<!-- how it work close -->


	<!-- testimonials -->
	<a name="testimonials" id="testimonials"></a>
	<section class="testimonials">
		<div class="container">
			<div class="testimonials_div">
				<div class="row">
					<div class="col-3">dsfdsfds</div>
					<div class="col-9">dsfdsfds</div>
				</div>
				
			</div>
		
			<!-- <div class="row">
				<h1 class="animated wow fadeInDown" data-wow-delay="0.3s">Testimonials</h1>
			</div> -->
			<!-- <div class="row">
				<div class="col-md-12 wow fadeInUp" data-wow-delay="0.6s">
					<ul class="nav nav-tabs animated fadeIn">
						<li class="active"><a data-toggle="tab" href="#Memberstest">Members</a></li>
						<li><a data-toggle="tab" href="#Fixerstest">Fixers</a></li>
					</ul>
					<div class="tab-content">
						<div id="Memberstest" class="tab-pane fade in active">
							<div id="carousel-example" class="carousel slide"
								data-ride="carousel">
								<div class="carousel-inner">
									<div class="item active">
										<div class="row">
											<div class="col-md-4 ">
												<div class="fixertimonial1">
													<div class="media">
														<div class="pull-left">
															<img alt=""
																src="images/Member_Leicester_United_Kingdom.png"
																class="media-object" />
														</div>
														<div class="media-body">
															<h5 class="media-heading">
																Leicester, UK - <span>Member</span>
															</h5>
															<p>Finally, a service that provides definitive
																answers to our SAP pain-points. ERPfixers definitely
																gets a thumbs up from me.</p>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="fixertimonial1">
													<div class="media">
														<div class="pull-left">
															<img alt="" src="images/Member_Montreal_Canada.png"
																class="media-object" />
														</div>
														<div class="media-body">
															<h5 class="media-heading">
																Montreal, Canada - <span>Member</span>
															</h5>
															<p>The skilled experts on this website allow my
																company internal team to focus more on the analytics
																that will help grow the business, rather than the
																niggling system issues that crop up from time to time.</p>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="fixertimonial1">
													<div class="media">
														<div class="pull-left">
															<img alt="" src="images/Member_Michigan_USA.png"
																class="media-object" />
														</div>
														<div class="media-body">
															<h5 class="media-heading">
																Michigan, USA - <span>Member</span>
															</h5>
															<p>I enjoy the easy-to-use format of the application,
																and appreciate the speedy responses of the Fixers.</p>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									//1//

									INDICATORS
									<ol class="carousel-indicators carousel-indicators-set">
                                 <li data-target="#carousel-example" data-slide-to="0" class="active"></li>
                                 <li data-target="#carousel-example" data-slide-to="1"></li>
                                 </ol>
								</div>
							</div>
						</div>
						<div id="Fixerstest" class="tab-pane fade">
							<div id="carousel-example" class="carousel slide"
								data-ride="carousel">
								<div class="carousel-inner">
									<div class="item active">
										<div class="row">
											<div class="col-md-4">
												<div class="customertestimonial1">
													<div class="media">
														<div class="pull-left">
															<img alt="" src="images/Fixer_Hyderabad.png"
																class="media-object" />
														</div>
														<div class="media-body">
															<h5 class="media-heading">
																Hyderabad, India - <span>Fixer</span>
															</h5>
															<p>A great way to earn extra income while utilizing
																your SAP skills at the same time.</p>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="customertestimonial1">
													<div class="media">
														<div class="pull-left">
															<img alt="" src="images/Fixer_Pennsylvania_USA.png"
																class="media-object" />
														</div>
														<div class="media-body">
															<h5 class="media-heading">
																Pennsylvania, USA - <span>Fixer</span>
															</h5>
															<p>I like the ratings system of the ERPfixers
																platform. It motivates me to resolve the Members issues
																as quickly and accurately as I can.</p>
														</div>
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="customertestimonial1">
													<div class="media">
														<div class="pull-left">
															<img alt="" src="images/Fixer_Sao_Paulo_Brazil.png"
																class="media-object" />
														</div>
														<div class="media-body">
															<h5 class="media-heading">
																Sao Paulo, Brazil - <span>Fixer</span>
															</h5>
															<p>It feels good to have the excitement and buzz that
																SAP consultants get, while expanding my skills beyond my
																normal job.</p>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									//1//

								</div>
							</div>
						</div>
					</div>
				</div>
			</div> -->
		</div>
		<div class="container"></div>
	</section>
	<!-- testimonials close -->



	<!-- about -->
	
	<section class="about" style="border-bottom: 1px dotted #c3c3c3;">
		<div class="container-fluid" style="padding: 0px;">
			<div class="row">
				<!-- 	<div class="col-md-3 col-sm-3"></div> -->
				<div class="col-md-12 col-sm-12">
					<img src="images/benifit.jpg" />
					<!-- <h1 class="animated  wow fadeInDown" data-wow-delay="0.3s">Benefits of Being a Member</h1>
						<p class="animated wow fadeInUp" data-wow-delay="0.6s">&nbsp;</p>
						<p class="animated wow fadeInUp" data-wow-delay="0.6s">1. Fixers will respond to your request within 24 hours.</p>
						<p class="animated wow fadeInUp" data-wow-delay="0.6s">2. Get quality SAP consulting at a fraction of the cost.</p>
						<p class="animated wow fadeInUp" data-wow-delay="0.6s">3. Effective user ratings system ensures your choice of a top Fixer.</p>
						<p class="animated wow fadeInUp" data-wow-delay="0.6s">4. The interface is simple and easy to use. </p>
						<p class="animated wow fadeInUp" data-wow-delay="0.6s">5. Allows you to focus on other business priorities.</p> -->
				</div>
				<!-- 	<div class="col-md-3 col-sm-3"></div> -->
			</div>
		</div>
	</section>
	<!-- about close -->


	<!-- about -->
	<a name="about" id="about"></a>
	<section class="about">
		<div class="container">
			<div class="row">
				<div class="col-md-3 col-sm-3"></div>
				<div class="col-md-6 col-sm-6">
					<h1 class="animated  wow fadeInDown" data-wow-delay="0.3s" style="margin-top:100px;">About
						Us</h1>
					<p class="animated wow fadeInUp" data-wow-delay="0.6s">ERP
						fixers is an online platform that quickly connects SAP customers
						with skilled and carefully-selected experts to find fixes for
						their system problems. ERPfixers provides quality à la carte
						consulting at your fingertips. Your satisfaction is our bottom
						line.</p>
					<div class="ceodetail wow fadeInRight" data-wow-delay="0.9s">
						<img alt="" src="images/paul-ceo.jpg" class="media-object" />
						CEO, Paul Ovigele
					</div>
				</div>
				<div class="col-md-3 col-sm-3"></div>
			</div>
		</div>
	</section>
	<!-- about close -->

	<!-- Modules page start  -->
	<section class="module_div">
		<div class="container">
			<div class="row">
				<div class="col-sm-12 indx_module">
					<h1 class="wow fadeInDown animated" data-wow-delay="0.3s"
						style="visibility: visible; animation-delay: 0.3s; animation-name: fadeInDown;">SAP
						Modules We Cover</h1>

					<div class="col-sm-3 cmmn_module_sectn">
						<div class="cmmn_module_blk">
							<div class="img_blk">
								<img src="images/financial.png" />
							</div>
							<h4>Financial Accounting</h4>
							<div class="hvr_div">
								<p>Accounts Payable</p>
								<p>Accounts Receivable</p>
								<p>Asset Accounting</p>
								<p>Bank Accounting</p>
								<p>and more...</p>
							
							</div>
						</div>
					</div>
					<!-- finacial Module div end here  -->
					<div class="col-sm-3 cmmn_module_sectn">
						<div class="cmmn_module_blk">
							<div class="img_blk">
								<img src="images/cotrolling.png" />
							</div>
							<h4>Controlling</h4>
							<div class="hvr_div">
								<p>Cost Center Accounting</p>
								<p>Overhead Cost Controlling</p>
								<p>Activity Based Costing</p>
								<p>Product Cost Controlling</p>
								<p>and more...</p>
							</div>
						</div>
					</div>
					<!-- Controlling  module div end here -->
					<div class="col-sm-3 cmmn_module_sectn">
						<div class="cmmn_module_blk">
							<div class="img_blk">
								<img src="images/basis.png" />
							</div>
							<h4>Basis</h4>
							<div class="hvr_div">
								<p>Security</p>
								<p>Application Link Enabling</p>
								<p>Remote Function Calls</p>
								<p>Object Linking and Embedding</p>
								<p>and more...</p>
							</div>
						</div>
					</div>
					<!-- Basis module div end here  -->
					<div class="col-sm-3 cmmn_module_sectn">
						<div class="cmmn_module_blk">
							<div class="img_blk">
								<img src="images/abap.png" />
							</div>
							<h4>ABAP</h4>
							<div class="hvr_div">
								<p>ABAP Workbench</p>
								<p>Menu Painter</p>
								<p>Screen Painter</p>
								<p>Data Dictionary</p>
								<p>and more...</p>
							</div>
						</div>
					</div>
					<!--  ABAP module div end here  -->
					<div class="col-sm-3 cmmn_module_sectn">
						<div class="cmmn_module_blk">
							<div class="img_blk">
								<img src="images/sales.png" />
							</div>
							<h4>Sales and Distribution</h4>
							<div class="hvr_div">
								<p>Sales Information System</p>
								<p>Billing</p>
								<p>Special Business</p>
								<p>Transactions</p>
								<p>Shipping</p>
								<p>and more...</p>
							</div>
						</div>
					</div>
					<!-- Sales module div end here  -->
					<div class="col-sm-3 cmmn_module_sectn">
						<div class="cmmn_module_blk">
							<div class="img_blk">
								<img src="images/human.png" />
							</div>
							<h4>Human Resources</h4>
							<div class="hvr_div">
								<p>Recruitment</p>
								<p>Personnel Administration</p>
								<p>Benefits Administration</p>
								<p>Payroll</p>
								<p>and more...</p>
							</div>
						</div>
					</div>
					<!-- Human module div end here  -->
					<div class="col-sm-3 cmmn_module_sectn">
						<div class="cmmn_module_blk">
							<div class="img_blk">
								<img src="images/materials.png" />
							</div>
							<h4>Materials Management</h4>
							<div class="hvr_div">
								<p>Purchasing</p>
								<p>Invoice Verification</p>
								<p>Logistics Information System</p>
								<p>Inventory Management</p>
								<p>and more...</p>
							</div>
						</div>
					</div>
					<!-- Materials module div end here  -->
					<div class="col-sm-3 cmmn_module_sectn">
						<div class="cmmn_module_blk">
							<div class="img_blk">
								<img src="images/production.png" />
							</div>
							<h4>Production Planning</h4>
							<div class="hvr_div">
								<p>Make to Order</p>
								<p>Repetitive Manufacturing</p>
								<p>PP for Process Industries</p>
								<p>Sales and Operations</p>
								<p>Planning</p>
								<p>and more...</p>
							</div>
						</div>
					</div>
					<!-- Production module div end here  -->
					<div class="sap_module_lnk">
						<a href="sapModules">View all modules covered </a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--  Modules page end here  -->





	<!-- contactus -->
	<a name="contact" id="contact"></a>
</body>