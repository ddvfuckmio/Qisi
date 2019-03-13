package qisi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qisi.bean.json.ApiResult;
import qisi.bean.query.WorkerDayOffPageQuery;
import qisi.bean.work.Worker;
import qisi.bean.work.WorkerDayOff;
import qisi.bean.work.WorkerUpdatePassword;
import qisi.service.WorkerService;
import qisi.utils.Utils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private HttpSession session;

	@PostMapping("/login")
	@ResponseBody
	public ApiResult login(@RequestBody Worker formWorker) {
		if (!Utils.checkWorker(formWorker)) {
			return ApiResult.FAILED("登陆信息有误!");
		}

		Worker worker = workerService.findWorkerByUsername(formWorker.getUsername());

		if (worker == null || !(worker.getPassword().equals(Utils.encode(formWorker.getPassword())))) {
			return ApiResult.FAILED("登录信息有误!");
		}

		session.setAttribute("username", formWorker.getUsername());
		return ApiResult.SUCCESS();
	}

	@GetMapping("/profile")
	@ResponseBody
	public Worker getWorkerProfile() {
		String username = "ddv";
		return workerService.findWorkerByUsername(username);
	}


	@GetMapping("/logout")
	public String logout() {
		session.removeAttribute("username");
		return "work/login";
	}

	@PostMapping("/sign")
	@ResponseBody
	public ApiResult sign(@RequestParam("type") int type) {
		String username = (String) session.getAttribute("username");
		if (username == null) username = "ddv";
		return workerService.workerSign(username, type);
	}

	@GetMapping("/dayOffs")
	@ResponseBody
	public WorkerDayOffPageQuery dayOffs(@RequestParam("page") int page, @RequestParam("rows") int rows) {
		WorkerDayOffPageQuery workerDayOffPageQuery = new WorkerDayOffPageQuery();
		List<WorkerDayOff> workerDayOffs = workerService.findWorkerDayOffsByPage("ddv", (page - 1) * rows, rows);
		int total = workerService.getWorkerDayOffCount("ddv");

		workerDayOffPageQuery.setTotal(total);
		workerDayOffPageQuery.setRows(workerDayOffs);

		return workerDayOffPageQuery;
	}

	@PostMapping("/dayOffCancel")
	@ResponseBody
	public ApiResult dayOffCancel(@RequestParam("id") int id) {
		String username = (String) session.getAttribute("username");
		username = username == null ? "ddv" : username;
		return workerService.cancelDayOff(username, id);
	}

	@PostMapping("/addDayOff")
	@ResponseBody
	public ApiResult AddDayOffCancel(@RequestBody WorkerDayOff workerDayOff) {
		System.out.println(workerDayOff.getStartDate().getTime());
		System.out.println(workerDayOff.getEndDate().getTime());

		workerDayOff.setUsername("ddv");
		return workerService.addDayOff(workerDayOff);
	}

	@GetMapping("/news")
	@ResponseBody
	public List<String> news() {
		List<String> news = new ArrayList<>();
		Worker worker = workerService.findWorkerByUsername("ddv");
		redisTemplate.opsForValue().set("worker", worker);
		return news;
	}

	@PostMapping("/updatePassword")
	@ResponseBody
	public ApiResult updatePassword(@RequestBody WorkerUpdatePassword workerUpdatePassword) {

		workerUpdatePassword.setUsername("ddv");

		return workerService.updatePassword(workerUpdatePassword);
	}

}
