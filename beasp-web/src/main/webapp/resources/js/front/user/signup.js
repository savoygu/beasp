$(document).ready(function() {
	/**注册校验**/
	
	/**
	 * 1. 找到所有的错误信息，循环遍历之。调用一个方法来决定是否显示错误信息！
	 */
	$(".errorClass").each(function() {
		showError($(this));//遍历每个元素，使用每个元素来调用showError方法。
	});
	
	/**
	 * 2.输入框得到焦点隐藏错误信息
	 * 3.输入框失去焦点进行校验 
	 */
	$(".inputClass").on({
		focus : function() {
			var labelId = $(this).attr("id")+"Error";//通过输入框找到对应的label的id
			$("#"+labelId).text("");//把label的内容清空
			showError($("#"+labelId));
		},
		blur : function() {//3. 输入框失去焦点进行校验 
			var id = $(this).attr("id");//获取当前输入框的id
			var funName = "validate" + id.substring(0,1).toUpperCase() + id.substring(1) + "()";//得到函数名
			eval(funName);//把字符串当作javascript 代码, 执行函数调用
		}
	});
	
	/**
	 * 单选按钮值改变时隐藏错误信息
	 */
	$(".radioClass").on({
		click: function() {
			var labelId = $(this).attr("id")+"Error";//通过输入框找到对应的label的id
			$("#"+labelId).text("");//把label的内容清空
			showError($("#"+labelId));
		}
	})
	
	/**
	 * 下拉框值改变时隐藏错误信息
	 */
	$(".selectClass").on({
		change: function() {
			var labelId = $(this).attr("id")+"Error";//通过输入框找到对应的label的id
			$("#"+labelId).text("");//把label的内容清空
			showError($("#"+labelId));
		}
	})
	
	/**
	 * 4.表单提交时进行校验
	 */
	$("#registForm").submit(function() {
		var bool = true;
		if(!validateUserName()) {
			bool = false;
		}
		if(!validatePassword()) {
			bool = false;
		}
		if(!validateNickName()) {
			bool = false;
		}
		if(!validateGender()) {
			bool = false;
		}
		if(!validateEmail()) {
			bool = false;
		}
		if(!validatePhone()) {
			bool = false;
		}
		if(!validateProvince()) {
			bool = false;
		}
		if(!validateCity()) {
			bool = false;
		}
		if(!validateSchool()) {
			bool = false;
		}
		return bool;
	});
})

/**
 * 用户名校验方法
 */
function validateUserName() {
	var id="userName";
	var value = $("#"+id).val();//获取输入框内容
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>用户名不能为空</p></div></div><i class='layer-arr'></i>");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2.长度校验
	 */
	if(value.length < 3 || value.length > 20) {
		$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>用户名长度必须在3~20之间</p></div></div><i class='layer-arr'></i>");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 3.是否存在校验
	 */
	var bool = true;
	$.ajax({
		url: "/beasp/admin/user/ajaxValidateUserName",
		data: {"userName":value, "time":new Date()},
		type: "POST",
		dataType: "json",
		async: false,
		cache: false,
		success: function(data) {
			if(!data) {
				$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>用户名已经被注册了</p></div></div><i class='layer-arr'></i>");
				showError($("#"+id+"Error"));
				bool = false;
			}
		}
	})
	return bool;
}

/**
 * 密码校验
 */
function validatePassword() {
	var id = "password";
	var value = $("#"+id).val();//获取输入框内容
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error" ).html("<div class='layer-mid'><div class='error-tt'><p>密码不能为空</p></div></div><i class='layer-arr'></i>");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2. 长度校验
	 */
	if(value.length < 6 || value.length > 20) {
		$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>密码长度必须在6~20之间</p></div></div><i class='layer-arr'></i>");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

/**
 * 昵称校验
 */
function validateNickName() {
	var id="nickName";
	var value = $("#"+id).val();//获取输入框内容
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>昵称不能为空</p></div></div><i class='layer-arr'></i>");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2.长度校验
	 */
	if(value.length < 3 || value.length > 20) {
		$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>昵称长度必须在3~20之间</p></div></div><i class='layer-arr'></i>");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

/**
 * 性别 校验
 */
function validateGender() {
	var id = "gender";
	var value = $("input:radio:checked").val();
	/**
	 * 未选校验
	 */
	if(!value) {
		return errorMsg(id, '你还没有选择性别');
	}
	return true;
}

/**
 * Email 校验
 */
function validateEmail()  {
	var id = "email";
	var value = $("#"+id).val();
	/**
	 * 1.非空校验
	 */
	if(!value) {
		/*$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>Email 不能为空</p></div></div><i class='layer-arr'></i>");
		showError($("#"+id+"Error"));
		return false;*/
		return errorMsg(id, 'Email 不能为空');
	}
	/**
	 * 2. 格式校验
	 */
	if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)) {
		return errorMsg(id, 'Email 格式不正确');
	}
	/**
	 * 3. 是否存在校验
	 */
	var bool = true;
	$.ajax({
		url:"/beasp/admin/user/ajaxValidateEmail",
		data:{"email":value, "time":new Date()},
		type:"POST",
		dataType: "json",
		async: false,
		cache: false,
		success:function(data) {
			if(!data) {
				$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>Email 已经被注册了</p></div></div><i class='layer-arr'></i>");
				showError($("#"+id+"Error"));
				bool = false;
			}
		}
	})
	return bool;
}
/**
 * 手机号 校验
 */
function validatePhone() {
	var id = "phone";
	var value = $("#"+id).val();
	/**
	 * 1. 非空校验
	 */
	if(!value) {
		return errorMsg(id, '手机号不能为空');
	}
	/**
	 * 2. 格式校验
	 */
	if(!/^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\d{8}$/.test(value)) {
		return errorMsg(id, '手机号格式不正确');
	}
	/**
	 * 3. 是否存在校验
	 */
	var bool = true;
	$.ajax({
		url:"/beasp/admin/user/ajaxValidatePhone",
		data:{"phone":value, "time":new Date()},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success: function(data) {
			if(!data) {
				$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>手机号已经被注册了</p></div></div><i class='layer-arr'></i>");
				showError($("#"+id+"Error"));
				bool = false;
			}
		}
	})
	return bool;
}

/**
 * 省市校验
 */
function validateProvince() {
	var id = "province";
	var value = $("#"+id).val();
	/**
	 * 1. 非空校验
	 */
	if(!value) {
		return errorMsg(id, '你还没选择所在省');
	}
	return true;
}

/**
 * 省市校验
 */
function validateCity() {
	var id = "city";
	var value = $("#"+id).val();
	if($("#province").val()) {
		/**
		 * 1. 非空校验
		 */
		if(!value) {
			return errorMsg(id, '你还没选择所在市');
		}
	}
	return true;
}

/**
 * 学校校验
 */
function validateSchool() {
	var id = "school";
	var value = $("#"+id).val();
	/**
	 * 1. 非空校验
	 */
	if(!value) {
		return	errorMsg(id, '你还没选择所在学校');
	}
	return true;
}


function errorMsg(id, errorMsg) {
	$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>"+errorMsg+"</p></div></div><i class='layer-arr'></i>");
	showError($("#"+id+"Error"));
	return false;
}

/**
 * 判断当前元素是否存在内容，如果存在显示，不存在不显示！
 * @param ele
 */
function showError(ele) {
	var text = ele.find(".error-tt p").text();//获取元素内容
	if(!text) {//如果没有内容
		ele.css({"display":"none", "-webkit-animation" : "shake 0.6s ease-in-out 0.3s",  "-moz-animation": "shake 0.6s ease-in-out 0.3s", " -o-animation" : "shake 0.6s ease-in-out 0.3s", "animation": "shake 0.6s ease-in-out 0.3s"});//隐藏元素
		ele.find(".layer-arr").css({display:"none"});
	} else {
		ele.css({"display":"block", "-webkit-animation" : "shake 0.6s ease-in-out 0.3s",  "-moz-animation": "shake 0.6s ease-in-out 0.3s", " -o-animation" : "shake 0.6s ease-in-out 0.3s", "animation": "shake 0.6s ease-in-out 0.3s"});//显示元素
		ele.find(".layer-arr").css({display:"block"});
	}
}
