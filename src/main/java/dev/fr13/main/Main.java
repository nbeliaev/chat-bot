package dev.fr13.main;

import dev.fr13.backgroundjob.BackgroundJob;
import dev.fr13.backgroundjob.BackgroundJobRunner;
import dev.fr13.server.WebServer;

public class Main {

    public static void main(String[] args) throws Exception {
        new BackgroundJobRunner(BackgroundJob.class).run();
        new WebServer().run();
    }
}
