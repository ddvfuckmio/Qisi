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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 用户登录
	 *
	 * @param formUser 表单用户数据
	 * @param map      页面回显数据
	 * @param session  全局session
	 * @return 登录页面/个人信息页面
	 */
	@PostMapping("/user/login")
	public String userLogin(User formUser, Map<String, Object> map, HttpSession session) {
		String username = formUser.getUsername();
		String password = formUser.getPassword();
		map.put("user", formUser);

		if (!Utils.fieldValue(username)) {
			map.put("error", "用户名格式有误!");
			return "login";
		}

		if (!Utils.fieldValue(password)) {
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
	 * 用户注册
	 *
	 * @param formUser
	 * @param request
	 * @return
	 */
	@PostMapping("/user/register")
	public String userRegister(User formUser, HttpServletRequest request) {
		request.setAttribute("user", formUser);
		AjaxResponse ajax = new AjaxResponse();
		if (!Utils.checkFormUser(formUser, ajax)) {
			request.setAttribute("error", ajax.getMsg());
			return "/register.html";
		}

		List<User> users = userService.checkUserIfExist(formUser);
		if (users.size() > 0) {
			request.setAttribute("error", "该用户已经注册!");
			return "/register.html";
		}

		formUser.setRole("普通用户");
		formUser.setCreatedAt(new Date());
		formUser.setPassword(Utils.encode(formUser.getPassword()));

		userService.userRegister(formUser);

		request.setAttribute("msg", "注册成功!");
		return "/login.html";
	}

	@ResponseBody
	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@ResponseBody
	@GetMapping(value = "/user")
	public MockUser findUserByUserName(@RequestParam("username") String username) {
		User user = userService.findUserByUsername(username);
		if (user == null) {
			return null;
		}
		MockUser mockUser = Dozer.getBean(user, MockUser.class);
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

		if (!Utils.checkFormUser(user, null
		)) {
			return response;
		}

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
		request.setAttribute("error", "上传完毕!");
		return "profile";
	}

}
