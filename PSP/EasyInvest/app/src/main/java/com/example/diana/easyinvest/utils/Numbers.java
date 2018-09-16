package com.example.diana.easyinvest.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Numbers {

    private static DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));

    private Numbers() {
    }

    public static String unifiedDouble(double d) {
        return df.format(d);
    }
}
