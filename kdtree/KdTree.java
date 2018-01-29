import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

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
        root = put(root, p, true, new RectHV(0, 0, 1, 1));
    }
    
    private Node put(Node n, Point2D p, boolean vert, RectHV r) {
        if (n == null) {
            size++;
            return new Node(p, r);
        }
        
        // check if the node is dividing space with vertical line
        int cmp;
        RectHV rect;
        if (vert) {
            // compare x coord
            cmp = Double.compare(n.point.x(), p.x());
            if (cmp < 0) {
                rect = new RectHV(r.xmin(), r.ymin(), n.point.x(), r.ymax());
                n.lb = put(n.lb, p, !vert, rect);
            }
            else {
                rect = new RectHV(n.point.x(), r.ymin(), r.xmax(), r.ymax());
                n.rt = put(n.rt, p, !vert, rect);
            }
        }
        else {
            // compare y coord
            cmp = Double.compare(n.point.y(), p.y());
            if (cmp < 0) {
                rect = new RectHV(r.xmin(), n.point.y(), r.xmax(), r.ymax());
                n.lb = put(n.lb, p, !vert, rect);
            }
            else {
                rect = new RectHV(r.xmin(), r.ymin(), r.xmax(), n.point.y());
                n.rt = put(n.rt, p, !vert, rect);
            }
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
    
    public void draw() {
        draw(root, true);
    }
    
    private void draw(Node n, boolean vert) {
        if (n == null) return;
        
        // draw point
        Point2D p = n.point;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(p.x(), p.y());
        
        // draw line
        StdDraw.setPenRadius();
        if (vert) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(p.x(), n.rect.ymin(), p.x(), n.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), p.y(), n.rect.xmax(), p.y());
        }
        
        draw(n.lb, !vert);
        draw(n.rt, !vert);
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }
    
    private void range(Node n, RectHV rect, Queue<Point2D> q) {
        if (n == null) return;
        if (!n.rect.intersects(rect)) return;
        if (rect.contains(n.point)) {
            q.enqueue(n.point);
        }
        range(n.lb, rect, q);
        range(n.rt, rect, q);
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearest(root, p, root.point, true);
    }

    private Point2D nearest(Node n, Point2D p, Point2D closest, boolean vert) {
        if (n == null) return closest;
        
        // prune nodes and children that cannot be closer
        if (closest.distanceSquaredTo(p) < n.rect.distanceSquaredTo(p))
            return closest;  
        
        // compare this node with closest
        if (n.point.distanceSquaredTo(p) < closest.distanceSquaredTo(p))
            closest = n.point;
        
        // go down the containing partition first
        Node first, second;
        if (vert) {
            if (p.x() < n.point.x()) {
                first = n.lb;
                second = n.rt;
            }
            else {
                first = n.rt;
                second = n.lb;
            }
        }
        else {
            if (p.y() < n.point.y()) {
                first = n.rt;
                second = n.lb;
            }
            else {
                first = n.lb;
                second = n.rt;
            }
        }
        
        closest = nearest(first, p, closest, !vert);
        closest = nearest(second, p, closest, !vert);
        
        return closest;
    }
    
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
        
        // test nearest
        Point2D nearest = new Point2D(0.9, 0.8);
        assert tree.nearest(nearest).equals(p2);
        
        // test range
        System.out.println("Range");
        RectHV range = new RectHV(0, 0, 1, 1);
        for (Point2D p : tree.range(range)) {
            System.out.println(p.toString());
        }
        System.out.println("----");
        range = new RectHV(0, 0, 0.5, 0.5);
        for (Point2D p : tree.range(range)) {
            System.out.println(p.toString());
        }
    }
}