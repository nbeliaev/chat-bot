package configs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final String CONNECTION_1C = "1C.connection-url";
    public static final String UPDATE_FREQUENCY = "1C.update-frequency";
    public static final String PORT = "jetty.port";
    public static final String CURRENCY = "measure.currency";
    public static final String PRICE_FORMAT = "measure.fraction-digits-in-price";
    private static Properties properties;
    private final static Logger log = LogManager.getLogger(Config.class);

    public static String getProperty(String propertyName) {
        if (properties == null) {
            initProperties();
        }
        return properties.getProperty(propertyName);
    }

    private static void initProperties() {
        try (final InputStream in = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                properties = new Properties();
                properties.load(in);
                log.debug("Application properties: " + properties);
            } else {
                throwIllegalException();
            }
        } catch (IOException e) {
            throwIllegalException();
        }
    }

    private static void throwIllegalException() {
        final String message = "Couldn't load applications settings.";
        log.error(message);
        throw new IllegalStateException(message);
    }

    private Config() {
        throw new UnsupportedOperationException();
    }
}
