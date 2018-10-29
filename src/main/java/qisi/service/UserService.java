package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qisi.bean.user.User;
import qisi.dao.UserRepository;

import java.util.List;

/**
 * @author : ddv
 * @date : 2018/10/24 下午2:29
 */

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void userRegister(User user) {
		userRepository.save(user);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	public User checkUserIfExist(User user) {
		return userRepository.findUserIfExist(user.getUsername(), user.getPhone(), user.getEmail());
	}

	public void mockUsers(List<User> users) {
		userRepository.saveAll(users);
	}

}
