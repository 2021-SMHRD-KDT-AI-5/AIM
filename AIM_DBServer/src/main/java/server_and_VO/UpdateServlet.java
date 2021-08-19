package server_and_VO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet("/UpdateServlet") 
public class UpdateServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		AIM_Member_DAO dao = new AIM_Member_DAO();
		BabyInfoDAO dao2 = new BabyInfoDAO();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String ip = request.getParameter("ip");
		String b_name = request.getParameter("baby_name");
		String b_birthday = request.getParameter("baby_birthday");

		System.out.println("아기이름 : "+b_name);
		System.out.println("아기생일 : "+b_birthday);
		
		int cnt1 = dao.update_member(id, pw, email, ip);
		int cnt2 = dao2.update_baby(id, b_name, b_birthday);
		
		System.out.println(cnt1);
		System.out.println();
		System.out.println(cnt2);
		
		
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter writer = response.getWriter();

		writer.print(cnt1);
		writer.print(cnt2);
		
	}

}
