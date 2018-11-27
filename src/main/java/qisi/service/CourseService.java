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

	@Autowired
	private CaseRepository caseRepository;

	@Autowired
	private ProgressRepository progressRepository;

	/**
	 * course业务
	 */

	public Course findCourseByCourseId(String courseId) {
		return courseRepository.findCourseByCourseId(courseId);
	}

	public List<Course> findAllCourses() {
		return courseRepository.findAllCourses();
	}

	public List<Course> findCourseByName(String courseName) {
		return courseRepository.findCourseByName(courseName);
	}

	public void saveCourses(List<Course> courses) {
		courseRepository.saveAll(courses);
	}

	public Course findCourseByTaskId(String taskId) {
		return courseRepository.findCourseByTaskId(taskId);
	}

	/**
	 * chapter业务
	 */

	public List<Chapter> findAllChapters() {
		return chapterRepository.findAll();
	}

	public List<Chapter> findChaptersByCourseId(String courseId) {
		return chapterRepository.findChaptersByCourseId(courseId);
	}

	public Integer countByCourseId(String courseId) {
		return chapterRepository.countByCourseId(courseId);
	}

	public void saveChapters(List<Chapter> chapters) {
		chapterRepository.saveAll(chapters);
	}


	/**
	 * lesson业务
	 */
	public List<Lesson> findAllLessons() {
		return lessonRepository.findAll();
	}

	public void saveLessons(List<Lesson> lessons) {
		lessonRepository.saveAll(lessons);
	}

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

	public void saveTasks(List<Task> tasks) {
		taskRepository.saveAll(tasks);
	}


	/**
	 * 测试用例业务
	 */
	public List<Case> findCasesByTaskId(String taskId){
		return caseRepository.findCasesByTaskId(taskId);
	}

	/**
	 * code业务
	 */

	public void saveCode(Code code) {
		codeRepository.save(code);
	}

	public void saveCases(List<Case> cases) {
		caseRepository.saveAll(cases);
	}


	/**
	 * 用户进度业务
	 */
	public void saveProgress(Progress progress) {
		progressRepository.save(progress);
	}

	public List<Lesson> findLessonsByChapterId(String chapterId) {
		return lessonRepository.findByChapterId(chapterId);
	}

	public Progress findProgressByUsernameAndCourseId(String username, String courseId) {
		return progressRepository.findProgressByUsernameAndCourseId(username,courseId);
	}

	public Lesson findLessonByTaskId(String taskId) {
		return lessonRepository.findByTaskId(taskId);
	}

	public Chapter findChapterByLessonId(String lessonId) {
		return chapterRepository.findChapterByLessonId(lessonId);
	}

	public void updateProgress(Progress progress) {
		progressRepository.updateProgress(progress.getProgressId(),progress.getChapterId(),progress.getLessonId(),progress.getTaskId());
	}
}
