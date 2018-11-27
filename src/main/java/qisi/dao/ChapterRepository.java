package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.course.Chapter;
import qisi.bean.course.Lesson;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/11/1 下午4:50
 */

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

	/**
	 * 返回课程ID查找课程的所有目录
	 *
	 * @param courseId
	 * @return
	 */
	@Query("from course_chapters where courseId=?1 order by chapterIndex")
	public List<Chapter> findChaptersByCourseId(String courseId);

	/**
	 * 返回某课程总目录数
	 *
	 * @param courseId
	 * @return
	 */
	public Integer countByCourseId(String courseId);

	/**
	 * 条件查询
	 *
	 * @param lessonId
	 * @return
	 */
	@Query("from course_chapters where lessonId =?1")
	Chapter findByLessonId(String lessonId);
}
