package qisi.config;


import qisi.bean.json.ApiResult;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author : ddv
 * @date : 2018/11/15 上午9:40
 */

@WebFilter(filterName = "sessionFilter", urlPatterns = {"/*"})
public class SessionFilter implements Filter {
	private static HashSet<String> set = new HashSet<>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		set.add("/pages/login");
		set.add("/user/login");
		set.add("/user/register");
		set.add("/pages/register");
		set.add("swagger-ui.html ");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		String url = request.getRequestURI();
		if (set.contains(url)) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			String username = (String) session.getAttribute("username");
			if (username != null && !"".equals(username)) {
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				if (isAjax(request)) {
					PrintWriter writer = response.getWriter();
					writer.write("需要登录的API!");
					writer.close();
					response.flushBuffer();
					return;
				} else {
					response.sendRedirect(request.getContextPath() + "/pages/login");
				}

			}
		}
		return;
	}

	@Override
	public void destroy() {

	}

	public static boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
	}

}
