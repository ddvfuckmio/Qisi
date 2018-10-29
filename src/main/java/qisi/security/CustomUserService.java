//package qisi.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import qisi.bean.user.User;
//import qisi.dao.UserRepository;
//import qisi.exception.userException.UserNotExistException;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author : ddv
// * @date : 2018/10/29 上午11:26
// */
//
//@Service
//public class CustomUserService implements UserDetailsService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepository.findUserByUsername(username);
//		if (user == null) {
//			throw new UserNotExistException("用户名不存在!");
//		}
//
//		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//		authorities.add(new SimpleGrantedAuthority(user.getRole()));
//
//		return new org.springframework.security.core.userdetails.User(user.getUsername(),
//				user.getPassword(), authorities);
//	}
//}
