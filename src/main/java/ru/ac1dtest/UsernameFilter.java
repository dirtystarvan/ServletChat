package ru.ac1dtest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

public class UsernameFilter implements Filter {
	private FilterConfig fc;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		fc = filterConfig;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		boolean pass = true;

		HttpServletRequest request = (HttpServletRequest)servletRequest;
		String userName = (String)request.getSession().getAttribute("login");

		String path = fc.getServletContext().getRealPath("/WEB-INF/blacklist.txt");

		try {
			if (path != null) {
				PrintWriter servletResponseWriter = servletResponse.getWriter();
				BufferedReader reader = new BufferedReader(new FileReader(path));

				String line;
				while ((line = reader.readLine()) != null) {
					if (line.equals(userName)) {
						servletResponseWriter.write("You are banned!");
						pass = false;
						break;
					}
				}
			}
		} catch (IOException e) {

		}

		if (pass) {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}
}
