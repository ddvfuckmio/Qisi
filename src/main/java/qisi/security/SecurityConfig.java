//package qisi.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
///**
// * @author : ddv
// * @date : 2018/10/29 上午9:59
// */
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private CustomUserService customUserService;
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserService); //user Details Service验证
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		//  定义当需要用户登录时候，转到的登录页面。
//		http.authorizeRequests()
//				.antMatchers("/mockUsers", "/user/login").permitAll()
//				.anyRequest().authenticated()
//				.and()
//				.formLogin()
//				.loginPage("/pages/login")
//				.defaultSuccessUrl("/pages/success")
//				.permitAll();
//	}
//}
