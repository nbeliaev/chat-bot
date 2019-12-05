package utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatterTest {

    @Test
    public void checkRuLocale() {
        final NumberFormat formatter = PriceFormatter.getInstance(new Locale("ru"));
        double price = 1_000.02;
        Assert.assertEquals("1 000", formatter.format(price));
    }

    @Test
    public void checkEnLocale() {
        final NumberFormat formatter = PriceFormatter.getInstance(new Locale("VN"));
        double price = 100.02;
        Assert.assertEquals("100", formatter.format(price));
    }

    @Test
    public void checkRTwoDigits() {
        final NumberFormat formatter = PriceFormatter.getInstance(new Locale("RU"), 2);
        double price = 1_000.10;
        Assert.assertEquals("1 000,10", formatter.format(price));
    }
}