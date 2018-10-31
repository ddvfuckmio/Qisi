package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import qisi.bean.course.Code;

/**
 * @author : ddv
 * @date : 2018/10/30 上午11:49
 */

public interface CodeRepository extends JpaRepository<Code,Integer> {
}
