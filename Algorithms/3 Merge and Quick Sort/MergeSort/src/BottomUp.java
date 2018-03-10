/**
 * Created by Diana on 07.02.2017.
 */
public class BottomUp extends MergeSort {
    private static Comparable[] aux;

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz) {
            for (int lo = 0; lo < N - sz; lo = sz + sz) {
                merge(a, aux, lo, lo + sz - 1, Math.min(N - 1, lo + sz + sz -1));
            }
        }
    }
}
