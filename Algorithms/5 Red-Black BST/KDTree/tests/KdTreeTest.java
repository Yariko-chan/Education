import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Diana on 01.03.2017.
 */
public class KdTreeTest {
    private KdTree kdT;

    private Point2D p01 = new Point2D(0.0, 0.1);
    private Point2D p12 = new Point2D(0.1, 0.2);
    private Point2D p22 = new Point2D(0.2, 0.2);
    private Point2D p82 = new Point2D(0.8, 0.2);
    private Point2D p15 = new Point2D(0.1, 0.5);
    private Point2D p36 = new Point2D(0.3, 0.6);
    private Point2D p87 = new Point2D(0.8, 0.7);
    private Point2D p79 = new Point2D(0.7, 0.9);

    @Before
    public void setUp() throws Exception {
        kdT = new KdTree();
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(kdT.isEmpty());

        kdT.insert(p12);
        kdT.insert(p01);
        assertFalse(kdT.isEmpty());
    }

    @Test
    public void size() throws Exception {
        assertEquals(0, kdT.size());

        kdT.insert(p12);
        assertEquals(1, kdT.size());

        kdT.insert(p01);
        assertEquals(2, kdT.size());

        kdT.insert(p12);
        assertEquals(2, kdT.size());
    }

    @Test (expected = NullPointerException.class)
    public void insert() throws Exception {
        assertFalse(kdT.contains(p01));
        assertFalse(kdT.contains(p12));
        kdT.insert(p01);
        assertTrue(kdT.contains(p01));
        assertFalse(kdT.contains(p12));
        kdT.insert(p12);
        assertTrue(kdT.contains(p01));
        assertTrue(kdT.contains(p12));
        kdT.insert(null);
    }

    @Test (expected = NullPointerException.class)
    public void contains() throws Exception {
        kdT.contains(null);
    }

    @Test (expected = NullPointerException.class)
    public void range() throws Exception {
        RectHV rect = new RectHV(0.1, 0.2, 0.7, 0.8);
        HashSet<Point2D> included = new HashSet<Point2D>();
        included.add(p12);
        included.add(p22);
        included.add(p15);
        included.add(p36);
        HashSet<Point2D> notIncluded = new HashSet<Point2D>();
        included.add(p01);
        included.add(p82);
        included.add(p87);
        included.add(p79);

        Iterable<Point2D> range = kdT.range(rect);
        assertFalse(range.iterator().hasNext());
        for (Point2D p:range) {
            assertFalse(notIncluded.contains(p));
            assertFalse(included.contains(p));
        }

        kdT.insert(p12);
        kdT.insert(p22);
        kdT.insert(p15);
        kdT.insert(p36);
        kdT.insert(p01);
        kdT.insert(p82);
        kdT.insert(p87);
        kdT.insert(p79);
        range = kdT.range(rect);
        assertTrue(range.iterator().hasNext());
        for (Point2D p:range) {
            assertFalse(notIncluded.contains(p));
            assertTrue(included.contains(p));
        }

        kdT.range(null);
    }

    @Test (expected = NullPointerException.class)
    public void nearest() throws Exception {
        assertNull(kdT.nearest(p12));

        kdT.insert(p12);
        kdT.insert(p15);
        kdT.insert(p01);
        kdT.insert(p82);
        kdT.insert(p79);

        assertEquals(p12, kdT.nearest(p22));
        assertEquals(p15, kdT.nearest(p36));
        assertEquals(p79, kdT.nearest(p87));
        assertEquals(p15, kdT.nearest(new Point2D(0.01, 0.99)));
        kdT.nearest(null);
    }

    @Test
    public void nearest_wrong() throws Exception {
        PointSET brute = new PointSET();
        brute.insert(new Point2D(0.3, 0.9));
        brute.insert(new Point2D(0.3, 0.1));
        brute.insert(new Point2D(0.3, 0.2));
        brute.insert(new Point2D(0.3, 0.8));
        brute.insert(new Point2D(0.3, 0.4));
        brute.insert(new Point2D(0.3, 0.7));
        brute.insert(new Point2D(0.3, 0.5));
        kdT.insert(new Point2D(0.3, 0.9));
        kdT.insert(new Point2D(0.3, 0.1));
        kdT.insert(new Point2D(0.3, 0.2));
        kdT.insert(new Point2D(0.3, 0.8));
        kdT.insert(new Point2D(0.3, 0.4));
        kdT.insert(new Point2D(0.3, 0.7));
        kdT.insert(new Point2D(0.3, 0.5));

        Point2D arg = new Point2D(0.296875, 0.6171875);
        assertEquals(brute.nearest(arg), kdT.nearest(arg));

        arg = new Point2D(0.298828125, 0.302734375);
        assertEquals(brute.nearest(arg), kdT.nearest(arg));
    }
}
