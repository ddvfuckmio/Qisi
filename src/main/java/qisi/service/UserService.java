package qisi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qisi.bean.User;
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

	public void saveUser(User user){
		userRepository.save(user);
	}

	public List<User> getUsers(){
		return userRepository.findAll();
	}

	public User findUserByAccount(String account){
		return userRepository.findUserByAccount(account);
	}

	public User checkUserIfExist(User user){
		return userRepository.findUserIfExist(user.getAccount(),user.getPhone(),user.getEmail());
	}
}
