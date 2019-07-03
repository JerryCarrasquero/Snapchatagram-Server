package UTILITIES.org;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import JO.org.database;

/**
 * Servlet implementation class favlist
 */
@WebServlet("/favlist")
public class favlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public favlist() {
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
		String query ="select post.userid,post.photo,likes.likespk,post.postid,post.description,post.created_at,usermain.email from post INNER JOIN likes on likes.postid=post.postid  INNER JOIN usermain ON  usermain.userid = post.userid where likes.userid= ?  order by likes.likespk desc";
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject requestjson = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println(requestjson);
		String k = requestjson.getString("id");
		JSONObject juan = new JSONObject();
		juan = database.imagetoget(query,k);
		PrintWriter out=response.getWriter();
		out.println(juan.toString());
	}

}
