package qisi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import qisi.bean.user.User;
import qisi.bean.work.Worker;
import qisi.dao.CourseRepository;
import qisi.dao.UserRepository;
import qisi.dao.worker.WorkerDayOffRepository;
import qisi.service.CourseService;
import qisi.service.WorkerService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

	@Test
	public void contextLoads() {
		for (int i = 0; i < 10; i++) {
			String index = String.valueOf(i);
			Worker worker = workerService.findWorkerByUsername(index);
			redisTemplate.opsForList().rightPush(index, worker);
			redisTemplate.expire(index, 50, TimeUnit.SECONDS);
		}

	}

}
