import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import services.ServiceTools;

public class GetLike extends HttpServlet {
	 private static final long serialVersionUID = 1;
	 
	 public GetLike() {}
	 
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		
		String message_id = request.getParameter("message");
		
		JSONArray res=new JSONArray();
		try {
			res = ServiceTools.getLike(message_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	 	response.setContentType( "application/json" );
		PrintWriter out = response.getWriter ();
		out.println(res);
	 }
	 
}