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
@WebServlet("/JoinServlet")// 회원가입 및 아기정보 등록 서블렛
public class JoinServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AIM_Member_DAO memberDao = new AIM_Member_DAO();
		BabyInfoDAO babyDao = new BabyInfoDAO();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String ip = request.getParameter("ip");
		String babyName = request.getParameter("babyName");
		String birthday = request.getParameter("babyBirthday");
		
		int result1 = memberDao.join(id, pw, email, ip);
		int result2 = babyDao.join(id, babyName, birthday);
		PrintWriter writer = response.getWriter();
		writer.print(result1);
		writer.print(result2);
		
	}

}