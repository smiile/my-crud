package auth;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/data/auth/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);
        boolean isValidEmail = m.matches();
        boolean hasError = false;

        StringBuilder errors = new StringBuilder();

        if (Objects.equals(email, "")) {
            hasError = true;
            errors.append("Email is mandatory.<br/>");
        }
        if (Objects.equals(name, "")) {
            hasError = true;
            errors.append("Name is mandatory.<br/>");
        }
        if (Objects.equals(password, "")) {
            hasError = true;
            errors.append("Password is mandatory.<br/>");
        }
        if (!isValidEmail && !Objects.equals(email, "")) {
            hasError = true;
            errors.append("Invalid email.<br/>");
        }

        if (!hasError) {
            EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(new User(email, name, password));
                em.getTransaction().commit();
            } finally {
                // Close the database connection:
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }

            response.sendRedirect(request.getServletContext().getContextPath());
        } else {
            request.setAttribute("error", errors.toString());
            request.getRequestDispatcher("/data/auth/register.jsp").forward(request, response);
        }
    }
}
