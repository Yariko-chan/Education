package sort;

import helper.Helper;

/**
 * Created by Diana on 31.01.2017.
 */
public class Insertion {
    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j >= 1; j--) {
                if (Helper.less(a[j], a[j - 1])) {
                    Helper.exch(a, j, j - 1);
                }
                else break;
            }
        }
    }
}
