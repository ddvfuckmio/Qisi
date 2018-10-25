package qisi.utils;

import qisi.bean.User;
import qisi.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/25 下午3:18
 */

public class MockUtil {
	private static UserService userService = new UserService();

	public static List<User> mockUsers() {
		List<User> users = new ArrayList<>(5000);
		for (int i = 0; i < 5000; i++) {
			String index = String.valueOf(i);
			String sex = (i & 1) == 0 ? "男" : "女";
			User user = new User();
			user.setAccount(index);
			user.setPassword(MD5Util.encode(index));
			user.setCreatedAt(new Date());
			user.setAge(index);
			user.setEmail("email_" + index);
			user.setPhone("phone_" + index);
			user.setSex(sex);
			users.add(user);
			user = null;
		}
		return users;
	}

}
