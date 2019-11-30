package configs;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    public static final String USER_1C = "USER_1C";
    public static final String PASSWORD_1C = "PASSWORD_1C";
    private static Dotenv dotenv;

    public static String getProperty(String propertyName) {
        if (dotenv == null) {
            dotenv = Dotenv.configure().load();
        }
        return dotenv.get(propertyName);
    }

    private EnvConfig() {
        throw new UnsupportedOperationException();
    }
}
