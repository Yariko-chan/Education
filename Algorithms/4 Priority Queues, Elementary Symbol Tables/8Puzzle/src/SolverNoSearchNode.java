import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Diana on 21.02.2017.
 */
public class SolverNoSearchNode {
    private boolean isSolvable = true;
    private int moves = 0;
    private Board mainPrev = null;
    private Board twinPrev = null;

    // constructor with comparator!
    private MinPQ<PriorityBoard> mainPQ = new MinPQ<>();
    private MinPQ<PriorityBoard> twinPQ = new MinPQ<>();
    private Queue<Board> path = new Queue<>();

    public SolverNoSearchNode(Board initial) {
        if (null == initial) throw new NullPointerException();
        // assert solvable

        Board mainBoard = initial;
        Board twinBoard = initial.twin();
        mainPQ.insert(new PriorityBoard(mainBoard, mainBoard.manhattan()));
        twinPQ.insert(new PriorityBoard(twinBoard, twinBoard.manhattan()));
        do {
            moves++;
            mainBoard = mainPQ.delMin().b;
            Iterable<Board> neighbors = mainBoard.neighbors();
            for (Board b : neighbors) {
                if (mainPrev == null || !b.equals(mainPrev)) {
                    mainPQ.insert(new PriorityBoard(b, priority(b)));
                }
            }
            path.enqueue(mainBoard);
            mainPrev = mainBoard;

            twinBoard = twinPQ.delMin().b;
            neighbors = twinBoard.neighbors();
            for (Board b : neighbors) {
                if (twinPrev == null || !b.equals(twinPrev)) {
                    twinPQ.insert(new PriorityBoard(b, priority(b)));
                }
            }
            twinPrev = twinBoard;
        } while (!mainBoard.isGoal() && !twinBoard.isGoal());
        if (twinBoard.isGoal() && !mainBoard.isGoal()) {
            moves = -1;
            path = null;
            isSolvable = false;
        }
    }

    private int priority(Board b) {
        return b.manhattan() + moves;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return path.size() - 1;
    }

    public Iterable<Board> solution() {
        return path;
    }

    public static void main(String[] args) {
        // create initial board
        // from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        SolverNoSearchNode solver = new SolverNoSearchNode(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    /*private class HammingComparator implements Comparator<Board> {
        @Override
        public int compare(Board b1, Board b2) {
            return b1.hamming() - b2.hamming();
        }
    }

    private class ManhattanComparator implements Comparator<Board> {
        @Override
        public int compare(Board b1, Board b2) {
            return (b1.manhattan() + moves) - (b2.manhattan() + moves);
        }
    }*/
    private class PriorityBoard implements Comparable {
        private final Board b;
        private final Integer p;

        public PriorityBoard(Board b, int priority) {
            this.b = b;
            this.p = priority;
        }

        @Override
        public int compareTo(Object o) {
            PriorityBoard that = (PriorityBoard) o;
            return p.compareTo(that.p);
        }
    }
}
