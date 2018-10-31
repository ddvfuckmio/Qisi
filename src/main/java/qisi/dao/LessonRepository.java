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

	/**根据课程ID查找课程的所有目录
	 * @param courseId
	 * @return
	 */
	@Query("from course_lessons where courseId=?1 order by lessonIndex")
	public List<Lesson> findLessonsByCourseId(String courseId);
}
