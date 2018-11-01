package qisi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.course.Lesson;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:53
 */

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
