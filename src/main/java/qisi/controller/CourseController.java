package qisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qisi.bean.course.Course;
import qisi.bean.course.Exercise;
import qisi.exception.courseException.CourseNotExistException;
import qisi.service.CourseService;
import qisi.utils.MockUtil;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:25
 */

@Controller
public class CourseController {

	@Autowired
	private CourseService courseService;

	@ResponseBody
	@GetMapping("/getExercises")
	public List<Exercise> getExercises(){
		return courseService.findExercises();
	}

	@ResponseBody
	@GetMapping("/getCourses")
	public List<Course> getCourses(){
		return courseService.findAllCourses();
	}

	@ResponseBody
	@GetMapping("/course")
	public Course getCourses(@RequestParam("courseName") String courseName){
		Course course =  courseService.findCourseByName(courseName);
		if (course == null){
			throw new CourseNotExistException(courseName);
		}
		return course;
	}

}
