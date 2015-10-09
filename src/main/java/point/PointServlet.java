package point;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/point/list")
public class PointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtain a database connection:
		EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
		EntityManager em = emf.createEntityManager();

		try {
			Integer x = null;
			Integer y = null;
			
			// Handle new point insertion
			try {
				x = Integer.valueOf(request.getParameter("x"));
				y = Integer.valueOf(request.getParameter("y"));
			} catch (NumberFormatException e) {
				// Ignore
			}

			if (x != null && y != null) {
				em.getTransaction().begin();
				em.persist(new Point(x, y));
				em.getTransaction().commit();
			}
			
			// Get existing points
			List<Point> pointList = em.createQuery("SELECT p FROM Point p", Point.class).getResultList();
			request.setAttribute("points", pointList);
			
			// Dispatch to jsp
			request.getRequestDispatcher("/data/point/list.jsp").forward(request, response);
			
		} finally {
			// Close the database connection:
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
