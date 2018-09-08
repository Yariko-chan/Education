package com.example.diana.easyinvest.utils;

public class ArrayUtils {

    private ArrayUtils() {
    }

    public static boolean containsPositives(double[] flows) {
        for (double d : flows) {
            if (d > 0) {
                return true;
            }
        }
        return false;
    }
}
