package servlet.fiber;

// import co.paralleluniverse.fibers.Suspendable;
// import co.paralleluniverse.fibers.servlet.FiberHttpServlet;
import java.io.IOException;
import static java.lang.System.out;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DatePrintServletFiber /* extends FiberHttpServlet */ {
    private static final long serialVersionUID = 1L;
    
//    @Override
//    @Suspendable
    public void service(ServletRequest req, ServletResponse res) {
        out.println("Entering DatePrintServlet.service");
        try {
//            suspendableService(req, res);
        } catch (Exception ex) {
//            log("Exception in fiber servlet", ex);
        }
        out.println("Exiting DatePrintServlet.service");
    }

//    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        out.println("Entering DatePrintServlet.doGet");
        res.getWriter().append(String.format("It's %s now ", new Date()));
        out.println("Exiting DatePrintServlet.doGet");
    }
}
