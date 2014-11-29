package container;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

// import servlet.TestContainerInitializerFiber;
import servlet.init.thread.TestContainerInitializerThread;

/**
 * @author circlespainter
 */
public class TomcatEmbeddedRunner {
    public void startServer() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        File base = new File(System.getProperty("java.io.tmpdir"));
        Context rootCtx = tomcat.addContext("/app", base.getAbsolutePath());
        // rootCtx.addServletContainerInitializer(new TestContainerInitializerFiber(), null);
        rootCtx.addServletContainerInitializer(new TestContainerInitializerThread(), null);

        tomcat.start();
        tomcat.getServer().await();
    }
}
