import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 08.02.2017.
 */
public class BruteCollinearPointsTest {

    @Test(expected = IllegalArgumentException.class)
    public void failedTest14_5_5() {

        Point p1 = new Point(12484, 18304);
        Point p2 = new Point(23131,  6630);
        Point p3 = new Point(14876, 13551);
        Point p4 = new Point(16053,  5090);
        Point p5 = new Point(12484, 18304);
        Point[] p = {p1, p2, p3, p4, p5};
        BruteCollinearPoints br = new BruteCollinearPoints(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failedTest14_5() {

        Point p1 = new Point(20474, 15771);
        Point p2 = new Point(1588, 24488);
        Point p3 = new Point(17782,  1025);
        Point p4 = new Point(27785,  6858);
        Point p5 = new Point(20474, 15771);
        Point[] p = {p1, p2, p3, p4, p5};
        BruteCollinearPoints br = new BruteCollinearPoints(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failedTest14_4() {

        Point p1 = new Point(28854, 24611);
        Point p2 = new Point(31967, 7537);
        Point p3 = new Point(28019, 21355);
        Point p4 = new Point(28854, 24611);
        Point[] p = {p1, p2, p3, p4};
        BruteCollinearPoints br = new BruteCollinearPoints(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failedTest14_3() {

        Point p1 = new Point(12278, 3091);
        Point p2 = new Point(21595, 20154);
        Point p3 = new Point(12278, 3091);
        Point[] p = {p1, p2, p3};
        BruteCollinearPoints br = new BruteCollinearPoints(p);
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