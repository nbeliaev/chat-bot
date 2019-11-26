package utils;

import configs.Config;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PriceFormatter {

    public static String format(double price) {
        NumberFormat formatter = new DecimalFormat(Config.getProperty(Config.PRICE_FORMAT));
        return formatter.format(price);
    }

    private PriceFormatter() {
        throw new UnsupportedOperationException();
    }
}
