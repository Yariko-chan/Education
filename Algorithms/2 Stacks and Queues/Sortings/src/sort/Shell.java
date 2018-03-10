package sort;

import helper.Helper;

/**
 * Created by Diana on 31.01.2017.
 */
public class Shell {
    public static void sort(Comparable[] a) {
        int h = 1;

        while (h < a.length/3) h = 3*h + 1;

        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h && Helper.less(a[j], a[j - 1]); j -= h) {
                    Helper.exch(a, j, j - 1);
                }
            }
            h = h/3;
        }
    }
}
