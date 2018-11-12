package qisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qisi.bean.course.*;
import qisi.bean.query.CourseChaptersQuery;
import qisi.bean.query.CoursesQuery;
import qisi.service.CourseService;
import qisi.utils.Utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

	@GetMapping("/courses")
	public String findAllCourses(Map<String, Object> map) {
		CoursesQuery coursesQuery = new CoursesQuery();
		coursesQuery.setCourses(courseService.findAllCourses());
		coursesQuery.setTotal(coursesQuery.getCourses().size());
		map.put("courses",coursesQuery.getCourses());
		return "main";
	}

	/**
	 * courseName
	 * 按课程名获取课程信息
	 */
	@ResponseBody
	@GetMapping("/course")
	public Course findCourseByName(@RequestParam("courseName") String courseName) {
		return courseService.findCourseByName(courseName);
	}

	/**
	 * courseId
	 * 按课程ID获取课程信息
	 */
	@ResponseBody
	@GetMapping("/course/{courseId}")
	public Course findCourseByCourseId(@PathVariable("courseId") String courseId) {
		return courseService.findCourseByCourseId(courseId);
	}


	/**
	 * courseId
	 * 按课程ID获取该课程下的所有目录
	 */
	@ResponseBody
	@GetMapping("/course/{courseId}/chapters")
	public CourseChaptersQuery findChaptersByCourseId(@PathVariable String courseId) {
		CourseChaptersQuery courseChaptersQuery = new CourseChaptersQuery();
		Course course = courseService.findCourseByCourseId(courseId);
		List<Chapter> chapters = courseService.findChaptersByCourseId(courseId);
		courseChaptersQuery.setCourse(course);
		courseChaptersQuery.setChapters(chapters);
		return courseChaptersQuery;
	}


	/**
	 * 查询所有目录
	 */
	@ResponseBody
	@GetMapping("/chapters")
	public List<Chapter> findChapters() {
		List<Chapter> chapters = courseService.findAllChapters();
		return chapters;
	}

	@ResponseBody
	@GetMapping("/chapter")
	public Integer countByCourseId(@RequestParam("courseId") String courseId) {
		return courseService.countByCourseId(courseId);
	}

	/**
	 * 查询所有lesson
	 */
	@ResponseBody
	@GetMapping("/lessons")
	public List<Lesson> findLessons() {
		List<Lesson> lessons = courseService.findAllLessons();
		return lessons;
	}

	/**
	 * lessonId
	 * 查询某节课对应的所有task
	 */
	@ResponseBody
	@GetMapping("/lesson/{lessonId}")
	public List<Task> findExercisesByLessonId(@PathVariable String lessonId) {
		List<Task> tasks = courseService.findTasksByLessonId(lessonId);
		return tasks;
	}


	/**
	 * 获取所有训练信息
	 */
	@ResponseBody
	@GetMapping("/tasks")
	public List<Task> findTasks() {
		return courseService.findTasks();
	}

	/**
	 * 根据训练ID获取训练信息
	 */
	@ResponseBody
	@GetMapping("/task/{taskId}")
	public Task findExerciseByExercise(@PathVariable("taskId") String taskId) {
		return courseService.findTaskByTaskId(taskId);
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
		code.setTaskId(exerciseId);
		code.setCodeId(Utils.getUUID());
		code.setCreatedAt(new Date());
		courseService.saveCode(code);
		return "保存完毕!";
	}


}
