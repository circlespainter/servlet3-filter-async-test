package servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TestFilter implements Filter {
    private static final Logger logger = Logger.getLogger(TestFilter.class.getName());

    private final String name;
    private FilterConfig config;
    
    public TestFilter(String name) {
        this.name = name;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.log(Level.INFO, "Init filter {0}", name);
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.log(Level.INFO, "Before filter {0}, servletContext is null? {1}", new Object[] { name, (request.getServletContext() == null)});
        logger.log(Level.INFO, "Before filter {0}, filterConfig.servletContext is null? {1}", new Object[] { name, (config.getServletContext() == null)});
        logger.log(Level.INFO, "Before filter {0}, contextPath is null? {1}", new Object[] { name, (((HttpServletRequest) request).getContextPath() == null)});
        logger.log(Level.INFO, "Before filter {0}, requestURI is null? {1}", new Object[] { name, (((HttpServletRequest) request).getRequestURI() == null)});
        logger.log(Level.INFO, "Before filter {0}, request.asyncContext.request.servletContext is null? {1}", new Object[] { name, (((HttpServletRequest) request.getAsyncContext().getRequest()).getServletContext() == null)});
        logger.log(Level.INFO, "Before filter {0}, request.asyncContext.request.contextPath is null? {1}", new Object[] { name, (((HttpServletRequest) request.getAsyncContext().getRequest()).getContextPath() == null)});
        logger.log(Level.INFO, "Before filter {0}, request.asyncContext.request.requestURI is null? {1}", new Object[] { name, (((HttpServletRequest) request.getAsyncContext().getRequest()).getRequestURI() == null)});
        chain.doFilter(request, response);
        logger.log(Level.INFO, "After filter {0}, servletContext is null? {1}", new Object[] { name, (request.getServletContext() == null)});
        logger.log(Level.INFO, "After filter {0}, filterConfig.servletContext is null? {1}", new Object[] { name, (config.getServletContext() == null)});
        logger.log(Level.INFO, "After filter {0}, contextPath is null? {1}", new Object[] { name, (((HttpServletRequest) request).getContextPath() == null)});
        logger.log(Level.INFO, "After filter {0}, requestURI is null? {1}", new Object[] { name, (((HttpServletRequest) request).getRequestURI() == null)});
        logger.log(Level.INFO, "After filter {0}, request.asyncContext.request.servletContext is null? {1}", new Object[] { name, (((HttpServletRequest) request.getAsyncContext().getRequest()).getServletContext() == null)});
        logger.log(Level.INFO, "After filter {0}, request.asyncContext.request.contextPath is null? {1}", new Object[] { name, (((HttpServletRequest) request.getAsyncContext().getRequest()).getContextPath() == null)});
        logger.log(Level.INFO, "After filter {0}, request.asyncContext.request.requestURI is null? {1}", new Object[] { name, (((HttpServletRequest) request.getAsyncContext().getRequest()).getRequestURI() == null)});
    }

    @Override
    public void destroy() {
        logger.log(Level.INFO, "Destroy filter {0}", name);
    }
}
