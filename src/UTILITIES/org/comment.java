package UTILITIES.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class comment
 */
@WebServlet("/comment")
public class comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public comment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		String output = request.getParameter("postid");
		String query="SELECT comments.commentid, comments.comment, comments.createdat, comments.postid, usermain.userid ,usermain.email FROM comments INNER JOIN usermain on comments.postid= ? and usermain.userid=comments.userid order by comments desc";
		JSONArray ja = new JSONArray();
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn= null ;
			conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
			PreparedStatement statement = conn.prepareStatement(query); 
			statement.setLong(1,Integer.parseInt(output) ); 
			ResultSet rs = statement.executeQuery();
			while (rs.next())
			{	JSONObject comments = new JSONObject();
				int commentid = rs.getInt("commentid");
				String userid = rs.getString("userid");
	        	String email = rs.getString("email");
			    String comment = rs.getString("comment");
			    Date cdate = rs.getDate("createdat");
			    comments.put("commentid", commentid);
			    comments.put("userid", userid);
			    comments.put("email", email);
			    comments.put("createdat", cdate);
			    comments.put("comment", comment);
			    ja.put(comments);
	        }
			    PrintWriter out=response.getWriter();
			    String advice=ja.toString();
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
		System.out.println(requestjson);
		String k = requestjson.getString("userid");
		String y = requestjson.getString("comment");
		int o = requestjson.getInt("id");
		//Date date = new Date();
		//java.sql.Timestamp timestamp = new java.sql.Timestamp(s.getTime());
		LocalDate todayLocalDate = LocalDate.now();  // Use proper "continent/region" time zone names; never use 3-4 letter codes like "EST" or "IST".
		java.sql.Date s = java.sql.Date.valueOf( todayLocalDate );
		String query3= "insert into comments (userid,comment,postid,createdat) values (?,?,?,?)"; 
		PrintWriter out=response.getWriter();
		JSONObject existinguser = new JSONObject();
		String advice=null;
		Connection conn=null;
		try {
			Class.forName("org.postgresql.Driver");
	    	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
			PreparedStatement ps = conn.prepareStatement(query3);
			existinguser.put("status","200");
			existinguser.put("message", "Account Created");
			ps.setString (1, k);
		    ps.setString (2, y);
		    ps.setInt	 (3, o);
		    ps.setDate   (4, s);
		    ps.execute();
			advice=existinguser.toString();
			out.println(advice);
		} catch (SQLException | ClassNotFoundException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
