import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.ServiceTools;

public class GetProfile extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public GetProfile() {}
	 
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("user");
		
		JSONObject res = new JSONObject();
		
		if (login != null)
			res = ServiceTools.getProfile(login);
		else
			res = ServiceTools.getProfile(ServiceTools.getLoginFromId(Integer.parseInt(request.getParameter("id"))));

		
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}