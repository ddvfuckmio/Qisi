package qisi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
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

	@RequestMapping("/main")
	public String success() {
		return "courses";
	}

	@RequestMapping("/profile")
	public String profile() {
		return "profile";
	}

}
