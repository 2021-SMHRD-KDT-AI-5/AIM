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
@WebServlet("/BoDeleteServlet") // 로그인 서블렛
public class BoDeleteServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Bohum_DAO dao = new Bohum_DAO();
		
		String id = request.getParameter("id");
		
		int result = dao.DeleteBo(id);

		System.out.println(result);
		
		PrintWriter writer = response.getWriter();
		writer.print(result+"");
		
	}

}


