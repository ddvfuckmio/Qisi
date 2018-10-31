package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qisi.bean.course.Code;
import qisi.bean.course.Course;
import qisi.bean.course.Exercise;
import qisi.bean.course.Lesson;
import qisi.dao.CodeRepository;
import qisi.dao.CourseRepository;
import qisi.dao.ExerciseRepository;
import qisi.dao.LessonRepository;

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
	private LessonRepository lessonRepository;

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Autowired
	private CodeRepository codeRepository;

	/**
	 * course业务
	 */

	public List<Course> findAllCourses() {
		return courseRepository.findAllCourses();
	}

	public Course findCourseByName(String courseName) {
		return courseRepository.findCourseByName(courseName);
	}

	public void saveCourses(List<Course> courseList) {
		courseRepository.saveAll(courseList);
	}

	/**
	 * lesson业务
	 */

	public List<Lesson> findLessonsByCourseId(String courseId) {
		return lessonRepository.findLessonsByCourseId(courseId);
	}

	public void saveLessons(List<Lesson> lessonList) {
		lessonRepository.saveAll(lessonList);
	}


	/**
	 * exercise业务
	 */

	public List<Exercise> findExercises() {
		return exerciseRepository.findAll();
	}

	public List<Exercise> findExercisesByLessonId(String lessonId) {
		return exerciseRepository.findExercisesByLessonId(lessonId);
	}

	public Exercise findExerciseByExerciseId(String exerciseId) {
		return exerciseRepository.findExerciseByExerciseId(exerciseId);
	}

	/**
	 * code业务
	 */

	public void saveCode(Code code) {
		codeRepository.save(code);
	}

	public Course findCourseByCourseId(String courseId) {
		return courseRepository.findCourseByCourseId(courseId);
	}
}
