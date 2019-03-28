package qisi.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qisi.service.AdminService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author : ddv
 * @date : 2018/11/15 上午9:40
 */
@Component
@WebFilter(filterName = "sessionFilter", urlPatterns = {"/*"})
public class SessionFilter implements Filter {

	@Autowired
	private AdminService adminService;
	private static HashSet<String> set = new HashSet<>();
	private static final String NEED_LOGIN = "{\n" +
			"  \"status\": 401,\n" +
			"  \"msg\":\"认证过期,请重新登录!\"\n" +
			"}";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		set.add("/pages/workerLogin");
		set.add("/worker/login");
		set.add("/easyui");
		set.add("/css");
		set.add("/js");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String url = request.getRequestURI();
		if (set.contains(url) || containResource(url)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			String username = (String) session.getAttribute("username");
			if (username != null && !"".equals(username)) {
				if (url.contains("/admin")) {
					if (!adminService.checkAdminUser(username)) {
						response.sendRedirect(request.getContextPath() + "/pages/admin/login");
					}
				}
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				if (isAjax(request)) {
					ajaxHandler(response);
					return;
				} else {
					response.sendRedirect(request.getContextPath() + "/pages/workerLogin");
				}

			}
		}
	}

	private boolean containResource(String url) {
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			if (url.startsWith(next)) {
				return true;
			}
		}
		return false;
	}

	private void ajaxHandler(HttpServletResponse response) throws IOException {
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(NEED_LOGIN);
		writer.close();
		response.flushBuffer();
		return;
	}

	@Override
	public void destroy() {

	}

	private static boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
	}

}
