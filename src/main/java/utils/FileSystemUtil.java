package utils;

import java.io.*;

public class FileSystemUtil {
    public static void save(File file, InputStream input) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             final BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
