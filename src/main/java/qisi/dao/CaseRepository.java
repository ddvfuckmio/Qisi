package qisi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qisi.bean.course.Case;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/11/6 下午4:01
 */

public interface CaseRepository extends JpaRepository<Case, Integer> {
	/**
	 * 根据任务Id查询测试用例
	 *
	 * @param taskId 任务Id
	 * @return 测试用例列表
	 */
	@Query("from course_chapter_lesson_task_cases where taskId=?1")
	List<Case> findCasesByTaskId(String taskId);
}
