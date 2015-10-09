package auth;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter(urlPatterns = { "/point/*", "/user/*", "/api/*" })
public class AuthFilter implements Filter {

	public void destroy() {

	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String basePath = request.getServletContext().getContextPath() + "/";
		HttpSession session = req.getSession(false);
		
		if(null == session) {
			resp.sendRedirect(basePath + "login");
			return;
		} else {
			Boolean isAuthenticated = (Boolean) session.getAttribute("authenticated");
			if(!Objects.equals(isAuthenticated, true)) {
				resp.sendRedirect(basePath + "login");
				return;
			}
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
}
