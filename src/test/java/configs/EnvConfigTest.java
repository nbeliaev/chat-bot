package configs;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnvConfigTest {

    @Test
    public void getProperty() {
        final String user = EnvConfig.getProperty(EnvConfig.USER_1C);
        Assert.assertEquals("bot", user);
    }
}