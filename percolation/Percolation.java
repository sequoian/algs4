import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[][] grid;
    private int size;
    
    private boolean validSite(int row, int col) {
        boolean validRow = row > 0 && row <= size;
        boolean validCol = col > 0 && col <= size;
        return validRow && validCol;
    }
    
    private int xyTo1D(int row, int col) {
        if (row == 0) return size * size;  // top
        if (row == size + 1) return size * size + 1;  // bottom
        if (col == 0) return -1;  // left, invalid
        if (col == size + 1) return -1;  // right, invalid
        return ((row - 1) * size) + (col - 1);
    }
    
    public Percolation(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n][n];
        size = n;
    }
    
    public void open(int row, int col) {
        if (!validSite(row, col)) {
            throw new java.lang.IllegalArgumentException();
        }
        // set to open
        grid[row - 1][col - 1] = true;
        
        // connect to neighbors
        int target = xyTo1D(row, col);
        int n;
        // top 
        if (row == 1) {
            n = size * size;
            uf.union(target, n);
        }
        else if (grid[row - 2][col - 1]) {
            n = xyTo1D(row - 1, col);
            uf.union(target, n);      
        }
        
        // bottom
        if (row == size) {
            n = size * size + 1;
            uf.union(target, n);
        }
        else if (grid[row][col - 1]) {
            n = xyTo1D(row + 1, col);
            uf.union(target, n);
        }
        
        // left
        if (col > 1 && grid[row - 1][col - 2]) {
            n = xyTo1D(row, col - 1);
            uf.union(target, n);
        }
        
        // right
        if (col < size && grid[row - 1][col]) {
            n = xyTo1D(row, col + 1);
            uf.union(target, n);
        }
    }
    
    public boolean isOpen(int row, int col) {
        if (!validSite(row, col)) {
            throw new java.lang.IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }
    
    public boolean isFull(int row, int col) {
        if (!validSite(row, col)) {
            throw new java.lang.IllegalArgumentException();
        }
        int topIdx = size * size;
        int siteIdx = xyTo1D(row, col);
        return uf.connected(siteIdx, topIdx);
    }
    
    public int numberOfOpenSites() {
        int count = 0;
        for (boolean[] row : grid) {
            for (boolean site: row) {
                if (site) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public boolean percolates() {
        int topIdx = size * size;
        int botIdx = size * size + 1;
        return uf.connected(topIdx, botIdx);
    }
    
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        
        System.out.println("1, 1");
        p.open(1, 1);
        System.out.println(p.isOpen(1, 1));
        System.out.println(p.isFull(1, 1));
        System.out.println(p.percolates());
 
        System.out.println("3, 1");
        p.open(3, 1);
        System.out.println(p.isOpen(3, 1));
        System.out.println(p.isFull(3, 1));
        System.out.println(p.percolates());
        
        System.out.println("2, 1");
        p.open(2, 1);
        System.out.println(p.isOpen(2, 1));
        System.out.println(p.isFull(2, 1));
        System.out.println(p.percolates());
        
        System.out.println("3, 3");
        p.open(3, 3);
        System.out.println(p.isOpen(3, 3));
        System.out.println(p.isFull(3, 3));
        System.out.println(p.percolates());
        
        System.out.println("---");
        System.out.println(p.numberOfOpenSites());
        
    }
    
}