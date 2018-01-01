
$(document).ready(function(){
	
	var menuArray = ["file-upload","summary-report","accumulative-reports","download-sample","save"];
	
	$("."+menuArray[0]).addClass("active");
	for(var i=0; i<menuArray.length; i++){
		if(location.pathname.indexOf(menuArray[i])>-1){
			$(".menu").removeClass("active");
			$("."+menuArray[i]).addClass("active");
		}
	}
	
});