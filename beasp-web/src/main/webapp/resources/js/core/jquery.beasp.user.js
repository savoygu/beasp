(function($){
	
	/** 鼠标移入移出事件 **/
	$.fn.mouseEvent = function(opts) {
		var settings = $.extend({
			overLeaveClz:"mouseOverLeaveClass",
			downClz:"mouseDownClass",
			isOverLeaveDown:true,
			isFocusBlur:false
		}, opts||{});
		if(settings.isOverLeaveDown) {
			this.on({
				mouseover : function() {
					$(this).addClass(settings.overLeaveClz);
				},
				mouseleave : function() {
					$(this).removeClass(settings.overLeaveClz);
				},
				mousedown : function() {
					$(this).addClass(settings.downClz);
				}
			})
		}
		if(settings.isFocusBlur) {
			this.on({
				blur : function() {
					$(this).css({
						"border" : "1px solid #B9B9B9",
						"transition" : "all .3s ease-in-out 0s"
					});
				},
				focus : function() {
					$(this).css({
						"border" : "1px solid #FF318C",
						"transition" : "all .3s ease-in-out 0s"
					});
				}
			});
		}
	}
})(jQuery)