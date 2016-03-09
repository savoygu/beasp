$(document).ready(function() {
	/**书籍上传校验**/
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
	 * 4.表单提交时进行校验
	 */
	$("#uploadForm").submit(function() {
		var bool = true;
		if(!validateName()) {
			bool = false;
		}
		if(!validateAuthor()) {
			bool = false;
		}
		if(!validatePress()) {
			bool = false;
		}
		if(!validateVersion()) {
			bool = false;
		}
		if(!validateIssueTime()) {
			bool = false;
		}
		if(edit != 1) {
			if(!validateImage()) {
				bool = false;
			}
		}
		if(!validateSummary()) {
			bool = false;
		}
		if(!validateCategoryName()) {
			bool = false;
		}
		return bool;
	})
})
/**
 * 校验书籍名称
 * @param ele
 */
function validateName() {
	var id = "name";
	var value = $("#"+id).val();//获取输入框的值
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").text("书籍名称不能为空!");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2.长度校验
	 */
	if(value.length<1 || value.length>40){
		$("#"+id+"Error").text("书籍名称长度应该在1~40之间!");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

function validateName1() {
	var id = "name1"
}

/**
 * 校验作者名称
 * @param ele
 */
function validateAuthor() {
	var id = "author";
	var value = $("#"+id).val();//获取输入框的值
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").text("书籍作者不能为空!");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2.长度校验
	 */
	if(value.length<1 || value.length>40){
		$("#"+id+"Error").text("书籍作者长度应该在1~40之间!");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}
/**
 * 校验出版社名称
 * @param ele
 */
function validatePress() {
	var id = "press";
	var value = $("#"+id).val();//获取输入框的值
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").text("书籍出版社不能为空!");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2.长度校验
	 */
	if(value.length<1 || value.length>20){
		$("#"+id+"Error").text("书籍出版社长度应该在1~20之间!");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

/**
 * 校验出版社名称
 * @param ele
 */
function validateVersion() {
	var id = "version";
	var value = $("#"+id).val();//获取输入框的值
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").text("你还没选择书籍版本!");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

/**
 * 校验出版时间
 * @param ele
 */
function validateIssueTime() {
	var id = "issueTime";
	var value = $("#"+id).val();//获取输入框的值
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").text("书籍出版时间不能为空!");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}
/**
 * 图片校验
 */
function validateImage() {
	var id = "image";
	var value = $("#"+id).val();//获取输入框的值
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").text("你还没有为书籍选择图片!");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}
/**
 * 校验描述
 * @param ele
 */
function validateSummary() {
	var id = "summary";
	var value = $("#"+id).val();//获取输入框的值
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").text("书籍描述不能为空!");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2.长度校验
	 */
	if(value.length<1 || value.length>100){
		$("#"+id+"Error").text("书籍描述字数不能超过100字!");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

/**
 * 校验书籍类别
 * @param ele
 */
function validateCategoryName() {
	var id = "categoryName";
	var value = $("#"+id).val();//获取输入框的值
	/**
	 * 1.非空校验
	 */
	if(!value) {
		$("#"+id+"Error").text("书籍所属类别不能为空!");
		showError($("#"+id+"Error"));
		return false;
	} else {
		$("#"+id+"Error").text("");
		showError($("#"+id+"Error"));
	}
	return true;
}

function showError(ele) {
	var text = ele.text();//获取元素内容
	if(!text) {//如果没有内容
		ele.css({"display":"none"});
	} else {
		ele.css({"display":"block"});
	}
}