package JO.org;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.tools.JavaFileObject;

import org.json.JSONArray;
import org.json.JSONObject;

import JC.org.JDBCproto;
import netscape.javascript.JSObject;

public class database {
	public ResultSet rs;
	private PreparedStatement pstmt;
	private static Connection con;
	
	public static String gettoken(String queary) throws SQLException{
    	String token = null;
    	Connection conn= null ;
    	try {
			Class.forName("org.postgresql.Driver");
			conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
        	Statement stmt=conn.createStatement(
        		    ResultSet.TYPE_SCROLL_INSENSITIVE, 
        		    ResultSet.CONCUR_READ_ONLY);
        	 int b = 0;
        	 ResultSet rs= stmt.executeQuery(queary);
        	 if (!rs.isBeforeFirst() ) {    
        		 System.out.println(queary); 
        		}else{ 
		    {
		    rs.first();
		    token = rs.getString("userid");
		    System.out.println(token);
		    }while (rs.next());}
		   
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return token;
    }
	
	public static void gettoken(String queary, Object[] des) throws SQLException, ClassNotFoundException{
		JDBCproto jdbc=new JDBCproto("fakeinsta","postgres","masterkey");
		jdbc.prepareQuery(queary,des);
		}
	public static JSONObject imagetoget(String query) {
		JSONArray  json = 	new JSONArray();
		JSONObject je= new JSONObject();
		try {
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
		    Statement st = con.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    while (rs.next())
		      {  JSONObject jo = new JSONObject();
		    	 int id = rs.getInt("postid");
		    	 String idu = rs.getString("userid");
		         String photo = rs.getString("photo");
		         String description = rs.getString("description");
		         Date created_at = rs.getDate("created_at");
		         String email = rs.getString("email");
		         jo.put("postid", id);
		         jo.put("userid", idu);
		         jo.put("photo", photo);
		         jo.put("description", description);
		         jo.put("created_at ", created_at );
		         jo.put("email", email);
		         jo.put("liked", false);
		         json.put(jo);
		      }
		    je.put("videoinfo", json);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return je;
	}
	
	public static JSONObject imagetoget(String query,int k) {
		JSONObject jo = new JSONObject();
		try {
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setLong(1, k); 
			ResultSet rs = statement.executeQuery();
		    while (rs.next())
		      {  
		    	 int id = rs.getInt("postid");
		    	 int idu = rs.getInt("userid");
		         String photo = rs.getString("photo");
		         String description = rs.getString("description");
		         Date created_at = rs.getDate("created_at");
		         String email = rs.getString("email");
		         jo.put("postid", id);
		         jo.put("userid", idu);
		         jo.put("photo", photo);
		         jo.put("description", description);
		         jo.put("created_at ", created_at );
		         jo.put("email", email);
		         
		      }
		    
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo;
	}
	public static JSONObject checkedifliked(int id, String idu) throws ClassNotFoundException, SQLException {
		JSONObject jose =new JSONObject();
		Class.forName("org.postgresql.Driver");
		con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
		PreparedStatement st = con.prepareStatement("select * from likes where postid=? and userid=?",
    		    ResultSet.TYPE_SCROLL_INSENSITIVE, 
    		    ResultSet.CONCUR_READ_ONLY);
		st.setLong(1, id);
		st.setString(2, idu);
		ResultSet rs = st.executeQuery();
		if (rs.next() == false) { 
			jose.put("liked", false);
			jose.put("likedspk", 0);
		}else {
				{
				rs.first();
				int likedid=rs.getInt("likespk");
				jose.put("liked", true);
				jose.put("likedspk", likedid);
				}while (rs.next());
		}
	    
	    return jose;
	}
	public static int checkcount(int id) throws ClassNotFoundException, SQLException {
		int idU = 0;
		Class.forName("org.postgresql.Driver");
		con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
		PreparedStatement st = con.prepareStatement("select count(*) from likes where postid=?");
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		while (rs.next())
	      {  
			
	    	  idU = rs.getInt("count");
	      }
		System.out.println("count = "+idU);
	    return idU;
	}
	
	public static JSONArray checkedifliked1(String output) throws ClassNotFoundException, SQLException {
		JSONArray  json = 	new JSONArray();
		Class.forName("org.postgresql.Driver");
		con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
		PreparedStatement st = con.prepareStatement("select * from likes where  userid=?");
		st.setString(1, output);
		ResultSet rs = st.executeQuery();
		while (rs.next())
	      {  JSONObject jo = new JSONObject();
	    	 int id1 = rs.getInt("postid");
	    	 String idu1 = rs.getString("userid");
	    	 jo.put("postid", id1);
	    	 jo.put("userid", idu1);
	    	 json.put(jo);
	      }
	    return json;
	}
	
	public static JSONArray checkcount() throws ClassNotFoundException, SQLException {
		JSONArray  json = 	new JSONArray();
		Class.forName("org.postgresql.Driver");
		con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select post.postid, likes.userid  from likes inner join post on likes.postid =post.postid order by post");
		int count=0;
		int oldint=0;
		while (rs.next())
	      {  
			int id1 = rs.getInt("postid");
	      	if(id1==oldint||oldint==0) {
	      		count++;
	      	}else {
	      		JSONObject jo = new JSONObject();
		    	jo.put("count", count);
		    	jo.put("postid", id1);
		    	json.put(jo);
		    	count=0;
		    	oldint=0;
	      	}
	      }
	    return json;
	}
	public void prepareQuery(String queary2,Object[] des) throws ClassNotFoundException{
    	String String_Array[]=new String[des.length];
    	for (int i=0;i<String_Array.length;i++) String_Array[i]=des[i].toString();
    	Connection conn= null ;
        try {	Class.forName("org.postgresql.Driver");
	        	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta\",\"postgres\",\"masterkey");
				PreparedStatement stmt;
		        stmt = conn.prepareStatement(queary2);
		        for (int i=0;i<String_Array.length;i++) {
		        	if ((isNumeric(String_Array[i]))){
		        	stmt.setLong(i+1,Integer.parseInt(String_Array[i]));
		    		}else{
		    		stmt.setString(i+1, String_Array[i]);
		    		}
		         }
		        stmt.executeUpdate();
	          } catch (SQLException e) {
				e.printStackTrace();
			}
        System.out.println("Informacion almacenada");
    }
	
	public static boolean isNumeric(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }

	public static JSONObject imagetoget(String query, String output) {
		JSONArray  json = 	new JSONArray();
		JSONObject je= new JSONObject();
		try {
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setString(1, output); 
			ResultSet rs = statement.executeQuery();
		    while (rs.next())
		      {  JSONObject jo = new JSONObject();
		    	 int id = rs.getInt("postid");
		    	 int idu = rs.getInt("userid");
		         String photo = rs.getString("photo");
		         String description = rs.getString("description");
		         Date created_at = rs.getDate("created_at");
		         String email = rs.getString("email");
		         jo.put("postid", id);
		         jo.put("userid", idu);
		         jo.put("photo", photo);
		         jo.put("description", description);
		         jo.put("created_at ", created_at );
		         jo.put("email", email);
		         json.put(jo);
		      }
		    je.put("videoinfo", json);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return je;
	}
	
	public static void delete(int idp, String idu,String query) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		PreparedStatement statement = con.prepareStatement(query); 
		statement.setInt(1, idp);
		statement.setString(2, idu);
		statement.executeUpdate();
	}
	
	public static void delete(int idp,String query) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		PreparedStatement statement = con.prepareStatement(query); 
		statement.setInt(1, idp);
		statement.executeUpdate();
	}
	

	
	
	
	
	
	
}
