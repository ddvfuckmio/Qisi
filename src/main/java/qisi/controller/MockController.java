package qisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import qisi.bean.course.Course;
import qisi.bean.course.Lesson;
import qisi.service.CourseService;
import qisi.utils.MockUtil;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:55
 */

@Controller
public class MockController {

	@Autowired
	private CourseService courseService;

	@GetMapping("/mockCourses")
	public String mockCourses(){
		List<Course> courses = MockUtil.mockCourses();
		courseService.saveCourses(courses);
		return "main";
	}

	@GetMapping("/mockLessons")
	public String mockLessons(){
		List<Lesson> lessons = MockUtil.mockLessons();
		courseService.saveLessons(lessons);
		return "main";
	}
}
