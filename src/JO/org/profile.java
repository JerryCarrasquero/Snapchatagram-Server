package JO.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import JC.org.JDBCproto;

/**
 * Servlet implementation class profile
 */
@WebServlet("/profile")
public class profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//select* from usermain where userid= '1'
		response.addHeader("Access-Control-Allow-Origin", "*");
		String output = request.getParameter("Userid");
		String query="select usermain.name,usermain.email,usermain.password,usermain.biography,post.postid, post.photo from usermain left join post on usermain.userid = post.userid  where usermain.userid = ? order by post.postid desc";
		String name=null;
		String email=null;
		String pasword=null;
		String bio =null;
		String photo=null;
		int postid=0;
		try {
			JSONObject json = new JSONObject();
			JSONArray ja = new JSONArray();
			Class.forName("org.postgresql.Driver");
			Connection conn= null ;
			conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
			PreparedStatement statement = conn.prepareStatement(query); 
			statement.setString(1, output); 
			JSONObject existinguser = new JSONObject();
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{	JSONObject fotogallery = new JSONObject();
	        	name = rs.getString("name");
			    email = rs.getString("email");
			    pasword = rs.getString("password");
			    bio = rs.getString("biography");
			    postid= rs.getInt("postid");
			    photo = rs.getString("photo");
			    System.out.println(name+" "+email+"");
			    existinguser.put("name",name);
			    existinguser.put("email",email);
			    existinguser.put("password",pasword);
			    existinguser.put("biography",bio);
			    fotogallery.put("postid", postid);
			    fotogallery.put("photo", photo);
			    ja.put(fotogallery);
	        }
			    json.put("userinfo", existinguser);
			    json.put("image",ja);
			    PrintWriter out=response.getWriter();
			    String advice=json.toString();
			    out.println(advice);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject requestjson = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println("request post");
		System.out.println(requestjson);
		String y = requestjson.getString("name");
		String k = requestjson.getString("email");
		String o = requestjson.getString("password");
		String id =requestjson.getString("userid");
		String s = requestjson.getString("biography");
		System.out.println("hello");
		Object[] des2 = new Object[] {k,o,y,s};
	    String query="UPDATE usermain SET email = ?, password = ? , name = ? ,biography=? WHERE userid = ? ";
	    Connection conn=null;
	    try {
	    	Class.forName("org.postgresql.Driver");
	    	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(5,id);
			ps.setString(1,k);
			ps.setString(2,o);
			ps.setString(3,y);
			ps.setString(4,s);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    System.out.println("yeah idk boss");
	    PrintWriter out=response.getWriter();
		JSONObject existinguser = new JSONObject();
		String advice=null;
		try {
			existinguser.put("status","200");
			existinguser.put("message", "Account Updated");
			database.gettoken(query, des2);
			advice=existinguser.toString();
			out.println(advice);
		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
