import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ServiceTools;
import services.UserService;

public class Follow extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public Follow() {}
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String key = request.getParameter("key");
		//int friend_id = Integer.parseInt(request.getParameter("friend"));
		String friend = request.getParameter("friend");
		Object res = null;
		
		//String password = request.getParameter("pass");
		
		try {
			//UserService.addFriend(key, friend_id);
			UserService.addFriend(key, ServiceTools.getIdUser(friend));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}