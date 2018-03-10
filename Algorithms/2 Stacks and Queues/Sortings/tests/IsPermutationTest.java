import org.junit.Test;
import shuffle.Sort;

import static org.junit.Assert.*;

/**
 * Created by Diana on 31.01.2017.
 */
public class IsPermutationTest {

    @Test
    public void isPermuationTest() {
        Integer[] ar1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer[] ar2 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        assertTrue(Sort.isPermutation(ar1, ar2));

        Integer[] ar3 = {9, 8, 7, 6, 5, 5, 3, 2, 1, 0};
        assertFalse(Sort.isPermutation(ar1, ar3));
        assertFalse(Sort.isPermutation(ar2, ar3));

        Double[] ar4 = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        assertFalse(Sort.isPermutation(ar1, ar3));
        assertFalse(Sort.isPermutation(ar2, ar3));
    }
}
