public class Board {
    
    private int[] tiles;
    private int dim;
    
    public Board(int[][] blocks) {
        int dim = blocks.length;
        tiles = new int[dim*dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                tiles[i] = blocks[i][j];
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
                int offset = Math.abs((i+1) - tile);
                int moves = (offset / dim) + (offset % dim);
                priority += moves;
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
        
    }
    
    public Iterable<Board> neighbors() {
        
    }
    
    public String toString() {
        
    }
    
    public static void main(String[] args) {
        
    }
}