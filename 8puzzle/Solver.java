import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;

public class Solver {
    
    private MinPQ<Node> pq = new MinPQ<Node>(nodeOrder());
    private MinPQ<Node> twin = new MinPQ<Node>(nodeOrder());
    private Queue<Board> solution = new Queue<Board>();
    private int moves = 0;
    private boolean solvable = false;
    
    public Solver(Board initial) {
        // create priority queue and twin
        pq.insert(new Node(initial, null, 0));
        twin.insert(new Node(initial.twin(), null, 0));
        
        // look for solution            
        while(true) {
            // test main board
            Node min = pq.delMin();
            Board b = min.board();
            solution.enqueue(b);
            if (b.isGoal()) {
                solvable = true;
                break;
            }
            
            moves++;
            for (Board n : b.neighbors()) {
                if (!n.equals(min.predecessor())) {
                    pq.insert(new Node(n, b, moves));
                }
            }
            
            // test twin
            min = twin.delMin();
            b = min.board();
            if (b.isGoal()) {
                solvable = false;
                break;
            }
            for (Board n : b.neighbors()) {
                if (!n.equals(min.predecessor())) {
                    twin.insert(new Node(n, b, moves));
                }
            }
        }
    }
    
    private class Node {
        
        private Board board;
        private Board predecessor;
        private int priority;
        
        public Node(Board b, Board p, int m) {
            board = b;
            predecessor = p;
            priority = m + b.manhattan();
        }
        
        public int priority() {
            return priority;
        }
        
        public Board board() {
            return board;
        }
        
        public Board predecessor() {
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
            else return 0;
        }
    }
    
    public boolean isSolvable() {
        return solvable;
    }
    
    public int moves() {
        return moves;
    }
    
    public Iterable<Board> solution() {
        return solution;
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