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
@WebServlet("/LicenseServlet") // 라이센스 서블렛
public class LicenseServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		License_DAO dao = new License_DAO();
		
		String id = request.getParameter("id");
		String servletCheck = request.getParameter("where");
		String type = request.getParameter("license_type");
		String codeOrNot = request.getParameter("codeOrNot");
		
		int result2 = 0;
		PrintWriter writer = response.getWriter();
		
		if(servletCheck.equals("insert")) {		// 있는지 체크
			result2 = dao.AddLicense(id, type, codeOrNot);
			writer.print(result2);
		}else if(servletCheck.equals("check")) {	// 등록
			boolean result1 = dao.LicenseChecked(id);
			writer.print(result1);
		}else if(servletCheck.equals("select")) {
			String result = dao.select_License(id);
			writer.print(result);
		}else if(servletCheck.equals("delete")) {
			result2 = dao.Delete_License(id);
			System.out.println(result2);
			writer.print(result2);
		}

	
	}

}


