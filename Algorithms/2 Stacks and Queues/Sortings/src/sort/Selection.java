package sort;

import helper.Helper;

/**
 * Created by Diana on 30.01.2017.
 */
public class Selection {

    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i; j < a.length; j++) {
                if (Helper.less(a[j], a[min])) min = j;
            }
            Helper.exch(a, i, min);
        }
    }
}
