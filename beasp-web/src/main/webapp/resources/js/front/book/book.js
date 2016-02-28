$(document).ready(function() {
	//回收站显示删除书籍图标
	$(".js-program-remove-edit").backUpIcon();
	$(".js-program-backup-edit").backUpIcon({isRemove:false});
	//非回收站
	$(".js-program-edit").click(function() {
		var _t = $(this).data("edit") | 0;
		if(!_t) {
			$(this).data("edit", "1").addClass('status-edit').html("<b>完成</b>");
			$(".js-book-list").addClass("editable");
		} else {
			$(this).data("edit", "0").removeClass('status-edit').html("<i class='icon icon-del'></i><b>删除</b>");
			$(".js-book-list").removeClass("editable");
		}
	})

	//删除书籍
	//$(".js-book-list .del").backUpOrRemove();
	/*$(".js-book-list").on("click", ".del", function() {
		var $li = $(this).parent("li");
		var id = $li.attr("data-id"), delBtn = $(this);
		if(delBtn.hasClass('deleting')) return;//防止多次点击哟
		delBtn.addClass('deleting');
		layer.confirm('你确定要删除该书籍吗?',{icon:3, title:"来自交换与分享平台的提示", shade: [0.8, '#393D49'], shift:0}, function(index) {
			$.ajax({
				url:"/space/ajaxGiveUp",
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
						$el = $(".js-count-book .got-num");
						if($el.length) {
							num = parseInt($el.text(), 10);
							num--;
							if(num <= 0) {
								num = 0;
							}
							$el.text(num);//修改左侧导航的书籍数量
							$(".tool-right .total-num b").text(num);//修改工具条上的书籍数量
						}
						
						layer.msg('删除成功', {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
						
					}else{
	                    layer.msg('删除失败，请稍后再试', {icon:2, time:2000, shade: [0.8, '#393D49'], shift:6});
	                }
				},
				error:function() {
					layer.msg('网络错误，请稍后再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
				}
			})
			layer.close(index);
		});
	})*/
	
	//还原书籍
	$(".js-book-list .backup").backUpOrRemove({
		confirmMsg : "你确定要还原该书籍吗?",
		url : "/space/ajaxBackUp",
		countEle : ".js-count-recyclebin",
		successMsg : "还原成功",
		errorMsg : "还原失败",
		isRemove : false
	});
	/*$(".js-book-list").on("click", ".backup", function() {
		var $li = $(this).parent("li");
		var id = $li.attr("data-id"), delBtn = $(this);
		if(delBtn.hasClass('deleting')) return;//防止多次点击哟
		delBtn.addClass('deleting');
		layer.confirm('你确定要还原该书籍吗?',{icon:3, title:"来自交换与分享平台的提示", shade: [0.8, '#393D49'], shift:0}, function(index) {
			$.ajax({
				url:"/space/ajaxBackUp",
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
						$el = $(".js-count-recyclebin .got-num");
						if($el.length) {
							num = parseInt($el.text(), 10);
							num--;
							if(num <= 0) {
								num = 0;
							}
							$el.text(num);//修改左侧导航的书籍数量
							$(".tool-right .total-num b").text(num);//修改工具条上的书籍数量
						}
						
						layer.msg('还原成功', {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
						
					}else{
	                    layer.msg('还原失败，请稍后再试', {icon:2, time:2000, shade: [0.8, '#393D49'], shift:6});
	                }
				},
				error:function() {
					layer.msg('网络错误，请稍后再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
				}
			})
			layer.close(index);
		});
	})*/
	
	//编辑个性签名
	$("#publishsign, #signed").on("click", function() {
		var newH = $("#signed").innerHeight(),
		editor = $("#js-sign-editor"),
		signed = $("#signed"),
		placeholder = "这位童鞋很懒，什么也没有留下~~！";
		signed.addClass("signed_visible");
		editor.val($.trim(signed.text())).innerHeight(newH).addClass("sign_block");
		//光标顶到textarea框内，选中其中内容
		editor.focus().select();
		//textarea失去焦点，就取新值，ajax请求
		editor.unbind('blur').blur(function() {
			var newtxt = editor.val(),
			len = newtxt.length;
			if(len>100) {
				$("#rlf-tip-wrap").html("个性签名不能超过100个字符~");
				return false;
			} else {
				$("#rlf-tip-wrap").empty();
				//ajax传递签名到后台，如果参数为空那么就为原来的参数。
				if(len <= 0){
					newtxt = placeholder;
				} else {
					newtxt = newtxt;
				}
				
				$.ajax({
					url:"/user/singleset",
					type:"post",
					dataType:"json",
					data:{about:newtxt},
					success:function(data) {
						if(data.status != 0) {
							alert(data.msg);
							signed.removeClass("signed_visible");
							editor.focus();
							return false;
						} else {
							signed.find('strong').html(newtxt.replace(/</g, "&lt;").replace(/>/g, "&gt;"));
							signed.removeClass("signed_visible");
							editor.removeClass("sign_block");
						}
					}
				})
			}
		})
	})
})