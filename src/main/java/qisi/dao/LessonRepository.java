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
	/**
	 * 条件查询
	 *
	 * @param chapterId
	 * @return
	 */
	@Query("from course_chapter_lessons where chapterId =?1")
	List<Lesson> findByChapterId(String chapterId);

	/**
	 * 条件查询
	 *
	 * @param taskId
	 * @return
	 */
	@Query("select a from course_chapter_lessons a inner join course_chapter_lesson_tasks b on a.lessonId = b.lessonId where b.taskId =?1")
	Lesson findByTaskId(String taskId);
}
