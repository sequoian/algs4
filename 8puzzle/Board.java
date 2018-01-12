import java.util.Arrays;
import java.util.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    
    private int[] tiles;
    private int dim;
    
    public Board(int[][] blocks) {
        dim = blocks.length;
        tiles = new int[dim*dim];
        int x = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                tiles[x] = blocks[i][j];
                x++;
            }
        }
    }
    
    public int dimension() {
        return dim;
    }
    
    public int hamming() {
        int priority = 0;
        int n = tiles.length;
        for (int i = 0; i < n; i++) {
            int tile = tiles[i];
            if (tile != 0 && tile != i+1) {
                priority++;
            }
        }
        return priority;
    }
    
    public int manhattan() {
        int priority = 0;
        for (int i = 0; i < tiles.length; i++) {
            int tile = tiles[i];
            if (tile != 0 && tile != i+1) {
                int target = tile - 1;
                int offY = target/dim - i/dim;
                int offX = target%dim - i%dim;
                //System.out.format("%d: %d + %d = %d\n", tile, Math.abs(offY), Math.abs(offX), Math.abs(offY) + Math.abs(offX));
                priority += Math.abs(offY) + Math.abs(offX);
            }
        }
        return priority;
    }
    
    public boolean isGoal() {
        boolean goal = true;
        for (int i = 0; i < tiles.length-1; i++) {
            if (tiles[i] != i+1) {
                goal = false;
                break;
            }
        }
        return goal;
    }
    
    public Board twin() {
        // create clone
        int[][] clone = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                clone[i][j] = tiles[i*dim + j];
            }
        }
        // alter clone
        int iSwap = -1;
        int jSwap = -1;
        outerloop:  // allows for breaking out of nested loop
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (clone[i][j] != 0) {
                    if (iSwap > -1) {
                        int swap = clone[i][j];
                        clone[i][j] = clone[iSwap][jSwap];
                        clone[iSwap][jSwap] = swap;
                        break outerloop;
                    }
                    else {
                        iSwap = i;
                        jSwap = j;
                    }
                }
            }
        }
        return new Board(clone); 
    }
    
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return (Arrays.equals(this.tiles, that.tiles)) && (this.dim == that.dim);
    }
    
    public Iterable<Board> neighbors() {
        // find empty tile
        int empty = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                empty = i;
                break;
            }
        }
        
        // calculate neighbor indices
        Stack<Integer> indices = new Stack<Integer>();
        // top
        int idx = empty - dim;
        if (idx > -1) indices.push(idx);
        // bot
        idx = empty + dim;
        if (idx < dim*dim) indices.push(idx);
        // left
        idx = empty - 1;
        if (idx > -1 && idx%dim != dim-1) indices.push(idx);
        // right
        idx = empty + 1;
        if (idx < dim*dim && idx%dim != 0) indices.push(idx);
        
        // create neighbor boards
        Stack<Board> neighbors = new Stack<Board>();
        for (int i : indices) {
            // clone tiles
            int n = tiles.length;
            int[] clone = new int[n];
            for (int j = 0; j < n; j++) {
                clone[j] = tiles[j];
            }
            // swap tiles
            clone[empty] = clone[i];
            clone[i] = 0;
            // switch to 2d array (optimally do this ahead and calculate based on 2d array)
            // TODO: private int[][] 1dTo2d()
            int[][] clone2 = new int[dim][dim];
            for (int k = 0; k < dim; k++) {
                for (int l = 0; l < dim; l++) {
                    clone2[k][l] = clone[k*dim + l];
                }
            }
            
            neighbors.push(new Board(clone2));
        }

        return neighbors;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%d ", tiles[i*dim + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board b = new Board(blocks);
        
        StdOut.println("BOARD");
        StdOut.println(b);
        StdOut.println("NEIGHBORS");
        for (Board s : b.neighbors()) {
            StdOut.println(s);
        }
        StdOut.println("TWIN");
        StdOut.println(b.twin());
        StdOut.println("MANHATTAN");
        StdOut.println(b.manhattan());
    }
}