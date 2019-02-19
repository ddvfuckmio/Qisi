package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import qisi.bean.work.Worker;

/**
 * @author : ddv
 * @date : 2019/1/24 下午2:36
 */

public interface WorkerRepository extends JpaRepository<Worker, Integer> {

	/**
	 * 根据用户名查找员工
	 *
	 * @param username
	 * @return
	 */
	@Query("from workers where username=?1")
	public Worker findWorkerByUsername(String username);

	/**
	 * 修改用户密码
	 *
	 * @param username
	 * @param newPassword
	 */
	@Transactional
	@Modifying
	@Query("update workers set password=?2 where username=?1")
	void updatePassword(String username, String newPassword);
}
