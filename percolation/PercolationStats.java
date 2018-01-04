import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private int numTrials;
    
    public PercolationStats(int n, int trials) {
        if (trials < 1 || n < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        numTrials = trials;
        results = new double[trials];
        Percolation p;
        
        int[] indices = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            indices[i] = i;
        }
        
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            boolean percolated = false;
            
            StdRandom.shuffle(indices);
            int idx = 0;
            while (!percolated) {
                int site = indices[idx];
                idx = idx + 1;
                int row = site / n + 1;
                int col = site % n + 1;
                
                p.open(row, col);
                percolated = p.percolates();
            }
            double open = p.numberOfOpenSites();
            double avg = open / (n * n);
            results[i] = avg;
        }
    }
    
    public double mean() {
        return StdStats.mean(results);
    }
    
    public double stddev() {
        return StdStats.stddev(results);
    }
    
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(numTrials);
    }
    
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(numTrials);
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        System.out.format("mean                    = %f \n", stats.mean());
        System.out.format("stddev                  = %f \n", stats.stddev());
        double lo = stats.confidenceLo();
        double hi = stats.confidenceHi();
        System.out.format("95%% confidence interval = [%f, %f] \n", lo, hi);
    }
}