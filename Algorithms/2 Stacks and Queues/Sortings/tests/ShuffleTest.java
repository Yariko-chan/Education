import helper.Helper;
import org.junit.Test;
import shuffle.Knuth;
import shuffle.Sort;

/**
 * Created by Diana on 31.01.2017.
 */
public class ShuffleTest {
    @Test
    public void shuffle() throws Exception {
        Integer[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        printArray(a);
        Knuth.shuffle(a);
        printArray(a);
        Sort.shuffle(a);
        printArray(a);
    }

    private void printArray(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

}