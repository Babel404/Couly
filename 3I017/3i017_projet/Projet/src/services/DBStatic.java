package services;

public interface DBStatic {
	public static boolean mysql_pooling=false;
	public static String mysql_host="localhost";
	public static String mysql_db="3i017";
	public static String mysql_username="root";
	public static String mysql_password="root";
	
	public static String mongo_host="localhost";
	public static String mongo_db="3i017_mongo";
	public static int mongo_port=27017;
}