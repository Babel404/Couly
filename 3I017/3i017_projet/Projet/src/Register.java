import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UserService;

public class Register extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public Register() {}
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String login = request.getParameter("login");
		String mail = request.getParameter("mail");
		String mdp = request.getParameter("mdp");
		Object res = null;
		
		//String password = request.getParameter("pass");
		
		try {
			UserService.createUser(login, mdp, nom, prenom, mail);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}