package servlet.init.thread;

import java.util.Set;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import servlet.thread.DatePrintServletThread;
import servlet.TestFilter;
import servlet.thread.ThreadFilter;

/**
 * @author circlespainter
 */
public class TestContainerInitializerThread implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext sc) throws ServletException {
        FilterRegistration.Dynamic ff = sc.addFilter("threadFilter", new ThreadFilter());
        ff.addMappingForServletNames(null, true, "dateServlet");
        ff.addMappingForUrlPatterns(null, true, "/*");
        ff.setAsyncSupported(true);

        FilterRegistration.Dynamic ft1 = sc.addFilter("testFilter1", new TestFilter("1"));
        ft1.addMappingForServletNames(null, true, "dateServlet");
        ft1.addMappingForUrlPatterns(null, true, "/*");
        ft1.setAsyncSupported(true);
        
        FilterRegistration.Dynamic ft2 = sc.addFilter("testFilter2", new TestFilter("2"));
        ft2.addMappingForServletNames(null, true, "dateServlet");
        ft2.addMappingForUrlPatterns(null, true, "/*");
        ft2.setAsyncSupported(true);

        Dynamic s = sc.addServlet("dateServlet", new DatePrintServletThread());
        s.setAsyncSupported(true);
        s.setLoadOnStartup(1);
        s.addMapping("/date");
    }
}