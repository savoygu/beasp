(function ($) {
	/*获取url参数值*/
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); 
        return null;
    }
    /**全选与否**/
    $.fn.checkAll = function(opts) {
    	var settings = $.extend({
    		all:"all",
    		single:"single"
    	}, opts||{});
    	this.filter(":checkbox[name='"+settings.all+"']").click(function() {
    		$(":checkbox[name='"+settings.single+"']").prop("checked", $(this).prop("checked"));
    	});
    	this.filter(":checkbox[name='"+settings.single+"']").click(function() {
    		$(":checkbox[name='"+settings.all+"']").prop("checked", 
    				$(":checkbox[name='"+settings.single+"']").length==$(":checkbox[name='"+settings.single+"']:checked").length);
    	})
    }
    /*//全选与否
	$(":checkbox[name='all']").click(function(){
		$(":checkbox[name='cateids']").prop("checked", $(this).prop("checked"));
	});
	$(":checkbox[name='cateids']").click(function(){
		$(":checkbox[name='all']").prop("checked", 
				$(":checkbox[name='cateids']").length==$(":checkbox[name='cateids']:checked").length);
	});*/
    /**单个删除**/
    $.fn.simpleDelete = function(opts) {
    	var settings = $.extend({
    		
    	}, opts||{});
    	this.on("click", function() {
    		var name = $(this).next(":hidden").val();
    		if(name) {
    			var flag = confirm("确定要删除'"+name+"'的信息吗?");
    		} else {
    			var flag = confirm("确定要删除当前项信息吗?");
    		}
    		if(flag) {
    			var url = $(this).attr("href");
    			$("#_form").attr("action", url);
    			$("#_method").val("DELETE");
    			$("#_form").submit();
    		}
    		return false;
    	})
    }
    /*//单个删除
    $(".delete").click(function() {
		var name = $(this).next(":hidden").val();
		var flag = confirm("确定要删除'"+name+"'的信息吗?");
		if(flag) {
			var url = $(this).attr("href");
			$("#_form").attr("action", url);
			$("#_method").val("DELETE");
			$("#_form").submit();
		}
		return false;
	});*/
    
    /**选中当前**/
    $.fn.choiceCurrent = function(opts) {
    	var settings = $.extend({
    		list:"list",
    		alertMsg:"请选择你要选中的样式!",
    		alertMsg2:"你只能选择一个选中样式!",
    		confirmMsg:"确定要选中这个样式吗?",
    		url:"",
    		form:"_form"
    	}, opts||{});
    	this.on("click", function() {
    		var param = "";
    		if($(":checkbox[name='"+settings.list+"']:checked").length < 1) {
    			alert(settings.alertMsg);
    			return;
    		} else if($(":checkbox[name='"+settings.list+"']:checked").length > 1){
    			alert(settings.alertMsg2);
    			return;
    		} else {
    			$(":checkbox[name='"+settings.list+"']:checked").each(function(index, value) {
        			param += $(this).val();
        		});
    			var flag = confirm(settings.confirmMsg);
        		if(flag) {
        			$("#"+settings.form).attr("action", settings.url);
        			$("#"+settings.form).append("<input type='hidden' name='param' value='"+param+"'>");
        			$("#"+settings.form).submit();
        		}
    		}
    	})
    };
    /**批量删除**/
    $.fn.batchDelete = function(opts){
    	var settings = $.extend({
    		list:"list",
    		alertMsg:"请选择你要删除的类别!",
    		confirmMsg:"确定要删除这些类别吗?",
    		url:"",
    		form:"_form",
    		isAdd:false,//是要还原吗?(针对样式设置为可见)
    		isRecyclebin:false//是来自回收站吗?(针对书籍,是否是从回收站删除的)
    	}, opts||{});
    	this.on("click", function() {
    		var param = "";
    		if($(":checkbox[name='"+settings.list+"']:checked").length < 1) {
    			alert(settings.alertMsg);
    			return;
    		}
    		$(":checkbox[name='"+settings.list+"']:checked").each(function(index, value) {
    			param += $(this).val() + "-";
    		});
    		param = param.substring(0, param.length-1);
    		var flag = confirm(settings.confirmMsg);
    		if(flag) {
    			$("#"+settings.form).attr("action", settings.url);
    			$("#"+settings.form).append("<input type='hidden' name='parameter' value='"+param+"'>");
    			if(settings.isAdd) {
    				$("#"+settings.form).append("<input type='hidden' name='isAdd' value='"+settings.isAdd+"'>");
    			}
    			if(settings.isRecyclebin) {
    				$("#"+settings.form).append("<input type='hidden' name='isRecyclebin' value='"+settings.isRecyclebin+"'>");
    			}
    			$("#"+settings.form).submit();
    		}
    	})
    }
    
    /*//批量删除
	$("#batchDelBtn").on("click", function(){
		var param = "";
		if($(":checkbox[name='cateids']:checked").length < 1) {
			alert("请选择你要删除的类别!");
			return;
		}
		$(":checkbox[name='cateids']:checked").each(function(index,value){
			param += $(this).val()+"-";
		})
		param = param.substring(0, param.length-1);
		var url = "<%=bookAdminPath%>/book/ids";
		var flag = confirm("确定要删除这些类别吗?");
		if(flag) {
			$("#_form").attr("action", url);
			$("#_form").append("<input type='hidden' name='param' value='"+param+"'>");
			$("#_form").submit();
		}
	})*/
    /**校验用户名**/
    $.fn.validateName = function(opts){
    	var settings = $.extend({
    		nameMsg:"nameMsg",
    		_oldName:"_oldName",
    		rightMsg:"书籍类别名称可以使用!",
    		emptyMsg:"书籍类别名称不能为空!",
    		errorMsg:"书籍类别名称已经存在!",
    		url:""
    	},opts||{});
    	//去除空格
    	var name = $.trim(this.val());
    	//非空校验
		if(!name) {
			$("#"+settings.nameMsg).html("<font color='red'>"+settings.emptyMsg+"</font>");
			return false;
		}
		//设值
		this.val(name);
		
		//若修改的name 和 之前的 name 一致, 则不发送Ajax 请求, 直接 alert:name 可用!
		var _oldName = $("#"+settings._oldName).val();
		_oldName = $.trim(_oldName);
		if(_oldName != null && _oldName != "" && _oldName==name) {
			$("#"+settings.nameMsg).html("<font color='green'>"+settings.rightMsg+"</font>");
			return true;
		}
		
		//是否存在校验
		var args = {"name":name,"date":new Date()};
		var bool = true;
		$.ajax({
			url:settings.url,
			data:args,
			type:"POST",
			dataType:"json",
			async:false,
			cache:false,
			success: function(data) {
				if(data == "0") {
					$("#"+settings.nameMsg).html("<font color='green'>"+settings.rightMsg+"</font>");
				} else if(data == "1") {
					$("#"+settings.nameMsg).html("<font color='red'>"+settings.errorMsg+"</font>");
					bool = false;
				}
			}
		});
		
		/*$.post(settings.url, args, function(data){
			if(data == "0") {
				$("#"+settings.nameMsg).html("<font color='green'>"+settings.rightMsg+"</font>");
			} else if(data == "1") {
				$("#"+settings.nameMsg).html("<font color='red'>"+settings.errorMsg+"</font>");
				bool = false;
			}
		},"json");*/
		return bool;
    }
    /*//校验用户名
    	function validateName() {
		//去除空格
		var name = $.trim($("#name").val());
		//非空校验
		if(!name) {
			$("#nameMsg").html("<font color='red'>书籍类别名称不能为空!</font>");
			return false;
		}
		
		//是否存在校验
		$("#name").val(name);
			
		//若修改的name 和 之前的 name 一致, 则不发送Ajax 请求, 直接 alert:name 可用!
		
		var _oldName = $("#_oldName").val();
		_oldName = $.trim(_oldName);
		if(_oldName != null && _oldName != "" && _oldName==name) {
			$("#nameMsg").html("<font color='green'>书籍类别名称可以使用!</font>");
			return true;
		}
		
		var url = "<%=bookAdminPath%>/ajaxValidateName";
		var args = {"name":name,"date":new Date()};
		var bool = true;
		$.ajax({
			url:url,
			data:args,
			type:"POST",
			dataType:"json",
			async:false,
			cache:false,
			success: function(data) {
				if(data == "0") {
					$("#nameMsg").html("<font color='green'>书籍类别名称可以使用!</font>");
				} else if(data == "1") {
					$("#nameMsg").html("<font color='red'>书籍类别名称已经存在!</font>");
					bool = false;
				}
			}
		});
		return bool;
		//$.post(url, args, function(data) {
			
		//}); 
	} */
})(jQuery);