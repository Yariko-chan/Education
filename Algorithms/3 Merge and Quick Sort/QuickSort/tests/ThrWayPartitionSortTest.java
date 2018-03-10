import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 11.02.2017.
 */
public class ThrWayPartitionSortTest {
    @Test
    public void sort() throws Exception {
        Integer[] ar1 = {1, 1, 3, 7, 5, 5, 7, 8, 3};
        ThrWayPartitionSort.sort(ar1);
        assertTrue(Helper.isSorted(ar1));

        Integer[] ar2 = {1, 3, 1, 4, 1, 6, 1, 8, 1};
        ThrWayPartitionSort.sort(ar2);
        assertTrue(Helper.isSorted(ar2));

        Integer[] ar3 = {1, 2, 2, 4, 2, 6, 8, 8, 8};
        ThrWayPartitionSort.sort(ar3);
        assertTrue(Helper.isSorted(ar3));

        Integer[] ar4 = {1, 1, 1, 1, 1, 1, 1, 2, 1, 2};
        ThrWayPartitionSort.sort(ar4);
        assertTrue(Helper.isSorted(ar3));

        Integer[] ar5 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        ThrWayPartitionSort.sort(ar5);
        assertTrue(Helper.isSorted(ar3));
    }

}