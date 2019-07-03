package UTILITIES.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * Servlet implementation class imageviver
 */
@WebServlet("/imageviver")
public class imageviver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public imageviver() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject requestjson = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println(requestjson);
		int k = requestjson.getInt("id");
		String u = requestjson.getString("uid");
		String query="select post.userid,post.photo,post.postid,post.description,post.created_at,usermain.email from post INNER JOIN usermain ON  usermain.userid = post.userid  where post.postid= ?";
		JSONObject juan = new JSONObject();
		juan = database.imagetoget(query,k);
		try {
			JSONObject jose=new JSONObject();
			jose=database.checkedifliked(k,u);
			int likedid = jose.getInt("likedspk");
			boolean likedbool=jose.getBoolean("liked");
			juan.put("liked", likedbool);
			juan.put("likedid", likedid);
			juan.put("count", database.checkcount(k));
			System.out.println("help");
		} catch (JSONException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject je=new JSONObject();
		je.put("videoinfo", juan);
		PrintWriter out=response.getWriter();
		out.println(je.toString());
	}

}
