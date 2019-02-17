package qisi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : ddv
 * @date : 2019/1/27 下午12:23
 */

@Controller
@RequestMapping("/tree")
public class TreeController {

	@GetMapping("/sign")
	public String sign() {
		return "tree/sign.html";
	}

	@GetMapping("/dayOffs")
	public String dayOffs() {
		return "tree/dayOffs.html";
	}

	@GetMapping("/addDayOffs")
	public String addDayOffs() {
		return "tree/addDayOffs.html";
	}

	@GetMapping("/password")
	public String profile() {
		return "tree/password.html";
	}


}
