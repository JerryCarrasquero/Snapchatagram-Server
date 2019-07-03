package JO.org;

import java.io.IOException;
import java.io.InputStream;
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
 * Servlet implementation class loginhandler
 */
@WebServlet(description = "login system for app", urlPatterns = { "/loginhandler" })
public class loginhandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginhandler() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	//insert into usermain (name,email,password,biography,avatar) values ('Juan Jose','JuanJose@hotmail.com','7557141','blablabla','placeholder');
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject requestjson = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println("request post");
		System.out.println(requestjson);
		String n = requestjson.getString("Email");
		String a = requestjson.getString("password");
		String  query2="SELECT email,password,userid FROM usermain WHERE email='"+n+"' AND password='"+a+"'";
		database confirm = new database();
		try {
			String userid= database.gettoken(query2);
			if (userid != null && !userid.isEmpty()){
				PrintWriter out=response.getWriter();
				JSONObject succesfulllogin = new JSONObject();
				succesfulllogin.put("status","200");
				succesfulllogin.put("userid", userid);
				String succes=succesfulllogin.toString();
				out.println(succes);
				}else{
					PrintWriter out=response.getWriter();
					JSONObject existinguser = new JSONObject();
					existinguser.put("status","401");
					existinguser.put("message", "Wrong user or pasword");
					String advice=existinguser.toString();
					out.println(advice);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
