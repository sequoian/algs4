import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private LineSegment[] segments = new LineSegment[2];
    private int segIdx = 0;
    
    public FastCollinearPoints(Point[] points) {
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
            // copy array ordered by point value
            Point[] bySlope = new Point[n];
            for (int j = 0; j < n; j++) {
                bySlope[j] = copy[j];
            }
            
            Point x = copy[i];
            
            // order copied array by slope in comparison to i
            Arrays.sort(bySlope, x.slopeOrder());
            int count = 0;
            for (int j = 0; j < n-1; j++) {
                //System.out.format("%f, %f\n", x.slopeTo(bySlope[j]), x.slopeTo(bySlope[j+1]));
                
                boolean slopeMatched = x.compareTo(bySlope[j]) < 0 && Double.compare(x.slopeTo(bySlope[j]), x.slopeTo(bySlope[j+1])) == 0;
                if (slopeMatched) {
                    count++;
                }
                
                if (!slopeMatched || (slopeMatched && j == n-2)) {
                    if (count >= 2) {
                        if (segIdx == segments.length) {
                            resize(segments.length * 2);
                        }
                        segments[segIdx++] = new LineSegment(x, bySlope[j+1]);
                    }
                    count = 0;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}