package point;
 
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PointListener implements ServletContextListener {
 
    // Prepare the EntityManagerFactory & Enhance:
    @Override
    public void contextInitialized(ServletContextEvent e) {
        com.objectdb.Enhancer.enhance("point.*");
        com.objectdb.Enhancer.enhance("auth.*");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/my.odb");
        e.getServletContext().setAttribute("emf", emf);
    }
 
    // Release the EntityManagerFactory:
    @Override
    public void contextDestroyed(ServletContextEvent e) {
        EntityManagerFactory emf =
            (EntityManagerFactory)e.getServletContext().getAttribute("emf");
        emf.close();
    }
}