import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Diana on 23.02.2017.
 */
public class Solver {
    private SearchNode solution = null;

    public Solver(Board initial) {

        MinPQ<SearchNode> mainPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        if (null == initial) throw new NullPointerException("Argument is null");

        SearchNode main = new SearchNode(initial, null, 0);
        SearchNode twin = new SearchNode(initial.twin(), null, 0);

        mainPQ.insert(main);
        twinPQ.insert(twin);

        // find goal
        do {

            // main
            main = mainPQ.delMin();
            Iterable<Board> neighbors = main.b.neighbors();
            for (Board b : neighbors) {
                // Critical Optimization
                if (null == main.prev || !main.prev.b.equals(b)) {
                    SearchNode node = new SearchNode(b, main, main.moves + 1);
                    mainPQ.insert(node);
                }
            }

            // twin
            twin = twinPQ.delMin();
            neighbors = twin.b.neighbors();
            for (Board b : neighbors) {
                if (null == twin.prev || !twin.prev.b.equals(b)) {
                    SearchNode node = new SearchNode(b, twin, twin.moves + 1);
                    twinPQ.insert(node);
                }
            }
        } while (!main.b.isGoal() && !twin.b.isGoal());

        // detect is solvable
        if (main.b.isGoal()) {
            solution = main;
        }
    }

    public boolean isSolvable() {
        return (null != solution);
    }

    public int moves() {
        if (null == solution) return -1;
        return solution.moves;
    }

    public Iterable<Board> solution() {
        if (null == solution) return null;
        // copy to avoid changing instance variable
        SearchNode node = solution;

        // reconstruct solution
        Stack<Board> solutionStack = new Stack<>();
        do {
            solutionStack.push(node.b);
            node = node.prev;
        } while (node != null);

        return solutionStack;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board b;
        private SearchNode prev;
        private int moves;

        public SearchNode(Board b, SearchNode prev, int moves) {
            this.b = b;
            this.prev = prev;
            this.moves = moves;
        }

        private int priority(SearchNode node) {
            return node.moves + node.b.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(priority(this), priority(that));
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
