package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class UserService {

	//public static void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
		//String login=req.getParameter("login");
		//String password=req.getParameter("password");
	
	public static JSONObject login (String login, String password) {
		if((login==null)||(password==null)) {
			System.out.println(ServiceTools.serviceRefused("Wrong Argument", -1).toString());
		}
		
		try {
			boolean is_user=ServiceTools.userExists(login);
			if(!is_user) {System.out.println(ServiceTools.serviceRefused("Unknown user"+login, 1).toString()); return null;}
			
			boolean password_ok=ServiceTools.checkPassword(login,password);
			if(!password_ok) {System.out.println(ServiceTools.serviceRefused("Wrong password", 2).toString()); return null;}
			
			int id_user=ServiceTools.getIdUser(login);
			String key = ServiceTools.insererConnexion(id_user,false);
			
			//a modifier -> sortie {id,login,key}
			JSONObject res = new JSONObject();
			res.put("id",id_user);
			res.put("login", login);
			res.put("key", key);
			
			return res;
			//return (new JSONObject()).put("key", key);
			
		} catch (JSONException e) {
			return ServiceTools.serviceRefused("JSON problem"+e.getMessage(),100);
		} catch (SQLException e) {
			return ServiceTools.serviceRefused("Erreur SQL"+e.getMessage(),1000);
		} catch (Exception e) {
			return ServiceTools.serviceRefused("Erreur"+e.getMessage(),10000);
		}
	}
	
	
	public static JSONObject logout(String key) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String query = "UPDATE sessions SET state=0 WHERE connexion_key='" + key + "';";
		
		try {
			Statement instruction = conn.createStatement();
			instruction.executeUpdate(query);
			instruction.close();
			return ServiceTools.serviceAccepted();
		} catch (Exception e) {
			throw new SQLException("Impossible de se deconnecter");
		} finally {
			conn.close();
		}
	}
	
	
	public static JSONObject createUser(String login, String password, String nom, String prenom, String mail) throws SQLException {
		if ((login==null)||(password==null)||(nom==null)||(prenom==null))
			return ServiceTools.serviceRefused("Erreur paramètres", 0);
		if (ServiceTools.userExists(login))
			return ServiceTools.serviceRefused("Username already exists", 1);
		
		Connection conn = Database.getMySQLConnection();
		String query = "INSERT INTO login_table (login, password, prenom, nom, mail) VALUES ('" + login + "','" + password + "','" + prenom + "','" + nom + "','" + mail + "');";
		
		try {
			Statement instruction = conn.createStatement();
			instruction.executeUpdate(query);
			instruction.close();
			return ServiceTools.serviceAccepted();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("Impossible de creer l'utilisateur");
		} finally {
			conn.close();
		}
	}
	
	
	public static void addComment(String key, String text, String replyTo) throws SQLException {
		MongoCollection<Document> collection = Database.getMongoDB().getCollection("comments");
		
		Document query = new Document();
		query.append("author_id", ServiceTools.getIdFromConnexion(key));
		
		GregorianCalendar calendar = new GregorianCalendar();
		//calendar.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		query.append("date", calendar.getTime());
		//query.append("date", "$currentDate: { date:true }");
		
		query.append("text", text);
		
		query.append("replyTo", replyTo);
		
		collection.insertOne(query);
	}
	
	
	public static JSONObject addFriend(String key, int friend_id) throws SQLException {
		
		if (friend_id < 0)
			return ServiceTools.serviceRefused("La cible n'existe pas",5);
		
		Connection conn = Database.getMySQLConnection();
		int user_id=ServiceTools.getIdFromConnexion(key);
		String query1 = "SELECT * FROM friends WHERE user_id = " + user_id + " AND friend_id = " + friend_id + ";";
		String query2 = "INSERT INTO friends (user_id, friend_id) VALUES ('" + user_id+"','" + friend_id + "');";
		
		try {
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query1);
			
			if (rs.next())
				throw new SQLException("L'utilisateur " + user_id + " est deja ami avec l'utilisateur" + friend_id);
			
			rs.close();
			
			instruction.executeUpdate(query2);
			instruction.close();
			return ServiceTools.serviceAccepted();
			
		} catch(Exception e) {
			throw new SQLException("Impossible d'ajouter l'ami");
		} finally {
			conn.close();
		}
	}
	
	
	public static JSONObject removeFriend(String key, int friend_id) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		int user_id=ServiceTools.getIdFromConnexion(key);
		String query = "DELETE FROM friends WHERE user_id='" + user_id + "' AND friend_id='" + friend_id + "';";

		try {
			Statement instruction = conn.createStatement();
			instruction.executeUpdate(query);
			instruction.close();
			return ServiceTools.serviceAccepted();
		} catch (Exception e) {
			throw new SQLException("Impossible de retirer l'ami");
		} finally {
			conn.close();
		}
	}
	
	
	public static JSONObject search(String key, Document query, ArrayList<Integer> friends) { //renvoie la liste de ses propres messages et ceux de sa friendlist
		MongoCollection<Document> collection = Database.getMongoDB().getCollection("comments");
			
		//ajouter les tests sur la date !!!
		
		int userId = -1;
		try {
			userId = ServiceTools.getIdFromConnexion(key);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		JSONObject res = new JSONObject();
		int cpt=0;
		
		MongoCursor<Document> cursor = collection.find(query).iterator();
		while (cursor.hasNext()) {
			Document obj = cursor.next();
			if (friends.contains(obj.get("author_id")) || obj.getDouble("author_id").intValue() == userId) {
				try {
					res.put(""+cpt, obj.toJson());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cpt++;
				System.out.println(obj);
			}
		}
		
		return res;
		
	}
	
	public static JSONArray search2(String key, Document query, ArrayList<Integer> friends) { //renvoie la liste de ses propres messages et ceux de sa friendlist
		MongoCollection<Document> collection = Database.getMongoDB().getCollection("comments");
		
		int userId = -1;
		try {
			userId = ServiceTools.getIdFromConnexion(key);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//userId = 1;
		//collection.find(query).sort(com.mongodb.client.model.Sorts.descending("date"));
		JSONArray res = new JSONArray();
		MongoCursor<Document> cursor = collection.find(query).sort(com.mongodb.client.model.Sorts.descending("date")).iterator();
		while (cursor.hasNext()) {
			Document obj = cursor.next();
			
			//System.out.println(obj.toJson());
			//System.out.println(obj.get("author_id").getClass());
			if (friends.contains(obj.get("author_id")) || obj.getInteger("author_id") == userId) {
				obj.append("login", ServiceTools.getLoginFromId(obj.getInteger("author_id")));
				res.put(obj);
				
				//System.out.println(obj.toJson());
			}
		}
		//System.out.println(res);
		return res;

	}
	
	
	public static JSONArray search3(String user, Document query, ArrayList<Integer> friends) { //renvoie la liste de ses propres messages et ceux de sa friendlist
		MongoCollection<Document> collection = Database.getMongoDB().getCollection("comments");
		
		int userId = -1;
		try {
			userId = ServiceTools.getIdUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//userId = 1;
		//collection.find(query).sort(com.mongodb.client.model.Sorts.descending("date"));
		JSONArray res = new JSONArray();
		MongoCursor<Document> cursor = collection.find(query).sort(com.mongodb.client.model.Sorts.descending("date")).iterator();
		while (cursor.hasNext()) {
			Document obj = cursor.next();
			
			//System.out.println(obj.toJson());
			//System.out.println(obj.get("author_id").getClass());
			if (friends.contains(obj.get("author_id")) || obj.getInteger("author_id") == userId) {
				obj.append("login", ServiceTools.getLoginFromId(obj.getInteger("author_id")));
				res.put(obj);
				
				//System.out.println(obj.toJson());
			}
		}
		//System.out.println(res);
		return res;

	}

	
	
	public static JSONArray search4(String key, Document query) { //recherche ds tt le site
		MongoCollection<Document> collection = Database.getMongoDB().getCollection("comments");
		
		JSONArray res = new JSONArray();
		MongoCursor<Document> cursor = collection.find(query).iterator();
		while (cursor.hasNext()) {
			Document obj = cursor.next();
			
			//System.out.println(obj.toJson());
			//System.out.println(obj.get("author_id").getClass());
			obj.append("login", ServiceTools.getLoginFromId(obj.getInteger("author_id")));
			res.put(obj);
				
				//System.out.println(obj.toJson());
		}
		System.out.println(res);
		return res;

	}

	
	public static JSONObject doesFollow(String user_key, int cible_id) {
		
		if (cible_id < 0)
			return ServiceTools.serviceRefused("La cible n'existe pas",5);
		
		
		try {
			Connection conn = Database.getMySQLConnection();
			int user_id=ServiceTools.getIdFromConnexion(user_key);
			String query1 = "SELECT * FROM friends WHERE user_id = " + user_id + ";";
			
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query1);
			
			JSONObject res = new JSONObject();
			
			if (rs.next())
				res.append("isFriend", true);
			else
				res.append("isFriend", false);
			rs.close();
			
			instruction.close();
			return res;
			
		} catch (Exception e) {
			return ServiceTools.serviceRefused("Erreur recherche amitié", 1);
		}
		
	}
	
	
	public static JSONObject deleteMessage(String key, String message_id) {
		MongoCollection<Document> collection = Database.getMongoDB().getCollection("comments");
		collection.deleteOne(Filters.eq("_id", new ObjectId(message_id)));
		return new JSONObject();
	}
	
	public static JSONObject like(String key, String message_id) {
		try {
			Connection conn = Database.getMySQLConnection();
			int user_id=ServiceTools.getIdFromConnexion(key);
			String query2 = "INSERT INTO likes (user_id, message_id) VALUES ('" + user_id+"','" + message_id + "');";
			try {
				Statement instruction = conn.createStatement();
				instruction.executeUpdate(query2);
				instruction.close();
				return ServiceTools.serviceAccepted();
				
			} catch(Exception e) {
				throw new SQLException("Impossible de like");
			} finally {
				conn.close();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new JSONObject();
		
	}
	
	
	public static JSONObject dislike(String key, String message_id) {
		try {
			Connection conn = Database.getMySQLConnection();
			int user_id=ServiceTools.getIdFromConnexion(key);
			String query2 = "DELETE from likes WHERE user_id = " + user_id + " AND message_id='" + message_id + "';";
			try {
				Statement instruction = conn.createStatement();
				instruction.executeUpdate(query2);
				instruction.close();
				return ServiceTools.serviceAccepted();
				
			} catch(Exception e) {
				throw new SQLException("Impossible de dislike");
			} finally {
				conn.close();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new JSONObject();
		
	}
	
	public static JSONArray digest(String key) {
		MongoCollection<Document> collection = Database.getMongoDB().getCollection("comments");
		try {
			Connection conn = Database.getMySQLConnection();
			int user_id=ServiceTools.getIdFromConnexion(key);
			String query = "SELECT mail FROM login_table WHERE user_id = " + user_id + ";";
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			String mail = rs.getString(0);
			rs.close();
			Document query2 = new Document();
			JSONArray res = new JSONArray();
			query2.append("author_id", ServiceTools.getIdFromConnexion(key));
			res=UserService.search4(key, query2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JSONArray();

	}
	
	
	public static JSONObject updateProfile(String key, String prenom, String nom, String password, String desc, String userpp, String color) {
		
		//String login = ServiceTools.get
		String elmts = "";
		if (password != null) {
			elmts+="password = '"+password+"'";
		}
		if (nom != null) {
			if (!elmts.equals(""))
				elmts += ", ";
			elmts+="nom = '"+nom+"'";
		}
		if (prenom != null) {
			if (!elmts.equals(""))
				elmts += ", ";
			elmts+="prenom = '"+prenom+"'";
		}
		if (desc != null) {
			if (!elmts.equals(""))
				elmts += ", ";
			elmts+="description = '"+desc+"'";
		}
		if (userpp != null) {
			if (!elmts.equals(""))
				elmts += ", ";
			elmts+="userpp = '"+userpp+"'";
		}
		if (color != null) {
			if (!elmts.equals(""))
				elmts += ", ";
			elmts+="color = '"+color+"'";
		}
		
		if (elmts.equals(""))
				return new JSONObject();
		
		try {
			String query = "UPDATE login_table SET "+elmts+" WHERE id="+ServiceTools.getIdFromConnexion(key)+";";
			//System.out.println(query);
			Connection conn = Database.getMySQLConnection();
			Statement instruction = conn.createStatement();
			instruction.executeUpdate(query);
			instruction.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		
		return new JSONObject();
	}
	
}