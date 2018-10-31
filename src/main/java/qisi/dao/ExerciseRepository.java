package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.course.Course;
import qisi.bean.course.Exercise;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/29 下午3:25
 */

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

	/**根据目录ID查询目录下的所有训练
	 * @param lessonId
	 * @return List<Exercise>
	 */
	@Query("from course_lesson_exercises where lessonId=?1 order by exerciseId")
	public List<Exercise> findExercisesByLessonId(String lessonId);

	/**根据训练ID查找该训练信息
	 * @param exerciseId
	 * @return
	 */
	@Query("from course_lesson_exercises where exerciseId=?1")
	public Exercise findExerciseByExerciseId(String exerciseId);

}
