package shuffle;

import helper.Helper;
import sort.Shell;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Diana on 31.01.2017.
 */
public class Sort {
    public static void shuffle(Comparable[] a) {
        Double[] random = new Double[a.length];
        for (int i = 0; i < random.length; i++) {
            random[i] = Math.random();
        }

        Double[] sortedCopy = Arrays.copyOf(random, random.length);
        Shell.sort(sortedCopy);

        Comparable[] aCopy = new Comparable[a.length];
        for (int i = 0; i < a.length; i++) {
            int index = 0;
            for (int j = 0; j < random.length; j++) {
                if (sortedCopy[i] == random[j]) {
                    index = j;
                    break;
                }
            }
            aCopy[i] = a[index];
            //Helper.exch(a, i, index);
        }
        //a = Arrays.copyOf(aCopy, a.length);
        for (int i = 0; i < a.length; i++) {
            a[i] = aCopy[i];
        }
        System.out.print("");
    }

    public static boolean isPermutation(Comparable[] a, Comparable[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            int index = -1;
            for (int j = i; j < b.length; j++) {
                if (a[i] == b[j]) index = j;
            }
            if (index >= 0) Helper.exch(b, i, index);
            else return false;
        }
        return true;
    }
}
