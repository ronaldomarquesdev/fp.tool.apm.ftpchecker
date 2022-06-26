package dev.ronaldomarques.fp.tool.apm.ftpchecker.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.ronaldomarques.fp.tool.apm.ftpchecker.service.FtpCheckerService;
import dev.ronaldomarques.util.myutility.debugger.DP;

@WebServlet({ "/FtpCheckerServlet", "/ftpchecker" })
public class FtpCheckerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * public FtpCheckerServlet() { super(); }
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DP.pdln("doGet");

		response.getWriter().append("<html>");
		response.getWriter().append("<h1>dev.ronaldomarques.fp.tool.apm.ftpchecker web version !</h1>");
		response.getWriter().append("<h3>FtpCheckerSevlet !</h3>");
		response.getWriter().append("<p>");
		response.getWriter().append("parameter 'action' = " + request.getParameter("action"));
		response.getWriter().append("<br/>");
		response.getWriter().append("parameter 'field' = " + request.getParameter("field"));
		response.getWriter().append("<br/>");
		response.getWriter().append(" parameter 'aliasvalue' = " + request.getParameter("aliasvalue"));
		response.getWriter().append("</p>");
		response.getWriter().append("</html>");

		response.getWriter().append("\n\nServed at: ").append(request.getContextPath());

		if (request.getParameter("action").equalsIgnoreCase("start")) {
			DP.pdln("start");
			FtpCheckerService.startLooping();
		} else if (request.getParameter("action").equalsIgnoreCase("stop")) {
			DP.pdln("stop");
			FtpCheckerService.stopLooping();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DP.pdln("doPost");

		response.getWriter().append("\n\nServed at: ").append(request.getContextPath());
		doGet(request, response);
	}

}
