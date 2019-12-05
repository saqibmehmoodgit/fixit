$(document).ready(function(){
	
	/* My Industries */
	 $("#AddNew").click(function(){
	        $("#MyIndustries").fadeIn();
	        window.scrollTo(0, 0);
	    });
	 $("#MIClose").click(function(){
	        $("#MyIndustries").fadeOut();
	    });
	 $("#savechange").click(function(){
	        $("#MyIndustries").fadeOut();
	    });
	
	 /* industries and categories */
	 $(".my-categories .headC").click(function(){
	        $(".my-categories .categoriesDiv").slideToggle();
			  $(".my-industries .categoriesDiv").slideToggle();
			  
			  $(".my-industries").css("background","#f1f2f2");
			  $(".my-categories").css("background","#d1d3d4");
			  
			  $(".my-categories .headC i").attr('data-icon','F');
			  $(".my-industries .headC i").attr('data-icon','I');
		    });
		 
		 $(".my-industries .headC").click(function(){
		      $(".my-industries .categoriesDiv").slideToggle();
			  $(".my-categories .categoriesDiv").slideToggle();
			  
			  $(".my-industries").css("background","#d1d3d4");
			  $(".my-categories").css("background","#f1f2f2");
			  $(".my-categories .headC i").attr('data-icon','I');
			  $(".my-industries .headC i").attr('data-icon','F');
	    });
	
	$("#filter").click(function(){
        $("#ParentCategory").slideDown();
        $("#SearchIndustry").slideDown();
        $("#SearchRating").slideDown();
		$("#SearchCuntry").slideDown();
		$("#filter").css({
			"display" : "none"
		});
		$("#ClearFilter").css({
			"display" : "block"
		});
	});
	$("#ClearFilter").click(function() {
		$("#ParentCategory").slideUp();
		$("#SearchIndustry").slideUp();
		$("#SearchRating").slideUp();
		$("#SearchCuntry").slideUp();
		$("#filter").css({
			"display" : "block"
		});
		$("#ClearFilter").css({
			"display" : "none"
		});
	});

	$(".clickEdit").click(function() {
		$(".CDelete").show();
		$(".clickEdit").hide();
	});

	$(".newmessage").click(function() {
		$(".messageChat").fadeIn("slow");
	});
	$(".closeMessage").click(function(){
		$(".messageChat").fadeOut("slow");
	});
	
	
	$(".mMneu").click(function(){
        $("aside").css("right", "0");
        $(".overlay-menu").show();
    });
	$(".menuCL").click(function(){
        $("aside").css("right", "-370px");
        $(".overlay-menu").hide();
    });

	/*$(document).on('click','.categoriesT',function(){
		$('.categoriesList').slideUp();
	    $(this).next('.categoriesList').slideToggle();
	     
	     return false;
	});*/
	
	
	var $container = $('.ChooseCategories li'),
	$trigger = $('.categoriesT');	 
		$('.modal-footer button').click(function(e){
			$(".categoriesList").css({"display":"none"});
		});
		$trigger.on('click', function() {
		var $this = $(this).siblings('.categoriesList');
		// Hide all dropdowns
		$container.find('.categoriesList').stop().slideUp();
		// Check if dropdown is visible
		// true - hide dropdown
		// false - show dropdown
		if ($this.is(':visible')) {
			$this.slideUp();
			highlightParentCatBorder();
		} else {
			$this.delay(500).stop().slideDown();
			highlightParentCatBorder();
		}
	});
	
	
	/*$(".ChooseCategories .categoriesT").click(function(){
		$(this).next(".ChooseCategories .categoriesList").slideToggle("slow")
		.siblings(".ChooseCategories .categoriesList:visible").slideUp("slow");
		
	});*/
	
	
	
	
	
	/*$(".ChooseCategories li").click(function(){
		$(".categoriesT").toggleClass("categoriesT1"); 
	});*/
}); 







  
function createCatView(){
	for(var i =0;i<updatedCategory.lenght;i++){
		console.log(updatedCategory[i]);
	}
	$("#AddNew").click(function(){
        $("#MyIndustries").fadeIn();
    });
 $("#MIClose").click(function(){
        $("#MyIndustries").fadeOut();
    });
 
 /* Hour Packs */
 $(".gotoreq").click(function(){
        $(".HourPacks").fadeIn();
    });
 $(".cancel").click(function(){
		$('.hp-cell').css('opacity','1');

        $(".HourPacks").fadeOut();
    });
}

function leftCredirBtnPressed(remainingCredits, currentValue) {
	var hoursShown = $('#hours').val();
	
	/*if(remainingCredits == 0)
	{
	$("#hoursAlert").css({
		"display" : "block"
	});
	$("#hoursAlert").text('You have 0 credit remaining');
	return false;
	}else{
		$("#hoursAlert").css({
			"display" : "none"
		});
	}*/
	
	hoursShown = hoursShown - 1;
	if (hoursShown > 0) {

		$('#hours').val(hoursShown);
		addhoursDateTime(-12);

	}
}

function rightCredirBtnPressed(remainingCredits, initalCredits) {
	/*if(remainingCredits == 0)
		{
		$("#hoursAlert").css({
			"display" : "block"
		});
		$("#hoursAlert").text('You have 0 credit remaining');
		return false;
		}else{
			$("#hoursAlert").css({
				"display" : "none"
			});
		}*/
	
var hoursShown = $('#hours').val();
/*		if ((parseInt(hoursShown) + 1) <= parseInt(remainingCredits))

	{
		$('#hours').val(parseInt(hoursShown) + 1);
		addhoursDateTime(12);
	}*/
	$('#hours').val(parseInt(hoursShown) + 1);
	addhoursDateTime(12);
}
// Add hours
function addHours(){
$(".gotoreq").click(function(){
    $(".HourPacks").fadeIn();
});
$(".cancel").click(function(){
	$('.hp-cell').css('opacity','1');
    $(".HourPacks").fadeOut();
});

}
/*
 * function makeAllFixerHeadersInActive() {
 * 
 * $("#dashboard").removeClass('active'); $("#unclaimed").removeClass('active');
 * $("#applied").removeClass('active'); $("#inprogress").removeClass('active');
 * $("#closed").removeClass('active');
 *  }
 */
