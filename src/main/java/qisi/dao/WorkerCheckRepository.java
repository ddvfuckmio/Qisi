package qisi.dao;

import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import qisi.bean.work.WorkerCheck;

import java.util.Date;

/**
 * @author : ddv
 * @date : 2019/1/27 下午3:11
 */

public interface WorkerCheckRepository extends JpaRepository<WorkerCheck, Integer> {

	@Query("from worker_checks where username=?1 and checkDay=?2")
	WorkerCheck findWorkerCheckByUsernameAndDate(String username, Date formatDate);

	@Query("from worker_checks where username=?1 and checkDay=?2")
	WorkerCheck findWorkerCheckByMonth(String username, Date formatDate);

	@Transactional
	@Modifying
	@Query("update worker_checks set signOut=?1 where username=?2 and checkDay=?3")
	void updateWorkerCheckSignOut(Date signOut, String username,Date checkDay);
}
