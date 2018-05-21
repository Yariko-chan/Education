package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Разработать приложение на основе TCP-соединения,
 * позволяющее осуществлять взаимодействие клиента
 * и сервера по совместному решению задач обработки
 * информации. Приложение должно располагать возможностью
 * передачи и модифицирования получаемых (передаваемых) данных.
 *
 * 6.   Разработать приложение-определитель матрицы.
 * На клиентской части вводится исходная матрица произвольного
 * порядка и передается серверу, а тот в свою очередь вычисляет
 * определитель этой матрицы и возвращает результат клиенту.
 *
 * Created by Ganeeva Diana in Май, 2018
 * for TcpUdpServers
 */
public class Client {

    public static void main(String[] arg) {
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        Socket clientSocket = null;
        try {
            System.out.println("Server connecting....");

            clientSocket = new Socket(Utils.SERVER_IP, Utils.SERVER_PORT);
            System.out.println("connection established....");

            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());

            String matrix = readMatrix(stdin);
            System.out.println("You've entered: " + matrix);
            while (!matrix.equals(Utils.EXIT)) {
                outputStream.writeObject(matrix);
                System.out.println("~server~: " + inputStream.readObject());
                System.out.println("---------------------------");

                matrix = readMatrix(stdin);
                System.out.println("You've entered: " + matrix);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readMatrix(BufferedReader stdin) throws IOException {
        System.out.printf("For exit program print \'%s\' \n\n", Utils.EXIT);

        System.out.print("Enter rows count: ");
        String read = stdin.readLine();
        if (read.equals(Utils.EXIT)) {
            return read;
        }
        int rows = Integer.valueOf(read);

        System.out.print("Enter columns count: ");
        read = stdin.readLine();
        if (read.equals(Utils.EXIT)) {
            return read;
        }
        int cols = Integer.valueOf(read);

        double[][] matrix = new double[rows][cols];

        System.out.println("Enter rows, dividing numbers with space. Double format using point.");
        for (int i = 0; i < rows; i++) {
            System.out.print("Row " + (i + 1) + ":");
            read = stdin.readLine();
            if (read.equals(Utils.EXIT)) {
                return read;
            }
            String[] numbers = read.split(" ");
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Double.valueOf(numbers[j]);
            }

        }
        return Utils.arrayToString(matrix);
    }
}
