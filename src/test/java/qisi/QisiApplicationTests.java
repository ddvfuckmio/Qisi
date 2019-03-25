package qisi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import qisi.dao.CourseRepository;
import qisi.dao.UserRepository;
import qisi.dao.WorkerRepository;
import qisi.dao.worker.WorkerDayOffRepository;
import qisi.service.AdminService;
import qisi.service.CourseService;
import qisi.service.WorkerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QisiApplicationTests {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkerDayOffRepository workerDayOffRepository;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private WorkerService workerService;

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private AdminService adminService;

	@Test
	public void contextLoads() {

	}


}
