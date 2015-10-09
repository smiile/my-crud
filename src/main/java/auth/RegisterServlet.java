package auth;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/data/auth/register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		if (Objects.equals(email, "") || Objects.equals(name, "") || Objects.equals(password, "")) {
			request.setAttribute("error", "All fields are mandatory.");
			request.getRequestDispatcher("/data/auth/register.jsp").forward(request, response);
		} else {
			EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
			EntityManager em = emf.createEntityManager();
			try {
				em.getTransaction().begin();
				em.persist(new User(email, name, password));
				em.getTransaction().commit();
			} finally {
				// Close the database connection:
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				em.close();
			}
			
			response.sendRedirect(request.getServletContext().getContextPath());
		}
	}
}
