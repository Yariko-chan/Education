import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by Diana on 26.01.2017.
 */
public class PercolationStats {
    private double[] results;
    private double mean;
    private double stddev;
    private double lowConf, highConf;

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            double x = monteCarlo(n);
            results[i] = x;
        }
        mean = mean();
        stddev = stddev();
        lowConf = confidenceLo();
        highConf = confidenceHi();
    }

    private double monteCarlo(int n) {
        Percolation p = new Percolation(n);

        while (!p.percolates()) {
            int i = (int) (StdRandom.uniform()*n+1);
            int j = (int) (StdRandom.uniform()*n+1);
            p.open(i, j);
        }
        return (double) p.numberOfOpenSites()/(n*n);
    }

    public double mean() {                          // sample mean of percolation threshold
        double sum = 0;
        for (double i:results) sum += i;
        return sum /results.length;
    }

    public double stddev() {                        // sample standard deviation of percolation threshold
        double sum = 0;
        for (double i:results) sum += Math.pow(i - mean, 2);
        double qu = sum/(results.length-1);
        return Math.sqrt(qu);
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        return mean - 1.96*stddev/Math.sqrt(results.length);
    }

    public double confidenceHi() {                 // high endpoint of 95% confidence interval
        return mean + 1.96*stddev/Math.sqrt(results.length);
    }

    public static void main(String[] args) {        // test client (described below)
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(size, trials);
        // PercolationStats p = new PercolationStats(2, 100000);
        StdOut.println("mean                    = " + p.mean);
        StdOut.println("stddev                  = " + p.stddev);
        StdOut.println("95% confidence interval = " + p.lowConf + ", " + p.highConf);
    }
}
