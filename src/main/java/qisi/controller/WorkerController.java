package qisi.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qisi.bean.json.ApiResult;
import qisi.bean.user.User;
import qisi.bean.work.Worker;
import qisi.service.WorkerService;
import qisi.utils.Utils;

import javax.servlet.http.HttpSession;

/**
 * @author : ddv
 * @date : 2019/1/23 下午6:53
 */

@Controller
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	private WorkerService workerService;

	@Autowired
	private HttpSession session;

	@PostMapping("/login")
	@ResponseBody
	public ApiResult login(@RequestBody Worker formWorker) {
		if (!Utils.checkFormWorker(formWorker)) {
			return ApiResult.FAILED("登陆信息有误!");
		}

		Worker worker = workerService.findWorkerByUsername(formWorker.getUsername());

		if (worker == null || !(worker.getPassword().equals(Utils.encode(formWorker.getPassword())))) {
			return ApiResult.FAILED("登录信息有误!");
		}

		session.setAttribute("username", formWorker.getUsername());
		return ApiResult.SUCCESS();
	}

	@GetMapping("/logout")
	public String logout() {
		session.removeAttribute("username");
		return "work/login";
	}

	@PostMapping("/sign")
	@ResponseBody
	public ApiResult sign(@RequestParam ("type") int type) {
		String username = (String) session.getAttribute("username");
		if (username == null) username = "ddv";
		return workerService.workerSign(username,type);
	}
}
