import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Diana on 30.01.2017.
 */
public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> qu = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            qu.enqueue(s);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(qu.dequeue());
        }
    }
}
