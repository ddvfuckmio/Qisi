package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qisi.bean.admin.AdminUser;
import qisi.bean.json.ApiResult;
import qisi.dao.AdminUserRepository;
import qisi.utils.Utils;

import javax.servlet.http.HttpSession;

/**
 * @author : ddv
 * @date : 2018/12/6 下午3:32
 */
@Service
public class AdminService {
	@Autowired
	private AdminUserRepository adminUserRepository;

	public ApiResult login(AdminUser formUser, HttpSession session) {

		if (Utils.checkAdminUser(formUser)) {
			return ApiResult.LOGIN_ERROR();
		}

		AdminUser adminUser = adminUserRepository.findAdminUserByUsername(formUser.getUsername());

		if (!(adminUser != null && adminUser.getPassword().equals(Utils.encode(formUser.getPassword())))) {
			return ApiResult.LOGIN_ERROR();
		}

		session.setAttribute("username", formUser.getUsername());
		return ApiResult.SUCCESS();
	}
}
