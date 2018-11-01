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

public interface ChapterRepository extends JpaRepository<Chapter,Integer> {

	/**根据课程ID查找课程的所有目录
	 * @param courseId
	 * @return
	 */
	@Query("from course_chapters where courseId=?1 order by chapterIndex")
	public List<Chapter> findChaptersByCourseId(String courseId);
}
