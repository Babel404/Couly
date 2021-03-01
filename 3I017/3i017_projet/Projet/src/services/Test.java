package services;

import java.sql.SQLException;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Test {

//---------------------------------------------------------------
//	Services :
//	  MySQL:
//		-login(login,password) : FINI
//		-logout(key) : FINI
//		-createUser(login,password,nom,prenom) : FINI
//		-addFriend(key,friend_id) : FINI
//		-removeFriend(key,friend_id) : FINI
//	
//	  MongoDB :
//		-addComment(key,text) : FINI
//		-search(key,query,friends) : A FAIRE
//	
//	Eventuellement remplacer les exceptions par des servicesRefused() ?
//	Détailler les servicesRefused()
//---------------------------------------------------------------	
	
	public static void main(String[] args) throws SQLException {
		//System.out.println("\u001B31;1mSlt");
		//UserService.createUser("keraroo2", "kerarooo", "ker", "rhl");
		//UserService.createUser("admin", "admin", "Nom", "Prenom");
		
		//JSONObject res = UserService.login("login0", "password0");
		//System.out.println(res.toString());
		//UserService.login("login4", "password3");
		//UserService.login("keraroo2a", "kerarooo");

		//UserService.logout("SvUcPbIn");
		
		//ServiceTools.getIdFromConnexion("k7j4gq5K");
		
		//UserService.addFriend("k7j4gq5K",3);
		//UserService.removeFriend("k7j4gq5K",3);
		
		//System.out.println(ServiceTools.randomKey(8));
		
		
//		MongoCollection<Document> collection = Database.getMongoDB().getCollection("comments");
		//query.append("date", );
		
		
//		BasicDBObject query2=new BasicDBObject("author_name","nom0");
//		Document myDoc = collection.find(query2).first();
//		System.out.println(myDoc.toJson());
		
//		query.append("author_id", 1);
//		query.append("date", "xxx2");
//		query.append("text", "msg2");
//		
		//query.append("date", "xxx2");http://localhost:8080/project4/messages?key=wpLiEsoo&message=5cb4b4fac319cb7eb44d647c
		
//		Document query = new Document();
//		query.append("replyTo", "5cb4af64c319cb7eb44d6463");
//		//query.append("author_id", 1);
////		
//		UserService.search3("wpLiEsoo", query);
//		
		//UserService.addComment("wpLiEsoo", "2e msg", null);
//		UserService.addComment("k7j4gq5K", "encore du text pr keraroo");

		
//		UserService.addFriend("wpLiEsoo", 2);
//		
//		for (int fid : ServiceTools.getFriends("wpLiEsoo")) {
//			System.out.println(fid);
//		}
		
//		collection.insertOne(query); //insert nv msg

//		MongoCursor<Document> cursor = collection.find(query).iterator(); //query
//		while (cursor.hasNext()) {
//			Document obj = cursor.next();
//			System.out.println(obj);
//		}
		
		//System.out.println("ehe");
		
		//UserService.search("wpLiEsoo", new Document(), ServiceTools.getFriends("wpLiEsoo"));
		
		//UserService.search2("wpLiEsoo", new Document(), ServiceTools.getFriends("wpLiEsoo"));
		//UserService.search2(user, query, ServiceTools.getFriends(ServiceTools.getIdUser(user)));
		
		//UserService.search3("login0", new Document(), ServiceTools.getFriends(ServiceTools.getIdUser("login0")));
		
		//System.out.println(UserService.doesFollow("wpLiEsoo", 7));
		
		//System.out.println(ServiceTools.getLoginFromId(7));
		
		//System.out.println(ServiceTools.getProfile("login0"));
		
		//UserService.deleteMessage("wpLiEsoo", "5cb0be6a1e1aae340818b056");
		//ServiceTools.getLike("5cb595aac510b505b846fc53");
		
		//UserService.dislike("wpLiEsoo", "5cb595aac510b505b846fc53");
		
		
		//UserService.createUser("lhhjrhrog", "mdp", "nom", "prenom", "mail");
		//UserService.updateProfile("INCqvjuW", "prenom", "2", "mdp", "ok", "oui", "ok");
		//UserService.updateProfile("INCqvjuW","YOOOOO","PAYOOOOO","123","","","");
		//UserService.updateProfile("INCqvjuW",);
		//http://localhost:8080/project4/update?key=vDZ3FyYq&prenom=null&nom=null&color=null&desc=null
		
	}
}