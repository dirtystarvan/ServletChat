package ru.ac1dtest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Chat extends HttpServlet {
	private final ConcurrentLinkedQueue<String> mq = new ConcurrentLinkedQueue<>();

	private final StringBuilder pagePart1 = new StringBuilder();
	private final StringBuilder pagePart2 = new StringBuilder();
	private StringBuilder messages = new StringBuilder();

	@Override
	public void init() throws ServletException {
		pagePart1.append("<html><body>");
		pagePart1.append("<h1>Chat</h1>");
		pagePart1.append("<form method=\"post\">");
		pagePart1.append("<textarea rows=\"10\" cols=\"45\" name=\"text\" readonly=\"true\">");

		pagePart2.append("</textarea><br>");
		pagePart2.append("<label>Message: </label>");
		pagePart2.append("<input type=\"text\" name=\"message\"<br><br>");
		pagePart2.append("<input type=\"submit\" value=\"Submit\">");
		pagePart2.append("</form>");

		pagePart2.append("<form method=\"post\">");
		pagePart2.append("<input type=\"submit\" name=\"logout\" value=\"Logout\">");
		pagePart2.append("</form>");

		pagePart2.append("</body></html>");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		resp.setIntHeader("Refresh", 1);
//		resp.setContentType("text/html");

		PrintWriter writer = resp.getWriter();

		messages.delete(0, messages.length());

		for (String item: mq)
			messages.append(item).append("\n");

		writer.write(pagePart1.toString());
		writer.write(messages.toString());
		writer.write(pagePart2.toString());

		writer.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("logout") != null) {
			HttpSession sess = req.getSession();
			sess.removeAttribute("login");
			sess.invalidate();
			resp.sendRedirect(req.getContextPath());
		} else {
			String userName = (String)req.getSession().getAttribute("login");
			String newMessage = req.getParameter("message");

			if (newMessage != null && !newMessage.isEmpty())
				mq.add(userName + ": " + newMessage);

			resp.sendRedirect(req.getContextPath() + "/chat");
		}
	}
}
