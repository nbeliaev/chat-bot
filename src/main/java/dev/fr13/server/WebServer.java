package dev.fr13.server;

import dev.fr13.configs.Config;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import dev.fr13.servlets.DialogflowServlet;

public class WebServer {
    private final Server server;
    private final static Logger log = LogManager.getLogger(WebServer.class);

    public WebServer() {
        server = new Server(Integer.parseInt(Config.getProperty(Config.PORT)));
        server.setHandler(getContext());
    }

    public void run() throws Exception {
        server.start();
        log.info(String.format("Server has been started, port %s.", Integer.parseInt(Config.getProperty(Config.PORT))));
        server.join();
    }

    private ServletContextHandler getContext() {
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new DialogflowServlet()), "/");
        return context;
    }
}
