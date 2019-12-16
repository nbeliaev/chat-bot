package configs;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EnvConfig {
    public static final String USER_1C = "USER_1C";
    public static final String PASSWORD_1C = "PASSWORD_1C";
    private static Dotenv dotenv;
    private final static Logger log = LogManager.getLogger(EnvConfig.class);

    public static String getProperty(String propertyName) {
        if (dotenv == null) {
            dotenv = Dotenv.configure().load();
            log.debug("Env properties: " + dotenv);
        }
        return dotenv.get(propertyName);
    }

    private EnvConfig() {
        throw new UnsupportedOperationException();
    }
}
