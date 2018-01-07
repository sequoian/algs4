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
        
        // copy array
        int n = points.length;
        Point[] copy = new Point[n];
        for (int i = 0; i < n; i++) {
            copy[i] = points[i];
        }
        
        // sort array and check for duplicates and nulls
        Arrays.sort(copy);
        for (int i = 0; i < n; i++) {
            boolean dupe = i > 0 && copy[i].compareTo(copy[i-1]) == 0;
            if (dupe || copy[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = copy[i];
                        Point q = copy[j];
                        Point r = copy[k];
                        Point s = copy[l];
                        
                        double pq = p.slopeTo(q);
                        double pr = p.slopeTo(r);
                        double ps = p.slopeTo(s);
                        if (Double.compare(pq, pr) == 0 && Double.compare(pr, ps) == 0) {
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