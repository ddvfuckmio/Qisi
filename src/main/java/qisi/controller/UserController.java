package qisi.controller;

/**
 * @author : ddv
 * @date : 2018/10/24 上午11:47
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import qisi.exception.userException.UserNotExistException;
import qisi.service.UserService;
import qisi.bean.user.User;
import qisi.utils.Utils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user/login")
	public String userLogin(User formUser, Map<String, Object> map, HttpSession session) {
		session.setAttribute("user", formUser.getUsername());
		String username = formUser.getUsername();
		String password = formUser.getPassword();
		map.put("user", formUser);

		if (username == null || "".equals(username)) {
			map.put("error", "用户名不能为空!");
			return "login";
		}

		if (password == null || "".equals(password)) {
			map.put("error", "用户密码不能为空!");
			return "login";
		}

		password = Utils.encode(formUser.getPassword());
		User user = userService.findUserByUsername(username);

		if (user == null || !password.equals(user.getPassword())) {
			map.put("error", "用户名与密码不匹配!");
			return "login";
		}

		return "success";
	}

	@PostMapping("/user/register")
	public String userRegister(@RequestBody User formUser, Map<String, Object> map) {
		String account = formUser.getUsername();
		String password = formUser.getPassword();
		String sex = formUser.getSex();
		String age = formUser.getAge();
		String job = formUser.getJob();
		String phone = formUser.getPhone();
		String email = formUser.getEmail();
		map.put("user", formUser);

		if (account == null || "".equals(account)) {
			map.put("error", "用户名不能为空");
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

		formUser.setRole("普通用户");
		formUser.setCreatedAt(new Date());

		User findUser = userService.checkUserIfExist(formUser);
		if (findUser != null) {
			map.put("error", "该用户已经注册!");
			return "register";
		}

		password = Utils.encode(password);
		formUser.setPassword(password);

		userService.userRegister(formUser);

		map.put("msg", "注册成功!");
		return "login";
	}

	@ResponseBody
	@GetMapping("/users")
	public List<User> getUsers(HttpSession session) {
		return userService.getUsers();
	}

	@ResponseBody
	@GetMapping(value = "/user")
	public User findUserByUserName(@RequestParam("username") String username) {
		User user = userService.findUserByUsername(username);
		if (user == null) {
			throw new UserNotExistException(username);
		}
		return user;
	}

	@Transactional(rollbackFor = Exception.class)
	@ResponseBody
	@PostMapping("/user/password")
	public String updatePassword(@RequestBody User user) {
		if (user.getUsername() == null || "".equals(user.getUsername())) {
			return "用户名非法!";
		}

		if (user.getPassword() == null || "".equals(user.getPassword())) {
			return "用户密码非法!";
		}

		user.setPassword(Utils.encode(user.getPassword()));
		System.out.println(user.getPassword());

		userService.updatePassword(user.getUsername(), user.getPassword());

		return "修改完毕!";
	}

}
