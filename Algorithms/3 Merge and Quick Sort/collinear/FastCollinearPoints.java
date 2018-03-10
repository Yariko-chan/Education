import java.util.Arrays;

/**
 * Created by Diana on 09.02.2017.
 */
public class FastCollinearPoints {
    private LineSegment[] lines;
    private CollinearPointsNode first = null;
    private int size = 0;

    private class CollinearPointsNode {
        private Point min;
        private Point max;
        private double slope;
        private CollinearPointsNode next;
    }

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("argument is null");
        }
        Point[] p = Arrays.copyOf(points, points.length);
        Arrays.sort(p);
        for (int i = 0; i < p.length; i++) {
            // create copy array after i
            Point[] copy = Arrays.copyOfRange(p, i + 1, p.length);
            if (1 > copy.length) continue;

            // sort array by it's slope to p[i]
            // no need to check null points because compareTo in slopeTo() will throw nullPointer if it is
            Arrays.sort(copy, p[i].slopeOrder());

            // duplicate points will be always first because -Inf is always first after sort
            if (p[i].compareTo(copy[0]) == 0)
            // if (p[i].slopeTo(copy[0]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException();

            // search collinear points
            // if !isAddedYet add to list

            // search all collinear points group
            int j = 0;
            while (j < copy.length) {
                // first of collinear points
                int begin = j;
                while ((j + 1) < copy.length && p[i].slopeTo(copy[j]) == p[i].slopeTo(copy[j+1])) {
                    j++;
                }
                // last of collinear points
                int end = j;
                int length = end - begin + 1;

                // save lineSegment if points >= 3 ( 3 + p[i] = 4)
                if (length >= 3) {
                    Point [] collinearPoints = new Point[length + 1]; // + 1 for p[i]
                    collinearPoints[0] = p[i]; // copy p[i]
                    System.arraycopy(copy, begin, collinearPoints, 1, length);  // copy all collinears

                    saveCollinearPoints(collinearPoints);
                }

                // next point !collinear with previous
                j++;
            }
        }
        createArray();
    }

    private void createArray() {
        lines = new LineSegment[size];
        int i = 0;
        while (first != null && i < lines.length) {
            lines[i] = new LineSegment(first.min, first.max);
            first = first.next;
            i++;
        }
    }

    private void saveCollinearPoints(Point[] points) {
        CollinearPointsNode newNode = createCollinearPointsNode(points);
        CollinearPointsNode equalSlopeNode = getEqualSlopeNode(newNode);
        if (equalSlopeNode != null) {
            if (newNode.min.compareTo(equalSlopeNode.min) < 0)
                equalSlopeNode.min = newNode.min;
            if (newNode.max.compareTo(equalSlopeNode.max) > 0)
                equalSlopeNode.max = newNode.max;
        } else {
            addNode(newNode);
        }
    }

    private CollinearPointsNode getEqualSlopeNode(CollinearPointsNode node) {
        CollinearPointsNode current = first;
        while (current != null) {
            if (current.slope == node.slope
                    && (current.min == node.min || current.max == node.max)
                    )
                return current;
            else
                current = current.next;
        }
        return null;
    }

    private CollinearPointsNode createCollinearPointsNode(Point[] p) {
        // get max and min
        Point max = p[0];
        Point min = p[0];
        for (Point point:p) {
            if (max.compareTo(point) < 0) max = point;
            else if (min.compareTo(point) > 0) min = point;
        }
        CollinearPointsNode node = new CollinearPointsNode();
        node.max = max;
        node.min = min;
        node.slope = min.slopeTo(max);
        return node;
    }

    private void addNode(CollinearPointsNode node) {
        CollinearPointsNode oldFirst = first;
        first = node;
        first.next = oldFirst;
        size++;
    }

    public int numberOfSegments() {
        return lines.length;
    }

    public LineSegment[] segments() {
        LineSegment[] copy = Arrays.copyOf(lines, lines.length);
        return copy;
    }
}
