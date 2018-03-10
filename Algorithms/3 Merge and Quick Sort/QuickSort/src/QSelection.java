import java.util.Random;

/**
 * Created by Diana on 11.02.2017.
 */
public class QSelection {
    //the problem is to find k-th large element
    // f. e. given array {1, 6, 3, 4, 2, 5}
    // the 3rd large element is 3
    // it's quite simple to find k-th element in sorted array
    //just call a[k]
    //but for unsorted array there is much more quicker algorithm
    //that don't need to sort all the array to find k-th element
    public static Comparable select(Comparable[] a, int k) {
        //shuffle
        int lo = 0;
        int hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (k > j) lo = j + 1;
            else if (k < j) hi = j - 1;
            else return a[k];
        }
        return a[k];
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
