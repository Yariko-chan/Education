import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Diana on 30.01.2017.
 */
public class SortTest {
    @Test
    public void sort() throws Exception {
        Integer[] ar1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer[] arN = new Integer[9];
        BottomUp.sort(ar1, arN, 0, ar1.length-1);
        assertTrue(Helper.isSorted(ar1));

        Integer[] ar2 = {1, 3, 2, 4, 5, 6, 7, 8, 9};
        arN = new Integer[9];
        BottomUp.sort(ar2, arN, 0, ar2.length-1);
        assertTrue(Helper.isSorted(ar2));

        Integer[] ar3 = {1, 3, 2, 4, 5, 6, 7, 8, 0};
        arN = new Integer[9];
        BottomUp.sort(ar3, arN, 0, ar3.length-1);
        assertTrue(Helper.isSorted(ar3));

        Integer[] ar4 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        arN = new Integer[10];
        BottomUp.sort(ar4, arN, 0, ar4.length-1);
        assertTrue(Helper.isSorted(ar3));

        Integer[] ar5 = {9, 0, 4, 6, 5, 2, 3, 1, 7, 8};
        arN = new Integer[10];
        BottomUp.sort(ar5, arN, 0, ar5.length-1);
        assertTrue(Helper.isSorted(ar3));
    }

}