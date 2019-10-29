package utils;

import configs.Config;
import org.apache.commons.codec.binary.Base64;

public class AuthUtil {

    public static String get1CAuthorization() {
        final String login = Config.getProperty(Config.USER_1C) +
                ":" +
                Config.getProperty(Config.PASSWORD_1C);
        return "Basic " + new String(Base64.encodeBase64(login.getBytes()));
    }
}
