package servlet.thread;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlet.TestFilter;

/**
 * @author circlespainter
 */
public class DatePrintServletThread extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TestFilter.class.getName());
    
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        logger.info("Servlet entering DatePrintServlet.doGet");
        res.getWriter().append(String.format("It's %s now ", new Date()));
        logger.info("Servlet exiting DatePrintServlet.doGet");
    }
}
