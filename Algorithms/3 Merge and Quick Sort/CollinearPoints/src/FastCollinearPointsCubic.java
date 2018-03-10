import java.util.Arrays;

/**
 * Created by Diana on 10.02.2017.
 */
public class FastCollinearPointsCubic {
    private LineSegment[] lines;
    private LineNode first = null;
    private int size = 0;

    private class LineNode {
        private LineSegment line;
        private LineNode next;
    }

    public FastCollinearPointsCubic(Point[] p) {
        if (p == null) {
            throw new NullPointerException("argument is null");
        }
        int N = p.length;
        for (int i = 0; i < N; i++) {
            // create copy array without p[i]
            Point[] copy = new Point[N - 1];
            // System.arraycopy(from, beginIndex, to, beginIndex, count);
            System.arraycopy(p, 0, copy, 0, i);
            System.arraycopy(p, i + 1, copy, i, N - i - 1);

            // sort array by it's slope to p[i]
            // no need to check null points because compareTo in slopeTo() will throw nullPointer if it is
            Arrays.sort(copy, p[i].slopeOrder());

            // duplicate points will be always first because -Inf is always first after sort
            if (p[i].slopeTo(copy[0]) == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();

            //search all collinear points group
            int j = 0;
            while (j < N - 1) {
                // first of collinear points
                int begin = j;
                while ((j + 1) < N - 1 && p[i].slopeTo(copy[j]) == p[i].slopeTo(copy[j+1])) {
                    j++;
                }
                // last of collinear points
                int end = j;
                int length = end - begin + 1;

                // save lineSegment if points >= 3 ( 3 + p[i]!)
                if (length >= 3) {
                    Point [] collinearPoints = new Point[length + 1]; // + 1 for p[i]
                    // System.arraycopy(from, beginIndex, to, beginIndex, count);
                    System.arraycopy(copy, begin, collinearPoints, 0, length);  // copy all collinears
                    System.arraycopy(p, i, collinearPoints, length, 1);  // copy p[i] at the end

                    saveLineSegment(collinearPoints);
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
            lines[i] = first.line;
            first = first.next;
            i++;
        }
    }

    private void saveLineSegment(Point[] p) {
        // get max and min
        Point max = p[0];
        Point min = p[0];
        for (Point point:p) {
            if (max.compareTo(point) < 0) max = point;
            else if (min.compareTo(point) > 0) min = point;
        }

        if (isSavedAlready(new LineSegment(min, max))) return;

        // add to lines
        LineNode oldFirst = first;
        first = new LineNode();
        first.line = new LineSegment(min, max);
        first.next = oldFirst;
        size++;
    }

    private boolean isSavedAlready(LineSegment line) {
        LineNode node = first;
        while (node != null) {
            if (node.line.toString().equals(line.toString())) return true;
            node = node.next;
        }
        return false;
    }

    public int numberOfSegments() {
        return lines.length;
    }

    public LineSegment[] segments() {
        LineSegment[] copy = Arrays.copyOf(lines, lines.length);
        return copy;
    }
}
