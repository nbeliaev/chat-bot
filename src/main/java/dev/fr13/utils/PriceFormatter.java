package dev.fr13.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatter {

    public static NumberFormat getInstance(Locale locale) {
        return getInstance(locale, 0);
    }

    public static NumberFormat getInstance(Locale locale, int fractionDigits) {
        final NumberFormat format = NumberFormat.getInstance(locale);
        format.setMinimumFractionDigits(fractionDigits);
        format.setMaximumFractionDigits(fractionDigits);
        return format;
    }

    private PriceFormatter() {
        throw new UnsupportedOperationException();
    }
}
