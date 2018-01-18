import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    
    private SET<Point2D> set;
    
    public PointSET() {
        set = new SET<Point2D>();
    }
    
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    public int size() {
        return set.size();
    }
    
    public void insert(Point2D p) {
        set.add(p);
    }
    
    public boolean contains(Point2D p) {
        return set.contains(p);
    }
    
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : set) {
           StdDraw.point(p.x(), p.y());
        }  
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        SET<Point2D> inRange = new SET<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                inRange.add(p);
            }
        }
        return inRange;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D nearest = null;
        for (Point2D point : set) {
            if (nearest == null) {
                nearest = point;
            }
            else if (p.distanceTo(point) < p.distanceTo(nearest)) {
                nearest = point;
            }
        }
        return nearest;   
    }
}