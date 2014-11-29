package servlet.thread;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author circlespainter
 */
public class ThreadFilter implements Filter {
    private static final Logger logger = Logger.getLogger(ThreadFilter.class.getName());

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.log(Level.INFO, "Init thread filter");
        this.config = filterConfig;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        logger.log(Level.INFO, "Entering thread filter, servletContext is null? {0}", new Object[] { (request.getServletContext() == null)});
        logger.log(Level.INFO, "Entering thread filter, filterConfig.servletContext is null? {0}", new Object[] { (config.getServletContext() == null)});
        logger.log(Level.INFO, "Entering thread filter, contextPath is null? {0}", new Object[] { (((HttpServletRequest) request).getContextPath() == null)});
        logger.log(Level.INFO, "Entering thread filter, requestURI is null? {0}", new Object[] { (((HttpServletRequest) request).getRequestURI() == null)});

        logger.log(Level.INFO, "Thread filter: starting async");
        final AsyncContext asyncContext = request.startAsync();
        
        logger.log(Level.INFO, "Thread filter after starting async, servletContext is null? {0}", new Object[] { (request.getServletContext() == null)});
        logger.log(Level.INFO, "Thread filter after starting async, filterConfig.servletContext is null? {0}", new Object[] { (config.getServletContext() == null)});
        logger.log(Level.INFO, "Thread filter after starting async, contextPath is null? {0}", new Object[] { (((HttpServletRequest) request).getContextPath() == null)});
        logger.log(Level.INFO, "Thread filter after starting async, requestURI is null? {0}", new Object[] { (((HttpServletRequest) request).getRequestURI() == null)});
        logger.log(Level.INFO, "Thread filter after starting async, request.asyncContext.request.servletContext is null? {0}", new Object[] { (((HttpServletRequest) request.getAsyncContext().getRequest()).getServletContext() == null)});
        logger.log(Level.INFO, "Thread filter after starting async, request.asyncContext.request.contextPath is null? {0}", new Object[] { (((HttpServletRequest) request.getAsyncContext().getRequest()).getContextPath() == null)});
        logger.log(Level.INFO, "Thread filter after starting async, request.asyncContext.request.requestURI is null? {0}", new Object[] { (((HttpServletRequest) request.getAsyncContext().getRequest()).getRequestURI() == null)});

        logger.log(Level.INFO, "\n\n\n\n*******************************************************\nThread filter: starting new thread for chain processing\n*******************************************************");
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.log(Level.INFO, "Thread filter (new thread): before chain.doFilter");
                try {
                    logger.log(Level.INFO, "Thread filter (new thread): before chain.doFilter, servletContext is null? {0}", new Object[] { (request.getServletContext() == null)});
                    logger.log(Level.INFO, "Thread filter (new thread): before chain.doFilter, filterConfig.servletContext is null? {0}", new Object[] { (config.getServletContext() == null)});
                    logger.log(Level.INFO, "Thread filter (new thread): before chain.doFilter, contextPath is null? {0}", new Object[] { (((HttpServletRequest) request).getContextPath() == null)});
                    logger.log(Level.INFO, "Thread filter (new thread): before chain.doFilter, requestURI is null? {0}", new Object[] { (((HttpServletRequest) request).getRequestURI() == null)});
                    logger.log(Level.INFO, "Thread filter (new thread): before chain.doFilter, request.asyncContext.request.servletContext is null? {0}", new Object[] { (((HttpServletRequest) request.getAsyncContext().getRequest()).getServletContext() == null)});
                    logger.log(Level.INFO, "Thread filter (new thread): before chain.doFilter, request.asyncContext.request.contextPath is null? {0}", new Object[] { (((HttpServletRequest) request.getAsyncContext().getRequest()).getContextPath() == null)});
                    logger.log(Level.INFO, "Thread filter (new thread): before chain.doFilter, request.asyncContext.request.requestURI is null? {0}", new Object[] { (((HttpServletRequest) request.getAsyncContext().getRequest()).getRequestURI()== null)});
                    chain.doFilter(request, response);
                    logger.log(Level.INFO, "Thread filter (new thread): after chain.doFilter, servletContext is null? {0}", new Object[] { (request.getServletContext() != null)});
                    logger.log(Level.INFO, "Thread filter (new thread): after chain.doFilter, filterConfig.servletContext is null? {0}", new Object[] { (config.getServletContext() != null)});
                    logger.log(Level.INFO, "Thread filter (new thread): after chain.doFilter, contextPath is null? {0}", new Object[] { (((HttpServletRequest) request).getContextPath() != null)});
                    logger.log(Level.INFO, "Thread filter (new thread): after chain.doFilter, requestURI is null? {0}", new Object[] { (((HttpServletRequest) request).getRequestURI() != null)});
                    logger.log(Level.INFO, "Thread filter (new thread): after chain.doFilter, request.asyncContext.request.servletContext is null? {0}", new Object[] { (((HttpServletRequest) request.getAsyncContext().getRequest()).getServletContext() == null)});
                    logger.log(Level.INFO, "Thread filter (new thread): after chain.doFilter, request.asyncContext.request.contextPath is null? {0}", new Object[] { (((HttpServletRequest) request.getAsyncContext().getRequest()).getContextPath() == null)});
                    logger.log(Level.INFO, "Thread filter (new thread): after chain.doFilter, request.asyncContext.request.requestURI is null? {0}", new Object[] { (((HttpServletRequest) request.getAsyncContext().getRequest()).getRequestURI() == null)});
                } catch (ServletException | IOException ex) {
                    Logger.getLogger(ThreadFilter.class.getName()).log(Level.SEVERE, null, ex);
                }
                logger.log(Level.INFO, "Thread filter (new thread): after chain.doFilter, committing response");
                asyncContext.complete();
                logger.log(Level.INFO, "Thread filter (new thread): after committing response");
            }
        }).start();

        logger.log(Level.INFO, "Thread filter: returning after dispatching rest of processing to a new thread");
    }

    @Override
    public void destroy() {
        logger.log(Level.INFO, "Destroy thread filter");
    }
}