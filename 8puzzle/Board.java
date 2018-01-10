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
        return tiles.length / 2;
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
        int n = tiles.length;
        for (int i = 0; i < n; i++) {
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
        
    }
    
    public Board twin() {
        
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