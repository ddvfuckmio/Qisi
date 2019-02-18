package qisi.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qisi.bean.admin.AdminUser;
import qisi.bean.course.*;
import qisi.bean.jms.CodeMessage;
import qisi.bean.json.ApiResult;
import qisi.bean.json.CodeJudge;
import qisi.bean.user.MockUser;
import qisi.bean.user.User;
import qisi.bean.work.Worker;
import qisi.bean.work.WorkerCheck;
import qisi.dao.WorkerCheckRepository;
import qisi.dao.WorkerRepository;
import qisi.exception.AdminAuthorityException;
import qisi.service.AdminService;
import qisi.utils.*;
import qisi.service.CourseService;
import qisi.service.ProducerService;
import qisi.service.UserService;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.*;

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

	@PostMapping("/login")
	public ApiResult login(@RequestBody AdminUser formUser) {
		return adminService.login(formUser, session);
	}

}
