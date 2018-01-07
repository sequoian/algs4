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
        
        // copy array and check for null
        int n = points.length;
        Point[] copy = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points[i] != null)
                copy[i] = points[i];
            else
                throw new IllegalArgumentException();
        }
        
        // sort array and check for duplicates
        Arrays.sort(copy);
        for (int i = 0; i < n; i++) {
            boolean dupe = i > 0 && copy[i].compareTo(copy[i-1]) == 0;
            if (dupe) {
                throw new IllegalArgumentException();
            }
        }
        
        for (int i = 0; i < n; i++) {
            // copy array ordered by point value
            Point[] bySlope = new Point[n];
            for (int j = 0; j < n; j++) {
                bySlope[j] = copy[j];
            }
            
            // point to compare rest of points by
            Point x = copy[i];
            
            // order copied array by slope in comparison to x
            Arrays.sort(bySlope, x.slopeOrder());
            
            // line segment variables
            int count = 0;
            Point min = null;
            Point max = null;
            
            // check every slope in relation to x
            for (int j = 0; j < n-1; j++) {
                boolean slopeMatched = Double.compare(x.slopeTo(bySlope[j]), x.slopeTo(bySlope[j+1])) == 0;
                if (slopeMatched) {
                    // we know the first matched point will be the smallest because of sort stability
                    if (count == 0)
                        min = bySlope[j];
                    // we know j+1 will be the largest matched point due to sort stability
                    max = bySlope[j+1];
                    // increment point counter
                    count++;
                }
                
                // check to see if a line segement should be created
                boolean endOfArray = slopeMatched && j == n - 2;
                if (!slopeMatched || endOfArray) {
                    // 2 matches mean 4 collinear points
                    if (count >= 2) {
                        // check that x is the smallest point.  this is the criteria we will
                        // use to create the segment in order to avoid creating sub-segments
                        if (x.compareTo(min) < 0) {
                            // resize array if needed
                            if (segIdx == segments.length)
                                resize(segments.length * 2);
                            // create line segment and increment idx
                            segments[segIdx++] = new LineSegment(x, max);
                        }
                    }
                    // reset counter
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