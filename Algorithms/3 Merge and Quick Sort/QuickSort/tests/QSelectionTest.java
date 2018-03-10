import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 11.02.2017.
 */
public class QSelectionTest {
    @Test
    public void select() throws Exception {
        Integer[] a1 = {3, 5, 6, 1, 7, 2};
        Integer k = (Integer) QSelection.select(a1, 2);
        assertEquals((Integer) 3, k);

        k = (Integer) QSelection.select(a1, 1);
        assertEquals((Integer) 2, k);

        k = (Integer) QSelection.select(a1, 5);
        assertEquals((Integer) 7, k);

        k = (Integer) QSelection.select(a1, 4);
        assertEquals((Integer) 6, k);

        k = (Integer) QSelection.select(a1, 3);
        assertEquals((Integer) 5, k);

        k = (Integer) QSelection.select(a1, 0);
        assertEquals((Integer) 1, k);
    }

}