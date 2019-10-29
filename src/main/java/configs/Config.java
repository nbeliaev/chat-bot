package configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final String USER_1C = "1C.user";
    public static final String PASSWORD_1C = "1C.password";
    public static final String CONNECTION_1C = "1C.connection-url";
    private static Properties properties;

    public static String getProperty(String propertyName) {
        if (properties == null) {
            initProperties();
        }
        return properties.getProperty(propertyName);
    }

    private static void initProperties() {
        try (final InputStream in = Config.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (in != null) {
                properties = new Properties();
                properties.load(in);
            } else {
                throwIllegalException();
            }
        } catch (IOException e) {
            throwIllegalException();
        }
    }

    private static void throwIllegalException() {
        throw new IllegalStateException("Couldn't load applications settings");
    }

    private Config() {

    }
}
