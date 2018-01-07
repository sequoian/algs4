import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    
    private LineSegment[] segments = new LineSegment[2];
    private int segIdx = 0;
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        
        Arrays.sort(points);
        
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        
                        double pq = p.slopeTo(q);
                        double pr = p.slopeTo(r);
                        double ps = p.slopeTo(s);
                        if (pq == pr && pr == ps) {
                            if (segIdx == segments.length) {
                                resize(2 * segments.length);
                            }
                            segments[segIdx++] = new LineSegment(p, s);
                        }
                    }
                }
            }
        }
    }
    
    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < segIdx; i++) {
            copy[i] = segments[i];
        }
        segments = copy;
    }
    
    public int numberOfSegments() {
        return segIdx;
    }
    
    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[segIdx];
        for (int i = 0; i < segIdx; i++) {
            copy[i] = segments[i];
        }
        
        return copy;
    }
    
    public static void main(String[] args) {     
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}