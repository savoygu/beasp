package org.huel.beasp.handler.user;

import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.huel.beasp.entity.common.City;
import org.huel.beasp.entity.common.Province;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.exception.BeaspFrontException;
import org.huel.beasp.handler.dto.AjaxOper;
import org.huel.beasp.service.common.CityService;
import org.huel.beasp.service.common.ProvinceService;
import org.huel.beasp.service.common.SchoolService;
import org.huel.beasp.service.user.UserService;
import org.huel.beasp.utils.EmailSender;
import org.huel.beasp.utils.FileUtils;
import org.huel.beasp.utils.MD5;
import org.huel.beasp.utils.Mail;
import org.huel.beasp.utils.MailUtils;
import org.huel.beasp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
/**
 * 个人设置：个人资料修改、头像设置、邮箱验证、密码修改
 * @author 001
 *
 */
@Controller
public class SettingsHandler {
	@Autowired private ProvinceService provinceService;
	@Autowired private CityService cityService;
	@Autowired private SchoolService schoolService;
	@Autowired private UserService userService;
	
	/**
	 * 个人设置入口
	 * @return
	 */
	@RequestMapping(value="/user/userinfo")
	public String userInfo() {
		return "redirect:/user/setprofile";//默认跳转
	}

	/**
	 * 找回密码之重设密码
	 * @return
	 */
	@RequestMapping(value="/account/resetpasspage", method=RequestMethod.POST)
	public String findPwd4(@RequestParam("email") String email, @RequestParam("newpwd") String newpwd,
			@RequestParam("repwd") String repwd, @RequestParam("fromurl") String fromurl,
			Map<String, Object> map, HttpServletRequest request) {
		if(email != null && !"".equals(email)) {
			User user = userService.getByEmail(email);
			if(user != null) {
				if(isNull(newpwd) && isNull(repwd) &&newpwd.equals(repwd)) {
					userService.updatePwd(user, MD5.MD5Encode(newpwd));//密码修改成功
					//单点登录
					if(fromurl != null && !"".equals(fromurl)) {
						fromurl = new String(Base64.decodeBase64(fromurl.trim().getBytes()));//获取解码后的url 
						//保存用户到 session
						request.getSession().setAttribute("user", userService.getById(user.getId()));
						return "redirect:"+fromurl;
					}
				} else{//密码不一致
					map.put("resetPwdMsg", "确认新密码与新密码不一致");
					return "front/user/settings/forgotreset";
				}
			} else
				throw new BeaspFrontException();
		} else
			throw new BeaspFrontException();
		return "front/user/settings/forgotsuccess";
	}
	
	/**
	 * 找回密码之重设界面
	 * @param active
	 * @param email
	 * @param date
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/account/resetpasspage", method=RequestMethod.GET)
	public String findPwd3(@RequestParam("active") String active, @RequestParam("email") String email,
			@RequestParam("date") String date, @RequestParam("fromurl") String fromurl, Map<String, Object> map) throws ParseException {
		String decodeEmail = new String(Base64.decodeBase64(email.getBytes()));
		if(userService.getCountByEmail(decodeEmail)) {//说明email 存在
			User user = userService.getByEmail(decodeEmail);
			if(user == null) 
				throw new BeaspFrontException();
			if(MD5.MD5Encode(decodeEmail+user.getPassword()+date).equals(active)) {//说明active ok, email ok
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date nowdate = new Date();
				if((nowdate.getTime()-sdf.parse(date).getTime())/(1000*60*60) <= 24) {//说明date ok
					map.put("email", decodeEmail);
					if(fromurl != null && !"".equals(fromurl)) {
						map.put("fromurl", fromurl);
					}
					return "front/user/settings/forgotreset";
				}
			}
		}
		return "front/user/settings/forgotout";//链接过期了
	}
	
	/**
	 * 找回密码之发送邮件
	 * @return
	 */
	@RequestMapping(value="/account/newforgot", method=RequestMethod.POST)
	public String findPwd2(@RequestParam("verifyCode") String verifyCode, 
			@RequestParam("email") String email, HttpServletRequest request,
			@RequestParam("fromurl") String fromurl) {
		/**
		 * 1. 校验
		 */
		Map<String, String> errors = validateForgotPwd(email, verifyCode, request);
		if(errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.setAttribute("email", email);
			if(fromurl != null && !"".equals(fromurl)) {
				request.setAttribute("fromurl", fromurl);
			}
			return "front/user/settings/newforgot";
		}
		User user = userService.getByEmail(email);
		if(user==null)
			throw new BeaspFrontException();
		/**
		 * 2. 发送邮件
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowdate = sdf.format(new Date());
		//模版文件
		Template template = Velocity.getTemplate("mailContent.html");
		VelocityContext context = new VelocityContext();
		context.put("active", MD5.MD5Encode(email+user.getPassword()+nowdate));//MD5
		context.put("email", new String(Base64.encodeBase64(email.getBytes())));//Base64加密
		context.put("date", nowdate);
		if(fromurl != null && !"".equals(fromurl)) {
			context.put("fromurl", fromurl);
		}
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		String content = writer.toString();
		EmailSender.send(email, "[书籍分享与交换平台]找回您的账户密码", content, "text/html");
		return "front/user/settings/forgotsent";
	}
	
	/**
	 * 校验
	 * @param email
	 * @param verifyCode
	 * @param request
	 * @return
	 */
	private Map<String, String> validateForgotPwd(String email, String verifyCode, 
			HttpServletRequest request) {
		Map<String, String> errors = new HashMap<String, String>();
		/**
		 * 1.校验Email
		 */
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email 不能为空");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email 格式不正确");
		} else if(!userService.getCountByEmail(email)) {
			errors.put("email", "请填写您的注册邮箱");
		}
		/**
		 * 2.校验验证码
		 */
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空");
		} else if(!verifyCode.equalsIgnoreCase((String)request.getSession().getAttribute("vCode"))){
			errors.put("verifyCode", "验证码错误");
		}
		return errors;
	}
	
	/**
	 * 忘记密码界面-单点登录
	 * @return
	 */
	@RequestMapping(value="/account/newforgot/fromurl/{fromurl}", method=RequestMethod.GET)
	public String forgotFromUrl(@PathVariable("fromurl") String fromurl,
			Map<String, Object> map) {
		if(fromurl != null && !"".equals(fromurl)) {
			map.put("fromurl", fromurl);
		}
		return "front/user/settings/newforgot";
	}
	
	/**
	 * 忘记密码界面
	 * @return
	 */
	@RequestMapping(value="/account/newforgot", method=RequestMethod.GET)
	public String forgot() {
		return "front/user/settings/newforgot";
	}
	
	/**
	 * 修改密码
	 * @param pwd 新密码
	 * @param repwd 确认密码
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/user/setresetpwd", method=RequestMethod.POST)
	public String updatePwd(@RequestParam("oldpwd") String oldpwd, 
			@RequestParam("newpwd") String newpwd, @RequestParam("repwd") String repwd, 
			Map<String, Object> map, HttpSession session) {
		User user = WebUtils.getUser(session);
		if(isNull(oldpwd) && isNull(newpwd) && isNull(repwd)) {
			String realOldPwd = MD5.MD5Encode(oldpwd);
			String realNewPwd = MD5.MD5Encode(newpwd);
			user = userService.getById(user.getId());
			if(!user.getPassword().equals(realOldPwd)) {//当前密码输入有误
				map.put("pwdMsg", "当前密码输入有误！");
			} else {//当前密码无误
				if(realOldPwd.equals(realNewPwd)) {//新密码与当前密码一致
					map.put("pwdMsg", "新密码不能与当前密码一样！");
					map.put("oldpwdval", oldpwd);
				} else {//新密码与当前密码不一致
					if(!newpwd.equals(repwd)) {//新密码与确认密码不一致
						map.put("pwdMsg", "新密码与确认密码不一致！");
						map.put("oldpwdval", oldpwd);
					} else {
						userService.updatePwd(user, realNewPwd);
						session.setAttribute("user", userService.getById(user.getId()));
						map.put("pwdMsg", "密码修改成功！");
						map.put("success", true);
					}
				}
			}
		}
		
		return "front/user/settings/setresetpwd";
	}
	/**
	 * 判断字符串是否为空
	 * @param param
	 * @return
	 */
	private boolean isNull(String param) {
		return param!=null && !"".equals(param);
	}
 	/**
	 * 修改密码界面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/user/setresetpwd", method=RequestMethod.GET)
	public String setResetPwd(Map<String, Object> map) {
		map.put("status", "4");
		return "front/user/settings/setresetpwd";
	}
	
	/**
	 * 去邮箱验证
	 * @return
	 */
	@RequestMapping("/user/openactivemail")
	public String openActiveEmail() {
		return "";
	}
	
	/**
	 * 激活邮箱
	 * @return
	 */
	@RequestMapping("/user/activatemail")
	public String activateEmail(@RequestParam("active") String active,
			Map<String, Object> map, HttpServletRequest request) {
		if(active != null && !"".equals(active)) {
			User user = WebUtils.getUser(request);
			if(user != null) {
				String code = MD5.MD5Encode(user.getUserName() + user.getPassword());
				if(user.getVerifyEmail() == 0) {//如果未验证
					if(code.equals(active)) {//如果校验码一致
						//修改校验邮箱为 已验证
						userService.updateVerifyEmail(user);
					} else {//如果校验码不一致
						request.setAttribute("activateEmailMsg", "你的邮箱验证链接无效！");
					}
				} else {//如果已验证
					request.setAttribute("activateEmailMsg", "你的邮箱验证已经验证了，就不要二次验证了！");
				}
			} else 
				return "redirect:/account/signin";
		} else {
			request.setAttribute("activateEmailMsg", "你的邮箱验证链接缺少参数！");
		}
		return "front/user/settings/activeemail";

	}
	
	/**
	 * 邮箱发送成功界面
	 * @return
	 */
	@RequestMapping("/user/setverifysent")
	public String setVerifySent() {
		return "front/user/settings/setverifysent";
	}
	
	/**
	 * 发送邮件
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user/verificationmail")
	public AjaxOper verificationEmail(@RequestParam("email") String email,
			HttpServletRequest request) {
		AjaxOper ajaxOper = new AjaxOper(0, "发送成功");
		if(email != null && !"".equals(email)) {
			User user = WebUtils.getUser(request);
			if(user != null) {
				/**
				 * 1.发邮件
				 */
				Properties prop = new Properties();
				try {
					prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
				} catch (IOException e) {
					ajaxOper = new AjaxOper(1, "发送失败");
				}
				/**
				 * 登录邮件服务器，得到session
				 */
				String host = prop.getProperty("host");//服务器主机
				String username = prop.getProperty("username");//登录名
				String password = prop.getProperty("password");//登录密码
				Session session = MailUtils.createSession(host, username, password);
				
				/**
				 * 创建Mail 对象
				 */
				String from = prop.getProperty("from");//发件人
				String to = email;//收件人
				String subject = prop.getProperty("subject");//主题
				String content = MessageFormat.format(prop.getProperty("content"), MD5.MD5Encode(user.getUserName()+user.getPassword()));
				Mail mail = new Mail(from, to, subject, content);
				
				/**
				 * 发送邮件
				 */
				try {
					MailUtils.send(session, mail);
				} catch (Exception e) {
					ajaxOper = new AjaxOper(1, "发送失败");
				}
			} else 
				throw new BeaspFrontException();
		}
		return ajaxOper;
	}
	
	/**
	 * 邮箱验证界面
	 * @return
	 */
	@RequestMapping(value="/user/setverifyemail", method=RequestMethod.GET)
	public String setVerifyEmail(Map<String, Object> map) {
		map.put("status", "3");
		return "front/user/settings/setverifyemail";
	}
	
	/**
	 * 头像上传
	 * @return
	 */
	@RequestMapping(value="/user/setavator", method=RequestMethod.POST)
	public String saveAvator(@RequestParam("photoName") MultipartFile photoName,
			HttpServletRequest request, Map<String, Object> map) {
		if(photoName != null) {
			if(!FileUtils.validateImageFileType(photoName, photoName.getContentType(), photoName.getOriginalFilename())) {
				System.out.println(photoName.getOriginalFilename()+"--"+photoName.getName()+"--"+photoName.getContentType()+"--"+photoName.getSize());
				map.put("photoNameErrorMsg", "文件格式不正确,只允许上传gif/jpg/png/bmp图片");
				return "front/user/settings/setavator";
			}
			String ext = FileUtils.getExt(photoName.getOriginalFilename());//获取扩展名
			String fileName = UUID.randomUUID().toString()+"."+ext;//构建文件名称
			User user = WebUtils.getUser(request);
			user.setPhotoName(fileName);
			userService.upload(user);//保存头像
			//保存图片到本地服务器
			try {
				FileUtils.savePhotoImageFile(photoName, user.getId(), fileName, request);
			} catch (Exception e) {}
		}
		return "redirect:/user/setavator";
	}
	
	/**
	 * 头像上传界面				
	 * @return
	 */
	@RequestMapping(value="/user/setavator", method=RequestMethod.GET)
	public String setAvator(Map<String, Object> map) {
		map.put("status", "2");
		return "front/user/settings/setavator";
	}
	
	/**
	 * 修改个人资料
	 * @return
	 */
	@RequestMapping(value="/user/setprofile", method=RequestMethod.POST)
	public String saveProfile(User user, HttpSession session) {
		User suser = WebUtils.getUser(session);
		/**设置项**/
		suser.setNickName(user.getNickName());
		suser.setProvince(user.getProvince());
		suser.setCity(user.getCity());
		suser.setSchool(user.getSchool());
		suser.setGender(user.getGender());
		suser.setPhone(user.getPhone());
		suser.setSummary(user.getSummary());
		userService.save(suser);//保存用户
		session.setAttribute("user", userService.getById(suser.getId()));//重新放到session中
		return "redirect:/user/setprofile";//重定向到当前页
	}
	
	/**
	 * 个人资料设置界面
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/user/setprofile", method=RequestMethod.GET)
	public String setProfile(Map<String, Object> map, HttpSession session) {
		User user = WebUtils.getUser(session);
		initZone(map, user);
		map.put("status", "1");//侧边栏状态
		return "front/user/settings/setprofile";
	}
	/**
	 * 初始化省市，学校
	 * @param map
	 * @param user
	 */
	private void initZone(Map<String, Object> map, User user) {
		Province province = provinceService.getByProvinceName(user.getProvince());//所在省份
		City city = cityService.getByCityName(user.getCity());//所在城市
		//School school = schoolService.getBySchoolName(user.getSchool());//所在学校
		map.put("provinces", provinceService.getAll());
		map.put("cities", cityService.getCitiesByProvinceId(province.getId()));
		map.put("schools", schoolService.getSchoolsByCityId(city.getId()));
	}
}
