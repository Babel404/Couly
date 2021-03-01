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

public class GetMessage extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public GetMessage() {}
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
			String key = request.getParameter("key");
			String user = request.getParameter("user");
			String msg_id = request.getParameter("message");
			Document query = new Document();
			
			//String password = request.getParameter("pass");
			
			JSONArray res = null;
			try {
				
				if (key != null) {
					if (msg_id != null) {
						query.append("replyTo", msg_id);
						res = UserService.search4(key, query);
					}
					else
						res = UserService.search2(key, query, ServiceTools.getFriends(key));
				}
				else
					res = UserService.search3(user, query, ServiceTools.getFriends(ServiceTools.getIdUser(user)));
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