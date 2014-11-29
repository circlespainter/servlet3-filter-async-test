package servlet.init.fiber;

// import co.paralleluniverse.fibers.springframework.web.servlet.FiberFilter;
import java.util.Set;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import servlet.TestFilter;

/**
 * @author circlespainter
 */
public class TestContainerInitializerFiber implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext sc) throws ServletException {
/*        FilterRegistration.Dynamic ff = sc.addFilter("fiberFilter", new FiberFilter());
        ff.addMappingForServletNames(null, true, "dateServlet");
        ff.addMappingForUrlPatterns(null, true, "/*");
        ff.setAsyncSupported(true); */

        FilterRegistration.Dynamic ft1 = sc.addFilter("testFilter1", new TestFilter("1"));
        ft1.addMappingForServletNames(null, true, "dateServlet");
        ft1.addMappingForUrlPatterns(null, true, "/*");
        ft1.setAsyncSupported(true);
        
        FilterRegistration.Dynamic ft2 = sc.addFilter("testFilter2", new TestFilter("2"));
        ft2.addMappingForServletNames(null, true, "dateServlet");
        ft2.addMappingForUrlPatterns(null, true, "/*");
        ft2.setAsyncSupported(true);

        /* Dynamic s = sc.addServlet("dateServlet", new DatePrintServletFiber());
        s.setAsyncSupported(true);
        s.setLoadOnStartup(1);
        s.addMapping("/date"); */
    }
}

