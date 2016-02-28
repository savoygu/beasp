package org.huel.beasp.repository.user;

import org.huel.beasp.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * 用户持久层
 * @author 001
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{
	
	/**
	 * 修改用户密码
	 * @param newpwd
	 * @param id
	 */
	@Modifying
	@Query("UPDATE User u SET u.password=?1 WHERE u.id=?2")
	public void updatePwd(String newpwd, Integer id);
	
	/**
	 * 修改用户邮箱校验状态
	 * @param id
	 */
	@Modifying
	@Query("UPDATE User u SET u.verifyEmail=?1 WHERE u.id=?2")
	public void updateVerifyEmail(int verifyEmail, Integer id);
	
	/**
	 * 修改用户签名
	 * @param summary
	 * @param id
	 */
	@Modifying
	@Query("UPDATE User u SET u.summary=:summary WHERE u.id=:id")
	public void updateSigned(@Param("summary")String summary, @Param("id")Integer id);
	
	/**
	 * 按 用户名 和 密码 获取用户
	 * @param userName
	 * @param password
	 * @return
	 */
	public User getByUserNameAndPassword(String userName, String password);
	
	/**
	 * 按 Email 和 密码 获取用户
	 * @param email
	 * @param password
	 * @return
	 */
	public User getByEmailAndPassword(String email, String password);
	
	/**
	 * 按 Phone 和 密码 获取用户
	 * @param phone
	 * @param password
	 * @return
	 */
	public User getByPhoneAndPassword(String phone, String password);
	
	/**
	 * 按 Phone 获取用户(ajax 校验 phone 是否可用)
	 * @param phone
	 * @return
	 */
	public User getByPhone(String phone);
	
	@Query("SELECT count(u) FROM User u WHERE u.phone=?1")
	public long getCountByPhone(String phone);
	
	/**
	 * 按 Email 获取用户(ajax 校验 email 是否可用)
	 * @param email
	 * @return
	 */
	public User getByEmail(String email);
	
	@Query("SELECT count(u) FROM User u WHERE u.email=?1")
	public long getCountByEmail(String email);
	
	/**
	 * 按用户名 获取用户(ajax 校验 userName 是否可用)
	 * @param userName
	 * @return
	 */
	public User getByUserName(String userName);
	
	@Query("SELECT count(u) FROM User u WHERE u.userName=?1")
	public long getCountByUserName(String userName);
	
	/**
	 * 删除,锁定，恢复用户（修改为删除状态）
	 * @param state
	 * @param id
	 */
	@Modifying
	@Query("UPDATE User u SET u.status=?1 WHERE u.id=?2")
	public void deleteById(Integer status, Integer id);
}
