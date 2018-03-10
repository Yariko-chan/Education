import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by Diana on 01.03.2017.
 */
public class PointSETTest {
    private PointSET ps;
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
        ps = new PointSET();
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(ps.isEmpty());

        ps.insert(p12);
        ps.insert(p01);
        assertFalse(ps.isEmpty());
    }

    @Test
    public void size() throws Exception {
        assertEquals(0, ps.size());

        ps.insert(p12);
        assertEquals(1, ps.size());

        ps.insert(p01);
        assertEquals(2, ps.size());
    }

    @Test (expected = NullPointerException.class)
    public void insert() throws Exception {
        assertFalse(ps.contains(p01));
        assertFalse(ps.contains(p12));
        ps.insert(p01);
        assertTrue(ps.contains(p01));
        assertFalse(ps.contains(p12));
        ps.insert(p12);
        assertTrue(ps.contains(p01));
        assertTrue(ps.contains(p12));
        ps.insert(null);
    }

    @Test (expected = NullPointerException.class)
    public void contains() throws Exception {
        ps.contains(null);
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

        Iterable<Point2D> range = ps.range(rect);
        assertFalse(range.iterator().hasNext());
        for (Point2D p:range) {
            assertFalse(notIncluded.contains(p));
            assertFalse(included.contains(p));
        }

        ps.insert(p12);
        ps.insert(p22);
        ps.insert(p15);
        ps.insert(p36);
        ps.insert(p01);
        ps.insert(p82);
        ps.insert(p87);
        ps.insert(p79);
        range = ps.range(rect);
        assertTrue(range.iterator().hasNext());
        for (Point2D p:range) {
            assertFalse(notIncluded.contains(p));
            assertTrue(included.contains(p));
        }

        ps.range(null);
    }

    @Test (expected = NullPointerException.class)
    public void nearest() throws Exception {
        assertNull(ps.nearest(p12));

        ps.insert(p12);
        ps.insert(p15);
        ps.insert(p01);
        ps.insert(p82);
        ps.insert(p79);

        assertEquals(p12, ps.nearest(p22));
        assertEquals(p15, ps.nearest(p36));
        assertEquals(p79, ps.nearest(p87));
        assertEquals(p15, ps.nearest(new Point2D(0.01, 0.99)));
        ps.nearest(null);
    }

    @Test
    public void draw() throws Exception {


    }
}