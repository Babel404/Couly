package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class ServiceTools {

	public static JSONObject serviceRefused(String message, int erreur) {
		JSONObject res= new JSONObject();
		try {
			res.put("erreur", erreur);
			res.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	
	public static JSONObject serviceAccepted() {
		JSONObject res= new JSONObject();
		try {
			res.put("message","OK");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	//inutilisé atm
	public static JSONObject serviceAccepted(String key, String val) {
		JSONObject res= new JSONObject();
		try {
			res.put(key,val);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	
	public static boolean userExists(String login) throws SQLException {
		boolean retour;
		
		Connection conn = Database.getMySQLConnection();
		String query = "SELECT id FROM login_table WHERE login = '"+ login + "';";
		
		try {
			Statement instruction = conn.createStatement();
			instruction.executeQuery(query);
			ResultSet rs = instruction.getResultSet();
			
			retour = rs.next();

			rs.close();
			instruction.close();
			return retour;
		} catch (Exception e) {
			throw new SQLException("Problem while checking if user exists");
		} finally {
			conn.close();
		}
	}
	
	
	public static boolean checkPassword(String login, String password) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String query = "SELECT password FROM login_table WHERE login='"+login+"';";
		
		try {
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			
			if (!rs.next())
				return false;
			String res=rs.getString(1);
			
			rs.close();
			instruction.close();
			
			return (res.equals(password));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();	
		}
		
		return false;
	}
	
	
	public static String insererConnexion(int id_user, boolean b) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String key = ServiceTools.randomKey(8);
		String query = "INSERT INTO sessions (connexion_key, user_id, date, state) VALUES ('" + key + "'," + id_user + ",NOW(),1);";
		
		try {
			Statement instruction = conn.createStatement();
			instruction.executeUpdate(query);
			
			instruction.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return key;
	}

	
	public static int getIdUser(String login) throws SQLException {
		int res=-1;
		
		Connection conn = Database.getMySQLConnection();
		String query = "SELECT id FROM login_table WHERE login='" + login + "';";
		
		try {
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			
			if (!rs.next())
				throw new SQLException("Utilisateur introuvable");
			res=rs.getInt(1);
			
			rs.close();
			instruction.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return res;
	}
	
	
	public static int getIdFromConnexion(String key) throws SQLException {
		int res=0;
		
		Connection conn = Database.getMySQLConnection();
		String query = "SELECT user_id FROM sessions WHERE connexion_key='" + key + "';";
		
		try {
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			
			if (!rs.next())
				throw new SQLException("Utilisateur introuvable");
			res=rs.getInt(1);
			
			rs.close();
			instruction.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return res;
	}
	
	
	public static String randomKey(int length) {
	    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    StringBuilder sb = new StringBuilder();
	    int nb_chars= chars.length();
	    
	    for(int x=0;x<length;x++) {
	       int i = (int)Math.floor(Math.random() * nb_chars);
	       sb.append(chars.charAt(i));
	    }
	    
	    return sb.toString();
	}
	
	public static ArrayList<Integer> getFriends(String key) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String query = "SELECT friend_id FROM friends WHERE user_id=" + ServiceTools.getIdFromConnexion(key) + ";";
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		try {
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			
			while (rs.next()) {
				res.add(rs.getInt(1));
			}
			
			rs.close();
			instruction.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return res;
	}
	
	
	public static ArrayList<Integer> getFriends(int user_id) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String query = "SELECT friend_id FROM friends WHERE user_id=" + user_id + ";";
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		try {
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			
			while (rs.next()) {
				res.add(rs.getInt(1));
			}
			
			rs.close();
			instruction.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return res;
	}
	
	
	public static ArrayList<Integer> getFollowers(String key) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String query = "SELECT user_id FROM friends WHERE friend_id=" + ServiceTools.getIdFromConnexion(key) + ";";
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		try {
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			
			while (rs.next()) {
				res.add(rs.getInt(1));
			}
			
			rs.close();
			instruction.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return res;
	}
	
	public static ArrayList<Integer> getFollowers(int user_id) throws SQLException {
		Connection conn = Database.getMySQLConnection();
		String query = "SELECT user_id FROM friends WHERE friend_id=" + user_id + ";";
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		try {
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			
			while (rs.next()) {
				res.add(rs.getInt(1));
			}
			
			rs.close();
			instruction.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return res;
	}
	
	public static String getLoginFromId(int user_id) {	
		
		String res = "";
		try {
			Connection conn = Database.getMySQLConnection();
			String query = "SELECT login FROM login_table WHERE id='" + user_id + "';";
			
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			
			if (!rs.next())
				throw new SQLException("Utilisateur introuvable");
			res=rs.getString(1);
			
			rs.close();
			instruction.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static JSONObject getProfile(String login) {
		
		JSONObject res = new JSONObject();
		
		try {
			Connection conn = Database.getMySQLConnection();
			String query = "SELECT * FROM login_table WHERE login='" + login + "';";
			Statement instruction = conn.createStatement();
			ResultSet rs = instruction.executeQuery(query);
			
			if (!rs.next())
				return new JSONObject();
			
			res = convert(rs);
			
			rs.close();
			conn.close();
			instruction.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	  public static JSONObject convert( ResultSet rs )
			    throws SQLException, JSONException
			  {
			    ResultSetMetaData rsmd = rs.getMetaData();

			      int numColumns = rsmd.getColumnCount();
			      JSONObject obj = new JSONObject();

			      for (int i=1; i<numColumns+1; i++) {
			        String column_name = rsmd.getColumnName(i);

			        if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
			         obj.put(column_name, rs.getArray(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
			         obj.put(column_name, rs.getInt(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
			         obj.put(column_name, rs.getBoolean(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
			         obj.put(column_name, rs.getBlob(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
			         obj.put(column_name, rs.getDouble(column_name)); 
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
			         obj.put(column_name, rs.getFloat(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
			         obj.put(column_name, rs.getInt(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
			         obj.put(column_name, rs.getNString(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
			         obj.put(column_name, rs.getString(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
			         obj.put(column_name, rs.getInt(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
			         obj.put(column_name, rs.getInt(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
			         obj.put(column_name, rs.getDate(column_name));
			        }
			        else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
			        obj.put(column_name, rs.getTimestamp(column_name));   
			        }
			        else{
			         obj.put(column_name, rs.getObject(column_name));
			        }
			      }

			    return obj;
			  }
	  
		
		public static JSONArray getLike(String message_id) throws SQLException {
			Connection conn = Database.getMySQLConnection();
			String query = "SELECT user_id FROM likes WHERE message_id='" + message_id + "';";
			
			JSONArray res = new JSONArray();
			
			try {
				Statement instruction = conn.createStatement();
				ResultSet rs = instruction.executeQuery(query);
				
				while (rs.next()) {
					//System.out.println(rs.getInt(1));
					//res.put(rs.getInt(1));
					res.put(ServiceTools.getLoginFromId(rs.getInt(1)));
				}
				
				rs.close();
				instruction.close();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
			
			//System.out.println(res);
			return res;
		}
		
	  

}