import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;

import services.ServiceTools;
import services.UserService;

public class Digest extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public Digest() {}
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
			String key = request.getParameter("key");
			Document query = new Document();
			
			//String password = request.getParameter("pass");
			
			JSONArray res = null;
			try {
				
				if (key != null) 
						res = UserService.digest(key);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (res == null) {
				res = new JSONArray();
				res.put("erreur");
			}
		
		response.setCharacterEncoding("UTF-8");
	 	response.setContentType("application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}