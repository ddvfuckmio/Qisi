package qisi.controller;

/**
 * @author : ddv
 * @date : 2018/10/24 上午11:47
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qisi.bean.json.AjaxResponse;
import qisi.bean.user.MockUser;
import qisi.exception.userException.UserNotExistException;
import qisi.service.UserService;
import qisi.bean.user.User;
import qisi.utils.Utils;
import qisi.utils.Dozer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user/login")
	public String userLogin(User formUser, Map<String, Object> map, HttpSession session) {
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

		map.put("user", user);
		session.setAttribute("username", formUser.getUsername());
		return "profile";
	}

	/**
	 * 测试请用postman发送json结构
	 *
	 * @param formUser
	 * @param request
	 * @return
	 */
	@PostMapping("/user/register")
	public String userRegister(User formUser, HttpServletRequest request) {
		String username = formUser.getUsername();
		String password = formUser.getPassword();
		String sex = formUser.getSex();
		String age = formUser.getAge();
		String job = formUser.getJob();
		String phone = formUser.getPhone();
		String email = formUser.getEmail();
		request.setAttribute("user", formUser);

		if (username == null || "".equals(username)) {
			request.setAttribute("error", "用户名不能为空");
			return "register";
		}

		if (password == null || "".equals(password)) {
			request.setAttribute("error", "密码不能为空");
			return "register";
		}

		if (sex == null || "".equals(sex)) {
			request.setAttribute("error", "性别不能为空");
			return "register";
		}

		if (age == null || "".equals(age)) {
			request.setAttribute("error", "年龄有误");
			return "register";
		}

		if (job == null || "".equals(job)) {
			request.setAttribute("error", "职业不能为空");
			return "register";
		}

		if (phone == null || "".equals(phone)) {
			request.setAttribute("error", "电话不能为空");
			return "register";
		}

		if (email == null || "".equals(email)) {
			request.setAttribute("error", "邮箱不能为空");
			return "register";
		}

		if (email == null || "".equals(email)) {
			request.setAttribute("error", "邮箱不能为空");
			return "register";
		}

		formUser.setRole("普通用户");
		formUser.setCreatedAt(new Date());

		List<User> users = userService.checkUserIfExist(formUser);
		if (users.size() > 0) {
			request.setAttribute("error", "该用户已经注册!");
			return "register";
		}

		password = Utils.encode(password);
		formUser.setPassword(password);

		userService.userRegister(formUser);

		request.setAttribute("msg", "注册成功!");
		return "login";
	}

	@ResponseBody
	@GetMapping("/users")
	public List<User> getUsers(HttpSession session) {
		return userService.getUsers();
	}

	@ResponseBody
	@GetMapping(value = "/user")
	public MockUser findUserByUserName(@RequestParam("username") String username) {
		User user = userService.findUserByUsername(username);
		if (user == null) {
			throw new UserNotExistException(username);
		}
		MockUser mockUser = Dozer.getBean(user, MockUser.class);
		System.out.println(user);
		System.out.println(mockUser);
		return mockUser;
	}

	@ResponseBody
	@PostMapping("/user/password")
	public String updatePassword(User user, HttpSession session) {

		String username = (String) session.getAttribute("username");
		System.out.println(username);
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

	@ResponseBody
	@PostMapping("/user/profile")
	public AjaxResponse updateProfile(@RequestBody User user, HttpSession session) {
		AjaxResponse response = new AjaxResponse();
		System.out.println(user);

		List<User> users = userService.checkUserIfExist(user);

		if (users.size() > 1) {
			response.setStatus(400);
			response.setMsg("电话或邮箱已被注册!");
			return response;
		}

		if (users.size() == 1) {
			if (!(users.get(0).getUsername().equals(user.getUsername()))) {
				response.setStatus(400);
				response.setMsg("请检查电话邮箱是否被注册!");
				return response;
			}
		}

		userService.updateProfile(user);

		response.setStatus(200);
		response.setMsg("修改成功!");

		return response;
	}

	@PostMapping("/upload")
	public String UploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		FileOutputStream fos;
		FileInputStream in;
		if (file.isEmpty()) {
			request.setAttribute("error", "请选择文件!");
			return "profile";
		}
		System.out.println(file.getOriginalFilename());
		try {
			fos = new FileOutputStream("images/" + file.getOriginalFilename());
			in = (FileInputStream) file.getInputStream();
			byte[] bytes = new byte[2048];
			int len;
			while ((len = (in.read(bytes))) != -1) {
				fos.write(bytes, 0, len);
			}
			fos.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return "profile";
	}

}
