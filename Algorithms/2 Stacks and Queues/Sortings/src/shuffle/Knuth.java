package shuffle;

import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;
import helper.Helper;
import java.util.Random;

/**
 * Created by Diana on 31.01.2017.
 */
public class Knuth {

    public static void shuffle(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            Random r = new Random();
            int n = r.nextInt(i);
            Helper.exch(a, i, n);
        }
    }
}
