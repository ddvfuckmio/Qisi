package qisi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qisi.dao.CourseRepository;
import qisi.service.CourseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QisiApplicationTests {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseRepository courseRepository;

	@Test
	public void contextLoads() {
		System.out.println(courseRepository.findAllPublishedCourses());
	}

}
