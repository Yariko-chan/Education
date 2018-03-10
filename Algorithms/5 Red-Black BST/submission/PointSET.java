import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

/**
 * Created by Diana on 01.03.2017.
 */
public class PointSET {
    private TreeSet<Point2D> set;


    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (null == p) throw new NullPointerException("Argument is null");
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (null == p) throw new NullPointerException("Argument is null");
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);

        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (null == rect) throw new NullPointerException("Argument is null");
        Queue<Point2D> q = new Queue<>();
        for (Point2D p: set) {
            if (rect.contains(p)) q.enqueue(p);
        }
        return q;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D argPoint) {
        if (null == argPoint) throw new NullPointerException("Argument is null");
        if (set.isEmpty()) return null;
        Point2D nearest = null;
        double minSqDistance = 2;
        for (Point2D curPoint: set) {
            double temp = argPoint.distanceSquaredTo(curPoint);
            if (minSqDistance >= temp) {
                minSqDistance = temp;
                nearest = curPoint;
            }
        }
        return nearest;
    }


    public static void main(String[] args) {
        Point2D p01 = new Point2D(0.0, 0.1);
        Point2D p12 = new Point2D(0.1, 0.2);
        Point2D p22 = new Point2D(0.2, 0.2);
        Point2D p82 = new Point2D(0.8, 0.2);
        Point2D p15 = new Point2D(0.1, 0.5);
        Point2D p36 = new Point2D(0.3, 0.6);
        Point2D p87 = new Point2D(0.8, 0.7);
        Point2D p79 = new Point2D(0.7, 0.9);

        PointSET ps = new PointSET();
        ps.insert(p12);
        ps.insert(p22);
        ps.insert(p15);
        ps.insert(p36);
        ps.insert(p01);
        ps.insert(p82);
        ps.insert(p87);
        ps.insert(p79);

        ps.draw();
    }
}
