import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UserService;

public class Reply extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public Reply() {}
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		 
		String key = request.getParameter("key");
		String text = request.getParameter("message");
		String replyTo = request.getParameter("replyto");
		Object res = null;
		
		//String password = request.getParameter("pass");
		
		try {
			UserService.addComment(key, text, replyTo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}