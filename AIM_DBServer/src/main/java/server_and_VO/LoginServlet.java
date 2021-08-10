package server_and_VO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet") // 로그인 서블렛
public class LoginServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AIM_Member_DAO dao = new AIM_Member_DAO();

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		boolean result = dao.login(id, pw);
		PrintWriter writer = response.getWriter();
		writer.print(result);
	}

}
