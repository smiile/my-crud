package api;

import java.io.IOException;
import java.io.StringWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import auth.User;

@WebServlet("/api/changePassword")
public class ChangePasswordServlet extends HttpServlet {
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
		String id = request.getParameter("id");
		String password =  request.getParameter("password");
		
		JSONObject responseObj = new JSONObject();

		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE id=:id", User.class);
			User user = query.setParameter("id", Integer.parseInt(id)).getSingleResult();
			
			em.getTransaction().begin();
			user.setNewPassword(password);
			em.getTransaction().commit();

			responseObj.put("status", "OK");
			responseObj.put("data", null);
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
