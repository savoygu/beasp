$(document).ready(function() {
	/**个人设置校验**/
	/**
	 * 1. 找到所有的错误信息，循环遍历之。调用一个方法来决定是否显示错误信息！
	 */
	$(".errorClass").each(function() {
		showError($(this));//遍历每个元素，使用每个元素来调用showError方法。
	})
	
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
	 * 下拉框值改变时隐藏错误信息
	 */
	$(".selectClass").on({
		change: function() {
			var value = $(this).val();
			if(value){
				var labelId = $(this).attr("id")+"Error";//通过输入框找到对应的label的id
				$("#"+labelId).text("");//把label的内容清空
				showError($("#"+labelId));
			} else {
				var id = $(this).attr("id");//获取当前输入框的id
				var funName = "validate"+ id.substring(0,1).toUpperCase()  + id.substring(1) +"()";
				eval(funName);//把字符串当作javascript 代码, 执行函数调用
			}
		}
	})
	
	/**
	 * 表单提交时校验
	 */
	$("#profileForm").submit(function() {
		var bool = true;
		if(!validateNickName()) {
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
		if(!validatePhone()) {
			bool = false;
		}
		if(!validateSummary()) {
			bool = false;
		}
		return bool;
	})
})
/***
 * 校验昵称
 */
function validateNickName() {
	var id = "nickName";
	var value = $("#"+id).val();//获取输入框内容
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").html("昵称不能为空");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2.长度校验
	 */
	if(value.length < 3 || value.length > 20) {
		$("#"+id+"Error").html("昵称长度必须在3~20之间");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
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
	if($("#city").val()) {
		/**
		 * 1. 非空校验
		 */
		if(!value) {
			return	errorMsg(id, '你还没选择所在学校');
		}
	}
	return true;
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
		$("#"+id+"Error").html("手机号不能为空");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2. 格式校验
	 */
	if(!/^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\d{8}$/.test(value)) {
		$("#"+id+"Error").html("手机号格式不正确");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 若修改的phone 和之前的phone 一致，则不发送ajax请求，直接phone可用
	 */
	if(value)  {
		var _oldPhone = $("#_oldPhone").val();
		_oldPhone = $.trim(_oldPhone);
		if(_oldPhone != null && _oldPhone != "" && _oldPhone == value) {
			return true;
		}
	}
	/**
	 * 3. 是否存在校验
	 */
	var bool = true;
	$.ajax({
		url:"/admin/user/ajaxValidatePhone",
		data:{"phone":value, "time":new Date()},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success: function(data) {
			if(!data) {
				$("#"+id+"Error").html("手机号已经被注册了	");
				showError($("#"+id+"Error"));
				bool = false;
			}
		}
	})
	return bool;
}
function validateSummary() {
	var id = "summary";
	var value = $("#"+id).val();
	/**
	 * 1.非空校验
	 */
	if(!value) {
		return errorMsg(id, "个性签名不能为空");
	}
	/**
	 * 2.长度校验
	 */
	if(value > 100) {
		return errorMsg(id, "个性签名字数不能超过100字");
	}
	return true;
}

function errorMsg(id, errorMsg) {
	$("#"+id+"Error").html(errorMsg);
	showError($("#"+id+"Error"));
	return false;
}

function showError(ele) {
	var text = ele.text();//获取元素内容
	if(!text) {//如果没有内容
		ele.css({"display":"none"});
	} else {
		ele.css({"display":"block"});
	}
}