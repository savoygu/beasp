(function($){
	
	//删除/还原书籍
	$.fn.backUpOrRemove = function(opts) {
		var settings = $.extend({
			confirmMsg : "你确定要删除该书籍吗?",
			completeMsg : "你确定要完全删除该书籍吗?",
			url : "/space/ajaxGiveUp",
			bookCountEle : ".js-count-book",
			recycleCountEle : ".js-count-recyclebin",
			successMsg : "删除成功",
			errorMsg : "删除失败",
			isRecycle : false,//是否是在回收站进行删除?
			isRemove : true//在回收站中，是删除还是还原?
		}, opts||{});
		
		this.on("click", function() {
			var $li = $(this).parent("li");
			var id = $li.attr("data-id"), delBtn = $(this);
			if(delBtn.hasClass('deleting')) return;//防止多次点击哟
			delBtn.addClass('deleting');
			layer.confirm(!settings.isRecycle?settings.confirmMsg:settings.completeMsg,{icon:3, title:"来自交换与分享平台的提示", shade: [0.8, '#393D49'], shift:0}, function(index) {
				$.ajax({
					url:settings.url,
					type:'POST',
					dataType:'json',
					data:{"bookId":id},
					success:function(data) {
						if(data.result==1){
							$li.animate({//删除动画效果
								'margin-top':-6,
								opacity:0
							}, 300, function() {
								var $ul = $li.parent();//获取当前$li 的父元素
								$li.remove();//移除当前$li
								$ul.children("li").length == 0 && $ul.parents('.tl-item').remove();//如果没有元素了，移除当前$li的父元素
								var $booklist = $(".js-book-list");
								if($booklist.children('.tl-item').length < 1) {//如果没有任何元素了，就重定向
									location.replace(location.href.replace(/\/page\/d+|\?pageNo=\d+/i, ''));//去除分页参数
								}
							});
							var $el, num;
							if(settings.isRemove) {//进行删除
								if(settings.isRecycle) {//在回收站进行
									$el = $(settings.recycleCountEle + " .got-num");
									if($el.length) {
										num = parseInt($el.text(), 10);
										num--;
										if(num <= 0) {
											num = 0;
										}
										$el.text(num);//修改左侧导航的回收站数量
										$(".tool-right .total-num b").text(num);//修改工具条上的回收站数量
									}
								} else {//不在回收站进行
									$el = $(settings.bookCountEle + " .got-num");
									if($el.length) {
										num = parseInt($el.text(), 10);
										num--;
										if(num <= 0) {
											num = 0;
										}
										//alert(num)
										$el.text(num);//修改左侧导航的书籍数量
										$(".tool-right .total-num b").text(num);//修改工具条上的书籍数量
									}
									$el = $(settings.recycleCountEle + " .got-num");
									if($el.length) {
										num = parseInt($el.text(), 10);
										num++;
										//alert(num)
										$el.text(num);//修改左侧导航的回收站数量
									}
								}
							} else {//进行还原
								$el = $(settings.recycleCountEle + " .got-num");
								if($el.length) {
									num = parseInt($el.text(), 10);
									num--;
									if(num <= 0) {
										num = 0;
									}
									$el.text(num);//修改左侧导航的回收站数量
									$(".tool-right .total-num b").text(num);//修改工具条上的回收站数量
								}
								$el = $(settings.bookCountEle + " .got-num");
								if($el.length) {
									num = parseInt($el.text(), 10);
									num++;
									$el.text(num);//修改左侧导航的书籍数量
								}
							}
							
							layer.msg(settings.successMsg, {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
							
						}else{
		                    layer.msg(settings.errorMsg, {icon:2, time:2000, shade: [0.8, '#393D49'], shift:6});
		                }
					},
					error:function() {
						layer.msg('网络错误，请稍后再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
					}
				})
				layer.close(index);
			});
		})
		
	}
	
	//显示、隐藏删除/还原书籍图标
	$.fn.backUpIcon = function(opts) {
		var settings = $.extend({
			isRemove:true
		}, opts||{});
		this.on("click", function() {
			var _t = $(this).data("edit") | 0;
			if(settings.isRemove) {
				if(!_t) {
					var _backup = $(".js-program-backup-edit").data("edit") | 0;
					//alert(_backup)
					if(_backup) {
						$(".js-program-backup-edit").data("edit", "0").removeClass('status-edit').html("<i class='icon icon-del'></i><b>还原</b>");
						$(".js-book-list").removeClass("rollable");
					}
					$(this).data("edit", "1").addClass('status-edit').html("<b>完成</b>");
					$(".js-book-list").addClass("editable");
				} else {
					$(this).data("edit", "0").removeClass('status-edit').html("<i class='icon icon-del'></i><b>删除</b>");
					$(".js-book-list").removeClass("editable");
				}
			} else {
				if(!_t) {
					var _remove = $(".js-program-remove-edit").data("edit") | 0;
					//alert(_remove);
					if(_remove) {
						$(".js-program-remove-edit").data("edit", "0").removeClass('status-edit').html("<i class='icon icon-del'></i><b>删除</b>");
						$(".js-book-list").removeClass("editable");
					}
					$(this).data("edit", "1").addClass('status-edit').html("<b>完成</b>");
					$(".js-book-list").addClass("rollable");
				} else {
					$(this).data("edit", "0").removeClass('status-edit').html("<i class='icon icon-del'></i><b>还原</b>");
					$(".js-book-list").removeClass("rollable");
				}
			}
		});
	}
	
})(jQuery)