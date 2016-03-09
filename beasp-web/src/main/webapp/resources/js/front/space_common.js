$(document).ready(function() {
	//点击全部
	$("#js-columnall").on("click", function(e){
		var jsicon = $(this).find(".icon"),
			jscolumnbd = $(this).siblings("#js-columnbd");
		jscolumnbd.css("marginLeft",-(jscolumnbd.width())/2);
		if(jsicon.hasClass("icon-down")) {
			jscolumnbd.show();
			jsicon.removeClass("icon-down").addClass("icon-top");
		} else if(jsicon.hasClass("icon-top")){
			jscolumnbd.hide();
			jsicon.removeClass("icon-top").addClass("icon-down");
		}
	}) 
	//点击全部弹层以外消失弹层
	$(document).mouseup(function(e){
		var _con = $("#js-columnbd");
		if(!_con.is(e.target)&&_con.has(e.target).length == 0) {//判断弹层是否是目标对象,如果不是执行以下操作（收起弹层，转换图标）
			$("#js-columnbd").hide();
			$("#js-columnall").find(".icon").removeClass("icon-top").addClass("icon-down");
		}
	})
	
	//关注书籍
	$(".js-book-list").on("click", ".btn-add-collection", function(e) {
		e.preventDefault();//阻止默认提交
		var obj = $(this).parents(".book-one");
		var id = obj.data("id");
		var _this = $(this);
		if(_this.hasClass("btn-remove-collection")) {
			$.ajax({
				type:"post",
				url:"/space/ajaxfollowcancel",
				dataType:"json",
				data:{book:id},
				success:function(data) {
					if(data.status==0) {
						_this.removeClass("btn-remove-collection");
						//左侧导航条-1
						var $el = $(".js-count-collection .got-num");
						if($el.length) {
							var num = parseInt($el.text(), 10);
							num--;
							if(num <= 0) {
								num = 0;
							}
							$el.text(num);//修改左侧导航的回收站数量
							//$(".tool-right .total-num b").text(num);//修改工具条上的回收站数量
						}
						layer.msg('取消收藏', {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
					} else {
						layer.msg('收藏失败，请稍后再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
					}
				}
			})
		} else {
			$.ajax({
				type:"post",
				url:"/space/ajaxfollow",
				dataType:"json",
				data:{book:id},
				success:function(data) {
					if(data.status==0) {
						_this.addClass("btn-remove-collection");
						//左侧导航条-1
						var $el = $(".js-count-collection .got-num");
						if($el.length) {
							var num = parseInt($el.text(), 10);
							num++;
							if(num <= 0) {
								num = 0;
							}
							$el.text(num);//修改左侧导航的回收站数量
							//$(".tool-right .total-num b").text(num);//修改工具条上的回收站数量
						}
						layer.msg('增加收藏', {icon:1, time:2000, shade: [0.8, '#393D49'], shift:4});
					} else {
						layer.msg('收藏失败，请稍后再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
					}
				}
			})
		}
	})
})