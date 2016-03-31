$(function() {
	$(document).on("click", "[data-fromto]", function(e) {
		e.preventDefault();
		var d = $(this).attr("data-fromto").split(":");
		if(d[0]=="signup") {//如果为注册
			//删除节点
			var oReg = document.getElementById("signup");
			var oMask = document.getElementById("mask");
			document.body.removeChild(oMask);
			document.body.removeChild(oReg);
			signin();
		} else {
			var oLogin = document.getElementById("signin");
			var oMask = document.getElementById("mask");
			document.body.removeChild(oMask);
			document.body.removeChild(oLogin);
			signup();
		}
	});
	
	$("#js-signin-btn").on("click", function() {
		signin();
	}) 
	
	$("#js-signup-btn").on("click", function() {
		signup();
	})
	
	/*var oSignin = document.getElementById("js-signin-btn");
	oSignin.onclick = function() {
		signin();
	}
	var oSignup = document.getElementById("js-signup-btn");
	oSignup.onclick = function() {
		signup();
	}*/
	
	/**
	 * 1.输入框得到焦点隐藏错误信息
	 * 2.输入框失去焦点进行校验 
	 */
	$(document).on("focus", ".ipt", function()  {
		var labelId = $(this).attr("id")+"Error";//通过输入框找到对应的label的id
		$("#"+labelId).text("");//把label的内容清空
		showError($("#"+labelId));
	});
	
	$(document).on("blur", ".ipt", function()  {
		var id = $(this).attr("id");//获取当前输入框的id
		var funName = "validate" + id.substring(0,1).toUpperCase() + id.substring(1) + "()";//得到函数名
		eval(funName);//把字符串当作javascript 代码, 执行函数调用
	});
	
	/**
	 * 4.表单提交时进行校验
	 */
	$(document).on("submit", "#signup", function() {
		var bool = true;
		if(!validateEmail()) {
			bool = false;
		}
		if(!validatePassword()) {
			bool = false;
		}
		if(!validateNickName()) {
			bool = false;
		}
		if(!validateVerify()) {
			bool = false;
		}
		if(bool == true) {
			$.ajax({
				url:"/ajaxSignup",
				data:{	
						"email":$("input[name='email']").val(), 
						"password":$("input[name='password']").val(), 
						"nickName":$("input[name='nickName']").val(),
						"verify":$("input[name='verify']").val(),
						"time":new Date()
					},
				type:"POST",
				dataType: "json",
				async: false,
				cache: false,
				success:function(data) {
					if(data.status == 0) {//成功了
						
					} else {//失败了
						$("#signin-globle-error").text(data.msg);
						return false;
					}
				}
			})
		}
		return bool;
	});
	/**
	 * 4.表单提交时进行校验
	 */
	$(document).on("submit", "#signin-form", function() {
		var bool = true;
		if(!validateEmail()) {
			bool = false;
		}
		if(!validatePassword()) {
			bool = false;
		}
		if(bool == true) {
			$.ajax({
				url:"/ajaxSignin",
				data:{
						"email":$("input[name='email']").val(), 
						"password":$("input[name='password']").val(), 
						"autoLogin":$("input[name='autoLogin']").val(), 
						"time":new Date()
					},
				type:"POST",
				dataType: "json",
				async: false,
				cache: false,
				success:function(data) {
					if(data.status == 0) {//成功了
						
					} else {//失败了
						$("#signin-globle-error").text(data.msg);
						bool = false
					}
				}
			})
		}
		return bool;
	})
})

/**
 * 判断当前元素是否存在内容，如果存在显示，不存在不显示！
 * @param ele
 */
function showError(ele) {
	var text = ele.text();//获取元素内容
	if(!text) {//如果没有内容
		ele.text(text);
//		ele.css({display:"none"});
	} else {
		ele.text(text);
//		ele.css({display:"block"});
	}
}

function errorMsg(id, errorMsg) {
	$("#"+id+"Error").html(errorMsg);
	showError($("#"+id+"Error"));
	return false;
}

function validateEmail() {
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
	if($("#"+id).parents("form").attr("id")=="signup-form") {
		/**
		 * 3. 是否存在校验
		 */
//		var bool = true;
		$.ajax({
			url:"/admin/user/ajaxValidateEmail",
			data:{"email":value, "time":new Date()},
			type:"POST",
			dataType: "json",
			async: false,
			cache: false,
			success:function(data) {
				if(!data) {
//					bool = errorMsg(id, "Email 已经被注册了");
					return errorMsg(id, "Email 已经被注册了")
				}
			}
		})
	}
	return true;
}

function validatePassword() {
	var id = "password";
	var value = $("#"+id).val();//获取输入框内容
	/**
	 * 1.非空校验
	 */
	if(!value) {
		return errorMsg(id, '密码不能为空');
	}
	/**
	 * 2. 长度校验
	 */
	if(value.length < 6 || value.length > 20) {
		return errorMsg(id, '密码长度必须在6~20之间');
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
		return errorMsg(id, "昵称不能为空");
	}
	/**
	 * 2.长度校验
	 */
	if(value.length < 3 || value.length > 20) {
		return errorMsg(id, "昵称长度必须在3~20之间");
	}
	return true;
}

/**
 * 登录校验验证码
 */
function validateVerify() {
	var id = "verify";
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
		url:"/ajaxValidateVerifyCode",
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
 * 换一张
 */
function _hyz() {
	$("#imgVerifyCode").attr("src", "/VerifyCodeServlet.servlet?a="+new Date().getTime());
}

/*window.onload = function() {
	var oSignin = document.getElementById("js-signin-btn");
		oSignin.onclick = function() {
			signin();
		}
	var oSignup = document.getElementById("js-signup-btn");
		oSignup.onclick = function() {
			signup();
		}
} */



function signup() {
	//获取页面的高度和宽度
	var sHeight = document.documentElement.scrollHeight;
	var sWidth = document.documentElement.scrollWidth;
	//可视区域的高度和宽度
	var wHeight = document.documentElement.clientHeight;
	var wWidth = document.documentElement.clientWidth;
	
	var oMask = document.createElement("div");
		oMask.id="mask";
		oMask.style.height=sHeight+"px";
		oMask.style.width=sWidth+"px";
		document.body.appendChild(oMask);
	var oReg = document.createElement("div");
		oReg.id="signup";
		oReg.className="rl-modal in";
		//oLogin.ariaHidden="false";
		oReg.innerHTML = "<div class='rl-modal-header'>"+
								"<h1>"+
									"<span data-fromto='signup:signin'>登录</span>"+
									"<span class='active-title'>注册</span>"+
								"</h1>"+
								"<button id='close' class='rl-close' aria-hidden='true' hidefocus='true' data-dismiss='modal' type='button'></button>"+
							"</div>"+
							"<div class='rl-modal-body'>"+
								"<div class='cf'>"+
									"<div class='l-left-warp l'>"+
										"<form id='signup-form' autocomplete='off' method='post'>"+
											"<p class='rlf-tip-globle' id='signin-globle-error'></p>"+
											"<div class='rlf-group'>"+
												"<input class='ipt ipt-email' type='email' id='email' name='email' placeholder='请输入电子邮箱地址' autocomplete='off' />"+
												"<p id='emailError' class='rlf-tip-wrap'></p>"+
											"</div>"+
											"<div class='rlf-group'>"+
												"<input class='ipt ipt-email' type='text' id='password' name='password' placeholder='6-20位密码，区分大小写，不能用空格' autocomplete='off' />"+
												"<p id='passwordError' class='rlf-tip-wrap'></p>"+
											"</div>"+
											"<div class='rlf-group'>"+
												"<input class='ipt ipt-email' type='text' id='nickName' name='nickName' placeholder='昵称为3-18位，中英文、数字及下划线' autocomplete='off' />"+
												"<p id='nickNameError' class='rlf-tip-wrap'></p>"+
											"</div>"+
											"<div class='rlf-group cf'>"+
												"<input id='verify' name='verify' class='ipt ipt-verify l' placeholder='请输入验证码' type='text' >"+
												"<a href='javascript:void(0)' hidefocus='true' class='verify-img-wrap js-verify-refresh'><img src='/VerifyCodeServlet.servlet' id='imgVerifyCode' class='verify-img'></a>"+
												"<a href='javascript:_hyz()' hidefocus='true' class='icon-refresh js-verify-refresh'>换一张</a>"+
												"<p id='verifyError' class='rlf-tip-wrap'></p>"+
											"</div>"+
											"<div class='rlf-group cf'>"+
											"<input id='signin-btn' value='注册' hidefocus='true' class='btn-red btn-full' type='submit'>"+
										"</div>"+
									"</form>"+
								"</div>"+
							"</div>"+
						"</div>";
		document.body.appendChild(oReg);
		//获取login的宽度和高度
		var dHeight = oReg.offsetHeight;
		var dWidth = oReg.offsetWidth;
			
			/* oReg.style.left=(wWidth-dWidth)/2+"px";
			oReg.style.top=(wHeight-dHeight)/2+"px"; */
			
		var oClose = document.getElementById("close");	
			oMask.onclick=oClose.onclick = function() {
				document.body.removeChild(oMask);
				document.body.removeChild(oReg);
			}
	
}

function signin() {
	//获取页面的高度和宽度
	var sHeight = document.documentElement.scrollHeight;
	var sWidth = document.documentElement.scrollWidth;
	//可视区域的高度和宽度
	var wHeight = document.documentElement.clientHeight;
	var wWidth = document.documentElement.clientWidth;
	
	var oMask = document.createElement("div");
		oMask.id="mask";
		oMask.style.height=sHeight+"px";
		oMask.style.width=sWidth+"px";
		document.body.appendChild(oMask);
	var oLogin = document.createElement("div");
		oLogin.id="signin";
		oLogin.className="rl-modal in";
		//oLogin.ariaHidden="false";
		oLogin.innerHTML = "<div class='rl-modal-header'>"+
								"<h1>"+
									"<span class='active-title'>登录</span>"+
									"<span data-fromto='signin:signup'>注册</span>"+
								"</h1>"+
								"<button id='close' class='rl-close' aria-hidden='true' hidefocus='true' data-dismiss='modal' type='button'></button>"+
							"</div>"+
							"<div class='rl-modal-body'>"+
								"<div class='cf'>"+
									"<div class='l-left-warp l'>"+
										"<form id='signin-form' autocomplete='off' method='post'>"+
											"<p class='rlf-tip-globle' id='signin-globle-error'></p>"+
											"<div class='rlf-group'>"+
												"<input class='ipt ipt-email' type='email' id='email' name='email' placeholder='请输入电子邮箱地址' autocomplete='off' />"+
												"<p id='emailError' class='rlf-tip-wrap'></p>"+
											"</div>"+
											"<div class='rlf-group'>"+
												"<input class='ipt ipt-email' type='text' id='password' name='password' placeholder='请输入密码' autocomplete='off' />"+
												"<p id='passwordError' class='rlf-tip-wrap'></p>"+
											"</div>"+
											"<div class='rlf-group rlf-appendix cf'>"+
												"<label for='auto-signin' class='rlf-autoin l' hidefocus='true'><input checked='checked' class='auto-cbx' name='autoLogin' id='auto-signin' type='checkbox'>下次自动登录</label>"+
												"<a href='/user/newforgot' class='rlf-forget r' target='_blank' hidefocus='true'>忘记密码 </a>"+
											"</div>"+
											"<div class='rlf-group cf'>"+
												"<input id='signin-btn' value='登录' hidefocus='true' class='btn-red btn-full' type='submit'>"+
											"</div>"+
										"</form>"+
									"</div>"+
								"</div>"+
							"</div>";
		document.body.appendChild(oLogin);
	//获取login的宽度和高度
	var dHeight = oLogin.offsetHeight;
	var dWidth = oLogin.offsetWidth;
		
		/* oLogin.style.left=(wWidth-dWidth)/2+"px";
		oLogin.style.top=(wHeight-dHeight)/2+"px"; */
		
	var oClose = document.getElementById("close");	
		oMask.onclick=oClose.onclick = function() {
			document.body.removeChild(oMask);
			document.body.removeChild(oLogin);
		}
}