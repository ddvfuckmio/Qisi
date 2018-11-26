package qisi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qisi.service.CourseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QisiApplicationTests {

	@Autowired
	private CourseService courseService;

	@Test
	public void contextLoads() {
		System.out.println(courseService.findCourseByTaskId("2e866bdc6ab44089b395417bc7a59153"));
	}

}
