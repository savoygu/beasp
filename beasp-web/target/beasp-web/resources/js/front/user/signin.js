$(document).ready(function() {
	/**登录校验**/
	
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
	 * 4.表单提交时进行校验
	 */
	$("#loginForm").submit(function() {
		var bool = true;
		if(!validateUserName()) {
			bool = false;
		}
		if(!validatePassword()) {
			bool = false;
		}
		if(!validateVerifyCode()) {
			bool = false;
		}
		return bool;
	});
});
/**
 * 登录名校验方法
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
	return true;
}

/**
 * 校验登录密码
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
 * 登录校验验证码
 */
function validateVerifyCode() {
	var id = "verifyCode";
	var value = $("#"+id).val();
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>验证码不能为空</p></div></div><i class='layer-arr'></i>");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2.长度校验
	 */
	if(value.length != 4) {
		$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>错误的验证码</p></div></div><i class='layer-arr'></i>");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 3.是否正确
	 */
	$.ajax({
		url:"/ajaxValidateVerifyCode",
		data:{verifyCode:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(data) {
			if(!data) {
				$("#"+id+"Error").html("<div class='layer-mid'><div class='error-tt'><p>验证码错误</p></div></div><i class='layer-arr'></i>");
				showError($("#"+id+"Error"));
				return false;
			}
		}
	})
	return true;
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

/**
 * 换一张
 */
function _hyz() {
	$("#imgVerifyCode").attr("src", "/VerifyCodeServlet.servlet?a="+new Date().getTime());
}

