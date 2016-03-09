/** 回到顶部 * */
$(function() {
	var timer = null;
	var isTop = true;

	$(window).scroll(function() {

		// 是否停止定时器
		if (!isTop) {
			clearInterval(timer);
		}

		isTop = false;

		if ($(this).scrollTop() > $(this).height()) {
			$("#back-to-top").css({
				right : "30px",
				transform : "rotate(-360deg)",
				"-moz-transform" : "rotate(-360deg)", /* Firefox 4 */
				"-webkit-transform" : "rotate(-360deg)", /* Safari and Chrome */
				"-o-transform" : "rotate(-360deg)" /* Opera */
			});
		} else {
			$("#back-to-top").css({
				"right" : "-40px",
				"transition-timing-function" : "ease-out",
				transform : "rotate(0deg)",
				"-moz-transform" : "rotate(0deg)", /* Firefox 4 */
				"-webkit-transform" : "rotate(0deg)", /* Safari and Chrome */
				"-o-transform" : "rotate(0deg)" /* Opera */
			});
		}
	});

	$("#back-to-top").on("click", function() {
		// 设置定时器
		timer = setInterval(function() {
			// 设置滚动条距离顶部的高度
			var scrollHeight = $(window).scrollTop();
			// 设置speed 为负数：防止非整数情况造成无法到达顶部
			var speed = Math.floor(-scrollHeight / 6);

			isTop = true;

			$(window).scrollTop(scrollHeight + speed);
			if (scrollHeight == 0) {
				clearInterval(timer);
			}
		}, 30)
	})
})