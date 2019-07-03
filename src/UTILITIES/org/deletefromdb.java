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

import org.json.JSONObject;

import JO.org.database;

/**
 * Servlet implementation class deletefromdb
 */
@WebServlet("/deletefromdb")
public class deletefromdb extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deletefromdb() {
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
		int k = requestjson.getInt("idp");
		String y = requestjson.getString("idu");
		int u = requestjson.getInt("object");
		JSONObject juan = new JSONObject();
		System.out.println(u);
		if (u==1) {
			System.out.println("hey");
			String query = "DELETE FROM likes where postid=? and userid=?";
			try {
				database.delete(k,y,query);
				juan.put("code",200);
				juan.put("message", "succesfully deleted");
			} catch (ClassNotFoundException | SQLException e) {
				juan.put("code",500);
				juan.put("message", "succesfully deleted");
				e.printStackTrace();
			}
		}else if (u==2){
			String query;
			try {
				
				query = "DELETE FROM likes where postid=?";
				database.delete(k, query);
				query = "DELETE FROM comments where postid=?";
				database.delete(k, query);
				query = "DELETE FROM post where postid=?";
				database.delete(k, query);
				juan.put("code",200);
				juan.put("message", "succesfully deleted");
			} catch (ClassNotFoundException | SQLException e) {
				juan.put("code",500);
				juan.put("message", "succesfully deleted");
				e.printStackTrace();
				e.printStackTrace();
			}
		}else {try {
			String query;
			query = "DELETE FROM comments where commentid=?";
			database.delete(k, query);
			juan.put("code",200);
			juan.put("message", "succesfully deleted");
		} catch (ClassNotFoundException | SQLException e) {
			juan.put("code",500);
			juan.put("message", "Internall server error");
			e.printStackTrace();
			e.printStackTrace();
		}}
		PrintWriter out=response.getWriter();
		System.out.println(juan.toString());
		out.println(juan.toString());
		}

}
