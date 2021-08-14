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
@WebServlet("/BohumServlet") // 로그인 서블렛
public class BohumServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Bohum_DAO dao = new Bohum_DAO();
		
		String id = request.getParameter("id");
		String u_name = request.getParameter("u_name");
		String bs_name = request.getParameter("bs_name");
		String b_name = request.getParameter("b_name");

		System.out.println("id = " + id);
		System.out.println("u_name = " + u_name);
		System.out.println("bs_name = " + bs_name);
		System.out.println("b_name = " + b_name);
		
		
		int result1 = dao.AddBo(id, u_name, bs_name, b_name);

		PrintWriter writer = response.getWriter();
		
		writer.print(result1);
	
		
	}

}


