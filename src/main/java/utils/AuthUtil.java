package utils;

import configs.EnvConfig;
import org.apache.commons.codec.binary.Base64;

public class AuthUtil {

    public static String getBasicAuthorization() {
        final String login = EnvConfig.getProperty(EnvConfig.USER_1C) +
                ":" +
                EnvConfig.getProperty(EnvConfig.PASSWORD_1C);
        return "Basic " + new String(Base64.encodeBase64(login.getBytes()));
    }

    private AuthUtil() {
        throw new UnsupportedOperationException();
    }
}
