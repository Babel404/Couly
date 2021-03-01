import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.UserService;

public class Login extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public Login() {}
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("user");
		String password = request.getParameter("pass");
		
		JSONObject res = UserService.login(login, password);
		if (res == null) {
			res = new JSONObject();
			try {
				res.put("erreur","utilisateur introuvable");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}