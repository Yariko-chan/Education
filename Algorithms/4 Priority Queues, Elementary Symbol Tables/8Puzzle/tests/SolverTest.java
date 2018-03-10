import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import org.junit.Test;

import java.util.Timer;

import static org.junit.Assert.*;

/**
 * Created by Diana on 21.02.2017.
 */
public class SolverTest {
    Board b;
    Board b_goal;
    int[][] sample = {
            {1, 2, 3},
            {0, 7, 6},
            {5, 4, 8}
    };


    int[][] solvableInOneMove = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 0, 8}
    };

    int[][] unsolvable = {
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
    };

    int[][] goal = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    @Test (expected = NullPointerException.class)
    public void ConstructorTest() throws Exception {
        Solver s = new Solver(null);
    }

    @Test
    public void isSolvable() throws Exception {
        Solver s = new Solver(new Board(solvableInOneMove));
        assertTrue(s.isSolvable());
        s = new Solver(new Board(unsolvable));
        assertFalse(s.isSolvable());
        s = new Solver(new Board(goal));
        assertTrue(s.isSolvable());
        s = new Solver(new Board(sample));
        assertTrue(s.isSolvable());
    }

    @Test
    public void moves() throws Exception {
        Solver s = new Solver(new Board(solvableInOneMove));
        assertEquals(1, s.moves());
        long start = System.currentTimeMillis();
        s = new Solver(new Board(unsolvable));
//        long end = System.currentTimeMillis();
//        System.out.println((end - start) + " millisec");
//        System.out.println((end - start) + " millisec");
//        System.out.println(s.moves());
//        System.out.print(s.solution());
        assertEquals(-1, s.moves());
        s = new Solver(new Board(goal));
        assertEquals(0, s.moves());
        s = new Solver(new Board(sample));
        System.out.print(s.moves());
    }

    @Test
    public void solution() throws Exception {
        Solver s = new Solver(new Board(solvableInOneMove));
        Stack<Board> q = (Stack<Board>)s.solution();
        assertEquals(2, q.size());

//        s = new SolverNoSearchNode(new Board(unsolvable));
//        q = (Queue<Board>)s.solution();
//        assertNull(q);

        s = new Solver(new Board(goal));
        q = (Stack<Board>)s.solution();
        assertEquals(1, q.size());

        s = new Solver(new Board(sample));
        q = (Stack<Board>)s.solution();
        System.out.print(q);

    }

    @Test
    public void unreachableForSomeReason() throws Exception {
        int[][] board = {
                {6, 2, 3},
                {4, 5, 1},
                {8, 7, 0}
        };
        long start = System.currentTimeMillis();
        Solver s = new Solver(new Board(board));
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " millisec");
        System.out.println(s.moves());
        System.out.print(s.solution());
    }
}