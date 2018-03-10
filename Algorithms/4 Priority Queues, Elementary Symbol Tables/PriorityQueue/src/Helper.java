/**
 * Created by Diana on 30.01.2017.
 */
public class Helper {

    public static void exch(Comparable[] a, int i, int j) {
        Comparable bufer = a[i];
        a[i] = a[j];
        a[j] = bufer;
    }

    public static boolean less(Comparable a, Comparable b) {
        boolean n = (a.compareTo(b) < 0);
        return n;
    }
}
