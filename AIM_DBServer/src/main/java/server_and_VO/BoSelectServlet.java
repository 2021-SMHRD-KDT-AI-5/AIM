package server_and_VO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JoinServlet
 */
@WebServlet("/BoSelectServlet")// 회원가입 및 아기정보 등록 서블렛
public class BoSelectServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Bohum_DAO dao = new Bohum_DAO();
		String id = request.getParameter("id");
		
		boolean result = dao.BoChecked(id);
		PrintWriter writer = response.getWriter();
		
		writer.print(result);
		
	}

}