package qisi.dao;

/**
 * @author : ddv
 * @date : 2018/10/24 上午11:45
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import qisi.bean.user.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * 根据username查找用户
	 *
	 * @param username
	 * @return user
	 */
	@Query("from users where username=?1")
	public User findUserByUsername(String username);


	/**
	 * 注册之前检查表中是否存在相同的username,phone,email 任何一项匹配则返回匹配的用户信息
	 *
	 * @param username
	 * @param phone
	 * @param email
	 * @return user
	 */
	@Query("from users where username=?1 or phone=?2 or email=?3")
	public List<User> findUserIfExist(String username, String phone, String email);


	/**
	 * 修改个人密码
	 *
	 * @param username
	 * @param password
	 * @return
	 */

	@Query("update users set password=?2 where username=?1")
	@Modifying
	public void updatePassword(String username, String password);


}
