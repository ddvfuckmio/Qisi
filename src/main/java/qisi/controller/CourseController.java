package qisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qisi.bean.course.Code;
import qisi.bean.course.Course;
import qisi.bean.course.Exercise;
import qisi.bean.course.Lesson;
import qisi.exception.courseException.CourseNotExistException;
import qisi.service.CourseService;
import qisi.utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:25
 */

@Controller
public class CourseController {

	@Autowired
	private CourseService courseService;


	/**
	 * 查询所有课程
	 */
	@ResponseBody
	@GetMapping("/courses")
	public List<Course> getCourses() {
		return courseService.findAllCourses();
	}

	/**
	 * courseName
	 * 按课程名获取课程信息
	 */
	@ResponseBody
	@GetMapping("/course")
	public Course getCourseByName(@RequestParam("courseName") String courseName) {
		Course course = courseService.findCourseByName(courseName);
		if (course == null) {
			throw new CourseNotExistException(courseName);
		}
		return course;
	}

	/**
	 * courseId
	 * 按课程ID获取课程信息
	 */
	@ResponseBody
	@GetMapping("/course/{courseId}")
	public Course getCourseByCourseId(@PathVariable("courseId") String courseId) {
		Course course = courseService.findCourseByCourseId(courseId);
		if (course == null) {
			throw new CourseNotExistException(courseId);
		}
		return course;
	}


	/**
	 * courseId
	 * 按课程ID获取该课程下的所有目录
	 */
	@ResponseBody
	@GetMapping("/course/{courseId}/lessons")
	public List<Lesson> findLessonsByCourseId(@PathVariable String courseId) {
		List<Lesson> lessons = courseService.findLessonsByCourseId(courseId);
		return lessons;
	}

	/**
	 * lessonId
	 * 按目录ID获取目录信息
	 */
	@GetMapping("/lesson/{lessonId}")
	public List<Exercise> findExercisesByLessonId(@PathVariable String lessonId) {
		List<Exercise> exercises = courseService.findExercisesByLessonId(lessonId);
		return exercises;
	}


	/**
	 * 获取所有训练信息
	 */
	@ResponseBody
	@GetMapping("/exercises")
	public List<Exercise> findExercises() {
		return courseService.findExercises();
	}

	/**
	 * 根据训练ID获取训练信息
	 */
	@ResponseBody
	@GetMapping("/exercise/{exerciseId}")
	public Exercise findExerciseByExercise(@PathVariable("exerciseId") String exerciseId) {
		return courseService.findExerciseByExerciseId(exerciseId);
	}

	/**
	 * 提交代码
	 *
	 * @param code
	 * @param exerciseId
	 * @return
	 */
	@ResponseBody
	@PostMapping("/code/{exerciseId}")
	public String commitCode(@RequestBody Code code, @PathVariable String exerciseId) {
		Exercise exercise = courseService.findExerciseByExerciseId(exerciseId);
		code.setExerciseId(exerciseId);
		code.setCodeId(Utils.getUUID());
		code.setCreatedAt(new Date());
		courseService.saveCode(code);
		return "保存完毕!";
	}


}
