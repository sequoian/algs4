import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;

public class Solver {
    
    private MinPQ<Node> pq = new MinPQ<Node>(nodeOrder());
    private MinPQ<Node> twin = new MinPQ<Node>(nodeOrder());
    private Node solution = null;
    private boolean solvable = false;
    
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        // create priority queue and twin
        pq.insert(new Node(initial, null, 0));
        twin.insert(new Node(initial.twin(), null, 0));
        
        // look for solution            
        while(true) {
            
            // test main board
            Node min = pq.delMin();
            Board b = min.board();
            solution = min;
            
            if (b.isGoal()) {
                solvable = true;
                break;
            }
            
            for (Board n : b.neighbors()) {
                if (min.predecessor == null || !n.equals(min.predecessor().board())) {
                    pq.insert(new Node(n, min, min.moves() + 1));
                }
            }
            
            // test twin
            min = twin.delMin();
            b = min.board();
            if (b.isGoal()) {
                solvable = false;
                solution = null;
                break;
            }
            for (Board n : b.neighbors()) {
                if (min.predecessor == null || !n.equals(min.predecessor().board())) {
                    twin.insert(new Node(n, min, min.moves() + 1));
                }
            }
        }
    }
    
    private class Node {
        
        private Board board;
        private Node predecessor;
        private int manhattan;
        private int moves;
        
        public Node(Board b, Node p, int m) {
            board = b;
            predecessor = p;
            moves = m;
            manhattan = b.manhattan();
        }
        
        public int priority() {
            return manhattan + moves;
        }
        
        public int manhattan() {
            return manhattan;
        }
        
        public int moves() {
            return moves;
        }
        
        public Board board() {
            return board;
        }
        
        public Node predecessor() {
            return predecessor;
        }
    }
    
    private Comparator<Node> nodeOrder() {
        return new NodeOrder();
    }
    
    private class NodeOrder implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            int p1 = n1.priority();
            int p2 = n2.priority();
            if (p1 < p2) return -1;
            else if (p1 > p2) return 1;
            else {
                int m1 = n1.manhattan();
                int m2 = n2.manhattan();
                if (m1 < m2) return -1;
                else if (m1 > m2) return 1;
                else return 0;
            }
        }
    }
    
    public boolean isSolvable() {
        return solvable;
    }
    
    public int moves() {
        if (solvable)
            return solution.moves();
        else
            return -1;
    }
    
    public Iterable<Board> solution() {
        if (solvable) {
            Stack s = new Stack<Board>();
            Node n = solution;
            while(n != null) {
                s.push(n.board());
                n = n.predecessor();
            }
            return s;
        }
        else {
            return null;
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