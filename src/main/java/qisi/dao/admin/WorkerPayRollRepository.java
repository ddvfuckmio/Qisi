package qisi.dao.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.work.WorkerPayRoll;

import java.util.Date;
import java.util.List;

/**
 * @author : ddv
 * @since : 2019/3/24 上午11:55
 */

public interface WorkerPayRollRepository extends JpaRepository<WorkerPayRoll, Integer>, JpaSpecificationExecutor<WorkerPayRoll> {

//	@Query("from worker_payrolls where payRollDate=?1")
//	List<WorkerPayRoll> getWorkerPayRollByDate(Date date);

}
