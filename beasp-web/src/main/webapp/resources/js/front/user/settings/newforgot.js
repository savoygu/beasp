$(document).ready(function() {
	
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
			var pId = $(this).attr("id")+"Error";
			$("#"+pId).text("");
			showError($("#"+pId));
		},
		blur : function() {
			var id = $(this).attr("id");//获取当前输入框的id
			var funName = "validate"+ id.substring(0,1).toUpperCase()  + id.substring(1) +"()";
			eval(funName);//把字符串当作javascript 代码, 执行函数调用
		}
	})
	
	/**
	 * 切换验证码
	 */
	$("#imgVerifyCode").on("click", function() {
		$(this).attr("src", "/beasp/VerifyCodeServlet.servlet?a="+new Date().getTime());
	})
	
	$("#js-forgot-form").submit(function() {
		var bool = true;
		if(!validateEmail()) {
			bool = false;
		} 
		if(!validateVerifyCode()) {
			bool = false;
		}
		return bool;
	})
})

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
			if(data) {
				$("#"+id+"Error").html("请填写您的注册邮箱");
				showError($("#"+id+"Error"));
				bool = false;
			}
		}
	})
	return bool;
}

/**
 * 校验验证码
 */
function validateVerifyCode() {
	var id = "verifyCode";
	var value = $("#"+id).val();
	/**
	 * 1.非空校验
	 */
	if(!value) {
		return errorMsg(id, "验证码不能为空");
	}
	/**
	 * 2.长度校验
	 */
	if(value.length != 4) {
		return errorMsg(id, "错误的验证码");
	}
	/**
	 * 3.是否正确
	 */
	$.ajax({
		url:"/beasp/ajaxValidateVerifyCode",
		data:{verifyCode:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(data) {
			if(!data) {
				return errorMsg(id, "验证码错误");
			}
		}
	})
	return true;
}

/**
 * 错误消息
 * @param id
 * @param errorMsg
 * @returns {Boolean}
 */
function errorMsg(id, errorMsg) {
	$("#"+id+"Error").html(errorMsg);
	showError($("#"+id+"Error"));
	return false;
}
/**
 * 显示错误消息
 * @param ele
 */
function showError(ele) {
	var text = ele.text();//获取元素内容
	if(!text) {//如果没有内容
		ele.css({"display":"none"});
	} else {
		ele.css({"display":"block"});
	}
}

/**
 * 换一张
 */
function _hyz() {
	$("#imgVerifyCode").attr("src", "/beasp/VerifyCodeServlet.servlet?a="+new Date().getTime());
}