package main;

import backgroundjob.BackgroundJob;
import backgroundjob.BackgroundJobRunner;
import server.WebServer;

public class Main {

    public static void main(String[] args) throws Exception {
        new BackgroundJobRunner(BackgroundJob.class).run();
        new WebServer().run();
    }
}
