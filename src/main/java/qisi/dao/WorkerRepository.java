package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
