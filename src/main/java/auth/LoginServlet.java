package auth;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/data/auth/login.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (Objects.equals(email, "") || Objects.equals(password, "")) {
			request.setAttribute("error", "All fields are mandatory.");
			request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
		} else {
			EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
			EntityManager em = emf.createEntityManager();

			// TODO: auth logic
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email= :email", User.class);
			query.setParameter("email", email);

			try {
				User user = query.getSingleResult();

				if (!Objects.equals(user.getPassword(), password)) {
					request.setAttribute("error", "Wrong email/password combination.");
					request.getRequestDispatcher("/data/auth/login.jsp").forward(request, response);
				} else {
					// Legit user; set session data & redirect
					HttpSession session = request.getSession();
		            session.setAttribute("username", user.getName());
		            session.setAttribute("authenticated", true);
		            //setting session to expiry in 30 mins
		            session.setMaxInactiveInterval(30*60);
		            
					response.sendRedirect(request.getServletContext().getContextPath());
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				request.setAttribute("error", "No such user.");
				request.getRequestDispatcher("/data/auth/login.jsp").forward(request, response);
			}

		}
	}
}
