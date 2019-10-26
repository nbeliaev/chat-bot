package utils;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;

public class WebUtil {
    public static Path saveWebPage(String address) {
        try {
            final URL url = new URL(address);
            final File file = File.createTempFile("index", ".html");
            FileSystemUtil.save(file, url.openStream());
            return file.toPath();
        } catch (IOException e) {
            // TODO
            throw new IllegalStateException("Couldn't get a page: " + address);
        }
    }
}
