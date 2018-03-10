import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by Diana on 26.01.2017.
 */
public class Percolation {
    private boolean[][] grid;

    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;

    private int size = 0;
    private int opened = 0;

    public Percolation(int n) {                // create n-by-n id, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException();
        grid = new boolean[n][n];
        uf1 = new WeightedQuickUnionUF(n*n + 2); // grid + upper + down site
        uf2 = new WeightedQuickUnionUF(n*n + 2); // grid + upper site; 1 is everytime void
        size = n;
    }

    public void open(int row, int col) {       // open site (row, col) if it is not open already
        if (row <= 0 || col <= 0 || row > size || col > size) throw new IndexOutOfBoundsException();
        if (isOpen(row, col)) return;
        grid[row-1][col-1] = true;

        if (1 == row) {
            uf1.union(0, getNum(row, col)); // if in first row, connect with upper site
            uf2.union(0, getNum(row, col));
        }
        else if (isOpen(row-1, col)) {              // else connect with opened site up in both id's
            uf1.union(getNum(row-1, col), getNum(row, col));
            uf2.union(getNum(row-1, col), getNum(row, col));
        }

        if (size == row) {
            uf1.union(1, getNum(row, col)); // if in last row, connect with down site
        }
        else if (isOpen(row+1, col)) {                 // else connect with opened site up in both id's
            uf1.union(getNum(row +1, col), getNum(row, col));
            uf2.union(getNum(row +1, col), getNum(row, col));
        }

        if (1 != col && isOpen(row, col-1)) {                // connect with left site if it open
            uf1.union(getNum(row, col-1), getNum(row, col));
            uf2.union(getNum(row, col-1), getNum(row, col));
        }
        if (size != col && isOpen(row, col+1)) {             // connect with right site if it open
            uf1.union(getNum(row, col+1), getNum(row, col));
            uf2.union(getNum(row, col+1), getNum(row, col));
        }

        opened++;
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        if (row <= 0 || col <= 0 || row > size || col > size) throw new IndexOutOfBoundsException();
        return grid[row-1][col-1];
    }

    public boolean isFull(int row, int col) {  // is site (row, col) full?
        if (row <= 0 || col <= 0 || row > size || col > size) throw new IndexOutOfBoundsException();
        return uf2.connected(0, getNum(row, col));
    }

    private int getNum(int row, int col) { // row 1..n col 1..n
        return (row-1) *size + col + 1;
    }

    public int numberOfOpenSites() {           // number of open sites
        return opened;
    }

    public boolean percolates() {              // does the system percolate?

        return uf1.connected(0, 1);
    }

    public static void main(String[] args) {   // test client (optional)
    }
}
