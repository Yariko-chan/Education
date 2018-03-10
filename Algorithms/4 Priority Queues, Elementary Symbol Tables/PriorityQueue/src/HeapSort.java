/**
 * Created by Diana on 16.02.2017.
 */
public class HeapSort {

    public static void sort(Comparable[] pq) {
        int size = pq.length - 1;
        //reorder to binary heap
        for (int i =  size/2; i >= 1; i--) {
            sink(pq, i, size);
        }
        int k = size;
        while (k > 1) {
            Helper.exch(pq, 1, k--);
            sink(pq, 1, k);
        }
    }

    private static void sink(Comparable[] pq, int i, int size) {
        while (i*2 <= size) {
            int j = 2*i;
            if (j < size && Helper.less(pq[j], pq[j + 1]))
                j++;
            if (Helper.less(pq[j], pq[i]))
                break;
            Helper.exch(pq, i, j);
            i = j;
        }
    }
}
