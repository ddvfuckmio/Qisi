package qisi.utils;

import qisi.bean.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author : ddv
 * @date : 2018/10/25 下午3:18
 */

public class MockUtil {
	private final static int COUNT = 10000;

	public static List<User> mockUsers() {
		Random random = new Random();
		String[] jobs = {"学生", "工人", "老师", "人事", "售货员"};
		List<User> users = new ArrayList<>(COUNT);
		for (int i = 0; i < COUNT; i++) {
			String index = String.valueOf(i);
			String sex = (i & 1) == 0 ? "男" : "女";
			String job = jobs[random.nextInt(jobs.length)];
			User user = new User();
			user.setAccount(index);
			user.setPassword(Md5Util.encode(index));
			user.setCreatedAt(new Date());
			user.setAge(index);
			user.setEmail("email_" + index);
			user.setPhone("phone_" + index);
			user.setSex(sex);
			user.setJob(job);
			users.add(user);
			user = null;
		}
		return users;
	}

}
