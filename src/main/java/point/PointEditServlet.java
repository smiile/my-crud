package point;

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

@WebServlet("/point/edit")
public class PointEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String deleteFlag = request.getParameter("delete");

		if (id == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		request.setAttribute("id", id);

		// Get entity manager
		EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
		EntityManager em = emf.createEntityManager();

		// Get the selected point entity
		TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p WHERE id=" + id, Point.class);
		Point p = query.getSingleResult();

		if (Objects.equals(deleteFlag, "1")) { // Delete functionality
			deletePoint(Integer.valueOf(id));
			// Redirect to point list
			response.sendRedirect("list");
		} else { // Update functionality
			request.setAttribute("point", p);
			request.getRequestDispatcher("/data/point/edit.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");

		if (id == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		request.setAttribute("id", id);

		Integer x = null;
		Integer y = null;

		try {
			x = Integer.valueOf(request.getParameter("x"));
			y = Integer.valueOf(request.getParameter("y"));
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}

		if (x != null && y != null) {
			// Update entity
			updatePoint(Integer.valueOf(id), x, y);

			// Redirect to point list
			response.sendRedirect("list");
		} else {

			request.setAttribute("error", "All fields are mandatory");
			request.getRequestDispatcher("/data/point/edit.jsp").forward(request, response);
		}
	}

	private void updatePoint(Integer id, Integer x, Integer y) {
		// Get entity manager
		EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
		EntityManager em = emf.createEntityManager();

		TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p WHERE id=" + id, Point.class);
		Point p = query.getSingleResult();

		try {
			// Update entity
			em.getTransaction().begin();
			p.setX(x.intValue());
			p.setY(y.intValue());
			em.getTransaction().commit();
		} finally {
			// Close the database connection:
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}
	}

	private void deletePoint(Integer id) {
		// Get entity manager
		EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
		EntityManager em = emf.createEntityManager();

		// Get the selected point entity
		TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p WHERE id=" + id, Point.class);
		Point p = query.getSingleResult();
		
		try {
			em.getTransaction().begin();
			em.remove(p);
			em.getTransaction().commit();
		} finally {
			// Close the database connection:
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

}
