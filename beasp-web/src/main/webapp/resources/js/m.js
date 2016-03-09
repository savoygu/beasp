$(function() {
	$('[data-toggle="tooltip"]').tooltip();
	// $('[data-toggle="popover"]').popover()
	$('[data-toggle="popover"]').popover({
		trigger : 'hover'
	});
	$('[data-spy="scroll"]').each(function() {
		var $spy = $(this).scrollspy('refresh')
	})
});
/* 搜索框 */
$("#seobut").click(function() {
	var seo = $("#searchtxt").val();
	if (seo.length >= 1) {
		window.location.href = "/book/search?words=" + seo;
	}
});
$('#searchtxt').bind('keypress', function(event) {

	if (event.keyCode == "13") {
		var seo = $("#searchtxt").val();
		if (seo.length >= 1) {
			window.location.href = "/book/search?words=" + seo;
		}
	}
});
$(function() {
	$('#searchtxt').bind({
		focus : function() {
			$(this).css({
				"border" : "1px solid #B9B9B9",
				"transition" : "all 1.5s ease 0s"
			});
		},
		blur : function() {
			$(this).css({
				"border" : "1px solid #2c2c2c",
				"transition" : "all 1.5s ease 0s"
			});
		}
	});
})

/* 导航条 */
$(function() {
	var sz = {};
	var zid;
	var pd1 = 0;
	var pd2 = 0;
	$(".nzz").hover(function() {
		zid = $(this).attr("id");
		sz[zid + '_timer'] = setTimeout(function() {
			$("#zt").addClass("mh");
			$(".nn").css("display", "none");
			$(".nav-zi").css("display", "block");
			$("#n" + zid).css("display", "block");
			$("#n" + zid).addClass("nadc");
			$(".nzz").removeClass("nav-zibg");
			$("#" + zid).addClass("nav-zibg");
			pd1 = 1;
			if (zid == "z1") {
				$(".nav-zi").animate({
					height : '143px'
				});
			}
			if (zid == "z2") {
				$(".nav-zi").animate({
					height : '143px'
				});
			}
			if (zid == "z3") {
				$(".nav-zi").animate({
					height : '143px'
				});
			}
			if (zid == "z4") {
				$(".nav-zi").animate({
					height : '143px'
				});
			}
			if (zid == "z5") {
				$(".nav-zi").animate({
					height : '143px'
				});
			}
			if (zid == "z6") {
				$(".nav-zi").animate({
					height : '143px'
				});
			}
			if (zid == "z7") {
				$(".nav-zi").animate({
					height : '143px'
				});
			}
			if (zid == "z8") {
				$(".nav-zi").animate({
					height : '143px'
				});
			}
		}, 300);
	}, function() {
		clearTimeout(sz[zid + '_timer']);
	});
	$(".yn").mouseleave(function() {
		$(".nav-zi").css("display", "none");
		$('#zt').removeClass('mh');
		$(".nzz").removeClass("nav-zibg");
	});
});
$(function() {
	/*导航单个背景渐变*/
	$(".nav-zi a").mouseover(function() {
		$(this).children().removeClass("ls");
	});
	$(".nav-zi a").mouseout(function() {
		$(this).children().addClass("ls");
	});
	/* 标签切换 */
	$("#recommendName li").on("mouseenter", function() {
		$(this).addClass("on");
		$(this).siblings().removeClass("on");
		var num = $(this).index();
		$("#hot-bookbox .book-list:eq(" + num + ")").css({
			"display" : "block"
		});
		$("#hot-bookbox .book-list:eq(" + num + ")").siblings().css({
			"display" : "none"
		});
	})
	/* 我要分享淡入淡出 */
	$("#hot-bookbox li").on("mouseenter", function() {
		$(this).find(".book-cover-btn").stop().fadeIn("slow");
		$(this).siblings().find(".zzc").addClass("zzc-other");
	})
	$("#hot-bookbox li").on("mouseleave", function() {
		$(this).find(".book-cover-btn").stop().fadeOut("slow");
		$(this).siblings().find(".zzc").removeClass("zzc-other");
	})
});
/* 滚动固定 */
$(window).scroll(function() {
	var menu_top = $('#menu_wrap').offset().top;
	if ($(window).scrollTop() >= 255) {
		$(".nav-bg").css("top", 50);
		$(".nav-bg").css("position", "fixed");
		$('#menu_wrap').addClass('menuFixed')
	}
	if ($(window).scrollTop() < 255) {
		$(".nav-bg").css("position", "relative");
		$(".nav-bg").css("top", 255);
		$('#menu_wrap').removeClass('menuFixed')
	}

	if (n == 1) {
		if ($(window).scrollTop() >= 30) {
			$(".nav-bg").css("top", 50);
			$(".nav-bg").css("position", "fixed");
			$('#menu_wrap').addClass('menuFixed')
		}
		if ($(window).scrollTop() < 30) {
			$(".nav-bg").css("position", "relative");
			$('#menu_wrap').removeClass('menuFixed')
		}
	}
});


/**并没什么卵用, 中看不中用**/
$(function() {
	$("[action-type='my_menu']").mouseenter(function() {
		$('[action-type="my_menu"]').addClass("hover")
	});
	$("[action-type='my_menu']").mouseleave(function() {
		$('[action-type="my_menu"]').removeClass("hover")
	})
})