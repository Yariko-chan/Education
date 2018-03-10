import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by Diana on 01.03.2017.
 */
public class KdTree {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private static final boolean LEFT = true;
    private static final boolean RIGHT = false;

    private PointNode root;
    private int size = 0;

    private static class PointNode {
        private Point2D point;
        private PointNode left;
        private PointNode right;

        public PointNode(Point2D p) {
            point = p;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (null == p) throw new NullPointerException("Argument is null");
        // points always in range 1..0
        if (p.x() < 0 || p.y() < 0 || p.x() * p.y() > 1) throw new IllegalArgumentException();
        // if (contains(p)) return;
        // don't use this here
        // it's doing same work twice
        // test this case in recursive insert
        root = insert(root, p, VERTICAL);
    }

    private PointNode insert(PointNode curNode, Point2D newPoint, boolean orientation) {
        if (null == curNode) {
            size++;
            return new PointNode(newPoint);
        }

        // do not save point, which is already in set
        if (0 == curNode.point.compareTo(newPoint)) {
            return curNode;
        }

        int c = (VERTICAL == orientation) ?
                  Double.compare(newPoint.x(), curNode.point.x())
                : Double.compare(newPoint.y(), curNode.point.y());

        if (c < 0) curNode.left  = insert(curNode.left, newPoint, !orientation);
        else       curNode.right = insert(curNode.right, newPoint, !orientation);

        return curNode;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (null == p) throw new NullPointerException("Argument is null");
        if (p.x() < 0 || p.y() < 0 || p.x() * p.y() > 1) throw new IllegalArgumentException();
        return contains(root, p, VERTICAL);
    }

    private boolean contains(PointNode curNode, Point2D searchPoint, boolean orientation) {
        // not found
        if (null == curNode) return false;

        // found
        if (0 == curNode.point.compareTo(searchPoint)) {
            return true;
        }

        int c = (VERTICAL == orientation) ?
                  Double.compare(searchPoint.x(), curNode.point.x())
                : Double.compare(searchPoint.y(), curNode.point.y());

        if (c < 0) return contains(curNode.left, searchPoint, !orientation);
        else       return contains(curNode.right, searchPoint, !orientation);
    }

    // draw all points to standard draw
    public void draw() {
        // settings for drawing points
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);

        draw(root, new RectHV(0, 0, 1, 1), VERTICAL);
    }

    private void draw(PointNode n, RectHV rect, boolean orientation) {
        if (null == n) return;

        // draw this node
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        n.point.draw();
        StdDraw.setPenRadius(0.001);
        if (VERTICAL == orientation) { /* draw by x coordinate */
            StdDraw.setPenColor(StdDraw.RED);

            StdDraw.line(n.point.x(), rect.ymin(), n.point.x(), rect.ymax());

            RectHV leftRect = getChildRect(n.point, rect, orientation, LEFT);
            draw(n.left, leftRect, HORIZONTAL);

            RectHV rightRect = getChildRect(n.point, rect, orientation, RIGHT);
            draw(n.right, rightRect, HORIZONTAL);
        } else { /* draw by y coordinate */
            StdDraw.setPenColor(StdDraw.BLUE);

            StdDraw.line(rect.xmin(), n.point.y(), rect.xmax(), n.point.y());

            RectHV bottomRect = getChildRect(n.point, rect, orientation, LEFT);
            draw(n.left, bottomRect, VERTICAL);

            RectHV topRect = getChildRect(n.point, rect, orientation, RIGHT);
            draw(n.right, topRect, VERTICAL);
        }
    }

    private RectHV getChildRect(Point2D parentPoint, RectHV parentRect, boolean orientation, boolean leftSide) {
        double xmin, ymin, xmax, ymax;

        if (VERTICAL == orientation) {
            ymin = parentRect.ymin();
            ymax = parentRect.ymax();
            if (leftSide) {
                xmin = parentRect.xmin();
                xmax = parentPoint.x();
            } else { /* right side */
                xmin = parentPoint.x();
                xmax = parentRect.xmax();
            }
        } else { /* HORIZONTAL */
            xmin = parentRect.xmin();
            xmax = parentRect.xmax();
            if (leftSide) { /* bottom */
                ymin = parentRect.ymin();
                ymax = parentPoint.y();
            } else { /* top */
                ymin = parentPoint.y();
                ymax = parentRect.ymax();
            }
        }

        return new RectHV(xmin, ymin, xmax, ymax);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rangeRect) {
        if (null == rangeRect) throw new NullPointerException("Argument is null");

        Queue<Point2D> included = new Queue<>();

        RectHV rootRect = new RectHV(0, 0, 1, 1);
        range(root, rootRect, VERTICAL, rangeRect, included);
        return included;
    }

    private void range(PointNode n, RectHV nRect, boolean nOrientation,
                       RectHV rangeRect, Queue<Point2D> included) {
        if (null == n) return;

        // if nRect not intersects rangeRect, return
        if (!nRect.intersects(rangeRect)) return;

        // add n if in rangeRect
        if (rangeRect.contains(n.point)) {
            included.enqueue(n.point);
        }
        // range left
        RectHV leftRect = getChildRect(n.point, nRect, nOrientation, LEFT);
        range(n.left, leftRect, !nOrientation, rangeRect, included);

        // range right
        RectHV rightRect = getChildRect(n.point, nRect, nOrientation, RIGHT);
        range(n.right, rightRect, !nOrientation, rangeRect, included);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D argPoint) {
        if (null == argPoint) throw new NullPointerException("Argument is null");

        RectHV rootRect = new RectHV(0, 0, 1, 1);
        return nearest(root, rootRect, VERTICAL, argPoint);
    }

    private Point2D nearest(PointNode n, RectHV nRect, boolean orientation, Point2D argPoint) {
        if (null == n) return null;

        Point2D nearest = n.point;
        double minSqDistance = argPoint.distanceSquaredTo(n.point);

        if (null == n.left && null == n.right) return nearest;

        RectHV leftRect = (null != n.left) ?
                getChildRect(n.point, nRect, orientation, LEFT)
                : null;
        RectHV rightRect = (null != n.right) ?
                getChildRect(n.point, nRect, orientation, RIGHT)
                : null;

        // getting order of traverse
        PointNode first = n.left;
        PointNode second = n.right;
        RectHV firstRect = leftRect;
        RectHV secondRect = rightRect;

        if (n.right != null && rightRect.contains(argPoint)) {
            first = n.right;
            second = n.left;
            firstRect = rightRect;
            secondRect = leftRect;
        }

        // search nearest in first
        if (first != null) {
            Point2D firstNearest = nearest(first, firstRect, !orientation, argPoint);
            double firstMinSqDistance = argPoint.distanceSquaredTo(firstNearest);
            if (minSqDistance > firstMinSqDistance) {
                minSqDistance = firstMinSqDistance;
                nearest = firstNearest;
            }
        }

        // if second rect is nearer than nearest point found so far
        if (second != null && minSqDistance > secondRect.distanceSquaredTo(argPoint)) {
            Point2D secondNearest = nearest(second, secondRect, !orientation, argPoint);
            double secondMinSqDistance = argPoint.distanceSquaredTo(secondNearest);
            if (minSqDistance > secondMinSqDistance) {
                minSqDistance = secondMinSqDistance;
                nearest = secondNearest;
            }
        }

        return nearest;
    }


    public static void main(String[] args) {

        KdTree kdT = new KdTree();

        Point2D p01 = new Point2D(0.206107, 0.095492);
        Point2D p12 = new Point2D(0.975528, 0.654508);
        Point2D p22 = new Point2D(0.024472, 0.345492);
        Point2D p82 = new Point2D(0.793893, 0.095492);
        Point2D p15 = new Point2D(0.793893, 0.904508);
        Point2D p36 = new Point2D(0.975528, 0.345492);
        Point2D p87 = new Point2D(0.206107, 0.904508);
        Point2D p79 = new Point2D(0.500000, 0.000000);
        Point2D p81 = new Point2D(0.024472, 0.654508);
        Point2D p19 = new Point2D(0.500000, 1.000000);

        kdT.insert(p01);
        kdT.insert(p12);
        kdT.insert(p22);
        kdT.insert(p82);
        kdT.insert(p15);
        kdT.insert(p36);
        kdT.insert(p87);
        kdT.insert(p79);
        kdT.insert(p81);
        kdT.insert(p19);

        kdT.draw();
    }
}
