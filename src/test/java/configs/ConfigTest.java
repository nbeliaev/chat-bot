package configs;

import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void getProperty() {
        final String user = Config.getProperty(Config.USER_1C);
        Assert.assertEquals("bot", user);
    }
}