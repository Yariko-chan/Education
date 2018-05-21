package tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;

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
public class Server
{
    public static void main(String[] arg)
    {
        ServerSocket serverSocket = null;
        Socket clientAccepted = null;
        ObjectInputStream clientInputStream = null;
        ObjectOutputStream clientOutputStream = null;
        try {
            System.out.println("Server started....");
            serverSocket = new ServerSocket(Utils.SERVER_PORT);
            clientAccepted = serverSocket.accept();
            System.out.println("Connection established....");

            clientInputStream = new ObjectInputStream(clientAccepted.getInputStream());
            clientOutputStream = new ObjectOutputStream(clientAccepted.getOutputStream());

            String msg = (String) clientInputStream.readObject();
            while (!msg.equals(Utils.EXIT))
            {
                System.out.println("message recieved: '" + msg + "'");
                String answer = handleMessage(msg);
                clientOutputStream.writeObject(answer);
                msg = (String) clientInputStream.readObject();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (clientInputStream != null) {
                    clientInputStream.close();//закрытие потока ввода
                }
                if (clientOutputStream != null) {
                    clientOutputStream.close();//закрытие потока вывода
                }
                if (clientAccepted != null) {
                    clientAccepted.close();//закрытие сокета, выделенного для клиента
                }
                if (serverSocket != null) {
                    serverSocket.close();//закрытие сокета сервера
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String handleMessage(String msg) {
        double determinant;
        double x[][] = Utils.stringToArray(msg);
        determinant = Utils.matrixDeterminant(x);
        System.out.println (MessageFormat.format ("Determinant: {0}", Double.toString (determinant)));
        return Double.toString (determinant);
    }

}
