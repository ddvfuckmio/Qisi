package qisi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import qisi.bean.admin.News;
import qisi.bean.user.User;
import qisi.bean.work.Worker;
import qisi.dao.CourseRepository;
import qisi.dao.UserRepository;
import qisi.dao.WorkerRepository;
import qisi.dao.worker.WorkerDayOffRepository;
import qisi.service.CourseService;
import qisi.service.WorkerService;
import qisi.utils.RedisUtils;
import qisi.utils.Utils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

	@Autowired
	private WorkerRepository workerRepository;

	@Test
	public void contextLoads() {
		Worker worker = new Worker();
		worker.setUsername("ddv");
		worker.setPhone("phone0");
		Page<Worker> workers = workerService.findWorkerByPageAndParams(worker, new PageRequest(0, 10) {
		});
		List<Worker> list = Utils.PageToList(workers);

		System.out.println(list);
	}


}
