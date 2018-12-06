package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qisi.bean.admin.AdminUser;
import qisi.dao.AdminUserRepository;

/**
 * @author : ddv
 * @date : 2018/12/6 下午3:32
 */
@Service
public class AdminService {
	@Autowired
	private AdminUserRepository adminUserRepository;

	public AdminUser findAdminUserByUsername(String username) {
		return adminUserRepository.findAdminUserByUsername(username);
	}
}
