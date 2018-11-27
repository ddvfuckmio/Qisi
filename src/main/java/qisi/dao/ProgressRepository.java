package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import qisi.bean.course.Progress;

/**
 * @author : ddv
 * @date : 2018/11/7 下午5:07
 */

public interface ProgressRepository extends JpaRepository<qisi.bean.course.Progress, Integer> {
	/**
	 * 查找用户对应课程的进度
	 *
	 * @param username 用户名
	 * @param courseId 课程id
	 * @return
	 */
	@Query("from user_course_progress where username=?1 and courseId=?2")
	Progress findProgressByUsernameAndCourseId(String username, String courseId);

	/**
	 * 修改progress表数据
	 *
	 * @param progressId
	 * @param chapterId
	 * @param lessonId
	 * @param taskId
	 */
	@Transactional
	@Query("update user_course_progress set chapterId=?2,lessonId=?3,taskId=?4 where progressId=?1")
	@Modifying
	void updateProgress(String progressId, String chapterId, String lessonId, String taskId);
}
