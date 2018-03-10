import helper.Helper;
import org.junit.Test;
import sort.Shell;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Diana on 30.01.2017.
 */
public class SortTest {
    @Test
    public void sort() throws Exception {
        Integer[] ar1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Shell.sort(ar1);
        assertTrue(isSorted(ar1));

        Integer[] ar2 = {1, 3, 2, 4, 5, 6, 7, 8, 9};
        Shell.sort(ar2);
        assertTrue(isSorted(ar2));

        Integer[] ar3 = {1, 3, 2, 4, 5, 6, 7, 8, 0};
        Shell.sort(ar3);
        assertTrue(isSorted(ar3));

        Integer[] ar4 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        Shell.sort(ar3);
        assertTrue(isSorted(ar3));

        Integer[] ar5 = {9, 0, 4, 6, 5, 2, 3, 1, 7, 8};
        Shell.sort(ar3);
        assertTrue(isSorted(ar3));
    }

    public boolean isSorted(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (Helper.less(a[i + 1], a[i])) return false;
        }
        return true;
    }

}