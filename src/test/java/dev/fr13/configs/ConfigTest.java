package dev.fr13.configs;

import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void getProperty() {
        final String currency = Config.getProperty(Config.CURRENCY);
        Assert.assertEquals("VND", currency);
    }
}