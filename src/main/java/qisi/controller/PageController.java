package qisi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页API
 *
 * @author : ddv
 * @date : 2018/10/25 上午9:58
 */

@Controller
@RequestMapping("/pages")
public class PageController {

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	@RequestMapping("/admin/login")
	public String adminLogin() {
		return "admin_login";
	}

	@RequestMapping("/index")
	public String index() {
		return "index.html";
	}


//	@GetMapping("/tree/json")
//	public String treeJson(){
//
//	}

	//员工登陆页面
	@RequestMapping("/workLogin")
	public String workLogin() {
		return "work/login.html";
	}

	//员工主页面
	@RequestMapping("/workMain")
	public String workMain() {
		return "work/main.html";
	}

	@RequestMapping("/test")
	public String test() {
		return "test/index.html";
	}

}
