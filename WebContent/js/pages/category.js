	var catList = new Array();
	var catList1 = new Array();
	var catArr = new Array();
	var childIdByParentId = new Array();
	var originalCategory = new Array();
	var checkedItem = new Array();
	var uncheckedItem = new Array();
	$(document).ready(function(){
		
		$('#myModal').on('show.bs.modal', function (e) {
			checkedItem=[];
			uncheckedItem=[];
		});
		
		  for(var i=0; i<catJson.length;i++){
		  	  catArr.push(catJson[i].catgName);
		  }
		 
		  for(var i=0; i<catJson.length;i++){
				    catList.push({
				  	key:catJson[i].catgName,
				  	value:catJson[i].catId,
				    });
		  } 	
			  
	      for(var i=0; i<catJson.length;i++){
				    catList1.push({
				  	key:catJson[i].catId,
				  	value:catJson[i].catgName,
				    });
			  } 	

			var substringMatcher = function(strs) {
				  return function findMatches(q, cb) {
				    var matches, substringRegex;

				    // an array that will be populated with substring matches
				    matches = [];

				    // regex used to determine if a string contains the substring `q`
				    substrRegex = new RegExp(q, 'i');

				    // iterate through the pool of strings and for any string that
				    // contains the substring `q`, add it to the `matches` array
				    $.each(strs, function(i, str) {
				      if (substrRegex.test(str)) {
				        matches.push(str);
				      }
				    });

				    cb(matches);
				  };
				};

				 $('#the-basics .typeahead').typeahead({
				       hint: true,
				       highlight: true,
				       minLength: 1
				     },
				     {
				       name: 'states',
				       source: substringMatcher(catArr)
				     });
				 
				 
				  $(document).on('click','#the-basics .tt-selectable',function(){
					  var str = $("#smart_search").val();
					  var id = findCatValueFromKey(catList,str);
					  if($("#catId"+id).attr("checked")!="checked"){
					   originalCategory.push(id); 
					  $("#catId"+id).attr('checked', true);
					  $("#catId"+id).prop('checked', true);
					  var myElement = document.getElementById("catId"+originalCategory[i]);
					  html = '<span  id="catNameId'+id+'" >'+str+'<i class="icon" data-icon="y" onclick="onDelete('+id+')" ></i></span>';
					  $("#categoryNameId").append(html);
					  $("#smart_search").val('');
					  }
				    }); 
	});
	

	function findCatValueFromKey(catList,key){
		  for(var i in catList){
			  if(catList[i]['key'] == key){
				  return  catList[i]['value'];
			}
		  }
	}
	  
	  function onFindValue(catList,key){
		for(var i in catList){
			  if(catList[i]['key'] == key){
				  return  catList[i]['value'];
			}
		  }
	}
	  
	  
	 function findChildCatIdByParent(id){
		 childIdByParentId = [];
		 for(var i=0; i<catJson.length;i++){
			 if(catJson[i].parentId==id){
				 childIdByParentId.push(catJson[i].catId);
			 }
		 }
	 }  
	 
	 
	function checkChildIdChecked(){
		var j;
		for(var i=0;i<childIdByParentId.length;i++){
			  j = checkedItem.indexOf(childIdByParentId[i]);
			if(j!=-1){
				return true;
			}
		}
		return false;
	} 
	  
	
	function onSave(){
		var myElement;
		var html;
		for(var i =0;i<checkedItem.length;i++){
			 myElement = document.getElementById("catId"+checkedItem[i]);
			 html = '<span  id="catNameId'+checkedItem[i]+'" >'+onFindValue(catList1,myElement.value)+'<i class="icon" data-icon="y" onclick="onDelete('+checkedItem[i]+')" ></i></span>';
			 $("#catId"+checkedItem[i]).attr('checked', true);
			 originalCategory.push(checkedItem[i]);
			 $("#categoryNameId").append(html);
		}
		for(var i=0;i<uncheckedItem.length;i++){
			 $("#catId"+uncheckedItem[i]).attr('checked', false);
			 var j = originalCategory.indexOf(uncheckedItem[i]);
			 myElement = document.getElementById("catNameId"+originalCategory[j]);
			 myElement.remove();
				if(j != -1) {
					originalCategory.splice(j, 1);
				}
			
			 
		}
	}
	function onCancel(){
		for(var i =0;i<checkedItem.length;i++){
			 $("#catId"+checkedItem[i]).attr('checked', false);
			 $("#catId"+checkedItem[i]).prop('checked', false);
		}
		
		for(var i =0;i<uncheckedItem.length;i++){
			 $("#catId"+uncheckedItem[i]).attr('checked', true);
			 $("#catId"+uncheckedItem[i]).prop('checked', true);
		}
    }
	
	

	function onParentCatClick(id){
		 $("#categoriesL"+id).slideToggle();
	       $("#categoriesT"+id).css({"border-color":"#1c75bc","color":"#1c75bc",});
	       
	}

	function onChildCatClick(id){
		var myElement;
		myElement = document.getElementById("catId"+id);
		if($("#catId"+id).is(':checked')){
			checkedItem.push(id);
		}else{
			uncheckedItem.push(id);
		}
	}

	function onDelete(id){
		var	myElement = document.getElementById("catId"+id);
		$("#catId"+id).attr('checked', false);
		$("#catId"+id).prop('checked', false);	
		
		var	myElement1 = document.getElementById("catNameId"+id);
			myElement1.remove();
			$('#smart_search').val('');
	}
	
	  function highlightParentCatBorder(){
		   
		   for(var i=0;i<parentCatJson.length;i++){
			   $("#"+"categoriesT"+parentCatJson[i].catId).removeClass('active');
		   }
		   
		   var catsIds = getCheckedBoxes('categories');
		   var parentId;
		   for (var j=0; j<catsIds.length; j++) {
				 for(var i=0; i<catJson.length;i++){
					 if(catJson[i].catId==catsIds[j]){
						parentId = catJson[i].parentId;
					 }
				 }
				$("#"+"categoriesT"+parentId).addClass('active');
		   }
	   }
	  
	  