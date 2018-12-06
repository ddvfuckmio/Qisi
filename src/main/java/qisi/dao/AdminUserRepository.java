package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.admin.AdminUser;

/**
 * @author : ddv
 * @date : 2018/12/6 下午3:37
 */

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {

	/**
	 * 查询adminUser
	 *
	 * @param username
	 * @return
	 */
	@Query("from admin_users where username=?1")
	public AdminUser findAdminUserByUsername(String username);
}
