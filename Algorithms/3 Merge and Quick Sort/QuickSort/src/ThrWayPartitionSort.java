/**
 * Created by Diana on 11.02.2017.
 */
public class ThrWayPartitionSort {

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo;
        int gt = hi;
        int i = lo;
        Comparable v = a[lo];
        while (i <= gt) {
            int c = a[i].compareTo(v);
            if      (c < 0) Helper.exch(a, lt++, i++);
            else if (c > 0) Helper.exch(a, gt--, i);
            else            i++;
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }
}
