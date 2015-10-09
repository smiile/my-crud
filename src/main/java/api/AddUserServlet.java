package api;

import java.io.IOException;
import java.io.StringWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import auth.User;

@WebServlet("/api/addUser")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.getWriter().write("Illegal request");
	}

        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get entity manager
		EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
		EntityManager em = emf.createEntityManager();
		// Get parameters
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		JSONObject responseObj = new JSONObject();
		
		try {
			User user = new User(email, name, password);
			
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			
			// Generate row for table
			String row = "<tr><td class=\"name\">"+user.getName()+"</td><td class=\"email\">"+user.getEmail()+"</td><td><a href=\"#\" class=\"changePassword\">Change password</a></td><td>"+user.getRegisterDate().toString()+"</td><td><a href=\"#\" class=\"update\">Update</a> | <a href=\"#\" class=\"delete\">Delete</a></td><td><input type=\"hidden\" name=\"id\" value=\""+user.getId().toString()+"\"></td></tr>"; 
			
			responseObj.put("status", "OK");
			responseObj.put("data", row);
		} catch (Exception e) {
			responseObj.put("status", "FAIL");
			responseObj.put("data", e.getMessage());
			
			e.printStackTrace();
		} finally {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
		
		StringWriter out = new StringWriter();
		responseObj.writeJSONString(out);
		String jsonText = out.toString();

		response.getWriter().write(jsonText);
	}

}
