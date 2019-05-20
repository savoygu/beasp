$(document).ready(function() {
		
		/**评论框**/
		$("#js-pl-input-fake").on({
			focusin : function() {
				if(!OP_CONFIG.userInfo) {
					signin();
				}
				$(this).addClass("ipt-fake-focus");
				$(".pub-editor-wrap").next(".errortip").hide();
			},
			focusout : function() {
				$(this).removeClass("ipt-fake-focus");
			},
			keyup : function() {
				var len = $.trim($("#js-pl-textarea").val()).length;
				if(len > 300) {
					$(this).addClass("ipt-fake-error").find('#js-pl-limit').addClass('limit-overflow');
				} else {
					$(this).removeClass("ipt-fake-error").find("#js-pl-limit").removeClass('limit-overflow');
				}
				$('#js-pl-limit').text(len);
			}
		})
		/**发布评论**/
		function ajaxComment(obj) {
			if(!OP_CONFIG.userInfo) {
				signin();
			}
			$.ajax({
				url:"/book/comment",
				type:"POST",
				dataType:"json",
				data:obj,
				success:function(data) {
					if(data.status == 0) {
						$("#js-pl-textarea").val('');//置空文本域
						var item = data.data;
						var plcon = obj.content;
						//TODO
						var reg, 
							escapeMap = {
								'&': '&amp;',
	                            '<': '&lt;',
	                            '>': '&gt;',
	                            '"': '&quot;',
	                            "'": '&#x27;',
	                            '`': '&#x60;'
							}
						reg = '(?:';
						for(var key in escapeMap) {
							reg += key + '|';
						}
						reg = reg.slice(0, -1);
						reg += ')';
						alert(reg);
						reg = RegExp(reg, 'g');
						plcon = plcon.replace(reg, function(m) {//escape html
							return escapeMap[m] || m;
						}).replace(/([^>\r\n]?)(\r\n|\n\r|\r|\n)/g, "$1<br>$2");
						
						if($(".plLoadListData ul").hasClass('follow-list')) {//如果没有评论，则删除子节点
							$(".plLoadListData ul").empty();
						}
						//创建li
						var html = '<li class="pl-list cf">';
						html += '<div class="pl-list-avator">' + '<a href="/space/index" target="_blank"><img src="' + item.portrait + '" width="40" height="40" title="' + item.nickname + '"/></a>' + '</div>' + '<div class="pl-list-main">' + '<div class="pl-list-nick">' + '<a href="/space/index" target="_blank">' + item.nickname + '</a>' + '</div>' + '<div class="pl-list-content">' + plcon + '</p>' + '<div class="pl-list-btm cf"> ' + '<span  class="l pl-list-time">时间：' + item.create_time + '</span>' + '</div>' + '</div>' + '</li>';
						
						$(".plLoadListData ul").prepend(html);//追加到最前
						
						$("#js-pl-limit").text('0');//字数设置为0
						
					} else {
						layer.msg('操作失败请稍候再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
					}
				},
				complete:function() {
					$("#js-pl-submit").removeClass("loading").val("发表评论");
				}
			})
		}
		
		function commentSubmit(checked, param) {
			var $this = $("#js-pl-submit"),//提交按钮
				$tipEl = $(".pub-editor-wrap").parent().find('.errortip'),//错误提示
				//verifyBox = $('.js-verify-box'),//验证码
				//verifyNum = null,
				//opts = {},
				val;
			if($this.hasClass('loading')) return;
			val = $.trim($("#js-pl-textarea").val());//获取文本域值
			if(val.length < 5 || val == $("#js-pl-textarea").attr('placeholder')) {//隐藏、显示错误信息
				$tipEl.text("内容不能少于5个字符").show();
				return;
			} else if(val.length > 300) {
				$tipEl.text("内容不能大于300个字符").show();
				return;
			} else {
				$tipEl.hide();
			}
			/*if(verifyBox.is(':visible')) {//验证码
				var c = verifyBox.find(".ipt").val();
				if(c.length == 0) {
					alert("请输入验证码");
					return;
				}
			}
			if(verifyBox.hasClass('vf-error')) {
				verifyBox.trigger('reset');
				return;
			}*/
			//verifyNum = verifyBox.find('.ipt').val().trim();
			//needVerifyCode && (opts.verify = verifyNum);
			
			$this.add("loading").val("发布中...");
			var data = {
					content : val,
					bid : $this.data("id")
			}
			if(checked) {data.checked = 1;}
			ajaxComment(data);
		}
		
		$("#js-pl-submit").click(function() {
			//如果未登录，弹出登录框
			/*if(!isLogin) {
				$(".js-textarea-unlogin").trigger('click');
				return false;
			}*/
			commentSubmit(false);
		})
		
		/*$(".extra i.fa").on({
			mouseenter : function() {
				$(this).removeClass("fa-thumbs-o-up").addClass("fa-thumbs-up"); 
			},
			mouseleave : function() {
				$(this).removeClass("fa-thumbs-up").addClass("fa-thumbs-o-up"); 
			}
		})*/
		
		var isAjax = 0;
		/**点赞**/
		$(".js-praise-action").on({
			click : function() {
				if(!OP_CONFIG.userInfo) {
					signin(); 
					return;
				}
				if(isAjax) return;
				isAjax = 1;
				var obj = $(this),
					id = obj.data("cid"),
					cmd = obj.data("cmd"),
					urls={
						praise : "/space/ajaxpraise",
						cancel : "/space/ajaxpraisecancel"
					};
				$.ajax({
					type : "POST",
					url : urls[cmd],
					dataType : "json",
					data : {
						book:id
					},
					success : function(data) {
						if(data.status == 0) {
							if(obj.data('cmd') == 'cancel') {
								$(".extra i.fa").removeClass("fa-thumbs-up").addClass("fa-thumbs-o-up"); 
								obj.html("点赞").data('cmd', 'praise');
							} else {
								$(".extra i.fa").removeClass("fa-thumbs-o-up").addClass("fa-thumbs-up"); 
								obj.html("已点赞").data('cmd', 'cancel');
							}
						} else {
							layer.msg('操作失败请稍候再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
						}
					},
					error : function() {
						layer.msg('网络错误，请稍后再试', {icon:6, time:2000, shade: [0.8, '#393D49'], shift:6});
					},
					complete : function() {
						isAjax = 0;
					}
				})
				
			} 
		})
		
		/**收藏**/
		var isAjax = 0;
		$(".js-follow-action").on("click", function() {
			if(!OP_CONFIG.userInfo) {
				signin();
				return;
			}
			if(isAjax) return;
			isAjax = 1;
			var obj = $(this),
				id = obj.data("cid"),
				cmd = obj.data("cmd"),
				urls={
						follow : "/space/ajaxfollow",
						cancel : "/space/ajaxfollowcancel"
				};
			$.ajax({
				type : "POST",
				url : urls[cmd],
				dataType : "json",
				data : {
					book:id
				},
				success : function(data) {
					if(data.status == 0) {
						if(obj.data('cmd') == 'cancel') {
							obj.removeClass('followed').html("收藏").data('cmd', 'follow');
						} else {
							obj.addClass('followed').html("已收藏").data('cmd', 'cancel');
						}
					} else {
						layer.msg('操作失败请稍候再试', {icon:5, time:2000, shade: [0.8, '#393D49'], shift:6});
					}
				},
				error : function() {
					layer.msg('网络错误，请稍后再试', {icon:6, time:2000, shade: [0.8, '#393D49'], shift:6});
				},
				complete : function() {
					isAjax = 0;
				}
			})
		})
})