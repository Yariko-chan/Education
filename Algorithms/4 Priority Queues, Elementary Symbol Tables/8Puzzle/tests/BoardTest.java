import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 20.02.2017.
 */
public class BoardTest {
    Board b;
    Board b_goal;
    int[][] sample = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
    };

    int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    int[][] sample2n = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
    };
    int[][] sample3n = {
            {1, 0, 2},
            {3, 4, 5},
            {6, 7, 8}
    };
    int[][] sample4n = {
            {4, 1, 2},
            {3, 0, 5},
            {6, 7, 8}
    };

    @Before
    public void setUp() throws Exception {
        b = new Board(sample);
        b_goal = new Board(goal);
    }

    @Test
    public void dimension() throws Exception {
        assertEquals(3, b.dimension());
    }

    @Test
    public void hamming() throws Exception {
        assertEquals(8, b.hamming());
        assertEquals(0, b_goal.hamming());
    }

    @Test
    public void manhattan() throws Exception {
        assertEquals(12, b.manhattan());
        assertEquals(0, b_goal.manhattan());
    }

    @Test
    public void isGoal() throws Exception {
        assertFalse(b.isGoal());
        assertTrue(b_goal.isGoal());
    }

    @Test
    public void equals() throws Exception {
        Board b_copy = new Board(sample);
        assertTrue(b.equals(b_copy));
        assertFalse(b.equals(b_goal));
    }

    @Test
    public void testToString() throws Exception {
        StdOut.print(b.toString());
    }

    @Test
    public void twin() throws Exception {
        System.out.print(b.twin().toString());
    }

    @Test
    public void neighbors() throws Exception {
        b = new Board(sample2n);
        StdOut.print(b.toString());
        Iterable<Board> neighs = b.neighbors();
        for (Board board:neighs) {
            StdOut.print(board.toString());
        }
        StdOut.print("---------------------");

        b = new Board(sample3n);
        StdOut.print(b.toString());
        neighs = b.neighbors();
        for (Board board:neighs) {
            StdOut.print(board.toString());
        }
        StdOut.print("---------------------");

        b = new Board(sample4n);
        StdOut.print(b.toString());
        neighs = b.neighbors();
        for (Board board:neighs) {
            StdOut.print(board.toString());
        }
    }

    public static void main(String[] args) {
        System.out.print("AAAA\nbbbb");
    }

}