package app;

import org.apache.catalina.LifecycleException;

import container.TomcatEmbeddedRunner;
import java.util.logging.Logger;

/**
 * @author circlespainter
 */
public class Tomcat {
    public static void main(final String[] args) throws LifecycleException {
        Logger.getLogger(Tomcat.class.getName()).info("Starting Tomcat");
        new TomcatEmbeddedRunner().startServer();
    }
}
