package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import qisi.bean.course.Course;
import qisi.bean.course.Exercise;

/**
 * @author : ddv
 * @date : 2018/10/29 下午3:25
 */

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
}
