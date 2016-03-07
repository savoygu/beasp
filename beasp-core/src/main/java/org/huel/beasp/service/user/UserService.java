package org.huel.beasp.service.user;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.huel.beasp.entity.common.City;
import org.huel.beasp.entity.common.Province;
import org.huel.beasp.entity.common.School;
import org.huel.beasp.entity.user.User;
import org.huel.beasp.exception.BeaspException;
import org.huel.beasp.repository.common.SchoolRepository;
import org.huel.beasp.repository.user.UserRepository;
import org.huel.beasp.service.common.CityService;
import org.huel.beasp.service.common.ProvinceService;
import org.huel.beasp.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务逻辑层
 * @author 001
 *
 */
@Service("userService")
public class UserService {
	@Autowired private UserRepository userRespository;
	@Autowired private CityService cityService;
	@Autowired private ProvinceService provinceService;
	@Autowired private SchoolRepository schoolRepository;
	
	/**
	 * 修改密码
	 * @param user
	 * @param newpwd
	 */
	@Transactional
	public void updatePwd(User user, String newpwd) {
		userRespository.updatePwd(newpwd, user.getId());
	} 
	
	/**
	 * 修改用户邮箱状态为己验证
	 * @param user
	 */
	@Transactional
	public void updateVerifyEmail(User user) {
		userRespository.updateVerifyEmail(1, user.getId());
	}
	
	/**
	 * 修改用户签名
	 * @param summary
	 * @param id
	 */
	@Transactional
	public void updateSigned(String summary, Integer id) {
		userRespository.updateSigned(summary, id);
	}
	
	/**
	 * 获取所有用户
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<User> getPage(int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize);
		return userRespository.findAll(pageable);
	}
	
	/**
	 * 按状态获取用户
	 * @param pageNo
	 * @param pageSize
	 * @param status
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<User> getPageByStatus(int pageNo, int pageSize, final int status) {
		Pageable pageable = new PageRequest(pageNo-1, pageSize);
		Specification<User> specification = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.equal(root.get("status"), status);
				return predicate;
			}
		};
		return userRespository.findAll(specification, pageable);
	}
	
	/**
	 * 按 Email 和 密码 获取用户
	 * @param email
	 * @param password
	 * @return
	 */
	public User getByEmailAndPassword(String email, String password) {
		return userRespository.getByEmailAndPassword(email, MD5.MD5Encode(password));
	}
	
	/**
	 * 按 Phone 和 密码 获取用户
	 * @param phone
	 * @param password
	 * @return
	 */
	public User getByPhoneAndPassword(String phone, String password) {
		return userRespository.getByPhoneAndPassword(phone, MD5.MD5Encode(password));
	}
	
	/**
	 * 按 用户名 和 密码 获取用户
	 * @param userName
	 * @param password
	 * @return
	 */
	public User getByUserNameAndPassword(String userName, String password) {
		return userRespository.getByUserNameAndPassword(userName, MD5.MD5Encode(password));
	}
	
	/**
	 * 按 phone 获取用户
	 * @param phone
	 * @return
	 */
	public User getByPhone(String phone) {
		return userRespository.getByPhone(phone);
	}
	
	/**
	 * 按 Phone 查询用户是否存在
	 * @param phone
	 * @return
	 */
	public boolean getCountByPhone(String phone) {
		return userRespository.getCountByPhone(phone) > 0;
	}
	
	/**
	 * 按 Email 获取用户
	 * @param email
	 * @return
	 */
	public User getByEmail(String email) {
		return userRespository.getByEmail(email);
	}
	
	/**
	 * 按 Email 查询用户是否存在
	 * @param email
	 * @return
	 */
	public boolean getCountByEmail(String email) {
		return userRespository.getCountByEmail(email) > 0;
	}
	
	/**
	 * 按 用户名 获取用户
	 * @param userName
	 * @return
	 */
	@Transactional(readOnly=true)
	public User getByUserName(String userName) {
		return userRespository.getByUserName(userName);
	}
	
	/**
	 * 按 用户名 查询用户是否存在
	 * @param userName
	 * @return true 表示存在, false 表示不存在
	 */
	public boolean getCountByUserName(String userName) {
		return userRespository.getCountByUserName(userName) > 0;
	}
	
	/**
	 * 按用户 id 获取 用户
	 * @param id
	 * @return
	 */
	public User getById(Integer id) {
		return userRespository.findOne(id);
	}
	
	/**
	 * 删除,锁定,恢复用户状态（非真正意义上，状态修改为2）
	 */
	@Transactional
	public void delete(Integer status, Integer id) {
		User user = userRespository.findOne(id);
		if(user == null) {
			throw new BeaspException("你要操作的用户不存在!");
		}
		if(user.getStatus() == status) {
			throw new BeaspException("请你不要重复操作!");
		}
		userRespository.deleteById(status, id);
	}
	
	/**
	 * 上传头像
	 * @param user
	 */
	@Transactional
	public void upload(User user) {
		userRespository.saveAndFlush(user);
	}
	
	/**
	 * 保存、更新用户
	 * @param user
	 */
	@Transactional
	public User save(User user) {
		if(user.getId() == null) {
			user.setPassword(MD5.MD5Encode(user.getPassword()));;//MD5加密
		}
		Province province = provinceService.getByProvinceId(Integer.parseInt(user.getProvince()));//用户所在省是否存在
		if(province != null) {
			user.setProvince(province.getName());
		}else 
			throw new BeaspException();
		City city = cityService.getById(Integer.parseInt(user.getCity()));//用户所在市是否存在
		if(city != null) {
			user.setCity(city.getName());
		} else
			throw new BeaspException();
		School school = schoolRepository.getById(Integer.parseInt(user.getSchool()));//用户所在学校是否存在
		if(school != null) {
			user.setSchool(school.getName());
		} else 
			throw new BeaspException();
		
		return userRespository.saveAndFlush(user);
	}

}
