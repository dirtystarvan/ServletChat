package ru.ac1dtest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Administration extends HttpServlet {
	private final String blackList = "\\blacklist.txt";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();

		writer.write("<html><body>");
		writer.write("<h1>Administration</h1>");
		writer.write("<form method=\"post\">");
		writer.write("<label>Participant: </label>");
		writer.write("<input type=\"text\" name=\"login\"<br><br>");
		writer.write("<input type=\"submit\" value=\"Ban\">");
		writer.write("</form>");
		writer.write("</body></html>");

		writer.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = getServletContext().getRealPath("/WEB-INF");

		FileWriter fw = new FileWriter(path + blackList, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(req.getParameter("login"));
		pw.close();
	}
}
