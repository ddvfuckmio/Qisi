package qisi.controller;

/**
 * @author : ddv
 * @date : 2018/10/24 上午11:47
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qisi.exception.userException.UserNotExistException;
import qisi.service.UserService;
import qisi.bean.user.User;
import qisi.utils.Md5Util;
import qisi.utils.MockUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user/login")
	public String userLogin(User formUser, Map<String, Object> map) {
		String account = formUser.getAccount();
		String password = formUser.getPassword();
		map.put("user", formUser);

		if (account == null || "".equals(account)) {
			map.put("error", "用户名不能为空!");
			return "login";
		}

		if (password == null || "".equals(password)) {
			map.put("error", "用户密码不能为空!");
			return "login";
		}

		password = Md5Util.encode(formUser.getPassword());
		User user = userService.findUserByAccount(account);

		if (user == null || !password.equals(user.getPassword())) {
			map.put("error", "用户名与密码不匹配!");
			return "login";
		}

		return "main";
	}

	@PostMapping("/user/register")
	public String userRegister(User formUser, Map<String, Object> map) {
		String account = formUser.getAccount();
		String password = formUser.getPassword();
		String sex = formUser.getSex();
		String age = formUser.getAge();
		String job = formUser.getJob();
		String phone = formUser.getPhone();
		String email = formUser.getEmail();
		map.put("user", formUser);

		if (account == null || "".equals(account)) {
			map.put("error", "账号不能为空");
			return "register";
		}

		if (password == null || "".equals(password)) {
			map.put("error", "密码不能为空");
			return "register";
		}

		if (sex == null || "".equals(sex)) {
			map.put("error", "性别不能为空");
			return "register";
		}

		if (age == null || "".equals(age)) {
			map.put("error", "年龄有误");
			return "register";
		}

		if (job == null || "".equals(job)) {
			map.put("error", "职业不能为空");
			return "register";
		}

		if (phone == null || "".equals(phone)) {
			map.put("error", "电话不能为空");
			return "register";
		}

		if (email == null || "".equals(email)) {
			map.put("error", "邮箱不能为空");
			return "register";
		}

		formUser.setCreatedAt(new Date());

		User findUser = userService.checkUserIfExist(formUser);
		if (findUser != null) {
			map.put("error", "该用户已经注册!");
			return "register";
		}

		password = Md5Util.encode(password);
		formUser.setPassword(password);

		userService.userRegister(formUser);

		map.put("msg", "注册成功!");
		return "main";
	}

	@ResponseBody
	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping(value = "/user")
	public User findUserByName(@RequestParam("account") String account) {
		User user = userService.findUserByAccount(account);
		if (user == null) {
			throw new UserNotExistException(account);
		}
		return user;
	}

	@GetMapping("/mockUsers")
	public String mockUsers() {
		List<User> users = MockUtil.mockUsers();
		userService.mockUsers(users);
		return "main";
	}

}
