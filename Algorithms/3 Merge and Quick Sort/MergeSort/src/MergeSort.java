/**
 * Created by Diana on 06.02.2017.
 */
public class MergeSort {
    public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo)/2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        for (int i = lo; i <= hi; i++) {
            aux[i] = a[i];
        }
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)                 a[k] = aux[j++];
            else if (j > hi)                  a[k] = aux[i++];
            else if (Helper.less(a[i], a[j])) a[k] = aux[i++];
            else                              a[k] = aux[j++];
        }
    }
}
