package container;

import org.eclipse.jetty.server.Server;

import servlet.init.thread.JettyInitializerThread;

/**
 * @author circlespainter
 */
public class JettyEmbeddedRunner {
    public void startServer() {
        try {
            Server server = new Server(8080);

            // JettyInitializerFiber.setup(server);
            JettyInitializerThread.setup(server);

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
