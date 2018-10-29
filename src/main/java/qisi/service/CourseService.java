package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qisi.bean.course.Course;
import qisi.bean.course.Exercise;
import qisi.bean.course.Lesson;
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
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private ExerciseRepository exerciseRepository;

	public void saveCourses(List<Course> courseList){
		courseRepository.saveAll(courseList);
	}

	public void saveLessons(List<Lesson> lessonList){
		lessonRepository.saveAll(lessonList);
	}

	public List<Exercise> findExercises(){
		return exerciseRepository.findAll();
	}

	public List<Course> findAllCourses(){
		return courseRepository.findAllCourses();
	}

	public Course findCourseByName(String courseName){
		return courseRepository.findCourseByName(courseName);
	}
}
