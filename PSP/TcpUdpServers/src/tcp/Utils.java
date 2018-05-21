package tcp;

import java.util.Arrays;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for TcpUdpServers
 */
public class Utils {
    public static final int SERVER_PORT = 4242;
    public static final String SERVER_IP = "127.0.0.1";
    public static final String EXIT = "exit";

    // 10.0, 15.0, 7.0;
    // 20.0, 57.0, 30.0;
    // 37.0, 56.0, 85.0;

    // 3 3 10.0 15.0 7.0 20.0 57.0 30.0 37.0 56.0 85.0


    public static String arrayToString(double[][] array) {
        StringBuilder b = new StringBuilder();
        b.append(array.length)
                .append(" ")
                .append(array[0].length)
                .append(" ");
        for (double[] child : array) {
            for (double d : child) {
                b.append(Double.toString(d))
                        .append(" ");
            }
        }
        return b.toString();
    }

    public static double[][] stringToArray(String src) {
        double[][] result = new double[0][0];
        if (src == null || src.isEmpty()) {
            System.out.println("Void source string");
            return result;
        }
        String[] strings = src.split("\\s");
        if (strings.length < 2) {
            System.out.println("Too small sourse array");
            return result;
        }
        int rows = Integer.valueOf(strings[0]);
        int cols = Integer.valueOf(strings[1]);
        result = new double[rows][cols];
        if (strings.length != rows * cols + 2) {
            System.out.println("Array must have size rows*cols + 2 int at start for row and col");
        }
        try {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    result[i][j] = Double.valueOf(strings[i * cols + j + 2]); // two is for col and row on start

                }
            }
        } catch (ClassCastException e) {
            System.out.println("Parse error");
        }
        return result;
    }

    public static void main(String[] args) {
        double x[][] = {
                {10, 15, 7,},
                {20, 57, 30,},
                {37, 56, 85,},
        };
        String test = Utils.arrayToString(x);
        System.out.println(test);
        double[][] test2 = Utils.stringToArray(test);
        System.out.println(Utils.arrayToString(test2));

    }

    /**
     * Method that calculates determinant of given matrix
     * @param matrix matrix of which we need to know determinant
     * @return determinant of given matrix
     */
    public static double matrixDeterminant (double[][] matrix) {
        double temporary[][];
        double result = 0;

        if (matrix.length == 1) {
            result = matrix[0][0];
            return (result);
        }

        if (matrix.length == 2) {
            result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
            return (result);
        }

        for (int i = 0; i < matrix[0].length; i++) {
            temporary = new double[matrix.length - 1][matrix[0].length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            result += matrix[0][i] * Math.pow (-1, (double) i) * matrixDeterminant (temporary);
        }
        return (result);
    }
}
