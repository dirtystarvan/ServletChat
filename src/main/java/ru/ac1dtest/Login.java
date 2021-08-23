package ru.ac1dtest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Login extends HttpServlet {
	private static final String CHAT_PATH = "/chat";
	private static final String ADMIN_PATH = "/admin";
	private static final String OMT_PATH = "/";
	private static final String LOGIN_SESS = "login";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();

		writer.write("<html><body>");
		writer.write("<h1>Login</h1>");
		writer.write("<form action=\"ServletChat/chat\" method=\"post\">");
		writer.write("<label>Your name: </label>");
		writer.write("<input type=\"text\" name=\"login\"<br><br>");
		writer.write("<input type=\"submit\" value=\"Submit\">");
		writer.write("</form>");
		writer.write("</body></html>");

		writer.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "admin".equalsIgnoreCase(req.getParameter("login")) ? ADMIN_PATH : CHAT_PATH;

		req.getSession().setAttribute(LOGIN_SESS, req.getParameter("login"));

		resp.sendRedirect(req.getContextPath() + path);
	}
}
