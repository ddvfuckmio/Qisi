package qisi.dao;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jdbc.Work;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import qisi.bean.work.Worker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author : ddv
 * @date : 2019/1/24 下午2:36
 */

public interface WorkerRepository extends JpaRepository<Worker, Integer>, JpaSpecificationExecutor<Worker> {

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

	@Query(nativeQuery = true, value = "select * from workers")
	List<Worker> findAllWorkers();

}
