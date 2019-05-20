$(document).ready(function() {
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
	
	$("#js-forgot-form").submit(function() {
		var bool = true;
		if(!validateNewpwd()) {
			bool = false;
		}
		if(!validateRepwd()) {
			bool = false;
		}
		return bool;
	})
})

/**
 * 当前密码校验
 * @returns {Boolean}
 */
function validateNewpwd() {
	var id = "newpwd";
	var value = $("#"+id).val();
	var oldpwd = $("#oldpwd").val();
	if(!value) {
		return errorMsg(id, "新密码不能为空！");
	}
	if(value.length<6 || value.length>20) {
		return errorMsg(id, "新密码长度应该在6~20之间！");
	}
	return true;
}

/**
 * 当前密码校验
 * @returns {Boolean}
 */
function validateRepwd() {
	var id = "repwd";
	var value = $("#"+id).val();
	var newpwd = $("#newpwd").val();
	if(!value) {
		return errorMsg(id, "确认新密码不能为空！")
	}
	if(value != newpwd) {
		return errorMsg(id, "确认新密码与新密码不一致");
	}
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