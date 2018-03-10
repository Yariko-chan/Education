import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 16.02.2017.
 */
public class HeapSortTest {

    @Test
    public void sort() throws Exception {
        Integer[] ar1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        HeapSort.sort(ar1);
        assertTrue(isSorted(ar1));

        Integer[] ar2 = {0, 1, 3, 2, 4, 5, 6, 7, 8, 9};
        HeapSort.sort(ar2);
        assertTrue(isSorted(ar2));

        Integer[] ar3 = {0, 1, 3, 2, 4, 5, 6, 7, 8, 0};
        HeapSort.sort(ar3);
        assertTrue(isSorted(ar3));

        Integer[] ar4 = {0, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        HeapSort.sort(ar4);
        assertTrue(isSorted(ar3));

        Integer[] ar5 = {0, 9, 0, 4, 6, 5, 2, 3, 1, 7, 8};
        HeapSort.sort(ar5);
        assertTrue(isSorted(ar3));
    }

    public boolean isSorted(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (Helper.less(a[i + 1], a[i])) return false;
        }
        return true;
    }
}