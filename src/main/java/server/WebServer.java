package server;

import configs.Config;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.DialogflowServlet;

public class WebServer {
    private final Server server;

    public WebServer() {
        server = new Server(Integer.parseInt(Config.getProperty(Config.PORT)));
        server.setHandler(getContext());
    }

    public void run() throws Exception {
        server.start();
        server.join();
    }

    private ServletContextHandler getContext() {
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new DialogflowServlet()), "/");
        return context;
    }
}
