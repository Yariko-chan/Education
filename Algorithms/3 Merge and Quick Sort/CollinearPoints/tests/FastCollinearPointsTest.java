import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diana on 09.02.2017.
 */
public class FastCollinearPointsTest {

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
        System.out.print(collinear.numberOfSegments());
        StdDraw.show();
    }

    @Test
    public void test() {
        Point p1 = new Point(5000,  0);
        Point p2 = new Point(10000, 0);
        Point p3 = new Point(15000, 0);
        Point p4 = new Point(20000, 0);
        Point p5 = new Point(25000, 0);
        Point p6 = new Point(30000, 0);
        Point[] p = {p1, p2, p3, p4, p5, p6};
        BruteCollinearPoints br = new BruteCollinearPoints(p);
        FastCollinearPoints fc = new FastCollinearPoints(p);
        System.out.println(br.numberOfSegments());
        System.out.println(fc.numberOfSegments());
    }
}