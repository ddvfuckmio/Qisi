package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : ddv
 * @date : 2018/11/7 下午5:07
 */

public interface ProgressRepository extends JpaRepository<qisi.bean.course.Progress, Integer> {
}
