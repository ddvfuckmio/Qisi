package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.course.Course;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:22
 */

public interface CourseRepository extends JpaRepository<Course, Integer> {

	/**
	 * 查找所有的课程
	 * @return
	 */
	@Query("from courses order by id")
	public List<Course> findAllCourses();

	/**
	 * 指定课程名查找
	 * @param courseName
	 * @return
	 */
	@Query("from courses where courseName=?1")
	public Course findCourseByName(String courseName);
}
