package qisi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import qisi.bean.admin.AdminUser;
import qisi.bean.json.ApiResult;
import qisi.bean.query.WorkerPageQuery;
import qisi.bean.work.Worker;
import qisi.service.AdminService;
import qisi.service.WorkerService;

import javax.servlet.http.HttpSession;

/**
 * 管理员及测试API
 * 添加类API自身主键id,标志id,createdAt自动生成,无需添加
 *
 * @author : ddv
 * @date : 2018/10/29 下午1:55
 */


@RestController
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private HttpSession session;

	@Autowired
	private AdminService adminService;

	@Autowired
	private WorkerService workerService;

	@PostMapping("/login")
	public ApiResult login(@RequestBody AdminUser formUser) {
		return adminService.login(formUser, session);
	}

	@GetMapping("/workers")
	@ResponseBody
	public WorkerPageQuery workers(String username, String realName, String sex, String department, @RequestParam("page") int page, @RequestParam("rows") int rows) {

		WorkerPageQuery workerPageQuery = new WorkerPageQuery();

		Worker worker = new Worker();
		worker.setUsername(username);
		worker.setRealName(realName);
		worker.setDepartment(department);
		worker.setSex(sex);

		workerPageQuery.setRows(workerService.findWorkerByPageAndParams(worker, PageRequest.of((page - 1), rows)));
		workerPageQuery.setTotal(workerService.getWorkerByParamsCount(worker));

		return workerPageQuery;

	}

}
