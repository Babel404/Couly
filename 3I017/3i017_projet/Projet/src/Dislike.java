import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UserService;

public class Dislike extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public Dislike() {}
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String key = request.getParameter("key");
		String message_id = request.getParameter("message");
		Object res = null;
		
		//String password = request.getParameter("pass");
		
		//UserService.addFriend(key, friend_id);
		UserService.dislike(key, message_id);
		
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}