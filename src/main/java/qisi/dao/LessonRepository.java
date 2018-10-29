package qisi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import qisi.bean.course.Lesson;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:53
 */

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
