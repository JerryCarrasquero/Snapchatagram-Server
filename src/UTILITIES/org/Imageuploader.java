package UTILITIES.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import JO.org.database;

/**
 * Servlet implementation class Imageuploader
 */
@WebServlet("/Imageuploader")
public class Imageuploader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Imageuploader() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		String query="select post.userid,post.photo,post.postid,post.description,post.created_at,usermain.email from post INNER JOIN usermain ON  usermain.userid = post.userid order by post.postid desc";
		JSONObject juan = new JSONObject();
		juan = database.imagetoget(query);
		//System.out.println(juan);
		PrintWriter out=response.getWriter();
		out.println(juan.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject requestjson = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println("request upload");
		String k = requestjson.getString("id");
		String y = requestjson.getString("image");
		String o = requestjson.getString("descripcion");
		//Date date = new Date();
		//java.sql.Timestamp timestamp = new java.sql.Timestamp(s.getTime());
		LocalDate todayLocalDate = LocalDate.now();  // Use proper "continent/region" time zone names; never use 3-4 letter codes like "EST" or "IST".

		java.sql.Date s = java.sql.Date.valueOf( todayLocalDate );
		System.out.println(k+" "+s+" "+o);
		k=k.toLowerCase();
		Object[] des2 = new Object[] {k,y,o,s};
		String query3= "insert into post (userid,photo,description,created_at) values (?,?,?,?)"; 
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
			ps.setInt (1, Integer.parseInt(k));
		    ps.setString (2, y);
		    ps.setString (3, o);
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
