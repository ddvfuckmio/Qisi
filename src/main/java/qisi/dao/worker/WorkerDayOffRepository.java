package qisi.dao.worker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import qisi.bean.work.WorkerDayOff;

import java.util.Date;
import java.util.List;

/**
 * @author : ddv
 * @date : 2019/1/30 上午12:46
 */

public interface WorkerDayOffRepository extends JpaRepository<WorkerDayOff, Integer> {

	@Query(nativeQuery = true, value = "select count(*) from worker_dayOffs where username=?1")
	public int getWorkerDayOffsCount(String username);

	@Query(nativeQuery = true, value = "select * from worker_dayOffs where username=?1 and endDate>=?2 limit ?3 , ?4")
	public List<WorkerDayOff> findWorkerDayOffsByPage(String username, Date now, int start, int count);

	@Query("from worker_dayOffs where id=?1")
	public WorkerDayOff findWorkerDayOffById(int id);

	@Transactional
	@Modifying
	@Query("delete from worker_dayOffs where id=?1")
	public void deleteWorkerDayOff(int id);
}
