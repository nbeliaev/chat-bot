package main;

import configs.Config;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SimpleServlet;

public class Main {

    public static void main(String[] args) throws Exception {
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SimpleServlet()), "/");
        final Server server = new Server(Integer.parseInt(Config.getProperty(Config.PORT)));
        server.setHandler(context);
        server.start();
        server.join();
    }
}
