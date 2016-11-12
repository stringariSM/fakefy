$(document).ready(function(){
	var windowHeight = $(window).height();
	$('.sptBody').css('height', windowHeight);
	//$('.sptDefault').css('height', windowHeight);
	$('body').css('height', windowHeight);
		
	$(window).on('resize', function(){
		var windowHeight = $(window).height();
		$('.sptBody').css('height', windowHeight);
		//$('.sptDefault').css('height', windowHeight);
		$('body').css('height', windowHeight);
	});
});