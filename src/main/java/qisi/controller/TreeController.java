package qisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import qisi.bean.json.TreeJson;
import qisi.service.AdminService;

import javax.servlet.http.HttpSession;

/**
 * @author : ddvv
 * @date : 2019/1/27 下午12:23
 */

@Controller
@RequestMapping("/tree")
public class TreeController {

	@Autowired
	private HttpSession session;

	@Autowired
	private AdminService adminService;

	@GetMapping("/json")
	@ResponseBody
	public TreeJson json() {
		String username = (String) session.getAttribute("username");
		username = username == null ? "ddv" : username;
		if (adminService.checkAdminUser(username)) {
			return new TreeJson(200, "/json/tree_admin.json");
		}
		return new TreeJson(200, "/json/tree.json");
	}

	@GetMapping("/sign")
	public String sign() {
		return "tree/worker/sign.html";
	}

	@GetMapping("/dayOffs")
	public String dayOffs() {
		return "tree/worker/dayOffs.html";
	}

	@GetMapping("/addDayOffs")
	public String addDayOffs() {
		return "tree/worker/addDayOffs.html";
	}

	@GetMapping("/updatePassword")
	public String profile() {
		return "tree/worker/updatePassword.html";
	}


	// admin tree
	
	@GetMapping("/admin/workers")
	public String adminWorkers() {
		return "tree/admin/workers.html";
	}

	@GetMapping("/admin/dayOffs")
	public String adminDayOffs() {
		return "tree/admin/dayOffs";
	}


}
