import java.util.Arrays;

/**
 * Created by Diana on 08.02.2017.
 */
public class BruteCollinearPoints {
    private LineSegment[] lines;
    private LineNode first = null;
    private int size = 0;

    private class LineNode {
        private LineSegment line;
        private LineNode next;
    }

    public BruteCollinearPoints(Point[] p) {
        if (p == null) {
            throw new NullPointerException("argument is null");
        }
        for (int i = 0; i < p.length; i++) {
            for (int j = i + 1; j < p.length; j++) {
                if (p[i].compareTo(p[j]) == 0) throw new IllegalArgumentException();
                for (int k = j + 1; k < p.length; k++) {
                    if (p[i].compareTo(p[k]) == 0) throw new IllegalArgumentException();
                    for (int l = k + 1; l < p.length; l++) {
                        if (p[i].compareTo(p[l]) == 0) throw new IllegalArgumentException();
                        Point[] points = new Point[]{p[i], p[j], p[k], p[l]};
                        if (isOnOneLine(points)) createLineSegment(points);
                    }
//                    if (isOnOneLine(new Point[]{p[i], p[j], p[k]})) {
//                    }
                }
            }
        }
        createArray();
    }

    private void createArray() {
        lines = new LineSegment[size];
        int i = 0;
        while (first != null && i < lines.length) {
            lines[i] = first.line;
            first = first.next;
            i++;
        }
    }

    private void createLineSegment(Point[] p) {
        // get max and min
        Point max = p[0];
        Point min = p[0];
        for (Point point:p) {
            if (max.compareTo(point) < 0) max = point;
            else if (min.compareTo(point) > 0) min = point;
        }
        // add to lines
        LineNode oldFirst = first;
        first = new LineNode();
        first.line = new LineSegment(min, max);
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

    private boolean isOnOneLine(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == null) throw new NullPointerException();
            for (int j = i + 1; j < p.length; j++) {
                if (p[j] == null) throw new NullPointerException();
                for (int k = j + 1; k < p.length; k++) {
                    if (p[k] == null) throw new NullPointerException();
                    double ij = p[i].slopeTo(p[j]);
                    double ik = p[i].slopeTo(p[k]);
                    double jk = p[j].slopeTo(p[k]);
                    if ((ij + ik + jk) == Double.NEGATIVE_INFINITY)
                        throw new IllegalArgumentException();
                    if (ij != ik || ij != jk) return false;
                }
            }
        }
        return true;
    }
}
