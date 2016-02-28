package org.huel.beasp.handler.user;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.huel.beasp.entity.common.City;
import org.huel.beasp.entity.common.School;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.service.common.CityService;
import org.huel.beasp.service.common.ProvinceService;
import org.huel.beasp.service.common.SchoolService;
import org.huel.beasp.service.user.UserService;
import org.huel.beasp.utils.Constants;
import org.huel.beasp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户控制层:
 * 	ajax操作：用户Phone校验、用户Email校验、用户UserName校验、获取城市、获取学校
 * @author 001
 *
 */
@RequestMapping("/admin/user")
@Controller
public class UserHandler {
	@Autowired private UserService userService;
	@Autowired private ProvinceService provinceService;
	@Autowired private CityService cityService;
	@Autowired private SchoolService schoolService;
	
	/**
	 * ajax 校验 Phone
	 * @param phone
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/ajaxValidatePhone", method=RequestMethod.POST)
	public void ajaxValidatePhone(@RequestParam(value="phone", required=true) String phone,
			HttpServletResponse response) throws IOException {
		setResponse(response, !userService.getCountByPhone(phone));
	}
	
	/**
	 * ajax 校验 Email
	 * @param userName
	 * @throws IOException 
	 */
	@RequestMapping(value="/ajaxValidateEmail", method=RequestMethod.POST)
	public void ajaxValidateEmail(@RequestParam(value="email", required=true) String email,
			HttpServletResponse response) throws IOException {
		/*String result = null;
		if(userService.getCountByEmail(email)) {
			result = "<font color='red'>该Email已经被使用!</font>";
		} else {
			result = "<font color='green'>该Email可以使用!</font>";
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);*/
		setResponse(response, !userService.getCountByEmail(email));
	}
	
	/**
	 * ajax 校验用户名
	 * @param userName
	 * @throws IOException 
	 */
	@RequestMapping(value="/ajaxValidateUserName", method=RequestMethod.POST)
	public void ajaxValidateUserName(@RequestParam(value="userName", required=true) String userName,
			HttpServletResponse response) throws IOException {
		setResponse(response, !userService.getCountByUserName(userName));
	}
	
	private void setResponse(HttpServletResponse response, boolean result) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result);
	}
	
	/**
	 * ajax 获取区和县
	 * @param cityId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxGetSchools", method=RequestMethod.GET)
	public List<School> ajaxGetAreas(@RequestParam(value="cityId", required=true) Integer cityId) {
		List<School> schools = schoolService.getSchoolsByCityId(cityId);
		return schools;
	}
	
	/**
	 * ajax 获取城市
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxGetCities", method=RequestMethod.GET)
	public List<City> ajaxGetCities(@RequestParam(value="provinceId", required=true) Integer provinceId, HttpServletResponse response) {
		List<City> cities = cityService.getCitiesByProvinceId(provinceId);
		return cities;
	}
	
	/**
	 * 用户添加
	 * @return
	 */
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public String save(User user) {
		userService.save(user);
		return "redirect:/admin/user/users";
	}
	
	/**
	 * 用户添加界面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("user", new User());
		map.put("provinces", provinceService.getAll());
		return "admin/user/user/input";
	}
	
	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping("/users")
	public String list(@RequestParam(value="pageNo", required=false, defaultValue="1") String pageNoStr,
			Map<String, Object> map,
			@RequestParam(value="status", required=false) Integer status) {
		Page<User> users;
		if(status != null && status >=0 && status <3) {
			users = userService.getPageByStatus(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN, status);	
		} else {
			users = userService.getPage(WebUtils.getCurrentPage(pageNoStr), Constants.PAGE_SIZE_ADMIN);
		}
		map.put("page", users);
		return "admin/user/user/list";
	}
}
