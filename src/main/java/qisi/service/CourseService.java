package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qisi.bean.course.*;
import qisi.dao.*;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:23
 */

@Service
public class CourseService {

	/**
	 * course业务层封装了course,lesson,exercise,code等持久层对象
	 */

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ChapterRepository chapterRepository;

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private CodeRepository codeRepository;

	/**
	 * course业务
	 */

	public Course findCourseByCourseId(String courseId) {
		return courseRepository.findCourseByCourseId(courseId);
	}

	public List<Course> findAllCourses() {
		return courseRepository.findAllCourses();
	}

	public Course findCourseByName(String courseName) {
		return courseRepository.findCourseByName(courseName);
	}

	public void saveCourses(List<Course> courses) {
		courseRepository.saveAll(courses);
	}


	/**
	 * chapter业务
	 */

	public List<Chapter> findChaptersByCourseId(String courseId) {
		return chapterRepository.findChaptersByCourseId(courseId);
	}

	public void saveChapters(List<Chapter> chapters) {
		chapterRepository.saveAll(chapters);
	}


	/**
	 * lesson业务
	 */

	/**
	 * task业务
	 */

	public List<Task> findTasks() {
		return taskRepository.findAll();
	}

	public List<Task> findTasksByLessonId(String lessonId) {
		return taskRepository.findTasksByLessonId(lessonId);
	}

	public Task findTaskByTaskId(String taskId) {
		return taskRepository.findTasksByTaskId(taskId);
	}

	/**
	 * code业务
	 */

	public void saveCode(Code code) {
		codeRepository.save(code);
	}


}
