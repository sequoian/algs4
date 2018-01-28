import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    
    private Node root = null;
    private int size = 0;
    
    public KdTree() {

    }
    
    private class Node {
        private Point2D point;
        private RectHV rect;
        private Node lb;
        private Node rt;
        
        public Node(Point2D p, RectHV r) {
            this.point = p;
            this.rect = r;
            this.lb = null;
            this.rt = null;
        }
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public int size() {
        return size;
    }
    
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = put(root, p, true);
    }
    
    private Node put(Node n, Point2D p, boolean vert) {
        if (n == null) {
            size++;
            return new Node(p, null);
        }
        
        // check if the node is dividing space with vertical line
        int cmp;
        if (vert) {
            // compare x coord
            cmp = Double.compare(n.point.x(), p.x());
        }
        else {
            // compare y coord
            cmp = Double.compare(n.point.y(), p.y());
        }
        
        // put in lb if smaller, and rt if greater or equal
        if (cmp < 0) {
            n.lb = put(n.lb, p, !vert);
        }
        else {
            n.rt = put(n.rt, p, !vert);
        }
        
        return n;
    }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(root, p, true) != null;
    }
    
    private Point2D get(Node n, Point2D p, boolean vert) {
        if (n == null) return null;
        if (n.point.equals(p)) return p;
        
        // compare points based on x or y coords
        int cmp;
        if (vert)
            cmp = Double.compare(n.point.x(), p.x());
        else
            cmp = Double.compare(n.point.y(), p.y());
        
        if (cmp < 0)
            return get(n.lb, p, !vert);
        else
            return get(n.rt, p, !vert);
    }
    
    //public void draw() {

    //}
    
    //public Iterable<Point2D> range(RectHV rect) {

    //}
    
    //public Point2D nearest(Point2D p) {

    //}
    
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        
        assert tree.isEmpty() == true;
        Point2D p1 = new Point2D(0.2, 0.3);
        Point2D p2 = new Point2D(0.8, 0.5);
        tree.insert(p1);
        assert tree.isEmpty() == false;
        assert tree.size == 1;
        assert tree.contains(p1) == true;
        assert tree.contains(p2) == false;
        tree.insert(p2);
        assert tree.size == 2;
        assert tree.contains(p2) == true;
    }
}