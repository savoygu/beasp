$(document).ready(function(){
	/**清空**/
	$(".btn-text-clear").on("click", function() {
		$(".J-suggest-input").val("");
	})
	/**搜索**/
	$(".btn-search").click(function() {
		var seo = $(".suggest-input").val();
		var pos = $("#currentPosition").text();
		if (seo.length >= 1) {
			window.location.href = "/book/search?words=" + seo + "&pos=" + pos;
		}
	});
	$(".suggest-input").bind('keypress', function(event) {
		if (event.keyCode == "13") {
			var seo = $(".suggest-input").val();
			var pos = $("#currentPosition").text();
			if (seo.length >= 1) {
				window.location.href = "/book/search?words=" + seo + "&pos=" + pos;
			}
		}
	});
})