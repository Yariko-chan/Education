import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 11.02.2017.
 */
public class QuickSortTest {
    @Test
    public void sort() throws Exception {
        Integer[] ar1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        QSort.sort(ar1);
        assertTrue(Helper.isSorted(ar1));

        Integer[] ar2 = {1, 3, 2, 4, 5, 6, 7, 8, 9};
        QSort.sort(ar2);
        assertTrue(Helper.isSorted(ar2));

        Integer[] ar3 = {1, 3, 2, 4, 5, 6, 7, 8, 0};
        QSort.sort(ar3);
        assertTrue(Helper.isSorted(ar3));

        Integer[] ar4 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        QSort.sort(ar4);
        assertTrue(Helper.isSorted(ar3));

        Integer[] ar5 = {9, 0, 4, 6, 5, 2, 3, 1, 7, 8};
        QSort.sort(ar5);
        assertTrue(Helper.isSorted(ar3));
    }
}