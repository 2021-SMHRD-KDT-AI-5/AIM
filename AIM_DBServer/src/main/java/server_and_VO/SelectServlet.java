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

@WebServlet("/SelectServlet") // �α��� ����
public class SelectServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		AIM_Member_DAO dao = new AIM_Member_DAO();
		BabyInfoDAO dao2 = new BabyInfoDAO();
		
		String id = request.getParameter("id");

		ArrayList<MemberVO> list = new ArrayList<>();
		ArrayList<BabyVO> list2 = new ArrayList<>();
		
		list = dao.select_member(id);
		list2 = dao2.select_baby(id);
				
		String info = list.get(0).getUser_id()+","+list.get(0).getUser_pw()+","+list.get(0).getUser_email()+","+list.get(0).getUser_ip();
		String info2 = ","+list2.get(0).getBaby_name()+","+list2.get(0).getBirthday();
		
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter writer = response.getWriter();

		writer.print(info);
		writer.print(info2);
		
	}

}
