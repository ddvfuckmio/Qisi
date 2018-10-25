package qisi.controller;

/**
 * @author : ddv
 * @date : 2018/10/24 上午11:47
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qisi.exception.userException.UserNotExistException;
import qisi.service.UserService;
import qisi.bean.User;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user/login")
	public String login(User formUser) {
		String account = formUser.getAccount();
		String password = formUser.getPassword();
		if (account == null || account.equals("")) {
			return "用户名不能为空!";
		}

		if (password == null || password.equals("")) {
			return "用户密码不能为空!";
		}

		User user = userService.findUserByAccount(account);

		if (user == null || !password.equals(user.getPassword())) {
			return "用户密码错误";
		}

		return "登陆成功!";
	}

	@PostMapping("/user")
	public String saveUser(User user) {
		user.setCreatedAt(new Date());
		User findUser = userService.checkUserIfExist(user);
		if (findUser != null) {
			return "该用户已经存在!";
		}
		userService.saveUser(user);
		return "注册成功!";
	}

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping(value = "/user", produces = "application/json;charset=UTF-8")
	public User findUserByName(@RequestParam("account") String account) {
		User user = userService.findUserByAccount(account);
		if (user == null) {
			throw new UserNotExistException(account);
		}
		return user;
	}

}
