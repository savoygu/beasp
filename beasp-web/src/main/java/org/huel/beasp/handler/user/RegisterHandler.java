package org.huel.beasp.handler.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.exception.BeaspException;
import org.huel.beasp.service.common.ProvinceService;
import org.huel.beasp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 用户注册控制层
 * @author 001
 *
 */
@Controller
public class RegisterHandler {
	@Autowired private ProvinceService provinceService;
	@Autowired private UserService userService;
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(value="/account/signup", method=RequestMethod.POST)
	public String signUp(User user, 
			HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("fromurl") String fromurl) {
		/**
		 * 1. 校验
		 */
		Map<String, String> errors = validateRegist(user);
		if(errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.setAttribute("user", user);
			return "front/user/signup";
		}
		/**
		 * 2. 保存用户
		 */
		try {
			userService.save(user);
		} catch (Exception e) {
			throw new BeaspException();
		}
		//单点登录
		if(fromurl != null && !"".equals(fromurl)) {
			fromurl = new String(Base64.decodeBase64(fromurl.trim().getBytes()));//获取解码后的url 
			//保存用户到 session
			request.getSession().setAttribute("user", userService.getById(user.getId()));
			return "redirect:"+fromurl;
		}
		return "redirect:/account/signin";
	}
	
	/**
	 * 注册校验
	 * @param user
	 * @return
	 */
	public Map<String, String> validateRegist(User user) {
		Map<String, String> errors = new HashMap<String, String>();
		/**
		 * 1.校验用户名
		 */
		if(user.getUserName() == null || user.getUserName().trim().isEmpty()) {
			errors.put("userName", "用户名不能为空");
		} else if(user.getUserName().length()<3 || user.getUserName().length() > 20) {
			errors.put("userName", "用户名长度必须在3~20之间");
		} else if(userService.getCountByUserName(user.getUserName())) {
			errors.put("userName", "用户名已经被注册了");
		}
		/**
		 * 2.校验密码
		 */
		if(user.getPassword() == null || user.getPassword().trim().isEmpty()) {
			errors.put("password", "密码不能为空");
		} else if(user.getPassword().length()<6 || user.getPassword().length() > 20) {
			errors.put("password", "密码长度必须在6~20之间");
		}
		/**
		 * 3.校验昵称
		 */
		if(user.getNickName() == null || user.getNickName().trim().isEmpty()) {
			errors.put("nickName", "昵称不能为空");
		} else if(user.getNickName().length()<3 || user.getNickName().length() > 20) {
			errors.put("nickName", "昵称长度必须在3~20之间");
		}
		/**
		 * 4.校验性别
		 */
		if(user.getGender() == null) {
			errors.put("gender", "性别不能为空");
		} 
		/**
		 * 5.校验Email
		 */
		if(user.getEmail() == null || user.getEmail().trim().isEmpty()) {
			errors.put("email", "Email 不能为空");
		} else if(!user.getEmail().matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email 格式不正确");
		} else if(userService.getCountByEmail(user.getEmail())) {
			errors.put("email", "Email 已经被注册了");
		}
		/**
		 * 6.校验手机号码
		 */
		if(user.getPhone() == null || user.getPhone().trim().isEmpty()) {
			errors.put("phone", "手机号不能为空");
		} else if(!user.getPhone().matches("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$")) {
			errors.put("phone", "手机号格式不正确");
		} else if(userService.getCountByPhone(user.getPhone())) {
			errors.put("phone", "手机号已经被注册了");
		}
		/**
		 * 7.校验所在地
		 */
		if(user.getProvince() == null) {
			errors.put("province", "你还没选择所在省");
		} else if(user.getCity() == null){
			errors.put("city", "你还没选择所在市");
		}
		/**
		 * 8.校验所在学校
		 */
		if(user.getSchool() == null) {
			errors.put("school", "你还没选择所在学校");
		}
		return errors;
	}
	
	/**
	 * 注册界面
	 * @return
	 */
	@RequestMapping(value="/account/signup/fromurl/{fromurl}", method=RequestMethod.GET)
	public String signUp(@PathVariable("fromurl") String fromurl, Map<String, Object> map) {
		map.put("provinces", provinceService.getAll());
		if(fromurl != null && !"".equals(fromurl)) {
			map.put("fromurl", fromurl);//带到注册界面
		}
		return "front/user/signup";
	}
	
	/**
	 * 注册界面
	 * @return
	 */
	@RequestMapping(value="/account/signup", method=RequestMethod.GET)
	public String signUp(Map<String, Object> map) {
		map.put("provinces", provinceService.getAll());
		return "front/user/signup";
	}
}
