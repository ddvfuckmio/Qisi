package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.course.Task;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/29 下午3:25
 */

public interface TaskRepository extends JpaRepository<Task, Integer> {

	/**
	 * 根据lessonId查找其下的所有任务
	 *
	 * @param lessonId
	 * @return List<Task>
	 */
	@Query("from course_chapter_lesson_tasks where lessonId=?1")
	public List<Task> findTasksByLessonId(String lessonId);

	/**
	 * 根据ID查找该任务
	 *
	 * @param taskId
	 * @return
	 */
	@Query("from course_chapter_lesson_tasks where taskId=?1")
	public Task findTasksByTaskId(String taskId);

}
