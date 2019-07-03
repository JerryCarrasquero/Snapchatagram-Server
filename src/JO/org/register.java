package JO.org;

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

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		System.out.println("request");
		String n = request.getParameter("email");
		n=n.toLowerCase();
		String query2 ="SELECT email,userid FROM usermain WHERE email='"+n+"'";
		database doggo = new database();
		try {
			String userid= database.gettoken(query2);
			if (userid == null){
				PrintWriter out=response.getWriter();
				JSONObject succesfulllogin = new JSONObject();
				succesfulllogin.put("status","200");
				succesfulllogin.put("message","Account available");
				String succes=succesfulllogin.toString();
				out.println(succes);
				}else{
					PrintWriter out=response.getWriter();
					JSONObject existinguser = new JSONObject();
					existinguser.put("status","401");
					existinguser.put("message", "Account already exist.");
					String advice=existinguser.toString();
					out.println(advice);
				}
		} catch (SQLException e) {
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
		String k = requestjson.getString("name");
		String y = requestjson.getString("Email");
		String o = requestjson.getString("password");
		String s = " ";
		System.out.println(k+""+s+""+y+""+o);
		k=k.toLowerCase();
		Object[] des2 = new Object[] {k,y,o,s,s};
		String query3= "insert into usermain (name,email,password,biography,avatar) values (?,?,?,?,?)"; 
		PrintWriter out=response.getWriter();
		JSONObject existinguser = new JSONObject();
		String advice=null;
		try {
			existinguser.put("status","200");
			existinguser.put("message", "Account Created");
			database.gettoken(query3, des2);
			advice=existinguser.toString();
			out.println(advice);
		} catch (SQLException | ClassNotFoundException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
