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
@WebServlet("/BoSelectServlet2")// 회원가입 및 아기정보 등록 서블렛
public class BoSelectServlet2 extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Bohum_DAO dao = new Bohum_DAO();
		String id = request.getParameter("id");
		
		
		String result = dao.select_Bo(id);
		
		
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(result);
		
	}

}