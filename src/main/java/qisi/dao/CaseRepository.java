package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import qisi.bean.course.Case;

/**
 * @author : ddv
 * @date : 2018/11/6 下午4:01
 */

public interface CaseRepository extends JpaRepository<Case,Integer> {
}
