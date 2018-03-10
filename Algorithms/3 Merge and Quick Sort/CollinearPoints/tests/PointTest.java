import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Diana on 07.02.2017.
 */
public class PointTest {

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     */
    @Test
    public void slopeTo() throws Exception {
        Point p1 = new Point(1, 3);
        Point p2 = new Point(5, 4);
        assertEquals(p1.slopeTo(p2), (double) (4 - 3) / (5 - 1), 0.001);

        p1 = new Point(1, 3);
        p2 = new Point(5, 3);
        assertEquals(p1.slopeTo(p2), 0, 0.001);

        p1 = new Point(1, 3);
        p2 = new Point(1, 5);
        assertEquals(p1.slopeTo(p2), Double.POSITIVE_INFINITY, 0.001);

        p1 = new Point(5, 3);
        p2 = new Point(5, 3);
        assertEquals(p1.slopeTo(p2), Double.NEGATIVE_INFINITY, 0.001);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    @Test
    public void compareTo() throws Exception {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 4);
        assertTrue(p1.compareTo(p2) < 0);

        p1 = new Point(2, 4);
        p2 = new Point(2, 3);
        assertTrue(p1.compareTo(p2) > 0);

        p1 = new Point(2, 2);
        p2 = new Point(3, 2);
        assertTrue(p1.compareTo(p2) < 0);

        p1 = new Point(4, 4);
        p2 = new Point(2, 4);
        assertTrue(p1.compareTo(p2) > 0);


        p1 = new Point(2, 4);
        p2 = new Point(2, 4);
        assertTrue(p1.compareTo(p2) == 0);
    }

    @Test
    public void compare() throws Exception {
        Point p0 = new Point(0, 0);
        Point p1 = new Point(0, 5);//inf
        Point p2 = new Point(2, 4);//2
        Point p3 = new Point(3, 3);//1
        Point p4 = new Point(4, 2);//0.5
        Point p5 = new Point(5, 0);//0
        int c21 = p0.slopeOrder().compare(p2, p1);
        int c32 = p0.slopeOrder().compare(p3, p2);
        int c43 = p0.slopeOrder().compare(p4, p3);
        int c54 = p0.slopeOrder().compare(p5, p4);
        assertTrue(c21 < 0);
        assertTrue(c32 < 0);
        assertTrue(c43 < 0);
        assertTrue(c54 < 0);

        //sgn(compare(x, y)) == -sgn(compare(y, x)) for all x and y.
        int c45 = p0.slopeOrder().compare(p4, p5);
        assertEquals(Math.signum(c54), -Math.signum(c45), 0.1);

        // ((compare(x, y)>0) && (compare(y, z)>0)) implies compare(x, z)>0.
        int c12 = p0.slopeOrder().compare(p1, p2);
        int c23 = p0.slopeOrder().compare(p2, p3);
        int c13 = p0.slopeOrder().compare(p1, p3);
        assertEquals(Math.signum(c12), Math.signum(c23), 0.1);
        assertEquals(Math.signum(c12), Math.signum(c13), 0.1);

        Point p5_2 = new Point(5, 0);//dublicate for p5
        int c55 = p0.slopeOrder().compare(p5, p5_2);
        assertEquals(0, c55);// p5 and p5_2 are equals

        int c51 = p0.slopeOrder().compare(p5, p1);
        int c5_21 = p0.slopeOrder().compare(p5_2, p1);
        // compare(x, y)==0 implies that sgn(compare(x, z))==sgn(compare(y, z)) for all z
        assertEquals(Math.signum(c12), Math.signum(c23), 0.1);
        assertEquals(Math.signum(c12), Math.signum(c23), 0.1);
    }

    @Test
    public void slopeOrderInArray() throws Exception {
        Point p0 = new Point(0, 0);//
        Point p1 = new Point(0, 5);//inf
        Point p2 = new Point(2, 4);//2
        Point p3 = new Point(3, 3);//1
        Point p4 = new Point(4, 2);//0.5
        Point p5 = new Point(5, 0);//0
        Point[] p = {p1, p2, p3, p4, p5};
        Arrays.sort(p, p0.slopeOrder());
        assertEquals(p[0], p5);
        assertEquals(p[1], p4);
        assertEquals(p[2], p3);
        assertEquals(p[3], p2);
        assertEquals(p[4], p1);
    }

}