import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UserService;

public class UpdateProfile extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public UpdateProfile() {}
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String key = request.getParameter("key");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String desc = request.getParameter("desc");
		String mdp = request.getParameter("mdp");
		String color = request.getParameter("color");
		String userpp = request.getParameter("userpp");
		
		Object res = null;
		
		//String password = request.getParameter("pass");
		
		UserService.updateProfile(key, prenom, nom, mdp, desc, userpp, color);
		
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}