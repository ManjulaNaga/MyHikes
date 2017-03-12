$(document).ready(function(){
	$("#image_rollover img").each(function(){
		var oldurl = $(this).attr("src");
		var newurl = $(this).attr("id");
		var rolloverImage = new Image(); 
		rolloverImage.src = newurl;
		
		$(this).hover(
			function(){
				$(this).attr("src",newurl);
				},
			function(){
				$(this).attr("src",oldurl);
				}
			); //end hover
		}); //end each
	}); //ens ready
	

