package qisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qisi.bean.json.ApiResult;
import qisi.service.UserService;
import qisi.bean.user.User;
import qisi.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户相关API
 * ResponseBody 此注解标志RestAPI, 并且返回数据JSON格式
 *
 * @author : ddv
 * @date : 2018/10/24 上午11:47
 */


@Controller
public class UserController {
	private static final String LOGIN_HTML = "login.html";
	private static final String PROFILE_HTML = "profile.html";
	private static final String REGISTER_HTML = "register.html";

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
			return LOGIN_HTML;
		}

		if (!Utils.fieldValue(password)) {
			map.put("error", "用户密码不能为空!");
			return LOGIN_HTML;
		}

		password = Utils.encode(formUser.getPassword());
		User user = userService.findUserByUsername(username);

		if (user == null || !password.equals(user.getPassword())) {
			map.put("error", "用户名与密码不匹配!");
			return LOGIN_HTML;
		}

		map.put("user", user);
		session.setAttribute("username", formUser.getUsername());
		return PROFILE_HTML;
	}

	/**
	 * 用户注册
	 *
	 * @param formUser 表单用户
	 * @param request  回显数据
	 * @return 登录/注册页面
	 */
	@PostMapping("/user/register")
	public String userRegister(User formUser, HttpServletRequest request) {
		request.setAttribute("user", formUser);
		ApiResult ajax = new ApiResult();

		if (!Utils.checkFormUser(formUser, ajax)) {
			request.setAttribute("error", ajax.getMsg());
			return REGISTER_HTML;
		}

		List<User> users = userService.checkUserIfExist(formUser);

		if (users.size() > 0) {
			request.setAttribute("error", "该用户已经注册!");
			return REGISTER_HTML;
		}

		formUser.setRole("普通用户");
		formUser.setCreatedAt(new Date());
		formUser.setPassword(Utils.encode(formUser.getPassword()));

		userService.userRegister(formUser);

		request.setAttribute("msg", "注册成功!");
		return LOGIN_HTML;
	}

	/**
	 * 修改用户密码
	 *
	 * @param user    用户信息载体(只包含密码)
	 * @param session 全局session
	 * @return ApiResult
	 */
	@ResponseBody
	@PostMapping("/user/password")
	public ApiResult updatePassword(@RequestBody User user, HttpSession session) {
		ApiResult apiResult = new ApiResult();
		String username = (String) session.getAttribute("username");

		if (!Utils.fieldValue(user.getPassword())) {
			apiResult.setMsg("密码格式有误!");
			return apiResult;
		}
		user.setPassword(Utils.encode(user.getPassword()));

		userService.updatePassword(username, user.getPassword());
		apiResult.setMsg("修改成功!");
		return apiResult;
	}

	/**
	 * 用户更新个人信息
	 *
	 * @param user    用户信息载体
	 * @param session 全局session
	 * @return ApiResult
	 */
	@ResponseBody
	@PostMapping("/user/profile")
	public ApiResult updateProfile(@RequestBody User user, HttpSession session) {
		ApiResult response = new ApiResult();
		user.setPassword("password");
		response.setStatus(400);
		response.setMsg("非法操作!");
		if (!Utils.checkFormUser(user, response)) {
			return response;
		}

		if (!user.getUsername().equals(session.getAttribute("username"))) {
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

	/**
	 * @param request 回显数据
	 * @param file    上传文件
	 * @return 个人信息页面
	 */
	@PostMapping("/upload")
	public String uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		FileOutputStream fos;
		FileInputStream in;
		if (file.isEmpty()) {
			request.setAttribute("error", "请选择文件!");
			return PROFILE_HTML;
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
		return PROFILE_HTML;
	}

}
