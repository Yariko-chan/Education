import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

public class SeamCarver {

    private final Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("argument is null");
        }
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public     int width() {
        return picture.width();
    }

    // height of current picture
    public     int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public  double energy(int x, int y) throws IllegalArgumentException {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new IllegalArgumentException("x or y is out of range");
        }
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return 1000;
        }

        Color xMin = picture().get(x - 1, y);
        Color xMax = picture().get(x + 1, y);
        long dx = (long) (Math.pow(xMax.getRed() - xMin.getRed(), 2) +
                        Math.pow(xMax.getGreen() - xMin.getGreen(), 2) +
                        Math.pow(xMax.getBlue() - xMin.getBlue(), 2));
        Color yMin = picture().get(x, y - 1);
        Color yMax = picture().get(x, y + 1);
        long dy = (long) (Math.pow(yMax.getRed() - yMin.getRed(), 2) +
                Math.pow(yMax.getGreen() - yMin.getGreen(), 2) +
                Math.pow(yMax.getBlue() - yMin.getBlue(), 2));

        return Math.sqrt(dx*dx + dy*dy);
    }

    // sequence of indices for horizontal seam
    public   int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public   int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public    void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (picture.height() <= 1) {
            throw new IllegalArgumentException("Height of the picture is less than or equal to 1");
        }
        if (seam.length != height()) {
            throw new IllegalArgumentException("Wrong seam length");
        }
        // todo also if two adjacent entries differ by more than 1

    }

    // remove vertical seam from current picture
    public    void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (picture.width() <= 1) {
            throw new IllegalArgumentException("Width of the picture is less than or equal to 1");
        }
        if (seam.length != height()) {
            throw new IllegalArgumentException("Wrong seam length");
        }
        // todo also if two adjacent entries differ by more than 1

    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d columns by %d rows\n", picture.width(), picture.height());
        picture.show();
        SeamCarver sc = new SeamCarver(picture);

        StdOut.printf("Displaying energy calculated for each pixel.\n");
        SCUtility.showEnergy(sc);
    }
}
