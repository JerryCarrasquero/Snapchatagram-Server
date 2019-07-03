package UTILITIES.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import JO.org.database;

/**
 * Servlet implementation class likes
 */
@WebServlet("/likes")
public class likes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public likes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject requestjson = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println("request upload");
		String k = requestjson.getString("userid");
		int y = requestjson.getInt("idpost");
		System.out.println(k+" "+y);
		String query3= "insert into likes (userid,postid) values (?,?)"; 
		PrintWriter out=response.getWriter();
		JSONObject existinguser = new JSONObject();
		String advice=null;
		Connection conn=null;
		try {
			Class.forName("org.postgresql.Driver");
	    	conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/fakeinsta","postgres","masterkey");
			PreparedStatement ps = conn.prepareStatement(query3);
			existinguser.put("status","200");
			existinguser.put("message", "liked");
			ps.setString (1, k);
		    ps.setInt (2, y);
		    ps.execute();
			advice=existinguser.toString();
			out.println(advice);
		} catch (SQLException | ClassNotFoundException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
