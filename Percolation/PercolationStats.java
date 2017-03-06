import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {

    private int count;
    private Percolation gv;
    private double[] percolationThresholds;

    // Performs T independent computational experiments on an N-by-N grid.
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Wrong Input, must be larger than 0");
        }
        count = T;
        percolationThresholds = new double[count];
        for (int expNo = 0; expNo < count; expNo++) {
            gv = new Percolation(N);
            int openedSites = 0;
            while (!gv.percolates()) {
                int i = StdRandom.uniform(1, N + 1);
                int j = StdRandom.uniform(1, N + 1);
                if (!gv.isOpen(i, j)) {
                    gv.open(i, j);
                    openedSites++;
                }
            }
            double fraction = (double) openedSites / (N * N);
            percolationThresholds[expNo] = fraction;
        }
    }

    // Sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    //Sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }
 
    //Returns lower bound of the 95% confidence interval.
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(count));
    }
 
    //Returns upper bound of the 95% confidence interval.
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(count));
    }

    //Used for Testing
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
