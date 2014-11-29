package app;

import container.JettyEmbeddedRunner;
import java.util.logging.Logger;

/**
 * @author circlespainter
 */
public class Jetty {
    public static void main(final String[] args) {
        Logger.getLogger(Jetty.class.getName()).info("Starting Jetty");
        new JettyEmbeddedRunner().startServer();
    }
}
