import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by Diana on 20.02.2017.
 */
public class Board {
    private int n = 0;
    private char[] b;
    private int voidBlock = -1;

    public Board(int[][] blocks) {
        if (blocks.length != blocks[0].length) {
            throw new IllegalArgumentException("Grid must be square. ");
        }
        n = blocks.length;
        b = new char[n*n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[n * i + j] = (char) blocks[i][j];
                if (0 == blocks[i][j]) {
                    voidBlock = n * i + j;
                }
            }
        }
        if (-1 == voidBlock) throw new IllegalArgumentException("Voia block not found.");
    }

    private Board(char[] b) {
        double size = Math.sqrt(b.length);
        if (0 != size % 1) {
            throw new IllegalArgumentException("Array can't be converted to n*n grid.");
        }
        this.n = (int) size;
        this.b = b;
        int i = 0;
        while (i < b.length && b[i] != 0) {
            i++;
        }
        if (b.length != i) {
            voidBlock = i;
        } else {
            throw new IllegalArgumentException("Void block not found. ");
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int res = 0;
        for (int i = 0; i < b.length; i++) {
            int value = (int) b[i];
            if (0 == value) continue;
            if (i != value - 1) res++;
        }
        return res;
    }

    public int manhattan() {
        int res = 0;

        for (int i = 0; i < b.length; i++) {
            if (b[i] == 0) continue;

            // find index of this number in goal
            int j = ((int) b[i]) - 1;

            // get row-col in b
            int bRow = i/n;
            int bCol = i % n;

            // get row-col in goal
            int goalRow = j/n;
            int goalCol = j % n;

            // distance = |bRow - goalRow| + |bCol - row_col|
            int dist = Math.abs(bRow - goalRow) + Math.abs(bCol - goalCol);
            res += dist;
        }

        return res;
    }

    public boolean isGoal() {
        if (0 != b[b.length - 1]) {
            return false;
        }
        for (int i = 0; i < b.length - 1; i++) {
            int value = (int) b[i];
            if (i != value - 1) return false;
        }

        return true;
    }

    public Board twin() {
        int i;
        int j;
        do {
            do {
                i = (int) Math.floor(StdRandom.uniform()*b.length);
            } while (0 == b[i]);
            do {
                j = (int) Math.floor(StdRandom.uniform()*b.length);
            } while (0 == b[j]);
        } while (i == j);
        return new Board(exch(b, i, j));
    }

    private char[] exch(char[] a, int i, int j) {
        char[] copy = Arrays.copyOf(a, a.length);
        char bufer = copy[i];
        copy[i] = copy[j];
        copy[j] = bufer;
        return copy;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        return (this.n == that.n) && Arrays.equals(this.b, that.b);
    }

    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();

        if (voidBlock - n >= 0) { /* move blank up */
            q.enqueue(new Board(exch(b, voidBlock, voidBlock - n)));
        }
        if (voidBlock + n < b.length) { /* move blank down */
            q.enqueue(new Board(exch(b, voidBlock, voidBlock + n)));
        }

        /* if voidBl is last in stroke, the next will %n == 0 */
        if ((voidBlock + 1) % n != 0) { /* move blank to right */
            q.enqueue(new Board(exch(b, voidBlock, voidBlock + 1)));
        }
        /* first block in str is always %n == 0 */
        if ((voidBlock) % n != 0) { /* move blank to left */
            q.enqueue(new Board(exch(b, voidBlock, voidBlock - 1)));
        }
        return q;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(Integer.toString(n) + '\n');
        for (int i = 0; i < b.length; i++) {
            int maxLength = String.valueOf(Math.abs(b.length)).length() + 1;
            String num = String.format("%1$" + maxLength + "d", (int) b[i]);
            res.append(num);
            if (0 == (i + 1) % n) res.append('\n');
        }
        return res.toString();
    }

    public static void main(String[] args) { }
}
