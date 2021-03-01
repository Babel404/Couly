import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import services.ServiceTools;

public class GetFollower extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public GetFollower() {}
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String key = request.getParameter("key");
		String user = request.getParameter("user");
		String mode = request.getParameter("display");
		
		JSONArray res = new JSONArray();
		try {
			
			if (key != null) {
				ArrayList<Integer> followers = ServiceTools.getFollowers(key);
				for (Integer follower_id : followers) {
					
					String log = ServiceTools.getLoginFromId(follower_id);
						
					if (log.equals(""))
						continue;
						
					if (mode != null && mode.equals("full"))
						res.put(ServiceTools.getProfile(log));
					else
						res.put(log);
				}
			} else {
				ArrayList<Integer> followers = ServiceTools.getFollowers(ServiceTools.getIdUser(user));
				for (Integer follower_id : followers) {
					
					String log = ServiceTools.getLoginFromId(follower_id);
						
					if (log.equals(""))
						continue;
						
					if (mode != null && mode.equals("full"))
						res.put(ServiceTools.getProfile(log));
					else
						res.put(log);
				}
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}