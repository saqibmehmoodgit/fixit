

$(document).ready(function(){
	 $('.mainav li a[href^="#"]').on('click',function (e) {
	     e.preventDefault();

	     var target = this.hash;
	     var $target = $(target);

	     $('html, body').stop().animate({
	         'scrollTop': $target.offset().top
	     }, 900, 'swing', function () {
	         window.location.hash = target;
	     });
		 
		 
		
		 
});
	
	
	//mobile menu
	$(".mmneu").click(function(){
		$(".navigation").slideToggle("slow");
		jQuery.noConflicts;
	});
	// $(".navigation ul li a").click(function(){
		// $(".navigation").fadeOut("slow");
	// });
	
	/*$('#carousel-example').carousel({
		interval: 5000 //TIME IN MILLI SECONDS
	});*/
	//navigation
	 $('.mainav li a').click(function() {
	   $(".mainav li a.active").removeClass("active");
	   $(this).addClass('active');
	 });
	
	 
	 
	 
	
});






