import java.util.Arrays;

/**
 * Created by Diana on 11.02.2017.
 */
public class QSort {
    public static void sort(Comparable[] a) {
        //shuffle
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (Helper.less(a[++i], a[lo])) {
                if (i >= hi) break;
            }
            while (Helper.less(a[lo], a[--j])) {
                if (j <= lo) break;
            }
            if (i >= j) break;
            Helper.exch(a, i, j);
        }
        Helper.exch(a, lo, j);
        return j;
    }
}
