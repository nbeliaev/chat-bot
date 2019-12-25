package dev.fr13.configs;

import org.junit.Assert;
import org.junit.Test;

public class EnvConfigTest {

    @Test
    public void getProperty() {
        final String user = EnvConfig.getProperty(EnvConfig.USER_1C);
        Assert.assertEquals("bot", user);
    }
}