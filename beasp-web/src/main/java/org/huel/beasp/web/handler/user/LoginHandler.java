package org.huel.beasp.web.handler.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.huel.beasp.entity.book.BookUser;
import org.huel.beasp.entity.book.State;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.exception.BeaspException;
import org.huel.beasp.service.book.BookUserService;
import org.huel.beasp.service.user.UserService;
import org.huel.beasp.utils.WebUtils;
import org.huel.beasp.web.handler.dto.AjaxOper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginHandler {
	@Autowired private UserService userService; 
//	private final static long cookieMaxAge = 60 * 60 * 24 * 10;//10天
//	private final static String beaspUserCookie = "com.beasp";//保存cookie 时的cookieName
//	private final static String webKey = "beasp";//加密 cookie 时的网站自定码
	@Autowired private BookUserService bookUserService;
	
	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping("/account/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {//清空cookie
			for(Cookie cookie : cookies) {
				if("beaspName".equals(cookie.getName())) {
					Cookie cookie2 = new Cookie("beaspName", null);
					cookie2.setMaxAge(0);
					cookie2.setPath("/");
					response.addCookie(cookie2);
					break;
				}
			}
		}
		return "redirect:/account/signin";
	}
	
	/**
	 * ajax注册
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxSignup", method=RequestMethod.POST)
	public AjaxOper ajaxSignup(@RequestParam("email") String email,
			@RequestParam("password") String password,@RequestParam("nickName") String nickName,
			@RequestParam("verify") String verify,
			 HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "注册成功");
		if(isNotEmpty(email) && isNotEmpty(password) &&
				isNotEmpty(nickName) && isNotEmpty(verify)) {
			User user = new User(password, nickName, email);
			/**
			 * 2. 保存用户
			 */
			try {
				user = userService.save(user);
				request.getSession().setAttribute("user", user);
				getLastestBrowse(request, user);
				
			} catch (Exception e) {
				throw new BeaspException();
			}
		} else {
			ajaxOper = new AjaxOper(1, "注册失败");
		}
		return ajaxOper;
	}
	
	private boolean isNotEmpty(String str) {
		return (str!=null&&!"".equals(str))?true:false;
	}
	
	/**
	 * ajax登录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxSignin", method=RequestMethod.POST)
	public AjaxOper ajaxSignin(@RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("autoLogin") Boolean autoLogin,
			 HttpServletRequest request, HttpServletResponse response) {
		AjaxOper ajaxOper = new AjaxOper(0, "电子邮箱地址和密码正确");
		if(email!= null && !"".equals(email) && 
				password!= null && !"".equals(password)) {
			User user = userService.getByEmailAndPassword(email, password);
			if(user != null) {
				//保存用户到 session
				request.getSession().setAttribute("user", user);
				getLastestBrowse(request, user);
				
				//保存10天cookie
				saveCookieInTen(email, password, autoLogin, response);
				
			} else {
				ajaxOper = new AjaxOper(1, "电子邮箱地址或密码错误");
			}
		} else {
			ajaxOper = new AjaxOper(1, "电子邮箱地址或密码错误");
		}
		return ajaxOper;
	}

	private void getLastestBrowse(HttpServletRequest request, User user) {
		//获取用户最近一次浏览的书籍
		BookUser bu = bookUserService.getTop1ByUserIdAndStateOrderByCreateTimeDesc(user.getId(), State.BROWSE);
		request.getSession().setAttribute("bu", bu);
	}
 	
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @param verifyCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/account/signin", method=RequestMethod.POST)
	public String login(@RequestParam("userName") String userName,
			@RequestParam("password") String password, @RequestParam("verifyCode") String verifyCode,
			@RequestParam("fromurl") String fromurl, @RequestParam("autoLogin") boolean autoLogin,
			HttpServletRequest request, HttpServletResponse response) {
		if(WebUtils.getUser(request) != null) {//是否存在用户
			return "redirect:/book/list";
		}
		/**
		 * 1. 校验
		 */
		Map<String, String> errors = validateLogin(userName, password, verifyCode, request);
		if(errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.setAttribute("userName", userName);
			request.setAttribute("password", password);
			if(fromurl != null && !"".equals(fromurl)) {
				request.setAttribute("fromurl", fromurl);//带到登录界面
			}
			return "front/user/signin";
		}
		/**
		 * 2.获取 User
		 * 正则一Email：^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$
		 * 正则二Phone：^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\d{8}$
		 */
		User user = getUserByUserName(userName, password);
		
		/**
		 * 3.判断
		 */
		if(user == null) {
			request.setAttribute("msg", "登录名或密码错误!");
			request.setAttribute("userName", userName);
			request.setAttribute("password", password);
			if(fromurl != null && !"".equals(fromurl)) {
				request.setAttribute("fromurl", fromurl);//带到登录界面
			}
			return "front/user/signin"; 
		} else {
			
			//保存用户到 session
			request.getSession().setAttribute("user", user);
			getLastestBrowse(request, user);
			
			saveCookieInTen(userName, password, autoLogin, response);
			
			/**
			 * 加密
			 */
			/*long validTime = System.currentTimeMillis() + (cookieMaxAge * 1000);
			//MD5 加密用户详细信息
			String cookieValueWithMd5 = MD5.MD5Encode(userName+":"+password+":"+validTime+":"+webKey);
			//将要保存的完整Cookie 值
			String cookieValue = userName + ":" + validTime + ":" + cookieValueWithMd5;
			//再一次对 Cookie 的值进行Base64 编码
			String cookieValueBase64 = new String(Base64Utils.encode(cookieValue.getBytes()));
			//开始保存Cookie
			Cookie cookie = new Cookie(beaspUserCookie, cookieValueBase64);
			cookie.setMaxAge(60 * 60 * 24 * 10);
			response.addCookie(cookie);*/
		}
		//单点登录
		if(fromurl != null && !"".equals(fromurl)) {
			fromurl = new String(Base64.decodeBase64(fromurl.trim().getBytes()));//获取解码后的url 
			return "redirect:"+fromurl;
		}
		return "redirect:/";
	}

	private void saveCookieInTen(String userName, String password,
			boolean autoLogin, HttpServletResponse response) {
		if(autoLogin) {//如果勾选就保存10天的cookie
			//获取用户名保存到cookie 中
			try {
				userName = URLEncoder.encode(userName,"UTF-8");
				password = URLEncoder.encode(password, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			/**
			 * 	非加密
			 */
			Cookie cookie = new Cookie("beaspName", userName+":"+password);
			cookie.setMaxAge(60 * 60 * 24 * 10);
			cookie.setPath("/");
			response.addCookie(cookie);
//				WebUtils.addCookie(response, "beaspName", userName+":"+password, 60 * 60 * 24 * 10);
		}
	}
	/**
	 * 获取 User 的方式
	 * @param userName
	 * @param password
	 * @return
	 */
	private User getUserByUserName(String userName, String password) {
		User user = null;
		if(userName.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			user = userService.getByEmailAndPassword(userName, password);
		} else if(userName.matches("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$")){
			user = userService.getByPhoneAndPassword(userName, password);
		} else {
			user = userService.getByUserNameAndPassword(userName, password);
		}
		return user;
	}
	
	/**
	 * 登录校验：对表单的字段进行逐个校验，如果有错误，使用的那给钱字段名称为key，错误信息为value，保存到map中并返回
	 * @param userName
	 * @param password
	 * @param verifyCode
	 * @return
	 */
	private Map<String, String> validateLogin(String userName, String password,
			String verifyCode, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<String, String>();
		/**
		 * 1.校验登录名
		 */
		if(userName == null || userName.trim().isEmpty()) {
			errors.put("userName", "用户名不能为空");
		} else if(userName.length()<3 || userName.length() > 20) {
			errors.put("userName", "用户名长度必须在3~20之间");
		}
		/**
		 * 2.校验密码
		 */
		if(password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空");
		} else if(password.length()<6 || password.length() > 20) {
			errors.put("password", "密码长度必须在6~20之间");
		}
		/**
		 * 3.校验验证码
		 */
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空");
		} else if(!verifyCode.equalsIgnoreCase((String)request.getSession().getAttribute("vCode"))){
			errors.put("verifyCode", "验证码错误");
		}
		return errors;
	}

	/**
	 * 单点登录界面
	 * @param fromurl
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/account/signin/fromurl/{fromurl}")
	public String signIn(@PathVariable("fromurl") String fromurl, HttpServletRequest request,
			HttpServletResponse response) {
		/**
		 * 1.读取Cookie 值
		 */
//		Cookie[] cookies = request.getCookies();
		/**
		 * 非加密
		 */
		/*String cookieValue = null;
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("beaspName")) {
				cookieValue = cookies[i].getValue();
				break;
			}
		}*/
		String cookieValue = WebUtils.getCookieByName(request, "beaspName");
		getSplitCookieValue(request, cookieValue);
		request.setAttribute("fromurl", fromurl);//带到登录界面
		return "front/user/signin";
	}

	private void getSplitCookieValue(HttpServletRequest request,
			String cookieValue) {
		if(cookieValue != null) {
			String[] cookieValues = cookieValue.split(":");
			if(cookieValues.length == 2) {
				try {
					request.setAttribute("beaspUserName", URLDecoder.decode(cookieValues[0], "UTF-8"));
					request.setAttribute("beaspPassword", URLDecoder.decode(cookieValues[1], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
	}
	
	/**
	 * 登录界面
	 * @return
	 */
	@RequestMapping(value="/account/signin", method=RequestMethod.GET)
	public String signIn(HttpServletRequest request, HttpServletResponse response) {
		if(WebUtils.getUser(request) != null) {//是否存在用户
			return "redirect:/";
		}
		String cookieValue = WebUtils.getCookieByName(request, "beaspName");
		getSplitCookieValue(request, cookieValue);
		/**
		 * 加密
		 */
		/*String cookieValue = null;
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(beaspUserCookie.equals(cookies[i].getName())) {
					cookieValue = cookies[i].getValue();
					break;
				}
			}
		}
		if(cookieValue != null) {
			//进行Base64解码
			String cookieValueDecode = new String(Base64Utils.decode(cookieValue.getBytes()));
			//对解码后的值进行分析，得到一个数组，如果数组长度不为3就是非法登录
			String [] cookieValues = cookieValueDecode.split(":");
			if(cookieValues.length == 3) {
				long validTimeInCookie = new Long(cookieValues[1]);
				if(validTimeInCookie < System.currentTimeMillis()) {
					//删除Cookie
					
				}
				//取出Cookie 中的用户名，并到数据库查出这个用户名
				String userName = cookieValues[0];
				User user = userService.getByUserName(userName);
				if(user != null) {
					String md5ValueInCookie = cookieValues[2];
					String md5ValueFromUser = MD5.MD5Encode(user.getUserName()+":"+user.getPassword()+":"+validTimeInCookie+":"+webKey);
					if(md5ValueFromUser.equals(md5ValueInCookie)) {
						request.setAttribute("user", user);
					}
				}
			}
		}*/
		return "front/user/signin";
	}
	
	/**
	 * 校验验证码
	 * @param verifyCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ajaxValidateVerifyCode")
	public boolean ajaxValidateVerifyCode(@RequestParam(name="verifyCode", required=true) String verifyCode, HttpServletRequest request) {
		/**
		 * 获取图片上真实的验证码
		 */
		String vCode = (String) request.getSession().getAttribute("vCode");
		return verifyCode.equalsIgnoreCase(vCode);
	}
}
