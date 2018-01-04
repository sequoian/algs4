/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        double rise = that.y - this.y;
        if (rise == 0) return 0;
        double run = that.x - this.x;
        if (run == 0) return Double.POSITIVE_INFINITY;
        return rise / run;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else if (this.x < that.x) return -1;
        else if (this.x > that.x) return 1;
        else return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }
    
    public class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            Double slope1 = slopeTo(p1);
            Double slope2 = slopeTo(p2);
            if (slope1 < slope2) return -1;
            else if (slope1 > slope2) return 1;
            else return 0;
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p = new Point(0, 0);
        Point same = new Point(0, 0);
        Point hori = new Point(1, 0);
        Point vert = new Point(0, 1);
        Point nhori = new Point(-1, 0);
        Point nvert = new Point(0, -1);
        Point diag = new Point(2, 1);
        
        // slope to
        assert p.slopeTo(same) == Double.NEGATIVE_INFINITY;
        assert p.slopeTo(vert) == Double.POSITIVE_INFINITY;
        assert p.slopeTo(hori) == 0;
        assert p.slopeTo(diag) == 0.5;
        
        // compare to
        assert p.compareTo(same) == 0;
        assert p.compareTo(hori) == -1;
        assert p.compareTo(vert) == -1;
        assert p.compareTo(nhori) == 1;
        assert p.compareTo(nvert) == 1;
        
        // slope order
        Comparator<Point> c = p.slopeOrder();
        // steep > shallow
        Point steep = new Point(1, 2);
        assert c.compare(diag, steep) == -1;
        assert c.compare(steep, diag) == 1;
        // same slopes match
        assert c.compare(diag, diag) == 0;
        assert c.compare(hori, nhori) == 0;
        assert c.compare(vert, nvert) == 0;
        // vert > posi > hori > nega > same
        Point posi = steep;
        Point nega = new Point(1, -2);
        assert c.compare(vert, steep) == 1;
        assert c.compare(steep, hori) == 1;
        assert c.compare(hori, nega) == 1;
        assert c.compare(nega, same) == 1;
        // changes based on point
        p = new Point(3, 2);
        c = p.slopeOrder();
        assert c.compare(diag, steep) == 1;
    }
}
