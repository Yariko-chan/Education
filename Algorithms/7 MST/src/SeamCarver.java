import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;


public class SeamCarver {

    private static final int BORDER_ENERGY = 1000;
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("argument is null");
        }
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
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
    public  double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new IllegalArgumentException("x or y is out of range");
        }
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return BORDER_ENERGY; // border has energy 1000
        }

        Color xLeft = picture.get(x - 1, y);
        Color xRight = picture.get(x + 1, y);
        int rx = xRight.getRed() - xLeft.getRed();
        int gx = xRight.getGreen() - xLeft.getGreen();
        int bx = xRight.getBlue() - xLeft.getBlue();
        int dxSq = rx * rx + gx * gx + bx * bx;

        Color yUp = picture.get(x, y - 1);
        Color yDown = picture.get(x, y + 1);
        int ry = yDown.getRed() - yUp.getRed();
        int gy = yDown.getGreen() - yUp.getGreen();
        int by = yDown.getBlue() - yUp.getBlue();
        int dySq = ry * ry + gy * gy + by * by;

        return Math.sqrt(dxSq + dySq);
    }

    // sequence of indices for horizontal seam
    public   int[] findHorizontalSeam() {
        Picture original = picture;
        Picture transposed = transposePicture(picture);
        picture = transposed;
        int[] result = findVerticalSeam();
        picture = original;
        return result;
    }

    // sequence of indices for vertical seam
    public   int[] findVerticalSeam() {
        double[][] distTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];

        // first row has distance 1000 and no prev node
        for (int i = 0; i < width(); i++) {
            distTo[0][i] = BORDER_ENERGY;
            edgeTo[0][i] = -1;
        }

        int minBottomPx = 0;
        for (int j = 1; j < height(); j++) { // row
            for (int i = 0; i < width(); i++) { // column

                // find parent with min distance
                // center parent
                int minParentIndex = i;
                // check left if distinct
                int leftParent = i - 1;
                if (leftParent >= 0 && distTo[j - 1][minParentIndex] >= distTo[j - 1][leftParent]) {
                    minParentIndex = leftParent;
                }
                // check right if distinct
                int rightParent = i + 1;
                if (rightParent < width() && distTo[j - 1][minParentIndex] > distTo[j - 1][rightParent]) {
                    minParentIndex = rightParent;
                }

                distTo[j][i] = distTo[j - 1][minParentIndex] + energy(i, j);
                edgeTo[j][i] = minParentIndex;

                if (j == (height() - 1) && distTo[j][i] < distTo[j][minBottomPx]) {
                    minBottomPx = i;
                }
            }
        }

        int[] result = new int[height()];
        result[result.length - 1] = minBottomPx;
        for (int i = result.length - 2; i >= 0; i--) {
            result[i] = edgeTo[i + 1][result[i + 1]];
        }
        return result;
    }

    // remove horizontal seam from current picture
    public    void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (picture.height() <= 1) {
            throw new IllegalArgumentException("Height of the picture is less than or equal to 1");
        }
        if (seam.length != width()) {
            throw new IllegalArgumentException("Wrong seam length");
        }
        Picture transposed = transposePicture(picture);
        picture = transposed;
        removeVerticalSeam(seam);
        picture = transposePicture(picture);

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
        Picture newPicture = new Picture(width() - 1, height());
        for (int i = 0; i < seam.length && i < picture.height(); i++) {
            if (seam[i] < 0 || seam[i] >= width()) {
                throw new IllegalArgumentException("Index out of bounds");
            }
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException(" two adjacent entries differ by more than 1");
            }
            for (int j = 0; j < picture.width() - 1; j++) {
                if (j >= seam[i]) {
                    newPicture.set(j, i, picture.get(j + 1, i));
                } else {
                    newPicture.set(j, i, picture.get(j, i));
                }
            }
        }
        picture = newPicture;
    }

    private Picture transposePicture(Picture srcPicture) {
        Picture transposed = new Picture(srcPicture.height(), srcPicture.width());
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                transposed.set(i, j, srcPicture.get(j, i));
            }
        }
        return transposed;
    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("image is %d columns by %d rows\n", picture.width(), picture.height());
        picture.show();
        SeamCarver sc = new SeamCarver(picture);

        StdOut.printf("Displaying energy calculated for each pixel.\n");
        SCUtility.showEnergy(sc);

        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++)
                StdOut.printf("%9.0f ", sc.energy(col, row));
            StdOut.println();
        }
    }
}
