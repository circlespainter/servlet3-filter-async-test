package servlet.init.thread;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.thread.DatePrintServletThread;
import servlet.TestFilter;
import servlet.thread.ThreadFilter;

/**
 * @author circlespainter
 */
public class JettyInitializerThread {
    public static void setup(Server server) {
        ServletContextHandler handler = new ServletContextHandler(server, "/app", true, false);

        FilterHolder ff = new FilterHolder(new ThreadFilter());
        // FilterHolder ff = new FilterHolder(new FiberFilter());
        ff.setAsyncSupported(true);
        handler.addFilter(ff, "/*", null);

        FilterHolder ft1 = new FilterHolder(new TestFilter("1"));
        ft1.setAsyncSupported(true);
        handler.addFilter(ft1, "/*", null);

        FilterHolder ft2 = new FilterHolder(new TestFilter("2"));
        ft2.setAsyncSupported(true);
        handler.addFilter(ft2, "/*", null);

        ServletHolder servletHolder = new ServletHolder(DatePrintServletThread.class);
        // ServletHolder servletHolder = new ServletHolder(DatePrintServletFiber.class);
        servletHolder.setAsyncSupported(true);
        handler.addServlet(servletHolder, "/date");
    }    
}
