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
import qisi.bean.User;

import javax.servlet.http.HttpServletRequest;
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
		if (account == null || "".equals(account)) {
			map.put("error", "用户名不能为空!");
			return "login";
		}

		if (password == null || "".equals(password)) {
			map.put("error", "用户密码不能为空!");
			return "login";
		}

		User user = userService.findUserByAccount(account);

		if (user == null || !password.equals(user.getPassword())) {
			map.put("error", "用户名与密码不匹配!");
			return "login";
		}

		return "main";
	}

	@PostMapping("/user")
	public String userRegister(User formUser) {
		formUser.setCreatedAt(new Date());
		User findUser = userService.checkUserIfExist(formUser);
		if (findUser != null) {
			return "该用户已经存在!";
		}
		userService.saveUser(formUser);
		return "注册成功!";
	}

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

}
