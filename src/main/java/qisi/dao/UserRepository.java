package qisi.dao;

/**
 * @author : ddv
 * @date   : 2018/10/24 上午11:45
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 *	根据account查找用户
	 * @param account
	 * @return user
	 */
	@Query("from users where account=?1")
	public User findUserByAccount(String account);


	/**
	 * 注册之前检查表中是否存在相同的account,phone,email 任何一项匹配则返回匹配的用户信息
	 * @param account
	 * @param phone
	 * @param email
	 * @return user
	 */
	@Query("from users where account=?1 or phone=?2 or email=?3")
	public User findUserIfExist(String account,String phone,String email);

}
