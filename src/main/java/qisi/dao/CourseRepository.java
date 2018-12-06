package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.course.Course;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:22
 */

public interface CourseRepository extends JpaRepository<Course, Integer> {

	/**
	 * 查询所有的课程
	 *
	 * @return List<Course>
	 */
	@Query("from courses order by id")
	public List<Course> findAllCourses();

	/**
	 * 查询已经发版的课程
	 *
	 * @return List<Course>
	 */
	@Query("from courses where isPublished = '1'")
	public List<Course> findAllPublishedCourses();

	/**
	 * 指定课程名查找
	 *
	 * @param courseName
	 * @return Course
	 */
	@Query("from courses where courseName=?1")
	public List<Course> findCourseByName(String courseName);

	/**
	 * 根据courseId修改courseName
	 *
	 * @param courseName
	 * @param courseId
	 */
	@Query("update courses set courseName=?1 where courseId=?2")
	@Modifying
	public void updateCourseName(String courseName, String courseId);

	/**
	 * 根据courseId查找
	 *
	 * @param courseId
	 * @return
	 */
	@Query("from courses where courseId=?1")
	public Course findCourseByCourseId(String courseId);


	/**
	 * 根据taskId查找course信息
	 *
	 * @param taskId
	 * @return
	 */
	@Query("select d from course_chapter_lesson_tasks a inner join course_chapter_lessons b on a.lessonId = b.lessonId inner join course_chapters c on b.chapterId = c.chapterId inner join courses d on c.courseId = d.courseId and a.taskId=?1")
	public Course findCourseByTaskId(String taskId);
}
